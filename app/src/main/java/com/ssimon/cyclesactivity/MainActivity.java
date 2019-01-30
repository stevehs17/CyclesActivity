package com.ssimon.cyclesactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String EXTRA_KEY = "EXTRA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Cycle c = new Cycle(1, 2, 3, 4);
        ArrayList<Cycle> cycles = new ArrayList<>();
        cycles.add(c);
        cycles.add(c);
        Volume v = new Volume(1, cycles);
        List<Volume> volumes = new ArrayList<>();
        volumes.add(v);
        volumes.add(v);
        Coffee cof = new Coffee(5, "coffee", volumes);
        Intent i = new Intent(this, CyclesActivity.class);
        i.putExtra(EXTRA_KEY, cof);
        startActivity(i);
    }
}
