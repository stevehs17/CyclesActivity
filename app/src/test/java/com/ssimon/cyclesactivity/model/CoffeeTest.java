package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.ModelUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class CoffeeTest {

    @Test
    public void new_Coffee_Succeeds() {
        Coffee cof = new Coffee(ModelUtils.DB_ID, ModelUtils.NAME, ModelUtils.createVolumeList(),
                ModelUtils.DB_ID);
        Cycle cyc = cof.volumes().get(0).cycles().get(0);
        assertEquals(ModelUtils.DB_ID, cof.id());
        assertEquals(ModelUtils.NAME, cof.name());
        assertEquals(ModelUtils.VOLUME, cyc.volumeMl());
        assertEquals(ModelUtils.BREWTIME, cyc.brewSeconds());
        assertEquals(ModelUtils.VACUUMTIME, cyc.vacuumSeconds());
    }

    @Test
    public void new_Coffee_null_name_Fails() {
        try {
            Coffee c = new Coffee(ModelUtils.DB_ID, null, ModelUtils.createVolumeList(),
                    ModelUtils.DB_ID);
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Coffee_empty_name_Fails() {
        try {
            Coffee c = new Coffee(ModelUtils.DB_ID, "", ModelUtils.createVolumeList(),
                    ModelUtils.DB_ID);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Coffee_null_volumes_Fails() {
        try {
            Coffee c = new Coffee(ModelUtils.DB_ID, ModelUtils.NAME, null,
                    ModelUtils.DB_ID);
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Coffee_empty_volumes_Fails() {
        try {
            Coffee c = new Coffee(ModelUtils.DB_ID, ModelUtils.NAME, new ArrayList<Volume>(),
                    ModelUtils.DB_ID);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void createManyCoffees_Success() {
        int numCoffees = 1;
        int numVolumes = 1;
        int numCycles = 1;

        List<Coffee> cs = ModelUtils.createCoffees(numCoffees, numVolumes, numCycles);
        ModelUtils.validateCoffees(cs);
    }
}
