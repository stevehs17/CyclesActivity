package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.Const;

import org.junit.Test;
import static org.junit.Assert.*;

public class CycleTest {

    @Test
    public void newCycleMin_Succeeds() {
        Cycle c = new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_BREWTIME,
                Cycle.MIN_VACUUMTIME);
        assertEquals(Cycle.MIN_VOLUME, c.volumeMl());
        assertEquals(Cycle.MIN_BREWTIME, c.brewSeconds());
        assertEquals(Cycle.MIN_VACUUMTIME, c.vacuumSeconds());
    }

    @Test
    public void newCycleMax_Succeeds() {
        Cycle c = new Cycle(Cycle.MAX_VOLUME, Cycle.MAX_BREWTIME,
                Cycle.MAX_VACUUMTIME);
        assertEquals(Cycle.MAX_VOLUME, c.volumeMl());
        assertEquals(Cycle.MAX_BREWTIME, c.brewSeconds());
        assertEquals(Cycle.MAX_VACUUMTIME, c.vacuumSeconds());
    }

    @Test
    public void newCycleVolumeUnderMin_Fails() {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME - 1,
                    Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_CycleBrewTimeUnderMin_Fails() {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME,
                    Cycle.MIN_BREWTIME - 1, Cycle.MIN_VACUUMTIME);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_CycleVacuumTimeUnderMin_Fails() {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME,
                    Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME - 1);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_CycleVolumeOverMax_Fails() {
        try {
            Cycle c = new Cycle(Cycle.MAX_VOLUME + 1,
                    Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_CycleBrewtimeOverMax_Fails() {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME,
                    Cycle.MAX_BREWTIME + 1, Cycle.MIN_VACUUMTIME);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_CycleVacuumTimeOverMax_Fails() {
        try {
            Cycle c = new Cycle(Cycle.MIN_VOLUME,
                    Cycle.MIN_BREWTIME, Cycle.MAX_VACUUMTIME + 1);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }
}
