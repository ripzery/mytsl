package com.socket9.tsl.Events.Bus;

import com.socket9.tsl.Models.User;

/**
 * Created by Euro on 10/16/2015 AD.
 */
public class ApiFire {
    public static class Login{
        String email;
        String password;

        public Login(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class LoginWithFb{
        String username;
        String nameEn;
        String nameTh;
        String email;
        String address;
        String facebookId;
        String facebookPic;

        public LoginWithFb(String username, String nameEn, String nameTh, String email, String address , String facebookId, String facebookPic) {
            this.username = username;
            this.nameEn = nameEn;
            this.nameTh = nameTh;
            this.email = email;
            this.address = address;
            this.facebookId = facebookId;
            this.facebookPic = facebookPic;
        }

        public String getUsername() {
            return username;
        }

        public String getNameEn() {
            return nameEn;
        }

        public String getNameTh() {
            return nameTh;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }

        public String getFacebookId() {
            return facebookId;
        }

        public String getFacebookPic() {
            return facebookPic;
        }
    }

    public static class ForgetPassword{
        String email;

        public ForgetPassword(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    public static class GetProfile{

    }

    public static class GetListNews{

    }

    public static class GetNew{
        int newId;

        public GetNew(int newId) {
            this.newId = newId;
        }

        public int getNewId() {
            return newId;
        }
    }

    public static class GetListEvents{

    }

    public static class GetEvent{
        int eventId;

        public GetEvent(int eventId) {
            this.eventId = eventId;
        }

        public int getEventId() {
            return eventId;
        }
    }

    public static class GetListContacts{

    }

    public static class GetContact{
        int contactId;

        public GetContact(int contactId) {
            this.contactId = contactId;
        }

        public int getContactId() {
            return contactId;
        }
    }

    public static class RegisterUser{
        String username;
        String password;
        String nameEn;
        String nameTh;
        String email;
        String address;
        String phone;
        String facebookId;
        String facebookPic;

        public RegisterUser(String username, String password, String nameEn, String nameTh, String email, String address, String phone, String facebookId, String facebookPic) {
            this.username = username;
            this.password = password;
            this.nameEn = nameEn;
            this.nameTh = nameTh;
            this.email = email;
            this.address = address;
            this.phone = phone;
            this.facebookId = facebookId;
            this.facebookPic = facebookPic;
        }

        public RegisterUser(String username, String password, String nameEn, String nameTh, String email) {
            this.username = username;
            this.password = password;
            this.nameEn = nameEn;
            this.nameTh = nameTh;
            this.email = email;
        }

        public RegisterUser(String username, String password, String nameEn, String nameTh, String email, String facebookId, String facebookPic) {
            this.username = username;
            this.password = password;
            this.nameEn = nameEn;
            this.nameTh = nameTh;
            this.email = email;
            this.facebookId = facebookId;
            this.facebookPic = facebookPic;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getNameEn() {
            return nameEn;
        }

        public String getNameTh() {
            return nameTh;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public String getFacebookId() {
            return facebookId;
        }

        public String getFacebookPic() {
            return facebookPic;
        }
    }

    public static class EmergencyCall{
        String lat;
        String lng;
        String type;

        public EmergencyCall(String lat, String lng, String type) {
            this.lat = lat;
            this.lng = lng;
            this.type = type;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }

        public String getType() {
            return type;
        }
    }

    public static class UploadPhoto {
        String path;

        public UploadPhoto(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public static class UpdateProfile{
        String nameEn;
        String nameTh;
        String phone;
        String address;
        String picture;

        public UpdateProfile(String nameEn, String nameTh, String phone, String address, String picture) {
            this.nameEn = nameEn;
            this.nameTh = nameTh;
            this.phone = phone;
            this.address = address;
            this.picture = picture;
        }

        public String getNameEn() {
            return nameEn;
        }

        public String getNameTh() {
            return nameTh;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getPicture() {
            return picture;
        }
    }

    public static class UpdatePicture{
        String picture;

        public UpdatePicture(String picture) {
            this.picture = picture;
        }

        public String getPicture() {
            return picture;
        }
    }
}
