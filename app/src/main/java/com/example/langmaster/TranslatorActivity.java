package com.example.langmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TranslatorActivity extends AppCompatActivity {

    private Translator translator;
    private Spinner sourceLangSpinner;
    private Spinner targetLangSpinner;
    private TextView sourceLang;
    private TextView targetLang;
    private TextView sourceText;
    private TextView translatedText;

    class LanguageItem {
        String name;
        String code;

        LanguageItem(String name, String code) {
            this.name = name;
            this.code = code;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        initUI();
        setupLanguageSpinners();
        setupButtons();
    }

    private void initUI() {
        sourceLangSpinner = findViewById(R.id.sourceLangSpinner);
        targetLangSpinner = findViewById(R.id.targetLangSpinner);
        sourceLang = findViewById(R.id.textView6);
        targetLang = findViewById(R.id.textView7);
        sourceText = findViewById(R.id.textInputEditText12);
        translatedText = findViewById(R.id.textInputEditText13);
    }

    private void setupLanguageSpinners() {
        List<LanguageItem> languageItems = prepareLanguageItems();
        ArrayAdapter<LanguageItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languageItems);
        sourceLangSpinner.setAdapter(adapter);
        targetLangSpinner.setAdapter(adapter);

        sourceLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LanguageItem item = (LanguageItem) parent.getItemAtPosition(position);
                sourceLang.setText(item.name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        targetLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LanguageItem item = (LanguageItem) parent.getItemAtPosition(position);
                targetLang.setText(item.name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        setSpinnerSelections(languageItems);
    }

    private List<LanguageItem> prepareLanguageItems() {
        List<LanguageItem> languageItems = new ArrayList<>();
        for (String code : TranslateLanguage.getAllLanguages()) {
            Locale locale = new Locale(code);
            String languageName = locale.getDisplayLanguage(Locale.ENGLISH);
            languageItems.add(new LanguageItem(languageName, code));
        }
        return languageItems;
    }

    private void setSpinnerSelections(List<LanguageItem> languageItems) {
        int defaultSourceIndex = -1;
        int defaultTargetIndex = -1;
        for (int i = 0; i < languageItems.size(); i++) {
            if (languageItems.get(i).name.equalsIgnoreCase("Polish")) {
                defaultSourceIndex = i;
            }
            if (languageItems.get(i).name.equalsIgnoreCase("English")) {
                defaultTargetIndex = i;
            }
        }

        if (defaultSourceIndex != -1) {
            sourceLangSpinner.setSelection(defaultSourceIndex);
        }
        if (defaultTargetIndex != -1) {
            targetLangSpinner.setSelection(defaultTargetIndex);
        }
    }

    private void setupButtons() {
        Button translateButton = findViewById(R.id.button8);
        Button backButton = findViewById(R.id.btn_Glowna2);
        Button swapButton = findViewById(R.id.zmiana);

        translateButton.setOnClickListener(view -> translateCurrentText());
        swapButton.setOnClickListener(view -> swapLanguages());
        backButton.setOnClickListener(view -> backToHomeNoAction());
    }

    private void translateCurrentText() {
        String text = sourceText.getText().toString();
        if (text.isEmpty()) {
            translatedText.setText("Please enter text to translate.");
            return;
        }
        LanguageItem sourceLang = (LanguageItem) sourceLangSpinner.getSelectedItem();
        LanguageItem targetLang = (LanguageItem) targetLangSpinner.getSelectedItem();
        initTranslator(sourceLang.code, targetLang.code);
        translateText(text);
    }

    private void swapLanguages() {
        int sourcePosition = sourceLangSpinner.getSelectedItemPosition();
        int targetPosition = targetLangSpinner.getSelectedItemPosition();
        sourceLangSpinner.setSelection(targetPosition);
        targetLangSpinner.setSelection(sourcePosition);
    }

    private void initTranslator(String sourceLang, String targetLang) {
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(sourceLang)
                .setTargetLanguage(targetLang)
                .build();
        translator = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder().requireWifi().build();
        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(unused -> translateText(sourceText.getText().toString()))
                .addOnFailureListener(e -> translatedText.setText("Error downloading language model: " + e.getMessage()));
    }

    private void translateText(String text) {
        translator.translate(text)
                .addOnSuccessListener(translatedText::setText)
                .addOnFailureListener(e -> translatedText.setText("Translation failed: " + e.getMessage()));
    }

    private void backToHomeNoAction() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("LANGUAGE_ID", getIntent().getIntExtra("LANGUAGE_ID", 1));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (translator != null) {
            translator.close();
        }
    }
}