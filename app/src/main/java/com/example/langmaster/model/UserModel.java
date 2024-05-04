package com.example.langmaster.model;

public interface UserModel {
    interface OnLoginListener {
        void onLoginSuccess(User user); // Accept User object
        void onLoginFailure(String message);
    }

    void login(String username, String password, OnLoginListener listener);
}