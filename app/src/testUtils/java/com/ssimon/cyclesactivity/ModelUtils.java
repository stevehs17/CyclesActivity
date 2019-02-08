package com.ssimon.cyclesactivity;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import java.util.ArrayList;
import java.util.List;

public class ModelUtils {
    static final public long DB_ID = Const.MIN_DATABASE_ID;
    static final public int VOLUME = Cycle.MIN_VOLUME;
    static final public int BREWTIME = Cycle.MIN_BREWTIME;
    static final public int VACUUMTIME = Cycle.MIN_VACUUMTIME;
    static final public String NAME = "COFFEE_NAME";

    static public Cycle createCycle() {
        return new Cycle(DB_ID, VOLUME, BREWTIME, VACUUMTIME);
    }

    static public List<Cycle> createCycleList() {
        List<Cycle> list = new ArrayList<>();
        list.add(createCycle());
        return list;
    }

    static public Volume createVolume() {
        return new Volume(DB_ID, createCycleList());
    }

    /*
    static public List<Volume> createVolumeList() {
        List<Volume> list = new ArrayList<>();
        list.add(createVolume());
        return list;
    }
    */
    static public List<Volume> createVolumeList() {
        List<Volume> list = new ArrayList<>();
        list.add(createVolume());
        list.add(createVolume());
        return list;
    }

    static public Coffee createCoffee() {
        return new Coffee(DB_ID, NAME, createVolumeList(), DB_ID);
    }
}
