package logs.proto.wireless.performance.mobile;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
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

public final class CpuProfiling {

    private CpuProfiling() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface CpuProfilingMetricOrBuilder extends MessageLiteOrBuilder {
        DeviceMetadata getDeviceMetadata();

        int getSampleBufferSize();

        int getSampleDurationActual();

        int getSampleDurationScheduled();

        int getSampleFrequency();

        double getSamplesPerEpoch();

        ByteString getTraceBlob();

        boolean hasDeviceMetadata();

        boolean hasSampleBufferSize();

        boolean hasSampleDurationActual();

        boolean hasSampleDurationScheduled();

        boolean hasSampleFrequency();

        boolean hasSamplesPerEpoch();

        boolean hasTraceBlob();
    }

    public interface DeviceMetadataOrBuilder extends MessageLiteOrBuilder {
        DeviceState getAfterState();

        float getBatteryDropPercent();

        DeviceState getBeforeState();

        boolean hasAfterState();

        boolean hasBatteryDropPercent();

        boolean hasBeforeState();
    }

    public interface DeviceStateOrBuilder extends MessageLiteOrBuilder {
        boolean getBluetoothOn();

        boolean getCharging();

        boolean getScreenOn();

        boolean getWifiOn();

        boolean hasBluetoothOn();

        boolean hasCharging();

        boolean hasScreenOn();

