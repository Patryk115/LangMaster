package com.example.langmaster.model;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void registerUser(User user, OnRegistrationListener listener) {
        new RegisterUserTask(listener).execute(user);
    }
}