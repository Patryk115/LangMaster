package com.example.langmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.langmaster.model.UserRepositoryImpl;
import com.example.langmaster.presenter.RegistrationPresenter;
import com.example.langmaster.view.RegistrationView;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {

    private RegistrationPresenter presenter;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        presenter = new RegistrationPresenter(this, new UserRepositoryImpl());


        firstNameEditText = findViewById(R.id.textInputEditText3);
        lastNameEditText = findViewById(R.id.textInputEditText4);
        emailEditText = findViewById(R.id.textInputEditText5);
        usernameEditText = findViewById(R.id.textInputEditText6);
        passwordEditText = findViewById(R.id.textInputEditText7);


        registerButton = findViewById(R.id.button3);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    presenter.register(firstName, lastName, email, username, password);
                } else {
                    Toast.makeText(RegistrationActivity.this, "Wszystkie pola są wymagane.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void showRegistrationSuccess() {
        Toast.makeText(this, "Rejestracja przebiegła pomyślnie.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRegistrationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
