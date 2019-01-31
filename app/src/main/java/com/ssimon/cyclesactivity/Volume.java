package com.ssimon.cyclesactivity;

import java.io.Serializable;
import java.util.List;

class Volume implements Serializable {
    final private long id;
    final private List<Cycle> cycles;

    Volume(long id, List<Cycle> cycles) {
        if (id != Const.UNSET_DATABASE_ID)
            Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(cycles);
        this.id = id;
        this.cycles = cycles;
    }

    long id() { return id; }
    List<Cycle> cycles() { return cycles; }
}
