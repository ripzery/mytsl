package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.socket9.tsl.API.APIService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.User;
import com.socket9.tsl.Utils.Singleton;

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
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.btnFbLogin)
    Button btnFbLogin;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.tvCall)
    TextView tvCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        setListener();
    }

    public void setInfo() {

    }

    public void setListener() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
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
                APIService.getTSLApi().login(etUsername.getText().toString(), etPassword.getText().toString(), new MyCallback<User>() {
                    @Override
                    public void good(User m, Response response) {
                        Timber.d(m.getData().getToken());
                        Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, m.getData().getToken());
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void bad(String error) {
                        Timber.i(error);
                    }
                });

                break;
            case R.id.btnRegister:
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;
        }
    }
}
