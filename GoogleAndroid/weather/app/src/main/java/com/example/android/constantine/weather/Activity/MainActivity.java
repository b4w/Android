package com.example.android.constantine.weather.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.android.constantine.weather.Adapter.WeatherWeekAdapter;
import com.example.android.constantine.weather.Loaders.RestWeather;
import com.example.android.constantine.weather.Pojo.WeatherList;
import com.example.android.constantine.weather.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private ListView activityMainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lView = (ListView) findViewById(R.id.activity_main_list_view);
        RestWeather restWeather = new RestWeather();
//
        restWeather.startAsyncWeather();

        List<WeatherList> weatherList = new ArrayList<>();

        WeatherWeekAdapter weatherWeekAdapter = new WeatherWeekAdapter(this, restWeather.getWeather().getList());
        lView.setAdapter(weatherWeekAdapter);
    }


}
