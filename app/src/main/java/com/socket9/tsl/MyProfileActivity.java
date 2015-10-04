package com.socket9.tsl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Models.Photo;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        setListener();
        initToolbar(myToolbar, "My Profile", true);
    }

    public void setListener(){
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

    public void uploadPhoto(String encodedBitmap){
        ApiService.getTSLApi().uploadPhoto(Singleton.getInstance().getToken(), encodedBitmap, new MyCallback<Photo>() {
            @Override
            public void good(Photo m, Response response) {
                Timber.i(m.getData().getPathUse());
                Glide.with(MyProfileActivity.this).load(m.getData().getPathUse()).into(ivUser);
            }

            @Override
            public void bad(String error) {
                Timber.d(error);
            }
        });
    }

    public byte[] compress(Bitmap bitmap, int percent){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, percent, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String encode(byte[] byteArray){
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
