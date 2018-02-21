package com.matteopierro.login.view;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityUITest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);
    private LoginUI loginUI;

    @Before
    public void setUp() throws Exception {
        LoginActivity loginActivity = activityRule.getActivity();
        loginUI = new LoginUI(loginActivity);
    }

    @Test
    public void loginSuccessForValidCredentials() throws Exception {
        loginUI.login("valid_user", "valid_password");

        loginUI.confirmLoginSuccess();
    }

    @Test
    public void unknownUsernameForAnInvalidUsername() throws Exception {
        loginUI.login("invalid_user", "valid_password");

        String errorMessage = loginUI.getUsernameError();

        assertThat(errorMessage, is("This username is invalid"));
    }
}