package com.example.android.asynctaskloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.asynctaskloader.utilities.NetworkUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

// TODO 1: Create an AsyncTaskLoader Class
public class GithubQueryLoader extends AsyncTaskLoader<String> {

    private String mGithubSearchQuery;
    private WeakReference<ProgressBar> mLoadingIndicator;

    // TODO 4: Cache the result  in String mGithubJson to prevent queries just because the user navigated away from the app
    private String mGithubJson;

    public GithubQueryLoader(@NonNull Context context, String githubSearchQuery,
                             ProgressBar loadingIndicator) {
        super(context);
        mGithubSearchQuery = githubSearchQuery;
        mLoadingIndicator = new WeakReference<>(loadingIndicator);
    }

    // TODO 2: Force a load
    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (mGithubSearchQuery == null || mGithubSearchQuery.isEmpty())
            return;

        if (mLoadingIndicator != null && mLoadingIndicator.get() != null)
            mLoadingIndicator.get().setVisibility(View.VISIBLE);

        if (mGithubJson != null)
            deliverResult(mGithubJson);
        else
            forceLoad();
    }

    // TODO 3: Load in background and return data
    @Nullable
    @Override
    public String loadInBackground() {
        if (mGithubSearchQuery == null || mGithubSearchQuery.isEmpty()) {
            return null;
        }
        String githubSearchResults = null;
        try {
            URL searchUrl = NetworkUtils.buildUrl(mGithubSearchQuery);
            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return githubSearchResults;
    }

    @Override
    public void deliverResult(@Nullable String data) {
        mGithubJson = data;
        super.deliverResult(data);
    }
}
