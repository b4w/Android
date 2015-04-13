package com.example.android.constantine.weather.Adapter;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.constantine.weather.Loaders.RestWeather;
import com.example.android.constantine.weather.Pojo.Weather;
import com.example.android.constantine.weather.Pojo.WeatherList;
import com.example.android.constantine.weather.R;

import java.util.List;

import retrofit.RestAdapter;

public class WeatherWeekAdapter extends ArrayAdapter<WeatherList> {

    private Context context;
    private LayoutInflater inflater;
    private RestAdapter restAdapter;

    public WeatherWeekAdapter(Context context, List<WeatherList> objects) {
        super(context, R.layout.list_item, objects);
        inflater = LayoutInflater.from(context);
        restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org/data/2.5/forecast?q=Moscow,ru/")
                .build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WeatherList weatherList = getItem(position);
        viewHolder.populate(weatherList);
        return convertView;
    }

    static class ViewHolder {
        private TextView list_item_date;
        private TextView list_item_text_view;
        private ImageView imageView;

        ViewHolder(View view) {
            list_item_date = (TextView) view.findViewById(R.id.list_item_date);
            list_item_text_view = (TextView) view.findViewById(R.id.list_item_text_view);
            imageView = (ImageView) view.findViewById(R.id.list_item_image_view);
        }

        private void populate(WeatherList weatherList) {
            RestWeather restWeather = new RestWeather();
            restWeather.startAsyncWeather();
            weatherList = restWeather.getWeatherList();
            list_item_date.setText(weatherList.getDt_txt());
//            name.setText(Html.fromHtml(item.getContent()));
        }
    }
}
