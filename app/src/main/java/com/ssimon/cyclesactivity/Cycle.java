package com.ssimon.cyclesactivity;

import java.io.Serializable;

class Cycle implements Serializable {
    static final int MAX_NUM_CYCLES = 6;
    static final int MIN_VOLUME = 180;
    static final int MAX_VOLUME = 1670;
    static final int MIN_BREWTIME = 1;
    static final int MAX_BREWTIME = 999;
    static final int MIN_VACUUMTIME = 1;
    static final int MAX_VACUUMTIME = 999;
    static final int MIN_LASTCYCLE_VACUUMTIME = 20;

    final private long id;
    final private int volumeMl;
    final private int brewSeconds;
    final private int vacuumSeconds;

    Cycle(long id, int volumeMl, int brewSeconds, int vacuumSeconds) {
        if (id != Const.UNSET_DATABASE_ID)
            Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.inRange(volumeMl, Cycle.MIN_VOLUME, Cycle.MAX_VOLUME);
        Checker.inRange(brewSeconds, Cycle.MIN_BREWTIME, Cycle.MAX_BREWTIME);
        Checker.inRange(vacuumSeconds, Cycle.MIN_VACUUMTIME, Cycle.MAX_VACUUMTIME);
        this.id = id;
        this.volumeMl = volumeMl;
        this.brewSeconds = brewSeconds;
        this.vacuumSeconds = vacuumSeconds;
    }

    long id() { return id; }
    int volumeMl() { return volumeMl; }
    int brewSeconds() { return brewSeconds; }
    int vacuumSeconds() { return vacuumSeconds; }
}
