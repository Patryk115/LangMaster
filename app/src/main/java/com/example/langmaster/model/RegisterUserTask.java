package com.example.langmaster.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.langmaster.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterUserTask extends AsyncTask<User, Void, String> {
    private UserRepository.OnRegistrationListener listener;

    public RegisterUserTask(UserRepository.OnRegistrationListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(User... users) {
        if (users.length != 1) {
            return "Invalid user data";
        }
        User user = users[0];

        // Sprawdzenie czy użytkownik o danym loginie już istnieje
        if (DatabaseConnector.checkUserExists(user.getUsername())) {
            return "Użytkownik o takim loginie już istnieje";
        }

        // Próba dodania nowego użytkownika
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO mobilne.user (imie, nazwisko, login, password, e_mail) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, user.getImie());
            stmt.setString(2, user.getNazwisko());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return null; // Success
            } else {
                return "No rows affected"; // Failure
            }
        } catch (SQLException e) {
            Log.e("UserRepositoryImpl", "SQL Error", e);
            return "SQL Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            listener.onRegistrationSuccess();
        } else {
            listener.onRegistrationFailure(result);
        }
    }
}