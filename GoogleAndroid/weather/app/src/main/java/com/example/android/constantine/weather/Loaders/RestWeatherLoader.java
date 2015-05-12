package com.example.android.constantine.weather.loaders;

import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.android.constantine.weather.api.IWeather;
import com.example.android.constantine.weather.data.OrmHelper;
import com.example.android.constantine.weather.data.dao.WeatherDAO;
import com.example.android.constantine.weather.data.dto.WeatherDTO;
import com.example.android.constantine.weather.pojo.Weather;
import com.example.android.constantine.weather.pojo.WeatherInfo;

import java.sql.SQLException;

import retrofit.RestAdapter;

public class RestWeatherLoader extends Loader<WeatherDTO> {
    private static final String TAG = RestWeatherLoader.class.getSimpleName();

    private OrmHelper ormHelper;
    private static WeatherDAO weatherDAO;
    private WeatherDTO weatherDTO;
    private Context context;
    private WeatherAsyncTask weatherAsyncTask;

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public RestWeatherLoader(Context context, Bundle args) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i(TAG, "onStartLoading()");
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.i(TAG, "onStopLoading()");
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Log.i(TAG, "onForceLoad() started");
        if (weatherAsyncTask != null)
            weatherAsyncTask.cancel(true);
        weatherAsyncTask = new WeatherAsyncTask();
        weatherAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Иниализация подключения к БД.
     */
    private void initDB() {
        Log.i(TAG, "initDB() started");
        ormHelper = new OrmHelper(context);
        ormHelper.clearDatabase();
        try {
            weatherDAO = new WeatherDAO(ormHelper.getConnectionSource(), WeatherDTO.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "initDB() done");
    }

    /**
     * Запись данных в БД.
     *
     * @param w
     */
    private void fillSampleData(Weather w) {
        Log.i(TAG, "fillSampleData() started");
        for (WeatherInfo weatherInfo : w.getWeatherInfoList()) {
            weatherDTO = new WeatherDTO(weatherInfo.getDt_txt(), weatherInfo.getTemp(), weatherInfo.getHumidity(), weatherInfo.getIcon());
            try {
                weatherDAO.create(weatherDTO);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "fillSampleData() done");
    }

    private void getResultFromTask(WeatherDTO result) {
        deliverResult(result);
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
            Log.i(TAG, "onPostExecute() started");
            initDB();
            fillSampleData(w);
            getResultFromTask(weatherDTO);
            Log.i(TAG, "onPostExecute() done");
        }
    }

    public static WeatherDAO getWeatherDAO() {
        return weatherDAO;
    }
}
