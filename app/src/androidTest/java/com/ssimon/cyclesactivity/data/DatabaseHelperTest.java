package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {
    final private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void test_create_tables_and_open_db_Success() {
        try {
            Class<?> c = Class.forName("com.ssimon.cyclesactivity.data.DatabaseHelper");
            Method m = c.getDeclaredMethod("getInstance", android.content.Context.class );
            m.setAccessible(true);
            DatabaseHelper dh  = (DatabaseHelper) m.invoke(null, context);
            SQLiteDatabase db = dh.getWritableDatabase();
            db.execSQL(Contract.Recipe.DELETE_TABLE);
            db.execSQL(Contract.Volume.DELETE_TABLE);
            db.execSQL(Contract.Cycle.DELETE_TABLE);
            db.execSQL(Contract.Recipe.CREATE_TABLE);
            db.execSQL(Contract.Volume.CREATE_TABLE);
            db.execSQL(Contract.Cycle.CREATE_TABLE);
            assertTrue(db.isOpen());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
