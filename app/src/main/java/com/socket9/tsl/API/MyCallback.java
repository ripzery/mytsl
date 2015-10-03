package com.socket9.tsl.API;

import com.socket9.tsl.Models.BaseModel;

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
            bad(m.getMessage());
        }
    }

    public abstract void good(T m, Response response);

    public abstract void bad(String error);

    @Override
    public void failure(RetrofitError error) {
        bad(error.getLocalizedMessage());
    }
}
