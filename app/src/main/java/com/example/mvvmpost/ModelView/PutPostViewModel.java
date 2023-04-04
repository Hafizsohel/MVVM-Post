package com.example.mvvmpost.ModelView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Repository.PutRepository;

public class PutPostViewModel extends AndroidViewModel {
    private PutRepository pRepository;
    private PostDatabase database;

    public PutPostViewModel(@NonNull Application application) {
        super(application);
        database = PostDatabase.getInstance(application);
        pRepository = new PutRepository(application, database);
    }
    public LiveData<PostModel> putPosts() {
        return pRepository.updateData();
    }
    public void requestPut(PostModel postModel) {
        pRepository.UpdatePosts(getApplication(),postModel);
    }
}
