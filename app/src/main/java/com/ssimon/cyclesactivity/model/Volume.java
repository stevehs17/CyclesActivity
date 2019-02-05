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
import java.util.List;

public class Volume implements Serializable {
    final private long id;
    final private List<Cycle> cycles;

    public Volume(long id, List<Cycle> cycles) {
        if (id != Const.UNSET_DATABASE_ID)
            Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(cycles);

        this.id = id;
        this.cycles = cycles;
    }

    long id() { return id; }
    public List<Cycle> cycles() { return cycles; }
}
