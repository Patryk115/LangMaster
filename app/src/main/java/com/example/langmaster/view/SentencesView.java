package com.example.langmaster.view;

public interface SentencesView {
    void updateSentence(String sentence);
    void updateOptions(String[] options);
    void showCorrect(int option);
    void showIncorrect(int selectedOption, int correctOption);
    void showError(String message);
}