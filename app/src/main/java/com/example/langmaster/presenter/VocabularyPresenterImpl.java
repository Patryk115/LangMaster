package com.example.langmaster.presenter;

import com.example.langmaster.model.VocabularyModel;
import com.example.langmaster.model.VocabularyModelImpl;
import com.example.langmaster.model.Word;
import com.example.langmaster.view.VocabularyView;

public class VocabularyPresenterImpl implements VocabularyPresenter, VocabularyModel.FetchWordCallback {
    private VocabularyView view;
    private VocabularyModel model;

    public VocabularyPresenterImpl(VocabularyView view) {
        this.view = view;
        this.model = new VocabularyModelImpl();
    }

    public void loadWord(int languageId, int categoryId) {
        model.fetchWord(languageId, categoryId, this);
    }

    @Override
    public void onWordFetched(Word word) {
        view.showWord(word);
    }

    @Override
    public void onError() {
        view.showError("Error fetching word");
    }
}