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


public class DbaseUtils {
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
        // name = "Central / South America (template)";

        List<Cycle> cs = new ArrayList<>();

        // name = "Latin America (template)";
        cs.add(new Cycle(1000, 139, 34));
        cs.add(new Cycle(200, 34, 55));
        // name = "Latin America (template)";
        cs.add(new Cycle(750, 139, 80));
        cs.add(new Cycle(750, 27, 100));
        cs.add(new Cycle(500, 100, 85));
        // name = "Latin America (template)";
        cs.add(new Cycle(1125, 138, 85));
        cs.add(new Cycle(1125, 25, 105));
        cs.add(new Cycle(750, 7, 110));
        // name = "Latin America (template)";
        cs.add(new Cycle(1400, 138, 90));
        cs.add(new Cycle(1400, 23, 105));
        cs.add(new Cycle(1000, 6, 110));

        //name = "Dark Blend (template)";
        cs.add(new Cycle(1000, 141, 80));
        cs.add(new Cycle(200, 37, 55));
        //name = "Dark Blend (template)";
        cs.add(new Cycle(750, 141, 80));
        cs.add(new Cycle(750, 28, 100));
        cs.add(new Cycle(500, 11, 85));
        //name = "Dark Blend (template)";
        cs.add(new Cycle(1125, 140, 85));
        cs.add(new Cycle(1125, 26, 105));
        cs.add(new Cycle(750, 10, 110));
        //name = "Dark Blend (template)";
        cs.add(new Cycle(1400, 140, 90));
        cs.add(new Cycle(1400, 25, 105));
        cs.add(new Cycle(1000, 10, 110));


        //name = "East African (template)";
        cs.add(new Cycle(1000, 139, 80));
        cs.add(new Cycle(200, 32, 55));
        //name = "East African (template)";
        cs.add(new Cycle(750, 139, 80));
        cs.add(new Cycle(750, 27, 100));
        cs.add(new Cycle(500, 6, 85));
        //name = "East African (template)";
        cs.add(new Cycle(1125, 138, 85));
        cs.add(new Cycle(1125, 25, 105));
        cs.add(new Cycle(750, 6, 110));
        //name = "East African (template)";
        cs.add(new Cycle(1400, 137, 90));
        cs.add(new Cycle(1400, 23, 105));
        cs.add(new Cycle(1000, 5, 110));

        //name = "Light Blend (template)";
        cs.add(new Cycle(1000, 140, 80));
        cs.add(new Cycle(200, 35, 55));
        //name = "Light Blend (template)";
        cs.add(new Cycle(750, 140, 80));
        cs.add(new Cycle(750, 28, 100));
        cs.add(new Cycle(500, 9, 85));
        //name = "Light Blend (template)";
        cs.add(new Cycle(1125, 139, 85));
        cs.add(new Cycle(1125, 25, 105));
        cs.add(new Cycle(750, 8, 110));
        //name = "Light Blend (template)";
        cs.add(new Cycle(1400, 139, 90));
        cs.add(new Cycle(1400, 24, 105));
        cs.add(new Cycle(1000, 8, 110));

        //name = "Natural (template)";
        cs.add(new Cycle(1000, 142, 80));
        cs.add(new Cycle(200, 27, 55));
        //name = "Natural (template)";
        cs.add(new Cycle(1000, 142, 80));
        cs.add(new Cycle(1000, 30, 55));
        //name = "Natural (template)";
        cs.add(new Cycle(1500, 142, 100));
        cs.add(new Cycle(1500, 27, 120));
        //name = "Natural (template)";
        cs.add(new Cycle(1400, 142, 90));
        cs.add(new Cycle(1400, 26, 105));
        cs.add(new Cycle(1000, 1, 110));

        Cycle[] carr = new Cycle[]{
                cs.get(0),
                cs.get(1)
        };

