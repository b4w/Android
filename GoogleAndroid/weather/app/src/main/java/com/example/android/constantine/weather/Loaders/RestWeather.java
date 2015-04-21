package com.example.android.constantine.weather.Loaders;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.constantine.weather.Adapter.WeatherWeekAdapter;
import com.example.android.constantine.weather.Api.IWeather;
import com.example.android.constantine.weather.Pojo.Weather;
import com.example.android.constantine.weather.SQLHelper.WeatherDBUtils;

import retrofit.RestAdapter;

public class RestWeather {

    private Weather weather;
    private WeatherDBUtils weatherDBUtils;
    private Context context;
    private WeatherWeekAdapter adapter;

    public RestWeather(Context context) {
        this.context = context;
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
        protected void onPostExecute(Weather w) {
            weather = w;
            weatherDBUtils = new WeatherDBUtils(context);
            weatherDBUtils.addDataToWeatherDB(w);
            adapter = new WeatherWeekAdapter(context, weatherDBUtils.getAllItems(), true);
            adapter.callBackWeather();
        }
    }

    public Weather getWeather() {
        return weather;
    }

    public WeatherWeekAdapter getAdapter() {
        return adapter;
    }
}
