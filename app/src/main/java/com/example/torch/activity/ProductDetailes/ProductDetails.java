package com.example.torch.activity.ProductDetailes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.RecyclerViews.AdapterProductDetails.AdaterprodutDetail;

import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.ProductDetailes.Data;
import com.example.torch.model.ProductDetailes.Product;
import com.example.torch.model.ProductDetailes.ProductDetailsModel;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductDetails extends AppCompatActivity {
    private TextView textName, textPrice, textDescount, textDescription;
    private CardView btnAddToBag;
    private ImageView imageFavo;
    private Slider slider;
    private RatingBar ratingBarProduct;
    private RecyclerView recyclerViewProduct;
    private Intent intent;
    private int ProductId;
    private SharedPreferences sharedPreferences;
    private AdaterprodutDetail adapter;
    private String jwt;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        intent = getIntent();
        sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
        jwt =sharedPreferences.getString("jwt", null);

        ProductId = intent.getExtras().getInt("id");
        TextView text_title = findViewById(R.id.textMain);
        text_title.setText(intent.getExtras().getString("name"));
        Toolbar toolbar = findViewById(R.id.actionbarProductdetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        init();
        imageFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ProductDetails.this);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.getFavoAndRating("Bearer " + jwt, "ar", ProductId);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String message = "";
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            // ResponseBody body = response.body();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                message = jsonObject.getString("message");
                                JSONObject data = jsonObject.getJSONObject("data");
                                int is_Fafo = data.getInt("is_fav");
                                Toast.makeText(ProductDetails.this, message, Toast.LENGTH_SHORT).show();
                                if (is_Fafo == 1) {
                                    imageFavo.setImageResource(R.drawable.favo);
                                } else {
                                    imageFavo.setImageResource(R.drawable.fav2);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().toString());
                                message = jsonObject.getString("message");
                                Toast.makeText(ProductDetails.this, message, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(ProductDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        btnAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ProductDetails.this);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.AddProductToBag("Bearer " + jwt, "ar", ProductId);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (response.isSuccessful()) {
                            try {
                                String message = jsonObject.getString("message");
                                Toast.makeText(ProductDetails.this, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                            Toast.makeText(ProductDetails.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        dialog.dismiss();

                    }
                });
            }
        });

    }

    private void init() {
        textName = findViewById(R.id.textProductName);
        textPrice = findViewById(R.id.textpRICE);
        textDescount = findViewById(R.id.text);
        textDescription = findViewById(R.id.textDescr);
        btnAddToBag = findViewById(R.id.btnAddBagMarket);
        imageFavo = findViewById(R.id.imageFavoProduct);
        slider = findViewById(R.id.slideShowProduct);
        ratingBarProduct = findViewById(R.id.ratingBarproduct);
        recyclerViewProduct = findViewById(R.id.recyclerProductsById);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        adapter = new AdaterprodutDetail(ProductDetails.this);
        recyclerViewProduct.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.searchProduct:

                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Dialog dialog = new Dialog(ProductDetails.this);
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        String jwt = sharedPreferences.getString("jwt", null);
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<ProductDetailsModel> call = api.getAllProductDetails("Bearer " + jwt, "en", ProductId);
        call.enqueue(new Callback<ProductDetailsModel>() {
            @Override
            public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    JsonParsing(response.body());
                } else {
                    Toast.makeText(ProductDetails.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsModel> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void JsonParsing(ProductDetailsModel body) {
        Data data = body.getData();
        String description = data.getDescription();
        Integer finalPrice = data.getFinalPrice();
        Integer originalPrice = data.getOriginalPrice();
        List<String> images = data.getImages();
        Integer rates = data.getRates();
        List<Product> products = data.getProducts();
        SetImagesOnSlider(images);
        Spannable spannable = new SpannableString(String.valueOf(originalPrice));
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannable.setSpan(strikethroughSpan, 0, String.valueOf(originalPrice).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textDescription.setText(description);
        textPrice.setText(String.valueOf(finalPrice));
        textDescount.setText(spannable);
        textName.setText(data.getName());
        adapter.SetData(products);
        adapter.notifyDataSetChanged();
        ratingBarProduct.setRating(rates.floatValue());

        if (data.getIsFav() == 1) {
            imageFavo.setImageResource(R.drawable.favo);
        } else {
            imageFavo.setImageResource(R.drawable.fav2);
        }


    }

    private void SetImagesOnSlider(List<String> images) {
        List<Slide> slideList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            slideList.add(new Slide(i, images.get(i), getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        }
        slider.addSlides(slideList);
    }
}
