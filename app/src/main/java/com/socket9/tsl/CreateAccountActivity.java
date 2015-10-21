package com.socket9.tsl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.Utils.BusProvider;
import com.socket9.tsl.Utils.Singleton;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateAccountActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.toolbarTitle)
    TextView toolbarTitle;
    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etAddress)
    EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        initToolbar(myToolbar, getString(R.string.toolbar_create_account), true);
        setListener();
    }

    private void setListener() {
        btnRegister.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
        return true;
    }

    @Subscribe
    public void onReceiveRegister(ApiReceive.RegisterUser registerUser) {
        Timber.d("Token : " + registerUser.getUser().getData().getToken());
        Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, registerUser.getUser().getData().getToken());
        Singleton.toast(getApplicationContext(), getString(R.string.toast_activate_account));
        finish();
    }


    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                BusProvider.post(new ApiFire.RegisterUser(etEmail.getText().toString(),
                        etPassword.getText().toString(),
                        etUsername.getText().toString(),
                        etUsername.getText().toString(),
                        etEmail.getText().toString(),
                        etAddress.getText().toString(),
                        etPhone.getText().toString(),
                        "", ""));
                break;
        }
    }
}
