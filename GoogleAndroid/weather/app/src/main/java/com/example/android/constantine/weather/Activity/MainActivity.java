package com.example.android.constantine.weather.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.android.constantine.weather.adapter.WeatherAdapter;
import com.example.android.constantine.weather.data.dto.WeatherDTO;
import com.example.android.constantine.weather.loaders.RestWeatherLoader;
import com.example.android.constantine.weather.R;
import com.facebook.stetho.Stetho;
import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;

public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<WeatherDTO> {

    private final static String TAG = MainActivity.class.getSimpleName();
    public static final int ORM_LOADER_ID = 4;
    public static final int REST_WEATHER_LOADER = 1;

    private ListView activityMainListView;
    private WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // библиотека для работы с БД в браузере
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

        setContentView(R.layout.activity_main);
        activityMainListView = (ListView) findViewById(R.id.activity_main_list_view);

        getLoaderManager().initLoader(REST_WEATHER_LOADER, null, this);

        weatherAdapter = new WeatherAdapter(this);
        ((AdapterView<ListAdapter>) activityMainListView).setAdapter(weatherAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Loader<WeatherDTO> loader = getLoaderManager().getLoader(REST_WEATHER_LOADER);
        if (loader != null)
            loader.forceLoad();
    }

    /**
     * Загружаем данные на экран
     */
    private void loadAdapter() {
        try {
            PreparedQuery query = RestWeatherLoader.getWeatherDAO().queryBuilder().prepare();
            OrmCursorLoaderCallback<WeatherDTO, Integer> ormCursorLoaderCallback =
                    new OrmCursorLoaderCallback<WeatherDTO, Integer>(this, RestWeatherLoader.getWeatherDAO(), query, weatherAdapter);
            getLoaderManager().initLoader(ORM_LOADER_ID, null, ormCursorLoaderCallback);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Loader<WeatherDTO> onCreateLoader(int id, Bundle args) {
        Loader<WeatherDTO> loader = null;
        if (REST_WEATHER_LOADER == id) {
            loader = new RestWeatherLoader(this, null);
            Log.i(TAG, "onCreateLoader() started");
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<WeatherDTO> loader, WeatherDTO data) {
        Log.i(TAG, "onLoadFinished() started");
        loadAdapter();
        Log.i(TAG, "onLoadFinished() done");
    }

    @Override
    public void onLoaderReset(Loader<WeatherDTO> loader) {
        Log.i(TAG, "onLoaderReset()");
    }
}
