package com.example.langmaster.view;

public interface RegistrationView {
    void showRegistrationSuccess();
    void showRegistrationError(String message);
    void navigateToLogin();
}