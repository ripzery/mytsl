package com.socket9.tsl.Models;

import com.socket9.tsl.ModelEntities.NewsEventEntity;

/**
 * Created by visit on 10/4/15 AD.
 */
public class NewsEvent extends BaseModel{
    public NewsEventEntity data;

    public NewsEventEntity getData() {
        return data;
    }

    public void setData(NewsEventEntity data) {
        this.data = data;
    }
}
