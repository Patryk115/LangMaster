package com.example.langmaster.model;

import android.os.AsyncTask;

import com.example.langmaster.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class VocabularyModelImpl implements VocabularyModel {
    @Override
    public void fetchWord(int languageId, int categoryId, FetchWordCallback callback) {
        new FetchWordTask(languageId, categoryId, callback).execute();
    }

    private static class FetchWordTask extends AsyncTask<Void, Void, Word> {
        private int languageId;
        private int categoryId;
        private FetchWordCallback callback;

        public FetchWordTask(int languageId, int categoryId, FetchWordCallback callback) {
            this.languageId = languageId;
            this.categoryId = categoryId;
            this.callback = callback;
        }

        @Override
        protected Word doInBackground(Void... voids) {
            try (Connection conn = DatabaseConnector.connect();
                 Statement stmt = conn.createStatement()) {
                String sql = "SELECT word, translation FROM mobilne.words WHERE id_language = " + languageId + " AND id_category = " + categoryId + " ORDER BY RANDOM() LIMIT 1";
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