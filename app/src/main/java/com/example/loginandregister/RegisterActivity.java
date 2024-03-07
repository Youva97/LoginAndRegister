package com.example.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    protected EditText editTxtPasswordConfirm, editTxtPassword, editTxtLogin, editTxtMail;

    protected RequestQueue queue;
    protected MyRequest request;

    protected Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTxtLogin = findViewById(R.id.editTxtLogin);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        editTxtMail = findViewById(R.id.editTxtMail);
        editTxtPasswordConfirm = findViewById(R.id.editTxtPasswordConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String LOGIN = editTxtLogin.getText().toString().trim();
                Log.d("LOGIN", "Le login: " + LOGIN);
                final String EMAIL = editTxtMail.getText().toString().trim();
                Log.d("EMAIL", "Le email: " + EMAIL);
                final String PASSWORD = editTxtPassword.getText().toString().trim();
                Log.d("password", "Le password: " + PASSWORD);
                final String PASSWORD2 = editTxtPasswordConfirm.getText().toString().trim();
                Log.d("password2", "Le password2: " + PASSWORD2);

                request.register(LOGIN, EMAIL, PASSWORD, PASSWORD2, new MyRequest.RetoursPHP() {
                    @Override
                    public void toutOK(JSONObject js, String message) {
                        Log.d("PHP", "messagePHP" + message);
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }

                    @Override
                    public void pasOK(String message) {
                        Log.d("PHP", "passage dans PAS OK de register Activity");
                        Toast.makeText(RegisterActivity.this, "Attention" + message, Toast.LENGTH_LONG).show();
                        return;
                    }

                    @Override
                    public void systemError(String message) {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}