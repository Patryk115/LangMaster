package com.example.langmaster.presenter;

import com.example.langmaster.model.DictModel;
import com.example.langmaster.model.DictModelImpl;
import com.example.langmaster.model.FetchWordsCallback;
import com.example.langmaster.model.Word;
import com.example.langmaster.view.DictView;
import java.util.List;

public class DictPresenter {
    private DictView view;
    private DictModel model;

    public DictPresenter(DictView view) {
        this.view = view;
        this.model = new DictModelImpl();
    }

    public void loadWords(int languageId, int categoryId) {
        model.fetchWords(languageId, categoryId, new FetchWordsCallback() {
            @Override
            public void onWordsFetched(List<Word> words) {
                view.displayWords(words);
            }

            @Override
            public void onError() {
                view.displayError();
            }
        });
    }
}