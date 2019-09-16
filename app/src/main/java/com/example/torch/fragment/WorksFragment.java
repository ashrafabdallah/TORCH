package com.example.torch.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.torch.R;
import com.example.torch.RecyclerViews.works.WorkAdapter;
import com.example.torch.activity.ProviderServicesAndProducts;
import com.example.torch.api.ApiCunterierAndProviders;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.works_model.Data;
import com.example.torch.model.works_model.ModelWorks;
import com.example.torch.model.works_model.Work;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorksFragment extends Fragment {
private WorkAdapter adapter;

    public WorksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_works, container, false);
        RecyclerView recyclerViewWorks=v.findViewById(R.id.recyclerWorks);
        adapter =new WorkAdapter(getActivity());
        recyclerViewWorks.setLayoutManager(new GridLayoutManager(getActivity(),getNumberOfColumns()));
        recyclerViewWorks.setAdapter(adapter);
        FeatchWrorksDetailes();
       return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    public int getNumberOfColumns() {
        View view = View.inflate(getActivity(), R.layout.item_row_works, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = view.getMeasuredWidth();
        int count = getResources().getDisplayMetrics().widthPixels / width;
        int remaining = getResources().getDisplayMetrics().widthPixels - width * count;
        if (remaining > width - 15)
            count++;
        return count;
    }

    public void   FeatchWrorksDetailes()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        final String jwt =sharedPreferences.getString("jwt", null);
        ProviderServicesAndProducts activity=(ProviderServicesAndProducts)getActivity();
        int providerId = activity.sentTofragmentProviderId();
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        ApiCunterierAndProviders api = instance.create(ApiCunterierAndProviders.class);
        Call<ModelWorks> call = api.getWorks(providerId, "Bearer " + jwt, "ar");
        call.enqueue(new Callback<ModelWorks>() {
            @Override
            public void onResponse(Call<ModelWorks> call, Response<ModelWorks> response) {
                if(response.isSuccessful())
                {
                    ModelWorks body = response.body();
                    Data data = body.getData();
                    List<Work> works = data.getWorks();
                    adapter.SetDataToRecyclerView(works);
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
            public void onFailure(Call<ModelWorks> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
