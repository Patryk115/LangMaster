package com.example.langmaster.model;

public class UserModelImpl implements UserModel {
    @Override
    public void login(String username, String password, OnLoginListener listener) {
        // Ta część powinna być zastąpiona prawdziwymi sprawdzeniami w bazie danych
        if (username.equals("validUser") && password.equals("validPassword")) {
            listener.onLoginSuccess();
        } else {
            listener.onLoginFailure("Invalid credentials");
        }
    }
}