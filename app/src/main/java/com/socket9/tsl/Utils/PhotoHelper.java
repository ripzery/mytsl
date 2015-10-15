package com.socket9.tsl.Utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.Photo;

import java.io.ByteArrayOutputStream;

/**
 * Created by Euro on 10/8/15 AD.
 */
class PhotoHelper {
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

    public static void uploadPhoto(String encodedBitmap, MyCallback<Photo> callback) {
        ApiService.getTSLApi().uploadPhoto(Singleton.getInstance().getToken(), encodedBitmap, callback);
    }
}
