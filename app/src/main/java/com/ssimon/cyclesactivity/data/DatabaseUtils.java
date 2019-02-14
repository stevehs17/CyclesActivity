package com.ssimon.cyclesactivity.data;

import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import com.ssimon.cyclesactivity.util.Checker;

import java.util.ArrayList;
import java.util.List;


class DatabaseUtils {
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

    static List<Coffee> createDefaultCoffees() {
        Cycle cyc = new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_BREWTIME, Cycle.MIN_LASTCYCLE_VACUUMTIME);
        List<Cycle> cycs = new ArrayList<>();
        cycs.add(cyc);
        Volume v = new Volume(Const.UNSET_DATABASE_ID, cycs);
        List<Volume> vs = new ArrayList<>();
        vs.add(v);
        Coffee cof = new Coffee(Const.UNSET_DATABASE_ID, "Default", vs, Const.UNSET_DATABASE_ID);
        List<Coffee> cofs = new ArrayList<>();
        return cofs;
    }
}
