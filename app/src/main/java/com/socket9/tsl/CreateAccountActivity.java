package com.socket9.tsl;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.User;
import com.socket9.tsl.Utils.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import timber.log.Timber;

public class CreateAccountActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.toolbarTitle)
    TextView toolbarTitle;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etAddress)
    EditText etAddress;
    @Bind(R.id.layoutProgress)
    LinearLayout layoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        initToolbar(myToolbar, getString(R.string.toolbar_create_account), true);
        setListener();
    }

    public void setListener() {
        btnRegister.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                layoutProgress.setVisibility(View.VISIBLE);
                ApiService.getTSLApi().registerUser(etEmail.getText().toString(),
                        etPassword.getText().toString(),
                        etUsername.getText().toString(),
                        etUsername.getText().toString(),
                        etEmail.getText().toString(),
                        etAddress.getText().toString(),
                        etPhone.getText().toString(),
                        "","", new MyCallback<User>() {
                            @Override
                            public void good(User m, Response response) {
                                layoutProgress.setVisibility(View.GONE);
                                Timber.d("Token : " + m.getData().getToken());
                                Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, m.getData().getToken());
                                Singleton.toast(getApplicationContext(), getString(R.string.toast_activate_account), Toast.LENGTH_LONG);
                                finish();
                            }

                            @Override
                            public void bad(String error, boolean isTokenExpired) {
                                Singleton.toast(CreateAccountActivity.this, error, Toast.LENGTH_LONG);
                            }
                        }
                );
                break;
        }
    }
}
