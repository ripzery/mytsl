package com.socket9.tsl.API;

import android.support.annotation.NonNull;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by Euro on 10/4/15 AD.
 */
class ApiErrorHandler implements ErrorHandler {

    @NonNull
    @Override
    public Throwable handleError(@NonNull RetrofitError cause) {
        String description;
        if (cause.getKind() == RetrofitError.Kind.NETWORK) {
            //Network error here
            description = "Please check your internet connection";
        } else {
            description = cause.getMessage();
        }
        return new Exception(description);
    }
}
