package com.example.quizapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.quizapplication.AnimalsManager;
import com.example.quizapplication.Photo;
import com.example.quizapplication.ResultFragment;


public class QuizFragment extends Fragment {

    private List<String> randomWords;
    private List<Photo> photoList;
    private int currentIndex = 0;
    private int score = 0;
    private int attempts = 0;
    private TextView scoreText;
    private TextView feedbackTextView;
    private ImageView imageView;
    private Button button1, button2, button3, buttonNext, btnBackToMain;


    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        QuizViewModel quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        quizViewModel.getPhotoList().observe(getViewLifecycleOwner(), photos -> {
            if(photos.size() < 0) {
                displayNextQuestion(photos);
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find Views
        scoreText = view.findViewById(R.id.scoreText);
        imageView = view.findViewById(R.id.imageview);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        buttonNext = view.findViewById(R.id.buttonNext);
        btnBackToMain = view.findViewById(R.id.btnBackToMain);

        AnimalsManager animalsManager = ((MyApplication) requireActivity().getApplication()).getAnimalsManager();
        photoList = animalsManager.getAnimalList();
        animalsManager.shuffleAnimals();

        randomWords = Arrays.asList("Ku", "Sau", "Okse", "Katt", "Hund");

        displayNextQuestion();

        buttonNext.setOnClickListener(v -> displayNextQuestion());
        btnBackToMain.setOnClickListener(v -> requireActivity().finish());
        // Set up quiz logic here (e.g., loading a random question)
    }
    private void displayNextQuestion(List<PhotoEntity> photoList) {
        // Example: Setting an image and options
        Photo currentPhoto = null;
        if (currentIndex < photoList.size()) {
            currentPhoto = photoList.get(currentIndex);
            currentIndex++;

            if (currentPhoto.getImageResId() != null && currentPhoto.getImageResId() != 0) {
                imageView.setImageResource(currentPhoto.getImageResId());
            } else {
                imageView.setImageURI(currentPhoto.getImageUri());
            }
            randomizeButtons(currentPhoto);
        } else {
            showResults();
        }
    }

    public void randomizeButtons(Photo correctPhoto) {
        List<Button> buttons = Arrays.asList(button1, button2, button3);
        Collections.shuffle(buttons);

        // Set correct answer
        buttons.get(0).setText(correctPhoto.getName());
        buttons.get(0).setOnClickListener(v -> {
            score++; // ✅ Increase score when correct answer is selected
            scoreText.setText("Score: " + score);
            Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
            displayNextQuestion();
        });

        // Get wrong answers from available names
        List<String> fakeAnswers = new ArrayList<>(Arrays.asList("Rev", "Katt", "Ku", "Okse"));
        fakeAnswers.remove(correctPhoto.getName()); // Remove correct answer
        Collections.shuffle(fakeAnswers);

        for (int i = 1; i < buttons.size(); i++) {
            buttons.get(i).setText(fakeAnswers.get(i - 1));
            buttons.get(i).setOnClickListener(v -> {
                Toast.makeText(getContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                displayNextQuestion(); // Move to next question even if wrong
            });
        }
    }

    public void updateScore() {
        score++;
        attempts++;
        scoreText.setText("Score: " + score);
        showFeedback(true);
    }
    public void updateScoreMinusOnePoints() {
        score = Math.max(0, score - 1);
        attempts++;
        scoreText.setText("Score: " + score);
        showFeedback(false);
    }

    public void endQuiz() {
        Toast.makeText(getContext(), "Quiz Finished! Score: " + score, Toast.LENGTH_LONG).show();
    }

    private void showFeedback(boolean isCorrect) {
        if (feedbackTextView == null) {
            Log.e("QuizFragment", "feedbackTextView is NULL in showFeedback!");
            return; // Prevent crash
        }
        feedbackTextView.setVisibility(View.VISIBLE);
        feedbackTextView.setText(isCorrect ? "Correct!" : "Wrong!");
        feedbackTextView.postDelayed(() -> feedbackTextView.setVisibility(View.GONE), 1000);
    }

    private void showResults() {
        ResultFragment resultsFragment = new ResultFragment();

        // Send score data to ResultsFragment
        Bundle args = new Bundle();
        args.putInt("finalScore", score);
        args.putInt("totalQuestions", photoList.size());
        resultsFragment.setArguments(args);

        // Load ResultsFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, resultsFragment)
                .commit();
    }
}
