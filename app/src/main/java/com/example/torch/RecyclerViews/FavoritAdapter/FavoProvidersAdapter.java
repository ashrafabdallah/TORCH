package com.example.torch.RecyclerViews.FavoritAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.ModelFavorits.Datum;
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

public class FavoProvidersAdapter extends RecyclerView.Adapter<FavoProvidersAdapter.Myfavoprovider> {
    private Context context;
    private List<Datum> datumList;
    private SharedPreferences sharedPreferences;

    public FavoProvidersAdapter(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userLogin", context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Myfavoprovider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_favorit_provider, parent, false);
        return new Myfavoprovider(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myfavoprovider holder, final int position) {
        Datum datum = datumList.get(position);

        String fullName = datum.getFullName();
        String image = datum.getImage();
        Integer isFav = datum.getIsFav();
        final Integer providerId = datum.getId();
        Integer rates = datum.getRates();
        holder.textName.setText(fullName);
        if (image.length() > 0) {
            Picasso.get().load(image).into(holder.profileimage);
        } else {
            holder.profileimage.setImageResource(R.drawable.logo);
        }
        if (isFav == 0) {
            holder.imageFavo.setImageResource(R.drawable.fav2);
        } else {
            holder.imageFavo.setImageResource(R.drawable.favo);

        }
        holder.ratingBar.setRating(rates);
        holder.imageFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String jwt = sharedPreferences.getString("jwt", null);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.getFavoProvider("Bearer  " + jwt, "ar", providerId);
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
                                    holder.imageFavo.setImageResource(R.drawable.favo);

                                } else {
                                    holder.imageFavo.setImageResource(R.drawable.fav2);
                                    datumList.remove(position);
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
    }

    @Override
    public int getItemCount() {
        return datumList != null ? datumList.size() : 0;
    }

    public void setData(List<Datum> datumList) {
        this.datumList = datumList;
    }

    class Myfavoprovider extends RecyclerView.ViewHolder {
        private ImageView profileimage, imageFavo;
        private TextView textName;
        private RatingBar ratingBar;

        public Myfavoprovider(@NonNull View itemView) {
            super(itemView);
            profileimage = itemView.findViewById(R.id.profile_image);
            imageFavo = itemView.findViewById(R.id.imageFavoProduct);
            textName = itemView.findViewById(R.id.textUserName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
