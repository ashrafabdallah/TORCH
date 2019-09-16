package com.example.torch.activity.Clothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.RecyclerViews.AdapterMoreOffers.AdapterMoreOffers;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.OfferModel.Datum;
import com.example.torch.model.OfferModel.OfferData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoreOffersActivity extends AppCompatActivity {
    private RecyclerView recyclerMoreOffer;
    private AdapterMoreOffers adapter;
    private SharedPreferences sharedPreferences;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_offers);
        sharedPreferences=getSharedPreferences("userLogin",MODE_PRIVATE);
        Toolbar toolbar=findViewById(R.id.actionbar_clothes);
        setSupportActionBar(toolbar);
        TextView textTitle=findViewById(R.id.textMain);
        textTitle.setText(R.string.clothes);
        recyclerMoreOffer = findViewById(R.id.recyclerOffers);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerMoreOffer.setLayoutManager(new LinearLayoutManager(this));
        adapter=new AdapterMoreOffers(this);
        recyclerMoreOffer.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FeatchAllOffers();
    }

    private void FeatchAllOffers() {
        String jwt = sharedPreferences.getString("jwt", null);
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<OfferData> call = api.getAllOffers("en","Bearer "+jwt);
        call.enqueue(new Callback<OfferData>() {
            @Override
            public void onResponse(Call<OfferData> call, Response<OfferData> response) {
                if(response.isSuccessful())
                {
                    JsonParsing(response.body());
                }else {
                    Toast.makeText(MoreOffersActivity.this, "error of All Offers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OfferData> call, Throwable t) {
                Toast.makeText(MoreOffersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void JsonParsing(OfferData body) {
        List<Datum> data = body.getData();

        adapter.SetData(data);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
//                onBackPressed();
                finish();
                break;
        }
        return true;
    }
}
