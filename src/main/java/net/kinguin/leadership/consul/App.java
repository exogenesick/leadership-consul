package net.kinguin.leadership.consul;

import net.kinguin.leadership.consul.config.ClusterConfiguration;
import net.kinguin.leadership.consul.mode.ClusterMode;
import net.kinguin.leadership.consul.mode.MultiMode;

public class App {
    public static void main(String[] args) {
        Session session = new Session(15);
        Cluster cluster = new Cluster(session, new ClusterConfiguration());

        ClusterMode mode = new MultiMode(cluster, 5);
        mode.asObservable().subscribe(s -> System.out.println(s));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {}
            }
        }).start();
    }
}
