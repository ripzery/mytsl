package com.socket9.tsl.ModelEntities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Euro on 10/4/15 AD.
 */
public class ContactEntity {

    /**
     * Phone : 02-269-9999
     * Address :  78/9 M1 Soi Chaengwattana 45
     * Fax :  02-575-2226
     * lng : 100.5294791
     * BusinessHours : abc
     * lat : 13.9041111
     */
    private int id;
    private String Phone;
    private String titleTh;
    private String titleEn;
    private String subTitle;
    private String Address;
    private String Fax;
    private String Email;
    private double lng;
    @SerializedName("Business Hours")
    private String BusinessHours;
    private double lat;
    private int icon;

    public ContactEntity(int id, String titleEn, String subTitle, int icon) {
        this.id = id;
        this.titleEn = titleEn;
        this.subTitle = subTitle;
        this.icon = icon;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getBusinessHours() {
        return BusinessHours;
    }

    public void setBusinessHours(String BusinessHours) {
        this.BusinessHours = BusinessHours;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
