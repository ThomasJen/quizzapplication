package com.example.quizapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PhotoDAO {

    @Insert
    void insert(PhotoEntity photo);

    @Query("SELECT * FROM photos")
    LiveData<List<PhotoEntity>> getAllPhotos();

}
