package com.example.torch.RecyclerViews.AtapterTo3RecyclerView_InHomeFragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.activity.ProviderServicesAndProducts;
import com.example.torch.model.homeModel.Offer;
import com.example.torch.model.homeModel.Provider;
import com.squareup.picasso.Picasso;

import java.util.List;

public class providersAdapter extends RecyclerView.Adapter<providersAdapter.MyOfferHolder> {
    private List<Provider> list;
    private Context context;

    public providersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyOfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_products, parent, false);

        return new MyOfferHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOfferHolder holder, int position) {
        final Provider provider = list.get(position);
        if (provider.getImage().length() > 0) {
            Picasso.get()
                    .load(provider.getImage())
                    .error(R.drawable.logo)
                    .into(holder.imageOffer);
        } else {
            holder.imageOffer.setImageResource(R.drawable.logo);
        }
        holder.textOffer.setText(provider.getName());
holder.contenar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(context, ProviderServicesAndProducts.class);
        i.putExtra("providerId",provider.getId());
        i.putExtra("fullname",provider.getName());
        i.putExtra("numberofProvider",list.size());
        context.startActivity(i);

    }
});

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void SetDataToAdapter(List<Provider> list) {
        this.list = list;
    }

    class MyOfferHolder extends RecyclerView.ViewHolder {
        private ImageView imageOffer;
        private TextView textOffer;
private CardView contenar;
        public MyOfferHolder(@NonNull View itemView) {
            super(itemView);
            imageOffer = itemView.findViewById(R.id.imageOffers);
            textOffer = itemView.findViewById(R.id.textOffers);
            contenar=itemView.findViewById(R.id.contenar);

        }
    }




}