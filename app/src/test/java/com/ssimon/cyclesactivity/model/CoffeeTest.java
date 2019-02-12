package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.ModelTestUtils;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class CoffeeTest {

    @Test
    public void new_Coffee_Succeeds() {
        Coffee cof = new Coffee(ModelTestUtils.DB_ID, ModelTestUtils.NAME, ModelTestUtils.createVolumeList(),
                ModelTestUtils.DB_ID);
        Cycle cyc = cof.volumes().get(0).cycles().get(0);
        assertEquals(ModelTestUtils.DB_ID, cof.id());
        assertEquals(ModelTestUtils.NAME, cof.name());
        assertEquals(ModelTestUtils.VOLUME, cyc.volumeMl());
        assertEquals(ModelTestUtils.BREWTIME, cyc.brewSeconds());
        assertEquals(ModelTestUtils.VACUUMTIME, cyc.vacuumSeconds());
    }

    @Test
    public void new_Coffee_null_name_Fails() {
        try {
            Coffee c = new Coffee(ModelTestUtils.DB_ID, null, ModelTestUtils.createVolumeList(),
                    ModelTestUtils.DB_ID);
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Coffee_empty_name_Fails() {
        try {
            Coffee c = new Coffee(ModelTestUtils.DB_ID, "", ModelTestUtils.createVolumeList(),
                    ModelTestUtils.DB_ID);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Coffee_null_volumes_Fails() {
        try {
            Coffee c = new Coffee(ModelTestUtils.DB_ID, ModelTestUtils.NAME, null,
                    ModelTestUtils.DB_ID);
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Coffee_empty_volumes_Fails() {
        try {
            Coffee c = new Coffee(ModelTestUtils.DB_ID, ModelTestUtils.NAME, new ArrayList<Volume>(),
                    ModelTestUtils.DB_ID);
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }
}
