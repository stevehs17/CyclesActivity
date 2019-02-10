package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {
    static final private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void reset_tables_and_open_db_Success() {
        SQLiteDatabase db = DatabaseUtils.
        assertTrue(db.isOpen());
    }

    static private SQLiteDatabase getResetDatabase() {
        SQLiteDatabase db = DatabaseUtils.getWritableDb(context);
        db.execSQL(Contract.Coffee.DELETE_TABLE);
        db.execSQL(Contract.Volume.DELETE_TABLE);
        db.execSQL(Contract.Cycle.DELETE_TABLE);
        db.execSQL(Contract.Coffee.CREATE_TABLE);
        db.execSQL(Contract.Volume.CREATE_TABLE);
        db.execSQL(Contract.Cycle.CREATE_TABLE);
        return db;
    }
}
