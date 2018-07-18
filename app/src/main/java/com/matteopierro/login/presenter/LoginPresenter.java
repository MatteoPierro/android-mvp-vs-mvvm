package com.matteopierro.login.presenter;

import com.matteopierro.login.view.LoginView;

class LoginPresenter {
    private final LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void login(String username) {
        view.displayEmptyUserNameError();
    }
}
