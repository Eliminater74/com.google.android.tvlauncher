package logs.proto.wireless.performance.mobile;

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

public final class MemoryMetric {

    private MemoryMetric() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface AndroidMemoryStatsOrBuilder extends MessageLiteOrBuilder {
        int getAvailableMemoryKb();

        int getDalvikPrivateDirtyKb();

        int getDalvikPssKb();

        int getNativePrivateDirtyKb();

        int getNativePssKb();

        int getOtherGraphicsPssKb();

        int getOtherPrivateDirtyKb();

        int getOtherPssKb();

        int getSummaryCodeKb();

        int getSummaryGraphicsKb();

        int getSummaryJavaHeapKb();

        int getSummaryPrivateOtherKb();

        int getSummaryStackKb();

        int getSummarySystemKb();

        int getTotalMemoryMb();

        int getTotalPrivateCleanKb();

        int getTotalPssByMemInfoKb();

        int getTotalSharedDirtyKb();

        int getTotalSwappablePssKb();

        boolean hasAvailableMemoryKb();

        boolean hasDalvikPrivateDirtyKb();

        boolean hasDalvikPssKb();

        boolean hasNativePrivateDirtyKb();

        boolean hasNativePssKb();

        boolean hasOtherGraphicsPssKb();

        boolean hasOtherPrivateDirtyKb();

        boolean hasOtherPssKb();

        boolean hasSummaryCodeKb();

        boolean hasSummaryGraphicsKb();

        boolean hasSummaryJavaHeapKb();

        boolean hasSummaryPrivateOtherKb();

        boolean hasSummaryStackKb();

        boolean hasSummarySystemKb();

        boolean hasTotalMemoryMb();

        boolean hasTotalPrivateCleanKb();

        boolean hasTotalPssByMemInfoKb();

        boolean hasTotalSharedDirtyKb();

        boolean hasTotalSwappablePssKb();
    }

    public interface DeviceStatsOrBuilder extends MessageLiteOrBuilder {
        boolean getIsScreenOn();

        boolean hasIsScreenOn();
    }

    public interface IosMemoryStatsOrBuilder extends MessageLiteOrBuilder {
        int getFreeKb();

        int getTotalMemoryMb();

        int getUsedKb();

        boolean hasFreeKb();

        boolean hasTotalMemoryMb();

        boolean hasUsedKb();
    }

    public interface MemoryStatsOrBuilder extends MessageLiteOrBuilder {
        AndroidMemoryStats getAndroidMemoryStats();

        IosMemoryStats getIosMemoryStats();

        boolean hasAndroidMemoryStats();

        boolean hasIosMemoryStats();
    }

    public interface MemoryUsageMetricOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<MemoryUsageMetric, MemoryUsageMetric.Builder> {
        String getActivityName();

        ByteString getActivityNameBytes();

        DeviceStats getDeviceStats();

        MemoryUsageMetric.MemoryEventCode getMemoryEventCode();

        MemoryStats getMemoryStats();

        ProcessProto.ProcessStats getProcessStats();

        boolean hasActivityName();

        boolean hasDeviceStats();

        boolean hasMemoryEventCode();

        boolean hasMemoryStats();

