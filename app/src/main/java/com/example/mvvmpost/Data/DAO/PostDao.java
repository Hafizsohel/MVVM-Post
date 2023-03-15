package com.example.mvvmpost.Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.mvvmpost.Data.Model.PostModel;
import java.util.List;

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<PostModel> postList);

    @Query("SELECT * from posts ORDER BY id DESC")
    LiveData<List<PostModel>> getAllPosts();

    @Query("DELETE FROM posts")
    void deleteAll();
}
