package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CoffeeDaoTest {
    private SQLiteDatabase db;

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }
    @Before
    public void setupTables() {
        DatabaseTestUtils.setupTables();
        Context ctx = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        db = dh.getWritableDatabase();
    }

    @Test
    public void createAndReadCoffeesSimple_Succeeds() {
        List<Cycle> cys = new ArrayList<>();
        int vol = Cycle.MIN_VOLUME;
        int brew = Cycle.MIN_TIME;
        int vac = Cycle.MIN_TIME + 1;
        cys.add(new Cycle(vol, brew, vac));
        List<Volume> vols = new ArrayList<>();
        vols.add(new Volume(cys));
        List<Coffee> cofs = new ArrayList<>();
        String name = "name";
        Coffee cof = new Coffee(name, vols);
        cofs.add(cof);
        CoffeeDao.insertCoffees(db, cofs);
        List<Coffee> cofsOut = CoffeeDao.getCoffees(db);
        Coffee cofOut = cofsOut.get(0);
        assertEquals(cof.name(), cofOut.name());
        assertEquals(cof.volumes().size(), cofOut.volumes().size());
        List<Cycle> cycsOut = cofOut.volumes().get(0).cycles();
        Cycle cycOut = cycsOut.get(0);
        assertEquals(vol, cycOut.volumeMl());
        assertEquals(brew, cycOut.brewSeconds());
        assertEquals(vac, cycOut.vacuumSeconds());
    }

    @Test
    public void createAndReadCoffeesMultiple_Succeeds() {
        List<Cycle> cys = new ArrayList<>();
        int vol = Cycle.MIN_VOLUME;
        int brew = Cycle.MIN_TIME;
        int vac = Cycle.MIN_TIME + 1;
        cys.add(new Cycle(vol, brew, vac));
        cys.add(new Cycle(vol, brew, vac));
        List<Volume> vols = new ArrayList<>();
        vols.add(new Volume(cys));
        cys.add(new Cycle(vol, brew, vac));
        vols.add(new Volume(cys));
        List<Coffee> cofs = new ArrayList<>();
        String name = "name";
        Coffee cof = new Coffee(name, vols);
        cofs.add(cof);
        CoffeeDao.insertCoffees(db, cofs);
        List<Coffee> cofsOut = CoffeeDao.getCoffees(db);
        Coffee cofOut = cofsOut.get(0);
        assertEquals(cof.name(), cofOut.name());
        assertEquals(cof.volumes().size(), cofOut.volumes().size());
        List<Cycle> cycsOut = cofOut.volumes().get(0).cycles();
        Cycle cycOut = cycsOut.get(0);
        assertEquals(vol, cycOut.volumeMl());
        assertEquals(brew, cycOut.brewSeconds());
        assertEquals(vac, cycOut.vacuumSeconds());
    }

    @Test
    public void createAndReadCoffeesSmallStressTest_Succeeds() {
        List<Coffee> c = DatabaseTestUtils.createCoffees(30, 10);
        CoffeeDao.insertCoffees(db, c);
        List<Coffee> cOut = CoffeeDao.getCoffees(db);
        DatabaseTestUtils.assertCoffeesEqual(c, cOut);
    }

    @Test
    public void createAndReadCoffeesBigStressTest_Succeeds() {
        List<Coffee> c = DatabaseTestUtils.createCoffees(300, 10);
        CoffeeDao.insertCoffees(db, c);
        List<Coffee> cOut = CoffeeDao.getCoffees(db);
        DatabaseTestUtils.assertCoffeesEqual(c, cOut);
    }

    @Test
    public void createAndReadManyCoffees_Succeeds() {
        List<Coffee> c = DatabaseTestUtils.createCoffees(3000, 1);
        CoffeeDao.insertCoffees(db, c);
        List<Coffee> cOut = CoffeeDao.getCoffees(db);
        DatabaseTestUtils.assertCoffeesEqual(c, cOut);
    }

    @Test
    public void createAndReadCoffeeWithManyVolumes_Succeeds() {
        List<Coffee> c = DatabaseTestUtils.createCoffees(1, 3000);
        CoffeeDao.insertCoffees(db, c);
        List<Coffee> cOut = CoffeeDao.getCoffees(db);
        DatabaseTestUtils.assertCoffeesEqual(c, cOut);
    }

    @Test
    public void deleteCoffee_Succeeds() {
        List<Cycle> cys = new ArrayList<>();
        int vol = Cycle.MIN_VOLUME;
        int brew = Cycle.MIN_TIME;
        int vac = Cycle.MIN_TIME + 1;
        cys.add(new Cycle(vol, brew, vac));
        List<Volume> vols = new ArrayList<>();
        vols.add(new Volume(cys));
        List<Coffee> cofs = new ArrayList<>();
        String name = "name";
        Coffee cof = new Coffee(name, vols);
        cofs.add(cof);

        CoffeeDao.insertCoffees(db, cofs);
        long n = DatabaseUtils.queryNumEntries(db, Contract.Coffee.TABLE_NAME);
        assertEquals(1, n);
        n = DatabaseUtils.queryNumEntries(db, Contract.Volume.TABLE_NAME);
        assertEquals(1, n);
        n = DatabaseUtils.queryNumEntries(db, Contract.Cycle.TABLE_NAME);
        assertEquals(1, n);

        List<Coffee> cofsOut = CoffeeDao.getCoffees(db);
        CoffeeDao.deleteCoffee(db, cofsOut.get(0).id());
        n = DatabaseUtils.queryNumEntries(db, Contract.Coffee.TABLE_NAME);
        assertEquals(0, n);
        n = DatabaseUtils.queryNumEntries(db, Contract.Volume.TABLE_NAME);
        assertEquals(0, n);
        n = DatabaseUtils.queryNumEntries(db, Contract.Cycle.TABLE_NAME);
        assertEquals(0, n);
    }
}
