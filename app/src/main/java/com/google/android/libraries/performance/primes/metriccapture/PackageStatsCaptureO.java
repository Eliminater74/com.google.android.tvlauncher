package com.google.android.libraries.performance.primes.metriccapture;

import android.annotation.TargetApi;
import android.app.usage.StorageStats;
import android.content.pm.PackageStats;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.annotation.Nullable;
import com.google.android.libraries.performance.primes.PrimesLog;
import java.util.UUID;

@TargetApi(26)
final class PackageStatsCaptureO {
    private static final String TAG = "PackageStatsO";

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0072, code lost:
        r4 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0074, code lost:
        r4 = e;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0072 A[ExcHandler: Error (e java.lang.Error), Splitter:B:5:0x001b] */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.content.pm.PackageStats getPackageStats(android.content.Context r13) {
        /*
            com.google.android.libraries.stitch.util.ThreadUtil.ensureBackgroundThread()
            java.lang.Class<android.os.storage.StorageManager> r0 = android.os.storage.StorageManager.class
            java.lang.Object r0 = r13.getSystemService(r0)
            android.os.storage.StorageManager r0 = (android.os.storage.StorageManager) r0
            r1 = 0
            r2 = 0
            java.lang.String r3 = "PackageStatsO"
            if (r0 != 0) goto L_0x0019
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r4 = "StorageManager is not available"
            com.google.android.libraries.performance.primes.PrimesLog.m48e(r3, r4, r2)
            return r1
        L_0x0019:
            java.lang.Class<android.app.usage.StorageStatsManager> r4 = android.app.usage.StorageStatsManager.class
            java.lang.Object r4 = r13.getSystemService(r4)     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            android.app.usage.StorageStatsManager r4 = (android.app.usage.StorageStatsManager) r4     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            java.lang.String r5 = r13.getPackageName()     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            android.content.pm.PackageStats r6 = new android.content.pm.PackageStats     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            r6.<init>(r5)     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            java.util.List r7 = r0.getStorageVolumes()     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
        L_0x0032:
            boolean r8 = r7.hasNext()     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            if (r8 == 0) goto L_0x0071
            java.lang.Object r8 = r7.next()     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            android.os.storage.StorageVolume r8 = (android.os.storage.StorageVolume) r8     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            java.lang.String r9 = r8.getState()     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            java.lang.String r10 = "mounted"
            boolean r9 = r9.equals(r10)     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            if (r9 == 0) goto L_0x0070
            java.util.UUID r9 = getUuid(r8)     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            if (r9 == 0) goto L_0x0070
            android.os.UserHandle r10 = android.os.Process.myUserHandle()     // Catch:{ NameNotFoundException -> 0x0067, IOException -> 0x0065, RuntimeException -> 0x0063, Error -> 0x0072 }
            android.app.usage.StorageStats r10 = r4.queryStatsForPackage(r9, r5, r10)     // Catch:{ NameNotFoundException -> 0x0067, IOException -> 0x0065, RuntimeException -> 0x0063, Error -> 0x0072 }
            java.util.UUID r11 = android.os.storage.StorageManager.UUID_DEFAULT     // Catch:{ NameNotFoundException -> 0x0067, IOException -> 0x0065, RuntimeException -> 0x0063, Error -> 0x0072 }
            boolean r11 = r11.equals(r9)     // Catch:{ NameNotFoundException -> 0x0067, IOException -> 0x0065, RuntimeException -> 0x0063, Error -> 0x0072 }
            addStorageToPackageStats(r6, r10, r11)     // Catch:{ NameNotFoundException -> 0x0067, IOException -> 0x0065, RuntimeException -> 0x0063, Error -> 0x0072 }
            goto L_0x0070
        L_0x0063:
            r10 = move-exception
            goto L_0x0068
        L_0x0065:
            r10 = move-exception
            goto L_0x0068
        L_0x0067:
            r10 = move-exception
        L_0x0068:
            java.lang.String r11 = "queryStatsForPackage() call failed"
            java.lang.Object[] r12 = new java.lang.Object[r2]     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
            com.google.android.libraries.performance.primes.PrimesLog.m47e(r3, r11, r10, r12)     // Catch:{ RuntimeException -> 0x0074, Error -> 0x0072 }
        L_0x0070:
            goto L_0x0032
        L_0x0071:
            return r6
        L_0x0072:
            r4 = move-exception
            goto L_0x0075
        L_0x0074:
            r4 = move-exception
        L_0x0075:
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r5 = "StorageStatsManager is not available"
            com.google.android.libraries.performance.primes.PrimesLog.m53w(r3, r5, r4, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.metriccapture.PackageStatsCaptureO.getPackageStats(android.content.Context):android.content.pm.PackageStats");
    }

    @Nullable
    private static UUID getUuid(StorageVolume storageVolume) {
        String uuidStr = storageVolume.getUuid();
        try {
            PrimesLog.m46d(TAG, "UUID for %s", uuidStr);
            return uuidStr == null ? StorageManager.UUID_DEFAULT : UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            PrimesLog.m48e(TAG, "Invalid UUID format: '%s'", uuidStr);
            return null;
        }
    }

    private static void addStorageToPackageStats(PackageStats packageStats, StorageStats storageStats, boolean internalStorage) {
        if (internalStorage) {
            packageStats.codeSize += storageStats.getAppBytes();
            packageStats.dataSize += storageStats.getDataBytes() - storageStats.getCacheBytes();
            packageStats.cacheSize += storageStats.getCacheBytes();
            return;
        }
        packageStats.externalCodeSize += storageStats.getAppBytes();
        packageStats.externalDataSize += storageStats.getDataBytes() - storageStats.getCacheBytes();
        packageStats.externalCacheSize += storageStats.getCacheBytes();
    }

    private PackageStatsCaptureO() {
    }
}
