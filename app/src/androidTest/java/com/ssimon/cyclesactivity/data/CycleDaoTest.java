package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.model.Cycle;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CycleDaoTest {
    static final private int NCYCLES = Cycle.MAX_NUM_CYCLES;
    static final private List<Cycle> CYCLES = DatabaseTestUtils.createCycles(NCYCLES);
    static final private long VOL_ID = 1;

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }

    @Before
    public void setupTables() {
        DatabaseTestUtils.setupTables();
    }


    @Test
    public void insertCycles_Succeeds() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();

        db.execSQL("PRAGMA foreign_keys = OFF;");
        CycleDao.insertCycles(db, VOL_ID, CYCLES);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }



    @Test
    public void insertCyclesConstraintViolation_Fails() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON;");
        try {
            CycleDao.insertCycles(db, VOL_ID, CYCLES);
        } catch (SQLiteConstraintException unused) {
            return;
        }
        throw new RuntimeException("constraint failed");
    }



    @Test
    public void getCycles_Succeeds() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        insertCycles_Succeeds();
        List<Cycle> cs = CycleDao.getCycles(db, VOL_ID);
        DatabaseTestUtils.assertCyclesEqual(CYCLES, cs);
    }



}
