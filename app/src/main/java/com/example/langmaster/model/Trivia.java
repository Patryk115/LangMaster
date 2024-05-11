package com.example.langmaster.model;

public class Trivia {
    private byte[] image;
    private String description;

    public Trivia(byte[] image, String description) {
        this.image = image;
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
