package com.example.torch.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.torch.R;
import com.example.torch.RecyclerViews.AdapterBag.MyBagAdapter;
import com.example.torch.activity.Delivery.DeliveryActivity;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.modelBag.Data;
import com.example.torch.model.modelBag.ModelMyBag;
import com.example.torch.model.modelBag.Product;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MYBageFragment extends Fragment {
    private RecyclerView recyclerViewMyBag;
    private MyBagAdapter myBagAdapter;
    private SharedPreferences sharedPreferences;
    private SwipeRefreshLayout swipeRefreshLayout;
private CardView btnAddBagMarket;
private Data data;
    public MYBageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mybage, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userLogin", MODE_PRIVATE);
        btnAddBagMarket=v.findViewById(R.id.btnAddBagMarket);
        swipeRefreshLayout = v.findViewById(R.id.SwipeRefres);
        recyclerViewMyBag = v.findViewById(R.id.recyclerViewMyBag);
        recyclerViewMyBag.setHasFixedSize(true);
        final TextView btnTotalPrice = v.findViewById(R.id.btnTotalPrice);
        recyclerViewMyBag.setLayoutManager(new LinearLayoutManager(getActivity()));
        myBagAdapter = new MyBagAdapter(getActivity(),btnTotalPrice);

        recyclerViewMyBag.setAdapter(myBagAdapter);
        FeatchData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FeatchData();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        btnAddBagMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), DeliveryActivity.class);
                i.putExtra("allData",data);
                startActivity(i);
            }
        });
        return v;
    }
    private void FeatchData() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        final String jwt = sharedPreferences.getString("jwt", null);
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<ModelMyBag> call = api.getAllBag("Bearer " + jwt, "ar");
        call.enqueue(new Callback<ModelMyBag>() {
            @Override
            public void onResponse(Call<ModelMyBag> call, Response<ModelMyBag> response) {
                swipeRefreshLayout.setRefreshing(false);
                dialog.dismiss();
                if (response.isSuccessful()) {
                    ModelMyBag body = response.body();
                    data = body.getData();
                    List<Product> productList = data.getProducts();
                    myBagAdapter.setData(productList);
                    myBagAdapter.notifyDataSetChanged();

                } else {
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelMyBag> call, Throwable t) {
                dialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();


            }

        });

    }
}
