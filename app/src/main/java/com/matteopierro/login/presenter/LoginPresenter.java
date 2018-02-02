package com.matteopierro.login.presenter;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.view.LoginRouter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginPresenter {

    private LoginRouter loginRouter;
    private UserRepository repository;

    public LoginPresenter(LoginRouter loginRouter, UserRepository repository) {
        this.loginRouter = loginRouter;
        this.repository = repository;
    }

    public void login(String username, String password) {
        FindUserObserver findUserObserver = new FindUserObserver(password);
        repository.findBy(username).subscribe(findUserObserver);
    }

    private class FindUserObserver implements Observer<User> {
        private String password;

        public FindUserObserver(String password) {
            this.password = password;
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(User user) {
            if(user.hasPassword(password)) {
                loginRouter.success();
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
