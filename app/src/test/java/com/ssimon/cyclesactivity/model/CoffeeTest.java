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
        cs.add(new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME));
        List<Volume> vs = new ArrayList<>();
        vs.add(new Volume(Const.MIN_DATABASE_ID, cs));
        String name = "n";
        Coffee c = new Coffee(Const.UNSET_DATABASE_ID, name, vs, Const.MIN_DATABASE_ID);
        assertEquals(Const.UNSET_DATABASE_ID, c.id());
        assertEquals(name, c.name());
        assertEquals(Const.MIN_DATABASE_ID, c.defaultVolumeId());
        Volume v = c.volumes().get(0);
        assertEquals(Const.MIN_DATABASE_ID, v.id());
        Cycle cy = v.cycles().get(0);
        assertEquals(Cycle.MIN_VOLUME, cy.volumeMl());
        assertEquals(Cycle.MIN_BREWTIME, cy.brewSeconds());
        assertEquals(Cycle.MIN_VACUUMTIME, cy.vacuumSeconds());
    }

    @Test
    public void newCoffeeNoIds_Succeeds() {
        List<Cycle> cs = new ArrayList<>();
        cs.add(new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME));
        List<Volume> vs = new ArrayList<>();
        vs.add(new Volume(Const.MIN_DATABASE_ID, cs));
        String name = "n";
        Coffee c = new Coffee(name, vs);
        assertEquals(Const.UNSET_DATABASE_ID, c.id());
        assertEquals(name, c.name());
        assertEquals(Const.UNSET_DATABASE_ID, c.defaultVolumeId());
        Volume v = c.volumes().get(0);
        assertEquals(Const.MIN_DATABASE_ID, v.id());
        Cycle cy = v.cycles().get(0);
        assertEquals(Cycle.MIN_VOLUME, cy.volumeMl());
        assertEquals(Cycle.MIN_BREWTIME, cy.brewSeconds());
        assertEquals(Cycle.MIN_VACUUMTIME, cy.vacuumSeconds());
    }



    @Test
    public void newCoffeeBadId_Fails() {
        try {
            Coffee c = new Coffee(Const.UNSET_DATABASE_ID - 1, "n", volumes(), Const.UNSET_DATABASE_ID);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void newCoffeeBadDefaultVolId_Fails() {
        try {
            Coffee c = new Coffee(Const.UNSET_DATABASE_ID, "n", volumes(), Const.UNSET_DATABASE_ID - 1);
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
            Coffee c = new Coffee(Const.UNSET_DATABASE_ID, "", volumes(), Const.UNSET_DATABASE_ID );
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void newCoffeeNullVolumes_Fails() {
        try {
            Coffee c = new Coffee(Const.UNSET_DATABASE_ID, "n", null, Const.UNSET_DATABASE_ID );
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void newCoffeeEmptyVolumes_Fails() {
        try {
            Coffee c = new Coffee(Const.UNSET_DATABASE_ID, "n", new ArrayList<Volume>(), Const.UNSET_DATABASE_ID );
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    private List<Volume> volumes() {
        List<Cycle> cs = new ArrayList<>();
        cs.add(new Cycle(Cycle.MIN_VOLUME, Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME));
        List<Volume> vs = new ArrayList<>();
        vs.add(new Volume(Const.MIN_DATABASE_ID, cs));
        return vs;
    }
}
