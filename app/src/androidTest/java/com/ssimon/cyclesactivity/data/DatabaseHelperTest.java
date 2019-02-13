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
        Coffee c = ModelTestUtils.createCoffee();
        List<Coffee> cs;
        try {
            cs = CoffeeDao.getCoffees(db);
            CoffeeDao.deleteCoffees(db, cs);
        } catch (IllegalStateException ignored) {
            // no coffees found
        }
        CoffeeDao.insertCoffee(db, c);
        cs = CoffeeDao.getCoffees(db);
        Coffee cout = cs.get(0);
        Volume v = ModelTestUtils.createVolume();
        dh.saveVolume(cout.id(), v.cycles());
    }
}
