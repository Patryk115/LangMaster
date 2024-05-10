package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.langmaster.model.FetchCategoryTask;
import com.example.langmaster.model.Word;
import com.example.langmaster.presenter.VocabularyPresenterImpl;
import com.example.langmaster.presenter.VocabularyPresenter;
import com.example.langmaster.view.VocabularyView;
import com.google.android.material.textfield.TextInputEditText;

public class VocabularyLearningActivity extends AppCompatActivity implements VocabularyView {
    private TextView wordTextView;
    private VocabularyPresenter presenter;
    private Word currentWord;
    private TextInputEditText translationInputEditText;

    private TextView isCorrectTextView;

    private Spinner spinner;
    private int selectedCategoryId = 1;

    private int totalAttempts = 0;
    private int correctAnswers = 0;
    private static final int SESSION_LENGTH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_learning);



        wordTextView = findViewById(R.id.word_pl);
        translationInputEditText = findViewById(R.id.textInputEditText7);
        isCorrectTextView = findViewById(R.id.is_correct);


        spinner = findViewById(R.id.spinner2);
        final TextView categoryTextView = findViewById(R.id.textView4);

        new FetchCategoryTask(spinner, this).execute();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCategoryId = position + 1;
                String selectedCategory = (String) parent.getItemAtPosition(position);

                categoryTextView.setText(selectedCategory);


                int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1);
                presenter.loadWord(languageId, selectedCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryTextView.setText("Wybierz kategorię");
            }
        });


        int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1);

        presenter = new VocabularyPresenterImpl(this);
        presenter.loadWord(languageId, selectedCategoryId);

        Button btnNextWord = findViewById(R.id.next_word);
        btnNextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalAttempts < SESSION_LENGTH) {
                    presenter.loadWord(languageId, selectedCategoryId);
                }
            }
        });
        Button btnAccept = findViewById(R.id.accept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalAttempts >= SESSION_LENGTH) {
                    resetSession();
                    int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1);
                    presenter.loadWord(languageId, selectedCategoryId);
                } else {
                    verifyTranslation();
                }
            }
        });

        Button btnPowrot = findViewById(R.id.btn_Powrot2);
        btnPowrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomeNoAction();
            }
        });
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
        if (totalAttempts < SESSION_LENGTH) {
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
