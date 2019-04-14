package com.ssimon.cyclesactivity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.model.Cycle;

import java.util.ArrayList;
import java.util.List;

public class AddcoffeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcoffee_activity);
        List<String> items = getCycleNumbers();
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.addcoffee_partial_spinneritem, items);
        Spinner s = (Spinner) findViewById(R.id.spin_numcycles);
        s.setAdapter(aa);
    }

    private List<String> getCycleNumbers() {
        List<String> nums = new ArrayList<>();
        for (int i = Cycle.MIN_NUM_CYCLES; i <= Cycle.MAX_NUM_CYCLES; i++)
            nums.add(String.valueOf(i));
        return nums;
    }
}
