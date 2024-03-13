package com.example.loginandregister;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MonEspace extends AppCompatActivity {

    protected TextView id, login, email, password;
    protected ImageView imgView;
    protected Button btnLogout;
    private SessionManager sessionManager; // Déclarez un nouveau champ sessionManager

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_espace);

        // Initialisez le sessionManager
        sessionManager = new SessionManager(this);

        id = findViewById(R.id.id);
        login= findViewById(R.id.login);
        email = findViewById(R.id.email);
        btnLogout = findViewById(R.id.btnLoggout);

        String emailValue = sessionManager.getEmail();
        String loginValue = sessionManager.getLogin();
        String idValue = sessionManager.getId(); // La valeur par défaut peut être définie ici

        id.setText("Votre ID: " + idValue);
        login.setText("Votre login: " + loginValue);
        email.setText("Votre email: " + emailValue);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                Log.d("intent", "intent: " + i);
                Toast.makeText(MonEspace.this, "Vous etes déconnecter", Toast.LENGTH_SHORT).show();
                sessionManager.logout();
                startActivity(i);
                finish();
                Log.d("session", "session: " + sessionManager);

            }
        });

    }
}
