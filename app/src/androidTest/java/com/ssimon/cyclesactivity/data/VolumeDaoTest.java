package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.ssimon.cyclesactivity.DatabaseTestUtils;
import com.ssimon.cyclesactivity.ModelTestUtils;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.model.Volume;
import com.ssimon.cyclesactivity.util.Checker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ssimon.cyclesactivity.data.Contract.Volume.TABLE_NAME;

public class VolumeDaoTest {
    static final private String TAG = "Dao";
    final private Context context = InstrumentationRegistry.getTargetContext();


    @Test
    public void insertVolumes_Failure() {
        try {
            DatabaseHelperTest dht = new DatabaseHelperTest();
            dht.reset_tables_and_open_db_Success();
            SQLiteDatabase db = DatabaseTestUtils.getWritableDb(context);
            List<Volume> vs = ModelTestUtils.createVolumeList();
            VolumeDao.insertVolumes(db, ModelTestUtils.DB_ID, vs);
        } catch (SQLiteConstraintException e) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void insertVolume_Failure() {
        try {
            DatabaseHelperTest dht = new DatabaseHelperTest();
            dht.reset_tables_and_open_db_Success();
            SQLiteDatabase db = DatabaseTestUtils.getWritableDb(context);
            Volume v = ModelTestUtils.createVolume();
            VolumeDao.insertVolume(db, ModelTestUtils.DB_ID, v);
        } catch (SQLiteConstraintException e) {
            return;
        }
        throw new RuntimeException("test failed");
    }

    @Test
    public void printManyVolumes_Success() {
        final int numCoffees = 50;
        final int numVolumes = 25;
        final int numCycles = 6;

        List<Coffee> coffees = ModelTestUtils.createCoffees(numCoffees, numVolumes, numCycles);
        ModelTestUtils.validateCoffeesNoIds(coffees);
        DatabaseHelperTest dht = new DatabaseHelperTest();
        dht.reset_tables_and_open_db_Success();
        SQLiteDatabase db = DatabaseTestUtils.getWritableDb(context);
        CoffeeDao.insertCoffees(db, coffees);
        List<Volume> volumes = getAndPrintVolumes(db);

        List<Coffee> coffeesOut = CoffeeDao.getCoffees(db);
        dht.reset_tables_and_open_db_Success();
        CoffeeDao.insertCoffees(db, coffeesOut);
        volumes = getAndPrintVolumes(db);
    }

    static List<Volume> getAndPrintVolumes(SQLiteDatabase db) {
        Checker.notNull(db);

        String query = String.format("SELECT * FROM %s", TABLE_NAME);
        Cursor c = db.rawQuery(query, null);
        List<Volume> volumes = new ArrayList<>();
        if (!c.moveToFirst())
            return volumes;
        Log.v(TAG, "");
        do {
            long coffeeId = c.getLong(c.getColumnIndexOrThrow(Contract.Volume.Col.COFFEE_ID));
            long volumeId = c.getLong(c.getColumnIndexOrThrow(Contract.Volume.Col.ID));
            List<Cycle> cycles = CycleDao.getCycles(db, volumeId);
            Volume v = new Volume(volumeId, cycles);
            Log.v(TAG, String.format("Coffee ID = %d, Volume ID = %d, Total Volume = %d",
                    coffeeId, volumeId, v.totalVolume()));
            volumes.add(v);
        } while (c.moveToNext());
        c.close();
        return volumes;
    }

}
