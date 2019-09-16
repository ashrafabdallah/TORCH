package com.example.torch.api;

import com.app.torch.utils.interceptors.CurlLoggingInterceptor;
import com.example.torch.intersectors.ResponseLoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getRetrofitInstance() {
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        okHttpClient.newBuilder().addInterceptor(new CurlLoggingInterceptor())
//                .addInterceptor(new ResponseLoggingInterceptor());
OkHttpClient.Builder okHttpClient=new OkHttpClient.Builder();
okHttpClient.addInterceptor(new CurlLoggingInterceptor()).addInterceptor(new ResponseLoggingInterceptor());

        String url = "http://torchksa.com/api/";
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url).addConverterFactory(GsonConverterFactory.create());
        builder.client(okHttpClient.build());
        return builder.build();
    }
}
