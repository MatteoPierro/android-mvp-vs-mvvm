package com.matteopierro.login.view;

public interface LoginView {
    void displayEmptyUserNameError();

    void clearErrors();

    void displayEmptyPasswordError();

    void displayLoginSuccess();
}
