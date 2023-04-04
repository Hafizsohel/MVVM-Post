package com.example.mvvmpost.Network;

import androidx.room.Delete;

import com.example.mvvmpost.Data.Model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface Api {
    @GET("posts")
    Call<List<PostModel>> getData();

    @POST("posts")
    Call<PostModel> createPost(@Body PostModel postModel);

    @PUT("posts/{id}")
    Call<PostModel> updatePost(
            @Path("id") int id,
            @Body PostModel postModel
    );

    @DELETE("posts/{id}")
    Call<PostModel> deletePost(@Path("id") int id);

}
