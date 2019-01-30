package com.ssimon.cyclesactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String EXTRA_COFFEE = "EXTRA_COFFEE";
    static final String EXTRA_VOLUME_IDX = "EXTRA_VOLUME_IDX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Cycle c = new Cycle(1, Cycle.MIN_VOLUME + 500, Cycle.MIN_BREWTIME + 30, Cycle.MIN_VACUUMTIME + 40);
        ArrayList<Cycle> cycles = new ArrayList<>();
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        c = new Cycle(1, Cycle.MIN_VOLUME, Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        cycles.add(c);
        Volume v = new Volume(1, cycles);
        List<Volume> volumes = new ArrayList<>();
        volumes.add(v);
        volumes.add(v);
        Coffee cof = new Coffee(5, "coffee", volumes);
        Intent i = new Intent(this, CyclesActivity.class);
        i.putExtra(EXTRA_COFFEE, cof);
        i.putExtra(EXTRA_VOLUME_IDX, 1);
        startActivity(i);
    }
}
