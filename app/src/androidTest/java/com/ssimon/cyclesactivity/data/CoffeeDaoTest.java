package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.Const;
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
    static private final Context sContext = InstrumentationRegistry.getTargetContext();
    static final private int NVOLS = 2;
    static final private List<Volume> VOLUMES = DatabaseTestUtils.createVolumes(NVOLS);
    static final private long COF_ID = 1;

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }

    @Before
    public void setupTables() {
        DatabaseTestUtils.setupTables(sContext);
    }

    @Test
    public void createAndReadCoffeesSimple_Succeeds() {
        List<Cycle> cys = new ArrayList<>();
        int vol = Cycle.MIN_VOLUME;
        int brew = Cycle.MIN_BREWTIME;
        int vac = Cycle.MIN_VACUUMTIME + 1;
        cys.add(new Cycle(vol, brew, vac));
        List<Volume> vols = new ArrayList<>();
        vols.add(new Volume(Const.UNSET_DATABASE_ID, cys));
        List<Coffee> cofs = new ArrayList<>();
        String name = "name";
        Coffee cof = new Coffee(Const.UNSET_DATABASE_ID, name, vols,
                Const.UNSET_DATABASE_ID);
        cofs.add(cof);
        DatabaseHelper dh = DatabaseHelper.getInstance(sContext);
        SQLiteDatabase db = dh.getWritableDatabase();
        CoffeeDao.insertCoffees(db, cofs);
        List<Coffee> cofsOut = CoffeeDao.getCoffees(db);
        Coffee cofOut = cofsOut.get(0);
        assertEquals(cof.name(), cofOut.name());
        assertEquals(cof.defaultVolumeId(), cofOut.defaultVolumeId());
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
        int brew = Cycle.MIN_BREWTIME;
        int vac = Cycle.MIN_VACUUMTIME + 1;
        cys.add(new Cycle(vol, brew, vac));
        cys.add(new Cycle(vol, brew, vac));
        List<Volume> vols = new ArrayList<>();
        vols.add(new Volume(Const.UNSET_DATABASE_ID, cys));
        cys.add(new Cycle(vol, brew, vac));
        vols.add(new Volume(Const.UNSET_DATABASE_ID, cys));
        List<Coffee> cofs = new ArrayList<>();
        String name = "name";
        Coffee cof = new Coffee(Const.UNSET_DATABASE_ID, name, vols,
                Const.UNSET_DATABASE_ID);
        cofs.add(cof);
        DatabaseHelper dh = DatabaseHelper.getInstance(sContext);
        SQLiteDatabase db = dh.getWritableDatabase();
        CoffeeDao.insertCoffees(db, cofs);
        List<Coffee> cofsOut = CoffeeDao.getCoffees(db);
        Coffee cofOut = cofsOut.get(0);
        assertEquals(cof.name(), cofOut.name());
        assertEquals(cof.defaultVolumeId(), cofOut.defaultVolumeId());
        assertEquals(cof.volumes().size(), cofOut.volumes().size());
        List<Cycle> cycsOut = cofOut.volumes().get(0).cycles();
        Cycle cycOut = cycsOut.get(0);
        assertEquals(vol, cycOut.volumeMl());
        assertEquals(brew, cycOut.brewSeconds());
        assertEquals(vac, cycOut.vacuumSeconds());
    }

    @Test
    public void deleteCoffee_Succeeds() {
        List<Cycle> cys = new ArrayList<>();
        int vol = Cycle.MIN_VOLUME;
        int brew = Cycle.MIN_BREWTIME;
        int vac = Cycle.MIN_VACUUMTIME + 1;
        cys.add(new Cycle(vol, brew, vac));
        List<Volume> vols = new ArrayList<>();
        vols.add(new Volume(Const.UNSET_DATABASE_ID, cys));
        List<Coffee> cofs = new ArrayList<>();
        String name = "name";
        Coffee cof = new Coffee(Const.UNSET_DATABASE_ID, name, vols,
                Const.UNSET_DATABASE_ID);
        cofs.add(cof);

        DatabaseHelper dh = DatabaseHelper.getInstance(sContext);
        SQLiteDatabase db = dh.getWritableDatabase();
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
