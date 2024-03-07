package com.example.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected TextView txtTitle;
    protected ProgressBar proBar;

    private Handler monHandler;

    protected ImageButton imageButton;
    private int pourcentageUpp = 0;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proBar = findViewById(R.id.probar);
        txtTitle = findViewById(R.id.txtTitre);
        imageButton = findViewById(R.id.imageButton);

        startProgressBar();


    }

    protected void startProgressBar() {
        pourcentageUpp = 0;
        proBar.setProgress(0);
        monHandler = new Handler(Looper.getMainLooper());

        monHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pourcentageUpp < 100) {
                    pourcentageUpp += 5;
                    proBar.setProgress(pourcentageUpp);
                    monHandler.postDelayed(this, 100);
                } else {
                    Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                }
            }
        }, 100);
    }

}