package com.ssimon.cyclesactivity.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.ssimon.cyclesactivity.util.UiUtils;
import com.ssimon.cyclesactivity.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CycleActivity extends AppCompatActivity {
    static final private List<Integer> VOLUMES = getVolumes();  // range of values for volumes per cycle
    static final private List<Integer> TIMES = getTimes();      // range of values for brew times and vacuum times for each cycle
    static final private List<Integer> LASTCYCLE_VACUUMTIMES = getLastCycleVacuumTimes();   // vacuum times in last cycle have their own range

    static final private int HEADER_ROW = 0;                    // header row for table of parameter values
    static final private int FIRST_PARM_ROW = HEADER_ROW + 1;   // first non-header row for parameter values

    private int cycleNumColumn;     // index for column containing cycle numbers in table of parameter values
    private int firstParmColumn;    // index for column containing first (user-adjustable) parameter, for range checking
    private int volumeMlColumn;     // index for column containing volume value
    private int brewSecsColumn;     // index for colume containing value for brew time
    private int vacuumSecsColiumn;  // index for column containing value for vacuum time
    private int lastParmColumn;     // index for column containing last (user-adjustable) parameter, for range checking

    private TextView totalVolumeText;   // displays the sum of the volumes for all cycles
    private TableLayout parmTable;      // table of parameter values
    private SeekBar seekBar;            // used to make gross adjustments of parameter values
    private TextView minValueText, maxValueText;    // the minimum and maximum values for the currently-selected parameter
    private ImageButton decrementButton, incrementButton;   // used to make fine adjustments of parameter values
    private Button deleteCycleButton, addCycleButton;   // used to delete and add cycles in the table of parameter values

    private Button currentParmButton = null;    // the currently selected parameter (either the volume, brew time, or vacuum time)
    private Coffee coffee;                      // the currently selected coffee, whose volume is being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycle_activity);
        setViews();
        setParmColumnIndices();
        initParmTable();
        UiUtils.setDecrementButton(decrementButton, seekBar);
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

    // handle the user's selection of the brew time parameter in a cycle
    public void onClickBrewSecs(View v) {
        boolean isVolume = false;
        onClickParm(v, TIMES, isVolume);
    }

    // handle the user's selection of the vacuum time parameter in a cycle
    public void onClickVacuumSecs(View v) {
        List<Integer> values = isLastCycle(v)
                ? LASTCYCLE_VACUUMTIMES
                : TIMES;
        boolean isVolume = false;
        onClickParm(v, values, isVolume);
    }

    // handle the user's selection of the volume parameter in a cycle
    public void onClickVolumeMl(View v) {
        boolean isVolume = true;
        onClickParm(v, VOLUMES, isVolume);
    }

    // add a cycle to the parameter table
    // Note: the add button is disabled when the maximum number of cycles is reached,
    // so it should never be possible to exceed the maximum.
    public void onClickAddCycle(View v) {
        for (int i = 0; i < Volume.MAX_NUM_CYCLES; i++) {
            TableRow row = (TableRow) parmTable.getChildAt(i + FIRST_PARM_ROW);
            if (row.getVisibility() == View.INVISIBLE) {
                row.setVisibility(View.VISIBLE);
                setDeleteCycleButtonEnabled();
                setAddCycleButtonEnabled();
                return;
            }
        }
        throw new IllegalStateException("attempted to exceed max num cycles");
    }

    // remove a cycle to the parameter table
    // Note 1: the delete button is disabled when the minimum number of cycles is reached,
    // so it should never be possible to fall under the minimum.
    // Note 2: becuase the last cycle has a different minimum vacuum time than other cycles,
    // the vacuum time for the newly-created last cycle resulting from deleting the current
    // last cycle is clipped to the minimum value.
    public void onClickDeleteCycle(View unused) {
        for (int i = Volume.MAX_NUM_CYCLES - 1; i >= 0; i--) {
            TableRow row = (TableRow) parmTable.getChildAt(i + FIRST_PARM_ROW);
            if (row.getVisibility() != View.VISIBLE)
                continue;
            row.setVisibility(View.INVISIBLE);
            TableRow newLastRow = (TableRow) parmTable.getChildAt(i - 1 + FIRST_PARM_ROW);
            Button b = (Button) newLastRow.getChildAt(vacuumSecsColiumn);
            String s = b.getText().toString();
            int vacSecs = Integer.valueOf(s);
            int min = LASTCYCLE_VACUUMTIMES.get(0);
            if (vacSecs < min)
                b.setText(Integer.toString(min));
            setDeleteCycleButtonEnabled();
            setAddCycleButtonEnabled();
            return;
        }
        throw new IllegalArgumentException("attempted to delete last remaining cycle");
    }

    // Save the values currently displayed in the parameter tables as a Volume object.
    public void onClickSaveCycles(View unused) {
        List<Cycle> cycles = buttonsToCycles();
        int vol = (new Volume(cycles)).totalVolume();
        long existingVolumeId = findMatchingVolume(coffee.volumes(), vol);
        if (existingVolumeId == Const.NULL_DATABASE_ID) {
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.saveVolume(coffee.id(), cycles);
            this.finish();
        } else {
            promptToOverWriteVolume(existingVolumeId, cycles);
        }
    }

    // sets up data used by activity and the activity's UI
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void configureActivity(CoffeeRefreshEvent unused ) {
        List<Coffee> coffees = CoffeeCache.getCoffees();
        if (coffees == null) {
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.refreshCoffeeCache();
        } else {
            Intent i = getIntent();
            long coffeeId = AndroidUtils.getLongIntentExtraOrThrow(i, CoffeeActivity.EXTRA_COFFEEID);
            coffee = UiUtils.getCoffeeById(coffeeId, coffees);
            long volumeId = AndroidUtils.getLongIntentExtraOrThrow(i, VolumeActivity.EXTRA_VOLUMEID);
            List<Cycle> cycles = (volumeId == Const.NULL_DATABASE_ID)
                    ? ModelUtils.createDefaultCyclesTemplate()
                    : getCyclesByIds(coffeeId, volumeId, coffees);
            configureUi(cycles);
        }
    }

    // Create a list of cycles based on the currently displayed values
    // in the table of parameters.
    private List<Cycle> buttonsToCycles() {
        List<Cycle> cycles = new ArrayList<>();
        for (int i = 0; i < Volume.MAX_NUM_CYCLES; i++) {
            TableRow row = (TableRow) parmTable.getChildAt(i + FIRST_PARM_ROW);
            if (row.getVisibility() == View.VISIBLE) {
                int val = getButtonInt(row, volumeMlColumn);
                int brew = getButtonInt(row, brewSecsColumn);
                int vac = getButtonInt(row, vacuumSecsColiumn);
                cycles.add(new Cycle(val, brew, vac));
            } else {
                break;
            }
        }
        return cycles;
    }

    // selects a default parameter (some parameter must always be selected)
    private void clickDefaultParmButton() {
        TableRow row = (TableRow) parmTable.getChildAt(FIRST_PARM_ROW);
        View v = row.getChildAt(volumeMlColumn);
        onClickVolumeMl(v);
    }

    // sets up the UI for the activity
    private void configureUi(List<Cycle> cycles) {
        Checker.notNull(cycles);
        Checker.inRange(cycles.size(), Volume.MIN_NUM_CYCLES, Volume.MAX_NUM_CYCLES);

        cyclesToButtons(cycles);
        clickDefaultParmButton();
        setTotalVolumeText();
        setAddCycleButtonEnabled();
        setDeleteCycleButtonEnabled();
        TextView name = (TextView) findViewById(R.id.txt_coffee);
        name.setText(coffee.name());
        int totalVolume = (new Volume(cycles)).totalVolume();
        TextView initial = (TextView) findViewById(R.id.txt_initialvolume);
        initial.setText(String.format(getString(R.string.cycle_txt_volumeformat), totalVolume));
    }

    // display cycle parameter values in the buttons in the parameter table
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

    // Return the ID of the Volume object with the specified total volume
    // NULL_DATABASE_ID if there is no such volume.
    private long findMatchingVolume(List<Volume> vs, int totalVolume) {
        Checker.notNullOrEmpty(vs);
        Checker.greaterThan(totalVolume, 0);

        for (Volume v : vs) {
            if (v.totalVolume() == totalVolume)
                return v.id();
        }
        return Const.NULL_DATABASE_ID;
    }

    // Gets the index for the brew time column.
    private int getBrewSecsColumnIndex() {
        return getParmColumnIndex(R.string.cycle_txt_brewsecs);
    }

    // Get the value of the integer being displayed for the parameter in the specified column.
    private int getButtonInt(TableRow row, int column) {
        Checker.notNull(row);
        Checker.inRange(column, firstParmColumn, lastParmColumn);

        Button b = (Button) row.getChildAt(column);
        String s = b.getText().toString();
        return Integer.valueOf(s);
    }

    // Get the index of the brew time column.
    private int getCycleNumColumnIndex() {
        return getParmColumnIndex(R.string.cycle_txt_cycle);
    }

    // Find the list of cycles associated with the specified coffee and volume IDs.
    // Note: it is assumed that the volume specified by those IDs exists.
    private List<Cycle> getCyclesByIds(long coffeeId, long volumeId, List<Coffee> coffees) {
        Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
        Checker.atLeast(volumeId, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(coffees);

        List<Volume> vols = UiUtils.getVolumesByCoffeeId(coffeeId, coffees);
        for (Volume v : vols) {
            if (v.id() == volumeId)
                return v.cycles();
        }
        final String format = "No coffee with id == %d and volume == %d";
        throw new IllegalArgumentException(String.format(format, coffeeId, volumeId));
    }

    // Create a list of integers starting with min and ending with max, using
    // the specified increment.
    static private List<Integer> getIntegerList(int min, int max, int increment) {
        Checker.atLeast(min, 0);
        Checker.lessThan(min, max);
        Checker.atLeast(increment, 1);

        List<Integer> vals = new ArrayList<>();
        for (int i = min; i <= max; i += increment)
            vals.add(i);
        return Collections.unmodifiableList(vals);
    }

    // Get the range of values for the vacuum times for the last cycle.
    static private List<Integer> getLastCycleVacuumTimes() {
        final int increment = 1;
        return getIntegerList(Cycle.MIN_LASTCYCLE_VACUUMTIME, Cycle.MAX_TIME, increment);
    }

    // Determine how many cycles have been created.
    private int getNumVisibleParmRows() {
        int numRows = 0;
        for (int i = 0; i < Volume.MAX_NUM_CYCLES; i++) {
            TableRow row = (TableRow) parmTable.getChildAt(i + FIRST_PARM_ROW);
            if (row.getVisibility() == View.INVISIBLE)
                break;
            ++numRows;
        }
        Checker.inRange(numRows, Volume.MIN_NUM_CYCLES, Volume.MAX_NUM_CYCLES);
        return numRows;
    }

    // Get the index for a column in the parameter table based on the
    // id of the string used for the column's heading.
    // Note: it is assumed that the string actually appears in the heading.
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

    // Get the range of values for brew times and (non-last cycle) vacuum times.
    static private List<Integer> getTimes() {
        final int increment = 1;
        return getIntegerList(Cycle.MIN_TIME, Cycle.MAX_TIME, increment);
    }

    // Gets the index for the vacuum time column.
    private int getVacuumSecsColumnIndex() {
        return getParmColumnIndex(R.string.cycle_txt_vacuumsecs);
    }

    // Get the index for the volume column.
    private int getVolumeMlColumnIndex() {
        return getParmColumnIndex(R.string.cycle_txt_volumeml);
    }

    // Get the range of values for the volume quantity.
    static private List<Integer> getVolumes() {
        final int increment = 10;
        return getIntegerList(Cycle.MIN_VOLUME, Cycle.MAX_VOLUME, increment);
    }

   // Set the values for the specified row in the parameter table, including the cycle number.
    private void initParmRow(TableRow row, int rowIndex, int volume, int brewSecs, int vacSecs) {
        Checker.notNull(row);
        Checker.atLeast(rowIndex, 0);
        Checker.inRange(volume, Cycle.MIN_VOLUME, Cycle.MAX_VOLUME);
        Checker.inRange(brewSecs, Cycle.MIN_TIME, Cycle.MAX_TIME);
        Checker.inRange(vacSecs, Cycle.MIN_TIME, Cycle.MAX_TIME);

        setRowChildText(row, cycleNumColumn, rowIndex+1);
        setParmRow(row, volume, brewSecs, vacSecs);
    }

    // Create the rows for the user-adjustable parameters in the table of cycles.
    // Note: the rows are created dynamically rather than in XML in order to
    // ensure that the number of rows is consistent with the number of cycles.
    private void initParmTable() {
        LayoutInflater infl = LayoutInflater.from(this);
        for (int i = 0; i < Volume.MAX_NUM_CYCLES; i++) {
            TableRow row = (TableRow) infl.inflate(R.layout.cycle_item_parm_row, null);
            row.setVisibility(View.INVISIBLE);
            initParmRow(row, i, VOLUMES.get(0), TIMES.get(0), LASTCYCLE_VACUUMTIMES.get(0));
            parmTable.addView(row);
        }
    }

    // Determine whether the current cycle is the last one; used to know what the minimum
    // vacuum time should be.
    private boolean isLastCycle(View v) {
        Checker.notNull(v);
        TableRow row = (TableRow) v.getParent();
        TextView tv = (TextView) row.getChildAt(cycleNumColumn);
        String rowNumStr = tv.getText().toString();
        int rowNum = Integer.valueOf(rowNumStr);
        int nrows = getNumVisibleParmRows();
        return rowNum == nrows;
    }

    // Common code used to handle clicks of all cycle parameters.
    private void onClickParm(View v, List<Integer> values, boolean isVolume) {
        Checker.notNull(v);
        Checker.notNullOrEmpty(values);

        if (currentParmButton != null)
            currentParmButton.setBackgroundResource(R.drawable.white_rectangle);
        currentParmButton = (Button) v;
        currentParmButton.setBackgroundResource(R.drawable.yellow_rectangle);
        String s = currentParmButton.getText().toString();
        int val = Integer.parseInt(s);
        int idx = values.indexOf(val);
        if (idx < 0)
            throw new IllegalStateException("failed to find parm value = " + val);
        int maxIdx = values.size() - 1;
        seekBar.setOnSeekBarChangeListener(null);   // set to null to suppress calls to listener when set max and progress
        seekBar.setMax(maxIdx);
        seekBar.setProgress(idx);
        seekBar.setOnSeekBarChangeListener(seekBarListener(values, isVolume));
        UiUtils.setIncrementButton(incrementButton, seekBar, maxIdx);
        minValueText.setText(Integer.toString(values.get(0)));
        maxValueText.setText(Integer.toString(values.get(maxIdx)));
    }

    // If a Volume object with the same total volume exists, prompt the user whether
    // to overwrite it, since each Coffee object can contain only one Volume object
    // with a given total volume.
    private void promptToOverWriteVolume(final long volumeId, final List<Cycle> cycles) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Confirm Replacement");
        b.setMessage("Replace existing volume?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int unused) {
                DatabaseHelper dh = DatabaseHelper.getInstance(CycleActivity.this);
                dh.replaceVolume(volumeId, cycles);
                dialog.dismiss();
                CycleActivity.this.finish();
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int unused) {
                dialog.dismiss();
            }
        });
        b.create().show();
    }

    // Handles gross adjustments of parameter values.
    // Note: the total volume is recalculated if (and only if) the parameter being adjusted is the volume.
    private SeekBar.OnSeekBarChangeListener seekBarListener(final List<Integer> values, final boolean isVolume) {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar unused, int idx, boolean unused1) {
                Checker.inRange(idx, 0, values.size() - 1);
                int val = values.get(idx);
                currentParmButton.setText(Integer.toString(val));
                if (isVolume)
                    setTotalVolumeText();
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }

    // Enable the Add Cycle button if and only if the current number of cycles is less
    // than the maximum.
    private void setAddCycleButtonEnabled() {
        int n = getNumVisibleParmRows();
        UiUtils.setViewEnabled(addCycleButton,
                n == Volume.MAX_NUM_CYCLES ? false : true);
    }

    // Enable the Delete Cycle button if and only if the current number of cycles is greater
    // than the minimum.
    private void setDeleteCycleButtonEnabled() {
        int n = getNumVisibleParmRows();
        UiUtils.setViewEnabled(deleteCycleButton,
                n == Volume.MIN_NUM_CYCLES ? false : true);
    }

    // Determines the columns for each item in the table of parameters, based on the strings
    // that appear in the header.
    private void setParmColumnIndices() {
        brewSecsColumn = getBrewSecsColumnIndex();
        cycleNumColumn = getCycleNumColumnIndex();
        vacuumSecsColiumn = getVacuumSecsColumnIndex();
        volumeMlColumn = getVolumeMlColumnIndex();
        firstParmColumn = Math.min(brewSecsColumn, Math.min(vacuumSecsColiumn, volumeMlColumn));
        lastParmColumn = Math.max(brewSecsColumn, Math.max(vacuumSecsColiumn, volumeMlColumn));
    }

    // Set the values for the user-adjustable parameters in the specified row in the parmater table.
    private void setParmRow(TableRow row, int volume, int brewSecs, int vacSecs) {
        Checker.notNull(row);
        Checker.inRange(volume, Cycle.MIN_VOLUME, Cycle.MAX_VOLUME);
        Checker.inRange(brewSecs, Cycle.MIN_TIME, Cycle.MAX_TIME);
        Checker.inRange(vacSecs, Cycle.MIN_TIME, Cycle.MAX_TIME);

        setRowChildText(row, volumeMlColumn, volume);
        setRowChildText(row, brewSecsColumn, brewSecs);
        setRowChildText(row, vacuumSecsColiumn, vacSecs);
    }

    // Display the specified parameter value in the button specified by
    // the parameter table row and column.
    private void setRowChildText(TableRow row, int column, int val) {
        Checker.notNull(row);
        Checker.atLeast(column, 0);
        Checker.greaterThan(val, 0);

        TextView tv = (TextView) row.getChildAt(column);
        tv.setText(Integer.toString(val));
    }

    // Calculate and display the total volume across all cycles.
    private void setTotalVolumeText() {
        int vol = 0;
        for (int i = 0; i < Volume.MAX_NUM_CYCLES; i++) {
            TableRow row = (TableRow) parmTable.getChildAt(i + FIRST_PARM_ROW);
            if (row.getVisibility() == View.INVISIBLE)
                break;
            vol += getButtonInt(row, volumeMlColumn);
        }
        totalVolumeText.setText(String.format(getString(R.string.cycle_txt_volumeformat), vol));
    }

    // Gets all the views that can be reset as the user interacts with the activity
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
}
