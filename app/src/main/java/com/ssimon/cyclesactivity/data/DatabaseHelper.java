package com.ssimon.cyclesactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Process;

import com.ssimon.cyclesactivity.Const;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.util.ModelUtils;
import com.ssimon.cyclesactivity.model.Cycle;
import com.ssimon.cyclesactivity.util.Checker;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    static private DatabaseHelper singleton = null;

    static synchronized public DatabaseHelper getInstance(Context c) {
        Checker.notNull(c);
        if (singleton == null)
            singleton = new DatabaseHelper(c.getApplicationContext());
        return singleton;
    }

    private DatabaseHelper(Context c) {
        super(c, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.Coffee.CREATE_TABLE);
        db.execSQL(Contract.Volume.CREATE_TABLE);
        db.execSQL(Contract.Cycle.CREATE_TABLE);
        List<Coffee> defaults = ModelUtils.createDefaultCoffeeTemplates();
        CoffeeDao.insertCoffees(db, defaults);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contract.Coffee.DELETE_TABLE);
        db.execSQL(Contract.Volume.DELETE_TABLE);
        db.execSQL(Contract.Cycle.DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    /*
    public void refreshCoffeeCache() {
        SQLiteDatabase db = getReadableDatabase();
        List<Coffee> cs = CoffeeDao.getCoffees(db);
        CoffeeCache.setCoffees(cs);
    }
    */

    public void refreshCoffeeCache() {
        new RefreshCoffeeCacheThread().start();
    }


    private class RefreshCoffeeCacheThread extends BackgroundThread {
        RefreshCoffeeCacheThread() {
            super();
        }

        @Override
        public void run() {
            super.run();
            SQLiteDatabase db = getReadableDatabase();
            List<Coffee> cs = CoffeeDao.getCoffees(db);
            CoffeeCache.setCoffees(cs);
        }
    }

    public void saveCoffee(Coffee c) {
        new SaveCoffeeThread(c).start();
    }

    private class SaveCoffeeThread extends BackgroundThread {
        final private Coffee coffee;

        SaveCoffeeThread(Coffee coffee) {
            super();
            Checker.notNull(coffee);
            this.coffee = coffee;
        }

        @Override
        public void run() {
            super.run();
            SQLiteDatabase db = getWritableDatabase();
            CoffeeDao.insertCoffee(db, coffee);
            refreshCoffeeCache();
        }
    }


    public void saveVolume(long coffeeId, List<Cycle> cycles) {
        new SaveVolumeThread(coffeeId, cycles).start();
    }

    private class SaveVolumeThread extends BackgroundThread {
        final private long coffeeId;
        final private List<Cycle> cycles;

        SaveVolumeThread(long coffeeId, List<Cycle> cycles) {
            super();
            Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
            Checker.notNullOrEmpty(cycles);
            this.coffeeId = coffeeId;
            this.cycles = cycles;
        }

        @Override
        public void run() {
            super.run();
            SQLiteDatabase db = getWritableDatabase();
            VolumeDao.insertVolume(db, coffeeId, cycles);
            refreshCoffeeCache();
        }
    }

    public void deleteCoffee(long coffeeId) {
        new DeleteCoffeeThread(coffeeId).start();
    }

    private class DeleteCoffeeThread extends BackgroundThread {
        final private long coffeeId;

        DeleteCoffeeThread(long coffeeId) {
            super();
            Checker.atLeast(coffeeId, Const.MIN_DATABASE_ID);
            this.coffeeId = coffeeId;
        }

        @Override
        public void run() {
            super.run();
            SQLiteDatabase db = getWritableDatabase();
            CoffeeDao.deleteCoffee(db, coffeeId);
            refreshCoffeeCache();
        }
    }

    abstract private class BackgroundThread extends Thread {
        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        }
    }
}
