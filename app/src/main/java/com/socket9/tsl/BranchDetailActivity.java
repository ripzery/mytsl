package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.Contact;
import com.socket9.tsl.Utils.Singleton;

import retrofit.client.Response;
import timber.log.Timber;

public class BranchDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_detail);
        int contactId = getIntent().getIntExtra("contactId", 0);
        getContact(contactId);

    }

    public void getContact(int id){
        ApiService.getTSLApi().getContact(Singleton.getInstance().getToken(), id, new MyCallback<Contact>() {
            @Override
            public void good(Contact m, Response response) {
                Timber.d(Html.escapeHtml(m.getData().getAddress()));
                Timber.d(Html.escapeHtml(m.getData().getPhone()));
            }

            @Override
            public void bad(String error, boolean isTokenExpired) {
                Timber.d(error);
                if(isTokenExpired){
                    Singleton.toast(BranchDetailActivity.this, "Someone has access your account, please login again.", Toast.LENGTH_LONG);
                    Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
                    startActivity(new Intent(BranchDetailActivity.this, SignInActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_detail, menu);
        return true;
    }
}
