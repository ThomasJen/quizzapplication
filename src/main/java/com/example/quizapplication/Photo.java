package com.example.quizapplication;

import android.net.Uri;
import java.io.Serializable;

public class Photo{
    private String name;
    private Integer imageResId;
    private Uri imageUri;


    public Photo(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
        this.imageUri = null;

    }

    /**
     * Constructor for user uploaded images*/
    public Photo(String name, Uri imageUri){
        this.name = name;
        this.imageResId = null;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public Uri getImageUri() {
        return imageUri;
    }

}