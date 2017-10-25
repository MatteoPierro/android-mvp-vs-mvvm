package com.matteopierro.login.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.matteopierro.login.R;
import com.matteopierro.login.viewmodel.FieldError;
import com.matteopierro.login.viewmodel.LoginViewModel;
import com.matteopierro.login.viewmodel.LoginViewModelFactory;

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

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        LoginViewModelFactory loginViewModelFactory = new LoginViewModelFactory();
        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel.class);

        loginViewModel.progress().observe(this, new ProgressObserver());
        loginViewModel.usernameError().observe(this, new FieldErrorObserver(usernameLayout));
        loginViewModel.passwordError().observe(this, new FieldErrorObserver(passwordLayout));
    }

    @OnClick(R.id.sign_in_button)
    public void login() {
        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        loginViewModel.login(username, password);
    }

    private class ProgressObserver implements Observer<Boolean> {

        @Override
        public void onChanged(Boolean inProgress) {
            int progressVisibility = inProgress ? View.VISIBLE : View.GONE;
            int loginVisibility = inProgress ? View.GONE : View.VISIBLE;
            loginFormView.setVisibility(loginVisibility);
            progressView.setVisibility(progressVisibility);
        }
    }

    private class FieldErrorObserver implements Observer<FieldError> {

        private TextInputLayout fieldLayout;

        private FieldErrorObserver(TextInputLayout fieldLayout) {
            this.fieldLayout = fieldLayout;
        }

        @Override
        public void onChanged(FieldError error) {
            fieldLayout.setError(getString(error.stringId()));
            fieldLayout.setErrorEnabled(error.isVisible());
        }
    }
}