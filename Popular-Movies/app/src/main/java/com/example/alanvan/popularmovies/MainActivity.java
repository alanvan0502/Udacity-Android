package com.example.alanvan.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.alanvan.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchMovieDataTask().execute(false);
    }

    public class FetchMovieDataTask extends AsyncTask<Boolean, Void, String> {

        @Override
        protected String doInBackground(Boolean... params) {
            Boolean byPopularity = params[0];
            URL movieRequestURL = NetworkUtils.buildUrl(byPopularity);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestURL);
                return jsonMovieResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }
}
