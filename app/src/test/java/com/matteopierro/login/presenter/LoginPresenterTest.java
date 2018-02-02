package com.matteopierro.login.presenter;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.view.LoginRouter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    public static final String VALID_USERNAME = "valid_username";
    public static final String VALID_PASSWORD = "valid_password";

    @Test
    public void login_success_when_username_and_password_are_correct() throws Exception {
        LoginRouter loginRouter = mock(LoginRouter.class);
        UserRepository repository = mock(UserRepository.class);
        Observable<User> user = Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                emitter.onNext(new User(VALID_USERNAME, VALID_PASSWORD));
                emitter.onComplete();
            }
        });

        when(repository.findBy(VALID_USERNAME)).thenReturn(user);
        LoginPresenter presenter = new LoginPresenter(loginRouter, repository);

        presenter.login(VALID_USERNAME, VALID_PASSWORD);

        verify(loginRouter).success();
    }
}