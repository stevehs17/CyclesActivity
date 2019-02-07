package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.security.keystore.UserNotAuthenticatedException;
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

        Cycle cyc = new Cycle(Const.UNSET_DATABASE_ID, volumeMl, brewTime, vacuumTime);
        List<Cycle> cycles = new ArrayList<>();
        cycles.add(cyc);
        Volume vol = new Volume(Const.UNSET_DATABASE_ID, cycles);
        List<Volume> vols = new ArrayList<>();
        vols.add(vol);
        Coffee cof = new Coffee(Const.UNSET_DATABASE_ID, name, vols);

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
        assertNotEquals(cyc.volumeId(), cycOut.volumeId());
        assertEquals(1, cycOut.volumeId());
        assertEquals(cyc.volumeMl(), cycOut.volumeMl());
        assertEquals(cyc.brewSeconds(), cycOut.brewSeconds());
        assertEquals(cyc.vacuumSeconds(), cycOut.vacuumSeconds());
    }

    /*
    @Test
    public void getCoffees_multipleitemlists_validate_values_Success() {
        final int volumeMl = (Cycle.MAX_VOLUME + Cycle.MIN_BREWTIME)/2;
        final int brewTime = (Cycle.MIN_BREWTIME + Cycle.MAX_BREWTIME)/2;
        final int vacuumTime = (Cycle.MIN_VACUUMTIME+ Cycle.MAX_VACUUMTIME)/2;
        final String name = "aname";

        Cycle cyc = new Cycle(Const.UNSET_DATABASE_ID, volumeMl, brewTime, vacuumTime);
        List<Cycle> cycles = new ArrayList<>();
        cycles.add(cyc);
        Cycle cyc2 = new Cycle(Const.UNSET_DATABASE_ID, volumeMl+1, brewTime+2, vacuumTime+3);
        cycles.add(cyc2);

        Volume vol = new Volume(Const.UNSET_DATABASE_ID, cycles);
        List<Volume> vols = new ArrayList<>();
        vols.add(vol);
        vols.add(vol);
        Coffee cof = new Coffee(Const.UNSET_DATABASE_ID, name, vols);

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
        assertEquals(1, volOut.id());

        Cycle cycOut = volOut.cycles().get(0);
        assertNotEquals(cyc.volumeId(), cycOut.volumeId());
        assertEquals(1, cycOut.volumeId());
        assertEquals(cyc.volumeMl(), cycOut.volumeMl());
        assertEquals(cyc.brewSeconds(), cycOut.brewSeconds());
        assertEquals(cyc.vacuumSeconds(), cycOut.vacuumSeconds());

        Cycle cyc2Out = volOut.cycles().get(1);
        assertNotEquals(cyc.volumeId(), cyc2Out.volumeId());
        assertEquals(1, cyc2Out.volumeId());
        assertEquals(cyc2.volumeMl(), cyc2Out.volumeMl());
        assertEquals(cyc2.brewSeconds(), cyc2Out.brewSeconds());
        assertEquals(cyc2.vacuumSeconds(), cyc2Out.vacuumSeconds());
    }
    */
}
