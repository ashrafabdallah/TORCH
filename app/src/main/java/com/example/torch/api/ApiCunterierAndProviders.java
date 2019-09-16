package com.example.torch.api;

import com.example.torch.model.cityAndCuntry.ModelCityAndcountry;
import com.example.torch.model.model_Tutorial.ModelTutorial;
import com.example.torch.model.providerdetailes.ProviderDetailes;
import com.example.torch.model.service.ModelService;
import com.example.torch.model.works_model.ModelWorks;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiCunterierAndProviders {

    @GET("country/all")
    Call<ModelCityAndcountry>getCuntry(@Header("lang")String lan);
    @GET("client/provider/{id}/services")
    Call<ModelService>getproviderdetails(@Path("id")int id,@Header("lang")String lang,@Header("Authorization")String Authorization);
    @GET("client/toggle_fav_provider/{id}")
    Call<ResponseBody>getProviderFeavo(@Path("id")int id,@Header("Authorization")String Authorization,@Header("lang")String lang);


    @GET("client/providers/country/{id}/city/{id}")
    Call<ProviderDetailes>getProvider(@Header("lang")String lang,@Path("id")int countryId,@Path("id")String cityId);

    @GET("client/provider/{id}/works")
    Call<ModelWorks>getWorks(@Path("id")int id,@Header("Authorization")String Authorization,@Header("lang")String lang);

    @GET("client/provider/{id}/tutorials")
    Call<ModelTutorial>getTutorial(@Path("id")int id, @Header("Authorization")String Authorization, @Header("lang")String lang);

}