        boolean hasWifiOn();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class DeviceMetadata extends GeneratedMessageLite<DeviceMetadata, Builder> implements DeviceMetadataOrBuilder {
        public static final int AFTER_STATE_FIELD_NUMBER = 2;
        public static final int BATTERY_DROP_PERCENT_FIELD_NUMBER = 3;
        public static final int BEFORE_STATE_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final DeviceMetadata DEFAULT_INSTANCE = new DeviceMetadata();
        private static volatile Parser<DeviceMetadata> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(DeviceMetadata.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private DeviceState afterState_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.FLOAT)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private float batteryDropPercent_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private DeviceState beforeState_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;

        private DeviceMetadata() {
        }

        public static DeviceMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceMetadata parseFrom(InputStream input) throws IOException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceMetadata parseFrom(CodedInputStream input) throws IOException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DeviceMetadata prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static DeviceMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceMetadata> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasBeforeState() {
            return (this.bitField0_ & 1) != 0;
        }

        public DeviceState getBeforeState() {
            DeviceState deviceState = this.beforeState_;
            return deviceState == null ? DeviceState.getDefaultInstance() : deviceState;
        }

        /* access modifiers changed from: private */
        public void setBeforeState(DeviceState value) {
            if (value != null) {
                this.beforeState_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setBeforeState(DeviceState.Builder builderForValue) {
            this.beforeState_ = (DeviceState) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeBeforeState(DeviceState value) {
            if (value != null) {
                DeviceState deviceState = this.beforeState_;
                if (deviceState == null || deviceState == DeviceState.getDefaultInstance()) {
                    this.beforeState_ = value;
                } else {
                    this.beforeState_ = (DeviceState) ((DeviceState.Builder) DeviceState.newBuilder(this.beforeState_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearBeforeState() {
            this.beforeState_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasAfterState() {
            return (this.bitField0_ & 2) != 0;
        }

        public DeviceState getAfterState() {
            DeviceState deviceState = this.afterState_;
            return deviceState == null ? DeviceState.getDefaultInstance() : deviceState;
        }

        /* access modifiers changed from: private */
        public void setAfterState(DeviceState value) {
            if (value != null) {
                this.afterState_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAfterState(DeviceState.Builder builderForValue) {
            this.afterState_ = (DeviceState) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeAfterState(DeviceState value) {
            if (value != null) {
                DeviceState deviceState = this.afterState_;
                if (deviceState == null || deviceState == DeviceState.getDefaultInstance()) {
                    this.afterState_ = value;
                } else {
                    this.afterState_ = (DeviceState) ((DeviceState.Builder) DeviceState.newBuilder(this.afterState_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAfterState() {
            this.afterState_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasBatteryDropPercent() {
            return (this.bitField0_ & 4) != 0;
        }

        public float getBatteryDropPercent() {
            return this.batteryDropPercent_;
        }

        /* access modifiers changed from: private */
        public void setBatteryDropPercent(float value) {
            this.bitField0_ |= 4;
            this.batteryDropPercent_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBatteryDropPercent() {
            this.bitField0_ &= -5;
            this.batteryDropPercent_ = 0.0f;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DeviceMetadata();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\u0001\u0002", new Object[]{"bitField0_", "beforeState_", "afterState_", "batteryDropPercent_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DeviceMetadata> parser = PARSER;
                    if (parser == null) {
                        synchronized (DeviceMetadata.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<DeviceMetadata, Builder> implements DeviceMetadataOrBuilder {
            private Builder() {
                super(DeviceMetadata.DEFAULT_INSTANCE);
            }

            public boolean hasBeforeState() {
                return ((DeviceMetadata) this.instance).hasBeforeState();
            }

            public DeviceState getBeforeState() {
                return ((DeviceMetadata) this.instance).getBeforeState();
            }

            public Builder setBeforeState(DeviceState value) {
                copyOnWrite();
                ((DeviceMetadata) this.instance).setBeforeState(value);
                return this;
            }

            public Builder setBeforeState(DeviceState.Builder builderForValue) {
                copyOnWrite();
                ((DeviceMetadata) this.instance).setBeforeState(builderForValue);
                return this;
            }

            public Builder mergeBeforeState(DeviceState value) {
                copyOnWrite();
                ((DeviceMetadata) this.instance).mergeBeforeState(value);
                return this;
            }

            public Builder clearBeforeState() {
                copyOnWrite();
                ((DeviceMetadata) this.instance).clearBeforeState();
                return this;
            }

            public boolean hasAfterState() {
                return ((DeviceMetadata) this.instance).hasAfterState();
            }

            public DeviceState getAfterState() {
                return ((DeviceMetadata) this.instance).getAfterState();
            }

            public Builder setAfterState(DeviceState value) {
                copyOnWrite();
                ((DeviceMetadata) this.instance).setAfterState(value);
                return this;
            }

            public Builder setAfterState(DeviceState.Builder builderForValue) {
                copyOnWrite();
                ((DeviceMetadata) this.instance).setAfterState(builderForValue);
                return this;
            }

            public Builder mergeAfterState(DeviceState value) {
                copyOnWrite();
                ((DeviceMetadata) this.instance).mergeAfterState(value);
                return this;
            }

            public Builder clearAfterState() {
                copyOnWrite();
                ((DeviceMetadata) this.instance).clearAfterState();
                return this;
            }

            public boolean hasBatteryDropPercent() {
                return ((DeviceMetadata) this.instance).hasBatteryDropPercent();
            }

            public float getBatteryDropPercent() {
                return ((DeviceMetadata) this.instance).getBatteryDropPercent();
            }

            public Builder setBatteryDropPercent(float value) {
                copyOnWrite();
                ((DeviceMetadata) this.instance).setBatteryDropPercent(value);
                return this;
            }

            public Builder clearBatteryDropPercent() {
                copyOnWrite();
                ((DeviceMetadata) this.instance).clearBatteryDropPercent();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class DeviceState extends GeneratedMessageLite<DeviceState, Builder> implements DeviceStateOrBuilder {
        public static final int BLUETOOTH_ON_FIELD_NUMBER = 4;
        public static final int CHARGING_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final DeviceState DEFAULT_INSTANCE = new DeviceState();
        public static final int SCREEN_ON_FIELD_NUMBER = 1;
        public static final int WIFI_ON_FIELD_NUMBER = 3;
        private static volatile Parser<DeviceState> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(DeviceState.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private boolean bluetoothOn_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean charging_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean screenOn_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean wifiOn_;

        private DeviceState() {
        }

        public static DeviceState parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceState parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceState parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceState parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceState parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceState parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceState parseFrom(InputStream input) throws IOException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceState parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceState parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceState) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceState parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceState) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceState parseFrom(CodedInputStream input) throws IOException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceState parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DeviceState prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static DeviceState getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceState> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasScreenOn() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getScreenOn() {
            return this.screenOn_;
        }

        /* access modifiers changed from: private */
        public void setScreenOn(boolean value) {
            this.bitField0_ |= 1;
            this.screenOn_ = value;
        }

        /* access modifiers changed from: private */
        public void clearScreenOn() {
            this.bitField0_ &= -2;
            this.screenOn_ = false;
        }

        public boolean hasCharging() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getCharging() {
            return this.charging_;
        }

        /* access modifiers changed from: private */
        public void setCharging(boolean value) {
            this.bitField0_ |= 2;
            this.charging_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCharging() {
            this.bitField0_ &= -3;
            this.charging_ = false;
        }

        public boolean hasWifiOn() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getWifiOn() {
            return this.wifiOn_;
        }

        /* access modifiers changed from: private */
        public void setWifiOn(boolean value) {
            this.bitField0_ |= 4;
            this.wifiOn_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWifiOn() {
            this.bitField0_ &= -5;
            this.wifiOn_ = false;
        }

        public boolean hasBluetoothOn() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getBluetoothOn() {
            return this.bluetoothOn_;
        }

        /* access modifiers changed from: private */
        public void setBluetoothOn(boolean value) {
            this.bitField0_ |= 8;
            this.bluetoothOn_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBluetoothOn() {
            this.bitField0_ &= -9;
            this.bluetoothOn_ = false;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DeviceState();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0007\u0000\u0002\u0007\u0001\u0003\u0007\u0002\u0004\u0007\u0003", new Object[]{"bitField0_", "screenOn_", "charging_", "wifiOn_", "bluetoothOn_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DeviceState> parser = PARSER;
                    if (parser == null) {
                        synchronized (DeviceState.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<DeviceState, Builder> implements DeviceStateOrBuilder {
            private Builder() {
                super(DeviceState.DEFAULT_INSTANCE);
            }

            public boolean hasScreenOn() {
                return ((DeviceState) this.instance).hasScreenOn();
            }

            public boolean getScreenOn() {
                return ((DeviceState) this.instance).getScreenOn();
            }

            public Builder setScreenOn(boolean value) {
                copyOnWrite();
                ((DeviceState) this.instance).setScreenOn(value);
                return this;
            }

            public Builder clearScreenOn() {
                copyOnWrite();
                ((DeviceState) this.instance).clearScreenOn();
                return this;
            }

            public boolean hasCharging() {
                return ((DeviceState) this.instance).hasCharging();
            }

            public boolean getCharging() {
                return ((DeviceState) this.instance).getCharging();
            }

            public Builder setCharging(boolean value) {
                copyOnWrite();
                ((DeviceState) this.instance).setCharging(value);
                return this;
            }

            public Builder clearCharging() {
                copyOnWrite();
                ((DeviceState) this.instance).clearCharging();
                return this;
            }

            public boolean hasWifiOn() {
                return ((DeviceState) this.instance).hasWifiOn();
            }

            public boolean getWifiOn() {
                return ((DeviceState) this.instance).getWifiOn();
            }

            public Builder setWifiOn(boolean value) {
                copyOnWrite();
                ((DeviceState) this.instance).setWifiOn(value);
                return this;
            }

            public Builder clearWifiOn() {
                copyOnWrite();
                ((DeviceState) this.instance).clearWifiOn();
                return this;
            }

            public boolean hasBluetoothOn() {
                return ((DeviceState) this.instance).hasBluetoothOn();
            }

            public boolean getBluetoothOn() {
                return ((DeviceState) this.instance).getBluetoothOn();
            }

            public Builder setBluetoothOn(boolean value) {
                copyOnWrite();
                ((DeviceState) this.instance).setBluetoothOn(value);
                return this;
            }

            public Builder clearBluetoothOn() {
                copyOnWrite();
                ((DeviceState) this.instance).clearBluetoothOn();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CpuProfilingMetric extends GeneratedMessageLite<CpuProfilingMetric, Builder> implements CpuProfilingMetricOrBuilder {
        /* access modifiers changed from: private */
        public static final CpuProfilingMetric DEFAULT_INSTANCE = new CpuProfilingMetric();
        public static final int DEVICE_METADATA_FIELD_NUMBER = 2;
        public static final int SAMPLES_PER_EPOCH_FIELD_NUMBER = 3;
        public static final int SAMPLE_BUFFER_SIZE_FIELD_NUMBER = 7;
        public static final int SAMPLE_DURATION_ACTUAL_FIELD_NUMBER = 5;
        public static final int SAMPLE_DURATION_SCHEDULED_FIELD_NUMBER = 4;
        public static final int SAMPLE_FREQUENCY_FIELD_NUMBER = 6;
        public static final int TRACE_BLOB_FIELD_NUMBER = 1;
        private static volatile Parser<CpuProfilingMetric> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(CpuProfilingMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private DeviceMetadata deviceMetadata_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private int sampleBufferSize_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int sampleDurationActual_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int sampleDurationScheduled_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int sampleFrequency_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.DOUBLE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private double samplesPerEpoch_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BYTES)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private ByteString traceBlob_ = ByteString.EMPTY;

        private CpuProfilingMetric() {
        }

        public static CpuProfilingMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CpuProfilingMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CpuProfilingMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CpuProfilingMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CpuProfilingMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CpuProfilingMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CpuProfilingMetric parseFrom(InputStream input) throws IOException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CpuProfilingMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CpuProfilingMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (CpuProfilingMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CpuProfilingMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CpuProfilingMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CpuProfilingMetric parseFrom(CodedInputStream input) throws IOException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CpuProfilingMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CpuProfilingMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CpuProfilingMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static CpuProfilingMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CpuProfilingMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasTraceBlob() {
            return (this.bitField0_ & 1) != 0;
        }

        public ByteString getTraceBlob() {
            return this.traceBlob_;
        }

        /* access modifiers changed from: private */
        public void setTraceBlob(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.traceBlob_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTraceBlob() {
            this.bitField0_ &= -2;
            this.traceBlob_ = getDefaultInstance().getTraceBlob();
        }

        public boolean hasDeviceMetadata() {
            return (this.bitField0_ & 2) != 0;
        }

        public DeviceMetadata getDeviceMetadata() {
            DeviceMetadata deviceMetadata = this.deviceMetadata_;
            return deviceMetadata == null ? DeviceMetadata.getDefaultInstance() : deviceMetadata;
        }

        /* access modifiers changed from: private */
        public void setDeviceMetadata(DeviceMetadata value) {
            if (value != null) {
                this.deviceMetadata_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setDeviceMetadata(DeviceMetadata.Builder builderForValue) {
            this.deviceMetadata_ = (DeviceMetadata) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeDeviceMetadata(DeviceMetadata value) {
            if (value != null) {
                DeviceMetadata deviceMetadata = this.deviceMetadata_;
                if (deviceMetadata == null || deviceMetadata == DeviceMetadata.getDefaultInstance()) {
                    this.deviceMetadata_ = value;
                } else {
                    this.deviceMetadata_ = (DeviceMetadata) ((DeviceMetadata.Builder) DeviceMetadata.newBuilder(this.deviceMetadata_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDeviceMetadata() {
            this.deviceMetadata_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasSamplesPerEpoch() {
            return (this.bitField0_ & 4) != 0;
        }

        public double getSamplesPerEpoch() {
            return this.samplesPerEpoch_;
        }

        /* access modifiers changed from: private */
        public void setSamplesPerEpoch(double value) {
            this.bitField0_ |= 4;
            this.samplesPerEpoch_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSamplesPerEpoch() {
            this.bitField0_ &= -5;
            this.samplesPerEpoch_ = 0.0d;
        }

        public boolean hasSampleDurationScheduled() {
            return (this.bitField0_ & 8) != 0;
        }

        public int getSampleDurationScheduled() {
            return this.sampleDurationScheduled_;
        }

        /* access modifiers changed from: private */
        public void setSampleDurationScheduled(int value) {
            this.bitField0_ |= 8;
            this.sampleDurationScheduled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSampleDurationScheduled() {
            this.bitField0_ &= -9;
            this.sampleDurationScheduled_ = 0;
        }

        public boolean hasSampleDurationActual() {
            return (this.bitField0_ & 16) != 0;
        }

        public int getSampleDurationActual() {
            return this.sampleDurationActual_;
        }

        /* access modifiers changed from: private */
        public void setSampleDurationActual(int value) {
            this.bitField0_ |= 16;
            this.sampleDurationActual_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSampleDurationActual() {
            this.bitField0_ &= -17;
            this.sampleDurationActual_ = 0;
        }

        public boolean hasSampleFrequency() {
            return (this.bitField0_ & 32) != 0;
        }

        public int getSampleFrequency() {
            return this.sampleFrequency_;
        }

        /* access modifiers changed from: private */
        public void setSampleFrequency(int value) {
            this.bitField0_ |= 32;
            this.sampleFrequency_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSampleFrequency() {
            this.bitField0_ &= -33;
            this.sampleFrequency_ = 0;
        }

        public boolean hasSampleBufferSize() {
            return (this.bitField0_ & 64) != 0;
        }

        public int getSampleBufferSize() {
            return this.sampleBufferSize_;
        }

        /* access modifiers changed from: private */
        public void setSampleBufferSize(int value) {
            this.bitField0_ |= 64;
            this.sampleBufferSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSampleBufferSize() {
            this.bitField0_ &= -65;
            this.sampleBufferSize_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CpuProfilingMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001\n\u0000\u0002\t\u0001\u0003\u0000\u0002\u0004\u0004\u0003\u0005\u0004\u0004\u0006\u0004\u0005\u0007\u0004\u0006", new Object[]{"bitField0_", "traceBlob_", "deviceMetadata_", "samplesPerEpoch_", "sampleDurationScheduled_", "sampleDurationActual_", "sampleFrequency_", "sampleBufferSize_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CpuProfilingMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (CpuProfilingMetric.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<CpuProfilingMetric, Builder> implements CpuProfilingMetricOrBuilder {
            private Builder() {
                super(CpuProfilingMetric.DEFAULT_INSTANCE);
            }

            public boolean hasTraceBlob() {
                return ((CpuProfilingMetric) this.instance).hasTraceBlob();
            }

            public ByteString getTraceBlob() {
                return ((CpuProfilingMetric) this.instance).getTraceBlob();
            }

            public Builder setTraceBlob(ByteString value) {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).setTraceBlob(value);
                return this;
            }

            public Builder clearTraceBlob() {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).clearTraceBlob();
                return this;
            }

            public boolean hasDeviceMetadata() {
                return ((CpuProfilingMetric) this.instance).hasDeviceMetadata();
            }

            public DeviceMetadata getDeviceMetadata() {
                return ((CpuProfilingMetric) this.instance).getDeviceMetadata();
            }

            public Builder setDeviceMetadata(DeviceMetadata value) {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).setDeviceMetadata(value);
                return this;
            }

            public Builder setDeviceMetadata(DeviceMetadata.Builder builderForValue) {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).setDeviceMetadata(builderForValue);
                return this;
            }

            public Builder mergeDeviceMetadata(DeviceMetadata value) {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).mergeDeviceMetadata(value);
                return this;
            }

            public Builder clearDeviceMetadata() {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).clearDeviceMetadata();
                return this;
            }

            public boolean hasSamplesPerEpoch() {
                return ((CpuProfilingMetric) this.instance).hasSamplesPerEpoch();
            }

            public double getSamplesPerEpoch() {
                return ((CpuProfilingMetric) this.instance).getSamplesPerEpoch();
            }

            public Builder setSamplesPerEpoch(double value) {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).setSamplesPerEpoch(value);
                return this;
            }

            public Builder clearSamplesPerEpoch() {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).clearSamplesPerEpoch();
                return this;
            }

            public boolean hasSampleDurationScheduled() {
                return ((CpuProfilingMetric) this.instance).hasSampleDurationScheduled();
            }

            public int getSampleDurationScheduled() {
                return ((CpuProfilingMetric) this.instance).getSampleDurationScheduled();
            }

            public Builder setSampleDurationScheduled(int value) {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).setSampleDurationScheduled(value);
                return this;
            }

            public Builder clearSampleDurationScheduled() {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).clearSampleDurationScheduled();
                return this;
            }

            public boolean hasSampleDurationActual() {
                return ((CpuProfilingMetric) this.instance).hasSampleDurationActual();
            }

            public int getSampleDurationActual() {
                return ((CpuProfilingMetric) this.instance).getSampleDurationActual();
            }

            public Builder setSampleDurationActual(int value) {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).setSampleDurationActual(value);
                return this;
            }

            public Builder clearSampleDurationActual() {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).clearSampleDurationActual();
                return this;
            }

            public boolean hasSampleFrequency() {
                return ((CpuProfilingMetric) this.instance).hasSampleFrequency();
            }

            public int getSampleFrequency() {
                return ((CpuProfilingMetric) this.instance).getSampleFrequency();
            }

            public Builder setSampleFrequency(int value) {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).setSampleFrequency(value);
                return this;
            }

            public Builder clearSampleFrequency() {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).clearSampleFrequency();
                return this;
            }

            public boolean hasSampleBufferSize() {
                return ((CpuProfilingMetric) this.instance).hasSampleBufferSize();
            }

            public int getSampleBufferSize() {
                return ((CpuProfilingMetric) this.instance).getSampleBufferSize();
            }

            public Builder setSampleBufferSize(int value) {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).setSampleBufferSize(value);
                return this;
            }

            public Builder clearSampleBufferSize() {
                copyOnWrite();
                ((CpuProfilingMetric) this.instance).clearSampleBufferSize();
                return this;
            }
        }
    }
}
