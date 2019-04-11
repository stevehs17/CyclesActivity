package com.ssimon.cyclesactivity.data;

import com.ssimon.cyclesactivity.message.CoffeeRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.util.Utils;
import com.ssimon.cyclesactivity.util.Checker;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CoffeeCache {
    static private AtomicReference<List<Coffee>> coffees = new AtomicReference<>();

    static void setCoffees(List<Coffee> cs) {
        Checker.notNullOrEmpty(cs);
        coffees.set(cs);
        Utils.postEvent(new CoffeeRefreshEvent());
    }

    static public List<Coffee> getCoffees() {
        return coffees.get();
    }
}
