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

public final class AggregatedMetricProto {

    private AggregatedMetricProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface AggregatedDataOrBuilder extends MessageLiteOrBuilder {
        int getCount();

        long getMax();

        long getMin();

        long getSum();

        boolean hasCount();

        boolean hasMax();

        boolean hasMin();

        boolean hasSum();
    }

    public interface AggregatedMetricOrBuilder extends MessageLiteOrBuilder {
        AggregatedData getAggregatedData();

        AggregatedMetric.Identifier getIdentifier();

        boolean hasAggregatedData();

        boolean hasIdentifier();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AggregatedMetric extends GeneratedMessageLite<AggregatedMetric, Builder> implements AggregatedMetricOrBuilder {
        public static final int AGGREGATED_DATA_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final AggregatedMetric DEFAULT_INSTANCE = new AggregatedMetric();
        public static final int IDENTIFIER_FIELD_NUMBER = 1;
        private static volatile Parser<AggregatedMetric> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(AggregatedMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private AggregatedData aggregatedData_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private Identifier identifier_;

        private AggregatedMetric() {
        }

        public static AggregatedMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AggregatedMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AggregatedMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AggregatedMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AggregatedMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AggregatedMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AggregatedMetric parseFrom(InputStream input) throws IOException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AggregatedMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AggregatedMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (AggregatedMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AggregatedMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AggregatedMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AggregatedMetric parseFrom(CodedInputStream input) throws IOException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AggregatedMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AggregatedMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AggregatedMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static AggregatedMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AggregatedMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasIdentifier() {
            return (this.bitField0_ & 1) != 0;
        }

        public Identifier getIdentifier() {
            Identifier identifier = this.identifier_;
            return identifier == null ? Identifier.getDefaultInstance() : identifier;
        }

        /* access modifiers changed from: private */
        public void setIdentifier(Identifier value) {
            if (value != null) {
                this.identifier_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setIdentifier(Identifier.Builder builderForValue) {
            this.identifier_ = (Identifier) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeIdentifier(Identifier value) {
            if (value != null) {
                Identifier identifier = this.identifier_;
                if (identifier == null || identifier == Identifier.getDefaultInstance()) {
                    this.identifier_ = value;
                } else {
                    this.identifier_ = (Identifier) ((Identifier.Builder) Identifier.newBuilder(this.identifier_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearIdentifier() {
            this.identifier_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasAggregatedData() {
            return (this.bitField0_ & 2) != 0;
        }

        public AggregatedData getAggregatedData() {
            AggregatedData aggregatedData = this.aggregatedData_;
            return aggregatedData == null ? AggregatedData.getDefaultInstance() : aggregatedData;
        }

        /* access modifiers changed from: private */
        public void setAggregatedData(AggregatedData value) {
            if (value != null) {
                this.aggregatedData_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAggregatedData(AggregatedData.Builder builderForValue) {
            this.aggregatedData_ = (AggregatedData) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeAggregatedData(AggregatedData value) {
            if (value != null) {
                AggregatedData aggregatedData = this.aggregatedData_;
                if (aggregatedData == null || aggregatedData == AggregatedData.getDefaultInstance()) {
                    this.aggregatedData_ = value;
                } else {
                    this.aggregatedData_ = (AggregatedData) ((AggregatedData.Builder) AggregatedData.newBuilder(this.aggregatedData_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAggregatedData() {
            this.aggregatedData_ = null;
            this.bitField0_ &= -3;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AggregatedMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001", new Object[]{"bitField0_", "identifier_", "aggregatedData_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AggregatedMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (AggregatedMetric.class) {
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

        public interface IdentifierOrBuilder extends MessageLiteOrBuilder {
            String getComponentName();

            ByteString getComponentNameBytes();

            String getConstantComponentName();

            ByteString getConstantComponentNameBytes();

            String getConstantCounterName();

            ByteString getConstantCounterNameBytes();

            String getCustomCounterName();

            ByteString getCustomCounterNameBytes();

            long getHashedComponentName();

            long getHashedCounterName();

            Identifier.Metric getMetric();

            boolean hasComponentName();

            boolean hasConstantComponentName();

            boolean hasConstantCounterName();

            boolean hasCustomCounterName();

            boolean hasHashedComponentName();

            boolean hasHashedCounterName();

            boolean hasMetric();
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class Identifier extends GeneratedMessageLite<Identifier, Builder> implements IdentifierOrBuilder {
            public static final int COMPONENT_NAME_FIELD_NUMBER = 1;
            public static final int CONSTANT_COMPONENT_NAME_FIELD_NUMBER = 4;
            public static final int CONSTANT_COUNTER_NAME_FIELD_NUMBER = 6;
            public static final int CUSTOM_COUNTER_NAME_FIELD_NUMBER = 3;
            /* access modifiers changed from: private */
            public static final Identifier DEFAULT_INSTANCE = new Identifier();
            public static final int HASHED_COMPONENT_NAME_FIELD_NUMBER = 5;
            public static final int HASHED_COUNTER_NAME_FIELD_NUMBER = 7;
            public static final int METRIC_FIELD_NUMBER = 2;
            private static volatile Parser<Identifier> PARSER;

            static {
                GeneratedMessageLite.registerDefaultInstance(Identifier.class, DEFAULT_INSTANCE);
            }

            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private String componentName_ = "";
            @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private String constantComponentName_ = "";
            @ProtoField(fieldNumber = 6, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
            private String constantCounterName_ = "";
            @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
            private String customCounterName_ = "";
            @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.FIXED64)
            @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
            private long hashedComponentName_;
            @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.FIXED64)
            @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
            private long hashedCounterName_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
            @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
            private int metric_;

            private Identifier() {
            }

            public static Identifier parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Identifier parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Identifier parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Identifier parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Identifier parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Identifier parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Identifier parseFrom(InputStream input) throws IOException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Identifier parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Identifier parseDelimitedFrom(InputStream input) throws IOException {
                return (Identifier) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static Identifier parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Identifier) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Identifier parseFrom(CodedInputStream input) throws IOException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Identifier parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Identifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(Identifier prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static Identifier getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Identifier> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }

            public boolean hasComponentName() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getComponentName() {
                return this.componentName_;
            }

            /* access modifiers changed from: private */
            public void setComponentName(String value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.componentName_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            public ByteString getComponentNameBytes() {
                return ByteString.copyFromUtf8(this.componentName_);
            }

            /* access modifiers changed from: private */
            public void setComponentNameBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.componentName_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearComponentName() {
                this.bitField0_ &= -2;
                this.componentName_ = getDefaultInstance().getComponentName();
            }

            public boolean hasConstantComponentName() {
                return (this.bitField0_ & 2) != 0;
            }

            public String getConstantComponentName() {
                return this.constantComponentName_;
            }

            /* access modifiers changed from: private */
            public void setConstantComponentName(String value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.constantComponentName_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            public ByteString getConstantComponentNameBytes() {
                return ByteString.copyFromUtf8(this.constantComponentName_);
            }

            /* access modifiers changed from: private */
            public void setConstantComponentNameBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.constantComponentName_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearConstantComponentName() {
                this.bitField0_ &= -3;
                this.constantComponentName_ = getDefaultInstance().getConstantComponentName();
            }

            public boolean hasHashedComponentName() {
                return (this.bitField0_ & 4) != 0;
            }

            public long getHashedComponentName() {
                return this.hashedComponentName_;
            }

            /* access modifiers changed from: private */
            public void setHashedComponentName(long value) {
                this.bitField0_ |= 4;
                this.hashedComponentName_ = value;
            }

            /* access modifiers changed from: private */
            public void clearHashedComponentName() {
                this.bitField0_ &= -5;
                this.hashedComponentName_ = 0;
            }

            public boolean hasMetric() {
                return (this.bitField0_ & 8) != 0;
            }

            public Metric getMetric() {
                Metric result = Metric.forNumber(this.metric_);
                return result == null ? Metric.UNKNOWN_METRIC : result;
            }

            /* access modifiers changed from: private */
            public void setMetric(Metric value) {
                if (value != null) {
                    this.bitField0_ |= 8;
                    this.metric_ = value.getNumber();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearMetric() {
                this.bitField0_ &= -9;
                this.metric_ = 0;
            }

            public boolean hasCustomCounterName() {
                return (this.bitField0_ & 16) != 0;
            }

            public String getCustomCounterName() {
                return this.customCounterName_;
            }

            /* access modifiers changed from: private */
            public void setCustomCounterName(String value) {
                if (value != null) {
                    this.bitField0_ |= 16;
                    this.customCounterName_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            public ByteString getCustomCounterNameBytes() {
                return ByteString.copyFromUtf8(this.customCounterName_);
            }

            /* access modifiers changed from: private */
            public void setCustomCounterNameBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 16;
                    this.customCounterName_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearCustomCounterName() {
                this.bitField0_ &= -17;
                this.customCounterName_ = getDefaultInstance().getCustomCounterName();
            }

            public boolean hasConstantCounterName() {
                return (this.bitField0_ & 32) != 0;
            }

            public String getConstantCounterName() {
                return this.constantCounterName_;
            }

            /* access modifiers changed from: private */
            public void setConstantCounterName(String value) {
                if (value != null) {
                    this.bitField0_ |= 32;
                    this.constantCounterName_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            public ByteString getConstantCounterNameBytes() {
                return ByteString.copyFromUtf8(this.constantCounterName_);
            }

            /* access modifiers changed from: private */
            public void setConstantCounterNameBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 32;
                    this.constantCounterName_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearConstantCounterName() {
                this.bitField0_ &= -33;
                this.constantCounterName_ = getDefaultInstance().getConstantCounterName();
            }

            public boolean hasHashedCounterName() {
                return (this.bitField0_ & 64) != 0;
            }

            public long getHashedCounterName() {
                return this.hashedCounterName_;
            }

            /* access modifiers changed from: private */
            public void setHashedCounterName(long value) {
                this.bitField0_ |= 64;
                this.hashedCounterName_ = value;
            }

            /* access modifiers changed from: private */
            public void clearHashedCounterName() {
                this.bitField0_ &= -65;
                this.hashedCounterName_ = 0;
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new Identifier();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001\b\u0000\u0002\f\u0003\u0003\b\u0004\u0004\b\u0001\u0005\u0005\u0002\u0006\b\u0005\u0007\u0005\u0006", new Object[]{"bitField0_", "componentName_", "metric_", Metric.internalGetVerifier(), "customCounterName_", "constantComponentName_", "hashedComponentName_", "constantCounterName_", "hashedCounterName_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<Identifier> parser = PARSER;
                        if (parser == null) {
                            synchronized (Identifier.class) {
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

            public enum Metric implements Internal.EnumLite {
                UNKNOWN_METRIC(0),
                FRAME_DURATION_MILLIS(1),
                FRAME_JANK_COUNT(2),
                FRAME_DAVEY_JUNIOR_COUNT(3),
                FRAME_DAVEY_COUNT(4),
                CUSTOM_COUNTER(5),
                MEMORY_TOTAL_PSS_KB(6),
                MEMORY_ALLOCATED_KB(7);

                public static final int CUSTOM_COUNTER_VALUE = 5;
                public static final int FRAME_DAVEY_COUNT_VALUE = 4;
                public static final int FRAME_DAVEY_JUNIOR_COUNT_VALUE = 3;
                public static final int FRAME_DURATION_MILLIS_VALUE = 1;
                public static final int FRAME_JANK_COUNT_VALUE = 2;
                public static final int MEMORY_ALLOCATED_KB_VALUE = 7;
                public static final int MEMORY_TOTAL_PSS_KB_VALUE = 6;
                public static final int UNKNOWN_METRIC_VALUE = 0;
                private static final Internal.EnumLiteMap<Metric> internalValueMap = new Internal.EnumLiteMap<Metric>() {
                    public Metric findValueByNumber(int number) {
                        return Metric.forNumber(number);
                    }
                };
                private final int value;

                private Metric(int value2) {
                    this.value = value2;
                }

                public static Metric forNumber(int value2) {
                    switch (value2) {
                        case 0:
                            return UNKNOWN_METRIC;
                        case 1:
                            return FRAME_DURATION_MILLIS;
                        case 2:
                            return FRAME_JANK_COUNT;
                        case 3:
                            return FRAME_DAVEY_JUNIOR_COUNT;
                        case 4:
                            return FRAME_DAVEY_COUNT;
                        case 5:
                            return CUSTOM_COUNTER;
                        case 6:
                            return MEMORY_TOTAL_PSS_KB;
                        case 7:
                            return MEMORY_ALLOCATED_KB;
                        default:
                            return null;
                    }
                }

                public static Internal.EnumLiteMap<Metric> internalGetValueMap() {
                    return internalValueMap;
                }

                public static Internal.EnumVerifier internalGetVerifier() {
                    return MetricVerifier.INSTANCE;
                }

                public final int getNumber() {
                    return this.value;
                }

                private static final class MetricVerifier implements Internal.EnumVerifier {
                    static final Internal.EnumVerifier INSTANCE = new MetricVerifier();

                    private MetricVerifier() {
                    }

                    public boolean isInRange(int number) {
                        return Metric.forNumber(number) != null;
                    }
                }
            }

            public static final class Builder extends GeneratedMessageLite.Builder<Identifier, Builder> implements IdentifierOrBuilder {
                private Builder() {
                    super(Identifier.DEFAULT_INSTANCE);
                }

                public boolean hasComponentName() {
                    return ((Identifier) this.instance).hasComponentName();
                }

                public String getComponentName() {
                    return ((Identifier) this.instance).getComponentName();
                }

                public Builder setComponentName(String value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setComponentName(value);
                    return this;
                }

                public ByteString getComponentNameBytes() {
                    return ((Identifier) this.instance).getComponentNameBytes();
                }

                public Builder setComponentNameBytes(ByteString value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setComponentNameBytes(value);
                    return this;
                }

                public Builder clearComponentName() {
                    copyOnWrite();
                    ((Identifier) this.instance).clearComponentName();
                    return this;
                }

                public boolean hasConstantComponentName() {
                    return ((Identifier) this.instance).hasConstantComponentName();
                }

                public String getConstantComponentName() {
                    return ((Identifier) this.instance).getConstantComponentName();
                }

                public Builder setConstantComponentName(String value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setConstantComponentName(value);
                    return this;
                }

                public ByteString getConstantComponentNameBytes() {
                    return ((Identifier) this.instance).getConstantComponentNameBytes();
                }

                public Builder setConstantComponentNameBytes(ByteString value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setConstantComponentNameBytes(value);
                    return this;
                }

                public Builder clearConstantComponentName() {
                    copyOnWrite();
                    ((Identifier) this.instance).clearConstantComponentName();
                    return this;
                }

                public boolean hasHashedComponentName() {
                    return ((Identifier) this.instance).hasHashedComponentName();
                }

                public long getHashedComponentName() {
                    return ((Identifier) this.instance).getHashedComponentName();
                }

                public Builder setHashedComponentName(long value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setHashedComponentName(value);
                    return this;
                }

                public Builder clearHashedComponentName() {
                    copyOnWrite();
                    ((Identifier) this.instance).clearHashedComponentName();
                    return this;
                }

                public boolean hasMetric() {
                    return ((Identifier) this.instance).hasMetric();
                }

                public Metric getMetric() {
                    return ((Identifier) this.instance).getMetric();
                }

                public Builder setMetric(Metric value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setMetric(value);
                    return this;
                }

                public Builder clearMetric() {
                    copyOnWrite();
                    ((Identifier) this.instance).clearMetric();
                    return this;
                }

                public boolean hasCustomCounterName() {
                    return ((Identifier) this.instance).hasCustomCounterName();
                }

                public String getCustomCounterName() {
                    return ((Identifier) this.instance).getCustomCounterName();
                }

                public Builder setCustomCounterName(String value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setCustomCounterName(value);
                    return this;
                }

                public ByteString getCustomCounterNameBytes() {
                    return ((Identifier) this.instance).getCustomCounterNameBytes();
                }

                public Builder setCustomCounterNameBytes(ByteString value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setCustomCounterNameBytes(value);
                    return this;
                }

                public Builder clearCustomCounterName() {
                    copyOnWrite();
                    ((Identifier) this.instance).clearCustomCounterName();
                    return this;
                }

                public boolean hasConstantCounterName() {
                    return ((Identifier) this.instance).hasConstantCounterName();
                }

                public String getConstantCounterName() {
                    return ((Identifier) this.instance).getConstantCounterName();
                }

                public Builder setConstantCounterName(String value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setConstantCounterName(value);
                    return this;
                }

                public ByteString getConstantCounterNameBytes() {
                    return ((Identifier) this.instance).getConstantCounterNameBytes();
                }

                public Builder setConstantCounterNameBytes(ByteString value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setConstantCounterNameBytes(value);
                    return this;
                }

                public Builder clearConstantCounterName() {
                    copyOnWrite();
                    ((Identifier) this.instance).clearConstantCounterName();
                    return this;
                }

                public boolean hasHashedCounterName() {
                    return ((Identifier) this.instance).hasHashedCounterName();
                }

                public long getHashedCounterName() {
                    return ((Identifier) this.instance).getHashedCounterName();
                }

                public Builder setHashedCounterName(long value) {
                    copyOnWrite();
                    ((Identifier) this.instance).setHashedCounterName(value);
                    return this;
                }

                public Builder clearHashedCounterName() {
                    copyOnWrite();
                    ((Identifier) this.instance).clearHashedCounterName();
                    return this;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<AggregatedMetric, Builder> implements AggregatedMetricOrBuilder {
            private Builder() {
                super(AggregatedMetric.DEFAULT_INSTANCE);
            }

            public boolean hasIdentifier() {
                return ((AggregatedMetric) this.instance).hasIdentifier();
            }

            public Identifier getIdentifier() {
                return ((AggregatedMetric) this.instance).getIdentifier();
            }

            public Builder setIdentifier(Identifier value) {
                copyOnWrite();
                ((AggregatedMetric) this.instance).setIdentifier(value);
                return this;
            }

            public Builder setIdentifier(Identifier.Builder builderForValue) {
                copyOnWrite();
                ((AggregatedMetric) this.instance).setIdentifier(builderForValue);
                return this;
            }

            public Builder mergeIdentifier(Identifier value) {
                copyOnWrite();
                ((AggregatedMetric) this.instance).mergeIdentifier(value);
                return this;
            }

            public Builder clearIdentifier() {
                copyOnWrite();
                ((AggregatedMetric) this.instance).clearIdentifier();
                return this;
            }

            public boolean hasAggregatedData() {
                return ((AggregatedMetric) this.instance).hasAggregatedData();
            }

            public AggregatedData getAggregatedData() {
                return ((AggregatedMetric) this.instance).getAggregatedData();
            }

            public Builder setAggregatedData(AggregatedData value) {
                copyOnWrite();
                ((AggregatedMetric) this.instance).setAggregatedData(value);
                return this;
            }

            public Builder setAggregatedData(AggregatedData.Builder builderForValue) {
                copyOnWrite();
                ((AggregatedMetric) this.instance).setAggregatedData(builderForValue);
                return this;
            }

            public Builder mergeAggregatedData(AggregatedData value) {
                copyOnWrite();
                ((AggregatedMetric) this.instance).mergeAggregatedData(value);
                return this;
            }

            public Builder clearAggregatedData() {
                copyOnWrite();
                ((AggregatedMetric) this.instance).clearAggregatedData();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AggregatedData extends GeneratedMessageLite<AggregatedData, Builder> implements AggregatedDataOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final AggregatedData DEFAULT_INSTANCE = new AggregatedData();
        public static final int MAX_FIELD_NUMBER = 3;
        public static final int MIN_FIELD_NUMBER = 4;
        public static final int SUM_FIELD_NUMBER = 2;
        private static volatile Parser<AggregatedData> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(AggregatedData.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long max_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private long min_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long sum_;

        private AggregatedData() {
        }

        public static AggregatedData parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AggregatedData parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AggregatedData parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AggregatedData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AggregatedData parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AggregatedData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AggregatedData parseFrom(InputStream input) throws IOException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AggregatedData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AggregatedData parseDelimitedFrom(InputStream input) throws IOException {
            return (AggregatedData) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AggregatedData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AggregatedData) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AggregatedData parseFrom(CodedInputStream input) throws IOException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AggregatedData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AggregatedData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AggregatedData prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static AggregatedData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AggregatedData> parser() {
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

        public boolean hasSum() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getSum() {
            return this.sum_;
        }

        /* access modifiers changed from: private */
        public void setSum(long value) {
            this.bitField0_ |= 2;
            this.sum_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSum() {
            this.bitField0_ &= -3;
            this.sum_ = 0;
        }

        public boolean hasMax() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getMax() {
            return this.max_;
        }

        /* access modifiers changed from: private */
        public void setMax(long value) {
            this.bitField0_ |= 4;
            this.max_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMax() {
            this.bitField0_ &= -5;
            this.max_ = 0;
        }

        public boolean hasMin() {
            return (this.bitField0_ & 8) != 0;
        }

        public long getMin() {
            return this.min_;
        }

        /* access modifiers changed from: private */
        public void setMin(long value) {
            this.bitField0_ |= 8;
            this.min_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMin() {
            this.bitField0_ &= -9;
            this.min_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AggregatedData();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0002\u0001\u0003\u0002\u0002\u0004\u0002\u0003", new Object[]{"bitField0_", "count_", "sum_", "max_", "min_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AggregatedData> parser = PARSER;
                    if (parser == null) {
                        synchronized (AggregatedData.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<AggregatedData, Builder> implements AggregatedDataOrBuilder {
            private Builder() {
                super(AggregatedData.DEFAULT_INSTANCE);
            }

            public boolean hasCount() {
                return ((AggregatedData) this.instance).hasCount();
            }

            public int getCount() {
                return ((AggregatedData) this.instance).getCount();
            }

            public Builder setCount(int value) {
                copyOnWrite();
                ((AggregatedData) this.instance).setCount(value);
                return this;
            }

            public Builder clearCount() {
                copyOnWrite();
                ((AggregatedData) this.instance).clearCount();
                return this;
            }

            public boolean hasSum() {
                return ((AggregatedData) this.instance).hasSum();
            }

            public long getSum() {
                return ((AggregatedData) this.instance).getSum();
            }

            public Builder setSum(long value) {
                copyOnWrite();
                ((AggregatedData) this.instance).setSum(value);
                return this;
            }

            public Builder clearSum() {
                copyOnWrite();
                ((AggregatedData) this.instance).clearSum();
                return this;
            }

            public boolean hasMax() {
                return ((AggregatedData) this.instance).hasMax();
            }

            public long getMax() {
                return ((AggregatedData) this.instance).getMax();
            }

            public Builder setMax(long value) {
                copyOnWrite();
                ((AggregatedData) this.instance).setMax(value);
                return this;
            }

            public Builder clearMax() {
                copyOnWrite();
                ((AggregatedData) this.instance).clearMax();
                return this;
            }

            public boolean hasMin() {
                return ((AggregatedData) this.instance).hasMin();
            }

            public long getMin() {
                return ((AggregatedData) this.instance).getMin();
            }

            public Builder setMin(long value) {
                copyOnWrite();
                ((AggregatedData) this.instance).setMin(value);
                return this;
            }

            public Builder clearMin() {
                copyOnWrite();
                ((AggregatedData) this.instance).clearMin();
                return this;
            }
        }
    }
}
