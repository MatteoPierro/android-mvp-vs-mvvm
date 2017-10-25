package com.matteopierro.login.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.matteopierro.login.viewmodel.FieldError.REQUIRED;
import static com.matteopierro.login.viewmodel.FieldError.NO_ERROR;
import static com.matteopierro.login.viewmodel.FieldError.WRONG_PASSWORD;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Boolean> progress = new MutableLiveData<>();
    private MutableLiveData<FieldError> usernameError = new MutableLiveData<>();
    private MutableLiveData<FieldError> passwordError = new MutableLiveData<>();

    private UserRepository users;

    public LoginViewModel(UserRepository users) {
        this.users = users;
    }

    public LiveData<Boolean> progress() {
        return progress;
    }

    public LiveData<FieldError> usernameError() {
        return usernameError;
    }

    public LiveData<FieldError> passwordError() {
        return passwordError;
    }

    public void login(String username, String password) {
        cleanErrors();

        if (fieldsAreEmpty(username, password)) {
            notifyErrors(username, password);
            return;
        }

        progress.setValue(true);

        FindUserObserver findUserObserver = new FindUserObserver(password);
        users.findBy(username).subscribe(findUserObserver);
    }

    private void notifyErrors(String username, String password) {
        if (username.isEmpty()) {
            usernameError.setValue(REQUIRED);
        }

        if (password.isEmpty()) {
            passwordError.setValue(REQUIRED);
        }
    }

    private boolean fieldsAreEmpty(String username, String password) {
        return username.isEmpty() || password.isEmpty();
    }


    private void cleanErrors() {
        usernameError.setValue(NO_ERROR);
        passwordError.setValue(NO_ERROR);
    }

    private class FindUserObserver implements Observer<User> {
        private String password;

        public FindUserObserver(String password) {
            this.password = password;
        }

        @Override
        public void onSubscribe(Disposable disposable) { }

        @Override
        public void onNext(User user) {
            if (!user.hasPassword(password)) {
                passwordError.setValue(WRONG_PASSWORD);
            }
        }

        @Override
        public void onError(Throwable e) {
            usernameError.setValue(FieldError.INVALID_USERNAME);
            progress.setValue(false);
        }

        @Override
        public void onComplete() {
            progress.setValue(false);
        }
    }
}
