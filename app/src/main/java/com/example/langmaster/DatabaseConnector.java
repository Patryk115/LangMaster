package com.example.langmaster;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://195.150.230.208:5432/2023_kindra_patryk";
    private static final String USER = "2023_kindra_patryk";
    private static final String PASSWORD = "36375";

    private static final String TAG = "DatabaseConnector"; // Tag używany w Logcat

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                Log.d(TAG, "Połączenie z bazą danych jest poprawne.");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "PostgreSQL JDBC Driver not found.", e);
            throw new SQLException("PostgreSQL JDBC Driver not found.", e);
        }
    }
    public static boolean checkUserExists(String username) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM mobilne.user WHERE login = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            Log.e("DatabaseConnector", "SQL Error", e);
        }
        return false;
    }
}
