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

        if (areEmpty(username, password)) {
            displayEmptyErrors(username, password);
            return;
        }

        repository.findBy(username);
    }

    private boolean areEmpty(String username, String password) {
        return username.isEmpty() || password.isEmpty();
    }

    private void displayEmptyErrors(String username, String password) {
        if (username.isEmpty()) {
            view.displayEmptyUserNameError();
        }

        if (password.isEmpty()) {
            view.displayEmptyPasswordError();
        }
    }
}
