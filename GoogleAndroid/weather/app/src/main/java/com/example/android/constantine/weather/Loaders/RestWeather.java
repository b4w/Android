package com.example.android.constantine.weather.Loaders;

import android.os.AsyncTask;

import com.example.android.constantine.weather.Api.IWeather;
import com.example.android.constantine.weather.Pojo.Weather;
import com.example.android.constantine.weather.Pojo.WeatherList;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;

public class RestWeather {

    private Weather weather;
    private WeatherList weatherList;

    public void startAsyncWeather() {
        new WeatherAsyncTask().execute();
    }

    public void startAsyncWeatherList() {
        new WeatherListAsyncTask().execute();
    }

    private class WeatherAsyncTask extends AsyncTask<Void, Void, Weather> {
        @Override
        protected Weather doInBackground(Void... params) {
//            RestAdapter restAdapter = new RestAdapter.Builder()
//                    .setEndpoint("https://dl.dropboxusercontent.com/u/14710782/files/test.json")
//                    .build();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.openweathermap.org/data/2.5/forecast?q=Moscow,ru/")
                    .build();

            IWeather iWeather = restAdapter.create(IWeather.class);
            return iWeather.getWeather();
        }

        @Override
        protected void onPostExecute(Weather weather) {
            setWeather(weather);
        }
    }

    private class WeatherListAsyncTask extends AsyncTask<Void, Void, WeatherList> {
        @Override
        protected WeatherList doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.openweathermap.org/data/2.5/forecast?q=Moscow,ru/")
                    .build();

            IWeather iWeather = restAdapter.create(IWeather.class);
            return iWeather.getWeatherList();
        }

        @Override
        protected void onPostExecute(WeatherList weatherList) {

        }
    }

//  ---  GET & SET  ---

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather() {
        return weather;
    }

    public WeatherList getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(WeatherList weatherList) {
        this.weatherList = weatherList;
    }
}
