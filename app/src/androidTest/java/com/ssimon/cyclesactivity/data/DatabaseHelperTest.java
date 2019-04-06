package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {
    private DatabaseHelper dbHelper;

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }

    @Before
    public void resetDb() {
        final Context c = InstrumentationRegistry.getTargetContext();
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
