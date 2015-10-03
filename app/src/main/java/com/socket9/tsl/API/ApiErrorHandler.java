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

    @Override
    public Throwable handleError(RetrofitError cause) {
        String description;
        if(cause.getKind() == RetrofitError.Kind.NETWORK){
            //Network error here
            description = "Please check your internet connection";
        }else{
            description = cause.getMessage();
        }
        return new Exception(description);
    }
}
