package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ssimon.cyclesactivity.util.Checker;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final private int DATABASE_VERSION = 1;
    static final private String DATABASE_NAME = "groundcontrol_database.db";
    static private DatabaseHelper singleton = null;

    static synchronized private DatabaseHelper getInstance(Context c) {
        Checker.notNull(c);
        if (singleton == null)
            singleton = new DatabaseHelper(c.getApplicationContext());
        return singleton;
    }

    private DatabaseHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
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
