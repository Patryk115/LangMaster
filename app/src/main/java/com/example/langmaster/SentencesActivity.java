package com.example.langmaster;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.langmaster.view.SentencesView;
import com.example.langmaster.presenter.SentencesPresenter;

public class SentencesActivity extends AppCompatActivity implements SentencesView {
    private SentencesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentences);
        presenter = new SentencesPresenter(this);

        initializeComponents();

        int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1);
        presenter.loadSentence(languageId);
    }

    private void initializeComponents() {
        Button btnReturn = findViewById(R.id.btn_Glowna4);
        btnReturn.setOnClickListener(v -> finish());

        Button btnNext = findViewById(R.id.next);
        Button btnPrev = findViewById(R.id.prev);
        btnNext.setOnClickListener(v -> {
            resetButtonColors();
            presenter.nextSentence();
        });
        btnPrev.setOnClickListener(v -> {
            resetButtonColors();
            presenter.previousSentence();
        });

        Button option1 = findViewById(R.id.option1);
        Button option2 = findViewById(R.id.option2);
        Button option3 = findViewById(R.id.option3);
        Button option4 = findViewById(R.id.option4);

        option1.setOnClickListener(v -> presenter.optionSelected(1));
        option2.setOnClickListener(v -> presenter.optionSelected(2));
        option3.setOnClickListener(v -> presenter.optionSelected(3));
        option4.setOnClickListener(v -> presenter.optionSelected(4));
    }

    @Override
    public void updateSentence(String sentence) {
        TextView sentenceTextView = findViewById(R.id.sentence);
        sentenceTextView.setText(sentence);
    }

    @Override
    public void updateOptions(String[] options) {
        Button[] optionButtons = {findViewById(R.id.option1), findViewById(R.id.option2),
                findViewById(R.id.option3), findViewById(R.id.option4)};
        for (int i = 0; i < options.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setBackgroundResource(R.drawable.rounded_square);
        }
    }

    @Override
    public void showCorrect(int option) {
        Button[] optionButtons = {findViewById(R.id.option1), findViewById(R.id.option2),
                findViewById(R.id.option3), findViewById(R.id.option4)};
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setBackgroundResource(i + 1 == option ? R.drawable.rounded_green : R.drawable.rounded_red);
        }
    }

    @Override
    public void showIncorrect(int selectedOption, int correctOption) {
        Button[] optionButtons = {findViewById(R.id.option1), findViewById(R.id.option2),
                findViewById(R.id.option3), findViewById(R.id.option4)};
        for (int i = 0; i < optionButtons.length; i++) {
            if (i + 1 == correctOption) {
                optionButtons[i].setBackgroundResource(R.drawable.rounded_green);
            } else {
                optionButtons[i].setBackgroundResource(R.drawable.rounded_red);
            }
        }
    }

    private void resetButtonColors() {
        Button[] optionButtons = {findViewById(R.id.option1), findViewById(R.id.option2),
                findViewById(R.id.option3), findViewById(R.id.option4)};
        for (Button button : optionButtons) {
            button.setBackgroundResource(R.drawable.rounded_square);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private Button getButtonByOption(int option) {
        switch (option) {
            case 1: return findViewById(R.id.option1);
            case 2: return findViewById(R.id.option2);
            case 3: return findViewById(R.id.option3);
            case 4: return findViewById(R.id.option4);
            default: throw new IllegalArgumentException("Invalid option number");
        }
    }
}
