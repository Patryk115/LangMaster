package com.example.langmaster.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.langmaster.DatabaseConnector;

import android.util.Log; // Import potrzebny do logowania

public class UserModelImpl implements UserModel {
    private static final String TAG = "UserModelImpl";

    @Override
    public void login(final String login, final String password, final OnLoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = DatabaseConnector.connect();
                    String sql = "SELECT login, imie, e_mail FROM mobilne.user WHERE login=? AND password=?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, login);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        User user = new User();
                        user.setLogin(rs.getString("login"));
                        user.setImie(rs.getString("imie"));
                        user.setEmail(rs.getString("e_mail"));
                        listener.onLoginSuccess(user);
                    } else {
                        listener.onLoginFailure("Błędny login lub hasło");
                    }
                } catch (SQLException e) {
                    Log.e(TAG, "SQL Exception: " + e.getMessage());
                    listener.onLoginFailure("Database error");
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            Log.e(TAG, "SQL Exception on close: " + ex.getMessage());
                        }
                    }
                }
            }
        }).start();
    }
}