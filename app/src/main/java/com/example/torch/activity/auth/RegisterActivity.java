package com.example.torch.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.Registermodel;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    private EditText name_filed, email_filed, pass1_filed, pass2_filed, address_filed, phone_filed;
    private Spinner spinner;
    private TextView text_login;
    private ImageView Toggle1, Toggle2;
    private Button btn_register;
    private String gender_type;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intalize();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_type = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(-1);

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                String fcm_Token = "gfdsas";
                String name = name_filed.getText().toString().trim();
                String email = email_filed.getText().toString().trim();
                String phone = phone_filed.getText().toString().trim();
                String address = address_filed.getText().toString().trim();
                String pass1 = pass1_filed.getText().toString().trim();
                String pass2 = pass2_filed.getText().toString().trim();
                if (validation(name, email, pass1, pass2, address, phone)) {
                    CreateUser(name, email, phone, address, pass1, gender_type,token, "client");
                }
            }
        });
        Toggle1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pass1_filed.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    pass1_filed.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }

                return true;
            }
        });
        Toggle2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pass2_filed.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        pass2_filed.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void CreateUser(String name, String email, final String phone, String address, String pass1, String gender_type, String fcm_token, String client) {
        // showdilag().show();
        final Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        Registermodel data = new Registermodel(name, phone, email, gender_type, pass1, address, fcm_token, client);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        Api api = retrofit.create(Api.class);
        Call<ResponseBody> call = api.createuser(data,"ar","android");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                    MoveToValidatioActivity(phone);
                } else {
                    dialog.dismiss();
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        Toast.makeText(RegisterActivity.this, error.get("message").toString(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validation(String name, String email, String pass1, String pass2, String address, String phone) {
        boolean validate = true;
        if (name.isEmpty()) {
            validate = false;
            name_filed.setError("please enter your name");
            name_filed.requestFocus();


        } else {
            name_filed.setError(null);
        }
        if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validate = false;

            email_filed.setError("please enter valid email");
            email_filed.requestFocus();
        } else {

            email_filed.setError(null);
        }
        if (pass1.isEmpty() && pass1.length() < 6) {
            validate = false;

            pass1_filed.setError("please enter valid password");
            pass1_filed.requestFocus();
        } else {
            pass1_filed.setError(null);

        }
        if (pass2.isEmpty() && !pass1.equals(pass2)) {
            validate = false;

            pass2_filed.setError("please enter valid password");
            pass2_filed.requestFocus();
        } else {

            pass2_filed.setError(null);
        }
        if (phone.isEmpty()) {
            validate = false;

            phone_filed.setError("please enter phone number");
            phone_filed.requestFocus();
        } else {
            phone_filed.setError(null);

        }
        if (address.isEmpty()) {
            validate = false;

            address_filed.setError("please enter your address");
            address_filed.requestFocus();
        } else {

            address_filed.setError(null);
        }

        return validate;
    }

    private void intalize() {
        name_filed = findViewById(R.id.edit_username);
        email_filed = findViewById(R.id.edit_email);
        pass1_filed = findViewById(R.id.edit_password);
        pass2_filed = findViewById(R.id.edit_password2);
        phone_filed = findViewById(R.id.edit_code);
        address_filed = findViewById(R.id.edit_address);
        spinner = findViewById(R.id.spinner_type);
        text_login = findViewById(R.id.text_login);
        Toggle1 = findViewById(R.id.toogle1);
        Toggle2 = findViewById(R.id.toogle2);
        btn_register = findViewById(R.id.btn_register);
    }

    private Dialog showdilag() {
        Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    private void MoveToValidatioActivity(String phone) {
        Intent i = new Intent(RegisterActivity.this, SenVerifyCodeActivity.class);
        i.putExtra("type", "active");
        i.putExtra("phone_number", phone);
        startActivity(i);

    }
}
