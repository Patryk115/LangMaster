package com.example.langmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.langmaster.model.FetchLanguagesTask;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = findViewById(R.id.spinner3);
        new FetchLanguagesTask(spinner, this).execute();

        Button btnTlumacz = findViewById(R.id.btn_Zatwierdz);
        btnTlumacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTranslator();
            }
        });

        Button btnUstawienia = findViewById(R.id.btn_Ustawienia);
        btnUstawienia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSettings();
            }
        });

        Button btnNaukaSlowek = findViewById(R.id.btn_Slowka);
        btnNaukaSlowek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToVocabularyLearning();
            }
        });

        Button btnSlownik = findViewById(R.id.btn_Slownik);
        btnSlownik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDict();
            }
        });

        Button btnCiekawostki = findViewById(R.id.btn_Ciekawostki);
        btnCiekawostki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTrivia();
            }
        });

        Button btnZdania = findViewById(R.id.btn_Zdania);
        btnZdania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSentence();
            }
        });
    }

    private void navigateToTranslator() {
        Intent intent = new Intent(this, TranslatorActivity.class);
        startActivity(intent);
    }

    private void navigateToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void navigateToVocabularyLearning() {
        int selectedLanguageId = spinner.getSelectedItemPosition();  // Pobieranie wybranego ID języka z spinnera
        Intent intent = new Intent(this, VocabularyLearningActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId + 1);  // Przekazanie ID języka jako extra (dodajemy 1, jeśli ID języków w bazie zaczyna się od 1)
        startActivity(intent);
    }
    private void navigateToDict() {
        Intent intent = new Intent(this, TranslatorActivity.class);
        startActivity(intent);
    }

    private void navigateToTrivia() {
        Intent intent = new Intent(this, TriviaActivity.class);
        startActivity(intent);
    }

    private void navigateToSentence() {
        Intent intent = new Intent(this, SentencesActivity.class);
        startActivity(intent);
    }
}
