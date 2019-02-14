package com.ssimon.cyclesactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ssimon.cyclesactivity.data.CoffeeDao;
import com.ssimon.cyclesactivity.data.DatabaseHelper;
import com.ssimon.cyclesactivity.model.Coffee;

import java.util.List;

public class DatabaseTestUtils {
    static public SQLiteDatabase getWritableDb(Context ctx) {
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        return dh.getWritableDatabase();
    }

    static public SQLiteDatabase getReadableleDb(Context ctx) {
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        return dh.getReadableDatabase();
    }

    static public SQLiteDatabase getCleanWritableDb(Context ctx) {
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        CoffeeDao.deleteCoffees(db);
        return db;
    }

    static public void addCoffeesToDb(Context ctx) {
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        dh.onUpgrade(db, 0, 0);

        final int nCofs = 20, nVols = 1, nCycs = 1;
        List<Coffee> coffees = ModelTestUtils.createCoffees(nCofs, nVols, nCycs);
        ModelTestUtils.validateCoffeesNoIds(coffees);
        CoffeeDao.insertCoffees(db, coffees);
    }
}
