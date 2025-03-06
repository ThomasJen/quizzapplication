package com.example.quizapplication;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.quizapplication.MainActivity;
public class AnimalsManager {
    private final List<Photo> photoList;
    private static AnimalsManager instance;

    public AnimalsManager(List<Photo> photoList) {
        this.photoList = photoList;
        initializeDefaultAnimals();
    }

    public void initializeDefaultAnimals() {
        // Legg til standarddyr
        if(photoList.isEmpty()) {
            photoList.add(new Photo("Tiger", R.drawable.tiger));
            photoList.add(new Photo("Rev", R.drawable.rev));
            photoList.add(new Photo("Gorilla", R.drawable.gorilla));
            photoList.add(new Photo("Sjiraff", R.drawable.sjiraff));
        }
    }

    public List<Photo> getAnimalList() {
        return photoList;
    }

    public void addAnimal(String name, int imageResId) {
        photoList.add(new Photo(name, imageResId));
    }

    public void addUserPhoto(String name, Uri imageUri) {
        photoList.add(new Photo(name, imageUri));
    }

    public void addAnimal(String name, Uri imageUri) {
        photoList.add(new Photo(name,imageUri));
    }

    public void shuffleAnimals() {
        Collections.shuffle(photoList);
    }
    public void deleteItemFromList(Photo photo){
        photoList.remove(photo);
    }
}