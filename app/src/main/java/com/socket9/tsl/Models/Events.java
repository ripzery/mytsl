package com.socket9.tsl.Models;

import com.socket9.tsl.ModelEntities.EventEntity;

import java.util.List;

/**
 * Created by visit on 10/4/15 AD.
 */
public class Events extends BaseModel{

    /**
     * data : [{"id":77,"titleTh":"เทส อีเว้นท์","titleEn":"Test Event","pic":"pic path","type":"Event","date":"11-03-2015"}]
     */
    private List<EventEntity> data;

    public void setData(List<EventEntity> data) {
        this.data = data;
    }

    public List<EventEntity> getData() {
        return data;
    }
}
