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
import java.util.List;

class Coffee implements Serializable {
    final private long id;
    final private String name;
    final private List<Volume> volumes;

    Coffee(long id, String name, List<Volume> volumes) {
        if (id != Const.UNSET_DATABASE_ID)
            Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(name);
        Checker.notNullOrEmpty(volumes);
        this.id = id;
        this.name = name;
        this.volumes = volumes;
    }

    long id() { return id; }
    String name() { return name; }
    List<Volume> volumes() { return volumes; }
}
