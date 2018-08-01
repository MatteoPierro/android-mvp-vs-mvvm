package com.matteopierro.login.view;

public interface LoginView {
    void clearErrors();

    void displayEmptyUserNameError();

    void displayUnknownUsernameError();

    void displayEmptyPasswordError();

    void displayIncorrectPasswordError();

    void displayLoginSuccess();

    void displayProgressIndicator();
}
