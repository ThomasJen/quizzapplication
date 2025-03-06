package com.example.quizapplication;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimalsManager {
    private List<Photo> animalList;
    private static AnimalsManager instance;

    public AnimalsManager() {
        animalList = new ArrayList<>();
        initializeDefaultAnimals();
    }

    public static AnimalsManager getInstance() {
        if (instance == null) {
            instance = new AnimalsManager();
        }
        return instance;
    }
    public void initializeDefaultAnimals() {
        // Legg til standarddyr
        animalList.add(new Photo("Tiger", R.drawable.tiger));
        animalList.add(new Photo("Rev", R.drawable.rev));
        animalList.add(new Photo("Gorilla", R.drawable.gorilla));

    }

    public List<Photo> getAnimalList() {
        return animalList;
    }

    public void addAnimal(String name, int imageResId) {
        animalList.add(new Photo(name, imageResId));
    }

    public void addAnimal(String name, Uri imageUri) {
        animalList.add(new Photo(name,imageUri));
    }

    public void shuffleAnimals() {
        Collections.shuffle(animalList);
    }
    public void deleteItemFromList(Photo photo){
        animalList.remove(photo);
    }
}