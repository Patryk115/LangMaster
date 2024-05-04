package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.langmaster.model.FetchCategoryTask;
import com.example.langmaster.presenter.VocabularyPresenter;

public class DictActivity extends AppCompatActivity {

    private int selectedCategoryId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        Spinner spinner = findViewById(R.id.spinner);
        final TextView categoryTextView = findViewById(R.id.textView20);

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

    private void backToHomeNoAction() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}