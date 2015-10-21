package com.socket9.tsl.Events.Bus;

import com.socket9.tsl.Models.BaseModel;
import com.socket9.tsl.Models.Contact;
import com.socket9.tsl.Models.ListContacts;
import com.socket9.tsl.Models.ListNewsEvent;
import com.socket9.tsl.Models.NewsEvent;
import com.socket9.tsl.Models.Photo;
import com.socket9.tsl.Models.Profile;
import com.socket9.tsl.Models.User;

/**
 * Created by Euro on 10/16/2015 AD.
 */
public class ApiReceive{

    /* Use with Login and LoginWithFb Api*/
    public static class Login{
        User user;

        public Login(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class ForgetPassword{
        BaseModel model;

        public ForgetPassword(BaseModel model) {
            this.model = model;
        }

        public BaseModel getModel() {
            return model;
        }
    }

    public static class RegisterUser{
        User user;

        public RegisterUser(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class Profile{
        com.socket9.tsl.Models.Profile profile;

        public Profile(com.socket9.tsl.Models.Profile profile) {
            this.profile = profile;
        }

        public com.socket9.tsl.Models.Profile getProfile() {
            return profile;
        }
    }

    public static class ListNews{
        ListNewsEvent listNewsEvent;

        public ListNews(ListNewsEvent listNewsEvent) {
            this.listNewsEvent = listNewsEvent;
        }

        public ListNewsEvent getListNewsEvent() {
            return listNewsEvent;
        }
    }

    public static class New{
        NewsEvent newsEvent;

        public New(NewsEvent newsEvent) {
            this.newsEvent = newsEvent;
        }

        public NewsEvent getNewsEvent() {
            return newsEvent;
        }
    }

    public static class ListEvents{
        ListNewsEvent listNewsEvent;

        public ListEvents(ListNewsEvent listNewsEvent) {
            this.listNewsEvent = listNewsEvent;
        }

        public ListNewsEvent getListNewsEvent() {
            return listNewsEvent;
        }
    }

    public static class Event{
        NewsEvent newsEvent;

        public Event(NewsEvent newsEvent) {
            this.newsEvent = newsEvent;
        }

        public NewsEvent getNewsEvent() {
            return newsEvent;
        }
    }

    public static class ListContacts{
        com.socket9.tsl.Models.ListContacts listContacts;

        public ListContacts(com.socket9.tsl.Models.ListContacts listContacts) {
            this.listContacts = listContacts;
        }

        public com.socket9.tsl.Models.ListContacts getListContacts() {
            return listContacts;
        }
    }

    public static class Contact{
        com.socket9.tsl.Models.Contact contact;

        public Contact(com.socket9.tsl.Models.Contact contact) {
            this.contact = contact;
        }

        public com.socket9.tsl.Models.Contact getContact() {
            return contact;
        }
    }

    public static class EmergencyCall{
        BaseModel baseModel;

        public EmergencyCall(BaseModel baseModel) {
            this.baseModel = baseModel;
        }

        public BaseModel getBaseModel() {
            return baseModel;
        }
    }

    public static class UploadPhoto{
        Photo photo;

        public UploadPhoto(Photo photo) {
            this.photo = photo;
        }

        public Photo getPhoto() {
            return photo;
        }
    }

    public static class UpdateProfile{
        BaseModel model;

        public UpdateProfile(BaseModel model) {
            this.model = model;
        }

        public BaseModel getModel() {
            return model;
        }
    }

    public static class UpdatePicture{
        BaseModel model;

        public UpdatePicture(BaseModel model) {
            this.model = model;
        }
    }

}
