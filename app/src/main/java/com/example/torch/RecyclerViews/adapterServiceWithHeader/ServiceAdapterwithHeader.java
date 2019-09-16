package com.example.torch.RecyclerViews.adapterServiceWithHeader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.model.service.Category;
import com.example.torch.model.service.Service;

import java.util.List;

public class ServiceAdapterwithHeader extends RecyclerView.Adapter<ServiceAdapterwithHeader.MYServiceHolder> {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;
    private List<Category> categoryList;
    //private List<Service> serviceList;
private Context context;

    public ServiceAdapterwithHeader(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MYServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        MYServiceHolder holder;
        if (viewType == TYPE_LIST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.serviece_list, parent, false);
            holder = new MYServiceHolder(view, viewType);
            return holder;
        } else if (viewType == TYPE_HEAD) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_head_service, parent, false);
            holder = new MYServiceHolder(view, viewType);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MYServiceHolder holder, int position) {
        Category category = categoryList.get(position );
        if (holder.ViewType == TYPE_LIST) {
            List<Service> serviceList = category.getServices();
            Service service = serviceList.get(position);
            holder.textNameServiec.setText(service.getName());
            holder.textHourServiec.setText(String.valueOf(service.getTime()));
            holder.textPriceServiec.setText(String.valueOf(service.getPrice()));
        } else if (holder.ViewType == TYPE_HEAD) {
            holder.textTitle.setText(category.getName());
        }

    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    public void SetDataToAdapter(List<Category> categoryList) {
    this.categoryList=categoryList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_LIST;
        }
    }

    class MYServiceHolder extends RecyclerView.ViewHolder {
        private int ViewType;

        // variable for list
        private TextView textNameServiec, textPriceServiec, textHourServiec;
        private Button btnCociesServiec;
        // variable for Head
        private TextView textTitle;

        public MYServiceHolder(@NonNull View itemView, int ViewType) {
            super(itemView);
            if (ViewType == TYPE_LIST) {
                textNameServiec = itemView.findViewById(R.id.textNameServiec);
                textPriceServiec = itemView.findViewById(R.id.textPriceServiec);
                textHourServiec = itemView.findViewById(R.id.textHourServiec);
                btnCociesServiec = itemView.findViewById(R.id.btnCociesServiec);
                ViewType = 1;
            } else if (ViewType == TYPE_HEAD) {
                textTitle = itemView.findViewById(R.id.textTitleOuter);
                ViewType = 0;
            }

        }
    }
}
