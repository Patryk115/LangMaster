package com.example.langmaster.model;



public class Word {
    private String polishWord;
    private String translatedWord;

    public Word(String polishWord, String translatedWord) {
        this.polishWord = polishWord;
        this.translatedWord = translatedWord;
    }

    public String getPolishWord() {
        return polishWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }
}