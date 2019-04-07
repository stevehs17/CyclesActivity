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

public class Coffee implements Serializable {
    final private long id;
    final private String name;
    final private List<Volume> volumes;
    final private long defaultVolumeId;

    public Coffee(long id, String name, List<Volume> volumes, long defaultVolumeId) {
        Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(name);
        Checker.notNullOrEmpty(volumes);
        if (defaultVolumeId != Const.UNSET_DATABASE_ID)
            Checker.atLeast(defaultVolumeId, Const.MIN_DATABASE_ID);

        this.id = id;
        this.name = name;
        this.volumes = Collections.unmodifiableList(volumes);
        this.defaultVolumeId = defaultVolumeId;
    }


    public Coffee(String name, List<Volume> volumes) {
        Checker.notNullOrEmpty(name);
        Checker.notNullOrEmpty(volumes);

        this.id = Const.UNSET_DATABASE_ID;
        this.name = name;
        this.volumes = Collections.unmodifiableList(volumes);
        this.defaultVolumeId = Const.UNSET_DATABASE_ID;
    }

    public long id() { return id; }
    public String name() { return name; }
    public List<Volume> volumes() { return volumes; }
    public long defaultVolumeId() { return defaultVolumeId; }

    public String toString() {
        String fmt = "CoffeeId = %d, name = %s, defaultVolId = %d\n";
        String s = String.format(fmt, id(), name(), defaultVolumeId());
        for (Volume v : volumes())
            s += v.toString();
        return s;
    }
}
