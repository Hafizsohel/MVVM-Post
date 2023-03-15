package com.example.mvvmpost.ModelView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.Repository.CreatePostRepository;

public class    CreatePostViewModel extends AndroidViewModel {
    private CreatePostRepository cPostRepository;
    private PostDatabase database;

    public CreatePostViewModel(@NonNull Application application) {
        super(application);
        database = PostDatabase.getInstance(application);
        cPostRepository= new CreatePostRepository(application,database);
    }

    public LiveData<PostModel> CreatePostData() {
        return cPostRepository.createPost();
    }

    public void requestPost(PostModel postModel) {
        cPostRepository.requestPostDataFromServer(getApplication(),postModel);
    }
}
