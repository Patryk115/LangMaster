package com.example.langmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.preference.PreferenceManager;

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

        spinner.setSelection(0);
        saveLanguageSelection(1);
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
                R.array.languages, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguageId = position + 1;
                String selectedLanguage = (String) parent.getItemAtPosition(position);
                languageTextView.setText(selectedLanguage);
                saveLanguageSelection(selectedLanguageId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                languageTextView.setText(getString(R.string.wybierz_jezyk));
            }
        });
    }

    private void updateUI() {

        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this,
                R.array.languages, R.layout.spinner_item);
        languageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(languageAdapter);


        spinner.setSelection(selectedLanguageId - 1);


        ((Button) findViewById(R.id.btn_Zatwierdz)).setText(R.string.tlumacz);
        ((Button) findViewById(R.id.btn_Ustawienia)).setText(R.string.ustawienia);
        ((Button) findViewById(R.id.btn_Slowka)).setText(R.string.nauka_slowek);
        ((Button) findViewById(R.id.btn_Slownik)).setText(R.string.slownik);
        ((Button) findViewById(R.id.btn_Zdania)).setText(R.string.zdania);
        ((Button) findViewById(R.id.btn_Ciekawostki)).setText(R.string.ciekawostki);
        ((TextView) findViewById(R.id.textView5)).setText(R.string.wybierz_jezyk);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void navigateToTranslator() {
        Intent intent = new Intent(this, TranslatorActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId);
        startActivity(intent);
    }

    private void navigateToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId);
        startActivity(intent);
    }

    private void navigateToVocabularyLearning() {
        Intent intent = new Intent(this, VocabularyLearningActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId);
        startActivityForResult(intent, 1);
    }

    private void navigateToDict() {
        Intent intent = new Intent(this, DictActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId);
        startActivityForResult(intent, 1);
    }

    private void navigateToTrivia() {
        Intent intent = new Intent(this, TriviaActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId);
        startActivityForResult(intent, 1);
    }

    private void navigateToSentence() {
        Intent intent = new Intent(this, SentencesActivity.class);
        intent.putExtra("LANGUAGE_ID", selectedLanguageId);
        startActivity(intent);
    }

    private void saveLanguageSelection(int languageId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("SelectedLanguageId", languageId);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectedLanguageId = data.getIntExtra("LANGUAGE_ID", 1);
            spinner.setSelection(selectedLanguageId - 1);
        }
    }


}
