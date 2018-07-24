package com.matteopierro.login.presenter;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.view.LoginView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginPresenter {
    private final LoginView view;
    private final UserRepository repository;

    public LoginPresenter(LoginView view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void login(String username, final String password) {
        view.clearErrors();

        if (areEmpty(username, password)) {
            displayEmptyErrors(username, password);
            return;
        }

        Observable<User> userObservable = repository.findBy(username);
        userObservable.subscribe(new UserObserver(password));
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

    private class UserObserver implements Observer<User> {
        private final String password;

        public UserObserver(String password) {
            this.password = password;
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(User user) {
            if (user.hasPassword(password)) {
                view.displayLoginSuccess();
            } else {
                view.displayIncorrectPasswordError();
            }
        }

        @Override
        public void onError(Throwable e) {
            view.displayUnknownUsernameError();
        }

        @Override
        public void onComplete() {

        }
    }
}
