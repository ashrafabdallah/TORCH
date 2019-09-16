package com.example.torch.RecyclerViews.AdapterReservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.model.modelreservation.Datum;

import java.util.List;

public class AdapterPendingReservation extends RecyclerView.Adapter<AdapterPendingReservation.MyPendingHolder>{
   private Context context;
   private List<Datum >datumList;

    public AdapterPendingReservation(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_pending_reservation,parent,false);
        return new MyPendingHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPendingHolder holder, int position) {
        Datum datum = datumList.get(position);
        String type = datum.getType();
        Integer price = datum.getPrice();
        String date = datum.getDate();
        String time = datum.getTime();
        holder.textType.setText(type);
        holder.textDate.setText(date);
        holder.textTime.setText(time);
        holder.textPriece.setText(String.valueOf(price));
    }

    @Override
    public int getItemCount() {
        return datumList!=null?datumList.size():0;
    }
    public void SetData(List<Datum >datumList)
    {
        this.datumList=datumList;
    }


    class MyPendingHolder extends RecyclerView.ViewHolder
    {
        TextView textType,textDate,textTime,textPriece;

        public MyPendingHolder(@NonNull View itemView) {
            super(itemView);
            textType=itemView.findViewById(R.id.textType);
            textDate=itemView.findViewById(R.id.textDate);
            textTime=itemView.findViewById(R.id.textTime);
            textPriece=itemView.findViewById(R.id.textPriece);
        }
    }
}
