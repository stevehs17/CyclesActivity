package com.ssimon.cyclesactivity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    static final private String TAG = "CyclesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycles_activity);
        minValueText = (TextView) findViewById(R.id.txt_min_value);
        maxValueText = (TextView) findViewById(R.id.txt_max_value);
        seekBar = (SeekBar) findViewById(R.id.seek);
        parmTable = (TableLayout) findViewById(R.id.lay_parms);
        setDecrementButton();

        final ImageButton incr = (ImageButton) findViewById(R.id.btn_increment);
        final Runnable rr = new Runnable() {
            @Override
            public void run() {
                onClickIncrement(null);
                incr.postDelayed(this, 100);
            }
        };
        incr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    onClickIncrement(null);
                    v.postDelayed(rr, ViewConfiguration.getLongPressTimeout());
                } else if (e.getAction() == MotionEvent.ACTION_UP) {
                    v.removeCallbacks(rr);
                }
                return true;
            }
        });

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
                int val = currentParmValues.get(idx);
                currentParmButton.setText(Integer.toString(val));
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }



    public void onClickDecrement(View unused) {
        if (currentParmValues == null)
            return;
        int idx = seekBar.getProgress();
        if (idx > 0) {
            idx--;
            setSeekBar(idx);
        }
    }

    public void onClickIncrement(View unused) {
        if (currentParmValues == null)
            return;
        int idx = seekBar.getProgress();
        if (idx < currentParmValues.size() - 1) {
            idx++;
            setSeekBar(idx);
        }
    }

    private void setSeekBar(int idx) {
        seekBar.setProgress(idx);
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
        final ImageButton decr = (ImageButton) findViewById(R.id.btn_decrement);
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                onClickDecrement(null);
                decr.postDelayed(this, 100);
            }
        };
        decr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    onClickDecrement(null);
                    v.postDelayed(r, ViewConfiguration.getLongPressTimeout());
                } else if (e.getAction() == MotionEvent.ACTION_UP) {
                    v.removeCallbacks(r);
                }
                return true;
            }
        });
    }
}