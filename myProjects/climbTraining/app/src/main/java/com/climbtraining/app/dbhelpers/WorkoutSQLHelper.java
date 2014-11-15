package com.climbtraining.app.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WorkoutSQLHelper extends SQLiteOpenHelper {

    private final String LOG_TAG = WorkoutSQLHelper.class.getSimpleName();
    public static final String TABLE_NAME = "workoutTable";

    public WorkoutSQLHelper(Context context) {
        super(context, "workoutTable", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate table workTable ---");
        // создаем таблицу с полями
        db.execSQL("create table " + TABLE_NAME + " (" +
                "id integer primary key autoincrement," +
                "date text," +
                "workout text," +
                "comments text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
