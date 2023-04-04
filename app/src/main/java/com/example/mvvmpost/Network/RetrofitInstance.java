package com.example.mvvmpost.Network;

import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.View.adapter.PostAdapter;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private PostModel postModel;
    //private Api api;

    private static final String URL_DATA = "https://jsonplaceholder.typicode.com/";
    public static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(URL_DATA)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
    public Api api=retrofit.create(Api.class);
}
