package com.example.android.constantine.weather.api;

import com.example.android.constantine.weather.pojo.Weather;
import com.example.android.constantine.weather.pojo.WeatherInfo;

import retrofit.http.POST;

public interface IWeather {

    @POST("/")
    public Weather getWeather();

    @POST("/")
    public WeatherInfo getWeatherList();
}
