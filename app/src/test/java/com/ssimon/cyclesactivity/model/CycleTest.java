package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.Const;

import org.junit.Test;
import static org.junit.Assert.*;

public class CycleTest {

    @Test
    public void new_Cycle_min_Succeeds() throws Exception {
        Cycle c = new Cycle(Cycle.MIN_VOLUME,
                Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        assertEquals(Cycle.MIN_VOLUME, c.volumeMl());
        assertEquals(Cycle.MIN_BREWTIME, c.brewSeconds());
        assertEquals(Cycle.MIN_VACUUMTIME, c.vacuumSeconds());
    }

    @Test
    public void new_Cycle_max_Succeeds() throws Exception {
        Cycle c = new Cycle(Cycle.MAX_VOLUME,
                Cycle.MAX_BREWTIME, Cycle.MAX_VACUUMTIME);
        assertEquals(Cycle.MAX_VOLUME, c.volumeMl());
        assertEquals(Cycle.MAX_BREWTIME, c.brewSeconds());
        assertEquals(Cycle.MAX_VACUUMTIME, c.vacuumSeconds());
    }

    @Test
    public void new_Cycle_UNSET_DATABASE_ID_Succeeds() throws Exception {
        Cycle c = new Cycle(Cycle.MIN_VOLUME,
                Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        assertEquals(Cycle.MIN_VOLUME, c.volumeMl());
        assertEquals(Cycle.MIN_BREWTIME, c.brewSeconds());
        assertEquals(Cycle.MIN_VACUUMTIME, c.vacuumSeconds());
    }

    @Test
    public void new_Cycle_volume_Fails() throws Exception {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME - 1,
                    Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Cycle_brewtime_Fails() throws Exception {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME,
                    Cycle.MIN_BREWTIME - 1, Cycle.MIN_VACUUMTIME);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Cycle_vacuumtime_Fails() throws Exception {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME,
                    Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME - 1);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Cycle_volume_overmax_Fails() throws Exception {
        try {
            Cycle c = new Cycle(Cycle.MAX_VOLUME + 1,
                    Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Cycle_brewtime_overmax_Fails() throws Exception {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME,
                    Cycle.MAX_BREWTIME +1, Cycle.MIN_VACUUMTIME);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Cycle_vacuumtime_overmax_Fails() throws Exception {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME,
                    Cycle.MIN_BREWTIME, Cycle.MAX_VACUUMTIME + 1);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }
}
