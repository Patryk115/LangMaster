package com.example.langmaster.model;

import android.os.AsyncTask;
import com.example.langmaster.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DictModelImpl implements DictModel {
    @Override
    public void fetchWords(int languageId, int categoryId, FetchWordsCallback callback) {
        new FetchWordsTask(languageId, categoryId, callback).execute();
    }

    private static class FetchWordsTask extends AsyncTask<Void, Void, List<Word>> {
        private int languageId;
        private int categoryId;
        private FetchWordsCallback callback;

        public FetchWordsTask(int languageId, int categoryId, FetchWordsCallback callback) {
            this.languageId = languageId;
            this.categoryId = categoryId;
            this.callback = callback;
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
            List<Word> words = new ArrayList<>();
            try (Connection conn = DatabaseConnector.connect();
                 Statement stmt = conn.createStatement()) {
                String sql = "SELECT word, translation FROM mobilne.words WHERE id_language = " + languageId + " AND id_category = " + categoryId;
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    words.add(new Word(rs.getString("word"), rs.getString("translation")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return words;
        }

        @Override
        protected void onPostExecute(List<Word> words) {
            if (!words.isEmpty()) {
                callback.onWordsFetched(words);
            } else {
                callback.onError();
            }
        }
    }
}