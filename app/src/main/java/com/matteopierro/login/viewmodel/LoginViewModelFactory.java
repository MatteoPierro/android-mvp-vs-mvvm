package com.matteopierro.login.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.matteopierro.login.view.FakeUserRepository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(new FakeUserRepository());
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }

}
