package com.socket9.tsl.API;

import android.content.Context;

import com.socket9.tsl.Models.BaseModel;
import com.socket9.tsl.Models.Contact;
import com.socket9.tsl.Models.ListContacts;
import com.socket9.tsl.Models.ListNewsEvent;
import com.socket9.tsl.Models.NewsEvent;
import com.socket9.tsl.Models.Photo;
import com.socket9.tsl.Models.Profile;
import com.socket9.tsl.Models.User;

import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Euro on 10/3/15 AD.
 */
public class ApiService {

//    private static final String BASE_URL = "http://uat.tsl.co.th/api";
    private static final String BASE_URL = "http://tsl.socket9.com/api";
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
        @POST("/checkLogin")
        void loginWithFb(@Field("facebookid") String facebookId, @Field("facebookpic") String facebookpic, MyCallback<User> cb);

        @FormUrlEncoded
        @POST("/forgetPassword")
        void forgetPassword(@Field("email") String email, MyCallback<BaseModel> cb);

        @FormUrlEncoded
        @POST("/registerUser")
        void registerUser(@Field("username") String username,
                          @Field("password") String password,
                          @Field("nameEn") String nameEn,
                          @Field("nameTh") String nameTh,
                          @Field("email") String email,
                          @Field("address") String address,
                          @Field("phone") String phone,
                          @Field("facebookid") String facebookId,
                          @Field("facebookpic") String facebookPic, MyCallback<User> cb);

        @FormUrlEncoded
        @POST("/getProfile")
        void getProfile(@Field("token") String token, MyCallback<Profile> cb);

        @FormUrlEncoded
        @POST("/getListNews")
        void getListNews(@Field("token") String token, MyCallback<ListNewsEvent> cb);

        @FormUrlEncoded
        @POST("/getNew")
        void getNew(@Field("token") String token, @Field("newid") int newId, MyCallback<NewsEvent> cb);

        @FormUrlEncoded
        @POST("/getListEvents")
        void getListEvents(@Field("token") String token, MyCallback<ListNewsEvent> cb);

        @FormUrlEncoded
        @POST("/getEvent")
        void getEvent(@Field("token") String token, @Field("eventid") int id, MyCallback<NewsEvent> cb);

        @FormUrlEncoded
        @POST("/getListContacts")
        void getListContacts(@Field("token") String token, MyCallback<ListContacts> cb);

        @FormUrlEncoded
        @POST("/getContact")
        void getContact(@Field("token") String token, @Field("contactid") int contactId, MyCallback<Contact> cb);

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

        @FormUrlEncoded
        @POST("/updateProfile")
        void updatePicture(@Field("token") String token,
                           @Field("picture") String picture, MyCallback<BaseModel> cb);
    }

}
