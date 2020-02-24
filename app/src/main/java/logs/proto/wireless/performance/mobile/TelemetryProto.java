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

public final class TelemetryProto {

    @Deprecated
    public interface TelemetryIdentifierOrBuilder extends MessageLiteOrBuilder {
        @Deprecated
        String getComponentName();

        @Deprecated
        ByteString getComponentNameBytes();

        @Deprecated
        String getCustomMetricName();

        @Deprecated
        ByteString getCustomMetricNameBytes();

        @Deprecated
        Metric getMetric();

        @Deprecated
        boolean hasComponentName();

        @Deprecated
        boolean hasCustomMetricName();

        @Deprecated
        boolean hasMetric();
    }

    @Deprecated
    public interface TelemetryMetricOrBuilder extends MessageLiteOrBuilder {
        @Deprecated
        int getCount();

        @Deprecated
        TelemetryIdentifier getIdentifier();

        @Deprecated
        long getSum();

        @Deprecated
        boolean hasCount();

        @Deprecated
        boolean hasIdentifier();

        @Deprecated
        boolean hasSum();
    }

    private TelemetryProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum Metric implements Internal.EnumLite {
        UNKNOWN_METRIC(0),
        FRAME_DURATION_MILLIS(1),
        FRAME_JANK_COUNT(2),
        FRAME_DAVEY_JUNIOR_COUNT(3),
        FRAME_DAVEY_COUNT(4),
        CUSTOM(5);
        
        public static final int CUSTOM_VALUE = 5;
        public static final int FRAME_DAVEY_COUNT_VALUE = 4;
        public static final int FRAME_DAVEY_JUNIOR_COUNT_VALUE = 3;
        public static final int FRAME_DURATION_MILLIS_VALUE = 1;
        public static final int FRAME_JANK_COUNT_VALUE = 2;
        public static final int UNKNOWN_METRIC_VALUE = 0;
        private static final Internal.EnumLiteMap<Metric> internalValueMap = new Internal.EnumLiteMap<Metric>() {
            public Metric findValueByNumber(int number) {
                return Metric.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static Metric forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN_METRIC;
            }
            if (value2 == 1) {
                return FRAME_DURATION_MILLIS;
            }
            if (value2 == 2) {
                return FRAME_JANK_COUNT;
            }
            if (value2 == 3) {
                return FRAME_DAVEY_JUNIOR_COUNT;
            }
            if (value2 == 4) {
                return FRAME_DAVEY_COUNT;
            }
            if (value2 != 5) {
                return null;
            }
            return CUSTOM;
        }

        public static Internal.EnumLiteMap<Metric> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return MetricVerifier.INSTANCE;
        }

        private static final class MetricVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new MetricVerifier();

            private MetricVerifier() {
            }

