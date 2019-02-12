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
    public void reset_tables_and_open_db_Success() {
        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        assertTrue(db.isOpen());
    }
}
