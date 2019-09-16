package com.example.torch.RecyclerViews.Providers.AdapterProviders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.model.providerdetailes.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class providerAdapter extends RecyclerView.Adapter<providerAdapter.MyProviderHolder> {
    private Context context;
    private List<Datum> datumList;

    public providerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyProviderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_imtem_provider, parent, false);
        return new MyProviderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProviderHolder holder, int position) {
        Datum datum = datumList.get(position);
        holder.textName.setText(datum.getName());
        if (datum.getImage().length() > 0) {
            Picasso.get().load(datum.getImage()).into(holder.providerimage);
        } else {
            holder.providerimage.setImageResource(R.drawable.logo);
        }


    }

    @Override
    public int getItemCount() {
        return datumList != null ? datumList.size() : 0;
    }
    public void setData(List<Datum> datumList)
    {
      this.datumList=datumList;
    }

    class MyProviderHolder extends RecyclerView.ViewHolder {
        private TextView textName;
        private ImageView providerimage;

        public MyProviderHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            providerimage = itemView.findViewById(R.id.imageprovider);
        }
    }
}
