package com.example.torch.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torch.R;
import com.example.torch.RecyclerViews.Tutorial.TutorailAdapter;
import com.example.torch.activity.ProviderServicesAndProducts;
import com.example.torch.api.ApiCunterierAndProviders;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.model_Tutorial.Data;
import com.example.torch.model.model_Tutorial.ModelTutorial;
import com.example.torch.model.model_Tutorial.Tutorial;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialFragment extends Fragment {
private TutorailAdapter tutorailAdapter;

    public TutorialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=inflater.inflate(R.layout.fragment_tutorial, container, false);
        RecyclerView recyclerViewTurorial=v.findViewById(R.id.recyclerTutorial);
        recyclerViewTurorial.setLayoutManager(new GridLayoutManager(getActivity(),getNumberOfColumns()));
        recyclerViewTurorial.setHasFixedSize(true);
        tutorailAdapter=new TutorailAdapter(getActivity());
        recyclerViewTurorial.setAdapter(tutorailAdapter);
       return v;
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
    @Override
    public void onStart() {
        super.onStart();
        fetchTutorial();
    }
    public void fetchTutorial()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        final String jwt =sharedPreferences.getString("jwt", null);
        ProviderServicesAndProducts activity=(ProviderServicesAndProducts)getActivity();
        int providerId = activity.sentTofragmentProviderId();
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        ApiCunterierAndProviders api = instance.create(ApiCunterierAndProviders.class);
        Call<ModelTutorial> call = api.getTutorial(providerId, "Bearer " + jwt, "ar");
        call.enqueue(new Callback<ModelTutorial>() {
            @Override
            public void onResponse(Call<ModelTutorial> call, Response<ModelTutorial> response) {
                if(response.isSuccessful())
                {
                    ModelTutorial body = response.body();
                    Data data = body.getData();
                    List<Tutorial> tutorials = data.getTutorials();
                    tutorailAdapter.setData(tutorials);
                    tutorailAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(getActivity(), "error for get Tutorial ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelTutorial> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
