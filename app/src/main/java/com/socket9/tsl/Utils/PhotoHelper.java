package com.socket9.tsl.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.TypedValue;

import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.Photo;

import java.io.ByteArrayOutputStream;

/**
 * Created by Euro on 10/8/15 AD.
 */
public class PhotoHelper {
    private static byte[] compress(@NonNull Bitmap bitmap, int percent) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, percent, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @NonNull
    private static String encode(@NonNull byte[] byteArray) {
        return "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @NonNull
    public static String compressThenEncoded(@NonNull Bitmap bitmap, int percent) {
        return encode(compress(bitmap, percent));
    }

    public static int convertDpToPx(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
