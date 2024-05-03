package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;  // Import klasy TextView
import android.widget.Toast;

import com.example.langmaster.model.FetchCategoryTask;
import com.example.langmaster.model.FetchLanguagesTask;
import com.example.langmaster.model.Word;
import com.example.langmaster.presenter.VocabularyPresenterImpl;
import com.example.langmaster.presenter.VocabularyPresenter;  // Import interfejsu prezentera
import com.example.langmaster.view.VocabularyView;
import com.google.android.material.textfield.TextInputEditText;

public class VocabularyLearningActivity extends AppCompatActivity implements VocabularyView {
    private TextView wordTextView;  // Deklaracja TextView dla słowa
    private VocabularyPresenter presenter;  // Deklaracja prezentera
    private Word currentWord;
    private TextInputEditText translationInputEditText;  // EditText for entering/displaying the translation

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
                // Pobranie wybranej kategorii
                selectedCategoryId = position + 1; // Zakładając, że ID kategorii zaczynają się od 1
                String selectedCategory = (String) parent.getItemAtPosition(position);
                // Ustawienie tekstu TextView na wybraną kategorię
                categoryTextView.setText(selectedCategory);

                // Natychmiastowe ładowanie nowego słowa dla wybranej kategorii i języka
                int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1); // Pobierz ID języka przekazane z MainActivity
                presenter.loadWord(languageId, selectedCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryTextView.setText("Wybierz kategorię");
            }
        });

        // Odbieranie ID języka przekazanego z MainActivity
        int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1);  // Domyślnie ustawiamy na 1, jeśli nie przekazano żadnego ID

        presenter = new VocabularyPresenterImpl(this);
        presenter.loadWord(languageId, selectedCategoryId);  // Użycie przekazanego ID języka do pobierania słów

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
                    int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1); // Pobierz ID języka przekazane z MainActivity
                    presenter.loadWord(languageId, selectedCategoryId); // Ładowanie nowego słowa
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
        translationInputEditText.setText(""); // Czyści pole po załadowaniu nowego słowa
        isCorrectTextView.setText(""); // Czyści poprzedni wynik
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
                // Wyświetlanie wyniku końcowego sesji
                isCorrectTextView.setText("Wynik sesji: " + correctAnswers + "/" + SESSION_LENGTH + ". Kliknij 'Zatwiedź', aby zacząć nową sesję.");
            }
        }
    }

    private void resetSession() {
        totalAttempts = 0;
        correctAnswers = 0;
        isCorrectTextView.setText("");
        translationInputEditText.setText("");
        // Nie ładuj automatycznie nowego słowa
    }

    private void backToHomeNoAction() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
