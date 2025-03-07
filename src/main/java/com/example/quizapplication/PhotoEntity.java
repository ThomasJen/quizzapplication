package com.example.quizapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.net.Uri;

@Entity(tableName = "photos")
public class PhotoEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String imageUrl;

    public PhotoEntity(String name, String imageUrl){
        this.name = name;
        this.imageUrl = imageUrl;
    }

}
