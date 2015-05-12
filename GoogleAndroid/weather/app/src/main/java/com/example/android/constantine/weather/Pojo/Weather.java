package com.example.android.constantine.weather.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private double message;
    @SerializedName("city")
    private City city;
    @SerializedName("cnt")
    private int cnt;
    @SerializedName("list")
    private List<WeatherInfo> weatherInfoList;

    public List<WeatherInfo> getWeatherInfoList() {
        return weatherInfoList;
    }
}
