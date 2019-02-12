package com.ssimon.cyclesactivity;

import android.util.Log;

import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ModelTestUtils {
    static final public long DB_ID = Const.MIN_DATABASE_ID;
    static final public int VOLUME = Cycle.MIN_VOLUME;
    static final public int BREWTIME = Cycle.MIN_BREWTIME;
    static final public int VACUUMTIME = Cycle.MIN_VACUUMTIME;
    static final public String NAME = "COFFEE_NAME";
    static final private long NOID = Const.UNSET_DATABASE_ID;
    static final private String TAG = "ModelTestUtils";

    static public Cycle createCycle() {
        return new Cycle(VOLUME, BREWTIME, VACUUMTIME);
    }

    static public List<Cycle> createCycleList() {
        List<Cycle> list = new ArrayList<>();
        list.add(createCycle());
        return list;
    }

    static public Volume createVolume() {
        return new Volume(DB_ID, createCycleList());
    }

    static public List<Volume> createVolumeList() {
        List<Volume> list = new ArrayList<>();
        list.add(createVolume());
        list.add(createVolume());
        return list;
    }

    static public Coffee createCoffee() {
        return new Coffee(DB_ID, NAME, createVolumeList(), DB_ID);
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
                volumes.add(new Volume(NOID, cycles));
            }
            coffees.add(new Coffee(NOID, name(i), volumes, NOID));
        }
        printCoffees(coffees);
        return coffees;
    }

    static private void printCoffees(List<Coffee> cs) {
        for (Coffee c : cs)
            Log.v(TAG,c.toString());
    }

    static public void validateCoffeesNoIds(List<Coffee> coffees) {;
        int vol = START_VOLUME;
        int brew = START_BREWTIME;
        int vac = START_VACUUMTIME;

        for (int i = 0; i < coffees.size(); i++) {
            Coffee cof = coffees.get(i);
            List<Volume> vols = cof.volumes();
            for (int j = 0; j < vols.size(); j++) {
                Volume v = vols.get(j);
                List<Cycle> cycles = v.cycles();
                for (int k = 0; k < cycles.size(); k++) {
                    Cycle cyc = cycles.get(k);
                    assertEquals(vol, cyc.volumeMl());
                    assertEquals(brew, cyc.brewSeconds());
                    assertEquals(vac, cyc.vacuumSeconds());
                    vol = incrVol(vol);
                    brew = incrBrew(brew);
                    vac = incrVac(vac);
                }
            }
        }
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

    static public void validateCoffeesNoIds(List<Coffee> cs1, List<Coffee> cs2) {
        assertEquals(cs1.size(), cs2.size());
        for (int i = 0; i < cs1.size(); i++) {
            Coffee cof1 = cs1.get(i);
            Coffee cof2 = cs2.get(i);
            assertEquals(cof1.id(), cof2.id());
            assertEquals(cof1.name(), cof2.name());
            List<Volume> vols1 = cof1.volumes();
            List<Volume> vols2 = cof2.volumes();
            assertEquals(vols1.size(), vols2.size());
            for (int j = 0; j < vols1.size(); j++) {
                Volume v1 = vols1.get(j);
                Volume v2 = vols2.get(j);
                List<Cycle> cycs1 = v1.cycles();
                List<Cycle> cycs2 = v2.cycles();
                assertEquals(cycs1.size(), cycs2.size());
                for (int k = 0; k < cycs1.size(); k++) {
                    Cycle cyc1 = cycs1.get(k);
                    Cycle cyc2 = cycs2.get(k);
                    assertEquals(cyc1.volumeMl(), cyc2.volumeMl());
                    assertEquals(cyc1.brewSeconds(), cyc2.brewSeconds());
                    assertEquals(cyc1.vacuumSeconds(), cyc2.vacuumSeconds());
                }
            }
        }
    }

    static public void newValidateCoffees(List<Coffee> cs1, List<Coffee> cs2) {
        assertEquals(cs1.size(), cs2.size());
        for (int i = 0; i < cs1.size(); i++) {
            Coffee cof1 = cs1.get(i);
            Coffee cof2 = cs2.get(i);
            assertEquals(cof1.id(), cof2.id());
            assertEquals(cof1.name(), cof2.name());
            assertEquals(cof1.defaultVolumeId(), cof2.defaultVolumeId());

            List<Volume> vs1 = cof1.volumes();
            List<Volume> vs2 = cof2.volumes();
            assertEquals(vs1.size(), vs2.size());

            for (int j = 0; j < vs1.size(); j++) {
                Volume v1 = vs1.get(j);
                Volume v2 = vs2.get(j);
                assertEquals(v1.id(), v2.id());

                List<Cycle> cys1 = v1.cycles();
                List<Cycle> cys2 = v2.cycles();
                assertEquals(cys1.size(), cys2.size());





            }

        }
    }
}
