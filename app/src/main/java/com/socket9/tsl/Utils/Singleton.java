package com.socket9.tsl.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by visit on 9/15/15 AD.
 */
public class Singleton {

    private static Singleton ourInstance = new Singleton();
    private static Toast toast;
    public static final String SHARED_PREF_NAME = "NavigoSharedPref";
    public static final String SHARE_PREF_KEY_TOKEN = "TOKEN";
    private static SharedPreferences sharedPreferences;

    public static Singleton getInstance() {
        return ourInstance;
    }

    public static void initSharePref(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setSharedPrefString(String key, String value){
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void setSharedPrefInt(String key, int value){
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public SharedPreferences getSharedPref(){
        return sharedPreferences;
    }

    public static void toast(Context context, String message, int length) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context, message, length);
        toast.show();
    }

}
