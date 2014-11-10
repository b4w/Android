package com.climbtraining.app.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractSQLHelper extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "climbTrainingDB.db";
    protected final static int DATABASE_VERSION = 1;

    public AbstractSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
