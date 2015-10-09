package com.socket9.tsl.Events;

/**
 * Created by visit on 10/9/2015 AD.
 */
//
public class BadEvent {
    public final String message;
    public final boolean isTokenExpired;

    public BadEvent(String message, boolean isTokenExpired) {
        this.message = message;
        this.isTokenExpired = isTokenExpired;
    }

}
