package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.ModelTestUtils;
import com.ssimon.cyclesactivity.message.CoffeesRefreshEvent;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.util.AndroidUtils;

import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;



public class CoffeesCacheTest {
    final private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void setAndGetCoffeesCache_Success() {
        int nCofs = 100;
        int nVols = 100;
        int nCycles = Cycle.MAX_NUM_CYCLES;

        List<Coffee> cs = ModelTestUtils.createCoffees(nCofs, nVols, nCycles);
        CoffeesCache.setCoffees(cs);
        List<Coffee> csOut = CoffeesCache.getCoffees();
        ModelTestUtils.validateCoffeesWithIds(cs, csOut);
    }

    volatile private List<Coffee> coffees;

    @Test
    public void receiveCoffeeRefreshEvent_Success() {
        int nCofs = 2;
        int nVols = 2;
        int nCycles = Cycle.MAX_NUM_CYCLES;

        List<Coffee> cs = ModelTestUtils.createCoffees(nCofs, nVols, nCycles);
        assertEquals(null, coffees);
        coffees = null;
        CoffeesCache.setCoffees(cs);
        AndroidUtils.registerEventBus(this);
        while (coffees == null)
            ;
        AndroidUtils.unregisterEventBus(this);
        assertNotEquals(null,coffees);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(CoffeesRefreshEvent e) {
        AndroidUtils.removeStickyEvent(e);
        List<Coffee> cs = CoffeesCache.getCoffees();
        if (cs == null) {
            DatabaseHelper dh = DatabaseHelper.getInstance(context);
            dh.refreshCoffeesCache();
        } else {
            coffees = cs;
        }
    }
}
