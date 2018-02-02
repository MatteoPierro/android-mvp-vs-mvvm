package com.matteopierro.login.presenter;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.view.LoginRouter;
import com.matteopierro.login.view.PasswordErrorView;
import com.matteopierro.login.view.UsernameErrorView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    public static final String VALID_USERNAME = "valid_username";
    public static final String VALID_PASSWORD = "valid_password";

    @Mock
    private LoginRouter loginRouter;

    @Mock
    private UserRepository repository;

    @Mock
    private UsernameErrorView usernameErrorView;

    @Mock
    private PasswordErrorView passwordErrorView;

    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(passwordErrorView, usernameErrorView, loginRouter, repository);

        Observable<User> user = Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                emitter.onNext(new User(VALID_USERNAME, VALID_PASSWORD));
                emitter.onComplete();
            }
        });

        when(repository.findBy(VALID_USERNAME)).thenReturn(user);
    }

    @Test
    public void login_success_when_username_and_password_are_correct() throws Exception {


        presenter.login(VALID_USERNAME, VALID_PASSWORD);

        verify(loginRouter).success();
    }

    @Test
    public void username_error_when_username_is_empty() throws Exception {
        presenter.login("", "a password");

        verify(usernameErrorView).empty();
    }

    @Test
    public void password_error_when_username_is_empty() throws Exception {
        presenter.login("valid_user", "");

        verify(passwordErrorView).empty();
    }

    @Test
    public void view_should_clean_all_errors_at_login() throws Exception {
        presenter.login(VALID_USERNAME, VALID_PASSWORD);

        verify(usernameErrorView).clean();
        verify(passwordErrorView).clean();
    }
}