package com.example.torch.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.torch.R;
import com.example.torch.activity.AboutAppActivity;
import com.example.torch.activity.CallUsActivity;
import com.example.torch.activity.Profile.ProfileDetailesActivity;
import com.example.torch.activity.auth.LoginActivity;
import com.example.torch.activity.reservation.ReservationActivity;
import com.example.torch.activity.settings.SettingsActivity;
import com.example.torch.api.Api;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.Data;
import com.example.torch.model.LoginModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreMainFragment extends Fragment {
    private ConstraintLayout  btnBooking, btnsettings, btnapplication, btncallus, btnlogout;
    private TextView textName;
    private CircleImageView profile_image;
    private String jwt;
    private Intent intent;
    private Data data;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CardView btnprofiiledetail;
private  SharedPreferences sharedPreferences;
    public MoreMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_more_main, container, false);
         sharedPreferences = getActivity().getSharedPreferences("userLogin", MODE_PRIVATE);
        jwt = sharedPreferences.getString("jwt", null);
        TextView titel=v.findViewById(R.id.textMain);
        titel.setText(R.string.more);
        swipeRefreshLayout=v.findViewById(R.id.SwipeRefres4);
        intalize(v);
        getProfileImageAndName();
        btnprofiiledetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ProfileDetailesActivity.class);
                intent.putExtra("allData", data);
                startActivity(intent);

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProfileImageAndName();
            }
        });
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ReservationActivity.class));
            }
        });
        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });
        btnapplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutAppActivity.class));

            }
        });
        btncallus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CallUsActivity.class));

            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().remove("jwt").commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
               // getActivity().getFragmentManager().popBackStack();
                getActivity().finish();
            }
        });
        return v;
    }

    private void getProfileImageAndName() {
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        Api api = instance.create(Api.class);
        Call<LoginModel> call = api.getProfileDetailes("Bearer " + jwt, "ar");
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    LoginModel body = response.body();
                   data  = body.getData();
                    if (data.getImage().length() > 0) {
                        Picasso.get().load(data.getImage()).into(profile_image);
                    } else {
                        profile_image.setImageResource(R.drawable.logo);
                    }
                    textName.setText(data.getFullName());
                } else {
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void intalize(View v) {
        btnprofiiledetail = v.findViewById(R.id.btnprofiiledetail);
        btnBooking = v.findViewById(R.id.btnBooking);
        btnsettings = v.findViewById(R.id.btnsettings);
        btnapplication = v.findViewById(R.id.btnapplication);
        btncallus = v.findViewById(R.id.btnTermsConditions);
        btnlogout = v.findViewById(R.id.btnlogout);
        textName = v.findViewById(R.id.textName);
        profile_image = v.findViewById(R.id.profile_image);
    }

}
