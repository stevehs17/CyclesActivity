package com.ssimon.cyclesactivity.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.util.Checker;
import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Volume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.ssimon.cyclesactivity.data.Contract.Volume.Col;
import static com.ssimon.cyclesactivity.data.Contract.Volume.TABLE_NAME;

public class VolumeDao {
    static public List<Volume> getVolumes(SQLiteDatabase db, long coffeeId) {
        Checker.notNull(db);
        Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);

        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                TABLE_NAME, Col.COFFEE_ID);
        String[] args = {String.valueOf(coffeeId)};
        Cursor c = db.rawQuery(query, args);
        if (!c.moveToFirst())
            throw new IllegalStateException("No volumes found for coffee id = " + coffeeId);
        List<Volume> volumes = new ArrayList<>();
        do {
            long volumeId = c.getLong(c.getColumnIndexOrThrow(Col.ID));
            List<Cycle> cycles = CycleDao.getCycles(db, volumeId);
            volumes.add(new Volume(volumeId, cycles));
        } while (c.moveToNext());
        c.close();
        Collections.sort(volumes, new TotalVolumeSorter());
        return volumes;
    }


    static private class TotalVolumeSorter implements Comparator<Volume> {
        @Override
        public int compare(Volume v1, Volume v2) {
            return v1.totalVolume() - v2.totalVolume();
        }
    }


    static public void insertVolumes(SQLiteDatabase db, long recipeId, List<Volume> vols) {
        Checker.notNull(db);
        Checker.atLeast(recipeId, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(vols);
        db.beginTransaction();
        try {
            for (Volume v : vols)
                insertVolume(db, recipeId, v);
            db.setTransactionSuccessful();
        }  finally {
            db.endTransaction();
        }
    }

    static public void insertVolume(SQLiteDatabase db, long recipeId, Volume vol) {
        Checker.notNull(db);
        Checker.atLeast(recipeId, Const.MIN_DATABASE_ID);
        Checker.notNull(vol);
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(Col.COFFEE_ID, recipeId);
            long volumeId = db.insertOrThrow(TABLE_NAME, null, cv);
            CycleDao.insertCycles(db, volumeId, vol.cycles());
            db.setTransactionSuccessful();
        }  finally {
            db.endTransaction();
        }
    }
}
