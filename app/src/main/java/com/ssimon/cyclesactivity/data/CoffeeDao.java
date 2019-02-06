package com.ssimon.cyclesactivity.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.util.Checker;

import static com.ssimon.cyclesactivity.data.Contract.Coffee.Col;
import static com.ssimon.cyclesactivity.data.Contract.Coffee.TABLE_NAME;


public class CoffeeDao {
    static long insertRecipe(SQLiteDatabase db, com.ssimon.cyclesactivity.model.Coffee cof) {
        Checker.notNull(db);
        Checker.notNull(cof);

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(Col.NAME, cof.name());
            cv.put(Col.DEFAULT_VOLUME_ID, Const.UNSET_DATABASE_ID);
            long id = db.insertOrThrow(TABLE_NAME, null, cv);
            VolumeDao.insertVolumes(db, id, cof.volumes());
            db.setTransactionSuccessful();
            return id;
        } finally {
            db.endTransaction();
        }
    }
}
