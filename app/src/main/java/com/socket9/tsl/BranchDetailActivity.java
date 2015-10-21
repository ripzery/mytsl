package com.socket9.tsl;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.Utils.BusProvider;
import com.socket9.tsl.Utils.MapHelper;
import com.socket9.tsl.Utils.Singleton;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    @Bind(R.id.tvHours)
    TextView tvHours;
    @Bind(R.id.tvEmail)
    TextView tvEmail;
    private MapHelper mapHelper;
    private MapHelper.MapListener mapListener;
    private boolean isAlreadyGetContact = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_detail);
        ButterKnife.bind(this);
        initToolbar(myToolbar, getString(R.string.toolbar_branch_detail), true);
        initMap(savedInstanceState);
    }

    private void initMap(Bundle savedInstanceState) {
        try {
            mapHelper = new MapHelper();
            mapHelper.initMap(this, mapView, savedInstanceState);
            mapHelper.getMap().setMyLocationEnabled(false);

        } catch (Exception e) {
            Singleton.toast(BranchDetailActivity.this, getString(R.string.toast_update_google_play_services));
            e.printStackTrace();
        }
    }

    private void getContact(int id) {
        if(isAlreadyGetContact) return;
        isAlreadyGetContact = true;
        BusProvider.post(new ApiFire.GetContact(id));
    }

    @Subscribe
    public void onReceiveContact(ApiReceive.Contact contact) {
        toolbarTitle.setText(getIntent().getStringExtra("contactName"));
        toolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        LatLng contactLatLng = new LatLng(contact.getContact().getData().getLat(), contact.getContact().getData().getLng());
        mapHelper.addMarkerThenZoom(contactLatLng);

        try {
            tvPhone.setText(Singleton.getPlainText(contact.getContact().getData().getPhone()));
            tvEmail.setText(Singleton.getPlainText(contact.getContact().getData().getEmail()));
            tvHours.setText(Html.fromHtml(contact.getContact().getData().getBusinessHours()));
            tvAddress.setText(Singleton.getPlainText(contact.getContact().getData().getAddress()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapHelper.getmMapView().onResume();
        int contactId = getIntent().getIntExtra("contactId", 0);
        getContact(contactId);
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
