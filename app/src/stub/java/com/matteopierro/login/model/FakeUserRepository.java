package com.matteopierro.login.model;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FakeUserRepository implements UserRepository {
    private static final Map<String, User> USERS = new HashMap<>();

    static {
        USERS.put("valid_user", new User("valid_user", "valid_password"));
    }

    @Override
    public Observable<User> findBy(final String username) {

        ObservableOnSubscribe<User> findByUsername = new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {

                if (USERS.containsKey(username)) {
                    emitter.onNext(USERS.get(username));
                    emitter.onComplete();
                    return;
                }

                emitter.onError(new IllegalArgumentException("unknown username"));
            }
        };

        return Observable.create(findByUsername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
