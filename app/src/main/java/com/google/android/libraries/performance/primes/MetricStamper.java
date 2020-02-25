package com.google.android.libraries.performance.primes;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;

import com.google.android.libraries.flashmanagement.storagestats.DataPartitionSize;
import com.google.android.libraries.performance.primes.metriccapture.ProcessStats;
import com.google.android.libraries.performance.primes.version.PrimesVersion;
import com.google.android.libraries.stitch.util.Preconditions;

import logs.proto.wireless.performance.mobile.SystemHealthProto;

public final class MetricStamper {
    private static final int BYTES_PER_KB = 1024;
    private static final String TAG = "MetricStamper";
    private final String applicationPackage;
    @Nullable
    private final Supplier<NoPiiString> componentNameSupplier;
    private final DataPartitionSize dataPartitionSize;
    private final SystemHealthProto.ApplicationInfo.HardwareVariant hardwareVariant;
    private final Long primesVersion;
    private final String shortProcessName;
    private final long totalDiskSizeKb;
    @Nullable
    private final String versionName;

    @VisibleForTesting
    MetricStamper(String appPackage, String shortProcessName2, @Nullable String versionName2, SystemHealthProto.ApplicationInfo.HardwareVariant hardwareVariant2, Long primesVersion2, DataPartitionSize dataPartitionSize2, @Nullable Supplier<NoPiiString> componentNameSupplier2) {
        this.applicationPackage = appPackage;
        this.shortProcessName = shortProcessName2;
        this.versionName = versionName2;
        this.hardwareVariant = hardwareVariant2;
        this.primesVersion = primesVersion2;
        this.dataPartitionSize = dataPartitionSize2;
        this.totalDiskSizeKb = dataPartitionSize2.getSizeInBytes() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        this.componentNameSupplier = componentNameSupplier2;
    }

    /* access modifiers changed from: private */
    public static MetricStamper createMetricStamper(Context applicationContext, @Nullable Supplier<NoPiiString> componentNameSupplier2) {
        String versionName2;
        SystemHealthProto.ApplicationInfo.HardwareVariant hardwareVariant2;
        String applicationPackage2 = ((Context) Preconditions.checkNotNull(applicationContext)).getPackageName();
        String shortProcessName2 = ProcessStats.getShortProcessName(applicationContext);
        SystemHealthProto.ApplicationInfo.HardwareVariant hardwareVariant3 = SystemHealthProto.ApplicationInfo.HardwareVariant.PHONE_OR_TABLET;
        PackageManager packageManager = applicationContext.getPackageManager();
        try {
            versionName2 = packageManager.getPackageInfo(applicationPackage2, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            PrimesLog.m54w(TAG, "Failed to get PackageInfo for: %s", applicationPackage2);
            versionName2 = null;
        }
        if (Build.VERSION.SDK_INT >= 20) {
            if (packageManager.hasSystemFeature("android.hardware.type.watch")) {
                hardwareVariant2 = SystemHealthProto.ApplicationInfo.HardwareVariant.WATCH;
            } else if (Build.VERSION.SDK_INT >= 21 && packageManager.hasSystemFeature("android.software.leanback")) {
                hardwareVariant2 = SystemHealthProto.ApplicationInfo.HardwareVariant.LEANBACK;
            }
            return new MetricStamper(applicationPackage2, shortProcessName2, versionName2, hardwareVariant2, PrimesVersion.getPrimesVersion(applicationContext), new DataPartitionSize(applicationContext), componentNameSupplier2);
        }
        hardwareVariant2 = hardwareVariant3;
        return new MetricStamper(applicationPackage2, shortProcessName2, versionName2, hardwareVariant2, PrimesVersion.getPrimesVersion(applicationContext), new DataPartitionSize(applicationContext), componentNameSupplier2);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Nullable
    @CheckResult
    public SystemHealthProto.SystemHealthMetric stamp(@Nullable SystemHealthProto.SystemHealthMetric metric) {
        if (metric == null) {
            PrimesLog.m54w(TAG, "Unexpected null metric to stamp, Stamping has been skipped.", new Object[0]);
            return null;
        }
        SystemHealthProto.SystemHealthMetric.Builder metricBuilder = (SystemHealthProto.SystemHealthMetric.Builder) metric.toBuilder();
        SystemHealthProto.ApplicationInfo.Builder applicationInfo = SystemHealthProto.ApplicationInfo.newBuilder().setHardwareVariant(this.hardwareVariant);
        String str = this.applicationPackage;
        if (str != null) {
            applicationInfo.setApplicationPackage(str);
        }
        Long l = this.primesVersion;
        if (l != null) {
            applicationInfo.setPrimesVersion(l.longValue());
        }
        String str2 = this.versionName;
        if (str2 != null) {
            applicationInfo.setApplicationVersionName(str2);
        }
        String str3 = this.shortProcessName;
        if (str3 != null) {
            applicationInfo.setShortProcessName(str3);
        }
        metricBuilder.setApplicationInfo(applicationInfo).setDeviceInfo(SystemHealthProto.DeviceInfo.newBuilder().setAvailableDiskSizeKb(this.dataPartitionSize.getFreeSpaceSizeInBytes() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID).setTotalDiskSizeKb(this.totalDiskSizeKb));
        String componentName = getComponentName();
        if (!TextUtils.isEmpty(componentName)) {
            SystemHealthProto.AccountableComponent.Builder accountableComponent = (SystemHealthProto.AccountableComponent.Builder) metric.getAccountableComponent().toBuilder();
            if (accountableComponent.getCustomName().isEmpty()) {
                accountableComponent.setCustomName(componentName);
            } else {
                accountableComponent.setCustomName(componentName + "::" + accountableComponent.getCustomName());
            }
            metricBuilder.setAccountableComponent(accountableComponent);
        }
        return (SystemHealthProto.SystemHealthMetric) metricBuilder.build();
    }

    @Nullable
    private String getComponentName() {
        Supplier<NoPiiString> supplier = this.componentNameSupplier;
        if (supplier == null) {
            return null;
        }
        return supplier.get().toString();
    }

    public String getApplicationPackage() {
        return this.applicationPackage;
    }

    @Nullable
    public String getVersionName() {
        return this.versionName;
    }

    public Long getPrimesVersion() {
        return this.primesVersion;
    }

    public static final class Builder {
        private volatile Context applicationContext;
        private volatile Supplier<NoPiiString> componentNameSupplier;

        private Builder() {
        }

        public Builder setApplication(Context applicationContext2) {
            this.applicationContext = applicationContext2;
            return this;
        }

        public Builder setComponentNameSupplier(Supplier<NoPiiString> componentNameSupplier2) {
            this.componentNameSupplier = componentNameSupplier2;
            return this;
        }

        public MetricStamper build() {
            return MetricStamper.createMetricStamper(this.applicationContext, this.componentNameSupplier);
        }
    }
}
