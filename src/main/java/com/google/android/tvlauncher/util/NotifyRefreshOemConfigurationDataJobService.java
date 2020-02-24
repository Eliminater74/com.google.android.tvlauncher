package com.google.android.tvlauncher.util;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;

public class NotifyRefreshOemConfigurationDataJobService extends JobService {
    public static final String REFRESH_OEM_CONFIGURATION_DATA = "refresh_oem_configuration_data";

    public static void schedule(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        if (scheduler != null) {
            scheduler.schedule(new JobInfo.Builder(2, new ComponentName(context, NotifyRefreshOemConfigurationDataJobService.class)).addTriggerContentUri(new JobInfo.TriggerContentUri(PartnerCustomizationContract.OEM_CONFIGURATION_URI, 1)).build());
        }
    }

    public boolean onStartJob(JobParameters params) {
        getSharedPreferences("oem_config", 0).edit().putBoolean(REFRESH_OEM_CONFIGURATION_DATA, true).apply();
        return false;
    }

    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
