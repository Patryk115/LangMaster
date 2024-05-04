package com.example.langmaster.view;

import com.example.langmaster.model.Word;
import java.util.List;

public interface DictView {
    void displayWords(List<Word> words);
    void displayError();
}