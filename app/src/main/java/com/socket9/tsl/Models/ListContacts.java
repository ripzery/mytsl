package com.socket9.tsl.Models;

import com.socket9.tsl.ModelEntities.ContactEntity;

import java.util.List;

/**
 * Created by visit on 10/4/15 AD.
 */
public class ListContacts extends BaseModel {
    private List<ContactEntity> data;

    public List<ContactEntity> getData() {
        return data;
    }

    public void setData(List<ContactEntity> data) {
        this.data = data;
    }
}
