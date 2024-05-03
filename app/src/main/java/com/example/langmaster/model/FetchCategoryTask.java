package com.example.langmaster.model;

import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.langmaster.DatabaseConnector;
import com.example.langmaster.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FetchCategoryTask extends AsyncTask<Void, Void, List<String>> {
    private Spinner spinner;
    private Context context;

    public FetchCategoryTask(Spinner spinner, Context context) {
        this.spinner = spinner;
        this.context = context;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> categories = new ArrayList<>();
        try {
            Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM mobilne.category";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String category = rs.getString("category");
                categories.add(category);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            Log.e("Database", "Error while fetching data", e);
        }
        return categories;
    }

    @Override
    protected void onPostExecute(List<String> categories) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, categories);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}
