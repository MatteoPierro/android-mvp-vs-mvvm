package com.matteopierro.login.presenter;

import com.matteopierro.login.view.LoginView;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {
    @Test
    public void shouldDisplayEmptyUsernameErrorWhenUsernameEmpty() {
        LoginView view = mock(LoginView.class);
        LoginPresenter presenter = new LoginPresenter(view);

        presenter.login("");

        verify(view).displayEmptyUserNameError();
    }
}
