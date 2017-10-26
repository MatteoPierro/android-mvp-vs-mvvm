package com.matteopierro.login.presenter;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.view.LoginRouter;
import com.matteopierro.login.view.PasswordErrorView;
import com.matteopierro.login.view.ProgressView;
import com.matteopierro.login.view.UsernameErrorView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class LoginPresenter {

    private final ProgressView progressView;
    private final UsernameErrorView usernameErrorView;
    private final PasswordErrorView passwordErrorView;
    private final LoginRouter loginRouter;
    private final UserRepository users;

    public LoginPresenter(ProgressView progressView, UsernameErrorView usernameErrorView, PasswordErrorView passwordErrorView, LoginRouter loginRouter, UserRepository users) {
        this.progressView = progressView;
        this.usernameErrorView = usernameErrorView;
        this.passwordErrorView = passwordErrorView;
        this.loginRouter = loginRouter;
        this.users = users;
    }

    public void login(String username, String password) {
        cleanErrors();

        if (fieldsAreEmpty(username, password)) {
            showErrors(username, password);
            return;
        }

        progressView.show();

        FindUserObserver findUserObserver = new FindUserObserver(password);
        users.findBy(username).subscribe(findUserObserver);
    }

    private void showErrors(String username, String password) {
        if (username.isEmpty()) {
            usernameErrorView.required();
        }

        if (password.isEmpty()) {
            passwordErrorView.required();
        }
    }

    private boolean fieldsAreEmpty(String username, String password) {
        return username.isEmpty() || password.isEmpty();
    }


    private void cleanErrors() {
        usernameErrorView.clean();
        passwordErrorView.clean();
    }

    private class FindUserObserver implements Observer<User> {
        private String password;

        public FindUserObserver(String password) {
            this.password = password;
        }

        @Override
        public void onSubscribe(Disposable disposable) {
        }

        @Override
        public void onNext(User user) {
            if (user.hasPassword(password)) {
                loginRouter.success();
                return;
            }

            passwordErrorView.wrong();
        }

        @Override
        public void onError(Throwable e) {
            usernameErrorView.invalid();
            progressView.hide();
        }

        @Override
        public void onComplete() {
            progressView.hide();
        }
    }
}

