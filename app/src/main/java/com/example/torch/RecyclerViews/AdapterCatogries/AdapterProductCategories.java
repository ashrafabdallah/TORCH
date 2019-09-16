package com.example.torch.RecyclerViews.AdapterCatogries;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.activity.products.ProductsActivity;
import com.example.torch.api.modelCatogries.Category;
import com.example.torch.api.modelCatogries.Data;

import java.util.List;

public class AdapterProductCategories extends RecyclerView.Adapter<AdapterProductCategories.MyCategoriesHolder> {
    private Context context;
    private List<Category> categoryList;
    private Data data;

    public AdapterProductCategories(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyCategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_products, parent, false);
        return new MyCategoriesHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyCategoriesHolder holder, int position) {

        Category category = categoryList.get(position);
        final Integer categoryId = category.getId();
        holder.textCatogryName.setText(category.getName());
        holder.parentProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProductsActivity.class);
                i.putExtra("categoryId", categoryId);
                i.putExtra("data",data);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0 ;
    }

    public void setData(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
    public void setdata(Data data)
    {
        this.data=data;
    }

    class MyCategoriesHolder extends RecyclerView.ViewHolder {
        private CardView parentProduct;
        private TextView textCatogryName;

        public MyCategoriesHolder(@NonNull View itemView) {
            super(itemView);
            textCatogryName = itemView.findViewById(R.id.textCatogryName);
            parentProduct = itemView.findViewById(R.id.parentProduct);
        }
    }
}
