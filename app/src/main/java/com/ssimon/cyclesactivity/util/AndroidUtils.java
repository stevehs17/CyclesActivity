package com.ssimon.cyclesactivity.util;

import android.content.Context;

import com.ssimon.cyclesactivity.message.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class AndroidUtils {
    static public void postEvent(MessageEvent e) {
        Checker.notNull(e);
        EventBus.getDefault().post(e);
    }

    static public void postStickyEvent(MessageEvent e) {
        Checker.notNull(e);
        EventBus.getDefault().postSticky(e);
    }

    static public void removeStickyEvent(MessageEvent e) {
        Checker.notNull(e);
        EventBus.getDefault().removeStickyEvent(e);
    }

    static public void registerEventBus(Object o) {
        Checker.notNull(o);
        EventBus.getDefault().register(o);
    }

    static public void unregisterEventBus(Object o) {
        Checker.notNull(o);
        EventBus.getDefault().unregister(o);
    }







}
