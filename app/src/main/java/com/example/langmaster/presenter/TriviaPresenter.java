package com.example.langmaster.presenter;

import android.os.AsyncTask;

import com.example.langmaster.model.Trivia;
import com.example.langmaster.model.TriviaModel;
import com.example.langmaster.view.TriviaView;
public class TriviaPresenter {

    private TriviaView view;
    private int currentTriviaId = 1; // Zakładamy, że ciekawostki zaczynają się od ID 1

    public TriviaPresenter(TriviaView view) {
        this.view = view;
    }

    public void loadTrivia() {
        new FetchImageTask().execute(currentTriviaId);
    }

    public void nextTrivia() {
        currentTriviaId++;
        loadTrivia();
    }

    public void previousTrivia() {
        if (currentTriviaId > 1) {
            currentTriviaId--;
            loadTrivia();
        }
    }

    private class FetchImageTask extends AsyncTask<Integer, Void, Trivia> {
        @Override
        protected Trivia doInBackground(Integer... params) {
            return TriviaModel.getTrivia(params[0]);
        }

        @Override
        protected void onPostExecute(Trivia trivia) {
            if (trivia != null) {
                view.displayTrivia(trivia.getImage(), trivia.getDescription());
            } else {
                view.displayError("Nie znaleziono ciekawostki");
            }
        }
    }
}
