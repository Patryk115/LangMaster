package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private static final int SESSION_LENGTH = 10;
    private boolean initialLoad = true;
    private boolean isWordVerified = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_learning);

        initializeViews();
        setupCategorySpinner();
        configureButtonListeners();
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
                if (!initialLoad) {
                    selectedCategoryId = position + 1;
                    String selectedCategory = (String) parent.getItemAtPosition(position);
                    categoryTextView.setText(selectedCategory);
                    if (isWordVerified) {
                        presenter.loadWord(getLanguageId(), selectedCategoryId);
                    } else {
                        Toast.makeText(VocabularyLearningActivity.this, "Przetłumacz aktualne słowo.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    initialLoad = false;
                    loadInitialWord();
                }
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
            if (totalAttempts < SESSION_LENGTH && isWordVerified) {
                presenter.loadWord(getLanguageId(), selectedCategoryId);
            } else {
                Toast.makeText(this, "Przetłumacz aktualne słowo.", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.accept).setOnClickListener(v -> {
            if (!translationInputEditText.getText().toString().trim().isEmpty()) {
                if (totalAttempts >= SESSION_LENGTH) {
                    resetSession();
                    presenter.loadWord(getLanguageId(), selectedCategoryId);
                } else {
                    verifyTranslation();
                }
            } else {
                Toast.makeText(this, "Podaj tłumaczenie.", Toast.LENGTH_SHORT).show();
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
        isWordVerified = false;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void verifyTranslation() {
        String userTranslation = translationInputEditText.getText().toString().trim();
        if (userTranslation.equalsIgnoreCase(currentWord.getTranslatedWord())) {
            correctAnswers++;
            isCorrectTextView.setText("Poprawne tłumaczenie!");
            isCorrectTextView.setTextColor(getResources().getColor(R.color.text_correct));
        } else {
            String correctMsg = "Źle, poprawne tłumaczenie:" + currentWord.getTranslatedWord();
            isCorrectTextView.setText(correctMsg);
            isCorrectTextView.setTextColor(getResources().getColor(R.color.text_incorrect));
        }
        totalAttempts++;
        isWordVerified = true;

        if (totalAttempts == SESSION_LENGTH) {
            isCorrectTextView.setText("Wynik: " + correctAnswers + "/" + SESSION_LENGTH + ". Kliknij 'Zatwierdź' aby zacząć nową sesję.");
        }
    }

    private void resetSession() {
        totalAttempts = 0;
        correctAnswers = 0;
        isCorrectTextView.setText("");
        translationInputEditText.setText("");
        isWordVerified = true;
    }

    private void backToHomeNoAction() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("LANGUAGE_ID", getIntent().getIntExtra("LANGUAGE_ID", 1));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}
