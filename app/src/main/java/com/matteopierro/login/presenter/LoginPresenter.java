package com.matteopierro.login.presenter;

import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.view.LoginView;

public class LoginPresenter {
    private final LoginView view;
    private final UserRepository repository;

    public LoginPresenter(LoginView view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void login(String username, String password) {
        view.clearErrors();

        if (username.isEmpty()) {
            view.displayEmptyUserNameError();
        }

        if (password.isEmpty()) {
            view.displayEmptyPasswordError();
        }

        repository.findBy(username);
    }
}
