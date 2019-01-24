package com.ssimon.cyclesactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

public class CyclesActivity extends AppCompatActivity {
    static final private String TAG = "MainActivity";
    private TextView minValueText, maxValueText;
    private SeekBar seekBar;
    private Button currentParmButton;
    private List<Integer> currentParmValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycles_activity);
        minValueText = (TextView) findViewById(R.id.txt_min_value);
        maxValueText = (TextView) findViewById(R.id.txt_max_value);
        seekBar = (SeekBar) findViewById(R.id.seek);
    }

    /*
    public void onClickVacuumSecs(View v) {
        Log.v(TAG, "clicked");
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        Log.v(TAG, "buttonText = " + buttonText);
        b.setText("123");
    }
    */

    public void onClickVacuumSecs(View v) {
        Log.v(TAG, "clicked");
        minValueText.setText(Integer.toString(CycleValues.MIN_VACUUMTIME));
        maxValueText.setText(Integer.toString(CycleValues.MAX_VACUUMTIME));
        currentParmValues = CycleValues.VACUUMTIMES;
        seekBar.setMax(currentParmValues.size());
        currentParmButton = (Button) v;
        String s = currentParmButton.getText().toString();
        int n = Integer.parseInt(s);
        seekBar.setProgress(n - CycleValues.MIN_VACUUMTIME);
        seekBar.setOnSeekBarChangeListener(seekBarListener());
    }

    private SeekBar.OnSeekBarChangeListener seekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int idx, boolean unused) {
                int val = currentParmValues.get(idx);
                currentParmButton.setText(Integer.toString(val));
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }

}


