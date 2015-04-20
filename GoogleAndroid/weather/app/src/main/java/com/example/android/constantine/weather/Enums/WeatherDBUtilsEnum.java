package com.example.android.constantine.weather.Enums;

/**
 * Created by KonstantinSysoev on 19.04.15.
 */
public enum WeatherDBUtilsEnum {

    UID("_id"),
    TABLE_NAME("weather_table"),
    DATABASE_NAME ("weather_database.db");

    private String name;

    WeatherDBUtilsEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
