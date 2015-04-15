package com.example.android.constantine.weather.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.constantine.weather.Enums.WeatherFieldsEnum;
import com.example.android.constantine.weather.R;

/**
 * Created by KonstantinSysoev on 16.04.15.
 */
public class WeatherActivity extends Activity {

    private TextView list_item_date;
    private TextView list_item_temperature;
    private TextView list_item_humidity;
    private ImageView list_item_image_view;
    private Button shareButton;

    private double defaultValue = 0.0;
    private Double temperature;
    private Double humidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initFields();

        if (getIntent() != null) {
            list_item_date.setText(getIntent().getStringExtra(WeatherFieldsEnum.LIST_ITEM_DATE.name()));
            temperature = getIntent().getDoubleExtra(WeatherFieldsEnum.LIST_ITEM_TEMPERATURE.name(), defaultValue);
            list_item_temperature.setText(temperature.toString());
            humidity = getIntent().getDoubleExtra(WeatherFieldsEnum.LIST_ITEM_HUMIDITY.name(), defaultValue);
            list_item_humidity.setText(humidity.toString());
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // создаем неявный intent

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(WeatherFieldsEnum.LIST_ITEM_DATE.name(), list_item_date.getText().toString());
                intent.putExtra(WeatherFieldsEnum.LIST_ITEM_HUMIDITY.name(), list_item_humidity.getText().toString());
                intent.putExtra(WeatherFieldsEnum.LIST_ITEM_TEMPERATURE.name(), list_item_temperature.getText().toString());
                startActivity(Intent.createChooser(intent, "Share text"));
            }
        });
    }

    private void initFields() {
        list_item_date = (TextView) findViewById(R.id.activity_weather_date);
        list_item_temperature = (TextView) findViewById(R.id.activity_weather_temperature);
        list_item_humidity = (TextView) findViewById(R.id.activity_weather_humidity);
        list_item_image_view = (ImageView) findViewById(R.id.activity_weather_image_view);
        shareButton = (Button) findViewById(R.id.activity_weather_share_button);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        list_item_date.setText(savedInstanceState.getString(WeatherFieldsEnum.LIST_ITEM_DATE.name()));
        list_item_temperature.setText(savedInstanceState.getString(WeatherFieldsEnum.LIST_ITEM_TEMPERATURE.name()));
        list_item_humidity.setText(savedInstanceState.getString(WeatherFieldsEnum.LIST_ITEM_HUMIDITY.name()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(WeatherFieldsEnum.LIST_ITEM_DATE.name(), list_item_date.getText().toString());
        outState.putString(WeatherFieldsEnum.LIST_ITEM_HUMIDITY.name(), list_item_humidity.getText().toString());
        outState.putString(WeatherFieldsEnum.LIST_ITEM_TEMPERATURE.name(), list_item_temperature.getText().toString());
    }
}
