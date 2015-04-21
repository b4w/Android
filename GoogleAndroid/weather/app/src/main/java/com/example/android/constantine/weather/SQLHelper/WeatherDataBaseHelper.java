package com.example.android.constantine.weather.SQLHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.constantine.weather.Enums.WeatherDBUtilsEnum;
import com.example.android.constantine.weather.Enums.WeatherFieldsEnum;

/**
 * Created by KonstantinSysoev on 19.04.15.
 */
public class WeatherDataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = WeatherDataBaseHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            WeatherDBUtilsEnum.TABLE_NAME.getName() + "( " +
            WeatherDBUtilsEnum.UID.getName() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WeatherFieldsEnum.DATE.getName() + " VARCHAR(255), " +
            WeatherFieldsEnum.TEMPERATURE.getName() + " DOUBLE, " +
            WeatherFieldsEnum.HUMIDITY.getName() + " DOUBLE);";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
            WeatherDBUtilsEnum.TABLE_NAME.getName();

    public WeatherDataBaseHelper(Context context) {
        super(context, WeatherDBUtilsEnum.DATABASE_NAME.getName(), null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate() started");
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.i(TAG, "onCreated() done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrade DB in " + oldVersion + " to " + newVersion + " version");
        db.execSQL(SQL_CREATE_ENTRIES);
        onCreate(db);
    }
}
