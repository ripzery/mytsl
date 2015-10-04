package com.socket9.tsl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.BaseModel;
import com.socket9.tsl.Models.Photo;
import com.socket9.tsl.Models.Profile;
import com.socket9.tsl.Utils.Singleton;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import timber.log.Timber;

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
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Bind(R.id.progress)
    ProgressBar progress;
    private Photo photo;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        setListener();
        getProfile();
        initToolbar(myToolbar, "My Profile", true);
    }

    public void setListener() {
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                updateProfile();
                return true;
            default:
                finish();
        }
        return false;
    }

    private void updateProfile() {
        String picture = "";
        if (photo != null)
            picture = photo.getData().getPathSave();

        ApiService.getTSLApi().updateProfile(Singleton.getInstance().getToken(),
                etName.getText().toString(),
                etName.getText().toString(),
                etPhone.getText().toString(),
                etAddress.getText().toString(),
                picture, new MyCallback<BaseModel>() {
                    @Override
                    public void good(BaseModel m, Response response) {
                        Timber.i(m.getMessage());
//                        Toast.makeText(MyProfileActivity.this, m.getMessage(), Toast.LENGTH_SHORT).show();
                        Singleton.toast(MyProfileActivity.this, m.getMessage(), Toast.LENGTH_LONG);
                    }

                    @Override
                    public void bad(String error) {
                        Timber.d(error);
                    }
                });
    }

    private void getProfile() {
        ApiService.getTSLApi().getProfile(Singleton.getInstance().getToken(), new MyCallback<Profile>() {
            @Override
            public void good(Profile m, Response response) {
                etName.setText(m.getData().getNameEn());
                etAddress.setText(m.getData().getAddress());
                etEmail.setText(m.getData().getEmail());
                etPhone.setText(m.getData().getPhone());
                Glide.with(MyProfileActivity.this).load(m.getData().getPic()).into(ivUser);
                etAddress.setText(m.getData().getAddress());
                Timber.i(m.getMessage());
                progress.setVisibility(View.GONE);
            }

            @Override
            public void bad(String error) {
                Timber.d(error);
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            ivUser.setImageBitmap(imageBitmap);
            String encodedBitmap = "data:image/png;base64," + encode(compress(imageBitmap, 70));
            uploadPhoto(encodedBitmap);
        }
    }

    public void uploadPhoto(String encodedBitmap) {
        progress.setVisibility(View.VISIBLE);

        ApiService.getTSLApi().uploadPhoto(Singleton.getInstance().getToken(), encodedBitmap, new MyCallback<Photo>() {
            @Override
            public void good(Photo m, Response response) {
                Timber.i(m.getData().getPathUse());
                photo = m;
                progress.setVisibility(View.GONE);
                Glide.with(MyProfileActivity.this).load(m.getData().getPathUse()).into(ivUser);
            }

            @Override
            public void bad(String error) {
                progress.setVisibility(View.GONE);
                Timber.d(error);
            }
        });
    }

    public byte[] compress(Bitmap bitmap, int percent) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, percent, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String encode(byte[] byteArray) {
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
