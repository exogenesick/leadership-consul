package net.kinguin.leadership.consul.mode;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SingleModeTest {
    private SingleMode singleMode;

    @Before
    public void setUp() throws Exception {
        singleMode = new SingleMode();
    }

    @Test
    public void should_emit_first_time_leader_elected_event() throws Exception {
        assertTrue(singleMode.asObservable()
            .map(String::valueOf)
            .toBlocking()
            .single()
            .equals("firsttimer"));
    }

    @Test
    public void should_always_return_leader_elected() throws Exception {
        assertTrue(singleMode.isLeader());
    }
}