package com.ssimon.cyclesactivity.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import java.util.List;

public class UiUtils {
    private UiUtils() {
        throw new UnsupportedOperationException();
    }

    // Find the coffee in the list of coffees that has the specified ID.
    // Note: it is assumed that the coffee with that ID occurs in the list.
    static public Coffee getCoffeeById(long coffeeId, List<Coffee> coffees) {
        Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(coffees);

        for (Coffee c : coffees) {
            if (c.id() == coffeeId)
                return c;
        }
        throw new IllegalArgumentException("No coffee found with id = " + coffeeId);
    }

    // Return the list of volumes contained in the Coffee with the specified ID.
    static public List<Volume> getVolumesByCoffeeId(long coffeeId, List<Coffee> coffees) {
        Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(coffees);

        for (Coffee c : coffees) {
            if (c.id() == coffeeId)
                return c.volumes();
        }
        throw new IllegalArgumentException("No coffee found with id = " + coffeeId);
    }

    static private class Repeater implements Runnable {
        final private ImageButton button;
        final private SeekBar seekBar;
        final private ProgressProcessor processor;
        private boolean isFirstRun = true;

        Repeater(ImageButton button, SeekBar seekBar, ProgressProcessor processor) {
            Checker.notNull(button);
            Checker.notNull(seekBar);
            Checker.notNull(processor);

            this.button = button;
            this.seekBar = seekBar;
            this.processor = processor;
        }

        @Override
        public void run() {
            processor.setProgress(seekBar);
            int millis = 100;
            if (isFirstRun) {
                isFirstRun = false;
                millis = 2000;
            }
            button.postDelayed(this, millis);
        }
    }

    // Set button used to decrement values also set by SeekBar
    static public void setDecrementButton(ImageButton btn, SeekBar bar) {
        setSeekBarButton(btn, bar, new ProgressDecrementer());
    }

    // Set button used to increment values also set by SeekBar
    static public void setIncrementButton(ImageButton btn, SeekBar bar, int maxIndex) {
        setSeekBarButton(btn, bar, new ProgressIncrementer(maxIndex));
    }

    // Set buttons used to change values also set by SeekBar
    static private void setSeekBarButton(final ImageButton btn, final SeekBar bar,
                                         final ProgressProcessor proc) {
        Checker.notNull(btn);
        Checker.notNull(bar);
        Checker.notNull(proc);

        final Runnable repeater = new Runnable() {
            @Override
            public void run() {
                proc.setProgress(bar);
                final int millis = 100;
                btn.postDelayed(this, millis);
            }
        };

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    proc.setProgress(bar);
                    v.postDelayed(repeater, ViewConfiguration.getLongPressTimeout());
                } else if (e.getAction() == MotionEvent.ACTION_UP) {
                    v.removeCallbacks(repeater);
                }
                return false;
            }
        });
    }

    // Interface used by setSeekBarButton.
    private interface ProgressProcessor {
        void setProgress(SeekBar bar);
    }

    // Decrement Progress bar
    static private class ProgressIncrementer implements ProgressProcessor {
        final private int maxIndex;

        ProgressIncrementer(int maxIndex) {
            Checker.greaterThan(maxIndex, 0);
            this.maxIndex = maxIndex;
        }

        @Override
        public void setProgress(SeekBar bar) {
            Checker.notNull(bar);
            int idx = bar.getProgress();
            if (idx < maxIndex) {
                idx++;
                bar.setProgress(idx);
            }
        }
    }

    // Incrment Progress bar
    static private class ProgressDecrementer implements ProgressProcessor {
        @Override
        public void setProgress(SeekBar bar) {
            Checker.notNull(bar);
            int idx = bar.getProgress();
            if (idx > 0) {
                idx--;
                bar.setProgress(idx);
            }
        }
    }

    // Enable/disable and undim/dim specified view (used for buttons)
    static public void setViewEnabled(View v, boolean enable) {
        Checker.notNull(v);
        v.setEnabled(enable);
        v.setAlpha(enable ? 1.0f : .5f);
    }
}