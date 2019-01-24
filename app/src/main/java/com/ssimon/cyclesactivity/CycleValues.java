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

    static List<Integer> getVolumes() {
        List<Integer> volumes = new ArrayList<>();
        for (int i = MIN_VOLUME; i <= MAX_VOLUME; i+=10)
            volumes.add(i);
        return Collections.unmodifiableList(volumes);
    }

    static List<Integer> getBrewTimes() {
        List<Integer> volumes = new ArrayList<>();
        for (int i = MIN_BREWTIME; i <= MAX_BREWTIME; i++)
            volumes.add(i);
        return Collections.unmodifiableList(volumes);
    }

    static List<Integer> getVacuumTimes() {
        List<Integer> volumes = new ArrayList<>();
        for (int i = MIN_VACUUMTIME; i <= MAX_VACUUMTIME; i++)
            volumes.add(i);
        return Collections.unmodifiableList(volumes);
    }
}
