package com.ssimon.cyclesactivity.data;

import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.model.Cycle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CycleDaoTest {
    static final private int NCYCLES = Cycle.MAX_NUM_CYCLES;
    static final private List<Cycle> CYCLES = DatabaseTestUtils.createCycles(NCYCLES);
    static final private long VOL_ID = 1;
    private SQLiteDatabase db;

    @Before
    public void resetDb() {
        db = DatabaseTestUtils.getNewWritableTestDb();
    }

    @Test
    public void insertCycles_Succeeds() {
        db.execSQL("PRAGMA foreign_keys = OFF;");
        CycleDao.insertCycles(db, VOL_ID, CYCLES);
    }

    /*
    @Test
    public void insertCyclesConstraintViolation_Fails() {
        db.execSQL("PRAGMA foreign_keys = ON;");
        try {
            CycleDao.insertCycles(db, VOL_ID, CYCLES);
        } catch (SQLiteConstraintException unused) {
            return;
        }
        throw new RuntimeException("constraint failed");
    }
    */

    @Test
    public void getCycles_Succeeds() {
        insertCycles_Succeeds();
        List<Cycle> cs = CycleDao.getCycles(db, VOL_ID);
        DatabaseTestUtils.assertCyclesEqual(CYCLES, cs);
    }
}
