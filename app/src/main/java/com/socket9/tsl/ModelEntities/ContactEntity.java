package com.socket9.tsl.ModelEntities;

/**
 * Created by visit on 10/4/15 AD.
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
    private String Address;
    private String Fax;
    private double lng;
    private String BusinessHours;
    private double lat;

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setBusinessHours(String BusinessHours) {
        this.BusinessHours = BusinessHours;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

    public String getFax() {
        return Fax;
    }

    public double getLng() {
        return lng;
    }

    public String getBusinessHours() {
        return BusinessHours;
    }

    public double getLat() {
        return lat;
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
}
