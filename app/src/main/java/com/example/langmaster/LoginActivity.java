package com.example.langmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.example.langmaster.presenter.LoginPresenter;
import com.example.langmaster.model.UserModelImpl;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.LoginView {

    private LoginPresenter presenter;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usernameEditText = findViewById(R.id.textInputEditText);
        passwordEditText = findViewById(R.id.textInputEditText2);


        presenter = new LoginPresenter(this, new UserModelImpl());


        Button loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validateCredentials(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString()
                );
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setLoginError(String errorMessage) {
        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
