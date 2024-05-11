package com.example.langmaster.model;


import com.example.langmaster.DatabaseConnector;
import com.example.langmaster.presenter.SentencesPresenter;
import java.util.List;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FetchSentenceTask extends AsyncTask<Void, Void, List<SentenceModel>> {
    private SentencesPresenter presenter;
    private int languageId;

    public FetchSentenceTask(SentencesPresenter presenter, int languageId) {
        this.presenter = presenter;
        this.languageId = languageId;
    }

    @Override
    protected List<SentenceModel> doInBackground(Void... voids) {
        List<SentenceModel> sentences = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnector.connect();
            String query = "SELECT id_sentences, sentence, option1, option2, option3, option4, correct_option " +
                    "FROM mobilne.sentences WHERE id_language = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, languageId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_sentences");
                String sentence = rs.getString("sentence");
                String[] options = {
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4")
                };
                int correctOption = rs.getInt("correct_option");

                sentences.add(new SentenceModel(id, sentence, options, correctOption));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return sentences;
    }

    @Override
    protected void onPostExecute(List<SentenceModel> sentences) {
        if (sentences != null && !sentences.isEmpty()) {
            presenter.updateModel(sentences);
        } else {
            presenter.onFetchFailure();
        }
    }
}