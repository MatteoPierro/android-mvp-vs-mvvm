package com.matteopierro.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.matteopierro.login.R;
import com.matteopierro.login.model.FakeUserRepository;
import com.matteopierro.login.model.UserRepository;
import com.matteopierro.login.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

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

        PasswordErrorView passwordErrorView = new AndroidPasswordErrorView();
        UsernameErrorView usernameErrorView = new AndroidUsernameErrorView();
        LoginRouter loginRouter = new AndroidLoginRouter();
        UserRepository userRepository = new FakeUserRepository();
        loginPresenter = new LoginPresenter(passwordErrorView, usernameErrorView, loginRouter, userRepository);
    }

    @OnClick(R.id.sign_in_button)
    public void login() {
        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        loginPresenter.login(username, password);
    }

    private class AndroidPasswordErrorView implements PasswordErrorView {
        @Override
        public void empty() {
            passwordLayout.setError(getString(R.string.error_field_required));
        }

        @Override
        public void clean() {
            passwordLayout.setErrorEnabled(false);
        }
    }

    private class AndroidUsernameErrorView implements UsernameErrorView {
        @Override
        public void empty() {
            usernameLayout.setError(getString(R.string.error_field_required));
        }

        @Override
        public void clean() {
            usernameLayout.setErrorEnabled(false);
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

