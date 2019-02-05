package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.Const;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    static final long DB_ID = Const.MIN_DATABASE_ID;
    static final int VOLUME = Cycle.MIN_VOLUME;
    static final int BREWTIME = Cycle.MIN_BREWTIME;
    static final int VACUUMTIME = Cycle.MIN_VACUUMTIME;
    static final String NAME = "COFFEE_NAME";

    static Cycle createCycle() {
        return new Cycle(DB_ID, VOLUME, BREWTIME, VACUUMTIME);
    }

    static List<Cycle> createCycleList() {
        List<Cycle> list = new ArrayList<>();
        list.add(createCycle());
        return list;
    }

    static Volume createVolume() {
        return new Volume(DB_ID, createCycleList());
    }

    static List<Volume> createVolumeList() {
        List<Volume> list = new ArrayList<>();
        list.add(createVolume());
        return list;
    }

    static Coffee createCoffee() {
        return new Coffee(DB_ID, NAME, createVolumeList());
    }
}
