package com.ssimon.cyclesactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.data.DatabaseHelper;

public class DatabaseTestUtils {
    static public SQLiteDatabase getWritableDb(Context ctx) {
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        return dh.getWritableDatabase();
    }

    static public SQLiteDatabase getReadableleDb(Context ctx) {
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        return dh.getReadableDatabase();
    }

    static public SQLiteDatabase getCleanWritableDb(Context ctx) {
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        dh.onUpgrade(db, 0, 0);
        return db;
    }
}
