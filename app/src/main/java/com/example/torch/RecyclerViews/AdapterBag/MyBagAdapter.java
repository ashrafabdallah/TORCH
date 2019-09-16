package com.example.torch.RecyclerViews.AdapterBag;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.modelBag.Product;
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

public class MyBagAdapter extends RecyclerView.Adapter<MyBagAdapter.MybagHolder> {
    private Context context;
    private List<Product> productList;
    private SharedPreferences sharedPreferences;
    private TextView textshow;
    private int count;

    public MyBagAdapter(Context context, TextView textshow) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userLogin", context.MODE_PRIVATE);
        this.textshow = textshow;
    }

    @NonNull
    @Override
    public MybagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_mybag, parent, false);
        return new MybagHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MybagHolder holder, final int position) {
        final String jwt = sharedPreferences.getString("jwt", null);
        final Product product = productList.get(position);
        if (productList.size() == 0) {
           // new ftechtoatl(v2, context).execute(0);
            textshow.setText(String.valueOf(0));
        }
        Integer isFav = product.getIsFav();
        final Integer productId = product.getProductId();
        final Integer IdProductOnCart = product.getId();
        final Integer total = product.getTotal();
       // new ftechtoatl(v2, context).execute(total);
        textshow.setText(String.valueOf(total));
        if (isFav == 0) {
            holder.imageFavo.setImageResource(R.drawable.fav2);
        } else {
            holder.imageFavo.setImageResource(R.drawable.favo);
        }
        if (product.getImage().length() > 0) {
            Picasso.get().load(product.getImage()).into(holder.imageProduct);
        } else {
            holder.imageProduct.setImageResource(R.drawable.logo);
        }
        holder.textName.setText(product.getName());
        holder.textDesc.setText(product.getDescription());
        holder.textQuanty.setText(String.valueOf(product.getQuantity()));
        holder.textPrice.setText("ريال" + product.getFinalPrice());

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               count  = Integer.parseInt(holder.textQuanty.getText().toString());
               ++ count;

                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.UpdateQuantityofProduct(count, "Bearer " + jwt, "ar", IdProductOnCart);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                JSONObject data = jsonObject.getJSONObject("data");
                                int total3 = data.getInt("total");
                                //new ftechtoatl(v2, context).execute(total3);
                                textshow.setText(String.valueOf(total3));
                                String message = jsonObject.getString("message");
                                notifyDataSetChanged();
                                holder.textQuanty.setText(data.getString("quantity")+"");

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        holder.btnDescrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = Integer.parseInt(holder.textQuanty.getText().toString());
               -- count;
                if (count > 0) {
                    Retrofit instance = RetrofitClient.getRetrofitInstance();
                    Api api = instance.create(Api.class);
                    Call<ResponseBody> call = api.UpdateQuantityofProduct(count, "Bearer " + jwt, "ar", IdProductOnCart);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {


                                try {

                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    int total3 = data.getInt("total");
//                                    new ftechtoatl(v2, context).execute(total3);
                                    textshow.setText(String.valueOf(total3));
                                    String message = jsonObject.getString("message");
                                    notifyDataSetChanged();
                                    holder.textQuanty.setText(data.getString("quantity")+"");

                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                try {
                                    Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        holder.imageFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.getFavoAndRating("Bearer  " + jwt, "ar", productId);
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
                                if (is_Fafo == 0) {
                                    holder.imageFavo.setImageResource(R.drawable.fav2);
                                } else {
                                    holder.imageFavo.setImageResource(R.drawable.favo);

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
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                Api api = instance.create(Api.class);
                Call<ResponseBody> call = api.DeleteProductFromBag("Bearer  " + jwt, "ar", IdProductOnCart);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            try {

                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String message = jsonObject.getString("message");

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                productList.remove(position);
                                if (productList.size() == 0) {
                                    textshow.setText(String.valueOf(0));
                                }
                                notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String message = jsonObject.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
        return productList != null ? productList.size() : 0;
    }

    public void setData(List<Product> productList) {
        this.productList = productList;
    }

    class MybagHolder extends RecyclerView.ViewHolder {
        private ImageView imageProduct, imageFavo, imageDelete;
        private TextView textName, textDesc, textQuanty, textPrice;
        private ImageButton btnIncrease, btnDescrease;
        private CardView bagContainer;

        public MybagHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            imageFavo = itemView.findViewById(R.id.imageFavo);
            imageDelete = itemView.findViewById(R.id.imageDelete);
            textName = itemView.findViewById(R.id.textName);
            textDesc = itemView.findViewById(R.id.textDesc);
            textQuanty = itemView.findViewById(R.id.textQuanty);
            textPrice = itemView.findViewById(R.id.textPrice);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDescrease = itemView.findViewById(R.id.btnDescrease);
            bagContainer = itemView.findViewById(R.id.bagContainer);
        }
    }


}
