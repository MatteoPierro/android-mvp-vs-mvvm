package com.matteopierro.login.presenter;

import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.view.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    public static final String EMPTY_FIELD = "";
    public static final String A_USERNAME = "username";
    public static final String A_PASSWORD = "password";
    @Mock
    private LoginView view;
    @Mock
    private UserRepository repository;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, repository);
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
}
