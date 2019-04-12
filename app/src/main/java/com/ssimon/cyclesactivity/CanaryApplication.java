package com.ssimon.cyclesactivity;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.ssimon.cyclesactivity.util.Checker;

public class CanaryApplication extends Application {
    public static CanaryApplication instance;
    private RefWatcher refWatcher;

    static public RefWatcher getRefWatcher(Context c) {
        Checker.notNull(c);
        CanaryApplication app = (CanaryApplication) c.getApplicationContext();
        return app.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        if (LeakCanary.isInAnalyzerProcess(this))
            return; // process is dedicated to LeakCanary for heap analysis.
        refWatcher = LeakCanary.install(this);
        */
    }
}
