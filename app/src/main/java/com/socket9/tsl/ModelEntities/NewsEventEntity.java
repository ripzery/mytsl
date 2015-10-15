package com.socket9.tsl.ModelEntities;

/**
 * Created by Euro on 10/4/15 AD.
 */
public class NewsEventEntity {
    /**
     * id : 5
     * titleTh : เตรียมเปิดตัวบิ๊กไบค์ใหม่
     * titleEn : launches new Big Bike
     * pic : pic path
     * type : Corporation
     * date : 11-03-2015
     */
    private int id;
    private String titleTh;
    private String titleEn;
    private String pic;
    private String type;
    private String date;
    private String contentEn;
    private String contentTh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleTh() {
        return titleTh;
    }

    public void setTitleTh(String titleTh) {
        this.titleTh = titleTh;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContentEn() {
        return contentEn;
    }

    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
    }

    public String getContentTh() {
        return contentTh;
    }

    public void setContentTh(String contentTh) {
        this.contentTh = contentTh;
    }
}