        List<Coffee> coffees = new ArrayList<>();
        return coffees;
    }

    /*
    List<Coffee> createNaturalTemplate() {
        List<Cycle> c = new ArrayList<>();
        // Volume 0
        c.add(new Cycle(1000, 142, 80));
        c.add(new Cycle(200, 27, 55));
        // Volume 1
        c.add(new Cycle(1000, 142, 80));
        c.add(new Cycle(1000, 30, 55));
        // Volume 2
        c.add(new Cycle(1500, 142, 100));
        c.add(new Cycle(1500, 27, 120));
        // Volume 3
        c.add(new Cycle(1400, 142, 90));
        c.add(new Cycle(1400, 26, 105));
        c.add(new Cycle(1000, 1, 110));

        List<Volume> v = new ArrayList<>();
        v.add(new )
    }
    */


    /*

        cs.add(new Cycle(0, , ));
        cs.add(new Cycle(0, , ));
        cs.add(new Cycle(0, , ));


    static List<Coffee> createDefaultCoffees() {
        List<Coffee> coffees = new ArrayList<>();

        Integer[] brewTimes1 = {139, 34};

        Cycle[] = new Cycle[] { new Cycle(1,1,1), new Cycle(1,1,1)};


        return coffees;

    }


   private void insertDefaultRecipes(SQLiteDatabase db) {
            String name = "Central / South America (template)";
            Integer[] volumes1 = {1000, 200};
            Integer[] brewTimes1 = {139, 34};
            Integer[] vacTimes1 = {80, 55};
            long id = RecipeDao.insertAdvancedRecipe(db, name, Arrays.asList(brewTimes1), Arrays.asList(volumes1), Arrays.asList(vacTimes1));

            Integer[] volumes2 = {750, 750, 500};
            Integer[] brewTimes2 = {139, 27, 8};
            Integer[] vacTimes2 = {80, 100, 85};
            AdvancedCycleDao.addRecipeCycleSet(db, 1, Arrays.asList(brewTimes2), Arrays.asList(volumes2), Arrays.asList(vacTimes2), id);

            Integer[] volumes3 = {1125, 1125, 750};
            Integer[] brewTimes3 = {138, 25, 7};
            Integer[] vacTimes3 = {85, 105, 110};
            AdvancedCycleDao.addRecipeCycleSet(db, 2, Arrays.asList(brewTimes3), Arrays.asList(volumes3), Arrays.asList(vacTimes3), id);

            Integer[] volumes4 = {1400, 1400, 1000};
            Integer[] brewTimes4 = {138, 23, 6};
            Integer[] vacTimes4 = {90, 105, 110};
            AdvancedCycleDao.addRecipeCycleSet(db, 3, Arrays.asList(brewTimes4), Arrays.asList(volumes4), Arrays.asList(vacTimes4), id);


            name = "Dark Blend (template)";
            Integer[] volumes5 = {1000, 200};
            Integer[] brewTimes5 = {141, 37};
            Integer[] vacTimes5 = {80, 55};
            id = RecipeDao.insertAdvancedRecipe(db, name, Arrays.asList(brewTimes5), Arrays.asList(volumes5), Arrays.asList(vacTimes5));

            //name = "Dark Blend";
            Integer[] volumes6 = {750, 750, 500};
            Integer[] brewTimes6 = {141, 28, 11};
            Integer[] vacTimes6 = {80, 100, 85};
            AdvancedCycleDao.addRecipeCycleSet(db, 1, Arrays.asList(brewTimes6), Arrays.asList(volumes6), Arrays.asList(vacTimes6), id);

            //name = "Dark Blend";
            Integer[] volumes7 = {1125, 1125, 750};
            Integer[] brewTimes7 = {140, 26, 10};
            Integer[] vacTimes7 = {85, 105, 110};
            AdvancedCycleDao.addRecipeCycleSet(db, 2, Arrays.asList(brewTimes7), Arrays.asList(volumes7), Arrays.asList(vacTimes7), id);

            //name = "Dark Blend";
            Integer[] volumes8 = {1400, 1400, 1000};
            Integer[] brewTimes8 = {140, 25, 10};
            Integer[] vacTimes8 = {90, 105, 110};
            AdvancedCycleDao.addRecipeCycleSet(db, 3, Arrays.asList(brewTimes8), Arrays.asList(volumes8), Arrays.asList(vacTimes8), id);

            //name = "East African (template)";
            Integer[] volumes9 = {1000, 200};
            Integer[] brewTimes9 = {139, 32};
            Integer[] vacTimes9 = {80, 55};
            id = RecipeDao.insertAdvancedRecipe(db, name, Arrays.asList(brewTimes9), Arrays.asList(volumes9), Arrays.asList(vacTimes9));

            //name = "East African";
            Integer[] volumes10 = {750, 750, 500};
            Integer[] brewTimes10 = {139, 27, 6};
            Integer[] vacTimes10 = {80, 100, 85};
            AdvancedCycleDao.addRecipeCycleSet(db, 1, Arrays.asList(brewTimes10), Arrays.asList(volumes10), Arrays.asList(vacTimes10), id);

            //name = "East African";
            Integer[] volumes11 = {1125, 1125, 750};
            Integer[] brewTimes11 = {138, 25, 6};
            Integer[] vacTimes11 = {85, 105, 110};
            AdvancedCycleDao.addRecipeCycleSet(db, 2, Arrays.asList(brewTimes11), Arrays.asList(volumes11), Arrays.asList(vacTimes11), id);

            //name = "East African";
            Integer[] volumes12 = {1400, 1400, 1000};
            Integer[] brewTimes12 = {137, 23, 5};
            Integer[] vacTimes12 = {90, 105, 110};
            AdvancedCycleDao.addRecipeCycleSet(db, 3, Arrays.asList(brewTimes12), Arrays.asList(volumes12), Arrays.asList(vacTimes12), id);

            name = "Light Blend (template)";
            Integer[] volumes13 = {1000, 200};
            Integer[] brewTimes13 = {140, 35};
            Integer[] vacTimes13 = {80, 55};
            id = RecipeDao.insertAdvancedRecipe(db, name, Arrays.asList(brewTimes13), Arrays.asList(volumes13), Arrays.asList(vacTimes13));

            //name = "Light Blend";
            Integer[] volumes14 = {750, 750, 500};
            Integer[] brewTimes14 = {140, 28, 9};
            Integer[] vacTimes14 = {80, 100, 85};
            AdvancedCycleDao.addRecipeCycleSet(db, 1, Arrays.asList(brewTimes14), Arrays.asList(volumes14), Arrays.asList(vacTimes14), id);

            //name = "Light Blend";
            Integer[] volumes15 = {1125, 1125, 750};
            Integer[] brewTimes15 = {139, 25, 8};
            Integer[] vacTimes15 = {85, 105, 110};
            AdvancedCycleDao.addRecipeCycleSet(db, 2, Arrays.asList(brewTimes15), Arrays.asList(volumes15), Arrays.asList(vacTimes15), id);

            //name = "Light Blend";
            Integer[] volumes16 = {1400, 1400, 1000};
            Integer[] brewTimes16 = {139, 24, 8};
            Integer[] vacTimes16 = {90, 105, 110};
            AdvancedCycleDao.addRecipeCycleSet(db, 3, Arrays.asList(brewTimes16), Arrays.asList(volumes16), Arrays.asList(vacTimes16), id);

            name = "Natural (template)";
            Integer[] volumes17 = {1000, 200};
            Integer[] brewTimes17 = {142, 27};
            Integer[] vacTimes17 = {80, 55};
            id = RecipeDao.insertAdvancedRecipe(db, name, Arrays.asList(brewTimes17), Arrays.asList(volumes17), Arrays.asList(vacTimes17));

            //name = "Natural";
            Integer[] volumes18 = {1000, 1000};
            Integer[] brewTimes18 = {142, 30};
            Integer[] vacTimes18 = {80, 55};
            AdvancedCycleDao.addRecipeCycleSet(db, 1, Arrays.asList(brewTimes18), Arrays.asList(volumes18), Arrays.asList(vacTimes18), id);

            //name = "Natural";
            Integer[] volumes19 = {1500, 1500};
            Integer[] brewTimes19 = {142, 27};
            Integer[] vacTimes19 = {100, 120};
            AdvancedCycleDao.addRecipeCycleSet(db, 2, Arrays.asList(brewTimes19), Arrays.asList(volumes19), Arrays.asList(vacTimes19), id);

            //name = "Natural";
            Integer[] volumes20 = {1400, 1400, 1000};
            Integer[] brewTimes20 = {142, 26, 1};
            Integer[] vacTimes20 = {90, 105, 110};
            AdvancedCycleDao.addRecipeCycleSet(db, 3, Arrays.asList(brewTimes20), Arrays.asList(volumes20), Arrays.asList(vacTimes20), id);

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
    */

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
