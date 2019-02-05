package com.ssimon.cyclesactivity.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.util.Checker;
import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Cycle;

import java.util.ArrayList;
import java.util.List;

import static com.ssimon.cyclesactivity.data.Contract.Cycle.Col;
import static com.ssimon.cyclesactivity.data.Contract.Cycle.TABLE_NAME;

public class CycleDao {
    static List<Cycle> getVolumeCycles(SQLiteDatabase db, long volumeId) {
        Checker.notNull(db);
        Checker.atLeast(volumeId, Const.MIN_DATABASE_ID);

        String query = String.format("SELECT * FROM %s WHERE %s = ? ORDER BY %s",
                TABLE_NAME, Col.VOLUME_ID, Col.INDEX);
        String[] args = { String.valueOf(volumeId) };
        Cursor c = db.rawQuery(query, args);
        if (!c.moveToFirst())
            throw new IllegalStateException("no cycles for Volume id = " + volumeId);
        List<Cycle> cycles = new ArrayList<>();
        do {
            long id = c.getLong(c.getColumnIndexOrThrow(Col.ID));
            int volume = c.getInt(c.getColumnIndexOrThrow(Col.VOLUME_ID));
            int brewTime = c.getInt(c.getColumnIndexOrThrow(Col.BREW_TIME_SECONDS));
            int vacuumTime = c.getInt(c.getColumnIndexOrThrow(Col.VACUUM_TIME_SECONDS));
            cycles.add(new Cycle(id, volume, brewTime, vacuumTime));
        } while (c.moveToNext());
        c.close();
        return cycles;
    }

    static void insertCycles(SQLiteDatabase db, long volumeId, List<Cycle> cycles) {
        Checker.notNull(db);
        Checker.atLeast(volumeId, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(cycles);

        db.beginTransaction();
        try {
            for (int i = 0; i < cycles.size(); i++)
                insertCycle(db, volumeId, cycles.get(i), i);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    static void insertCycle(SQLiteDatabase db, long volumeId, Cycle cycle, int index) {
        Checker.notNull(db);
        Checker.atLeast(volumeId, Const.MIN_DATABASE_ID);
        Checker.notNull(cycle);
        Checker.inRange(index, 0, Cycle.MAX_NUM_CYCLES);

        ContentValues cv = new ContentValues();
        cv.put(Col.VOLUME_ID, volumeId);
        cv.put(Col.VOLUME_MILLILITERS, cycle.volumeMl());
        cv.put(Col.BREW_TIME_SECONDS, cycle.brewSeconds());
        cv.put(Col.VACUUM_TIME_SECONDS, cycle.vacuumSeconds());
        cv.put(Col.INDEX, index);
        db.insertOrThrow(TABLE_NAME, null, cv);
    }


}
