package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.DatabaseTestUtils;
import com.ssimon.cyclesactivity.ModelTestUtils;
import com.ssimon.cyclesactivity.model.*;

import org.junit.Test;

public class CycleDaoTest {
    final private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void test2_insertCycle_Failure() {
        try {
            DatabaseHelperTest dht = new DatabaseHelperTest();
            dht.test_reset_tables_and_open_db_Success();
            SQLiteDatabase db = DatabaseTestUtils.getWritableDb(context);
            Cycle c = ModelTestUtils.createCycle();
            CycleDao.insertCycle(db, Const.MIN_DATABASE_ID, c, 0);
        } catch (SQLiteConstraintException e) {
            return;
        }
        throw new RuntimeException("test failed");
    }
}
