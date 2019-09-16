package com.example.torch.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.torch.R;
import com.example.torch.activity.ProviderServicesAndProducts;
import com.example.torch.api.Api;
import com.example.torch.api.ApiCunterierAndProviders;
import com.example.torch.api.RetrofitClient;
import com.example.torch.model.DataIMianImages;
import com.example.torch.model.service.Data;
import com.example.torch.model.service.ModelService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Services extends Fragment {
private TextView textName,textNumberProvider;
private ImageView imgFavo,imageProfile;
private RatingBar ratingBar;
private Slider slider;
private Button btnServies,btnWorks,btnTutorial;


    public Services() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ProviderServicesAndProducts activity=(ProviderServicesAndProducts)getActivity();
       SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
       final String jwt =sharedPreferences.getString("jwt", null);
        final int providerId = activity.sentTofragmentProviderId();
        int number = activity.sentTofragmentNumberofProfider();

        View v= inflater.inflate(R.layout.fragment_services, container, false);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framServises,new ServiesFragmentNestedRecycler()).commit();

        fetchprovideDetails(providerId,number);
        inatlize(v);
        SelectedButtonServices(v);
        imgFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.loading_bar);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Retrofit instance = RetrofitClient.getRetrofitInstance();
                ApiCunterierAndProviders api = instance.create(ApiCunterierAndProviders.class);
                Call<ResponseBody> call = api.getProviderFeavo(providerId, "Bearer " + jwt, "ar");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        String message = "";
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            // ResponseBody body = response.body();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                message = jsonObject.getString("message");
                                JSONObject data = jsonObject.getJSONObject("data");
                                int is_Fafo = data.getInt("is_fav");
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                if (is_Fafo == 1) {
                                    imgFavo.setImageResource(R.drawable.favo);
                                } else {
                                    imgFavo.setImageResource(R.drawable.fav2);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().toString());
                                message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnServies.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                btnServies.setBackgroundResource(R.drawable.button_shape_selected);
                btnServies.setTextColor(R.color.colorPrimary);
                btnWorks.setBackgroundResource(R.drawable.button_shap_unselected);
                btnWorks.setTextColor(R.color.colorAccent);
                btnTutorial.setBackgroundResource(R.drawable.button_shap_unselected);
                btnTutorial.setTextColor(R.color.colorAccent);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framServises,new ServiesFragmentNestedRecycler()).commit();
            }
        });
        btnWorks.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                btnWorks.setBackgroundResource(R.drawable.button_shape_selected);
                btnWorks.setTextColor(R.color.colorPrimary);

                btnServies.setBackgroundResource(R.drawable.button_shap_unselected);
                btnServies.setTextColor(R.color.colorAccent);
                btnTutorial.setBackgroundResource(R.drawable.button_shap_unselected);
                btnTutorial.setTextColor(R.color.colorAccent);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framServises,new WorksFragment()).commit();

            }
        });
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                btnServies.setBackgroundResource(R.drawable.button_shap_unselected);
                btnServies.setTextColor(R.color.colorAccent);
                btnTutorial.setBackgroundResource(R.drawable.button_shape_selected);
                btnTutorial.setTextColor(R.color.colorPrimary);
                btnWorks.setBackgroundResource(R.drawable.button_shap_unselected);
                btnWorks.setTextColor(R.color.colorAccent);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framServises,new TutorialFragment()).commit();

            }
        });
        return v;
    }

    @SuppressLint("ResourceAsColor")
    private void SelectedButtonServices(View v) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framServises,new ServiesFragmentNestedRecycler()).commit();
        btnServies.setBackgroundResource(R.drawable.button_shape_selected);
        btnServies.setTextColor(R.color.colorPrimary);


    }

    private void fetchprovideDetails(int providerId, final int numberProfiders) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        final String jwt =sharedPreferences.getString("jwt", null);
        Retrofit instance = RetrofitClient.getRetrofitInstance();
        ApiCunterierAndProviders api = instance.create(ApiCunterierAndProviders.class);
        Call<ModelService> call = api.getproviderdetails(providerId, "en","Bearer "+jwt);
        call.enqueue(new Callback<ModelService>() {
            @Override
            public void onResponse(Call<ModelService> call, Response<ModelService> response) {
                if(response.isSuccessful())
                {
                    ModelService body = response.body();
                    Data data = body.getData();
                    String fullName = data.getFullName();
                    String image = data.getImage();
                    Integer isFav = data.getIsFav();
                    Integer rates = data.getRates();
                    setData(fullName,image,isFav,rates,numberProfiders);

                }else {
                    Toast.makeText(getActivity(), "error provider details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelService> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setData(String fullName, String image, Integer isFav, Integer rates,int numberProfiders) {
        textName.setText(fullName);
        if (image.length() > 0) {
            Picasso.get()
                    .load(image)
                    .error(R.drawable.logo)
                    .into(imageProfile);
        } else {
           imageProfile.setImageResource(R.drawable.logo);
        }
        if(isFav==0)
        {
            imgFavo.setImageResource(R.drawable.fav2);
        }else {
            imgFavo.setImageResource(R.drawable.favo);
        }
        ratingBar.setRating(rates.floatValue());
        textNumberProvider.setText("مستخدم "+numberProfiders);

    }

    private void inatlize(View v) {
        textName=v.findViewById(R.id.textUserName);
        textNumberProvider=v.findViewById(R.id.textNumberOfprovidder);
        imageProfile=v.findViewById(R.id.profile_image);
        imgFavo=v.findViewById(R.id.imageFavoProduct);
        ratingBar=v.findViewById(R.id.ratingBar);
        slider=v.findViewById(R.id.slideShowProduct);
        btnServies=v.findViewById(R.id.btnServices);
        btnTutorial=v.findViewById(R.id.btnTutorial);
        btnWorks=v.findViewById(R.id.btnWorks);
    }

    @Override
    public void onStart() {
        super.onStart();
        setImagseOnSlider();
    }

    private void setImagseOnSlider() {
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
}
