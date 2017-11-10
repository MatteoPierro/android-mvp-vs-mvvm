package com.matteopierro.login.presenter;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.view.LoginRouter;
import com.matteopierro.login.view.PasswordErrorView;
import com.matteopierro.login.view.ProgressView;
import com.matteopierro.login.view.UsernameErrorView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    private ProgressView progressView;
    @Mock
    private UsernameErrorView usernameErrorView;
    @Mock
    private PasswordErrorView passwordErrorView;
    @Mock
    private LoginRouter loginRouter;
    @Mock
    private UserRepository users;

    private LoginPresenter loginPresenter;

    @Before
    public void setUp() throws Exception {
        when(users.findBy(anyString())).thenReturn(Observable.<User>empty());
        loginPresenter = new LoginPresenter(progressView, usernameErrorView, passwordErrorView, loginRouter, users);
    }

    @Test
    public void should_be_in_progress_during_login_attempt() throws Exception {
        loginPresenter.login("username", "password");

        InOrder inOrder = Mockito.inOrder(progressView);
        inOrder.verify(progressView).show();
        inOrder.verify(progressView).hide();
    }

    @Test
    public void should_clean_all_errors() throws Exception {
        loginPresenter.login("username", "password");

        verify(usernameErrorView).clean();
        verify(passwordErrorView).clean();
    }

    @Test
    public void username_error_when_username_is_empty() throws Exception {
        loginPresenter.login("", "password");

        verify(usernameErrorView).required();
    }

    @Test
    public void password_error_when_username_is_empty() throws Exception {
        loginPresenter.login("username", "");

        verify(passwordErrorView).required();
    }

    @Test
    public void username_error_when_username_is_unknown() throws Exception {
        Observable<User> error = Observable.error(new IllegalArgumentException("unknown username"));
        when(users.findBy("unknown username")).thenReturn(error);

        loginPresenter.login("unknown username", "password");

        verify(usernameErrorView).invalid();
    }

    @Test
    public void password_error_when_password_is_wrong() throws Exception {
        Observable<User> user = Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                emitter.onNext(new User("username", "password"));
                emitter.onComplete();
            }
        });
        when(users.findBy("username")).thenReturn(user);

        loginPresenter.login("username", "wrong password");

        passwordErrorView.wrong();
    }

    @Test
    public void login_success_when_username_and_password_are_correct() throws Exception {
        Observable<User> user = Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                emitter.onNext(new User("username", "password"));
                emitter.onComplete();
            }
        });
        when(users.findBy("username")).thenReturn(user);

        loginPresenter.login("username", "password");

        verify(loginRouter).success();
    }
}