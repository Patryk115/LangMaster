package com.example.langmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private int selectedLanguageId = 1;
    private TextView languageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner3);
        languageTextView = findViewById(R.id.textView5);
        setupButtonListeners();
        setupSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupSpinner(); // Ensures spinner reflects current locale
    }

    private void setupButtonListeners() {
        findViewById(R.id.btn_Zatwierdz).setOnClickListener(v -> navigateToTranslator());
        findViewById(R.id.btn_Ustawienia).setOnClickListener(v -> navigateToSettings());
        findViewById(R.id.btn_Slowka).setOnClickListener(v -> navigateToVocabularyLearning());
        findViewById(R.id.btn_Slownik).setOnClickListener(v -> navigateToDict());
        findViewById(R.id.btn_Ciekawostki).setOnClickListener(v -> navigateToTrivia());
        findViewById(R.id.btn_Zdania).setOnClickListener(v -> navigateToSentence());
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguageId = position + 1;
                String selectedLanguage = (String) parent.getItemAtPosition(position);
                languageTextView.setText(selectedLanguage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                languageTextView.setText(getString(R.string.wybierz_jezyk));
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
        Intent intent = new Intent(this, VocabularyLearningActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId);
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
        intent.putExtra("LANGUAGE_ID", selectedLanguageId);
        startActivity(intent);
    }
}
