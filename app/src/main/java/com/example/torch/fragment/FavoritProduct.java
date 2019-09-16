package com.example.torch.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.RecyclerViews.FavoritAdapter.FavoProductsAdapter;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.OfferModel.Datum;
import com.example.torch.model.OfferModel.OfferData;

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
public class FavoritProduct extends Fragment {
    private FavoProductsAdapter adapter;
    private String jwt;
private SharedPreferences sharedPreferences;
private   SwipeRefreshLayout swipeRefreshLayout;
    public FavoritProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorit_product, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userLogin", MODE_PRIVATE);
        jwt = sharedPreferences.getString("jwt", null);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerFavoritProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavoProductsAdapter(getActivity());
        recyclerView.setAdapter(adapter);
       swipeRefreshLayout=v.findViewById(R.id.SwipeRefres2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchData();
            }
        });
        FetchData();
        return v;
    }

    private void FetchData() {
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<OfferData> call = api.getProductFavo("ar", "Bearer  " + jwt);
        call.enqueue(new Callback<OfferData>() {
            @Override
            public void onResponse(Call<OfferData> call, Response<OfferData> response) {
               swipeRefreshLayout.setRefreshing(false);
                if(response.isSuccessful())
                {
                    OfferData body = response.body();
                    List<Datum> data = body.getData();
                    adapter.SetData(data);
                    adapter.notifyDataSetChanged();
                }else {
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OfferData> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
