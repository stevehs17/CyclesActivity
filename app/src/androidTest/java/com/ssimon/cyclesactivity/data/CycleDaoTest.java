package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.model.Cycle;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CycleDaoTest {
    static private final Context sContext = InstrumentationRegistry.getTargetContext();
    static final private int NCYCLES = Cycle.MAX_NUM_CYCLES;
    static final private List<Cycle> CYCLES = DatabaseTestUtils.createCycles(NCYCLES);
    static final private long VOL_ID = 1;
    /*
    final private SQLiteDatabase db = DatabaseTestUtils.getNewWritableTestDb();
    */

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }


    /*
    @Before
    public void resetDb() {
        //db = DatabaseTestUtils.getNewWritableTestDb();
    }
    */

    @Before
    public void setupTables() {
        DatabaseTestUtils.setupTables(sContext);
    }



    @Test
    public void insertCycles_Succeeds() {
        DatabaseHelper dh = DatabaseHelper.getInstance(sContext);
        SQLiteDatabase db = dh.getWritableDatabase();

        db.execSQL("PRAGMA foreign_keys = OFF;");
        CycleDao.insertCycles(db, VOL_ID, CYCLES);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }



    @Test
    public void insertCyclesConstraintViolation_Fails() {
        DatabaseHelper dh = DatabaseHelper.getInstance(sContext);
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
        DatabaseHelper dh = DatabaseHelper.getInstance(sContext);
        SQLiteDatabase db = dh.getWritableDatabase();
        insertCycles_Succeeds();
        List<Cycle> cs = CycleDao.getCycles(db, VOL_ID);
        DatabaseTestUtils.assertCyclesEqual(CYCLES, cs);
    }



}
