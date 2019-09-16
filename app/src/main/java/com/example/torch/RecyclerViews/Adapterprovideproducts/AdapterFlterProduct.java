package com.example.torch.RecyclerViews.Adapterprovideproducts;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.modelFilter.Datum;
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

import static android.content.Context.MODE_PRIVATE;

public class AdapterFlterProduct extends RecyclerView.Adapter<AdapterFlterProduct.MyFilterproviderproductHolder> {
private Context context;
private List<Datum> datumList;
private SharedPreferences sharedPreferences;
    public AdapterFlterProduct(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userLogin", MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyFilterproviderproductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_you_like, parent, false);
        return new MyFilterproviderproductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyFilterproviderproductHolder holder, int position) {
        Datum datum = datumList.get(position);
        String description = datum.getDescription();
        Integer finalPrice = datum.getFinalPrice();

        final Integer productId = datum.getId();
        String image = datum.getImage();
        Integer isFav = datum.getIsFav();
        String name = datum.getName();

        if (image.length() > 0) {
            Picasso.get()
                    .load(datum.getImage())
                    .error(R.drawable.logo)
                    .into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.logo);
        }
        if (isFav == 1) {
            holder.favoImage.setImageResource(R.drawable.favo);
        } else {
            holder.favoImage.setImageResource(R.drawable.fav2);
        }
        holder.favoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                String jwt = sharedPreferences.getString("jwt", null);

                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.getFavoAndRating("Bearer " + jwt, "ar", productId);
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
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                if (is_Fafo == 1) {
                                    holder.favoImage.setImageResource(R.drawable.favo);
                                } else {
                                    holder.favoImage.setImageResource(R.drawable.fav2);

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
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.textProductName.setText(name);
        holder.textDes.setText(description);
        holder.textPrice.setText(String.valueOf(finalPrice));


        // rest of button buy now
        holder.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jwt = sharedPreferences.getString("jwt", null);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.AddProductToBag("Bearer " + jwt, "ar", productId);
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
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                            Toast.makeText(context, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return datumList!=null?datumList.size():0;
    }
    public void SetFilterData(List<Datum> datumList)
    {
        this.datumList=datumList;
    }

    class MyFilterproviderproductHolder extends RecyclerView.ViewHolder {
        ImageView productImage, favoImage;
        TextView textProductName, textDes, textPrice;
        Button btnBuyNow;

        public MyFilterproviderproductHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageProduct);
            favoImage = itemView.findViewById(R.id.imageButtonFavo);
            textProductName = itemView.findViewById(R.id.textProductName);
            textPrice = itemView.findViewById(R.id.textQuanty);
            textDes = itemView.findViewById(R.id.textDesc);
            btnBuyNow = itemView.findViewById(R.id.btnBuy);
        }
    }
}
