package com.ssimon.cyclesactivity.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssimon.cyclesactivity.Const;

public class CheckerTest {
    static final private double DOUBLEMAX = 5.1;
    static final private double DOUBLEMIN = 2.1;
    static final private float FLOATMIN = 2.1F;
    static final private float FLOATMAX = 10.5F;
    static final private float FLOATAVG = (FLOATMAX - FLOATMIN)/2;
    static final private int INTMIN = 2;
    static final private int INTMAX = 10;
    static final private int INTAVG = (INTMAX - INTMIN)/2;
    static final private long LONGMAX = 5L;
    static final private long LONGMIN = 2L;
    static final private List<String> EMPTY_COLLECTION = new ArrayList<>();
    static final private List<String> NONEMPTY_COLLECTION = Arrays.asList("test");
    static final private List<String> NULL_COLLECTION = null;
    static final private Map<String, String> EMPTY_MAP = new HashMap<>();
    static final private Map<String, String> NONEMPTY_MAP = new HashMap<String, String>(){{put("testone", "one");}};
    static final private Map<String, String> NULL_MAP = null;
    static final private String EMPTY_STRING = "";
    static final private String NONEMPTY_STRING = "1";
    static final private String NULL_STRING = null;
    static final private Object NONNULL_OBJECT = "";
    static final private Object NULL_OBJECT = null;

    @Test
    public void atLeast_double_Succeeds() throws Exception {
        Checker.atLeast(DOUBLEMAX, DOUBLEMIN);
    }

