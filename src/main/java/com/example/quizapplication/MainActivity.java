package com.example.quizapplication;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapplication.CustomAdapter.OnPhotoClickListener;
import com.example.quizapplication.Photo;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnPhotoClickListener {

    private List<Photo> photoList;
    private AnimalsManager animalsManager;
    private CustomAdapter customAdapter;
    private Uri selectedImage;
    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /** Button to start the quiz.
         * adds clicklistener that takes you to quiz activity*/
        Button button = findViewById(R.id.btnStartQuiz);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity2.class);
            startActivity(intent);
        });

        animalsManager = ((MyApplication) getApplicationContext()).getAnimalsManager();
        photoList = animalsManager.getAnimalList();

        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        customAdapter = new CustomAdapter(photoList,  this);
        recyclerView.setAdapter(customAdapter);

        /* text input from user*/
        EditText editText = findViewById(R.id.edit_text);
        Button buttonSubmit = findViewById(R.id.button_submit);

        /* image input from user*/
        Button pickImage = findViewById(R.id.imageUpload);
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            selectedImage = uri;

                        }
                    }
                }
        );

        pickImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                imagePickerLauncher.launch("image/*");
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editText.getText().toString().trim();
                if(selectedImage !=null && !userInput.isEmpty()) {
                    String cap = userInput.substring(0,1).toUpperCase() + userInput.substring(1);
                    //Photo newPhoto = new Photo(userInput, selectedImage);
                    animalsManager.addAnimal(cap,selectedImage);
                    customAdapter.notifyItemInserted(photoList.size() - 1);
                }
            }
        });

        /* sort the list in recycleView*/
        Button buttonSortAZ = findViewById(R.id.btnSortAZ);
        Button buttonSortZA = findViewById(R.id.btnSortZA);

        buttonSortAZ.setOnClickListener(v -> {
            Collections.sort(photoList, Comparator.comparing(Photo::getName));
            customAdapter.notifyDataSetChanged();
        });
        buttonSortZA.setOnClickListener(v -> {
            Collections.sort(photoList, (p1, p2) -> p2.getName().compareToIgnoreCase(p1.getName()));
            customAdapter.notifyDataSetChanged();
        });


    }

    public void saveNewImage(Uri imageUri, String name) {
        if (imageUri != null && !name.isEmpty()) {
            animalsManager.addUserPhoto(name, imageUri); // ✅ Add to shared list
            Toast.makeText(this, "Image added to quiz!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * uses the interface in CustomAdapter that handles click on image.
     * deletes the image from the list
     */
    @Override
    public void onPhotoClick(Photo photo) {
        Toast.makeText(this,"Clicked: " + photo.getName(), Toast.LENGTH_SHORT).show();
        animalsManager.deleteItemFromList(photo);
        customAdapter.notifyDataSetChanged();
        // can use notifyItemRemoved to update only the deleted item
    }


}