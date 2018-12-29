package com.example.android.background.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.lang.ref.WeakReference;

public class WaterReminderFirebaseJobService extends JobService {
    private AsyncTask mBackgroundTask;
    @Override
    public boolean onStartJob(JobParameters job) {
        mBackgroundTask = new WaterReminderAsyncTask(this, job);
        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask != null)
            mBackgroundTask.cancel(true);
        return true;
    }
}

class WaterReminderAsyncTask extends AsyncTask {
    private WeakReference<WaterReminderFirebaseJobService> mJobService;
    private WeakReference<JobParameters> mJob;

    WaterReminderAsyncTask(
            WaterReminderFirebaseJobService jobService,
            JobParameters job) {
        mJobService = new WeakReference<WaterReminderFirebaseJobService>(jobService);
        mJob = new WeakReference<JobParameters>(job);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        ReminderTasks.executeTask(mJobService.get(), ReminderTasks.ACTION_CHARGING_REMINDER);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        mJobService.get().jobFinished(mJob.get(), false);
    }
}
