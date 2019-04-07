package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.model.Volume;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class VolumeDaoTest {
    static final private int NVOLS = 2;
    static final private List<Volume> VOLUMES = DatabaseTestUtils.createVolumes(NVOLS);
    static final private long COF_ID = 1;

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }

    @Before
    public void setupTables() {
        DatabaseTestUtils.setupTables();
    }

    @Test
    public void insertVolumes_Succeeds() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = OFF;");
        VolumeDao.insertVolumes(db, COF_ID, VOLUMES);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Test
    public void insertVolumesConstraintViolation_Fails() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        try {
            VolumeDao.insertVolumes(db, COF_ID, VOLUMES);
        } catch (SQLiteConstraintException unused) {
            return;
        }
        throw new RuntimeException("constraint failed");
    }

    @Test
    public void getVolumes_Succeeds() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = OFF;");
        VolumeDao.insertVolumes(db, COF_ID, VOLUMES);
        List<Volume> vs = VolumeDao.getVolumes(db, COF_ID);
        DatabaseTestUtils.assertVolumesEqual(VOLUMES, vs);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }
}
