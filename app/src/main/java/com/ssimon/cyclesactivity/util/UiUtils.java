package com.ssimon.cyclesactivity.util;

import android.widget.Button;

public class UiUtils {
    private UiUtils() {
        throw new UnsupportedOperationException();
    }

    static public void setButtonEnabled(Button b, boolean enable) {
        Checker.notNull(b);
        if (enable)
            enableButton(b);
        else
            disableButton(b);
    }

    static private void disableButton(Button b) {
        b.setAlpha(0.5f);
        b.setEnabled(false);
    }

    static private void enableButton(Button b) {
        b.setAlpha(1f);
        b.setEnabled(true);
    }
}
