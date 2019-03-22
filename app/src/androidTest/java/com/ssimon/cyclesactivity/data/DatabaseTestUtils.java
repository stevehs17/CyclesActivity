package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

class DatabaseTestUtils {
    static {
        Context c = InstrumentationRegistry.getTargetContext();
        c.deleteDatabase(Contract.DATABASE_NAME);
    }

    private DatabaseTestUtils() {}

    public static void setupDatabase() {} // called to execute static block

    static void setupTables(Context c) {
        DatabaseHelper dh = DatabaseHelper.getInstance(c);
        SQLiteDatabase db = dh.getWritableDatabase();
        db.execSQL(Contract.Coffee.DELETE_TABLE);
        db.execSQL(Contract.Volume.DELETE_TABLE);
        db.execSQL(Contract.Cycle.DELETE_TABLE);
        db.execSQL(Contract.Coffee.CREATE_TABLE);
        db.execSQL(Contract.Volume.CREATE_TABLE);
        db.execSQL(Contract.Cycle.CREATE_TABLE);
    }

    /*
    static public SQLiteDatabase getNewWritableTestDb() {
        Context c = InstrumentationRegistry.getTargetContext();
        //c.deleteDatabase(Contract.DATABASE_NAME);
        DatabaseHelper dh = DatabaseHelper.getInstance(c);
        SQLiteDatabase db = dh.getWritableDatabase();



        db.execSQL(Contract.Coffee.DELETE_TABLE);
        db.execSQL(Contract.Volume.DELETE_TABLE);
        db.execSQL(Contract.Cycle.DELETE_TABLE);

        db.execSQL(Contract.Coffee.CREATE_TABLE);
        db.execSQL(Contract.Volume.CREATE_TABLE);
        db.execSQL(Contract.Cycle.CREATE_TABLE);

        return db;
    }
    */

    static List<Cycle> createCycles(int ncycles) {
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
        return Collections.unmodifiableList(cs);
    }

    static void assertCyclesEqual(List<Cycle> expected, List<Cycle> actual) {
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
        List<Volume> vs = new ArrayList<>();
        for (int i = 0; i < nvolumes; i++) {
            int ncycles = Cycle.MIN_NUM_CYCLES + i;
            ncycles = (ncycles > Cycle.MAX_NUM_CYCLES ? Cycle.MIN_NUM_CYCLES : ncycles);
            List<Cycle> cycles = createCycles(ncycles);
            vs.add(new Volume(Const.UNSET_DATABASE_ID, cycles));
        }
        return Collections.unmodifiableList(vs);
    }

    static void assertVolumesEqual(List<Volume> expected, List<Volume> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Volume e = expected.get(i);
            Volume a = actual.get(i);
            assertCyclesEqual(e.cycles(), a.cycles());
        }
    }


}
