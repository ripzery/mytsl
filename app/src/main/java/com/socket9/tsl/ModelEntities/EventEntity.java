package com.socket9.tsl.ModelEntities;

/**
 * Created by visit on 10/4/15 AD.
 */
public class EventEntity {

    /**
     * id : 77
     * titleTh : เทส อีเว้นท์
     * titleEn : Test Event
     * pic : pic path
     * type : Event
     * date : 11-03-2015
     */
    private int id;
    private String titleTh;
    private String titleEn;
    private String pic;
    private String type;
    private String date;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitleTh(String titleTh) {
        this.titleTh = titleTh;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitleTh() {
        return titleTh;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getPic() {
        return pic;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}
