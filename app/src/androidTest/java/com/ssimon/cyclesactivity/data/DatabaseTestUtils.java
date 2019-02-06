package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DatabaseTestUtils {
    SQLiteDatabase getWritableDb(Context ctx) {
        try {
            Class<?> cl = Class.forName("com.ssimon.cyclesactivity.data.DatabaseHelper");
            Method m = cl.getDeclaredMethod("getInstance", android.content.Context.class );
            m.setAccessible(true);
            DatabaseHelper dh  = (DatabaseHelper) m.invoke(null, ctx);
            return dh.getWritableDatabase();
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
