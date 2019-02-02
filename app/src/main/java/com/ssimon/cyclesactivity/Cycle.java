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

package com.ssimon.cyclesactivity;

import java.io.Serializable;

class Cycle implements Serializable {
    static final int MAX_NUM_CYCLES = 6;
    static final int MIN_VOLUME = 180;
    static final int MAX_VOLUME = 1670;
    static final int MIN_BREWTIME = 1;
    static final int MAX_BREWTIME = 999;
    static final int MIN_VACUUMTIME = 1;
    static final int MAX_VACUUMTIME = 999;
    static final int MIN_LASTCYCLE_VACUUMTIME = 20;

    final private long id;
    final private int volumeMl;
    final private int brewSeconds;
    final private int vacuumSeconds;

    Cycle(long id, int volumeMl, int brewSeconds, int vacuumSeconds) {
        if (id != Const.UNSET_DATABASE_ID)
            Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.inRange(volumeMl, Cycle.MIN_VOLUME, Cycle.MAX_VOLUME);
        Checker.inRange(brewSeconds, Cycle.MIN_BREWTIME, Cycle.MAX_BREWTIME);
        Checker.inRange(vacuumSeconds, Cycle.MIN_VACUUMTIME, Cycle.MAX_VACUUMTIME);
        this.id = id;
        this.volumeMl = volumeMl;
        this.brewSeconds = brewSeconds;
        this.vacuumSeconds = vacuumSeconds;
    }

    long id() { return id; }
    int volumeMl() { return volumeMl; }
    int brewSeconds() { return brewSeconds; }
    int vacuumSeconds() { return vacuumSeconds; }
}
