package com.example.langmaster.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface DictModel {
    void fetchWords(int languageId, int categoryId, FetchWordsCallback callback);
}