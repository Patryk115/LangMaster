package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.langmaster.model.FetchCategoryTask;
import com.example.langmaster.model.Word;
import com.example.langmaster.presenter.DictPresenter;
import com.example.langmaster.presenter.VocabularyPresenter;
import com.example.langmaster.presenter.VocabularyPresenterImpl;
import com.example.langmaster.view.DictView;

import java.util.List;

public class DictActivity extends AppCompatActivity implements DictView {

    private int selectedCategoryId = 1;

    private DictPresenter presenter;
    private RecyclerView wordsRecyclerView;
    private WordsAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        presenter = new DictPresenter(this);

        Spinner spinner = findViewById(R.id.spinner);
        TextView categoryTextView = findViewById(R.id.textView20);

        wordsRecyclerView = findViewById(R.id.wordsRecyclerView);
        wordsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordsAdapter();
        wordsRecyclerView.setAdapter(adapter);

        new FetchCategoryTask(spinner, this).execute();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryId = position + 1;
                String selectedCategory = (String) parent.getItemAtPosition(position);
                categoryTextView.setText(selectedCategory);

                int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1);
                presenter.loadWords(languageId, selectedCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryTextView.setText("Wybierz kategorię");
            }
        });

        Button btnPowrot3 = findViewById(R.id.btn_Powrot3);
        btnPowrot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomeNoAction();
            }
        });
    }

    @Override
    public void displayWords(List<Word> words) {
        adapter.setWords(words);
    }

    @Override
    public void displayError() {
        // Wyświetl komunikat o błędzie
    }

    private void backToHomeNoAction() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}