package com.matteopierro.login.view;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.matteopierro.login.R;

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
        onView(withId(R.id.username))
                .perform(typeText("valid_user"), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText("valid_password"), closeSoftKeyboard());

        onView(withId(R.id.sign_in_button)).perform(click());

        onView(withId(R.id.login_success))
                .check(matches(withText(("Login Success"))));
    }
}