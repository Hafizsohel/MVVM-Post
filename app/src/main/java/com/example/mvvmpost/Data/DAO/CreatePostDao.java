package com.example.mvvmpost.Data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.mvvmpost.Data.Model.PostModel;
import java.util.List;

@Dao
public interface CreatePostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(PostModel postModel);

    @Query("SELECT * FROM posts")
    List<PostModel> getAllData();

}
