package com.ssimon.cyclesactivity.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.util.Checker;
import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Volume;

import java.util.List;

import static com.ssimon.cyclesactivity.data.Contract.Volume.Col;
import static com.ssimon.cyclesactivity.data.Contract.Volume.TABLE_NAME;

public class VolumeDao {
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
            cv.put(Col.RECIPE_ID, recipeId);
            long volumeId = db.insertOrThrow(TABLE_NAME, null, cv);
            CycleDao.insertCycles(db, volumeId, vol.cycles());
            db.setTransactionSuccessful();
        }  finally {
            db.endTransaction();
        }
    }
}
