package com.google.android.libraries.performance.primes.hprof;

import android.app.Application;
import android.app.admin.DevicePolicyManager;
import android.os.Build;

public final class PrimesHprofs {
    private PrimesHprofs() {
    }

    public static boolean isHeapDumpEligible(Application application) {
        return Build.VERSION.SDK_INT >= 23 && isDeviceEncrypted(application) && Build.FINGERPRINT.contains("userdebug");
    }

    private static boolean isDeviceEncrypted(Application application) {
        int encryptionStatus;
        DevicePolicyManager dpm = (DevicePolicyManager) application.getSystemService("device_policy");
        if (dpm == null) {
            encryptionStatus = 0;
        } else {
            encryptionStatus = dpm.getStorageEncryptionStatus();
        }
        if (encryptionStatus == 3 || encryptionStatus == 4 || encryptionStatus == 5) {
            return true;
        }
        return false;
    }
}
