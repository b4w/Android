package com.example.android.constantine.weather.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.constantine.weather.R;
import com.example.android.constantine.weather.data.dto.WeatherDTO;
import com.j256.ormlite.android.apptools.OrmLiteCursorAdapter;

/**
 * Created by KonstantinSysoev on 25.04.15.
 */
public class WeatherAdapter extends OrmLiteCursorAdapter<WeatherDTO, View> {
    private final static String TAG = WeatherAdapter.class.getSimpleName();
    private final LayoutInflater inflater;
    private Typeface weatherFont;

    public WeatherAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/owfont-regular.ttf");
    }

    @Override
    public void bindView(View view, Context context, WeatherDTO weatherDTO) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.date.setText(weatherDTO.getDate());
        holder.temperature.setText(String.valueOf(weatherDTO.getTemperature()));
        holder.humidity.setText(String.valueOf(weatherDTO.getHumidity()));
        holder.icon.setTypeface(weatherFont);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        final View view = inflater.inflate(R.layout.list_item, parent, false);
        holder.date = (TextView) view.findViewById(R.id.list_item_date);
        holder.temperature = (TextView) view.findViewById(R.id.list_item_temperature);
        holder.humidity = (TextView) view.findViewById(R.id.list_item_humidity);
        holder.icon = (TextView) view.findViewById(R.id.list_item_icon);
        holder.icon.setTypeface(weatherFont);
        view.setTag(holder);
        return view;
    }

    static class ViewHolder {
        public TextView date;
        public TextView temperature;
        public TextView humidity;
        public TextView icon;
    }
}
