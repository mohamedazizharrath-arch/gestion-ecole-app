package com.example.monecole;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class Acceuil extends AppCompatActivity {

    private CardView cardStudent, cardTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        initializeViews();
        setupSystemBars();
        setupClickListeners();
    }

    private void initializeViews() {
        cardStudent = findViewById(R.id.cardStudent);
        cardTeacher = findViewById(R.id.cardTeacher);
    }

    private void setupSystemBars() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.overlay), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupClickListeners() {
        // Carte Étudiant
        cardStudent.setOnClickListener(v -> {
            navigateToLogin("student");
        });

        // Carte Enseignant
        cardTeacher.setOnClickListener(v -> {
            navigateToLogin("teacher");
        });
    }

    private void navigateToLogin(String userType) {
        Intent intent = new Intent(Acceuil.this, student.class);
        intent.putExtra("user_type", userType);
        startActivity(intent);
    }}