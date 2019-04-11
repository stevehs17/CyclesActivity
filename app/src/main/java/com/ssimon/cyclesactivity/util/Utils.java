package com.ssimon.cyclesactivity.util;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.message.MessageEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class Utils {
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

    static public Coffee getCoffeeById(long coffeeId, List<Coffee> coffees) {
        Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(coffees);

        for (Coffee c : coffees) {
            if (c.id() == coffeeId)
                return c;
        }
        throw new IllegalArgumentException("No coffee found with id = " + coffeeId);
    }


    static public List<Volume> getVolumesByCoffeeId(long coffeeId, List<Coffee> coffees) {
        Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(coffees);

        for (Coffee c : coffees) {
            if (c.id() == coffeeId)
                return c.volumes();
        }
        throw new IllegalArgumentException("No coffee found with id = " + coffeeId);
    }

    static public List<Cycle> getCyclesByCoffeeAndVolumeIds(long coffeeId, long volumeId, List<Coffee> coffees) {
        Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(coffees);

        List<Volume> vols = Utils.getVolumesByCoffeeId(coffeeId, coffees);
        for (Volume v : vols)
            if (v.id() == volumeId)
                return v.cycles();
        throw new IllegalArgumentException("No coffee with id = " + coffeeId + " and volume = " + volumeId + " found.");
    }








}
