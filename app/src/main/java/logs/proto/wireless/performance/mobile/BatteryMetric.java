package logs.proto.wireless.performance.mobile;

import com.google.android.exoplayer2.C0841C;
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

public final class BatteryMetric {

    private BatteryMetric() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface BatteryStatsDiffOrBuilder extends MessageLiteOrBuilder {
        long getDurationMs();

        long getElapedRealtimeMs();

        BatteryStatsDiff.SampleInfo getEndInfo();

        String getStartConstantEventName();

        ByteString getStartConstantEventNameBytes();

        String getStartCustomEventName();

        ByteString getStartCustomEventNameBytes();

        long getStartHashedCustomEventName();

        BatteryStatsDiff.SampleInfo getStartInfo();

        ExtensionMetric.MetricExtension getStartMetricExtension();

        @Deprecated
        SyncStats getSyncStats(int i);

        @Deprecated
        int getSyncStatsCount();

        @Deprecated
        List<SyncStats> getSyncStatsList();

        UidHealthProto getUidHealthProtoDiff();

        @Deprecated
        WakelockStats getWakelockStats(int i);

        @Deprecated
        int getWakelockStatsCount();

        @Deprecated
        List<WakelockStats> getWakelockStatsList();

        boolean hasDurationMs();

        boolean hasElapedRealtimeMs();

        boolean hasEndInfo();

        boolean hasStartConstantEventName();

        boolean hasStartCustomEventName();

        boolean hasStartHashedCustomEventName();

        boolean hasStartInfo();

        boolean hasStartMetricExtension();

        boolean hasUidHealthProtoDiff();
    }

    public interface BatteryUsageMetricOrBuilder extends MessageLiteOrBuilder {
        BatteryStatsDiff getBatteryStatsDiff();

        boolean hasBatteryStatsDiff();
    }

    public interface CounterOrBuilder extends MessageLiteOrBuilder {
        int getCount();

        HashedString getName();

        boolean hasCount();

        boolean hasName();
    }

    public interface HashedStringOrBuilder extends MessageLiteOrBuilder {
        long getHash();

        String getUnhashedName();

        ByteString getUnhashedNameBytes();

        boolean hasHash();

        boolean hasUnhashedName();
    }

    public interface PackageHealthProtoOrBuilder extends MessageLiteOrBuilder {
        HashedString getName();

        ServiceHealthProto getStatsServices(int i);

        int getStatsServicesCount();

        List<ServiceHealthProto> getStatsServicesList();

        Counter getWakeupAlarmsCount(int i);

        int getWakeupAlarmsCountCount();

        List<Counter> getWakeupAlarmsCountList();

        boolean hasName();
    }

    public interface PidHealthProtoOrBuilder extends MessageLiteOrBuilder {
    }

    public interface ProcessHealthProtoOrBuilder extends MessageLiteOrBuilder {
        long getAnrCount();

        long getCrashesCount();

        long getForegroundMs();

        HashedString getName();

        long getStartsCount();

        long getSystemTimeMs();

        long getUserTimeMs();

        boolean hasAnrCount();

        boolean hasCrashesCount();

        boolean hasForegroundMs();

        boolean hasName();

        boolean hasStartsCount();

        boolean hasSystemTimeMs();

        boolean hasUserTimeMs();
    }

    public interface ServiceHealthProtoOrBuilder extends MessageLiteOrBuilder {
        int getLaunchCount();

        HashedString getName();

        int getStartServiceCount();

        boolean hasLaunchCount();

        boolean hasName();

        boolean hasStartServiceCount();
    }

    public interface SyncStatsOrBuilder extends MessageLiteOrBuilder {
        @Deprecated
        int getCount();

        @Deprecated
        long getDurationMs();

        @Deprecated
        HashedString getName();

        @Deprecated
        boolean hasCount();

        @Deprecated
        boolean hasDurationMs();

        @Deprecated
        boolean hasName();
    }

    public interface TimerOrBuilder extends MessageLiteOrBuilder {
        int getCount();

        long getDurationMs();

        HashedString getName();

        boolean hasCount();

        boolean hasDurationMs();

        boolean hasName();
    }

    public interface UidHealthProtoOrBuilder extends MessageLiteOrBuilder {
        Timer getAudio();

        long getBluetoothIdleMs();

        long getBluetoothPowerMams();

        long getBluetoothRxBytes();

        long getBluetoothRxMs();

        long getBluetoothRxPackets();

        Timer getBluetoothScan();

        long getBluetoothTxBytes();

        long getBluetoothTxMs();

        long getBluetoothTxPackets();

        long getButtonUserActivityCount();

        Timer getCamera();

        long getCpuPowerMams();

        Timer getFlashlight();

        Timer getForegroundActivity();

        Timer getGpsSensor();

        Timer getJobs(int i);

        int getJobsCount();

        List<Timer> getJobsList();

        long getMobileIdleMs();

        long getMobilePowerMams();

        Timer getMobileRadioActive();

        long getMobileRxBytes();

        long getMobileRxMs();

        long getMobileRxPackets();

        long getMobileTxBytes();

        long getMobileTxMs();

        long getMobileTxPackets();

        long getOtherUserActivityCount();

        Timer getProcessStateBackgroundMs();

        Timer getProcessStateCachedMs();

        Timer getProcessStateForegroundMs();

        Timer getProcessStateForegroundServiceMs();

        Timer getProcessStateTopMs();

        Timer getProcessStateTopSleepingMs();

        long getRealtimeBatteryMs();

        long getRealtimeScreenOffBatteryMs();

        Timer getSensors(int i);

        int getSensorsCount();

        List<Timer> getSensorsList();

        PackageHealthProto getStatsPackages(int i);

        int getStatsPackagesCount();

        List<PackageHealthProto> getStatsPackagesList();

        PidHealthProto getStatsPids(int i);

        int getStatsPidsCount();

        List<PidHealthProto> getStatsPidsList();

        ProcessHealthProto getStatsProcesses(int i);

        int getStatsProcessesCount();

        List<ProcessHealthProto> getStatsProcessesList();

        Timer getSyncs(int i);

        int getSyncsCount();

        List<Timer> getSyncsList();

        long getSystemCpuTimeMs();

        long getTouchUserActivityCount();

        long getUptimeBatteryMs();

        long getUptimeScreenOffBatteryMs();

        long getUserCpuTimeMs();

        Timer getVibrator();

        Timer getVideo();

        Timer getWakelocksDraw(int i);

        int getWakelocksDrawCount();

        List<Timer> getWakelocksDrawList();

        Timer getWakelocksFull(int i);

        int getWakelocksFullCount();

        List<Timer> getWakelocksFullList();

        Timer getWakelocksPartial(int i);

        int getWakelocksPartialCount();

        List<Timer> getWakelocksPartialList();

        Timer getWakelocksWindow(int i);

        int getWakelocksWindowCount();

        List<Timer> getWakelocksWindowList();

        long getWifiFullLockMs();

        long getWifiIdleMs();

        long getWifiMulticastMs();

        long getWifiPowerMams();

        long getWifiRunningMs();

        long getWifiRxBytes();

        long getWifiRxMs();

        long getWifiRxPackets();

        Timer getWifiScan();

        long getWifiTxBytes();

        long getWifiTxMs();

        long getWifiTxPackets();

        boolean hasAudio();

        boolean hasBluetoothIdleMs();

        boolean hasBluetoothPowerMams();

        boolean hasBluetoothRxBytes();

        boolean hasBluetoothRxMs();

        boolean hasBluetoothRxPackets();

        boolean hasBluetoothScan();

        boolean hasBluetoothTxBytes();

        boolean hasBluetoothTxMs();

        boolean hasBluetoothTxPackets();

        boolean hasButtonUserActivityCount();

        boolean hasCamera();

        boolean hasCpuPowerMams();

        boolean hasFlashlight();

        boolean hasForegroundActivity();

        boolean hasGpsSensor();

        boolean hasMobileIdleMs();

        boolean hasMobilePowerMams();

        boolean hasMobileRadioActive();

        boolean hasMobileRxBytes();

        boolean hasMobileRxMs();

        boolean hasMobileRxPackets();

        boolean hasMobileTxBytes();

        boolean hasMobileTxMs();

        boolean hasMobileTxPackets();

        boolean hasOtherUserActivityCount();

        boolean hasProcessStateBackgroundMs();

        boolean hasProcessStateCachedMs();

        boolean hasProcessStateForegroundMs();

        boolean hasProcessStateForegroundServiceMs();

        boolean hasProcessStateTopMs();

        boolean hasProcessStateTopSleepingMs();

        boolean hasRealtimeBatteryMs();

        boolean hasRealtimeScreenOffBatteryMs();

        boolean hasSystemCpuTimeMs();

        boolean hasTouchUserActivityCount();

        boolean hasUptimeBatteryMs();

        boolean hasUptimeScreenOffBatteryMs();

        boolean hasUserCpuTimeMs();

        boolean hasVibrator();

        boolean hasVideo();

        boolean hasWifiFullLockMs();

        boolean hasWifiIdleMs();

        boolean hasWifiMulticastMs();

        boolean hasWifiPowerMams();

        boolean hasWifiRunningMs();

        boolean hasWifiRxBytes();

        boolean hasWifiRxMs();

        boolean hasWifiRxPackets();

        boolean hasWifiScan();

        boolean hasWifiTxBytes();

        boolean hasWifiTxMs();

        boolean hasWifiTxPackets();
    }

    public interface WakelockStatsOrBuilder extends MessageLiteOrBuilder {
        @Deprecated
        int getCount();

        @Deprecated
        long getDurationMs();

        @Deprecated
        HashedString getName();

        @Deprecated
        boolean hasCount();

        @Deprecated
        boolean hasDurationMs();

