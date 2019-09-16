package com.example.torch.RecyclerViews.FavoritAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.OfferModel.Datum;
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

public class FavoProductsAdapter extends RecyclerView.Adapter<FavoProductsAdapter.MyHolderproductFavo> {
    private List<Datum> datalist;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String jwt;

    public FavoProductsAdapter(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userLogin", MODE_PRIVATE);
        jwt = sharedPreferences.getString("jwt", null);
    }

    @NonNull
    @Override
    public MyHolderproductFavo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_more_offers, parent, false);
        return new MyHolderproductFavo(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolderproductFavo holder, final int position) {
        final Datum datum = datalist.get(position);
        holder.textTitel.setText(datum.getName());
        holder.textDescription.setText(datum.getDescription());

        if (datum.getImage().length() > 0) {
            Picasso.get()
                    .load(datum.getImage())
                    .error(R.drawable.logo)
                    .into(holder.imageOffer);
        } else {
            holder.imageOffer.setImageResource(R.drawable.logo);
        }

        final Integer id = datum.getId();

        if (datum.getIsFav() == 1) {
            holder.imagFavo.setImageResource(R.drawable.favo);
        } else {
            holder.imagFavo.setImageResource(R.drawable.fav2);
        }

        holder.imagFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.getFavoAndRating("Bearer " + jwt, "ar", id);
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
                                    holder.imagFavo.setImageResource(R.drawable.favo);
                                } else {
                                    holder.imagFavo.setImageResource(R.drawable.fav2);
                                    datalist.remove(position);
                                    notifyDataSetChanged();

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


        final Integer rates = datum.getRates();
        holder.ratingBarOffer.setRating(rates.floatValue());
        holder.btnAddBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.AddProductToBag("Bearer " + jwt, "ar", id);
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

        holder.btnAddBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.AddProductToBag("Bearer " + jwt, "ar", datum.getId()
                );
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
        return datalist != null ? datalist.size() : 0;
    }

    public void SetData(List<Datum> datalist) {
        this.datalist = datalist;
    }


    class MyHolderproductFavo extends RecyclerView.ViewHolder {
        private Button btnAddBag;
        private RatingBar ratingBarOffer;
        private ImageView imageOffer, imagFavo;
        private TextView textDescription, textTitel;

        public MyHolderproductFavo(@NonNull View itemView) {
            super(itemView);
            btnAddBag = itemView.findViewById(R.id.btnAddBagoffer);
            ratingBarOffer = itemView.findViewById(R.id.ratingBarOffers);
            imageOffer = itemView.findViewById(R.id.imageOffers);
            imagFavo = itemView.findViewById(R.id.imageFavoOffer);
            textDescription = itemView.findViewById(R.id.textDescOffer);
            textTitel = itemView.findViewById(R.id.textTitelOffer);
        }
    }
}
