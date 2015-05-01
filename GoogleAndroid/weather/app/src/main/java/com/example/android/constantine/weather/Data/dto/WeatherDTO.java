package com.example.android.constantine.weather.data.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by KonstantinSysoev on 23.04.15.
 */
@DatabaseTable(tableName = "weather_orm")
public class WeatherDTO implements IIndexed {

    @DatabaseField(generatedId = true, columnName = COLUMN_NAME_ID)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME_DATE)
    private String date;

    @DatabaseField(columnName = COLUMN_NAME_TEMPERATURE)
    private double temperature;

    @DatabaseField(columnName = COLUMN_NAME_HUMIDITY)
    private double humidity;

    @DatabaseField(columnName = COLUMN_NAME_ICON)
    private String icon;

    public WeatherDTO() {
        // need for OrmLite
    }

    public WeatherDTO(int id, String date, double temperature, double humidity, String icon) {
        this.id = id;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.icon = icon;
    }

    public WeatherDTO(String date, double temperature, double humidity, String icon) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherDTO that = (WeatherDTO) o;

        if (humidity != that.humidity) return false;
        if (id != that.id) return false;
        if (temperature != that.temperature) return false;
        if (!date.equals(that.date)) return false;
        if (!icon.equals(that.icon)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
