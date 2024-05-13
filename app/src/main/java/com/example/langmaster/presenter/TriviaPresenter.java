package com.example.langmaster.presenter;

import android.os.AsyncTask;

import com.example.langmaster.model.Trivia;
import com.example.langmaster.model.TriviaModel;
import com.example.langmaster.view.TriviaView;

public class TriviaPresenter {

    private TriviaView view;
    private int currentTriviaId;
    private int languageId;

    public TriviaPresenter(TriviaView view, int languageId) {
        this.view = view;
        this.languageId = languageId;
        setStartingTriviaId(languageId);
    }

    private void setStartingTriviaId(int langId) {
        switch (langId) {
            case 1:
                currentTriviaId = 1;
                break;
            case 2:
                currentTriviaId = 4;
                break;
            default:
                currentTriviaId = 1;
                break;
        }
    }

    public void loadTrivia() {
        new FetchImageTask().execute(currentTriviaId, languageId);
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
            int triviaId = params[0];
            int langId = params[1];
            return TriviaModel.getTrivia(triviaId, langId);
        }

        @Override
        protected void onPostExecute(Trivia trivia) {
            if (trivia != null) {
                view.displayTrivia(trivia.getImage(), trivia.getDescription());
            } else {
            }
        }
    }
}