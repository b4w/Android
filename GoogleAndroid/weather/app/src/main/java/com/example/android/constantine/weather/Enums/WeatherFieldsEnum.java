package com.example.android.constantine.weather.Enums;

/**
 * Created by KonstantinSysoev on 16.04.15.
 */
public enum WeatherFieldsEnum {

    DATE("date"),
    TEMPERATURE("temperature"),
    HUMIDITY("humidity");

    private String name;

    WeatherFieldsEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
