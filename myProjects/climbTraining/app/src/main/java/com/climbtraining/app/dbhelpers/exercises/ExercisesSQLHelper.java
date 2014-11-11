package com.climbtraining.app.dbhelpers.exercises;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.climbtraining.app.dbhelpers.AbstractSQLHelper;
import com.climbtraining.app.pojo.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExercisesSQLHelper extends AbstractSQLHelper {

    private static final String TAG = ExercisesSQLHelper.class.getSimpleName();
    private static final String DATABASE_TABLE = "Exercises";
    private static final int DATABASE_VERSION = 1;

    public ExercisesSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // если БД не существует, то вызываем метод onCreate
        database.execSQL("create table " + DATABASE_TABLE + "( " +
                "id integer primary key autoincrement," +
                "name text not null," +
                "comment text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // если версия БД изменилась, то вызывается метод onUpgrade
        String query = "DROP TABLE IF EXISTS " + DATABASE_TABLE;
        database.execSQL(query);
        onCreate(database);
    }


    public void insertExercise(Exercise exercise) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", exercise.getName());
        contentValues.put("comment", exercise.getComment());

        database.insert(DATABASE_TABLE, null, contentValues);
        database.close();
    }

    public int updateExercise(Exercise exercise) {
        // todo по какому параментру обновлять?
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", exercise.getName());
        contentValues.put("comments", exercise.getComment());

        String whereClause = "name = ?";
        String[] whereArgs = {"Подтягивание"};

        return database.update(DATABASE_TABLE, contentValues, whereClause, whereArgs);
    }

    public void deleteExerciseById(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + DATABASE_TABLE + " where id='" + id + "'";
        database.execSQL(deleteQuery);
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<Exercise>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise(cursor.getString(1), cursor.getString(2));
                exercises.add(exercise);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "Empty table - " + DATABASE_TABLE);
        }
        return exercises;
    }

    public Exercise getExerciseById(String id) {
        Exercise exercise = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE + " WHERE id='" + id + "'";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                exercise = new Exercise(cursor.getColumnName(1), cursor.getColumnName(2));
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "Empty table - " + DATABASE_TABLE);
        }
        return exercise;
    }
}
