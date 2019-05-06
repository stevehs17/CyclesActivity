package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CycleDaoTest {
    static final private List<Cycle> CYCLES = DatabaseTestUtils
            .createCycles(Volume.MAX_NUM_CYCLES);
    static final private List<Cycle> CYCLES_2 = DatabaseTestUtils
            .createCycles(Volume.MIN_NUM_CYCLES);
    static final private long VOL_ID = 1;
    private SQLiteDatabase db;

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }

    @Before
    public void setupTables() {
        DatabaseTestUtils.setupTables();
        Context ctx = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        db = dh.getWritableDatabase();
    }

    @Test
    public void insertCycles_Succeeds() {
        db.execSQL("PRAGMA foreign_keys = OFF;");
        CycleDao.insertCycles(db, VOL_ID, CYCLES);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

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

    @Test
    public void getCycles_Succeeds() {
        insertCycles_Succeeds();
        List<Cycle> cs = CycleDao.getCycles(db, VOL_ID);
        DatabaseTestUtils.assertCyclesEqual(CYCLES, cs);
    }

    @Test
    public void deleteCyclesByVolumeId_Succeeds() {
        db.execSQL("PRAGMA foreign_keys = OFF;");
        CycleDao.insertCycles(db, VOL_ID, CYCLES);
        List<Cycle> cs = CycleDao.getCycles(db, VOL_ID);
        DatabaseTestUtils.assertCyclesEqual(CYCLES, cs);
        CycleDao.deleteCyclesByVolumeId(db, VOL_ID);
        db.execSQL("PRAGMA foreign_keys = ON;");
        try {
            CycleDao.getCycles(db, VOL_ID);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("failure deleting cycles");
    }

    @Test
    public void replaceCycles_Succeeds() {
        db.execSQL("PRAGMA foreign_keys = OFF;");
        CycleDao.insertCycles(db, VOL_ID, CYCLES);
        List<Cycle> cs = CycleDao.getCycles(db, VOL_ID);
        DatabaseTestUtils.assertCyclesEqual(CYCLES, cs);
        CycleDao.replaceCycles(db, VOL_ID, CYCLES_2);
        db.execSQL("PRAGMA foreign_keys = ON;");
        cs = CycleDao.getCycles(db, VOL_ID);
        DatabaseTestUtils.assertCyclesEqual(CYCLES_2, cs);
    }
}