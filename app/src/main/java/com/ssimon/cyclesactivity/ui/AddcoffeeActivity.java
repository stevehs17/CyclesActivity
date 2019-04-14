package com.ssimon.cyclesactivity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.model.Cycle;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AddcoffeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back_addcoffee_activity);
        List<String> items = getCycleNumbers();
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
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
