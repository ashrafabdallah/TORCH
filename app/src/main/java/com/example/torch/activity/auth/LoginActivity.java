package com.example.torch.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.activity.auth.Password.ForgetPassActivity;
import com.example.torch.activity.HomeActvity;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.Data;
import com.example.torch.model.LoginModel;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private EditText edit_phone, edit_pass;
    private Button btnLogin;
    private TextView text_Register, text_forgetpass;
    private SharedPreferences sharedPreferences;
    private ImageView TogglePass;

private FirebaseAnalytics firebaseAnalytics;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
        intalize();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edit_phone.getText().toString().trim();
                String pass = edit_pass.getText().toString().trim();

                if (ValidateEmail(email, pass)) {
                    LoginUser(email, pass);
                }
            }
        });
        text_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        text_forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
                finish();
            }
        });
        TogglePass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        edit_pass.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        edit_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

        text_forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(i);
            }
        });
    }


    private void intalize() {
        edit_phone = findViewById(R.id.editphone);
        edit_pass = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btn_logn);
        text_Register = findViewById(R.id.text_register);
        text_forgetpass = findViewById(R.id.text_fprgrt_pass);
        TogglePass = findViewById(R.id.toogle1);

    }

    private boolean ValidateEmail(String email, String pass) {
        boolean valid = true;
        if (email.isEmpty() && !Patterns.PHONE.matcher(email).matches()) {
            valid = false;
            edit_phone.setError("Please enter your phone");

        } else {

            edit_phone.setError(null);

        }
        if (pass.isEmpty() && pass.length() < 6) {
            valid = false;
            edit_pass.setError("Please enter your password");

        } else {

            edit_pass.setError(null);

        }
        return valid;
    }

    private void LoginUser(String email, String pass) {
        // showdilag().show();
        String token = FirebaseInstanceId.getInstance().getToken();
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        Retrofit retrofitInstance = RetrofitClient.getRetrofitInstance();
        Api api = retrofitInstance.create(Api.class);
        Call<LoginModel> call = api.getLogin(email, pass, token);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                //showdilag().dismiss();
                dialog.dismiss();
                if (response.isSuccessful()) {
                    Toasty.success(LoginActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
                    parsingJson(response.body());
                    Intent i = new Intent(LoginActivity.this, HomeActvity.class);
                    //  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "error user login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                //  showdilag().dismiss();
                dialog.dismiss();
                Toasty.error(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT, true).show();

            }
        });


    }

    private void parsingJson(LoginModel body) {

        Data data = body.getData();
        String jwt = data.getJwt();
        sharedPreferences.edit().putString("jwt", jwt).commit();

    }

    private Dialog showdilag() {
        Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.loading_bar);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }
}
