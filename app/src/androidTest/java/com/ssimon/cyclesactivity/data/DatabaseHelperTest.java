package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.DatabaseTestUtils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {
    final private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void test_reset_tables_and_open_db_Success() {
        SQLiteDatabase db = DatabaseTestUtils.getWritableDb(context);
        db.execSQL(Contract.Recipe.DELETE_TABLE);
        db.execSQL(Contract.Volume.DELETE_TABLE);
        db.execSQL(Contract.Cycle.DELETE_TABLE);
        db.execSQL(Contract.Recipe.CREATE_TABLE);
        db.execSQL(Contract.Volume.CREATE_TABLE);
        db.execSQL(Contract.Cycle.CREATE_TABLE);
        assertTrue(db.isOpen());
    }
}
