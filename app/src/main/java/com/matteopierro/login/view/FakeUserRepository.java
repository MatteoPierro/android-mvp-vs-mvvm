package com.matteopierro.login.view;

import com.matteopierro.login.model.User;
import com.matteopierro.login.model.UserRepository;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FakeUserRepository implements UserRepository {
    @Override
    public Observable<User> findBy(String username) {

        ObservableOnSubscribe<User> findByUsername = new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                Thread.sleep(3000);
                emitter.onError(new IllegalArgumentException("unknown username"));
                emitter.onComplete();
            }
        };

        return Observable.create(findByUsername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
