package com.example.langmaster.model;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.graphics.Bitmap;

import com.example.langmaster.DatabaseConnector;

public class FetchImageTask extends AsyncTask<Integer, Void, Bitmap> {
    private ImageView imageView;

    public FetchImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        return DatabaseConnector.getImage(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            imageView.setImageBitmap(result);
        } else {
            Log.e("TriviaActivity", "Failed to fetch image");
        }
    }
}