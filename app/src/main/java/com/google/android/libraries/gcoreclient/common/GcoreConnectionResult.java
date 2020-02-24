package com.google.android.libraries.gcoreclient.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;

public interface GcoreConnectionResult {
    public static final int API_UNAVAILABLE = 16;
    public static final int CANCELLED = 13;
    @Deprecated
    public static final int DATE_INVALID = 12;
    public static final int DEVELOPER_ERROR = 10;
    @Deprecated
    public static final int DRIVE_EXTERNAL_STORAGE_REQUIRED = 1500;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 15;
    public static final int INVALID_ACCOUNT = 5;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int RESTRICTED_PROFILE = 20;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_MISSING_PERMISSION = 19;
    public static final int SERVICE_UPDATING = 18;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_FAILED = 17;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int TIMEOUT = 14;

    int getErrorCode();

    PendingIntent getResolution();

    boolean hasResolution();

    boolean isSuccess();

    void startResolutionForResult(Activity activity, int i) throws IntentSender.SendIntentException;

    String toString();
}
