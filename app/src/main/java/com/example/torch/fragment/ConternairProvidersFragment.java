package com.example.torch.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.RecyclerViews.Providers.AdapterCuntry.CountryInterface;
import com.example.torch.RecyclerViews.Providers.AdapterCuntry.cityAdapter;
import com.example.torch.RecyclerViews.Providers.AdapterCuntry.cityInterface;
import com.example.torch.RecyclerViews.Providers.AdapterCuntry.cuntryAdapter;
import com.example.torch.RecyclerViews.Providers.AdapterProviders.providerAdapter;
import com.example.torch.api.ApiCunterierAndProviders;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.cityAndCuntry.City;
import com.example.torch.model.cityAndCuntry.Cuntry;
import com.example.torch.model.cityAndCuntry.ModelCityAndcountry;
import com.example.torch.model.providerdetailes.Datum;
import com.example.torch.model.providerdetailes.ProviderDetailes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConternairProvidersFragment extends Fragment implements cityInterface, CountryInterface {

    private RecyclerView recyclerViewCuntry, recyclerViewCity, recyclerViewProviders;
    private cuntryAdapter cuntryAdapter;
    private cityAdapter cityAdapter;
    private int CountryID;
    private String cityId = "all";
    private providerAdapter providerAdapter;

    public ConternairProvidersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_conternair_providers, container, false);
        inatlize(v);
        return v;
    }

    private void inatlize(View v) {
        recyclerViewCity = v.findViewById(R.id.recyclerViewCity);
        recyclerViewCuntry = v.findViewById(R.id.recyclerViewCuntry);
        recyclerViewProviders = v.findViewById(R.id.recyclerViewProviders);
        stupAllRecyclerview();
    }

    private void stupAllRecyclerview() {
        recyclerViewCuntry.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        recyclerViewProviders.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        cuntryAdapter = new cuntryAdapter(getActivity(), this);
        cityAdapter = new cityAdapter(getActivity());
        providerAdapter = new providerAdapter(getActivity());
        recyclerViewCuntry.setAdapter(cuntryAdapter);
        recyclerViewCity.setAdapter(cityAdapter);
        recyclerViewProviders.setAdapter(providerAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        FetchCuntry();
//        FetchProviderDetaile();
    }


    private void FetchCuntry() {
        Retrofit instance = RetrofitClient.getRetrofitInstance();

        ApiCunterierAndProviders api = instance.create(ApiCunterierAndProviders.class);
        Call<ModelCityAndcountry> call = api.getCuntry("en");
        call.enqueue(new Callback<ModelCityAndcountry>() {
            @Override
            public void onResponse(Call<ModelCityAndcountry> call, Response<ModelCityAndcountry> response) {
                if (response.isSuccessful()) {
                    ModelCityAndcountry body = response.body();
                    List<Cuntry> data = body.getData();
                    for (int i = 0; i < data.size(); i++) {
                        Cuntry cuntry = data.get(i);
                        List<City> cities = cuntry.getCities();
                        cityAdapter.SetData(cities);
                        cityAdapter.notifyDataSetChanged();

                    }
                    CountryID = data.get(0).getId();
                    cityId = cityAdapter.CityId();
                    FetchProviderDetaile(CountryID, cityId);
                    cityAdapter.SetData(data.get(0).getCities());
                    cuntryAdapter.SetData(data);
                    cuntryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "error of  country", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelCityAndcountry> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        cuntryAdapter.setCityInterface(this);
    }

    @Override
    public void getCity(List<City> cities) {
        cityAdapter.SetData(cities);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void SetCitypostion(int postion) {

    }

    @Override
    public void getcityId(String cityId) {
        FetchProviderDetaile(CountryID, cityId);
    }


    public void FetchProviderDetaile(int countryId, String city) {
        Log.i("CityFragment", cityId + "");

        //CountryID = cuntryAdapter.CountryId();
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        ApiCunterierAndProviders api = instance.create(ApiCunterierAndProviders.class);
        Call<ProviderDetailes> call = api.getProvider("ar", countryId, city);
        call.enqueue(new Callback<ProviderDetailes>() {
            @Override
            public void onResponse(Call<ProviderDetailes> call, Response<ProviderDetailes> response) {
                if (response.isSuccessful()) {
                    ProviderDetailes body = response.body();
                    List<Datum> data = body.getData();
                    providerAdapter.setData(data);
                    providerAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProviderDetailes> call, Throwable t) {

            }
        });

    }


    @Override
    public void getCountryId(int CountryID, int postion) {
        this.CountryID = CountryID;

    }
}
