package com.example.langmaster.model;

public interface UserModel {
    interface OnLoginListener {
        void onLoginSuccess(User user);
        void onLoginFailure(String message);
    }

    void login(String username, String password, OnLoginListener listener);
}