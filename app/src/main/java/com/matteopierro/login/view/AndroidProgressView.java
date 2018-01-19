package com.matteopierro.login.view;

import android.view.View;

public class AndroidProgressView implements ProgressView {
    private View loginFormView;
    private View progressView;

    public AndroidProgressView(View loginFormView, View progressView) {
        this.loginFormView = loginFormView;
        this.progressView = progressView;
    }

    @Override
    public void show() {
        loginFormView.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        loginFormView.setVisibility(View.VISIBLE);
        progressView.setVisibility(View.GONE);
    }
}
