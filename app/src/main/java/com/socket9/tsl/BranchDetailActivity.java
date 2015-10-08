package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.Contact;
import com.socket9.tsl.Utils.MapHelper;
import com.socket9.tsl.Utils.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import timber.log.Timber;

public class BranchDetailActivity extends BaseActivity {

    @Bind(R.id.toolbarTitle)
    TextView toolbarTitle;
    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.mapView)
    MapView mapView;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvPhone)
    TextView tvPhone;
    @Bind(R.id.layoutProgress)
    LinearLayout layoutProgress;
    @Bind(R.id.tvHours)
    TextView tvHours;
    @Bind(R.id.tvEmail)
    TextView tvEmail;
    private MapHelper mapHelper;
    private MapHelper.MapListener mapListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_detail);
        ButterKnife.bind(this);
        initToolbar(myToolbar, getString(R.string.toolbar_branch_detail), true);
        initMap(savedInstanceState);
        int contactId = getIntent().getIntExtra("contactId", 0);
        String contactName = getIntent().getStringExtra("contactName");
        getContact(contactId, contactName);

    }

    public void initMap(Bundle savedInstanceState) {
        try {
            mapHelper = new MapHelper();
            mapHelper.initMap(this, mapView, savedInstanceState);
            mapHelper.getMap().setMyLocationEnabled(false);

        } catch (Exception e) {
            Singleton.toast(BranchDetailActivity.this, getString(R.string.toast_update_google_play_services), Toast.LENGTH_LONG);
            e.printStackTrace();
        }
    }

    public void getContact(int id, final String contactName) {
        layoutProgress.setVisibility(View.VISIBLE);
        ApiService.getTSLApi().getContact(Singleton.getInstance().getToken(), id, new MyCallback<Contact>() {

            @Override
            public void good(Contact m, Response response) {
                layoutProgress.setVisibility(View.GONE);
                toolbarTitle.setText(contactName);
                toolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                LatLng contactLatLng = new LatLng(m.getData().getLat(), m.getData().getLng());
                mapHelper.addMarkerThenZoom(contactLatLng, 15);

                try{
                    tvPhone.setText(Singleton.getPlainText(m.getData().getPhone()));
                    tvEmail.setText(Singleton.getPlainText(m.getData().getEmail()));
                    tvHours.setText(Html.fromHtml(m.getData().getBusinessHours()));
                    tvAddress.setText(Singleton.getPlainText(m.getData().getAddress()));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void bad(String error, boolean isTokenExpired) {
                layoutProgress.setVisibility(View.GONE);
                Timber.d(error);
                if (isTokenExpired) {
                    Singleton.toast(BranchDetailActivity.this, getString(R.string.toast_token_invalid), Toast.LENGTH_LONG);
                    Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
                    startActivity(new Intent(BranchDetailActivity.this, SignInActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapHelper.getmMapView().onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapHelper.getmMapView().onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapHelper.getmMapView().onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapHelper.getmMapView().onLowMemory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_detail, menu);
        return true;
    }
}
