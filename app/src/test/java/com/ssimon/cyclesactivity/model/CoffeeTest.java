package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.Const;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CoffeeTest {
    @Test
    public void newCoffee_Succeeds() {
        List<Cycle> cs = new ArrayList<>();
        cs.add(new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_TIME, Cycle.MIN_TIME));
        List<Volume> vs = new ArrayList<>();
        vs.add(new Volume(Const.MIN_DATABASE_ID, cs));
        String name = "n";
        Coffee c = new Coffee(Const.MIN_DATABASE_ID, name, vs);
        assertEquals(Const.MIN_DATABASE_ID, c.id());
        assertEquals(name, c.name());
        Volume v = c.volumes().get(0);
        assertEquals(Const.MIN_DATABASE_ID, v.id());
        Cycle cy = v.cycles().get(0);
        assertEquals(Cycle.MIN_VOLUME, cy.volumeMl());
        assertEquals(Cycle.MIN_TIME, cy.brewSeconds());
        assertEquals(Cycle.MIN_TIME, cy.vacuumSeconds());
    }

    @Test
    public void newCoffeeNoIds_Succeeds() {
        List<Cycle> cs = new ArrayList<>();
        cs.add(new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_TIME, Cycle.MIN_TIME));
        List<Volume> vs = new ArrayList<>();
        vs.add(new Volume(Const.MIN_DATABASE_ID, cs));
        String name = "n";
        Coffee c = new Coffee(name, vs);
        assertEquals(Const.NULL_DATABASE_ID, c.id());
        assertEquals(name, c.name());
        Volume v = c.volumes().get(0);
        assertEquals(Const.MIN_DATABASE_ID, v.id());
        Cycle cy = v.cycles().get(0);
        assertEquals(Cycle.MIN_VOLUME, cy.volumeMl());
        assertEquals(Cycle.MIN_TIME, cy.brewSeconds());
        assertEquals(Cycle.MIN_TIME, cy.vacuumSeconds());
    }

    @Test
    public void newCoffeeBadId_Fails() {
        try {
            Coffee c = new Coffee(Const.NULL_DATABASE_ID - 1, "n", volumes());
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void newCoffeeNullName_Fails() {
        try {
            Coffee c = new Coffee(null, volumes());
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void newCoffeeEmptyName_Fails() {
        try {
            Coffee c = new Coffee("", volumes());
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void newCoffeeNullVolumes_Fails() {
        try {
            Coffee c = new Coffee("n", null);
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void newCoffeeEmptyVolumes_Fails() {
        try {
            Coffee c = new Coffee("n", new ArrayList<Volume>());
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    private List<Volume> volumes() {
        List<Cycle> cs = new ArrayList<>();
        cs.add(new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_TIME, Cycle.MIN_TIME));
        List<Volume> vs = new ArrayList<>();
        vs.add(new Volume(Const.MIN_DATABASE_ID, cs));
        return vs;
    }
}