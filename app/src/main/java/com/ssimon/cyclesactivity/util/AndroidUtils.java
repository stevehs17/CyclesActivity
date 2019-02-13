package com.ssimon.cyclesactivity.util;

import com.ssimon.cyclesactivity.message.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class AndroidUtils {
    static public void postEvent(MessageEvent e) {
        EventBus.getDefault().postSticky(e);
    }
}
