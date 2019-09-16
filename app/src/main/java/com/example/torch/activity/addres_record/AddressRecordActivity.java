package com.example.torch.activity.addres_record;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.activity.addres_record.addAddress.AddAddressActivity;

public class AddressRecordActivity extends AppCompatActivity {
private Button btnaddAddress;
private RecyclerView recyclerViewOffers;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_record);
        TextView text_title = findViewById(R.id.textMain);
        text_title.setText(R.string.recodAddress);
        Toolbar toolbar = findViewById(R.id.actionbarOffers);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        btnaddAddress=findViewById(R.id.btnaddAddress);
        btnaddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressRecordActivity.this, AddAddressActivity.class));
            }
        });
        RecyclerView recyclerViewaddress=findViewById(R.id.recyclerViewaddress);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
