package com.climbtraining.app.dbhelpers.exercises;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.climbtraining.app.dbhelpers.AbstractSQLHelper;
import com.climbtraining.app.pojo.exercise.Category;

import java.util.ArrayList;
import java.util.List;

public class CategorySQLHelper extends AbstractSQLHelper {

    private static final String TAG = CategorySQLHelper.class.getSimpleName();
    private static final String DATABASE_TABLE = "CATEGORY";

    public CategorySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "CREATE TABLE " + DATABASE_TABLE + " ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT NOT NULL, " +
                "ENG_NAME TEXT, " +
                "DESCRIPTION TEXT, " +
                "ENG_DESCRIPTION TEXT, " +
                "IMAGE INTEGER, " +
                "ICON INTEGER, " +
                "COMMENTS TEXT)";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DATABASE_TABLE;
        database.execSQL(query);
        onCreate(database);
    }

    // todo переделать на generic в abstract классе
    public void insertValue(Category category) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", category.getName());
        contentValues.put("ENG_NAME", category.getEngName());
        contentValues.put("DESCRIPTION", category.getDescription());
        contentValues.put("ENG_DESCRIPTION", category.getEngDescription());
        contentValues.put("IMAGE", category.getImage());
        contentValues.put("ICON", category.getIcon());
        contentValues.put("COMMENTS", category.getComments());

        database.insert(DATABASE_TABLE, null, contentValues);
        database.close();
    }

    public List<Category> getAllExercises() {
        List<Category> exercises = new ArrayList<Category>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7));
                exercises.add(category);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "Empty table - " + DATABASE_TABLE);
        }
        return exercises;
    }
}
