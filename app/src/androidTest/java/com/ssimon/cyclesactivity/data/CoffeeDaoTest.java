package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.ssimon.cyclesactivity.DatabaseUtils;
import com.ssimon.cyclesactivity.ModelUtils;
import com.ssimon.cyclesactivity.model.Coffee;

import org.junit.Test;

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
}
