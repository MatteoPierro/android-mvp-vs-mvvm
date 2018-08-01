package com.matteopierro.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.matteopierro.login.R;
import com.matteopierro.login.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity implements LoginView {

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

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this, new FakeUserRepository());
    }

    @OnClick(R.id.sign_in_button)
    public void login() {
        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        presenter.login(username, password);
    }

    @Override
    public void displayEmptyUserNameError() {
        usernameLayout.setError("Username should not be empty");
    }

    @Override
    public void displayUnknownUsernameError() {
        usernameLayout.setError("Unknown username");
    }

    @Override
    public void displayEmptyPasswordError() {
        passwordLayout.setError("Password should not be empty");
    }

    @Override
    public void displayIncorrectPasswordError() {
        passwordLayout.setError("Password is incorrect");
    }

    @Override
    public void displayLoginSuccess() {
        startActivity(new Intent(this, SuccessLoginActivity.class));
    }

    @Override
    public void displayProgressIndicator() {
        progressView.setVisibility(VISIBLE);
        loginFormView.setVisibility(GONE);
    }

    @Override
    public void hideProgressIndicator() {
        progressView.setVisibility(GONE);
        loginFormView.setVisibility(VISIBLE);
    }

    @Override
    public void clearErrors() {
        usernameLayout.setError("");
        passwordLayout.setError("");
    }
}

