package com.matteopierro.login.view;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AndroidProgressViewInstrumentationTest {

    private View loginFormView;
    private View progressView;
    private AndroidProgressView androidProgressView;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getContext();
        loginFormView = new View(context);
        progressView = new View(context);
        androidProgressView = new AndroidProgressView(loginFormView, progressView);
    }

    @Test
    public void whenAndroidProgressViewIsShownThenLoginFormVisibilityIsGone() throws Exception {
        androidProgressView.show();

        assertThat(loginFormView.getVisibility(), is(GONE));
    }

    @Test
    public void whenAndroidProgressViewIsShownThenProgressViewIsVisible() throws Exception {
        androidProgressView.show();

        assertThat(progressView.getVisibility(), is(VISIBLE));
    }

    @Test
    public void whenAndroidProgressViewIsHiddenThenLoginViewIsVisible() throws Exception {
        androidProgressView.hide();

        assertThat(loginFormView.getVisibility(), is(VISIBLE));
    }

    @Test
    public void whenAndroidProgressViewIsHiddenThenProgressViewVisibilityIsGone() throws Exception {
        androidProgressView.hide();

        assertThat(progressView.getVisibility(), is(GONE));
    }
}