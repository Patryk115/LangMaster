package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.langmaster.presenter.TriviaPresenter;
import com.example.langmaster.view.TriviaView;

public class TriviaActivity extends AppCompatActivity implements TriviaView {

    private TriviaPresenter presenter;
    private ImageView imageView;
    private TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        Button btnGlowna2 = findViewById(R.id.btn_Glowna3);
        btnGlowna2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomeNoAction();
            }
        });



        imageView = findViewById(R.id.triviaImage);
        textView = findViewById(R.id.triviaText);

        int languageId = getIntent().getIntExtra("LANGUAGE_ID", 1);

        presenter = new TriviaPresenter(this, languageId);
        presenter.loadTrivia();

        findViewById(R.id.button13).setOnClickListener(v -> presenter.nextTrivia());
        findViewById(R.id.button10).setOnClickListener(v -> presenter.previousTrivia());
    }

    private void backToHomeNoAction() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void displayTrivia(byte[] imageBytes, String description) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageView.setImageBitmap(bitmap);
        textView.setText(description);
    }

    @Override
    public void displayError(String message) {
        textView.setText(message);
    }
}