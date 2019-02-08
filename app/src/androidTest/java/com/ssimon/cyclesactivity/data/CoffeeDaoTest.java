package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.DatabaseUtils;
import com.ssimon.cyclesactivity.ModelUtils;
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
        DatabaseHelperTest dht = new DatabaseHelperTest();
        dht.reset_tables_and_open_db_Success();
        SQLiteDatabase db = DatabaseUtils.getWritableDb(context);
        Coffee c = ModelUtils.createCoffee();
        CoffeeDao.insertCoffee(db, c);
    }

    @Test
    public void getCoffees_Success() {
        insertCoffee_Success();
        SQLiteDatabase db = DatabaseUtils.getReadableleDb(context);
        List<Coffee> cs = CoffeeDao.getCoffees(db);
    }

    @Test
    public void getCoffees_singleitemlists_validate_values_Success() {
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

        DatabaseHelperTest dht = new DatabaseHelperTest();
        dht.reset_tables_and_open_db_Success();
        SQLiteDatabase db = DatabaseUtils.getWritableDb(context);
        CoffeeDao.insertCoffee(db, cof);

        db = DatabaseUtils.getReadableleDb(context);
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

        DatabaseHelperTest dht = new DatabaseHelperTest();
        dht.reset_tables_and_open_db_Success();
        SQLiteDatabase db = DatabaseUtils.getWritableDb(context);
        CoffeeDao.insertCoffee(db, cof);

        db = DatabaseUtils.getReadableleDb(context);
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
        DatabaseHelperTest dht = new DatabaseHelperTest();
        dht.reset_tables_and_open_db_Success();
        SQLiteDatabase db = DatabaseUtils.getWritableDb(context);
        CoffeeDao.insertCoffee(db, cof);
        List<Coffee> coffees = CoffeeDao.getCoffees(db);
        List<Volume> vols = coffees.get(0).volumes();
        assertEquals(Cycle.MIN_VOLUME, vols.get(0).totalVolume());
        assertEquals(Cycle.MIN_VOLUME * 2, vols.get(1).totalVolume());
        assertEquals(Cycle.MIN_VOLUME * 3, vols.get(2).totalVolume());
    }

    static final private long NOID = Const.UNSET_DATABASE_ID;


    @Test
    public void create_and_validate_many_coffees_Success() {
        final int numCoffees = 100;
        final int numVolumes = 50;
        final int numCycles = 10;

        List<Coffee> coffees = createCoffees(numCoffees, numVolumes, numCycles);
        validateCoffees(coffees);
    }


    private List<Coffee> createCoffees(int numCoffees, int numVolumes, int numCycles) {
        List<Coffee> coffees = new ArrayList<>();
        for (int i = 0; i < numCoffees; i++) {
            List<Volume> volumes = new ArrayList<>();
            for (int j = 0; j < numVolumes; j++) {
                List<Cycle> cycles = new ArrayList<>();
                for (int k = 0; k < numCycles; k++) {
                    cycles.add(new Cycle(volume(i, j, k), brewtime(i, j, k), vactime(i, j, k)));
                }
                volumes.add(new Volume(NOID, cycles));
            }
            coffees.add(new Coffee(NOID, name(i), volumes, NOID));
        }
        return coffees;
    }

    private void validateCoffees(List<Coffee> coffees) {
        for (int i = 0; i < coffees.size(); i++) {
            Coffee cof = coffees.get(i);
            //assertEquals(Const.MIN_DATABASE_ID + i, cof.id());
            assertEquals(name(i), cof.name());
            List<Volume> vols = cof.volumes();
            for (int j = 0; j < vols.size(); j++) {
                Volume vol = vols.get(j);
                //assertEquals(Const.MIN_DATABASE_ID + 10*i + j, vol.id());
                List<Cycle> cycles = vol.cycles();
                for (int k = 0; k < cycles.size(); k++) {
                    Cycle cyc = cycles.get(k);
                    assertEquals(volume(i, j, k), cyc.volumeMl());
                    assertEquals(brewtime(i, j, k), cyc.brewSeconds());
                    assertEquals(vactime(i, j, k), cyc.vacuumSeconds());
                }
            }
        }
    }

    private int volume(int i, int j, int k) {
        return parm(Cycle.MIN_VOLUME, Cycle.MAX_VOLUME, i, j, k);
    }

    private int brewtime(int i, int j, int k) {
        return parm(Cycle.MIN_BREWTIME, Cycle.MAX_BREWTIME, i, j, k);
    }

    private int vactime(int i, int j, int k) {
        return parm(Cycle.MIN_VACUUMTIME+1, Cycle.MAX_VACUUMTIME, i, j, k);
    }

    private int parm(int min, int max, int i, int j, int k) {
        int n = min + i*100 + j*10 + k;
        return n > max ? min : n;
    }

    private String name(int i) {
       return String.valueOf(i);
    }
}
