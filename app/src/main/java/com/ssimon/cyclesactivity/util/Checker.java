/*
 Copyright (c) 2019 Steven H. Simon
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain	a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.
*/

package com.ssimon.cyclesactivity.util;

import com.ssimon.cyclesactivity.Const;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public class Checker {
    private Checker(){ throw new UnsupportedOperationException(); }

    /* general validations */

    static public void atLeast(double val, double min) {
        if (val < min) {
            final String fmt = "val (%f) is less than min (%f)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt, val, min));
        }
    }

    static public void atLeast(float val, float min) {
        if (val < min) {
            final String fmt = "val (%f) is less than min (%f)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt, val, min));
        }
    }

    static public void atLeast(int val, int min) {
        if (val < min) {
            final String fmt = "val (%d) is less than min (%d)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt, val, min));
        }
    }

    static public void atLeast(long val, long min) {
        if (val < min) {
            final String fmt = "val (%d) is less than min (%d)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt, val, min));
        }
    }

    static public void greaterThan(double val, double lowerBound) {
        if (val <= lowerBound) {
            final String fmt = "val (%f) is less than or equal to lower bound (%f)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt, val, lowerBound));
        }
    }

    static public void greaterThan(int val, int lowerBound) {
        if (val <= lowerBound) {
            final String fmt = "val (%d) is less than or equal to lower bound (%d)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt, val, lowerBound));
        }
    }

    static public void greaterThan(long val, long lowerBound) {
        if (val <= lowerBound) {
            final String fmt = "val (%d) is less than or equal to lower bound (%d)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt, val, lowerBound));
        }
    }

    static public void inRange(float val, float min, float max) {
        atLeast(val, min);
        notGreaterThan(val, max);
    }

    static public void inRange(int val, int min, int max) {
        atLeast(val, min);
        notGreaterThan(val, max);
    }

    static public void isExactly(int val, int targetVal) {
        inRange(val, targetVal, targetVal);
    }

    static public void lessThan(int val, int upperBound) {
        if (val >= upperBound) {
            final String fmt = "val (%d) equals or exceeds upper bound (%d)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt,  val, upperBound));
        }
    }

    static public void notEmpty(Collection<?> col) {
        if (col.isEmpty())
            throw new IllegalStateException("collection is empty");
    }

    static public void notEmpty(Map<?, ?> map) {
        if (map.isEmpty())
            throw new IllegalStateException("map is empty");
    }

    static public void notEmpty(String str) {
        if (str.isEmpty())
             throw new IllegalStateException("string is empty");
    }

    static public void notEquals(int val, int targetVal) {
        if (val == targetVal)
            throw new IllegalStateException("val is equal to " + targetVal);
    }

    static public void notGreaterThan(float val, float max) {
        if (val > max) {
            final String fmt = "val (%f) is greater than max (%f)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt, val, max));
        }
    }

    static public void notGreaterThan(int val, int max) {
        if (val > max) {
            final String fmt = "val (%d) is greater than max (%d)";
            throw new IllegalStateException(String.format(Locale.getDefault(), fmt, val, max));
        }
    }

    static public void notNull(Collection<?> col) {
        if (col == null)
            throw new NullPointerException("collection is null");
    }

    static public void notNull(Map<?, ?> map) {
        if (map == null)
            throw new NullPointerException("map is null");
    }

    static public void notNull(Object obj) {
        if (obj == null)
            throw new NullPointerException("object is null");
    }

    static public void notNullOrEmpty(Collection<?> col) {
        notNull(col);
        notEmpty(col);
    }

    static public void notNullOrEmpty(Map<?, ?> map) {
        notNull(map);
        notEmpty(map);
    }

    static public void notNullOrEmpty(String str) {
        notNull(str);
        notEmpty(str);
    }

    static public void notNullResourceId(int id) {
        if (id == Const.NULL_RESOURCE_ID)
            throw new IllegalStateException("resource ID is null (0)");
    }
}
