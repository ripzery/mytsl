package com.socket9.tsl.Models;

import com.socket9.tsl.ModelEntities.TokenEntity;

/**
 * Created by Euro on 10/4/15 AD.
 */
public class User extends BaseModel {
    private TokenEntity data;

    public TokenEntity getData() {
        return data;
    }

    public void setData(TokenEntity data) {
        this.data = data;
    }
}
