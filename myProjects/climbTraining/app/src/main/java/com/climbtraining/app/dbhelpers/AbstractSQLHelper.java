package com.climbtraining.app.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public abstract class AbstractSQLHelper extends SQLiteOpenHelper {

    private final static String TAG = AbstractSQLHelper.class.getSimpleName();
    protected final static String DATABASE_NAME = "climbTrainingDB.db";

    public AbstractSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void removeEntityById(String dbName, String id) {
        Log.d(TAG, "removeEntityById() start");
        SQLiteDatabase database = this.getWritableDatabase();
        String whereClause = "id=?";
        String [] whereArgs = {id};
        database.delete(dbName, whereClause, whereArgs);
        database.close();
        Log.d(TAG, "removeEntityById() done");
    }
}
