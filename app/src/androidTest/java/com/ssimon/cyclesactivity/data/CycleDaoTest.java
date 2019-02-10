package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.ModelUtils;
import com.ssimon.cyclesactivity.model.*;

import org.junit.Test;

import java.util.List;

public class CycleDaoTest {
    static final private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void insertCycles_Failure() {
        try {
            DatabaseHelperTest dht = new DatabaseHelperTest();
            dht.reset_tables_and_open_db_Success();
            SQLiteDatabase db = DatabaseUtils.getWritableDb(context);
            List<Cycle> cs = ModelUtils.createCycleList();
            CycleDao.insertCycles(db, Const.MIN_DATABASE_ID, cs);
        } catch (SQLiteConstraintException e) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void insertCycle_Failure() {
        try {
            DatabaseHelperTest dht = new DatabaseHelperTest();
            dht.reset_tables_and_open_db_Success();
            SQLiteDatabase db = DatabaseUtils.getWritableDb(context);
            Cycle c = ModelUtils.createCycle();
            CycleDao.insertCycle(db, Const.MIN_DATABASE_ID, c, 0);
        } catch (SQLiteConstraintException e) {
            return;
        }
        throw new RuntimeException("test failed");
    }
}
