package com.socket9.tsl.Models;

import com.socket9.tsl.ModelEntities.NewsEntity;

/**
 * Created by visit on 10/4/15 AD.
 */
public class News extends BaseModel{
    public NewsEntity data;

    public NewsEntity getData() {
        return data;
    }

    public void setData(NewsEntity data) {
        this.data = data;
    }
}
