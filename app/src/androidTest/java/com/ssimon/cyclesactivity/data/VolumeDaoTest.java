package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.DatabaseUtils;
import com.ssimon.cyclesactivity.ModelUtils;
import com.ssimon.cyclesactivity.model.Volume;

import org.junit.Test;

import java.util.List;

public class VolumeDaoTest {
    final private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void insertVolumes_Failure() {
        try {
            DatabaseHelperTest dht = new DatabaseHelperTest();
            dht.reset_tables_and_open_db_Success();
            SQLiteDatabase db = DatabaseUtils.getWritableDb(context);
            List<Volume> vs = ModelUtils.createVolumeList();
            VolumeDao.insertVolumes(db, ModelUtils.DB_ID, vs);
        } catch (SQLiteConstraintException e) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void insertVolume_Failure() {
        try {
            DatabaseHelperTest dht = new DatabaseHelperTest();
            dht.reset_tables_and_open_db_Success();
            SQLiteDatabase db = DatabaseUtils.getWritableDb(context);
            Volume v = ModelUtils.createVolume();
            VolumeDao.insertVolume(db, ModelUtils.DB_ID, v);
        } catch (SQLiteConstraintException e) {
            return;
        }
        throw new RuntimeException("test failed");
    }

}
