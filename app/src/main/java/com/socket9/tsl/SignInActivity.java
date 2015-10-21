package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.Utils.BusProvider;
import com.socket9.tsl.Utils.DialogHelper;
import com.socket9.tsl.Utils.Singleton;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    @Bind(R.id.ivForgotPassword)
    ImageView ivForgotPassword;
    private CallbackManager callbackManager;
    private String facebookId;
    private String fbPhoto;

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

    private void setListener() {
        btnLogin.setOnClickListener(this);
        ivForgotPassword.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnFbLoginFake.setOnClickListener(this);
        tvCall.setOnClickListener(this);
    }

    private void setupFacebook() {
        callbackManager = CallbackManager.Factory.create();
        btnFbLoginReal.setReadPermissions("user_friends", "user_hometown", "email", "user_about_me");

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(@NonNull LoginResult loginResult) {
                        requestGraphApi(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(@NonNull FacebookException exception) {
                        if (exception.getLocalizedMessage().contains("CONNECTION_FAILURE")) {
                            Singleton.toast(SignInActivity.this, "Please check your internet connection");
                        }
                        // App code
                    }
                });
    }

    private void requestGraphApi(final AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(@NonNull JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            facebookId = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String hometown = "";
                            fbPhoto = "";
                            try {
                                hometown = object.getJSONObject("hometown").getString("name");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                fbPhoto = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BusProvider.post(new ApiFire.LoginWithFb(email, name, name, email, hometown, facebookId, fbPhoto));
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

    @Subscribe
    public void onReceiveLogin(ApiReceive.Login login) {
        Timber.d(login.getUser().getData().getToken());
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    @Subscribe
    public void onReceiveForgetPassword(ApiReceive.ForgetPassword forgetPassword) {
        try {
            Singleton.toast(SignInActivity.this, forgetPassword.getModel().getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onReceiveLoginWithFacebook(ApiReceive.Login m) {
        startActivity(new Intent(SignInActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                BusProvider.post(new ApiFire.Login(etUsername.getText().toString(), etPassword.getText().toString()));
                break;
            case R.id.btnRegister:
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;
            case R.id.btnFbLoginFake:
                btnFbLoginReal.performClick();
                break;
            case R.id.tvCall:
                DialogHelper.getCallUsDialog(this, new MaterialDialog.SingleButtonCallback() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        Timber.i("Call Us");
                    }
                }).show();
                break;
            case R.id.ivForgotPassword:
                DialogHelper.getForgotDialog(this, new MaterialDialog.InputCallback() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public void onInput(@NonNull MaterialDialog materialDialog, @NonNull CharSequence charSequence) {
                        BusProvider.post(new ApiFire.ForgetPassword(charSequence.toString()));
                    }
                }).show();
                break;
        }
    }
}
