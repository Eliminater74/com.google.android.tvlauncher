package logs.proto.wireless.performance.mobile;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtoField;
import com.google.protobuf.ProtoMessage;
import com.google.protobuf.ProtoPresenceBits;
import com.google.protobuf.ProtoPresenceCheckedField;
import com.google.protobuf.ProtoSyntax;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import logs.proto.wireless.performance.mobile.AggregatedMetricProto;
import logs.proto.wireless.performance.mobile.BatteryMetric;
import logs.proto.wireless.performance.mobile.CpuMetric;
import logs.proto.wireless.performance.mobile.CpuProfiling;
import logs.proto.wireless.performance.mobile.ErrorMessageOuterClass;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.MemoryMetric;
import logs.proto.wireless.performance.mobile.NetworkMetric;
import logs.proto.wireless.performance.mobile.PrimesHeapDumpProto;
import logs.proto.wireless.performance.mobile.PrimesScenarioProto;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;
import logs.proto.wireless.performance.mobile.ProcessProto;
import logs.proto.wireless.performance.mobile.TelemetryProto;
import logs.proto.wireless.performance.mobile.internal.PrimesForPrimesEventProto;

public final class SystemHealthProto {

    public interface AccountableComponentOrBuilder extends MessageLiteOrBuilder {
        String getCustomName();

        ByteString getCustomNameBytes();

        boolean hasCustomName();
    }

    public interface ApplicationInfoOrBuilder extends MessageLiteOrBuilder {
        String getApplicationPackage();

        ByteString getApplicationPackageBytes();

        String getApplicationVersionName();

        ByteString getApplicationVersionNameBytes();

        ApplicationInfo.HardwareVariant getHardwareVariant();

        long getPrimesVersion();

        String getShortProcessName();

        ByteString getShortProcessNameBytes();

        boolean hasApplicationPackage();

        boolean hasApplicationVersionName();

        boolean hasHardwareVariant();

        boolean hasPrimesVersion();

        boolean hasShortProcessName();
    }

    public interface ClientErrorLoggingMetricOrBuilder extends MessageLiteOrBuilder {
        String getActiveComponentName();

        ByteString getActiveComponentNameBytes();

        ErrorMessageOuterClass.ErrorMessage getErrorMessage();

        ProcessProto.ProcessStats getProcessStats();

        String getThreadName();

        ByteString getThreadNameBytes();

        boolean hasActiveComponentName();

        boolean hasErrorMessage();

        boolean hasProcessStats();

        boolean hasThreadName();
    }

    public interface CrashMetricOrBuilder extends MessageLiteOrBuilder {
        String getActiveComponentName();

        ByteString getActiveComponentNameBytes();

        String getCrashClassName();

        ByteString getCrashClassNameBytes();

        CrashMetric.CrashType getCrashType();

        boolean getHasCrashed();

        long getHashedStackTrace();

        ProcessProto.ProcessStats getProcessStats();

        String getThreadName();

        ByteString getThreadNameBytes();

        boolean hasActiveComponentName();

        boolean hasCrashClassName();

        boolean hasCrashType();

        boolean hasHasCrashed();

        boolean hasHashedStackTrace();

        boolean hasProcessStats();

        boolean hasThreadName();
    }

    public interface DeviceInfoOrBuilder extends MessageLiteOrBuilder {
        long getAvailableDiskSizeKb();

        long getTotalDiskSizeKb();

        boolean hasAvailableDiskSizeKb();

        boolean hasTotalDiskSizeKb();
    }

    public interface FrameRateMetricOrBuilder extends MessageLiteOrBuilder {
        @Deprecated
        float getAverageFramesPerSecond();

        @Deprecated
        int getDurationMs();

        @Deprecated
        int getMaxFrameRenderTimeMs();

        @Deprecated
        boolean hasAverageFramesPerSecond();

        @Deprecated
        boolean hasDurationMs();

        @Deprecated
        boolean hasMaxFrameRenderTimeMs();
    }

    public interface HistogramBucketOrBuilder extends MessageLiteOrBuilder {
        int getCount();

        int getMax();

        int getMin();

        boolean hasCount();

        boolean hasMax();

        boolean hasMin();
    }

    public interface JankMetricOrBuilder extends MessageLiteOrBuilder {
        int getDeviceRefreshRate();

        int getDurationMs();

        HistogramBucket getFrameTimeHistogram(int i);

        int getFrameTimeHistogramCount();

        List<HistogramBucket> getFrameTimeHistogramList();

        int getJankyFrameCount();

        int getMaxFrameRenderTimeMs();

        int getRenderedFrameCount();

        boolean hasDeviceRefreshRate();

        boolean hasDurationMs();

        boolean hasJankyFrameCount();

        boolean hasMaxFrameRenderTimeMs();

        boolean hasRenderedFrameCount();
    }

    public interface MagicEyeMetricOrBuilder extends MessageLiteOrBuilder {
        MagicEyeMetric.AppStateEvent getAppStateEvent();

        boolean hasAppStateEvent();
    }

    public interface MemoryLeakMetricOrBuilder extends MessageLiteOrBuilder {
        ObjectInfo getObjectInfo(int i);

        int getObjectInfoCount();

        List<ObjectInfo> getObjectInfoList();
    }

    public interface MicroCpuTimeOrBuilder extends MessageLiteOrBuilder {
        long getCpuMicros();

        long getWallMicros();

        boolean hasCpuMicros();

        boolean hasWallMicros();
    }

    public interface ObjectInfoOrBuilder extends MessageLiteOrBuilder {
        String getClassName();

        ByteString getClassNameBytes();

        String getLeakPath();

        ByteString getLeakPathBytes();

        int getLeakedCount();

        int getReleasedCount();

        int getRetainedHeapBytes();

        boolean hasClassName();

        boolean hasLeakPath();

        boolean hasLeakedCount();

        boolean hasReleasedCount();

        boolean hasRetainedHeapBytes();
    }

    public interface PackageMetricOrBuilder extends MessageLiteOrBuilder {
        long getCacheSize();

        long getCodeSize();

        @Deprecated
        long getDataPartitionSizeBytes();

        long getDataSize();

        PackageMetric.DirStats getDirStats(int i);

        int getDirStatsCount();

        List<PackageMetric.DirStats> getDirStatsList();

        long getExternalCacheSize();

        long getExternalCodeSize();

        long getExternalDataSize();

        long getExternalMediaSize();

        long getExternalObbSize();

        @Deprecated
        String getPackageName();

        @Deprecated
        ByteString getPackageNameBytes();

        boolean hasCacheSize();

        boolean hasCodeSize();

        @Deprecated
        boolean hasDataPartitionSizeBytes();

        boolean hasDataSize();

        boolean hasExternalCacheSize();

        boolean hasExternalCodeSize();

        boolean hasExternalDataSize();

        boolean hasExternalMediaSize();

        boolean hasExternalObbSize();

        @Deprecated
        boolean hasPackageName();
    }

    public interface PrimesForPrimesOrBuilder extends MessageLiteOrBuilder {
        PrimesForPrimes.InternalTimer getInternalTimers(int i);

        int getInternalTimersCount();

        List<PrimesForPrimes.InternalTimer> getInternalTimersList();
    }

    public interface PrimesHeapDumpCalibrationStatusOrBuilder extends MessageLiteOrBuilder {
        int getCurrentSampleCount();

        float getNewSamplePercentile();

        boolean hasCurrentSampleCount();

        boolean hasNewSamplePercentile();
    }

    public interface PrimesHeapDumpEventOrBuilder extends MessageLiteOrBuilder {
        PrimesHeapDumpEvent.PrimesHeapDumpError getError();

        int getSerializedSizeKb();

        int getTotalPssKbSamples(int i);

        int getTotalPssKbSamplesCount();

        List<Integer> getTotalPssKbSamplesList();

        boolean hasError();

        boolean hasSerializedSizeKb();
    }

    public interface PrimesStatsOrBuilder extends MessageLiteOrBuilder {
        int getEstimatedCount();

        PrimesStats.PrimesDebugMessage getPrimesDebugMessage();

        PrimesStats.PrimesEvent getPrimesEvent();

        boolean hasEstimatedCount();

        boolean hasPrimesDebugMessage();

        boolean hasPrimesEvent();
    }

    public interface StrictModeViolationMetricOrBuilder extends MessageLiteOrBuilder {
        StrictModeViolationMetric.ViolationType getViolationType();

        boolean hasViolationType();
    }

    public interface SystemHealthMetricOrBuilder extends MessageLiteOrBuilder {
        AccountableComponent getAccountableComponent();

        AggregatedMetricProto.AggregatedMetric getAggregatedMetrics(int i);

        int getAggregatedMetricsCount();

        List<AggregatedMetricProto.AggregatedMetric> getAggregatedMetricsList();

        ApplicationInfo getApplicationInfo();

        BatteryMetric.BatteryUsageMetric getBatteryUsageMetric();

        ClientErrorLoggingMetric getClientErrorLoggingMetrics();

        String getConstantEventName();

        ByteString getConstantEventNameBytes();

        CpuProfiling.CpuProfilingMetric getCpuProfilingMetric();

        @Deprecated
        CpuMetric.CpuUsageMetric getCpuUsageMetric();

        CrashMetric getCrashMetric();

        String getCustomEventName();

        ByteString getCustomEventNameBytes();

        DeviceInfo getDeviceInfo();

        @Deprecated
        FrameRateMetric getFrameRateMetric();

        long getHashedCustomEventName();

        JankMetric getJankMetric();

        MagicEyeMetric getMagicEyeMetric();

        MemoryLeakMetric getMemoryLeakMetric();

        MemoryMetric.MemoryUsageMetric getMemoryUsageMetric();

        ExtensionMetric.MetricExtension getMetricExtension();

        NetworkMetric.NetworkUsageMetric getNetworkUsageMetric();

        PackageMetric getPackageMetric();

        PrimesForPrimes getPrimesForPrimes();

        PrimesHeapDumpProto.PrimesHeapDump getPrimesHeapDump();

        PrimesScenarioProto.PrimesScenario getPrimesScenario();

        @Deprecated
        PrimesTraceOuterClass.PrimesSpans getPrimesSpans();

        PrimesStats getPrimesStats();

        PrimesTraceOuterClass.PrimesTrace getPrimesTrace();

        StrictModeViolationMetric getStrictModeViolation();

        @Deprecated
        TelemetryProto.TelemetryMetric getTelemetryMetrics(int i);

        @Deprecated
        int getTelemetryMetricsCount();

        @Deprecated
        List<TelemetryProto.TelemetryMetric> getTelemetryMetricsList();

        TimerMetric getTimerMetric();

        boolean hasAccountableComponent();

        boolean hasApplicationInfo();

        boolean hasBatteryUsageMetric();

        boolean hasClientErrorLoggingMetrics();

        boolean hasConstantEventName();

        boolean hasCpuProfilingMetric();

        @Deprecated
        boolean hasCpuUsageMetric();

        boolean hasCrashMetric();

        boolean hasCustomEventName();

        boolean hasDeviceInfo();

        @Deprecated
        boolean hasFrameRateMetric();

        boolean hasHashedCustomEventName();

        boolean hasJankMetric();

        boolean hasMagicEyeMetric();

        boolean hasMemoryLeakMetric();

        boolean hasMemoryUsageMetric();

        boolean hasMetricExtension();

        boolean hasNetworkUsageMetric();

        boolean hasPackageMetric();

        boolean hasPrimesForPrimes();

        boolean hasPrimesHeapDump();

        boolean hasPrimesScenario();

        @Deprecated
        boolean hasPrimesSpans();

        boolean hasPrimesStats();

        boolean hasPrimesTrace();

        boolean hasStrictModeViolation();

        boolean hasTimerMetric();
    }

    public interface TimerMetricOrBuilder extends MessageLiteOrBuilder {
        long getDurationMs();

        PrimesTraceOuterClass.EndStatus getEndStatus();

        boolean hasDurationMs();

        boolean hasEndStatus();
    }

