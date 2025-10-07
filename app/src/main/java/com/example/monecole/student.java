package com.example.monecole;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class student extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button btnLogin;
    private EditText etEmail, etPassword;
    private final Handler handler = new Handler();

    // Variables pour gérer la progression
    private boolean isLoginInProgress = false;
    private int currentProgress = 0;
    private final int TOTAL_DURATION = 3000; // 3 secondes total
    private final int PROGRESS_INTERVAL = 100; // Mise à jour toutes les 100ms
    private Runnable progressRunnable;

    // Clés pour sauvegarder l'état
    private static final String KEY_LOGIN_IN_PROGRESS = "login_in_progress";
    private static final String KEY_CURRENT_PROGRESS = "current_progress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des vues
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Restaurer l'état si disponible
        if (savedInstanceState != null) {
            isLoginInProgress = savedInstanceState.getBoolean(KEY_LOGIN_IN_PROGRESS, false);
            currentProgress = savedInstanceState.getInt(KEY_CURRENT_PROGRESS, 0);

            if (isLoginInProgress) {
                restoreLoginProcess();
            }
        }

        // Gestion des marges système (EdgeToEdge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Action du bouton de connexion
        btnLogin.setOnClickListener(v -> {
            if (validateInputs() && !isLoginInProgress) {
                startLoginProcess();
            }
        });

        // Initialiser le Runnable pour la progression
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (isLoginInProgress) {
                    currentProgress += (PROGRESS_INTERVAL * 100) / TOTAL_DURATION;

                    if (currentProgress >= 100) {
                        currentProgress = 100;
                        completeLogin();
                    } else {
                        progressBar.setProgress(currentProgress);
                        handler.postDelayed(this, PROGRESS_INTERVAL);
                    }
                }
            }
        };
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Sauvegarder l'état de la progression
        outState.putBoolean(KEY_LOGIN_IN_PROGRESS, isLoginInProgress);
        outState.putInt(KEY_CURRENT_PROGRESS, currentProgress);
    }

    private boolean validateInputs() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("L'email est requis");
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Le mot de passe est requis");
            return false;
        }

        return true;
    }

    private void startLoginProcess() {
        isLoginInProgress = true;
        currentProgress = 0;

        // Désactiver le bouton et les champs de saisie
        btnLogin.setEnabled(false);
        etEmail.setEnabled(false);
        etPassword.setEnabled(false);

        // Afficher et initialiser la progress bar
        progressBar.setVisibility(android.view.View.VISIBLE);
        progressBar.setProgress(0);

        // Démarrer la progression
        handler.postDelayed(progressRunnable, PROGRESS_INTERVAL);
    }

    private void restoreLoginProcess() {
        // Restaurer l'interface utilisateur
        btnLogin.setEnabled(false);
        etEmail.setEnabled(false);
        etPassword.setEnabled(false);
        progressBar.setVisibility(android.view.View.VISIBLE);
        progressBar.setProgress(currentProgress);

        // Reprendre la progression si elle n'est pas terminée
        if (currentProgress < 100) {
            handler.postDelayed(progressRunnable, PROGRESS_INTERVAL);
        }
    }

    private void pauseLoginProcess() {
        // Juste arrêter les mises à jour, sans réinitialiser
        handler.removeCallbacks(progressRunnable);
    }

    private void resumeLoginProcess() {
        // Reprendre les mises à jour si la connexion est en cours
        if (isLoginInProgress && currentProgress < 100) {
            handler.postDelayed(progressRunnable, PROGRESS_INTERVAL);
        }
    }

    private void completeLogin() {
        // Finaliser le processus
        isLoginInProgress = false;

        // Cacher la progress bar après un petit délai
        handler.postDelayed(() -> {
            progressBar.setVisibility(android.view.View.GONE);

            // Réactiver les champs
            btnLogin.setEnabled(true);
            etEmail.setEnabled(true);
            etPassword.setEnabled(true);

            // Naviguer vers l'écran d'accueil
            Intent intent = new Intent(student.this, calculmoyenne.class);
            startActivity(intent);
        }, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Mettre en pause la progression sans réinitialiser
        pauseLoginProcess();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reprendre la progression si elle était en cours
        resumeLoginProcess();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Mettre en pause la progression sans réinitialiser
        pauseLoginProcess();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Nettoyer les handlers pour éviter les fuites de mémoire
        handler.removeCallbacks(progressRunnable);
    }
}