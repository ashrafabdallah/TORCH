package com.example.torch.activity.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.torch.R;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.Data;
import com.example.torch.model.updateprofile.ModelUpdateProfile;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileDetailesActivity extends AppCompatActivity {
    private EditText editName, editEmail, editPhone, editPassword;
    private Button btnMakeUpdate, btnshowFiled;
    private ImageView imageCamera;
    private CircleImageView profile_image;
    private Data data;
    private MultipartBody.Part profile2;
    private Bitmap bitmap;
    private String jwt;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detailes);
        Toolbar toolbar = findViewById(R.id.actionProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titel = findViewById(R.id.textMain);
        titel.setText(R.string.myAccount);
        SharedPreferences sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
        jwt = sharedPreferences.getString("jwt", null);
        data = (Data) getIntent().getExtras().getSerializable("allData");
        intalize();
        btnshowFiled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnshowFiled.setVisibility(View.INVISIBLE);
                btnMakeUpdate.setVisibility(View.VISIBLE);
                imageCamera.setVisibility(View.VISIBLE);
                editName.setEnabled(true);
                editEmail.setEnabled(true);
                editPhone.setEnabled(true);
                editPassword.setEnabled(true);
            }
        });
        btnMakeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnshowFiled.setVisibility(View.VISIBLE);
                btnMakeUpdate.setVisibility(View.INVISIBLE);
                imageCamera.setVisibility(View.INVISIBLE);
                editName.setEnabled(false);
                editEmail.setEnabled(false);
                editPhone.setEnabled(false);
                editPassword.setEnabled(false);
                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (Validation(name, email, phone, password)) {
                    UpdateProfile(name, email, phone, password);
                }
            }
        });
        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, 3);
            }
        });
    }

    private void intalize() {
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        btnMakeUpdate = findViewById(R.id.btnMakeUpdate);
        btnshowFiled = findViewById(R.id.btnshowFiled);
        imageCamera = findViewById(R.id.imageCamera);
        profile_image = findViewById(R.id.profile_image);
        ShowData();
    }

    private void ShowData() {
        editName.setText(data.getFullName());
        editEmail.setText(data.getEmail());
        editPhone.setText(data.getMobile());
        Picasso.get().load(data.getImage()).into(profile_image);
    }

    private boolean Validation(String name, String email, String phone, String password) {
        boolean valid = true;
        if (name.isEmpty()) {
            valid = false;
            editName.setError("please enter name.");
        } else {
            editName.setError(null);
        }
        if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            editEmail.setError("please enter email.");
        } else {
            editEmail.setError(null);
        }

        if (phone.isEmpty()) {
            valid = false;
            editPhone.setError("please enter phone.");
        } else {
            editPhone.setError(null);
        }
        if (phone.isEmpty()) {
            valid = false;
            editPassword.setError("please enter password.");
        } else {
            editPassword.setError(null);
        }
        return valid;
    }

    private void UpdateProfile(String name, String email, String phone, String password) {

        List<MultipartBody.Part> profileList = new ArrayList<>();
        profileList.add(MultipartBody.Part.createFormData("full_name", name));
        profileList.add(MultipartBody.Part.createFormData("mobile", phone));
        profileList.add(MultipartBody.Part.createFormData("email", email));
        profileList.add(MultipartBody.Part.createFormData("password", password));
        profileList.add(profile2);
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<ModelUpdateProfile> call = api.UpdateProfile(profileList, "ar", "Bearer " + jwt);
        call.enqueue(new Callback<ModelUpdateProfile>() {
            @Override
            public void onResponse(Call<ModelUpdateProfile> call, Response<ModelUpdateProfile> response) {
                if (response.isSuccessful()) {
                    ModelUpdateProfile body = response.body();
                    com.example.torch.model.updateprofile.Data dataProfile = body.getData();
                    ShowUpdate(dataProfile);
                } else {
                    try {
                        Toast.makeText(ProfileDetailesActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelUpdateProfile> call, Throwable t) {
                Toast.makeText(ProfileDetailesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void ShowUpdate(com.example.torch.model.updateprofile.Data dataProfile) {
        editName.setText(dataProfile.getFullName());
        editPhone.setText(dataProfile.getMobile());
        editEmail.setText(dataProfile.getEmail());
        Picasso.get().load(dataProfile.getImage()).into(profile_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode == 3 && resultCode == RESULT_OK && data2 != null) {
            Uri uri = data2.getData();
            File file = new File(getRealPathFromURI(ProfileDetailesActivity.this, uri));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            profile2 = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(ProfileDetailesActivity.this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            profile_image.setImageBitmap(bitmap);

        }
    }


    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }
}
