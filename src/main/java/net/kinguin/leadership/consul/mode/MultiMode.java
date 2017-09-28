package net.kinguin.leadership.consul.mode;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.kinguin.leadership.consul.Cluster;
import rx.Observable;
import rx.subjects.PublishSubject;

public class MultiMode extends ClusterMode implements Runnable {
    private Cluster cluster;
    private long attemptsFrequencySeconds;
    private boolean gotLeadership;
    private boolean wasLeader = false;
    private PublishSubject<Object> subject = PublishSubject.create();

    public MultiMode(
        Cluster cluster,
        long attemptsFrequencySeconds
    ) {
        this.cluster = cluster;
        this.attemptsFrequencySeconds = attemptsFrequencySeconds;

        try {
            upkeep();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void upkeep() throws InterruptedException {
        Thread upkeep = new Thread(this);
        upkeep.setDaemon(true);
        upkeep.start();
    }

    public synchronized void run() {
        while (true) {
            try {
                wait(attemptsFrequencySeconds * 1000);
                gotLeadership = cluster.claimLeadership();
                subject.onNext(new String("elected"));

                if (false == wasLeader) {
                    subject.onNext(new String("firsttimer"));
                    wasLeader = true;
                }
            } catch (Exception e) {
                subject.onError(e);
                gotLeadership = false;
            }
        }
    }

    public synchronized boolean isLeader() {
        return gotLeadership;
    }

    @Override
    public Observable<Object> asObservable() {
        return subject;
    }
}
