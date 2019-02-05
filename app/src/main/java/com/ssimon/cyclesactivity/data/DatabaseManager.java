package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import util.Checker;

public class DatabaseManager {
    static private DatabaseHelper databaseHelper = null;

    static private synchronized DatabaseHelper getInstance(Context c) {
        Checker.notNull(c);
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper(c.getApplicationContext());
        return databaseHelper;
    }

    static SQLiteDatabase getReadableDb(Context c) {
        Checker.notNull(c);
        DatabaseHelper h = getInstance(c);
        return h.getReadableDatabase();
    }

    static SQLiteDatabase getWritableDb(Context c) {
        Checker.notNull(c);
        DatabaseHelper h = getInstance(c);
        return h.getWritableDatabase();
    }

    static private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context c) {
            super(c, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Contract.Recipe.CREATE_TABLE);
            db.execSQL(Contract.Volume.CREATE_TABLE);
            db.execSQL(Contract.Cycle.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(Contract.Recipe.CREATE_TABLE);
            db.execSQL(Contract.Volume.CREATE_TABLE);
            db.execSQL(Contract.Cycle.CREATE_TABLE);
            onConfigure(db);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }
    }
}
