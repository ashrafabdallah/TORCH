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
import com.example.torch.activity.ProductDetailes.ProductDetails;
import com.example.torch.activity.products.ProductsActivity;
import com.example.torch.model.homeModel.Offer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterOffers extends RecyclerView.Adapter<AdapterOffers.MyOfferHolder> {
    private List<Offer> list;
    private Context context;

    public AdapterOffers(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyOfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new MyOfferHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOfferHolder holder, int position) {
        final Offer offer = list.get(position);
        if (offer.getImage().length() > 0) {
            Picasso.get()
                    .load(offer.getImage())
                    .error(R.drawable.logo)
                    .into(holder.imageOffer);
        } else {
            holder.imageOffer.setImageResource(R.drawable.logo);
        }
        holder.textOffer.setText(offer.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProductDetails.class);
                i.putExtra("id", offer.getId());
                i.putExtra("name",offer.getName());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void SetDataToAdapter(List<Offer> list) {
        this.list = list;
    }

    class MyOfferHolder extends RecyclerView.ViewHolder {
        private ImageView imageOffer;
        private TextView textOffer;
        private CardView cardView;

        public MyOfferHolder(@NonNull View itemView) {
            super(itemView);
            imageOffer = itemView.findViewById(R.id.imageOffers);
            textOffer = itemView.findViewById(R.id.textOffers);
            cardView = itemView.findViewById(R.id.Container);
        }
    }


}

