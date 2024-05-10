package com.example.langmaster.model;


public interface DictModel {
    void fetchWords(int languageId, int categoryId, FetchWordsCallback callback);
}