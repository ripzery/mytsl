package com.socket9.tsl;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.Models.Photo;
import com.socket9.tsl.Models.Profile;
import com.socket9.tsl.Utils.BusProvider;
import com.socket9.tsl.Utils.DialogHelper;
import com.socket9.tsl.Utils.PhotoHelper;
import com.socket9.tsl.Utils.PickImageChooser;
import com.socket9.tsl.Utils.Singleton;
import com.soundcloud.android.crop.Crop;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by visit on 10/8/15 AD.
 */
public class MyProfileActivity extends BaseActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
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
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvPhone)
    TextView tvPhone;
    @Bind(R.id.tvEmail)
    TextView tvEmail;
    @Bind(R.id.tvPassword)
    TextView tvPassword;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    private Photo photo;
    private Profile profile;
    private ArrayList<EditText> editTextArrayList = new ArrayList<>();
    private ArrayList<TextView> textViewArrayList = new ArrayList<>();
    private TextWatcher textWatcher;
    private Menu profileMenu;
    private MenuItem save;
    private MyCallback<Photo> uploadCallback;
    private boolean isAlreadyGetProfile = false;
    private File cacheCropImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        setListener();
        initToolbar(myToolbar, "My Profile", true);

        editTextArrayList.add(etName);
        editTextArrayList.add(etAddress);
        editTextArrayList.add(etEmail);
        editTextArrayList.add(etPhone);
        editTextArrayList.add(etPassword);

        textViewArrayList.add(tvName);
        textViewArrayList.add(tvAddress);
        textViewArrayList.add(tvEmail);
        textViewArrayList.add(tvPhone);
        textViewArrayList.add(tvPassword);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAlreadyGetProfile) return;
        getProfile();
    }

    public void setListener() {
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dispatchTakePictureIntent();
                startActivityForResult(PickImageChooser.getPickImageChooserIntent(MyProfileActivity.this), 200);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        profileMenu = menu;
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
        save = menu.findItem(R.id.action_save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                updateProfile();
                return true;
            default:
                if (save.isVisible()) {
                    DialogHelper.getUpdateProfileDialog(this, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            finish();
                        }
                    }).show();
                } else
                    finish(); // finish if data isn't change
        }
        return false;
    }

    private void updateProfile() {
        String picture = "";
        if (photo != null)
            picture = photo.getData().getPathSave();

        BusProvider.post(new ApiFire.UpdateProfile(
                etName.getText().toString(),
                etName.getText().toString(),
                etPhone.getText().toString(),
                etAddress.getText().toString(),
                picture));
    }

    @Subscribe
    public void onReceiveUpdateProfile(ApiReceive.UpdateProfile updateProfile) {
        Singleton.toast(MyProfileActivity.this, updateProfile.getModel().getMessage());
        finish();
    }

    @Subscribe
    public void onReceiveProfile(ApiReceive.Profile profile) {
        try {
            isAlreadyGetProfile = true;
            tvName.setText(profile.getProfile().getData().getNameEn());
            tvAddress.setText(profile.getProfile().getData().getAddress() == null || profile.getProfile().getData().getAddress().equals("") ? "Blank" : profile.getProfile().getData().getAddress());
            tvEmail.setText(profile.getProfile().getData().getEmail() == null || profile.getProfile().getData().getEmail().equals("") ? "Blank" : profile.getProfile().getData().getEmail());
            tvPhone.setText(profile.getProfile().getData().getPhone() == null || profile.getProfile().getData().getPhone().equals("") ? "Blank" : profile.getProfile().getData().getPhone());

            etName.setText(profile.getProfile().getData().getNameEn());
            etAddress.setText(profile.getProfile().getData().getAddress() == null || profile.getProfile().getData().getAddress().equals("") ? "Blank" : profile.getProfile().getData().getAddress());
            etEmail.setText(profile.getProfile().getData().getEmail() == null || profile.getProfile().getData().getEmail().equals("") ? "Blank" : profile.getProfile().getData().getEmail());
            etPhone.setText(profile.getProfile().getData().getPhone() == null || profile.getProfile().getData().getPhone().equals("") ? "" : profile.getProfile().getData().getPhone());

            if (profile.getProfile().getData().getPic() != null)
                Glide.with(MyProfileActivity.this).load(profile.getProfile().getData().getPic()).into(ivUser);
            ivUser.setVisibility(View.VISIBLE);

            Timber.i(profile.getProfile().getMessage());
            setViewListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideEditText(EditText et) {
        for (int i = 0; i < editTextArrayList.size(); i++) {
            editTextArrayList.get(i).setVisibility(editTextArrayList.get(i).equals(et) ? View.VISIBLE : View.GONE);
            textViewArrayList.get(i).setVisibility(editTextArrayList.get(i).equals(et) ? View.GONE : View.VISIBLE);
        }
    }

    private void getProfile() {
        BusProvider.post(new ApiFire.GetProfile());
    }

    private void setViewListener() {
        tvName.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvEmail.setOnClickListener(this);
        tvPassword.setOnClickListener(this);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (save != null && !save.isVisible()) {
                    save.setVisible(true);
                }
            }
        };

        etName.addTextChangedListener(textWatcher);
        etPhone.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etAddress.addTextChangedListener(textWatcher);
        etEmail.addTextChangedListener(textWatcher);

    }

    @Override
    public void onBackPressed() {
        if (save.isVisible()) {
            DialogHelper.getUpdateProfileDialog(this, new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    MyProfileActivity.super.onBackPressed();
                }
            }).show();
        } else
            super.onBackPressed();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode != Crop.REQUEST_CROP) {
            cacheCropImg = new File(getCacheDir(), "cropped");
            Uri destination = Uri.fromFile(cacheCropImg);
            ivUser.setImageDrawable(null); // clear image before start crop activity
            int px = PhotoHelper.convertDpToPx(this, 128);
            Crop.of(PickImageChooser.getPickImageResultUri(data, this), destination).withMaxSize(px, px).asSquare().start(this);
        } else {
            handleCrop(resultCode, data);
        }
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            save.setVisible(true);
            int px = PhotoHelper.convertDpToPx(this, 128);

            Glide.with(this)
                .load(Crop.getOutput(result))
                    .asBitmap()
                    .signature(new MediaStoreSignature("image/jpeg", cacheCropImg.lastModified(), 0))
                    .into(new SimpleTarget<Bitmap>(px, px) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            // Do something with bitmap here.
                            uploadPhoto(PhotoHelper.compressThenEncoded(bitmap, 100));
                        }
                    });

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadPhoto(String encodedBitmap) {
        BusProvider.post(new ApiFire.UploadPhoto(encodedBitmap));
    }

    @Subscribe
    public void onReceiveUploadPhoto(ApiReceive.UploadPhoto uploadPhoto) {
        photo = uploadPhoto.getPhoto();
        Glide.with(MyProfileActivity.this).load(uploadPhoto.getPhoto().getData().getPathUse()).into(ivUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvName:
                tvName.setVisibility(View.GONE);
                hideEditText(etName);
                break;
            case R.id.tvPhone:
                tvPhone.setVisibility(View.GONE);
                hideEditText(etPhone);
                break;
            case R.id.tvAddress:
                tvAddress.setVisibility(View.GONE);
                hideEditText(etAddress);
                break;
            case R.id.tvEmail:
//                tvEmail.setVisibility(View.GONE);
//                hideEditText(etEmail);
                break;
            case R.id.tvPassword:
                tvPassword.setVisibility(View.GONE);
                hideEditText(etPassword);
                break;
        }
    }

}
