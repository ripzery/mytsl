package com.socket9.tsl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.socket9.tsl.Utils.Singleton;

import timber.log.Timber;

/**
 * Created by visit on 10/4/15 AD.
 */
public class EntryActivity  extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isHasToken = !Singleton.getInstance().getSharedPref().getString(Singleton.SHARE_PREF_KEY_TOKEN, "").equals("");
        Timber.i(Singleton.getInstance().getSharedPref().getString(Singleton.SHARE_PREF_KEY_TOKEN, ""));
        startActivity(new Intent(this, isHasToken ? MainActivity.class : SignInActivity.class));
        finish();
    }
}
