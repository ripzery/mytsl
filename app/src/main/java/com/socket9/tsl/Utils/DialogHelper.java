package com.socket9.tsl.Utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.afollestad.materialdialogs.MaterialDialog;
import com.socket9.tsl.R;

/**
 * Created by visit on 10/7/15 AD.
 */
public class DialogHelper {
    public static MaterialDialog getForgotDialog(Context context, MaterialDialog.InputCallback callback){
        return new MaterialDialog.Builder(context)
                .title(getString(context, R.string.dialog_forgot_pw_title))
                .content(getString(context, R.string.dialog_forgot_pw_content))
                .positiveText(getString(context, R.string.dialog_forgot_pw_positive))
                .negativeText(getString(context, R.string.dialog_forgot_pw_negative))
                .input(getString(context, R.string.dialog_forgot_pw_input_hint),"",false, callback)
                .build();
    }

    public static MaterialDialog getCallUsDialog(Context context, MaterialDialog.SingleButtonCallback callback){
        return new MaterialDialog.Builder(context)
                .title(getString(context, R.string.dialog_call_us_title))
                .content(getString(context, R.string.dialog_call_us_content))
                .positiveText(getString(context, R.string.dialog_call_us_positive))
                .negativeText(getString(context, R.string.dialog_call_us_negative))
                .onPositive(callback)
                .build();
    }

    public static MaterialDialog getUpdateProfileDialog(Context context, MaterialDialog.SingleButtonCallback callback){
        return new MaterialDialog.Builder(context)
                .title(getString(context, R.string.dialog_update_profile_title))
                .content(getString(context, R.string.dialog_update_profile_content))
                .positiveText(getString(context, R.string.dialog_update_profile_positive))
                        .negativeText(getString(context, R.string.dialog_update_profile_negative))
                        .onPositive(callback)
                        .build();
    }

    public static MaterialDialog getSignOutDialog(Context context, MaterialDialog.SingleButtonCallback callback) {
        return new MaterialDialog.Builder(context)
                .title(getString(context, R.string.dialog_signout_title))
                .content(getString(context, R.string.dialog_signout_content))
                .positiveText(getString(context, R.string.dialog_signout_positive))
                .negativeText(getString(context, R.string.dialog_signout_negative))
                .onPositive(callback)
                .build();
    }

    public static MaterialDialog getChangeLangDialog(Context context, MaterialDialog.ListCallbackSingleChoice callback){
        return new MaterialDialog.Builder(context)
                .title(getString(context , R.string.dialog_change_lang_title))
                .items(R.array.change_language)
                .itemsCallbackSingleChoice(Singleton.getInstance().getSharedPref().getString(Singleton.SHARE_PREF_LANG, "").equals("en") ? 1 : 0 ,callback)
                .negativeText(getString(context, R.string.dialog_change_lang_cancel))
                .positiveText(getString(context ,R.string.dialog_change_lang_choose))
                .build();
    }

    public static String getString(Context context, int id){
        return context.getString(id);
    }

}
