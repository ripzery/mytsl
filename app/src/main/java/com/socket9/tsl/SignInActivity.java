package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.socket9.tsl.Models.User;
import com.socket9.tsl.Utils.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

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
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.layoutProgress)
    LinearLayout layoutProgress;
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
        btnRegister.setOnClickListener(this);
        btnFbLoginFake.setOnClickListener(this);
    }

    public void setupFacebook() {
        callbackManager = CallbackManager.Factory.create();
        btnFbLoginReal.setReadPermissions("user_friends", "user_hometown", "email","user_about_me");

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
                            try {
                                hometown = object.getJSONObject("hometown").getString("name");
                            } catch (Exception e) {

                            }
                            Singleton.setAccessToken(token);
                            registerWithFb(facebookId, name, email, hometown);
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


    public void registerWithFb(final String fbId, String name, String email, String address) {
        Timber.i(fbId);
        Timber.i(name);
        Timber.i(email);
        Timber.i(address);
        layoutProgress.setVisibility(View.VISIBLE);
        ApiService.getTSLApi().registerUser(email, "123456", name, name, email, address, "", fbId, new MyCallback<User>() {
            @Override
            public void good(User m, Response response) {
                loginWithFacebook(fbId);
            }

            @Override
            public void bad(String error) {
                Timber.i(error);
                if (error.contains("already used")) {
                    loginWithFacebook(fbId);
                }
            }
        });
    }

    public void loginWithFacebook(String fbId) {
        ApiService.getTSLApi().loginWithFb(fbId, new MyCallback<User>() {
            @Override
            public void good(User m, Response response) {
                Timber.d(m.getData().getToken());
                Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, m.getData().getToken());
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
                layoutProgress.setVisibility(View.GONE);
            }

            @Override
            public void bad(String error) {
                Timber.i(error);
                Singleton.toast(SignInActivity.this, error, Toast.LENGTH_LONG);
                layoutProgress.setVisibility(View.GONE);
            }
        });
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
                ApiService.getTSLApi().login(etUsername.getText().toString(), etPassword.getText().toString(), new MyCallback<User>() {
                    @Override
                    public void good(User m, Response response) {
                        Timber.d(m.getData().getToken());
                        Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, m.getData().getToken());
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void bad(String error) {
                        Singleton.toast(getApplicationContext(), error, Toast.LENGTH_LONG);
                        Timber.i(error);
                    }
                });

                break;
            case R.id.btnRegister:
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;
            case R.id.btnFbLoginFake:
                btnFbLoginReal.performClick();
                break;
        }
    }
}
