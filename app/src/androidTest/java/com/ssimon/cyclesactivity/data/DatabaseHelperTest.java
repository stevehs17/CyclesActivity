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

    /*
    @Test
    public void testDb() {
        DatabaseHelper dh = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = dh.getWritableDatabase();
        db.execSQL(Contract.Recipe.DELETE_TABLE);
        db.execSQL(Contract.Volume.DELETE_TABLE);
        db.execSQL(Contract.Cycle.DELETE_TABLE);
        db.execSQL(Contract.Recipe.CREATE_TABLE);
        db.execSQL(Contract.Volume.CREATE_TABLE);
        db.execSQL(Contract.Cycle.CREATE_TABLE);
        assertTrue(db.isOpen());
    }
    */

    @Test
    public void testDbReflect() {
        try {
            Class<?> c = Class.forName("com.ssimon.cyclesactivity.data.DatabaseHelper");
            Method m = c.getDeclaredMethod("getInstance", android.content.Context.class );
            m.setAccessible(true);
            DatabaseHelper dh  = (DatabaseHelper) m.invoke(null, context);
            SQLiteDatabase db = dh.getWritableDatabase();
            db.execSQL(Contract.Recipe.DELETE_TABLE);
            db.execSQL(Contract.Recipe.DELETE_TABLE);
            db.execSQL(Contract.Volume.DELETE_TABLE);
            db.execSQL(Contract.Cycle.DELETE_TABLE);
            db.execSQL(Contract.Recipe.CREATE_TABLE);
            db.execSQL(Contract.Volume.CREATE_TABLE);
            db.execSQL(Contract.Cycle.CREATE_TABLE);
            assertTrue(db.isOpen());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e1) {
            throw new RuntimeException(e1);
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

}
