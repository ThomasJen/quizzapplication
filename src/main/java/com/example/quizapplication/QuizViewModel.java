package com.example.quizapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuizViewModel extends AndroidViewModel {
    private final PhotoDAO photoDao;
    private final LiveData<List<PhotoEntity>> photoList;

    public int currentIndex = 0;
    public int score = 0;

    public QuizViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        photoDao = db.photoDAO();
        photoList = photoDao.getAllPhotos();
    }

    public LiveData<List<PhotoEntity>> getPhotoList() {
        return photoList;
    }

    public void addPhoto(String name, String imageUri) {
        new Thread(() -> {
            photoDao.insert(new PhotoEntity(name, imageUri));
        }).start();

    }
}
