package com.ssimon.cyclesactivity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.util.Checker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CycleActivity extends AppCompatActivity {
    static final private List<Integer> LASTCYCLE_VACUUMTIME = getLastCycleVacuumTimes();
    static final private List<Integer> TIMES = getTimes();
    static final private List<Integer> VOLUMES = getVolumes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycle_activity);
    }



    static private List<Integer> getLastCycleVacuumTimes() {
        final int increment = 1;
        return getIntegerList(Cycle.MIN_LASTCYCLE_VACUUMTIME, Cycle.MAX_TIME, increment);
    }

    static private List<Integer> getTimes() {
        final int increment = 1;
        return getIntegerList(Cycle.MIN_TIME, Cycle.MAX_TIME, increment);
    }

    static private List<Integer> getVolumes() {
        final int increment = 10;
        return getIntegerList(Cycle.MIN_VOLUME, Cycle.MAX_VOLUME, increment);
    }

    static private List<Integer> getIntegerList(int min, int max, int increment) {
        Checker.atLeast(min, 0);
        Checker.lessThan(min, max);
        Checker.atLeast(increment, 1);

        List<Integer> vals = new ArrayList<>();
        for (int i = min; i <= max; i += increment)
            vals.add(i);
        return Collections.unmodifiableList(vals);
    }
}
