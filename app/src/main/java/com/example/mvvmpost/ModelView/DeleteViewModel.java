package com.example.mvvmpost.ModelView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Repository.DeleteRepository;

public class DeleteViewModel extends AndroidViewModel {
    private static final String TAG = "DeleteViewModel";

    private DeleteRepository dRepository;
    private PostDatabase database;

    public DeleteViewModel(@NonNull Application application) {
        super(application);
        database = PostDatabase.getInstance(application);
        dRepository = new DeleteRepository(application, database);
    }

    public LiveData<PostModel> deletePosts() {
        return dRepository.deleteData();
    }


    public void requestDelete(PostModel postModel) {
        dRepository.deletePost(getApplication(), postModel);
    }
}
