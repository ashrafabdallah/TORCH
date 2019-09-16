package com.example.torch.activity.auth.Password;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.activity.HomeActvity;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SetNewPasswordActivity extends AppCompatActivity {
    private EditText edit_pass1, edit_pass2;
    private Button btn_send;
private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        sharedPreferences=getSharedPreferences("userLogin",MODE_PRIVATE);
        intalize();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = edit_pass1.getText().toString().trim();
                String pass2 = edit_pass2.getText().toString().trim();
                if (pass1.isEmpty() && pass1.equals(pass2) && pass2.isEmpty()) {
                    edit_pass1.setError("enter password");
                    edit_pass2.setError("enter password");
                } else {
                    RestNewPassword(pass1);
                }
            }
        });
    }

    private void RestNewPassword(String pass1) {
        String jwt = sharedPreferences.getString("jwt", null);

      //  showdilag().show();
        final Dialog dialog = new Dialog(SetNewPasswordActivity.this);
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<ResponseBody> call = api.restPassword(pass1,"Bearer "+jwt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //showdilag().dismiss();
                dialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(SetNewPasswordActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SetNewPasswordActivity.this, HomeActvity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(SetNewPasswordActivity.this, "error password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
             //   showdilag().dismiss();
                dialog.dismiss();
                Toast.makeText(SetNewPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void intalize() {
        edit_pass1 = findViewById(R.id.edit_pass1);
        edit_pass2 = findViewById(R.id.editpass2);
        btn_send = findViewById(R.id.btn_snd_code);
    }

    private Dialog showdilag() {
        Dialog dialog = new Dialog(SetNewPasswordActivity.this);
        dialog.setContentView(R.layout.loading_bar);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }
}
