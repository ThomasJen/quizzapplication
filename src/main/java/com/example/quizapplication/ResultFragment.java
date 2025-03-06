package com.example.quizapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ResultFragment extends Fragment {

    private TextView resultsText;
    private Button btnRestart, btnMainMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize UI components
        resultsText = view.findViewById(R.id.resultsText);
        btnRestart = view.findViewById(R.id.btnRestart);
        btnMainMenu = view.findViewById(R.id.btnMainMenu);
        // Get final score from arguments
        Bundle args = getArguments();
        if (args != null) {
            int finalScore = args.getInt("finalScore", 0);
            int totalQuestions = args.getInt("totalQuestions", 0);
            resultsText.setText("Your Score: " + finalScore + " / " + totalQuestions);
        }
        // Restart the quiz
        btnRestart.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new QuizFragment())
                    .commit();
        });
        // Go back to main menu
        btnMainMenu.setOnClickListener(v -> {
            requireActivity().finish(); // Ends the activity and returns to main menu
        });
    }
}