package com.example.langmaster.model;

public class SentenceModel {
    private int id;
    private String sentence;
    private String[] options;
    private int correctOption;

    public SentenceModel(int id, String sentence, String[] options, int correctOption) {
        this.id = id;
        this.sentence = sentence;
        this.options = options;
        this.correctOption = correctOption;
    }

    public int getId() {
        return id;
    }

    public String getSentence() {
        return sentence;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}