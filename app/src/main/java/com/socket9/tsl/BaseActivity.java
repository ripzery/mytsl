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

import com.socket9.tsl.Events.BadEvent;
import com.socket9.tsl.Events.GoodEvent;
import com.socket9.tsl.Utils.Singleton;

import java.util.Locale;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Euro on 10/2/15 AD.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    @Bind(R.id.layoutProgress)
    private
    LinearLayout layoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    void setProgressVisible(boolean isVisible) {
        layoutProgress.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    // Compiler didn't detect that this is use by Eventbus
    public void onEvent(@NonNull BadEvent event) {
        setProgressVisible(false);
        if (!event.message.contains("is already used")) {
            Singleton.toast(getApplicationContext(), event.message);
        }
        Timber.i(event.message);
        if (event.isTokenExpired) {
            Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
            startActivity(new Intent(this, SignInActivity.class));
        }
        Timber.i("BadEvent : " + event.message);
    }

    public void onEvent(GoodEvent event) {
        setProgressVisible(false);
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
