package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.socket9.tsl.Fragments.ContactFragment;
import com.socket9.tsl.Fragments.EmergencyFragment;
import com.socket9.tsl.Fragments.HomeFragment;
import com.socket9.tsl.Fragments.MyProfileFragment;
import com.socket9.tsl.Fragments.NewsEventFragment;
import com.socket9.tsl.Utils.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements HomeFragment.OnHomeListener{

    private static final int FRAGMENT_DISPLAY_HOME = 1;
    private static final int FRAGMENT_DISPLAY_NEWS = 2;
    private static final int FRAGMENT_DISPLAY_CONTACT = 3;
    private static final int FRAGMENT_DISPLAY_EMERGENCY = 4;
    private static final int FRAGMENT_DISPLAY_PROFILE = 5;
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
    private Fragment homeFragment;
    private Fragment newsFragment;
    private Fragment contactFragment;
    private Fragment emergencyFragment;
    private Fragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar(myToolbar, "Main", false);
        initFragment();
        setupDrawerContent();
        replaceFragment(FRAGMENT_DISPLAY_HOME);
        navView.setCheckedItem(R.id.nav_home);
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        newsFragment = new NewsEventFragment();
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
                        Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
                        startActivity(new Intent(MainActivity.this, SignInActivity.class));
                        finish();
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
        }
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
}
