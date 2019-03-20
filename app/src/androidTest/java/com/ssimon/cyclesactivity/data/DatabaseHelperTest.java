package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {
    private DatabaseHelper dbHelper;

    @Before
    public void resetDb() {
        final Context c = InstrumentationRegistry.getTargetContext();
        c.deleteDatabase(Contract.DATABASE_NAME);
        dbHelper = dbHelper.getInstance(c);
    }

    @Test
    public void openWritableDb_Succeeds() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void openReadableDb_Succeeds() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        assertTrue(db.isOpen());
    }
}
