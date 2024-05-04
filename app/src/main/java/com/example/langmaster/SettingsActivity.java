package com.example.langmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnGlowna = findViewById(R.id.btn_Glowna);
        btnGlowna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome();
            }
        });

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String login = prefs.getString("Login", "N/A");
        String imie = prefs.getString("Imie", "N/A");
        String email = prefs.getString("Email", "N/A");

        TextView loginView = findViewById(R.id.loginVIew);
        TextView imieView = findViewById(R.id.imieView);
        TextView emailView = findViewById(R.id.emailView);

        loginView.setText(login);
        imieView.setText(imie);
        emailView.setText(email);
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}