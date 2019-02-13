package com.ssimon.cyclesactivity.data;

import com.ssimon.cyclesactivity.ModelTestUtils;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;

import org.junit.Test;

import java.util.List;

public class CoffeesCacheTest {

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

}
