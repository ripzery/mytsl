package com.socket9.tsl.Models;

/**
 * Created by visit on 10/4/15 AD.
 */
public class Profile extends BaseModel {


    /**
     * data : {"phone":"phone","nameTh":"name","address":"address","nameEn":"name","pic":"picpath or null"}
     */
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * phone : phone
         * nameTh : name
         * address : address
         * nameEn : name
         * pic : picpath or null
         */
        private String phone;
        private String nameTh;
        private String address;
        private String nameEn;
        private String pic;
        private String email;

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setNameTh(String nameTh) {
            this.nameTh = nameTh;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPhone() {
            return phone;
        }

        public String getNameTh() {
            return nameTh;
        }

        public String getAddress() {
            return address;
        }

        public String getNameEn() {
            return nameEn;
        }

        public String getPic() {
            return pic;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
