package com.ssimon.cyclesactivity;

import android.support.v7.app.ActionBar;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CyclesActivity extends AppCompatActivity {
    static final private int FIRST_PARM_ROW_INDEX = 1;
    static final private int FIRST_PARM_COLUMN_INDEX = 1;
    static final private int VOLUME_COLUMN = FIRST_PARM_COLUMN_INDEX;
    static final private int BREWTIME_COLUMN = VOLUME_COLUMN + 1;
    static final private int VACUUMTIME_COLUMN = BREWTIME_COLUMN + 1;
    static final List<Integer> VOLUMES = getVolumes();
    static final List<Integer> BREWTIMES = getBrewTimes();
    static final List<Integer> VACUUMTIMES = getVacuumTimes();

    private TextView minValueText, maxValueText;
    private SeekBar seekBar;
    private TableLayout parmTable;
    private Button currentParmButton = null;
    private List<Integer> currentParmValues = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycles_activity);
        minValueText = (TextView) findViewById(R.id.txt_min_value);
        maxValueText = (TextView) findViewById(R.id.txt_max_value);
        seekBar = (SeekBar) findViewById(R.id.seek);
        parmTable = (TableLayout) findViewById(R.id.lay_parms);
        setDecrementButton();
        setIncrementButton();
        Coffee cof = (Coffee) getIntent().getSerializableExtra(MainActivity.EXTRA_COFFEE);
        int volumeIdx = getIntent().getIntExtra(MainActivity.EXTRA_VOLUME_IDX, -1);
        if (volumeIdx < 0 || volumeIdx > Cycle.MAX_NUM_CYCLES - 1)
            throw new IllegalStateException("invalid volume index: " + volumeIdx);
        setParmButtonValues(cof.volumes().get(volumeIdx).cycles());
        setDefaultParmButton();
    }

    private void setParmButtonValues(List<Cycle> cycles) {
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
        setCurrentParmButton(v, Cycle.MIN_BREWTIME, Cycle.MAX_BREWTIME, BREWTIMES);
    }

    public void onClickVacuumSecs(View v) {
        setCurrentParmButton(v, Cycle.MIN_VACUUMTIME, Cycle.MAX_VACUUMTIME, VACUUMTIMES);
    }

    private void setCurrentParmButton(View v, int minVal, int maxVal, List<Integer> values) {
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
        if (idx < 0) throw new IllegalStateException("idx = " + idx);
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
                b.setText(Integer.toString(Cycle.MIN_BREWTIME));
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

    static private List<Integer> getVolumes() {
        final int increment = 10;
        return getIntegerList(Cycle.MIN_VOLUME, Cycle.MAX_VOLUME, increment);
    }

    static private List<Integer> getBrewTimes() {
        final int increment = 1;
        return getIntegerList(Cycle.MIN_BREWTIME, Cycle.MAX_BREWTIME, increment);
    }

    static List<Integer> getVacuumTimes() {
        final int increment = 1;
        return getIntegerList(Cycle.MIN_VACUUMTIME, Cycle.MAX_VACUUMTIME, increment);
    }

    static private List<Integer> getIntegerList(int min, int max, int increment) {
        List<Integer> volumes = new ArrayList<>();
        for (int i = min; i <= max; i += increment)
            volumes.add(i);
        return Collections.unmodifiableList(volumes);
    }
}