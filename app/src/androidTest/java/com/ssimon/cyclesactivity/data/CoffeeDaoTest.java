package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.DatabaseTestUtils;
import com.ssimon.cyclesactivity.ModelTestUtils;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

public class CoffeeDaoTest {
    final private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void insertCoffee_Success() {
        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        Coffee c = ModelTestUtils.createCoffee();
        CoffeeDao.insertCoffee(db, c);
    }

    @Test
    public void getCoffees_Success() {
        insertCoffee_Success();
        SQLiteDatabase db = DatabaseTestUtils.getReadableleDb(context);
        List<Coffee> cs = CoffeeDao.getCoffees(db);
    }

    @Test
    public void getCoffees_singleitemlists_validate_values_Success2() {
        final int volumeMl = (Cycle.MAX_VOLUME + Cycle.MIN_BREWTIME)/2;
        final int brewTime = (Cycle.MIN_BREWTIME + Cycle.MAX_BREWTIME)/2;
        final int vacuumTime = (Cycle.MIN_VACUUMTIME+ Cycle.MAX_VACUUMTIME)/2;
        final String name = "aname";

        Cycle cyc = new Cycle(volumeMl, brewTime, vacuumTime);
        List<Cycle> cycles = new ArrayList<>();
        cycles.add(cyc);
        Volume vol = new Volume(Const.UNSET_DATABASE_ID, cycles);
        List<Volume> vols = new ArrayList<>();
        vols.add(vol);
        Coffee cof = new Coffee(Const.UNSET_DATABASE_ID, name, vols, Const.UNSET_DATABASE_ID);

        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        CoffeeDao.insertCoffee(db, cof);

        db = DatabaseTestUtils.getReadableleDb(context);
        List<Coffee> cs = CoffeeDao.getCoffees(db);
        Coffee cofOut = cs.get(0);
        assertNotEquals(cof.id(), cofOut.id());
        assertEquals(1, cofOut.id());
        assertEquals(cof.name(), cofOut.name());

        Volume volOut = cofOut.volumes().get(0);
        assertNotEquals(vol.id(), volOut.id());
        assertEquals(1, volOut.id());

        Cycle cycOut = volOut.cycles().get(0);
        assertEquals(cyc.volumeMl(), cycOut.volumeMl());
        assertEquals(cyc.brewSeconds(), cycOut.brewSeconds());
        assertEquals(cyc.vacuumSeconds(), cycOut.vacuumSeconds());
    }

    @Test
    public void getCoffees_multipleitemlists_validate_values_Success() {
        final int volumeMl = (Cycle.MAX_VOLUME + Cycle.MIN_BREWTIME)/2;
        final int brewTime = (Cycle.MIN_BREWTIME + Cycle.MAX_BREWTIME)/2;
        final int vacuumTime = (Cycle.MIN_VACUUMTIME+ Cycle.MAX_VACUUMTIME)/2;
        final String name = "aname";

        Cycle cyc = new Cycle(volumeMl, brewTime, vacuumTime);
        List<Cycle> cycles = new ArrayList<>();
        cycles.add(cyc);
        Cycle cyc2 = new Cycle(volumeMl+1, brewTime+2, vacuumTime+3);
        cycles.add(cyc2);

        Volume vol = new Volume(Const.UNSET_DATABASE_ID, cycles);
        List<Volume> vols = new ArrayList<>();
        vols.add(vol);
        vols.add(vol);
        Coffee cof = new Coffee(Const.UNSET_DATABASE_ID, name, vols, Const.UNSET_DATABASE_ID);

        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        CoffeeDao.insertCoffee(db, cof);

        db = DatabaseTestUtils.getReadableleDb(context);
        List<Coffee> cs = CoffeeDao.getCoffees(db);
        Coffee cofOut = cs.get(0);
        assertNotEquals(cof.id(), cofOut.id());
        assertEquals(1, cofOut.id());
        assertEquals(cof.name(), cofOut.name());

        Volume volOut = cofOut.volumes().get(1);
        assertNotEquals(vol.id(), volOut.id());
        assertEquals(2, volOut.id());

        Cycle cycOut = volOut.cycles().get(0);
        assertEquals(cyc.volumeMl(), cycOut.volumeMl());
        assertEquals(cyc.brewSeconds(), cycOut.brewSeconds());
        assertEquals(cyc.vacuumSeconds(), cycOut.vacuumSeconds());

        Cycle cyc2Out = volOut.cycles().get(1);
        assertEquals(cyc2.volumeMl(), cyc2Out.volumeMl());
        assertEquals(cyc2.brewSeconds(), cyc2Out.brewSeconds());
        assertEquals(cyc2.vacuumSeconds(), cyc2Out.vacuumSeconds());
    }

