package com.example.mvvmpost.Repository;

import static com.example.mvvmpost.Network.RetrofitInstance.getRetrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Network.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteRepository {
    private static final String TAG = "DeleteRepository";

    private final Api api;
    private final PostDatabase database;
    private final MutableLiveData<PostModel> dLiveData = new MutableLiveData<>();
    public DeleteRepository(Context context, PostDatabase database) {
        api = getRetrofit().create(Api.class);
        this.database = database;
    }
    public LiveData<PostModel> deleteData() {
        return dLiveData;
    }

    public void deletePost(Context context, PostModel postModel) {
        Call<PostModel> call = api.deletePost(postModel.getId());
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()) {
                    database.deleteDao().deletePost(postModel); //Delete post from the room database
                   // dLiveData.postValue(postModel); //
                    Log.d(TAG, "onResponse:Delete Successfully! ");
                } else {
                    dLiveData.postValue(postModel);
                    Log.d(TAG, "onResponse:Delete Failed! ");
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
               // dLiveData.postValue(postModel); //
                Log.d(TAG, "onFailure:Delete Failed! ");
            }
        });
    }
}
