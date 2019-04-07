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

import com.ssimon.cyclesactivity.data.VolumeDao;
import com.ssimon.cyclesactivity.util.Checker;
import com.ssimon.cyclesactivity.Const;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Coffee implements Serializable {
    final private long id;
    final private String name;
    final private List<Volume> volumes;
    final private long defaultVolumeId;

    // todo: figure out how to consolidate this and other constructor, so didn't repeat sorting code
    // (may need to go back to allowing UNSET_DB_ID as value for coffee id)s
    public Coffee(long id, String name, List<Volume> volumes, long defaultVolumeId) {
        Checker.atLeast(id, Const.MIN_DATABASE_ID);
        Checker.notNullOrEmpty(name);
        Checker.notNullOrEmpty(volumes);
        if (defaultVolumeId != Const.UNSET_DATABASE_ID)
            Checker.atLeast(defaultVolumeId, Const.MIN_DATABASE_ID);

        this.id = id;
        this.name = name;
        Collections.sort(volumes, new TotalVolumeSorter());
        this.volumes = Collections.unmodifiableList(volumes);
        this.defaultVolumeId = defaultVolumeId;
    }


    public Coffee(String name, List<Volume> volumes) {
        Checker.notNullOrEmpty(name);
        Checker.notNullOrEmpty(volumes);

        this.id = Const.UNSET_DATABASE_ID;
        this.name = name;
        Collections.sort(volumes, new TotalVolumeSorter());
        this.volumes = Collections.unmodifiableList(volumes);
        this.defaultVolumeId = Const.UNSET_DATABASE_ID;
    }

    static private class TotalVolumeSorter implements Comparator<Volume> {
        @Override
        public int compare(Volume v1, Volume v2) {
            return v1.totalVolume() - v2.totalVolume();
        }
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
