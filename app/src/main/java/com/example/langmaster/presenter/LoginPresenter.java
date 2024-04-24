package com.example.langmaster.presenter;

import com.example.langmaster.LoginActivity;
import com.example.langmaster.model.UserModel;

public class LoginPresenter {
    private LoginActivity.LoginView loginView;
    private UserModel userModel;

    public LoginPresenter(LoginActivity.LoginView view, UserModel model) {
        this.loginView = view;
        this.userModel = model;
    }

    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        userModel.login(username, password, new UserModel.OnLoginListener() {
            @Override
            public void onLoginSuccess() {
                if (loginView != null) {
                    loginView.hideProgress();
                    loginView.navigateToHome();
                }
            }

            @Override
            public void onLoginFailure(String message) {
                if (loginView != null) {
                    loginView.hideProgress();
                    loginView.setLoginError();
                }
            }
        });
    }

    public void onDestroy() {
        loginView = null;
    }
}