    @Test
    public void getCoffees_sortvolumes_Success() {
        final int volumeMl = Cycle.MIN_VOLUME;
        Cycle c1 = new Cycle(volumeMl, Cycle.MIN_BREWTIME, Cycle.MIN_VACUUMTIME);

        List<Cycle> cs = new ArrayList<>();
        cs.add(c1);
        cs.add(c1);
        cs.add(c1);
        Volume v = new Volume(Const.MIN_DATABASE_ID, cs);
        List<Volume> vs = new ArrayList<>();
        vs.add(v);

        cs = new ArrayList<>();
        cs.add(c1);
        v = new Volume(Const.MIN_DATABASE_ID, cs);
        vs.add(v);

        cs = new ArrayList<>();
        cs.add(c1);
        cs.add(c1);
        v = new Volume(Const.MIN_DATABASE_ID, cs);
        vs.add(v);

        Coffee cof = new Coffee(Const.MIN_DATABASE_ID, "test", vs, Const.MIN_DATABASE_ID);
        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        CoffeeDao.insertCoffee(db, cof);
        List<Coffee> coffees = CoffeeDao.getCoffees(db);
        List<Volume> vols = coffees.get(0).volumes();
        assertEquals(Cycle.MIN_VOLUME, vols.get(0).totalVolume());
        assertEquals(Cycle.MIN_VOLUME * 2, vols.get(1).totalVolume());
        assertEquals(Cycle.MIN_VOLUME * 3, vols.get(2).totalVolume());
    }

    @Test
    public void deletes_Success() {
        final int numCoffees = 1;
        final int numVolumes = 2;
        final int numCycles = 6;

        List<Coffee> coffees = ModelTestUtils.createCoffees(numCoffees, numVolumes, numCycles);
        /*
        DatabaseHelperTest dht = new DatabaseHelperTest();
        dht.reset_tables_and_open_db_Success();
        SQLiteDatabase db = DatabaseTestUtils.getWritableDb(context);
        */
        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        CoffeeDao.insertCoffees(db, coffees);

        List<Volume> vols = VolumeDaoTest.getAndPrintVolumes(db);
        assertEquals(numCoffees * numVolumes, vols.size());
        List<Cycle> cycs = CycleDao.getCycles(db);
        assertEquals(numCoffees * numVolumes * numCycles, cycs.size());

        VolumeDao.deleteVolume(db, Const.MIN_DATABASE_ID);
        vols = VolumeDaoTest.getAndPrintVolumes(db);
        assertEquals(numCoffees * numVolumes - 1, vols.size());
        cycs = CycleDao.getCycles(db);
        assertEquals(numCoffees * numVolumes * numCycles - numCycles, cycs.size());

        CoffeeDao.deleteCoffee(db, Const.MIN_DATABASE_ID);
        cycs = CycleDao.getCycles(db);
        assertEquals(0, cycs.size());
        vols = VolumeDaoTest.getAndPrintVolumes(db);
        assertEquals(0, vols.size());
    }

    @Test
    public void createManyCoffees_Success() {
        int numCoffees = 100;
        int numVolumes = 100;
        int numCycles = Cycle.MAX_NUM_CYCLES;

        List<Coffee> cs = ModelTestUtils.createCoffees(numCoffees, numVolumes, numCycles);
        assertEquals(numCoffees, cs.size());
        assertEquals(numVolumes, cs.get(numCoffees-1).volumes().size() );
        assertEquals(numCycles, cs.get(numCoffees-1).volumes().get(numVolumes-1).cycles().size() );
        ModelTestUtils.validateCoffeesNoIds(cs);
    }

    @Test
    public void saveAndRetrieveManyCoffees_Success() {
        int numCofs = 50;
        int numVols = 50;
        int numCycs = 6;

        List<Coffee> cs = ModelTestUtils.createCoffees(numCofs, numVols, numCycs);
        ModelTestUtils.validateCoffeesNoIds(cs);

        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        CoffeeDao.insertCoffees(db, cs);
        List<Coffee> csOut = CoffeeDao.getCoffees(db);

        db = DatabaseTestUtils.getCleanWritableDb(context);
        CoffeeDao.insertCoffees(db, csOut);
        List<Coffee> csOutOut = CoffeeDao.getCoffees(db);
        ModelTestUtils.validateCoffeesNoIds(csOut, csOutOut);

        db = DatabaseTestUtils.getCleanWritableDb(context);
        CoffeeDao.insertCoffees(db, csOutOut);
        List<Coffee> csOutOutOut = CoffeeDao.getCoffees(db);
        ModelTestUtils.validateCoffeesWithIds(csOutOutOut, csOutOut);
    }
}
