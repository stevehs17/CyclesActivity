package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.Const;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VolumeTest {

    @Test
    public void newVolume_Succeeds() {
        Cycle c = new Cycle(Cycle.MIN_VOLUME,
                Cycle.MIN_TIME, Cycle.MAX_TIME);
        List<Cycle> cs = new ArrayList<>();
        cs.add(c);
        Volume v = new Volume(Const.MIN_DATABASE_ID, cs);
        assertEquals(Const.MIN_DATABASE_ID, v.id());
        cs = v.cycles();
        Cycle cout = cs.get(0);
        assertEquals(Cycle.MIN_VOLUME, cout.volumeMl());
        assertEquals(Cycle.MIN_TIME, cout.brewSeconds());
        assertEquals(Cycle.MAX_TIME, cout.vacuumSeconds());
    }

    @Test
    public void newVolumeNoId_Succeeds() {
        Cycle c = new Cycle(Cycle.MIN_VOLUME,
                Cycle.MIN_TIME, Cycle.MAX_TIME);
        List<Cycle> cs = new ArrayList<>();
        cs.add(c);
        Volume v = new Volume(cs);
        assertEquals(Const.NULL_DATABASE_ID, v.id());
        cs = v.cycles();
        Cycle cout = cs.get(0);
        assertEquals(Cycle.MIN_VOLUME, cout.volumeMl());
        assertEquals(Cycle.MIN_TIME, cout.brewSeconds());
        assertEquals(Cycle.MAX_TIME, cout.vacuumSeconds());
    }

    @Test
    public void newVolumeNullCycles_Fails() {
        try {
            Volume v = new Volume(Const.MIN_DATABASE_ID, null);
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void newVolumeEmptyCycles_Fails() {
        try {
             Volume v = new Volume(Const.MIN_DATABASE_ID, new ArrayList<Cycle>());
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void totalVolume_Succeeds() {
        List<Cycle> cycles = new ArrayList<>();
        cycles.add(new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_VOLUME, Cycle.MIN_TIME));
        cycles.add(new Cycle(Cycle.MAX_VOLUME, Cycle.MIN_VOLUME, Cycle.MIN_TIME));
        Volume v = new Volume(Const.MIN_DATABASE_ID, cycles);
        assertEquals(Cycle.MIN_VOLUME + Cycle.MAX_VOLUME, v.totalVolume());
    }
}
