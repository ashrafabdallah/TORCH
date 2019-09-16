package com.example.torch.activity.addres_record.addAddress;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.torch.R;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.modeladdAddress.ModelAddAddress;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddAddressActivity extends AppCompatActivity {
    private EditText editFristname, editLastName, editFlat, editcity, editregion, editphone;
    private CheckBox checkBoxAddress;
    private Button btnAddress;
    private int is_default;
    private String jwt;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        SharedPreferences sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
        jwt =sharedPreferences.getString("jwt", null);
        TextView text_title = findViewById(R.id.textMain);
        text_title.setText(R.string.addAddress);
        Toolbar toolbar = findViewById(R.id.actionbarAddaddress);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        Initialize();
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String frName = editFristname.getText().toString().trim();
                String lsName = editLastName.getText().toString().trim();
                String flat = editFlat.getText().toString().trim();
                String city = editcity.getText().toString().trim();
                String region = editregion.getText().toString().trim();
                String phone = editphone.getText().toString().trim();
                if (validation(frName, lsName, flat, city, region, phone)) {
                    addAddress(frName, lsName, city, flat, region, phone, is_default);
                }
            }
        });

    }

    private void addAddress(String frName, String lsName, String city, String flat, String region, String phone, int is_default) {
        ModelAddAddress modelAddAddress=new ModelAddAddress(frName,lsName,city,
                flat,region,phone,is_default);
        final Dialog dialog = new Dialog(AddAddressActivity.this);
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<ResponseBody> call = api.setAddress(modelAddAddress, "Bearer " + jwt, "ar");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                if(response.isSuccessful())
                {
                    try {
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        String message = jsonObject.getString("message");
                        Toast.makeText(AddAddressActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(AddAddressActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void Initialize() {
        editFristname = findViewById(R.id.editFristname);
        editLastName = findViewById(R.id.editLastName);
        editFlat = findViewById(R.id.editFlat);
        editcity = findViewById(R.id.editcity);
        editregion = findViewById(R.id.editregion);
        editphone = findViewById(R.id.editphone);
        checkBoxAddress = findViewById(R.id.checkBoxAddress);
        btnAddress = findViewById(R.id.btnAddress);
    }

    public void select(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            is_default = 1;
        } else {
            is_default = 0;
        }

    }

    public boolean validation(String frName, String lsName, String flat, String city, String region, String phone) {
        boolean is_valied = true;
        if (frName.isEmpty()) {
            is_valied = false;
            editFristname.setError("please enter first name ");
            editFristname.requestFocus();
        } else {
            editFristname.setError(null);
        }
        if (lsName.isEmpty()) {
            is_valied = false;
            editLastName.setError("please enter last name ");
            editLastName.requestFocus();
        } else {
            editLastName.setError(null);
        }

        if (flat.isEmpty()) {
            is_valied = false;
            editFlat.setError("please enter flat ");
            editFlat.requestFocus();
        } else {
            editFlat.setError(null);
        }
        if (city.isEmpty()) {
            is_valied = false;
            editcity.setError("please enter city ");
            editcity.requestFocus();
        } else {
            editcity.setError(null);
        }
        if (region.isEmpty()) {
            is_valied = false;
            editregion.setError("please enter region ");
            editregion.requestFocus();
        } else {
            editregion.setError(null);
        }
        if (phone.isEmpty()) {
            is_valied = false;
            editphone.setError("please enter phone ");
            editphone.requestFocus();
        } else {
            editphone.setError(null);
        }
        return is_valied;
    }
}
