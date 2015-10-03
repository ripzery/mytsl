package com.socket9.tsl.API;

import android.content.Context;
import android.widget.Toast;

import com.socket9.tsl.Utils.Singleton;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by visit on 10/4/15 AD.
 */
public class ApiErrorHandler implements ErrorHandler {

    public Context context;

    public ApiErrorHandler(Context context) {
        this.context = context;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        String description;
        if(cause.getKind() == RetrofitError.Kind.NETWORK){
            //Network error here
            description = "Please check your internet connection";
        }else{
            description = cause.getMessage();
        }
        Singleton.toast(context, cause.getMessage(), Toast.LENGTH_LONG);
        return new Exception(description);
    }
}
