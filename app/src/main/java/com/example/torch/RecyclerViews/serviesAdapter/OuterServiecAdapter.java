package com.example.torch.RecyclerViews.serviesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.model.service.Category;
import com.example.torch.model.service.Service;

import java.util.List;

public class OuterServiecAdapter extends RecyclerView.Adapter<OuterServiecAdapter.MyholderServiec> {
    private List<Category> categoryList;
    private Context context;

    public OuterServiecAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyholderServiec onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_recycler_serviec_outer, parent, false);

        return new MyholderServiec(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyholderServiec holder, int position) {
        Category category = categoryList.get(position);
        List<Service> services = category.getServices();
        holder.textTitle.setText(category.getName());
        InnerServiecAdapter innerServiecAdapter = new InnerServiecAdapter(context);
        holder.recyclerViewParent.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerViewParent.setAdapter(innerServiecAdapter);
        innerServiecAdapter.SetDataToinnerRecycler(services);

        // innerServiecAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    public void SetDataToOuterRecyclerView(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    class MyholderServiec extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private RecyclerView recyclerViewParent;

        public MyholderServiec(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitleOuter);
            recyclerViewParent = itemView.findViewById(R.id.recyclerServiesInner);
        }
    }
}
