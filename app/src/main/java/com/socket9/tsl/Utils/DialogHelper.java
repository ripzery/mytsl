package com.socket9.tsl.Utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by visit on 10/7/15 AD.
 */
public class DialogHelper {
    public static MaterialDialog getForgotDialog(Context context, MaterialDialog.InputCallback callback){
        return new MaterialDialog.Builder(context)
                .title("Forgot Password")
                .content("Enter your email address to request a password reset.")
                .positiveText("Request")
                .negativeText("Cancel")
                .input("Your email","",false, callback)
                .build();
    }

    public static MaterialDialog getUpdateProfileDialog(Context context, MaterialDialog.SingleButtonCallback callback){
        return new MaterialDialog.Builder(context)
                .title("Confirmation")
                .content("Exit without saving your changed?")
                .positiveText("Yes")
                .negativeText("No")
                .onPositive(callback)
                .build();
    }

    public static MaterialDialog getSignOutDialog(Context context, MaterialDialog.SingleButtonCallback callback){
        return new MaterialDialog.Builder(context)
                .title("Sign out")
                .content("Are you sure you want to sign out?")
                .positiveText("Yes")
                .negativeText("No")
                .onPositive(callback)
                .build();
    }
}
