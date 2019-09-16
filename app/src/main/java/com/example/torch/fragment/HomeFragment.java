package com.example.torch.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.RecyclerViews.AtapterTo3RecyclerView_InHomeFragment.AdapterOffers;
import com.example.torch.RecyclerViews.AtapterTo3RecyclerView_InHomeFragment.CousesAdapter;
import com.example.torch.RecyclerViews.AtapterTo3RecyclerView_InHomeFragment.providersAdapter;
import com.example.torch.activity.Clothes.MoreOffersActivity;
import com.example.torch.activity.ProviderActivity;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.DataIMianImages;
import com.example.torch.model.homeModel.Data;
import com.example.torch.model.homeModel.HomeData;
import com.example.torch.model.homeModel.Offer;
import com.example.torch.model.homeModel.Provider;
import com.example.torch.model.homeModel.Tutorial;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {


    private Slider slider;
    private TextView btnMoreOffer, btnMoreproduct;
    private RecyclerView recyclerViewProducts, recyclerViewOffers, recyclerViewCurose;
    private AdapterOffers adapterOffers;
    private providersAdapter providersAdapter;
    private CousesAdapter cousesAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Inatlize(v);
        slider = v.findViewById(R.id.slideShowProduct);
        SetImages();
        setHasOptionsMenu(true);
        btnMoreOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMareAboutOffers();
            }
        });
        btnMoreproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProviderActivity.class));
            }
        });

        return v;
    }

    private void getMareAboutOffers() {
        startActivity(new Intent(getActivity(), MoreOffersActivity.class));
    }

    private void Inatlize(View v) {
        btnMoreOffer = v.findViewById(R.id.btnMore);
        btnMoreproduct = v.findViewById(R.id.btnMore2);
        recyclerViewCurose = v.findViewById(R.id.recyclerViewCourse);
        recyclerViewProducts = v.findViewById(R.id.recyclerViewProducts);
        recyclerViewOffers = v.findViewById(R.id.recyclerView_Offers);
        setUprecyclerview();

    }

    private void SetImages() {

        Retrofit retrofitInstance = RetrofitClient.getRetrofitInstance();
        Api api = retrofitInstance.create(Api.class);
        Call<DataIMianImages> call = api.getMainImages("en");
        call.enqueue(new Callback<DataIMianImages>() {
            @Override
            public void onResponse(Call<DataIMianImages> call, Response<DataIMianImages> response) {
                if (response.isSuccessful()) {
                    List<String> data = response.body().getData();
                    List<Slide> slideList = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        slideList.add(new Slide(i, data.get(i), getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
                    }
                    slider.addSlides(slideList);
                } else {
                    Toast.makeText(getActivity(), "error load images", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataIMianImages> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setUprecyclerview() {
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        recyclerViewOffers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        recyclerViewCurose.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

        adapterOffers = new AdapterOffers(getActivity());
        providersAdapter = new providersAdapter(getActivity());
        cousesAdapter = new CousesAdapter(getActivity());

        recyclerViewOffers.setAdapter(adapterOffers);
        recyclerViewProducts.setAdapter(providersAdapter);
        recyclerViewCurose.setAdapter(cousesAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        FetchData();

    }

    private void FetchData() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.loading_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<HomeData> call = api.getHomeData("en");
        call.enqueue(new Callback<HomeData>() {
            @Override
            public void onResponse(Call<HomeData> call, Response<HomeData> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    JsonPasrsing(response.body());
                } else {
                    Toast.makeText(getActivity(), "error of Offers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeData> call, Throwable t) {
                dialog.dismiss();

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void JsonPasrsing(HomeData body) {

        Data data = body.getData();
        List<Provider> providers = data.getProviders();
        List<Offer> offers = data.getOffers();
        List<Tutorial> tutorial = data.getTutorial();
        adapterOffers.SetDataToAdapter(offers);
        providersAdapter.SetDataToAdapter(providers);
        cousesAdapter.SetDataToAdapter(tutorial);
        adapterOffers.notifyDataSetChanged();
        providersAdapter.notifyDataSetChanged();
        cousesAdapter.notifyDataSetChanged();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
      //  menu.findItem(R.id.searchHome).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }
}
