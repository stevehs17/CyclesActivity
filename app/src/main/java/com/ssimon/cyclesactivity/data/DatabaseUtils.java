package com.ssimon.cyclesactivity.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        cyc = new Cycle(Cycle.MIN_VOLUME + 1, Cycle.MIN_BREWTIME, Cycle.MIN_LASTCYCLE_VACUUMTIME);
        cycs.add(cyc);
        v = new Volume(Const.UNSET_DATABASE_ID, cycs);
        vs.add(v);
        Coffee cof = new Coffee(Const.UNSET_DATABASE_ID, "Default", vs, Const.UNSET_DATABASE_ID);
        List<Coffee> cofs = new ArrayList<>();
        cofs.add(cof);
        cof = new Coffee(Const.UNSET_DATABASE_ID, "Default1", vs, Const.UNSET_DATABASE_ID);
        cofs.add(cof);
        cof = new Coffee(Const.UNSET_DATABASE_ID, "Default2", vs, Const.UNSET_DATABASE_ID);
        cofs.add(cof);
        cof = new Coffee(Const.UNSET_DATABASE_ID, "Default3", vs, Const.UNSET_DATABASE_ID);
        cofs.add(cof);
        cof = new Coffee(Const.UNSET_DATABASE_ID, "Default4", vs, Const.UNSET_DATABASE_ID);
        cofs.add(cof);
        return cofs;
    }

    static List<Coffee> createDefaultCoffees2() {
        final int nCofs = 5, nVols = 5, nCycs = 5;
        return createCoffees(nCofs, nVols, nCycs);

    }

    static final private int START_VOLUME = Cycle.MIN_VOLUME;
    static final private int START_BREWTIME = Cycle.MIN_BREWTIME;
    static final private int START_VACUUMTIME = Cycle.MIN_VACUUMTIME + 1;

    static public List<Coffee> createCoffees(int numCoffees, int numVolumes, int numCycles) {
        int vol = START_VOLUME;
        int brew = START_BREWTIME;
        int vac = START_VACUUMTIME;

        List<Coffee> coffees = new ArrayList<>();
        for (int i = 0; i < numCoffees; i++) {
            List<Volume> volumes = new ArrayList<>();
            for (int j = 0; j < numVolumes; j++) {
                List<Cycle> cycles = new ArrayList<>();
                for (int k = 0; k < numCycles; k++) {
                    Cycle cy = new Cycle(vol, brew, vac);
                    cycles.add(cy);
                    vol = incrVol(vol);
                    brew = incrBrew(brew);
                    vac = incrVac(vac);
                }
                volumes.add(new Volume(Const.UNSET_DATABASE_ID, cycles));
            }
            coffees.add(new Coffee(Const.UNSET_DATABASE_ID, name(i), volumes, Const.UNSET_DATABASE_ID));
        }
        return coffees;
    }

    static private int incrVol(int vol) {
        ++vol;
        if (vol > Cycle.MAX_VOLUME)
            return START_VOLUME;
        else
            return vol;
    }

    static private int incrBrew(int brew) {
        ++brew;
        if (brew > Cycle.MAX_BREWTIME)
            return START_BREWTIME;
        else
            return brew;
    }


    static private int incrVac(int vac) {
        ++vac;
        if (vac > Cycle.MAX_VACUUMTIME)
            return START_VACUUMTIME;
        else
            return vac;
    }


    static private String name(int i) {
        String base = "a";
        String s = "";
        for (int j = 0; j <= i; j++)
            s += base;
        return s;
    }

}
