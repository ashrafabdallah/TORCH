package com.example.torch.activity.auth.Password;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.activity.auth.SenVerifyCodeActivity;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgetPassActivity extends AppCompatActivity {
    private EditText phone_filed;
    private Button btn_snd_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        intalize();
        btn_snd_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phone_filed.getText().toString().trim();
                if (phone.isEmpty()) {
                    phone_filed.setError("Please enter your phone");
                } else {
                    SendMoblieNumber(phone);
                }
            }
        });
    }

    private void SendMoblieNumber(final String phone) {
       // showdilag().show();
        final Dialog dialog = new Dialog(ForgetPassActivity.this);
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        Retrofit retrofitInstance = RetrofitClient.getRetrofitInstance();
        Api api = retrofitInstance.create(Api.class);
        Call<ResponseBody> call = api.SendMobile(phone);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              //  showdilag().dismiss();
                dialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(ForgetPassActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    MoveToSendVerifiyCodeActivity(phone);
                }else {
                    JSONObject error = null;
                    try {
                        error = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Toast.makeText(ForgetPassActivity.this, error.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ForgetPassActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void MoveToSendVerifiyCodeActivity(String phone) {
        Intent i=new Intent(ForgetPassActivity.this, SenVerifyCodeActivity.class);
        i.putExtra("type","reset");
        i.putExtra("phone_number",phone);
        startActivity(i);
        finish();

    }

    private void intalize() {
        phone_filed = findViewById(R.id.edit_code);
        btn_snd_code = findViewById(R.id.btn_snd_code);

    }

    private Dialog showdilag() {
        Dialog dialog = new Dialog(ForgetPassActivity.this);
        dialog.setContentView(R.layout.loading_bar);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }
}
