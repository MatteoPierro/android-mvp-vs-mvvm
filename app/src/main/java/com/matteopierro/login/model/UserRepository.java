package com.matteopierro.login.model;

import io.reactivex.Observable;

public interface UserRepository {
    Observable<User> findBy(String username);
}
