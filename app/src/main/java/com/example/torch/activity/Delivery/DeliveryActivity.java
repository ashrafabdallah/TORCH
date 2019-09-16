package com.example.torch.activity.Delivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.torch.R;
import com.example.torch.activity.addres_record.AddressRecordActivity;

public class DeliveryActivity extends AppCompatActivity {
    private TextView btnChange, textName, textStreet, textCountry, textDelivery, textPrice, textTotal1, textTotal2, textTotal3;
    private Button btnContinueShopping;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        TextView text_title = findViewById(R.id.textMain);
        text_title.setText(R.string.delivery);
        Toolbar toolbar = findViewById(R.id.actionbarDeliveryActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        initalize();
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeliveryActivity.this, AddressRecordActivity.class);
                startActivity(i);
            }
        });
    }

    private void initalize() {
        btnChange = findViewById(R.id.btnChange);
        textName = findViewById(R.id.textName);
        textStreet = findViewById(R.id.textStreet);
        textCountry = findViewById(R.id.textCountry);
        textDelivery = findViewById(R.id.textDelivery);
        textPrice = findViewById(R.id.textPrice);
        textTotal1 = findViewById(R.id.textTotal1);
        textTotal2 = findViewById(R.id.textTotal2);
        textTotal3 = findViewById(R.id.text);
        btnContinueShopping = findViewById(R.id.btnContinueShopping);

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
