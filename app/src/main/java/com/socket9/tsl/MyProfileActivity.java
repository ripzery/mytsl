package com.socket9.tsl;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyProfileActivity extends BaseActivity {

    @Bind(R.id.toolbarTitle)
    TextView toolbarTitle;
    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.ivUser)
    ImageView ivUser;
    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etAddress)
    EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        initToolbar(myToolbar, "My Profile", true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
        return true;
    }

}
