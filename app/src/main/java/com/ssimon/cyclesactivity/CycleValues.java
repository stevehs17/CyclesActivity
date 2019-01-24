package com.ssimon.cyclesactivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CycleValues {
    static final int MIN_VOLUME = 180;
    static final int MAX_VOLUME = 1670;
    static final List<Integer> VOLUMES = getVolumes();
    static final int MIN_BREWTIME = 1;
    static final int MAX_BREWTIME = 999;
    static final List<Integer> BREWTIMES = getBrewTimes();
    static final int MIN_VACUUMTIME = 1;
    static final int MAX_VACUUMTIME = 999;
    static final List<Integer> VACUUMTIMES = getVacuumTimes();

    static private List<Integer> getVolumes() {
        final int increment = 10;
        return getIntegerList(MIN_VOLUME, MAX_VOLUME, increment);
    }

    static private List<Integer> getBrewTimes() {
        final int increment = 1;
        return getIntegerList(MIN_BREWTIME, MAX_BREWTIME, increment);
    }

    static List<Integer> getVacuumTimes() {
        final int increment = 1;
        return getIntegerList(MIN_VACUUMTIME, MAX_VACUUMTIME, increment);
    }

    static private List<Integer> getIntegerList(int min, int max, int increment) {
        List<Integer> volumes = new ArrayList<>();
        for (int i = min; i <= max; i += increment)
            volumes.add(i);
        return Collections.unmodifiableList(volumes);
    }
  }
