package com.example.android.constantine.weather.Pojo;

import java.util.List;

public class WeatherResponse {
    private String cod;
    private double message;
    private City city;
    private int cnt;
    private List<WeatherList> weatherLists;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<WeatherList> getWeatherLists() {
        return weatherLists;
    }

    public void setWeatherLists(List<WeatherList> weatherLists) {
        this.weatherLists = weatherLists;
    }
}
