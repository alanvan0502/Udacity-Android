package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private TextView mDayWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDayWeather = findViewById(R.id.day_weather_tv);
        // TODO (2) Display the weather forecast that was passed from MainActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.DAY_WEATHER)) {
            String dayWeather = intent.getStringExtra(MainActivity.DAY_WEATHER);
            mDayWeather.setText(dayWeather);
        }
    }
}