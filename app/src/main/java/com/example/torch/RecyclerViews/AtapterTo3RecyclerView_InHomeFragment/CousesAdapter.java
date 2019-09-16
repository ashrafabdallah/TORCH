package com.example.torch.RecyclerViews.AtapterTo3RecyclerView_InHomeFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.torch.R;
import com.example.torch.model.homeModel.Tutorial;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CousesAdapter extends RecyclerView.Adapter<CousesAdapter.MyOfferHolder> {
    private List<Tutorial> list;

private Context context;

    public CousesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyOfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_course, parent, false);

        return new MyOfferHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOfferHolder holder, int position) {
        final Tutorial tutorial = list.get(position);
        if (tutorial.getImage().length() > 0) {
            Picasso.get()
                    .load(tutorial.getImage())
                    .error(R.drawable.logo)
                    .into(holder.imageOffer);
        } else {
            holder.imageOffer.setImageResource(R.drawable.logo);
        }
        holder.textOffer.setVisibility(View.INVISIBLE);
holder.cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(tutorial.getName()));
        i.setPackage("com.google.android.youtube");
        context.startActivity(i);
    }
});

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void SetDataToAdapter(List<Tutorial> list) {
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
            cardView=itemView.findViewById(R.id.Container);

        }
    }




}