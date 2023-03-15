package com.example.mvvmpost.ModelView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.Network.Api;
import com.example.mvvmpost.Repository.PostRepository;

import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private PostRepository repository;
    private PostDatabase database;
    private Api api;

    public MutableLiveData<List<PostModel>> mutableLiveData = new MutableLiveData<>();

    public PostViewModel(@NonNull Application application) {
        super(application);
        database = PostDatabase.getInstance(application);
        repository = new PostRepository(database);
    }


    public LiveData<List<PostModel>> GetData() {
        return repository.getPost();
    }

    public void requestDataServer() {
        repository.requestGetDatafromServer();
    }

    public LiveData<List<PostModel>> getAllData() {
        return database.postDao().getAllPosts();
    }


}
