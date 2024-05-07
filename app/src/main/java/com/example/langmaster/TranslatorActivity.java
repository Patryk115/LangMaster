package com.example.langmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        sourceLangSpinner = findViewById(R.id.sourceLangSpinner);
        targetLangSpinner = findViewById(R.id.targetLangSpinner);
        sourceText = findViewById(R.id.textInputEditText12);
        translatedText = findViewById(R.id.textInputEditText13);
        Button translateButton = findViewById(R.id.button8);
        Button backButton = findViewById(R.id.btn_Glowna2);
        Button swapButton = findViewById(R.id.zmiana);

        List<LanguageItem> languageItems = new ArrayList<>();
        int defaultSourceIndex = -1;
        int defaultTargetIndex = -1;
        for (String code : TranslateLanguage.getAllLanguages()) {
            Locale locale = new Locale(code);
            String languageName = locale.getDisplayLanguage(Locale.ENGLISH);
            LanguageItem item = new LanguageItem(languageName, code);
            languageItems.add(item);
            if (languageName.equalsIgnoreCase("Polish")) {
                defaultSourceIndex = languageItems.size() - 1;
            }
            if (languageName.equalsIgnoreCase("English")) {
                defaultTargetIndex = languageItems.size() - 1;
            }
        }

        ArrayAdapter<LanguageItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languageItems);
        sourceLangSpinner.setAdapter(adapter);
        targetLangSpinner.setAdapter(adapter);
        if (defaultSourceIndex >= 0) sourceLangSpinner.setSelection(defaultSourceIndex);
        if (defaultTargetIndex >= 0) targetLangSpinner.setSelection(defaultTargetIndex);

        translateButton.setOnClickListener(view -> {
            LanguageItem sourceLang = (LanguageItem) sourceLangSpinner.getSelectedItem();
            LanguageItem targetLang = (LanguageItem) targetLangSpinner.getSelectedItem();
            initTranslator(sourceLang.code, targetLang.code);
            translateText(sourceText.getText().toString());
        });

        swapButton.setOnClickListener(view -> {
            int sourcePosition = sourceLangSpinner.getSelectedItemPosition();
            int targetPosition = targetLangSpinner.getSelectedItemPosition();

            sourceLangSpinner.setSelection(targetPosition);
            targetLangSpinner.setSelection(sourcePosition);
        });

        backButton.setOnClickListener(view -> backToHomeNoAction());
    }

    private void initTranslator(String sourceLang, String targetLang) {
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(sourceLang)
                .setTargetLanguage(targetLang)
                .build();
        translator = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder().requireWifi().build();
        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(unused -> {
                    translateText(sourceText.getText().toString());
                })
                .addOnFailureListener(e -> {
                    translatedText.setText("Error downloading language model: " + e.getMessage());
                });
    }

    private void translateText(String text) {
        if (text.isEmpty()) {
            translatedText.setText("Please enter text to translate.");
            return;
        }
        if (translator == null) {
            translatedText.setText("Translator not initialized, make sure model is downloaded.");
            return;
        }
        translator.translate(text)
                .addOnSuccessListener(translatedText::setText)
                .addOnFailureListener(e -> translatedText.setText("Translation failed: " + e.getMessage()));
    }

    private void backToHomeNoAction() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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