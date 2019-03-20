package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {
    private DatabaseHelper dbHelper;

    @Before
    public void resetDb() {
        final String name = "test.db";
        final Context c = InstrumentationRegistry.getTargetContext();
        c.deleteDatabase(name);
        dbHelper = dbHelper.getInstance(c, name);
    }

    @Test
    public void openWritableDb_Succeeds() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void openReadableDb_Succeeds() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        assertTrue(db.isOpen());
    }

    /*
    final private Context context = InstrumentationRegistry.getTargetContext();
    static SQLiteDatabase db = null;

    @Test
    public void openWritableDb_Success() {
        DatabaseHelper dh = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = dh.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void openReadableDb_Success() {
        DatabaseHelper dh = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = dh.getReadableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void saveVolume_Success() {
        DatabaseHelper dh = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = dh.getWritableDatabase();
        CoffeeDao.deleteCoffees(db);
        CoffeeDao.insertCoffee(db, ModelTestUtils.createCoffee());
        List<Coffee> cs = CoffeeDao.getCoffees(db);
        Coffee cout = cs.get(0);
        Volume v = ModelTestUtils.createVolume();
        dh.saveVolume(cout.id(), v.cycles());
    }
    */
}
