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
