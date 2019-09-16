package com.example.torch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.torch.R;
import com.example.torch.viewPgerAdapter.viewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ProviderServicesAndProducts extends AppCompatActivity {
private int providerId;
private String providername;
private int numberofprovider;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_services_and_products);
        Intent intent = getIntent();
        providerId = intent.getExtras().getInt("providerId");
        providername=intent.getExtras().getString("fullname");
        numberofprovider=intent.getExtras().getInt("numberofProvider");
        TextView titel=findViewById(R.id.textMain);
        titel.setText(providername);
        Toolbar toolbar=findViewById(R.id.ActionBarServies);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TabLayout tabLayout=findViewById(R.id.tabLayout);
        ViewPager viewPager=findViewById(R.id.Viewpager);
        viewPager.setAdapter(new viewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }
    public int sentTofragmentProviderId()
    {
        return providerId;
    }
    public int sentTofragmentNumberofProfider()
    {
        return numberofprovider;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_product,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.searchProduct:
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
