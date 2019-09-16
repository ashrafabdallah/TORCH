package com.example.torch.fragment.reservation;


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
import com.example.torch.RecyclerViews.AdapterReservation.AdapterPendingReservation;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.modelreservation.Datum;
import com.example.torch.model.modelreservation.ModelReservation;

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
public class Pending extends Fragment {
    private AdapterPendingReservation adapterPendingReservation;
    private String jwt;

    public Pending() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pending, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLogin", MODE_PRIVATE);
        jwt = sharedPreferences.getString("jwt", null);
        RecyclerView recyclerView = v.findViewById(R.id.recyclepending);
        adapterPendingReservation = new AdapterPendingReservation(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapterPendingReservation);
        featchData();

        return v;
    }

    private void featchData() {
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<ModelReservation> call = api.getPendingReservation("Bearer " + jwt, "ar");
        call.enqueue(new Callback<ModelReservation>() {
            @Override
            public void onResponse(Call<ModelReservation> call, Response<ModelReservation> response) {
                if (response.isSuccessful()) {
                    ModelReservation body = response.body();
                    List<Datum> data = body.getData();
                    adapterPendingReservation.SetData(data);
                    adapterPendingReservation.notifyDataSetChanged();
                } else {
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelReservation> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
