package com.example.mvvmpost.Repository;

import static com.example.mvvmpost.Network.RetrofitInstance.getRetrofit;

import android.content.Context;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.Network.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostRepository {
    private final PostDatabase database;
    private Api api;

    private final MutableLiveData<PostModel> MliveData = new MutableLiveData<>();

    public CreatePostRepository(Context context, PostDatabase database) {
        api = getRetrofit().create(Api.class);
        this.database = database;
    }

    public LiveData<PostModel> createPost() {
        return MliveData;
    }

    public void requestPostDataFromServer(Context context, PostModel postModel) {
        Call<PostModel> call = api.createPost(postModel); // create post on api
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()) {
                    database.createPostDao().create(response.body()); //Store into the room database
                    MliveData.postValue(response.body()); // post with internet access
                    Toast.makeText(context, "post successful: " + response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure( Call<PostModel> call, Throwable t) {
                Toast.makeText(context, "something went wrong...", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
