package com.example.android.constantine.weather.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherInfo {

    @SerializedName("dt")
    private long dt;
    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("dt_txt")
    private String dt_txt;

    private class Sys {
        @SerializedName("pod")
        private String pod;
    }

    private class Wind {
        @SerializedName("speed")
        private double speed;
        @SerializedName("deg")
        private double deg;
    }

    private class Clouds {
        @SerializedName("all")
        private int all;
    }

    private class Weather {
        @SerializedName("id")
        private int id;
        @SerializedName("main")
        private String main;
        @SerializedName("description")
        private String description;
        @SerializedName("icon")
        private String icon;

        public String getIcon() {
            return icon;
        }
    }

    private class Main {
        @SerializedName("temp")
        private double temp;
        @SerializedName("temp_min")
        private double temp_min;
        @SerializedName("temp_max")
        private double temp_max;
        @SerializedName("pressure")
        private double pressure;
        @SerializedName("sea_level")
        private double sea_level;
        @SerializedName("grnd_level")
        private double grnd_level;
        @SerializedName("humidity")
        private double humidity;
        @SerializedName("temp_kf")
        private double temp_kf;

        public double getTemp() {
            return temp;
        }

        public double getHumidity() {
            return humidity;
        }
    }

    public double getTemp() {
        return main.getTemp();
    }

    public double getHumidity() {
        return main.getHumidity();
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public String getIcon() {
        return weather.get(0).getIcon();
    }
}
