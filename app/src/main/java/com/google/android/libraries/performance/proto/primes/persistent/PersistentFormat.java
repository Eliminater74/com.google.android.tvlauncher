package com.google.android.libraries.performance.proto.primes.persistent;

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
import logs.proto.wireless.performance.mobile.BatteryMetric;
import logs.proto.wireless.performance.mobile.ExtensionMetric;

public final class PersistentFormat {

    public interface BatterySnapshotOrBuilder extends MessageLiteOrBuilder {
        long getCurrentTime();

        String getCustomEventName();

        ByteString getCustomEventNameBytes();

        long getElapsedTime();

        boolean getIsEventNameConstant();

        ExtensionMetric.MetricExtension getMetricExtension();

        long getPrimesVersion();

        int getSampleInfo();

        BatteryMetric.UidHealthProto getUidHealthProto();

        long getVersionNameHash();

        boolean hasCurrentTime();

        boolean hasCustomEventName();

        boolean hasElapsedTime();

        boolean hasIsEventNameConstant();

        boolean hasMetricExtension();

        boolean hasPrimesVersion();

        boolean hasSampleInfo();

        boolean hasUidHealthProto();

        boolean hasVersionNameHash();
    }

    public interface MemorySampleOrBuilder extends MessageLiteOrBuilder {
        int getTotalPssKb();

        boolean hasTotalPssKb();
    }

    public interface PersistentMemorySamplesOrBuilder extends MessageLiteOrBuilder {
        MemorySample getSamples(int i);

        int getSamplesCount();

        List<MemorySample> getSamplesList();

        int getVersionNameHash();

        boolean hasVersionNameHash();
    }

