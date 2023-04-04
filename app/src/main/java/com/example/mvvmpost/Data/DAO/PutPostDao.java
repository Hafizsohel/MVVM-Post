package com.example.mvvmpost.Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvmpost.Data.Model.PostModel;

import java.util.List;

@Dao
public interface PutPostDao {
    @Update
    void updatePosts(PostModel postModel);
    @Query("DELETE FROM posts")
    void deleteAll();
}
