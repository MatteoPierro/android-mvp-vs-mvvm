package com.matteopierro.login.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.viewmodel.FieldError;
import com.matteopierro.login.viewmodel.LoginViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static com.matteopierro.login.viewmodel.FieldError.EMPTY;
import static com.matteopierro.login.viewmodel.FieldError.INVALID_USERNAME;
import static com.matteopierro.login.viewmodel.FieldError.NO_ERROR;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Boolean> progress;
    @Mock
    private Observer<FieldError> usernameError;
    @Mock
    private Observer<Boolean> passwordError;

    @Mock
    private UserRepository users;

    private LoginViewModel loginViewModel;

    @Before
    public void setUp() throws Exception {
        when(users.findBy(anyString())).thenReturn(Observable.<User>empty());
        loginViewModel = new LoginViewModel(users);
        loginViewModel.progress().observeForever(progress);
        loginViewModel.usernameError().observeForever(usernameError);
        loginViewModel.passwordError().observeForever(passwordError);
    }

    @Test
    public void should_be_in_progress_during_login_attempt() throws Exception {
        loginViewModel.login("username", "password");

        InOrder inOrder = inOrder(progress);
        inOrder.verify(progress).onChanged(true);
        inOrder.verify(progress).onChanged(false);
    }

    @Test
    public void should_clean_all_errors() throws Exception {
        loginViewModel.login("username", "password");

        verify(usernameError).onChanged(NO_ERROR);
        verify(passwordError).onChanged(false);
    }

    @Test
    public void username_error_when_username_is_empty() throws Exception {
        loginViewModel.login("", "password");

        verify(usernameError).onChanged(EMPTY);
    }

    @Test
    public void password_error_when_username_is_empty() throws Exception {
        loginViewModel.login("username", "");

        verify(passwordError).onChanged(true);
    }

    @Test
    public void username_error_when_username_is_unknown() throws Exception {
        Observable<User> error = Observable.error(new IllegalArgumentException("unknown username"));
        when(users.findBy("unknown username")).thenReturn(error);

        loginViewModel.login("unknown username", "password");

        verify(usernameError).onChanged(INVALID_USERNAME);
    }
}