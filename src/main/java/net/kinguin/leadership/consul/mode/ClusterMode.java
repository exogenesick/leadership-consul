package net.kinguin.leadership.consul.mode;

import rx.Observable;

public interface ClusterMode {
    boolean isLeader();
    Observable<Object> asObservable();
}