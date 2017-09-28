package net.kinguin.leadership.consul.mode;

import rx.Observable;

public class SingleMode extends ClusterMode {
    @Override
    public boolean isLeader() {
        return true;
    }

    @Override
    public Observable<Object> asObservable() {
        return Observable.just("firsttimer");
    }
}