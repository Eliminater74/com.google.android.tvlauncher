package com.google.android.tvlauncher.appsview.data;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class StopOutOfBoxOrderingJobService extends JobService {
    public boolean onStartJob(JobParameters params) {
        LaunchItemsManagerProvider.getInstance(this).stopOutOfBoxOrdering();
        return false;
    }

    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
