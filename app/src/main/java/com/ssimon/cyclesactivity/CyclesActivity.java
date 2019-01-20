package com.ssimon.cyclesactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CyclesActivity extends AppCompatActivity {
    static final private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycles_activity);
    }

    public void onClickVacuumSecs(View v) {
        Log.v(TAG, "clicked");
    }
}


