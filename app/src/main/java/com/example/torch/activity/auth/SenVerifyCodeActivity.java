package com.example.torch.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.activity.HomeActvity;
import com.example.torch.activity.auth.Password.SetNewPasswordActivity;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.Ask_Reset;
import com.example.torch.model.LoginModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SenVerifyCodeActivity extends AppCompatActivity {
    private TextView Text_count, Text_resend;
    private Button btn_sendcode;
    private EditText code_filed;
    private Intent intent;
    private CountDownTimer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sen_verify_code);
        intent = getIntent();
        final String type = intent.getExtras().getString("type");
        final String phone_number = intent.getExtras().getString("phone_number");


        inatlize();
        btn_sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = code_filed.getText().toString().trim();
                if (code.isEmpty()) {
                    code_filed.setError("Please enter your code");
                } else {
                    SendValidation(code, phone_number, type);
                }
            }
        });
        count = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                Text_count.setText(String.valueOf(millisUntilFinished / 1000));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                Text_count.setText("00");
                Text_resend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RsendCode(phone_number, type);
                    }
                });
            }

        }.start();
    }

    private void RsendCode(String phone_number, String type) {
        // showdilag().show();
        final Dialog dialog = new Dialog(SenVerifyCodeActivity.this);
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<ResponseBody> call = api.resendCode(phone_number, type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //  showdilag().dismiss();
                dialog.dismiss();
                if (response.isSuccessful()) {
                    count.start();
                } else {
                    Toast.makeText(SenVerifyCodeActivity.this, "error to resend code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //  showdilag().dismiss();
                dialog.dismiss();
                Toast.makeText(SenVerifyCodeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SendValidation(String code, String phone_number, String type) {
        showdilag().show();
        Ask_Reset ask_reset = new Ask_Reset(phone_number, code, type);
        Retrofit retrofitInstance = RetrofitClient.getRetrofitInstance();
        Api api = retrofitInstance.create(Api.class);
        Call<LoginModel> call = api.auth_activate(ask_reset);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                showdilag().dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(SenVerifyCodeActivity.this, "Success..", Toast.LENGTH_SHORT).show();
                    CheckType(response.body());
                } else {
                    Toast.makeText(SenVerifyCodeActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                showdilag().dismiss();
            }
        });
    }

    private void CheckType(LoginModel body) {
        String jwt = body.getData().getJwt();
        SharedPreferences preferences = getSharedPreferences("userLogin", MODE_PRIVATE);
        preferences.edit().putString("jwt", jwt).commit();
        String type = body.getType();

        if (!type.equals("active")) {
            startActivity(new Intent(SenVerifyCodeActivity.this, SetNewPasswordActivity.class));

            finish();
        } else {
            startActivity(new Intent(SenVerifyCodeActivity.this, HomeActvity.class));

            finish();
        }

    }

    private void inatlize() {
        Text_count = findViewById(R.id.text_count_down2);
        Text_resend = findViewById(R.id.text_resend);
        btn_sendcode = findViewById(R.id.btn_snd_code);
        code_filed = findViewById(R.id.edit_code);
    }

    private Dialog showdilag() {
        Dialog dialog = new Dialog(SenVerifyCodeActivity.this);
        dialog.setContentView(R.layout.loading_bar);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }
}
