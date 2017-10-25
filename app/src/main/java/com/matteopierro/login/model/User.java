package com.matteopierro.login.model;

public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean hasPassword(String password) {
        return this.password.equals(password);
    }
}
