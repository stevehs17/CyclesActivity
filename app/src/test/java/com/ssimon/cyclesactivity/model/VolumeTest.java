package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.Const;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VolumeTest {

    @Test
    public void new_Volume_Succeeds() throws Exception {
        Cycle c = new Cycle(Const.MIN_DATABASE_ID, Cycle.MIN_VOLUME,
                Cycle.MIN_BREWTIME, Cycle.MAX_VACUUMTIME);
        List<Cycle> cs = new ArrayList<>();
        cs.add(c);
        Volume v = new Volume(Const.MIN_DATABASE_ID, cs);
        assertEquals(Const.MIN_DATABASE_ID, v.id());
        cs = v.cycles();
        Cycle cout = cs.get(0);
        assertEquals(Cycle.MIN_VOLUME, cout.volumeMl());
        assertEquals(Cycle.MIN_BREWTIME, cout.brewSeconds());
        assertEquals(Cycle.MAX_VACUUMTIME, cout.vacuumSeconds());
    }

    @Test
    public void new_Volume_null_cycles_Fails() throws Exception {
        try {
            Volume v = new Volume(Const.MIN_DATABASE_ID, null);
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Volume_empty_cycles_Fails() throws Exception {
        try {
             Volume v = new Volume(Const.MIN_DATABASE_ID, new ArrayList<Cycle>());
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }
}
