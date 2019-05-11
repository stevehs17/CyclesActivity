package com.ssimon.cyclesactivity.util;

import com.ssimon.cyclesactivity.message.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class Utils {
    static final private EventBus eventBus =  EventBus
            .builder()
            .throwSubscriberException(true)
            .build();

    private Utils() {
        throw new UnsupportedOperationException();
    }

    static public void postEvent(MessageEvent e) {
        Checker.notNull(e);
        eventBus.post(e);
     }

    static public void registerEventBus(Object o) {
        Checker.notNull(o);
        eventBus.register(o);
    }

    static public void unregisterEventBus(Object o) {
        Checker.notNull(o);
        eventBus.unregister(o);
    }
}
