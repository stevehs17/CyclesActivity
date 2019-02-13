package com.ssimon.cyclesactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.data.DatabaseHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DatabaseTestUtils {
    static public SQLiteDatabase getWritableDb(Context ctx) {
        DatabaseHelper dh = getDatabaseHelper(ctx);
        return dh.getWritableDatabase();
    }

    static public SQLiteDatabase getReadableleDb(Context ctx) {
        DatabaseHelper dh = getDatabaseHelper(ctx);
        return dh.getReadableDatabase();
    }

    static public SQLiteDatabase getCleanWritableDb(Context ctx) {
        DatabaseHelper dh = getDatabaseHelper(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        dh.onUpgrade(db, 0, 0);
        return db;
    }

    static private DatabaseHelper getDatabaseHelper(Context ctx) {
        return DatabaseHelper.getInstance(ctx);
    }



    /*
    static private DatabaseHelper getDatabaseHelper(Context ctx) {
        try {
            Class<?> cl = Class.forName("com.ssimon.cyclesactivity.data.DatabaseHelper");
            Method m = cl.getDeclaredMethod("getInstance", Context.class);
            m.setAccessible(true);
            return (DatabaseHelper) m.invoke(null, ctx);
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
    */
}
