package com.matteopierro.login.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Boolean> progress = new MutableLiveData<>();
    private MutableLiveData<FieldError> usernameError = new MutableLiveData<>();
    private MutableLiveData<Boolean> passwordError = new MutableLiveData<>();

    private UserRepository users;
    private FindUserObserver findUserObserver = new FindUserObserver();

    public LoginViewModel(UserRepository users) {
        this.users = users;
    }

    public LiveData<Boolean> progress() {
        return progress;
    }

    public LiveData<FieldError> usernameError() {
        return usernameError;
    }

    public LiveData<Boolean> passwordError() {
        return passwordError;
    }

    public void login(String username, String password) {
        cleanErrors();

        if (fieldsAreEmpty(username, password)) {
            notifyErrors(username, password);
            return;
        }

        progress.setValue(true);

        users.findBy(username).subscribe(findUserObserver);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        findUserObserver.dispose();
    }

    private void notifyErrors(String username, String password) {
        if (username.isEmpty()) {
            usernameError.setValue(FieldError.EMPTY);
        }

        if (password.isEmpty()) {
            passwordError.setValue(true);
        }
    }

    private boolean fieldsAreEmpty(String username, String password) {
        return username.isEmpty() || password.isEmpty();
    }


    private void cleanErrors() {
        usernameError.setValue(FieldError.NO_ERROR);
        passwordError.setValue(false);
    }

    private class FindUserObserver implements Observer<User> {
        private Disposable disposable;

        @Override
        public void onSubscribe(Disposable disposable) {
            this.disposable = disposable;
        }

        @Override
        public void onNext(User value) {

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

        public void dispose() {
            if (disposable != null)
                disposable.dispose();
        }
    }
}
