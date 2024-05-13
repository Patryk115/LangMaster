package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.langmaster.model.Word;
import com.example.langmaster.presenter.DictPresenter;
import com.example.langmaster.view.DictView;
import java.util.List;

public class DictActivity extends AppCompatActivity implements DictView {

    private int selectedCategoryId = 1;
    private DictPresenter presenter;
    private RecyclerView wordsRecyclerView;
    private WordsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        presenter = new DictPresenter(this);
        wordsRecyclerView = findViewById(R.id.wordsRecyclerView);
        wordsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordsAdapter();
        wordsRecyclerView.setAdapter(adapter);

        setupCategorySpinner();

        findViewById(R.id.btn_Powrot3).setOnClickListener(v -> backToHomeNoAction());
    }

    private void setupCategorySpinner() {
        Spinner spinner = findViewById(R.id.spinner);
        TextView categoryTextView = findViewById(R.id.textView20);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryId = position + 1;
                String selectedCategory = (String) parent.getItemAtPosition(position);
                categoryTextView.setText(selectedCategory);
                presenter.loadWords(getIntent().getIntExtra("LANGUAGE_ID", 1), selectedCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryTextView.setText(getString(R.string.select_category));
            }
        });
    }

    @Override
    public void displayWords(List<Word> words) {
        adapter.setWords(words);
    }

    @Override
    public void displayError() {
    }

    private void backToHomeNoAction() {
        Intent intent = new Intent();
        intent.putExtra("LANGUAGE_ID", getIntent().getIntExtra("LANGUAGE_ID", 1));
        setResult(RESULT_OK, intent);
        finish();
    }
}
