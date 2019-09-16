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

public class cityAdapter extends RecyclerView.Adapter<cityAdapter.Myholder> implements cityInterface {

    private List<City> cityList;
    private static int lastCheckedPos = -1;
    static String cityId;
    private Context context;
    private ModelofCityIdAndCountryIdModel model;

    public cityAdapter(Context context) {
        this.context = context;
        model = new ModelofCityIdAndCountryIdModel();
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_city_cuntry, parent, false);
        return new Myholder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
//        int i = cuntryAdapter.getCities();

//        final Datum cuntry = cityList.get(position);
//        final String name = cuntry.getName();
        final City city = cityList.get(position);
        cityId = "all";
//        model.setCityId(cityId);


        if (position == lastCheckedPos) {
            model.setCityId(String.valueOf(city.getId()));
            holder.textName.setText(city.getName());
            holder.ItemBackground.setBackgroundResource(R.drawable.city_shape_selected);
            holder.textName.setTextColor(R.color.colorPrimary);
        } else {
            model.setCityId(String.valueOf(city.getId()));
            holder.textName.setText(city.getName());
            holder.ItemBackground.setBackgroundResource(R.drawable.city_shape);
            holder.textName.setTextColor(R.color.colorAccent);
        }

        holder.setItemClicklistner(new ItemClicklistner() {
            @Override
            public void onclick(View view, int postion) {
                int prevPos = lastCheckedPos;
                lastCheckedPos = postion;
                notifyItemChanged(prevPos);
                notifyItemChanged(lastCheckedPos);
                cityId = String.valueOf(city.getId());
                getcityId(cityId);
                //  model.setCityId(cityId);
            }
        });

    }

    public String CityId() {
        Log.i("cityIdAdapter", model.getCityId() + "");
        return cityId != null ? cityId : "all";
    }

    @Override
    public int getItemCount() {
        return cityList != null ? cityList.size() : 0;
    }

    public void SetData(List<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public void getCity(List<City> cities) {
        this.cityList = cities;
    }

    @Override
    public void SetCitypostion(int postion) {
        lastCheckedPos = postion;
    }

    @Override
    public void getcityId(String cityId) {
        this.cityId=cityId;

    }

    //    @Override
//    public void getcityId(String cityId) {
//        this.cityId=cityId;
//
//    }
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

        public void setItemClicklistner(ItemClicklistner itemClicklistner) {
            this.itemClicklistner = itemClicklistner;
        }

        @Override
        public void onClick(View v) {
            itemClicklistner.onclick(v, getAdapterPosition());

        }
    }

    interface ItemClicklistner {
        void onclick(View view, int postion);
    }

}
