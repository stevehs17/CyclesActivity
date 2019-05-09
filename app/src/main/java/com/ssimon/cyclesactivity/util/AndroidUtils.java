package com.ssimon.cyclesactivity.util;

import android.content.Intent;

import com.ssimon.cyclesactivity.Const;

public class AndroidUtils {
    private AndroidUtils() {
        throw new UnsupportedOperationException();
    }

    static public long getLongIntentExtraOrThrow(Intent i, String key) {
        Checker.notNull(i);
        Checker.notNullOrEmpty(key);

        long defaultVal = Const.NULL_LONG;
        long val = i.getLongExtra(key, defaultVal);
        Checker.notEquals(val, defaultVal);
        return val;
    }


}
