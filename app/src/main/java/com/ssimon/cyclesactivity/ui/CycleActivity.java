package com.ssimon.cyclesactivity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;
import com.ssimon.cyclesactivity.util.Checker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CycleActivity extends AppCompatActivity {
    static final private List<Integer> LASTCYCLE_VACUUMTIMES = getLastCycleVacuumTimes();
    static final private List<Integer> TIMES = getTimes();
    static final private List<Integer> VOLUMES = getVolumes();

    static final private int HEADER_ROW = 0;
    static final private int FIRST_PARM_ROW = HEADER_ROW + 1;

    private int cycleNumColumn;
    private int firstParmColumn;
    private int volumeMlColumn;
    private int brewSecsColumn;
    private int vacuumSecsColiumn;
    private int lastParmColumn;

    private TextView totalVolumeText;
    private TableLayout parmTable;
    private SeekBar seekBar;
    private TextView minValueText, maxValueText;
    private ImageButton decrementButton, incrementButton;
    private Button deleteCycleButton, addCycleButton;

    private Button currentParmButton = null;
    private Coffee coffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycle_activity);
        setViews();
        setParmColumnIndices();
        initParmTable();
        //UiUtil.setDecrementButton(decrementButton, seekBar);
    }

    private void setViews() {
        totalVolumeText = (TextView) findViewById(R.id.txt_totalvolume);
        parmTable = (TableLayout) findViewById(R.id.lay_parms);
        seekBar = (SeekBar) findViewById(R.id.seek);
        minValueText = (TextView) findViewById(R.id.txt_min_value);
        maxValueText = (TextView) findViewById(R.id.txt_max_value);
        decrementButton = (ImageButton) findViewById(R.id.btn_decrement);
        incrementButton = (ImageButton) findViewById(R.id.btn_increment);
        addCycleButton = (Button) findViewById(R.id.btn_addcycle);
        deleteCycleButton = (Button) findViewById(R.id.btn_deletecycle);
    }

    private void setParmColumnIndices() {
        brewSecsColumn = getBrewSecsColumnIndex();
        cycleNumColumn = getCycleNumColumnIndex();
        vacuumSecsColiumn = getVacuumSecsColumnIndex();
        volumeMlColumn = getVolumeMlColumnIndex();
        firstParmColumn = Math.min(brewSecsColumn, Math.min(vacuumSecsColiumn, volumeMlColumn));
        lastParmColumn = Math.max(brewSecsColumn, Math.max(vacuumSecsColiumn, volumeMlColumn));
    }

    private int getBrewSecsColumnIndex() {
        return getParmColumnIndex(R.string.cycle_txt_brewsecs);
    }

    private int getCycleNumColumnIndex() {
        return getParmColumnIndex(R.string.cycle_txt_cycle);
    }

    private int getVacuumSecsColumnIndex() {
        return getParmColumnIndex(R.string.cycle_txt_vacuumsecs);
    }

    private int getVolumeMlColumnIndex() {
        return getParmColumnIndex(R.string.cycle_txt_volumeml);
    }

    private int getParmColumnIndex(int headingId) {
        Checker.notNullResourceId(headingId);

        TableRow header = (TableRow) parmTable.getChildAt(HEADER_ROW);
        for (int i = 0; i < header.getChildCount(); i++) {
            TextView tv = (TextView) header.getChildAt(i);
            String s = tv.getText().toString();
            if (s.equals(getString(headingId)))
                return i;
        }
        throw new RuntimeException("no heading found with string id = " + headingId);
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

    private void initParmTable() {
        LayoutInflater infl = LayoutInflater.from(this);
        for (int i = 0; i < Volume.MAX_NUM_CYCLES; i++) {
            TableRow row = (TableRow) infl.inflate(R.layout.cycle_item_parm_row, null);
            row.setVisibility(View.INVISIBLE);
            initParmRow(row, i, VOLUMES.get(0), TIMES.get(0), LASTCYCLE_VACUUMTIMES.get(0));
            parmTable.addView(row);
        }
    }

    private void initParmRow(TableRow row, int rowIndex, int volume, int brewSecs, int vacSecs) {
        Checker.notNull(row);
        Checker.atLeast(rowIndex, 0);
        Checker.inRange(volume, Cycle.MIN_VOLUME, Cycle.MAX_VOLUME);
        Checker.inRange(brewSecs, Cycle.MIN_TIME, Cycle.MAX_TIME);
        Checker.inRange(vacSecs, Cycle.MIN_TIME, Cycle.MAX_TIME);

        setRowChildText(row, cycleNumColumn, rowIndex+1);
        setParmRow(row, volume, brewSecs, vacSecs);
    }

    private void setParmRow(TableRow row, int volume, int brewSecs, int vacSecs) {
        Checker.notNull(row);
        Checker.inRange(volume, Cycle.MIN_VOLUME, Cycle.MAX_VOLUME);
        Checker.inRange(brewSecs, Cycle.MIN_TIME, Cycle.MAX_TIME);
        Checker.inRange(vacSecs, Cycle.MIN_TIME, Cycle.MAX_TIME);

        setRowChildText(row, volumeMlColumn, volume);
        setRowChildText(row, brewSecsColumn, brewSecs);
        setRowChildText(row, vacuumSecsColiumn, vacSecs);
    }

    private void setRowChildText(TableRow row, int column, int val) {
        Checker.notNull(row);
        Checker.atLeast(column, 0);
        Checker.greaterThan(val, 0);

        TextView tv = (TextView) row.getChildAt(column);
        tv.setText(Integer.toString(val));
    }
}
