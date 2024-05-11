package com.example.langmaster.view;

public interface TriviaView {
    void displayTrivia(byte[] imageBytes, String description);
    void displayError(String message);
}
