package com.ssimon.cyclesactivity.model;

import com.ssimon.cyclesactivity.Const;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class CoffeeTest {

    @Test
    public void new_Coffee_Succeeds() throws Exception {
        Coffee cof = new Coffee(TestUtils.DB_ID, TestUtils.NAME, TestUtils.createVolumeList());
        Cycle cyc = cof.volumes().get(0).cycles().get(0);
        assertEquals(TestUtils.DB_ID, cof.id());
        assertEquals(TestUtils.NAME, cof.name());
        assertEquals(TestUtils.VOLUME, cyc.volumeMl());
        assertEquals(TestUtils.BREWTIME, cyc.brewSeconds());
        assertEquals(TestUtils.VACUUMTIME, cyc.vacuumSeconds());
    }

    @Test
    public void new_Coffee_null_name_Fails() throws Exception {
        try {
            Coffee c = new Coffee(TestUtils.DB_ID, null, TestUtils.createVolumeList());
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Coffee_empty_name_Fails() throws Exception {
        try {
            Coffee c = new Coffee(TestUtils.DB_ID, "", TestUtils.createVolumeList());
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Coffee_null_volumes_Fails() throws Exception {
        try {
            Coffee c = new Coffee(TestUtils.DB_ID, TestUtils.NAME, null);
        } catch (NullPointerException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void new_Coffee_empty_volumes_Fails() throws Exception {
        try {
            Coffee c = new Coffee(TestUtils.DB_ID, TestUtils.NAME, new ArrayList<Volume>());
        } catch (IllegalStateException unused) {
            return;
        }
        throw new RuntimeException("test failed");
    }
}