    @Test
    public void atLeast_double_Fails() throws Exception {
        try {
            Checker.atLeast(DOUBLEMIN, DOUBLEMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void atLeast_float_Succeeds() throws Exception {
        Checker.atLeast(FLOATAVG, FLOATMIN);
    }

    @Test
    public void atLeast_float_Fails() throws Exception {
        try {
            Checker.atLeast(FLOATMIN, FLOATMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void atLeast_int_Succeeds() throws Exception {
        Checker.atLeast(INTMAX, INTMIN);
    }

    @Test
    public void atLeast_int_Fails() throws Exception {
         try {
            Checker.atLeast(INTMIN, INTMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void atLeast_long_Succeeds() throws Exception {
        Checker.atLeast(LONGMAX, LONGMIN);
    }

    @Test
    public void atLeast_long_Fails() throws Exception {
        try {
            Checker.atLeast(LONGMIN, LONGMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void greaterThan_double_Succeeds() throws Exception {
        Checker.greaterThan(DOUBLEMAX, DOUBLEMIN);
    }

    @Test
    public void greaterThan_double_Fails() throws Exception {
        try {
            Checker.greaterThan(DOUBLEMAX, DOUBLEMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void greaterThan_float_Succeeds() throws Exception {
        Checker.greaterThan(FLOATMAX, FLOATMIN);
    }

    @Test
    public void greaterThan_float_Fails() throws Exception {
        try {
            Checker.greaterThan(FLOATMIN, FLOATMIN);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void greaterThan_int_Succeeds() throws Exception {
        Checker.greaterThan(INTMAX, INTMIN);
    }

    @Test
    public void greaterThan_int_Fails() throws Exception {
        try {
            Checker.greaterThan(INTMIN, INTMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void greaterThan_long_Succeeds() throws Exception {
        Checker.greaterThan(LONGMAX, LONGMIN);
    }

    @Test
    public void greaterThan_long_Fails() throws Exception {
        try {
            Checker.greaterThan(LONGMAX, LONGMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void inRange_float_Succeeds() throws Exception {
        Checker.inRange(FLOATAVG, FLOATMIN, FLOATMAX);
    }

    @Test
    public void inRange_float_Fails() throws Exception {
        try {
            Checker.inRange(FLOATMIN, FLOATAVG, FLOATMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void inRange_int_Succeeds() throws Exception {
        Checker.inRange(INTAVG, INTMIN, INTMAX);
    }

    @Test
    public void inRange_int_Fails() throws Exception {
        try {
            Checker.inRange(INTMIN, INTAVG, INTMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void isExactly_int_Succeeds() throws Exception {
        Checker.isExactly(INTAVG, INTAVG);
    }

    @Test
    public void isExactly_int_Fails() throws Exception {
        try {
            Checker.isExactly(INTMIN, INTAVG);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void lessThan_int_Succeeds() throws Exception {
        Checker.lessThan(INTMIN, INTAVG);
    }

    @Test
    public void lessThan_int_Fails() throws Exception {
        try {
            Checker.lessThan(INTAVG, INTAVG);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notEmpty_Collection_Succeeds() throws Exception {
        Checker.notEmpty(NONEMPTY_COLLECTION);
    }

    @Test
    public void notEmpty_Collection_Fails() throws Exception {
        try {
            Checker.notEmpty(EMPTY_COLLECTION);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notEmpty_Map_Succeeds() throws Exception {
        Checker.notEmpty(NONEMPTY_MAP);
    }

    @Test
    public void notEmpty_Map_Fails() throws Exception {
        try {
            Checker.notEmpty(EMPTY_MAP);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notEmpty_String_Fails() throws Exception {
        try {
            Checker.notEmpty(EMPTY_STRING);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notEmpty_String_Succeeds() throws Exception {
        Checker.notEmpty(NONEMPTY_STRING);
    }

    @Test
    public void notEquals_int_Succeeds() throws Exception {
        Checker.notEquals(INTMIN, INTMAX);
    }

    @Test
    public void notEquals_long_Succeeds() throws Exception {
        Checker.notEquals(LONGMIN, LONGMAX);
    }

    @Test
    public void notEquals_String_Fails() throws Exception {
        try {
            Checker.notEquals(INTMIN, INTMIN);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notGreaterThan_float_Succeeds() throws Exception {
        Checker.notGreaterThan(FLOATMIN, FLOATMIN);
    }

    @Test
    public void notGreaterThan_float_Fails() throws Exception {
        try {
            Checker.notGreaterThan(FLOATMAX, FLOATMIN);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notGreaterThan_int_Succeeds() throws Exception {
        Checker.notGreaterThan(INTMIN, INTMIN);
    }

    @Test
    public void notGreaterThan_int_Fails() throws Exception {
        try {
            Checker.notGreaterThan(INTMAX, INTMIN);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNull_Collection_Succeeds() throws Exception {
        Checker.notNull(EMPTY_COLLECTION);
    }

    @Test
    public void notNull_Collection_Fails() throws Exception {
        try {
            Checker.notNull(NULL_COLLECTION);
        } catch (NullPointerException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNull_Map_Succeeds() throws Exception {
        Checker.notNull(EMPTY_MAP);
    }

    @Test
    public void notNull_Map_Fails() throws Exception {
        try {
            Checker.notNull(NULL_MAP);
        } catch (NullPointerException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNull_Object_Succeeds() throws Exception {
        Checker.notNull(NONNULL_OBJECT);
    }

    @Test
    public void notNull_Object_Fails() throws Exception {
        try {
            Checker.notNull(NULL_OBJECT);
        } catch (NullPointerException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNull_String_Succeeds() throws Exception {
        Checker.notNull(EMPTY_STRING);
    }

    @Test
    public void notNull_String_Fails() throws Exception {
        try {
            Checker.notNull(NULL_STRING);
        } catch (NullPointerException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNullOrEmpty_Collection_Succeeds() throws Exception {
        Checker.notNullOrEmpty(NONEMPTY_COLLECTION);
    }

    @Test
    public void notNullOrEmpty_Collection_null_Fails() throws Exception {
        try {
            Checker.notNullOrEmpty(NULL_COLLECTION);
        } catch (NullPointerException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNullOrEmptyCollection_empty_Fails() throws Exception {
        try {
            Checker.notNullOrEmpty(EMPTY_COLLECTION);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNullOrEmpty_Map_Succeeds() throws Exception {
        Checker.notNullOrEmpty(NONEMPTY_MAP);
    }

    @Test
    public void notNullOrEmpty_Map_null_Fails() throws Exception {
        try {
            Checker.notNullOrEmpty(NULL_MAP);
        } catch (NullPointerException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNullOrEmpty_Map_empty_Fails() throws Exception {
        try {
            Checker.notNullOrEmpty(EMPTY_MAP);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNullOrEmpty_String_Succeeds() throws Exception {
        Checker.notNullOrEmpty(NONEMPTY_STRING);
    }

    @Test
    public void notNullOrEmpty_String_null_Fails() throws Exception {
        try {
            Checker.notNullOrEmpty(NULL_STRING);
        } catch (NullPointerException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNullOrEmpty_String_empty_Fails() throws Exception {
        try {
            Checker.notNullOrEmpty(EMPTY_STRING);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notNullResourceId_Succeeds() throws Exception {
        Checker.notNullResourceId(Const.NULL_RESOURCE_ID + 1);
    }

    @Test
    public void notNullResourceId_Fails() throws Exception {
        try {
            Checker.notNullResourceId(Const.NULL_RESOURCE_ID);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }
}