package com.socket9.tsl.Utils;

import android.databinding.BindingAdapter;

import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

/**
 * Created by Euro on 11/2/2015 AD.
 */
public class DataBinder {

    public DataBinder() {

    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(CircleImageView circleImageView, String url){
        Timber.i("imageUrl : " + url);
    }
}
