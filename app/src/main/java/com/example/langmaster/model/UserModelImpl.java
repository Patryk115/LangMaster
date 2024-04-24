package com.example.langmaster.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.langmaster.DatabaseConnector;

public class UserModelImpl implements UserModel {

    @Override
    public void login(final String username, final String password, final OnLoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = DatabaseConnector.connect();
                    String sql = "SELECT * FROM mobilne.user WHERE login=? AND password=?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        listener.onLoginSuccess();
                    } else {
                        listener.onLoginFailure("Invalid credentials");
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    listener.onLoginFailure("Error connecting to database");
                }
            }
        }).start();
    }
}
