package com.example.langmaster.model;

import android.util.Log;

import com.example.langmaster.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TriviaModel {

    public static Trivia getTrivia(int triviaId, int languageId) {
        Trivia trivia = null;
        String sql = "SELECT image, description FROM mobilne.trivia WHERE id_trivia = ? AND id_language = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, triviaId);
            stmt.setInt(2, languageId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("image");
                String description = rs.getString("description");
                trivia = new Trivia(imageBytes, description);
            }
        } catch (SQLException e) {
            Log.e("DatabaseConnector", "SQL Error", e);
        }
        return trivia;
    }
}