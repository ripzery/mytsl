package com.socket9.tsl.Models;

import com.google.gson.annotations.SerializedName;
import com.socket9.tsl.ModelEntities.NewsEntity;

import java.util.List;

/**
 * Created by visit on 10/4/15 AD.
 */
public class News extends BaseModel {


    /**
     * data : [{"id":5,"titleTh":"เตรียมเปิดตัวบิ๊กไบค์ใหม่","titleEn":"launches new Big Bike","pic":"pic path","type":"Corporation","date":"11-03-2015"},{"id":6,"titleTh":"ทดสอบ Style","titleEn":"test style","pic":"pic path","type":"Service","date":"17-03-2015"}]
     */
    private List<NewsEntity> data;

    public void setData(List<NewsEntity> data) {
        this.data = data;
    }

    public List<NewsEntity> getData() {
        return data;
    }

}
