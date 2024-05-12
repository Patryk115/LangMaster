package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.langmaster.model.Word;
import com.example.langmaster.presenter.VocabularyPresenter;
import com.example.langmaster.presenter.VocabularyPresenterImpl;
import com.example.langmaster.view.VocabularyView;
import com.google.android.material.textfield.TextInputEditText;

public class VocabularyLearningActivity extends AppCompatActivity implements VocabularyView {
    private TextView wordTextView;
    private VocabularyPresenter presenter;
    private Word currentWord;
    private TextInputEditText translationInputEditText;

    private TextView isCorrectTextView;
    private TextView categoryTextView;

    private Spinner spinner;
    private int selectedCategoryId = 1;

    private int totalAttempts = 0;
    private int correctAnswers = 0;
    private static final int SESSION_LENGTH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_learning);

        initializeViews();
        setupCategorySpinner();
        configureButtonListeners();
        loadInitialWord();
    }

    private void initializeViews() {
        wordTextView = findViewById(R.id.word_pl);
        translationInputEditText = findViewById(R.id.textInputEditText7);
        isCorrectTextView = findViewById(R.id.is_correct);
        categoryTextView = findViewById(R.id.textView4);
        spinner = findViewById(R.id.spinner2);
    }

    private void setupCategorySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryId = position + 1;
                String selectedCategory = (String) parent.getItemAtPosition(position);
                categoryTextView.setText(selectedCategory);
                presenter.loadWord(getLanguageId(), selectedCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryTextView.setText(getString(R.string.select_category));
            }
        });
    }

    private int getLanguageId() {
        return getIntent().getIntExtra("LANGUAGE_ID", 1);
    }

    private void configureButtonListeners() {
        findViewById(R.id.next_word).setOnClickListener(v -> {
            if (totalAttempts < SESSION_LENGTH) {
                presenter.loadWord(getLanguageId(), selectedCategoryId);
            }
        });

        findViewById(R.id.accept).setOnClickListener(v -> {
            if (totalAttempts >= SESSION_LENGTH) {
                resetSession();
                presenter.loadWord(getLanguageId(), selectedCategoryId);
            } else {
                verifyTranslation();
            }
        });

        findViewById(R.id.btn_Powrot2).setOnClickListener(v -> backToHomeNoAction());
    }

    private void loadInitialWord() {
        presenter = new VocabularyPresenterImpl(this);
        presenter.loadWord(getLanguageId(), selectedCategoryId);
    }

    @Override
    public void showWord(Word word) {
        currentWord = word;
        wordTextView.setText(word.getPolishWord());
        translationInputEditText.setText("");
        isCorrectTextView.setText("");
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void verifyTranslation() {
        String userTranslation = translationInputEditText.getText().toString().trim();
        if (userTranslation.equalsIgnoreCase(currentWord.getTranslatedWord())) {
            correctAnswers++;
            isCorrectTextView.setText("Dobrze!!!");
            isCorrectTextView.setTextColor(Color.GREEN);
        } else {
            String correctMsg = "Źle, poprawne tłumaczenie: " + currentWord.getTranslatedWord();
            isCorrectTextView.setText(correctMsg);
            isCorrectTextView.setTextColor(Color.RED);
        }
        totalAttempts++;

        if (totalAttempts == SESSION_LENGTH) {
            isCorrectTextView.setText("Wynik sesji: " + correctAnswers + "/" + SESSION_LENGTH + ". Kliknij 'Zatwiedź', aby zacząć nową sesję.");
        }
    }

    private void resetSession() {
        totalAttempts = 0;
        correctAnswers = 0;
        isCorrectTextView.setText("");
        translationInputEditText.setText("");
    }

    private void backToHomeNoAction() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}