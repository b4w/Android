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
    public static final String DATABASE_TABLE = "CATEGORY";
    private static final int DATABASE_VERSION = 2;

    private static final int INDEX_ID = 0;
    private static final int INDEX_NAME = 1;
    private static final int INDEX_DESCRIPTION = 2;
    private static final int INDEX_IMAGE = 3;
    private static final int INDEX_ICON = 4;
    private static final int INDEX_COMMENTS = 5;

    private static final String CREATE_TABLE = "CREATE TABLE CATEGORY ( " +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NAME TEXT NOT NULL, " +
            "DESCRIPTION TEXT, " +
            "IMAGE INTEGER, " +
            "ICON INTEGER, " +
            "COMMENTS TEXT)";

    public CategorySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
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
        contentValues.put("DESCRIPTION", category.getDescription());
        contentValues.put("IMAGE", category.getImage());
        contentValues.put("ICON", category.getIcon());
        contentValues.put("COMMENTS", category.getComments());

        database.insert(DATABASE_TABLE, null, contentValues);
        database.close();
    }

    public List<Category> getAllCategories() {
        List<Category> exercises = new ArrayList<Category>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(cursor.getInt(INDEX_ID),
                        cursor.getString(INDEX_NAME),
                        cursor.getString(INDEX_DESCRIPTION),
                        cursor.getInt(INDEX_IMAGE),
                        cursor.getInt(INDEX_ICON),
                        cursor.getString(INDEX_COMMENTS));
                exercises.add(category);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "Empty table - " + DATABASE_TABLE);
        }
        return exercises;
    }
}
