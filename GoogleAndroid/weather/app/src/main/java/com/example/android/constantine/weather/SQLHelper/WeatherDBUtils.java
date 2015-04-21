package com.example.android.constantine.weather.SQLHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.constantine.weather.Enums.WeatherDBUtilsEnum;
import com.example.android.constantine.weather.Enums.WeatherFieldsEnum;
import com.example.android.constantine.weather.Pojo.Weather;
import com.example.android.constantine.weather.Pojo.WeatherInfo;

/**
 * Created by KonstantinSysoev on 21.04.15.
 */
public class WeatherDBUtils {

    private SQLiteOpenHelper weatherDataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public WeatherDBUtils(Context context) {
        init(context);
    }

    public void init(Context context) {
        weatherDataBaseHelper = new WeatherDataBaseHelper(context);
        sqLiteDatabase = weatherDataBaseHelper.getWritableDatabase();
    }

    public void closeConnections() {
        if (weatherDataBaseHelper != null) {
            weatherDataBaseHelper.close();
        }
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
    }

    public void addDataToWeatherDB(Weather weather) {
        // добавление данных в БД
        // можно перенести в другой класс
        if (weatherDataBaseHelper.getDatabaseName() == null || weatherDataBaseHelper.getDatabaseName().isEmpty()) {
            weatherDataBaseHelper.onCreate(sqLiteDatabase);
        }

        String query;
        for (WeatherInfo weatherInfo : weather.getWeatherInfoList()) {

            query = "INSERT INTO " + WeatherDBUtilsEnum.TABLE_NAME.getName() +
                    " (" + WeatherFieldsEnum.DATE.getName() + ", " +
                    WeatherFieldsEnum.TEMPERATURE.getName() + ", " +
                    WeatherFieldsEnum.HUMIDITY.getName() + ") VALUES ('" +
                    weatherInfo.getDt_txt() + "', " + weatherInfo.getTemp() + ", " + weatherInfo.getHumidity() + ")";
            sqLiteDatabase.execSQL(query);
        }

        sqLiteDatabase.close();
        weatherDataBaseHelper.close();
    }

    public Cursor getAllItems() {
        sqLiteDatabase = weatherDataBaseHelper.getReadableDatabase();
        return sqLiteDatabase.query(WeatherDBUtilsEnum.TABLE_NAME.getName(), null, null, null, null, null, null);
    }

}
