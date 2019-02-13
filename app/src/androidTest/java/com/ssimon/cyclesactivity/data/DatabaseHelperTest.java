package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.DatabaseTestUtils;
import com.ssimon.cyclesactivity.ModelTestUtils;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.model.Volume;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {
    final private Context context = InstrumentationRegistry.getTargetContext();

    /*
    @Test
    public void resetTablesAndOpenDb_Success() {
        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        assertTrue(db.isOpen());
    }

    @Test
    public void saveVolume_Success() {
        Coffee c = ModelTestUtils.createCoffee();
        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        CoffeeDao.insertCoffee(db, c);

        List<Coffee> cs = CoffeeDao.getCoffees(db);
        Coffee cout = cs.get(0);
        Volume v = ModelTestUtils.createVolume();
        DatabaseHelper dh = DatabaseHelper.getInstance(context);
        dh.saveVolume(cout.id(), v.cycles());
    */

    @Test
    public void resetTablesAndOpenDb_Success() {
        SQLiteDatabase db = DatabaseTestUtils.getCleanWritableDb(context);
        assertTrue(db.isOpen());
    }

    @Test
    public void saveVolume_Success() {
        DatabaseHelper dh = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = dh.getWritableDatabase();
        dh.onUpgrade(db, 0, 0);
        Coffee c = ModelTestUtils.createCoffee();
        CoffeeDao.insertCoffee(db, c);
        List<Coffee> cs = CoffeeDao.getCoffees(db);
        Coffee cout = cs.get(0);
        Volume v = ModelTestUtils.createVolume();
        dh.saveVolume(cout.id(), v.cycles());
/*
        List<Coffee> cs = CoffeeDao.getCoffees(db);
        Coffee cout = cs.get(0);
        Volume v = ModelTestUtils.createVolume();
        DatabaseHelper dh = DatabaseHelper.getInstance(context);
        dh.saveVolume(cout.id(), v.cycles());
*/

    }
}
