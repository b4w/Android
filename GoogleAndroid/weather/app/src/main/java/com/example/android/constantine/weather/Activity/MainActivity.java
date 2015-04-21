package com.example.android.constantine.weather.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.constantine.weather.Adapter.WeatherWeekAdapter;
import com.example.android.constantine.weather.Enums.WeatherFieldsEnum;
import com.example.android.constantine.weather.Loaders.RestWeather;
import com.example.android.constantine.weather.Pojo.WeatherInfo;
import com.example.android.constantine.weather.R;
import com.example.android.constantine.weather.SQLHelper.WeatherDataBaseHelper;


public class MainActivity extends ActionBarActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private ListView activityMainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lView = (ListView) findViewById(R.id.activity_main_list_view);

        RestWeather restWeather = new RestWeather(this);
        restWeather.startAsyncWeather();

        lView.setAdapter(restWeather.getAdapter());

//        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                WeatherInfo weatherInfo = adapter.getItem(position);
//                Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
//                intent.putExtra(WeatherFieldsEnum.DATE.getName(), weatherInfo.getDt_txt());
//                intent.putExtra(WeatherFieldsEnum.HUMIDITY.getName(), weatherInfo.getHumidity());
//                intent.putExtra(WeatherFieldsEnum.TEMPERATURE.getName(), weatherInfo.getTemp());
//                startActivity(intent);
//            }
//        });
    }
}
