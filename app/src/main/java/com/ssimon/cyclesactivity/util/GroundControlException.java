package com.ssimon.cyclesactivity.util;

public class GroundControlException extends Exception {
    public GroundControlException() {
        super();
    }

    public GroundControlException(Exception e) {
        super(e);
    }

    public GroundControlException(String message) {
        super(message);
    }
}
