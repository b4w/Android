package com.example.android.constantine.weather.Api;

import com.example.android.constantine.weather.Pojo.Weather;
import com.example.android.constantine.weather.Pojo.WeatherInfo;

import retrofit.http.POST;

public interface IWeather {

    @POST("/")
    public Weather getWeather();

    @POST("/")
    public WeatherInfo getWeatherList();
}
