package com.ssimon.cyclesactivity.data;

import android.provider.BaseColumns;

public class Contract {
    static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "groundcontrol_database.db";
    //static final String DATABASE_NAME = "nexxxxtest.db";

    static final private String CLOSE_PAREN = " ) ";
    static final private String COMMA = " , ";
    static final private String CREATE = " CREATE TABLE ";
    static final private String DROP_TABLE_IF_EXISTS = " DROP TABLE IF EXISTS ";
    static final private String FOREIGN_KEY = " FOREIGN KEY ";
    static final private String INTEGER = " INTEGER ";
    static final private String NOT_NULL = " NOT NULL ";
    static final private String ON_DELETE_CASCADE = " ON DELETE CASCADE ";
    static final private String OPEN_PAREN = " ( ";
    static final private String PRIMARY_KEY = " PRIMARY KEY ";
    static final private String REFERENCES = " REFERENCES ";
    static final private String TEXT = " TEXT ";
    static final private String UNIQUE = " UNIQUE ";

    static abstract class Coffee implements BaseColumns {
        static final String TABLE_NAME = "coffee";

        static abstract class Col {
            static final String ID = BaseColumns._ID;
            static final String NAME = "name";
         }

        static final String CREATE_TABLE =
                CREATE + TABLE_NAME + OPEN_PAREN
                + Col.ID + INTEGER  + PRIMARY_KEY + COMMA
                + Col.NAME + TEXT + NOT_NULL + UNIQUE
                + CLOSE_PAREN;

        static final String DELETE_TABLE = DROP_TABLE_IF_EXISTS + TABLE_NAME;
    }

    static abstract class Volume implements BaseColumns {
        static final String TABLE_NAME = "volume";

        static abstract class Col {
            static final String ID = BaseColumns._ID;
            static final String COFFEE_ID = "coffee_id";
        }

        static final String CREATE_TABLE =
                CREATE + TABLE_NAME + OPEN_PAREN
                + Col.ID + INTEGER  + PRIMARY_KEY + COMMA
                + Col.COFFEE_ID + INTEGER + NOT_NULL + COMMA
                + FOREIGN_KEY + OPEN_PAREN + Col.COFFEE_ID + CLOSE_PAREN
                    + REFERENCES + Coffee.TABLE_NAME
                    + OPEN_PAREN + Coffee.Col.ID + CLOSE_PAREN + ON_DELETE_CASCADE
                + CLOSE_PAREN;

        static final String DELETE_TABLE = DROP_TABLE_IF_EXISTS + TABLE_NAME;
    }

    static abstract class Cycle implements BaseColumns {
        static final String TABLE_NAME = "cycle";

      static abstract class Col {
            static final String ID = "_id";
            static final String BREW_TIME_SECONDS = "brew_time_seconds";
            static final String VOLUME_MILLILITERS = "volume_milliliters";
            static final String VACUUM_TIME_SECONDS = "vacuum_time_seconds";
            static final String VOLUME_ID = "volume_id";
        }

        static final String CREATE_TABLE =
            CREATE + TABLE_NAME + OPEN_PAREN
            + Col.ID + INTEGER + PRIMARY_KEY + COMMA
            + Col.BREW_TIME_SECONDS + INTEGER + NOT_NULL + COMMA
            + Col.VOLUME_MILLILITERS + INTEGER + NOT_NULL + COMMA
            + Col.VACUUM_TIME_SECONDS + INTEGER + NOT_NULL + COMMA
            + Col.VOLUME_ID + INTEGER + NOT_NULL + COMMA
            + FOREIGN_KEY + OPEN_PAREN + Col.VOLUME_ID + CLOSE_PAREN
                + REFERENCES + Volume.TABLE_NAME
                + OPEN_PAREN + Volume.Col.ID + CLOSE_PAREN + ON_DELETE_CASCADE
            + CLOSE_PAREN;

        static final String DELETE_TABLE = DROP_TABLE_IF_EXISTS + TABLE_NAME;
    }
}
