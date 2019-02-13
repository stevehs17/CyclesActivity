package com.ssimon.cyclesactivity.data;

import com.ssimon.cyclesactivity.message.CoffeesRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.util.AndroidUtils;
import com.ssimon.cyclesactivity.util.Checker;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CoffeesCache {
    static private AtomicReference<List<Coffee>> coffees = new AtomicReference<>();

    static public void setCoffees(List<Coffee> cs) {
        Checker.notNullOrEmpty(cs);
        coffees.set(cs);
        AndroidUtils.postStickyEvent(new CoffeesRefreshEvent());
    }

    static public List<Coffee> getCoffees() {
        return coffees.get();
    }
}
