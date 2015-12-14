package com.socket9.tsl.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Euro on 9/15/15 AD.
 */
public class Singleton {

    public static final String SHARE_PREF_KEY_TOKEN = "TOKEN";
    public static final String SHARE_PREF_LANG = "Language";
    private static final Singleton ourInstance = new Singleton();
    private static final String SHARED_PREF_NAME = "NavigoSharedPref";
    @Nullable
    private static Toast toast;
    private static SharedPreferences sharedPreferences;

    @NonNull
    public static Singleton getInstance() {
        return ourInstance;
    }

    public static String getPlainText(@NonNull String htmlString) {
        return htmlString.replaceAll("<.*?>", "");
    }

    public static void initSharePref(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void toast(@NonNull Context context, String message) {
        try {
            if (toast != null) {
                toast.cancel();
                toast = null;
            }
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSharedPrefString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void setSharedPrefInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public SharedPreferences getSharedPref(Context context) {
        if(sharedPreferences == null){
            initSharePref(context);
        }
        return sharedPreferences;
    }

    @Nullable
    public String getToken() {
        return sharedPreferences.getString(SHARE_PREF_KEY_TOKEN, "");
    }

}
