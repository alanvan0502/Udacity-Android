package com.udacity.example.quizexample;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.udacity.example.droidtermsprovider.DroidTermsExampleContract;

import java.lang.ref.WeakReference;

public class ContentAsyncTask extends AsyncTask<Void, Void, Cursor> {
    private WeakReference<MainActivity> activity;

    ContentAsyncTask(MainActivity mainActivity, TextView wordTv, TextView definitionTv) {
        activity = new WeakReference<>(mainActivity);
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        ContentResolver contentResolver = activity.get().getContentResolver();
        return contentResolver.query(DroidTermsExampleContract.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        activity.get().updateCursorData(cursor);
    }

}