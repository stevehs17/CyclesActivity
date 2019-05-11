package com.ssimon.cyclesactivity.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class UiUtils {
    private UiUtils() {
        throw new UnsupportedOperationException();
    }

    static public void setDecrementButton(ImageButton btn, SeekBar bar) {
        setSeekBarButton(btn, bar, new ProgressDecrementer());
    }

    static public void setIncrementButton(ImageButton btn, SeekBar bar, int maxIndex) {
        setSeekBarButton(btn, bar, new ProgressIncrementer(maxIndex));
    }

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


    private interface ProgressProcessor {
        void setProgress(SeekBar bar);
    }

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

    static public void setViewEnabled(View v, boolean enable) {
        Checker.notNull(v);
        v.setEnabled(enable);
        v.setAlpha(enable ? 1.0f : .5f);
    }
}