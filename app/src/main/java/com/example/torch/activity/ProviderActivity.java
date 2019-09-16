package com.example.torch.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.torch.R;
import com.example.torch.fragment.ConternairProvidersFragment;

public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameProviderActivity,new ConternairProvidersFragment()).commit();
    }
}
