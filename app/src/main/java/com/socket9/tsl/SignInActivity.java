package com.socket9.tsl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.BaseModel;
import com.socket9.tsl.Models.Photo;
import com.socket9.tsl.Models.User;
import com.socket9.tsl.Utils.DialogHelper;
import com.socket9.tsl.Utils.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import timber.log.Timber;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";
    @Bind(R.id.ivLogo)
    ImageView ivLogo;
    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.tvCall)
    TextView tvCall;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.btnFbLoginFake)
    Button btnFbLoginFake;
    @Bind(R.id.btnFbLoginReal)
    LoginButton btnFbLoginReal;
    @Bind(R.id.layoutProgress)
    LinearLayout layoutProgress;
    @Bind(R.id.ivForgotPassword)
    ImageView ivForgotPassword;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        setListener();
        setupFacebook();

    }

    public void setInfo() {

    }

    public void setListener() {
        btnLogin.setOnClickListener(this);
        ivForgotPassword.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnFbLoginFake.setOnClickListener(this);
        tvCall.setOnClickListener(this);
    }

    public void setupFacebook() {
        callbackManager = CallbackManager.Factory.create();
        btnFbLoginReal.setReadPermissions("user_friends", "user_hometown", "email", "user_about_me");

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        requestGraphApi(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        if (exception.getLocalizedMessage().contains("CONNECTION_FAILURE")) {
                            Singleton.toast(SignInActivity.this, "Please check your internet connection", Toast.LENGTH_LONG);
                        }
                        // App code
                    }
                });
    }

    public void requestGraphApi(final AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            String facebookId = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String hometown = "";
                            String photo = "";
                            try {
                                hometown = object.getJSONObject("hometown").getString("name");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                photo = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            registerWithFb(facebookId, name, email, hometown, photo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,hometown,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    public void registerWithFb(final String fbId, String name, String email, String address, final String photo) {
        Timber.i(fbId);
        Timber.i(name);
        Timber.i(email);
        Timber.i(address);
        layoutProgress.setVisibility(View.VISIBLE);
        ApiService.getTSLApi().registerUser(email, "123456", name, name, email, address, "", fbId, photo, new MyCallback<User>() {
            @Override
            public void good(User m, Response response) {
                loginWithFacebook(fbId, photo);
            }

            @Override
            public void bad(String error, boolean isTokenExpired) {
                if (error.contains("already used")) {
                    loginWithFacebook(fbId, photo);
                }
            }
        });
    }

    public void loginWithFacebook(String fbId, final String photo) {
        setProgressVisible(true);
        ApiService.getTSLApi().loginWithFb(fbId, photo, new MyCallback<User>() {
            @Override
            public void good(User m, Response response) {
                Timber.d(m.getData().getToken());
                Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, m.getData().getToken());
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
//                updatePicture(photo);
            }

            @Override
            public void bad(String error, boolean isTokenExpired) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void updatePicture(String photo) {
        Glide.with(this).load(photo).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                String encodedBitmap = "data:image/png;base64," + encode(compress(resource, 100));
                ApiService.getTSLApi().uploadPhoto(Singleton.getInstance().getToken(), encodedBitmap, new MyCallback<Photo>() {
                    @Override
                    public void good(Photo m, Response response) {
                        ApiService.getTSLApi().updatePicture(Singleton.getInstance().getToken(),
                                m.getData().getPathSave(), new MyCallback<BaseModel>() {
                                    @Override
                                    public void good(BaseModel m, Response response) {
                                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                        finish();
                                        layoutProgress.setVisibility(View.GONE);
                                    }

                                });
                    }

                });
            }
        });

    }

    public byte[] compress(Bitmap bitmap, int percent) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, percent, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String encode(byte[] byteArray) {
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                setProgressVisible(true);
                ApiService.getTSLApi().login(etUsername.getText().toString(), etPassword.getText().toString(), new MyCallback<User>() {
                    @Override
                    public void good(User m, Response response) {
                        Timber.d(m.getData().getToken());
                        Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, m.getData().getToken());
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    }
                });

                break;
            case R.id.btnRegister:
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;
            case R.id.btnFbLoginFake:
                btnFbLoginReal.performClick();
                break;
            case R.id.tvCall:
                DialogHelper.getCallUsDialog(this, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        Timber.i("Call Us");
                    }
                }).show();
                break;
            case R.id.ivForgotPassword:
                DialogHelper.getForgotDialog(this, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        ApiService.getTSLApi().forgetPassword(charSequence.toString(), new MyCallback<BaseModel>() {
                            @Override
                            public void good(BaseModel m, Response response) {
                                try {
                                    Singleton.toast(SignInActivity.this, m.getMessage(), Toast.LENGTH_LONG);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).show();
                break;
        }
    }
}
