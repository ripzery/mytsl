package com.socket9.tsl;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socket9.tsl.API.FireApiService;
import com.socket9.tsl.Events.Bus.BadEvent;
import com.socket9.tsl.Events.Bus.EndApiEvent;
import com.socket9.tsl.Events.Bus.StartApiEvent;
import com.socket9.tsl.Utils.BusProvider;
import com.socket9.tsl.Utils.Singleton;
import com.squareup.otto.Subscribe;

import java.util.Locale;

import butterknife.Bind;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Euro on 10/2/15 AD.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    @Bind(R.id.layoutProgress)
    LinearLayout layoutProgress;
    private Object startEndApiEvent;
    private FireApiService fireApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startEndApiEvent = new Object() {
            @Subscribe
            public void onStartApiEvent(StartApiEvent event){
                setProgressVisible(true);
            }

            @Subscribe
            public void onEndApiEvent(EndApiEvent event){
                setProgressVisible(false);
            }

            @Subscribe
            public void onBadEvent(BadEvent event){
                setProgressVisible(false);
                Timber.i(event.message);
                if (!event.message.contains("is already used")) {
                    Singleton.toast(getApplicationContext(), event.message);
                }
                if (event.isTokenExpired) {
                    Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
                    startActivity(new Intent(BaseActivity.this, SignInActivity.class));
                }
                Timber.i("BadEvent : " + event.message);
            }
        };


    }



    @Override
    public void setContentView(int layoutResID) {
        FrameLayout baseLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout childLayout = (FrameLayout) baseLayout.findViewById(R.id.layoutContent);
        getLayoutInflater().inflate(layoutResID, childLayout, true);
        super.setContentView(baseLayout);
    }

    void setLocale(@NonNull String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_LANG, lang);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        BusProvider.getInstance().register(startEndApiEvent);
    }

    @Override
    protected void onPause() {
        BusProvider.getInstance().unregister(this);
        BusProvider.getInstance().unregister(startEndApiEvent);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void setProgressVisible(boolean isVisible) {
        layoutProgress.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void initToolbar(@NonNull Toolbar myToolbar, String title, boolean isBackVisible) {
        setSupportActionBar(myToolbar);
        ActionBar mActionBar = getSupportActionBar();
        TextView tvTitle = (TextView) myToolbar.findViewById(R.id.toolbarTitle);
        tvTitle.setText(title);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        if (!isBackVisible)
            mActionBar.setHomeAsUpIndicator(R.drawable.menu);
    }
}
