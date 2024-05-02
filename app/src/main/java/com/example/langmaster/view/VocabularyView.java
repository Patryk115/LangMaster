package com.example.langmaster.view;

import com.example.langmaster.model.Word;

public interface VocabularyView {
    void showWord(Word word);
    void showError(String message);
}