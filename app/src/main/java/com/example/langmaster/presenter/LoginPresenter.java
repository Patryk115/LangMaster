package com.example.langmaster.presenter;

import com.example.langmaster.model.UserModel;

public class LoginPresenter {
    public interface LoginView {
        void showProgress();
        void hideProgress();
        void setLoginError(String errorMessage);
        void navigateToHome();
    }

    private final LoginView loginView;
    private final UserModel userModel;

    public LoginPresenter(LoginView loginView, UserModel userModel) {
        this.loginView = loginView;
        this.userModel = userModel;
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
                    loginView.setLoginError(message);
                }
            }
        });
    }

    public void onDestroy() {


    }
}