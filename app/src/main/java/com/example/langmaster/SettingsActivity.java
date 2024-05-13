package com.example.langmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

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

        Button btnAngielski = findViewById(R.id.btn_Angielski);
        btnAngielski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
            }
        });

        Button btnPolski = findViewById(R.id.btn_Polski);
        btnPolski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("pl");
            }
        });

        Button btnWyloguj = findViewById(R.id.btn_Wyloguj);
        btnWyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
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
        Intent resultIntent = new Intent();
        resultIntent.putExtra("LANGUAGE_ID", getIntent().getIntExtra("LANGUAGE_ID", 1));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void logoutUser() {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        Context context = getApplicationContext();
        resources = context.getResources();
        config = resources.getConfiguration();
        config.setLocale(locale);
        context.createConfigurationContext(config);

        finish();
        startActivity(getIntent());
    }
}