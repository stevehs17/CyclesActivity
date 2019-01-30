package com.ssimon.cyclesactivity;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CheckerTest {
    static final private double DOUBLEVAL = 5.1;
    static final private double DOUBLEMIN = 2.1;
    static final private float FLOATVAL = 5.1F;
    static final private float FLOATMIN = 2.1F;
    static final private int INTVAL = 5;
    static final private int INTMIN = 2;
    static final private long LONGVAL = 5;
    static final private long LONGMIN = 2;

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

    /*

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
     */
}