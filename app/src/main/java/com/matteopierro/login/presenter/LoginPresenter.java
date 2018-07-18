package com.matteopierro.login.presenter;

import com.matteopierro.login.view.LoginView;

public class LoginPresenter {
    private final LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void login(String username, String password) {
        view.clearErrors();

        if (username.isEmpty()) {
            view.displayEmptyUserNameError();
        }

        if (password.isEmpty()) {
            view.displayEmptyPasswordError();
        }
    }
}
