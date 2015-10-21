package com.socket9.tsl.API;

import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.Events.Bus.BadEvent;
import com.socket9.tsl.Models.BaseModel;
import com.socket9.tsl.Models.Contact;
import com.socket9.tsl.Models.ListContacts;
import com.socket9.tsl.Models.ListNewsEvent;
import com.socket9.tsl.Models.NewsEvent;
import com.socket9.tsl.Models.Photo;
import com.socket9.tsl.Models.Profile;
import com.socket9.tsl.Models.User;
import com.socket9.tsl.Utils.BusProvider;
import com.socket9.tsl.Utils.Singleton;
import com.squareup.otto.Subscribe;

import retrofit.client.Response;
import timber.log.Timber;

/**
 * Created by Euro on 10/16/2015 AD.
 */
public class FireApiService {
    public static FireApiService fireApiService = new FireApiService();

    public static FireApiService getInstance(){
        return fireApiService;
    }

    @Subscribe
    public void onFireLogin(ApiFire.Login login) {
        ApiService.getTSLApi().login(login.getEmail(), login.getPassword(), new MyCallback<User>() {
            @Override
            public void good(User m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.Login(m));
            }
        });
    }

    @Subscribe
    public void onFireLoginWithFacebook(final ApiFire.LoginWithFb loginWithFb) {

        ApiService.getTSLApi().registerUser(loginWithFb.getUsername(),
                "123456",
                loginWithFb.getNameEn(),
                loginWithFb.getNameTh(),
                loginWithFb.getEmail(),
                loginWithFb.getAddress(),
                "",
                loginWithFb.getFacebookId(),
                loginWithFb.getFacebookPic(),
                new MyCallback<User>() {
                    @Override
                    public void good(User m, Response response) {
                        ApiService.getTSLApi().loginWithFb(loginWithFb.getFacebookId(), loginWithFb.getFacebookPic(), new MyCallback<User>() {
                            @Override
                            public void good(User m, Response response) {
                                Timber.d(m.getData().getToken());
                                Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, m.getData().getToken());
                                BusProvider.getInstance().post(new ApiReceive.Login(m));
                            }
                        });
                    }

                    @Override
                    public void bad(BadEvent badEvent) {
                        super.bad(badEvent);
                        ApiService.getTSLApi().loginWithFb(loginWithFb.getFacebookId(), loginWithFb.getFacebookPic(), new MyCallback<User>() {
                            @Override
                            public void good(User m, Response response) {
                                Timber.d(m.getData().getToken());
                                Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, m.getData().getToken());
                                BusProvider.getInstance().post(new ApiReceive.Login(m));
                            }
                        });
                    }
                });
    }

    @Subscribe
    public void onFireForgotPassword(ApiFire.ForgetPassword forgetPassword) {
        ApiService.getTSLApi().forgetPassword(forgetPassword.getEmail(), new MyCallback<BaseModel>() {
            @Override
            public void good(BaseModel m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.ForgetPassword(m));
            }
        });
    }

    @Subscribe
    public void onFireGetProfile(ApiFire.GetProfile profile) {
        ApiService.getTSLApi().getProfile(Singleton.getInstance().getToken(), new MyCallback<Profile>() {
            @Override
            public void good(Profile m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.Profile(m));
            }
        });
    }

    @Subscribe
    public void onFireGetListNews(ApiFire.GetListNews getListNews) {
        ApiService.getTSLApi().getListNews(Singleton.getInstance().getToken(), new MyCallback<ListNewsEvent>() {
            @Override
            public void good(ListNewsEvent m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.ListNews(m));
            }
        });
    }

    @Subscribe
    public void onFireGetNew(ApiFire.GetNew getNew) {
        ApiService.getTSLApi().getNew(Singleton.getInstance().getToken(), getNew.getNewId(), new MyCallback<NewsEvent>() {
            @Override
            public void good(NewsEvent m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.New(m));
            }
        });
    }

    @Subscribe
    public void onFireGetListEvents(ApiFire.GetListEvents listEvents) {
        ApiService.getTSLApi().getListEvents(Singleton.getInstance().getToken(), new MyCallback<ListNewsEvent>() {
            @Override
            public void good(ListNewsEvent m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.ListEvents(m));
            }
        });
    }

    @Subscribe
    public void onFireGetEvent(ApiFire.GetEvent getEvent) {
        ApiService.getTSLApi().getEvent(Singleton.getInstance().getToken(), getEvent.getEventId(), new MyCallback<NewsEvent>() {
            @Override
            public void good(NewsEvent m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.Event(m));
            }
        });
    }

    @Subscribe
    public void onFireGetListContacts(ApiFire.GetListContacts getListContacts) {
        ApiService.getTSLApi().getListContacts(Singleton.getInstance().getToken(), new MyCallback<ListContacts>() {
            @Override
            public void good(ListContacts m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.ListContacts(m));
            }
        });
    }

    @Subscribe
    public void onFireGetContact(ApiFire.GetContact getContact) {
        ApiService.getTSLApi().getContact(Singleton.getInstance().getToken(), getContact.getContactId(), new MyCallback<Contact>() {
            @Override
            public void good(Contact m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.Contact(m));
            }
        });
    }

    @Subscribe
    public void onFireRegisterUser(ApiFire.RegisterUser registerUser) {
        ApiService.getTSLApi().registerUser(registerUser.getUsername(),
                registerUser.getPassword(),
                registerUser.getNameEn(),
                registerUser.getNameTh(),
                registerUser.getEmail(),
                registerUser.getAddress(),
                registerUser.getPhone(),
                registerUser.getFacebookId(),
                registerUser.getFacebookPic(),
                new MyCallback<User>() {
                    @Override
                    public void good(User m, Response response) {
                        BusProvider.getInstance().post(new ApiReceive.RegisterUser(m));
                    }
                });
    }

    @Subscribe
    public void onFireEmergencyCall(ApiFire.EmergencyCall emergencyCall) {
        ApiService.getTSLApi().emergencyCall(Singleton.getInstance().getToken(), emergencyCall.getLat(), emergencyCall.getLng(), emergencyCall.getType(), new MyCallback<BaseModel>() {
            @Override
            public void good(BaseModel m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.EmergencyCall(m));
            }
        });
    }

    @Subscribe
    public void onFireUpdatePicture(ApiFire.UploadPhoto uploadPhoto) {
        ApiService.getTSLApi().updatePicture(Singleton.getInstance().getToken(), uploadPhoto.getPath(), new MyCallback<BaseModel>() {
            @Override
            public void good(BaseModel m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.UpdatePicture(m));
            }
        });
    }

    @Subscribe
    public void onFireUpdateProfile(ApiFire.UpdateProfile updateProfile) {
        ApiService.getTSLApi().updateProfile(Singleton.getInstance().getToken(), updateProfile.getNameEn(),
                updateProfile.getNameTh(),
                updateProfile.getPhone(),
                updateProfile.getAddress(),
                updateProfile.getPicture(),
                new MyCallback<BaseModel>() {
                    @Override
                    public void good(BaseModel m, Response response) {
                        BusProvider.getInstance().post(new ApiReceive.UpdateProfile(m));
                    }
                });
    }

    @Subscribe
    public void onFireUploadPhoto(ApiFire.UploadPhoto uploadPhoto) {
        ApiService.getTSLApi().uploadPhoto(Singleton.getInstance().getToken(), uploadPhoto.getPath(), new MyCallback<Photo>() {
            @Override
            public void good(Photo m, Response response) {
                BusProvider.getInstance().post(new ApiReceive.UploadPhoto(m));
            }
        });
    }
}
