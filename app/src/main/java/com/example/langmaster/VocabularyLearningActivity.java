package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;  // Import klasy TextView
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_learning);

        wordTextView = findViewById(R.id.word_pl);
        translationInputEditText = findViewById(R.id.textInputEditText7);
        isCorrectTextView = findViewById(R.id.is_correct);

        // Odbieranie ID języka przekazanego z MainActivity
        int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1);  // Domyślnie ustawiamy na 1, jeśli nie przekazano żadnego ID

        presenter = new VocabularyPresenterImpl(this);
        presenter.loadWord(languageId);  // Użycie przekazanego ID języka do pobierania słów

        Button btnNextWord = findViewById(R.id.next_word);
        btnNextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadWord(languageId);  // Ponowne załadowanie nowego słowa dla bieżącego ID języka
            }
        });

        Button btnAccept = findViewById(R.id.accept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyTranslation();
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
        if (userTranslation.equalsIgnoreCase(currentWord.getTranslatedWord())) {
            isCorrectTextView.setText("Dobrze!!!");
            isCorrectTextView.setTextColor(Color.GREEN);
        } else {
            String correctMsg = "Poprawne tłumaczenie: " + currentWord.getTranslatedWord();
            isCorrectTextView.setText(correctMsg);
            isCorrectTextView.setTextColor(Color.RED);
        }
    }

    private void backToHomeNoAction() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
