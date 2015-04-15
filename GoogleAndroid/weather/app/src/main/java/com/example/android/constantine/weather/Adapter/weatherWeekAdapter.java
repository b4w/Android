package com.example.android.constantine.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.constantine.weather.Pojo.Weather;
import com.example.android.constantine.weather.Pojo.WeatherInfo;
import com.example.android.constantine.weather.R;

import retrofit.RestAdapter;

public class WeatherWeekAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private Weather weather;
    private RestAdapter restAdapter;

    public WeatherWeekAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (weather != null
                && weather.getWeatherInfoList() != null
                && !weather.getWeatherInfoList().isEmpty()) {
            return weather.getWeatherInfoList().size();
        }
        return 0;
    }

    @Override
    public WeatherInfo getItem(int position) {
        WeatherInfo weatherInfo = weather.getWeatherInfoList().get(position);
        return weatherInfo;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = createNewView(parent);
        }
        bindView(view, getItem(position));
        return view;
    }

    private View createNewView(ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    private void bindView(View view, WeatherInfo item) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.populate(item);
    }

    public void callBackWeather(Weather weather) {
        this.weather = weather;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        private TextView list_item_date;
        private TextView list_item_temperature;
        private TextView list_item_humidity;
        private ImageView imageView;

        public ViewHolder(View view) {
            list_item_date = (TextView) view.findViewById(R.id.list_item_date);
            list_item_temperature = (TextView) view.findViewById(R.id.list_item_temperature);
            list_item_humidity = (TextView) view.findViewById(R.id.list_item_humidity);
            imageView = (ImageView) view.findViewById(R.id.list_item_image_view);
        }

        private void populate(WeatherInfo weatherInfo) {
            list_item_date.setText(weatherInfo.getDt_txt());
            list_item_temperature.setText("Температура - " + weatherInfo.getTemp());
            list_item_humidity.setText("Влажность - " + weatherInfo.getHumidity());
        }
    }
}
