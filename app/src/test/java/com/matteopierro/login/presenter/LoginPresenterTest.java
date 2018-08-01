package com.matteopierro.login.presenter;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.view.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    private static final String EMPTY_FIELD = "";
    private static final String A_USERNAME = "username";
    private static final String A_PASSWORD = "password";
    private static final String CORRECT_USERNAME = "correct username";
    private static final String CORRECT_PASSWORD = "correct password";
    private static final String WRONG_PASSWORD = "wrong password";
    private static final String UNKNOWN_USERNAME = "unknown username";
    @Mock
    private LoginView view;
    @Mock
    private UserRepository repository;
    private LoginPresenter presenter;

    @Before
    public void setUp() {
        presenter = new LoginPresenter(view, repository);
        when(repository.findBy(anyString())).thenReturn(Observable.<User>empty());
    }

    @Test
    public void shouldDisplayEmptyUsernameErrorWhenUsernameEmpty() {
        presenter.login(EMPTY_FIELD, A_PASSWORD);

        verify(view).displayEmptyUserNameError();
    }

    @Test
    public void shouldNotDisplayEmptyUsernameErrorWhenUsernameIsNotEmpty() {
        presenter.login(A_USERNAME, A_PASSWORD);

        verify(view, never()).displayEmptyUserNameError();
    }

    @Test
    public void shouldDisplayEmptyPasswordErrorWhenPasswordEmpty() {
        presenter.login(A_USERNAME, EMPTY_FIELD);

        verify(view).displayEmptyPasswordError();
    }

    @Test
    public void shouldNotDisplayEmptyPasswordErrorWhenPasswordIsNotEmpty() {
        presenter.login(A_USERNAME, A_PASSWORD);

        verify(view, never()).displayEmptyPasswordError();
    }

    @Test
    public void shouldClearErrorsAtLogin() {
        presenter.login(A_USERNAME, A_PASSWORD);

        verify(view).clearErrors();
    }

    @Test
    public void shouldRequestUserWhenUsernameAndPasswordAreNotEmpty() {
        presenter.login(A_USERNAME, A_PASSWORD);

        verify(repository).findBy(A_USERNAME);
    }

    @Test
    public void shouldNotRequestUserWhenUsernameOrPasswordIsEmpty() {
        presenter.login(EMPTY_FIELD, EMPTY_FIELD);

        verify(repository, never()).findBy(EMPTY_FIELD);
    }

    @Test
    public void shouldDisplayLoginSuccessWhenUsernameAndPasswordAreCorrect() {
        Observable<User> observableUser = anObservableUserWith(CORRECT_USERNAME, CORRECT_PASSWORD);
        when(repository.findBy(CORRECT_USERNAME)).thenReturn(observableUser);

        presenter.login(CORRECT_USERNAME, CORRECT_PASSWORD);

        verify(view).displayLoginSuccess();
    }

    @Test
    public void shouldDisplayPasswordIncorrectErrorWhenGivenWrongPassword() {
        Observable<User> observableUser = anObservableUserWith(CORRECT_USERNAME, CORRECT_PASSWORD);
        when(repository.findBy(CORRECT_USERNAME)).thenReturn(observableUser);

        presenter.login(CORRECT_USERNAME, WRONG_PASSWORD);

        verify(view).displayIncorrectPasswordError();
        verify(view,never()).displayLoginSuccess();
    }

    @Test
    public void shouldDisplayUsernameUnknownErrorWhenThereIsNoUserWithGivenUsername() {
        Observable<User> observableError = Observable.error(new IllegalArgumentException(UNKNOWN_USERNAME));
        when(repository.findBy(UNKNOWN_USERNAME)).thenReturn(observableError);

        presenter.login(UNKNOWN_USERNAME, A_PASSWORD);

        verify(view).displayUnknownUsernameError();
    }

    @Test
    public void shouldDisplayProgressIndicatorWhenLoginAttempted() {
        presenter.login(A_USERNAME,A_PASSWORD);

        verify(view).displayProgressIndicator();
    }

    private Observable<User> anObservableUserWith(final String username, final String password) {
        return Observable.create(new ObservableOnSubscribe<User>() {
                @Override
                public void subscribe(ObservableEmitter<User> emitter) {
                    emitter.onNext(new User(username, password));
                    emitter.onComplete();
                }
            });
    }
}
