package com.ssimon.cyclesactivity.data;

import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class VolumeDaoTest {
    static final private int NVOLS = 2;
    static final private List<Volume> VOLUMES = DatabaseTestUtils.createVolumes(NVOLS);
    static final private long COF_ID = 1;
    private SQLiteDatabase db;

    @Before
    public void resetDb() {
        db = DatabaseTestUtils.getNewWritableTestDb();
    }

    @Test
    public void insertVolumes_Succeeds() {
        db.execSQL("PRAGMA foreign_keys = OFF;");
        VolumeDao.insertVolumes(db, COF_ID, VOLUMES);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Test
    public void insertVolumesConstraintViolation_Fails() {
        try {
            VolumeDao.insertVolumes(db, COF_ID, VOLUMES);
        } catch (SQLiteConstraintException unused) {
            return;
        }
        throw new RuntimeException("constraint failed");
    }

    @Test
    public void getVolumes_Succeeds() {
        db.execSQL("PRAGMA foreign_keys = OFF;");
        VolumeDao.insertVolumes(db, COF_ID, VOLUMES);
        List<Volume> vs = VolumeDao.getVolumes(db, COF_ID);
        DatabaseTestUtils.assertVolumesEqual(VOLUMES, vs);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }
}
