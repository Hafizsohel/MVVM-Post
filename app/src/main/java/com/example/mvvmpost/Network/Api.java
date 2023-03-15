package com.example.mvvmpost.Network;

import com.example.mvvmpost.Data.Model.PostModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface Api {
    @GET("posts")
    Call<List<PostModel>> getData();

    @POST("posts")
    Call<PostModel> createPost(@Body PostModel postModel);
}
