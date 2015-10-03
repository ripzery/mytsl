package com.socket9.tsl.Models;

/**
 * Created by visit on 10/4/15 AD.
 */
public class User extends BaseModel {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
