package com.ssimon.cyclesactivity.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.util.Checker;
import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Volume;

import static com.ssimon.cyclesactivity.data.Contract.Volume.Col;
import static com.ssimon.cyclesactivity.data.Contract.Volume.TABLE_NAME;

public class VolumeDao {
    void insertVolume(SQLiteDatabase db, long recipeId, Volume vol) {
        Checker.notNull(db);
        Checker.atLeast(recipeId, Const.MIN_DATABASE_ID);
        Checker.notNull(vol);
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(Col.RECIPE_ID, recipeId);
            long volumeId = db.insertOrThrow(TABLE_NAME, null, cv);
            CycleDao.insertCycles(db, volumeId, vol.cycles());
            db.setTransactionSuccessful();
        }  finally {
            db.endTransaction();
        }
    }
}
