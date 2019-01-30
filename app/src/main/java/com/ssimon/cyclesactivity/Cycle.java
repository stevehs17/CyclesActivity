package com.ssimon.cyclesactivity;

import java.io.Serializable;

class Cycle implements Serializable {
    final private long id;
    final private int volumeMl;
    final private int brewSeconds;
    final private int vacuumSeconds;

    Cycle(long id, int volumeMl, int brewSeconds, int vacuumSeconds) {
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
