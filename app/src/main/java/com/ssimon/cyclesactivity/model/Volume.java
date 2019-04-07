/*
 Copyright (c) 2019 Steven H. Simon
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain	a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.
*/

package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.util.Checker;
import com.ssimon.cyclesactivity.Const;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Volume implements Serializable {
    final private long id;
    final private List<Cycle> cycles;

    public Volume(List<Cycle> cycles) {
        Checker.notNull(cycles);
        Checker.inRange(cycles.size(), Cycle.MIN_NUM_CYCLES, Cycle.MAX_NUM_CYCLES);

        this.id = Const.UNSET_DATABASE_ID;
        this.cycles = Collections.unmodifiableList(cycles);
    }

    public Volume(long id, List<Cycle> cycles) {
        Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.notNull(cycles);
        Checker.inRange(cycles.size(), Cycle.MIN_NUM_CYCLES, Cycle.MAX_NUM_CYCLES);

        this.id = id;
        this.cycles = Collections.unmodifiableList(cycles);
    }

    public long id() { return id; }
    public List<Cycle> cycles() { return cycles; }

    public int totalVolume() {
        int n = 0;
        for (Cycle c : cycles())
            n += c.volumeMl();
        return n;
    }

    public String name() {
        return String.format("%d ml", totalVolume());
    }

    public String toString() {
        String s = String.format("\tVolumeId = %d\n", id());
        for (Cycle c : cycles())
            s += c.toString();
        return s;
    }
}