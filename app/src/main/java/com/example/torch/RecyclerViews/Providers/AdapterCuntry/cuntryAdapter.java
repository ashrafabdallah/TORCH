package com.example.torch.RecyclerViews.Providers.AdapterCuntry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.model.cityAndCuntry.City;
import com.example.torch.model.cityAndCuntry.Cuntry;

import java.util.List;

public class cuntryAdapter extends RecyclerView.Adapter<cuntryAdapter.Myholder> implements CountryInterface {
    private Context context;
    private List<Cuntry> cuntries;
    private static int lastCheckedPos = 0;
    private static List<City> cities;
    private Integer cuntryId;
    private cityInterface cityInterface;

    public cuntryAdapter(Context context, cityInterface cityInterface) {
        this.context = context;
        this.cityInterface = cityInterface;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_city_cuntry, parent, false);
        return new Myholder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final Myholder holder, final int position) {
        final Cuntry cuntry = cuntries.get(position);

        final String name = cuntry.getName();

        if (position == lastCheckedPos) {

            holder.textName.setText(name);
            holder.ItemBackground.setBackgroundResource(R.drawable.city_shape_selected);
            holder.textName.setTextColor(R.color.colorPrimary);
        } else {
            holder.textName.setText(name);
            holder.ItemBackground.setBackgroundResource(R.drawable.city_shape);
            holder.textName.setTextColor(R.color.colorAccent);
        }
        final cityAdapter cityAdapter = new cityAdapter(context);
        holder.setItemClicklistner(new ItemClicklistner() {
            @Override
            public void onclick(View view, int postion) {
                int prevPos = lastCheckedPos;
                lastCheckedPos = postion;
                notifyItemChanged(prevPos);
                notifyItemChanged(lastCheckedPos);

                cities = cuntry.getCities();
                cuntryId = cuntry.getId();
                Toast.makeText(context, cuntryId + "", Toast.LENGTH_SHORT).show();
                cityAdapter.SetData(cities);
                cityInterface.getCity(cities);
                cityInterface.SetCitypostion(-1);
                getCountryId(cuntryId, lastCheckedPos);


            }
        });
    }

    public void setCityInterface(cityInterface cityInterface) {
        this.cityInterface = cityInterface;
    }


    @Override
    public int getItemCount() {
        return cuntries != null ? cuntries.size() : 0;
    }

    public void SetData(List<Cuntry> cuntries) {
        this.cuntries = cuntries;
    }


    public int CountryId() {
        return cuntryId;
    }

    @Override
    public void getCountryId(int CountryID, int postion) {
        this.cuntryId = CountryID;
    }

    class Myholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textName;
        private ConstraintLayout ItemBackground;
        private CardView conternair;
        private ItemClicklistner itemClicklistner;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textCity);
            ItemBackground = itemView.findViewById(R.id.backGround);
            conternair = itemView.findViewById(R.id.container);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClicklistner.onclick(v, getAdapterPosition());

        }

        public void setItemClicklistner(ItemClicklistner itemClicklistner) {
            this.itemClicklistner = itemClicklistner;
        }

    }

    interface ItemClicklistner {
        void onclick(View view, int postion);
    }


}

