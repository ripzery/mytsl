package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.socket9.tsl.Fragments.ContactFragment;
import com.socket9.tsl.Fragments.EmergencyFragment;
import com.socket9.tsl.Fragments.EventFragment;
import com.socket9.tsl.Fragments.HomeFragment;
import com.socket9.tsl.Fragments.MyProfileFragment;
import com.socket9.tsl.Fragments.NewsFragment;
import com.socket9.tsl.Utils.DialogHelper;
import com.socket9.tsl.Utils.OnFragmentInteractionListener;
import com.socket9.tsl.Utils.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener, View.OnClickListener {

    public static final int FRAGMENT_DISPLAY_HOME = 1;
    public static final int FRAGMENT_DISPLAY_NEWS = 2;
    public static final int FRAGMENT_DISPLAY_CONTACT = 3;
    public static final int FRAGMENT_DISPLAY_EMERGENCY = 4;
    public static final int FRAGMENT_DISPLAY_PROFILE = 5;
    public static final int FRAGMENT_DISPLAY_EVENT = 6;
    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Bind(R.id.parent_layout)
    RelativeLayout parentLayout;
    @Bind(R.id.navView)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbarTitle)
    TextView toolbarTitle;
    @Bind(R.id.layoutNewsEvent)
    LinearLayout layoutNewsEvent;
    @Bind(R.id.btnLeft)
    Button btnLeft;
    @Bind(R.id.btnRight)
    Button btnRight;
    @Bind(R.id.layoutProgress)
    LinearLayout layoutProgress;
    private Fragment homeFragment;
    private Fragment newsFragment;
    private Fragment contactFragment;
    private Fragment emergencyFragment;
    private Fragment profileFragment;
    private Fragment eventFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar(myToolbar, "Main", false);
        initFragment();
        setListener();
        setupDrawerContent();
        replaceFragment(FRAGMENT_DISPLAY_HOME);
        navView.setCheckedItem(R.id.nav_home);
    }

    public void setListener() {
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        newsFragment = new NewsFragment();
        eventFragment = new EventFragment();
        contactFragment = new ContactFragment();
        emergencyFragment = new EmergencyFragment();
        profileFragment = new MyProfileFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setupDrawerContent() {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        replaceFragment(FRAGMENT_DISPLAY_HOME);
                        menuItem.setChecked(true);
                        break;
                    case R.id.nav_news:
                        replaceFragment(FRAGMENT_DISPLAY_NEWS);
                        menuItem.setChecked(true);
                        break;
                    case R.id.nav_contact:
                        replaceFragment(FRAGMENT_DISPLAY_CONTACT);
                        menuItem.setChecked(true);
                        break;
                    case R.id.nav_emergency_call:
                        replaceFragment(FRAGMENT_DISPLAY_EMERGENCY);
                        menuItem.setChecked(true);
                        break;
                    case R.id.nav_sign_out:
                        // Show dialog
                        DialogHelper.getSignOutDialog(MainActivity.this, new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                LoginManager.getInstance().logOut();
                                Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
                                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                                finish();
                            }

                        }).show();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void replaceFragment(int mode) {
        switch (mode) {
            case FRAGMENT_DISPLAY_HOME:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                break;
            case FRAGMENT_DISPLAY_NEWS:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newsFragment).commit();
                break;
            case FRAGMENT_DISPLAY_CONTACT:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, contactFragment).commit();
                break;
            case FRAGMENT_DISPLAY_EMERGENCY:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, emergencyFragment).commit();
                break;
            case FRAGMENT_DISPLAY_PROFILE:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                break;
            case FRAGMENT_DISPLAY_EVENT:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, eventFragment).commit();
                break;
        }
    }

    // call from fragment only
    public void onFragmentAttached(int number) {
        String mTitle = "";
        toolbarTitle.setVisibility(number == FRAGMENT_DISPLAY_NEWS ? View.GONE : View.VISIBLE);
        layoutNewsEvent.setVisibility(number == FRAGMENT_DISPLAY_NEWS ? View.VISIBLE : View.GONE);
        switch (number) {
            case FRAGMENT_DISPLAY_HOME:
                mTitle = "Home";
                break;
            case FRAGMENT_DISPLAY_EMERGENCY:
                mTitle = "Emergency";
                break;
            case FRAGMENT_DISPLAY_CONTACT:
                mTitle = "Contact";
                break;
            case FRAGMENT_DISPLAY_NEWS:

                break;
            case FRAGMENT_DISPLAY_PROFILE:
                mTitle = "Profile";
                break;
        }
        toolbarTitle.setText(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onImageClick() {
//        replaceFragment(FRAGMENT_DISPLAY_PROFILE);
        startActivity(new Intent(this, MyProfileActivity.class));
    }

    @Override
    public void onProgressComplete() {
        layoutProgress.setVisibility(View.GONE);
    }

    @Override
    public void onProgressStart() {
        layoutProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLeft:
                if (!newsFragment.isVisible()) {
                    replaceFragment(FRAGMENT_DISPLAY_NEWS);
                }

                btnLeft.setTextColor(ContextCompat.getColor(this, R.color.colorTextPrimary));
                btnLeft.setBackground(ContextCompat.getDrawable(this, R.drawable.button_corner_left));
                btnRight.setTextColor(ContextCompat.getColor(this, R.color.colorTextSecondary));
                btnRight.setBackground(ContextCompat.getDrawable(this, R.drawable.button_corner_right_white));
                break;
            case R.id.btnRight:
                if (!eventFragment.isVisible()) {
                    replaceFragment(FRAGMENT_DISPLAY_EVENT);
                }

                btnLeft.setTextColor(ContextCompat.getColor(this, R.color.colorTextSecondary));
                btnLeft.setBackground(ContextCompat.getDrawable(this, R.drawable.button_corner_left_white));
                btnRight.setTextColor(ContextCompat.getColor(this, R.color.colorTextPrimary));
                btnRight.setBackground(ContextCompat.getDrawable(this, R.drawable.button_corner_right));
                break;
        }
    }
}
