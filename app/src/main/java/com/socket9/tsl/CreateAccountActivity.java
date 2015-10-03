package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateAccountActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.btnCreateAccount)
    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        initToolbar(myToolbar, "Create New Account", true);
        setListener();
    }

    public void setListener(){
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCreateAccount :
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}
