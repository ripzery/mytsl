package com.socket9.tsl.Models;

import com.socket9.tsl.ModelEntities.ContactEntity;

/**
 * Created by visit on 10/4/15 AD.
 */
public class Contact extends BaseModel {
    private ContactEntity data;

    public ContactEntity getData() {
        return data;
    }

    public void setData(ContactEntity data) {
        this.data = data;
    }
}
