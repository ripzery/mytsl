package com.socket9.tsl.API;

import android.support.annotation.NonNull;

import com.socket9.tsl.Events.Bus.BadEvent;
import com.socket9.tsl.Events.Bus.EndApiEvent;
import com.socket9.tsl.Models.BaseModel;
import com.socket9.tsl.Utils.BusProvider;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Euro on 10/4/15 AD.
 */
public abstract class MyCallback<T extends BaseModel> implements Callback<T> {
    @Override
    public void success(@NonNull T m, Response response) {
        BusProvider.getInstance().post(new EndApiEvent());

        if (m.isResult()) {
            good(m, response);
        } else {
            bad(new BadEvent(m.getMessage(), false));
            BusProvider.getInstance().post(new BadEvent(m.getMessage(), m.getMessage().contains("token") && m.getMessage().contains("invalid")));
        }
    }

    public abstract void good(T m, Response response);

    public void bad(BadEvent badEvent){

    }

    @Override
    public void failure(@NonNull RetrofitError error) {
        BusProvider.getInstance().post(new EndApiEvent());
        BusProvider.getInstance().post(new BadEvent(error.getLocalizedMessage(), false));
        bad(new BadEvent(error.getLocalizedMessage(), false));
    }
}
