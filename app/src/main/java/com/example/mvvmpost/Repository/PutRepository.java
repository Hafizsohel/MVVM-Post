package com.example.mvvmpost.Repository;

import static com.example.mvvmpost.Network.RetrofitInstance.getRetrofit;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmpost.Data.DAO.PutPostDao;
import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Network.Api;
import com.example.mvvmpost.Network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PutRepository {
    private static final String TAG = "PutRepository";

    private final PostDatabase database;
    private Api api;

    private final MutableLiveData<PostModel> mLiveData = new MutableLiveData<>();

    public PutRepository(Context context, PostDatabase database) {
        api = getRetrofit().create(Api.class);
        this.database = database;
    }

    public LiveData<PostModel> updateData() {
        return mLiveData;
    }

    public void UpdatePosts(Context context, PostModel postModel) {
        Call<PostModel> call = api.updatePost(postModel.getId(), postModel); // create post on api
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()) {
                    database.putPostDao().updatePosts(response.body()); //Store into the room database
                    mLiveData.postValue(response.body()); // post with internet access
                   //Log.d(TAG, "onResponse:Update Successfully! ");

                }
            }

            @Override
            public void onFailure( Call<PostModel> call, Throwable t) {
               // Toast.makeText(context, "Update Failed!", Toast.LENGTH_SHORT).show();
            }

        });
    }
    }

