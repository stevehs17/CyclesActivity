package com.ssimon.cyclesactivity;

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
import java.util.List;

public class CyclesActivity extends AppCompatActivity {
    static final private int FIRST_PARM_ROW_INDEX = 1;
    static final private int FIRST_PARM_COLUMN_INDEX = 1;
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
        setDefaultParmButton();
    }

    private void setDefaultParmButton() {
        TableRow tr = (TableRow) parmTable.getChildAt(FIRST_PARM_ROW_INDEX);
        View v =  tr.getChildAt(FIRST_PARM_COLUMN_INDEX);
        onClickVolumeMl(v);
    }

    public void onClickVolumeMl(View v) {
        setCurrentParmButton(v, CycleValues.MIN_VOLUME, CycleValues.MAX_VOLUME,
                CycleValues.VOLUMES);
    }

    public void onClickBrewSecs(View v) {
        setCurrentParmButton(v, CycleValues.MIN_BREWTIME, CycleValues.MAX_BREWTIME,
                CycleValues.BREWTIMES);
    }

    public void onClickVacuumSecs(View v) {
        setCurrentParmButton(v, CycleValues.MIN_VACUUMTIME, CycleValues.MAX_VACUUMTIME,
                CycleValues.VACUUMTIMES);
    }

    private void setCurrentParmButton(View v, int minVal, int maxVal, List<Integer> values) {
        minValueText.setText(Integer.toString(minVal));
        maxValueText.setText(Integer.toString(maxVal));
        currentParmValues = values;
        if (currentParmButton != null)
            currentParmButton.setBackgroundResource(R.drawable.rectangular_outline_white);
        currentParmButton = (Button) v;
        currentParmButton.setBackgroundResource(R.drawable.rectangular_outline_yellow);
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

    private void setParmButton(int idx) {
        int val = currentParmValues.get(idx);
        currentParmButton.setText(Integer.toString(val));
    }

    public void onClickAddCycle(View unused) {
        for (int i = 2; i < parmTable.getChildCount(); i++) {
            View v = parmTable.getChildAt(i);
            if (v.getVisibility() == View.INVISIBLE) {
                v.setVisibility(View.VISIBLE);
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
}