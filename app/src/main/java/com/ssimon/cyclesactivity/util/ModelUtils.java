package com.ssimon.cyclesactivity.util;

import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelUtils {
    static public List<Coffee> createDefaultCoffeeTemplates() {
        List<Coffee> c = new ArrayList<>();
/*
        c.add(createDarkBlendTemplate());
        c.add(createEastAfricanTemplate());
        c.add(createLatinAmericanTemplate());
        c.add(createLightBlendTemplate());
*/
        c.add(createNaturalTemplate());

        c.add(createTestCoffee());
        return c;
    }

    static Coffee createTestCoffee() {
        final String name = "Test";

        List<Cycle> c = new ArrayList<>();
        // V0
        c.add(new Cycle(1000, 141, 80));
        c.add(new Cycle(200, 37, 55));

        List<Volume> v = new ArrayList<>();
        v.add(new Volume(Arrays.asList(c.get(0), c.get(1))));

        return new Coffee(name, v);
    }

    static Coffee createDarkBlendTemplate() {
        final String name = "Dark Blend (template)";

        List<Cycle> c = new ArrayList<>();
        // V0
        c.add(new Cycle(1000, 141, 80));
        c.add(new Cycle(200, 37, 55));
        // V1
        c.add(new Cycle(750, 141, 80));
        c.add(new Cycle(750, 28, 100));
        c.add(new Cycle(500, 11, 85));
        // V2
        c.add(new Cycle(1125, 140, 85));
        c.add(new Cycle(1125, 26, 105));
        c.add(new Cycle(750, 10, 110));
        // V3
        c.add(new Cycle(1400, 140, 90));
        c.add(new Cycle(1400, 25, 105));
        c.add(new Cycle(1000, 10, 110));

        List<Volume> v = new ArrayList<>();
        v.add(new Volume(Arrays.asList(c.get(0), c.get(1))));
        v.add(new Volume(Arrays.asList(c.get(2), c.get(3), c.get(4))));
        v.add(new Volume(Arrays.asList(c.get(5), c.get(6), c.get(7))));
        v.add(new Volume(Arrays.asList(c.get(8), c.get(9), c.get(10))));

        return new Coffee(name, v);
    }


    static Coffee createEastAfricanTemplate() {
        final String name = "East African (template)";

        List<Cycle> c = new ArrayList<>();
        // V0
        c.add(new Cycle(1000, 139, 80));   // 0
        c.add(new Cycle(200, 32, 55));     // 1
        // V1
        c.add(new Cycle(750, 139, 80));    // 2
        c.add(new Cycle(750, 27, 100));    // 3
        c.add(new Cycle(500, 6, 85));      // 4
        // V2
        c.add(new Cycle(1125, 138, 85));   // 5
        c.add(new Cycle(1125, 25, 105));   // 6
        c.add(new Cycle(750, 6, 110));     // 7
        // V3
        c.add(new Cycle(1400, 137, 90));   // 8
        c.add(new Cycle(1400, 23, 105));   // 9
        c.add(new Cycle(1000, 5, 110));    // 10

        List<Volume> v = new ArrayList<>();
        v.add(new Volume(Arrays.asList(c.get(0), c.get(1))));
        v.add(new Volume(Arrays.asList(c.get(2), c.get(3), c.get(4))));
        v.add(new Volume(Arrays.asList(c.get(5), c.get(6), c.get(7))));
        v.add(new Volume(Arrays.asList(c.get(8), c.get(9), c.get(10))));

        return new Coffee(name, v);
    }

    static Coffee createLatinAmericanTemplate() {
        final String name = "Latin American(template)";

        List<Cycle> c = new ArrayList<>();
        // V0
        c.add(new Cycle(1000, 139, 34));        // 0
        c.add(new Cycle(200, 34, 55));          // 1
        // V1
        c.add(new Cycle(750, 139, 80));         // 2
        c.add(new Cycle(750, 27, 100));         // 3
        c.add(new Cycle(500, 100, 85));         // 4
        // V2
        c.add(new Cycle(1125, 138, 85));        // 5
        c.add(new Cycle(1125, 25, 105));        // 6
        c.add(new Cycle(750, 7, 110));          // 7
        // V3
        c.add(new Cycle(1400, 138, 90));        // 8
        c.add(new Cycle(1400, 23, 105));        // 9
        c.add(new Cycle(1000, 6, 110));         // 10

        List<Volume> v = new ArrayList<>();
        v.add(new Volume(Arrays.asList(c.get(0), c.get(1))));
        v.add(new Volume(Arrays.asList(c.get(2), c.get(3), c.get(4))));
        v.add(new Volume(Arrays.asList(c.get(5), c.get(6), c.get(7))));
        v.add(new Volume(Arrays.asList(c.get(8), c.get(9), c.get(10))));

        return new Coffee(name, v);
    }

    static Coffee createLightBlendTemplate() {
        final String name = "Light Blend (template)";

        List<Cycle> c = new ArrayList<>();
        // V0
        c.add(new Cycle(1000, 140, 80));    // 0
        c.add(new Cycle(200, 35, 55));      // 1
        // V1
        c.add(new Cycle(750, 140, 80));     // 2
        c.add(new Cycle(750, 28, 100));     // 3
        c.add(new Cycle(500, 9, 85));       // 4
        // V2
        c.add(new Cycle(1125, 139, 85));    // 5
        c.add(new Cycle(1125, 25, 105));    // 6
        c.add(new Cycle(750, 8, 110));      // 7
        // V3
        c.add(new Cycle(1400, 139, 90));    // 8
        c.add(new Cycle(1400, 24, 105));    // 9
        c.add(new Cycle(1000, 8, 110));     // 10

        List<Volume> v = new ArrayList<>();
        v.add(new Volume(Arrays.asList(c.get(0), c.get(1))));
        v.add(new Volume(Arrays.asList(c.get(2), c.get(3), c.get(4))));
        v.add(new Volume(Arrays.asList(c.get(5), c.get(6), c.get(7))));
        v.add(new Volume(Arrays.asList(c.get(8), c.get(9), c.get(10))));

        return new Coffee(name, v);
    }

    static Coffee createNaturalTemplate() {
        final String name = "Natural (template)";

        List<Cycle> c = new ArrayList<>();
        // Volume 0
        c.add(new Cycle(1000, 142, 80));    // 0
        c.add(new Cycle(200, 27, 55));      // 1
        // Volume 1
        c.add(new Cycle(1000, 142, 80));    // 2
        c.add(new Cycle(1000, 30, 55));     // 3
        // Volume 2
        c.add(new Cycle(1500, 142, 100));   // 4
        c.add(new Cycle(1500, 27, 120));    // 5
        // Volume 3
        c.add(new Cycle(1400, 142, 90));    // 6
        c.add(new Cycle(1400, 26, 105));    // 7
        c.add(new Cycle(1000, 1, 110));     // 8

        List<Volume> v = new ArrayList<>();
        v.add(new Volume(Arrays.asList(c.get(0), c.get(1))));
        v.add(new Volume(Arrays.asList(c.get(2), c.get(3))));
        v.add(new Volume(Arrays.asList(c.get(4), c.get(5))));
        v.add(new Volume(Arrays.asList(c.get(6), c.get(7), c.get(8))));

        return new Coffee(name, v);
    }

}
