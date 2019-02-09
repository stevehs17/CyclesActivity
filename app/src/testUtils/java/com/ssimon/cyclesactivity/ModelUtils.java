package com.ssimon.cyclesactivity;

import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ModelUtils {
    static final public long DB_ID = Const.MIN_DATABASE_ID;
    static final public int VOLUME = Cycle.MIN_VOLUME;
    static final public int BREWTIME = Cycle.MIN_BREWTIME;
    static final public int VACUUMTIME = Cycle.MIN_VACUUMTIME;
    static final public String NAME = "COFFEE_NAME";
    static final private long NOID = Const.UNSET_DATABASE_ID;

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

    static public List<Coffee> createCoffees(int numCoffees, int numVolumes, int numCycles) {
        List<Coffee> coffees = new ArrayList<>();
        for (int i = 0; i < numCoffees; i++) {
            List<Volume> volumes = new ArrayList<>();
            for (int j = 0; j < numVolumes; j++) {
                List<Cycle> cycles = new ArrayList<>();
                for (int k = 0; k < numCycles; k++)
                    cycles.add(new Cycle(volume(i, j, k), brewtime(i, j, k), vactime(i, j, k)));
                volumes.add(new Volume(NOID, cycles));
            }
            coffees.add(new Coffee(NOID, name(i), volumes, NOID));
        }
        return coffees;
    }

    static public void validateCoffees(List<Coffee> coffees) {
        for (int i = 0; i < coffees.size(); i++) {
            Coffee cof = coffees.get(i);
            List<Volume> vols = cof.volumes();
            for (int j = 0; j < vols.size(); j++) {
                Volume vol = vols.get(j);
                List<Cycle> cycles = vol.cycles();
                for (int k = 0; k < cycles.size(); k++) {
                    Cycle cyc = cycles.get(k);
                    assertEquals(volume(i, j, k), cyc.volumeMl());
                    assertEquals(brewtime(i, j, k), cyc.brewSeconds());
                    assertEquals(vactime(i, j, k), cyc.vacuumSeconds());
                }
            }
        }
    }

    static private int volume(int i, int j, int k) {
        return parm(Cycle.MIN_VOLUME, Cycle.MAX_VOLUME, i, j, k);
    }

    static private int brewtime(int i, int j, int k) {
        return parm(Cycle.MIN_BREWTIME, Cycle.MAX_BREWTIME, i, j, k);
    }

    static private int vactime(int i, int j, int k) {
        return parm(Cycle.MIN_VACUUMTIME+1, Cycle.MAX_VACUUMTIME, i, j, k);
    }

    static private int parm(int min, int max, int i, int j, int k) {
        int n = min + i*100 + j*10 + k;
        return n > max ? min : n;
    }

    static private String name(int i) {
        String base = "a";
        String s = "";
        for (int j = 0; j <= i; j++)
            s += base;
        return s;
    }

    static public void validateCoffees(List<Coffee> cs1, List<Coffee> cs2) {
        assertEquals(cs1.size(), cs2.size());
        for (int i = 0; i < cs1.size(); i++) {
            Coffee cof1 = cs1.get(i);
            Coffee cof2 = cs2.get(i);
            assertEquals(cof1.id(), cof2.id());
            assertEquals(cof1.name(), cof2.name());
            //assertEquals(cof1.defaultVolumeId(), cof2.defaultVolumeId());
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
}