            public boolean isInRange(int number) {
                return Metric.forNumber(number) != null;
            }
        }

        private Metric(int value2) {
            this.value = value2;
        }
    }

    @Deprecated
    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class TelemetryMetric extends GeneratedMessageLite<TelemetryMetric, Builder> implements TelemetryMetricOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final TelemetryMetric DEFAULT_INSTANCE = new TelemetryMetric();
        public static final int IDENTIFIER_FIELD_NUMBER = 1;
        private static volatile Parser<TelemetryMetric> PARSER = null;
        public static final int SUM_FIELD_NUMBER = 3;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private TelemetryIdentifier identifier_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long sum_;

        private TelemetryMetric() {
        }

        @Deprecated
        public boolean hasIdentifier() {
            return (this.bitField0_ & 1) != 0;
        }

        @Deprecated
        public TelemetryIdentifier getIdentifier() {
            TelemetryIdentifier telemetryIdentifier = this.identifier_;
            return telemetryIdentifier == null ? TelemetryIdentifier.getDefaultInstance() : telemetryIdentifier;
        }

        /* access modifiers changed from: private */
        public void setIdentifier(TelemetryIdentifier value) {
            if (value != null) {
                this.identifier_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setIdentifier(TelemetryIdentifier.Builder builderForValue) {
            this.identifier_ = (TelemetryIdentifier) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeIdentifier(TelemetryIdentifier value) {
            if (value != null) {
                TelemetryIdentifier telemetryIdentifier = this.identifier_;
                if (telemetryIdentifier == null || telemetryIdentifier == TelemetryIdentifier.getDefaultInstance()) {
                    this.identifier_ = value;
                } else {
                    this.identifier_ = (TelemetryIdentifier) ((TelemetryIdentifier.Builder) TelemetryIdentifier.newBuilder(this.identifier_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
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
        public boolean hasSum() {
            return (this.bitField0_ & 4) != 0;
        }

        @Deprecated
        public long getSum() {
            return this.sum_;
        }

        /* access modifiers changed from: private */
        public void setSum(long value) {
            this.bitField0_ |= 4;
            this.sum_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSum() {
            this.bitField0_ &= -5;
            this.sum_ = 0;
        }

        public static TelemetryMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TelemetryMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TelemetryMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TelemetryMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TelemetryMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TelemetryMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TelemetryMetric parseFrom(InputStream input) throws IOException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TelemetryMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TelemetryMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (TelemetryMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TelemetryMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TelemetryMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TelemetryMetric parseFrom(CodedInputStream input) throws IOException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TelemetryMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TelemetryMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TelemetryMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<TelemetryMetric, Builder> implements TelemetryMetricOrBuilder {
            private Builder() {
                super(TelemetryMetric.DEFAULT_INSTANCE);
            }

            @Deprecated
            public boolean hasIdentifier() {
                return ((TelemetryMetric) this.instance).hasIdentifier();
            }

            @Deprecated
            public TelemetryIdentifier getIdentifier() {
                return ((TelemetryMetric) this.instance).getIdentifier();
            }

            @Deprecated
            public Builder setIdentifier(TelemetryIdentifier value) {
                copyOnWrite();
                ((TelemetryMetric) this.instance).setIdentifier(value);
                return this;
            }

            @Deprecated
            public Builder setIdentifier(TelemetryIdentifier.Builder builderForValue) {
                copyOnWrite();
                ((TelemetryMetric) this.instance).setIdentifier(builderForValue);
                return this;
            }

            @Deprecated
            public Builder mergeIdentifier(TelemetryIdentifier value) {
                copyOnWrite();
                ((TelemetryMetric) this.instance).mergeIdentifier(value);
                return this;
            }

            @Deprecated
            public Builder clearIdentifier() {
                copyOnWrite();
                ((TelemetryMetric) this.instance).clearIdentifier();
                return this;
            }

            @Deprecated
            public boolean hasCount() {
                return ((TelemetryMetric) this.instance).hasCount();
            }

            @Deprecated
            public int getCount() {
                return ((TelemetryMetric) this.instance).getCount();
            }

            @Deprecated
            public Builder setCount(int value) {
                copyOnWrite();
                ((TelemetryMetric) this.instance).setCount(value);
                return this;
            }

            @Deprecated
            public Builder clearCount() {
                copyOnWrite();
                ((TelemetryMetric) this.instance).clearCount();
                return this;
            }

            @Deprecated
            public boolean hasSum() {
                return ((TelemetryMetric) this.instance).hasSum();
            }

            @Deprecated
            public long getSum() {
                return ((TelemetryMetric) this.instance).getSum();
            }

            @Deprecated
            public Builder setSum(long value) {
                copyOnWrite();
                ((TelemetryMetric) this.instance).setSum(value);
                return this;
            }

            @Deprecated
            public Builder clearSum() {
                copyOnWrite();
                ((TelemetryMetric) this.instance).clearSum();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new TelemetryMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\u0004\u0001\u0003\u0002\u0002", new Object[]{"bitField0_", "identifier_", "count_", "sum_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<TelemetryMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (TelemetryMetric.class) {
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
            GeneratedMessageLite.registerDefaultInstance(TelemetryMetric.class, DEFAULT_INSTANCE);
        }

        public static TelemetryMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TelemetryMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @Deprecated
    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class TelemetryIdentifier extends GeneratedMessageLite<TelemetryIdentifier, Builder> implements TelemetryIdentifierOrBuilder {
        public static final int COMPONENT_NAME_FIELD_NUMBER = 1;
        public static final int CUSTOM_METRIC_NAME_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final TelemetryIdentifier DEFAULT_INSTANCE = new TelemetryIdentifier();
        public static final int METRIC_FIELD_NUMBER = 2;
        private static volatile Parser<TelemetryIdentifier> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String componentName_ = "";
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String customMetricName_ = "";
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int metric_;

        private TelemetryIdentifier() {
        }

        @Deprecated
        public boolean hasComponentName() {
            return (this.bitField0_ & 1) != 0;
        }

        @Deprecated
        public String getComponentName() {
            return this.componentName_;
        }

        @Deprecated
        public ByteString getComponentNameBytes() {
            return ByteString.copyFromUtf8(this.componentName_);
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

        /* access modifiers changed from: private */
        public void clearComponentName() {
            this.bitField0_ &= -2;
            this.componentName_ = getDefaultInstance().getComponentName();
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

        @Deprecated
        public boolean hasMetric() {
            return (this.bitField0_ & 2) != 0;
        }

        @Deprecated
        public Metric getMetric() {
            Metric result = Metric.forNumber(this.metric_);
            return result == null ? Metric.UNKNOWN_METRIC : result;
        }

        /* access modifiers changed from: private */
        public void setMetric(Metric value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.metric_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMetric() {
            this.bitField0_ &= -3;
            this.metric_ = 0;
        }

        @Deprecated
        public boolean hasCustomMetricName() {
            return (this.bitField0_ & 4) != 0;
        }

        @Deprecated
        public String getCustomMetricName() {
            return this.customMetricName_;
        }

        @Deprecated
        public ByteString getCustomMetricNameBytes() {
            return ByteString.copyFromUtf8(this.customMetricName_);
        }

        /* access modifiers changed from: private */
        public void setCustomMetricName(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.customMetricName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCustomMetricName() {
            this.bitField0_ &= -5;
            this.customMetricName_ = getDefaultInstance().getCustomMetricName();
        }

        /* access modifiers changed from: private */
        public void setCustomMetricNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.customMetricName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static TelemetryIdentifier parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TelemetryIdentifier parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TelemetryIdentifier parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TelemetryIdentifier parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TelemetryIdentifier parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TelemetryIdentifier parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TelemetryIdentifier parseFrom(InputStream input) throws IOException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TelemetryIdentifier parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TelemetryIdentifier parseDelimitedFrom(InputStream input) throws IOException {
            return (TelemetryIdentifier) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TelemetryIdentifier parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TelemetryIdentifier) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TelemetryIdentifier parseFrom(CodedInputStream input) throws IOException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TelemetryIdentifier parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TelemetryIdentifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TelemetryIdentifier prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<TelemetryIdentifier, Builder> implements TelemetryIdentifierOrBuilder {
            private Builder() {
                super(TelemetryIdentifier.DEFAULT_INSTANCE);
            }

            @Deprecated
            public boolean hasComponentName() {
                return ((TelemetryIdentifier) this.instance).hasComponentName();
            }

            @Deprecated
            public String getComponentName() {
                return ((TelemetryIdentifier) this.instance).getComponentName();
            }

            @Deprecated
            public ByteString getComponentNameBytes() {
                return ((TelemetryIdentifier) this.instance).getComponentNameBytes();
            }

            @Deprecated
            public Builder setComponentName(String value) {
                copyOnWrite();
                ((TelemetryIdentifier) this.instance).setComponentName(value);
                return this;
            }

            @Deprecated
            public Builder clearComponentName() {
                copyOnWrite();
                ((TelemetryIdentifier) this.instance).clearComponentName();
                return this;
            }

            @Deprecated
            public Builder setComponentNameBytes(ByteString value) {
                copyOnWrite();
                ((TelemetryIdentifier) this.instance).setComponentNameBytes(value);
                return this;
            }

            @Deprecated
            public boolean hasMetric() {
                return ((TelemetryIdentifier) this.instance).hasMetric();
            }

            @Deprecated
            public Metric getMetric() {
                return ((TelemetryIdentifier) this.instance).getMetric();
            }

            @Deprecated
            public Builder setMetric(Metric value) {
                copyOnWrite();
                ((TelemetryIdentifier) this.instance).setMetric(value);
                return this;
            }

            @Deprecated
            public Builder clearMetric() {
                copyOnWrite();
                ((TelemetryIdentifier) this.instance).clearMetric();
                return this;
            }

            @Deprecated
            public boolean hasCustomMetricName() {
                return ((TelemetryIdentifier) this.instance).hasCustomMetricName();
            }

            @Deprecated
            public String getCustomMetricName() {
                return ((TelemetryIdentifier) this.instance).getCustomMetricName();
            }

            @Deprecated
            public ByteString getCustomMetricNameBytes() {
                return ((TelemetryIdentifier) this.instance).getCustomMetricNameBytes();
            }

            @Deprecated
            public Builder setCustomMetricName(String value) {
                copyOnWrite();
                ((TelemetryIdentifier) this.instance).setCustomMetricName(value);
                return this;
            }

            @Deprecated
            public Builder clearCustomMetricName() {
                copyOnWrite();
                ((TelemetryIdentifier) this.instance).clearCustomMetricName();
                return this;
            }

            @Deprecated
            public Builder setCustomMetricNameBytes(ByteString value) {
                copyOnWrite();
                ((TelemetryIdentifier) this.instance).setCustomMetricNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new TelemetryIdentifier();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\b\u0000\u0002\f\u0001\u0003\b\u0002", new Object[]{"bitField0_", "componentName_", "metric_", Metric.internalGetVerifier(), "customMetricName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<TelemetryIdentifier> parser = PARSER;
                    if (parser == null) {
                        synchronized (TelemetryIdentifier.class) {
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
            GeneratedMessageLite.registerDefaultInstance(TelemetryIdentifier.class, DEFAULT_INSTANCE);
        }

        public static TelemetryIdentifier getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TelemetryIdentifier> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
