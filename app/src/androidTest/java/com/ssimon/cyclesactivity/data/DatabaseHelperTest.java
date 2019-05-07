package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {
    private DatabaseHelper dbHelp;

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }

    @Before
    public void resetDb() {
        final Context c = InstrumentationRegistry.getTargetContext();
        dbHelp = dbHelp.getInstance(c);
    }

    @Test
    public void openWritableDb_Succeeds() {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void openReadableDb_Succeeds() {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        assertTrue(db.isOpen());
    }
}