// package com.socket9.tsl;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.v7.widget.Toolbar;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.afollestad.materialdialogs.DialogAction;
//import com.afollestad.materialdialogs.MaterialDialog;
//import com.bumptech.glide.Glide;
//import com.socket9.tsl.API.ApiService;
//import com.socket9.tsl.API.MyCallback;
//import com.socket9.tsl.Models.BaseModel;
//import com.socket9.tsl.Models.Photo;
//import com.socket9.tsl.Models.Profile;
//import com.socket9.tsl.Utils.DialogHelper;
//import com.socket9.tsl.Utils.PhotoHelper;
//import com.socket9.tsl.Utils.PickImageChooser;
//import com.socket9.tsl.Utils.Singleton;
//import com.soundcloud.android.crop.Crop;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import retrofit.client.Response;
//import timber.log.Timber;
//
///**
// * Created by visit on 10/8/15 AD.
// */
//public class BackupProfile extends BaseActivity implements View.OnClickListener{
//    @Bind(R.id.toolbarTitle)
//    TextView toolbarTitle;
//    @Bind(R.id.my_toolbar)
//    Toolbar myToolbar;
//    @Bind(R.id.ivUser)
//    ImageView ivUser;
//    @Bind(R.id.etName)
//    EditText etName;
//    @Bind(R.id.etPhone)
//    EditText etPhone;
//    @Bind(R.id.etEmail)
//    EditText etEmail;
//    @Bind(R.id.etPassword)
//    EditText etPassword;
//    @Bind(R.id.etAddress)
//    EditText etAddress;
//    static final int REQUEST_IMAGE_CAPTURE = 1;
//    @Bind(R.id.tvName)
//    TextView tvName;
//    @Bind(R.id.tvPhone)
//    TextView tvPhone;
//    @Bind(R.id.tvEmail)
//    TextView tvEmail;
//    @Bind(R.id.tvPassword)
//    TextView tvPassword;
//    @Bind(R.id.tvAddress)
//    TextView tvAddress;
//    @Bind(R.id.layoutProgress)
//    LinearLayout layoutProgress;
//    private Photo photo;
//    private Profile profile;
//    private ArrayList<EditText> editTextArrayList = new ArrayList<>();
//    private ArrayList<TextView> textViewArrayList = new ArrayList<>();
//    private TextWatcher textWatcher;
//    private Menu profileMenu;
//    private MenuItem save;
//    private MyCallback<Photo> uploadCallback;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_profile);
//        ButterKnife.bind(this);
//        setListener();
//        getProfile();
//        initToolbar(myToolbar, "My Profile", true);
//
//        editTextArrayList.add(etName);
//        editTextArrayList.add(etAddress);
//        editTextArrayList.add(etEmail);
//        editTextArrayList.add(etPhone);
//        editTextArrayList.add(etPassword);
//
//        textViewArrayList.add(tvName);
//        textViewArrayList.add(tvAddress);
//        textViewArrayList.add(tvEmail);
//        textViewArrayList.add(tvPhone);
//        textViewArrayList.add(tvPassword);
//    }
//
//    public void setListener() {
//        ivUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                dispatchTakePictureIntent();
//                startActivityForResult(PickImageChooser.getPickImageChooserIntent(MyProfileActivity.this), 200);
//            }
//        });
//
//        uploadCallback = new MyCallback<Photo>() {
//            @Override
//            public void good(Photo m, Response response) {
//                photo = m;
//                layoutProgress.setVisibility(View.GONE);
//                Glide.with(MyProfileActivity.this).load(m.getData().getPathUse()).into(ivUser);
//            }
//
//            @Override
//            public void bad(String error, boolean isTokenExpired) {
//                layoutProgress.setVisibility(View.GONE);
//                if (isTokenExpired) {
//                    Singleton.toast(MyProfileActivity.this, getString(R.string.toast_token_invalid), Toast.LENGTH_LONG);
//                    Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
//                    startActivity(new Intent(MyProfileActivity.this, SignInActivity.class));
//                    finish();
//                }
//            }
//        };
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        profileMenu = menu;
//        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
//        save = menu.findItem(R.id.action_save);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_save:
//                updateProfile();
//                return true;
//            default:
//                if (save.isVisible()) {
//                    DialogHelper.getUpdateProfileDialog(this, new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
//                            finish();
//                        }
//                    }).show();
//                } else
//                    finish(); // finish if data isn't change
//        }
//        return false;
//    }
//
//    private void updateProfile() {
//        String picture = "";
//        if (photo != null)
//            picture = photo.getData().getPathSave();
//
//        ApiService.getTSLApi().updateProfile(Singleton.getInstance().getToken(),
//                etName.getText().toString(),
//                etName.getText().toString(),
//                etPhone.getText().toString(),
//                etAddress.getText().toString(),
//                picture, new MyCallback<BaseModel>() {
//                    @Override
//                    public void good(BaseModel m, Response response) {
//                        Timber.i(m.getMessage());
////                        Toast.makeText(MyProfileActivity.this, m.getMessage(), Toast.LENGTH_SHORT).show();
//                        Singleton.toast(MyProfileActivity.this, m.getMessage(), Toast.LENGTH_LONG);
//                        finish();
//                    }
//
//                    @Override
//                    public void bad(String error, boolean isTokenExpired) {
//                        Timber.d(error);
//                        if (isTokenExpired) {
//                            Singleton.toast(MyProfileActivity.this, getString(R.string.toast_token_invalid), Toast.LENGTH_LONG);
//                            Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
//                            startActivity(new Intent(MyProfileActivity.this, SignInActivity.class));
//                            finish();
//                        }
//                    }
//                });
//    }
//
//    private void hideEditText(EditText et) {
//        for (int i = 0; i < editTextArrayList.size(); i++) {
//            editTextArrayList.get(i).setVisibility(editTextArrayList.get(i).equals(et) ? View.VISIBLE : View.GONE);
//            textViewArrayList.get(i).setVisibility(editTextArrayList.get(i).equals(et) ? View.GONE : View.VISIBLE);
//        }
//    }
//
//    private void getProfile() {
//        layoutProgress.setVisibility(View.VISIBLE);
//        ApiService.getTSLApi().getProfile(Singleton.getInstance().getToken(), new MyCallback<Profile>() {
//            @Override
//            public void good(Profile m, Response response) {
//                try {
//                    tvName.setText(m.getData().getNameEn());
//                    tvAddress.setText(m.getData().getAddress() == null || m.getData().getAddress().equals("") ? "Blank" : m.getData().getAddress());
//                    tvEmail.setText(m.getData().getEmail() == null || m.getData().getEmail().equals("") ? "Blank" : m.getData().getEmail());
//                    tvPhone.setText(m.getData().getPhone() == null || m.getData().getPhone().equals("") ? "Blank" : m.getData().getPhone());
//
//                    etName.setText(m.getData().getNameEn());
//                    etAddress.setText(m.getData().getAddress() == null || m.getData().getAddress().equals("") ? "Blank" : m.getData().getAddress());
//                    etEmail.setText(m.getData().getEmail() == null || m.getData().getEmail().equals("") ? "Blank" : m.getData().getEmail());
//                    etPhone.setText(m.getData().getPhone() == null || m.getData().getPhone().equals("") ? "" : m.getData().getPhone());
//
//                    if (m.getData().getPic() != null)
//                        Glide.with(MyProfileActivity.this).load(m.getData().getPic()).into(ivUser);
//
//                    Timber.i(m.getMessage());
//                    layoutProgress.setVisibility(View.GONE);
//                    setViewListener();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void bad(String error, boolean isTokenExpired) {
//                Timber.d(error);
//                layoutProgress.setVisibility(View.GONE);
//                if (isTokenExpired) {
//                    Singleton.toast(MyProfileActivity.this, getString(R.string.toast_token_invalid), Toast.LENGTH_LONG);
//                    Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
//                    startActivity(new Intent(MyProfileActivity.this, SignInActivity.class));
//                    finish();
//                }
//            }
//        });
//    }
//
//    private void setViewListener() {
//
//        tvName.setOnClickListener(this);
//        tvPhone.setOnClickListener(this);
//        tvAddress.setOnClickListener(this);
//        tvEmail.setOnClickListener(this);
//        tvPassword.setOnClickListener(this);
//
//        textWatcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (save != null && !save.isVisible()) {
//                    save.setVisible(true);
//                }
//            }
//        };
//
//        etName.addTextChangedListener(textWatcher);
//        etPhone.addTextChangedListener(textWatcher);
//        etPassword.addTextChangedListener(textWatcher);
//        etAddress.addTextChangedListener(textWatcher);
//        etEmail.addTextChangedListener(textWatcher);
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (save.isVisible()) {
//            DialogHelper.getUpdateProfileDialog(this, new MaterialDialog.SingleButtonCallback() {
//                @Override
//                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
//                    MyProfileActivity.super.onBackPressed();
//                }
//            }).show();
//        } else
//            super.onBackPressed();
//    }
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode != Crop.REQUEST_CROP) {
////            Bundle extras = data.getExtras();
////            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
//            Crop.of(PickImageChooser.getPickImageResultUri(data, this), destination).withMaxSize(100, 100).asSquare().start(this);
////            Timber.i(imageUri.toString());
////            ivUser.setImageURI(imageUri);
//            try {
////                Bitmap imageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
////                String encodedBitmap = PhotoHelper.compressThenEncoded(imageBitmap, 70);
////                uploadPhoto(encodedBitmap);
//                save.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }else{
//            handleCrop(resultCode, data);
//        }
//    }
//
//    private void handleCrop(int resultCode, Intent result) {
//        if (resultCode == RESULT_OK) {
//            ivUser.setImageURI(Crop.getOutput(result));
//        } else if (resultCode == Crop.RESULT_ERROR) {
//            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void uploadPhoto(String encodedBitmap) {
//        layoutProgress.setVisibility(View.VISIBLE);
//        PhotoHelper.uploadPhoto(encodedBitmap, uploadCallback);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tvName:
//                tvName.setVisibility(View.GONE);
//                hideEditText(etName);
//                break;
//            case R.id.tvPhone:
//                tvPhone.setVisibility(View.GONE);
//                hideEditText(etPhone);
//                break;
//            case R.id.tvAddress:
//                tvAddress.setVisibility(View.GONE);
//                hideEditText(etAddress);
//                break;
//            case R.id.tvEmail:
////                tvEmail.setVisibility(View.GONE);
////                hideEditText(etEmail);
//                break;
//            case R.id.tvPassword:
//                tvPassword.setVisibility(View.GONE);
//                hideEditText(etPassword);
//                break;
//        }
//    }
////
////    @Override
////    public void onFocusChange(View view, boolean hasFocus) {
////        if(!hasFocus) {
////            switch (view.getId()) {
////                case R.id.etName:
////                    etName.setVisibility(View.GONE);
////                    tvName.setVisibility(View.VISIBLE);
////                    break;
////                case R.id.etEmail:
////                    etEmail.setVisibility(View.GONE);
////                    tvEmail.setVisibility(View.VISIBLE);
////                    break;
////                case R.id.etAddress:
////                    etAddress.setVisibility(View.GONE);
////                    tvAddress.setVisibility(View.VISIBLE);
////                    break;
////                case R.id.etPhone:
////                    etPhone.setVisibility(View.GONE);
////                    tvPhone.setVisibility(View.VISIBLE);
////                    break;
////            }
////        }
////    }
//}
