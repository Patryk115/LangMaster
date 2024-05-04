package com.example.langmaster.model;

import java.util.List;

public interface FetchWordsCallback {
    void onWordsFetched(List<Word> words);
    void onError();
}