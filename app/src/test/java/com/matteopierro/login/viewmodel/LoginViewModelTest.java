package com.matteopierro.login.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static com.matteopierro.login.viewmodel.FieldError.INVALID_USERNAME;
import static com.matteopierro.login.viewmodel.FieldError.NO_ERROR;
import static com.matteopierro.login.viewmodel.FieldError.REQUIRED;
import static com.matteopierro.login.viewmodel.FieldError.WRONG_PASSWORD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Boolean> progress;

    @Mock
    private UserRepository users;

    private LoginViewModel loginViewModel;

    @Before
    public void setUp() throws Exception {
        when(users.findBy(anyString())).thenReturn(Observable.<User>empty());
        loginViewModel = new LoginViewModel(users);
        loginViewModel.progress().observeForever(progress);
    }

    @Test
    public void should_clean_all_errors() throws Exception {
        loginViewModel.login("username", "password");

        assertEquals(NO_ERROR, loginViewModel.usernameError().getValue());
        assertEquals(NO_ERROR, loginViewModel.passwordError().getValue());
    }

    @Test
    public void username_error_when_username_is_empty() throws Exception {
        loginViewModel.login("", "password");

        assertThat(loginViewModel.usernameError().getValue(), is(REQUIRED));
    }

    @Test
    public void password_error_when_username_is_empty() throws Exception {
        loginViewModel.login("username", "");

        assertThat(loginViewModel.passwordError().getValue(), is(REQUIRED));
    }

    @Test
    public void username_error_when_username_is_unknown() throws Exception {
        Observable<User> error = Observable.error(new IllegalArgumentException("unknown username"));
        when(users.findBy("unknown username")).thenReturn(error);

        loginViewModel.login("unknown username", "password");

        assertThat(loginViewModel.usernameError().getValue(), is(INVALID_USERNAME));
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

        loginViewModel.login("username", "wrong password");

        assertThat(loginViewModel.passwordError().getValue(), is(WRONG_PASSWORD));
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

        loginViewModel.login("username", "password");

        assertTrue(loginViewModel.loginSuccess().getValue());
    }

    @Test
    public void should_be_in_progress_during_login_attempt() throws Exception {
        loginViewModel.login("username", "password");

        InOrder inOrder = inOrder(progress);
        inOrder.verify(progress).onChanged(true);
        inOrder.verify(progress).onChanged(false);
    }
}