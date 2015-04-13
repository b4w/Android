package com.example.android.constantine.weather.Pojo;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("coord")
    private Coord coord;
    @SerializedName("country")
    private String country;
    @SerializedName("population")
    private int population;

    public class Coord {
        @SerializedName("lon")
        private double lon;
        @SerializedName("lat")
        private double lat;
    }
}
