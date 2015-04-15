package com.example.android.constantine.weather.Loaders;

import android.os.AsyncTask;

import com.example.android.constantine.weather.Adapter.WeatherWeekAdapter;
import com.example.android.constantine.weather.Api.IWeather;
import com.example.android.constantine.weather.Pojo.Weather;

import retrofit.RestAdapter;

public class RestWeather {

    private WeatherWeekAdapter adapter;

    public RestWeather(WeatherWeekAdapter adapter) {
        this.adapter = adapter;
    }

    public void startAsyncWeather() {
        new WeatherAsyncTask().execute();
    }

    private class WeatherAsyncTask extends AsyncTask<Void, Void, Weather> {
        @Override
        protected Weather doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.openweathermap.org/data/2.5/forecast?q=Moscow,ru/")
                    .build();

            IWeather iWeather = restAdapter.create(IWeather.class);
            return iWeather.getWeather();
        }

        @Override
        protected void onPostExecute(Weather weather) {
            adapter.callBackWeather(weather);
        }
    }
}
