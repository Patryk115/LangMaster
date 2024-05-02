package com.example.langmaster.model;

import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.langmaster.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FetchLanguagesTask extends AsyncTask<Void, Void, List<String>> {
    private Spinner spinner;
    private Context context;

    public FetchLanguagesTask(Spinner spinner, Context context) {
        this.spinner = spinner;
        this.context = context;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> languages = new ArrayList<>();
        try {
            Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM mobilne.language";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String language = rs.getString("language");
                languages.add(language);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            Log.e("Database", "Error while fetching data", e);
        }
        return languages;
    }

    @Override
    protected void onPostExecute(List<String> languages) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
