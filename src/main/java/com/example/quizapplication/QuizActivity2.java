package com.example.quizapplication;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity2 extends AppCompatActivity {

    private List<String> randomWords;
    private List<Photo> photoList;
    private int currentIndex = 0;
    private int score = 0;
    private int attempts = 0;
    private TextView scoreText;
    private TextView feedbackTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_quiz2);

        FragmentContainerView fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer == null) {
            Log.e("QuizActivity2", "ERROR: fragment_container is NULL! Check activity_quiz2.xml");
            return; // Prevent crash
        } else {
            Log.d("QuizActivity2", "fragment_container found, loading fragment.");
        }

     if(savedInstanceState == null){
         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuizFragment()).commit();
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("CURRENT_INDEX", currentIndex);
        outState.putInt("SCORE", score);

    }

}
