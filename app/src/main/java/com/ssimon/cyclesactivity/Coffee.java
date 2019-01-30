package com.ssimon.cyclesactivity;

import java.io.Serializable;
import java.util.List;

class Coffee implements Serializable {
    final private long id;
    final private String name;
    final private List<Volume> volumes;

    Coffee(long id, String name, List<Volume> volumes) {
        this.id = id;
        this.name = name;
        this.volumes = volumes;
    }

    long id() { return id; }
    String name() { return name; }
    List<Volume> volumes() { return volumes; }
}
