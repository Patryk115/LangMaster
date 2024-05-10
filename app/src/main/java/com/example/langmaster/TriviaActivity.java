package com.example.langmaster;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TriviaActivity extends AppCompatActivity {

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
    }

    private void backToHomeNoAction() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}