        boolean hasProcessStats();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MemoryUsageMetric extends GeneratedMessageLite.ExtendableMessage<MemoryUsageMetric, Builder> implements MemoryUsageMetricOrBuilder {
        public static final int ACTIVITY_NAME_FIELD_NUMBER = 5;
        /* access modifiers changed from: private */
        public static final MemoryUsageMetric DEFAULT_INSTANCE = new MemoryUsageMetric();
        public static final int DEVICE_STATS_FIELD_NUMBER = 4;
        public static final int MEMORY_EVENT_CODE_FIELD_NUMBER = 3;
        public static final int MEMORY_STATS_FIELD_NUMBER = 1;
        public static final int PROCESS_STATS_FIELD_NUMBER = 2;
        private static volatile Parser<MemoryUsageMetric> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(MemoryUsageMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 5, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private String activityName_ = "";
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private DeviceStats deviceStats_;
        private byte memoizedIsInitialized = 2;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int memoryEventCode_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private MemoryStats memoryStats_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private ProcessProto.ProcessStats processStats_;

        private MemoryUsageMetric() {
        }

        public static MemoryUsageMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemoryUsageMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemoryUsageMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemoryUsageMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemoryUsageMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemoryUsageMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemoryUsageMetric parseFrom(InputStream input) throws IOException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MemoryUsageMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MemoryUsageMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (MemoryUsageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MemoryUsageMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemoryUsageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MemoryUsageMetric parseFrom(CodedInputStream input) throws IOException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MemoryUsageMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemoryUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MemoryUsageMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static MemoryUsageMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MemoryUsageMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasMemoryStats() {
            return (this.bitField0_ & 1) != 0;
        }

        public MemoryStats getMemoryStats() {
            MemoryStats memoryStats = this.memoryStats_;
            return memoryStats == null ? MemoryStats.getDefaultInstance() : memoryStats;
        }

        /* access modifiers changed from: private */
        public void setMemoryStats(MemoryStats value) {
            if (value != null) {
                this.memoryStats_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMemoryStats(MemoryStats.Builder builderForValue) {
            this.memoryStats_ = (MemoryStats) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeMemoryStats(MemoryStats value) {
            if (value != null) {
                MemoryStats memoryStats = this.memoryStats_;
                if (memoryStats == null || memoryStats == MemoryStats.getDefaultInstance()) {
                    this.memoryStats_ = value;
                } else {
                    this.memoryStats_ = (MemoryStats) ((MemoryStats.Builder) MemoryStats.newBuilder(this.memoryStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMemoryStats() {
            this.memoryStats_ = null;
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

        public boolean hasMemoryEventCode() {
            return (this.bitField0_ & 4) != 0;
        }

        public MemoryEventCode getMemoryEventCode() {
            MemoryEventCode result = MemoryEventCode.forNumber(this.memoryEventCode_);
            return result == null ? MemoryEventCode.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setMemoryEventCode(MemoryEventCode value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.memoryEventCode_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMemoryEventCode() {
            this.bitField0_ &= -5;
            this.memoryEventCode_ = 0;
        }

        public boolean hasDeviceStats() {
            return (this.bitField0_ & 8) != 0;
        }

        public DeviceStats getDeviceStats() {
            DeviceStats deviceStats = this.deviceStats_;
            return deviceStats == null ? DeviceStats.getDefaultInstance() : deviceStats;
        }

        /* access modifiers changed from: private */
        public void setDeviceStats(DeviceStats value) {
            if (value != null) {
                this.deviceStats_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setDeviceStats(DeviceStats.Builder builderForValue) {
            this.deviceStats_ = (DeviceStats) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeDeviceStats(DeviceStats value) {
            if (value != null) {
                DeviceStats deviceStats = this.deviceStats_;
                if (deviceStats == null || deviceStats == DeviceStats.getDefaultInstance()) {
                    this.deviceStats_ = value;
                } else {
                    this.deviceStats_ = (DeviceStats) ((DeviceStats.Builder) DeviceStats.newBuilder(this.deviceStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDeviceStats() {
            this.deviceStats_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasActivityName() {
            return (this.bitField0_ & 16) != 0;
        }

        public String getActivityName() {
            return this.activityName_;
        }

        /* access modifiers changed from: private */
        public void setActivityName(String value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.activityName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getActivityNameBytes() {
            return ByteString.copyFromUtf8(this.activityName_);
        }

        /* access modifiers changed from: private */
        public void setActivityNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.activityName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearActivityName() {
            this.bitField0_ &= -17;
            this.activityName_ = getDefaultInstance().getActivityName();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MemoryUsageMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\f\u0002\u0004\t\u0003\u0005\b\u0004", new Object[]{"bitField0_", "memoryStats_", "processStats_", "memoryEventCode_", MemoryEventCode.internalGetVerifier(), "deviceStats_", "activityName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MemoryUsageMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (MemoryUsageMetric.class) {
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

        public enum MemoryEventCode implements Internal.EnumLite {
            UNKNOWN(0),
            APP_CREATED(1),
            APP_TO_BACKGROUND(2),
            APP_TO_FOREGROUND(3),
            APP_IN_BACKGROUND_FOR_SECONDS(4),
            APP_IN_FOREGROUND_FOR_SECONDS(5),
            DELTA_OF_MEMORY(6);

            public static final int APP_CREATED_VALUE = 1;
            public static final int APP_IN_BACKGROUND_FOR_SECONDS_VALUE = 4;
            public static final int APP_IN_FOREGROUND_FOR_SECONDS_VALUE = 5;
            public static final int APP_TO_BACKGROUND_VALUE = 2;
            public static final int APP_TO_FOREGROUND_VALUE = 3;
            public static final int DELTA_OF_MEMORY_VALUE = 6;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<MemoryEventCode> internalValueMap = new Internal.EnumLiteMap<MemoryEventCode>() {
                public MemoryEventCode findValueByNumber(int number) {
                    return MemoryEventCode.forNumber(number);
                }
            };
            private final int value;

            private MemoryEventCode(int value2) {
                this.value = value2;
            }

            public static MemoryEventCode forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return UNKNOWN;
                    case 1:
                        return APP_CREATED;
                    case 2:
                        return APP_TO_BACKGROUND;
                    case 3:
                        return APP_TO_FOREGROUND;
                    case 4:
                        return APP_IN_BACKGROUND_FOR_SECONDS;
                    case 5:
                        return APP_IN_FOREGROUND_FOR_SECONDS;
                    case 6:
                        return DELTA_OF_MEMORY;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<MemoryEventCode> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return MemoryEventCodeVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class MemoryEventCodeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new MemoryEventCodeVerifier();

                private MemoryEventCodeVerifier() {
                }

                public boolean isInRange(int number) {
                    return MemoryEventCode.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<MemoryUsageMetric, Builder> implements MemoryUsageMetricOrBuilder {
            private Builder() {
                super(MemoryUsageMetric.DEFAULT_INSTANCE);
            }

            public boolean hasMemoryStats() {
                return ((MemoryUsageMetric) this.instance).hasMemoryStats();
            }

            public MemoryStats getMemoryStats() {
                return ((MemoryUsageMetric) this.instance).getMemoryStats();
            }

            public Builder setMemoryStats(MemoryStats value) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).setMemoryStats(value);
                return this;
            }

            public Builder setMemoryStats(MemoryStats.Builder builderForValue) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).setMemoryStats(builderForValue);
                return this;
            }

            public Builder mergeMemoryStats(MemoryStats value) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).mergeMemoryStats(value);
                return this;
            }

            public Builder clearMemoryStats() {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).clearMemoryStats();
                return this;
            }

            public boolean hasProcessStats() {
                return ((MemoryUsageMetric) this.instance).hasProcessStats();
            }

            public ProcessProto.ProcessStats getProcessStats() {
                return ((MemoryUsageMetric) this.instance).getProcessStats();
            }

            public Builder setProcessStats(ProcessProto.ProcessStats value) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).setProcessStats(value);
                return this;
            }

            public Builder setProcessStats(ProcessProto.ProcessStats.Builder builderForValue) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).setProcessStats(builderForValue);
                return this;
            }

            public Builder mergeProcessStats(ProcessProto.ProcessStats value) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).mergeProcessStats(value);
                return this;
            }

            public Builder clearProcessStats() {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).clearProcessStats();
                return this;
            }

            public boolean hasMemoryEventCode() {
                return ((MemoryUsageMetric) this.instance).hasMemoryEventCode();
            }

            public MemoryEventCode getMemoryEventCode() {
                return ((MemoryUsageMetric) this.instance).getMemoryEventCode();
            }

            public Builder setMemoryEventCode(MemoryEventCode value) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).setMemoryEventCode(value);
                return this;
            }

            public Builder clearMemoryEventCode() {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).clearMemoryEventCode();
                return this;
            }

            public boolean hasDeviceStats() {
                return ((MemoryUsageMetric) this.instance).hasDeviceStats();
            }

            public DeviceStats getDeviceStats() {
                return ((MemoryUsageMetric) this.instance).getDeviceStats();
            }

            public Builder setDeviceStats(DeviceStats value) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).setDeviceStats(value);
                return this;
            }

            public Builder setDeviceStats(DeviceStats.Builder builderForValue) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).setDeviceStats(builderForValue);
                return this;
            }

            public Builder mergeDeviceStats(DeviceStats value) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).mergeDeviceStats(value);
                return this;
            }

            public Builder clearDeviceStats() {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).clearDeviceStats();
                return this;
            }

            public boolean hasActivityName() {
                return ((MemoryUsageMetric) this.instance).hasActivityName();
            }

            public String getActivityName() {
                return ((MemoryUsageMetric) this.instance).getActivityName();
            }

            public Builder setActivityName(String value) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).setActivityName(value);
                return this;
            }

            public ByteString getActivityNameBytes() {
                return ((MemoryUsageMetric) this.instance).getActivityNameBytes();
            }

            public Builder setActivityNameBytes(ByteString value) {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).setActivityNameBytes(value);
                return this;
            }

            public Builder clearActivityName() {
                copyOnWrite();
                ((MemoryUsageMetric) this.instance).clearActivityName();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MemoryStats extends GeneratedMessageLite<MemoryStats, Builder> implements MemoryStatsOrBuilder {
        public static final int ANDROID_MEMORY_STATS_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final MemoryStats DEFAULT_INSTANCE = new MemoryStats();
        public static final int IOS_MEMORY_STATS_FIELD_NUMBER = 2;
        private static volatile Parser<MemoryStats> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(MemoryStats.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private AndroidMemoryStats androidMemoryStats_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private IosMemoryStats iosMemoryStats_;

        private MemoryStats() {
        }

        public static MemoryStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemoryStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemoryStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemoryStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemoryStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemoryStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemoryStats parseFrom(InputStream input) throws IOException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MemoryStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MemoryStats parseDelimitedFrom(InputStream input) throws IOException {
            return (MemoryStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MemoryStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemoryStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MemoryStats parseFrom(CodedInputStream input) throws IOException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MemoryStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MemoryStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static MemoryStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MemoryStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasAndroidMemoryStats() {
            return (this.bitField0_ & 1) != 0;
        }

        public AndroidMemoryStats getAndroidMemoryStats() {
            AndroidMemoryStats androidMemoryStats = this.androidMemoryStats_;
            return androidMemoryStats == null ? AndroidMemoryStats.getDefaultInstance() : androidMemoryStats;
        }

        /* access modifiers changed from: private */
        public void setAndroidMemoryStats(AndroidMemoryStats value) {
            if (value != null) {
                this.androidMemoryStats_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAndroidMemoryStats(AndroidMemoryStats.Builder builderForValue) {
            this.androidMemoryStats_ = (AndroidMemoryStats) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeAndroidMemoryStats(AndroidMemoryStats value) {
            if (value != null) {
                AndroidMemoryStats androidMemoryStats = this.androidMemoryStats_;
                if (androidMemoryStats == null || androidMemoryStats == AndroidMemoryStats.getDefaultInstance()) {
                    this.androidMemoryStats_ = value;
                } else {
                    this.androidMemoryStats_ = (AndroidMemoryStats) ((AndroidMemoryStats.Builder) AndroidMemoryStats.newBuilder(this.androidMemoryStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAndroidMemoryStats() {
            this.androidMemoryStats_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasIosMemoryStats() {
            return (this.bitField0_ & 2) != 0;
        }

        public IosMemoryStats getIosMemoryStats() {
            IosMemoryStats iosMemoryStats = this.iosMemoryStats_;
            return iosMemoryStats == null ? IosMemoryStats.getDefaultInstance() : iosMemoryStats;
        }

        /* access modifiers changed from: private */
        public void setIosMemoryStats(IosMemoryStats value) {
            if (value != null) {
                this.iosMemoryStats_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setIosMemoryStats(IosMemoryStats.Builder builderForValue) {
            this.iosMemoryStats_ = (IosMemoryStats) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeIosMemoryStats(IosMemoryStats value) {
            if (value != null) {
                IosMemoryStats iosMemoryStats = this.iosMemoryStats_;
                if (iosMemoryStats == null || iosMemoryStats == IosMemoryStats.getDefaultInstance()) {
                    this.iosMemoryStats_ = value;
                } else {
                    this.iosMemoryStats_ = (IosMemoryStats) ((IosMemoryStats.Builder) IosMemoryStats.newBuilder(this.iosMemoryStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearIosMemoryStats() {
            this.iosMemoryStats_ = null;
            this.bitField0_ &= -3;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MemoryStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001", new Object[]{"bitField0_", "androidMemoryStats_", "iosMemoryStats_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MemoryStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (MemoryStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<MemoryStats, Builder> implements MemoryStatsOrBuilder {
            private Builder() {
                super(MemoryStats.DEFAULT_INSTANCE);
            }

            public boolean hasAndroidMemoryStats() {
                return ((MemoryStats) this.instance).hasAndroidMemoryStats();
            }

            public AndroidMemoryStats getAndroidMemoryStats() {
                return ((MemoryStats) this.instance).getAndroidMemoryStats();
            }

            public Builder setAndroidMemoryStats(AndroidMemoryStats value) {
                copyOnWrite();
                ((MemoryStats) this.instance).setAndroidMemoryStats(value);
                return this;
            }

            public Builder setAndroidMemoryStats(AndroidMemoryStats.Builder builderForValue) {
                copyOnWrite();
                ((MemoryStats) this.instance).setAndroidMemoryStats(builderForValue);
                return this;
            }

            public Builder mergeAndroidMemoryStats(AndroidMemoryStats value) {
                copyOnWrite();
                ((MemoryStats) this.instance).mergeAndroidMemoryStats(value);
                return this;
            }

            public Builder clearAndroidMemoryStats() {
                copyOnWrite();
                ((MemoryStats) this.instance).clearAndroidMemoryStats();
                return this;
            }

            public boolean hasIosMemoryStats() {
                return ((MemoryStats) this.instance).hasIosMemoryStats();
            }

            public IosMemoryStats getIosMemoryStats() {
                return ((MemoryStats) this.instance).getIosMemoryStats();
            }

            public Builder setIosMemoryStats(IosMemoryStats value) {
                copyOnWrite();
                ((MemoryStats) this.instance).setIosMemoryStats(value);
                return this;
            }

            public Builder setIosMemoryStats(IosMemoryStats.Builder builderForValue) {
                copyOnWrite();
                ((MemoryStats) this.instance).setIosMemoryStats(builderForValue);
                return this;
            }

            public Builder mergeIosMemoryStats(IosMemoryStats value) {
                copyOnWrite();
                ((MemoryStats) this.instance).mergeIosMemoryStats(value);
                return this;
            }

            public Builder clearIosMemoryStats() {
                copyOnWrite();
                ((MemoryStats) this.instance).clearIosMemoryStats();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AndroidMemoryStats extends GeneratedMessageLite<AndroidMemoryStats, Builder> implements AndroidMemoryStatsOrBuilder {
        public static final int AVAILABLE_MEMORY_KB_FIELD_NUMBER = 17;
        public static final int DALVIK_PRIVATE_DIRTY_KB_FIELD_NUMBER = 4;
        public static final int DALVIK_PSS_KB_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final AndroidMemoryStats DEFAULT_INSTANCE = new AndroidMemoryStats();
        public static final int NATIVE_PRIVATE_DIRTY_KB_FIELD_NUMBER = 5;
        public static final int NATIVE_PSS_KB_FIELD_NUMBER = 2;
        public static final int OTHER_GRAPHICS_PSS_KB_FIELD_NUMBER = 10;
        public static final int OTHER_PRIVATE_DIRTY_KB_FIELD_NUMBER = 6;
        public static final int OTHER_PSS_KB_FIELD_NUMBER = 3;
        public static final int SUMMARY_CODE_KB_FIELD_NUMBER = 12;
        public static final int SUMMARY_GRAPHICS_KB_FIELD_NUMBER = 14;
        public static final int SUMMARY_JAVA_HEAP_KB_FIELD_NUMBER = 11;
        public static final int SUMMARY_PRIVATE_OTHER_KB_FIELD_NUMBER = 15;
        public static final int SUMMARY_STACK_KB_FIELD_NUMBER = 13;
        public static final int SUMMARY_SYSTEM_KB_FIELD_NUMBER = 16;
        public static final int TOTAL_MEMORY_MB_FIELD_NUMBER = 18;
        public static final int TOTAL_PRIVATE_CLEAN_KB_FIELD_NUMBER = 7;
        public static final int TOTAL_PSS_BY_MEM_INFO_KB_FIELD_NUMBER = 19;
        public static final int TOTAL_SHARED_DIRTY_KB_FIELD_NUMBER = 8;
        public static final int TOTAL_SWAPPABLE_PSS_KB_FIELD_NUMBER = 9;
        private static volatile Parser<AndroidMemoryStats> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(AndroidMemoryStats.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 17, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 131072, presenceBitsId = 0)
        private int availableMemoryKb_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int dalvikPrivateDirtyKb_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int dalvikPssKb_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int nativePrivateDirtyKb_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int nativePssKb_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private int otherGraphicsPssKb_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int otherPrivateDirtyKb_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int otherPssKb_;
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 0)
        private int summaryCodeKb_;
        @ProtoField(fieldNumber = 14, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16384, presenceBitsId = 0)
        private int summaryGraphicsKb_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private int summaryJavaHeapKb_;
        @ProtoField(fieldNumber = 15, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 32768, presenceBitsId = 0)
        private int summaryPrivateOtherKb_;
        @ProtoField(fieldNumber = 13, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8192, presenceBitsId = 0)
        private int summaryStackKb_;
        @ProtoField(fieldNumber = 16, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 65536, presenceBitsId = 0)
        private int summarySystemKb_;
        @ProtoField(fieldNumber = 18, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 262144, presenceBitsId = 0)
        private int totalMemoryMb_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int totalPrivateCleanKb_;
        @ProtoField(fieldNumber = 19, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private int totalPssByMemInfoKb_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private int totalSharedDirtyKb_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private int totalSwappablePssKb_;

        private AndroidMemoryStats() {
        }

        public static AndroidMemoryStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AndroidMemoryStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AndroidMemoryStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AndroidMemoryStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AndroidMemoryStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AndroidMemoryStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AndroidMemoryStats parseFrom(InputStream input) throws IOException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AndroidMemoryStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AndroidMemoryStats parseDelimitedFrom(InputStream input) throws IOException {
            return (AndroidMemoryStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AndroidMemoryStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AndroidMemoryStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AndroidMemoryStats parseFrom(CodedInputStream input) throws IOException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AndroidMemoryStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AndroidMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AndroidMemoryStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static AndroidMemoryStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AndroidMemoryStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasDalvikPssKb() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getDalvikPssKb() {
            return this.dalvikPssKb_;
        }

        /* access modifiers changed from: private */
        public void setDalvikPssKb(int value) {
            this.bitField0_ |= 1;
            this.dalvikPssKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDalvikPssKb() {
            this.bitField0_ &= -2;
            this.dalvikPssKb_ = 0;
        }

        public boolean hasNativePssKb() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNativePssKb() {
            return this.nativePssKb_;
        }

        /* access modifiers changed from: private */
        public void setNativePssKb(int value) {
            this.bitField0_ |= 2;
            this.nativePssKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNativePssKb() {
            this.bitField0_ &= -3;
            this.nativePssKb_ = 0;
        }

        public boolean hasOtherPssKb() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getOtherPssKb() {
            return this.otherPssKb_;
        }

        /* access modifiers changed from: private */
        public void setOtherPssKb(int value) {
            this.bitField0_ |= 4;
            this.otherPssKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearOtherPssKb() {
            this.bitField0_ &= -5;
            this.otherPssKb_ = 0;
        }

        public boolean hasDalvikPrivateDirtyKb() {
            return (this.bitField0_ & 8) != 0;
        }

        public int getDalvikPrivateDirtyKb() {
            return this.dalvikPrivateDirtyKb_;
        }

        /* access modifiers changed from: private */
        public void setDalvikPrivateDirtyKb(int value) {
            this.bitField0_ |= 8;
            this.dalvikPrivateDirtyKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDalvikPrivateDirtyKb() {
            this.bitField0_ &= -9;
            this.dalvikPrivateDirtyKb_ = 0;
        }

        public boolean hasNativePrivateDirtyKb() {
            return (this.bitField0_ & 16) != 0;
        }

        public int getNativePrivateDirtyKb() {
            return this.nativePrivateDirtyKb_;
        }

        /* access modifiers changed from: private */
        public void setNativePrivateDirtyKb(int value) {
            this.bitField0_ |= 16;
            this.nativePrivateDirtyKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNativePrivateDirtyKb() {
            this.bitField0_ &= -17;
            this.nativePrivateDirtyKb_ = 0;
        }

        public boolean hasOtherPrivateDirtyKb() {
            return (this.bitField0_ & 32) != 0;
        }

        public int getOtherPrivateDirtyKb() {
            return this.otherPrivateDirtyKb_;
        }

        /* access modifiers changed from: private */
        public void setOtherPrivateDirtyKb(int value) {
            this.bitField0_ |= 32;
            this.otherPrivateDirtyKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearOtherPrivateDirtyKb() {
            this.bitField0_ &= -33;
            this.otherPrivateDirtyKb_ = 0;
        }

        public boolean hasTotalPssByMemInfoKb() {
            return (this.bitField0_ & 64) != 0;
        }

        public int getTotalPssByMemInfoKb() {
            return this.totalPssByMemInfoKb_;
        }

        /* access modifiers changed from: private */
        public void setTotalPssByMemInfoKb(int value) {
            this.bitField0_ |= 64;
            this.totalPssByMemInfoKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalPssByMemInfoKb() {
            this.bitField0_ &= -65;
            this.totalPssByMemInfoKb_ = 0;
        }

        public boolean hasTotalPrivateCleanKb() {
            return (this.bitField0_ & 128) != 0;
        }

        public int getTotalPrivateCleanKb() {
            return this.totalPrivateCleanKb_;
        }

        /* access modifiers changed from: private */
        public void setTotalPrivateCleanKb(int value) {
            this.bitField0_ |= 128;
            this.totalPrivateCleanKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalPrivateCleanKb() {
            this.bitField0_ &= -129;
            this.totalPrivateCleanKb_ = 0;
        }

        public boolean hasTotalSharedDirtyKb() {
            return (this.bitField0_ & 256) != 0;
        }

        public int getTotalSharedDirtyKb() {
            return this.totalSharedDirtyKb_;
        }

        /* access modifiers changed from: private */
        public void setTotalSharedDirtyKb(int value) {
            this.bitField0_ |= 256;
            this.totalSharedDirtyKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalSharedDirtyKb() {
            this.bitField0_ &= -257;
            this.totalSharedDirtyKb_ = 0;
        }

        public boolean hasTotalSwappablePssKb() {
            return (this.bitField0_ & 512) != 0;
        }

        public int getTotalSwappablePssKb() {
            return this.totalSwappablePssKb_;
        }

        /* access modifiers changed from: private */
        public void setTotalSwappablePssKb(int value) {
            this.bitField0_ |= 512;
            this.totalSwappablePssKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalSwappablePssKb() {
            this.bitField0_ &= -513;
            this.totalSwappablePssKb_ = 0;
        }

        public boolean hasOtherGraphicsPssKb() {
            return (this.bitField0_ & 1024) != 0;
        }

        public int getOtherGraphicsPssKb() {
            return this.otherGraphicsPssKb_;
        }

        /* access modifiers changed from: private */
        public void setOtherGraphicsPssKb(int value) {
            this.bitField0_ |= 1024;
            this.otherGraphicsPssKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearOtherGraphicsPssKb() {
            this.bitField0_ &= -1025;
            this.otherGraphicsPssKb_ = 0;
        }

        public boolean hasSummaryJavaHeapKb() {
            return (this.bitField0_ & 2048) != 0;
        }

        public int getSummaryJavaHeapKb() {
            return this.summaryJavaHeapKb_;
        }

        /* access modifiers changed from: private */
        public void setSummaryJavaHeapKb(int value) {
            this.bitField0_ |= 2048;
            this.summaryJavaHeapKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSummaryJavaHeapKb() {
            this.bitField0_ &= -2049;
            this.summaryJavaHeapKb_ = 0;
        }

        public boolean hasSummaryCodeKb() {
            return (this.bitField0_ & 4096) != 0;
        }

        public int getSummaryCodeKb() {
            return this.summaryCodeKb_;
        }

        /* access modifiers changed from: private */
        public void setSummaryCodeKb(int value) {
            this.bitField0_ |= 4096;
            this.summaryCodeKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSummaryCodeKb() {
            this.bitField0_ &= -4097;
            this.summaryCodeKb_ = 0;
        }

        public boolean hasSummaryStackKb() {
            return (this.bitField0_ & 8192) != 0;
        }

        public int getSummaryStackKb() {
            return this.summaryStackKb_;
        }

        /* access modifiers changed from: private */
        public void setSummaryStackKb(int value) {
            this.bitField0_ |= 8192;
            this.summaryStackKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSummaryStackKb() {
            this.bitField0_ &= -8193;
            this.summaryStackKb_ = 0;
        }

        public boolean hasSummaryGraphicsKb() {
            return (this.bitField0_ & 16384) != 0;
        }

        public int getSummaryGraphicsKb() {
            return this.summaryGraphicsKb_;
        }

        /* access modifiers changed from: private */
        public void setSummaryGraphicsKb(int value) {
            this.bitField0_ |= 16384;
            this.summaryGraphicsKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSummaryGraphicsKb() {
            this.bitField0_ &= -16385;
            this.summaryGraphicsKb_ = 0;
        }

        public boolean hasSummaryPrivateOtherKb() {
            return (this.bitField0_ & 32768) != 0;
        }

        public int getSummaryPrivateOtherKb() {
            return this.summaryPrivateOtherKb_;
        }

        /* access modifiers changed from: private */
        public void setSummaryPrivateOtherKb(int value) {
            this.bitField0_ |= 32768;
            this.summaryPrivateOtherKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSummaryPrivateOtherKb() {
            this.bitField0_ &= -32769;
            this.summaryPrivateOtherKb_ = 0;
        }

        public boolean hasSummarySystemKb() {
            return (this.bitField0_ & 65536) != 0;
        }

        public int getSummarySystemKb() {
            return this.summarySystemKb_;
        }

        /* access modifiers changed from: private */
        public void setSummarySystemKb(int value) {
            this.bitField0_ |= 65536;
            this.summarySystemKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSummarySystemKb() {
            this.bitField0_ &= -65537;
            this.summarySystemKb_ = 0;
        }

        public boolean hasAvailableMemoryKb() {
            return (this.bitField0_ & 131072) != 0;
        }

        public int getAvailableMemoryKb() {
            return this.availableMemoryKb_;
        }

        /* access modifiers changed from: private */
        public void setAvailableMemoryKb(int value) {
            this.bitField0_ |= 131072;
            this.availableMemoryKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAvailableMemoryKb() {
            this.bitField0_ &= -131073;
            this.availableMemoryKb_ = 0;
        }

        public boolean hasTotalMemoryMb() {
            return (this.bitField0_ & 262144) != 0;
        }

        public int getTotalMemoryMb() {
            return this.totalMemoryMb_;
        }

        /* access modifiers changed from: private */
        public void setTotalMemoryMb(int value) {
            this.bitField0_ |= 262144;
            this.totalMemoryMb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalMemoryMb() {
            this.bitField0_ &= -262145;
            this.totalMemoryMb_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AndroidMemoryStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0013\u0000\u0001\u0001\u0013\u0013\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u0004\u0002\u0004\u0004\u0003\u0005\u0004\u0004\u0006\u0004\u0005\u0007\u0004\u0007\b\u0004\b\t\u0004\t\n\u0004\n\u000b\u0004\u000b\f\u0004\f\r\u0004\r\u000e\u0004\u000e\u000f\u0004\u000f\u0010\u0004\u0010\u0011\u0004\u0011\u0012\u0004\u0012\u0013\u0004\u0006", new Object[]{"bitField0_", "dalvikPssKb_", "nativePssKb_", "otherPssKb_", "dalvikPrivateDirtyKb_", "nativePrivateDirtyKb_", "otherPrivateDirtyKb_", "totalPrivateCleanKb_", "totalSharedDirtyKb_", "totalSwappablePssKb_", "otherGraphicsPssKb_", "summaryJavaHeapKb_", "summaryCodeKb_", "summaryStackKb_", "summaryGraphicsKb_", "summaryPrivateOtherKb_", "summarySystemKb_", "availableMemoryKb_", "totalMemoryMb_", "totalPssByMemInfoKb_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AndroidMemoryStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (AndroidMemoryStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<AndroidMemoryStats, Builder> implements AndroidMemoryStatsOrBuilder {
            private Builder() {
                super(AndroidMemoryStats.DEFAULT_INSTANCE);
            }

            public boolean hasDalvikPssKb() {
                return ((AndroidMemoryStats) this.instance).hasDalvikPssKb();
            }

            public int getDalvikPssKb() {
                return ((AndroidMemoryStats) this.instance).getDalvikPssKb();
            }

            public Builder setDalvikPssKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setDalvikPssKb(value);
                return this;
            }

            public Builder clearDalvikPssKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearDalvikPssKb();
                return this;
            }

            public boolean hasNativePssKb() {
                return ((AndroidMemoryStats) this.instance).hasNativePssKb();
            }

            public int getNativePssKb() {
                return ((AndroidMemoryStats) this.instance).getNativePssKb();
            }

            public Builder setNativePssKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setNativePssKb(value);
                return this;
            }

            public Builder clearNativePssKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearNativePssKb();
                return this;
            }

            public boolean hasOtherPssKb() {
                return ((AndroidMemoryStats) this.instance).hasOtherPssKb();
            }

            public int getOtherPssKb() {
                return ((AndroidMemoryStats) this.instance).getOtherPssKb();
            }

            public Builder setOtherPssKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setOtherPssKb(value);
                return this;
            }

            public Builder clearOtherPssKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearOtherPssKb();
                return this;
            }

            public boolean hasDalvikPrivateDirtyKb() {
                return ((AndroidMemoryStats) this.instance).hasDalvikPrivateDirtyKb();
            }

            public int getDalvikPrivateDirtyKb() {
                return ((AndroidMemoryStats) this.instance).getDalvikPrivateDirtyKb();
            }

            public Builder setDalvikPrivateDirtyKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setDalvikPrivateDirtyKb(value);
                return this;
            }

            public Builder clearDalvikPrivateDirtyKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearDalvikPrivateDirtyKb();
                return this;
            }

            public boolean hasNativePrivateDirtyKb() {
                return ((AndroidMemoryStats) this.instance).hasNativePrivateDirtyKb();
            }

            public int getNativePrivateDirtyKb() {
                return ((AndroidMemoryStats) this.instance).getNativePrivateDirtyKb();
            }

            public Builder setNativePrivateDirtyKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setNativePrivateDirtyKb(value);
                return this;
            }

            public Builder clearNativePrivateDirtyKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearNativePrivateDirtyKb();
                return this;
            }

            public boolean hasOtherPrivateDirtyKb() {
                return ((AndroidMemoryStats) this.instance).hasOtherPrivateDirtyKb();
            }

            public int getOtherPrivateDirtyKb() {
                return ((AndroidMemoryStats) this.instance).getOtherPrivateDirtyKb();
            }

            public Builder setOtherPrivateDirtyKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setOtherPrivateDirtyKb(value);
                return this;
            }

            public Builder clearOtherPrivateDirtyKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearOtherPrivateDirtyKb();
                return this;
            }

            public boolean hasTotalPssByMemInfoKb() {
                return ((AndroidMemoryStats) this.instance).hasTotalPssByMemInfoKb();
            }

            public int getTotalPssByMemInfoKb() {
                return ((AndroidMemoryStats) this.instance).getTotalPssByMemInfoKb();
            }

            public Builder setTotalPssByMemInfoKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setTotalPssByMemInfoKb(value);
                return this;
            }

            public Builder clearTotalPssByMemInfoKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearTotalPssByMemInfoKb();
                return this;
            }

            public boolean hasTotalPrivateCleanKb() {
                return ((AndroidMemoryStats) this.instance).hasTotalPrivateCleanKb();
            }

            public int getTotalPrivateCleanKb() {
                return ((AndroidMemoryStats) this.instance).getTotalPrivateCleanKb();
            }

            public Builder setTotalPrivateCleanKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setTotalPrivateCleanKb(value);
                return this;
            }

            public Builder clearTotalPrivateCleanKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearTotalPrivateCleanKb();
                return this;
            }

            public boolean hasTotalSharedDirtyKb() {
                return ((AndroidMemoryStats) this.instance).hasTotalSharedDirtyKb();
            }

            public int getTotalSharedDirtyKb() {
                return ((AndroidMemoryStats) this.instance).getTotalSharedDirtyKb();
            }

            public Builder setTotalSharedDirtyKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setTotalSharedDirtyKb(value);
                return this;
            }

            public Builder clearTotalSharedDirtyKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearTotalSharedDirtyKb();
                return this;
            }

            public boolean hasTotalSwappablePssKb() {
                return ((AndroidMemoryStats) this.instance).hasTotalSwappablePssKb();
            }

            public int getTotalSwappablePssKb() {
                return ((AndroidMemoryStats) this.instance).getTotalSwappablePssKb();
            }

            public Builder setTotalSwappablePssKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setTotalSwappablePssKb(value);
                return this;
            }

            public Builder clearTotalSwappablePssKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearTotalSwappablePssKb();
                return this;
            }

            public boolean hasOtherGraphicsPssKb() {
                return ((AndroidMemoryStats) this.instance).hasOtherGraphicsPssKb();
            }

            public int getOtherGraphicsPssKb() {
                return ((AndroidMemoryStats) this.instance).getOtherGraphicsPssKb();
            }

            public Builder setOtherGraphicsPssKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setOtherGraphicsPssKb(value);
                return this;
            }

            public Builder clearOtherGraphicsPssKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearOtherGraphicsPssKb();
                return this;
            }

            public boolean hasSummaryJavaHeapKb() {
                return ((AndroidMemoryStats) this.instance).hasSummaryJavaHeapKb();
            }

            public int getSummaryJavaHeapKb() {
                return ((AndroidMemoryStats) this.instance).getSummaryJavaHeapKb();
            }

            public Builder setSummaryJavaHeapKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setSummaryJavaHeapKb(value);
                return this;
            }

            public Builder clearSummaryJavaHeapKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearSummaryJavaHeapKb();
                return this;
            }

            public boolean hasSummaryCodeKb() {
                return ((AndroidMemoryStats) this.instance).hasSummaryCodeKb();
            }

            public int getSummaryCodeKb() {
                return ((AndroidMemoryStats) this.instance).getSummaryCodeKb();
            }

            public Builder setSummaryCodeKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setSummaryCodeKb(value);
                return this;
            }

            public Builder clearSummaryCodeKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearSummaryCodeKb();
                return this;
            }

            public boolean hasSummaryStackKb() {
                return ((AndroidMemoryStats) this.instance).hasSummaryStackKb();
            }

            public int getSummaryStackKb() {
                return ((AndroidMemoryStats) this.instance).getSummaryStackKb();
            }

            public Builder setSummaryStackKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setSummaryStackKb(value);
                return this;
            }

            public Builder clearSummaryStackKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearSummaryStackKb();
                return this;
            }

            public boolean hasSummaryGraphicsKb() {
                return ((AndroidMemoryStats) this.instance).hasSummaryGraphicsKb();
            }

            public int getSummaryGraphicsKb() {
                return ((AndroidMemoryStats) this.instance).getSummaryGraphicsKb();
            }

            public Builder setSummaryGraphicsKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setSummaryGraphicsKb(value);
                return this;
            }

            public Builder clearSummaryGraphicsKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearSummaryGraphicsKb();
                return this;
            }

            public boolean hasSummaryPrivateOtherKb() {
                return ((AndroidMemoryStats) this.instance).hasSummaryPrivateOtherKb();
            }

            public int getSummaryPrivateOtherKb() {
                return ((AndroidMemoryStats) this.instance).getSummaryPrivateOtherKb();
            }

            public Builder setSummaryPrivateOtherKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setSummaryPrivateOtherKb(value);
                return this;
            }

            public Builder clearSummaryPrivateOtherKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearSummaryPrivateOtherKb();
                return this;
            }

            public boolean hasSummarySystemKb() {
                return ((AndroidMemoryStats) this.instance).hasSummarySystemKb();
            }

            public int getSummarySystemKb() {
                return ((AndroidMemoryStats) this.instance).getSummarySystemKb();
            }

            public Builder setSummarySystemKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setSummarySystemKb(value);
                return this;
            }

            public Builder clearSummarySystemKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearSummarySystemKb();
                return this;
            }

            public boolean hasAvailableMemoryKb() {
                return ((AndroidMemoryStats) this.instance).hasAvailableMemoryKb();
            }

            public int getAvailableMemoryKb() {
                return ((AndroidMemoryStats) this.instance).getAvailableMemoryKb();
            }

            public Builder setAvailableMemoryKb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setAvailableMemoryKb(value);
                return this;
            }

            public Builder clearAvailableMemoryKb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearAvailableMemoryKb();
                return this;
            }

            public boolean hasTotalMemoryMb() {
                return ((AndroidMemoryStats) this.instance).hasTotalMemoryMb();
            }

            public int getTotalMemoryMb() {
                return ((AndroidMemoryStats) this.instance).getTotalMemoryMb();
            }

            public Builder setTotalMemoryMb(int value) {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).setTotalMemoryMb(value);
                return this;
            }

            public Builder clearTotalMemoryMb() {
                copyOnWrite();
                ((AndroidMemoryStats) this.instance).clearTotalMemoryMb();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class IosMemoryStats extends GeneratedMessageLite<IosMemoryStats, Builder> implements IosMemoryStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final IosMemoryStats DEFAULT_INSTANCE = new IosMemoryStats();
        public static final int FREE_KB_FIELD_NUMBER = 2;
        public static final int TOTAL_MEMORY_MB_FIELD_NUMBER = 3;
        public static final int USED_KB_FIELD_NUMBER = 1;
        private static volatile Parser<IosMemoryStats> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(IosMemoryStats.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int freeKb_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int totalMemoryMb_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int usedKb_;

        private IosMemoryStats() {
        }

        public static IosMemoryStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static IosMemoryStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static IosMemoryStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static IosMemoryStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static IosMemoryStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static IosMemoryStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static IosMemoryStats parseFrom(InputStream input) throws IOException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static IosMemoryStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static IosMemoryStats parseDelimitedFrom(InputStream input) throws IOException {
            return (IosMemoryStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static IosMemoryStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (IosMemoryStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static IosMemoryStats parseFrom(CodedInputStream input) throws IOException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static IosMemoryStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (IosMemoryStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(IosMemoryStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static IosMemoryStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<IosMemoryStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasUsedKb() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getUsedKb() {
            return this.usedKb_;
        }

        /* access modifiers changed from: private */
        public void setUsedKb(int value) {
            this.bitField0_ |= 1;
            this.usedKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearUsedKb() {
            this.bitField0_ &= -2;
            this.usedKb_ = 0;
        }

        public boolean hasFreeKb() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getFreeKb() {
            return this.freeKb_;
        }

        /* access modifiers changed from: private */
        public void setFreeKb(int value) {
            this.bitField0_ |= 2;
            this.freeKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearFreeKb() {
            this.bitField0_ &= -3;
            this.freeKb_ = 0;
        }

        public boolean hasTotalMemoryMb() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getTotalMemoryMb() {
            return this.totalMemoryMb_;
        }

        /* access modifiers changed from: private */
        public void setTotalMemoryMb(int value) {
            this.bitField0_ |= 4;
            this.totalMemoryMb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalMemoryMb() {
            this.bitField0_ &= -5;
            this.totalMemoryMb_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new IosMemoryStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u0004\u0002", new Object[]{"bitField0_", "usedKb_", "freeKb_", "totalMemoryMb_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<IosMemoryStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (IosMemoryStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<IosMemoryStats, Builder> implements IosMemoryStatsOrBuilder {
            private Builder() {
                super(IosMemoryStats.DEFAULT_INSTANCE);
            }

            public boolean hasUsedKb() {
                return ((IosMemoryStats) this.instance).hasUsedKb();
            }

            public int getUsedKb() {
                return ((IosMemoryStats) this.instance).getUsedKb();
            }

            public Builder setUsedKb(int value) {
                copyOnWrite();
                ((IosMemoryStats) this.instance).setUsedKb(value);
                return this;
            }

            public Builder clearUsedKb() {
                copyOnWrite();
                ((IosMemoryStats) this.instance).clearUsedKb();
                return this;
            }

            public boolean hasFreeKb() {
                return ((IosMemoryStats) this.instance).hasFreeKb();
            }

            public int getFreeKb() {
                return ((IosMemoryStats) this.instance).getFreeKb();
            }

            public Builder setFreeKb(int value) {
                copyOnWrite();
                ((IosMemoryStats) this.instance).setFreeKb(value);
                return this;
            }

            public Builder clearFreeKb() {
                copyOnWrite();
                ((IosMemoryStats) this.instance).clearFreeKb();
                return this;
            }

            public boolean hasTotalMemoryMb() {
                return ((IosMemoryStats) this.instance).hasTotalMemoryMb();
            }

            public int getTotalMemoryMb() {
                return ((IosMemoryStats) this.instance).getTotalMemoryMb();
            }

            public Builder setTotalMemoryMb(int value) {
                copyOnWrite();
                ((IosMemoryStats) this.instance).setTotalMemoryMb(value);
                return this;
            }

            public Builder clearTotalMemoryMb() {
                copyOnWrite();
                ((IosMemoryStats) this.instance).clearTotalMemoryMb();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class DeviceStats extends GeneratedMessageLite<DeviceStats, Builder> implements DeviceStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final DeviceStats DEFAULT_INSTANCE = new DeviceStats();
        public static final int IS_SCREEN_ON_FIELD_NUMBER = 1;
        private static volatile Parser<DeviceStats> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(DeviceStats.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean isScreenOn_;

        private DeviceStats() {
        }

        public static DeviceStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceStats parseFrom(InputStream input) throws IOException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceStats parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceStats parseFrom(CodedInputStream input) throws IOException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DeviceStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static DeviceStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasIsScreenOn() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getIsScreenOn() {
            return this.isScreenOn_;
        }

        /* access modifiers changed from: private */
        public void setIsScreenOn(boolean value) {
            this.bitField0_ |= 1;
            this.isScreenOn_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsScreenOn() {
            this.bitField0_ &= -2;
            this.isScreenOn_ = false;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DeviceStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0007\u0000", new Object[]{"bitField0_", "isScreenOn_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DeviceStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (DeviceStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<DeviceStats, Builder> implements DeviceStatsOrBuilder {
            private Builder() {
                super(DeviceStats.DEFAULT_INSTANCE);
            }

            public boolean hasIsScreenOn() {
                return ((DeviceStats) this.instance).hasIsScreenOn();
            }

            public boolean getIsScreenOn() {
                return ((DeviceStats) this.instance).getIsScreenOn();
            }

            public Builder setIsScreenOn(boolean value) {
                copyOnWrite();
                ((DeviceStats) this.instance).setIsScreenOn(value);
                return this;
            }

            public Builder clearIsScreenOn() {
                copyOnWrite();
                ((DeviceStats) this.instance).clearIsScreenOn();
                return this;
            }
        }
    }
}
