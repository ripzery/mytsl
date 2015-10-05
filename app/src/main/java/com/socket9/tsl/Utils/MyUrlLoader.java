package com.socket9.tsl.Utils;

import android.content.Context;

import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

/**
 * Created by visit on 10/5/15 AD.
 */
public class MyUrlLoader extends BaseGlideUrlLoader<MyDataModel> {

    public MyUrlLoader(Context context) {
        super(context);
    }

    @Override
    protected String getUrl(MyDataModel model, int width, int height) {
        return model.buildUrl(width,height);
    }
}
