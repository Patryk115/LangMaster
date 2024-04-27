package com.example.langmaster.model;

import com.example.langmaster.model.RegisterUserTask;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void registerUser(User user, OnRegistrationListener listener) {
        new RegisterUserTask(listener).execute(user);
    }
}