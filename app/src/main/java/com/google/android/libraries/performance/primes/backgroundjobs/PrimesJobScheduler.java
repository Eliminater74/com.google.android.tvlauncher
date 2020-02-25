package com.google.android.libraries.performance.primes.backgroundjobs;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.PrimesLog;

public final class PrimesJobScheduler {
    public static final String FILE_NAME_PARAM = "file_name";
    public static final String LOGGER_JOB_NAME = "com.google.android.libraries.performance.primes.backgroundjobs.logger.LoggerJob";
    public static final String LOG_SOURCE_PARAM = "log_source";
    @VisibleForTesting
    static final int UPLOAD_JOB_ID = 184188964;
    private static final String TAG = "PrimesJobScheduler";

    private PrimesJobScheduler() {
    }

    public static void scheduleFileUpload(Application application, String fileName, String logSource) {
        if (Build.VERSION.SDK_INT >= 21 && isJobEnabled(application, LOGGER_JOB_NAME)) {
            PersistableBundle params = new PersistableBundle();
            params.putString(FILE_NAME_PARAM, fileName);
            params.putString(LOG_SOURCE_PARAM, logSource);
            ((JobScheduler) application.getSystemService("jobscheduler")).schedule(new JobInfo.Builder(UPLOAD_JOB_ID, new ComponentName(application, LOGGER_JOB_NAME)).setRequiredNetworkType(2).setRequiresCharging(true).setRequiresDeviceIdle(true).setExtras(params).build());
        }
    }

    public static boolean isJobEnabled(Application application, String jobName) {
        try {
            ServiceInfo[] services = application.getPackageManager().getPackageInfo(application.getPackageName(), 4).services;
            if (services == null) {
                return false;
            }
            for (ServiceInfo service : services) {
                if (service.name.equals(jobName)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            PrimesLog.m45d(TAG, "Failed to find application package", e, new Object[0]);
        }
    }

    public static boolean isFileUploadScheduled(Application application) {
        JobScheduler scheduler = (JobScheduler) application.getSystemService("jobscheduler");
        if (Build.VERSION.SDK_INT < 24) {
            for (JobInfo job : scheduler.getAllPendingJobs()) {
                if (job.getId() == UPLOAD_JOB_ID) {
                    return true;
                }
            }
            return false;
        } else if (scheduler.getPendingJob(UPLOAD_JOB_ID) != null) {
            return true;
        } else {
            return false;
        }
    }
}
