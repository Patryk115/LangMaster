package com.example.langmaster.model;

public interface UserRepository {
    void registerUser(User user, OnRegistrationListener listener);

    interface OnRegistrationListener {
        void onRegistrationSuccess();
        void onRegistrationFailure(String message);
    }
}