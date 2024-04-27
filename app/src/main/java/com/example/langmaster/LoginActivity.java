package com.example.langmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
        TextView registerLink = findViewById(R.id.textView3);

        presenter = new LoginPresenter(this, new UserModelImpl());


        Button loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()) {
                    presenter.validateCredentials(username, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Nie podałeś hasła lub loginu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void setLoginError(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
