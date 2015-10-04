package com.socket9.tsl.API;

import android.content.Context;

import com.socket9.tsl.Models.BaseModel;
import com.socket9.tsl.Models.Contact;
import com.socket9.tsl.Models.Contacts;
import com.socket9.tsl.Models.Events;
import com.socket9.tsl.Models.New;
import com.socket9.tsl.Models.News;
import com.socket9.tsl.Models.Photo;
import com.socket9.tsl.Models.Profile;
import com.socket9.tsl.Models.User;

import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by visit on 10/3/15 AD.
 */
public class ApiService {

    public static final String BASE_URL = "http://tsl.socket9.com/api";
    private static TSLApi service;
    private static Context context;

    public static TSLApi getTSLApi() {
        if (service == null) {
            RestAdapter retrofit = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setErrorHandler(new ApiErrorHandler())
                    .build();

            service = retrofit.create(TSLApi.class);
        }
        return service;
    }

    public interface TSLApi {
        @FormUrlEncoded
        @POST("/checkLogin")
        void login(@Field("email") String email, @Field("password") String password, MyCallback<User> cb);

        @FormUrlEncoded
        @POST("/checklogin")
        void loginWithFb(@Field("facebookid") String facebookId, MyCallback<User> cb);

        @FormUrlEncoded
        @POST("/forgetPassword")
        void forgetPassword(@Field("email") String email, MyCallback<BaseModel> cb);

        @FormUrlEncoded
        @POST("/getProfile")
        void getProfile(@Field("token") String token, MyCallback<Profile> cb);

        @FormUrlEncoded
        @POST("/getListNews")
        void getListNews(@Field("token") String token, MyCallback<News> cb);

        @FormUrlEncoded
        @POST("/getNew")
        void getNew(@Field("token") String token, int newId, MyCallback<New> cb);

        @FormUrlEncoded
        @POST("/getListEvents")
        void getListEvents(@Field("token") String token, MyCallback<Events> cb);

        @FormUrlEncoded
        @POST("/getEvent")
        void getEvent(@Field("token") String token, @Field("eventid") int id, MyCallback<Events> cb);

        @FormUrlEncoded
        @POST("/getListContacts")
        void getListContacts(@Field("token") String token, MyCallback<Contacts> cb);

        @FormUrlEncoded
        @POST("/getContact")
        void getContact(@Field("token") String token, @Field("contactid") int contactId, MyCallback<Contact> cb);

        @FormUrlEncoded
        @POST("/registerUser")
        void registerUser(@Field("username") String username,
                          @Field("password") String password,
                          @Field("nameEn") String nameEn,
                          @Field("nameTh") String nameTh,
                          @Field("email") String email,
                          @Field("address") String address,
                          @Field("phone") String phone,
                          @Field("facebookid") String facebookId, MyCallback<User> cb);

        @FormUrlEncoded
        @POST("/emergencyCall")
        void emergencyCall(@Field("token") String token, @Field("lat") String lat, @Field("lng") String lng, @Field("type") String type,
                           MyCallback<BaseModel> cb);

        @FormUrlEncoded
        @POST("/uploadPhotoBase64")
        void uploadPhoto(@Field("token") String token, @Field("photo") String path, MyCallback<Photo> cb);

        @FormUrlEncoded
        @POST("/updateProfile")
        void updateProfile(@Field("token") String token,
                           @Field("nameEn") String nameEn,
                           @Field("nameTh") String nameTh,
                           @Field("phone") String phone,
                           @Field("address") String address,
                           @Field("picture") String picture, MyCallback<BaseModel> cb);
    }

}
