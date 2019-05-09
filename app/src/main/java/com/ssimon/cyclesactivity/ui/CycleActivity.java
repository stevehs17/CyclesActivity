package com.ssimon.cyclesactivity.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
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

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.data.CoffeeCache;
import com.ssimon.cyclesactivity.data.DatabaseHelper;
import com.ssimon.cyclesactivity.message.CoffeeRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;
import com.ssimon.cyclesactivity.util.AndroidUtils;
import com.ssimon.cyclesactivity.util.Checker;
import com.ssimon.cyclesactivity.util.ModelUtils;
import com.ssimon.cyclesactivity.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    protected void onResume() {
        super.onResume();
        Utils.registerEventBus(this);
        configureActivity(null);
    }

    @Override
    protected void onPause() {
        Utils.unregisterEventBus(this);
        super.onPause();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void configureActivity(CoffeeRefreshEvent unused ) {
        List<Coffee> coffees = CoffeeCache.getCoffees();
        if (coffees == null) {
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.refreshCoffeeCache();
        } else {
            Intent i = getIntent();
            long coffeeId = AndroidUtils.getLongIntentExtraOrThrow(i, CoffeeActivity.EXTRA_COFFEEID);
            coffee = getCoffeeById(coffeeId, coffees);
            long volumeId = AndroidUtils.getLongIntentExtraOrThrow(i, VolumeActivity.EXTRA_VOLUMEID);
            List<Cycle> cycles = (volumeId == Const.NULL_DATABASE_ID)
                    ? ModelUtils.createDefaultCyclesTemplate()
                    : getCyclesByIds(coffeeId, volumeId, coffees);
            configureUi(cycles);
        }
    }

    private void configureUi(List<Cycle> cycles) {
        Checker.notNull(cycles);
        Checker.inRange(cycles.size(), Volume.MIN_NUM_CYCLES, Volume.MAX_NUM_CYCLES);

        cyclesToButtons(cycles);
        //clickDefaultParmButton();
        //setTotalVolume();
        //setAddCycleButton();
        //setDeleteCycleButton();
        TextView name = (TextView) findViewById(R.id.txt_coffee);
        name.setText(coffee.name());
        int totalVolume = (new Volume(cycles)).totalVolume();
        TextView initial = (TextView) findViewById(R.id.txt_initialvolume);
        initial.setText(Integer.toString(totalVolume) + " mL");
    }

    private void cyclesToButtons(List<Cycle> cycles) {
        Checker.notNull(cycles);
        Checker.inRange(cycles.size(), Volume.MIN_NUM_CYCLES, Volume.MAX_NUM_CYCLES);

        for (int i = 0; i < cycles.size(); i++) {
            TableRow row = (TableRow) parmTable.getChildAt(i + FIRST_PARM_ROW);
            row.setVisibility(View.VISIBLE);
            Cycle c = cycles.get(i);
            setParmRow(row, c.volumeMl(), c.brewSeconds(), c.vacuumSeconds());
        }
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

    private Coffee getCoffeeById(long id, List<Coffee> coffees) {
        Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(coffees);

        for (Coffee c : coffees) {
            if (c.id() == id)
                return c;
        }
        throw new IllegalArgumentException("no coffee found with id = " + id);
    }

    private List<Cycle> getCyclesByIds(long coffeeId, long volumeId, List<Coffee> coffees) {
        Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
        Checker.atLeast(volumeId, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(coffees);

        List<Volume> vols = getVolumesByCoffeeId(coffeeId, coffees);
        for (Volume v : vols) {
            if (v.id() == volumeId)
                return v.cycles();
        }
        final String format = "No coffee with id == %d and volume == %d";
        throw new IllegalArgumentException(String.format(format, coffeeId, volumeId));
    }

    private List<Volume> getVolumesByCoffeeId(long id, List<Coffee> coffees) {
        Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(coffees);

        for (Coffee c : coffees) {
            if (c.id() == id)
                return c.volumes();
        }
        throw new IllegalArgumentException("no coffee found with id = " + id);
    }
}
