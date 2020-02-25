package com.google.android.libraries.performance.primes.transmitter.impl;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

import com.google.android.libraries.performance.primes.Config;
import com.google.android.libraries.performance.primes.Hashing;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.protobuf.MessageLite;

import logs.proto.wireless.performance.mobile.BatteryMetric;
import logs.proto.wireless.performance.mobile.NetworkMetric;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

public abstract class HashedNamesTransmitter implements MetricTransmitter {
    @VisibleForTesting
    static final MetricNameAccess<BatteryMetric.BatteryStatsDiff.Builder> BATTERY_METRIC_NAME_ACCESS = new MetricNameAccess<BatteryMetric.BatteryStatsDiff.Builder>() {
        public String getConstantName(BatteryMetric.BatteryStatsDiff.Builder message) {
            return message.getStartConstantEventName();
        }

        public String getCustomName(BatteryMetric.BatteryStatsDiff.Builder message) {
            return message.getStartCustomEventName();
        }

        public void clearConstantName(BatteryMetric.BatteryStatsDiff.Builder message) {
            message.clearStartConstantEventName();
        }

        public void setCustomName(BatteryMetric.BatteryStatsDiff.Builder message, @Nullable String name) {
            if (name != null) {
                message.setStartCustomEventName(name);
            } else {
                message.clearStartCustomEventName();
            }
        }

        public void setHashedName(BatteryMetric.BatteryStatsDiff.Builder message, @Nullable Long hash) {
            if (hash != null) {
                message.setStartHashedCustomEventName(hash.longValue());
            } else {
                message.clearStartHashedCustomEventName();
            }
        }
    };
    @VisibleForTesting
    static final MetricNameAccess<SystemHealthProto.SystemHealthMetric.Builder> SHM_METRIC_NAME_ACCESS = new MetricNameAccess<SystemHealthProto.SystemHealthMetric.Builder>() {
        public String getConstantName(SystemHealthProto.SystemHealthMetric.Builder message) {
            return message.getConstantEventName();
        }

        public String getCustomName(SystemHealthProto.SystemHealthMetric.Builder message) {
            return message.getCustomEventName();
        }

        public void clearConstantName(SystemHealthProto.SystemHealthMetric.Builder message) {
            message.clearConstantEventName();
        }

        public void setCustomName(SystemHealthProto.SystemHealthMetric.Builder message, @Nullable String name) {
            if (name != null) {
                message.setCustomEventName(name);
            } else {
                message.clearCustomEventName();
            }
        }

        public void setHashedName(SystemHealthProto.SystemHealthMetric.Builder message, @Nullable Long hash) {
            if (hash != null) {
                message.setHashedCustomEventName(hash.longValue());
            } else {
                message.clearHashedCustomEventName();
            }
        }
    };
    @VisibleForTesting
    static final MetricNameAccess<PrimesTraceOuterClass.Span.Builder> SPAN_METRIC_NAME_ACCESS = new MetricNameAccess<PrimesTraceOuterClass.Span.Builder>() {
        public String getConstantName(PrimesTraceOuterClass.Span.Builder message) {
            return message.getConstantName();
        }

        public String getCustomName(PrimesTraceOuterClass.Span.Builder message) {
            return message.getName();
        }

        public void clearConstantName(PrimesTraceOuterClass.Span.Builder messageBuilder) {
            messageBuilder.clearConstantName();
        }

        public void setCustomName(PrimesTraceOuterClass.Span.Builder messageBuilder, @Nullable String name) {
            if (name == null) {
                messageBuilder.clearName();
            } else {
                messageBuilder.setName(name);
            }
        }

        public void setHashedName(PrimesTraceOuterClass.Span.Builder messageBuilder, @Nullable Long hash) {
            if (hash == null) {
                messageBuilder.clearHashedName();
            } else {
                messageBuilder.setHashedName(hash.longValue());
            }
        }
    };
    private static final String PATH_DELIMITER = "/+";
    private static final String TAG = "HashedNamesTransmitter";

    @VisibleForTesting
    static <T extends MessageLite.Builder> void ensureNoPiiName(MetricNameAccess<T> messageAccess, T message) {
        if (Config.isGerrit() && !TextUtils.isEmpty(messageAccess.getConstantName(message))) {
            PrimesLog.m54w(TAG, "Constant name exists in message", messageAccess.getConstantName(message));
            messageAccess.setCustomName(message, messageAccess.getConstantName(message));
            messageAccess.clearConstantName(message);
        }
        if (TextUtils.isEmpty(messageAccess.getConstantName(message))) {
            messageAccess.setHashedName(message, Hashing.hash(messageAccess.getCustomName(message)));
        } else {
            messageAccess.setHashedName(message, null);
        }
        messageAccess.setCustomName(message, null);
    }

    private static void convertTopLevelFields(SystemHealthProto.SystemHealthMetric.Builder message) {
        ensureNoPiiName(SHM_METRIC_NAME_ACCESS, message);
    }

    private static void convertMetricSpecificFields(SystemHealthProto.SystemHealthMetric.Builder message) {
        convertBatteryUsageMetricNames(message);
        convertPackageDirectoryNames(message);
        convertRpcPathNames(message);
        convertSpanNames(message);
    }

