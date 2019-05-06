package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.widget.ListView;

import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;
import com.ssimon.cyclesactivity.util.Checker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseTestUtils {
    static {
        Context c = InstrumentationRegistry.getTargetContext();
        c.deleteDatabase(Contract.DATABASE_NAME);
    }

    private DatabaseTestUtils() {}

    // This method exists to provide a means to execute the static code
    // above, which doesn't work right if the same code insdie the static
    // block is executed instead inside setupDatabase().
    static public void setupDatabase() {}

    static public void setupTables() {
        Context c = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(c);
        SQLiteDatabase db = dh.getWritableDatabase();
        db.execSQL(Contract.Coffee.DELETE_TABLE);
        db.execSQL(Contract.Volume.DELETE_TABLE);
        db.execSQL(Contract.Cycle.DELETE_TABLE);
        db.execSQL(Contract.Coffee.CREATE_TABLE);
        db.execSQL(Contract.Volume.CREATE_TABLE);
        db.execSQL(Contract.Cycle.CREATE_TABLE);
    }

    static List<Cycle> createCycles(int ncycles) {
        Checker.greaterThan(ncycles, 0);

        List<Cycle> cs = new ArrayList<>();
        for (int i = 0; i < ncycles; i++) {
            int vol = Cycle.MIN_VOLUME + 10*i;
            vol = (vol > Cycle.MAX_VOLUME ? Cycle.MIN_VOLUME : vol);
            int brew = Cycle.MIN_TIME + i;
            brew = (brew > Cycle.MAX_TIME ? Cycle.MIN_TIME : brew);
            int vac = Cycle.MIN_TIME + i;
            vac = (vac > Cycle.MAX_TIME ? Cycle.MAX_TIME : vac);
            cs.add(new Cycle(vol, brew, vac));
        }
        return Collections.unmodifiableList(cs);
    }

    static void assertCyclesEqual(List<Cycle> expected, List<Cycle> actual) {
        Checker.notNullOrEmpty(expected);
        Checker.notNullOrEmpty(actual);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Cycle e = expected.get(i);
            Cycle a = actual.get(i);
            assertEquals(e.volumeMl(), a.volumeMl());
            assertEquals(e.brewSeconds(), a.brewSeconds());
            assertEquals(e.vacuumSeconds(), a.vacuumSeconds());
        }
    }

    static List<Volume> createVolumes(int nvolumes) {
        Checker.greaterThan(nvolumes, 0);

        List<Volume> vs = new ArrayList<>();
        for (int i = 0; i < nvolumes; i++) {
            int ncycles = Volume.MIN_NUM_CYCLES + i;
            ncycles = (ncycles > Volume.MAX_NUM_CYCLES ? Volume.MIN_NUM_CYCLES : ncycles);
            List<Cycle> cycles = createCycles(ncycles);
            vs.add(new Volume(cycles));
        }
        return Collections.unmodifiableList(vs);
    }

    static void assertVolumesEqual(List<Volume> expected, List<Volume> actual) {
        Checker.notNullOrEmpty(expected);
        Checker.notNullOrEmpty(actual);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Volume e = expected.get(i);
            Volume a = actual.get(i);
            assertCyclesEqual(e.cycles(), a.cycles());
        }
    }

    static List<Coffee> createCoffees(int ncoffees, int nvolumes) {
        Checker.greaterThan(ncoffees, 0);
        Checker.greaterThan(nvolumes, 0);

        List<Cycle> cycles = new ArrayList<>();
        for (int i = 0; i < Volume.MAX_NUM_CYCLES; i++)
            cycles.add(new Cycle(Cycle.MAX_VOLUME, Cycle.MAX_TIME, Cycle.MAX_TIME-1));
        String name = "a";
        List<Coffee> coffees = new ArrayList<>();
        for (int i = 0; i < ncoffees; i++) {
            List<Volume> volumes = new ArrayList<>();
            for (int j = 0; j < nvolumes; j++)
                volumes.add(new Volume(cycles));
            name += "b";
            coffees.add(new Coffee(name, volumes));
        }
        return coffees;
    }

    static void assertCoffeesEqual(List<Coffee> expected, List<Coffee> actual) {
        Checker.notNullOrEmpty(expected);
        Checker.notNullOrEmpty(actual);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Coffee e = expected.get(i);
            Coffee a = actual.get(i);
            assertVolumesEqual(e.volumes(), a.volumes());
        }
    }
}
