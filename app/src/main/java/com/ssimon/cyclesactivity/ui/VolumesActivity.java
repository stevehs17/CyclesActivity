/*
 Copyright (c) 2019 Steven H. Simon
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain	a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.
*/

package com.ssimon.cyclesactivity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ssimon.cyclesactivity.R;

import java.util.ArrayList;
import java.util.List;

import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

public class VolumesActivity extends AppCompatActivity {
    static final String EXTRA_COFFEE = "EXTRA_COFFEE";
    static final String EXTRA_VOLUME_IDX = "EXTRA_VOLUME_IDX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Cycle c = new Cycle(Cycle.MIN_VOLUME + 500, Cycle.MIN_BREWTIME + 30, Cycle.MIN_VACUUMTIME + 40);
        ArrayList<Cycle> cycles = new ArrayList<>();
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        cycles.add(c);
        c = new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);
        cycles.add(c);
        Volume v = new Volume(1, cycles);
        List<Volume> volumes = new ArrayList<>();
        volumes.add(v);
        volumes.add(v);
        Coffee cof = new Coffee(5, "coffee", volumes, 1);
        Intent i = new Intent(this, CyclesActivity.class);
        i.putExtra(EXTRA_COFFEE, cof);
        i.putExtra(EXTRA_VOLUME_IDX, 1);
        startActivity(i);
    }
}
