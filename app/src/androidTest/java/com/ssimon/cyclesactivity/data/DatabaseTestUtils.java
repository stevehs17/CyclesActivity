package com.ssimon.cyclesactivity.data;

import com.ssimon.cyclesactivity.model.Cycle;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class DatabaseTestUtils {
    List<Cycle> createCycles(int ncycles) {
        List<Cycle> cs = new ArrayList<>();
        for (int i = 0; i < ncycles; i++) {
            int vol = Cycle.MIN_VOLUME + 10*i;
            vol = (vol > Cycle.MAX_VOLUME ? Cycle.MIN_VOLUME : vol);
            int brew = Cycle.MIN_BREWTIME + i;
            brew = (brew > Cycle.MAX_BREWTIME ? Cycle.MIN_BREWTIME : brew);
            int vac = Cycle.MIN_VACUUMTIME + i;
            vac = (vac > Cycle.MAX_VACUUMTIME ? Cycle.MAX_VACUUMTIME : vac);
            cs.add(new Cycle(vol, brew, vac));
        }
        return cs;
    }

    void assertEqual(List<Cycle> expected, List<Cycle> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Cycle e = expected.get(i);
            Cycle a = actual.get(i);
            assertEquals(e.volumeMl(), a.volumeMl());
            assertEquals(e.brewSeconds(), a.brewSeconds());
            assertEquals(e.vacuumSeconds(), a.vacuumSeconds());
        }
    }
}
