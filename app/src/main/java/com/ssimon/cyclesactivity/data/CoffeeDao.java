package com.ssimon.cyclesactivity.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Volume;
import com.ssimon.cyclesactivity.util.Checker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ssimon.cyclesactivity.data.Contract.Coffee.Col;
import static com.ssimon.cyclesactivity.data.Contract.Coffee.TABLE_NAME;

public class CoffeeDao {
    static List<Coffee> getCoffees(SQLiteDatabase db) {
        Checker.notNull(db);
        String query = String.format("SELECT * FROM %s ORDER BY %s COLLATE NOCASE ASC",
                TABLE_NAME, Col.NAME);
        Cursor c = db.rawQuery(query, null);
        List<Coffee> coffees = new ArrayList<>();
        if (!c.moveToFirst())
            throw new IllegalStateException("No coffees found");
        do {
            long id = c.getLong(c.getColumnIndexOrThrow(Col.ID));
            String name = c.getString(c.getColumnIndexOrThrow(Col.NAME));
            List<Volume> volumes = VolumeDao.getVolumes(db, id);
            long volumeId = c.getLong(c.getColumnIndexOrThrow(Col.DEFAULT_VOLUME_ID));
            coffees.add(new Coffee(id, name, volumes, volumeId));
        } while (c.moveToNext());
        c.close();
        return Collections.unmodifiableList(coffees);
    }

    static public void insertCoffees(SQLiteDatabase db, List<Coffee> coffees) {
        Checker.notNull(db);
        Checker.notNullOrEmpty(coffees);
        db.beginTransaction();
        try {
            for (Coffee c : coffees)
                insertCoffee(db, c);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    static long insertCoffee(SQLiteDatabase db, Coffee cof) {
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

     // for testing
    static public void deleteCoffees(SQLiteDatabase db) {
        Checker.notNull(db);
        List<Coffee> cs = null;
        try {
            cs = getCoffees(db);
        } catch (IllegalStateException unused) {
            return;
        }
        db.beginTransaction();
        try {
            for (Coffee c : cs)
                deleteCoffee(db, c.id());
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    static void deleteCoffee(SQLiteDatabase db, long id) {
        Checker.notNull(db);
        Checker.atLeast(id, Const.MIN_DATABASE_ID);
        DbaseUtils.deleteTableRow(db, TABLE_NAME, Col.ID, id);
    }
}
