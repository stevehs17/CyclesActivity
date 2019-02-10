package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DatabaseUtils {
    static public SQLiteDatabase getWritableDb(Context ctx) {
        try {
            Class<?> cl = Class.forName("com.ssimon.cyclesactivity.data.DatabaseHelper");
            Method m = cl.getDeclaredMethod("getInstance", android.content.Context.class);
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

    static public SQLiteDatabase getReadableleDb(Context ctx) {
        try {
            Class<?> cl = Class.forName("com.ssimon.cyclesactivity.data.DatabaseHelper");
            Method m = cl.getDeclaredMethod("getInstance", android.content.Context.class);
            m.setAccessible(true);
            DatabaseHelper dh  = (DatabaseHelper) m.invoke(null, ctx);
            return dh.getReadableDatabase();
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

    static public SQLiteDatabase getResetDatabase() {
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
