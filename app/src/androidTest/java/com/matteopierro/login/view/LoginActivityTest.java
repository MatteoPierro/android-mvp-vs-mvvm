package com.matteopierro.login.view;

import android.support.design.widget.TextInputLayout;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.matteopierro.login.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);
    
    @Test
    public void loginSuccessForValidCredentials() throws Exception {
        insertUsername("valid_user");
        insertPassword("valid_password");

        clickLogin();

        loginSuccess();
    }

    @Test
    public void unknownUsernameForAnInvalidUsername() throws Exception {
        insertUsername("invalid_user");
        insertPassword("valid_password");

        clickLogin();

        usernameHasError("This username is invalid");
    }

    private void insertUsername(String username) {
        onView(withId(R.id.username))
                .perform(typeText(username), closeSoftKeyboard());
    }

    private void insertPassword(String password) {
        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());
    }

    private void clickLogin() {
        onView(withId(R.id.sign_in_button)).perform(click());
    }

    private void loginSuccess() {
        onView(withId(R.id.login_success))
                .check(matches(withText(("Login Success"))));
    }

    private void usernameHasError(String usernameError) {
        onView(withId(R.id.username_layout))
                .check(matches(withError(usernameError)));
    }

    private static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                if (view instanceof TextInputLayout) {
                    return ((TextInputLayout)view).getError().toString().equals(expected);
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Not found error message [" + expected + "]");
            }
        };
    }
}