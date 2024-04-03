package com.example.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    protected Button btnRegister, btnLogin;
    protected TextInputLayout editTxtLogin, editTxtPassword;
    protected RequestQueue queue;
    protected MyRequest request;
    private ProgressBar pgBar;
    private SessionManager sessionManager;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^"+
            "(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#!$%^&+=])" +
            "(?=\\S+$)" +
            ".{6,20}" +
            "$");
    private String strLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        editTxtLogin = findViewById(R.id.editTxtLogin);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        pgBar = findViewById(R.id.progressBar);

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        sessionManager = new SessionManager(this);
        if (sessionManager.isLogged()) {
            Intent i = new Intent(getApplicationContext(), MonEspace.class);
            startActivity(i);
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validLogin();
                validPassword();

                if (!validLogin() || !validPassword())
                    return;
                pgBar.setVisibility(View.VISIBLE);
                final String LOGIN = editTxtLogin.getText().toString().trim();
                Log.d("LOGIN", "Le login: " + LOGIN);
                final String PASSWORD = editTxtPassword.getText().toString().trim();
                Log.d("PASSWORD", "Le password: " + PASSWORD);

                Log.d("request", "request: " + request);
                request.login(LOGIN, PASSWORD, new MyRequest.RetoursPHP() {


                    @Override
                    public void toutOK(JSONObject js, String message) throws JSONException {
                        Log.d("PHP", "messagePHP" + message);
                        JSONObject user = js.getJSONObject("user");
                        String email = user.getString("login");
                        String login = user.getString("pseudo");
                        String id = user.getString("id");


                        sessionManager.insertUser(id, login, email);
                        Intent intentLogin = new Intent(getApplicationContext(), MonEspace.class);
//                            intentLogin.putExtra("email", email);
//                            intentLogin.putExtra("pseudo", pseudo);
//                            intentLogin.putExtra("id", id);
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                        startActivity(intentLogin);
                        finish();

                        // Affichage des informations de l'utilisateur dans la console
                        Log.d("User Info", "ID: " + id + ", Pseudo: " + login + ", Email: " + email);

                    }

                    @Override
                    public void pasOK(String message) {
                        Log.d("PHP", "passage dans PAS OK de Login Activity");
                        Toast.makeText(LoginActivity.this, "Attention" + message, Toast.LENGTH_LONG).show();
                        return;
                    }

                    @Override
                    public void systemError(String message) {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}