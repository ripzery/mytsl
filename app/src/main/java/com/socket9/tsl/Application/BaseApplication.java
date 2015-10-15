package com.socket9.tsl.Application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.socket9.tsl.R;
import com.socket9.tsl.Utils.Singleton;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Euro on 10/2/15 AD.
 */
public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    @NonNull
    public static String token = "";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Singleton.initSharePref(getApplicationContext());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/samakarn/Samakarn-Bold.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        String language = Singleton.getInstance().getSharedPref().getString(Singleton.SHARE_PREF_LANG, "");
        if (language.equals("")) {
            Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_LANG, "en");
        }
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
