package com.example.langmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.langmaster.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.example.langmaster.presenter.LoginPresenter;
import com.example.langmaster.model.UserModelImpl;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.LoginView {

    private LoginPresenter presenter;
    private TextInputEditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.textInputEditText);
        passwordEditText = findViewById(R.id.editTextTextPassword);

        presenter = new LoginPresenter(this, new UserModelImpl());

        Button loginButton = findViewById(R.id.btn_Ustawienia);
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (!username.isEmpty() && !password.isEmpty()) {
                presenter.validateCredentials(username, password);
            } else {
                Toast.makeText(LoginActivity.this, "Nie podałeś hasła lub loginu", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.textView3).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void setLoginError(final String errorMessage) {
        runOnUiThread(() -> Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void navigateToHome(User user) {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Login", user.getLogin());
        editor.putString("Imie", user.getImie());
        editor.putString("Email", user.getEmail());
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}