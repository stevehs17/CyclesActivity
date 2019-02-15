package com.ssimon.cyclesactivity.util;

import android.content.Context;

import com.ssimon.cyclesactivity.message.MessageEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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

    static public List<Volume> getVolumesByCoffeeId(long coffeeId, List<Coffee> coffees) {
        for (Coffee c : coffees) {
            if (c.id() == coffeeId)
                return c.volumes();
        }
        throw new IllegalStateException("No coffee found with id = " + coffeeId);
    }

    static public List<Cycle> getCyclesByCoffeeAndVolumeIds(long coffeeId, long volumeId, List<Coffee> coffees) {
        List<Volume> vols = AndroidUtils.getVolumesByCoffeeId(coffeeId, coffees);
        for (Volume v : vols)
            if (v.id() == volumeId)
                return v.cycles();
        throw new IllegalStateException("No coffee found with id = " + coffeeId);
    }








}
