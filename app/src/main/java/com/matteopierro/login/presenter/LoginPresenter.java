package com.matteopierro.login.presenter;

import com.matteopierro.login.view.LoginView;

public class LoginPresenter {
    private final LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void login(String username) {
        if (username.isEmpty()) {
            view.displayEmptyUserNameError();
        }
    }
}
