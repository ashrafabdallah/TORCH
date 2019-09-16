package com.example.torch.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.torch.R;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.modelContactus.DataContact;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallUsActivity extends AppCompatActivity {
    private EditText editUsername, editPhone, editAddress, editMassage;
    private Button btnSend;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_us);

        Toolbar toolbar = findViewById(R.id.actionbarCallus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        TextView textTiltel = findViewById(R.id.textMain);
        textTiltel.setText(R.string.callus);
        initialize();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editUsername.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String address = editAddress.getText().toString().trim();
                String message = editMassage.getText().toString().trim();
                if (validation(name, phone, address, message)) {
                    sendData(name, phone, address, message);
                }
            }
        });

    }

    private void sendData(String name, String phone, String address, String message) {
        final Dialog dialog = new Dialog(CallUsActivity.this);
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.show();
        DataContact dataContact = new DataContact(name, address, phone, message);
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<ResponseBody> call = api.ContactUs(dataContact, "en");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String messages = jsonObject.getString("message");
                        Toast.makeText(CallUsActivity.this, messages, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Toast.makeText(CallUsActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(CallUsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initialize() {
        editUsername = findViewById(R.id.editUsername);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);
        editMassage = findViewById(R.id.editMassage);
        btnSend = findViewById(R.id.btnSend);
    }

    public boolean validation(String name, String phone, String address, String massage) {
        boolean valied = true;
        if (name.isEmpty()) {
            valied = false;
            editUsername.setError("Please enter your name");
            editUsername.requestFocus();
        } else {
            editUsername.setError(null);
        }
        if (address.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(address).matches()) {
            valied = false;
            editAddress.setError("Please enter your address");
            editAddress.requestFocus();
        } else {
            editAddress.setError(null);
        }
        if (phone.isEmpty()) {
            valied = false;
            editPhone.setError("Please enter your phone");
            editPhone.requestFocus();
        } else {
            editPhone.setError(null);
        }
        if (massage.isEmpty()) {
            valied = false;
            editMassage.setError("Please enter your massage");
            editMassage.requestFocus();
        } else {
            editMassage.setError(null);
        }
        return valied;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
