package com.ssimon.cyclesactivity.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import com.ssimon.cyclesactivity.util.Checker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DaoUtils {
    static void deleteTableRow(SQLiteDatabase db, String table, String col, long id) {
        Checker.notNull(db);
        Checker.notNullOrEmpty(table);
        Checker.notNullOrEmpty(col);
        Checker.atLeast(id, Const.MIN_DATABASE_ID);

        String where = col + "=?";
        String[] whereArgs = {Long.toString(id)};
        int numDeleted = db.delete(table, where, whereArgs);
        if (numDeleted != 1)
            throw new IllegalStateException("instead of 1 deleted there were " + numDeleted);
    }
}
