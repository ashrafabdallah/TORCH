package com.example.torch.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.torch.R;
import com.example.torch.fragment.BaseFavoriteFragment;
import com.example.torch.fragment.ConternairProvidersFragment;
import com.example.torch.fragment.HomeFragment;
import com.example.torch.fragment.MYBageFragment;
import com.example.torch.fragment.MoreMainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActvity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView textTitel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_actvity);
        final Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        textTitel = findViewById(R.id.textMain);
        toolbar.inflateMenu(R.menu.main_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.conternaer, new HomeFragment()).commit();
        toolbar.setVisibility(View.GONE);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:

                        getSupportFragmentManager().beginTransaction().replace(R.id.conternaer, new HomeFragment()).commit();
                        break;
                    case R.id.groups:
                        toolbar.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.conternaer, new ConternairProvidersFragment()).commit();

                        break;
                    case R.id.things:
                        toolbar.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.conternaer, new MYBageFragment()).commit();
                        break;
                    case R.id.favo:
                        toolbar.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.conternaer, new BaseFavoriteFragment()).commit();
                        break;
                    case R.id.more:
                        toolbar.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.conternaer, new MoreMainFragment()).commit();

                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }
}
