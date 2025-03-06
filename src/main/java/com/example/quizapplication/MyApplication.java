package com.example.quizapplication;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/*
 * holds a single instance of animalsmanager
 * ensures all activities share the same animalmanger
 * all activities access the same animalsmanager instance
 * if list is modified in mainActivity, changes automatically appear in Quizactivity2, or any other activity
 * data is lost when the app is closed */
public class MyApplication extends Application {
    private AnimalsManager animalsManager;

    private final List<Photo> photoList = new ArrayList<>();
    //*
    // creates instance of Animalmanager that persiststhrough the app lifecycle*/
    @Override
    public void onCreate() {
        super.onCreate();
        animalsManager = new AnimalsManager(photoList);
        animalsManager.initializeDefaultAnimals();
    }
    /*allows acticities to access Animalmanager*/
    public AnimalsManager getAnimalsManager() {
        return animalsManager;
    }


}
