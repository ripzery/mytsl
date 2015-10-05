package com.socket9.tsl.Application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.socket9.tsl.R;
import com.socket9.tsl.Utils.Singleton;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by visit on 10/2/15 AD.
 */
public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks{

    public static String token = "";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Singleton.initSharePref(getApplicationContext());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/samakarn/Samakarn-Bold.tth")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
