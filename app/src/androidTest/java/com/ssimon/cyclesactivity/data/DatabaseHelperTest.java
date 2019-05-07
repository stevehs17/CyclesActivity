package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {
    private DatabaseHelper dbHelp;

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }

    @Before
    public void resetDb() {
        final Context c = InstrumentationRegistry.getTargetContext();
        dbHelp = dbHelp.getInstance(c);
    }

    @Test
    public void openWritableDb_Succeeds() {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void openReadableDb_Succeeds() {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void deleteCoffee_Succeeds() {
        List<Coffee> cs = DatabaseTestUtils.createCoffees(1, 1);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        CoffeeDao.insertCoffees(db, cs);
        List<Coffee> csOut = CoffeeDao.getCoffees(db);
        dbHelp.deleteCoffee(csOut.get(0).id());
    }

    @Test
    public void deleteVolume_Succeeds() {
        List<Coffee> cs = DatabaseTestUtils.createCoffees(1, 1);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        CoffeeDao.insertCoffees(db, cs);
        List<Coffee> csOut = CoffeeDao.getCoffees(db);
        long id = csOut.get(0).volumes().get(0).id();
        dbHelp.deleteVolume(id);
    }

    @Test
    public void refreshCoffeeCache_Succeeds() {
        List<Coffee> cs = DatabaseTestUtils.createCoffees(1, 1);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        CoffeeDao.insertCoffees(db, cs);
        dbHelp.refreshCoffeeCache();
    }

    @Test
    public void saveVolume_Succeeds() {
        List<Coffee> cs = DatabaseTestUtils.createCoffees(1, 1);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        CoffeeDao.insertCoffees(db, cs);
        List<Coffee> csOut = CoffeeDao.getCoffees(db);
        List<Cycle> cycles = DatabaseTestUtils.createCycles(Volume.MAX_NUM_CYCLES);
        dbHelp.saveVolume(csOut.get(0).id(), cycles);
    }

    @Test
    public void saveCoffee_Succeeds() {
        List<Coffee> cs = DatabaseTestUtils.createCoffees(1, 1);
        dbHelp.saveCoffee(cs.get(0));
    }


}