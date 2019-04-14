package com.ssimon.cyclesactivity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.model.Cycle;

import java.util.ArrayList;
import java.util.List;

public class AddcoffeeActivity extends AppCompatActivity {
    static final private int DEFAULT_BREW_TIME = (Cycle.MAX_TIME - Cycle.MIN_TOTAL_TIME)/2;
    SeekBar seekBar;
    TextView brewTimeText;

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
        decr.setOnClickListener(decrementListener());
        ImageButton incr = (ImageButton) findViewById(R.id.btn_increment);
        incr.setOnClickListener(incrementListener());
        brewTimeText = (TextView) findViewById(R.id.txt_brewtime);

        int idx = DEFAULT_BREW_TIME - Cycle.MIN_TOTAL_TIME;
        seekBar.setProgress(idx);
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
        for (int i = Cycle.MIN_NUM_CYCLES; i <= Cycle.MAX_NUM_CYCLES; i++)
            nums.add(String.valueOf(i));
        return nums;
    }
}
