package com.socket9.tsl.Models;

import com.socket9.tsl.ModelEntities.EventEntity;

/**
 * Created by visit on 10/4/15 AD.
 */
public class Event extends EventEntity {
    public EventEntity data;

    public EventEntity getData() {
        return data;
    }

    public void setData(EventEntity data) {
        this.data = data;
    }
}
