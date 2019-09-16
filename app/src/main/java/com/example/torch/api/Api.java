package com.example.torch.api;

import com.example.torch.api.modelCatogries.ModelProductCategories;
import com.example.torch.model.Ask_Reset;
import com.example.torch.model.DataIMianImages;
import com.example.torch.model.LoginModel;
import com.example.torch.model.ModelFavorits.ModelFavoritProviders;
import com.example.torch.model.OfferModel.OfferData;
import com.example.torch.model.ProductDetailes.ProductDetailsModel;
import com.example.torch.model.ProviderProductsByid.Modelproviderproducts;
import com.example.torch.model.Registermodel;
import com.example.torch.model.homeModel.HomeData;
import com.example.torch.model.modelBag.ModelMyBag;
import com.example.torch.model.modelContactus.DataContact;
import com.example.torch.model.modelFilter.ModelFilterProducts;
import com.example.torch.model.modeladdAddress.ModelAddAddress;
import com.example.torch.model.modelreservation.ModelReservation;
import com.example.torch.model.updateprofile.ModelUpdateProfile;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {
    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> getLogin(@Field("mobile") String mob, @Field("password") String pass, @Field("fcm_token") String token);

    @POST("register")
    Call<ResponseBody> createuser(@Body Registermodel body, @Header("lang") String lan, @Header("os") String os);

    @FormUrlEncoded
    @POST("auth/ask_reset")
    Call<ResponseBody> SendMobile(@Field("mobile") String mobile);


    @POST("auth/activate")
    Call<LoginModel> auth_activate(@Body Ask_Reset body);

    @FormUrlEncoded
    @POST("auth/resend_code")
    Call<ResponseBody> resendCode(@Field("mobile") String mobile, @Field("type") String type);

    @FormUrlEncoded
    @POST("auth/do_reset")
    Call<ResponseBody> restPassword(@Field("password") String password, @Header("Authorization") String Authorization);

    @POST("client/main/images")
    Call<DataIMianImages> getMainImages(@Header("lang") String lang);


    @POST("client/home")
    Call<HomeData> getHomeData(@Header("lang") String lang);

    @POST("client/offers/all")
    Call<OfferData> getAllOffers(@Header("lang") String lang, @Header("Authorization") String Authorization);

    @GET("client/toggle_fav_product/{id}")
    Call<ResponseBody> getFavoAndRating(@Header("Authorization") String Authorization, @Header("lang") String lan, @Path("id") int id);

    @GET("client/offer/{id}")
    Call<ProductDetailsModel> getAllProductDetails(@Header("Authorization") String Authorization, @Header("lang") String lan, @Path("id") int id);

    @GET("client/add/product/{id}/bag")
    Call<ResponseBody> AddProductToBag(@Header("Authorization") String Authorization, @Header("lang") String lan, @Path("id") int id);

    @GET("client/my/bag")
    Call<ModelMyBag> getAllBag(@Header("Authorization") String Authorization, @Header("lang") String lan);

    @GET("client/delete/{id}/bag")
    Call<ResponseBody> DeleteProductFromBag(@Header("Authorization") String Authorization, @Header("lang") String lan, @Path("id") int id);

    @FormUrlEncoded
    @POST("client/update/{id}/bag")
    Call<ResponseBody> UpdateQuantityofProduct(@Field("quantity") int quantity, @Header("Authorization") String Authorization, @Header("lang") String lan, @Path("id") int id);

    @GET("client/favorite/products")
    Call<OfferData> getProductFavo(@Header("lang") String lang, @Header("Authorization") String Authorization);

    @GET("client/favorite/providers")
    Call<ModelFavoritProviders> getProviderFavo(@Header("lang") String lang, @Header("Authorization") String Authorization);

    @GET("client/toggle_fav_provider/{id}")
    Call<ResponseBody> getFavoProvider(@Header("Authorization") String Authorization, @Header("lang") String lan, @Path("id") int id);

    @GET("client/provider/{id}/product/categories")
    Call<ModelProductCategories> getAllCategories(@Header("Authorization") String Authorization, @Header("lang") String lan, @Path("id") int id);

    @FormUrlEncoded
    @POST("client/provider/{id}/products")
    Call<Modelproviderproducts> getproviderproducts(@Field("category_id") int category_id, @Path("id") int providerId, @Header("lang") String lang, @Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST("client/provider/{id}/category/{id}/filter")
    Call<ModelFilterProducts> FilterProduct(@Field("filter_by") String filter_by, @Path("id") int providerId, @Path("id") int category_id, @Header("lang") String lang);

    @GET("client/my/profile")
    Call<LoginModel> getProfileDetailes(@Header("Authorization") String Authorization, @Header("lang") String lan);

    @Multipart
    @POST("client/update/profile")
    Call<ModelUpdateProfile> UpdateProfile(@Part List<MultipartBody.Part> Profile, @Header("lang") String lan, @Header("Authorization") String Authorization);

    @GET("client/my/orders/pending")
    Call<ModelReservation>getPendingReservation(@Header("Authorization") String Authorization, @Header("lang") String lan);

    @GET("client/my/orders/done")
    Call<ModelReservation>getDoneReservation(@Header("Authorization") String Authorization, @Header("lang") String lan);

    @GET("settings/terms")
    Call<ResponseBody>getTerms(@Header("lang")String lang);
    @GET("settings/policy")
    Call<ResponseBody>getPolicy(@Header("lang")String lang);
    @GET("settings/about")
    Call<ResponseBody>getabout(@Header("lang")String lang);
    @POST("contact")
    Call<ResponseBody>ContactUs(@Body DataContact dataContact,@Header("lang")String lang);

    @POST("client/add/address")
    Call<ResponseBody>setAddress(@Body ModelAddAddress modelAddAddress,@Header("Authorization") String Authorization, @Header("lang") String lan);
}

