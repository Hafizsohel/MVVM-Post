package com.example.mvvmpost.Data.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.mvvmpost.Data.Model.PostModel;

@Dao
public interface DeleteDao {
    @Delete
    void deletePost(PostModel postModel);

    @Query("DELETE FROM posts")
    void deleteAllPosts();
}
