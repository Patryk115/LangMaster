package com.example.langmaster.presenter;

import com.example.langmaster.model.User;
import com.example.langmaster.model.UserRepository;
import com.example.langmaster.view.RegistrationView;

public class RegistrationPresenter {
    private RegistrationView view;
    private UserRepository repository;

    public RegistrationPresenter(RegistrationView view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void register(String firstName, String lastName, String email, String username, String password) {
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);

        repository.registerUser(newUser, new UserRepository.OnRegistrationListener() {
            @Override
            public void onRegistrationSuccess() {
                if (view != null) {
                    view.showRegistrationSuccess();
                    view.navigateToLogin();
                }
            }

            @Override
            public void onRegistrationFailure(String message) {
                if (view != null) {
                    view.showRegistrationError(message);
                }
            }
        });
    }
}