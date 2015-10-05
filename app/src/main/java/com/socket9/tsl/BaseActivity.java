package com.socket9.tsl;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by visit on 10/2/15 AD.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate " + TAG);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void initToolbar(Toolbar myToolbar, String title, boolean isBackVisible){
        setSupportActionBar(myToolbar);
        mActionBar = getSupportActionBar();
//        mActionBar.setTitle(title);
        TextView tvTitle = (TextView) myToolbar.findViewById(R.id.toolbarTitle);
        tvTitle.setText(title);
//        title.setText(title);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        if(!isBackVisible)
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_grey_500_24dp);
    }
}
