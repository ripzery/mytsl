package com.socket9.tsl.API;

import android.widget.Toast;

import com.socket9.tsl.Models.BaseModel;
import com.socket9.tsl.Utils.Singleton;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by visit on 10/4/15 AD.
 */
public abstract class MyCallback <T extends BaseModel> implements Callback<T>{
    @Override
    public void success(T m, Response response) {
        if(m.isResult()){
            good(m, response);
        }else{
            bad(m.getMessage(), m.getMessage().contains("token") && m.getMessage().contains("invalid"));
        }
    }

    public abstract void good(T m, Response response);

    public abstract void bad(String error, boolean isTokenExpired);

    @Override
    public void failure(RetrofitError error) {
        bad(error.getLocalizedMessage(), false);
    }
}
