package com.socket9.tsl.Utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.Photo;
import com.socket9.tsl.SignInActivity;

import java.io.ByteArrayOutputStream;

import retrofit.client.Response;
import timber.log.Timber;

/**
 * Created by visit on 10/8/15 AD.
 */
public class PhotoHelper {
    public static byte[] compress(Bitmap bitmap, int percent) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, percent, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String encode(byte[] byteArray) {
        return "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String compressThenEncoded(Bitmap bitmap, int percent){
        return encode(compress(bitmap, percent));
    }

    public static void uploadPhoto(String encodedBitmap, MyCallback<Photo> callback){
        ApiService.getTSLApi().uploadPhoto(Singleton.getInstance().getToken(), encodedBitmap, callback);
    }
}
