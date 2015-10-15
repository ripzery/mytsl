package com.socket9.tsl.API;

import android.support.annotation.NonNull;

import com.socket9.tsl.Events.BadEvent;
import com.socket9.tsl.Events.GoodEvent;
import com.socket9.tsl.Models.BaseModel;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Euro on 10/4/15 AD.
 */
public abstract class MyCallback<T extends BaseModel> implements Callback<T> {
    @Override
    public void success(@NonNull T m, Response response) {
        if (m.isResult()) {
            good(m, response);
            EventBus.getDefault().post(new GoodEvent());
        } else {
            bad(m.getMessage(), m.getMessage().contains("token") && m.getMessage().contains("invalid"));
            EventBus.getDefault().post(new BadEvent(m.getMessage(), m.getMessage().contains("token") && m.getMessage().contains("invalid")));
        }
    }

    public abstract void good(T m, Response response);

    public void bad(String error, boolean isTokenExpired) {

    }

    @Override
    public void failure(@NonNull RetrofitError error) {
        bad(error.getLocalizedMessage(), false);
        EventBus.getDefault().post(new BadEvent(error.getLocalizedMessage(), false));
    }
}
