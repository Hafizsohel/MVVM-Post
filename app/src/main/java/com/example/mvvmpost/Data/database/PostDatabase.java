package com.example.mvvmpost.Data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mvvmpost.Data.DAO.CreatePostDao;
import com.example.mvvmpost.Data.DAO.DeleteDao;
import com.example.mvvmpost.Data.DAO.PostDao;
import com.example.mvvmpost.Data.DAO.PutPostDao;
import com.example.mvvmpost.Data.Model.PostModel;

@Database(entities = {PostModel.class}, version = 1, exportSchema = false)
public abstract class PostDatabase extends RoomDatabase {

    private static String DATABASE_NAME = "PostDatabase";
    private static volatile PostDatabase INSTANCE;

    public static PostDatabase getInstance(Context context) {
        synchronized (PostDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = (PostDatabase) Room.databaseBuilder((Context) context.getApplicationContext(),
                                PostDatabase.class, (String) DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
            PostDatabase database = INSTANCE;
            return database;
        }
    }

    public abstract PostDao postDao();
    public abstract CreatePostDao createPostDao();
    public abstract PutPostDao putPostDao();
    public abstract DeleteDao deleteDao();

}
