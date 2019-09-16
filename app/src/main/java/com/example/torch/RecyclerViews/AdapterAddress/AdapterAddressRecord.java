package com.example.torch.RecyclerViews.AdapterAddress;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.activity.addres_record.addAddress.UpdateAddressActivity;
import com.example.torch.model.modelmyAddress.Datum;

import java.util.List;

public class AdapterAddressRecord extends RecyclerView.Adapter<AdapterAddressRecord.MyAddressHolder> {

    private List<Datum> datumList;
    private Context context;

    public AdapterAddressRecord(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyAddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_address_record, parent, false);
        return new MyAddressHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAddressHolder holder, int position) {
        final Datum datum = datumList.get(position);
        holder.textName.setText(datum.getFullName());
        holder.textStreet.setText(datum.getFlat());
        holder.textCountry.setText(datum.getRegion());
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, UpdateAddressActivity.class);
                i.putExtra("dataAddress",datum);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datumList != null ? datumList.size() : 0;
    }

    public void setData(List<Datum> datumList) {
    this.datumList=datumList;
    }

    class MyAddressHolder extends RecyclerView.ViewHolder {
        private TextView textName, textStreet, textCountry, btnupdate, btndelete;

        public MyAddressHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textStreet = itemView.findViewById(R.id.textStreet);
            textCountry = itemView.findViewById(R.id.textCountry);
            btnupdate = itemView.findViewById(R.id.btnChange);
            btndelete = itemView.findViewById(R.id.btndelete);
        }
    }
}
