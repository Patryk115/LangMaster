package com.example.langmaster.presenter;

import com.example.langmaster.model.User;
import com.example.langmaster.model.UserModel;

public class LoginPresenter {
    public interface LoginView {
        void setLoginError(String errorMessage);
        void navigateToHome(User user);
    }

    private final LoginView loginView;
    private final UserModel userModel;

    public LoginPresenter(LoginView loginView, UserModel userModel) {
        this.loginView = loginView;
        this.userModel = userModel;
    }

    public void validateCredentials(String username, String password) {
        userModel.login(username, password, new UserModel.OnLoginListener() {
            @Override
            public void onLoginSuccess(User user) {
                if (loginView != null) {
                    loginView.navigateToHome(user); // Updated to pass User object
                }
            }

            @Override
            public void onLoginFailure(String message) {
                if (loginView != null) {
                    loginView.setLoginError(message);
                }
            }
        });
    }

    public void onDestroy() {
        // Cleanup resources if needed
    }
}