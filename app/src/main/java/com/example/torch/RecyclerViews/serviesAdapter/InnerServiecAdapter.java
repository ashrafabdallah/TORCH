package com.example.torch.RecyclerViews.serviesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.model.service.Service;

import java.util.List;

public class InnerServiecAdapter extends RecyclerView.Adapter<InnerServiecAdapter.MyInnerHolder> {
    private Context context;
    private List<Service> serviceList;

    public InnerServiecAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyInnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_recycler_sevies_inner, parent, false);
        return new MyInnerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyInnerHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.textNameServiec.setText(service.getName());
        holder.textHourServiec.setText(String.valueOf(service.getTime()));
        holder.textPriceServiec.setText("( "+service.getPrice()+"ريال "+" )");
    }

    @Override
    public int getItemCount() {
        return serviceList!=null?serviceList.size():0;
    }
public void SetDataToinnerRecycler(List<Service> serviceList)
{
    this.serviceList=serviceList;
}
    class MyInnerHolder extends RecyclerView.ViewHolder {
        private TextView textNameServiec, textPriceServiec, textHourServiec;
        private Button btnCociesServiec;

        public MyInnerHolder(@NonNull View itemView) {
            super(itemView);
            textNameServiec = itemView.findViewById(R.id.textNameServiec);
            textPriceServiec = itemView.findViewById(R.id.textPriceServiec);
            textHourServiec = itemView.findViewById(R.id.textHourServiec);
            btnCociesServiec = itemView.findViewById(R.id.btnCociesServiec);
        }
    }
}
