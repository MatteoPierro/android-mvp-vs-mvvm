package com.matteopierro.login.presenter;

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

    @Mock
    private LoginView view;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view);
    }

    @Test
    public void shouldDisplayEmptyUsernameErrorWhenUsernameEmpty() {
        presenter.login("");

        verify(view).displayEmptyUserNameError();
    }

    @Test
    public void shouldNotDisplayEmptyUsernameErrorWhenUsernameIsNotEmpty() {
        presenter.login("username");

        verify(view, never()).displayEmptyUserNameError();
    }
}