        @Deprecated
        boolean hasName();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class BatteryUsageMetric extends GeneratedMessageLite<BatteryUsageMetric, Builder> implements BatteryUsageMetricOrBuilder {
        public static final int BATTERY_STATS_DIFF_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final BatteryUsageMetric DEFAULT_INSTANCE = new BatteryUsageMetric();
        private static volatile Parser<BatteryUsageMetric> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(BatteryUsageMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private BatteryStatsDiff batteryStatsDiff_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;

        private BatteryUsageMetric() {
        }

        public static BatteryUsageMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BatteryUsageMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BatteryUsageMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BatteryUsageMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BatteryUsageMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BatteryUsageMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BatteryUsageMetric parseFrom(InputStream input) throws IOException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BatteryUsageMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BatteryUsageMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (BatteryUsageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static BatteryUsageMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BatteryUsageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BatteryUsageMetric parseFrom(CodedInputStream input) throws IOException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BatteryUsageMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BatteryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(BatteryUsageMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static BatteryUsageMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BatteryUsageMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasBatteryStatsDiff() {
            return (this.bitField0_ & 1) != 0;
        }

        public BatteryStatsDiff getBatteryStatsDiff() {
            BatteryStatsDiff batteryStatsDiff = this.batteryStatsDiff_;
            return batteryStatsDiff == null ? BatteryStatsDiff.getDefaultInstance() : batteryStatsDiff;
        }

        /* access modifiers changed from: private */
        public void setBatteryStatsDiff(BatteryStatsDiff value) {
            if (value != null) {
                this.batteryStatsDiff_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setBatteryStatsDiff(BatteryStatsDiff.Builder builderForValue) {
            this.batteryStatsDiff_ = (BatteryStatsDiff) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeBatteryStatsDiff(BatteryStatsDiff value) {
            if (value != null) {
                BatteryStatsDiff batteryStatsDiff = this.batteryStatsDiff_;
                if (batteryStatsDiff == null || batteryStatsDiff == BatteryStatsDiff.getDefaultInstance()) {
                    this.batteryStatsDiff_ = value;
                } else {
                    this.batteryStatsDiff_ = (BatteryStatsDiff) ((BatteryStatsDiff.Builder) BatteryStatsDiff.newBuilder(this.batteryStatsDiff_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearBatteryStatsDiff() {
            this.batteryStatsDiff_ = null;
            this.bitField0_ &= -2;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new BatteryUsageMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t\u0000", new Object[]{"bitField0_", "batteryStatsDiff_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<BatteryUsageMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (BatteryUsageMetric.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<BatteryUsageMetric, Builder> implements BatteryUsageMetricOrBuilder {
            private Builder() {
                super(BatteryUsageMetric.DEFAULT_INSTANCE);
            }

            public boolean hasBatteryStatsDiff() {
                return ((BatteryUsageMetric) this.instance).hasBatteryStatsDiff();
            }

            public BatteryStatsDiff getBatteryStatsDiff() {
                return ((BatteryUsageMetric) this.instance).getBatteryStatsDiff();
            }

            public Builder setBatteryStatsDiff(BatteryStatsDiff value) {
                copyOnWrite();
                ((BatteryUsageMetric) this.instance).setBatteryStatsDiff(value);
                return this;
            }

            public Builder setBatteryStatsDiff(BatteryStatsDiff.Builder builderForValue) {
                copyOnWrite();
                ((BatteryUsageMetric) this.instance).setBatteryStatsDiff(builderForValue);
                return this;
            }

            public Builder mergeBatteryStatsDiff(BatteryStatsDiff value) {
                copyOnWrite();
                ((BatteryUsageMetric) this.instance).mergeBatteryStatsDiff(value);
                return this;
            }

            public Builder clearBatteryStatsDiff() {
                copyOnWrite();
                ((BatteryUsageMetric) this.instance).clearBatteryStatsDiff();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class BatteryStatsDiff extends GeneratedMessageLite<BatteryStatsDiff, Builder> implements BatteryStatsDiffOrBuilder {
        /* access modifiers changed from: private */
        public static final BatteryStatsDiff DEFAULT_INSTANCE = new BatteryStatsDiff();
        public static final int DURATION_MS_FIELD_NUMBER = 3;
        public static final int ELAPED_REALTIME_MS_FIELD_NUMBER = 7;
        public static final int END_INFO_FIELD_NUMBER = 2;
        public static final int START_CONSTANT_EVENT_NAME_FIELD_NUMBER = 10;
        public static final int START_CUSTOM_EVENT_NAME_FIELD_NUMBER = 9;
        public static final int START_HASHED_CUSTOM_EVENT_NAME_FIELD_NUMBER = 8;
        public static final int START_INFO_FIELD_NUMBER = 1;
        public static final int START_METRIC_EXTENSION_FIELD_NUMBER = 11;
        public static final int SYNC_STATS_FIELD_NUMBER = 5;
        public static final int UID_HEALTH_PROTO_DIFF_FIELD_NUMBER = 6;
        public static final int WAKELOCK_STATS_FIELD_NUMBER = 4;
        private static volatile Parser<BatteryStatsDiff> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(BatteryStatsDiff.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private long durationMs_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private long elapedRealtimeMs_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int endInfo_;
        @ProtoField(fieldNumber = 10, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private String startConstantEventName_ = "";
        @ProtoField(fieldNumber = 9, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String startCustomEventName_ = "";
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long startHashedCustomEventName_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int startInfo_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private ExtensionMetric.MetricExtension startMetricExtension_;
        @ProtoField(fieldNumber = 5, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<SyncStats> syncStats_ = emptyProtobufList();
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private UidHealthProto uidHealthProtoDiff_;
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<WakelockStats> wakelockStats_ = emptyProtobufList();

        private BatteryStatsDiff() {
        }

        public static BatteryStatsDiff parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BatteryStatsDiff parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BatteryStatsDiff parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BatteryStatsDiff parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BatteryStatsDiff parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BatteryStatsDiff parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BatteryStatsDiff parseFrom(InputStream input) throws IOException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BatteryStatsDiff parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BatteryStatsDiff parseDelimitedFrom(InputStream input) throws IOException {
            return (BatteryStatsDiff) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static BatteryStatsDiff parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BatteryStatsDiff) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BatteryStatsDiff parseFrom(CodedInputStream input) throws IOException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BatteryStatsDiff parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BatteryStatsDiff) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(BatteryStatsDiff prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static BatteryStatsDiff getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BatteryStatsDiff> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasStartInfo() {
            return (this.bitField0_ & 1) != 0;
        }

        public SampleInfo getStartInfo() {
            SampleInfo result = SampleInfo.forNumber(this.startInfo_);
            return result == null ? SampleInfo.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setStartInfo(SampleInfo value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.startInfo_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearStartInfo() {
            this.bitField0_ &= -2;
            this.startInfo_ = 0;
        }

        public boolean hasStartHashedCustomEventName() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getStartHashedCustomEventName() {
            return this.startHashedCustomEventName_;
        }

        /* access modifiers changed from: private */
        public void setStartHashedCustomEventName(long value) {
            this.bitField0_ |= 2;
            this.startHashedCustomEventName_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStartHashedCustomEventName() {
            this.bitField0_ &= -3;
            this.startHashedCustomEventName_ = 0;
        }

        public boolean hasStartCustomEventName() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getStartCustomEventName() {
            return this.startCustomEventName_;
        }

        /* access modifiers changed from: private */
        public void setStartCustomEventName(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.startCustomEventName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getStartCustomEventNameBytes() {
            return ByteString.copyFromUtf8(this.startCustomEventName_);
        }

        /* access modifiers changed from: private */
        public void setStartCustomEventNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.startCustomEventName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearStartCustomEventName() {
            this.bitField0_ &= -5;
            this.startCustomEventName_ = getDefaultInstance().getStartCustomEventName();
        }

        public boolean hasStartConstantEventName() {
            return (this.bitField0_ & 8) != 0;
        }

        public String getStartConstantEventName() {
            return this.startConstantEventName_;
        }

        /* access modifiers changed from: private */
        public void setStartConstantEventName(String value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.startConstantEventName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getStartConstantEventNameBytes() {
            return ByteString.copyFromUtf8(this.startConstantEventName_);
        }

        /* access modifiers changed from: private */
        public void setStartConstantEventNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.startConstantEventName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearStartConstantEventName() {
            this.bitField0_ &= -9;
            this.startConstantEventName_ = getDefaultInstance().getStartConstantEventName();
        }

        public boolean hasStartMetricExtension() {
            return (this.bitField0_ & 16) != 0;
        }

        public ExtensionMetric.MetricExtension getStartMetricExtension() {
            ExtensionMetric.MetricExtension metricExtension = this.startMetricExtension_;
            return metricExtension == null ? ExtensionMetric.MetricExtension.getDefaultInstance() : metricExtension;
        }

        /* access modifiers changed from: private */
        public void setStartMetricExtension(ExtensionMetric.MetricExtension value) {
            if (value != null) {
                this.startMetricExtension_ = value;
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setStartMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
            this.startMetricExtension_ = (ExtensionMetric.MetricExtension) builderForValue.build();
            this.bitField0_ |= 16;
        }

        /* access modifiers changed from: private */
        public void mergeStartMetricExtension(ExtensionMetric.MetricExtension value) {
            if (value != null) {
                ExtensionMetric.MetricExtension metricExtension = this.startMetricExtension_;
                if (metricExtension == null || metricExtension == ExtensionMetric.MetricExtension.getDefaultInstance()) {
                    this.startMetricExtension_ = value;
                } else {
                    this.startMetricExtension_ = (ExtensionMetric.MetricExtension) ((ExtensionMetric.MetricExtension.Builder) ExtensionMetric.MetricExtension.newBuilder(this.startMetricExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearStartMetricExtension() {
            this.startMetricExtension_ = null;
            this.bitField0_ &= -17;
        }

        public boolean hasEndInfo() {
            return (this.bitField0_ & 32) != 0;
        }

        public SampleInfo getEndInfo() {
            SampleInfo result = SampleInfo.forNumber(this.endInfo_);
            return result == null ? SampleInfo.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setEndInfo(SampleInfo value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.endInfo_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearEndInfo() {
            this.bitField0_ &= -33;
            this.endInfo_ = 0;
        }

        public boolean hasDurationMs() {
            return (this.bitField0_ & 64) != 0;
        }

        public long getDurationMs() {
            return this.durationMs_;
        }

        /* access modifiers changed from: private */
        public void setDurationMs(long value) {
            this.bitField0_ |= 64;
            this.durationMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDurationMs() {
            this.bitField0_ &= -65;
            this.durationMs_ = 0;
        }

        @Deprecated
        public List<WakelockStats> getWakelockStatsList() {
            return this.wakelockStats_;
        }

        @Deprecated
        public List<? extends WakelockStatsOrBuilder> getWakelockStatsOrBuilderList() {
            return this.wakelockStats_;
        }

        @Deprecated
        public int getWakelockStatsCount() {
            return this.wakelockStats_.size();
        }

        @Deprecated
        public WakelockStats getWakelockStats(int index) {
            return this.wakelockStats_.get(index);
        }

        @Deprecated
        public WakelockStatsOrBuilder getWakelockStatsOrBuilder(int index) {
            return this.wakelockStats_.get(index);
        }

        private void ensureWakelockStatsIsMutable() {
            if (!this.wakelockStats_.isModifiable()) {
                this.wakelockStats_ = GeneratedMessageLite.mutableCopy(this.wakelockStats_);
            }
        }

        /* access modifiers changed from: private */
        public void setWakelockStats(int index, WakelockStats value) {
            if (value != null) {
                ensureWakelockStatsIsMutable();
                this.wakelockStats_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setWakelockStats(int index, WakelockStats.Builder builderForValue) {
            ensureWakelockStatsIsMutable();
            this.wakelockStats_.set(index, (WakelockStats) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelockStats(WakelockStats value) {
            if (value != null) {
                ensureWakelockStatsIsMutable();
                this.wakelockStats_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelockStats(int index, WakelockStats value) {
            if (value != null) {
                ensureWakelockStatsIsMutable();
                this.wakelockStats_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelockStats(WakelockStats.Builder builderForValue) {
            ensureWakelockStatsIsMutable();
            this.wakelockStats_.add((WakelockStats) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelockStats(int index, WakelockStats.Builder builderForValue) {
            ensureWakelockStatsIsMutable();
            this.wakelockStats_.add(index, (WakelockStats) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$WakelockStats>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$WakelockStats>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllWakelockStats(Iterable<? extends WakelockStats> values) {
            ensureWakelockStatsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.wakelockStats_);
        }

        /* access modifiers changed from: private */
        public void clearWakelockStats() {
            this.wakelockStats_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeWakelockStats(int index) {
            ensureWakelockStatsIsMutable();
            this.wakelockStats_.remove(index);
        }

        @Deprecated
        public List<SyncStats> getSyncStatsList() {
            return this.syncStats_;
        }

        @Deprecated
        public List<? extends SyncStatsOrBuilder> getSyncStatsOrBuilderList() {
            return this.syncStats_;
        }

        @Deprecated
        public int getSyncStatsCount() {
            return this.syncStats_.size();
        }

        @Deprecated
        public SyncStats getSyncStats(int index) {
            return this.syncStats_.get(index);
        }

        @Deprecated
        public SyncStatsOrBuilder getSyncStatsOrBuilder(int index) {
            return this.syncStats_.get(index);
        }

        private void ensureSyncStatsIsMutable() {
            if (!this.syncStats_.isModifiable()) {
                this.syncStats_ = GeneratedMessageLite.mutableCopy(this.syncStats_);
            }
        }

        /* access modifiers changed from: private */
        public void setSyncStats(int index, SyncStats value) {
            if (value != null) {
                ensureSyncStatsIsMutable();
                this.syncStats_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSyncStats(int index, SyncStats.Builder builderForValue) {
            ensureSyncStatsIsMutable();
            this.syncStats_.set(index, (SyncStats) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSyncStats(SyncStats value) {
            if (value != null) {
                ensureSyncStatsIsMutable();
                this.syncStats_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSyncStats(int index, SyncStats value) {
            if (value != null) {
                ensureSyncStatsIsMutable();
                this.syncStats_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSyncStats(SyncStats.Builder builderForValue) {
            ensureSyncStatsIsMutable();
            this.syncStats_.add((SyncStats) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSyncStats(int index, SyncStats.Builder builderForValue) {
            ensureSyncStatsIsMutable();
            this.syncStats_.add(index, (SyncStats) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$SyncStats>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$SyncStats>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSyncStats(Iterable<? extends SyncStats> values) {
            ensureSyncStatsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.syncStats_);
        }

        /* access modifiers changed from: private */
        public void clearSyncStats() {
            this.syncStats_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSyncStats(int index) {
            ensureSyncStatsIsMutable();
            this.syncStats_.remove(index);
        }

        public boolean hasUidHealthProtoDiff() {
            return (this.bitField0_ & 128) != 0;
        }

        public UidHealthProto getUidHealthProtoDiff() {
            UidHealthProto uidHealthProto = this.uidHealthProtoDiff_;
            return uidHealthProto == null ? UidHealthProto.getDefaultInstance() : uidHealthProto;
        }

        /* access modifiers changed from: private */
        public void setUidHealthProtoDiff(UidHealthProto value) {
            if (value != null) {
                this.uidHealthProtoDiff_ = value;
                this.bitField0_ |= 128;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUidHealthProtoDiff(UidHealthProto.Builder builderForValue) {
            this.uidHealthProtoDiff_ = (UidHealthProto) builderForValue.build();
            this.bitField0_ |= 128;
        }

        /* access modifiers changed from: private */
        public void mergeUidHealthProtoDiff(UidHealthProto value) {
            if (value != null) {
                UidHealthProto uidHealthProto = this.uidHealthProtoDiff_;
                if (uidHealthProto == null || uidHealthProto == UidHealthProto.getDefaultInstance()) {
                    this.uidHealthProtoDiff_ = value;
                } else {
                    this.uidHealthProtoDiff_ = (UidHealthProto) ((UidHealthProto.Builder) UidHealthProto.newBuilder(this.uidHealthProtoDiff_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 128;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearUidHealthProtoDiff() {
            this.uidHealthProtoDiff_ = null;
            this.bitField0_ &= -129;
        }

        public boolean hasElapedRealtimeMs() {
            return (this.bitField0_ & 256) != 0;
        }

        public long getElapedRealtimeMs() {
            return this.elapedRealtimeMs_;
        }

        /* access modifiers changed from: private */
        public void setElapedRealtimeMs(long value) {
            this.bitField0_ |= 256;
            this.elapedRealtimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearElapedRealtimeMs() {
            this.bitField0_ &= -257;
            this.elapedRealtimeMs_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new BatteryStatsDiff();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0002\u0000\u0001\f\u0000\u0002\f\u0005\u0003\u0002\u0006\u0004\u001b\u0005\u001b\u0006\t\u0007\u0007\u0002\b\b\u0005\u0001\t\b\u0002\n\b\u0003\u000b\t\u0004", new Object[]{"bitField0_", "startInfo_", SampleInfo.internalGetVerifier(), "endInfo_", SampleInfo.internalGetVerifier(), "durationMs_", "wakelockStats_", WakelockStats.class, "syncStats_", SyncStats.class, "uidHealthProtoDiff_", "elapedRealtimeMs_", "startHashedCustomEventName_", "startCustomEventName_", "startConstantEventName_", "startMetricExtension_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<BatteryStatsDiff> parser = PARSER;
                    if (parser == null) {
                        synchronized (BatteryStatsDiff.class) {
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

        public enum SampleInfo implements Internal.EnumLite {
            UNKNOWN(0),
            FOREGROUND_TO_BACKGROUND(1),
            BACKGROUND_TO_FOREGROUND(2),
            FOREGROUND_SERVICE_START(3),
            FOREGROUND_SERVICE_STOP(4),
            CUSTOM_MEASURE_START(5),
            CUSTOM_MEASURE_STOP(6);

            public static final int BACKGROUND_TO_FOREGROUND_VALUE = 2;
            public static final int CUSTOM_MEASURE_START_VALUE = 5;
            public static final int CUSTOM_MEASURE_STOP_VALUE = 6;
            public static final int FOREGROUND_SERVICE_START_VALUE = 3;
            public static final int FOREGROUND_SERVICE_STOP_VALUE = 4;
            public static final int FOREGROUND_TO_BACKGROUND_VALUE = 1;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<SampleInfo> internalValueMap = new Internal.EnumLiteMap<SampleInfo>() {
                public SampleInfo findValueByNumber(int number) {
                    return SampleInfo.forNumber(number);
                }
            };
            private final int value;

            private SampleInfo(int value2) {
                this.value = value2;
            }

            public static SampleInfo forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return UNKNOWN;
                    case 1:
                        return FOREGROUND_TO_BACKGROUND;
                    case 2:
                        return BACKGROUND_TO_FOREGROUND;
                    case 3:
                        return FOREGROUND_SERVICE_START;
                    case 4:
                        return FOREGROUND_SERVICE_STOP;
                    case 5:
                        return CUSTOM_MEASURE_START;
                    case 6:
                        return CUSTOM_MEASURE_STOP;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<SampleInfo> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return SampleInfoVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class SampleInfoVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new SampleInfoVerifier();

                private SampleInfoVerifier() {
                }

                public boolean isInRange(int number) {
                    return SampleInfo.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<BatteryStatsDiff, Builder> implements BatteryStatsDiffOrBuilder {
            private Builder() {
                super(BatteryStatsDiff.DEFAULT_INSTANCE);
            }

            public boolean hasStartInfo() {
                return ((BatteryStatsDiff) this.instance).hasStartInfo();
            }

            public SampleInfo getStartInfo() {
                return ((BatteryStatsDiff) this.instance).getStartInfo();
            }

            public Builder setStartInfo(SampleInfo value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setStartInfo(value);
                return this;
            }

            public Builder clearStartInfo() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearStartInfo();
                return this;
            }

            public boolean hasStartHashedCustomEventName() {
                return ((BatteryStatsDiff) this.instance).hasStartHashedCustomEventName();
            }

            public long getStartHashedCustomEventName() {
                return ((BatteryStatsDiff) this.instance).getStartHashedCustomEventName();
            }

            public Builder setStartHashedCustomEventName(long value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setStartHashedCustomEventName(value);
                return this;
            }

            public Builder clearStartHashedCustomEventName() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearStartHashedCustomEventName();
                return this;
            }

            public boolean hasStartCustomEventName() {
                return ((BatteryStatsDiff) this.instance).hasStartCustomEventName();
            }

            public String getStartCustomEventName() {
                return ((BatteryStatsDiff) this.instance).getStartCustomEventName();
            }

            public Builder setStartCustomEventName(String value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setStartCustomEventName(value);
                return this;
            }

            public ByteString getStartCustomEventNameBytes() {
                return ((BatteryStatsDiff) this.instance).getStartCustomEventNameBytes();
            }

            public Builder setStartCustomEventNameBytes(ByteString value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setStartCustomEventNameBytes(value);
                return this;
            }

            public Builder clearStartCustomEventName() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearStartCustomEventName();
                return this;
            }

            public boolean hasStartConstantEventName() {
                return ((BatteryStatsDiff) this.instance).hasStartConstantEventName();
            }

            public String getStartConstantEventName() {
                return ((BatteryStatsDiff) this.instance).getStartConstantEventName();
            }

            public Builder setStartConstantEventName(String value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setStartConstantEventName(value);
                return this;
            }

            public ByteString getStartConstantEventNameBytes() {
                return ((BatteryStatsDiff) this.instance).getStartConstantEventNameBytes();
            }

            public Builder setStartConstantEventNameBytes(ByteString value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setStartConstantEventNameBytes(value);
                return this;
            }

            public Builder clearStartConstantEventName() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearStartConstantEventName();
                return this;
            }

            public boolean hasStartMetricExtension() {
                return ((BatteryStatsDiff) this.instance).hasStartMetricExtension();
            }

            public ExtensionMetric.MetricExtension getStartMetricExtension() {
                return ((BatteryStatsDiff) this.instance).getStartMetricExtension();
            }

            public Builder setStartMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setStartMetricExtension(value);
                return this;
            }

            public Builder setStartMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setStartMetricExtension(builderForValue);
                return this;
            }

            public Builder mergeStartMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).mergeStartMetricExtension(value);
                return this;
            }

            public Builder clearStartMetricExtension() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearStartMetricExtension();
                return this;
            }

            public boolean hasEndInfo() {
                return ((BatteryStatsDiff) this.instance).hasEndInfo();
            }

            public SampleInfo getEndInfo() {
                return ((BatteryStatsDiff) this.instance).getEndInfo();
            }

            public Builder setEndInfo(SampleInfo value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setEndInfo(value);
                return this;
            }

            public Builder clearEndInfo() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearEndInfo();
                return this;
            }

            public boolean hasDurationMs() {
                return ((BatteryStatsDiff) this.instance).hasDurationMs();
            }

            public long getDurationMs() {
                return ((BatteryStatsDiff) this.instance).getDurationMs();
            }

            public Builder setDurationMs(long value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setDurationMs(value);
                return this;
            }

            public Builder clearDurationMs() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearDurationMs();
                return this;
            }

            @Deprecated
            public List<WakelockStats> getWakelockStatsList() {
                return Collections.unmodifiableList(((BatteryStatsDiff) this.instance).getWakelockStatsList());
            }

            @Deprecated
            public int getWakelockStatsCount() {
                return ((BatteryStatsDiff) this.instance).getWakelockStatsCount();
            }

            @Deprecated
            public WakelockStats getWakelockStats(int index) {
                return ((BatteryStatsDiff) this.instance).getWakelockStats(index);
            }

            @Deprecated
            public Builder setWakelockStats(int index, WakelockStats value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setWakelockStats(index, value);
                return this;
            }

            @Deprecated
            public Builder setWakelockStats(int index, WakelockStats.Builder builderForValue) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setWakelockStats(index, builderForValue);
                return this;
            }

            @Deprecated
            public Builder addWakelockStats(WakelockStats value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addWakelockStats(value);
                return this;
            }

            @Deprecated
            public Builder addWakelockStats(int index, WakelockStats value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addWakelockStats(index, value);
                return this;
            }

            @Deprecated
            public Builder addWakelockStats(WakelockStats.Builder builderForValue) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addWakelockStats(builderForValue);
                return this;
            }

            @Deprecated
            public Builder addWakelockStats(int index, WakelockStats.Builder builderForValue) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addWakelockStats(index, builderForValue);
                return this;
            }

            @Deprecated
            public Builder addAllWakelockStats(Iterable<? extends WakelockStats> values) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addAllWakelockStats(values);
                return this;
            }

            @Deprecated
            public Builder clearWakelockStats() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearWakelockStats();
                return this;
            }

            @Deprecated
            public Builder removeWakelockStats(int index) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).removeWakelockStats(index);
                return this;
            }

            @Deprecated
            public List<SyncStats> getSyncStatsList() {
                return Collections.unmodifiableList(((BatteryStatsDiff) this.instance).getSyncStatsList());
            }

            @Deprecated
            public int getSyncStatsCount() {
                return ((BatteryStatsDiff) this.instance).getSyncStatsCount();
            }

            @Deprecated
            public SyncStats getSyncStats(int index) {
                return ((BatteryStatsDiff) this.instance).getSyncStats(index);
            }

            @Deprecated
            public Builder setSyncStats(int index, SyncStats value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setSyncStats(index, value);
                return this;
            }

            @Deprecated
            public Builder setSyncStats(int index, SyncStats.Builder builderForValue) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setSyncStats(index, builderForValue);
                return this;
            }

            @Deprecated
            public Builder addSyncStats(SyncStats value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addSyncStats(value);
                return this;
            }

            @Deprecated
            public Builder addSyncStats(int index, SyncStats value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addSyncStats(index, value);
                return this;
            }

            @Deprecated
            public Builder addSyncStats(SyncStats.Builder builderForValue) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addSyncStats(builderForValue);
                return this;
            }

            @Deprecated
            public Builder addSyncStats(int index, SyncStats.Builder builderForValue) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addSyncStats(index, builderForValue);
                return this;
            }

            @Deprecated
            public Builder addAllSyncStats(Iterable<? extends SyncStats> values) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).addAllSyncStats(values);
                return this;
            }

            @Deprecated
            public Builder clearSyncStats() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearSyncStats();
                return this;
            }

            @Deprecated
            public Builder removeSyncStats(int index) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).removeSyncStats(index);
                return this;
            }

            public boolean hasUidHealthProtoDiff() {
                return ((BatteryStatsDiff) this.instance).hasUidHealthProtoDiff();
            }

            public UidHealthProto getUidHealthProtoDiff() {
                return ((BatteryStatsDiff) this.instance).getUidHealthProtoDiff();
            }

            public Builder setUidHealthProtoDiff(UidHealthProto value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setUidHealthProtoDiff(value);
                return this;
            }

            public Builder setUidHealthProtoDiff(UidHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setUidHealthProtoDiff(builderForValue);
                return this;
            }

            public Builder mergeUidHealthProtoDiff(UidHealthProto value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).mergeUidHealthProtoDiff(value);
                return this;
            }

            public Builder clearUidHealthProtoDiff() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearUidHealthProtoDiff();
                return this;
            }

            public boolean hasElapedRealtimeMs() {
                return ((BatteryStatsDiff) this.instance).hasElapedRealtimeMs();
            }

            public long getElapedRealtimeMs() {
                return ((BatteryStatsDiff) this.instance).getElapedRealtimeMs();
            }

            public Builder setElapedRealtimeMs(long value) {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).setElapedRealtimeMs(value);
                return this;
            }

            public Builder clearElapedRealtimeMs() {
                copyOnWrite();
                ((BatteryStatsDiff) this.instance).clearElapedRealtimeMs();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class UidHealthProto extends GeneratedMessageLite<UidHealthProto, Builder> implements UidHealthProtoOrBuilder {
        public static final int AUDIO_FIELD_NUMBER = 32;
        public static final int BLUETOOTH_IDLE_MS_FIELD_NUMBER = 20;
        public static final int BLUETOOTH_POWER_MAMS_FIELD_NUMBER = 23;
        public static final int BLUETOOTH_RX_BYTES_FIELD_NUMBER = 52;
        public static final int BLUETOOTH_RX_MS_FIELD_NUMBER = 21;
        public static final int BLUETOOTH_RX_PACKETS_FIELD_NUMBER = 58;
        public static final int BLUETOOTH_SCAN_FIELD_NUMBER = 37;
        public static final int BLUETOOTH_TX_BYTES_FIELD_NUMBER = 53;
        public static final int BLUETOOTH_TX_MS_FIELD_NUMBER = 22;
        public static final int BLUETOOTH_TX_PACKETS_FIELD_NUMBER = 59;
        public static final int BUTTON_USER_ACTIVITY_COUNT_FIELD_NUMBER = 46;
        public static final int CAMERA_FIELD_NUMBER = 35;
        public static final int CPU_POWER_MAMS_FIELD_NUMBER = 64;
        /* access modifiers changed from: private */
        public static final UidHealthProto DEFAULT_INSTANCE = new UidHealthProto();
        public static final int FLASHLIGHT_FIELD_NUMBER = 34;
        public static final int FOREGROUND_ACTIVITY_FIELD_NUMBER = 36;
        public static final int GPS_SENSOR_FIELD_NUMBER = 11;
        public static final int JOBS_FIELD_NUMBER = 10;
        public static final int MOBILE_IDLE_MS_FIELD_NUMBER = 24;
        public static final int MOBILE_POWER_MAMS_FIELD_NUMBER = 27;
        public static final int MOBILE_RADIO_ACTIVE_FIELD_NUMBER = 61;
        public static final int MOBILE_RX_BYTES_FIELD_NUMBER = 48;
        public static final int MOBILE_RX_MS_FIELD_NUMBER = 25;
        public static final int MOBILE_RX_PACKETS_FIELD_NUMBER = 54;
        public static final int MOBILE_TX_BYTES_FIELD_NUMBER = 49;
        public static final int MOBILE_TX_MS_FIELD_NUMBER = 26;
        public static final int MOBILE_TX_PACKETS_FIELD_NUMBER = 55;
        public static final int OTHER_USER_ACTIVITY_COUNT_FIELD_NUMBER = 45;
        public static final int PROCESS_STATE_BACKGROUND_MS_FIELD_NUMBER = 42;
        public static final int PROCESS_STATE_CACHED_MS_FIELD_NUMBER = 43;
        public static final int PROCESS_STATE_FOREGROUND_MS_FIELD_NUMBER = 41;
        public static final int PROCESS_STATE_FOREGROUND_SERVICE_MS_FIELD_NUMBER = 39;
        public static final int PROCESS_STATE_TOP_MS_FIELD_NUMBER = 38;
        public static final int PROCESS_STATE_TOP_SLEEPING_MS_FIELD_NUMBER = 40;
        public static final int REALTIME_BATTERY_MS_FIELD_NUMBER = 1;
        public static final int REALTIME_SCREEN_OFF_BATTERY_MS_FIELD_NUMBER = 3;
        public static final int SENSORS_FIELD_NUMBER = 12;
        public static final int STATS_PACKAGES_FIELD_NUMBER = 15;
        public static final int STATS_PIDS_FIELD_NUMBER = 13;
        public static final int STATS_PROCESSES_FIELD_NUMBER = 14;
        public static final int SYNCS_FIELD_NUMBER = 9;
        public static final int SYSTEM_CPU_TIME_MS_FIELD_NUMBER = 63;
        public static final int TOUCH_USER_ACTIVITY_COUNT_FIELD_NUMBER = 47;
        public static final int UPTIME_BATTERY_MS_FIELD_NUMBER = 2;
        public static final int UPTIME_SCREEN_OFF_BATTERY_MS_FIELD_NUMBER = 4;
        public static final int USER_CPU_TIME_MS_FIELD_NUMBER = 62;
        public static final int VIBRATOR_FIELD_NUMBER = 44;
        public static final int VIDEO_FIELD_NUMBER = 33;
        public static final int WAKELOCKS_DRAW_FIELD_NUMBER = 8;
        public static final int WAKELOCKS_FULL_FIELD_NUMBER = 5;
        public static final int WAKELOCKS_PARTIAL_FIELD_NUMBER = 6;
        public static final int WAKELOCKS_WINDOW_FIELD_NUMBER = 7;
        public static final int WIFI_FULL_LOCK_MS_FIELD_NUMBER = 29;
        public static final int WIFI_IDLE_MS_FIELD_NUMBER = 16;
        public static final int WIFI_MULTICAST_MS_FIELD_NUMBER = 31;
        public static final int WIFI_POWER_MAMS_FIELD_NUMBER = 19;
        public static final int WIFI_RUNNING_MS_FIELD_NUMBER = 28;
        public static final int WIFI_RX_BYTES_FIELD_NUMBER = 50;
        public static final int WIFI_RX_MS_FIELD_NUMBER = 17;
        public static final int WIFI_RX_PACKETS_FIELD_NUMBER = 56;
        public static final int WIFI_SCAN_FIELD_NUMBER = 30;
        public static final int WIFI_TX_BYTES_FIELD_NUMBER = 51;
        public static final int WIFI_TX_MS_FIELD_NUMBER = 18;
        public static final int WIFI_TX_PACKETS_FIELD_NUMBER = 57;
        private static volatile Parser<UidHealthProto> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(UidHealthProto.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 32, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2097152, presenceBitsId = 0)
        private Timer audio_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoPresenceBits(mo28548id = 1)
        private int bitField1_;
        @ProtoField(fieldNumber = 20, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private long bluetoothIdleMs_;
        @ProtoField(fieldNumber = 23, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 0)
        private long bluetoothPowerMams_;
        @ProtoField(fieldNumber = 52, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 1)
        private long bluetoothRxBytes_;
        @ProtoField(fieldNumber = 21, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private long bluetoothRxMs_;
        @ProtoField(fieldNumber = 58, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 32768, presenceBitsId = 1)
        private long bluetoothRxPackets_;
        @ProtoField(fieldNumber = 37, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 67108864, presenceBitsId = 0)
        private Timer bluetoothScan_;
        @ProtoField(fieldNumber = 53, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 1)
        private long bluetoothTxBytes_;
        @ProtoField(fieldNumber = 22, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private long bluetoothTxMs_;
        @ProtoField(fieldNumber = 59, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 65536, presenceBitsId = 1)
        private long bluetoothTxPackets_;
        @ProtoField(fieldNumber = 46, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 1)
        private long buttonUserActivityCount_;
        @ProtoField(fieldNumber = 35, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16777216, presenceBitsId = 0)
        private Timer camera_;
        @ProtoField(fieldNumber = 64, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1048576, presenceBitsId = 1)
        private long cpuPowerMams_;
        @ProtoField(fieldNumber = 34, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8388608, presenceBitsId = 0)
        private Timer flashlight_;
        @ProtoField(fieldNumber = 36, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 33554432, presenceBitsId = 0)
        private Timer foregroundActivity_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private Timer gpsSensor_;
        @ProtoField(fieldNumber = 10, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Timer> jobs_ = emptyProtobufList();
        @ProtoField(fieldNumber = 24, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8192, presenceBitsId = 0)
        private long mobileIdleMs_;
        @ProtoField(fieldNumber = 27, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 65536, presenceBitsId = 0)
        private long mobilePowerMams_;
        @ProtoField(fieldNumber = 61, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 131072, presenceBitsId = 1)
        private Timer mobileRadioActive_;
        @ProtoField(fieldNumber = 48, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 1)
        private long mobileRxBytes_;
        @ProtoField(fieldNumber = 25, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 16384, presenceBitsId = 0)
        private long mobileRxMs_;
        @ProtoField(fieldNumber = 54, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 1)
        private long mobileRxPackets_;
        @ProtoField(fieldNumber = 49, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 1)
        private long mobileTxBytes_;
        @ProtoField(fieldNumber = 26, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 32768, presenceBitsId = 0)
        private long mobileTxMs_;
        @ProtoField(fieldNumber = 55, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 1)
        private long mobileTxPackets_;
        @ProtoField(fieldNumber = 45, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 1)
        private long otherUserActivityCount_;
        @ProtoField(fieldNumber = 42, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = Integer.MIN_VALUE, presenceBitsId = 0)
        private Timer processStateBackgroundMs_;
        @ProtoField(fieldNumber = 43, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 1)
        private Timer processStateCachedMs_;
        @ProtoField(fieldNumber = 41, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1073741824, presenceBitsId = 0)
        private Timer processStateForegroundMs_;
        @ProtoField(fieldNumber = 39, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = C0841C.ENCODING_PCM_MU_LAW, presenceBitsId = 0)
        private Timer processStateForegroundServiceMs_;
        @ProtoField(fieldNumber = 38, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 134217728, presenceBitsId = 0)
        private Timer processStateTopMs_;
        @ProtoField(fieldNumber = 40, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 536870912, presenceBitsId = 0)
        private Timer processStateTopSleepingMs_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long realtimeBatteryMs_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long realtimeScreenOffBatteryMs_;
        @ProtoField(fieldNumber = 12, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Timer> sensors_ = emptyProtobufList();
        @ProtoField(fieldNumber = 15, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<PackageHealthProto> statsPackages_ = emptyProtobufList();
        @ProtoField(fieldNumber = 13, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<PidHealthProto> statsPids_ = emptyProtobufList();
        @ProtoField(fieldNumber = 14, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ProcessHealthProto> statsProcesses_ = emptyProtobufList();
        @ProtoField(fieldNumber = 9, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Timer> syncs_ = emptyProtobufList();
        @ProtoField(fieldNumber = 63, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 524288, presenceBitsId = 1)
        private long systemCpuTimeMs_;
        @ProtoField(fieldNumber = 47, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 1)
        private long touchUserActivityCount_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long uptimeBatteryMs_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private long uptimeScreenOffBatteryMs_;
        @ProtoField(fieldNumber = 62, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 262144, presenceBitsId = 1)
        private long userCpuTimeMs_;
        @ProtoField(fieldNumber = 44, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 1)
        private Timer vibrator_;
        @ProtoField(fieldNumber = 33, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4194304, presenceBitsId = 0)
        private Timer video_;
        @ProtoField(fieldNumber = 8, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Timer> wakelocksDraw_ = emptyProtobufList();
        @ProtoField(fieldNumber = 5, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Timer> wakelocksFull_ = emptyProtobufList();
        @ProtoField(fieldNumber = 6, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Timer> wakelocksPartial_ = emptyProtobufList();
        @ProtoField(fieldNumber = 7, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Timer> wakelocksWindow_ = emptyProtobufList();
        @ProtoField(fieldNumber = 29, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 262144, presenceBitsId = 0)
        private long wifiFullLockMs_;
        @ProtoField(fieldNumber = 16, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private long wifiIdleMs_;
        @ProtoField(fieldNumber = 31, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1048576, presenceBitsId = 0)
        private long wifiMulticastMs_;
        @ProtoField(fieldNumber = 19, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private long wifiPowerMams_;
        @ProtoField(fieldNumber = 28, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 131072, presenceBitsId = 0)
        private long wifiRunningMs_;
        @ProtoField(fieldNumber = 50, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 1)
        private long wifiRxBytes_;
        @ProtoField(fieldNumber = 17, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private long wifiRxMs_;
        @ProtoField(fieldNumber = 56, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8192, presenceBitsId = 1)
        private long wifiRxPackets_;
        @ProtoField(fieldNumber = 30, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 524288, presenceBitsId = 0)
        private Timer wifiScan_;
        @ProtoField(fieldNumber = 51, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 1)
        private long wifiTxBytes_;
        @ProtoField(fieldNumber = 18, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private long wifiTxMs_;
        @ProtoField(fieldNumber = 57, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 16384, presenceBitsId = 1)
        private long wifiTxPackets_;

        private UidHealthProto() {
        }

        public static UidHealthProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static UidHealthProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static UidHealthProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static UidHealthProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static UidHealthProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static UidHealthProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static UidHealthProto parseFrom(InputStream input) throws IOException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static UidHealthProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static UidHealthProto parseDelimitedFrom(InputStream input) throws IOException {
            return (UidHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static UidHealthProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UidHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static UidHealthProto parseFrom(CodedInputStream input) throws IOException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static UidHealthProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(UidHealthProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static UidHealthProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<UidHealthProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasRealtimeBatteryMs() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getRealtimeBatteryMs() {
            return this.realtimeBatteryMs_;
        }

        /* access modifiers changed from: private */
        public void setRealtimeBatteryMs(long value) {
            this.bitField0_ |= 1;
            this.realtimeBatteryMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRealtimeBatteryMs() {
            this.bitField0_ &= -2;
            this.realtimeBatteryMs_ = 0;
        }

        public boolean hasUptimeBatteryMs() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getUptimeBatteryMs() {
            return this.uptimeBatteryMs_;
        }

        /* access modifiers changed from: private */
        public void setUptimeBatteryMs(long value) {
            this.bitField0_ |= 2;
            this.uptimeBatteryMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearUptimeBatteryMs() {
            this.bitField0_ &= -3;
            this.uptimeBatteryMs_ = 0;
        }

        public boolean hasRealtimeScreenOffBatteryMs() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getRealtimeScreenOffBatteryMs() {
            return this.realtimeScreenOffBatteryMs_;
        }

        /* access modifiers changed from: private */
        public void setRealtimeScreenOffBatteryMs(long value) {
            this.bitField0_ |= 4;
            this.realtimeScreenOffBatteryMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRealtimeScreenOffBatteryMs() {
            this.bitField0_ &= -5;
            this.realtimeScreenOffBatteryMs_ = 0;
        }

        public boolean hasUptimeScreenOffBatteryMs() {
            return (this.bitField0_ & 8) != 0;
        }

        public long getUptimeScreenOffBatteryMs() {
            return this.uptimeScreenOffBatteryMs_;
        }

        /* access modifiers changed from: private */
        public void setUptimeScreenOffBatteryMs(long value) {
            this.bitField0_ |= 8;
            this.uptimeScreenOffBatteryMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearUptimeScreenOffBatteryMs() {
            this.bitField0_ &= -9;
            this.uptimeScreenOffBatteryMs_ = 0;
        }

        public List<Timer> getWakelocksFullList() {
            return this.wakelocksFull_;
        }

        public List<? extends TimerOrBuilder> getWakelocksFullOrBuilderList() {
            return this.wakelocksFull_;
        }

        public int getWakelocksFullCount() {
            return this.wakelocksFull_.size();
        }

        public Timer getWakelocksFull(int index) {
            return this.wakelocksFull_.get(index);
        }

        public TimerOrBuilder getWakelocksFullOrBuilder(int index) {
            return this.wakelocksFull_.get(index);
        }

        private void ensureWakelocksFullIsMutable() {
            if (!this.wakelocksFull_.isModifiable()) {
                this.wakelocksFull_ = GeneratedMessageLite.mutableCopy(this.wakelocksFull_);
            }
        }

        /* access modifiers changed from: private */
        public void setWakelocksFull(int index, Timer value) {
            if (value != null) {
                ensureWakelocksFullIsMutable();
                this.wakelocksFull_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setWakelocksFull(int index, Timer.Builder builderForValue) {
            ensureWakelocksFullIsMutable();
            this.wakelocksFull_.set(index, (Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelocksFull(Timer value) {
            if (value != null) {
                ensureWakelocksFullIsMutable();
                this.wakelocksFull_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelocksFull(int index, Timer value) {
            if (value != null) {
                ensureWakelocksFullIsMutable();
                this.wakelocksFull_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelocksFull(Timer.Builder builderForValue) {
            ensureWakelocksFullIsMutable();
            this.wakelocksFull_.add((Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelocksFull(int index, Timer.Builder builderForValue) {
            ensureWakelocksFullIsMutable();
            this.wakelocksFull_.add(index, (Timer) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$Timer>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$Timer>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllWakelocksFull(Iterable<? extends Timer> values) {
            ensureWakelocksFullIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.wakelocksFull_);
        }

        /* access modifiers changed from: private */
        public void clearWakelocksFull() {
            this.wakelocksFull_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeWakelocksFull(int index) {
            ensureWakelocksFullIsMutable();
            this.wakelocksFull_.remove(index);
        }

        public List<Timer> getWakelocksPartialList() {
            return this.wakelocksPartial_;
        }

        public List<? extends TimerOrBuilder> getWakelocksPartialOrBuilderList() {
            return this.wakelocksPartial_;
        }

        public int getWakelocksPartialCount() {
            return this.wakelocksPartial_.size();
        }

        public Timer getWakelocksPartial(int index) {
            return this.wakelocksPartial_.get(index);
        }

        public TimerOrBuilder getWakelocksPartialOrBuilder(int index) {
            return this.wakelocksPartial_.get(index);
        }

        private void ensureWakelocksPartialIsMutable() {
            if (!this.wakelocksPartial_.isModifiable()) {
                this.wakelocksPartial_ = GeneratedMessageLite.mutableCopy(this.wakelocksPartial_);
            }
        }

        /* access modifiers changed from: private */
        public void setWakelocksPartial(int index, Timer value) {
            if (value != null) {
                ensureWakelocksPartialIsMutable();
                this.wakelocksPartial_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setWakelocksPartial(int index, Timer.Builder builderForValue) {
            ensureWakelocksPartialIsMutable();
            this.wakelocksPartial_.set(index, (Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelocksPartial(Timer value) {
            if (value != null) {
                ensureWakelocksPartialIsMutable();
                this.wakelocksPartial_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelocksPartial(int index, Timer value) {
            if (value != null) {
                ensureWakelocksPartialIsMutable();
                this.wakelocksPartial_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelocksPartial(Timer.Builder builderForValue) {
            ensureWakelocksPartialIsMutable();
            this.wakelocksPartial_.add((Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelocksPartial(int index, Timer.Builder builderForValue) {
            ensureWakelocksPartialIsMutable();
            this.wakelocksPartial_.add(index, (Timer) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$Timer>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$Timer>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllWakelocksPartial(Iterable<? extends Timer> values) {
            ensureWakelocksPartialIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.wakelocksPartial_);
        }

        /* access modifiers changed from: private */
        public void clearWakelocksPartial() {
            this.wakelocksPartial_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeWakelocksPartial(int index) {
            ensureWakelocksPartialIsMutable();
            this.wakelocksPartial_.remove(index);
        }

        public List<Timer> getWakelocksWindowList() {
            return this.wakelocksWindow_;
        }

        public List<? extends TimerOrBuilder> getWakelocksWindowOrBuilderList() {
            return this.wakelocksWindow_;
        }

        public int getWakelocksWindowCount() {
            return this.wakelocksWindow_.size();
        }

        public Timer getWakelocksWindow(int index) {
            return this.wakelocksWindow_.get(index);
        }

        public TimerOrBuilder getWakelocksWindowOrBuilder(int index) {
            return this.wakelocksWindow_.get(index);
        }

        private void ensureWakelocksWindowIsMutable() {
            if (!this.wakelocksWindow_.isModifiable()) {
                this.wakelocksWindow_ = GeneratedMessageLite.mutableCopy(this.wakelocksWindow_);
            }
        }

        /* access modifiers changed from: private */
        public void setWakelocksWindow(int index, Timer value) {
            if (value != null) {
                ensureWakelocksWindowIsMutable();
                this.wakelocksWindow_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setWakelocksWindow(int index, Timer.Builder builderForValue) {
            ensureWakelocksWindowIsMutable();
            this.wakelocksWindow_.set(index, (Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelocksWindow(Timer value) {
            if (value != null) {
                ensureWakelocksWindowIsMutable();
                this.wakelocksWindow_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelocksWindow(int index, Timer value) {
            if (value != null) {
                ensureWakelocksWindowIsMutable();
                this.wakelocksWindow_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelocksWindow(Timer.Builder builderForValue) {
            ensureWakelocksWindowIsMutable();
            this.wakelocksWindow_.add((Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelocksWindow(int index, Timer.Builder builderForValue) {
            ensureWakelocksWindowIsMutable();
            this.wakelocksWindow_.add(index, (Timer) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$Timer>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$Timer>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllWakelocksWindow(Iterable<? extends Timer> values) {
            ensureWakelocksWindowIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.wakelocksWindow_);
        }

        /* access modifiers changed from: private */
        public void clearWakelocksWindow() {
            this.wakelocksWindow_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeWakelocksWindow(int index) {
            ensureWakelocksWindowIsMutable();
            this.wakelocksWindow_.remove(index);
        }

        public List<Timer> getWakelocksDrawList() {
            return this.wakelocksDraw_;
        }

        public List<? extends TimerOrBuilder> getWakelocksDrawOrBuilderList() {
            return this.wakelocksDraw_;
        }

        public int getWakelocksDrawCount() {
            return this.wakelocksDraw_.size();
        }

        public Timer getWakelocksDraw(int index) {
            return this.wakelocksDraw_.get(index);
        }

        public TimerOrBuilder getWakelocksDrawOrBuilder(int index) {
            return this.wakelocksDraw_.get(index);
        }

        private void ensureWakelocksDrawIsMutable() {
            if (!this.wakelocksDraw_.isModifiable()) {
                this.wakelocksDraw_ = GeneratedMessageLite.mutableCopy(this.wakelocksDraw_);
            }
        }

        /* access modifiers changed from: private */
        public void setWakelocksDraw(int index, Timer value) {
            if (value != null) {
                ensureWakelocksDrawIsMutable();
                this.wakelocksDraw_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setWakelocksDraw(int index, Timer.Builder builderForValue) {
            ensureWakelocksDrawIsMutable();
            this.wakelocksDraw_.set(index, (Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelocksDraw(Timer value) {
            if (value != null) {
                ensureWakelocksDrawIsMutable();
                this.wakelocksDraw_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelocksDraw(int index, Timer value) {
            if (value != null) {
                ensureWakelocksDrawIsMutable();
                this.wakelocksDraw_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakelocksDraw(Timer.Builder builderForValue) {
            ensureWakelocksDrawIsMutable();
            this.wakelocksDraw_.add((Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakelocksDraw(int index, Timer.Builder builderForValue) {
            ensureWakelocksDrawIsMutable();
            this.wakelocksDraw_.add(index, (Timer) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$Timer>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$Timer>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllWakelocksDraw(Iterable<? extends Timer> values) {
            ensureWakelocksDrawIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.wakelocksDraw_);
        }

        /* access modifiers changed from: private */
        public void clearWakelocksDraw() {
            this.wakelocksDraw_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeWakelocksDraw(int index) {
            ensureWakelocksDrawIsMutable();
            this.wakelocksDraw_.remove(index);
        }

        public List<Timer> getSyncsList() {
            return this.syncs_;
        }

        public List<? extends TimerOrBuilder> getSyncsOrBuilderList() {
            return this.syncs_;
        }

        public int getSyncsCount() {
            return this.syncs_.size();
        }

        public Timer getSyncs(int index) {
            return this.syncs_.get(index);
        }

        public TimerOrBuilder getSyncsOrBuilder(int index) {
            return this.syncs_.get(index);
        }

        private void ensureSyncsIsMutable() {
            if (!this.syncs_.isModifiable()) {
                this.syncs_ = GeneratedMessageLite.mutableCopy(this.syncs_);
            }
        }

        /* access modifiers changed from: private */
        public void setSyncs(int index, Timer value) {
            if (value != null) {
                ensureSyncsIsMutable();
                this.syncs_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSyncs(int index, Timer.Builder builderForValue) {
            ensureSyncsIsMutable();
            this.syncs_.set(index, (Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSyncs(Timer value) {
            if (value != null) {
                ensureSyncsIsMutable();
                this.syncs_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSyncs(int index, Timer value) {
            if (value != null) {
                ensureSyncsIsMutable();
                this.syncs_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSyncs(Timer.Builder builderForValue) {
            ensureSyncsIsMutable();
            this.syncs_.add((Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSyncs(int index, Timer.Builder builderForValue) {
            ensureSyncsIsMutable();
            this.syncs_.add(index, (Timer) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$Timer>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$Timer>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSyncs(Iterable<? extends Timer> values) {
            ensureSyncsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.syncs_);
        }

        /* access modifiers changed from: private */
        public void clearSyncs() {
            this.syncs_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSyncs(int index) {
            ensureSyncsIsMutable();
            this.syncs_.remove(index);
        }

        public List<Timer> getJobsList() {
            return this.jobs_;
        }

        public List<? extends TimerOrBuilder> getJobsOrBuilderList() {
            return this.jobs_;
        }

        public int getJobsCount() {
            return this.jobs_.size();
        }

        public Timer getJobs(int index) {
            return this.jobs_.get(index);
        }

        public TimerOrBuilder getJobsOrBuilder(int index) {
            return this.jobs_.get(index);
        }

        private void ensureJobsIsMutable() {
            if (!this.jobs_.isModifiable()) {
                this.jobs_ = GeneratedMessageLite.mutableCopy(this.jobs_);
            }
        }

        /* access modifiers changed from: private */
        public void setJobs(int index, Timer value) {
            if (value != null) {
                ensureJobsIsMutable();
                this.jobs_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setJobs(int index, Timer.Builder builderForValue) {
            ensureJobsIsMutable();
            this.jobs_.set(index, (Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addJobs(Timer value) {
            if (value != null) {
                ensureJobsIsMutable();
                this.jobs_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addJobs(int index, Timer value) {
            if (value != null) {
                ensureJobsIsMutable();
                this.jobs_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addJobs(Timer.Builder builderForValue) {
            ensureJobsIsMutable();
            this.jobs_.add((Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addJobs(int index, Timer.Builder builderForValue) {
            ensureJobsIsMutable();
            this.jobs_.add(index, (Timer) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$Timer>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$Timer>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllJobs(Iterable<? extends Timer> values) {
            ensureJobsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.jobs_);
        }

        /* access modifiers changed from: private */
        public void clearJobs() {
            this.jobs_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeJobs(int index) {
            ensureJobsIsMutable();
            this.jobs_.remove(index);
        }

        public boolean hasGpsSensor() {
            return (this.bitField0_ & 16) != 0;
        }

        public Timer getGpsSensor() {
            Timer timer = this.gpsSensor_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setGpsSensor(Timer value) {
            if (value != null) {
                this.gpsSensor_ = value;
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setGpsSensor(Timer.Builder builderForValue) {
            this.gpsSensor_ = (Timer) builderForValue.build();
            this.bitField0_ |= 16;
        }

        /* access modifiers changed from: private */
        public void mergeGpsSensor(Timer value) {
            if (value != null) {
                Timer timer = this.gpsSensor_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.gpsSensor_ = value;
                } else {
                    this.gpsSensor_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.gpsSensor_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearGpsSensor() {
            this.gpsSensor_ = null;
            this.bitField0_ &= -17;
        }

        public List<Timer> getSensorsList() {
            return this.sensors_;
        }

        public List<? extends TimerOrBuilder> getSensorsOrBuilderList() {
            return this.sensors_;
        }

        public int getSensorsCount() {
            return this.sensors_.size();
        }

        public Timer getSensors(int index) {
            return this.sensors_.get(index);
        }

        public TimerOrBuilder getSensorsOrBuilder(int index) {
            return this.sensors_.get(index);
        }

        private void ensureSensorsIsMutable() {
            if (!this.sensors_.isModifiable()) {
                this.sensors_ = GeneratedMessageLite.mutableCopy(this.sensors_);
            }
        }

        /* access modifiers changed from: private */
        public void setSensors(int index, Timer value) {
            if (value != null) {
                ensureSensorsIsMutable();
                this.sensors_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSensors(int index, Timer.Builder builderForValue) {
            ensureSensorsIsMutable();
            this.sensors_.set(index, (Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSensors(Timer value) {
            if (value != null) {
                ensureSensorsIsMutable();
                this.sensors_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSensors(int index, Timer value) {
            if (value != null) {
                ensureSensorsIsMutable();
                this.sensors_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSensors(Timer.Builder builderForValue) {
            ensureSensorsIsMutable();
            this.sensors_.add((Timer) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSensors(int index, Timer.Builder builderForValue) {
            ensureSensorsIsMutable();
            this.sensors_.add(index, (Timer) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$Timer>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$Timer>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSensors(Iterable<? extends Timer> values) {
            ensureSensorsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.sensors_);
        }

        /* access modifiers changed from: private */
        public void clearSensors() {
            this.sensors_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSensors(int index) {
            ensureSensorsIsMutable();
            this.sensors_.remove(index);
        }

        public List<PidHealthProto> getStatsPidsList() {
            return this.statsPids_;
        }

        public List<? extends PidHealthProtoOrBuilder> getStatsPidsOrBuilderList() {
            return this.statsPids_;
        }

        public int getStatsPidsCount() {
            return this.statsPids_.size();
        }

        public PidHealthProto getStatsPids(int index) {
            return this.statsPids_.get(index);
        }

        public PidHealthProtoOrBuilder getStatsPidsOrBuilder(int index) {
            return this.statsPids_.get(index);
        }

        private void ensureStatsPidsIsMutable() {
            if (!this.statsPids_.isModifiable()) {
                this.statsPids_ = GeneratedMessageLite.mutableCopy(this.statsPids_);
            }
        }

        /* access modifiers changed from: private */
        public void setStatsPids(int index, PidHealthProto value) {
            if (value != null) {
                ensureStatsPidsIsMutable();
                this.statsPids_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setStatsPids(int index, PidHealthProto.Builder builderForValue) {
            ensureStatsPidsIsMutable();
            this.statsPids_.set(index, (PidHealthProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStatsPids(PidHealthProto value) {
            if (value != null) {
                ensureStatsPidsIsMutable();
                this.statsPids_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStatsPids(int index, PidHealthProto value) {
            if (value != null) {
                ensureStatsPidsIsMutable();
                this.statsPids_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStatsPids(PidHealthProto.Builder builderForValue) {
            ensureStatsPidsIsMutable();
            this.statsPids_.add((PidHealthProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStatsPids(int index, PidHealthProto.Builder builderForValue) {
            ensureStatsPidsIsMutable();
            this.statsPids_.add(index, (PidHealthProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$PidHealthProto>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$PidHealthProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllStatsPids(Iterable<? extends PidHealthProto> values) {
            ensureStatsPidsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.statsPids_);
        }

        /* access modifiers changed from: private */
        public void clearStatsPids() {
            this.statsPids_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeStatsPids(int index) {
            ensureStatsPidsIsMutable();
            this.statsPids_.remove(index);
        }

        public List<ProcessHealthProto> getStatsProcessesList() {
            return this.statsProcesses_;
        }

        public List<? extends ProcessHealthProtoOrBuilder> getStatsProcessesOrBuilderList() {
            return this.statsProcesses_;
        }

        public int getStatsProcessesCount() {
            return this.statsProcesses_.size();
        }

        public ProcessHealthProto getStatsProcesses(int index) {
            return this.statsProcesses_.get(index);
        }

        public ProcessHealthProtoOrBuilder getStatsProcessesOrBuilder(int index) {
            return this.statsProcesses_.get(index);
        }

        private void ensureStatsProcessesIsMutable() {
            if (!this.statsProcesses_.isModifiable()) {
                this.statsProcesses_ = GeneratedMessageLite.mutableCopy(this.statsProcesses_);
            }
        }

        /* access modifiers changed from: private */
        public void setStatsProcesses(int index, ProcessHealthProto value) {
            if (value != null) {
                ensureStatsProcessesIsMutable();
                this.statsProcesses_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setStatsProcesses(int index, ProcessHealthProto.Builder builderForValue) {
            ensureStatsProcessesIsMutable();
            this.statsProcesses_.set(index, (ProcessHealthProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStatsProcesses(ProcessHealthProto value) {
            if (value != null) {
                ensureStatsProcessesIsMutable();
                this.statsProcesses_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStatsProcesses(int index, ProcessHealthProto value) {
            if (value != null) {
                ensureStatsProcessesIsMutable();
                this.statsProcesses_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStatsProcesses(ProcessHealthProto.Builder builderForValue) {
            ensureStatsProcessesIsMutable();
            this.statsProcesses_.add((ProcessHealthProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStatsProcesses(int index, ProcessHealthProto.Builder builderForValue) {
            ensureStatsProcessesIsMutable();
            this.statsProcesses_.add(index, (ProcessHealthProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$ProcessHealthProto>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$ProcessHealthProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllStatsProcesses(Iterable<? extends ProcessHealthProto> values) {
            ensureStatsProcessesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.statsProcesses_);
        }

        /* access modifiers changed from: private */
        public void clearStatsProcesses() {
            this.statsProcesses_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeStatsProcesses(int index) {
            ensureStatsProcessesIsMutable();
            this.statsProcesses_.remove(index);
        }

        public List<PackageHealthProto> getStatsPackagesList() {
            return this.statsPackages_;
        }

        public List<? extends PackageHealthProtoOrBuilder> getStatsPackagesOrBuilderList() {
            return this.statsPackages_;
        }

        public int getStatsPackagesCount() {
            return this.statsPackages_.size();
        }

        public PackageHealthProto getStatsPackages(int index) {
            return this.statsPackages_.get(index);
        }

        public PackageHealthProtoOrBuilder getStatsPackagesOrBuilder(int index) {
            return this.statsPackages_.get(index);
        }

        private void ensureStatsPackagesIsMutable() {
            if (!this.statsPackages_.isModifiable()) {
                this.statsPackages_ = GeneratedMessageLite.mutableCopy(this.statsPackages_);
            }
        }

        /* access modifiers changed from: private */
        public void setStatsPackages(int index, PackageHealthProto value) {
            if (value != null) {
                ensureStatsPackagesIsMutable();
                this.statsPackages_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setStatsPackages(int index, PackageHealthProto.Builder builderForValue) {
            ensureStatsPackagesIsMutable();
            this.statsPackages_.set(index, (PackageHealthProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStatsPackages(PackageHealthProto value) {
            if (value != null) {
                ensureStatsPackagesIsMutable();
                this.statsPackages_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStatsPackages(int index, PackageHealthProto value) {
            if (value != null) {
                ensureStatsPackagesIsMutable();
                this.statsPackages_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStatsPackages(PackageHealthProto.Builder builderForValue) {
            ensureStatsPackagesIsMutable();
            this.statsPackages_.add((PackageHealthProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStatsPackages(int index, PackageHealthProto.Builder builderForValue) {
            ensureStatsPackagesIsMutable();
            this.statsPackages_.add(index, (PackageHealthProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$PackageHealthProto>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$PackageHealthProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllStatsPackages(Iterable<? extends PackageHealthProto> values) {
            ensureStatsPackagesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.statsPackages_);
        }

        /* access modifiers changed from: private */
        public void clearStatsPackages() {
            this.statsPackages_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeStatsPackages(int index) {
            ensureStatsPackagesIsMutable();
            this.statsPackages_.remove(index);
        }

        public boolean hasWifiIdleMs() {
            return (this.bitField0_ & 32) != 0;
        }

        public long getWifiIdleMs() {
            return this.wifiIdleMs_;
        }

        /* access modifiers changed from: private */
        public void setWifiIdleMs(long value) {
            this.bitField0_ |= 32;
            this.wifiIdleMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiIdleMs() {
            this.bitField0_ &= -33;
            this.wifiIdleMs_ = 0;
        }

        public boolean hasWifiRxMs() {
            return (this.bitField0_ & 64) != 0;
        }

        public long getWifiRxMs() {
            return this.wifiRxMs_;
        }

        /* access modifiers changed from: private */
        public void setWifiRxMs(long value) {
            this.bitField0_ |= 64;
            this.wifiRxMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiRxMs() {
            this.bitField0_ &= -65;
            this.wifiRxMs_ = 0;
        }

        public boolean hasWifiTxMs() {
            return (this.bitField0_ & 128) != 0;
        }

        public long getWifiTxMs() {
            return this.wifiTxMs_;
        }

        /* access modifiers changed from: private */
        public void setWifiTxMs(long value) {
            this.bitField0_ |= 128;
            this.wifiTxMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiTxMs() {
            this.bitField0_ &= -129;
            this.wifiTxMs_ = 0;
        }

        public boolean hasWifiPowerMams() {
            return (this.bitField0_ & 256) != 0;
        }

        public long getWifiPowerMams() {
            return this.wifiPowerMams_;
        }

        /* access modifiers changed from: private */
        public void setWifiPowerMams(long value) {
            this.bitField0_ |= 256;
            this.wifiPowerMams_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiPowerMams() {
            this.bitField0_ &= -257;
            this.wifiPowerMams_ = 0;
        }

        public boolean hasBluetoothIdleMs() {
            return (this.bitField0_ & 512) != 0;
        }

        public long getBluetoothIdleMs() {
            return this.bluetoothIdleMs_;
        }

        /* access modifiers changed from: private */
        public void setBluetoothIdleMs(long value) {
            this.bitField0_ |= 512;
            this.bluetoothIdleMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBluetoothIdleMs() {
            this.bitField0_ &= -513;
            this.bluetoothIdleMs_ = 0;
        }

        public boolean hasBluetoothRxMs() {
            return (this.bitField0_ & 1024) != 0;
        }

        public long getBluetoothRxMs() {
            return this.bluetoothRxMs_;
        }

        /* access modifiers changed from: private */
        public void setBluetoothRxMs(long value) {
            this.bitField0_ |= 1024;
            this.bluetoothRxMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBluetoothRxMs() {
            this.bitField0_ &= -1025;
            this.bluetoothRxMs_ = 0;
        }

        public boolean hasBluetoothTxMs() {
            return (this.bitField0_ & 2048) != 0;
        }

        public long getBluetoothTxMs() {
            return this.bluetoothTxMs_;
        }

        /* access modifiers changed from: private */
        public void setBluetoothTxMs(long value) {
            this.bitField0_ |= 2048;
            this.bluetoothTxMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBluetoothTxMs() {
            this.bitField0_ &= -2049;
            this.bluetoothTxMs_ = 0;
        }

        public boolean hasBluetoothPowerMams() {
            return (this.bitField0_ & 4096) != 0;
        }

        public long getBluetoothPowerMams() {
            return this.bluetoothPowerMams_;
        }

        /* access modifiers changed from: private */
        public void setBluetoothPowerMams(long value) {
            this.bitField0_ |= 4096;
            this.bluetoothPowerMams_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBluetoothPowerMams() {
            this.bitField0_ &= -4097;
            this.bluetoothPowerMams_ = 0;
        }

        public boolean hasMobileIdleMs() {
            return (this.bitField0_ & 8192) != 0;
        }

        public long getMobileIdleMs() {
            return this.mobileIdleMs_;
        }

        /* access modifiers changed from: private */
        public void setMobileIdleMs(long value) {
            this.bitField0_ |= 8192;
            this.mobileIdleMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMobileIdleMs() {
            this.bitField0_ &= -8193;
            this.mobileIdleMs_ = 0;
        }

        public boolean hasMobileRxMs() {
            return (this.bitField0_ & 16384) != 0;
        }

        public long getMobileRxMs() {
            return this.mobileRxMs_;
        }

        /* access modifiers changed from: private */
        public void setMobileRxMs(long value) {
            this.bitField0_ |= 16384;
            this.mobileRxMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMobileRxMs() {
            this.bitField0_ &= -16385;
            this.mobileRxMs_ = 0;
        }

        public boolean hasMobileTxMs() {
            return (this.bitField0_ & 32768) != 0;
        }

        public long getMobileTxMs() {
            return this.mobileTxMs_;
        }

        /* access modifiers changed from: private */
        public void setMobileTxMs(long value) {
            this.bitField0_ |= 32768;
            this.mobileTxMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMobileTxMs() {
            this.bitField0_ &= -32769;
            this.mobileTxMs_ = 0;
        }

        public boolean hasMobilePowerMams() {
            return (this.bitField0_ & 65536) != 0;
        }

        public long getMobilePowerMams() {
            return this.mobilePowerMams_;
        }

        /* access modifiers changed from: private */
        public void setMobilePowerMams(long value) {
            this.bitField0_ |= 65536;
            this.mobilePowerMams_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMobilePowerMams() {
            this.bitField0_ &= -65537;
            this.mobilePowerMams_ = 0;
        }

        public boolean hasWifiRunningMs() {
            return (this.bitField0_ & 131072) != 0;
        }

        public long getWifiRunningMs() {
            return this.wifiRunningMs_;
        }

        /* access modifiers changed from: private */
        public void setWifiRunningMs(long value) {
            this.bitField0_ |= 131072;
            this.wifiRunningMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiRunningMs() {
            this.bitField0_ &= -131073;
            this.wifiRunningMs_ = 0;
        }

        public boolean hasWifiFullLockMs() {
            return (this.bitField0_ & 262144) != 0;
        }

        public long getWifiFullLockMs() {
            return this.wifiFullLockMs_;
        }

        /* access modifiers changed from: private */
        public void setWifiFullLockMs(long value) {
            this.bitField0_ |= 262144;
            this.wifiFullLockMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiFullLockMs() {
            this.bitField0_ &= -262145;
            this.wifiFullLockMs_ = 0;
        }

        public boolean hasWifiScan() {
            return (this.bitField0_ & 524288) != 0;
        }

        public Timer getWifiScan() {
            Timer timer = this.wifiScan_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setWifiScan(Timer value) {
            if (value != null) {
                this.wifiScan_ = value;
                this.bitField0_ |= 524288;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setWifiScan(Timer.Builder builderForValue) {
            this.wifiScan_ = (Timer) builderForValue.build();
            this.bitField0_ |= 524288;
        }

        /* access modifiers changed from: private */
        public void mergeWifiScan(Timer value) {
            if (value != null) {
                Timer timer = this.wifiScan_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.wifiScan_ = value;
                } else {
                    this.wifiScan_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.wifiScan_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 524288;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearWifiScan() {
            this.wifiScan_ = null;
            this.bitField0_ &= -524289;
        }

        public boolean hasWifiMulticastMs() {
            return (this.bitField0_ & 1048576) != 0;
        }

        public long getWifiMulticastMs() {
            return this.wifiMulticastMs_;
        }

        /* access modifiers changed from: private */
        public void setWifiMulticastMs(long value) {
            this.bitField0_ |= 1048576;
            this.wifiMulticastMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiMulticastMs() {
            this.bitField0_ &= -1048577;
            this.wifiMulticastMs_ = 0;
        }

        public boolean hasAudio() {
            return (this.bitField0_ & 2097152) != 0;
        }

        public Timer getAudio() {
            Timer timer = this.audio_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setAudio(Timer value) {
            if (value != null) {
                this.audio_ = value;
                this.bitField0_ |= 2097152;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAudio(Timer.Builder builderForValue) {
            this.audio_ = (Timer) builderForValue.build();
            this.bitField0_ |= 2097152;
        }

        /* access modifiers changed from: private */
        public void mergeAudio(Timer value) {
            if (value != null) {
                Timer timer = this.audio_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.audio_ = value;
                } else {
                    this.audio_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.audio_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2097152;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAudio() {
            this.audio_ = null;
            this.bitField0_ &= -2097153;
        }

        public boolean hasVideo() {
            return (this.bitField0_ & 4194304) != 0;
        }

        public Timer getVideo() {
            Timer timer = this.video_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setVideo(Timer value) {
            if (value != null) {
                this.video_ = value;
                this.bitField0_ |= 4194304;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setVideo(Timer.Builder builderForValue) {
            this.video_ = (Timer) builderForValue.build();
            this.bitField0_ |= 4194304;
        }

        /* access modifiers changed from: private */
        public void mergeVideo(Timer value) {
            if (value != null) {
                Timer timer = this.video_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.video_ = value;
                } else {
                    this.video_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.video_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4194304;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearVideo() {
            this.video_ = null;
            this.bitField0_ &= -4194305;
        }

        public boolean hasFlashlight() {
            return (this.bitField0_ & 8388608) != 0;
        }

        public Timer getFlashlight() {
            Timer timer = this.flashlight_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setFlashlight(Timer value) {
            if (value != null) {
                this.flashlight_ = value;
                this.bitField0_ |= 8388608;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setFlashlight(Timer.Builder builderForValue) {
            this.flashlight_ = (Timer) builderForValue.build();
            this.bitField0_ |= 8388608;
        }

        /* access modifiers changed from: private */
        public void mergeFlashlight(Timer value) {
            if (value != null) {
                Timer timer = this.flashlight_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.flashlight_ = value;
                } else {
                    this.flashlight_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.flashlight_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8388608;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearFlashlight() {
            this.flashlight_ = null;
            this.bitField0_ &= -8388609;
        }

        public boolean hasCamera() {
            return (this.bitField0_ & 16777216) != 0;
        }

        public Timer getCamera() {
            Timer timer = this.camera_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setCamera(Timer value) {
            if (value != null) {
                this.camera_ = value;
                this.bitField0_ |= 16777216;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCamera(Timer.Builder builderForValue) {
            this.camera_ = (Timer) builderForValue.build();
            this.bitField0_ |= 16777216;
        }

        /* access modifiers changed from: private */
        public void mergeCamera(Timer value) {
            if (value != null) {
                Timer timer = this.camera_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.camera_ = value;
                } else {
                    this.camera_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.camera_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16777216;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCamera() {
            this.camera_ = null;
            this.bitField0_ &= -16777217;
        }

        public boolean hasForegroundActivity() {
            return (this.bitField0_ & 33554432) != 0;
        }

        public Timer getForegroundActivity() {
            Timer timer = this.foregroundActivity_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setForegroundActivity(Timer value) {
            if (value != null) {
                this.foregroundActivity_ = value;
                this.bitField0_ |= 33554432;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setForegroundActivity(Timer.Builder builderForValue) {
            this.foregroundActivity_ = (Timer) builderForValue.build();
            this.bitField0_ |= 33554432;
        }

        /* access modifiers changed from: private */
        public void mergeForegroundActivity(Timer value) {
            if (value != null) {
                Timer timer = this.foregroundActivity_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.foregroundActivity_ = value;
                } else {
                    this.foregroundActivity_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.foregroundActivity_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 33554432;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearForegroundActivity() {
            this.foregroundActivity_ = null;
            this.bitField0_ &= -33554433;
        }

        public boolean hasBluetoothScan() {
            return (this.bitField0_ & 67108864) != 0;
        }

        public Timer getBluetoothScan() {
            Timer timer = this.bluetoothScan_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setBluetoothScan(Timer value) {
            if (value != null) {
                this.bluetoothScan_ = value;
                this.bitField0_ |= 67108864;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setBluetoothScan(Timer.Builder builderForValue) {
            this.bluetoothScan_ = (Timer) builderForValue.build();
            this.bitField0_ |= 67108864;
        }

        /* access modifiers changed from: private */
        public void mergeBluetoothScan(Timer value) {
            if (value != null) {
                Timer timer = this.bluetoothScan_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.bluetoothScan_ = value;
                } else {
                    this.bluetoothScan_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.bluetoothScan_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 67108864;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearBluetoothScan() {
            this.bluetoothScan_ = null;
            this.bitField0_ &= -67108865;
        }

        public boolean hasProcessStateTopMs() {
            return (this.bitField0_ & 134217728) != 0;
        }

        public Timer getProcessStateTopMs() {
            Timer timer = this.processStateTopMs_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setProcessStateTopMs(Timer value) {
            if (value != null) {
                this.processStateTopMs_ = value;
                this.bitField0_ |= 134217728;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStateTopMs(Timer.Builder builderForValue) {
            this.processStateTopMs_ = (Timer) builderForValue.build();
            this.bitField0_ |= 134217728;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStateTopMs(Timer value) {
            if (value != null) {
                Timer timer = this.processStateTopMs_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.processStateTopMs_ = value;
                } else {
                    this.processStateTopMs_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.processStateTopMs_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 134217728;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStateTopMs() {
            this.processStateTopMs_ = null;
            this.bitField0_ &= -134217729;
        }

        public boolean hasProcessStateForegroundServiceMs() {
            return (this.bitField0_ & C0841C.ENCODING_PCM_MU_LAW) != 0;
        }

        public Timer getProcessStateForegroundServiceMs() {
            Timer timer = this.processStateForegroundServiceMs_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setProcessStateForegroundServiceMs(Timer value) {
            if (value != null) {
                this.processStateForegroundServiceMs_ = value;
                this.bitField0_ |= C0841C.ENCODING_PCM_MU_LAW;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStateForegroundServiceMs(Timer.Builder builderForValue) {
            this.processStateForegroundServiceMs_ = (Timer) builderForValue.build();
            this.bitField0_ |= C0841C.ENCODING_PCM_MU_LAW;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStateForegroundServiceMs(Timer value) {
            if (value != null) {
                Timer timer = this.processStateForegroundServiceMs_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.processStateForegroundServiceMs_ = value;
                } else {
                    this.processStateForegroundServiceMs_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.processStateForegroundServiceMs_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= C0841C.ENCODING_PCM_MU_LAW;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStateForegroundServiceMs() {
            this.processStateForegroundServiceMs_ = null;
            this.bitField0_ &= -268435457;
        }

        public boolean hasProcessStateTopSleepingMs() {
            return (this.bitField0_ & 536870912) != 0;
        }

        public Timer getProcessStateTopSleepingMs() {
            Timer timer = this.processStateTopSleepingMs_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setProcessStateTopSleepingMs(Timer value) {
            if (value != null) {
                this.processStateTopSleepingMs_ = value;
                this.bitField0_ |= 536870912;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStateTopSleepingMs(Timer.Builder builderForValue) {
            this.processStateTopSleepingMs_ = (Timer) builderForValue.build();
            this.bitField0_ |= 536870912;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStateTopSleepingMs(Timer value) {
            if (value != null) {
                Timer timer = this.processStateTopSleepingMs_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.processStateTopSleepingMs_ = value;
                } else {
                    this.processStateTopSleepingMs_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.processStateTopSleepingMs_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 536870912;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStateTopSleepingMs() {
            this.processStateTopSleepingMs_ = null;
            this.bitField0_ &= -536870913;
        }

        public boolean hasProcessStateForegroundMs() {
            return (this.bitField0_ & 1073741824) != 0;
        }

        public Timer getProcessStateForegroundMs() {
            Timer timer = this.processStateForegroundMs_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setProcessStateForegroundMs(Timer value) {
            if (value != null) {
                this.processStateForegroundMs_ = value;
                this.bitField0_ |= 1073741824;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStateForegroundMs(Timer.Builder builderForValue) {
            this.processStateForegroundMs_ = (Timer) builderForValue.build();
            this.bitField0_ |= 1073741824;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStateForegroundMs(Timer value) {
            if (value != null) {
                Timer timer = this.processStateForegroundMs_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.processStateForegroundMs_ = value;
                } else {
                    this.processStateForegroundMs_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.processStateForegroundMs_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1073741824;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStateForegroundMs() {
            this.processStateForegroundMs_ = null;
            this.bitField0_ &= -1073741825;
        }

        public boolean hasProcessStateBackgroundMs() {
            return (this.bitField0_ & Integer.MIN_VALUE) != 0;
        }

        public Timer getProcessStateBackgroundMs() {
            Timer timer = this.processStateBackgroundMs_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setProcessStateBackgroundMs(Timer value) {
            if (value != null) {
                this.processStateBackgroundMs_ = value;
                this.bitField0_ |= Integer.MIN_VALUE;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStateBackgroundMs(Timer.Builder builderForValue) {
            this.processStateBackgroundMs_ = (Timer) builderForValue.build();
            this.bitField0_ |= Integer.MIN_VALUE;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStateBackgroundMs(Timer value) {
            if (value != null) {
                Timer timer = this.processStateBackgroundMs_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.processStateBackgroundMs_ = value;
                } else {
                    this.processStateBackgroundMs_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.processStateBackgroundMs_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= Integer.MIN_VALUE;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStateBackgroundMs() {
            this.processStateBackgroundMs_ = null;
            this.bitField0_ &= Integer.MAX_VALUE;
        }

        public boolean hasProcessStateCachedMs() {
            return (this.bitField1_ & 1) != 0;
        }

        public Timer getProcessStateCachedMs() {
            Timer timer = this.processStateCachedMs_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setProcessStateCachedMs(Timer value) {
            if (value != null) {
                this.processStateCachedMs_ = value;
                this.bitField1_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProcessStateCachedMs(Timer.Builder builderForValue) {
            this.processStateCachedMs_ = (Timer) builderForValue.build();
            this.bitField1_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeProcessStateCachedMs(Timer value) {
            if (value != null) {
                Timer timer = this.processStateCachedMs_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.processStateCachedMs_ = value;
                } else {
                    this.processStateCachedMs_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.processStateCachedMs_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField1_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessStateCachedMs() {
            this.processStateCachedMs_ = null;
            this.bitField1_ &= -2;
        }

        public boolean hasVibrator() {
            return (this.bitField1_ & 2) != 0;
        }

        public Timer getVibrator() {
            Timer timer = this.vibrator_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setVibrator(Timer value) {
            if (value != null) {
                this.vibrator_ = value;
                this.bitField1_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setVibrator(Timer.Builder builderForValue) {
            this.vibrator_ = (Timer) builderForValue.build();
            this.bitField1_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeVibrator(Timer value) {
            if (value != null) {
                Timer timer = this.vibrator_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.vibrator_ = value;
                } else {
                    this.vibrator_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.vibrator_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField1_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearVibrator() {
            this.vibrator_ = null;
            this.bitField1_ &= -3;
        }

        public boolean hasOtherUserActivityCount() {
            return (this.bitField1_ & 4) != 0;
        }

        public long getOtherUserActivityCount() {
            return this.otherUserActivityCount_;
        }

        /* access modifiers changed from: private */
        public void setOtherUserActivityCount(long value) {
            this.bitField1_ |= 4;
            this.otherUserActivityCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearOtherUserActivityCount() {
            this.bitField1_ &= -5;
            this.otherUserActivityCount_ = 0;
        }

        public boolean hasButtonUserActivityCount() {
            return (this.bitField1_ & 8) != 0;
        }

        public long getButtonUserActivityCount() {
            return this.buttonUserActivityCount_;
        }

        /* access modifiers changed from: private */
        public void setButtonUserActivityCount(long value) {
            this.bitField1_ |= 8;
            this.buttonUserActivityCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearButtonUserActivityCount() {
            this.bitField1_ &= -9;
            this.buttonUserActivityCount_ = 0;
        }

        public boolean hasTouchUserActivityCount() {
            return (this.bitField1_ & 16) != 0;
        }

        public long getTouchUserActivityCount() {
            return this.touchUserActivityCount_;
        }

        /* access modifiers changed from: private */
        public void setTouchUserActivityCount(long value) {
            this.bitField1_ |= 16;
            this.touchUserActivityCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTouchUserActivityCount() {
            this.bitField1_ &= -17;
            this.touchUserActivityCount_ = 0;
        }

        public boolean hasMobileRxBytes() {
            return (this.bitField1_ & 32) != 0;
        }

        public long getMobileRxBytes() {
            return this.mobileRxBytes_;
        }

        /* access modifiers changed from: private */
        public void setMobileRxBytes(long value) {
            this.bitField1_ |= 32;
            this.mobileRxBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMobileRxBytes() {
            this.bitField1_ &= -33;
            this.mobileRxBytes_ = 0;
        }

        public boolean hasMobileTxBytes() {
            return (this.bitField1_ & 64) != 0;
        }

        public long getMobileTxBytes() {
            return this.mobileTxBytes_;
        }

        /* access modifiers changed from: private */
        public void setMobileTxBytes(long value) {
            this.bitField1_ |= 64;
            this.mobileTxBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMobileTxBytes() {
            this.bitField1_ &= -65;
            this.mobileTxBytes_ = 0;
        }

        public boolean hasWifiRxBytes() {
            return (this.bitField1_ & 128) != 0;
        }

        public long getWifiRxBytes() {
            return this.wifiRxBytes_;
        }

        /* access modifiers changed from: private */
        public void setWifiRxBytes(long value) {
            this.bitField1_ |= 128;
            this.wifiRxBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiRxBytes() {
            this.bitField1_ &= -129;
            this.wifiRxBytes_ = 0;
        }

        public boolean hasWifiTxBytes() {
            return (this.bitField1_ & 256) != 0;
        }

        public long getWifiTxBytes() {
            return this.wifiTxBytes_;
        }

        /* access modifiers changed from: private */
        public void setWifiTxBytes(long value) {
            this.bitField1_ |= 256;
            this.wifiTxBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiTxBytes() {
            this.bitField1_ &= -257;
            this.wifiTxBytes_ = 0;
        }

        public boolean hasBluetoothRxBytes() {
            return (this.bitField1_ & 512) != 0;
        }

        public long getBluetoothRxBytes() {
            return this.bluetoothRxBytes_;
        }

        /* access modifiers changed from: private */
        public void setBluetoothRxBytes(long value) {
            this.bitField1_ |= 512;
            this.bluetoothRxBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBluetoothRxBytes() {
            this.bitField1_ &= -513;
            this.bluetoothRxBytes_ = 0;
        }

        public boolean hasBluetoothTxBytes() {
            return (this.bitField1_ & 1024) != 0;
        }

        public long getBluetoothTxBytes() {
            return this.bluetoothTxBytes_;
        }

        /* access modifiers changed from: private */
        public void setBluetoothTxBytes(long value) {
            this.bitField1_ |= 1024;
            this.bluetoothTxBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBluetoothTxBytes() {
            this.bitField1_ &= -1025;
            this.bluetoothTxBytes_ = 0;
        }

        public boolean hasMobileRxPackets() {
            return (this.bitField1_ & 2048) != 0;
        }

        public long getMobileRxPackets() {
            return this.mobileRxPackets_;
        }

        /* access modifiers changed from: private */
        public void setMobileRxPackets(long value) {
            this.bitField1_ |= 2048;
            this.mobileRxPackets_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMobileRxPackets() {
            this.bitField1_ &= -2049;
            this.mobileRxPackets_ = 0;
        }

        public boolean hasMobileTxPackets() {
            return (this.bitField1_ & 4096) != 0;
        }

        public long getMobileTxPackets() {
            return this.mobileTxPackets_;
        }

        /* access modifiers changed from: private */
        public void setMobileTxPackets(long value) {
            this.bitField1_ |= 4096;
            this.mobileTxPackets_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMobileTxPackets() {
            this.bitField1_ &= -4097;
            this.mobileTxPackets_ = 0;
        }

        public boolean hasWifiRxPackets() {
            return (this.bitField1_ & 8192) != 0;
        }

        public long getWifiRxPackets() {
            return this.wifiRxPackets_;
        }

        /* access modifiers changed from: private */
        public void setWifiRxPackets(long value) {
            this.bitField1_ |= 8192;
            this.wifiRxPackets_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiRxPackets() {
            this.bitField1_ &= -8193;
            this.wifiRxPackets_ = 0;
        }

        public boolean hasWifiTxPackets() {
            return (this.bitField1_ & 16384) != 0;
        }

        public long getWifiTxPackets() {
            return this.wifiTxPackets_;
        }

        /* access modifiers changed from: private */
        public void setWifiTxPackets(long value) {
            this.bitField1_ |= 16384;
            this.wifiTxPackets_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiTxPackets() {
            this.bitField1_ &= -16385;
            this.wifiTxPackets_ = 0;
        }

        public boolean hasBluetoothRxPackets() {
            return (this.bitField1_ & 32768) != 0;
        }

        public long getBluetoothRxPackets() {
            return this.bluetoothRxPackets_;
        }

        /* access modifiers changed from: private */
        public void setBluetoothRxPackets(long value) {
            this.bitField1_ |= 32768;
            this.bluetoothRxPackets_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBluetoothRxPackets() {
            this.bitField1_ &= -32769;
            this.bluetoothRxPackets_ = 0;
        }

        public boolean hasBluetoothTxPackets() {
            return (this.bitField1_ & 65536) != 0;
        }

        public long getBluetoothTxPackets() {
            return this.bluetoothTxPackets_;
        }

        /* access modifiers changed from: private */
        public void setBluetoothTxPackets(long value) {
            this.bitField1_ |= 65536;
            this.bluetoothTxPackets_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBluetoothTxPackets() {
            this.bitField1_ &= -65537;
            this.bluetoothTxPackets_ = 0;
        }

        public boolean hasMobileRadioActive() {
            return (this.bitField1_ & 131072) != 0;
        }

        public Timer getMobileRadioActive() {
            Timer timer = this.mobileRadioActive_;
            return timer == null ? Timer.getDefaultInstance() : timer;
        }

        /* access modifiers changed from: private */
        public void setMobileRadioActive(Timer value) {
            if (value != null) {
                this.mobileRadioActive_ = value;
                this.bitField1_ |= 131072;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMobileRadioActive(Timer.Builder builderForValue) {
            this.mobileRadioActive_ = (Timer) builderForValue.build();
            this.bitField1_ |= 131072;
        }

        /* access modifiers changed from: private */
        public void mergeMobileRadioActive(Timer value) {
            if (value != null) {
                Timer timer = this.mobileRadioActive_;
                if (timer == null || timer == Timer.getDefaultInstance()) {
                    this.mobileRadioActive_ = value;
                } else {
                    this.mobileRadioActive_ = (Timer) ((Timer.Builder) Timer.newBuilder(this.mobileRadioActive_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField1_ |= 131072;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMobileRadioActive() {
            this.mobileRadioActive_ = null;
            this.bitField1_ &= -131073;
        }

        public boolean hasUserCpuTimeMs() {
            return (this.bitField1_ & 262144) != 0;
        }

        public long getUserCpuTimeMs() {
            return this.userCpuTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setUserCpuTimeMs(long value) {
            this.bitField1_ |= 262144;
            this.userCpuTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearUserCpuTimeMs() {
            this.bitField1_ &= -262145;
            this.userCpuTimeMs_ = 0;
        }

        public boolean hasSystemCpuTimeMs() {
            return (this.bitField1_ & 524288) != 0;
        }

        public long getSystemCpuTimeMs() {
            return this.systemCpuTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setSystemCpuTimeMs(long value) {
            this.bitField1_ |= 524288;
            this.systemCpuTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSystemCpuTimeMs() {
            this.bitField1_ &= -524289;
            this.systemCpuTimeMs_ = 0;
        }

        public boolean hasCpuPowerMams() {
            return (this.bitField1_ & 1048576) != 0;
        }

        public long getCpuPowerMams() {
            return this.cpuPowerMams_;
        }

        /* access modifiers changed from: private */
        public void setCpuPowerMams(long value) {
            this.bitField1_ |= 1048576;
            this.cpuPowerMams_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCpuPowerMams() {
            this.bitField1_ &= -1048577;
            this.cpuPowerMams_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new UidHealthProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001?\u0000\u0002\u0001@?\u0000\n\u0000\u0001\u0002\u0000\u0002\u0002\u0001\u0003\u0002\u0002\u0004\u0002\u0003\u0005\u001b\u0006\u001b\u0007\u001b\b\u001b\t\u001b\n\u001b\u000b\t\u0004\f\u001b\r\u001b\u000e\u001b\u000f\u001b\u0010\u0002\u0005\u0011\u0002\u0006\u0012\u0002\u0007\u0013\u0002\b\u0014\u0002\t\u0015\u0002\n\u0016\u0002\u000b\u0017\u0002\f\u0018\u0002\r\u0019\u0002\u000e\u001a\u0002\u000f\u001b\u0002\u0010\u001c\u0002\u0011\u001d\u0002\u0012\u001e\t\u0013\u001f\u0002\u0014 \t\u0015!\t\u0016\"\t\u0017#\t\u0018$\t\u0019%\t\u001a&\t\u001b'\t\u001c(\t\u001d)\t\u001e*\t\u001f+\t ,\t!-\u0002\".\u0002#/\u0002$0\u0002%1\u0002&2\u0002'3\u0002(4\u0002)5\u0002*6\u0002+7\u0002,8\u0002-9\u0002.:\u0002/;\u00020=\t1>\u00022?\u00023@\u00024", new Object[]{"bitField0_", "bitField1_", "realtimeBatteryMs_", "uptimeBatteryMs_", "realtimeScreenOffBatteryMs_", "uptimeScreenOffBatteryMs_", "wakelocksFull_", Timer.class, "wakelocksPartial_", Timer.class, "wakelocksWindow_", Timer.class, "wakelocksDraw_", Timer.class, "syncs_", Timer.class, "jobs_", Timer.class, "gpsSensor_", "sensors_", Timer.class, "statsPids_", PidHealthProto.class, "statsProcesses_", ProcessHealthProto.class, "statsPackages_", PackageHealthProto.class, "wifiIdleMs_", "wifiRxMs_", "wifiTxMs_", "wifiPowerMams_", "bluetoothIdleMs_", "bluetoothRxMs_", "bluetoothTxMs_", "bluetoothPowerMams_", "mobileIdleMs_", "mobileRxMs_", "mobileTxMs_", "mobilePowerMams_", "wifiRunningMs_", "wifiFullLockMs_", "wifiScan_", "wifiMulticastMs_", "audio_", "video_", "flashlight_", "camera_", "foregroundActivity_", "bluetoothScan_", "processStateTopMs_", "processStateForegroundServiceMs_", "processStateTopSleepingMs_", "processStateForegroundMs_", "processStateBackgroundMs_", "processStateCachedMs_", "vibrator_", "otherUserActivityCount_", "buttonUserActivityCount_", "touchUserActivityCount_", "mobileRxBytes_", "mobileTxBytes_", "wifiRxBytes_", "wifiTxBytes_", "bluetoothRxBytes_", "bluetoothTxBytes_", "mobileRxPackets_", "mobileTxPackets_", "wifiRxPackets_", "wifiTxPackets_", "bluetoothRxPackets_", "bluetoothTxPackets_", "mobileRadioActive_", "userCpuTimeMs_", "systemCpuTimeMs_", "cpuPowerMams_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<UidHealthProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (UidHealthProto.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<UidHealthProto, Builder> implements UidHealthProtoOrBuilder {
            private Builder() {
                super(UidHealthProto.DEFAULT_INSTANCE);
            }

            public boolean hasRealtimeBatteryMs() {
                return ((UidHealthProto) this.instance).hasRealtimeBatteryMs();
            }

            public long getRealtimeBatteryMs() {
                return ((UidHealthProto) this.instance).getRealtimeBatteryMs();
            }

            public Builder setRealtimeBatteryMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setRealtimeBatteryMs(value);
                return this;
            }

            public Builder clearRealtimeBatteryMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearRealtimeBatteryMs();
                return this;
            }

            public boolean hasUptimeBatteryMs() {
                return ((UidHealthProto) this.instance).hasUptimeBatteryMs();
            }

            public long getUptimeBatteryMs() {
                return ((UidHealthProto) this.instance).getUptimeBatteryMs();
            }

            public Builder setUptimeBatteryMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setUptimeBatteryMs(value);
                return this;
            }

            public Builder clearUptimeBatteryMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearUptimeBatteryMs();
                return this;
            }

            public boolean hasRealtimeScreenOffBatteryMs() {
                return ((UidHealthProto) this.instance).hasRealtimeScreenOffBatteryMs();
            }

            public long getRealtimeScreenOffBatteryMs() {
                return ((UidHealthProto) this.instance).getRealtimeScreenOffBatteryMs();
            }

            public Builder setRealtimeScreenOffBatteryMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setRealtimeScreenOffBatteryMs(value);
                return this;
            }

            public Builder clearRealtimeScreenOffBatteryMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearRealtimeScreenOffBatteryMs();
                return this;
            }

            public boolean hasUptimeScreenOffBatteryMs() {
                return ((UidHealthProto) this.instance).hasUptimeScreenOffBatteryMs();
            }

            public long getUptimeScreenOffBatteryMs() {
                return ((UidHealthProto) this.instance).getUptimeScreenOffBatteryMs();
            }

            public Builder setUptimeScreenOffBatteryMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setUptimeScreenOffBatteryMs(value);
                return this;
            }

            public Builder clearUptimeScreenOffBatteryMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearUptimeScreenOffBatteryMs();
                return this;
            }

            public List<Timer> getWakelocksFullList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getWakelocksFullList());
            }

            public int getWakelocksFullCount() {
                return ((UidHealthProto) this.instance).getWakelocksFullCount();
            }

            public Timer getWakelocksFull(int index) {
                return ((UidHealthProto) this.instance).getWakelocksFull(index);
            }

            public Builder setWakelocksFull(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWakelocksFull(index, value);
                return this;
            }

            public Builder setWakelocksFull(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWakelocksFull(index, builderForValue);
                return this;
            }

            public Builder addWakelocksFull(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksFull(value);
                return this;
            }

            public Builder addWakelocksFull(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksFull(index, value);
                return this;
            }

            public Builder addWakelocksFull(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksFull(builderForValue);
                return this;
            }

            public Builder addWakelocksFull(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksFull(index, builderForValue);
                return this;
            }

            public Builder addAllWakelocksFull(Iterable<? extends Timer> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllWakelocksFull(values);
                return this;
            }

            public Builder clearWakelocksFull() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWakelocksFull();
                return this;
            }

            public Builder removeWakelocksFull(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeWakelocksFull(index);
                return this;
            }

            public List<Timer> getWakelocksPartialList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getWakelocksPartialList());
            }

            public int getWakelocksPartialCount() {
                return ((UidHealthProto) this.instance).getWakelocksPartialCount();
            }

            public Timer getWakelocksPartial(int index) {
                return ((UidHealthProto) this.instance).getWakelocksPartial(index);
            }

            public Builder setWakelocksPartial(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWakelocksPartial(index, value);
                return this;
            }

            public Builder setWakelocksPartial(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWakelocksPartial(index, builderForValue);
                return this;
            }

            public Builder addWakelocksPartial(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksPartial(value);
                return this;
            }

            public Builder addWakelocksPartial(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksPartial(index, value);
                return this;
            }

            public Builder addWakelocksPartial(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksPartial(builderForValue);
                return this;
            }

            public Builder addWakelocksPartial(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksPartial(index, builderForValue);
                return this;
            }

            public Builder addAllWakelocksPartial(Iterable<? extends Timer> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllWakelocksPartial(values);
                return this;
            }

            public Builder clearWakelocksPartial() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWakelocksPartial();
                return this;
            }

            public Builder removeWakelocksPartial(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeWakelocksPartial(index);
                return this;
            }

            public List<Timer> getWakelocksWindowList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getWakelocksWindowList());
            }

            public int getWakelocksWindowCount() {
                return ((UidHealthProto) this.instance).getWakelocksWindowCount();
            }

            public Timer getWakelocksWindow(int index) {
                return ((UidHealthProto) this.instance).getWakelocksWindow(index);
            }

            public Builder setWakelocksWindow(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWakelocksWindow(index, value);
                return this;
            }

            public Builder setWakelocksWindow(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWakelocksWindow(index, builderForValue);
                return this;
            }

            public Builder addWakelocksWindow(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksWindow(value);
                return this;
            }

            public Builder addWakelocksWindow(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksWindow(index, value);
                return this;
            }

            public Builder addWakelocksWindow(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksWindow(builderForValue);
                return this;
            }

            public Builder addWakelocksWindow(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksWindow(index, builderForValue);
                return this;
            }

            public Builder addAllWakelocksWindow(Iterable<? extends Timer> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllWakelocksWindow(values);
                return this;
            }

            public Builder clearWakelocksWindow() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWakelocksWindow();
                return this;
            }

            public Builder removeWakelocksWindow(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeWakelocksWindow(index);
                return this;
            }

            public List<Timer> getWakelocksDrawList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getWakelocksDrawList());
            }

            public int getWakelocksDrawCount() {
                return ((UidHealthProto) this.instance).getWakelocksDrawCount();
            }

            public Timer getWakelocksDraw(int index) {
                return ((UidHealthProto) this.instance).getWakelocksDraw(index);
            }

            public Builder setWakelocksDraw(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWakelocksDraw(index, value);
                return this;
            }

            public Builder setWakelocksDraw(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWakelocksDraw(index, builderForValue);
                return this;
            }

            public Builder addWakelocksDraw(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksDraw(value);
                return this;
            }

            public Builder addWakelocksDraw(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksDraw(index, value);
                return this;
            }

            public Builder addWakelocksDraw(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksDraw(builderForValue);
                return this;
            }

            public Builder addWakelocksDraw(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addWakelocksDraw(index, builderForValue);
                return this;
            }

            public Builder addAllWakelocksDraw(Iterable<? extends Timer> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllWakelocksDraw(values);
                return this;
            }

            public Builder clearWakelocksDraw() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWakelocksDraw();
                return this;
            }

            public Builder removeWakelocksDraw(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeWakelocksDraw(index);
                return this;
            }

            public List<Timer> getSyncsList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getSyncsList());
            }

            public int getSyncsCount() {
                return ((UidHealthProto) this.instance).getSyncsCount();
            }

            public Timer getSyncs(int index) {
                return ((UidHealthProto) this.instance).getSyncs(index);
            }

            public Builder setSyncs(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setSyncs(index, value);
                return this;
            }

            public Builder setSyncs(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setSyncs(index, builderForValue);
                return this;
            }

            public Builder addSyncs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addSyncs(value);
                return this;
            }

            public Builder addSyncs(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addSyncs(index, value);
                return this;
            }

            public Builder addSyncs(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addSyncs(builderForValue);
                return this;
            }

            public Builder addSyncs(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addSyncs(index, builderForValue);
                return this;
            }

            public Builder addAllSyncs(Iterable<? extends Timer> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllSyncs(values);
                return this;
            }

            public Builder clearSyncs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearSyncs();
                return this;
            }

            public Builder removeSyncs(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeSyncs(index);
                return this;
            }

            public List<Timer> getJobsList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getJobsList());
            }

            public int getJobsCount() {
                return ((UidHealthProto) this.instance).getJobsCount();
            }

            public Timer getJobs(int index) {
                return ((UidHealthProto) this.instance).getJobs(index);
            }

            public Builder setJobs(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setJobs(index, value);
                return this;
            }

            public Builder setJobs(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setJobs(index, builderForValue);
                return this;
            }

            public Builder addJobs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addJobs(value);
                return this;
            }

            public Builder addJobs(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addJobs(index, value);
                return this;
            }

            public Builder addJobs(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addJobs(builderForValue);
                return this;
            }

            public Builder addJobs(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addJobs(index, builderForValue);
                return this;
            }

            public Builder addAllJobs(Iterable<? extends Timer> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllJobs(values);
                return this;
            }

            public Builder clearJobs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearJobs();
                return this;
            }

            public Builder removeJobs(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeJobs(index);
                return this;
            }

            public boolean hasGpsSensor() {
                return ((UidHealthProto) this.instance).hasGpsSensor();
            }

            public Timer getGpsSensor() {
                return ((UidHealthProto) this.instance).getGpsSensor();
            }

            public Builder setGpsSensor(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setGpsSensor(value);
                return this;
            }

            public Builder setGpsSensor(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setGpsSensor(builderForValue);
                return this;
            }

            public Builder mergeGpsSensor(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeGpsSensor(value);
                return this;
            }

            public Builder clearGpsSensor() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearGpsSensor();
                return this;
            }

            public List<Timer> getSensorsList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getSensorsList());
            }

            public int getSensorsCount() {
                return ((UidHealthProto) this.instance).getSensorsCount();
            }

            public Timer getSensors(int index) {
                return ((UidHealthProto) this.instance).getSensors(index);
            }

            public Builder setSensors(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setSensors(index, value);
                return this;
            }

            public Builder setSensors(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setSensors(index, builderForValue);
                return this;
            }

            public Builder addSensors(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addSensors(value);
                return this;
            }

            public Builder addSensors(int index, Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addSensors(index, value);
                return this;
            }

            public Builder addSensors(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addSensors(builderForValue);
                return this;
            }

            public Builder addSensors(int index, Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addSensors(index, builderForValue);
                return this;
            }

            public Builder addAllSensors(Iterable<? extends Timer> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllSensors(values);
                return this;
            }

            public Builder clearSensors() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearSensors();
                return this;
            }

            public Builder removeSensors(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeSensors(index);
                return this;
            }

            public List<PidHealthProto> getStatsPidsList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getStatsPidsList());
            }

            public int getStatsPidsCount() {
                return ((UidHealthProto) this.instance).getStatsPidsCount();
            }

            public PidHealthProto getStatsPids(int index) {
                return ((UidHealthProto) this.instance).getStatsPids(index);
            }

            public Builder setStatsPids(int index, PidHealthProto value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setStatsPids(index, value);
                return this;
            }

            public Builder setStatsPids(int index, PidHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setStatsPids(index, builderForValue);
                return this;
            }

            public Builder addStatsPids(PidHealthProto value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsPids(value);
                return this;
            }

            public Builder addStatsPids(int index, PidHealthProto value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsPids(index, value);
                return this;
            }

            public Builder addStatsPids(PidHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsPids(builderForValue);
                return this;
            }

            public Builder addStatsPids(int index, PidHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsPids(index, builderForValue);
                return this;
            }

            public Builder addAllStatsPids(Iterable<? extends PidHealthProto> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllStatsPids(values);
                return this;
            }

            public Builder clearStatsPids() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearStatsPids();
                return this;
            }

            public Builder removeStatsPids(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeStatsPids(index);
                return this;
            }

            public List<ProcessHealthProto> getStatsProcessesList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getStatsProcessesList());
            }

            public int getStatsProcessesCount() {
                return ((UidHealthProto) this.instance).getStatsProcessesCount();
            }

            public ProcessHealthProto getStatsProcesses(int index) {
                return ((UidHealthProto) this.instance).getStatsProcesses(index);
            }

            public Builder setStatsProcesses(int index, ProcessHealthProto value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setStatsProcesses(index, value);
                return this;
            }

            public Builder setStatsProcesses(int index, ProcessHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setStatsProcesses(index, builderForValue);
                return this;
            }

            public Builder addStatsProcesses(ProcessHealthProto value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsProcesses(value);
                return this;
            }

            public Builder addStatsProcesses(int index, ProcessHealthProto value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsProcesses(index, value);
                return this;
            }

            public Builder addStatsProcesses(ProcessHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsProcesses(builderForValue);
                return this;
            }

            public Builder addStatsProcesses(int index, ProcessHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsProcesses(index, builderForValue);
                return this;
            }

            public Builder addAllStatsProcesses(Iterable<? extends ProcessHealthProto> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllStatsProcesses(values);
                return this;
            }

            public Builder clearStatsProcesses() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearStatsProcesses();
                return this;
            }

            public Builder removeStatsProcesses(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeStatsProcesses(index);
                return this;
            }

            public List<PackageHealthProto> getStatsPackagesList() {
                return Collections.unmodifiableList(((UidHealthProto) this.instance).getStatsPackagesList());
            }

            public int getStatsPackagesCount() {
                return ((UidHealthProto) this.instance).getStatsPackagesCount();
            }

            public PackageHealthProto getStatsPackages(int index) {
                return ((UidHealthProto) this.instance).getStatsPackages(index);
            }

            public Builder setStatsPackages(int index, PackageHealthProto value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setStatsPackages(index, value);
                return this;
            }

            public Builder setStatsPackages(int index, PackageHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setStatsPackages(index, builderForValue);
                return this;
            }

            public Builder addStatsPackages(PackageHealthProto value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsPackages(value);
                return this;
            }

            public Builder addStatsPackages(int index, PackageHealthProto value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsPackages(index, value);
                return this;
            }

            public Builder addStatsPackages(PackageHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsPackages(builderForValue);
                return this;
            }

            public Builder addStatsPackages(int index, PackageHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addStatsPackages(index, builderForValue);
                return this;
            }

            public Builder addAllStatsPackages(Iterable<? extends PackageHealthProto> values) {
                copyOnWrite();
                ((UidHealthProto) this.instance).addAllStatsPackages(values);
                return this;
            }

            public Builder clearStatsPackages() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearStatsPackages();
                return this;
            }

            public Builder removeStatsPackages(int index) {
                copyOnWrite();
                ((UidHealthProto) this.instance).removeStatsPackages(index);
                return this;
            }

            public boolean hasWifiIdleMs() {
                return ((UidHealthProto) this.instance).hasWifiIdleMs();
            }

            public long getWifiIdleMs() {
                return ((UidHealthProto) this.instance).getWifiIdleMs();
            }

            public Builder setWifiIdleMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiIdleMs(value);
                return this;
            }

            public Builder clearWifiIdleMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiIdleMs();
                return this;
            }

            public boolean hasWifiRxMs() {
                return ((UidHealthProto) this.instance).hasWifiRxMs();
            }

            public long getWifiRxMs() {
                return ((UidHealthProto) this.instance).getWifiRxMs();
            }

            public Builder setWifiRxMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiRxMs(value);
                return this;
            }

            public Builder clearWifiRxMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiRxMs();
                return this;
            }

            public boolean hasWifiTxMs() {
                return ((UidHealthProto) this.instance).hasWifiTxMs();
            }

            public long getWifiTxMs() {
                return ((UidHealthProto) this.instance).getWifiTxMs();
            }

            public Builder setWifiTxMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiTxMs(value);
                return this;
            }

            public Builder clearWifiTxMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiTxMs();
                return this;
            }

            public boolean hasWifiPowerMams() {
                return ((UidHealthProto) this.instance).hasWifiPowerMams();
            }

            public long getWifiPowerMams() {
                return ((UidHealthProto) this.instance).getWifiPowerMams();
            }

            public Builder setWifiPowerMams(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiPowerMams(value);
                return this;
            }

            public Builder clearWifiPowerMams() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiPowerMams();
                return this;
            }

            public boolean hasBluetoothIdleMs() {
                return ((UidHealthProto) this.instance).hasBluetoothIdleMs();
            }

            public long getBluetoothIdleMs() {
                return ((UidHealthProto) this.instance).getBluetoothIdleMs();
            }

            public Builder setBluetoothIdleMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothIdleMs(value);
                return this;
            }

            public Builder clearBluetoothIdleMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearBluetoothIdleMs();
                return this;
            }

            public boolean hasBluetoothRxMs() {
                return ((UidHealthProto) this.instance).hasBluetoothRxMs();
            }

            public long getBluetoothRxMs() {
                return ((UidHealthProto) this.instance).getBluetoothRxMs();
            }

            public Builder setBluetoothRxMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothRxMs(value);
                return this;
            }

            public Builder clearBluetoothRxMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearBluetoothRxMs();
                return this;
            }

            public boolean hasBluetoothTxMs() {
                return ((UidHealthProto) this.instance).hasBluetoothTxMs();
            }

            public long getBluetoothTxMs() {
                return ((UidHealthProto) this.instance).getBluetoothTxMs();
            }

            public Builder setBluetoothTxMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothTxMs(value);
                return this;
            }

            public Builder clearBluetoothTxMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearBluetoothTxMs();
                return this;
            }

            public boolean hasBluetoothPowerMams() {
                return ((UidHealthProto) this.instance).hasBluetoothPowerMams();
            }

            public long getBluetoothPowerMams() {
                return ((UidHealthProto) this.instance).getBluetoothPowerMams();
            }

            public Builder setBluetoothPowerMams(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothPowerMams(value);
                return this;
            }

            public Builder clearBluetoothPowerMams() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearBluetoothPowerMams();
                return this;
            }

            public boolean hasMobileIdleMs() {
                return ((UidHealthProto) this.instance).hasMobileIdleMs();
            }

            public long getMobileIdleMs() {
                return ((UidHealthProto) this.instance).getMobileIdleMs();
            }

            public Builder setMobileIdleMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobileIdleMs(value);
                return this;
            }

            public Builder clearMobileIdleMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearMobileIdleMs();
                return this;
            }

            public boolean hasMobileRxMs() {
                return ((UidHealthProto) this.instance).hasMobileRxMs();
            }

            public long getMobileRxMs() {
                return ((UidHealthProto) this.instance).getMobileRxMs();
            }

            public Builder setMobileRxMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobileRxMs(value);
                return this;
            }

            public Builder clearMobileRxMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearMobileRxMs();
                return this;
            }

            public boolean hasMobileTxMs() {
                return ((UidHealthProto) this.instance).hasMobileTxMs();
            }

            public long getMobileTxMs() {
                return ((UidHealthProto) this.instance).getMobileTxMs();
            }

            public Builder setMobileTxMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobileTxMs(value);
                return this;
            }

            public Builder clearMobileTxMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearMobileTxMs();
                return this;
            }

            public boolean hasMobilePowerMams() {
                return ((UidHealthProto) this.instance).hasMobilePowerMams();
            }

            public long getMobilePowerMams() {
                return ((UidHealthProto) this.instance).getMobilePowerMams();
            }

            public Builder setMobilePowerMams(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobilePowerMams(value);
                return this;
            }

            public Builder clearMobilePowerMams() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearMobilePowerMams();
                return this;
            }

            public boolean hasWifiRunningMs() {
                return ((UidHealthProto) this.instance).hasWifiRunningMs();
            }

            public long getWifiRunningMs() {
                return ((UidHealthProto) this.instance).getWifiRunningMs();
            }

            public Builder setWifiRunningMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiRunningMs(value);
                return this;
            }

            public Builder clearWifiRunningMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiRunningMs();
                return this;
            }

            public boolean hasWifiFullLockMs() {
                return ((UidHealthProto) this.instance).hasWifiFullLockMs();
            }

            public long getWifiFullLockMs() {
                return ((UidHealthProto) this.instance).getWifiFullLockMs();
            }

            public Builder setWifiFullLockMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiFullLockMs(value);
                return this;
            }

            public Builder clearWifiFullLockMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiFullLockMs();
                return this;
            }

            public boolean hasWifiScan() {
                return ((UidHealthProto) this.instance).hasWifiScan();
            }

            public Timer getWifiScan() {
                return ((UidHealthProto) this.instance).getWifiScan();
            }

            public Builder setWifiScan(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiScan(value);
                return this;
            }

            public Builder setWifiScan(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiScan(builderForValue);
                return this;
            }

            public Builder mergeWifiScan(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeWifiScan(value);
                return this;
            }

            public Builder clearWifiScan() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiScan();
                return this;
            }

            public boolean hasWifiMulticastMs() {
                return ((UidHealthProto) this.instance).hasWifiMulticastMs();
            }

            public long getWifiMulticastMs() {
                return ((UidHealthProto) this.instance).getWifiMulticastMs();
            }

            public Builder setWifiMulticastMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiMulticastMs(value);
                return this;
            }

            public Builder clearWifiMulticastMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiMulticastMs();
                return this;
            }

            public boolean hasAudio() {
                return ((UidHealthProto) this.instance).hasAudio();
            }

            public Timer getAudio() {
                return ((UidHealthProto) this.instance).getAudio();
            }

            public Builder setAudio(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setAudio(value);
                return this;
            }

            public Builder setAudio(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setAudio(builderForValue);
                return this;
            }

            public Builder mergeAudio(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeAudio(value);
                return this;
            }

            public Builder clearAudio() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearAudio();
                return this;
            }

            public boolean hasVideo() {
                return ((UidHealthProto) this.instance).hasVideo();
            }

            public Timer getVideo() {
                return ((UidHealthProto) this.instance).getVideo();
            }

            public Builder setVideo(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setVideo(value);
                return this;
            }

            public Builder setVideo(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setVideo(builderForValue);
                return this;
            }

            public Builder mergeVideo(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeVideo(value);
                return this;
            }

            public Builder clearVideo() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearVideo();
                return this;
            }

            public boolean hasFlashlight() {
                return ((UidHealthProto) this.instance).hasFlashlight();
            }

            public Timer getFlashlight() {
                return ((UidHealthProto) this.instance).getFlashlight();
            }

            public Builder setFlashlight(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setFlashlight(value);
                return this;
            }

            public Builder setFlashlight(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setFlashlight(builderForValue);
                return this;
            }

            public Builder mergeFlashlight(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeFlashlight(value);
                return this;
            }

            public Builder clearFlashlight() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearFlashlight();
                return this;
            }

            public boolean hasCamera() {
                return ((UidHealthProto) this.instance).hasCamera();
            }

            public Timer getCamera() {
                return ((UidHealthProto) this.instance).getCamera();
            }

            public Builder setCamera(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setCamera(value);
                return this;
            }

            public Builder setCamera(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setCamera(builderForValue);
                return this;
            }

            public Builder mergeCamera(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeCamera(value);
                return this;
            }

            public Builder clearCamera() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearCamera();
                return this;
            }

            public boolean hasForegroundActivity() {
                return ((UidHealthProto) this.instance).hasForegroundActivity();
            }

            public Timer getForegroundActivity() {
                return ((UidHealthProto) this.instance).getForegroundActivity();
            }

            public Builder setForegroundActivity(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setForegroundActivity(value);
                return this;
            }

            public Builder setForegroundActivity(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setForegroundActivity(builderForValue);
                return this;
            }

            public Builder mergeForegroundActivity(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeForegroundActivity(value);
                return this;
            }

            public Builder clearForegroundActivity() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearForegroundActivity();
                return this;
            }

            public boolean hasBluetoothScan() {
                return ((UidHealthProto) this.instance).hasBluetoothScan();
            }

            public Timer getBluetoothScan() {
                return ((UidHealthProto) this.instance).getBluetoothScan();
            }

            public Builder setBluetoothScan(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothScan(value);
                return this;
            }

            public Builder setBluetoothScan(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothScan(builderForValue);
                return this;
            }

            public Builder mergeBluetoothScan(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeBluetoothScan(value);
                return this;
            }

            public Builder clearBluetoothScan() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearBluetoothScan();
                return this;
            }

            public boolean hasProcessStateTopMs() {
                return ((UidHealthProto) this.instance).hasProcessStateTopMs();
            }

            public Timer getProcessStateTopMs() {
                return ((UidHealthProto) this.instance).getProcessStateTopMs();
            }

            public Builder setProcessStateTopMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateTopMs(value);
                return this;
            }

            public Builder setProcessStateTopMs(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateTopMs(builderForValue);
                return this;
            }

            public Builder mergeProcessStateTopMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeProcessStateTopMs(value);
                return this;
            }

            public Builder clearProcessStateTopMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearProcessStateTopMs();
                return this;
            }

            public boolean hasProcessStateForegroundServiceMs() {
                return ((UidHealthProto) this.instance).hasProcessStateForegroundServiceMs();
            }

            public Timer getProcessStateForegroundServiceMs() {
                return ((UidHealthProto) this.instance).getProcessStateForegroundServiceMs();
            }

            public Builder setProcessStateForegroundServiceMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateForegroundServiceMs(value);
                return this;
            }

            public Builder setProcessStateForegroundServiceMs(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateForegroundServiceMs(builderForValue);
                return this;
            }

            public Builder mergeProcessStateForegroundServiceMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeProcessStateForegroundServiceMs(value);
                return this;
            }

            public Builder clearProcessStateForegroundServiceMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearProcessStateForegroundServiceMs();
                return this;
            }

            public boolean hasProcessStateTopSleepingMs() {
                return ((UidHealthProto) this.instance).hasProcessStateTopSleepingMs();
            }

            public Timer getProcessStateTopSleepingMs() {
                return ((UidHealthProto) this.instance).getProcessStateTopSleepingMs();
            }

            public Builder setProcessStateTopSleepingMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateTopSleepingMs(value);
                return this;
            }

            public Builder setProcessStateTopSleepingMs(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateTopSleepingMs(builderForValue);
                return this;
            }

            public Builder mergeProcessStateTopSleepingMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeProcessStateTopSleepingMs(value);
                return this;
            }

            public Builder clearProcessStateTopSleepingMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearProcessStateTopSleepingMs();
                return this;
            }

            public boolean hasProcessStateForegroundMs() {
                return ((UidHealthProto) this.instance).hasProcessStateForegroundMs();
            }

            public Timer getProcessStateForegroundMs() {
                return ((UidHealthProto) this.instance).getProcessStateForegroundMs();
            }

            public Builder setProcessStateForegroundMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateForegroundMs(value);
                return this;
            }

            public Builder setProcessStateForegroundMs(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateForegroundMs(builderForValue);
                return this;
            }

            public Builder mergeProcessStateForegroundMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeProcessStateForegroundMs(value);
                return this;
            }

            public Builder clearProcessStateForegroundMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearProcessStateForegroundMs();
                return this;
            }

            public boolean hasProcessStateBackgroundMs() {
                return ((UidHealthProto) this.instance).hasProcessStateBackgroundMs();
            }

            public Timer getProcessStateBackgroundMs() {
                return ((UidHealthProto) this.instance).getProcessStateBackgroundMs();
            }

            public Builder setProcessStateBackgroundMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateBackgroundMs(value);
                return this;
            }

            public Builder setProcessStateBackgroundMs(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateBackgroundMs(builderForValue);
                return this;
            }

            public Builder mergeProcessStateBackgroundMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeProcessStateBackgroundMs(value);
                return this;
            }

            public Builder clearProcessStateBackgroundMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearProcessStateBackgroundMs();
                return this;
            }

            public boolean hasProcessStateCachedMs() {
                return ((UidHealthProto) this.instance).hasProcessStateCachedMs();
            }

            public Timer getProcessStateCachedMs() {
                return ((UidHealthProto) this.instance).getProcessStateCachedMs();
            }

            public Builder setProcessStateCachedMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateCachedMs(value);
                return this;
            }

            public Builder setProcessStateCachedMs(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setProcessStateCachedMs(builderForValue);
                return this;
            }

            public Builder mergeProcessStateCachedMs(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeProcessStateCachedMs(value);
                return this;
            }

            public Builder clearProcessStateCachedMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearProcessStateCachedMs();
                return this;
            }

            public boolean hasVibrator() {
                return ((UidHealthProto) this.instance).hasVibrator();
            }

            public Timer getVibrator() {
                return ((UidHealthProto) this.instance).getVibrator();
            }

            public Builder setVibrator(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setVibrator(value);
                return this;
            }

            public Builder setVibrator(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setVibrator(builderForValue);
                return this;
            }

            public Builder mergeVibrator(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeVibrator(value);
                return this;
            }

            public Builder clearVibrator() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearVibrator();
                return this;
            }

            public boolean hasOtherUserActivityCount() {
                return ((UidHealthProto) this.instance).hasOtherUserActivityCount();
            }

            public long getOtherUserActivityCount() {
                return ((UidHealthProto) this.instance).getOtherUserActivityCount();
            }

            public Builder setOtherUserActivityCount(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setOtherUserActivityCount(value);
                return this;
            }

            public Builder clearOtherUserActivityCount() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearOtherUserActivityCount();
                return this;
            }

            public boolean hasButtonUserActivityCount() {
                return ((UidHealthProto) this.instance).hasButtonUserActivityCount();
            }

            public long getButtonUserActivityCount() {
                return ((UidHealthProto) this.instance).getButtonUserActivityCount();
            }

            public Builder setButtonUserActivityCount(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setButtonUserActivityCount(value);
                return this;
            }

            public Builder clearButtonUserActivityCount() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearButtonUserActivityCount();
                return this;
            }

            public boolean hasTouchUserActivityCount() {
                return ((UidHealthProto) this.instance).hasTouchUserActivityCount();
            }

            public long getTouchUserActivityCount() {
                return ((UidHealthProto) this.instance).getTouchUserActivityCount();
            }

            public Builder setTouchUserActivityCount(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setTouchUserActivityCount(value);
                return this;
            }

            public Builder clearTouchUserActivityCount() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearTouchUserActivityCount();
                return this;
            }

            public boolean hasMobileRxBytes() {
                return ((UidHealthProto) this.instance).hasMobileRxBytes();
            }

            public long getMobileRxBytes() {
                return ((UidHealthProto) this.instance).getMobileRxBytes();
            }

            public Builder setMobileRxBytes(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobileRxBytes(value);
                return this;
            }

            public Builder clearMobileRxBytes() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearMobileRxBytes();
                return this;
            }

            public boolean hasMobileTxBytes() {
                return ((UidHealthProto) this.instance).hasMobileTxBytes();
            }

            public long getMobileTxBytes() {
                return ((UidHealthProto) this.instance).getMobileTxBytes();
            }

            public Builder setMobileTxBytes(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobileTxBytes(value);
                return this;
            }

            public Builder clearMobileTxBytes() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearMobileTxBytes();
                return this;
            }

            public boolean hasWifiRxBytes() {
                return ((UidHealthProto) this.instance).hasWifiRxBytes();
            }

            public long getWifiRxBytes() {
                return ((UidHealthProto) this.instance).getWifiRxBytes();
            }

            public Builder setWifiRxBytes(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiRxBytes(value);
                return this;
            }

            public Builder clearWifiRxBytes() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiRxBytes();
                return this;
            }

            public boolean hasWifiTxBytes() {
                return ((UidHealthProto) this.instance).hasWifiTxBytes();
            }

            public long getWifiTxBytes() {
                return ((UidHealthProto) this.instance).getWifiTxBytes();
            }

            public Builder setWifiTxBytes(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiTxBytes(value);
                return this;
            }

            public Builder clearWifiTxBytes() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiTxBytes();
                return this;
            }

            public boolean hasBluetoothRxBytes() {
                return ((UidHealthProto) this.instance).hasBluetoothRxBytes();
            }

            public long getBluetoothRxBytes() {
                return ((UidHealthProto) this.instance).getBluetoothRxBytes();
            }

            public Builder setBluetoothRxBytes(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothRxBytes(value);
                return this;
            }

            public Builder clearBluetoothRxBytes() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearBluetoothRxBytes();
                return this;
            }

            public boolean hasBluetoothTxBytes() {
                return ((UidHealthProto) this.instance).hasBluetoothTxBytes();
            }

            public long getBluetoothTxBytes() {
                return ((UidHealthProto) this.instance).getBluetoothTxBytes();
            }

            public Builder setBluetoothTxBytes(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothTxBytes(value);
                return this;
            }

            public Builder clearBluetoothTxBytes() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearBluetoothTxBytes();
                return this;
            }

            public boolean hasMobileRxPackets() {
                return ((UidHealthProto) this.instance).hasMobileRxPackets();
            }

            public long getMobileRxPackets() {
                return ((UidHealthProto) this.instance).getMobileRxPackets();
            }

            public Builder setMobileRxPackets(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobileRxPackets(value);
                return this;
            }

            public Builder clearMobileRxPackets() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearMobileRxPackets();
                return this;
            }

            public boolean hasMobileTxPackets() {
                return ((UidHealthProto) this.instance).hasMobileTxPackets();
            }

            public long getMobileTxPackets() {
                return ((UidHealthProto) this.instance).getMobileTxPackets();
            }

            public Builder setMobileTxPackets(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobileTxPackets(value);
                return this;
            }

            public Builder clearMobileTxPackets() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearMobileTxPackets();
                return this;
            }

            public boolean hasWifiRxPackets() {
                return ((UidHealthProto) this.instance).hasWifiRxPackets();
            }

            public long getWifiRxPackets() {
                return ((UidHealthProto) this.instance).getWifiRxPackets();
            }

            public Builder setWifiRxPackets(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiRxPackets(value);
                return this;
            }

            public Builder clearWifiRxPackets() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiRxPackets();
                return this;
            }

            public boolean hasWifiTxPackets() {
                return ((UidHealthProto) this.instance).hasWifiTxPackets();
            }

            public long getWifiTxPackets() {
                return ((UidHealthProto) this.instance).getWifiTxPackets();
            }

            public Builder setWifiTxPackets(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setWifiTxPackets(value);
                return this;
            }

            public Builder clearWifiTxPackets() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearWifiTxPackets();
                return this;
            }

            public boolean hasBluetoothRxPackets() {
                return ((UidHealthProto) this.instance).hasBluetoothRxPackets();
            }

            public long getBluetoothRxPackets() {
                return ((UidHealthProto) this.instance).getBluetoothRxPackets();
            }

            public Builder setBluetoothRxPackets(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothRxPackets(value);
                return this;
            }

            public Builder clearBluetoothRxPackets() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearBluetoothRxPackets();
                return this;
            }

            public boolean hasBluetoothTxPackets() {
                return ((UidHealthProto) this.instance).hasBluetoothTxPackets();
            }

            public long getBluetoothTxPackets() {
                return ((UidHealthProto) this.instance).getBluetoothTxPackets();
            }

            public Builder setBluetoothTxPackets(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setBluetoothTxPackets(value);
                return this;
            }

            public Builder clearBluetoothTxPackets() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearBluetoothTxPackets();
                return this;
            }

            public boolean hasMobileRadioActive() {
                return ((UidHealthProto) this.instance).hasMobileRadioActive();
            }

            public Timer getMobileRadioActive() {
                return ((UidHealthProto) this.instance).getMobileRadioActive();
            }

            public Builder setMobileRadioActive(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobileRadioActive(value);
                return this;
            }

            public Builder setMobileRadioActive(Timer.Builder builderForValue) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setMobileRadioActive(builderForValue);
                return this;
            }

            public Builder mergeMobileRadioActive(Timer value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).mergeMobileRadioActive(value);
                return this;
            }

            public Builder clearMobileRadioActive() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearMobileRadioActive();
                return this;
            }

            public boolean hasUserCpuTimeMs() {
                return ((UidHealthProto) this.instance).hasUserCpuTimeMs();
            }

            public long getUserCpuTimeMs() {
                return ((UidHealthProto) this.instance).getUserCpuTimeMs();
            }

            public Builder setUserCpuTimeMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setUserCpuTimeMs(value);
                return this;
            }

            public Builder clearUserCpuTimeMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearUserCpuTimeMs();
                return this;
            }

            public boolean hasSystemCpuTimeMs() {
                return ((UidHealthProto) this.instance).hasSystemCpuTimeMs();
            }

            public long getSystemCpuTimeMs() {
                return ((UidHealthProto) this.instance).getSystemCpuTimeMs();
            }

            public Builder setSystemCpuTimeMs(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setSystemCpuTimeMs(value);
                return this;
            }

            public Builder clearSystemCpuTimeMs() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearSystemCpuTimeMs();
                return this;
            }

            public boolean hasCpuPowerMams() {
                return ((UidHealthProto) this.instance).hasCpuPowerMams();
            }

            public long getCpuPowerMams() {
                return ((UidHealthProto) this.instance).getCpuPowerMams();
            }

            public Builder setCpuPowerMams(long value) {
                copyOnWrite();
                ((UidHealthProto) this.instance).setCpuPowerMams(value);
                return this;
            }

            public Builder clearCpuPowerMams() {
                copyOnWrite();
                ((UidHealthProto) this.instance).clearCpuPowerMams();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PidHealthProto extends GeneratedMessageLite<PidHealthProto, Builder> implements PidHealthProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final PidHealthProto DEFAULT_INSTANCE = new PidHealthProto();
        private static volatile Parser<PidHealthProto> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(PidHealthProto.class, DEFAULT_INSTANCE);
        }

        private PidHealthProto() {
        }

        public static PidHealthProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PidHealthProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PidHealthProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PidHealthProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PidHealthProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PidHealthProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PidHealthProto parseFrom(InputStream input) throws IOException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PidHealthProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PidHealthProto parseDelimitedFrom(InputStream input) throws IOException {
            return (PidHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PidHealthProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PidHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PidHealthProto parseFrom(CodedInputStream input) throws IOException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PidHealthProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PidHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PidHealthProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static PidHealthProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PidHealthProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PidHealthProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0000", null);
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PidHealthProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (PidHealthProto.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<PidHealthProto, Builder> implements PidHealthProtoOrBuilder {
            private Builder() {
                super(PidHealthProto.DEFAULT_INSTANCE);
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ProcessHealthProto extends GeneratedMessageLite<ProcessHealthProto, Builder> implements ProcessHealthProtoOrBuilder {
        public static final int ANR_COUNT_FIELD_NUMBER = 5;
        public static final int CRASHES_COUNT_FIELD_NUMBER = 4;
        /* access modifiers changed from: private */
        public static final ProcessHealthProto DEFAULT_INSTANCE = new ProcessHealthProto();
        public static final int FOREGROUND_MS_FIELD_NUMBER = 6;
        public static final int NAME_FIELD_NUMBER = 7;
        public static final int STARTS_COUNT_FIELD_NUMBER = 3;
        public static final int SYSTEM_TIME_MS_FIELD_NUMBER = 2;
        public static final int USER_TIME_MS_FIELD_NUMBER = 1;
        private static volatile Parser<ProcessHealthProto> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(ProcessHealthProto.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private long anrCount_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private long crashesCount_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private long foregroundMs_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private HashedString name_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long startsCount_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long systemTimeMs_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long userTimeMs_;

        private ProcessHealthProto() {
        }

        public static ProcessHealthProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ProcessHealthProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ProcessHealthProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ProcessHealthProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ProcessHealthProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ProcessHealthProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ProcessHealthProto parseFrom(InputStream input) throws IOException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ProcessHealthProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ProcessHealthProto parseDelimitedFrom(InputStream input) throws IOException {
            return (ProcessHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ProcessHealthProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProcessHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ProcessHealthProto parseFrom(CodedInputStream input) throws IOException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ProcessHealthProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProcessHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ProcessHealthProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static ProcessHealthProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ProcessHealthProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasUserTimeMs() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getUserTimeMs() {
            return this.userTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setUserTimeMs(long value) {
            this.bitField0_ |= 1;
            this.userTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearUserTimeMs() {
            this.bitField0_ &= -2;
            this.userTimeMs_ = 0;
        }

        public boolean hasSystemTimeMs() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getSystemTimeMs() {
            return this.systemTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setSystemTimeMs(long value) {
            this.bitField0_ |= 2;
            this.systemTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSystemTimeMs() {
            this.bitField0_ &= -3;
            this.systemTimeMs_ = 0;
        }

        public boolean hasStartsCount() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getStartsCount() {
            return this.startsCount_;
        }

        /* access modifiers changed from: private */
        public void setStartsCount(long value) {
            this.bitField0_ |= 4;
            this.startsCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStartsCount() {
            this.bitField0_ &= -5;
            this.startsCount_ = 0;
        }

        public boolean hasCrashesCount() {
            return (this.bitField0_ & 8) != 0;
        }

        public long getCrashesCount() {
            return this.crashesCount_;
        }

        /* access modifiers changed from: private */
        public void setCrashesCount(long value) {
            this.bitField0_ |= 8;
            this.crashesCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCrashesCount() {
            this.bitField0_ &= -9;
            this.crashesCount_ = 0;
        }

        public boolean hasAnrCount() {
            return (this.bitField0_ & 16) != 0;
        }

        public long getAnrCount() {
            return this.anrCount_;
        }

        /* access modifiers changed from: private */
        public void setAnrCount(long value) {
            this.bitField0_ |= 16;
            this.anrCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAnrCount() {
            this.bitField0_ &= -17;
            this.anrCount_ = 0;
        }

        public boolean hasForegroundMs() {
            return (this.bitField0_ & 32) != 0;
        }

        public long getForegroundMs() {
            return this.foregroundMs_;
        }

        /* access modifiers changed from: private */
        public void setForegroundMs(long value) {
            this.bitField0_ |= 32;
            this.foregroundMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearForegroundMs() {
            this.bitField0_ &= -33;
            this.foregroundMs_ = 0;
        }

        public boolean hasName() {
            return (this.bitField0_ & 64) != 0;
        }

        public HashedString getName() {
            HashedString hashedString = this.name_;
            return hashedString == null ? HashedString.getDefaultInstance() : hashedString;
        }

        /* access modifiers changed from: private */
        public void setName(HashedString value) {
            if (value != null) {
                this.name_ = value;
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setName(HashedString.Builder builderForValue) {
            this.name_ = (HashedString) builderForValue.build();
            this.bitField0_ |= 64;
        }

        /* access modifiers changed from: private */
        public void mergeName(HashedString value) {
            if (value != null) {
                HashedString hashedString = this.name_;
                if (hashedString == null || hashedString == HashedString.getDefaultInstance()) {
                    this.name_ = value;
                } else {
                    this.name_ = (HashedString) ((HashedString.Builder) HashedString.newBuilder(this.name_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = null;
            this.bitField0_ &= -65;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ProcessHealthProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001\u0002\u0000\u0002\u0002\u0001\u0003\u0002\u0002\u0004\u0002\u0003\u0005\u0002\u0004\u0006\u0002\u0005\u0007\t\u0006", new Object[]{"bitField0_", "userTimeMs_", "systemTimeMs_", "startsCount_", "crashesCount_", "anrCount_", "foregroundMs_", "name_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ProcessHealthProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (ProcessHealthProto.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<ProcessHealthProto, Builder> implements ProcessHealthProtoOrBuilder {
            private Builder() {
                super(ProcessHealthProto.DEFAULT_INSTANCE);
            }

            public boolean hasUserTimeMs() {
                return ((ProcessHealthProto) this.instance).hasUserTimeMs();
            }

            public long getUserTimeMs() {
                return ((ProcessHealthProto) this.instance).getUserTimeMs();
            }

            public Builder setUserTimeMs(long value) {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).setUserTimeMs(value);
                return this;
            }

            public Builder clearUserTimeMs() {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).clearUserTimeMs();
                return this;
            }

            public boolean hasSystemTimeMs() {
                return ((ProcessHealthProto) this.instance).hasSystemTimeMs();
            }

            public long getSystemTimeMs() {
                return ((ProcessHealthProto) this.instance).getSystemTimeMs();
            }

            public Builder setSystemTimeMs(long value) {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).setSystemTimeMs(value);
                return this;
            }

            public Builder clearSystemTimeMs() {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).clearSystemTimeMs();
                return this;
            }

            public boolean hasStartsCount() {
                return ((ProcessHealthProto) this.instance).hasStartsCount();
            }

            public long getStartsCount() {
                return ((ProcessHealthProto) this.instance).getStartsCount();
            }

            public Builder setStartsCount(long value) {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).setStartsCount(value);
                return this;
            }

            public Builder clearStartsCount() {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).clearStartsCount();
                return this;
            }

            public boolean hasCrashesCount() {
                return ((ProcessHealthProto) this.instance).hasCrashesCount();
            }

            public long getCrashesCount() {
                return ((ProcessHealthProto) this.instance).getCrashesCount();
            }

            public Builder setCrashesCount(long value) {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).setCrashesCount(value);
                return this;
            }

            public Builder clearCrashesCount() {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).clearCrashesCount();
                return this;
            }

            public boolean hasAnrCount() {
                return ((ProcessHealthProto) this.instance).hasAnrCount();
            }

            public long getAnrCount() {
                return ((ProcessHealthProto) this.instance).getAnrCount();
            }

            public Builder setAnrCount(long value) {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).setAnrCount(value);
                return this;
            }

            public Builder clearAnrCount() {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).clearAnrCount();
                return this;
            }

            public boolean hasForegroundMs() {
                return ((ProcessHealthProto) this.instance).hasForegroundMs();
            }

            public long getForegroundMs() {
                return ((ProcessHealthProto) this.instance).getForegroundMs();
            }

            public Builder setForegroundMs(long value) {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).setForegroundMs(value);
                return this;
            }

            public Builder clearForegroundMs() {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).clearForegroundMs();
                return this;
            }

            public boolean hasName() {
                return ((ProcessHealthProto) this.instance).hasName();
            }

            public HashedString getName() {
                return ((ProcessHealthProto) this.instance).getName();
            }

            public Builder setName(HashedString value) {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).setName(value);
                return this;
            }

            public Builder setName(HashedString.Builder builderForValue) {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).setName(builderForValue);
                return this;
            }

            public Builder mergeName(HashedString value) {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).mergeName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((ProcessHealthProto) this.instance).clearName();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PackageHealthProto extends GeneratedMessageLite<PackageHealthProto, Builder> implements PackageHealthProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final PackageHealthProto DEFAULT_INSTANCE = new PackageHealthProto();
        public static final int NAME_FIELD_NUMBER = 3;
        public static final int STATS_SERVICES_FIELD_NUMBER = 1;
        public static final int WAKEUP_ALARMS_COUNT_FIELD_NUMBER = 2;
        private static volatile Parser<PackageHealthProto> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(PackageHealthProto.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private HashedString name_;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ServiceHealthProto> statsServices_ = emptyProtobufList();
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Counter> wakeupAlarmsCount_ = emptyProtobufList();

        private PackageHealthProto() {
        }

        public static PackageHealthProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PackageHealthProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PackageHealthProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PackageHealthProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PackageHealthProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PackageHealthProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PackageHealthProto parseFrom(InputStream input) throws IOException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PackageHealthProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PackageHealthProto parseDelimitedFrom(InputStream input) throws IOException {
            return (PackageHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PackageHealthProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PackageHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PackageHealthProto parseFrom(CodedInputStream input) throws IOException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PackageHealthProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PackageHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PackageHealthProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static PackageHealthProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PackageHealthProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public List<ServiceHealthProto> getStatsServicesList() {
            return this.statsServices_;
        }

        public List<? extends ServiceHealthProtoOrBuilder> getStatsServicesOrBuilderList() {
            return this.statsServices_;
        }

        public int getStatsServicesCount() {
            return this.statsServices_.size();
        }

        public ServiceHealthProto getStatsServices(int index) {
            return this.statsServices_.get(index);
        }

        public ServiceHealthProtoOrBuilder getStatsServicesOrBuilder(int index) {
            return this.statsServices_.get(index);
        }

        private void ensureStatsServicesIsMutable() {
            if (!this.statsServices_.isModifiable()) {
                this.statsServices_ = GeneratedMessageLite.mutableCopy(this.statsServices_);
            }
        }

        /* access modifiers changed from: private */
        public void setStatsServices(int index, ServiceHealthProto value) {
            if (value != null) {
                ensureStatsServicesIsMutable();
                this.statsServices_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setStatsServices(int index, ServiceHealthProto.Builder builderForValue) {
            ensureStatsServicesIsMutable();
            this.statsServices_.set(index, (ServiceHealthProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStatsServices(ServiceHealthProto value) {
            if (value != null) {
                ensureStatsServicesIsMutable();
                this.statsServices_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStatsServices(int index, ServiceHealthProto value) {
            if (value != null) {
                ensureStatsServicesIsMutable();
                this.statsServices_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStatsServices(ServiceHealthProto.Builder builderForValue) {
            ensureStatsServicesIsMutable();
            this.statsServices_.add((ServiceHealthProto) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStatsServices(int index, ServiceHealthProto.Builder builderForValue) {
            ensureStatsServicesIsMutable();
            this.statsServices_.add(index, (ServiceHealthProto) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$ServiceHealthProto>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$ServiceHealthProto>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllStatsServices(Iterable<? extends ServiceHealthProto> values) {
            ensureStatsServicesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.statsServices_);
        }

        /* access modifiers changed from: private */
        public void clearStatsServices() {
            this.statsServices_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeStatsServices(int index) {
            ensureStatsServicesIsMutable();
            this.statsServices_.remove(index);
        }

        public List<Counter> getWakeupAlarmsCountList() {
            return this.wakeupAlarmsCount_;
        }

        public List<? extends CounterOrBuilder> getWakeupAlarmsCountOrBuilderList() {
            return this.wakeupAlarmsCount_;
        }

        public int getWakeupAlarmsCountCount() {
            return this.wakeupAlarmsCount_.size();
        }

        public Counter getWakeupAlarmsCount(int index) {
            return this.wakeupAlarmsCount_.get(index);
        }

        public CounterOrBuilder getWakeupAlarmsCountOrBuilder(int index) {
            return this.wakeupAlarmsCount_.get(index);
        }

        private void ensureWakeupAlarmsCountIsMutable() {
            if (!this.wakeupAlarmsCount_.isModifiable()) {
                this.wakeupAlarmsCount_ = GeneratedMessageLite.mutableCopy(this.wakeupAlarmsCount_);
            }
        }

        /* access modifiers changed from: private */
        public void setWakeupAlarmsCount(int index, Counter value) {
            if (value != null) {
                ensureWakeupAlarmsCountIsMutable();
                this.wakeupAlarmsCount_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setWakeupAlarmsCount(int index, Counter.Builder builderForValue) {
            ensureWakeupAlarmsCountIsMutable();
            this.wakeupAlarmsCount_.set(index, (Counter) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakeupAlarmsCount(Counter value) {
            if (value != null) {
                ensureWakeupAlarmsCountIsMutable();
                this.wakeupAlarmsCount_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakeupAlarmsCount(int index, Counter value) {
            if (value != null) {
                ensureWakeupAlarmsCountIsMutable();
                this.wakeupAlarmsCount_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addWakeupAlarmsCount(Counter.Builder builderForValue) {
            ensureWakeupAlarmsCountIsMutable();
            this.wakeupAlarmsCount_.add((Counter) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addWakeupAlarmsCount(int index, Counter.Builder builderForValue) {
            ensureWakeupAlarmsCountIsMutable();
            this.wakeupAlarmsCount_.add(index, (Counter) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.BatteryMetric$Counter>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.BatteryMetric$Counter>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllWakeupAlarmsCount(Iterable<? extends Counter> values) {
            ensureWakeupAlarmsCountIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.wakeupAlarmsCount_);
        }

        /* access modifiers changed from: private */
        public void clearWakeupAlarmsCount() {
            this.wakeupAlarmsCount_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeWakeupAlarmsCount(int index) {
            ensureWakeupAlarmsCountIsMutable();
            this.wakeupAlarmsCount_.remove(index);
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public HashedString getName() {
            HashedString hashedString = this.name_;
            return hashedString == null ? HashedString.getDefaultInstance() : hashedString;
        }

        /* access modifiers changed from: private */
        public void setName(HashedString value) {
            if (value != null) {
                this.name_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setName(HashedString.Builder builderForValue) {
            this.name_ = (HashedString) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeName(HashedString value) {
            if (value != null) {
                HashedString hashedString = this.name_;
                if (hashedString == null || hashedString == HashedString.getDefaultInstance()) {
                    this.name_ = value;
                } else {
                    this.name_ = (HashedString) ((HashedString.Builder) HashedString.newBuilder(this.name_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = null;
            this.bitField0_ &= -2;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PackageHealthProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0002\u0000\u0001\u001b\u0002\u001b\u0003\t\u0000", new Object[]{"bitField0_", "statsServices_", ServiceHealthProto.class, "wakeupAlarmsCount_", Counter.class, "name_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PackageHealthProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (PackageHealthProto.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<PackageHealthProto, Builder> implements PackageHealthProtoOrBuilder {
            private Builder() {
                super(PackageHealthProto.DEFAULT_INSTANCE);
            }

            public List<ServiceHealthProto> getStatsServicesList() {
                return Collections.unmodifiableList(((PackageHealthProto) this.instance).getStatsServicesList());
            }

            public int getStatsServicesCount() {
                return ((PackageHealthProto) this.instance).getStatsServicesCount();
            }

            public ServiceHealthProto getStatsServices(int index) {
                return ((PackageHealthProto) this.instance).getStatsServices(index);
            }

            public Builder setStatsServices(int index, ServiceHealthProto value) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).setStatsServices(index, value);
                return this;
            }

            public Builder setStatsServices(int index, ServiceHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).setStatsServices(index, builderForValue);
                return this;
            }

            public Builder addStatsServices(ServiceHealthProto value) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addStatsServices(value);
                return this;
            }

            public Builder addStatsServices(int index, ServiceHealthProto value) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addStatsServices(index, value);
                return this;
            }

            public Builder addStatsServices(ServiceHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addStatsServices(builderForValue);
                return this;
            }

            public Builder addStatsServices(int index, ServiceHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addStatsServices(index, builderForValue);
                return this;
            }

            public Builder addAllStatsServices(Iterable<? extends ServiceHealthProto> values) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addAllStatsServices(values);
                return this;
            }

            public Builder clearStatsServices() {
                copyOnWrite();
                ((PackageHealthProto) this.instance).clearStatsServices();
                return this;
            }

            public Builder removeStatsServices(int index) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).removeStatsServices(index);
                return this;
            }

            public List<Counter> getWakeupAlarmsCountList() {
                return Collections.unmodifiableList(((PackageHealthProto) this.instance).getWakeupAlarmsCountList());
            }

            public int getWakeupAlarmsCountCount() {
                return ((PackageHealthProto) this.instance).getWakeupAlarmsCountCount();
            }

            public Counter getWakeupAlarmsCount(int index) {
                return ((PackageHealthProto) this.instance).getWakeupAlarmsCount(index);
            }

            public Builder setWakeupAlarmsCount(int index, Counter value) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).setWakeupAlarmsCount(index, value);
                return this;
            }

            public Builder setWakeupAlarmsCount(int index, Counter.Builder builderForValue) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).setWakeupAlarmsCount(index, builderForValue);
                return this;
            }

            public Builder addWakeupAlarmsCount(Counter value) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addWakeupAlarmsCount(value);
                return this;
            }

            public Builder addWakeupAlarmsCount(int index, Counter value) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addWakeupAlarmsCount(index, value);
                return this;
            }

            public Builder addWakeupAlarmsCount(Counter.Builder builderForValue) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addWakeupAlarmsCount(builderForValue);
                return this;
            }

            public Builder addWakeupAlarmsCount(int index, Counter.Builder builderForValue) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addWakeupAlarmsCount(index, builderForValue);
                return this;
            }

            public Builder addAllWakeupAlarmsCount(Iterable<? extends Counter> values) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).addAllWakeupAlarmsCount(values);
                return this;
            }

            public Builder clearWakeupAlarmsCount() {
                copyOnWrite();
                ((PackageHealthProto) this.instance).clearWakeupAlarmsCount();
                return this;
            }

            public Builder removeWakeupAlarmsCount(int index) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).removeWakeupAlarmsCount(index);
                return this;
            }

            public boolean hasName() {
                return ((PackageHealthProto) this.instance).hasName();
            }

            public HashedString getName() {
                return ((PackageHealthProto) this.instance).getName();
            }

            public Builder setName(HashedString value) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).setName(value);
                return this;
            }

            public Builder setName(HashedString.Builder builderForValue) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).setName(builderForValue);
                return this;
            }

            public Builder mergeName(HashedString value) {
                copyOnWrite();
                ((PackageHealthProto) this.instance).mergeName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((PackageHealthProto) this.instance).clearName();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ServiceHealthProto extends GeneratedMessageLite<ServiceHealthProto, Builder> implements ServiceHealthProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final ServiceHealthProto DEFAULT_INSTANCE = new ServiceHealthProto();
        public static final int LAUNCH_COUNT_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 3;
        public static final int START_SERVICE_COUNT_FIELD_NUMBER = 1;
        private static volatile Parser<ServiceHealthProto> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(ServiceHealthProto.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int launchCount_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private HashedString name_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int startServiceCount_;

        private ServiceHealthProto() {
        }

        public static ServiceHealthProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceHealthProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceHealthProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceHealthProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceHealthProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceHealthProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceHealthProto parseFrom(InputStream input) throws IOException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceHealthProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceHealthProto parseDelimitedFrom(InputStream input) throws IOException {
            return (ServiceHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceHealthProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceHealthProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceHealthProto parseFrom(CodedInputStream input) throws IOException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceHealthProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceHealthProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ServiceHealthProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static ServiceHealthProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ServiceHealthProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasStartServiceCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getStartServiceCount() {
            return this.startServiceCount_;
        }

        /* access modifiers changed from: private */
        public void setStartServiceCount(int value) {
            this.bitField0_ |= 1;
            this.startServiceCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStartServiceCount() {
            this.bitField0_ &= -2;
            this.startServiceCount_ = 0;
        }

        public boolean hasLaunchCount() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getLaunchCount() {
            return this.launchCount_;
        }

        /* access modifiers changed from: private */
        public void setLaunchCount(int value) {
            this.bitField0_ |= 2;
            this.launchCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLaunchCount() {
            this.bitField0_ &= -3;
            this.launchCount_ = 0;
        }

        public boolean hasName() {
            return (this.bitField0_ & 4) != 0;
        }

        public HashedString getName() {
            HashedString hashedString = this.name_;
            return hashedString == null ? HashedString.getDefaultInstance() : hashedString;
        }

        /* access modifiers changed from: private */
        public void setName(HashedString value) {
            if (value != null) {
                this.name_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setName(HashedString.Builder builderForValue) {
            this.name_ = (HashedString) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeName(HashedString value) {
            if (value != null) {
                HashedString hashedString = this.name_;
                if (hashedString == null || hashedString == HashedString.getDefaultInstance()) {
                    this.name_ = value;
                } else {
                    this.name_ = (HashedString) ((HashedString.Builder) HashedString.newBuilder(this.name_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = null;
            this.bitField0_ &= -5;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ServiceHealthProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\t\u0002", new Object[]{"bitField0_", "startServiceCount_", "launchCount_", "name_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ServiceHealthProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (ServiceHealthProto.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<ServiceHealthProto, Builder> implements ServiceHealthProtoOrBuilder {
            private Builder() {
                super(ServiceHealthProto.DEFAULT_INSTANCE);
            }

            public boolean hasStartServiceCount() {
                return ((ServiceHealthProto) this.instance).hasStartServiceCount();
            }

            public int getStartServiceCount() {
                return ((ServiceHealthProto) this.instance).getStartServiceCount();
            }

            public Builder setStartServiceCount(int value) {
                copyOnWrite();
                ((ServiceHealthProto) this.instance).setStartServiceCount(value);
                return this;
            }

            public Builder clearStartServiceCount() {
                copyOnWrite();
                ((ServiceHealthProto) this.instance).clearStartServiceCount();
                return this;
            }

            public boolean hasLaunchCount() {
                return ((ServiceHealthProto) this.instance).hasLaunchCount();
            }

            public int getLaunchCount() {
                return ((ServiceHealthProto) this.instance).getLaunchCount();
            }

            public Builder setLaunchCount(int value) {
                copyOnWrite();
                ((ServiceHealthProto) this.instance).setLaunchCount(value);
                return this;
            }

            public Builder clearLaunchCount() {
                copyOnWrite();
                ((ServiceHealthProto) this.instance).clearLaunchCount();
                return this;
            }

            public boolean hasName() {
                return ((ServiceHealthProto) this.instance).hasName();
            }

            public HashedString getName() {
                return ((ServiceHealthProto) this.instance).getName();
            }

            public Builder setName(HashedString value) {
                copyOnWrite();
                ((ServiceHealthProto) this.instance).setName(value);
                return this;
            }

            public Builder setName(HashedString.Builder builderForValue) {
                copyOnWrite();
                ((ServiceHealthProto) this.instance).setName(builderForValue);
                return this;
            }

            public Builder mergeName(HashedString value) {
                copyOnWrite();
                ((ServiceHealthProto) this.instance).mergeName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((ServiceHealthProto) this.instance).clearName();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Timer extends GeneratedMessageLite<Timer, Builder> implements TimerOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final Timer DEFAULT_INSTANCE = new Timer();
        public static final int DURATION_MS_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 3;
        private static volatile Parser<Timer> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(Timer.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long durationMs_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private HashedString name_;

        private Timer() {
        }

        public static Timer parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Timer parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Timer parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Timer parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Timer parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Timer parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Timer parseFrom(InputStream input) throws IOException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Timer parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Timer parseDelimitedFrom(InputStream input) throws IOException {
            return (Timer) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Timer parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Timer) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Timer parseFrom(CodedInputStream input) throws IOException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Timer parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Timer) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Timer prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static Timer getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Timer> parser() {
            return DEFAULT_INSTANCE.getParserForType();
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

        public boolean hasDurationMs() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getDurationMs() {
            return this.durationMs_;
        }

        /* access modifiers changed from: private */
        public void setDurationMs(long value) {
            this.bitField0_ |= 2;
            this.durationMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDurationMs() {
            this.bitField0_ &= -3;
            this.durationMs_ = 0;
        }

        public boolean hasName() {
            return (this.bitField0_ & 4) != 0;
        }

        public HashedString getName() {
            HashedString hashedString = this.name_;
            return hashedString == null ? HashedString.getDefaultInstance() : hashedString;
        }

        /* access modifiers changed from: private */
        public void setName(HashedString value) {
            if (value != null) {
                this.name_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setName(HashedString.Builder builderForValue) {
            this.name_ = (HashedString) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeName(HashedString value) {
            if (value != null) {
                HashedString hashedString = this.name_;
                if (hashedString == null || hashedString == HashedString.getDefaultInstance()) {
                    this.name_ = value;
                } else {
                    this.name_ = (HashedString) ((HashedString.Builder) HashedString.newBuilder(this.name_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = null;
            this.bitField0_ &= -5;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Timer();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0002\u0001\u0003\t\u0002", new Object[]{"bitField0_", "count_", "durationMs_", "name_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Timer> parser = PARSER;
                    if (parser == null) {
                        synchronized (Timer.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<Timer, Builder> implements TimerOrBuilder {
            private Builder() {
                super(Timer.DEFAULT_INSTANCE);
            }

            public boolean hasCount() {
                return ((Timer) this.instance).hasCount();
            }

            public int getCount() {
                return ((Timer) this.instance).getCount();
            }

            public Builder setCount(int value) {
                copyOnWrite();
                ((Timer) this.instance).setCount(value);
                return this;
            }

            public Builder clearCount() {
                copyOnWrite();
                ((Timer) this.instance).clearCount();
                return this;
            }

            public boolean hasDurationMs() {
                return ((Timer) this.instance).hasDurationMs();
            }

            public long getDurationMs() {
                return ((Timer) this.instance).getDurationMs();
            }

            public Builder setDurationMs(long value) {
                copyOnWrite();
                ((Timer) this.instance).setDurationMs(value);
                return this;
            }

            public Builder clearDurationMs() {
                copyOnWrite();
                ((Timer) this.instance).clearDurationMs();
                return this;
            }

            public boolean hasName() {
                return ((Timer) this.instance).hasName();
            }

            public HashedString getName() {
                return ((Timer) this.instance).getName();
            }

            public Builder setName(HashedString value) {
                copyOnWrite();
                ((Timer) this.instance).setName(value);
                return this;
            }

            public Builder setName(HashedString.Builder builderForValue) {
                copyOnWrite();
                ((Timer) this.instance).setName(builderForValue);
                return this;
            }

            public Builder mergeName(HashedString value) {
                copyOnWrite();
                ((Timer) this.instance).mergeName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((Timer) this.instance).clearName();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Counter extends GeneratedMessageLite<Counter, Builder> implements CounterOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final Counter DEFAULT_INSTANCE = new Counter();
        public static final int NAME_FIELD_NUMBER = 2;
        private static volatile Parser<Counter> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(Counter.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private HashedString name_;

        private Counter() {
        }

        public static Counter parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Counter parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Counter parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Counter parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Counter parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Counter parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Counter parseFrom(InputStream input) throws IOException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Counter parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Counter parseDelimitedFrom(InputStream input) throws IOException {
            return (Counter) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Counter parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Counter) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Counter parseFrom(CodedInputStream input) throws IOException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Counter parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Counter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Counter prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static Counter getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Counter> parser() {
            return DEFAULT_INSTANCE.getParserForType();
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

        public boolean hasName() {
            return (this.bitField0_ & 2) != 0;
        }

        public HashedString getName() {
            HashedString hashedString = this.name_;
            return hashedString == null ? HashedString.getDefaultInstance() : hashedString;
        }

        /* access modifiers changed from: private */
        public void setName(HashedString value) {
            if (value != null) {
                this.name_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setName(HashedString.Builder builderForValue) {
            this.name_ = (HashedString) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeName(HashedString value) {
            if (value != null) {
                HashedString hashedString = this.name_;
                if (hashedString == null || hashedString == HashedString.getDefaultInstance()) {
                    this.name_ = value;
                } else {
                    this.name_ = (HashedString) ((HashedString.Builder) HashedString.newBuilder(this.name_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = null;
            this.bitField0_ &= -3;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Counter();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0000\u0002\t\u0001", new Object[]{"bitField0_", "count_", "name_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Counter> parser = PARSER;
                    if (parser == null) {
                        synchronized (Counter.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<Counter, Builder> implements CounterOrBuilder {
            private Builder() {
                super(Counter.DEFAULT_INSTANCE);
            }

            public boolean hasCount() {
                return ((Counter) this.instance).hasCount();
            }

            public int getCount() {
                return ((Counter) this.instance).getCount();
            }

            public Builder setCount(int value) {
                copyOnWrite();
                ((Counter) this.instance).setCount(value);
                return this;
            }

            public Builder clearCount() {
                copyOnWrite();
                ((Counter) this.instance).clearCount();
                return this;
            }

            public boolean hasName() {
                return ((Counter) this.instance).hasName();
            }

            public HashedString getName() {
                return ((Counter) this.instance).getName();
            }

            public Builder setName(HashedString value) {
                copyOnWrite();
                ((Counter) this.instance).setName(value);
                return this;
            }

            public Builder setName(HashedString.Builder builderForValue) {
                copyOnWrite();
                ((Counter) this.instance).setName(builderForValue);
                return this;
            }

            public Builder mergeName(HashedString value) {
                copyOnWrite();
                ((Counter) this.instance).mergeName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((Counter) this.instance).clearName();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class HashedString extends GeneratedMessageLite<HashedString, Builder> implements HashedStringOrBuilder {
        /* access modifiers changed from: private */
        public static final HashedString DEFAULT_INSTANCE = new HashedString();
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int UNHASHED_NAME_FIELD_NUMBER = 2;
        private static volatile Parser<HashedString> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(HashedString.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long hash_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String unhashedName_ = "";

        private HashedString() {
        }

        public static HashedString parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static HashedString parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static HashedString parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static HashedString parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static HashedString parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static HashedString parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static HashedString parseFrom(InputStream input) throws IOException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static HashedString parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static HashedString parseDelimitedFrom(InputStream input) throws IOException {
            return (HashedString) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static HashedString parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HashedString) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static HashedString parseFrom(CodedInputStream input) throws IOException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static HashedString parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HashedString) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(HashedString prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static HashedString getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HashedString> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getHash() {
            return this.hash_;
        }

        /* access modifiers changed from: private */
        public void setHash(long value) {
            this.bitField0_ |= 1;
            this.hash_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHash() {
            this.bitField0_ &= -2;
            this.hash_ = 0;
        }

        public boolean hasUnhashedName() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getUnhashedName() {
            return this.unhashedName_;
        }

        /* access modifiers changed from: private */
        public void setUnhashedName(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.unhashedName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getUnhashedNameBytes() {
            return ByteString.copyFromUtf8(this.unhashedName_);
        }

        /* access modifiers changed from: private */
        public void setUnhashedNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.unhashedName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearUnhashedName() {
            this.bitField0_ &= -3;
            this.unhashedName_ = getDefaultInstance().getUnhashedName();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new HashedString();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0005\u0000\u0002\b\u0001", new Object[]{"bitField0_", "hash_", "unhashedName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<HashedString> parser = PARSER;
                    if (parser == null) {
                        synchronized (HashedString.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<HashedString, Builder> implements HashedStringOrBuilder {
            private Builder() {
                super(HashedString.DEFAULT_INSTANCE);
            }

            public boolean hasHash() {
                return ((HashedString) this.instance).hasHash();
            }

            public long getHash() {
                return ((HashedString) this.instance).getHash();
            }

            public Builder setHash(long value) {
                copyOnWrite();
                ((HashedString) this.instance).setHash(value);
                return this;
            }

            public Builder clearHash() {
                copyOnWrite();
                ((HashedString) this.instance).clearHash();
                return this;
            }

            public boolean hasUnhashedName() {
                return ((HashedString) this.instance).hasUnhashedName();
            }

            public String getUnhashedName() {
                return ((HashedString) this.instance).getUnhashedName();
            }

            public Builder setUnhashedName(String value) {
                copyOnWrite();
                ((HashedString) this.instance).setUnhashedName(value);
                return this;
            }

            public ByteString getUnhashedNameBytes() {
                return ((HashedString) this.instance).getUnhashedNameBytes();
            }

            public Builder setUnhashedNameBytes(ByteString value) {
                copyOnWrite();
                ((HashedString) this.instance).setUnhashedNameBytes(value);
                return this;
            }

            public Builder clearUnhashedName() {
                copyOnWrite();
                ((HashedString) this.instance).clearUnhashedName();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class WakelockStats extends GeneratedMessageLite<WakelockStats, Builder> implements WakelockStatsOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final WakelockStats DEFAULT_INSTANCE = new WakelockStats();
        public static final int DURATION_MS_FIELD_NUMBER = 3;
        public static final int NAME_FIELD_NUMBER = 1;
        private static volatile Parser<WakelockStats> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(WakelockStats.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long durationMs_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private HashedString name_;

        private WakelockStats() {
        }

        public static WakelockStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static WakelockStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static WakelockStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static WakelockStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static WakelockStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static WakelockStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static WakelockStats parseFrom(InputStream input) throws IOException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static WakelockStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static WakelockStats parseDelimitedFrom(InputStream input) throws IOException {
            return (WakelockStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static WakelockStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WakelockStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static WakelockStats parseFrom(CodedInputStream input) throws IOException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static WakelockStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WakelockStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(WakelockStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static WakelockStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WakelockStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        @Deprecated
        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        @Deprecated
        public HashedString getName() {
            HashedString hashedString = this.name_;
            return hashedString == null ? HashedString.getDefaultInstance() : hashedString;
        }

        /* access modifiers changed from: private */
        public void setName(HashedString value) {
            if (value != null) {
                this.name_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setName(HashedString.Builder builderForValue) {
            this.name_ = (HashedString) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeName(HashedString value) {
            if (value != null) {
                HashedString hashedString = this.name_;
                if (hashedString == null || hashedString == HashedString.getDefaultInstance()) {
                    this.name_ = value;
                } else {
                    this.name_ = (HashedString) ((HashedString.Builder) HashedString.newBuilder(this.name_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = null;
            this.bitField0_ &= -2;
        }

        @Deprecated
        public boolean hasCount() {
            return (this.bitField0_ & 2) != 0;
        }

        @Deprecated
        public int getCount() {
            return this.count_;
        }

        /* access modifiers changed from: private */
        public void setCount(int value) {
            this.bitField0_ |= 2;
            this.count_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCount() {
            this.bitField0_ &= -3;
            this.count_ = 0;
        }

        @Deprecated
        public boolean hasDurationMs() {
            return (this.bitField0_ & 4) != 0;
        }

        @Deprecated
        public long getDurationMs() {
            return this.durationMs_;
        }

        /* access modifiers changed from: private */
        public void setDurationMs(long value) {
            this.bitField0_ |= 4;
            this.durationMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDurationMs() {
            this.bitField0_ &= -5;
            this.durationMs_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new WakelockStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\u0004\u0001\u0003\u0002\u0002", new Object[]{"bitField0_", "name_", "count_", "durationMs_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<WakelockStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (WakelockStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<WakelockStats, Builder> implements WakelockStatsOrBuilder {
            private Builder() {
                super(WakelockStats.DEFAULT_INSTANCE);
            }

            @Deprecated
            public boolean hasName() {
                return ((WakelockStats) this.instance).hasName();
            }

            @Deprecated
            public HashedString getName() {
                return ((WakelockStats) this.instance).getName();
            }

            @Deprecated
            public Builder setName(HashedString value) {
                copyOnWrite();
                ((WakelockStats) this.instance).setName(value);
                return this;
            }

            @Deprecated
            public Builder setName(HashedString.Builder builderForValue) {
                copyOnWrite();
                ((WakelockStats) this.instance).setName(builderForValue);
                return this;
            }

            @Deprecated
            public Builder mergeName(HashedString value) {
                copyOnWrite();
                ((WakelockStats) this.instance).mergeName(value);
                return this;
            }

            @Deprecated
            public Builder clearName() {
                copyOnWrite();
                ((WakelockStats) this.instance).clearName();
                return this;
            }

            @Deprecated
            public boolean hasCount() {
                return ((WakelockStats) this.instance).hasCount();
            }

            @Deprecated
            public int getCount() {
                return ((WakelockStats) this.instance).getCount();
            }

            @Deprecated
            public Builder setCount(int value) {
                copyOnWrite();
                ((WakelockStats) this.instance).setCount(value);
                return this;
            }

            @Deprecated
            public Builder clearCount() {
                copyOnWrite();
                ((WakelockStats) this.instance).clearCount();
                return this;
            }

            @Deprecated
            public boolean hasDurationMs() {
                return ((WakelockStats) this.instance).hasDurationMs();
            }

            @Deprecated
            public long getDurationMs() {
                return ((WakelockStats) this.instance).getDurationMs();
            }

            @Deprecated
            public Builder setDurationMs(long value) {
                copyOnWrite();
                ((WakelockStats) this.instance).setDurationMs(value);
                return this;
            }

            @Deprecated
            public Builder clearDurationMs() {
                copyOnWrite();
                ((WakelockStats) this.instance).clearDurationMs();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SyncStats extends GeneratedMessageLite<SyncStats, Builder> implements SyncStatsOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final SyncStats DEFAULT_INSTANCE = new SyncStats();
        public static final int DURATION_MS_FIELD_NUMBER = 3;
        public static final int NAME_FIELD_NUMBER = 1;
        private static volatile Parser<SyncStats> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(SyncStats.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long durationMs_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private HashedString name_;

        private SyncStats() {
        }

        public static SyncStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SyncStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SyncStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SyncStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SyncStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SyncStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SyncStats parseFrom(InputStream input) throws IOException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SyncStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SyncStats parseDelimitedFrom(InputStream input) throws IOException {
            return (SyncStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SyncStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SyncStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SyncStats parseFrom(CodedInputStream input) throws IOException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SyncStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SyncStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SyncStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static SyncStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SyncStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        @Deprecated
        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        @Deprecated
        public HashedString getName() {
            HashedString hashedString = this.name_;
            return hashedString == null ? HashedString.getDefaultInstance() : hashedString;
        }

        /* access modifiers changed from: private */
        public void setName(HashedString value) {
            if (value != null) {
                this.name_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setName(HashedString.Builder builderForValue) {
            this.name_ = (HashedString) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeName(HashedString value) {
            if (value != null) {
                HashedString hashedString = this.name_;
                if (hashedString == null || hashedString == HashedString.getDefaultInstance()) {
                    this.name_ = value;
                } else {
                    this.name_ = (HashedString) ((HashedString.Builder) HashedString.newBuilder(this.name_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = null;
            this.bitField0_ &= -2;
        }

        @Deprecated
        public boolean hasCount() {
            return (this.bitField0_ & 2) != 0;
        }

        @Deprecated
        public int getCount() {
            return this.count_;
        }

        /* access modifiers changed from: private */
        public void setCount(int value) {
            this.bitField0_ |= 2;
            this.count_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCount() {
            this.bitField0_ &= -3;
            this.count_ = 0;
        }

        @Deprecated
        public boolean hasDurationMs() {
            return (this.bitField0_ & 4) != 0;
        }

        @Deprecated
        public long getDurationMs() {
            return this.durationMs_;
        }

        /* access modifiers changed from: private */
        public void setDurationMs(long value) {
            this.bitField0_ |= 4;
            this.durationMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDurationMs() {
            this.bitField0_ &= -5;
            this.durationMs_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SyncStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\u0004\u0001\u0003\u0002\u0002", new Object[]{"bitField0_", "name_", "count_", "durationMs_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SyncStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (SyncStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<SyncStats, Builder> implements SyncStatsOrBuilder {
            private Builder() {
                super(SyncStats.DEFAULT_INSTANCE);
            }

            @Deprecated
            public boolean hasName() {
                return ((SyncStats) this.instance).hasName();
            }

            @Deprecated
            public HashedString getName() {
                return ((SyncStats) this.instance).getName();
            }

            @Deprecated
            public Builder setName(HashedString value) {
                copyOnWrite();
                ((SyncStats) this.instance).setName(value);
                return this;
            }

            @Deprecated
            public Builder setName(HashedString.Builder builderForValue) {
                copyOnWrite();
                ((SyncStats) this.instance).setName(builderForValue);
                return this;
            }

            @Deprecated
            public Builder mergeName(HashedString value) {
                copyOnWrite();
                ((SyncStats) this.instance).mergeName(value);
                return this;
            }

            @Deprecated
            public Builder clearName() {
                copyOnWrite();
                ((SyncStats) this.instance).clearName();
                return this;
            }

            @Deprecated
            public boolean hasCount() {
                return ((SyncStats) this.instance).hasCount();
            }

            @Deprecated
            public int getCount() {
                return ((SyncStats) this.instance).getCount();
            }

            @Deprecated
            public Builder setCount(int value) {
                copyOnWrite();
                ((SyncStats) this.instance).setCount(value);
                return this;
            }

            @Deprecated
            public Builder clearCount() {
                copyOnWrite();
                ((SyncStats) this.instance).clearCount();
                return this;
            }

            @Deprecated
            public boolean hasDurationMs() {
                return ((SyncStats) this.instance).hasDurationMs();
            }

            @Deprecated
            public long getDurationMs() {
                return ((SyncStats) this.instance).getDurationMs();
            }

            @Deprecated
            public Builder setDurationMs(long value) {
                copyOnWrite();
                ((SyncStats) this.instance).setDurationMs(value);
                return this;
            }

            @Deprecated
            public Builder clearDurationMs() {
                copyOnWrite();
                ((SyncStats) this.instance).clearDurationMs();
                return this;
            }
        }
    }
}