    private static void convertBatteryUsageMetricNames(SystemHealthProto.SystemHealthMetric.Builder metric) {
        if (metric.hasBatteryUsageMetric() && metric.getBatteryUsageMetric().hasBatteryStatsDiff()) {
            BatteryMetric.BatteryStatsDiff.Builder batteryStatsDiff = (BatteryMetric.BatteryStatsDiff.Builder) metric.getBatteryUsageMetric().getBatteryStatsDiff().toBuilder();
            ensureNoPiiName(BATTERY_METRIC_NAME_ACCESS, batteryStatsDiff);
            metric.setBatteryUsageMetric(((BatteryMetric.BatteryUsageMetric.Builder) metric.getBatteryUsageMetric().toBuilder()).setBatteryStatsDiff(batteryStatsDiff));
        }
    }

    private static String[] splitTokens(String slashSeparatedTokens) {
        return slashSeparatedTokens.replaceFirst("^/+", "").split(PATH_DELIMITER);
    }

    private static long[] hashTokens(String slashSeparatedTokens) {
        String[] tokens = splitTokens(slashSeparatedTokens);
        long[] hashedTokens = new long[tokens.length];
        for (int i = 0; i < hashedTokens.length; i++) {
            hashedTokens[i] = Hashing.hash(tokens[i]).longValue();
        }
        return hashedTokens;
    }

    private static void convertPackageDirectoryNames(SystemHealthProto.SystemHealthMetric.Builder metric) {
        if (metric.hasPackageMetric() && metric.getPackageMetric().getDirStatsCount() != 0) {
            SystemHealthProto.PackageMetric.Builder packageMetric = (SystemHealthProto.PackageMetric.Builder) metric.getPackageMetric().toBuilder();
            for (int i = 0; i < packageMetric.getDirStatsCount(); i++) {
                SystemHealthProto.PackageMetric.DirStats.Builder stats = (SystemHealthProto.PackageMetric.DirStats.Builder) packageMetric.getDirStats(i).toBuilder();
                if (!TextUtils.isEmpty(stats.getDirPath())) {
                    stats.clearHashedDirPath();
                    for (String token : splitTokens(stats.getDirPath())) {
                        stats.addHashedDirPath(Hashing.hash(token).longValue());
                    }
                }
                stats.clearDirPath();
                packageMetric.setDirStats(i, stats);
            }
            metric.setPackageMetric(packageMetric);
        }
    }

    private static void convertRpcPathNames(SystemHealthProto.SystemHealthMetric.Builder metric) {
        if (metric.hasNetworkUsageMetric() && metric.getNetworkUsageMetric().getNetworkEventUsageCount() != 0) {
            NetworkMetric.NetworkUsageMetric.Builder networkUsageMetric = (NetworkMetric.NetworkUsageMetric.Builder) metric.getNetworkUsageMetric().toBuilder();
            for (int i = 0; i < networkUsageMetric.getNetworkEventUsageCount(); i++) {
                NetworkMetric.NetworkEventUsage.Builder networkEventUsage = (NetworkMetric.NetworkEventUsage.Builder) networkUsageMetric.getNetworkEventUsage(i).toBuilder();
                if (!TextUtils.isEmpty(networkEventUsage.getRpcPath())) {
                    networkEventUsage.clearHashedRpcPath();
                    for (long hashedRpcPath : hashTokens(networkEventUsage.getRpcPath())) {
                        networkEventUsage.addHashedRpcPath(hashedRpcPath);
                    }
                }
                networkEventUsage.clearRpcPath();
                networkUsageMetric.setNetworkEventUsage(i, networkEventUsage);
            }
            metric.setNetworkUsageMetric(networkUsageMetric);
        }
    }

    private static void convertSpanNames(SystemHealthProto.SystemHealthMetric.Builder metric) {
        if (metric.hasPrimesTrace() && metric.getPrimesTrace().getSpansCount() != 0) {
            PrimesTraceOuterClass.PrimesTrace.Builder primesTrace = (PrimesTraceOuterClass.PrimesTrace.Builder) metric.getPrimesTrace().toBuilder();
            for (int i = 0; i < primesTrace.getSpansCount(); i++) {
                PrimesTraceOuterClass.Span.Builder span = (PrimesTraceOuterClass.Span.Builder) primesTrace.getSpans(i).toBuilder();
                ensureNoPiiName(SPAN_METRIC_NAME_ACCESS, span);
                primesTrace.setSpans(i, span);
            }
            metric.setPrimesTrace(primesTrace);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void sendHashedEvent(SystemHealthProto.SystemHealthMetric systemHealthMetric);

    public void send(SystemHealthProto.SystemHealthMetric message) {
        if (PrimesLog.vLoggable(TAG)) {
            PrimesLog.m52v(TAG, "unhashed: %s", message);
        }
        SystemHealthProto.SystemHealthMetric.Builder messageBuilder = (SystemHealthProto.SystemHealthMetric.Builder) message.toBuilder();
        convertTopLevelFields(messageBuilder);
        convertMetricSpecificFields(messageBuilder);
        sendHashedEvent((SystemHealthProto.SystemHealthMetric) messageBuilder.build());
    }

    @VisibleForTesting
    interface MetricNameAccess<T extends MessageLite.Builder> {
        void clearConstantName(T t);

        String getConstantName(T t);

        String getCustomName(T t);

        void setCustomName(T t, @Nullable String str);

        void setHashedName(T t, @Nullable Long l);
    }
}
