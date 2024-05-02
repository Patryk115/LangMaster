package com.example.langmaster.model;

public interface VocabularyModel {
    void fetchWord(int languageId, FetchWordCallback callback);

    interface FetchWordCallback {
        void onWordFetched(Word word);
        void onError();
    }
}