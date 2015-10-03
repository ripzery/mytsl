package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    public void setInfo(){

    }

    public void setListener(){
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
        switch (view.getId()){
            case R.id.btnLogin :
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.btnRegister :
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;
        }
    }
}
