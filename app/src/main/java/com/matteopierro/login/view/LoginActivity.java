package com.matteopierro.login.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.matteopierro.login.R;
import com.matteopierro.login.model.FakeUserRepository;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @BindView(R.id.username_layout)
    public TextInputLayout usernameLayout;

    @BindView(R.id.username)
    public EditText usernameTextView;

    @BindView(R.id.password_layout)
    public TextInputLayout passwordLayout;

    @BindView(R.id.password)
    public EditText passwordTextView;

    @BindView(R.id.login_progress)
    public View progressView;

    @BindView(R.id.login_form)
    public View loginFormView;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ProgressView androidProgressView = new AndroidProgressView(loginFormView, progressView);
        UsernameErrorView usernameErrorView = new AndroidUsernameErrorView();
        PasswordErrorView passwordErrorView = new AndroidPasswordErrorView();
        LoginRouter loginRouter = new AndroidLoginRouter();
        UserRepository users = new FakeUserRepository();

        loginPresenter = new LoginPresenter(androidProgressView, usernameErrorView, passwordErrorView, loginRouter, users);
    }

    @OnClick(R.id.sign_in_button)
    public void login() {
        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        loginPresenter.login(username, password);
    }

    private class AndroidUsernameErrorView implements UsernameErrorView {
        @Override
        public void required() {
            usernameLayout.setError(getString(R.string.error_field_required));
        }

        @Override
        public void clean() {
            usernameLayout.setErrorEnabled(false);
        }

        @Override
        public void invalid() {
            usernameLayout.setError(getString(R.string.error_invalid_username));
        }
    }

    private class AndroidPasswordErrorView implements PasswordErrorView {
        @Override
        public void required() {
            passwordLayout.setError(getString(R.string.error_field_required));
        }

        @Override
        public void clean() {
            passwordLayout.setErrorEnabled(false);
        }

        @Override
        public void wrong() {
            passwordLayout.setError(getString(R.string.error_wrong_password));
        }
    }

    private class AndroidLoginRouter implements LoginRouter {
        @Override
        public void success() {
            Intent intent = new Intent(LoginActivity.this, SuccessLoginActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    }
}

