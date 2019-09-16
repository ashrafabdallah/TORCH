package com.example.torch.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.RecyclerViews.adapterServiceWithHeader.ServiceAdapterwithHeader;
import com.example.torch.RecyclerViews.serviesAdapter.OuterServiecAdapter;
import com.example.torch.activity.ProviderServicesAndProducts;
import com.example.torch.api.ApiCunterierAndProviders;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.service.Category;
import com.example.torch.model.service.Data;
import com.example.torch.model.service.ModelService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiesFragmentNestedRecycler extends Fragment {

    private OuterServiecAdapter outerServiecAdapter;
    private ServiceAdapterwithHeader adapterwithHeader;

    public ServiesFragmentNestedRecycler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_servies_fragment_nested_recycler, container, false);
        RecyclerView recyclerOuter = v.findViewById(R.id.recyclerOuter);
        recyclerOuter.setLayoutManager(new LinearLayoutManager(getActivity()));
        outerServiecAdapter = new OuterServiecAdapter(getActivity());
       // adapterwithHeader = new ServiceAdapterwithHeader(getActivity());
        recyclerOuter.setAdapter(adapterwithHeader);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

       // fetchDataToRecyclerviewWithHeader();
        FetchData();
    }

    public void FetchData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        final String jwt = sharedPreferences.getString("jwt", null);
        ProviderServicesAndProducts activity = (ProviderServicesAndProducts) getActivity();
        int providerId = activity.sentTofragmentProviderId();
        Retrofit retrofitInstance = RetrofitClient.getRetrofitInstance();
        ApiCunterierAndProviders api = retrofitInstance.create(ApiCunterierAndProviders.class);
        Call<ModelService> call = api.getproviderdetails(3, "en", "Bearer " + jwt);
        call.enqueue(new Callback<ModelService>() {
            @Override
            public void onResponse(Call<ModelService> call, Response<ModelService> response) {
                if (response.isSuccessful()) {
                    ModelService body = response.body();
                    Data data = body.getData();
                    List<Category> categories = data.getCategories();
                    outerServiecAdapter.SetDataToOuterRecyclerView(categories);
//                    outerServiecAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), "error to get category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelService> call, Throwable t) {

            }
        });
    }

//    public void fetchDataToRecyclerviewWithHeader() {
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
//        final String jwt = sharedPreferences.getString("jwt", null);
//        ProviderServicesAndProducts activity = (ProviderServicesAndProducts) getActivity();
//        int providerId = activity.sentTofragmentProviderId();
//        Retrofit retrofitInstance = RetrofitClient.getRetrofitInstance();
//        ApiCunterierAndProviders api = retrofitInstance.create(ApiCunterierAndProviders.class);
//        Call<ModelService> call = api.getproviderdetails(3, "en", "Bearer " + jwt);
//        call.enqueue(new Callback<ModelService>() {
//            @Override
//            public void onResponse(Call<ModelService> call, Response<ModelService> response) {
//                if (response.isSuccessful()) {
//                    ModelService body = response.body();
//                    Data data = body.getData();
//                    List<Category> categories = data.getCategories();
//                    adapterwithHeader.SetDataToAdapter(categories);
//                    adapterwithHeader.notifyDataSetChanged();
//
//                } else {
//                    Toast.makeText(getActivity(), "error to get category", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelService> call, Throwable t) {
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
