package com.example.monecole;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Charger le layout

        // Récupérer le bouton
        Button btnStart = findViewById(R.id.btnStart);

        // Gestion des marges système (EdgeToEdge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Action du bouton
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,Acceuil.class); // Login.class
            startActivity(intent);
        });
    }
}

