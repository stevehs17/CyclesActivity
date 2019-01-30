package com.ssimon.cyclesactivity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CheckerTest {
    static final private double DOUBLEVAL = 5.1;
    static final private double DOUBLEMIN = 2.1;
    static final private float FLOATVAL = 5.1F;
    static final private float FLOATMIN = 2.1F;
    static final private float FLOATMAX = 10.5F;
    static final private int INTVAL = 5;
    static final private int INTMIN = 2;
    static final private int INTMAX = 10;
    static final private long LONGVAL = 5;
    static final private long LONGMIN = 2;
    static final private List<String> EMPTY_COLLECTION = new ArrayList<>();
    static final private List<String> NONEMPTY_COLLECTION = Arrays.asList("test");
    static final private Map<String, String> EMPTY_MAP = new HashMap<>();
    static final private Map<String, String> NONEMPTY_MAP = new HashMap<String, String>(){{put("testone", "one");}};
    static final private String EMPTY_STRING = "";
    static final private String NONEMPTY_STRING = "1";

    @Test
    public void atLeast_double_isCorrect() throws Exception {
        Checker.atLeast(DOUBLEVAL, DOUBLEMIN);
    }

    @Test
    public void atLeast_double_isIncorrect() throws Exception {
        try {
            Checker.atLeast(DOUBLEMIN, DOUBLEVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void atLeast_float_isCorrect() throws Exception {
        Checker.atLeast(FLOATVAL, FLOATMIN);
    }

    @Test
    public void atLeast_float_isIncorrect() throws Exception {
        try {
            Checker.atLeast(FLOATMIN, FLOATVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void atLeast_int_isCorrect() throws Exception {
        Checker.atLeast(INTVAL, INTMIN);
    }

    @Test
    public void atLeast_int_isIncorrect() throws Exception {
         try {
            Checker.atLeast(INTMIN, INTVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void atLeast_long_isCorrect() throws Exception {
        Checker.atLeast(LONGVAL, LONGMIN);
    }

    @Test
    public void atLeast_long_isIncorrect() throws Exception {
        try {
            Checker.atLeast(LONGMIN, LONGVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void greaterThan_double_isCorrect() throws Exception {
        Checker.greaterThan(DOUBLEVAL, DOUBLEMIN);
    }

    @Test
    public void greaterThan_double_isIncorrect() throws Exception {
        try {
            Checker.greaterThan(DOUBLEVAL, DOUBLEVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void greaterThan_float_isCorrect() throws Exception {
        Checker.greaterThan(FLOATVAL, FLOATMIN);
    }

    @Test
    public void greaterThan_float_isIncorrect() throws Exception {
        try {
            Checker.greaterThan(FLOATVAL, FLOATVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void greaterThan_int_isCorrect() throws Exception {
        Checker.greaterThan(INTVAL, INTMIN);
    }

    @Test
    public void greaterThan_int_isIncorrect() throws Exception {
         try {
            Checker.greaterThan(INTVAL, INTVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void greaterThan_long_isCorrect() throws Exception {
        Checker.greaterThan(LONGVAL, LONGMIN);
    }

    @Test
    public void greaterThan_long_isIncorrect() throws Exception {
        try {
            Checker.greaterThan(LONGVAL, LONGVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void inRange_float_isCorrect() throws Exception {
        Checker.inRange(FLOATVAL, FLOATMIN, FLOATMAX);
    }

    @Test
    public void inRange_float_isIncorrect() throws Exception {
        try {
            Checker.inRange(FLOATMIN, FLOATVAL, FLOATMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void inRange_int_isCorrect() throws Exception {
        Checker.inRange(INTVAL, INTMIN, INTMAX);
    }

    @Test
    public void inRange_int_isIncorrect() throws Exception {
        try {
            Checker.inRange(INTMIN, INTVAL, INTMAX);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void isExactly_int_isCorrect() throws Exception {
        Checker.isExactly(INTVAL, INTVAL);
    }

    @Test
    public void isExactly_int_isIncorrect() throws Exception {
        try {
            Checker.isExactly(INTMIN, INTVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void lessThan_int_isCorrect() throws Exception {
        Checker.lessThan(INTMIN, INTVAL);
    }

    @Test
    public void lessThan_int_isIncorrect() throws Exception {
        try {
            Checker.lessThan(INTVAL, INTVAL);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notEmpty_Collection_isCorrect() throws Exception {
        Checker.notEmpty(NONEMPTY_COLLECTION);
    }

    @Test
    public void notEmpty_Collection_isIncorrect() throws Exception {
        try {
            Checker.notEmpty(EMPTY_COLLECTION);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notEmpty_Map_isCorrect() throws Exception {
        Checker.notEmpty(NONEMPTY_MAP);
    }

    @Test
    public void notEmpty_Map_isIncorrect() throws Exception {
        try {
            Checker.notEmpty(EMPTY_MAP);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notEmpty_String_isCorrect() throws Exception {
        Checker.notEmpty(NONEMPTY_STRING);
    }

    @Test
    public void notEmpty_String_isIncorrect() throws Exception {
        try {
            Checker.notEmpty(EMPTY_STRING);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }

    @Test
    public void notEquals_int_isCorrect() throws Exception {
        Checker.notEquals(INTMIN, INTMAX);
    }

    @Test
    public void notEquals_String_isIncorrect() throws Exception {
        try {
            Checker.notEquals(INTMIN, INTMIN);
        } catch (IllegalStateException e) {
            return;
        }
        throw new IllegalStateException("failure");
    }
}