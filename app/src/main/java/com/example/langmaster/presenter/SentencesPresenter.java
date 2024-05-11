package com.example.langmaster.presenter;

import com.example.langmaster.view.SentencesView;
import com.example.langmaster.model.SentenceModel;
import com.example.langmaster.model.FetchSentenceTask;
import java.util.List;
import java.util.ArrayList;

public class SentencesPresenter {
    private SentencesView view;
    private List<SentenceModel> sentences;
    private int currentSentenceIndex = 0;
    private int languageId;

    public SentencesPresenter(SentencesView view) {
        this.view = view;
        this.sentences = new ArrayList<>();
    }

    public void loadSentence(int languageId) {
        this.languageId = languageId;
        new FetchSentenceTask(this, languageId).execute();
    }

    public void updateModel(List<SentenceModel> sentences) {
        if (sentences != null && !sentences.isEmpty()) {
            this.sentences = sentences;
            loadCurrentSentence();
        }
    }

    public void onFetchFailure() {
        view.showError("Failed to load sentences.");
    }

    private void loadCurrentSentence() {
        if (currentSentenceIndex < sentences.size()) {
            SentenceModel model = sentences.get(currentSentenceIndex);
            view.updateSentence(model.getSentence());
            view.updateOptions(model.getOptions());
        }
    }

    public void nextSentence() {
        if (currentSentenceIndex < sentences.size() - 1) {
            currentSentenceIndex++;
            loadCurrentSentence();
        }
    }

    public void previousSentence() {
        if (currentSentenceIndex > 0) {
            currentSentenceIndex--;
            loadCurrentSentence();
        }
    }

    public void optionSelected(int selectedOption) {
        SentenceModel currentModel = sentences.get(currentSentenceIndex);
        if (selectedOption == currentModel.getCorrectOption()) {
            view.showCorrect(selectedOption);
        } else {
            view.showIncorrect(selectedOption, currentModel.getCorrectOption());
        }
    }
}