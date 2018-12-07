package com.example.android.sunshine;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.lang.ref.WeakReference;
import java.net.URL;

public class FetchWeatherLoader extends AsyncTaskLoader<String[]> {

    private WeakReference<ProgressBar> mLoadingIndicator;
    private String mLocation;

    private String[] mWeatherData;

    public FetchWeatherLoader(@NonNull Context context,
                              String location, ProgressBar loadingIndicator) {
        super(context);
        mLocation = location;
        mLoadingIndicator = new WeakReference<>(loadingIndicator);

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mLoadingIndicator != null && mLoadingIndicator.get() != null) {
            mLoadingIndicator.get().setVisibility(View.VISIBLE);
        }
        if (mWeatherData != null)
            deliverResult(mWeatherData);
        else
            forceLoad();
    }

    @Nullable
    @Override
    public String[] loadInBackground() {
        /* If there's no zip code, there's nothing to look up. */
        if (mLocation == null) {
            return null;
        }

        URL weatherRequestUrl = NetworkUtils.buildUrl(mLocation);

        try {
            String jsonWeatherResponse = NetworkUtils
                    .getResponseFromHttpUrl(weatherRequestUrl);

            return OpenWeatherJsonUtils
                    .getSimpleWeatherStringsFromJson
                            (mLoadingIndicator.get().getContext(), jsonWeatherResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(@Nullable String[] data) {
        mWeatherData = data;
        super.deliverResult(data);
    }
}