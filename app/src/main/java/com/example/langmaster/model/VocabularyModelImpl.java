package com.example.langmaster.model;

import android.os.AsyncTask;

import com.example.langmaster.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class VocabularyModelImpl implements VocabularyModel {

    @Override
    public void fetchWord(int languageId, FetchWordCallback callback) {
        new FetchWordTask(languageId, callback).execute();
    }

    private static class FetchWordTask extends AsyncTask<Void, Void, Word> {
        private int languageId;

        private FetchWordCallback callback;

        public FetchWordTask(int languageId, FetchWordCallback callback) {
            this.languageId = languageId;
            this.callback = callback;
        }

        @Override
        protected Word doInBackground(Void... voids) {
            try (Connection conn = DatabaseConnector.connect();
                 Statement stmt = conn.createStatement()) {
                String sql = "SELECT word, translation FROM mobilne.words WHERE id_language = " + languageId  + " ORDER BY RANDOM() LIMIT 1";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    return new Word(rs.getString("word"), rs.getString("translation"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Word word) {
            if (word != null) {
                callback.onWordFetched(word);
            } else {
                callback.onError();
            }
        }
    }
}