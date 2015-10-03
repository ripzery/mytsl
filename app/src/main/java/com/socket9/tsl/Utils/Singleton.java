package com.socket9.tsl.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by visit on 9/15/15 AD.
 */
public class Singleton {

    private static Singleton ourInstance = new Singleton();
    private static Toast toast;

    public static Singleton getInstance() {
        return ourInstance;
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
