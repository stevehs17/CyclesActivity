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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ssimon.cyclesactivity.data.CoffeeCache;
import com.ssimon.cyclesactivity.data.DatabaseHelper;
import com.ssimon.cyclesactivity.message.CoffeeRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.util.Utils;
import com.ssimon.cyclesactivity.util.Checker;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CycleActivity extends AppCompatActivity {
    static final private int FIRST_PARM_ROW_INDEX = 1;
    static final private int FIRST_PARM_COLUMN_INDEX = 1;
    static final private int VOLUME_COLUMN = FIRST_PARM_COLUMN_INDEX;
    static final private int BREWTIME_COLUMN = VOLUME_COLUMN + 1;
    static final private int VACUUMTIME_COLUMN = BREWTIME_COLUMN + 1;
    static final private List<Integer> VOLUMES = getVolumes();
    static final private List<Integer> BREWTIMES = getBrewTimes();
    static final private List<Integer> VACUUMTIMES = getVacuumTimes();

    private TextView minValueText, maxValueText;
    private SeekBar seekBar;
    private TableLayout parmTable;
    private Button currentParmButton = null;
    private List<Integer> currentParmValues = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycle_activity);
        minValueText = (TextView) findViewById(R.id.txt_min_value);
        maxValueText = (TextView) findViewById(R.id.txt_max_value);
        seekBar = (SeekBar) findViewById(R.id.seek);
        parmTable = (TableLayout) findViewById(R.id.lay_parms);
        setDecrementButton();
        setIncrementButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.registerEventBus(this);
        Utils.postEvent(new CoffeeRefreshEvent());
    }

    @Override
    protected void onStop() {
        Utils.unregisterEventBus(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCycleTable(CoffeeRefreshEvent e) {
        List<Coffee> coffees = CoffeeCache.getCoffees();
        if (coffees == null) {
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.refreshCoffeesCache();
        } else {
            long coffeeId = getIntent().getLongExtra(CoffeeActivity.EXTRA_COFFEEID, Const.UNSET_DATABASE_ID);
            Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
            long volumeId = getIntent().getLongExtra(VolumeActivity.EXTRA_VOLUMEID, Const.UNSET_DATABASE_ID);
            Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
            List<Cycle> cycles = Utils.getCyclesByCoffeeAndVolumeIds(coffeeId, volumeId, coffees);
            setParmButtonValues(cycles);
            setDefaultParmButton();
        }
    }

    private List<Cycle> parmButtonToCycles() {
        List<Cycle> cycles = new ArrayList<>();
        for (int i = 0; i < Cycle.MAX_NUM_CYCLES; i++) {
            TableRow tr = (TableRow) parmTable.getChildAt(i + FIRST_PARM_ROW_INDEX);
            if (tr.getVisibility() == View.VISIBLE) {
                Button b = (Button) tr.getChildAt(VOLUME_COLUMN);
                String s = b.getText().toString();
                int vol = Integer.valueOf(s);
                b = (Button) tr.getChildAt(BREWTIME_COLUMN);
                s = b.getText().toString();
                int brew = Integer.valueOf(s);
                b = (Button) tr.getChildAt(VACUUMTIME_COLUMN);
                s = b.getText().toString();
                int vac = Integer.valueOf(s);
                cycles.add(new Cycle(vol, brew, vac));
            } else {
                break;
            }
        }
        return cycles;
    }

    private void setParmButtonValues(List<Cycle> cycles) {
        Checker.notNullOrEmpty(cycles);
        int numCycles = cycles.size();
        for (int i = 0; i < Cycle.MAX_NUM_CYCLES; i++) {
            TableRow tr = (TableRow) parmTable.getChildAt(i + FIRST_PARM_ROW_INDEX);
            if (i < numCycles) {
                Cycle c = cycles.get(i);
                Button b = (Button) tr.getChildAt(VOLUME_COLUMN);
                b.setText(Integer.toString(c.volumeMl()));
                b = (Button) tr.getChildAt(BREWTIME_COLUMN);
                b.setText(Integer.toString(c.brewSeconds()));
                b = (Button) tr.getChildAt(VACUUMTIME_COLUMN);
                b.setText(Integer.toString(c.vacuumSeconds()));
            } else {
                tr.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setDecrementButton() {
        setSeekBarButton(true);
    }

    private void setIncrementButton() {
        setSeekBarButton(false);
    }

    private void setSeekBarButton(final boolean isDecrement) {
        final ImageButton btn = (ImageButton) findViewById(isDecrement ? R.id.btn_decrement
                : R.id.btn_increment);
        final Runnable repeater = new Runnable() {
            @Override
            public void run() {
                if (isDecrement)
                    decrement();
                else
                    increment();
                final int milliseconds = 100;
                btn.postDelayed(this, milliseconds);
            }
        };
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isDecrement)
                        decrement();
                    else
                        increment();
                    v.postDelayed(repeater, ViewConfiguration.getLongPressTimeout());
                } else if (e.getAction() == MotionEvent.ACTION_UP) {
                    v.removeCallbacks(repeater);
                }
                return true;
            }
        });
    }

    private void decrement() {
        int idx = seekBar.getProgress();
        if (idx > 0) {
            idx--;
            seekBar.setProgress(idx);
            setParmButton(idx);
        }
    }

    private void increment() {
        int idx = seekBar.getProgress();
        if (idx < currentParmValues.size() - 1) {
            idx++;
            seekBar.setProgress(idx);
            setParmButton(idx);
        }
    }

    private void setDefaultParmButton() {
        TableRow tr = (TableRow) parmTable.getChildAt(FIRST_PARM_ROW_INDEX);
        View v =  tr.getChildAt(FIRST_PARM_COLUMN_INDEX);
        onClickVolumeMl(v);
    }

    public void onClickVolumeMl(View v) {
        setCurrentParmButton(v, Cycle.MIN_VOLUME, Cycle.MAX_VOLUME, VOLUMES);
    }

    public void onClickBrewSecs(View v) {
        setCurrentParmButton(v, Cycle.MIN_TIME, Cycle.MAX_TIME, BREWTIMES);
    }

    public void onClickVacuumSecs(View v) {
        setCurrentParmButton(v, Cycle.MIN_TIME, Cycle.MAX_TIME, VACUUMTIMES);
    }

    private void setCurrentParmButton(View v, int minVal, int maxVal, List<Integer> values) {
        Checker.notNull(v);
        Checker.greaterThan(minVal, 0);
        Checker.lessThan(minVal, maxVal);
        Checker.notNullOrEmpty(values);
        
        minValueText.setText(Integer.toString(minVal));
        maxValueText.setText(Integer.toString(maxVal));
        currentParmValues = values;
        if (currentParmButton != null)
            currentParmButton.setBackgroundResource(R.drawable.white_rectangle);
        currentParmButton = (Button) v;
        currentParmButton.setBackgroundResource(R.drawable.yellow_rectangle);
        String s = currentParmButton.getText().toString();
        int val = Integer.parseInt(s);
        int idx = currentParmValues.indexOf(val);
        if (idx < 0)
            throw new IllegalStateException("idx = " + idx);
        seekBar.setMax(currentParmValues.size() - 1);
        seekBar.setProgress(idx);
        seekBar.setOnSeekBarChangeListener(seekBarListener());
    }

    private SeekBar.OnSeekBarChangeListener seekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar unused, int idx, boolean unused1) {
                setParmButton(idx);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }

    private void setParmButton(int idx) {
        Checker.inRange(idx, 0, currentParmValues.size() - 1);
        int val = currentParmValues.get(idx);
        currentParmButton.setText(Integer.toString(val));
    }

    public void onClickAddCycle(View unused) {
        for (int i = 2; i < parmTable.getChildCount(); i++) {
            View v = parmTable.getChildAt(i);
            if (v.getVisibility() == View.INVISIBLE) {
                TableRow tr = (TableRow) v;
                tr.setVisibility(View.VISIBLE);
                Button b = (Button) tr.getChildAt(VOLUME_COLUMN);
                b.setText(Integer.toString(Cycle.MIN_VOLUME));
                b = (Button) tr.getChildAt(BREWTIME_COLUMN);
                b.setText(Integer.toString(Cycle.MIN_TIME));
                b = (Button) tr.getChildAt(VACUUMTIME_COLUMN);
                b.setText(Integer.toString(Cycle.MIN_LASTCYCLE_VACUUMTIME));
                break;
            }
        }
    }

    public void onClickDeleteCycle(View unused) {
        for (int i = parmTable.getChildCount() - 1; i > 1; i--) {
            View v = parmTable.getChildAt(i);
            if (v.getVisibility() == View.VISIBLE) {
                v.setVisibility(View.INVISIBLE);
                setDefaultParmButton();
                break;
            }
        }
    }

    public void onClickSaveCycles(View unused) {
        
    }

    static private List<Integer> getVolumes() {
        final int increment = 10;
        return getIntegerList(Cycle.MIN_VOLUME, Cycle.MAX_VOLUME, increment);
    }

    static private List<Integer> getBrewTimes() {
        final int increment = 1;
        return getIntegerList(Cycle.MIN_TIME, Cycle.MAX_TIME, increment);
    }

    static List<Integer> getVacuumTimes() {
        final int increment = 1;
        return getIntegerList(Cycle.MIN_TIME, Cycle.MAX_TIME, increment);
    }

    static private List<Integer> getIntegerList(int min, int max, int increment) {
        Checker.atLeast(min, 0);
        Checker.lessThan(min, max);
        Checker.greaterThan(increment, 0);
        List<Integer> volumes = new ArrayList<>();
        for (int i = min; i <= max; i += increment)
            volumes.add(i);
        return Collections.unmodifiableList(volumes);
    }
}