    private SystemHealthProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {1}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SystemHealthMetric extends GeneratedMessageLite<SystemHealthMetric, Builder> implements SystemHealthMetricOrBuilder {
        public static final int ACCOUNTABLE_COMPONENT_FIELD_NUMBER = 21;
        public static final int AGGREGATED_METRICS_FIELD_NUMBER = 25;
        public static final int APPLICATION_INFO_FIELD_NUMBER = 5;
        public static final int BATTERY_USAGE_METRIC_FIELD_NUMBER = 10;
        public static final int CLIENT_ERROR_LOGGING_METRICS_FIELD_NUMBER = 29;
        public static final int CONSTANT_EVENT_NAME_FIELD_NUMBER = 17;
        public static final int CPU_PROFILING_METRIC_FIELD_NUMBER = 27;
        public static final int CPU_USAGE_METRIC_FIELD_NUMBER = 18;
        public static final int CRASH_METRIC_FIELD_NUMBER = 7;
        public static final int CUSTOM_EVENT_NAME_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final SystemHealthMetric DEFAULT_INSTANCE = new SystemHealthMetric();
        public static final int DEVICE_INFO_FIELD_NUMBER = 23;
        public static final int FRAME_RATE_METRIC_FIELD_NUMBER = 11;
        public static final int HASHED_CUSTOM_EVENT_NAME_FIELD_NUMBER = 2;
        public static final int JANK_METRIC_FIELD_NUMBER = 12;
        public static final int MAGIC_EYE_METRIC_FIELD_NUMBER = 15;
        public static final int MEMORY_LEAK_METRIC_FIELD_NUMBER = 13;
        public static final int MEMORY_USAGE_METRIC_FIELD_NUMBER = 1;
        public static final int METRIC_EXTENSION_FIELD_NUMBER = 14;
        public static final int NETWORK_USAGE_METRIC_FIELD_NUMBER = 6;
        public static final int PACKAGE_METRIC_FIELD_NUMBER = 9;
        private static volatile Parser<SystemHealthMetric> PARSER = null;
        public static final int PRIMES_FOR_PRIMES_FIELD_NUMBER = 24;
        public static final int PRIMES_HEAP_DUMP_FIELD_NUMBER = 22;
        public static final int PRIMES_SCENARIO_FIELD_NUMBER = 26;
        public static final int PRIMES_SPANS_FIELD_NUMBER = 19;
        public static final int PRIMES_STATS_FIELD_NUMBER = 8;
        public static final int PRIMES_TRACE_FIELD_NUMBER = 16;
        public static final int STRICT_MODE_VIOLATION_FIELD_NUMBER = 28;
        public static final int TELEMETRY_METRICS_FIELD_NUMBER = 20;
        public static final int TIMER_METRIC_FIELD_NUMBER = 4;
        @ProtoField(fieldNumber = 21, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1048576, presenceBitsId = 0)
        private AccountableComponent accountableComponent_;
        @ProtoField(fieldNumber = 25, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<AggregatedMetricProto.AggregatedMetric> aggregatedMetrics_ = emptyProtobufList();
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private ApplicationInfo applicationInfo_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private BatteryMetric.BatteryUsageMetric batteryUsageMetric_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 29, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 67108864, presenceBitsId = 0)
        private ClientErrorLoggingMetric clientErrorLoggingMetrics_;
        @ProtoField(fieldNumber = 17, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 65536, presenceBitsId = 0)
        private String constantEventName_ = "";
        @ProtoField(fieldNumber = 27, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 262144, presenceBitsId = 0)
        private CpuProfiling.CpuProfilingMetric cpuProfilingMetric_;
        @ProtoField(fieldNumber = 18, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 131072, presenceBitsId = 0)
        private CpuMetric.CpuUsageMetric cpuUsageMetric_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private CrashMetric crashMetric_;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String customEventName_ = "";
        @ProtoField(fieldNumber = 23, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4194304, presenceBitsId = 0)
        private DeviceInfo deviceInfo_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private FrameRateMetric frameRateMetric_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long hashedCustomEventName_;
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private JankMetric jankMetric_;
        @ProtoField(fieldNumber = 15, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16384, presenceBitsId = 0)
        private MagicEyeMetric magicEyeMetric_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 13, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 0)
        private MemoryLeakMetric memoryLeakMetric_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private MemoryMetric.MemoryUsageMetric memoryUsageMetric_;
        @ProtoField(fieldNumber = 14, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8192, presenceBitsId = 0)
        private ExtensionMetric.MetricExtension metricExtension_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private NetworkMetric.NetworkUsageMetric networkUsageMetric_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private PackageMetric packageMetric_;
        @ProtoField(fieldNumber = 24, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8388608, presenceBitsId = 0)
        private PrimesForPrimes primesForPrimes_;
        @ProtoField(fieldNumber = 22, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2097152, presenceBitsId = 0)
        private PrimesHeapDumpProto.PrimesHeapDump primesHeapDump_;
        @ProtoField(fieldNumber = 26, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16777216, presenceBitsId = 0)
        private PrimesScenarioProto.PrimesScenario primesScenario_;
        @ProtoField(fieldNumber = 19, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 524288, presenceBitsId = 0)
        private PrimesTraceOuterClass.PrimesSpans primesSpans_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private PrimesStats primesStats_;
        @ProtoField(fieldNumber = 16, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 32768, presenceBitsId = 0)
        private PrimesTraceOuterClass.PrimesTrace primesTrace_;
        @ProtoField(fieldNumber = 28, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 33554432, presenceBitsId = 0)
        private StrictModeViolationMetric strictModeViolation_;
        @ProtoField(fieldNumber = 20, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<TelemetryProto.TelemetryMetric> telemetryMetrics_ = emptyProtobufList();
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private TimerMetric timerMetric_;

        private SystemHealthMetric() {
        }

        public boolean hasMemoryUsageMetric() {
            return (this.bitField0_ & 1) != 0;
        }

        public MemoryMetric.MemoryUsageMetric getMemoryUsageMetric() {
            MemoryMetric.MemoryUsageMetric memoryUsageMetric = this.memoryUsageMetric_;
            return memoryUsageMetric == null ? MemoryMetric.MemoryUsageMetric.getDefaultInstance() : memoryUsageMetric;
        }

        /* access modifiers changed from: private */
        public void setMemoryUsageMetric(MemoryMetric.MemoryUsageMetric value) {
            if (value != null) {
                this.memoryUsageMetric_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMemoryUsageMetric(MemoryMetric.MemoryUsageMetric.Builder builderForValue) {
            this.memoryUsageMetric_ = (MemoryMetric.MemoryUsageMetric) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeMemoryUsageMetric(MemoryMetric.MemoryUsageMetric value) {
            if (value != null) {
                MemoryMetric.MemoryUsageMetric memoryUsageMetric = this.memoryUsageMetric_;
                if (memoryUsageMetric == null || memoryUsageMetric == MemoryMetric.MemoryUsageMetric.getDefaultInstance()) {
                    this.memoryUsageMetric_ = value;
                } else {
                    this.memoryUsageMetric_ = (MemoryMetric.MemoryUsageMetric) ((MemoryMetric.MemoryUsageMetric.Builder) MemoryMetric.MemoryUsageMetric.newBuilder(this.memoryUsageMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMemoryUsageMetric() {
            this.memoryUsageMetric_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasHashedCustomEventName() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getHashedCustomEventName() {
            return this.hashedCustomEventName_;
        }

        /* access modifiers changed from: private */
        public void setHashedCustomEventName(long value) {
            this.bitField0_ |= 2;
            this.hashedCustomEventName_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHashedCustomEventName() {
            this.bitField0_ &= -3;
            this.hashedCustomEventName_ = 0;
        }

        public boolean hasCustomEventName() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getCustomEventName() {
            return this.customEventName_;
        }

        public ByteString getCustomEventNameBytes() {
            return ByteString.copyFromUtf8(this.customEventName_);
        }

        /* access modifiers changed from: private */
        public void setCustomEventName(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.customEventName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCustomEventName() {
            this.bitField0_ &= -5;
            this.customEventName_ = getDefaultInstance().getCustomEventName();
        }

        /* access modifiers changed from: private */
        public void setCustomEventNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.customEventName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasTimerMetric() {
            return (this.bitField0_ & 8) != 0;
        }

        public TimerMetric getTimerMetric() {
            TimerMetric timerMetric = this.timerMetric_;
            return timerMetric == null ? TimerMetric.getDefaultInstance() : timerMetric;
        }

        /* access modifiers changed from: private */
        public void setTimerMetric(TimerMetric value) {
            if (value != null) {
                this.timerMetric_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setTimerMetric(TimerMetric.Builder builderForValue) {
            this.timerMetric_ = (TimerMetric) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeTimerMetric(TimerMetric value) {
            if (value != null) {
                TimerMetric timerMetric = this.timerMetric_;
                if (timerMetric == null || timerMetric == TimerMetric.getDefaultInstance()) {
                    this.timerMetric_ = value;
                } else {
                    this.timerMetric_ = (TimerMetric) ((TimerMetric.Builder) TimerMetric.newBuilder(this.timerMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTimerMetric() {
            this.timerMetric_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasApplicationInfo() {
            return (this.bitField0_ & 16) != 0;
        }

        public ApplicationInfo getApplicationInfo() {
            ApplicationInfo applicationInfo = this.applicationInfo_;
            return applicationInfo == null ? ApplicationInfo.getDefaultInstance() : applicationInfo;
        }

        /* access modifiers changed from: private */
        public void setApplicationInfo(ApplicationInfo value) {
            if (value != null) {
                this.applicationInfo_ = value;
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setApplicationInfo(ApplicationInfo.Builder builderForValue) {
            this.applicationInfo_ = (ApplicationInfo) builderForValue.build();
            this.bitField0_ |= 16;
        }

        /* access modifiers changed from: private */
        public void mergeApplicationInfo(ApplicationInfo value) {
            if (value != null) {
                ApplicationInfo applicationInfo = this.applicationInfo_;
                if (applicationInfo == null || applicationInfo == ApplicationInfo.getDefaultInstance()) {
                    this.applicationInfo_ = value;
                } else {
                    this.applicationInfo_ = (ApplicationInfo) ((ApplicationInfo.Builder) ApplicationInfo.newBuilder(this.applicationInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearApplicationInfo() {
            this.applicationInfo_ = null;
            this.bitField0_ &= -17;
        }

        public boolean hasNetworkUsageMetric() {
            return (this.bitField0_ & 32) != 0;
        }

        public NetworkMetric.NetworkUsageMetric getNetworkUsageMetric() {
            NetworkMetric.NetworkUsageMetric networkUsageMetric = this.networkUsageMetric_;
            return networkUsageMetric == null ? NetworkMetric.NetworkUsageMetric.getDefaultInstance() : networkUsageMetric;
        }

        /* access modifiers changed from: private */
        public void setNetworkUsageMetric(NetworkMetric.NetworkUsageMetric value) {
            if (value != null) {
                this.networkUsageMetric_ = value;
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setNetworkUsageMetric(NetworkMetric.NetworkUsageMetric.Builder builderForValue) {
            this.networkUsageMetric_ = (NetworkMetric.NetworkUsageMetric) builderForValue.build();
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void mergeNetworkUsageMetric(NetworkMetric.NetworkUsageMetric value) {
            if (value != null) {
                NetworkMetric.NetworkUsageMetric networkUsageMetric = this.networkUsageMetric_;
                if (networkUsageMetric == null || networkUsageMetric == NetworkMetric.NetworkUsageMetric.getDefaultInstance()) {
                    this.networkUsageMetric_ = value;
                } else {
                    this.networkUsageMetric_ = (NetworkMetric.NetworkUsageMetric) ((NetworkMetric.NetworkUsageMetric.Builder) NetworkMetric.NetworkUsageMetric.newBuilder(this.networkUsageMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearNetworkUsageMetric() {
            this.networkUsageMetric_ = null;
            this.bitField0_ &= -33;
        }

        public boolean hasCrashMetric() {
            return (this.bitField0_ & 64) != 0;
        }

        public CrashMetric getCrashMetric() {
            CrashMetric crashMetric = this.crashMetric_;
            return crashMetric == null ? CrashMetric.getDefaultInstance() : crashMetric;
        }

        /* access modifiers changed from: private */
        public void setCrashMetric(CrashMetric value) {
            if (value != null) {
                this.crashMetric_ = value;
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCrashMetric(CrashMetric.Builder builderForValue) {
            this.crashMetric_ = (CrashMetric) builderForValue.build();
            this.bitField0_ |= 64;
        }

        /* access modifiers changed from: private */
        public void mergeCrashMetric(CrashMetric value) {
            if (value != null) {
                CrashMetric crashMetric = this.crashMetric_;
                if (crashMetric == null || crashMetric == CrashMetric.getDefaultInstance()) {
                    this.crashMetric_ = value;
                } else {
                    this.crashMetric_ = (CrashMetric) ((CrashMetric.Builder) CrashMetric.newBuilder(this.crashMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCrashMetric() {
            this.crashMetric_ = null;
            this.bitField0_ &= -65;
        }

        public boolean hasPrimesStats() {
            return (this.bitField0_ & 128) != 0;
        }

        public PrimesStats getPrimesStats() {
            PrimesStats primesStats = this.primesStats_;
            return primesStats == null ? PrimesStats.getDefaultInstance() : primesStats;
        }

        /* access modifiers changed from: private */
        public void setPrimesStats(PrimesStats value) {
            if (value != null) {
                this.primesStats_ = value;
                this.bitField0_ |= 128;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrimesStats(PrimesStats.Builder builderForValue) {
            this.primesStats_ = (PrimesStats) builderForValue.build();
            this.bitField0_ |= 128;
        }

        /* access modifiers changed from: private */
        public void mergePrimesStats(PrimesStats value) {
            if (value != null) {
                PrimesStats primesStats = this.primesStats_;
                if (primesStats == null || primesStats == PrimesStats.getDefaultInstance()) {
                    this.primesStats_ = value;
                } else {
                    this.primesStats_ = (PrimesStats) ((PrimesStats.Builder) PrimesStats.newBuilder(this.primesStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 128;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPrimesStats() {
            this.primesStats_ = null;
            this.bitField0_ &= -129;
        }

        public boolean hasPackageMetric() {
            return (this.bitField0_ & 256) != 0;
        }

        public PackageMetric getPackageMetric() {
            PackageMetric packageMetric = this.packageMetric_;
            return packageMetric == null ? PackageMetric.getDefaultInstance() : packageMetric;
        }

        /* access modifiers changed from: private */
        public void setPackageMetric(PackageMetric value) {
            if (value != null) {
                this.packageMetric_ = value;
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPackageMetric(PackageMetric.Builder builderForValue) {
            this.packageMetric_ = (PackageMetric) builderForValue.build();
            this.bitField0_ |= 256;
        }

        /* access modifiers changed from: private */
        public void mergePackageMetric(PackageMetric value) {
            if (value != null) {
                PackageMetric packageMetric = this.packageMetric_;
                if (packageMetric == null || packageMetric == PackageMetric.getDefaultInstance()) {
                    this.packageMetric_ = value;
                } else {
                    this.packageMetric_ = (PackageMetric) ((PackageMetric.Builder) PackageMetric.newBuilder(this.packageMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageMetric() {
            this.packageMetric_ = null;
            this.bitField0_ &= -257;
        }

        public boolean hasBatteryUsageMetric() {
            return (this.bitField0_ & 512) != 0;
        }

        public BatteryMetric.BatteryUsageMetric getBatteryUsageMetric() {
            BatteryMetric.BatteryUsageMetric batteryUsageMetric = this.batteryUsageMetric_;
            return batteryUsageMetric == null ? BatteryMetric.BatteryUsageMetric.getDefaultInstance() : batteryUsageMetric;
        }

        /* access modifiers changed from: private */
        public void setBatteryUsageMetric(BatteryMetric.BatteryUsageMetric value) {
            if (value != null) {
                this.batteryUsageMetric_ = value;
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setBatteryUsageMetric(BatteryMetric.BatteryUsageMetric.Builder builderForValue) {
            this.batteryUsageMetric_ = (BatteryMetric.BatteryUsageMetric) builderForValue.build();
            this.bitField0_ |= 512;
        }

        /* access modifiers changed from: private */
        public void mergeBatteryUsageMetric(BatteryMetric.BatteryUsageMetric value) {
            if (value != null) {
                BatteryMetric.BatteryUsageMetric batteryUsageMetric = this.batteryUsageMetric_;
                if (batteryUsageMetric == null || batteryUsageMetric == BatteryMetric.BatteryUsageMetric.getDefaultInstance()) {
                    this.batteryUsageMetric_ = value;
                } else {
                    this.batteryUsageMetric_ = (BatteryMetric.BatteryUsageMetric) ((BatteryMetric.BatteryUsageMetric.Builder) BatteryMetric.BatteryUsageMetric.newBuilder(this.batteryUsageMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearBatteryUsageMetric() {
            this.batteryUsageMetric_ = null;
            this.bitField0_ &= -513;
        }

        @Deprecated
        public boolean hasFrameRateMetric() {
            return (this.bitField0_ & 1024) != 0;
        }

        @Deprecated
        public FrameRateMetric getFrameRateMetric() {
            FrameRateMetric frameRateMetric = this.frameRateMetric_;
            return frameRateMetric == null ? FrameRateMetric.getDefaultInstance() : frameRateMetric;
        }

        /* access modifiers changed from: private */
        public void setFrameRateMetric(FrameRateMetric value) {
            if (value != null) {
                this.frameRateMetric_ = value;
                this.bitField0_ |= 1024;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setFrameRateMetric(FrameRateMetric.Builder builderForValue) {
            this.frameRateMetric_ = (FrameRateMetric) builderForValue.build();
            this.bitField0_ |= 1024;
        }

        /* access modifiers changed from: private */
        public void mergeFrameRateMetric(FrameRateMetric value) {
            if (value != null) {
                FrameRateMetric frameRateMetric = this.frameRateMetric_;
                if (frameRateMetric == null || frameRateMetric == FrameRateMetric.getDefaultInstance()) {
                    this.frameRateMetric_ = value;
                } else {
                    this.frameRateMetric_ = (FrameRateMetric) ((FrameRateMetric.Builder) FrameRateMetric.newBuilder(this.frameRateMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1024;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearFrameRateMetric() {
            this.frameRateMetric_ = null;
            this.bitField0_ &= -1025;
        }

        public boolean hasJankMetric() {
            return (this.bitField0_ & 2048) != 0;
        }

        public JankMetric getJankMetric() {
            JankMetric jankMetric = this.jankMetric_;
            return jankMetric == null ? JankMetric.getDefaultInstance() : jankMetric;
        }

        /* access modifiers changed from: private */
        public void setJankMetric(JankMetric value) {
            if (value != null) {
                this.jankMetric_ = value;
                this.bitField0_ |= 2048;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setJankMetric(JankMetric.Builder builderForValue) {
            this.jankMetric_ = (JankMetric) builderForValue.build();
            this.bitField0_ |= 2048;
        }

        /* access modifiers changed from: private */
        public void mergeJankMetric(JankMetric value) {
            if (value != null) {
                JankMetric jankMetric = this.jankMetric_;
                if (jankMetric == null || jankMetric == JankMetric.getDefaultInstance()) {
                    this.jankMetric_ = value;
                } else {
                    this.jankMetric_ = (JankMetric) ((JankMetric.Builder) JankMetric.newBuilder(this.jankMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2048;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearJankMetric() {
            this.jankMetric_ = null;
            this.bitField0_ &= -2049;
        }

        public boolean hasMemoryLeakMetric() {
            return (this.bitField0_ & 4096) != 0;
        }

        public MemoryLeakMetric getMemoryLeakMetric() {
            MemoryLeakMetric memoryLeakMetric = this.memoryLeakMetric_;
            return memoryLeakMetric == null ? MemoryLeakMetric.getDefaultInstance() : memoryLeakMetric;
        }

        /* access modifiers changed from: private */
        public void setMemoryLeakMetric(MemoryLeakMetric value) {
            if (value != null) {
                this.memoryLeakMetric_ = value;
                this.bitField0_ |= 4096;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMemoryLeakMetric(MemoryLeakMetric.Builder builderForValue) {
            this.memoryLeakMetric_ = (MemoryLeakMetric) builderForValue.build();
            this.bitField0_ |= 4096;
        }

        /* access modifiers changed from: private */
        public void mergeMemoryLeakMetric(MemoryLeakMetric value) {
            if (value != null) {
                MemoryLeakMetric memoryLeakMetric = this.memoryLeakMetric_;
                if (memoryLeakMetric == null || memoryLeakMetric == MemoryLeakMetric.getDefaultInstance()) {
                    this.memoryLeakMetric_ = value;
                } else {
                    this.memoryLeakMetric_ = (MemoryLeakMetric) ((MemoryLeakMetric.Builder) MemoryLeakMetric.newBuilder(this.memoryLeakMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4096;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMemoryLeakMetric() {
            this.memoryLeakMetric_ = null;
            this.bitField0_ &= -4097;
        }

        public boolean hasMetricExtension() {
            return (this.bitField0_ & 8192) != 0;
        }

        public ExtensionMetric.MetricExtension getMetricExtension() {
            ExtensionMetric.MetricExtension metricExtension = this.metricExtension_;
            return metricExtension == null ? ExtensionMetric.MetricExtension.getDefaultInstance() : metricExtension;
        }

        /* access modifiers changed from: private */
        public void setMetricExtension(ExtensionMetric.MetricExtension value) {
            if (value != null) {
                this.metricExtension_ = value;
                this.bitField0_ |= 8192;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
            this.metricExtension_ = (ExtensionMetric.MetricExtension) builderForValue.build();
            this.bitField0_ |= 8192;
        }

        /* access modifiers changed from: private */
        public void mergeMetricExtension(ExtensionMetric.MetricExtension value) {
            if (value != null) {
                ExtensionMetric.MetricExtension metricExtension = this.metricExtension_;
                if (metricExtension == null || metricExtension == ExtensionMetric.MetricExtension.getDefaultInstance()) {
                    this.metricExtension_ = value;
                } else {
                    this.metricExtension_ = (ExtensionMetric.MetricExtension) ((ExtensionMetric.MetricExtension.Builder) ExtensionMetric.MetricExtension.newBuilder(this.metricExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8192;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMetricExtension() {
            this.metricExtension_ = null;
            this.bitField0_ &= -8193;
        }

        public boolean hasMagicEyeMetric() {
            return (this.bitField0_ & 16384) != 0;
        }

        public MagicEyeMetric getMagicEyeMetric() {
            MagicEyeMetric magicEyeMetric = this.magicEyeMetric_;
            return magicEyeMetric == null ? MagicEyeMetric.getDefaultInstance() : magicEyeMetric;
        }

        /* access modifiers changed from: private */
        public void setMagicEyeMetric(MagicEyeMetric value) {
            if (value != null) {
                this.magicEyeMetric_ = value;
                this.bitField0_ |= 16384;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMagicEyeMetric(MagicEyeMetric.Builder builderForValue) {
            this.magicEyeMetric_ = (MagicEyeMetric) builderForValue.build();
            this.bitField0_ |= 16384;
        }

        /* access modifiers changed from: private */
        public void mergeMagicEyeMetric(MagicEyeMetric value) {
            if (value != null) {
                MagicEyeMetric magicEyeMetric = this.magicEyeMetric_;
                if (magicEyeMetric == null || magicEyeMetric == MagicEyeMetric.getDefaultInstance()) {
                    this.magicEyeMetric_ = value;
                } else {
                    this.magicEyeMetric_ = (MagicEyeMetric) ((MagicEyeMetric.Builder) MagicEyeMetric.newBuilder(this.magicEyeMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16384;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMagicEyeMetric() {
            this.magicEyeMetric_ = null;
            this.bitField0_ &= -16385;
        }

        public boolean hasPrimesTrace() {
            return (this.bitField0_ & 32768) != 0;
        }

        public PrimesTraceOuterClass.PrimesTrace getPrimesTrace() {
            PrimesTraceOuterClass.PrimesTrace primesTrace = this.primesTrace_;
            return primesTrace == null ? PrimesTraceOuterClass.PrimesTrace.getDefaultInstance() : primesTrace;
        }

        /* access modifiers changed from: private */
        public void setPrimesTrace(PrimesTraceOuterClass.PrimesTrace value) {
            if (value != null) {
                this.primesTrace_ = value;
                this.bitField0_ |= 32768;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrimesTrace(PrimesTraceOuterClass.PrimesTrace.Builder builderForValue) {
            this.primesTrace_ = (PrimesTraceOuterClass.PrimesTrace) builderForValue.build();
            this.bitField0_ |= 32768;
        }

        /* access modifiers changed from: private */
        public void mergePrimesTrace(PrimesTraceOuterClass.PrimesTrace value) {
            if (value != null) {
                PrimesTraceOuterClass.PrimesTrace primesTrace = this.primesTrace_;
                if (primesTrace == null || primesTrace == PrimesTraceOuterClass.PrimesTrace.getDefaultInstance()) {
                    this.primesTrace_ = value;
                } else {
                    this.primesTrace_ = (PrimesTraceOuterClass.PrimesTrace) ((PrimesTraceOuterClass.PrimesTrace.Builder) PrimesTraceOuterClass.PrimesTrace.newBuilder(this.primesTrace_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 32768;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPrimesTrace() {
            this.primesTrace_ = null;
            this.bitField0_ &= -32769;
        }

        public boolean hasConstantEventName() {
            return (this.bitField0_ & 65536) != 0;
        }

        public String getConstantEventName() {
            return this.constantEventName_;
        }

        public ByteString getConstantEventNameBytes() {
            return ByteString.copyFromUtf8(this.constantEventName_);
        }

        /* access modifiers changed from: private */
        public void setConstantEventName(String value) {
            if (value != null) {
                this.bitField0_ |= 65536;
                this.constantEventName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearConstantEventName() {
            this.bitField0_ &= -65537;
            this.constantEventName_ = getDefaultInstance().getConstantEventName();
        }

        /* access modifiers changed from: private */
        public void setConstantEventNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 65536;
                this.constantEventName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Deprecated
        public boolean hasCpuUsageMetric() {
            return (this.bitField0_ & 131072) != 0;
        }

        @Deprecated
        public CpuMetric.CpuUsageMetric getCpuUsageMetric() {
            CpuMetric.CpuUsageMetric cpuUsageMetric = this.cpuUsageMetric_;
            return cpuUsageMetric == null ? CpuMetric.CpuUsageMetric.getDefaultInstance() : cpuUsageMetric;
        }

        /* access modifiers changed from: private */
        public void setCpuUsageMetric(CpuMetric.CpuUsageMetric value) {
            if (value != null) {
                this.cpuUsageMetric_ = value;
                this.bitField0_ |= 131072;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCpuUsageMetric(CpuMetric.CpuUsageMetric.Builder builderForValue) {
            this.cpuUsageMetric_ = (CpuMetric.CpuUsageMetric) builderForValue.build();
            this.bitField0_ |= 131072;
        }

        /* access modifiers changed from: private */
        public void mergeCpuUsageMetric(CpuMetric.CpuUsageMetric value) {
            if (value != null) {
                CpuMetric.CpuUsageMetric cpuUsageMetric = this.cpuUsageMetric_;
                if (cpuUsageMetric == null || cpuUsageMetric == CpuMetric.CpuUsageMetric.getDefaultInstance()) {
                    this.cpuUsageMetric_ = value;
                } else {
                    this.cpuUsageMetric_ = (CpuMetric.CpuUsageMetric) ((CpuMetric.CpuUsageMetric.Builder) CpuMetric.CpuUsageMetric.newBuilder(this.cpuUsageMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 131072;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCpuUsageMetric() {
            this.cpuUsageMetric_ = null;
            this.bitField0_ &= -131073;
        }

        public boolean hasCpuProfilingMetric() {
            return (this.bitField0_ & 262144) != 0;
        }

        public CpuProfiling.CpuProfilingMetric getCpuProfilingMetric() {
            CpuProfiling.CpuProfilingMetric cpuProfilingMetric = this.cpuProfilingMetric_;
            return cpuProfilingMetric == null ? CpuProfiling.CpuProfilingMetric.getDefaultInstance() : cpuProfilingMetric;
        }

        /* access modifiers changed from: private */
        public void setCpuProfilingMetric(CpuProfiling.CpuProfilingMetric value) {
            if (value != null) {
                this.cpuProfilingMetric_ = value;
                this.bitField0_ |= 262144;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCpuProfilingMetric(CpuProfiling.CpuProfilingMetric.Builder builderForValue) {
            this.cpuProfilingMetric_ = (CpuProfiling.CpuProfilingMetric) builderForValue.build();
            this.bitField0_ |= 262144;
        }

        /* access modifiers changed from: private */
        public void mergeCpuProfilingMetric(CpuProfiling.CpuProfilingMetric value) {
            if (value != null) {
                CpuProfiling.CpuProfilingMetric cpuProfilingMetric = this.cpuProfilingMetric_;
                if (cpuProfilingMetric == null || cpuProfilingMetric == CpuProfiling.CpuProfilingMetric.getDefaultInstance()) {
                    this.cpuProfilingMetric_ = value;
                } else {
                    this.cpuProfilingMetric_ = (CpuProfiling.CpuProfilingMetric) ((CpuProfiling.CpuProfilingMetric.Builder) CpuProfiling.CpuProfilingMetric.newBuilder(this.cpuProfilingMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 262144;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCpuProfilingMetric() {
            this.cpuProfilingMetric_ = null;
            this.bitField0_ &= -262145;
        }

        @Deprecated
        public boolean hasPrimesSpans() {
            return (this.bitField0_ & 524288) != 0;
        }

        @Deprecated
        public PrimesTraceOuterClass.PrimesSpans getPrimesSpans() {
            PrimesTraceOuterClass.PrimesSpans primesSpans = this.primesSpans_;
            return primesSpans == null ? PrimesTraceOuterClass.PrimesSpans.getDefaultInstance() : primesSpans;
        }

        /* access modifiers changed from: private */
        public void setPrimesSpans(PrimesTraceOuterClass.PrimesSpans value) {
            if (value != null) {
                this.primesSpans_ = value;
                this.bitField0_ |= 524288;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrimesSpans(PrimesTraceOuterClass.PrimesSpans.Builder builderForValue) {
            this.primesSpans_ = (PrimesTraceOuterClass.PrimesSpans) builderForValue.build();
            this.bitField0_ |= 524288;
        }

        /* access modifiers changed from: private */
        public void mergePrimesSpans(PrimesTraceOuterClass.PrimesSpans value) {
            if (value != null) {
                PrimesTraceOuterClass.PrimesSpans primesSpans = this.primesSpans_;
                if (primesSpans == null || primesSpans == PrimesTraceOuterClass.PrimesSpans.getDefaultInstance()) {
                    this.primesSpans_ = value;
                } else {
                    this.primesSpans_ = (PrimesTraceOuterClass.PrimesSpans) ((PrimesTraceOuterClass.PrimesSpans.Builder) PrimesTraceOuterClass.PrimesSpans.newBuilder(this.primesSpans_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 524288;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPrimesSpans() {
            this.primesSpans_ = null;
            this.bitField0_ &= -524289;
        }

        @Deprecated
        public List<TelemetryProto.TelemetryMetric> getTelemetryMetricsList() {
            return this.telemetryMetrics_;
        }

        @Deprecated
        public List<? extends TelemetryProto.TelemetryMetricOrBuilder> getTelemetryMetricsOrBuilderList() {
            return this.telemetryMetrics_;
        }

        @Deprecated
        public int getTelemetryMetricsCount() {
            return this.telemetryMetrics_.size();
        }

        @Deprecated
        public TelemetryProto.TelemetryMetric getTelemetryMetrics(int index) {
            return this.telemetryMetrics_.get(index);
        }

        @Deprecated
        public TelemetryProto.TelemetryMetricOrBuilder getTelemetryMetricsOrBuilder(int index) {
            return this.telemetryMetrics_.get(index);
        }

        private void ensureTelemetryMetricsIsMutable() {
            if (!this.telemetryMetrics_.isModifiable()) {
                this.telemetryMetrics_ = GeneratedMessageLite.mutableCopy(this.telemetryMetrics_);
            }
        }

        /* access modifiers changed from: private */
        public void setTelemetryMetrics(int index, TelemetryProto.TelemetryMetric value) {
            if (value != null) {
                ensureTelemetryMetricsIsMutable();
                this.telemetryMetrics_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setTelemetryMetrics(int index, TelemetryProto.TelemetryMetric.Builder builderForValue) {
            ensureTelemetryMetricsIsMutable();
            this.telemetryMetrics_.set(index, (TelemetryProto.TelemetryMetric) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addTelemetryMetrics(TelemetryProto.TelemetryMetric value) {
            if (value != null) {
                ensureTelemetryMetricsIsMutable();
                this.telemetryMetrics_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addTelemetryMetrics(int index, TelemetryProto.TelemetryMetric value) {
            if (value != null) {
                ensureTelemetryMetricsIsMutable();
                this.telemetryMetrics_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addTelemetryMetrics(TelemetryProto.TelemetryMetric.Builder builderForValue) {
            ensureTelemetryMetricsIsMutable();
            this.telemetryMetrics_.add((TelemetryProto.TelemetryMetric) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addTelemetryMetrics(int index, TelemetryProto.TelemetryMetric.Builder builderForValue) {
            ensureTelemetryMetricsIsMutable();
            this.telemetryMetrics_.add(index, (TelemetryProto.TelemetryMetric) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.TelemetryProto$TelemetryMetric>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.TelemetryProto$TelemetryMetric>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllTelemetryMetrics(Iterable<? extends TelemetryProto.TelemetryMetric> values) {
            ensureTelemetryMetricsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.telemetryMetrics_);
        }

        /* access modifiers changed from: private */
        public void clearTelemetryMetrics() {
            this.telemetryMetrics_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeTelemetryMetrics(int index) {
            ensureTelemetryMetricsIsMutable();
            this.telemetryMetrics_.remove(index);
        }

        public boolean hasAccountableComponent() {
            return (this.bitField0_ & 1048576) != 0;
        }

        public AccountableComponent getAccountableComponent() {
            AccountableComponent accountableComponent = this.accountableComponent_;
            return accountableComponent == null ? AccountableComponent.getDefaultInstance() : accountableComponent;
        }

        /* access modifiers changed from: private */
        public void setAccountableComponent(AccountableComponent value) {
            if (value != null) {
                this.accountableComponent_ = value;
                this.bitField0_ |= 1048576;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAccountableComponent(AccountableComponent.Builder builderForValue) {
            this.accountableComponent_ = (AccountableComponent) builderForValue.build();
            this.bitField0_ |= 1048576;
        }

        /* access modifiers changed from: private */
        public void mergeAccountableComponent(AccountableComponent value) {
            if (value != null) {
                AccountableComponent accountableComponent = this.accountableComponent_;
                if (accountableComponent == null || accountableComponent == AccountableComponent.getDefaultInstance()) {
                    this.accountableComponent_ = value;
                } else {
                    this.accountableComponent_ = (AccountableComponent) ((AccountableComponent.Builder) AccountableComponent.newBuilder(this.accountableComponent_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1048576;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAccountableComponent() {
            this.accountableComponent_ = null;
            this.bitField0_ &= -1048577;
        }

        public boolean hasPrimesHeapDump() {
            return (this.bitField0_ & 2097152) != 0;
        }

        public PrimesHeapDumpProto.PrimesHeapDump getPrimesHeapDump() {
            PrimesHeapDumpProto.PrimesHeapDump primesHeapDump = this.primesHeapDump_;
            return primesHeapDump == null ? PrimesHeapDumpProto.PrimesHeapDump.getDefaultInstance() : primesHeapDump;
        }

        /* access modifiers changed from: private */
        public void setPrimesHeapDump(PrimesHeapDumpProto.PrimesHeapDump value) {
            if (value != null) {
                this.primesHeapDump_ = value;
                this.bitField0_ |= 2097152;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrimesHeapDump(PrimesHeapDumpProto.PrimesHeapDump.Builder builderForValue) {
            this.primesHeapDump_ = (PrimesHeapDumpProto.PrimesHeapDump) builderForValue.build();
            this.bitField0_ |= 2097152;
        }

        /* access modifiers changed from: private */
        public void mergePrimesHeapDump(PrimesHeapDumpProto.PrimesHeapDump value) {
            if (value != null) {
                PrimesHeapDumpProto.PrimesHeapDump primesHeapDump = this.primesHeapDump_;
                if (primesHeapDump == null || primesHeapDump == PrimesHeapDumpProto.PrimesHeapDump.getDefaultInstance()) {
                    this.primesHeapDump_ = value;
                } else {
                    this.primesHeapDump_ = (PrimesHeapDumpProto.PrimesHeapDump) ((PrimesHeapDumpProto.PrimesHeapDump.Builder) PrimesHeapDumpProto.PrimesHeapDump.newBuilder(this.primesHeapDump_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2097152;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPrimesHeapDump() {
            this.primesHeapDump_ = null;
            this.bitField0_ &= -2097153;
        }

        public boolean hasDeviceInfo() {
            return (this.bitField0_ & 4194304) != 0;
        }

        public DeviceInfo getDeviceInfo() {
            DeviceInfo deviceInfo = this.deviceInfo_;
            return deviceInfo == null ? DeviceInfo.getDefaultInstance() : deviceInfo;
        }

        /* access modifiers changed from: private */
        public void setDeviceInfo(DeviceInfo value) {
            if (value != null) {
                this.deviceInfo_ = value;
                this.bitField0_ |= 4194304;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setDeviceInfo(DeviceInfo.Builder builderForValue) {
            this.deviceInfo_ = (DeviceInfo) builderForValue.build();
            this.bitField0_ |= 4194304;
        }

        /* access modifiers changed from: private */
        public void mergeDeviceInfo(DeviceInfo value) {
            if (value != null) {
                DeviceInfo deviceInfo = this.deviceInfo_;
                if (deviceInfo == null || deviceInfo == DeviceInfo.getDefaultInstance()) {
                    this.deviceInfo_ = value;
                } else {
                    this.deviceInfo_ = (DeviceInfo) ((DeviceInfo.Builder) DeviceInfo.newBuilder(this.deviceInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4194304;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDeviceInfo() {
            this.deviceInfo_ = null;
            this.bitField0_ &= -4194305;
        }

        public boolean hasPrimesForPrimes() {
            return (this.bitField0_ & 8388608) != 0;
        }

        public PrimesForPrimes getPrimesForPrimes() {
            PrimesForPrimes primesForPrimes = this.primesForPrimes_;
            return primesForPrimes == null ? PrimesForPrimes.getDefaultInstance() : primesForPrimes;
        }

        /* access modifiers changed from: private */
        public void setPrimesForPrimes(PrimesForPrimes value) {
            if (value != null) {
                this.primesForPrimes_ = value;
                this.bitField0_ |= 8388608;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrimesForPrimes(PrimesForPrimes.Builder builderForValue) {
            this.primesForPrimes_ = (PrimesForPrimes) builderForValue.build();
            this.bitField0_ |= 8388608;
        }

        /* access modifiers changed from: private */
        public void mergePrimesForPrimes(PrimesForPrimes value) {
            if (value != null) {
                PrimesForPrimes primesForPrimes = this.primesForPrimes_;
                if (primesForPrimes == null || primesForPrimes == PrimesForPrimes.getDefaultInstance()) {
                    this.primesForPrimes_ = value;
                } else {
                    this.primesForPrimes_ = (PrimesForPrimes) ((PrimesForPrimes.Builder) PrimesForPrimes.newBuilder(this.primesForPrimes_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8388608;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPrimesForPrimes() {
            this.primesForPrimes_ = null;
            this.bitField0_ &= -8388609;
        }

        public List<AggregatedMetricProto.AggregatedMetric> getAggregatedMetricsList() {
            return this.aggregatedMetrics_;
        }

        public List<? extends AggregatedMetricProto.AggregatedMetricOrBuilder> getAggregatedMetricsOrBuilderList() {
            return this.aggregatedMetrics_;
        }

        public int getAggregatedMetricsCount() {
            return this.aggregatedMetrics_.size();
        }

        public AggregatedMetricProto.AggregatedMetric getAggregatedMetrics(int index) {
            return this.aggregatedMetrics_.get(index);
        }

        public AggregatedMetricProto.AggregatedMetricOrBuilder getAggregatedMetricsOrBuilder(int index) {
            return this.aggregatedMetrics_.get(index);
        }

        private void ensureAggregatedMetricsIsMutable() {
            if (!this.aggregatedMetrics_.isModifiable()) {
                this.aggregatedMetrics_ = GeneratedMessageLite.mutableCopy(this.aggregatedMetrics_);
            }
        }

        /* access modifiers changed from: private */
        public void setAggregatedMetrics(int index, AggregatedMetricProto.AggregatedMetric value) {
            if (value != null) {
                ensureAggregatedMetricsIsMutable();
                this.aggregatedMetrics_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAggregatedMetrics(int index, AggregatedMetricProto.AggregatedMetric.Builder builderForValue) {
            ensureAggregatedMetricsIsMutable();
            this.aggregatedMetrics_.set(index, (AggregatedMetricProto.AggregatedMetric) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addAggregatedMetrics(AggregatedMetricProto.AggregatedMetric value) {
            if (value != null) {
                ensureAggregatedMetricsIsMutable();
                this.aggregatedMetrics_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAggregatedMetrics(int index, AggregatedMetricProto.AggregatedMetric value) {
            if (value != null) {
                ensureAggregatedMetricsIsMutable();
                this.aggregatedMetrics_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAggregatedMetrics(AggregatedMetricProto.AggregatedMetric.Builder builderForValue) {
            ensureAggregatedMetricsIsMutable();
            this.aggregatedMetrics_.add((AggregatedMetricProto.AggregatedMetric) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addAggregatedMetrics(int index, AggregatedMetricProto.AggregatedMetric.Builder builderForValue) {
            ensureAggregatedMetricsIsMutable();
            this.aggregatedMetrics_.add(index, (AggregatedMetricProto.AggregatedMetric) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.AggregatedMetricProto$AggregatedMetric>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.AggregatedMetricProto$AggregatedMetric>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllAggregatedMetrics(Iterable<? extends AggregatedMetricProto.AggregatedMetric> values) {
            ensureAggregatedMetricsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.aggregatedMetrics_);
        }

        /* access modifiers changed from: private */
        public void clearAggregatedMetrics() {
            this.aggregatedMetrics_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeAggregatedMetrics(int index) {
            ensureAggregatedMetricsIsMutable();
            this.aggregatedMetrics_.remove(index);
        }

        public boolean hasPrimesScenario() {
            return (this.bitField0_ & 16777216) != 0;
        }

        public PrimesScenarioProto.PrimesScenario getPrimesScenario() {
            PrimesScenarioProto.PrimesScenario primesScenario = this.primesScenario_;
            return primesScenario == null ? PrimesScenarioProto.PrimesScenario.getDefaultInstance() : primesScenario;
        }

        /* access modifiers changed from: private */
        public void setPrimesScenario(PrimesScenarioProto.PrimesScenario value) {
            if (value != null) {
                this.primesScenario_ = value;
                this.bitField0_ |= 16777216;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrimesScenario(PrimesScenarioProto.PrimesScenario.Builder builderForValue) {
            this.primesScenario_ = (PrimesScenarioProto.PrimesScenario) builderForValue.build();
            this.bitField0_ |= 16777216;
        }

        /* access modifiers changed from: private */
        public void mergePrimesScenario(PrimesScenarioProto.PrimesScenario value) {
            if (value != null) {
                PrimesScenarioProto.PrimesScenario primesScenario = this.primesScenario_;
                if (primesScenario == null || primesScenario == PrimesScenarioProto.PrimesScenario.getDefaultInstance()) {
                    this.primesScenario_ = value;
                } else {
                    this.primesScenario_ = (PrimesScenarioProto.PrimesScenario) ((PrimesScenarioProto.PrimesScenario.Builder) PrimesScenarioProto.PrimesScenario.newBuilder(this.primesScenario_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16777216;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPrimesScenario() {
            this.primesScenario_ = null;
            this.bitField0_ &= -16777217;
        }

        public boolean hasStrictModeViolation() {
            return (this.bitField0_ & 33554432) != 0;
        }

        public StrictModeViolationMetric getStrictModeViolation() {
            StrictModeViolationMetric strictModeViolationMetric = this.strictModeViolation_;
            return strictModeViolationMetric == null ? StrictModeViolationMetric.getDefaultInstance() : strictModeViolationMetric;
        }

        /* access modifiers changed from: private */
        public void setStrictModeViolation(StrictModeViolationMetric value) {
            if (value != null) {
                this.strictModeViolation_ = value;
                this.bitField0_ |= 33554432;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setStrictModeViolation(StrictModeViolationMetric.Builder builderForValue) {
            this.strictModeViolation_ = (StrictModeViolationMetric) builderForValue.build();
            this.bitField0_ |= 33554432;
        }

        /* access modifiers changed from: private */
        public void mergeStrictModeViolation(StrictModeViolationMetric value) {
            if (value != null) {
                StrictModeViolationMetric strictModeViolationMetric = this.strictModeViolation_;
                if (strictModeViolationMetric == null || strictModeViolationMetric == StrictModeViolationMetric.getDefaultInstance()) {
                    this.strictModeViolation_ = value;
                } else {
                    this.strictModeViolation_ = (StrictModeViolationMetric) ((StrictModeViolationMetric.Builder) StrictModeViolationMetric.newBuilder(this.strictModeViolation_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 33554432;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearStrictModeViolation() {
            this.strictModeViolation_ = null;
            this.bitField0_ &= -33554433;
        }

        public boolean hasClientErrorLoggingMetrics() {
            return (this.bitField0_ & 67108864) != 0;
        }

        public ClientErrorLoggingMetric getClientErrorLoggingMetrics() {
            ClientErrorLoggingMetric clientErrorLoggingMetric = this.clientErrorLoggingMetrics_;
            return clientErrorLoggingMetric == null ? ClientErrorLoggingMetric.getDefaultInstance() : clientErrorLoggingMetric;
        }

        /* access modifiers changed from: private */
        public void setClientErrorLoggingMetrics(ClientErrorLoggingMetric value) {
            if (value != null) {
                this.clientErrorLoggingMetrics_ = value;
                this.bitField0_ |= 67108864;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setClientErrorLoggingMetrics(ClientErrorLoggingMetric.Builder builderForValue) {
            this.clientErrorLoggingMetrics_ = (ClientErrorLoggingMetric) builderForValue.build();
            this.bitField0_ |= 67108864;
        }

        /* access modifiers changed from: private */
        public void mergeClientErrorLoggingMetrics(ClientErrorLoggingMetric value) {
            if (value != null) {
                ClientErrorLoggingMetric clientErrorLoggingMetric = this.clientErrorLoggingMetrics_;
                if (clientErrorLoggingMetric == null || clientErrorLoggingMetric == ClientErrorLoggingMetric.getDefaultInstance()) {
                    this.clientErrorLoggingMetrics_ = value;
                } else {
                    this.clientErrorLoggingMetrics_ = (ClientErrorLoggingMetric) ((ClientErrorLoggingMetric.Builder) ClientErrorLoggingMetric.newBuilder(this.clientErrorLoggingMetrics_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 67108864;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearClientErrorLoggingMetrics() {
            this.clientErrorLoggingMetrics_ = null;
            this.bitField0_ &= -67108865;
        }

        public static SystemHealthMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemHealthMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemHealthMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemHealthMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemHealthMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemHealthMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemHealthMetric parseFrom(InputStream input) throws IOException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemHealthMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SystemHealthMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (SystemHealthMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemHealthMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemHealthMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SystemHealthMetric parseFrom(CodedInputStream input) throws IOException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemHealthMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemHealthMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SystemHealthMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SystemHealthMetric, Builder> implements SystemHealthMetricOrBuilder {
            private Builder() {
                super(SystemHealthMetric.DEFAULT_INSTANCE);
            }

            public boolean hasMemoryUsageMetric() {
                return ((SystemHealthMetric) this.instance).hasMemoryUsageMetric();
            }

            public MemoryMetric.MemoryUsageMetric getMemoryUsageMetric() {
                return ((SystemHealthMetric) this.instance).getMemoryUsageMetric();
            }

            public Builder setMemoryUsageMetric(MemoryMetric.MemoryUsageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setMemoryUsageMetric(value);
                return this;
            }

            public Builder setMemoryUsageMetric(MemoryMetric.MemoryUsageMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setMemoryUsageMetric(builderForValue);
                return this;
            }

            public Builder mergeMemoryUsageMetric(MemoryMetric.MemoryUsageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeMemoryUsageMetric(value);
                return this;
            }

            public Builder clearMemoryUsageMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearMemoryUsageMetric();
                return this;
            }

            public boolean hasHashedCustomEventName() {
                return ((SystemHealthMetric) this.instance).hasHashedCustomEventName();
            }

            public long getHashedCustomEventName() {
                return ((SystemHealthMetric) this.instance).getHashedCustomEventName();
            }

            public Builder setHashedCustomEventName(long value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setHashedCustomEventName(value);
                return this;
            }

            public Builder clearHashedCustomEventName() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearHashedCustomEventName();
                return this;
            }

            public boolean hasCustomEventName() {
                return ((SystemHealthMetric) this.instance).hasCustomEventName();
            }

            public String getCustomEventName() {
                return ((SystemHealthMetric) this.instance).getCustomEventName();
            }

            public ByteString getCustomEventNameBytes() {
                return ((SystemHealthMetric) this.instance).getCustomEventNameBytes();
            }

            public Builder setCustomEventName(String value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setCustomEventName(value);
                return this;
            }

            public Builder clearCustomEventName() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearCustomEventName();
                return this;
            }

            public Builder setCustomEventNameBytes(ByteString value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setCustomEventNameBytes(value);
                return this;
            }

            public boolean hasTimerMetric() {
                return ((SystemHealthMetric) this.instance).hasTimerMetric();
            }

            public TimerMetric getTimerMetric() {
                return ((SystemHealthMetric) this.instance).getTimerMetric();
            }

            public Builder setTimerMetric(TimerMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setTimerMetric(value);
                return this;
            }

            public Builder setTimerMetric(TimerMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setTimerMetric(builderForValue);
                return this;
            }

            public Builder mergeTimerMetric(TimerMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeTimerMetric(value);
                return this;
            }

            public Builder clearTimerMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearTimerMetric();
                return this;
            }

            public boolean hasApplicationInfo() {
                return ((SystemHealthMetric) this.instance).hasApplicationInfo();
            }

            public ApplicationInfo getApplicationInfo() {
                return ((SystemHealthMetric) this.instance).getApplicationInfo();
            }

            public Builder setApplicationInfo(ApplicationInfo value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setApplicationInfo(value);
                return this;
            }

            public Builder setApplicationInfo(ApplicationInfo.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setApplicationInfo(builderForValue);
                return this;
            }

            public Builder mergeApplicationInfo(ApplicationInfo value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeApplicationInfo(value);
                return this;
            }

            public Builder clearApplicationInfo() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearApplicationInfo();
                return this;
            }

            public boolean hasNetworkUsageMetric() {
                return ((SystemHealthMetric) this.instance).hasNetworkUsageMetric();
            }

            public NetworkMetric.NetworkUsageMetric getNetworkUsageMetric() {
                return ((SystemHealthMetric) this.instance).getNetworkUsageMetric();
            }

            public Builder setNetworkUsageMetric(NetworkMetric.NetworkUsageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setNetworkUsageMetric(value);
                return this;
            }

            public Builder setNetworkUsageMetric(NetworkMetric.NetworkUsageMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setNetworkUsageMetric(builderForValue);
                return this;
            }

            public Builder mergeNetworkUsageMetric(NetworkMetric.NetworkUsageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeNetworkUsageMetric(value);
                return this;
            }

            public Builder clearNetworkUsageMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearNetworkUsageMetric();
                return this;
            }

            public boolean hasCrashMetric() {
                return ((SystemHealthMetric) this.instance).hasCrashMetric();
            }

            public CrashMetric getCrashMetric() {
                return ((SystemHealthMetric) this.instance).getCrashMetric();
            }

            public Builder setCrashMetric(CrashMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setCrashMetric(value);
                return this;
            }

            public Builder setCrashMetric(CrashMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setCrashMetric(builderForValue);
                return this;
            }

            public Builder mergeCrashMetric(CrashMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeCrashMetric(value);
                return this;
            }

            public Builder clearCrashMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearCrashMetric();
                return this;
            }

            public boolean hasPrimesStats() {
                return ((SystemHealthMetric) this.instance).hasPrimesStats();
            }

            public PrimesStats getPrimesStats() {
                return ((SystemHealthMetric) this.instance).getPrimesStats();
            }

            public Builder setPrimesStats(PrimesStats value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesStats(value);
                return this;
            }

            public Builder setPrimesStats(PrimesStats.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesStats(builderForValue);
                return this;
            }

            public Builder mergePrimesStats(PrimesStats value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergePrimesStats(value);
                return this;
            }

            public Builder clearPrimesStats() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearPrimesStats();
                return this;
            }

            public boolean hasPackageMetric() {
                return ((SystemHealthMetric) this.instance).hasPackageMetric();
            }

            public PackageMetric getPackageMetric() {
                return ((SystemHealthMetric) this.instance).getPackageMetric();
            }

            public Builder setPackageMetric(PackageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPackageMetric(value);
                return this;
            }

            public Builder setPackageMetric(PackageMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPackageMetric(builderForValue);
                return this;
            }

            public Builder mergePackageMetric(PackageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergePackageMetric(value);
                return this;
            }

            public Builder clearPackageMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearPackageMetric();
                return this;
            }

            public boolean hasBatteryUsageMetric() {
                return ((SystemHealthMetric) this.instance).hasBatteryUsageMetric();
            }

            public BatteryMetric.BatteryUsageMetric getBatteryUsageMetric() {
                return ((SystemHealthMetric) this.instance).getBatteryUsageMetric();
            }

            public Builder setBatteryUsageMetric(BatteryMetric.BatteryUsageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setBatteryUsageMetric(value);
                return this;
            }

            public Builder setBatteryUsageMetric(BatteryMetric.BatteryUsageMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setBatteryUsageMetric(builderForValue);
                return this;
            }

            public Builder mergeBatteryUsageMetric(BatteryMetric.BatteryUsageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeBatteryUsageMetric(value);
                return this;
            }

            public Builder clearBatteryUsageMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearBatteryUsageMetric();
                return this;
            }

            @Deprecated
            public boolean hasFrameRateMetric() {
                return ((SystemHealthMetric) this.instance).hasFrameRateMetric();
            }

            @Deprecated
            public FrameRateMetric getFrameRateMetric() {
                return ((SystemHealthMetric) this.instance).getFrameRateMetric();
            }

            @Deprecated
            public Builder setFrameRateMetric(FrameRateMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setFrameRateMetric(value);
                return this;
            }

            @Deprecated
            public Builder setFrameRateMetric(FrameRateMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setFrameRateMetric(builderForValue);
                return this;
            }

            @Deprecated
            public Builder mergeFrameRateMetric(FrameRateMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeFrameRateMetric(value);
                return this;
            }

            @Deprecated
            public Builder clearFrameRateMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearFrameRateMetric();
                return this;
            }

            public boolean hasJankMetric() {
                return ((SystemHealthMetric) this.instance).hasJankMetric();
            }

            public JankMetric getJankMetric() {
                return ((SystemHealthMetric) this.instance).getJankMetric();
            }

            public Builder setJankMetric(JankMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setJankMetric(value);
                return this;
            }

            public Builder setJankMetric(JankMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setJankMetric(builderForValue);
                return this;
            }

            public Builder mergeJankMetric(JankMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeJankMetric(value);
                return this;
            }

            public Builder clearJankMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearJankMetric();
                return this;
            }

            public boolean hasMemoryLeakMetric() {
                return ((SystemHealthMetric) this.instance).hasMemoryLeakMetric();
            }

            public MemoryLeakMetric getMemoryLeakMetric() {
                return ((SystemHealthMetric) this.instance).getMemoryLeakMetric();
            }

            public Builder setMemoryLeakMetric(MemoryLeakMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setMemoryLeakMetric(value);
                return this;
            }

            public Builder setMemoryLeakMetric(MemoryLeakMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setMemoryLeakMetric(builderForValue);
                return this;
            }

            public Builder mergeMemoryLeakMetric(MemoryLeakMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeMemoryLeakMetric(value);
                return this;
            }

            public Builder clearMemoryLeakMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearMemoryLeakMetric();
                return this;
            }

            public boolean hasMetricExtension() {
                return ((SystemHealthMetric) this.instance).hasMetricExtension();
            }

            public ExtensionMetric.MetricExtension getMetricExtension() {
                return ((SystemHealthMetric) this.instance).getMetricExtension();
            }

            public Builder setMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setMetricExtension(value);
                return this;
            }

            public Builder setMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setMetricExtension(builderForValue);
                return this;
            }

            public Builder mergeMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeMetricExtension(value);
                return this;
            }

            public Builder clearMetricExtension() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearMetricExtension();
                return this;
            }

            public boolean hasMagicEyeMetric() {
                return ((SystemHealthMetric) this.instance).hasMagicEyeMetric();
            }

            public MagicEyeMetric getMagicEyeMetric() {
                return ((SystemHealthMetric) this.instance).getMagicEyeMetric();
            }

            public Builder setMagicEyeMetric(MagicEyeMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setMagicEyeMetric(value);
                return this;
            }

            public Builder setMagicEyeMetric(MagicEyeMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setMagicEyeMetric(builderForValue);
                return this;
            }

            public Builder mergeMagicEyeMetric(MagicEyeMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeMagicEyeMetric(value);
                return this;
            }

            public Builder clearMagicEyeMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearMagicEyeMetric();
                return this;
            }

            public boolean hasPrimesTrace() {
                return ((SystemHealthMetric) this.instance).hasPrimesTrace();
            }

            public PrimesTraceOuterClass.PrimesTrace getPrimesTrace() {
                return ((SystemHealthMetric) this.instance).getPrimesTrace();
            }

            public Builder setPrimesTrace(PrimesTraceOuterClass.PrimesTrace value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesTrace(value);
                return this;
            }

            public Builder setPrimesTrace(PrimesTraceOuterClass.PrimesTrace.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesTrace(builderForValue);
                return this;
            }

            public Builder mergePrimesTrace(PrimesTraceOuterClass.PrimesTrace value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergePrimesTrace(value);
                return this;
            }

            public Builder clearPrimesTrace() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearPrimesTrace();
                return this;
            }

            public boolean hasConstantEventName() {
                return ((SystemHealthMetric) this.instance).hasConstantEventName();
            }

            public String getConstantEventName() {
                return ((SystemHealthMetric) this.instance).getConstantEventName();
            }

            public ByteString getConstantEventNameBytes() {
                return ((SystemHealthMetric) this.instance).getConstantEventNameBytes();
            }

            public Builder setConstantEventName(String value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setConstantEventName(value);
                return this;
            }

            public Builder clearConstantEventName() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearConstantEventName();
                return this;
            }

            public Builder setConstantEventNameBytes(ByteString value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setConstantEventNameBytes(value);
                return this;
            }

            @Deprecated
            public boolean hasCpuUsageMetric() {
                return ((SystemHealthMetric) this.instance).hasCpuUsageMetric();
            }

            @Deprecated
            public CpuMetric.CpuUsageMetric getCpuUsageMetric() {
                return ((SystemHealthMetric) this.instance).getCpuUsageMetric();
            }

            @Deprecated
            public Builder setCpuUsageMetric(CpuMetric.CpuUsageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setCpuUsageMetric(value);
                return this;
            }

            @Deprecated
            public Builder setCpuUsageMetric(CpuMetric.CpuUsageMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setCpuUsageMetric(builderForValue);
                return this;
            }

            @Deprecated
            public Builder mergeCpuUsageMetric(CpuMetric.CpuUsageMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeCpuUsageMetric(value);
                return this;
            }

            @Deprecated
            public Builder clearCpuUsageMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearCpuUsageMetric();
                return this;
            }

            public boolean hasCpuProfilingMetric() {
                return ((SystemHealthMetric) this.instance).hasCpuProfilingMetric();
            }

            public CpuProfiling.CpuProfilingMetric getCpuProfilingMetric() {
                return ((SystemHealthMetric) this.instance).getCpuProfilingMetric();
            }

            public Builder setCpuProfilingMetric(CpuProfiling.CpuProfilingMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setCpuProfilingMetric(value);
                return this;
            }

            public Builder setCpuProfilingMetric(CpuProfiling.CpuProfilingMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setCpuProfilingMetric(builderForValue);
                return this;
            }

            public Builder mergeCpuProfilingMetric(CpuProfiling.CpuProfilingMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeCpuProfilingMetric(value);
                return this;
            }

            public Builder clearCpuProfilingMetric() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearCpuProfilingMetric();
                return this;
            }

            @Deprecated
            public boolean hasPrimesSpans() {
                return ((SystemHealthMetric) this.instance).hasPrimesSpans();
            }

            @Deprecated
            public PrimesTraceOuterClass.PrimesSpans getPrimesSpans() {
                return ((SystemHealthMetric) this.instance).getPrimesSpans();
            }

            @Deprecated
            public Builder setPrimesSpans(PrimesTraceOuterClass.PrimesSpans value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesSpans(value);
                return this;
            }

            @Deprecated
            public Builder setPrimesSpans(PrimesTraceOuterClass.PrimesSpans.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesSpans(builderForValue);
                return this;
            }

            @Deprecated
            public Builder mergePrimesSpans(PrimesTraceOuterClass.PrimesSpans value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergePrimesSpans(value);
                return this;
            }

            @Deprecated
            public Builder clearPrimesSpans() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearPrimesSpans();
                return this;
            }

            @Deprecated
            public List<TelemetryProto.TelemetryMetric> getTelemetryMetricsList() {
                return Collections.unmodifiableList(((SystemHealthMetric) this.instance).getTelemetryMetricsList());
            }

            @Deprecated
            public int getTelemetryMetricsCount() {
                return ((SystemHealthMetric) this.instance).getTelemetryMetricsCount();
            }

            @Deprecated
            public TelemetryProto.TelemetryMetric getTelemetryMetrics(int index) {
                return ((SystemHealthMetric) this.instance).getTelemetryMetrics(index);
            }

            @Deprecated
            public Builder setTelemetryMetrics(int index, TelemetryProto.TelemetryMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setTelemetryMetrics(index, value);
                return this;
            }

            @Deprecated
            public Builder setTelemetryMetrics(int index, TelemetryProto.TelemetryMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setTelemetryMetrics(index, builderForValue);
                return this;
            }

            @Deprecated
            public Builder addTelemetryMetrics(TelemetryProto.TelemetryMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addTelemetryMetrics(value);
                return this;
            }

            @Deprecated
            public Builder addTelemetryMetrics(int index, TelemetryProto.TelemetryMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addTelemetryMetrics(index, value);
                return this;
            }

            @Deprecated
            public Builder addTelemetryMetrics(TelemetryProto.TelemetryMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addTelemetryMetrics(builderForValue);
                return this;
            }

            @Deprecated
            public Builder addTelemetryMetrics(int index, TelemetryProto.TelemetryMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addTelemetryMetrics(index, builderForValue);
                return this;
            }

            @Deprecated
            public Builder addAllTelemetryMetrics(Iterable<? extends TelemetryProto.TelemetryMetric> values) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addAllTelemetryMetrics(values);
                return this;
            }

            @Deprecated
            public Builder clearTelemetryMetrics() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearTelemetryMetrics();
                return this;
            }

            @Deprecated
            public Builder removeTelemetryMetrics(int index) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).removeTelemetryMetrics(index);
                return this;
            }

            public boolean hasAccountableComponent() {
                return ((SystemHealthMetric) this.instance).hasAccountableComponent();
            }

            public AccountableComponent getAccountableComponent() {
                return ((SystemHealthMetric) this.instance).getAccountableComponent();
            }

            public Builder setAccountableComponent(AccountableComponent value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setAccountableComponent(value);
                return this;
            }

            public Builder setAccountableComponent(AccountableComponent.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setAccountableComponent(builderForValue);
                return this;
            }

            public Builder mergeAccountableComponent(AccountableComponent value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeAccountableComponent(value);
                return this;
            }

            public Builder clearAccountableComponent() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearAccountableComponent();
                return this;
            }

            public boolean hasPrimesHeapDump() {
                return ((SystemHealthMetric) this.instance).hasPrimesHeapDump();
            }

            public PrimesHeapDumpProto.PrimesHeapDump getPrimesHeapDump() {
                return ((SystemHealthMetric) this.instance).getPrimesHeapDump();
            }

            public Builder setPrimesHeapDump(PrimesHeapDumpProto.PrimesHeapDump value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesHeapDump(value);
                return this;
            }

            public Builder setPrimesHeapDump(PrimesHeapDumpProto.PrimesHeapDump.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesHeapDump(builderForValue);
                return this;
            }

            public Builder mergePrimesHeapDump(PrimesHeapDumpProto.PrimesHeapDump value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergePrimesHeapDump(value);
                return this;
            }

            public Builder clearPrimesHeapDump() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearPrimesHeapDump();
                return this;
            }

            public boolean hasDeviceInfo() {
                return ((SystemHealthMetric) this.instance).hasDeviceInfo();
            }

            public DeviceInfo getDeviceInfo() {
                return ((SystemHealthMetric) this.instance).getDeviceInfo();
            }

            public Builder setDeviceInfo(DeviceInfo value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setDeviceInfo(value);
                return this;
            }

            public Builder setDeviceInfo(DeviceInfo.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setDeviceInfo(builderForValue);
                return this;
            }

            public Builder mergeDeviceInfo(DeviceInfo value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeDeviceInfo(value);
                return this;
            }

            public Builder clearDeviceInfo() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearDeviceInfo();
                return this;
            }

            public boolean hasPrimesForPrimes() {
                return ((SystemHealthMetric) this.instance).hasPrimesForPrimes();
            }

            public PrimesForPrimes getPrimesForPrimes() {
                return ((SystemHealthMetric) this.instance).getPrimesForPrimes();
            }

            public Builder setPrimesForPrimes(PrimesForPrimes value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesForPrimes(value);
                return this;
            }

            public Builder setPrimesForPrimes(PrimesForPrimes.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesForPrimes(builderForValue);
                return this;
            }

            public Builder mergePrimesForPrimes(PrimesForPrimes value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergePrimesForPrimes(value);
                return this;
            }

            public Builder clearPrimesForPrimes() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearPrimesForPrimes();
                return this;
            }

            public List<AggregatedMetricProto.AggregatedMetric> getAggregatedMetricsList() {
                return Collections.unmodifiableList(((SystemHealthMetric) this.instance).getAggregatedMetricsList());
            }

            public int getAggregatedMetricsCount() {
                return ((SystemHealthMetric) this.instance).getAggregatedMetricsCount();
            }

            public AggregatedMetricProto.AggregatedMetric getAggregatedMetrics(int index) {
                return ((SystemHealthMetric) this.instance).getAggregatedMetrics(index);
            }

            public Builder setAggregatedMetrics(int index, AggregatedMetricProto.AggregatedMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setAggregatedMetrics(index, value);
                return this;
            }

            public Builder setAggregatedMetrics(int index, AggregatedMetricProto.AggregatedMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setAggregatedMetrics(index, builderForValue);
                return this;
            }

            public Builder addAggregatedMetrics(AggregatedMetricProto.AggregatedMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addAggregatedMetrics(value);
                return this;
            }

            public Builder addAggregatedMetrics(int index, AggregatedMetricProto.AggregatedMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addAggregatedMetrics(index, value);
                return this;
            }

            public Builder addAggregatedMetrics(AggregatedMetricProto.AggregatedMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addAggregatedMetrics(builderForValue);
                return this;
            }

            public Builder addAggregatedMetrics(int index, AggregatedMetricProto.AggregatedMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addAggregatedMetrics(index, builderForValue);
                return this;
            }

            public Builder addAllAggregatedMetrics(Iterable<? extends AggregatedMetricProto.AggregatedMetric> values) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).addAllAggregatedMetrics(values);
                return this;
            }

            public Builder clearAggregatedMetrics() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearAggregatedMetrics();
                return this;
            }

            public Builder removeAggregatedMetrics(int index) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).removeAggregatedMetrics(index);
                return this;
            }

            public boolean hasPrimesScenario() {
                return ((SystemHealthMetric) this.instance).hasPrimesScenario();
            }

            public PrimesScenarioProto.PrimesScenario getPrimesScenario() {
                return ((SystemHealthMetric) this.instance).getPrimesScenario();
            }

            public Builder setPrimesScenario(PrimesScenarioProto.PrimesScenario value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesScenario(value);
                return this;
            }

            public Builder setPrimesScenario(PrimesScenarioProto.PrimesScenario.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setPrimesScenario(builderForValue);
                return this;
            }

            public Builder mergePrimesScenario(PrimesScenarioProto.PrimesScenario value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergePrimesScenario(value);
                return this;
            }

            public Builder clearPrimesScenario() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearPrimesScenario();
                return this;
            }

            public boolean hasStrictModeViolation() {
                return ((SystemHealthMetric) this.instance).hasStrictModeViolation();
            }

            public StrictModeViolationMetric getStrictModeViolation() {
                return ((SystemHealthMetric) this.instance).getStrictModeViolation();
            }

            public Builder setStrictModeViolation(StrictModeViolationMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setStrictModeViolation(value);
                return this;
            }

            public Builder setStrictModeViolation(StrictModeViolationMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setStrictModeViolation(builderForValue);
                return this;
            }

            public Builder mergeStrictModeViolation(StrictModeViolationMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeStrictModeViolation(value);
                return this;
            }

            public Builder clearStrictModeViolation() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearStrictModeViolation();
                return this;
            }

            public boolean hasClientErrorLoggingMetrics() {
                return ((SystemHealthMetric) this.instance).hasClientErrorLoggingMetrics();
            }

            public ClientErrorLoggingMetric getClientErrorLoggingMetrics() {
                return ((SystemHealthMetric) this.instance).getClientErrorLoggingMetrics();
            }

            public Builder setClientErrorLoggingMetrics(ClientErrorLoggingMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setClientErrorLoggingMetrics(value);
                return this;
            }

            public Builder setClientErrorLoggingMetrics(ClientErrorLoggingMetric.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).setClientErrorLoggingMetrics(builderForValue);
                return this;
            }

            public Builder mergeClientErrorLoggingMetrics(ClientErrorLoggingMetric value) {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).mergeClientErrorLoggingMetrics(value);
                return this;
            }

            public Builder clearClientErrorLoggingMetrics() {
                copyOnWrite();
                ((SystemHealthMetric) this.instance).clearClientErrorLoggingMetrics();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SystemHealthMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u001d\u0000\u0001\u0001\u001d\u001d\u0000\u0002\u0001\u0001Љ\u0000\u0002\u0005\u0001\u0003\b\u0002\u0004\t\u0003\u0005\t\u0004\u0006\t\u0005\u0007\t\u0006\b\t\u0007\t\t\b\n\t\t\u000b\t\n\f\t\u000b\r\t\f\u000e\t\r\u000f\t\u000e\u0010\t\u000f\u0011\b\u0010\u0012\t\u0011\u0013\t\u0013\u0014\u001b\u0015\t\u0014\u0016\t\u0015\u0017\t\u0016\u0018\t\u0017\u0019\u001b\u001a\t\u0018\u001b\t\u0012\u001c\t\u0019\u001d\t\u001a", new Object[]{"bitField0_", "memoryUsageMetric_", "hashedCustomEventName_", "customEventName_", "timerMetric_", "applicationInfo_", "networkUsageMetric_", "crashMetric_", "primesStats_", "packageMetric_", "batteryUsageMetric_", "frameRateMetric_", "jankMetric_", "memoryLeakMetric_", "metricExtension_", "magicEyeMetric_", "primesTrace_", "constantEventName_", "cpuUsageMetric_", "primesSpans_", "telemetryMetrics_", TelemetryProto.TelemetryMetric.class, "accountableComponent_", "primesHeapDump_", "deviceInfo_", "primesForPrimes_", "aggregatedMetrics_", AggregatedMetricProto.AggregatedMetric.class, "primesScenario_", "cpuProfilingMetric_", "strictModeViolation_", "clientErrorLoggingMetrics_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SystemHealthMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (SystemHealthMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(SystemHealthMetric.class, DEFAULT_INSTANCE);
        }

        public static SystemHealthMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SystemHealthMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AccountableComponent extends GeneratedMessageLite<AccountableComponent, Builder> implements AccountableComponentOrBuilder {
        public static final int CUSTOM_NAME_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final AccountableComponent DEFAULT_INSTANCE = new AccountableComponent();
        private static volatile Parser<AccountableComponent> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String customName_ = "";

        private AccountableComponent() {
        }

        public boolean hasCustomName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getCustomName() {
            return this.customName_;
        }

        public ByteString getCustomNameBytes() {
            return ByteString.copyFromUtf8(this.customName_);
        }

        /* access modifiers changed from: private */
        public void setCustomName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.customName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCustomName() {
            this.bitField0_ &= -2;
            this.customName_ = getDefaultInstance().getCustomName();
        }

        /* access modifiers changed from: private */
        public void setCustomNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.customName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static AccountableComponent parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AccountableComponent parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AccountableComponent parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AccountableComponent parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AccountableComponent parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AccountableComponent parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AccountableComponent parseFrom(InputStream input) throws IOException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AccountableComponent parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AccountableComponent parseDelimitedFrom(InputStream input) throws IOException {
            return (AccountableComponent) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AccountableComponent parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AccountableComponent) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AccountableComponent parseFrom(CodedInputStream input) throws IOException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AccountableComponent parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AccountableComponent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AccountableComponent prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<AccountableComponent, Builder> implements AccountableComponentOrBuilder {
            private Builder() {
                super(AccountableComponent.DEFAULT_INSTANCE);
            }

            public boolean hasCustomName() {
                return ((AccountableComponent) this.instance).hasCustomName();
            }

            public String getCustomName() {
                return ((AccountableComponent) this.instance).getCustomName();
            }

            public ByteString getCustomNameBytes() {
                return ((AccountableComponent) this.instance).getCustomNameBytes();
            }

            public Builder setCustomName(String value) {
                copyOnWrite();
                ((AccountableComponent) this.instance).setCustomName(value);
                return this;
            }

            public Builder clearCustomName() {
                copyOnWrite();
                ((AccountableComponent) this.instance).clearCustomName();
                return this;
            }

            public Builder setCustomNameBytes(ByteString value) {
                copyOnWrite();
                ((AccountableComponent) this.instance).setCustomNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AccountableComponent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\b\u0000", new Object[]{"bitField0_", "customName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AccountableComponent> parser = PARSER;
                    if (parser == null) {
                        synchronized (AccountableComponent.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(AccountableComponent.class, DEFAULT_INSTANCE);
        }

        public static AccountableComponent getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AccountableComponent> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class TimerMetric extends GeneratedMessageLite<TimerMetric, Builder> implements TimerMetricOrBuilder {
        /* access modifiers changed from: private */
        public static final TimerMetric DEFAULT_INSTANCE = new TimerMetric();
        public static final int DURATION_MS_FIELD_NUMBER = 1;
        public static final int END_STATUS_FIELD_NUMBER = 2;
        private static volatile Parser<TimerMetric> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long durationMs_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int endStatus_;

        private TimerMetric() {
        }

        public boolean hasDurationMs() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getDurationMs() {
            return this.durationMs_;
        }

        /* access modifiers changed from: private */
        public void setDurationMs(long value) {
            this.bitField0_ |= 1;
            this.durationMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDurationMs() {
            this.bitField0_ &= -2;
            this.durationMs_ = 0;
        }

        public boolean hasEndStatus() {
            return (this.bitField0_ & 2) != 0;
        }

        public PrimesTraceOuterClass.EndStatus getEndStatus() {
            PrimesTraceOuterClass.EndStatus result = PrimesTraceOuterClass.EndStatus.forNumber(this.endStatus_);
            return result == null ? PrimesTraceOuterClass.EndStatus.UNKNOWN_STATUS : result;
        }

        /* access modifiers changed from: private */
        public void setEndStatus(PrimesTraceOuterClass.EndStatus value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.endStatus_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearEndStatus() {
            this.bitField0_ &= -3;
            this.endStatus_ = 0;
        }

        public static TimerMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TimerMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TimerMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TimerMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TimerMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TimerMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TimerMetric parseFrom(InputStream input) throws IOException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TimerMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TimerMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (TimerMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TimerMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TimerMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TimerMetric parseFrom(CodedInputStream input) throws IOException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TimerMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TimerMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TimerMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<TimerMetric, Builder> implements TimerMetricOrBuilder {
            private Builder() {
                super(TimerMetric.DEFAULT_INSTANCE);
            }

            public boolean hasDurationMs() {
                return ((TimerMetric) this.instance).hasDurationMs();
            }

            public long getDurationMs() {
                return ((TimerMetric) this.instance).getDurationMs();
            }

            public Builder setDurationMs(long value) {
                copyOnWrite();
                ((TimerMetric) this.instance).setDurationMs(value);
                return this;
            }

            public Builder clearDurationMs() {
                copyOnWrite();
                ((TimerMetric) this.instance).clearDurationMs();
                return this;
            }

            public boolean hasEndStatus() {
                return ((TimerMetric) this.instance).hasEndStatus();
            }

            public PrimesTraceOuterClass.EndStatus getEndStatus() {
                return ((TimerMetric) this.instance).getEndStatus();
            }

            public Builder setEndStatus(PrimesTraceOuterClass.EndStatus value) {
                copyOnWrite();
                ((TimerMetric) this.instance).setEndStatus(value);
                return this;
            }

            public Builder clearEndStatus() {
                copyOnWrite();
                ((TimerMetric) this.instance).clearEndStatus();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new TimerMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0000\u0002\f\u0001", new Object[]{"bitField0_", "durationMs_", "endStatus_", PrimesTraceOuterClass.EndStatus.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<TimerMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (TimerMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(TimerMetric.class, DEFAULT_INSTANCE);
        }

        public static TimerMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TimerMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class FrameRateMetric extends GeneratedMessageLite<FrameRateMetric, Builder> implements FrameRateMetricOrBuilder {
        public static final int AVERAGE_FRAMES_PER_SECOND_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final FrameRateMetric DEFAULT_INSTANCE = new FrameRateMetric();
        public static final int DURATION_MS_FIELD_NUMBER = 3;
        public static final int MAX_FRAME_RENDER_TIME_MS_FIELD_NUMBER = 2;
        private static volatile Parser<FrameRateMetric> PARSER;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.FLOAT)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private float averageFramesPerSecond_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int durationMs_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int maxFrameRenderTimeMs_;

        private FrameRateMetric() {
        }

        @Deprecated
        public boolean hasAverageFramesPerSecond() {
            return (this.bitField0_ & 1) != 0;
        }

        @Deprecated
        public float getAverageFramesPerSecond() {
            return this.averageFramesPerSecond_;
        }

        /* access modifiers changed from: private */
        public void setAverageFramesPerSecond(float value) {
            this.bitField0_ |= 1;
            this.averageFramesPerSecond_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAverageFramesPerSecond() {
            this.bitField0_ &= -2;
            this.averageFramesPerSecond_ = 0.0f;
        }

        @Deprecated
        public boolean hasMaxFrameRenderTimeMs() {
            return (this.bitField0_ & 2) != 0;
        }

        @Deprecated
        public int getMaxFrameRenderTimeMs() {
            return this.maxFrameRenderTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setMaxFrameRenderTimeMs(int value) {
            this.bitField0_ |= 2;
            this.maxFrameRenderTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMaxFrameRenderTimeMs() {
            this.bitField0_ &= -3;
            this.maxFrameRenderTimeMs_ = 0;
        }

        @Deprecated
        public boolean hasDurationMs() {
            return (this.bitField0_ & 4) != 0;
        }

        @Deprecated
        public int getDurationMs() {
            return this.durationMs_;
        }

        /* access modifiers changed from: private */
        public void setDurationMs(int value) {
            this.bitField0_ |= 4;
            this.durationMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDurationMs() {
            this.bitField0_ &= -5;
            this.durationMs_ = 0;
        }

        public static FrameRateMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FrameRateMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FrameRateMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FrameRateMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FrameRateMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FrameRateMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FrameRateMetric parseFrom(InputStream input) throws IOException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FrameRateMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FrameRateMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (FrameRateMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FrameRateMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FrameRateMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FrameRateMetric parseFrom(CodedInputStream input) throws IOException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FrameRateMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FrameRateMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FrameRateMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FrameRateMetric, Builder> implements FrameRateMetricOrBuilder {
            private Builder() {
                super(FrameRateMetric.DEFAULT_INSTANCE);
            }

            @Deprecated
            public boolean hasAverageFramesPerSecond() {
                return ((FrameRateMetric) this.instance).hasAverageFramesPerSecond();
            }

            @Deprecated
            public float getAverageFramesPerSecond() {
                return ((FrameRateMetric) this.instance).getAverageFramesPerSecond();
            }

            @Deprecated
            public Builder setAverageFramesPerSecond(float value) {
                copyOnWrite();
                ((FrameRateMetric) this.instance).setAverageFramesPerSecond(value);
                return this;
            }

            @Deprecated
            public Builder clearAverageFramesPerSecond() {
                copyOnWrite();
                ((FrameRateMetric) this.instance).clearAverageFramesPerSecond();
                return this;
            }

            @Deprecated
            public boolean hasMaxFrameRenderTimeMs() {
                return ((FrameRateMetric) this.instance).hasMaxFrameRenderTimeMs();
            }

            @Deprecated
            public int getMaxFrameRenderTimeMs() {
                return ((FrameRateMetric) this.instance).getMaxFrameRenderTimeMs();
            }

            @Deprecated
            public Builder setMaxFrameRenderTimeMs(int value) {
                copyOnWrite();
                ((FrameRateMetric) this.instance).setMaxFrameRenderTimeMs(value);
                return this;
            }

            @Deprecated
            public Builder clearMaxFrameRenderTimeMs() {
                copyOnWrite();
                ((FrameRateMetric) this.instance).clearMaxFrameRenderTimeMs();
                return this;
            }

            @Deprecated
            public boolean hasDurationMs() {
                return ((FrameRateMetric) this.instance).hasDurationMs();
            }

            @Deprecated
            public int getDurationMs() {
                return ((FrameRateMetric) this.instance).getDurationMs();
            }

            @Deprecated
            public Builder setDurationMs(int value) {
                copyOnWrite();
                ((FrameRateMetric) this.instance).setDurationMs(value);
                return this;
            }

            @Deprecated
            public Builder clearDurationMs() {
                copyOnWrite();
                ((FrameRateMetric) this.instance).clearDurationMs();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FrameRateMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0001\u0000\u0002\u0004\u0001\u0003\u0004\u0002", new Object[]{"bitField0_", "averageFramesPerSecond_", "maxFrameRenderTimeMs_", "durationMs_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FrameRateMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (FrameRateMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(FrameRateMetric.class, DEFAULT_INSTANCE);
        }

        public static FrameRateMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FrameRateMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ApplicationInfo extends GeneratedMessageLite<ApplicationInfo, Builder> implements ApplicationInfoOrBuilder {
        public static final int APPLICATION_PACKAGE_FIELD_NUMBER = 1;
        public static final int APPLICATION_VERSION_NAME_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final ApplicationInfo DEFAULT_INSTANCE = new ApplicationInfo();
        public static final int HARDWARE_VARIANT_FIELD_NUMBER = 3;
        private static volatile Parser<ApplicationInfo> PARSER = null;
        public static final int PRIMES_VERSION_FIELD_NUMBER = 4;
        public static final int SHORT_PROCESS_NAME_FIELD_NUMBER = 5;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String applicationPackage_ = "";
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String applicationVersionName_ = "";
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int hardwareVariant_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private long primesVersion_;
        @ProtoField(fieldNumber = 5, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private String shortProcessName_ = "";

        private ApplicationInfo() {
        }

        public enum HardwareVariant implements Internal.EnumLite {
            UNKNOWN_HARDWARE_VARIANT(0),
            PHONE_OR_TABLET(1),
            WATCH(2),
            LEANBACK(3);
            
            public static final int LEANBACK_VALUE = 3;
            public static final int PHONE_OR_TABLET_VALUE = 1;
            public static final int UNKNOWN_HARDWARE_VARIANT_VALUE = 0;
            public static final int WATCH_VALUE = 2;
            private static final Internal.EnumLiteMap<HardwareVariant> internalValueMap = new Internal.EnumLiteMap<HardwareVariant>() {
                public HardwareVariant findValueByNumber(int number) {
                    return HardwareVariant.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static HardwareVariant forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN_HARDWARE_VARIANT;
                }
                if (value2 == 1) {
                    return PHONE_OR_TABLET;
                }
                if (value2 == 2) {
                    return WATCH;
                }
                if (value2 != 3) {
                    return null;
                }
                return LEANBACK;
            }

            public static Internal.EnumLiteMap<HardwareVariant> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return HardwareVariantVerifier.INSTANCE;
            }

            private static final class HardwareVariantVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new HardwareVariantVerifier();

                private HardwareVariantVerifier() {
                }

                public boolean isInRange(int number) {
                    return HardwareVariant.forNumber(number) != null;
                }
            }

            private HardwareVariant(int value2) {
                this.value = value2;
            }
        }

        public boolean hasApplicationPackage() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getApplicationPackage() {
            return this.applicationPackage_;
        }

        public ByteString getApplicationPackageBytes() {
            return ByteString.copyFromUtf8(this.applicationPackage_);
        }

        /* access modifiers changed from: private */
        public void setApplicationPackage(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.applicationPackage_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearApplicationPackage() {
            this.bitField0_ &= -2;
            this.applicationPackage_ = getDefaultInstance().getApplicationPackage();
        }

        /* access modifiers changed from: private */
        public void setApplicationPackageBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.applicationPackage_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasApplicationVersionName() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getApplicationVersionName() {
            return this.applicationVersionName_;
        }

        public ByteString getApplicationVersionNameBytes() {
            return ByteString.copyFromUtf8(this.applicationVersionName_);
        }

        /* access modifiers changed from: private */
        public void setApplicationVersionName(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.applicationVersionName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearApplicationVersionName() {
            this.bitField0_ &= -3;
            this.applicationVersionName_ = getDefaultInstance().getApplicationVersionName();
        }

        /* access modifiers changed from: private */
        public void setApplicationVersionNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.applicationVersionName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasHardwareVariant() {
            return (this.bitField0_ & 4) != 0;
        }

        public HardwareVariant getHardwareVariant() {
            HardwareVariant result = HardwareVariant.forNumber(this.hardwareVariant_);
            return result == null ? HardwareVariant.UNKNOWN_HARDWARE_VARIANT : result;
        }

        /* access modifiers changed from: private */
        public void setHardwareVariant(HardwareVariant value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.hardwareVariant_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearHardwareVariant() {
            this.bitField0_ &= -5;
            this.hardwareVariant_ = 0;
        }

        public boolean hasPrimesVersion() {
            return (this.bitField0_ & 8) != 0;
        }

        public long getPrimesVersion() {
            return this.primesVersion_;
        }

        /* access modifiers changed from: private */
        public void setPrimesVersion(long value) {
            this.bitField0_ |= 8;
            this.primesVersion_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPrimesVersion() {
            this.bitField0_ &= -9;
            this.primesVersion_ = 0;
        }

        public boolean hasShortProcessName() {
            return (this.bitField0_ & 16) != 0;
        }

        public String getShortProcessName() {
            return this.shortProcessName_;
        }

        public ByteString getShortProcessNameBytes() {
            return ByteString.copyFromUtf8(this.shortProcessName_);
        }

        /* access modifiers changed from: private */
        public void setShortProcessName(String value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.shortProcessName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearShortProcessName() {
            this.bitField0_ &= -17;
            this.shortProcessName_ = getDefaultInstance().getShortProcessName();
        }

        /* access modifiers changed from: private */
        public void setShortProcessNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.shortProcessName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static ApplicationInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ApplicationInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ApplicationInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ApplicationInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ApplicationInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ApplicationInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ApplicationInfo parseFrom(InputStream input) throws IOException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ApplicationInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ApplicationInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (ApplicationInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ApplicationInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ApplicationInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ApplicationInfo parseFrom(CodedInputStream input) throws IOException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ApplicationInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ApplicationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ApplicationInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ApplicationInfo, Builder> implements ApplicationInfoOrBuilder {
            private Builder() {
                super(ApplicationInfo.DEFAULT_INSTANCE);
            }

            public boolean hasApplicationPackage() {
                return ((ApplicationInfo) this.instance).hasApplicationPackage();
            }

            public String getApplicationPackage() {
                return ((ApplicationInfo) this.instance).getApplicationPackage();
            }

            public ByteString getApplicationPackageBytes() {
                return ((ApplicationInfo) this.instance).getApplicationPackageBytes();
            }

            public Builder setApplicationPackage(String value) {
                copyOnWrite();
                ((ApplicationInfo) this.instance).setApplicationPackage(value);
                return this;
            }

            public Builder clearApplicationPackage() {
                copyOnWrite();
                ((ApplicationInfo) this.instance).clearApplicationPackage();
                return this;
            }

            public Builder setApplicationPackageBytes(ByteString value) {
                copyOnWrite();
                ((ApplicationInfo) this.instance).setApplicationPackageBytes(value);
                return this;
            }

            public boolean hasApplicationVersionName() {
                return ((ApplicationInfo) this.instance).hasApplicationVersionName();
            }

            public String getApplicationVersionName() {
                return ((ApplicationInfo) this.instance).getApplicationVersionName();
            }

            public ByteString getApplicationVersionNameBytes() {
                return ((ApplicationInfo) this.instance).getApplicationVersionNameBytes();
            }

            public Builder setApplicationVersionName(String value) {
                copyOnWrite();
                ((ApplicationInfo) this.instance).setApplicationVersionName(value);
                return this;
            }

            public Builder clearApplicationVersionName() {
                copyOnWrite();
                ((ApplicationInfo) this.instance).clearApplicationVersionName();
                return this;
            }

            public Builder setApplicationVersionNameBytes(ByteString value) {
                copyOnWrite();
                ((ApplicationInfo) this.instance).setApplicationVersionNameBytes(value);
                return this;
            }

            public boolean hasHardwareVariant() {
                return ((ApplicationInfo) this.instance).hasHardwareVariant();
            }

            public HardwareVariant getHardwareVariant() {
                return ((ApplicationInfo) this.instance).getHardwareVariant();
            }

            public Builder setHardwareVariant(HardwareVariant value) {
                copyOnWrite();
                ((ApplicationInfo) this.instance).setHardwareVariant(value);
                return this;
            }

            public Builder clearHardwareVariant() {
                copyOnWrite();
                ((ApplicationInfo) this.instance).clearHardwareVariant();
                return this;
            }

            public boolean hasPrimesVersion() {
                return ((ApplicationInfo) this.instance).hasPrimesVersion();
            }

            public long getPrimesVersion() {
                return ((ApplicationInfo) this.instance).getPrimesVersion();
            }

            public Builder setPrimesVersion(long value) {
                copyOnWrite();
                ((ApplicationInfo) this.instance).setPrimesVersion(value);
                return this;
            }

            public Builder clearPrimesVersion() {
                copyOnWrite();
                ((ApplicationInfo) this.instance).clearPrimesVersion();
                return this;
            }

            public boolean hasShortProcessName() {
                return ((ApplicationInfo) this.instance).hasShortProcessName();
            }

            public String getShortProcessName() {
                return ((ApplicationInfo) this.instance).getShortProcessName();
            }

            public ByteString getShortProcessNameBytes() {
                return ((ApplicationInfo) this.instance).getShortProcessNameBytes();
            }

            public Builder setShortProcessName(String value) {
                copyOnWrite();
                ((ApplicationInfo) this.instance).setShortProcessName(value);
                return this;
            }

            public Builder clearShortProcessName() {
                copyOnWrite();
                ((ApplicationInfo) this.instance).clearShortProcessName();
                return this;
            }

            public Builder setShortProcessNameBytes(ByteString value) {
                copyOnWrite();
                ((ApplicationInfo) this.instance).setShortProcessNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ApplicationInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\f\u0002\u0004\u0002\u0003\u0005\b\u0004", new Object[]{"bitField0_", "applicationPackage_", "applicationVersionName_", "hardwareVariant_", HardwareVariant.internalGetVerifier(), "primesVersion_", "shortProcessName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ApplicationInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (ApplicationInfo.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(ApplicationInfo.class, DEFAULT_INSTANCE);
        }

        public static ApplicationInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ApplicationInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ClientErrorLoggingMetric extends GeneratedMessageLite<ClientErrorLoggingMetric, Builder> implements ClientErrorLoggingMetricOrBuilder {
        public static final int ACTIVE_COMPONENT_NAME_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final ClientErrorLoggingMetric DEFAULT_INSTANCE = new ClientErrorLoggingMetric();
        public static final int ERROR_MESSAGE_FIELD_NUMBER = 1;
        private static volatile Parser<ClientErrorLoggingMetric> PARSER = null;
        public static final int PROCESS_STATS_FIELD_NUMBER = 2;
        public static final int THREAD_NAME_FIELD_NUMBER = 4;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String activeComponentName_ = "";
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private ErrorMessageOuterClass.ErrorMessage errorMessage_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private ProcessProto.ProcessStats processStats_;
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private String threadName_ = "";

        private ClientErrorLoggingMetric() {
        }

        public boolean hasErrorMessage() {
            return (this.bitField0_ & 1) != 0;
        }

        public ErrorMessageOuterClass.ErrorMessage getErrorMessage() {
            ErrorMessageOuterClass.ErrorMessage errorMessage = this.errorMessage_;
            return errorMessage == null ? ErrorMessageOuterClass.ErrorMessage.getDefaultInstance() : errorMessage;
        }

        /* access modifiers changed from: private */
        public void setErrorMessage(ErrorMessageOuterClass.ErrorMessage value) {
            if (value != null) {
                this.errorMessage_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setErrorMessage(ErrorMessageOuterClass.ErrorMessage.Builder builderForValue) {
            this.errorMessage_ = (ErrorMessageOuterClass.ErrorMessage) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeErrorMessage(ErrorMessageOuterClass.ErrorMessage value) {
            if (value != null) {
                ErrorMessageOuterClass.ErrorMessage errorMessage = this.errorMessage_;
                if (errorMessage == null || errorMessage == ErrorMessageOuterClass.ErrorMessage.getDefaultInstance()) {
                    this.errorMessage_ = value;
                } else {
                    this.errorMessage_ = (ErrorMessageOuterClass.ErrorMessage) ((ErrorMessageOuterClass.ErrorMessage.Builder) ErrorMessageOuterClass.ErrorMessage.newBuilder(this.errorMessage_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearErrorMessage() {
            this.errorMessage_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasProcessStats() {
            return (this.bitField0_ & 2) != 0;
        }

        public ProcessProto.ProcessStats getProcessStats() {
            ProcessProto.ProcessStats processStats = this.processStats_;
            return processStats == null ? ProcessProto.ProcessStats.getDefaultInstance() : processStats;
        }

        /* access modifiers changed from: private */
        public void setProcessStats(ProcessProto.ProcessStats value) {
            if (value != null) {
                this.processStats_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStats(ProcessProto.ProcessStats.Builder builderForValue) {
            this.processStats_ = (ProcessProto.ProcessStats) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStats(ProcessProto.ProcessStats value) {
            if (value != null) {
                ProcessProto.ProcessStats processStats = this.processStats_;
                if (processStats == null || processStats == ProcessProto.ProcessStats.getDefaultInstance()) {
                    this.processStats_ = value;
                } else {
                    this.processStats_ = (ProcessProto.ProcessStats) ((ProcessProto.ProcessStats.Builder) ProcessProto.ProcessStats.newBuilder(this.processStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStats() {
            this.processStats_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasActiveComponentName() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getActiveComponentName() {
            return this.activeComponentName_;
        }

        public ByteString getActiveComponentNameBytes() {
            return ByteString.copyFromUtf8(this.activeComponentName_);
        }

        /* access modifiers changed from: private */
        public void setActiveComponentName(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.activeComponentName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearActiveComponentName() {
            this.bitField0_ &= -5;
            this.activeComponentName_ = getDefaultInstance().getActiveComponentName();
        }

        /* access modifiers changed from: private */
        public void setActiveComponentNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.activeComponentName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasThreadName() {
            return (this.bitField0_ & 8) != 0;
        }

        public String getThreadName() {
            return this.threadName_;
        }

        public ByteString getThreadNameBytes() {
            return ByteString.copyFromUtf8(this.threadName_);
        }

        /* access modifiers changed from: private */
        public void setThreadName(String value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.threadName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearThreadName() {
            this.bitField0_ &= -9;
            this.threadName_ = getDefaultInstance().getThreadName();
        }

        /* access modifiers changed from: private */
        public void setThreadNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.threadName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static ClientErrorLoggingMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClientErrorLoggingMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClientErrorLoggingMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClientErrorLoggingMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClientErrorLoggingMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClientErrorLoggingMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClientErrorLoggingMetric parseFrom(InputStream input) throws IOException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ClientErrorLoggingMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ClientErrorLoggingMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (ClientErrorLoggingMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ClientErrorLoggingMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientErrorLoggingMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ClientErrorLoggingMetric parseFrom(CodedInputStream input) throws IOException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ClientErrorLoggingMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientErrorLoggingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ClientErrorLoggingMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ClientErrorLoggingMetric, Builder> implements ClientErrorLoggingMetricOrBuilder {
            private Builder() {
                super(ClientErrorLoggingMetric.DEFAULT_INSTANCE);
            }

            public boolean hasErrorMessage() {
                return ((ClientErrorLoggingMetric) this.instance).hasErrorMessage();
            }

            public ErrorMessageOuterClass.ErrorMessage getErrorMessage() {
                return ((ClientErrorLoggingMetric) this.instance).getErrorMessage();
            }

            public Builder setErrorMessage(ErrorMessageOuterClass.ErrorMessage value) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).setErrorMessage(value);
                return this;
            }

            public Builder setErrorMessage(ErrorMessageOuterClass.ErrorMessage.Builder builderForValue) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).setErrorMessage(builderForValue);
                return this;
            }

            public Builder mergeErrorMessage(ErrorMessageOuterClass.ErrorMessage value) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).mergeErrorMessage(value);
                return this;
            }

            public Builder clearErrorMessage() {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).clearErrorMessage();
                return this;
            }

            public boolean hasProcessStats() {
                return ((ClientErrorLoggingMetric) this.instance).hasProcessStats();
            }

            public ProcessProto.ProcessStats getProcessStats() {
                return ((ClientErrorLoggingMetric) this.instance).getProcessStats();
            }

            public Builder setProcessStats(ProcessProto.ProcessStats value) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).setProcessStats(value);
                return this;
            }

            public Builder setProcessStats(ProcessProto.ProcessStats.Builder builderForValue) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).setProcessStats(builderForValue);
                return this;
            }

            public Builder mergeProcessStats(ProcessProto.ProcessStats value) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).mergeProcessStats(value);
                return this;
            }

            public Builder clearProcessStats() {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).clearProcessStats();
                return this;
            }

            public boolean hasActiveComponentName() {
                return ((ClientErrorLoggingMetric) this.instance).hasActiveComponentName();
            }

            public String getActiveComponentName() {
                return ((ClientErrorLoggingMetric) this.instance).getActiveComponentName();
            }

            public ByteString getActiveComponentNameBytes() {
                return ((ClientErrorLoggingMetric) this.instance).getActiveComponentNameBytes();
            }

            public Builder setActiveComponentName(String value) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).setActiveComponentName(value);
                return this;
            }

            public Builder clearActiveComponentName() {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).clearActiveComponentName();
                return this;
            }

            public Builder setActiveComponentNameBytes(ByteString value) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).setActiveComponentNameBytes(value);
                return this;
            }

            public boolean hasThreadName() {
                return ((ClientErrorLoggingMetric) this.instance).hasThreadName();
            }

            public String getThreadName() {
                return ((ClientErrorLoggingMetric) this.instance).getThreadName();
            }

            public ByteString getThreadNameBytes() {
                return ((ClientErrorLoggingMetric) this.instance).getThreadNameBytes();
            }

            public Builder setThreadName(String value) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).setThreadName(value);
                return this;
            }

            public Builder clearThreadName() {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).clearThreadName();
                return this;
            }

            public Builder setThreadNameBytes(ByteString value) {
                copyOnWrite();
                ((ClientErrorLoggingMetric) this.instance).setThreadNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ClientErrorLoggingMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\b\u0002\u0004\b\u0003", new Object[]{"bitField0_", "errorMessage_", "processStats_", "activeComponentName_", "threadName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ClientErrorLoggingMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (ClientErrorLoggingMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(ClientErrorLoggingMetric.class, DEFAULT_INSTANCE);
        }

        public static ClientErrorLoggingMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ClientErrorLoggingMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CrashMetric extends GeneratedMessageLite<CrashMetric, Builder> implements CrashMetricOrBuilder {
        public static final int ACTIVE_COMPONENT_NAME_FIELD_NUMBER = 3;
        public static final int CRASH_CLASS_NAME_FIELD_NUMBER = 7;
        public static final int CRASH_TYPE_FIELD_NUMBER = 5;
        /* access modifiers changed from: private */
        public static final CrashMetric DEFAULT_INSTANCE = new CrashMetric();
        public static final int HASHED_STACK_TRACE_FIELD_NUMBER = 6;
        public static final int HAS_CRASHED_FIELD_NUMBER = 1;
        private static volatile Parser<CrashMetric> PARSER = null;
        public static final int PROCESS_STATS_FIELD_NUMBER = 2;
        public static final int THREAD_NAME_FIELD_NUMBER = 4;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String activeComponentName_ = "";
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 7, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private String crashClassName_ = "";
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int crashType_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean hasCrashed_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private long hashedStackTrace_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private ProcessProto.ProcessStats processStats_;
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private String threadName_ = "";

        private CrashMetric() {
        }

        public enum CrashType implements Internal.EnumLite {
            UNKNOWN(0),
            NULL_POINTER_EXCEPTION(1),
            OUT_OF_MEMORY_ERROR(2),
            OTHER_RUNTIME_EXCEPTION(3),
            OTHER_ERROR(4);
            
            public static final int NULL_POINTER_EXCEPTION_VALUE = 1;
            public static final int OTHER_ERROR_VALUE = 4;
            public static final int OTHER_RUNTIME_EXCEPTION_VALUE = 3;
            public static final int OUT_OF_MEMORY_ERROR_VALUE = 2;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<CrashType> internalValueMap = new Internal.EnumLiteMap<CrashType>() {
                public CrashType findValueByNumber(int number) {
                    return CrashType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static CrashType forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return NULL_POINTER_EXCEPTION;
                }
                if (value2 == 2) {
                    return OUT_OF_MEMORY_ERROR;
                }
                if (value2 == 3) {
                    return OTHER_RUNTIME_EXCEPTION;
                }
                if (value2 != 4) {
                    return null;
                }
                return OTHER_ERROR;
            }

            public static Internal.EnumLiteMap<CrashType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return CrashTypeVerifier.INSTANCE;
            }

            private static final class CrashTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new CrashTypeVerifier();

                private CrashTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return CrashType.forNumber(number) != null;
                }
            }

            private CrashType(int value2) {
                this.value = value2;
            }
        }

        public boolean hasHasCrashed() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getHasCrashed() {
            return this.hasCrashed_;
        }

        /* access modifiers changed from: private */
        public void setHasCrashed(boolean value) {
            this.bitField0_ |= 1;
            this.hasCrashed_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasCrashed() {
            this.bitField0_ &= -2;
            this.hasCrashed_ = false;
        }

        public boolean hasProcessStats() {
            return (this.bitField0_ & 2) != 0;
        }

        public ProcessProto.ProcessStats getProcessStats() {
            ProcessProto.ProcessStats processStats = this.processStats_;
            return processStats == null ? ProcessProto.ProcessStats.getDefaultInstance() : processStats;
        }

        /* access modifiers changed from: private */
        public void setProcessStats(ProcessProto.ProcessStats value) {
            if (value != null) {
                this.processStats_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStats(ProcessProto.ProcessStats.Builder builderForValue) {
            this.processStats_ = (ProcessProto.ProcessStats) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStats(ProcessProto.ProcessStats value) {
            if (value != null) {
                ProcessProto.ProcessStats processStats = this.processStats_;
                if (processStats == null || processStats == ProcessProto.ProcessStats.getDefaultInstance()) {
                    this.processStats_ = value;
                } else {
                    this.processStats_ = (ProcessProto.ProcessStats) ((ProcessProto.ProcessStats.Builder) ProcessProto.ProcessStats.newBuilder(this.processStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStats() {
            this.processStats_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasActiveComponentName() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getActiveComponentName() {
            return this.activeComponentName_;
        }

        public ByteString getActiveComponentNameBytes() {
            return ByteString.copyFromUtf8(this.activeComponentName_);
        }

        /* access modifiers changed from: private */
        public void setActiveComponentName(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.activeComponentName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearActiveComponentName() {
            this.bitField0_ &= -5;
            this.activeComponentName_ = getDefaultInstance().getActiveComponentName();
        }

        /* access modifiers changed from: private */
        public void setActiveComponentNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.activeComponentName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasThreadName() {
            return (this.bitField0_ & 8) != 0;
        }

        public String getThreadName() {
            return this.threadName_;
        }

        public ByteString getThreadNameBytes() {
            return ByteString.copyFromUtf8(this.threadName_);
        }

        /* access modifiers changed from: private */
        public void setThreadName(String value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.threadName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearThreadName() {
            this.bitField0_ &= -9;
            this.threadName_ = getDefaultInstance().getThreadName();
        }

        /* access modifiers changed from: private */
        public void setThreadNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.threadName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasCrashType() {
            return (this.bitField0_ & 16) != 0;
        }

        public CrashType getCrashType() {
            CrashType result = CrashType.forNumber(this.crashType_);
            return result == null ? CrashType.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setCrashType(CrashType value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.crashType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCrashType() {
            this.bitField0_ &= -17;
            this.crashType_ = 0;
        }

        public boolean hasHashedStackTrace() {
            return (this.bitField0_ & 32) != 0;
        }

        public long getHashedStackTrace() {
            return this.hashedStackTrace_;
        }

        /* access modifiers changed from: private */
        public void setHashedStackTrace(long value) {
            this.bitField0_ |= 32;
            this.hashedStackTrace_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHashedStackTrace() {
            this.bitField0_ &= -33;
            this.hashedStackTrace_ = 0;
        }

        public boolean hasCrashClassName() {
            return (this.bitField0_ & 64) != 0;
        }

        public String getCrashClassName() {
            return this.crashClassName_;
        }

        public ByteString getCrashClassNameBytes() {
            return ByteString.copyFromUtf8(this.crashClassName_);
        }

        /* access modifiers changed from: private */
        public void setCrashClassName(String value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.crashClassName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCrashClassName() {
            this.bitField0_ &= -65;
            this.crashClassName_ = getDefaultInstance().getCrashClassName();
        }

        /* access modifiers changed from: private */
        public void setCrashClassNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.crashClassName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static CrashMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CrashMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CrashMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CrashMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CrashMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CrashMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CrashMetric parseFrom(InputStream input) throws IOException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CrashMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CrashMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (CrashMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CrashMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CrashMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CrashMetric parseFrom(CodedInputStream input) throws IOException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CrashMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CrashMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CrashMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<CrashMetric, Builder> implements CrashMetricOrBuilder {
            private Builder() {
                super(CrashMetric.DEFAULT_INSTANCE);
            }

            public boolean hasHasCrashed() {
                return ((CrashMetric) this.instance).hasHasCrashed();
            }

            public boolean getHasCrashed() {
                return ((CrashMetric) this.instance).getHasCrashed();
            }

            public Builder setHasCrashed(boolean value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setHasCrashed(value);
                return this;
            }

            public Builder clearHasCrashed() {
                copyOnWrite();
                ((CrashMetric) this.instance).clearHasCrashed();
                return this;
            }

            public boolean hasProcessStats() {
                return ((CrashMetric) this.instance).hasProcessStats();
            }

            public ProcessProto.ProcessStats getProcessStats() {
                return ((CrashMetric) this.instance).getProcessStats();
            }

            public Builder setProcessStats(ProcessProto.ProcessStats value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setProcessStats(value);
                return this;
            }

            public Builder setProcessStats(ProcessProto.ProcessStats.Builder builderForValue) {
                copyOnWrite();
                ((CrashMetric) this.instance).setProcessStats(builderForValue);
                return this;
            }

            public Builder mergeProcessStats(ProcessProto.ProcessStats value) {
                copyOnWrite();
                ((CrashMetric) this.instance).mergeProcessStats(value);
                return this;
            }

            public Builder clearProcessStats() {
                copyOnWrite();
                ((CrashMetric) this.instance).clearProcessStats();
                return this;
            }

            public boolean hasActiveComponentName() {
                return ((CrashMetric) this.instance).hasActiveComponentName();
            }

            public String getActiveComponentName() {
                return ((CrashMetric) this.instance).getActiveComponentName();
            }

            public ByteString getActiveComponentNameBytes() {
                return ((CrashMetric) this.instance).getActiveComponentNameBytes();
            }

            public Builder setActiveComponentName(String value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setActiveComponentName(value);
                return this;
            }

            public Builder clearActiveComponentName() {
                copyOnWrite();
                ((CrashMetric) this.instance).clearActiveComponentName();
                return this;
            }

            public Builder setActiveComponentNameBytes(ByteString value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setActiveComponentNameBytes(value);
                return this;
            }

            public boolean hasThreadName() {
                return ((CrashMetric) this.instance).hasThreadName();
            }

            public String getThreadName() {
                return ((CrashMetric) this.instance).getThreadName();
            }

            public ByteString getThreadNameBytes() {
                return ((CrashMetric) this.instance).getThreadNameBytes();
            }

            public Builder setThreadName(String value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setThreadName(value);
                return this;
            }

            public Builder clearThreadName() {
                copyOnWrite();
                ((CrashMetric) this.instance).clearThreadName();
                return this;
            }

            public Builder setThreadNameBytes(ByteString value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setThreadNameBytes(value);
                return this;
            }

            public boolean hasCrashType() {
                return ((CrashMetric) this.instance).hasCrashType();
            }

            public CrashType getCrashType() {
                return ((CrashMetric) this.instance).getCrashType();
            }

            public Builder setCrashType(CrashType value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setCrashType(value);
                return this;
            }

            public Builder clearCrashType() {
                copyOnWrite();
                ((CrashMetric) this.instance).clearCrashType();
                return this;
            }

            public boolean hasHashedStackTrace() {
                return ((CrashMetric) this.instance).hasHashedStackTrace();
            }

            public long getHashedStackTrace() {
                return ((CrashMetric) this.instance).getHashedStackTrace();
            }

            public Builder setHashedStackTrace(long value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setHashedStackTrace(value);
                return this;
            }

            public Builder clearHashedStackTrace() {
                copyOnWrite();
                ((CrashMetric) this.instance).clearHashedStackTrace();
                return this;
            }

            public boolean hasCrashClassName() {
                return ((CrashMetric) this.instance).hasCrashClassName();
            }

            public String getCrashClassName() {
                return ((CrashMetric) this.instance).getCrashClassName();
            }

            public ByteString getCrashClassNameBytes() {
                return ((CrashMetric) this.instance).getCrashClassNameBytes();
            }

            public Builder setCrashClassName(String value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setCrashClassName(value);
                return this;
            }

            public Builder clearCrashClassName() {
                copyOnWrite();
                ((CrashMetric) this.instance).clearCrashClassName();
                return this;
            }

            public Builder setCrashClassNameBytes(ByteString value) {
                copyOnWrite();
                ((CrashMetric) this.instance).setCrashClassNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CrashMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001\u0007\u0000\u0002\t\u0001\u0003\b\u0002\u0004\b\u0003\u0005\f\u0004\u0006\u0005\u0005\u0007\b\u0006", new Object[]{"bitField0_", "hasCrashed_", "processStats_", "activeComponentName_", "threadName_", "crashType_", CrashType.internalGetVerifier(), "hashedStackTrace_", "crashClassName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CrashMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (CrashMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(CrashMetric.class, DEFAULT_INSTANCE);
        }

        public static CrashMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CrashMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MemoryLeakMetric extends GeneratedMessageLite<MemoryLeakMetric, Builder> implements MemoryLeakMetricOrBuilder {
        /* access modifiers changed from: private */
        public static final MemoryLeakMetric DEFAULT_INSTANCE = new MemoryLeakMetric();
        public static final int OBJECT_INFO_FIELD_NUMBER = 1;
        private static volatile Parser<MemoryLeakMetric> PARSER;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ObjectInfo> objectInfo_ = emptyProtobufList();

        private MemoryLeakMetric() {
        }

        public List<ObjectInfo> getObjectInfoList() {
            return this.objectInfo_;
        }

        public List<? extends ObjectInfoOrBuilder> getObjectInfoOrBuilderList() {
            return this.objectInfo_;
        }

        public int getObjectInfoCount() {
            return this.objectInfo_.size();
        }

        public ObjectInfo getObjectInfo(int index) {
            return this.objectInfo_.get(index);
        }

        public ObjectInfoOrBuilder getObjectInfoOrBuilder(int index) {
            return this.objectInfo_.get(index);
        }

        private void ensureObjectInfoIsMutable() {
            if (!this.objectInfo_.isModifiable()) {
                this.objectInfo_ = GeneratedMessageLite.mutableCopy(this.objectInfo_);
            }
        }

        /* access modifiers changed from: private */
        public void setObjectInfo(int index, ObjectInfo value) {
            if (value != null) {
                ensureObjectInfoIsMutable();
                this.objectInfo_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setObjectInfo(int index, ObjectInfo.Builder builderForValue) {
            ensureObjectInfoIsMutable();
            this.objectInfo_.set(index, (ObjectInfo) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addObjectInfo(ObjectInfo value) {
            if (value != null) {
                ensureObjectInfoIsMutable();
                this.objectInfo_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addObjectInfo(int index, ObjectInfo value) {
            if (value != null) {
                ensureObjectInfoIsMutable();
                this.objectInfo_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addObjectInfo(ObjectInfo.Builder builderForValue) {
            ensureObjectInfoIsMutable();
            this.objectInfo_.add((ObjectInfo) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addObjectInfo(int index, ObjectInfo.Builder builderForValue) {
            ensureObjectInfoIsMutable();
            this.objectInfo_.add(index, (ObjectInfo) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.SystemHealthProto$ObjectInfo>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.SystemHealthProto$ObjectInfo>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllObjectInfo(Iterable<? extends ObjectInfo> values) {
            ensureObjectInfoIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.objectInfo_);
        }

        /* access modifiers changed from: private */
        public void clearObjectInfo() {
            this.objectInfo_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeObjectInfo(int index) {
            ensureObjectInfoIsMutable();
            this.objectInfo_.remove(index);
        }

        public static MemoryLeakMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemoryLeakMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemoryLeakMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemoryLeakMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemoryLeakMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemoryLeakMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemoryLeakMetric parseFrom(InputStream input) throws IOException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MemoryLeakMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MemoryLeakMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (MemoryLeakMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MemoryLeakMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemoryLeakMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MemoryLeakMetric parseFrom(CodedInputStream input) throws IOException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MemoryLeakMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemoryLeakMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MemoryLeakMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<MemoryLeakMetric, Builder> implements MemoryLeakMetricOrBuilder {
            private Builder() {
                super(MemoryLeakMetric.DEFAULT_INSTANCE);
            }

            public List<ObjectInfo> getObjectInfoList() {
                return Collections.unmodifiableList(((MemoryLeakMetric) this.instance).getObjectInfoList());
            }

            public int getObjectInfoCount() {
                return ((MemoryLeakMetric) this.instance).getObjectInfoCount();
            }

            public ObjectInfo getObjectInfo(int index) {
                return ((MemoryLeakMetric) this.instance).getObjectInfo(index);
            }

            public Builder setObjectInfo(int index, ObjectInfo value) {
                copyOnWrite();
                ((MemoryLeakMetric) this.instance).setObjectInfo(index, value);
                return this;
            }

            public Builder setObjectInfo(int index, ObjectInfo.Builder builderForValue) {
                copyOnWrite();
                ((MemoryLeakMetric) this.instance).setObjectInfo(index, builderForValue);
                return this;
            }

            public Builder addObjectInfo(ObjectInfo value) {
                copyOnWrite();
                ((MemoryLeakMetric) this.instance).addObjectInfo(value);
                return this;
            }

            public Builder addObjectInfo(int index, ObjectInfo value) {
                copyOnWrite();
                ((MemoryLeakMetric) this.instance).addObjectInfo(index, value);
                return this;
            }

            public Builder addObjectInfo(ObjectInfo.Builder builderForValue) {
                copyOnWrite();
                ((MemoryLeakMetric) this.instance).addObjectInfo(builderForValue);
                return this;
            }

            public Builder addObjectInfo(int index, ObjectInfo.Builder builderForValue) {
                copyOnWrite();
                ((MemoryLeakMetric) this.instance).addObjectInfo(index, builderForValue);
                return this;
            }

            public Builder addAllObjectInfo(Iterable<? extends ObjectInfo> values) {
                copyOnWrite();
                ((MemoryLeakMetric) this.instance).addAllObjectInfo(values);
                return this;
            }

            public Builder clearObjectInfo() {
                copyOnWrite();
                ((MemoryLeakMetric) this.instance).clearObjectInfo();
                return this;
            }

            public Builder removeObjectInfo(int index) {
                copyOnWrite();
                ((MemoryLeakMetric) this.instance).removeObjectInfo(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MemoryLeakMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"objectInfo_", ObjectInfo.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MemoryLeakMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (MemoryLeakMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(MemoryLeakMetric.class, DEFAULT_INSTANCE);
        }

        public static MemoryLeakMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MemoryLeakMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ObjectInfo extends GeneratedMessageLite<ObjectInfo, Builder> implements ObjectInfoOrBuilder {
        public static final int CLASS_NAME_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final ObjectInfo DEFAULT_INSTANCE = new ObjectInfo();
        public static final int LEAKED_COUNT_FIELD_NUMBER = 3;
        public static final int LEAK_PATH_FIELD_NUMBER = 4;
        private static volatile Parser<ObjectInfo> PARSER = null;
        public static final int RELEASED_COUNT_FIELD_NUMBER = 2;
        public static final int RETAINED_HEAP_BYTES_FIELD_NUMBER = 5;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String className_ = "";
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private String leakPath_ = "";
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int leakedCount_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int releasedCount_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int retainedHeapBytes_;

        private ObjectInfo() {
        }

        public boolean hasClassName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getClassName() {
            return this.className_;
        }

        public ByteString getClassNameBytes() {
            return ByteString.copyFromUtf8(this.className_);
        }

        /* access modifiers changed from: private */
        public void setClassName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.className_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearClassName() {
            this.bitField0_ &= -2;
            this.className_ = getDefaultInstance().getClassName();
        }

        /* access modifiers changed from: private */
        public void setClassNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.className_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasReleasedCount() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getReleasedCount() {
            return this.releasedCount_;
        }

        /* access modifiers changed from: private */
        public void setReleasedCount(int value) {
            this.bitField0_ |= 2;
            this.releasedCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearReleasedCount() {
            this.bitField0_ &= -3;
            this.releasedCount_ = 0;
        }

        public boolean hasLeakedCount() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getLeakedCount() {
            return this.leakedCount_;
        }

        /* access modifiers changed from: private */
        public void setLeakedCount(int value) {
            this.bitField0_ |= 4;
            this.leakedCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLeakedCount() {
            this.bitField0_ &= -5;
            this.leakedCount_ = 0;
        }

        public boolean hasLeakPath() {
            return (this.bitField0_ & 8) != 0;
        }

        public String getLeakPath() {
            return this.leakPath_;
        }

        public ByteString getLeakPathBytes() {
            return ByteString.copyFromUtf8(this.leakPath_);
        }

        /* access modifiers changed from: private */
        public void setLeakPath(String value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.leakPath_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLeakPath() {
            this.bitField0_ &= -9;
            this.leakPath_ = getDefaultInstance().getLeakPath();
        }

        /* access modifiers changed from: private */
        public void setLeakPathBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.leakPath_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasRetainedHeapBytes() {
            return (this.bitField0_ & 16) != 0;
        }

        public int getRetainedHeapBytes() {
            return this.retainedHeapBytes_;
        }

        /* access modifiers changed from: private */
        public void setRetainedHeapBytes(int value) {
            this.bitField0_ |= 16;
            this.retainedHeapBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRetainedHeapBytes() {
            this.bitField0_ &= -17;
            this.retainedHeapBytes_ = 0;
        }

        public static ObjectInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ObjectInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ObjectInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ObjectInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ObjectInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ObjectInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ObjectInfo parseFrom(InputStream input) throws IOException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ObjectInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ObjectInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (ObjectInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ObjectInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ObjectInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ObjectInfo parseFrom(CodedInputStream input) throws IOException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ObjectInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ObjectInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ObjectInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ObjectInfo, Builder> implements ObjectInfoOrBuilder {
            private Builder() {
                super(ObjectInfo.DEFAULT_INSTANCE);
            }

            public boolean hasClassName() {
                return ((ObjectInfo) this.instance).hasClassName();
            }

            public String getClassName() {
                return ((ObjectInfo) this.instance).getClassName();
            }

            public ByteString getClassNameBytes() {
                return ((ObjectInfo) this.instance).getClassNameBytes();
            }

            public Builder setClassName(String value) {
                copyOnWrite();
                ((ObjectInfo) this.instance).setClassName(value);
                return this;
            }

            public Builder clearClassName() {
                copyOnWrite();
                ((ObjectInfo) this.instance).clearClassName();
                return this;
            }

            public Builder setClassNameBytes(ByteString value) {
                copyOnWrite();
                ((ObjectInfo) this.instance).setClassNameBytes(value);
                return this;
            }

            public boolean hasReleasedCount() {
                return ((ObjectInfo) this.instance).hasReleasedCount();
            }

            public int getReleasedCount() {
                return ((ObjectInfo) this.instance).getReleasedCount();
            }

            public Builder setReleasedCount(int value) {
                copyOnWrite();
                ((ObjectInfo) this.instance).setReleasedCount(value);
                return this;
            }

            public Builder clearReleasedCount() {
                copyOnWrite();
                ((ObjectInfo) this.instance).clearReleasedCount();
                return this;
            }

            public boolean hasLeakedCount() {
                return ((ObjectInfo) this.instance).hasLeakedCount();
            }

            public int getLeakedCount() {
                return ((ObjectInfo) this.instance).getLeakedCount();
            }

            public Builder setLeakedCount(int value) {
                copyOnWrite();
                ((ObjectInfo) this.instance).setLeakedCount(value);
                return this;
            }

            public Builder clearLeakedCount() {
                copyOnWrite();
                ((ObjectInfo) this.instance).clearLeakedCount();
                return this;
            }

            public boolean hasLeakPath() {
                return ((ObjectInfo) this.instance).hasLeakPath();
            }

            public String getLeakPath() {
                return ((ObjectInfo) this.instance).getLeakPath();
            }

            public ByteString getLeakPathBytes() {
                return ((ObjectInfo) this.instance).getLeakPathBytes();
            }

            public Builder setLeakPath(String value) {
                copyOnWrite();
                ((ObjectInfo) this.instance).setLeakPath(value);
                return this;
            }

            public Builder clearLeakPath() {
                copyOnWrite();
                ((ObjectInfo) this.instance).clearLeakPath();
                return this;
            }

            public Builder setLeakPathBytes(ByteString value) {
                copyOnWrite();
                ((ObjectInfo) this.instance).setLeakPathBytes(value);
                return this;
            }

            public boolean hasRetainedHeapBytes() {
                return ((ObjectInfo) this.instance).hasRetainedHeapBytes();
            }

            public int getRetainedHeapBytes() {
                return ((ObjectInfo) this.instance).getRetainedHeapBytes();
            }

            public Builder setRetainedHeapBytes(int value) {
                copyOnWrite();
                ((ObjectInfo) this.instance).setRetainedHeapBytes(value);
                return this;
            }

            public Builder clearRetainedHeapBytes() {
                copyOnWrite();
                ((ObjectInfo) this.instance).clearRetainedHeapBytes();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ObjectInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\u0004\u0001\u0003\u0004\u0002\u0004\b\u0003\u0005\u0004\u0004", new Object[]{"bitField0_", "className_", "releasedCount_", "leakedCount_", "leakPath_", "retainedHeapBytes_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ObjectInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (ObjectInfo.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(ObjectInfo.class, DEFAULT_INSTANCE);
        }

        public static ObjectInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ObjectInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PrimesStats extends GeneratedMessageLite<PrimesStats, Builder> implements PrimesStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final PrimesStats DEFAULT_INSTANCE = new PrimesStats();
        public static final int ESTIMATED_COUNT_FIELD_NUMBER = 2;
        private static volatile Parser<PrimesStats> PARSER = null;
        public static final int PRIMES_DEBUG_MESSAGE_FIELD_NUMBER = 3;
        public static final int PRIMES_EVENT_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int estimatedCount_ = 1;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private PrimesDebugMessage primesDebugMessage_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int primesEvent_;

        public interface PrimesDebugMessageOrBuilder extends MessageLiteOrBuilder {
            CrashMetric getPreviousCrash();

            PrimesHeapDumpCalibrationStatus getPrimesHeapDumpCalibrationStatus();

            PrimesHeapDumpEvent getPrimesHeapDumpEvent();

            boolean hasPreviousCrash();

            boolean hasPrimesHeapDumpCalibrationStatus();

            boolean hasPrimesHeapDumpEvent();
        }

        private PrimesStats() {
        }

        public enum PrimesEvent implements Internal.EnumLite {
            UNKNOWN(0),
            PRIMES_INITIALIZED(1),
            PRIMES_CRASH_MONITORING_INITIALIZED(2),
            PRIMES_FIRST_ACTIVITY_LAUNCHED(3),
            PRIMES_CUSTOM_LAUNCHED(4);
            
            public static final int PRIMES_CRASH_MONITORING_INITIALIZED_VALUE = 2;
            public static final int PRIMES_CUSTOM_LAUNCHED_VALUE = 4;
            public static final int PRIMES_FIRST_ACTIVITY_LAUNCHED_VALUE = 3;
            public static final int PRIMES_INITIALIZED_VALUE = 1;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<PrimesEvent> internalValueMap = new Internal.EnumLiteMap<PrimesEvent>() {
                public PrimesEvent findValueByNumber(int number) {
                    return PrimesEvent.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static PrimesEvent forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return PRIMES_INITIALIZED;
                }
                if (value2 == 2) {
                    return PRIMES_CRASH_MONITORING_INITIALIZED;
                }
                if (value2 == 3) {
                    return PRIMES_FIRST_ACTIVITY_LAUNCHED;
                }
                if (value2 != 4) {
                    return null;
                }
                return PRIMES_CUSTOM_LAUNCHED;
            }

            public static Internal.EnumLiteMap<PrimesEvent> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return PrimesEventVerifier.INSTANCE;
            }

            private static final class PrimesEventVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new PrimesEventVerifier();

                private PrimesEventVerifier() {
                }

                public boolean isInRange(int number) {
                    return PrimesEvent.forNumber(number) != null;
                }
            }

            private PrimesEvent(int value2) {
                this.value = value2;
            }
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class PrimesDebugMessage extends GeneratedMessageLite<PrimesDebugMessage, Builder> implements PrimesDebugMessageOrBuilder {
            /* access modifiers changed from: private */
            public static final PrimesDebugMessage DEFAULT_INSTANCE = new PrimesDebugMessage();
            private static volatile Parser<PrimesDebugMessage> PARSER = null;
            public static final int PREVIOUS_CRASH_FIELD_NUMBER = 1;
            public static final int PRIMES_HEAP_DUMP_CALIBRATION_STATUS_FIELD_NUMBER = 3;
            public static final int PRIMES_HEAP_DUMP_EVENT_FIELD_NUMBER = 2;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private CrashMetric previousCrash_;
            @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
            @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
            private PrimesHeapDumpCalibrationStatus primesHeapDumpCalibrationStatus_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private PrimesHeapDumpEvent primesHeapDumpEvent_;

            private PrimesDebugMessage() {
            }

            public boolean hasPreviousCrash() {
                return (this.bitField0_ & 1) != 0;
            }

            public CrashMetric getPreviousCrash() {
                CrashMetric crashMetric = this.previousCrash_;
                return crashMetric == null ? CrashMetric.getDefaultInstance() : crashMetric;
            }

            /* access modifiers changed from: private */
            public void setPreviousCrash(CrashMetric value) {
                if (value != null) {
                    this.previousCrash_ = value;
                    this.bitField0_ |= 1;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void setPreviousCrash(CrashMetric.Builder builderForValue) {
                this.previousCrash_ = (CrashMetric) builderForValue.build();
                this.bitField0_ |= 1;
            }

            /* access modifiers changed from: private */
            public void mergePreviousCrash(CrashMetric value) {
                if (value != null) {
                    CrashMetric crashMetric = this.previousCrash_;
                    if (crashMetric == null || crashMetric == CrashMetric.getDefaultInstance()) {
                        this.previousCrash_ = value;
                    } else {
                        this.previousCrash_ = (CrashMetric) ((CrashMetric.Builder) CrashMetric.newBuilder(this.previousCrash_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                    }
                    this.bitField0_ |= 1;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearPreviousCrash() {
                this.previousCrash_ = null;
                this.bitField0_ &= -2;
            }

            public boolean hasPrimesHeapDumpEvent() {
                return (this.bitField0_ & 2) != 0;
            }

            public PrimesHeapDumpEvent getPrimesHeapDumpEvent() {
                PrimesHeapDumpEvent primesHeapDumpEvent = this.primesHeapDumpEvent_;
                return primesHeapDumpEvent == null ? PrimesHeapDumpEvent.getDefaultInstance() : primesHeapDumpEvent;
            }

            /* access modifiers changed from: private */
            public void setPrimesHeapDumpEvent(PrimesHeapDumpEvent value) {
                if (value != null) {
                    this.primesHeapDumpEvent_ = value;
                    this.bitField0_ |= 2;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void setPrimesHeapDumpEvent(PrimesHeapDumpEvent.Builder builderForValue) {
                this.primesHeapDumpEvent_ = (PrimesHeapDumpEvent) builderForValue.build();
                this.bitField0_ |= 2;
            }

            /* access modifiers changed from: private */
            public void mergePrimesHeapDumpEvent(PrimesHeapDumpEvent value) {
                if (value != null) {
                    PrimesHeapDumpEvent primesHeapDumpEvent = this.primesHeapDumpEvent_;
                    if (primesHeapDumpEvent == null || primesHeapDumpEvent == PrimesHeapDumpEvent.getDefaultInstance()) {
                        this.primesHeapDumpEvent_ = value;
                    } else {
                        this.primesHeapDumpEvent_ = (PrimesHeapDumpEvent) ((PrimesHeapDumpEvent.Builder) PrimesHeapDumpEvent.newBuilder(this.primesHeapDumpEvent_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                    }
                    this.bitField0_ |= 2;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearPrimesHeapDumpEvent() {
                this.primesHeapDumpEvent_ = null;
                this.bitField0_ &= -3;
            }

            public boolean hasPrimesHeapDumpCalibrationStatus() {
                return (this.bitField0_ & 4) != 0;
            }

            public PrimesHeapDumpCalibrationStatus getPrimesHeapDumpCalibrationStatus() {
                PrimesHeapDumpCalibrationStatus primesHeapDumpCalibrationStatus = this.primesHeapDumpCalibrationStatus_;
                return primesHeapDumpCalibrationStatus == null ? PrimesHeapDumpCalibrationStatus.getDefaultInstance() : primesHeapDumpCalibrationStatus;
            }

            /* access modifiers changed from: private */
            public void setPrimesHeapDumpCalibrationStatus(PrimesHeapDumpCalibrationStatus value) {
                if (value != null) {
                    this.primesHeapDumpCalibrationStatus_ = value;
                    this.bitField0_ |= 4;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void setPrimesHeapDumpCalibrationStatus(PrimesHeapDumpCalibrationStatus.Builder builderForValue) {
                this.primesHeapDumpCalibrationStatus_ = (PrimesHeapDumpCalibrationStatus) builderForValue.build();
                this.bitField0_ |= 4;
            }

            /* access modifiers changed from: private */
            public void mergePrimesHeapDumpCalibrationStatus(PrimesHeapDumpCalibrationStatus value) {
                if (value != null) {
                    PrimesHeapDumpCalibrationStatus primesHeapDumpCalibrationStatus = this.primesHeapDumpCalibrationStatus_;
                    if (primesHeapDumpCalibrationStatus == null || primesHeapDumpCalibrationStatus == PrimesHeapDumpCalibrationStatus.getDefaultInstance()) {
                        this.primesHeapDumpCalibrationStatus_ = value;
                    } else {
                        this.primesHeapDumpCalibrationStatus_ = (PrimesHeapDumpCalibrationStatus) ((PrimesHeapDumpCalibrationStatus.Builder) PrimesHeapDumpCalibrationStatus.newBuilder(this.primesHeapDumpCalibrationStatus_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                    }
                    this.bitField0_ |= 4;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearPrimesHeapDumpCalibrationStatus() {
                this.primesHeapDumpCalibrationStatus_ = null;
                this.bitField0_ &= -5;
            }

            public static PrimesDebugMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static PrimesDebugMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static PrimesDebugMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static PrimesDebugMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static PrimesDebugMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static PrimesDebugMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static PrimesDebugMessage parseFrom(InputStream input) throws IOException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static PrimesDebugMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static PrimesDebugMessage parseDelimitedFrom(InputStream input) throws IOException {
                return (PrimesDebugMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static PrimesDebugMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (PrimesDebugMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static PrimesDebugMessage parseFrom(CodedInputStream input) throws IOException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static PrimesDebugMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (PrimesDebugMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(PrimesDebugMessage prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<PrimesDebugMessage, Builder> implements PrimesDebugMessageOrBuilder {
                private Builder() {
                    super(PrimesDebugMessage.DEFAULT_INSTANCE);
                }

                public boolean hasPreviousCrash() {
                    return ((PrimesDebugMessage) this.instance).hasPreviousCrash();
                }

                public CrashMetric getPreviousCrash() {
                    return ((PrimesDebugMessage) this.instance).getPreviousCrash();
                }

                public Builder setPreviousCrash(CrashMetric value) {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).setPreviousCrash(value);
                    return this;
                }

                public Builder setPreviousCrash(CrashMetric.Builder builderForValue) {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).setPreviousCrash(builderForValue);
                    return this;
                }

                public Builder mergePreviousCrash(CrashMetric value) {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).mergePreviousCrash(value);
                    return this;
                }

                public Builder clearPreviousCrash() {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).clearPreviousCrash();
                    return this;
                }

                public boolean hasPrimesHeapDumpEvent() {
                    return ((PrimesDebugMessage) this.instance).hasPrimesHeapDumpEvent();
                }

                public PrimesHeapDumpEvent getPrimesHeapDumpEvent() {
                    return ((PrimesDebugMessage) this.instance).getPrimesHeapDumpEvent();
                }

                public Builder setPrimesHeapDumpEvent(PrimesHeapDumpEvent value) {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).setPrimesHeapDumpEvent(value);
                    return this;
                }

                public Builder setPrimesHeapDumpEvent(PrimesHeapDumpEvent.Builder builderForValue) {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).setPrimesHeapDumpEvent(builderForValue);
                    return this;
                }

                public Builder mergePrimesHeapDumpEvent(PrimesHeapDumpEvent value) {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).mergePrimesHeapDumpEvent(value);
                    return this;
                }

                public Builder clearPrimesHeapDumpEvent() {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).clearPrimesHeapDumpEvent();
                    return this;
                }

                public boolean hasPrimesHeapDumpCalibrationStatus() {
                    return ((PrimesDebugMessage) this.instance).hasPrimesHeapDumpCalibrationStatus();
                }

                public PrimesHeapDumpCalibrationStatus getPrimesHeapDumpCalibrationStatus() {
                    return ((PrimesDebugMessage) this.instance).getPrimesHeapDumpCalibrationStatus();
                }

                public Builder setPrimesHeapDumpCalibrationStatus(PrimesHeapDumpCalibrationStatus value) {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).setPrimesHeapDumpCalibrationStatus(value);
                    return this;
                }

                public Builder setPrimesHeapDumpCalibrationStatus(PrimesHeapDumpCalibrationStatus.Builder builderForValue) {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).setPrimesHeapDumpCalibrationStatus(builderForValue);
                    return this;
                }

                public Builder mergePrimesHeapDumpCalibrationStatus(PrimesHeapDumpCalibrationStatus value) {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).mergePrimesHeapDumpCalibrationStatus(value);
                    return this;
                }

                public Builder clearPrimesHeapDumpCalibrationStatus() {
                    copyOnWrite();
                    ((PrimesDebugMessage) this.instance).clearPrimesHeapDumpCalibrationStatus();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new PrimesDebugMessage();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"bitField0_", "previousCrash_", "primesHeapDumpEvent_", "primesHeapDumpCalibrationStatus_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<PrimesDebugMessage> parser = PARSER;
                        if (parser == null) {
                            synchronized (PrimesDebugMessage.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return (byte) 1;
                    case SET_MEMOIZED_IS_INITIALIZED:
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                GeneratedMessageLite.registerDefaultInstance(PrimesDebugMessage.class, DEFAULT_INSTANCE);
            }

            public static PrimesDebugMessage getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<PrimesDebugMessage> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasPrimesEvent() {
            return (this.bitField0_ & 1) != 0;
        }

        public PrimesEvent getPrimesEvent() {
            PrimesEvent result = PrimesEvent.forNumber(this.primesEvent_);
            return result == null ? PrimesEvent.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setPrimesEvent(PrimesEvent value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.primesEvent_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPrimesEvent() {
            this.bitField0_ &= -2;
            this.primesEvent_ = 0;
        }

        public boolean hasEstimatedCount() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getEstimatedCount() {
            return this.estimatedCount_;
        }

        /* access modifiers changed from: private */
        public void setEstimatedCount(int value) {
            this.bitField0_ |= 2;
            this.estimatedCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearEstimatedCount() {
            this.bitField0_ &= -3;
            this.estimatedCount_ = 1;
        }

        public boolean hasPrimesDebugMessage() {
            return (this.bitField0_ & 4) != 0;
        }

        public PrimesDebugMessage getPrimesDebugMessage() {
            PrimesDebugMessage primesDebugMessage = this.primesDebugMessage_;
            return primesDebugMessage == null ? PrimesDebugMessage.getDefaultInstance() : primesDebugMessage;
        }

        /* access modifiers changed from: private */
        public void setPrimesDebugMessage(PrimesDebugMessage value) {
            if (value != null) {
                this.primesDebugMessage_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrimesDebugMessage(PrimesDebugMessage.Builder builderForValue) {
            this.primesDebugMessage_ = (PrimesDebugMessage) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergePrimesDebugMessage(PrimesDebugMessage value) {
            if (value != null) {
                PrimesDebugMessage primesDebugMessage = this.primesDebugMessage_;
                if (primesDebugMessage == null || primesDebugMessage == PrimesDebugMessage.getDefaultInstance()) {
                    this.primesDebugMessage_ = value;
                } else {
                    this.primesDebugMessage_ = (PrimesDebugMessage) ((PrimesDebugMessage.Builder) PrimesDebugMessage.newBuilder(this.primesDebugMessage_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPrimesDebugMessage() {
            this.primesDebugMessage_ = null;
            this.bitField0_ &= -5;
        }

        public static PrimesStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesStats parseFrom(InputStream input) throws IOException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesStats parseDelimitedFrom(InputStream input) throws IOException {
            return (PrimesStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesStats parseFrom(CodedInputStream input) throws IOException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PrimesStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PrimesStats, Builder> implements PrimesStatsOrBuilder {
            private Builder() {
                super(PrimesStats.DEFAULT_INSTANCE);
            }

            public boolean hasPrimesEvent() {
                return ((PrimesStats) this.instance).hasPrimesEvent();
            }

            public PrimesEvent getPrimesEvent() {
                return ((PrimesStats) this.instance).getPrimesEvent();
            }

            public Builder setPrimesEvent(PrimesEvent value) {
                copyOnWrite();
                ((PrimesStats) this.instance).setPrimesEvent(value);
                return this;
            }

            public Builder clearPrimesEvent() {
                copyOnWrite();
                ((PrimesStats) this.instance).clearPrimesEvent();
                return this;
            }

            public boolean hasEstimatedCount() {
                return ((PrimesStats) this.instance).hasEstimatedCount();
            }

            public int getEstimatedCount() {
                return ((PrimesStats) this.instance).getEstimatedCount();
            }

            public Builder setEstimatedCount(int value) {
                copyOnWrite();
                ((PrimesStats) this.instance).setEstimatedCount(value);
                return this;
            }

            public Builder clearEstimatedCount() {
                copyOnWrite();
                ((PrimesStats) this.instance).clearEstimatedCount();
                return this;
            }

            public boolean hasPrimesDebugMessage() {
                return ((PrimesStats) this.instance).hasPrimesDebugMessage();
            }

            public PrimesDebugMessage getPrimesDebugMessage() {
                return ((PrimesStats) this.instance).getPrimesDebugMessage();
            }

            public Builder setPrimesDebugMessage(PrimesDebugMessage value) {
                copyOnWrite();
                ((PrimesStats) this.instance).setPrimesDebugMessage(value);
                return this;
            }

            public Builder setPrimesDebugMessage(PrimesDebugMessage.Builder builderForValue) {
                copyOnWrite();
                ((PrimesStats) this.instance).setPrimesDebugMessage(builderForValue);
                return this;
            }

            public Builder mergePrimesDebugMessage(PrimesDebugMessage value) {
                copyOnWrite();
                ((PrimesStats) this.instance).mergePrimesDebugMessage(value);
                return this;
            }

            public Builder clearPrimesDebugMessage() {
                copyOnWrite();
                ((PrimesStats) this.instance).clearPrimesDebugMessage();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PrimesStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\f\u0000\u0002\u0004\u0001\u0003\t\u0002", new Object[]{"bitField0_", "primesEvent_", PrimesEvent.internalGetVerifier(), "estimatedCount_", "primesDebugMessage_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PrimesStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (PrimesStats.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(PrimesStats.class, DEFAULT_INSTANCE);
        }

        public static PrimesStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PrimesStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PackageMetric extends GeneratedMessageLite<PackageMetric, Builder> implements PackageMetricOrBuilder {
        public static final int CACHE_SIZE_FIELD_NUMBER = 1;
        public static final int CODE_SIZE_FIELD_NUMBER = 2;
        public static final int DATA_PARTITION_SIZE_BYTES_FIELD_NUMBER = 11;
        public static final int DATA_SIZE_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final PackageMetric DEFAULT_INSTANCE = new PackageMetric();
        public static final int DIR_STATS_FIELD_NUMBER = 10;
        public static final int EXTERNAL_CACHE_SIZE_FIELD_NUMBER = 4;
        public static final int EXTERNAL_CODE_SIZE_FIELD_NUMBER = 5;
        public static final int EXTERNAL_DATA_SIZE_FIELD_NUMBER = 6;
        public static final int EXTERNAL_MEDIA_SIZE_FIELD_NUMBER = 7;
        public static final int EXTERNAL_OBB_SIZE_FIELD_NUMBER = 8;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 9;
        private static volatile Parser<PackageMetric> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long cacheSize_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long codeSize_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private long dataPartitionSizeBytes_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long dataSize_;
        @ProtoField(fieldNumber = 10, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<DirStats> dirStats_ = emptyProtobufList();
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private long externalCacheSize_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private long externalCodeSize_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private long externalDataSize_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private long externalMediaSize_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private long externalObbSize_;
        @ProtoField(fieldNumber = 9, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private String packageName_ = "";

        public interface DirStatsOrBuilder extends MessageLiteOrBuilder {
            String getDirPath();

            ByteString getDirPathBytes();

            long getHashedDirPath(int i);

            int getHashedDirPathCount();

            List<Long> getHashedDirPathList();

            long getSizeBytes();

            boolean hasDirPath();

            boolean hasSizeBytes();
        }

        private PackageMetric() {
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class DirStats extends GeneratedMessageLite<DirStats, Builder> implements DirStatsOrBuilder {
            /* access modifiers changed from: private */
            public static final DirStats DEFAULT_INSTANCE = new DirStats();
            public static final int DIR_PATH_FIELD_NUMBER = 1;
            public static final int HASHED_DIR_PATH_FIELD_NUMBER = 3;
            private static volatile Parser<DirStats> PARSER = null;
            public static final int SIZE_BYTES_FIELD_NUMBER = 2;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private String dirPath_ = "";
            @ProtoField(fieldNumber = 3, type = FieldType.FIXED64_LIST)
            private Internal.LongList hashedDirPath_ = emptyLongList();
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private long sizeBytes_;

            private DirStats() {
            }

            public boolean hasDirPath() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getDirPath() {
                return this.dirPath_;
            }

            public ByteString getDirPathBytes() {
                return ByteString.copyFromUtf8(this.dirPath_);
            }

            /* access modifiers changed from: private */
            public void setDirPath(String value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.dirPath_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearDirPath() {
                this.bitField0_ &= -2;
                this.dirPath_ = getDefaultInstance().getDirPath();
            }

            /* access modifiers changed from: private */
            public void setDirPathBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.dirPath_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public List<Long> getHashedDirPathList() {
                return this.hashedDirPath_;
            }

            public int getHashedDirPathCount() {
                return this.hashedDirPath_.size();
            }

            public long getHashedDirPath(int index) {
                return this.hashedDirPath_.getLong(index);
            }

            private void ensureHashedDirPathIsMutable() {
                if (!this.hashedDirPath_.isModifiable()) {
                    this.hashedDirPath_ = GeneratedMessageLite.mutableCopy(this.hashedDirPath_);
                }
            }

            /* access modifiers changed from: private */
            public void setHashedDirPath(int index, long value) {
                ensureHashedDirPathIsMutable();
                this.hashedDirPath_.setLong(index, value);
            }

            /* access modifiers changed from: private */
            public void addHashedDirPath(long value) {
                ensureHashedDirPathIsMutable();
                this.hashedDirPath_.addLong(value);
            }

            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
             arg types: [java.lang.Iterable<? extends java.lang.Long>, com.google.protobuf.Internal$LongList]
             candidates:
              com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
              com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
            /* access modifiers changed from: private */
            public void addAllHashedDirPath(Iterable<? extends Long> values) {
                ensureHashedDirPathIsMutable();
                AbstractMessageLite.addAll((Iterable) values, (List) this.hashedDirPath_);
            }

            /* access modifiers changed from: private */
            public void clearHashedDirPath() {
                this.hashedDirPath_ = emptyLongList();
            }

            public boolean hasSizeBytes() {
                return (this.bitField0_ & 2) != 0;
            }

            public long getSizeBytes() {
                return this.sizeBytes_;
            }

            /* access modifiers changed from: private */
            public void setSizeBytes(long value) {
                this.bitField0_ |= 2;
                this.sizeBytes_ = value;
            }

            /* access modifiers changed from: private */
            public void clearSizeBytes() {
                this.bitField0_ &= -3;
                this.sizeBytes_ = 0;
            }

            public static DirStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static DirStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static DirStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static DirStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static DirStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static DirStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static DirStats parseFrom(InputStream input) throws IOException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static DirStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static DirStats parseDelimitedFrom(InputStream input) throws IOException {
                return (DirStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static DirStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (DirStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static DirStats parseFrom(CodedInputStream input) throws IOException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static DirStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (DirStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(DirStats prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<DirStats, Builder> implements DirStatsOrBuilder {
                private Builder() {
                    super(DirStats.DEFAULT_INSTANCE);
                }

                public boolean hasDirPath() {
                    return ((DirStats) this.instance).hasDirPath();
                }

                public String getDirPath() {
                    return ((DirStats) this.instance).getDirPath();
                }

                public ByteString getDirPathBytes() {
                    return ((DirStats) this.instance).getDirPathBytes();
                }

                public Builder setDirPath(String value) {
                    copyOnWrite();
                    ((DirStats) this.instance).setDirPath(value);
                    return this;
                }

                public Builder clearDirPath() {
                    copyOnWrite();
                    ((DirStats) this.instance).clearDirPath();
                    return this;
                }

                public Builder setDirPathBytes(ByteString value) {
                    copyOnWrite();
                    ((DirStats) this.instance).setDirPathBytes(value);
                    return this;
                }

                public List<Long> getHashedDirPathList() {
                    return Collections.unmodifiableList(((DirStats) this.instance).getHashedDirPathList());
                }

                public int getHashedDirPathCount() {
                    return ((DirStats) this.instance).getHashedDirPathCount();
                }

                public long getHashedDirPath(int index) {
                    return ((DirStats) this.instance).getHashedDirPath(index);
                }

                public Builder setHashedDirPath(int index, long value) {
                    copyOnWrite();
                    ((DirStats) this.instance).setHashedDirPath(index, value);
                    return this;
                }

                public Builder addHashedDirPath(long value) {
                    copyOnWrite();
                    ((DirStats) this.instance).addHashedDirPath(value);
                    return this;
                }

                public Builder addAllHashedDirPath(Iterable<? extends Long> values) {
                    copyOnWrite();
                    ((DirStats) this.instance).addAllHashedDirPath(values);
                    return this;
                }

                public Builder clearHashedDirPath() {
                    copyOnWrite();
                    ((DirStats) this.instance).clearHashedDirPath();
                    return this;
                }

                public boolean hasSizeBytes() {
                    return ((DirStats) this.instance).hasSizeBytes();
                }

                public long getSizeBytes() {
                    return ((DirStats) this.instance).getSizeBytes();
                }

                public Builder setSizeBytes(long value) {
                    copyOnWrite();
                    ((DirStats) this.instance).setSizeBytes(value);
                    return this;
                }

                public Builder clearSizeBytes() {
                    copyOnWrite();
                    ((DirStats) this.instance).clearSizeBytes();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new DirStats();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001\b\u0000\u0002\u0002\u0001\u0003\u0017", new Object[]{"bitField0_", "dirPath_", "sizeBytes_", "hashedDirPath_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<DirStats> parser = PARSER;
                        if (parser == null) {
                            synchronized (DirStats.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return (byte) 1;
                    case SET_MEMOIZED_IS_INITIALIZED:
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                GeneratedMessageLite.registerDefaultInstance(DirStats.class, DEFAULT_INSTANCE);
            }

            public static DirStats getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<DirStats> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasCacheSize() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getCacheSize() {
            return this.cacheSize_;
        }

        /* access modifiers changed from: private */
        public void setCacheSize(long value) {
            this.bitField0_ |= 1;
            this.cacheSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCacheSize() {
            this.bitField0_ &= -2;
            this.cacheSize_ = 0;
        }

        public boolean hasCodeSize() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getCodeSize() {
            return this.codeSize_;
        }

        /* access modifiers changed from: private */
        public void setCodeSize(long value) {
            this.bitField0_ |= 2;
            this.codeSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCodeSize() {
            this.bitField0_ &= -3;
            this.codeSize_ = 0;
        }

        public boolean hasDataSize() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getDataSize() {
            return this.dataSize_;
        }

        /* access modifiers changed from: private */
        public void setDataSize(long value) {
            this.bitField0_ |= 4;
            this.dataSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDataSize() {
            this.bitField0_ &= -5;
            this.dataSize_ = 0;
        }

        public boolean hasExternalCacheSize() {
            return (this.bitField0_ & 8) != 0;
        }

        public long getExternalCacheSize() {
            return this.externalCacheSize_;
        }

        /* access modifiers changed from: private */
        public void setExternalCacheSize(long value) {
            this.bitField0_ |= 8;
            this.externalCacheSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExternalCacheSize() {
            this.bitField0_ &= -9;
            this.externalCacheSize_ = 0;
        }

        public boolean hasExternalCodeSize() {
            return (this.bitField0_ & 16) != 0;
        }

        public long getExternalCodeSize() {
            return this.externalCodeSize_;
        }

        /* access modifiers changed from: private */
        public void setExternalCodeSize(long value) {
            this.bitField0_ |= 16;
            this.externalCodeSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExternalCodeSize() {
            this.bitField0_ &= -17;
            this.externalCodeSize_ = 0;
        }

        public boolean hasExternalDataSize() {
            return (this.bitField0_ & 32) != 0;
        }

        public long getExternalDataSize() {
            return this.externalDataSize_;
        }

        /* access modifiers changed from: private */
        public void setExternalDataSize(long value) {
            this.bitField0_ |= 32;
            this.externalDataSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExternalDataSize() {
            this.bitField0_ &= -33;
            this.externalDataSize_ = 0;
        }

        public boolean hasExternalMediaSize() {
            return (this.bitField0_ & 64) != 0;
        }

        public long getExternalMediaSize() {
            return this.externalMediaSize_;
        }

        /* access modifiers changed from: private */
        public void setExternalMediaSize(long value) {
            this.bitField0_ |= 64;
            this.externalMediaSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExternalMediaSize() {
            this.bitField0_ &= -65;
            this.externalMediaSize_ = 0;
        }

        public boolean hasExternalObbSize() {
            return (this.bitField0_ & 128) != 0;
        }

        public long getExternalObbSize() {
            return this.externalObbSize_;
        }

        /* access modifiers changed from: private */
        public void setExternalObbSize(long value) {
            this.bitField0_ |= 128;
            this.externalObbSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExternalObbSize() {
            this.bitField0_ &= -129;
            this.externalObbSize_ = 0;
        }

        @Deprecated
        public boolean hasPackageName() {
            return (this.bitField0_ & 256) != 0;
        }

        @Deprecated
        public String getPackageName() {
            return this.packageName_;
        }

        @Deprecated
        public ByteString getPackageNameBytes() {
            return ByteString.copyFromUtf8(this.packageName_);
        }

        /* access modifiers changed from: private */
        public void setPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 256;
                this.packageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageName() {
            this.bitField0_ &= -257;
            this.packageName_ = getDefaultInstance().getPackageName();
        }

        /* access modifiers changed from: private */
        public void setPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 256;
                this.packageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public List<DirStats> getDirStatsList() {
            return this.dirStats_;
        }

        public List<? extends DirStatsOrBuilder> getDirStatsOrBuilderList() {
            return this.dirStats_;
        }

        public int getDirStatsCount() {
            return this.dirStats_.size();
        }

        public DirStats getDirStats(int index) {
            return this.dirStats_.get(index);
        }

        public DirStatsOrBuilder getDirStatsOrBuilder(int index) {
            return this.dirStats_.get(index);
        }

        private void ensureDirStatsIsMutable() {
            if (!this.dirStats_.isModifiable()) {
                this.dirStats_ = GeneratedMessageLite.mutableCopy(this.dirStats_);
            }
        }

        /* access modifiers changed from: private */
        public void setDirStats(int index, DirStats value) {
            if (value != null) {
                ensureDirStatsIsMutable();
                this.dirStats_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setDirStats(int index, DirStats.Builder builderForValue) {
            ensureDirStatsIsMutable();
            this.dirStats_.set(index, (DirStats) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addDirStats(DirStats value) {
            if (value != null) {
                ensureDirStatsIsMutable();
                this.dirStats_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addDirStats(int index, DirStats value) {
            if (value != null) {
                ensureDirStatsIsMutable();
                this.dirStats_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addDirStats(DirStats.Builder builderForValue) {
            ensureDirStatsIsMutable();
            this.dirStats_.add((DirStats) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addDirStats(int index, DirStats.Builder builderForValue) {
            ensureDirStatsIsMutable();
            this.dirStats_.add(index, (DirStats) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.SystemHealthProto$PackageMetric$DirStats>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.SystemHealthProto$PackageMetric$DirStats>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllDirStats(Iterable<? extends DirStats> values) {
            ensureDirStatsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.dirStats_);
        }

        /* access modifiers changed from: private */
        public void clearDirStats() {
            this.dirStats_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeDirStats(int index) {
            ensureDirStatsIsMutable();
            this.dirStats_.remove(index);
        }

        @Deprecated
        public boolean hasDataPartitionSizeBytes() {
            return (this.bitField0_ & 512) != 0;
        }

        @Deprecated
        public long getDataPartitionSizeBytes() {
            return this.dataPartitionSizeBytes_;
        }

        /* access modifiers changed from: private */
        public void setDataPartitionSizeBytes(long value) {
            this.bitField0_ |= 512;
            this.dataPartitionSizeBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDataPartitionSizeBytes() {
            this.bitField0_ &= -513;
            this.dataPartitionSizeBytes_ = 0;
        }

        public static PackageMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PackageMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PackageMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PackageMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PackageMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PackageMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PackageMetric parseFrom(InputStream input) throws IOException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PackageMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PackageMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (PackageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PackageMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PackageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PackageMetric parseFrom(CodedInputStream input) throws IOException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PackageMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PackageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PackageMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PackageMetric, Builder> implements PackageMetricOrBuilder {
            private Builder() {
                super(PackageMetric.DEFAULT_INSTANCE);
            }

            public boolean hasCacheSize() {
                return ((PackageMetric) this.instance).hasCacheSize();
            }

            public long getCacheSize() {
                return ((PackageMetric) this.instance).getCacheSize();
            }

            public Builder setCacheSize(long value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setCacheSize(value);
                return this;
            }

            public Builder clearCacheSize() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearCacheSize();
                return this;
            }

            public boolean hasCodeSize() {
                return ((PackageMetric) this.instance).hasCodeSize();
            }

            public long getCodeSize() {
                return ((PackageMetric) this.instance).getCodeSize();
            }

            public Builder setCodeSize(long value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setCodeSize(value);
                return this;
            }

            public Builder clearCodeSize() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearCodeSize();
                return this;
            }

            public boolean hasDataSize() {
                return ((PackageMetric) this.instance).hasDataSize();
            }

            public long getDataSize() {
                return ((PackageMetric) this.instance).getDataSize();
            }

            public Builder setDataSize(long value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setDataSize(value);
                return this;
            }

            public Builder clearDataSize() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearDataSize();
                return this;
            }

            public boolean hasExternalCacheSize() {
                return ((PackageMetric) this.instance).hasExternalCacheSize();
            }

            public long getExternalCacheSize() {
                return ((PackageMetric) this.instance).getExternalCacheSize();
            }

            public Builder setExternalCacheSize(long value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setExternalCacheSize(value);
                return this;
            }

            public Builder clearExternalCacheSize() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearExternalCacheSize();
                return this;
            }

            public boolean hasExternalCodeSize() {
                return ((PackageMetric) this.instance).hasExternalCodeSize();
            }

            public long getExternalCodeSize() {
                return ((PackageMetric) this.instance).getExternalCodeSize();
            }

            public Builder setExternalCodeSize(long value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setExternalCodeSize(value);
                return this;
            }

            public Builder clearExternalCodeSize() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearExternalCodeSize();
                return this;
            }

            public boolean hasExternalDataSize() {
                return ((PackageMetric) this.instance).hasExternalDataSize();
            }

            public long getExternalDataSize() {
                return ((PackageMetric) this.instance).getExternalDataSize();
            }

            public Builder setExternalDataSize(long value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setExternalDataSize(value);
                return this;
            }

            public Builder clearExternalDataSize() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearExternalDataSize();
                return this;
            }

            public boolean hasExternalMediaSize() {
                return ((PackageMetric) this.instance).hasExternalMediaSize();
            }

            public long getExternalMediaSize() {
                return ((PackageMetric) this.instance).getExternalMediaSize();
            }

            public Builder setExternalMediaSize(long value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setExternalMediaSize(value);
                return this;
            }

            public Builder clearExternalMediaSize() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearExternalMediaSize();
                return this;
            }

            public boolean hasExternalObbSize() {
                return ((PackageMetric) this.instance).hasExternalObbSize();
            }

            public long getExternalObbSize() {
                return ((PackageMetric) this.instance).getExternalObbSize();
            }

            public Builder setExternalObbSize(long value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setExternalObbSize(value);
                return this;
            }

            public Builder clearExternalObbSize() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearExternalObbSize();
                return this;
            }

            @Deprecated
            public boolean hasPackageName() {
                return ((PackageMetric) this.instance).hasPackageName();
            }

            @Deprecated
            public String getPackageName() {
                return ((PackageMetric) this.instance).getPackageName();
            }

            @Deprecated
            public ByteString getPackageNameBytes() {
                return ((PackageMetric) this.instance).getPackageNameBytes();
            }

            @Deprecated
            public Builder setPackageName(String value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setPackageName(value);
                return this;
            }

            @Deprecated
            public Builder clearPackageName() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearPackageName();
                return this;
            }

            @Deprecated
            public Builder setPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setPackageNameBytes(value);
                return this;
            }

            public List<DirStats> getDirStatsList() {
                return Collections.unmodifiableList(((PackageMetric) this.instance).getDirStatsList());
            }

            public int getDirStatsCount() {
                return ((PackageMetric) this.instance).getDirStatsCount();
            }

            public DirStats getDirStats(int index) {
                return ((PackageMetric) this.instance).getDirStats(index);
            }

            public Builder setDirStats(int index, DirStats value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setDirStats(index, value);
                return this;
            }

            public Builder setDirStats(int index, DirStats.Builder builderForValue) {
                copyOnWrite();
                ((PackageMetric) this.instance).setDirStats(index, builderForValue);
                return this;
            }

            public Builder addDirStats(DirStats value) {
                copyOnWrite();
                ((PackageMetric) this.instance).addDirStats(value);
                return this;
            }

            public Builder addDirStats(int index, DirStats value) {
                copyOnWrite();
                ((PackageMetric) this.instance).addDirStats(index, value);
                return this;
            }

            public Builder addDirStats(DirStats.Builder builderForValue) {
                copyOnWrite();
                ((PackageMetric) this.instance).addDirStats(builderForValue);
                return this;
            }

            public Builder addDirStats(int index, DirStats.Builder builderForValue) {
                copyOnWrite();
                ((PackageMetric) this.instance).addDirStats(index, builderForValue);
                return this;
            }

            public Builder addAllDirStats(Iterable<? extends DirStats> values) {
                copyOnWrite();
                ((PackageMetric) this.instance).addAllDirStats(values);
                return this;
            }

            public Builder clearDirStats() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearDirStats();
                return this;
            }

            public Builder removeDirStats(int index) {
                copyOnWrite();
                ((PackageMetric) this.instance).removeDirStats(index);
                return this;
            }

            @Deprecated
            public boolean hasDataPartitionSizeBytes() {
                return ((PackageMetric) this.instance).hasDataPartitionSizeBytes();
            }

            @Deprecated
            public long getDataPartitionSizeBytes() {
                return ((PackageMetric) this.instance).getDataPartitionSizeBytes();
            }

            @Deprecated
            public Builder setDataPartitionSizeBytes(long value) {
                copyOnWrite();
                ((PackageMetric) this.instance).setDataPartitionSizeBytes(value);
                return this;
            }

            @Deprecated
            public Builder clearDataPartitionSizeBytes() {
                copyOnWrite();
                ((PackageMetric) this.instance).clearDataPartitionSizeBytes();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PackageMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0001\u0000\u0001\u0002\u0000\u0002\u0002\u0001\u0003\u0002\u0002\u0004\u0002\u0003\u0005\u0002\u0004\u0006\u0002\u0005\u0007\u0002\u0006\b\u0002\u0007\t\b\b\n\u001b\u000b\u0002\t", new Object[]{"bitField0_", "cacheSize_", "codeSize_", "dataSize_", "externalCacheSize_", "externalCodeSize_", "externalDataSize_", "externalMediaSize_", "externalObbSize_", "packageName_", "dirStats_", DirStats.class, "dataPartitionSizeBytes_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PackageMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (PackageMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(PackageMetric.class, DEFAULT_INSTANCE);
        }

        public static PackageMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PackageMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class JankMetric extends GeneratedMessageLite<JankMetric, Builder> implements JankMetricOrBuilder {
        /* access modifiers changed from: private */
        public static final JankMetric DEFAULT_INSTANCE = new JankMetric();
        public static final int DEVICE_REFRESH_RATE_FIELD_NUMBER = 6;
        public static final int DURATION_MS_FIELD_NUMBER = 4;
        public static final int FRAME_TIME_HISTOGRAM_FIELD_NUMBER = 5;
        public static final int JANKY_FRAME_COUNT_FIELD_NUMBER = 1;
        public static final int MAX_FRAME_RENDER_TIME_MS_FIELD_NUMBER = 3;
        private static volatile Parser<JankMetric> PARSER = null;
        public static final int RENDERED_FRAME_COUNT_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int deviceRefreshRate_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int durationMs_;
        @ProtoField(fieldNumber = 5, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<HistogramBucket> frameTimeHistogram_ = emptyProtobufList();
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int jankyFrameCount_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int maxFrameRenderTimeMs_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int renderedFrameCount_;

        private JankMetric() {
        }

        public boolean hasJankyFrameCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getJankyFrameCount() {
            return this.jankyFrameCount_;
        }

        /* access modifiers changed from: private */
        public void setJankyFrameCount(int value) {
            this.bitField0_ |= 1;
            this.jankyFrameCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJankyFrameCount() {
            this.bitField0_ &= -2;
            this.jankyFrameCount_ = 0;
        }

        public boolean hasRenderedFrameCount() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getRenderedFrameCount() {
            return this.renderedFrameCount_;
        }

        /* access modifiers changed from: private */
        public void setRenderedFrameCount(int value) {
            this.bitField0_ |= 2;
            this.renderedFrameCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRenderedFrameCount() {
            this.bitField0_ &= -3;
            this.renderedFrameCount_ = 0;
        }

        public boolean hasMaxFrameRenderTimeMs() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getMaxFrameRenderTimeMs() {
            return this.maxFrameRenderTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setMaxFrameRenderTimeMs(int value) {
            this.bitField0_ |= 4;
            this.maxFrameRenderTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMaxFrameRenderTimeMs() {
            this.bitField0_ &= -5;
            this.maxFrameRenderTimeMs_ = 0;
        }

        public boolean hasDurationMs() {
            return (this.bitField0_ & 8) != 0;
        }

        public int getDurationMs() {
            return this.durationMs_;
        }

        /* access modifiers changed from: private */
        public void setDurationMs(int value) {
            this.bitField0_ |= 8;
            this.durationMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDurationMs() {
            this.bitField0_ &= -9;
            this.durationMs_ = 0;
        }

        public List<HistogramBucket> getFrameTimeHistogramList() {
            return this.frameTimeHistogram_;
        }

        public List<? extends HistogramBucketOrBuilder> getFrameTimeHistogramOrBuilderList() {
            return this.frameTimeHistogram_;
        }

        public int getFrameTimeHistogramCount() {
            return this.frameTimeHistogram_.size();
        }

        public HistogramBucket getFrameTimeHistogram(int index) {
            return this.frameTimeHistogram_.get(index);
        }

        public HistogramBucketOrBuilder getFrameTimeHistogramOrBuilder(int index) {
            return this.frameTimeHistogram_.get(index);
        }

        private void ensureFrameTimeHistogramIsMutable() {
            if (!this.frameTimeHistogram_.isModifiable()) {
                this.frameTimeHistogram_ = GeneratedMessageLite.mutableCopy(this.frameTimeHistogram_);
            }
        }

        /* access modifiers changed from: private */
        public void setFrameTimeHistogram(int index, HistogramBucket value) {
            if (value != null) {
                ensureFrameTimeHistogramIsMutable();
                this.frameTimeHistogram_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setFrameTimeHistogram(int index, HistogramBucket.Builder builderForValue) {
            ensureFrameTimeHistogramIsMutable();
            this.frameTimeHistogram_.set(index, (HistogramBucket) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addFrameTimeHistogram(HistogramBucket value) {
            if (value != null) {
                ensureFrameTimeHistogramIsMutable();
                this.frameTimeHistogram_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addFrameTimeHistogram(int index, HistogramBucket value) {
            if (value != null) {
                ensureFrameTimeHistogramIsMutable();
                this.frameTimeHistogram_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addFrameTimeHistogram(HistogramBucket.Builder builderForValue) {
            ensureFrameTimeHistogramIsMutable();
            this.frameTimeHistogram_.add((HistogramBucket) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addFrameTimeHistogram(int index, HistogramBucket.Builder builderForValue) {
            ensureFrameTimeHistogramIsMutable();
            this.frameTimeHistogram_.add(index, (HistogramBucket) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.SystemHealthProto$HistogramBucket>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.SystemHealthProto$HistogramBucket>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllFrameTimeHistogram(Iterable<? extends HistogramBucket> values) {
            ensureFrameTimeHistogramIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.frameTimeHistogram_);
        }

        /* access modifiers changed from: private */
        public void clearFrameTimeHistogram() {
            this.frameTimeHistogram_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeFrameTimeHistogram(int index) {
            ensureFrameTimeHistogramIsMutable();
            this.frameTimeHistogram_.remove(index);
        }

        public boolean hasDeviceRefreshRate() {
            return (this.bitField0_ & 16) != 0;
        }

        public int getDeviceRefreshRate() {
            return this.deviceRefreshRate_;
        }

        /* access modifiers changed from: private */
        public void setDeviceRefreshRate(int value) {
            this.bitField0_ |= 16;
            this.deviceRefreshRate_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeviceRefreshRate() {
            this.bitField0_ &= -17;
            this.deviceRefreshRate_ = 0;
        }

        public static JankMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static JankMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static JankMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static JankMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static JankMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static JankMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static JankMetric parseFrom(InputStream input) throws IOException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static JankMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static JankMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (JankMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static JankMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (JankMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static JankMetric parseFrom(CodedInputStream input) throws IOException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static JankMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (JankMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(JankMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<JankMetric, Builder> implements JankMetricOrBuilder {
            private Builder() {
                super(JankMetric.DEFAULT_INSTANCE);
            }

            public boolean hasJankyFrameCount() {
                return ((JankMetric) this.instance).hasJankyFrameCount();
            }

            public int getJankyFrameCount() {
                return ((JankMetric) this.instance).getJankyFrameCount();
            }

            public Builder setJankyFrameCount(int value) {
                copyOnWrite();
                ((JankMetric) this.instance).setJankyFrameCount(value);
                return this;
            }

            public Builder clearJankyFrameCount() {
                copyOnWrite();
                ((JankMetric) this.instance).clearJankyFrameCount();
                return this;
            }

            public boolean hasRenderedFrameCount() {
                return ((JankMetric) this.instance).hasRenderedFrameCount();
            }

            public int getRenderedFrameCount() {
                return ((JankMetric) this.instance).getRenderedFrameCount();
            }

            public Builder setRenderedFrameCount(int value) {
                copyOnWrite();
                ((JankMetric) this.instance).setRenderedFrameCount(value);
                return this;
            }

            public Builder clearRenderedFrameCount() {
                copyOnWrite();
                ((JankMetric) this.instance).clearRenderedFrameCount();
                return this;
            }

            public boolean hasMaxFrameRenderTimeMs() {
                return ((JankMetric) this.instance).hasMaxFrameRenderTimeMs();
            }

            public int getMaxFrameRenderTimeMs() {
                return ((JankMetric) this.instance).getMaxFrameRenderTimeMs();
            }

            public Builder setMaxFrameRenderTimeMs(int value) {
                copyOnWrite();
                ((JankMetric) this.instance).setMaxFrameRenderTimeMs(value);
                return this;
            }

            public Builder clearMaxFrameRenderTimeMs() {
                copyOnWrite();
                ((JankMetric) this.instance).clearMaxFrameRenderTimeMs();
                return this;
            }

            public boolean hasDurationMs() {
                return ((JankMetric) this.instance).hasDurationMs();
            }

            public int getDurationMs() {
                return ((JankMetric) this.instance).getDurationMs();
            }

            public Builder setDurationMs(int value) {
                copyOnWrite();
                ((JankMetric) this.instance).setDurationMs(value);
                return this;
            }

            public Builder clearDurationMs() {
                copyOnWrite();
                ((JankMetric) this.instance).clearDurationMs();
                return this;
            }

            public List<HistogramBucket> getFrameTimeHistogramList() {
                return Collections.unmodifiableList(((JankMetric) this.instance).getFrameTimeHistogramList());
            }

            public int getFrameTimeHistogramCount() {
                return ((JankMetric) this.instance).getFrameTimeHistogramCount();
            }

            public HistogramBucket getFrameTimeHistogram(int index) {
                return ((JankMetric) this.instance).getFrameTimeHistogram(index);
            }

            public Builder setFrameTimeHistogram(int index, HistogramBucket value) {
                copyOnWrite();
                ((JankMetric) this.instance).setFrameTimeHistogram(index, value);
                return this;
            }

            public Builder setFrameTimeHistogram(int index, HistogramBucket.Builder builderForValue) {
                copyOnWrite();
                ((JankMetric) this.instance).setFrameTimeHistogram(index, builderForValue);
                return this;
            }

            public Builder addFrameTimeHistogram(HistogramBucket value) {
                copyOnWrite();
                ((JankMetric) this.instance).addFrameTimeHistogram(value);
                return this;
            }

            public Builder addFrameTimeHistogram(int index, HistogramBucket value) {
                copyOnWrite();
                ((JankMetric) this.instance).addFrameTimeHistogram(index, value);
                return this;
            }

            public Builder addFrameTimeHistogram(HistogramBucket.Builder builderForValue) {
                copyOnWrite();
                ((JankMetric) this.instance).addFrameTimeHistogram(builderForValue);
                return this;
            }

            public Builder addFrameTimeHistogram(int index, HistogramBucket.Builder builderForValue) {
                copyOnWrite();
                ((JankMetric) this.instance).addFrameTimeHistogram(index, builderForValue);
                return this;
            }

            public Builder addAllFrameTimeHistogram(Iterable<? extends HistogramBucket> values) {
                copyOnWrite();
                ((JankMetric) this.instance).addAllFrameTimeHistogram(values);
                return this;
            }

            public Builder clearFrameTimeHistogram() {
                copyOnWrite();
                ((JankMetric) this.instance).clearFrameTimeHistogram();
                return this;
            }

            public Builder removeFrameTimeHistogram(int index) {
                copyOnWrite();
                ((JankMetric) this.instance).removeFrameTimeHistogram(index);
                return this;
            }

            public boolean hasDeviceRefreshRate() {
                return ((JankMetric) this.instance).hasDeviceRefreshRate();
            }

            public int getDeviceRefreshRate() {
                return ((JankMetric) this.instance).getDeviceRefreshRate();
            }

            public Builder setDeviceRefreshRate(int value) {
                copyOnWrite();
                ((JankMetric) this.instance).setDeviceRefreshRate(value);
                return this;
            }

            public Builder clearDeviceRefreshRate() {
                copyOnWrite();
                ((JankMetric) this.instance).clearDeviceRefreshRate();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new JankMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0001\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u0004\u0002\u0004\u0004\u0003\u0005\u001b\u0006\u0004\u0004", new Object[]{"bitField0_", "jankyFrameCount_", "renderedFrameCount_", "maxFrameRenderTimeMs_", "durationMs_", "frameTimeHistogram_", HistogramBucket.class, "deviceRefreshRate_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<JankMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (JankMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(JankMetric.class, DEFAULT_INSTANCE);
        }

        public static JankMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<JankMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class HistogramBucket extends GeneratedMessageLite<HistogramBucket, Builder> implements HistogramBucketOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final HistogramBucket DEFAULT_INSTANCE = new HistogramBucket();
        public static final int MAX_FIELD_NUMBER = 3;
        public static final int MIN_FIELD_NUMBER = 2;
        private static volatile Parser<HistogramBucket> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int max_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int min_;

        private HistogramBucket() {
        }

        public boolean hasCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getCount() {
            return this.count_;
        }

        /* access modifiers changed from: private */
        public void setCount(int value) {
            this.bitField0_ |= 1;
            this.count_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCount() {
            this.bitField0_ &= -2;
            this.count_ = 0;
        }

        public boolean hasMin() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getMin() {
            return this.min_;
        }

        /* access modifiers changed from: private */
        public void setMin(int value) {
            this.bitField0_ |= 2;
            this.min_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMin() {
            this.bitField0_ &= -3;
            this.min_ = 0;
        }

        public boolean hasMax() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getMax() {
            return this.max_;
        }

        /* access modifiers changed from: private */
        public void setMax(int value) {
            this.bitField0_ |= 4;
            this.max_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMax() {
            this.bitField0_ &= -5;
            this.max_ = 0;
        }

        public static HistogramBucket parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static HistogramBucket parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static HistogramBucket parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static HistogramBucket parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static HistogramBucket parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static HistogramBucket parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static HistogramBucket parseFrom(InputStream input) throws IOException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static HistogramBucket parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static HistogramBucket parseDelimitedFrom(InputStream input) throws IOException {
            return (HistogramBucket) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static HistogramBucket parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HistogramBucket) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static HistogramBucket parseFrom(CodedInputStream input) throws IOException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static HistogramBucket parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HistogramBucket) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(HistogramBucket prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<HistogramBucket, Builder> implements HistogramBucketOrBuilder {
            private Builder() {
                super(HistogramBucket.DEFAULT_INSTANCE);
            }

            public boolean hasCount() {
                return ((HistogramBucket) this.instance).hasCount();
            }

            public int getCount() {
                return ((HistogramBucket) this.instance).getCount();
            }

            public Builder setCount(int value) {
                copyOnWrite();
                ((HistogramBucket) this.instance).setCount(value);
                return this;
            }

            public Builder clearCount() {
                copyOnWrite();
                ((HistogramBucket) this.instance).clearCount();
                return this;
            }

            public boolean hasMin() {
                return ((HistogramBucket) this.instance).hasMin();
            }

            public int getMin() {
                return ((HistogramBucket) this.instance).getMin();
            }

            public Builder setMin(int value) {
                copyOnWrite();
                ((HistogramBucket) this.instance).setMin(value);
                return this;
            }

            public Builder clearMin() {
                copyOnWrite();
                ((HistogramBucket) this.instance).clearMin();
                return this;
            }

            public boolean hasMax() {
                return ((HistogramBucket) this.instance).hasMax();
            }

            public int getMax() {
                return ((HistogramBucket) this.instance).getMax();
            }

            public Builder setMax(int value) {
                copyOnWrite();
                ((HistogramBucket) this.instance).setMax(value);
                return this;
            }

            public Builder clearMax() {
                copyOnWrite();
                ((HistogramBucket) this.instance).clearMax();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new HistogramBucket();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u0004\u0002", new Object[]{"bitField0_", "count_", "min_", "max_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<HistogramBucket> parser = PARSER;
                    if (parser == null) {
                        synchronized (HistogramBucket.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(HistogramBucket.class, DEFAULT_INSTANCE);
        }

        public static HistogramBucket getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HistogramBucket> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MagicEyeMetric extends GeneratedMessageLite<MagicEyeMetric, Builder> implements MagicEyeMetricOrBuilder {
        public static final int APP_STATE_EVENT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final MagicEyeMetric DEFAULT_INSTANCE = new MagicEyeMetric();
        private static volatile Parser<MagicEyeMetric> PARSER;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int appStateEvent_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;

        private MagicEyeMetric() {
        }

        public enum AppStateEvent implements Internal.EnumLite {
            UNKNOWN(0),
            APP_TO_FOREGROUND(1),
            APP_TO_BACKGROUND(2);
            
            public static final int APP_TO_BACKGROUND_VALUE = 2;
            public static final int APP_TO_FOREGROUND_VALUE = 1;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<AppStateEvent> internalValueMap = new Internal.EnumLiteMap<AppStateEvent>() {
                public AppStateEvent findValueByNumber(int number) {
                    return AppStateEvent.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static AppStateEvent forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return APP_TO_FOREGROUND;
                }
                if (value2 != 2) {
                    return null;
                }
                return APP_TO_BACKGROUND;
            }

            public static Internal.EnumLiteMap<AppStateEvent> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return AppStateEventVerifier.INSTANCE;
            }

            private static final class AppStateEventVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new AppStateEventVerifier();

                private AppStateEventVerifier() {
                }

                public boolean isInRange(int number) {
                    return AppStateEvent.forNumber(number) != null;
                }
            }

            private AppStateEvent(int value2) {
                this.value = value2;
            }
        }

        public boolean hasAppStateEvent() {
            return (this.bitField0_ & 1) != 0;
        }

        public AppStateEvent getAppStateEvent() {
            AppStateEvent result = AppStateEvent.forNumber(this.appStateEvent_);
            return result == null ? AppStateEvent.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setAppStateEvent(AppStateEvent value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.appStateEvent_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAppStateEvent() {
            this.bitField0_ &= -2;
            this.appStateEvent_ = 0;
        }

        public static MagicEyeMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MagicEyeMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MagicEyeMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MagicEyeMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MagicEyeMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MagicEyeMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MagicEyeMetric parseFrom(InputStream input) throws IOException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MagicEyeMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MagicEyeMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (MagicEyeMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MagicEyeMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MagicEyeMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MagicEyeMetric parseFrom(CodedInputStream input) throws IOException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MagicEyeMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MagicEyeMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MagicEyeMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<MagicEyeMetric, Builder> implements MagicEyeMetricOrBuilder {
            private Builder() {
                super(MagicEyeMetric.DEFAULT_INSTANCE);
            }

            public boolean hasAppStateEvent() {
                return ((MagicEyeMetric) this.instance).hasAppStateEvent();
            }

            public AppStateEvent getAppStateEvent() {
                return ((MagicEyeMetric) this.instance).getAppStateEvent();
            }

            public Builder setAppStateEvent(AppStateEvent value) {
                copyOnWrite();
                ((MagicEyeMetric) this.instance).setAppStateEvent(value);
                return this;
            }

            public Builder clearAppStateEvent() {
                copyOnWrite();
                ((MagicEyeMetric) this.instance).clearAppStateEvent();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MagicEyeMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\f\u0000", new Object[]{"bitField0_", "appStateEvent_", AppStateEvent.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MagicEyeMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (MagicEyeMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(MagicEyeMetric.class, DEFAULT_INSTANCE);
        }

        public static MagicEyeMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MagicEyeMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PrimesHeapDumpEvent extends GeneratedMessageLite<PrimesHeapDumpEvent, Builder> implements PrimesHeapDumpEventOrBuilder {
        /* access modifiers changed from: private */
        public static final PrimesHeapDumpEvent DEFAULT_INSTANCE = new PrimesHeapDumpEvent();
        public static final int ERROR_FIELD_NUMBER = 1;
        private static volatile Parser<PrimesHeapDumpEvent> PARSER = null;
        public static final int SERIALIZED_SIZE_KB_FIELD_NUMBER = 2;
        public static final int TOTAL_PSS_KB_SAMPLES_FIELD_NUMBER = 3;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int error_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int serializedSizeKb_;
        @ProtoField(fieldNumber = 3, type = FieldType.INT32_LIST)
        private Internal.IntList totalPssKbSamples_ = emptyIntList();

        private PrimesHeapDumpEvent() {
        }

        public enum PrimesHeapDumpError implements Internal.EnumLite {
            UNKNOWN(0),
            OUT_OF_MEMORY_PARSING(1),
            OUT_OF_MEMORY_SERIALIZING(2),
            SERIALIZED_HEAP_DUMP_TOO_LARGE(3),
            NONE(4);
            
            public static final int NONE_VALUE = 4;
            public static final int OUT_OF_MEMORY_PARSING_VALUE = 1;
            public static final int OUT_OF_MEMORY_SERIALIZING_VALUE = 2;
            public static final int SERIALIZED_HEAP_DUMP_TOO_LARGE_VALUE = 3;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<PrimesHeapDumpError> internalValueMap = new Internal.EnumLiteMap<PrimesHeapDumpError>() {
                public PrimesHeapDumpError findValueByNumber(int number) {
                    return PrimesHeapDumpError.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static PrimesHeapDumpError forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return OUT_OF_MEMORY_PARSING;
                }
                if (value2 == 2) {
                    return OUT_OF_MEMORY_SERIALIZING;
                }
                if (value2 == 3) {
                    return SERIALIZED_HEAP_DUMP_TOO_LARGE;
                }
                if (value2 != 4) {
                    return null;
                }
                return NONE;
            }

            public static Internal.EnumLiteMap<PrimesHeapDumpError> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return PrimesHeapDumpErrorVerifier.INSTANCE;
            }

            private static final class PrimesHeapDumpErrorVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new PrimesHeapDumpErrorVerifier();

                private PrimesHeapDumpErrorVerifier() {
                }

                public boolean isInRange(int number) {
                    return PrimesHeapDumpError.forNumber(number) != null;
                }
            }

            private PrimesHeapDumpError(int value2) {
                this.value = value2;
            }
        }

        public boolean hasError() {
            return (this.bitField0_ & 1) != 0;
        }

        public PrimesHeapDumpError getError() {
            PrimesHeapDumpError result = PrimesHeapDumpError.forNumber(this.error_);
            return result == null ? PrimesHeapDumpError.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setError(PrimesHeapDumpError value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.error_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearError() {
            this.bitField0_ &= -2;
            this.error_ = 0;
        }

        public boolean hasSerializedSizeKb() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getSerializedSizeKb() {
            return this.serializedSizeKb_;
        }

        /* access modifiers changed from: private */
        public void setSerializedSizeKb(int value) {
            this.bitField0_ |= 2;
            this.serializedSizeKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSerializedSizeKb() {
            this.bitField0_ &= -3;
            this.serializedSizeKb_ = 0;
        }

        public List<Integer> getTotalPssKbSamplesList() {
            return this.totalPssKbSamples_;
        }

        public int getTotalPssKbSamplesCount() {
            return this.totalPssKbSamples_.size();
        }

        public int getTotalPssKbSamples(int index) {
            return this.totalPssKbSamples_.getInt(index);
        }

        private void ensureTotalPssKbSamplesIsMutable() {
            if (!this.totalPssKbSamples_.isModifiable()) {
                this.totalPssKbSamples_ = GeneratedMessageLite.mutableCopy(this.totalPssKbSamples_);
            }
        }

        /* access modifiers changed from: private */
        public void setTotalPssKbSamples(int index, int value) {
            ensureTotalPssKbSamplesIsMutable();
            this.totalPssKbSamples_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addTotalPssKbSamples(int value) {
            ensureTotalPssKbSamplesIsMutable();
            this.totalPssKbSamples_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllTotalPssKbSamples(Iterable<? extends Integer> values) {
            ensureTotalPssKbSamplesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.totalPssKbSamples_);
        }

        /* access modifiers changed from: private */
        public void clearTotalPssKbSamples() {
            this.totalPssKbSamples_ = emptyIntList();
        }

        public static PrimesHeapDumpEvent parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesHeapDumpEvent parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesHeapDumpEvent parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesHeapDumpEvent parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesHeapDumpEvent parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesHeapDumpEvent parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesHeapDumpEvent parseFrom(InputStream input) throws IOException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesHeapDumpEvent parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesHeapDumpEvent parseDelimitedFrom(InputStream input) throws IOException {
            return (PrimesHeapDumpEvent) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesHeapDumpEvent parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesHeapDumpEvent) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesHeapDumpEvent parseFrom(CodedInputStream input) throws IOException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesHeapDumpEvent parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesHeapDumpEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PrimesHeapDumpEvent prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PrimesHeapDumpEvent, Builder> implements PrimesHeapDumpEventOrBuilder {
            private Builder() {
                super(PrimesHeapDumpEvent.DEFAULT_INSTANCE);
            }

            public boolean hasError() {
                return ((PrimesHeapDumpEvent) this.instance).hasError();
            }

            public PrimesHeapDumpError getError() {
                return ((PrimesHeapDumpEvent) this.instance).getError();
            }

            public Builder setError(PrimesHeapDumpError value) {
                copyOnWrite();
                ((PrimesHeapDumpEvent) this.instance).setError(value);
                return this;
            }

            public Builder clearError() {
                copyOnWrite();
                ((PrimesHeapDumpEvent) this.instance).clearError();
                return this;
            }

            public boolean hasSerializedSizeKb() {
                return ((PrimesHeapDumpEvent) this.instance).hasSerializedSizeKb();
            }

            public int getSerializedSizeKb() {
                return ((PrimesHeapDumpEvent) this.instance).getSerializedSizeKb();
            }

            public Builder setSerializedSizeKb(int value) {
                copyOnWrite();
                ((PrimesHeapDumpEvent) this.instance).setSerializedSizeKb(value);
                return this;
            }

            public Builder clearSerializedSizeKb() {
                copyOnWrite();
                ((PrimesHeapDumpEvent) this.instance).clearSerializedSizeKb();
                return this;
            }

            public List<Integer> getTotalPssKbSamplesList() {
                return Collections.unmodifiableList(((PrimesHeapDumpEvent) this.instance).getTotalPssKbSamplesList());
            }

            public int getTotalPssKbSamplesCount() {
                return ((PrimesHeapDumpEvent) this.instance).getTotalPssKbSamplesCount();
            }

            public int getTotalPssKbSamples(int index) {
                return ((PrimesHeapDumpEvent) this.instance).getTotalPssKbSamples(index);
            }

            public Builder setTotalPssKbSamples(int index, int value) {
                copyOnWrite();
                ((PrimesHeapDumpEvent) this.instance).setTotalPssKbSamples(index, value);
                return this;
            }

            public Builder addTotalPssKbSamples(int value) {
                copyOnWrite();
                ((PrimesHeapDumpEvent) this.instance).addTotalPssKbSamples(value);
                return this;
            }

            public Builder addAllTotalPssKbSamples(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((PrimesHeapDumpEvent) this.instance).addAllTotalPssKbSamples(values);
                return this;
            }

            public Builder clearTotalPssKbSamples() {
                copyOnWrite();
                ((PrimesHeapDumpEvent) this.instance).clearTotalPssKbSamples();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PrimesHeapDumpEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001\f\u0000\u0002\u0004\u0001\u0003\u0016", new Object[]{"bitField0_", "error_", PrimesHeapDumpError.internalGetVerifier(), "serializedSizeKb_", "totalPssKbSamples_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PrimesHeapDumpEvent> parser = PARSER;
                    if (parser == null) {
                        synchronized (PrimesHeapDumpEvent.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(PrimesHeapDumpEvent.class, DEFAULT_INSTANCE);
        }

        public static PrimesHeapDumpEvent getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PrimesHeapDumpEvent> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class DeviceInfo extends GeneratedMessageLite<DeviceInfo, Builder> implements DeviceInfoOrBuilder {
        public static final int AVAILABLE_DISK_SIZE_KB_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final DeviceInfo DEFAULT_INSTANCE = new DeviceInfo();
        private static volatile Parser<DeviceInfo> PARSER = null;
        public static final int TOTAL_DISK_SIZE_KB_FIELD_NUMBER = 2;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long availableDiskSizeKb_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long totalDiskSizeKb_;

        private DeviceInfo() {
        }

        public boolean hasAvailableDiskSizeKb() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getAvailableDiskSizeKb() {
            return this.availableDiskSizeKb_;
        }

        /* access modifiers changed from: private */
        public void setAvailableDiskSizeKb(long value) {
            this.bitField0_ |= 1;
            this.availableDiskSizeKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAvailableDiskSizeKb() {
            this.bitField0_ &= -2;
            this.availableDiskSizeKb_ = 0;
        }

        public boolean hasTotalDiskSizeKb() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getTotalDiskSizeKb() {
            return this.totalDiskSizeKb_;
        }

        /* access modifiers changed from: private */
        public void setTotalDiskSizeKb(long value) {
            this.bitField0_ |= 2;
            this.totalDiskSizeKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalDiskSizeKb() {
            this.bitField0_ &= -3;
            this.totalDiskSizeKb_ = 0;
        }

        public static DeviceInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceInfo parseFrom(InputStream input) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceInfo parseFrom(CodedInputStream input) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DeviceInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DeviceInfo, Builder> implements DeviceInfoOrBuilder {
            private Builder() {
                super(DeviceInfo.DEFAULT_INSTANCE);
            }

            public boolean hasAvailableDiskSizeKb() {
                return ((DeviceInfo) this.instance).hasAvailableDiskSizeKb();
            }

            public long getAvailableDiskSizeKb() {
                return ((DeviceInfo) this.instance).getAvailableDiskSizeKb();
            }

            public Builder setAvailableDiskSizeKb(long value) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setAvailableDiskSizeKb(value);
                return this;
            }

            public Builder clearAvailableDiskSizeKb() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearAvailableDiskSizeKb();
                return this;
            }

            public boolean hasTotalDiskSizeKb() {
                return ((DeviceInfo) this.instance).hasTotalDiskSizeKb();
            }

            public long getTotalDiskSizeKb() {
                return ((DeviceInfo) this.instance).getTotalDiskSizeKb();
            }

            public Builder setTotalDiskSizeKb(long value) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setTotalDiskSizeKb(value);
                return this;
            }

            public Builder clearTotalDiskSizeKb() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearTotalDiskSizeKb();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DeviceInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0000\u0002\u0002\u0001", new Object[]{"bitField0_", "availableDiskSizeKb_", "totalDiskSizeKb_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DeviceInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (DeviceInfo.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(DeviceInfo.class, DEFAULT_INSTANCE);
        }

        public static DeviceInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PrimesForPrimes extends GeneratedMessageLite<PrimesForPrimes, Builder> implements PrimesForPrimesOrBuilder {
        /* access modifiers changed from: private */
        public static final PrimesForPrimes DEFAULT_INSTANCE = new PrimesForPrimes();
        public static final int INTERNAL_TIMERS_FIELD_NUMBER = 1;
        private static volatile Parser<PrimesForPrimes> PARSER;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<InternalTimer> internalTimers_ = emptyProtobufList();

        public interface InternalTimerOrBuilder extends MessageLiteOrBuilder {
            MicroCpuTime getDuration();

            PrimesForPrimesEventProto.PrimesForPrimesEvent getPrimesForPrimesEvent();

            boolean hasDuration();

            boolean hasPrimesForPrimesEvent();
        }

        private PrimesForPrimes() {
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class InternalTimer extends GeneratedMessageLite<InternalTimer, Builder> implements InternalTimerOrBuilder {
            /* access modifiers changed from: private */
            public static final InternalTimer DEFAULT_INSTANCE = new InternalTimer();
            public static final int DURATION_FIELD_NUMBER = 2;
            private static volatile Parser<InternalTimer> PARSER = null;
            public static final int PRIMES_FOR_PRIMES_EVENT_FIELD_NUMBER = 1;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private MicroCpuTime duration_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private int primesForPrimesEvent_;

            private InternalTimer() {
            }

            public boolean hasPrimesForPrimesEvent() {
                return (this.bitField0_ & 1) != 0;
            }

            public PrimesForPrimesEventProto.PrimesForPrimesEvent getPrimesForPrimesEvent() {
                PrimesForPrimesEventProto.PrimesForPrimesEvent result = PrimesForPrimesEventProto.PrimesForPrimesEvent.forNumber(this.primesForPrimesEvent_);
                return result == null ? PrimesForPrimesEventProto.PrimesForPrimesEvent.UNKNOWN : result;
            }

            /* access modifiers changed from: private */
            public void setPrimesForPrimesEvent(PrimesForPrimesEventProto.PrimesForPrimesEvent value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.primesForPrimesEvent_ = value.getNumber();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearPrimesForPrimesEvent() {
                this.bitField0_ &= -2;
                this.primesForPrimesEvent_ = 0;
            }

            public boolean hasDuration() {
                return (this.bitField0_ & 2) != 0;
            }

            public MicroCpuTime getDuration() {
                MicroCpuTime microCpuTime = this.duration_;
                return microCpuTime == null ? MicroCpuTime.getDefaultInstance() : microCpuTime;
            }

            /* access modifiers changed from: private */
            public void setDuration(MicroCpuTime value) {
                if (value != null) {
                    this.duration_ = value;
                    this.bitField0_ |= 2;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void setDuration(MicroCpuTime.Builder builderForValue) {
                this.duration_ = (MicroCpuTime) builderForValue.build();
                this.bitField0_ |= 2;
            }

            /* access modifiers changed from: private */
            public void mergeDuration(MicroCpuTime value) {
                if (value != null) {
                    MicroCpuTime microCpuTime = this.duration_;
                    if (microCpuTime == null || microCpuTime == MicroCpuTime.getDefaultInstance()) {
                        this.duration_ = value;
                    } else {
                        this.duration_ = (MicroCpuTime) ((MicroCpuTime.Builder) MicroCpuTime.newBuilder(this.duration_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                    }
                    this.bitField0_ |= 2;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearDuration() {
                this.duration_ = null;
                this.bitField0_ &= -3;
            }

            public static InternalTimer parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static InternalTimer parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static InternalTimer parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static InternalTimer parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static InternalTimer parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static InternalTimer parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static InternalTimer parseFrom(InputStream input) throws IOException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static InternalTimer parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static InternalTimer parseDelimitedFrom(InputStream input) throws IOException {
                return (InternalTimer) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static InternalTimer parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (InternalTimer) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static InternalTimer parseFrom(CodedInputStream input) throws IOException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static InternalTimer parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (InternalTimer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(InternalTimer prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<InternalTimer, Builder> implements InternalTimerOrBuilder {
                private Builder() {
                    super(InternalTimer.DEFAULT_INSTANCE);
                }

                public boolean hasPrimesForPrimesEvent() {
                    return ((InternalTimer) this.instance).hasPrimesForPrimesEvent();
                }

                public PrimesForPrimesEventProto.PrimesForPrimesEvent getPrimesForPrimesEvent() {
                    return ((InternalTimer) this.instance).getPrimesForPrimesEvent();
                }

                public Builder setPrimesForPrimesEvent(PrimesForPrimesEventProto.PrimesForPrimesEvent value) {
                    copyOnWrite();
                    ((InternalTimer) this.instance).setPrimesForPrimesEvent(value);
                    return this;
                }

                public Builder clearPrimesForPrimesEvent() {
                    copyOnWrite();
                    ((InternalTimer) this.instance).clearPrimesForPrimesEvent();
                    return this;
                }

                public boolean hasDuration() {
                    return ((InternalTimer) this.instance).hasDuration();
                }

                public MicroCpuTime getDuration() {
                    return ((InternalTimer) this.instance).getDuration();
                }

                public Builder setDuration(MicroCpuTime value) {
                    copyOnWrite();
                    ((InternalTimer) this.instance).setDuration(value);
                    return this;
                }

                public Builder setDuration(MicroCpuTime.Builder builderForValue) {
                    copyOnWrite();
                    ((InternalTimer) this.instance).setDuration(builderForValue);
                    return this;
                }

                public Builder mergeDuration(MicroCpuTime value) {
                    copyOnWrite();
                    ((InternalTimer) this.instance).mergeDuration(value);
                    return this;
                }

                public Builder clearDuration() {
                    copyOnWrite();
                    ((InternalTimer) this.instance).clearDuration();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new InternalTimer();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\f\u0000\u0002\t\u0001", new Object[]{"bitField0_", "primesForPrimesEvent_", PrimesForPrimesEventProto.PrimesForPrimesEvent.internalGetVerifier(), "duration_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<InternalTimer> parser = PARSER;
                        if (parser == null) {
                            synchronized (InternalTimer.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return (byte) 1;
                    case SET_MEMOIZED_IS_INITIALIZED:
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                GeneratedMessageLite.registerDefaultInstance(InternalTimer.class, DEFAULT_INSTANCE);
            }

            public static InternalTimer getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<InternalTimer> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<InternalTimer> getInternalTimersList() {
            return this.internalTimers_;
        }

        public List<? extends InternalTimerOrBuilder> getInternalTimersOrBuilderList() {
            return this.internalTimers_;
        }

        public int getInternalTimersCount() {
            return this.internalTimers_.size();
        }

        public InternalTimer getInternalTimers(int index) {
            return this.internalTimers_.get(index);
        }

        public InternalTimerOrBuilder getInternalTimersOrBuilder(int index) {
            return this.internalTimers_.get(index);
        }

        private void ensureInternalTimersIsMutable() {
            if (!this.internalTimers_.isModifiable()) {
                this.internalTimers_ = GeneratedMessageLite.mutableCopy(this.internalTimers_);
            }
        }

        /* access modifiers changed from: private */
        public void setInternalTimers(int index, InternalTimer value) {
            if (value != null) {
                ensureInternalTimersIsMutable();
                this.internalTimers_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setInternalTimers(int index, InternalTimer.Builder builderForValue) {
            ensureInternalTimersIsMutable();
            this.internalTimers_.set(index, (InternalTimer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addInternalTimers(InternalTimer value) {
            if (value != null) {
                ensureInternalTimersIsMutable();
                this.internalTimers_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addInternalTimers(int index, InternalTimer value) {
            if (value != null) {
                ensureInternalTimersIsMutable();
                this.internalTimers_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addInternalTimers(InternalTimer.Builder builderForValue) {
            ensureInternalTimersIsMutable();
            this.internalTimers_.add((InternalTimer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addInternalTimers(int index, InternalTimer.Builder builderForValue) {
            ensureInternalTimersIsMutable();
            this.internalTimers_.add(index, (InternalTimer) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.SystemHealthProto$PrimesForPrimes$InternalTimer>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.SystemHealthProto$PrimesForPrimes$InternalTimer>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllInternalTimers(Iterable<? extends InternalTimer> values) {
            ensureInternalTimersIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.internalTimers_);
        }

        /* access modifiers changed from: private */
        public void clearInternalTimers() {
            this.internalTimers_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeInternalTimers(int index) {
            ensureInternalTimersIsMutable();
            this.internalTimers_.remove(index);
        }

        public static PrimesForPrimes parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesForPrimes parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesForPrimes parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesForPrimes parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesForPrimes parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesForPrimes parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesForPrimes parseFrom(InputStream input) throws IOException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesForPrimes parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesForPrimes parseDelimitedFrom(InputStream input) throws IOException {
            return (PrimesForPrimes) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesForPrimes parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesForPrimes) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesForPrimes parseFrom(CodedInputStream input) throws IOException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesForPrimes parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesForPrimes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PrimesForPrimes prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PrimesForPrimes, Builder> implements PrimesForPrimesOrBuilder {
            private Builder() {
                super(PrimesForPrimes.DEFAULT_INSTANCE);
            }

            public List<InternalTimer> getInternalTimersList() {
                return Collections.unmodifiableList(((PrimesForPrimes) this.instance).getInternalTimersList());
            }

            public int getInternalTimersCount() {
                return ((PrimesForPrimes) this.instance).getInternalTimersCount();
            }

            public InternalTimer getInternalTimers(int index) {
                return ((PrimesForPrimes) this.instance).getInternalTimers(index);
            }

            public Builder setInternalTimers(int index, InternalTimer value) {
                copyOnWrite();
                ((PrimesForPrimes) this.instance).setInternalTimers(index, value);
                return this;
            }

            public Builder setInternalTimers(int index, InternalTimer.Builder builderForValue) {
                copyOnWrite();
                ((PrimesForPrimes) this.instance).setInternalTimers(index, builderForValue);
                return this;
            }

            public Builder addInternalTimers(InternalTimer value) {
                copyOnWrite();
                ((PrimesForPrimes) this.instance).addInternalTimers(value);
                return this;
            }

            public Builder addInternalTimers(int index, InternalTimer value) {
                copyOnWrite();
                ((PrimesForPrimes) this.instance).addInternalTimers(index, value);
                return this;
            }

            public Builder addInternalTimers(InternalTimer.Builder builderForValue) {
                copyOnWrite();
                ((PrimesForPrimes) this.instance).addInternalTimers(builderForValue);
                return this;
            }

            public Builder addInternalTimers(int index, InternalTimer.Builder builderForValue) {
                copyOnWrite();
                ((PrimesForPrimes) this.instance).addInternalTimers(index, builderForValue);
                return this;
            }

            public Builder addAllInternalTimers(Iterable<? extends InternalTimer> values) {
                copyOnWrite();
                ((PrimesForPrimes) this.instance).addAllInternalTimers(values);
                return this;
            }

            public Builder clearInternalTimers() {
                copyOnWrite();
                ((PrimesForPrimes) this.instance).clearInternalTimers();
                return this;
            }

            public Builder removeInternalTimers(int index) {
                copyOnWrite();
                ((PrimesForPrimes) this.instance).removeInternalTimers(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PrimesForPrimes();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"internalTimers_", InternalTimer.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PrimesForPrimes> parser = PARSER;
                    if (parser == null) {
                        synchronized (PrimesForPrimes.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(PrimesForPrimes.class, DEFAULT_INSTANCE);
        }

        public static PrimesForPrimes getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PrimesForPrimes> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MicroCpuTime extends GeneratedMessageLite<MicroCpuTime, Builder> implements MicroCpuTimeOrBuilder {
        public static final int CPU_MICROS_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final MicroCpuTime DEFAULT_INSTANCE = new MicroCpuTime();
        private static volatile Parser<MicroCpuTime> PARSER = null;
        public static final int WALL_MICROS_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long cpuMicros_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long wallMicros_;

        private MicroCpuTime() {
        }

        public boolean hasCpuMicros() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getCpuMicros() {
            return this.cpuMicros_;
        }

        /* access modifiers changed from: private */
        public void setCpuMicros(long value) {
            this.bitField0_ |= 1;
            this.cpuMicros_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCpuMicros() {
            this.bitField0_ &= -2;
            this.cpuMicros_ = 0;
        }

        public boolean hasWallMicros() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getWallMicros() {
            return this.wallMicros_;
        }

        /* access modifiers changed from: private */
        public void setWallMicros(long value) {
            this.bitField0_ |= 2;
            this.wallMicros_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWallMicros() {
            this.bitField0_ &= -3;
            this.wallMicros_ = 0;
        }

        public static MicroCpuTime parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MicroCpuTime parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MicroCpuTime parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MicroCpuTime parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MicroCpuTime parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MicroCpuTime parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MicroCpuTime parseFrom(InputStream input) throws IOException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MicroCpuTime parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MicroCpuTime parseDelimitedFrom(InputStream input) throws IOException {
            return (MicroCpuTime) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MicroCpuTime parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MicroCpuTime) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MicroCpuTime parseFrom(CodedInputStream input) throws IOException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MicroCpuTime parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MicroCpuTime) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MicroCpuTime prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<MicroCpuTime, Builder> implements MicroCpuTimeOrBuilder {
            private Builder() {
                super(MicroCpuTime.DEFAULT_INSTANCE);
            }

            public boolean hasCpuMicros() {
                return ((MicroCpuTime) this.instance).hasCpuMicros();
            }

            public long getCpuMicros() {
                return ((MicroCpuTime) this.instance).getCpuMicros();
            }

            public Builder setCpuMicros(long value) {
                copyOnWrite();
                ((MicroCpuTime) this.instance).setCpuMicros(value);
                return this;
            }

            public Builder clearCpuMicros() {
                copyOnWrite();
                ((MicroCpuTime) this.instance).clearCpuMicros();
                return this;
            }

            public boolean hasWallMicros() {
                return ((MicroCpuTime) this.instance).hasWallMicros();
            }

            public long getWallMicros() {
                return ((MicroCpuTime) this.instance).getWallMicros();
            }

            public Builder setWallMicros(long value) {
                copyOnWrite();
                ((MicroCpuTime) this.instance).setWallMicros(value);
                return this;
            }

            public Builder clearWallMicros() {
                copyOnWrite();
                ((MicroCpuTime) this.instance).clearWallMicros();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MicroCpuTime();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0000\u0002\u0002\u0001", new Object[]{"bitField0_", "cpuMicros_", "wallMicros_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MicroCpuTime> parser = PARSER;
                    if (parser == null) {
                        synchronized (MicroCpuTime.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(MicroCpuTime.class, DEFAULT_INSTANCE);
        }

        public static MicroCpuTime getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MicroCpuTime> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PrimesHeapDumpCalibrationStatus extends GeneratedMessageLite<PrimesHeapDumpCalibrationStatus, Builder> implements PrimesHeapDumpCalibrationStatusOrBuilder {
        public static final int CURRENT_SAMPLE_COUNT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final PrimesHeapDumpCalibrationStatus DEFAULT_INSTANCE = new PrimesHeapDumpCalibrationStatus();
        public static final int NEW_SAMPLE_PERCENTILE_FIELD_NUMBER = 2;
        private static volatile Parser<PrimesHeapDumpCalibrationStatus> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int currentSampleCount_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.FLOAT)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private float newSamplePercentile_;

        private PrimesHeapDumpCalibrationStatus() {
        }

        public boolean hasCurrentSampleCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getCurrentSampleCount() {
            return this.currentSampleCount_;
        }

        /* access modifiers changed from: private */
        public void setCurrentSampleCount(int value) {
            this.bitField0_ |= 1;
            this.currentSampleCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCurrentSampleCount() {
            this.bitField0_ &= -2;
            this.currentSampleCount_ = 0;
        }

        public boolean hasNewSamplePercentile() {
            return (this.bitField0_ & 2) != 0;
        }

        public float getNewSamplePercentile() {
            return this.newSamplePercentile_;
        }

        /* access modifiers changed from: private */
        public void setNewSamplePercentile(float value) {
            this.bitField0_ |= 2;
            this.newSamplePercentile_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNewSamplePercentile() {
            this.bitField0_ &= -3;
            this.newSamplePercentile_ = 0.0f;
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(InputStream input) throws IOException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesHeapDumpCalibrationStatus parseDelimitedFrom(InputStream input) throws IOException {
            return (PrimesHeapDumpCalibrationStatus) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesHeapDumpCalibrationStatus parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesHeapDumpCalibrationStatus) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(CodedInputStream input) throws IOException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesHeapDumpCalibrationStatus parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesHeapDumpCalibrationStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PrimesHeapDumpCalibrationStatus prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PrimesHeapDumpCalibrationStatus, Builder> implements PrimesHeapDumpCalibrationStatusOrBuilder {
            private Builder() {
                super(PrimesHeapDumpCalibrationStatus.DEFAULT_INSTANCE);
            }

            public boolean hasCurrentSampleCount() {
                return ((PrimesHeapDumpCalibrationStatus) this.instance).hasCurrentSampleCount();
            }

            public int getCurrentSampleCount() {
                return ((PrimesHeapDumpCalibrationStatus) this.instance).getCurrentSampleCount();
            }

            public Builder setCurrentSampleCount(int value) {
                copyOnWrite();
                ((PrimesHeapDumpCalibrationStatus) this.instance).setCurrentSampleCount(value);
                return this;
            }

            public Builder clearCurrentSampleCount() {
                copyOnWrite();
                ((PrimesHeapDumpCalibrationStatus) this.instance).clearCurrentSampleCount();
                return this;
            }

            public boolean hasNewSamplePercentile() {
                return ((PrimesHeapDumpCalibrationStatus) this.instance).hasNewSamplePercentile();
            }

            public float getNewSamplePercentile() {
                return ((PrimesHeapDumpCalibrationStatus) this.instance).getNewSamplePercentile();
            }

            public Builder setNewSamplePercentile(float value) {
                copyOnWrite();
                ((PrimesHeapDumpCalibrationStatus) this.instance).setNewSamplePercentile(value);
                return this;
            }

            public Builder clearNewSamplePercentile() {
                copyOnWrite();
                ((PrimesHeapDumpCalibrationStatus) this.instance).clearNewSamplePercentile();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PrimesHeapDumpCalibrationStatus();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0001\u0001", new Object[]{"bitField0_", "currentSampleCount_", "newSamplePercentile_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PrimesHeapDumpCalibrationStatus> parser = PARSER;
                    if (parser == null) {
                        synchronized (PrimesHeapDumpCalibrationStatus.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(PrimesHeapDumpCalibrationStatus.class, DEFAULT_INSTANCE);
        }

        public static PrimesHeapDumpCalibrationStatus getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PrimesHeapDumpCalibrationStatus> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class StrictModeViolationMetric extends GeneratedMessageLite<StrictModeViolationMetric, Builder> implements StrictModeViolationMetricOrBuilder {
        /* access modifiers changed from: private */
        public static final StrictModeViolationMetric DEFAULT_INSTANCE = new StrictModeViolationMetric();
        private static volatile Parser<StrictModeViolationMetric> PARSER = null;
        public static final int VIOLATION_TYPE_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int violationType_;

        private StrictModeViolationMetric() {
        }

        public enum ViolationType implements Internal.EnumLite {
            UNKNOWN(0),
            DISK_READ(1),
            DISK_WRITE(2),
            SLOW(3);
            
            public static final int DISK_READ_VALUE = 1;
            public static final int DISK_WRITE_VALUE = 2;
            public static final int SLOW_VALUE = 3;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<ViolationType> internalValueMap = new Internal.EnumLiteMap<ViolationType>() {
                public ViolationType findValueByNumber(int number) {
                    return ViolationType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static ViolationType forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return DISK_READ;
                }
                if (value2 == 2) {
                    return DISK_WRITE;
                }
                if (value2 != 3) {
                    return null;
                }
                return SLOW;
            }

            public static Internal.EnumLiteMap<ViolationType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return ViolationTypeVerifier.INSTANCE;
            }

            private static final class ViolationTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new ViolationTypeVerifier();

                private ViolationTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return ViolationType.forNumber(number) != null;
                }
            }

            private ViolationType(int value2) {
                this.value = value2;
            }
        }

        public boolean hasViolationType() {
            return (this.bitField0_ & 1) != 0;
        }

        public ViolationType getViolationType() {
            ViolationType result = ViolationType.forNumber(this.violationType_);
            return result == null ? ViolationType.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setViolationType(ViolationType value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.violationType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearViolationType() {
            this.bitField0_ &= -2;
            this.violationType_ = 0;
        }

        public static StrictModeViolationMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StrictModeViolationMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StrictModeViolationMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StrictModeViolationMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StrictModeViolationMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StrictModeViolationMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StrictModeViolationMetric parseFrom(InputStream input) throws IOException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StrictModeViolationMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StrictModeViolationMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (StrictModeViolationMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static StrictModeViolationMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StrictModeViolationMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StrictModeViolationMetric parseFrom(CodedInputStream input) throws IOException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StrictModeViolationMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StrictModeViolationMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(StrictModeViolationMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<StrictModeViolationMetric, Builder> implements StrictModeViolationMetricOrBuilder {
            private Builder() {
                super(StrictModeViolationMetric.DEFAULT_INSTANCE);
            }

            public boolean hasViolationType() {
                return ((StrictModeViolationMetric) this.instance).hasViolationType();
            }

            public ViolationType getViolationType() {
                return ((StrictModeViolationMetric) this.instance).getViolationType();
            }

            public Builder setViolationType(ViolationType value) {
                copyOnWrite();
                ((StrictModeViolationMetric) this.instance).setViolationType(value);
                return this;
            }

            public Builder clearViolationType() {
                copyOnWrite();
                ((StrictModeViolationMetric) this.instance).clearViolationType();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new StrictModeViolationMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\f\u0000", new Object[]{"bitField0_", "violationType_", ViolationType.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<StrictModeViolationMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (StrictModeViolationMetric.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(StrictModeViolationMetric.class, DEFAULT_INSTANCE);
        }

        public static StrictModeViolationMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<StrictModeViolationMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