    private PersistentFormat() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class BatterySnapshot extends GeneratedMessageLite<BatterySnapshot, Builder> implements BatterySnapshotOrBuilder {
        public static final int CURRENT_TIME_FIELD_NUMBER = 3;
        public static final int CUSTOM_EVENT_NAME_FIELD_NUMBER = 7;
        /* access modifiers changed from: private */
        public static final BatterySnapshot DEFAULT_INSTANCE = new BatterySnapshot();
        public static final int ELAPSED_TIME_FIELD_NUMBER = 2;
        public static final int IS_EVENT_NAME_CONSTANT_FIELD_NUMBER = 8;
        public static final int METRIC_EXTENSION_FIELD_NUMBER = 9;
        private static volatile Parser<BatterySnapshot> PARSER = null;
        public static final int PRIMES_VERSION_FIELD_NUMBER = 4;
        public static final int SAMPLE_INFO_FIELD_NUMBER = 6;
        public static final int UID_HEALTH_PROTO_FIELD_NUMBER = 1;
        public static final int VERSION_NAME_HASH_FIELD_NUMBER = 5;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long currentTime_;
        @ProtoField(fieldNumber = 7, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private String customEventName_ = "";
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long elapsedTime_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private boolean isEventNameConstant_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private ExtensionMetric.MetricExtension metricExtension_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private long primesVersion_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int sampleInfo_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private BatteryMetric.UidHealthProto uidHealthProto_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private long versionNameHash_;

        private BatterySnapshot() {
        }

        public boolean hasUidHealthProto() {
            return (this.bitField0_ & 1) != 0;
        }

        public BatteryMetric.UidHealthProto getUidHealthProto() {
            BatteryMetric.UidHealthProto uidHealthProto = this.uidHealthProto_;
            return uidHealthProto == null ? BatteryMetric.UidHealthProto.getDefaultInstance() : uidHealthProto;
        }

        /* access modifiers changed from: private */
        public void setUidHealthProto(BatteryMetric.UidHealthProto value) {
            if (value != null) {
                this.uidHealthProto_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setUidHealthProto(BatteryMetric.UidHealthProto.Builder builderForValue) {
            this.uidHealthProto_ = (BatteryMetric.UidHealthProto) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeUidHealthProto(BatteryMetric.UidHealthProto value) {
            if (value != null) {
                BatteryMetric.UidHealthProto uidHealthProto = this.uidHealthProto_;
                if (uidHealthProto == null || uidHealthProto == BatteryMetric.UidHealthProto.getDefaultInstance()) {
                    this.uidHealthProto_ = value;
                } else {
                    this.uidHealthProto_ = (BatteryMetric.UidHealthProto) ((BatteryMetric.UidHealthProto.Builder) BatteryMetric.UidHealthProto.newBuilder(this.uidHealthProto_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearUidHealthProto() {
            this.uidHealthProto_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasElapsedTime() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getElapsedTime() {
            return this.elapsedTime_;
        }

        /* access modifiers changed from: private */
        public void setElapsedTime(long value) {
            this.bitField0_ |= 2;
            this.elapsedTime_ = value;
        }

        /* access modifiers changed from: private */
        public void clearElapsedTime() {
            this.bitField0_ &= -3;
            this.elapsedTime_ = 0;
        }

        public boolean hasCurrentTime() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getCurrentTime() {
            return this.currentTime_;
        }

        /* access modifiers changed from: private */
        public void setCurrentTime(long value) {
            this.bitField0_ |= 4;
            this.currentTime_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCurrentTime() {
            this.bitField0_ &= -5;
            this.currentTime_ = 0;
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

        public boolean hasVersionNameHash() {
            return (this.bitField0_ & 16) != 0;
        }

        public long getVersionNameHash() {
            return this.versionNameHash_;
        }

        /* access modifiers changed from: private */
        public void setVersionNameHash(long value) {
            this.bitField0_ |= 16;
            this.versionNameHash_ = value;
        }

        /* access modifiers changed from: private */
        public void clearVersionNameHash() {
            this.bitField0_ &= -17;
            this.versionNameHash_ = 0;
        }

        public boolean hasSampleInfo() {
            return (this.bitField0_ & 32) != 0;
        }

        public int getSampleInfo() {
            return this.sampleInfo_;
        }

        /* access modifiers changed from: private */
        public void setSampleInfo(int value) {
            this.bitField0_ |= 32;
            this.sampleInfo_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSampleInfo() {
            this.bitField0_ &= -33;
            this.sampleInfo_ = 0;
        }

        public boolean hasCustomEventName() {
            return (this.bitField0_ & 64) != 0;
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
                this.bitField0_ |= 64;
                this.customEventName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCustomEventName() {
            this.bitField0_ &= -65;
            this.customEventName_ = getDefaultInstance().getCustomEventName();
        }

        /* access modifiers changed from: private */
        public void setCustomEventNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.customEventName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasIsEventNameConstant() {
            return (this.bitField0_ & 128) != 0;
        }

        public boolean getIsEventNameConstant() {
            return this.isEventNameConstant_;
        }

        /* access modifiers changed from: private */
        public void setIsEventNameConstant(boolean value) {
            this.bitField0_ |= 128;
            this.isEventNameConstant_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsEventNameConstant() {
            this.bitField0_ &= -129;
            this.isEventNameConstant_ = false;
        }

        public boolean hasMetricExtension() {
            return (this.bitField0_ & 256) != 0;
        }

        public ExtensionMetric.MetricExtension getMetricExtension() {
            ExtensionMetric.MetricExtension metricExtension = this.metricExtension_;
            return metricExtension == null ? ExtensionMetric.MetricExtension.getDefaultInstance() : metricExtension;
        }

        /* access modifiers changed from: private */
        public void setMetricExtension(ExtensionMetric.MetricExtension value) {
            if (value != null) {
                this.metricExtension_ = value;
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
            this.metricExtension_ = (ExtensionMetric.MetricExtension) builderForValue.build();
            this.bitField0_ |= 256;
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
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMetricExtension() {
            this.metricExtension_ = null;
            this.bitField0_ &= -257;
        }

        public static BatterySnapshot parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BatterySnapshot parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BatterySnapshot parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BatterySnapshot parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BatterySnapshot parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BatterySnapshot parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BatterySnapshot parseFrom(InputStream input) throws IOException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BatterySnapshot parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BatterySnapshot parseDelimitedFrom(InputStream input) throws IOException {
            return (BatterySnapshot) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static BatterySnapshot parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BatterySnapshot) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BatterySnapshot parseFrom(CodedInputStream input) throws IOException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BatterySnapshot parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BatterySnapshot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(BatterySnapshot prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<BatterySnapshot, Builder> implements BatterySnapshotOrBuilder {
            private Builder() {
                super(BatterySnapshot.DEFAULT_INSTANCE);
            }

            public boolean hasUidHealthProto() {
                return ((BatterySnapshot) this.instance).hasUidHealthProto();
            }

            public BatteryMetric.UidHealthProto getUidHealthProto() {
                return ((BatterySnapshot) this.instance).getUidHealthProto();
            }

            public Builder setUidHealthProto(BatteryMetric.UidHealthProto value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setUidHealthProto(value);
                return this;
            }

            public Builder setUidHealthProto(BatteryMetric.UidHealthProto.Builder builderForValue) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setUidHealthProto(builderForValue);
                return this;
            }

            public Builder mergeUidHealthProto(BatteryMetric.UidHealthProto value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).mergeUidHealthProto(value);
                return this;
            }

            public Builder clearUidHealthProto() {
                copyOnWrite();
                ((BatterySnapshot) this.instance).clearUidHealthProto();
                return this;
            }

            public boolean hasElapsedTime() {
                return ((BatterySnapshot) this.instance).hasElapsedTime();
            }

            public long getElapsedTime() {
                return ((BatterySnapshot) this.instance).getElapsedTime();
            }

            public Builder setElapsedTime(long value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setElapsedTime(value);
                return this;
            }

            public Builder clearElapsedTime() {
                copyOnWrite();
                ((BatterySnapshot) this.instance).clearElapsedTime();
                return this;
            }

            public boolean hasCurrentTime() {
                return ((BatterySnapshot) this.instance).hasCurrentTime();
            }

            public long getCurrentTime() {
                return ((BatterySnapshot) this.instance).getCurrentTime();
            }

            public Builder setCurrentTime(long value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setCurrentTime(value);
                return this;
            }

            public Builder clearCurrentTime() {
                copyOnWrite();
                ((BatterySnapshot) this.instance).clearCurrentTime();
                return this;
            }

            public boolean hasPrimesVersion() {
                return ((BatterySnapshot) this.instance).hasPrimesVersion();
            }

            public long getPrimesVersion() {
                return ((BatterySnapshot) this.instance).getPrimesVersion();
            }

            public Builder setPrimesVersion(long value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setPrimesVersion(value);
                return this;
            }

            public Builder clearPrimesVersion() {
                copyOnWrite();
                ((BatterySnapshot) this.instance).clearPrimesVersion();
                return this;
            }

            public boolean hasVersionNameHash() {
                return ((BatterySnapshot) this.instance).hasVersionNameHash();
            }

            public long getVersionNameHash() {
                return ((BatterySnapshot) this.instance).getVersionNameHash();
            }

            public Builder setVersionNameHash(long value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setVersionNameHash(value);
                return this;
            }

            public Builder clearVersionNameHash() {
                copyOnWrite();
                ((BatterySnapshot) this.instance).clearVersionNameHash();
                return this;
            }

            public boolean hasSampleInfo() {
                return ((BatterySnapshot) this.instance).hasSampleInfo();
            }

            public int getSampleInfo() {
                return ((BatterySnapshot) this.instance).getSampleInfo();
            }

            public Builder setSampleInfo(int value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setSampleInfo(value);
                return this;
            }

            public Builder clearSampleInfo() {
                copyOnWrite();
                ((BatterySnapshot) this.instance).clearSampleInfo();
                return this;
            }

            public boolean hasCustomEventName() {
                return ((BatterySnapshot) this.instance).hasCustomEventName();
            }

            public String getCustomEventName() {
                return ((BatterySnapshot) this.instance).getCustomEventName();
            }

            public ByteString getCustomEventNameBytes() {
                return ((BatterySnapshot) this.instance).getCustomEventNameBytes();
            }

            public Builder setCustomEventName(String value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setCustomEventName(value);
                return this;
            }

            public Builder clearCustomEventName() {
                copyOnWrite();
                ((BatterySnapshot) this.instance).clearCustomEventName();
                return this;
            }

            public Builder setCustomEventNameBytes(ByteString value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setCustomEventNameBytes(value);
                return this;
            }

            public boolean hasIsEventNameConstant() {
                return ((BatterySnapshot) this.instance).hasIsEventNameConstant();
            }

            public boolean getIsEventNameConstant() {
                return ((BatterySnapshot) this.instance).getIsEventNameConstant();
            }

            public Builder setIsEventNameConstant(boolean value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setIsEventNameConstant(value);
                return this;
            }

            public Builder clearIsEventNameConstant() {
                copyOnWrite();
                ((BatterySnapshot) this.instance).clearIsEventNameConstant();
                return this;
            }

            public boolean hasMetricExtension() {
                return ((BatterySnapshot) this.instance).hasMetricExtension();
            }

            public ExtensionMetric.MetricExtension getMetricExtension() {
                return ((BatterySnapshot) this.instance).getMetricExtension();
            }

            public Builder setMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setMetricExtension(value);
                return this;
            }

            public Builder setMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).setMetricExtension(builderForValue);
                return this;
            }

            public Builder mergeMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((BatterySnapshot) this.instance).mergeMetricExtension(value);
                return this;
            }

            public Builder clearMetricExtension() {
                copyOnWrite();
                ((BatterySnapshot) this.instance).clearMetricExtension();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new BatterySnapshot();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0000\u0000\u0001\t\u0000\u0002\u0002\u0001\u0003\u0002\u0002\u0004\u0002\u0003\u0005\u0005\u0004\u0006\u0004\u0005\u0007\b\u0006\b\u0007\u0007\t\t\b", new Object[]{"bitField0_", "uidHealthProto_", "elapsedTime_", "currentTime_", "primesVersion_", "versionNameHash_", "sampleInfo_", "customEventName_", "isEventNameConstant_", "metricExtension_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<BatterySnapshot> parser = PARSER;
                    if (parser == null) {
                        synchronized (BatterySnapshot.class) {
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
            GeneratedMessageLite.registerDefaultInstance(BatterySnapshot.class, DEFAULT_INSTANCE);
        }

        public static BatterySnapshot getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BatterySnapshot> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PersistentMemorySamples extends GeneratedMessageLite<PersistentMemorySamples, Builder> implements PersistentMemorySamplesOrBuilder {
        /* access modifiers changed from: private */
        public static final PersistentMemorySamples DEFAULT_INSTANCE = new PersistentMemorySamples();
        private static volatile Parser<PersistentMemorySamples> PARSER = null;
        public static final int SAMPLES_FIELD_NUMBER = 1;
        public static final int VERSION_NAME_HASH_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<MemorySample> samples_ = emptyProtobufList();
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int versionNameHash_;

        private PersistentMemorySamples() {
        }

        public List<MemorySample> getSamplesList() {
            return this.samples_;
        }

        public List<? extends MemorySampleOrBuilder> getSamplesOrBuilderList() {
            return this.samples_;
        }

        public int getSamplesCount() {
            return this.samples_.size();
        }

        public MemorySample getSamples(int index) {
            return this.samples_.get(index);
        }

        public MemorySampleOrBuilder getSamplesOrBuilder(int index) {
            return this.samples_.get(index);
        }

        private void ensureSamplesIsMutable() {
            if (!this.samples_.isModifiable()) {
                this.samples_ = GeneratedMessageLite.mutableCopy(this.samples_);
            }
        }

        /* access modifiers changed from: private */
        public void setSamples(int index, MemorySample value) {
            if (value != null) {
                ensureSamplesIsMutable();
                this.samples_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSamples(int index, MemorySample.Builder builderForValue) {
            ensureSamplesIsMutable();
            this.samples_.set(index, (MemorySample) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSamples(MemorySample value) {
            if (value != null) {
                ensureSamplesIsMutable();
                this.samples_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSamples(int index, MemorySample value) {
            if (value != null) {
                ensureSamplesIsMutable();
                this.samples_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSamples(MemorySample.Builder builderForValue) {
            ensureSamplesIsMutable();
            this.samples_.add((MemorySample) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSamples(int index, MemorySample.Builder builderForValue) {
            ensureSamplesIsMutable();
            this.samples_.add(index, (MemorySample) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.android.libraries.performance.proto.primes.persistent.PersistentFormat$MemorySample>, com.google.protobuf.Internal$ProtobufList<com.google.android.libraries.performance.proto.primes.persistent.PersistentFormat$MemorySample>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSamples(Iterable<? extends MemorySample> values) {
            ensureSamplesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.samples_);
        }

        /* access modifiers changed from: private */
        public void clearSamples() {
            this.samples_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSamples(int index) {
            ensureSamplesIsMutable();
            this.samples_.remove(index);
        }

        public boolean hasVersionNameHash() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getVersionNameHash() {
            return this.versionNameHash_;
        }

        /* access modifiers changed from: private */
        public void setVersionNameHash(int value) {
            this.bitField0_ |= 1;
            this.versionNameHash_ = value;
        }

        /* access modifiers changed from: private */
        public void clearVersionNameHash() {
            this.bitField0_ &= -2;
            this.versionNameHash_ = 0;
        }

        public static PersistentMemorySamples parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PersistentMemorySamples parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PersistentMemorySamples parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PersistentMemorySamples parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PersistentMemorySamples parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PersistentMemorySamples parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PersistentMemorySamples parseFrom(InputStream input) throws IOException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PersistentMemorySamples parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PersistentMemorySamples parseDelimitedFrom(InputStream input) throws IOException {
            return (PersistentMemorySamples) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PersistentMemorySamples parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PersistentMemorySamples) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PersistentMemorySamples parseFrom(CodedInputStream input) throws IOException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PersistentMemorySamples parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PersistentMemorySamples) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PersistentMemorySamples prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PersistentMemorySamples, Builder> implements PersistentMemorySamplesOrBuilder {
            private Builder() {
                super(PersistentMemorySamples.DEFAULT_INSTANCE);
            }

            public List<MemorySample> getSamplesList() {
                return Collections.unmodifiableList(((PersistentMemorySamples) this.instance).getSamplesList());
            }

            public int getSamplesCount() {
                return ((PersistentMemorySamples) this.instance).getSamplesCount();
            }

            public MemorySample getSamples(int index) {
                return ((PersistentMemorySamples) this.instance).getSamples(index);
            }

            public Builder setSamples(int index, MemorySample value) {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).setSamples(index, value);
                return this;
            }

            public Builder setSamples(int index, MemorySample.Builder builderForValue) {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).setSamples(index, builderForValue);
                return this;
            }

            public Builder addSamples(MemorySample value) {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).addSamples(value);
                return this;
            }

            public Builder addSamples(int index, MemorySample value) {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).addSamples(index, value);
                return this;
            }

            public Builder addSamples(MemorySample.Builder builderForValue) {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).addSamples(builderForValue);
                return this;
            }

            public Builder addSamples(int index, MemorySample.Builder builderForValue) {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).addSamples(index, builderForValue);
                return this;
            }

            public Builder addAllSamples(Iterable<? extends MemorySample> values) {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).addAllSamples(values);
                return this;
            }

            public Builder clearSamples() {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).clearSamples();
                return this;
            }

            public Builder removeSamples(int index) {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).removeSamples(index);
                return this;
            }

            public boolean hasVersionNameHash() {
                return ((PersistentMemorySamples) this.instance).hasVersionNameHash();
            }

            public int getVersionNameHash() {
                return ((PersistentMemorySamples) this.instance).getVersionNameHash();
            }

            public Builder setVersionNameHash(int value) {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).setVersionNameHash(value);
                return this;
            }

            public Builder clearVersionNameHash() {
                copyOnWrite();
                ((PersistentMemorySamples) this.instance).clearVersionNameHash();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PersistentMemorySamples();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002\u0004\u0000", new Object[]{"bitField0_", "samples_", MemorySample.class, "versionNameHash_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PersistentMemorySamples> parser = PARSER;
                    if (parser == null) {
                        synchronized (PersistentMemorySamples.class) {
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
            GeneratedMessageLite.registerDefaultInstance(PersistentMemorySamples.class, DEFAULT_INSTANCE);
        }

        public static PersistentMemorySamples getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PersistentMemorySamples> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MemorySample extends GeneratedMessageLite<MemorySample, Builder> implements MemorySampleOrBuilder {
        /* access modifiers changed from: private */
        public static final MemorySample DEFAULT_INSTANCE = new MemorySample();
        private static volatile Parser<MemorySample> PARSER = null;
        public static final int TOTAL_PSS_KB_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int totalPssKb_;

        private MemorySample() {
        }

        public boolean hasTotalPssKb() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getTotalPssKb() {
            return this.totalPssKb_;
        }

        /* access modifiers changed from: private */
        public void setTotalPssKb(int value) {
            this.bitField0_ |= 1;
            this.totalPssKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalPssKb() {
            this.bitField0_ &= -2;
            this.totalPssKb_ = 0;
        }

        public static MemorySample parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemorySample parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemorySample parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemorySample parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemorySample parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MemorySample parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MemorySample parseFrom(InputStream input) throws IOException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MemorySample parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MemorySample parseDelimitedFrom(InputStream input) throws IOException {
            return (MemorySample) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MemorySample parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemorySample) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MemorySample parseFrom(CodedInputStream input) throws IOException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MemorySample parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MemorySample) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MemorySample prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<MemorySample, Builder> implements MemorySampleOrBuilder {
            private Builder() {
                super(MemorySample.DEFAULT_INSTANCE);
            }

            public boolean hasTotalPssKb() {
                return ((MemorySample) this.instance).hasTotalPssKb();
            }

            public int getTotalPssKb() {
                return ((MemorySample) this.instance).getTotalPssKb();
            }

            public Builder setTotalPssKb(int value) {
                copyOnWrite();
                ((MemorySample) this.instance).setTotalPssKb(value);
                return this;
            }

            public Builder clearTotalPssKb() {
                copyOnWrite();
                ((MemorySample) this.instance).clearTotalPssKb();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MemorySample();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004\u0000", new Object[]{"bitField0_", "totalPssKb_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MemorySample> parser = PARSER;
                    if (parser == null) {
                        synchronized (MemorySample.class) {
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
            GeneratedMessageLite.registerDefaultInstance(MemorySample.class, DEFAULT_INSTANCE);
        }

        public static MemorySample getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MemorySample> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
