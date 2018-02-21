package com.matteopierro.login.view;

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.matteopierro.login.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class LoginUI {

    private LoginActivity loginActivity;

    public LoginUI(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void login(String username, String password) {
        insertUsername(username);
        insertPassword(password);
        clickLogin();
    }

    private void insertUsername(String username) {
        onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText(username), ViewActions.closeSoftKeyboard());
    }

    private void insertPassword(String password) {
        onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());
    }

    private void clickLogin() {
        onView(ViewMatchers.withId(R.id.sign_in_button)).perform(ViewActions.click());
    }

    public void confirmLoginSuccess() {
        onView(withId(R.id.login_success))
                .check(matches(withText(("Login Success"))));
    }

    public String getUsernameError() {
        TextInputLayout usernameLayout = loginActivity.findViewById(R.id.username_layout);
        return usernameLayout.getError().toString();
    }
}