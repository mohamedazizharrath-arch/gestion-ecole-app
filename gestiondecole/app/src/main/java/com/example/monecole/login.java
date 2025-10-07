package com.example.monecole; // mets ton vrai package

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.equals("admin@ecole.com") && password.equals("1234")) {
                // Connexion réussie → aller au Dashboard
                Toast.makeText(LoginActivity.this, "Connexion réussie 🎉", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish(); // fermer l'écran de login
            } else {
                // Erreur
                Toast.makeText(LoginActivity.this, "Email ou mot de passe incorrect ❌", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
