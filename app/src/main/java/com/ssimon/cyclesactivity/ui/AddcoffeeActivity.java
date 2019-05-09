package com.ssimon.cyclesactivity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.data.CoffeeCache;
import com.ssimon.cyclesactivity.data.DatabaseHelper;
import com.ssimon.cyclesactivity.message.CoffeeRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;
import com.ssimon.cyclesactivity.util.Checker;
import com.ssimon.cyclesactivity.util.GroundControlException;
import com.ssimon.cyclesactivity.util.UiUtils;
import com.ssimon.cyclesactivity.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class AddcoffeeActivity extends AppCompatActivity {
    static final private int DEFAULT_BREW_TIME = (Cycle.MAX_TIME - Cycle.MIN_TOTAL_TIME) / 2;
    private SeekBar seekBar;
    private TextView brewTimeText;
    private Spinner numCyclesSpin;
    private EditText nameEdit;
    private Button createButton;
    private List<Coffee> coffees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcoffee_activity);
        List<String> items = getCycleNumbers();
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.addcoffee_partial_spinneritem, items);
        Spinner s = (Spinner) findViewById(R.id.spin_numcycles);
        s.setAdapter(aa);
        TextView min = (TextView) findViewById(R.id.txt_min_value);
        min.setText(Integer.toString(Cycle.MIN_TOTAL_TIME));
        TextView max = (TextView) findViewById(R.id.txt_max_value);
        max.setText(Integer.toString(Cycle.MAX_TIME));
        seekBar = (SeekBar) findViewById(R.id.seek);
        seekBar.setMax(Cycle.MAX_TIME - Cycle.MIN_TOTAL_TIME);
        seekBar.setOnSeekBarChangeListener(seekBarListener());
        ImageButton decr = (ImageButton) findViewById(R.id.btn_decrement);
        //decr.setOnClickListener(decrementListener());
        ImageButton incr = (ImageButton) findViewById(R.id.btn_increment);
        //incr.setOnClickListener(incrementListener());
        brewTimeText = (TextView) findViewById(R.id.txt_brewtime);

        int idx = DEFAULT_BREW_TIME - Cycle.MIN_TOTAL_TIME;
        seekBar.setProgress(idx);

        setDecrementButton();
        setIncrementButton();

        findViewById(R.id.activity_addcoffee).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        numCyclesSpin = (Spinner) findViewById(R.id.spin_numcycles);
        nameEdit = (EditText) findViewById(R.id.edit_name);
        createButton = (Button) findViewById(R.id.btn_create);
        UiUtils.setButtonEnabled(createButton, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.registerEventBus(this);
        Utils.postEvent(new CoffeeRefreshEvent());
    }

    @Override
    protected void onPause() {
        Utils.unregisterEventBus(this);
        super.onPause();
    }


    private void setDecrementButton() {
        setSeekBarButton(true);
    }

    private void setIncrementButton() {
        setSeekBarButton(false);
    }


    private View.OnClickListener decrementListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                seekBar.setProgress(seekBar.getProgress() - 1);
            }
        };
    }

    private View.OnClickListener incrementListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                seekBar.setProgress(seekBar.getProgress() + 1);
            }
        };
    }


    private SeekBar.OnSeekBarChangeListener seekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar unused1, int value, boolean fromUser) {
                value += Cycle.MIN_TOTAL_TIME;
                brewTimeText.setText(Integer.toString(value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar unused) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar unused) {
            }
        };
    }

    private List<String> getCycleNumbers() {
        List<String> nums = new ArrayList<>();
        for (int i = Volume.MIN_NUM_CYCLES; i <= Volume.MAX_NUM_CYCLES; i++)
            nums.add(String.valueOf(i));
        return nums;
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
        }
    }

    private void increment() {
        int idx = seekBar.getProgress();
        if (idx < (Cycle.MAX_TIME - Cycle.MIN_TOTAL_TIME)) {
            idx++;
            seekBar.setProgress(idx);
        }
    }

    public void onClickCreate(View unused) {
        try {
            String name = getCoffeeName();
            int numCycles = getNumCycles();
            int brewTime = getBrewTime();
            List<Volume> vols = createVolumes(numCycles, brewTime);
            Coffee c = new Coffee(name, vols);
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.saveCoffee(c);
            finish();
        } catch (GroundControlException unused1) {
        }
    }

    private String getCoffeeName() throws GroundControlException {
        String name = nameEdit.getText().toString();
        if (name.isEmpty()) {
            nameEdit.requestFocus();
            nameEdit.setError("Please fill in value");
            throw new GroundControlException();
        }
        List<Coffee> coffees = CoffeeCache.getCoffees();
        Checker.notNullOrEmpty(coffees);
        if (nameInUse(name, coffees)) {
            nameEdit.requestFocus();
            nameEdit.setError("Name is already used.");
            throw new GroundControlException();
        }
        return name;
    }

    private boolean nameInUse(String name, List<Coffee> coffees) {
        Checker.notNullOrEmpty(name);
        Checker.notNullOrEmpty(coffees);

        for (Coffee c : coffees) {
            if (c.name().equals(name))
                return true;
        }
        return false;
    }

    private int getNumCycles() {
        try {
            String s = numCyclesSpin.getSelectedItem().toString();
            int n = Integer.valueOf(s);
            Checker.inRange(n, Volume.MIN_NUM_CYCLES, Volume.MAX_NUM_CYCLES);
            return n;
        } catch (NumberFormatException e) {
            throw new IllegalStateException(e);
        }
    }

    private int getBrewTime() {
        int idx = seekBar.getProgress();
        int time = idx + Cycle.MIN_TOTAL_TIME;
        Checker.inRange(time, Cycle.MIN_TOTAL_TIME, Cycle.MAX_TIME);
        return time;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCoffeeList(CoffeeRefreshEvent e) {
        coffees = CoffeeCache.getCoffees();
        if (coffees == null) {
            DatabaseHelper dh = DatabaseHelper.getInstance(this);
            dh.refreshCoffeeCache();
        } else {
            UiUtils.setButtonEnabled(createButton, true);
        }
    }

    private List<Volume> createVolumes(int numCycles, int brewTime) {
        Checker.inRange(numCycles, Volume.MIN_NUM_CYCLES, Volume.MAX_NUM_CYCLES);
        Checker.inRange(brewTime, Cycle.MIN_TOTAL_TIME, Cycle.MAX_TIME);

        List<Volume> vols = new ArrayList<>();
        for (Integer amount : numCyclesToAmounts(numCycles)) {
            int cycleAmount = getCycleAmount(amount, numCycles);
            int cycleVacTime = getVacuumTimePerCycle(cycleAmount);
            List<Cycle> cycles = new ArrayList<>();
            for (Float factor : numCyclesToTimeFactors(numCycles)) {
                int cycleBrewTime = (int) (brewTime * factor);
                cycles.add(new Cycle(cycleAmount, cycleBrewTime, cycleVacTime));
            }
            vols.add(new Volume(cycles));
        }
        return vols;
    }

    private int getCycleAmount(int totalVolume, int numCycles) {
        Checker.inRange(numCycles, Volume.MIN_NUM_CYCLES, Volume.MAX_NUM_CYCLES);

        int n = totalVolume/numCycles;
        n = (n/10) * 10;
        return n;
    }

    private List<Integer> numCyclesToAmounts(int n) {
        Checker.inRange(n, Volume.MIN_NUM_CYCLES, Volume.MAX_NUM_CYCLES);
        final int smallCup = 350;
        final int largeCup = 470;
        final int smallPot = 1000;
        final int mediumPot = 3000;
        final int largePot = 5000;

        switch (n) {
            case 1: return Arrays.asList(smallCup, largeCup, smallPot);
            case 2: return Arrays.asList(smallCup, largeCup, smallPot, mediumPot);
            case 3: return Arrays.asList(largeCup, smallPot, mediumPot);
            case 4:
            case 5:
            case 6: return Arrays.asList(smallPot, mediumPot, largePot);
            default: throw new IllegalArgumentException("Illegal number of cycles: " + n);
        }
    }

    private List<Float> numCyclesToTimeFactors(int n) {
        Checker.inRange(n, Volume.MIN_NUM_CYCLES, Volume.MAX_NUM_CYCLES);
        switch (n) {
            case 1: return Arrays.asList(1f);
            case 2: return Arrays.asList(.6f, .4f);
            case 3: return Arrays.asList(.4f, .3f, .3f);
            case 4: return Arrays.asList(.4f, .2f, .2f, .2f);
            case 5: return Arrays.asList(.3f, .2f, .2f, .15f, .15f);
            case 6: return Arrays.asList(.3f, .15f, .15f, .15f, .15f, .1f);
            default: throw new IllegalArgumentException("Illegal number of cycles: " + n);
        }
    }

    private int getVacuumTimePerCycle(int volumePerCycle) {
        Checker.atLeast(volumePerCycle, 1);

        if (volumePerCycle < 300)
            return 15;
        else if (volumePerCycle >= 300 & volumePerCycle < 500)
            return 25;
        else
            return 40;
    }


}
