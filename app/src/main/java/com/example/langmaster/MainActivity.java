package com.example.langmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.langmaster.model.FetchLanguagesTask;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private int selectedLanguageId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = findViewById(R.id.spinner3);
        final TextView languageTextView = findViewById(R.id.textView5);

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguageId = position + 1;
                String selectedLanguage = (String) parent.getItemAtPosition(position);
                // Ustawienie tekstu TextView na wybraną kategorię
                languageTextView.setText(selectedLanguage);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                languageTextView.setText("Wybierz Język");
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
        int selectedLanguageId = spinner.getSelectedItemPosition();
        Intent intent = new Intent(this, VocabularyLearningActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId + 1);
        startActivity(intent);
    }



    private void navigateToDict() {
        Intent intent = new Intent(this, DictActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId);
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
