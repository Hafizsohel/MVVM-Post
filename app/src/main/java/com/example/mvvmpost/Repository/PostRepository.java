package com.example.mvvmpost.Repository;

import static com.example.mvvmpost.Network.RetrofitInstance.getRetrofit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.Network.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {
    private final PostDatabase database;
    private Api api;


    private final MutableLiveData<List<PostModel>> mLiveData = new MutableLiveData<>();


    public PostRepository(PostDatabase database) {
        api = getRetrofit().create(Api.class); //Call Api
        this.database = database;
    }

    public LiveData<List<PostModel>> getPost() {
        return mLiveData;
    }

    public void requestGetDatafromServer() {
        Call<List<PostModel>> call = api.getData();
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.isSuccessful()) {
                    database.postDao().insert(response.body());
                    mLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
            }
        });
    }
}
