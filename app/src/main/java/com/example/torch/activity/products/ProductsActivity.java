package com.example.torch.activity.products;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.RecyclerViews.Adapterprovideproducts.AdapterFlterProduct;
import com.example.torch.RecyclerViews.Adapterprovideproducts.providerproductsAdapterbyId;
import com.example.torch.api.Api;
import com.example.torch.api.ApiCunterierAndProviders;
import com.example.torch.api.RetrofitClient;
import com.example.torch.api.modelCatogries.Data;
import com.example.torch.model.ProviderProductsByid.Modelproviderproducts;
import com.example.torch.model.ProviderProductsByid.Product;
import com.example.torch.model.modelFilter.Datum;
import com.example.torch.model.modelFilter.ModelFilterProducts;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductsActivity extends AppCompatActivity {
    private TextView textName, textNumberProvider;
    private ImageView imgFavo, imageProfile;
    private RecyclerView recyclerProduct;
    private providerproductsAdapterbyId adapter;
    private AdapterFlterProduct adapterFlterProduct;
    private String jwt;
    private int providerId, categoryId;
    private ConstraintLayout btnSort;
    private  Dialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        inatlize();
        SharedPreferences sharedPreferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        jwt = sharedPreferences.getString("jwt", null);
        Intent intent = getIntent();
        final Data data = (Data) intent.getExtras().getSerializable("data");
        categoryId = intent.getExtras().getInt("categoryId");
        adapterFlterProduct=new AdapterFlterProduct(ProductsActivity.this);
        providerId = data.getId();
        String fullName = data.getFullName();
        String image = data.getImage();
        Integer isFav = data.getIsFav();
        textName.setText(fullName);
        Picasso.get().load(image).into(imageProfile);
        if (isFav == 0) {
            imgFavo.setImageResource(R.drawable.fav2);
        } else {
            imgFavo.setImageResource(R.drawable.favo);

        }
        imgFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ProductsActivity.this);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                ApiCunterierAndProviders api = instance.create(ApiCunterierAndProviders.class);
                Call<ResponseBody> call = api.getProviderFeavo(data.getId(), "Bearer " + jwt, "ar");
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
                                Toast.makeText(ProductsActivity.this, message, Toast.LENGTH_SHORT).show();
                                if (is_Fafo == 1) {
                                    imgFavo.setImageResource(R.drawable.favo);
                                } else {
                                    imgFavo.setImageResource(R.drawable.fav2);

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
                                Toast.makeText(ProductsActivity.this, message, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(ProductsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        FeatchProducts(providerId, categoryId);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dialog2=new Dialog(ProductsActivity.this);
               dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
               dialog2.setContentView(R.layout.custom_dialog);
               dialog2.setCancelable(false);
               dialog2.getWindow().getAttributes().gravity= Gravity.CENTER;
               dialog2.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                WindowManager.LayoutParams ip=new WindowManager.LayoutParams();
                ip.copyFrom(dialog2.getWindow().getAttributes());
                ip.width=WindowManager.LayoutParams.MATCH_PARENT;
                ip.height=WindowManager.LayoutParams.WRAP_CONTENT;
                dialog2.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);




                dialog2.show();

            }
        });
    }

    private void FeatchProducts(int providerId, int categoryId) {
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<Modelproviderproducts> call = api.getproviderproducts(categoryId, providerId, "ar", "Bearer " + jwt);
        call.enqueue(new Callback<Modelproviderproducts>() {
            @Override
            public void onResponse(Call<Modelproviderproducts> call, Response<Modelproviderproducts> response) {
                if (response.isSuccessful()) {
                    Modelproviderproducts body = response.body();
                    com.example.torch.model.ProviderProductsByid.Data data = body.getData();
                    List<Product> productList = data.getProducts();
                    adapter.setData(productList);
                    adapter.notifyDataSetChanged();
                } else {
                    try {
                        Toast.makeText(ProductsActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Modelproviderproducts> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inatlize() {
        textName = findViewById(R.id.textUserName);
        textNumberProvider = findViewById(R.id.textNumberOfprovidder);
        imageProfile = findViewById(R.id.profile_image);
        imgFavo = findViewById(R.id.imageFavoProduct);
        recyclerProduct = findViewById(R.id.recyclerProductsById);
        recyclerProduct.setLayoutManager(new GridLayoutManager(ProductsActivity.this, getNumberOfColumns()));
        adapter = new providerproductsAdapterbyId(ProductsActivity.this);
        recyclerProduct.setAdapter(adapter);
        btnSort = findViewById(R.id.btnSortBy);

    }

    public int getNumberOfColumns() {
        View view = View.inflate(this, R.layout.row_item_you_like, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = view.getMeasuredWidth();
        int count = getResources().getDisplayMetrics().widthPixels / width;
        int remaining = getResources().getDisplayMetrics().widthPixels - width * count;
        if (remaining > width - 15)
            count++;
        return count;
    }

    public void select(View view) {
      switch (view.getId()) {
          case R.id.radioButton1:
              FilterProduct("most_ordered");
              dialog2.dismiss();

              break;
          case R.id.radioButton2:
              FilterProduct("newest");
              dialog2.dismiss();

              break;
          case R.id.radioButton3:
              FilterProduct("oldest");
              dialog2.dismiss();

              break;
          case R.id.radioButton4:
              FilterProduct("top_rated");
              dialog2.dismiss();

              break;
      }

    }
    public void FilterProduct(String sort)
    {
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<ModelFilterProducts> call = api.FilterProduct(sort, providerId, categoryId, "ar");
        call.enqueue(new Callback<ModelFilterProducts>() {
            @Override
            public void onResponse(Call<ModelFilterProducts> call, Response<ModelFilterProducts> response) {
                if (response.isSuccessful()) {
                    ModelFilterProducts body = response.body();
                    List<Datum> data = body.getData();
                    adapterFlterProduct.SetFilterData(data);
                    adapterFlterProduct.notifyDataSetChanged();

                } else {
                    try {
                        Toast.makeText(ProductsActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelFilterProducts> call, Throwable t) {
              Toast.makeText(ProductsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
