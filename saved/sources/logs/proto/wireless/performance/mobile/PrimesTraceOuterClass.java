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
import logs.proto.wireless.performance.mobile.ExtensionMetric;

public final class PrimesTraceOuterClass {

    public interface CollectionErrorOrBuilder extends MessageLiteOrBuilder {
        int getNumDroppedSpans();

        long getTimedOutAfterMs();

        boolean hasNumDroppedSpans();

        boolean hasTimedOutAfterMs();
    }

    public interface PrimesSpansOrBuilder extends MessageLiteOrBuilder {
        Span getSpans(int i);

        int getSpansCount();

        List<Span> getSpansList();
    }

    public interface PrimesTraceOrBuilder extends MessageLiteOrBuilder {
        CollectionError getCollectionError();

        Span getSpans(int i);

        int getSpansCount();

        List<Span> getSpansList();

        long getTraceId();

        float getTraceSamplingRate();

        PrimesTrace.TraceType getTraceType();

        boolean hasCollectionError();

        boolean hasTraceId();

        boolean hasTraceSamplingRate();

        boolean hasTraceType();
    }

    public interface SpanOrBuilder extends MessageLiteOrBuilder {
        String getConstantName();

        ByteString getConstantNameBytes();

        int getCpuTimeMs();

        long getDurationMs();

        long getHashedName();

        long getId();

        ExtensionMetric.MetricExtension getMetricExtension();

        String getName();

        ByteString getNameBytes();

        long getParentId();

        Span.SpanType getSpanType();

        long getStartTimeMs();

        long getThreadId();

        boolean hasConstantName();

        boolean hasCpuTimeMs();

        boolean hasDurationMs();

        boolean hasHashedName();

        boolean hasId();

        boolean hasMetricExtension();

        boolean hasName();

        boolean hasParentId();

        boolean hasSpanType();

        boolean hasStartTimeMs();

        boolean hasThreadId();
    }

    private PrimesTraceOuterClass() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum EndStatus implements Internal.EnumLite {
        UNKNOWN_STATUS(0),
        SUCCESS(1),
        ERROR(2),
        CANCEL(3);
        
        public static final int CANCEL_VALUE = 3;
        public static final int ERROR_VALUE = 2;
        public static final int SUCCESS_VALUE = 1;
        public static final int UNKNOWN_STATUS_VALUE = 0;
        private static final Internal.EnumLiteMap<EndStatus> internalValueMap = new Internal.EnumLiteMap<EndStatus>() {
            public EndStatus findValueByNumber(int number) {
                return EndStatus.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static EndStatus forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN_STATUS;
            }
            if (value2 == 1) {
                return SUCCESS;
            }
            if (value2 == 2) {
                return ERROR;
            }
            if (value2 != 3) {
                return null;
            }
            return CANCEL;
        }

        public static Internal.EnumLiteMap<EndStatus> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return EndStatusVerifier.INSTANCE;
        }

        private static final class EndStatusVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new EndStatusVerifier();

            private EndStatusVerifier() {
            }

            public boolean isInRange(int number) {
                return EndStatus.forNumber(number) != null;
            }
        }

        private EndStatus(int value2) {
            this.value = value2;
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PrimesTrace extends GeneratedMessageLite<PrimesTrace, Builder> implements PrimesTraceOrBuilder {
        public static final int COLLECTION_ERROR_FIELD_NUMBER = 5;
        /* access modifiers changed from: private */
        public static final PrimesTrace DEFAULT_INSTANCE = new PrimesTrace();
        private static volatile Parser<PrimesTrace> PARSER = null;
        public static final int SPANS_FIELD_NUMBER = 2;
        public static final int TRACE_ID_FIELD_NUMBER = 1;
        public static final int TRACE_SAMPLING_RATE_FIELD_NUMBER = 4;
        public static final int TRACE_TYPE_FIELD_NUMBER = 3;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private CollectionError collectionError_;
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Span> spans_ = emptyProtobufList();
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long traceId_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.FLOAT)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private float traceSamplingRate_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int traceType_;

        private PrimesTrace() {
        }

        public enum TraceType implements Internal.EnumLite {
            UNKNOWN(0),
            TIKTOK_TRACE(1),
            MINI_TRACE(2),
            PRIMES_TRACE(3);
            
            public static final int MINI_TRACE_VALUE = 2;
            public static final int PRIMES_TRACE_VALUE = 3;
            public static final int TIKTOK_TRACE_VALUE = 1;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<TraceType> internalValueMap = new Internal.EnumLiteMap<TraceType>() {
                public TraceType findValueByNumber(int number) {
                    return TraceType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static TraceType forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return TIKTOK_TRACE;
                }
                if (value2 == 2) {
                    return MINI_TRACE;
                }
                if (value2 != 3) {
                    return null;
                }
                return PRIMES_TRACE;
            }

            public static Internal.EnumLiteMap<TraceType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TraceTypeVerifier.INSTANCE;
            }

            private static final class TraceTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new TraceTypeVerifier();

                private TraceTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return TraceType.forNumber(number) != null;
                }
            }

            private TraceType(int value2) {
                this.value = value2;
            }
        }

        public boolean hasTraceId() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getTraceId() {
            return this.traceId_;
        }

        /* access modifiers changed from: private */
        public void setTraceId(long value) {
            this.bitField0_ |= 1;
            this.traceId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTraceId() {
            this.bitField0_ &= -2;
            this.traceId_ = 0;
        }

        public boolean hasTraceType() {
            return (this.bitField0_ & 2) != 0;
        }

        public TraceType getTraceType() {
            TraceType result = TraceType.forNumber(this.traceType_);
            return result == null ? TraceType.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setTraceType(TraceType value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.traceType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTraceType() {
            this.bitField0_ &= -3;
            this.traceType_ = 0;
        }

        public List<Span> getSpansList() {
            return this.spans_;
        }

        public List<? extends SpanOrBuilder> getSpansOrBuilderList() {
            return this.spans_;
        }

        public int getSpansCount() {
            return this.spans_.size();
        }

        public Span getSpans(int index) {
            return this.spans_.get(index);
        }

        public SpanOrBuilder getSpansOrBuilder(int index) {
            return this.spans_.get(index);
        }

        private void ensureSpansIsMutable() {
            if (!this.spans_.isModifiable()) {
                this.spans_ = GeneratedMessageLite.mutableCopy(this.spans_);
            }
        }

        /* access modifiers changed from: private */
        public void setSpans(int index, Span value) {
            if (value != null) {
                ensureSpansIsMutable();
                this.spans_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSpans(int index, Span.Builder builderForValue) {
            ensureSpansIsMutable();
            this.spans_.set(index, (Span) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSpans(Span value) {
            if (value != null) {
                ensureSpansIsMutable();
                this.spans_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSpans(int index, Span value) {
            if (value != null) {
                ensureSpansIsMutable();
                this.spans_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSpans(Span.Builder builderForValue) {
            ensureSpansIsMutable();
            this.spans_.add((Span) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSpans(int index, Span.Builder builderForValue) {
            ensureSpansIsMutable();
            this.spans_.add(index, (Span) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.PrimesTraceOuterClass$Span>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.PrimesTraceOuterClass$Span>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSpans(Iterable<? extends Span> values) {
            ensureSpansIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.spans_);
        }

        /* access modifiers changed from: private */
        public void clearSpans() {
            this.spans_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSpans(int index) {
            ensureSpansIsMutable();
            this.spans_.remove(index);
        }

        public boolean hasTraceSamplingRate() {
            return (this.bitField0_ & 4) != 0;
        }

        public float getTraceSamplingRate() {
            return this.traceSamplingRate_;
        }

        /* access modifiers changed from: private */
        public void setTraceSamplingRate(float value) {
            this.bitField0_ |= 4;
            this.traceSamplingRate_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTraceSamplingRate() {
            this.bitField0_ &= -5;
            this.traceSamplingRate_ = 0.0f;
        }

        public boolean hasCollectionError() {
            return (this.bitField0_ & 8) != 0;
        }

        public CollectionError getCollectionError() {
            CollectionError collectionError = this.collectionError_;
            return collectionError == null ? CollectionError.getDefaultInstance() : collectionError;
        }

        /* access modifiers changed from: private */
        public void setCollectionError(CollectionError value) {
            if (value != null) {
                this.collectionError_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCollectionError(CollectionError.Builder builderForValue) {
            this.collectionError_ = (CollectionError) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeCollectionError(CollectionError value) {
            if (value != null) {
                CollectionError collectionError = this.collectionError_;
                if (collectionError == null || collectionError == CollectionError.getDefaultInstance()) {
                    this.collectionError_ = value;
                } else {
                    this.collectionError_ = (CollectionError) ((CollectionError.Builder) CollectionError.newBuilder(this.collectionError_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCollectionError() {
            this.collectionError_ = null;
            this.bitField0_ &= -9;
        }

        public static PrimesTrace parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesTrace parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesTrace parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesTrace parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesTrace parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesTrace parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesTrace parseFrom(InputStream input) throws IOException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesTrace parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesTrace parseDelimitedFrom(InputStream input) throws IOException {
            return (PrimesTrace) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesTrace parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesTrace) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesTrace parseFrom(CodedInputStream input) throws IOException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesTrace parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PrimesTrace prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PrimesTrace, Builder> implements PrimesTraceOrBuilder {
            private Builder() {
                super(PrimesTrace.DEFAULT_INSTANCE);
            }

            public boolean hasTraceId() {
                return ((PrimesTrace) this.instance).hasTraceId();
            }

            public long getTraceId() {
                return ((PrimesTrace) this.instance).getTraceId();
            }

            public Builder setTraceId(long value) {
                copyOnWrite();
                ((PrimesTrace) this.instance).setTraceId(value);
                return this;
            }

            public Builder clearTraceId() {
                copyOnWrite();
                ((PrimesTrace) this.instance).clearTraceId();
                return this;
            }

            public boolean hasTraceType() {
                return ((PrimesTrace) this.instance).hasTraceType();
            }

            public TraceType getTraceType() {
                return ((PrimesTrace) this.instance).getTraceType();
            }

            public Builder setTraceType(TraceType value) {
                copyOnWrite();
                ((PrimesTrace) this.instance).setTraceType(value);
                return this;
            }

            public Builder clearTraceType() {
                copyOnWrite();
                ((PrimesTrace) this.instance).clearTraceType();
                return this;
            }

            public List<Span> getSpansList() {
                return Collections.unmodifiableList(((PrimesTrace) this.instance).getSpansList());
            }

            public int getSpansCount() {
                return ((PrimesTrace) this.instance).getSpansCount();
            }

            public Span getSpans(int index) {
                return ((PrimesTrace) this.instance).getSpans(index);
            }

            public Builder setSpans(int index, Span value) {
                copyOnWrite();
                ((PrimesTrace) this.instance).setSpans(index, value);
                return this;
            }

            public Builder setSpans(int index, Span.Builder builderForValue) {
                copyOnWrite();
                ((PrimesTrace) this.instance).setSpans(index, builderForValue);
                return this;
            }

            public Builder addSpans(Span value) {
                copyOnWrite();
                ((PrimesTrace) this.instance).addSpans(value);
                return this;
            }

            public Builder addSpans(int index, Span value) {
                copyOnWrite();
                ((PrimesTrace) this.instance).addSpans(index, value);
                return this;
            }

            public Builder addSpans(Span.Builder builderForValue) {
                copyOnWrite();
                ((PrimesTrace) this.instance).addSpans(builderForValue);
                return this;
            }

            public Builder addSpans(int index, Span.Builder builderForValue) {
                copyOnWrite();
                ((PrimesTrace) this.instance).addSpans(index, builderForValue);
                return this;
            }

            public Builder addAllSpans(Iterable<? extends Span> values) {
                copyOnWrite();
                ((PrimesTrace) this.instance).addAllSpans(values);
                return this;
            }

            public Builder clearSpans() {
                copyOnWrite();
                ((PrimesTrace) this.instance).clearSpans();
                return this;
            }

            public Builder removeSpans(int index) {
                copyOnWrite();
                ((PrimesTrace) this.instance).removeSpans(index);
                return this;
            }

            public boolean hasTraceSamplingRate() {
                return ((PrimesTrace) this.instance).hasTraceSamplingRate();
            }

            public float getTraceSamplingRate() {
                return ((PrimesTrace) this.instance).getTraceSamplingRate();
            }

            public Builder setTraceSamplingRate(float value) {
                copyOnWrite();
                ((PrimesTrace) this.instance).setTraceSamplingRate(value);
                return this;
            }

            public Builder clearTraceSamplingRate() {
                copyOnWrite();
                ((PrimesTrace) this.instance).clearTraceSamplingRate();
                return this;
            }

            public boolean hasCollectionError() {
                return ((PrimesTrace) this.instance).hasCollectionError();
            }

            public CollectionError getCollectionError() {
                return ((PrimesTrace) this.instance).getCollectionError();
            }

            public Builder setCollectionError(CollectionError value) {
                copyOnWrite();
                ((PrimesTrace) this.instance).setCollectionError(value);
                return this;
            }

            public Builder setCollectionError(CollectionError.Builder builderForValue) {
                copyOnWrite();
                ((PrimesTrace) this.instance).setCollectionError(builderForValue);
                return this;
            }

            public Builder mergeCollectionError(CollectionError value) {
                copyOnWrite();
                ((PrimesTrace) this.instance).mergeCollectionError(value);
                return this;
            }

            public Builder clearCollectionError() {
                copyOnWrite();
                ((PrimesTrace) this.instance).clearCollectionError();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PrimesTrace();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001\u0005\u0000\u0002\u001b\u0003\f\u0001\u0004\u0001\u0002\u0005\t\u0003", new Object[]{"bitField0_", "traceId_", "spans_", Span.class, "traceType_", TraceType.internalGetVerifier(), "traceSamplingRate_", "collectionError_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PrimesTrace> parser = PARSER;
                    if (parser == null) {
                        synchronized (PrimesTrace.class) {
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
            GeneratedMessageLite.registerDefaultInstance(PrimesTrace.class, DEFAULT_INSTANCE);
        }

        public static PrimesTrace getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PrimesTrace> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PrimesSpans extends GeneratedMessageLite<PrimesSpans, Builder> implements PrimesSpansOrBuilder {
        /* access modifiers changed from: private */
        public static final PrimesSpans DEFAULT_INSTANCE = new PrimesSpans();
        private static volatile Parser<PrimesSpans> PARSER = null;
        public static final int SPANS_FIELD_NUMBER = 1;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Span> spans_ = emptyProtobufList();

        private PrimesSpans() {
        }

        public List<Span> getSpansList() {
            return this.spans_;
        }

        public List<? extends SpanOrBuilder> getSpansOrBuilderList() {
            return this.spans_;
        }

        public int getSpansCount() {
            return this.spans_.size();
        }

        public Span getSpans(int index) {
            return this.spans_.get(index);
        }

        public SpanOrBuilder getSpansOrBuilder(int index) {
            return this.spans_.get(index);
        }

        private void ensureSpansIsMutable() {
            if (!this.spans_.isModifiable()) {
                this.spans_ = GeneratedMessageLite.mutableCopy(this.spans_);
            }
        }

        /* access modifiers changed from: private */
        public void setSpans(int index, Span value) {
            if (value != null) {
                ensureSpansIsMutable();
                this.spans_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSpans(int index, Span.Builder builderForValue) {
            ensureSpansIsMutable();
            this.spans_.set(index, (Span) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSpans(Span value) {
            if (value != null) {
                ensureSpansIsMutable();
                this.spans_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSpans(int index, Span value) {
            if (value != null) {
                ensureSpansIsMutable();
                this.spans_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSpans(Span.Builder builderForValue) {
            ensureSpansIsMutable();
            this.spans_.add((Span) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSpans(int index, Span.Builder builderForValue) {
            ensureSpansIsMutable();
            this.spans_.add(index, (Span) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.PrimesTraceOuterClass$Span>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.PrimesTraceOuterClass$Span>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSpans(Iterable<? extends Span> values) {
            ensureSpansIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.spans_);
        }

        /* access modifiers changed from: private */
        public void clearSpans() {
            this.spans_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSpans(int index) {
            ensureSpansIsMutable();
            this.spans_.remove(index);
        }

        public static PrimesSpans parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesSpans parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesSpans parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesSpans parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesSpans parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesSpans parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesSpans parseFrom(InputStream input) throws IOException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesSpans parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesSpans parseDelimitedFrom(InputStream input) throws IOException {
            return (PrimesSpans) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesSpans parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesSpans) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesSpans parseFrom(CodedInputStream input) throws IOException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesSpans parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesSpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PrimesSpans prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PrimesSpans, Builder> implements PrimesSpansOrBuilder {
            private Builder() {
                super(PrimesSpans.DEFAULT_INSTANCE);
            }

            public List<Span> getSpansList() {
                return Collections.unmodifiableList(((PrimesSpans) this.instance).getSpansList());
            }

            public int getSpansCount() {
                return ((PrimesSpans) this.instance).getSpansCount();
            }

            public Span getSpans(int index) {
                return ((PrimesSpans) this.instance).getSpans(index);
            }

            public Builder setSpans(int index, Span value) {
                copyOnWrite();
                ((PrimesSpans) this.instance).setSpans(index, value);
                return this;
            }

            public Builder setSpans(int index, Span.Builder builderForValue) {
                copyOnWrite();
                ((PrimesSpans) this.instance).setSpans(index, builderForValue);
                return this;
            }

            public Builder addSpans(Span value) {
                copyOnWrite();
                ((PrimesSpans) this.instance).addSpans(value);
                return this;
            }

            public Builder addSpans(int index, Span value) {
                copyOnWrite();
                ((PrimesSpans) this.instance).addSpans(index, value);
                return this;
            }

            public Builder addSpans(Span.Builder builderForValue) {
                copyOnWrite();
                ((PrimesSpans) this.instance).addSpans(builderForValue);
                return this;
            }

            public Builder addSpans(int index, Span.Builder builderForValue) {
                copyOnWrite();
                ((PrimesSpans) this.instance).addSpans(index, builderForValue);
                return this;
            }

            public Builder addAllSpans(Iterable<? extends Span> values) {
                copyOnWrite();
                ((PrimesSpans) this.instance).addAllSpans(values);
                return this;
            }

            public Builder clearSpans() {
                copyOnWrite();
                ((PrimesSpans) this.instance).clearSpans();
                return this;
            }

            public Builder removeSpans(int index) {
                copyOnWrite();
                ((PrimesSpans) this.instance).removeSpans(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PrimesSpans();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"spans_", Span.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PrimesSpans> parser = PARSER;
                    if (parser == null) {
                        synchronized (PrimesSpans.class) {
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
            GeneratedMessageLite.registerDefaultInstance(PrimesSpans.class, DEFAULT_INSTANCE);
        }

        public static PrimesSpans getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PrimesSpans> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Span extends GeneratedMessageLite<Span, Builder> implements SpanOrBuilder {
        public static final int CONSTANT_NAME_FIELD_NUMBER = 1;
        public static final int CPU_TIME_MS_FIELD_NUMBER = 10;
        /* access modifiers changed from: private */
        public static final Span DEFAULT_INSTANCE = new Span();
        public static final int DURATION_MS_FIELD_NUMBER = 5;
        public static final int HASHED_NAME_FIELD_NUMBER = 8;
        public static final int ID_FIELD_NUMBER = 2;
        public static final int METRIC_EXTENSION_FIELD_NUMBER = 11;
        public static final int NAME_FIELD_NUMBER = 9;
        public static final int PARENT_ID_FIELD_NUMBER = 3;
        private static volatile Parser<Span> PARSER = null;
        public static final int SPAN_TYPE_FIELD_NUMBER = 7;
        public static final int START_TIME_MS_FIELD_NUMBER = 4;
        public static final int THREAD_ID_FIELD_NUMBER = 6;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String constantName_ = "";
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int cpuTimeMs_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private long durationMs_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long hashedName_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private long id_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private ExtensionMetric.MetricExtension metricExtension_;
        @ProtoField(fieldNumber = 9, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private long parentId_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private int spanType_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private long startTimeMs_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private long threadId_;

        private Span() {
        }

        public enum SpanType implements Internal.EnumLite {
            NONE(0),
            TRACE(1),
            SCENARIO(2),
            METRIC(3),
            TIMER(4),
            DOMINANT_SPAN(5);
            
            public static final int DOMINANT_SPAN_VALUE = 5;
            public static final int METRIC_VALUE = 3;
            public static final int NONE_VALUE = 0;
            public static final int SCENARIO_VALUE = 2;
            public static final int TIMER_VALUE = 4;
            public static final int TRACE_VALUE = 1;
            private static final Internal.EnumLiteMap<SpanType> internalValueMap = new Internal.EnumLiteMap<SpanType>() {
                public SpanType findValueByNumber(int number) {
                    return SpanType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static SpanType forNumber(int value2) {
                if (value2 == 0) {
                    return NONE;
                }
                if (value2 == 1) {
                    return TRACE;
                }
                if (value2 == 2) {
                    return SCENARIO;
                }
                if (value2 == 3) {
                    return METRIC;
                }
                if (value2 == 4) {
                    return TIMER;
                }
                if (value2 != 5) {
                    return null;
                }
                return DOMINANT_SPAN;
            }

            public static Internal.EnumLiteMap<SpanType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return SpanTypeVerifier.INSTANCE;
            }

            private static final class SpanTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new SpanTypeVerifier();

                private SpanTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return SpanType.forNumber(number) != null;
                }
            }

            private SpanType(int value2) {
                this.value = value2;
            }
        }

        public boolean hasConstantName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getConstantName() {
            return this.constantName_;
        }

        public ByteString getConstantNameBytes() {
            return ByteString.copyFromUtf8(this.constantName_);
        }

        /* access modifiers changed from: private */
        public void setConstantName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.constantName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearConstantName() {
            this.bitField0_ &= -2;
            this.constantName_ = getDefaultInstance().getConstantName();
        }

        /* access modifiers changed from: private */
        public void setConstantNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.constantName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasHashedName() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getHashedName() {
            return this.hashedName_;
        }

        /* access modifiers changed from: private */
        public void setHashedName(long value) {
            this.bitField0_ |= 2;
            this.hashedName_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHashedName() {
            this.bitField0_ &= -3;
            this.hashedName_ = 0;
        }

        public boolean hasName() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -5;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasId() {
            return (this.bitField0_ & 8) != 0;
        }

        public long getId() {
            return this.id_;
        }

        /* access modifiers changed from: private */
        public void setId(long value) {
            this.bitField0_ |= 8;
            this.id_ = value;
        }

        /* access modifiers changed from: private */
        public void clearId() {
            this.bitField0_ &= -9;
            this.id_ = 0;
        }

        public boolean hasParentId() {
            return (this.bitField0_ & 16) != 0;
        }

        public long getParentId() {
            return this.parentId_;
        }

        /* access modifiers changed from: private */
        public void setParentId(long value) {
            this.bitField0_ |= 16;
            this.parentId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearParentId() {
            this.bitField0_ &= -17;
            this.parentId_ = 0;
        }

        public boolean hasStartTimeMs() {
            return (this.bitField0_ & 32) != 0;
        }

        public long getStartTimeMs() {
            return this.startTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setStartTimeMs(long value) {
            this.bitField0_ |= 32;
            this.startTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStartTimeMs() {
            this.bitField0_ &= -33;
            this.startTimeMs_ = 0;
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

        public boolean hasCpuTimeMs() {
            return (this.bitField0_ & 128) != 0;
        }

        public int getCpuTimeMs() {
            return this.cpuTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setCpuTimeMs(int value) {
            this.bitField0_ |= 128;
            this.cpuTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCpuTimeMs() {
            this.bitField0_ &= -129;
            this.cpuTimeMs_ = 0;
        }

        public boolean hasThreadId() {
            return (this.bitField0_ & 256) != 0;
        }

        public long getThreadId() {
            return this.threadId_;
        }

        /* access modifiers changed from: private */
        public void setThreadId(long value) {
            this.bitField0_ |= 256;
            this.threadId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearThreadId() {
            this.bitField0_ &= -257;
            this.threadId_ = 0;
        }

        public boolean hasSpanType() {
            return (this.bitField0_ & 512) != 0;
        }

        public SpanType getSpanType() {
            SpanType result = SpanType.forNumber(this.spanType_);
            return result == null ? SpanType.NONE : result;
        }

        /* access modifiers changed from: private */
        public void setSpanType(SpanType value) {
            if (value != null) {
                this.bitField0_ |= 512;
                this.spanType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSpanType() {
            this.bitField0_ &= -513;
            this.spanType_ = 0;
        }

        public boolean hasMetricExtension() {
            return (this.bitField0_ & 1024) != 0;
        }

        public ExtensionMetric.MetricExtension getMetricExtension() {
            ExtensionMetric.MetricExtension metricExtension = this.metricExtension_;
            return metricExtension == null ? ExtensionMetric.MetricExtension.getDefaultInstance() : metricExtension;
        }

        /* access modifiers changed from: private */
        public void setMetricExtension(ExtensionMetric.MetricExtension value) {
            if (value != null) {
                this.metricExtension_ = value;
                this.bitField0_ |= 1024;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
            this.metricExtension_ = (ExtensionMetric.MetricExtension) builderForValue.build();
            this.bitField0_ |= 1024;
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
                this.bitField0_ |= 1024;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMetricExtension() {
            this.metricExtension_ = null;
            this.bitField0_ &= -1025;
        }

        public static Span parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Span parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Span parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Span parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Span parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Span parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Span parseFrom(InputStream input) throws IOException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Span parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Span parseDelimitedFrom(InputStream input) throws IOException {
            return (Span) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Span parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Span) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Span parseFrom(CodedInputStream input) throws IOException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Span parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Span) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Span prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Span, Builder> implements SpanOrBuilder {
            private Builder() {
                super(Span.DEFAULT_INSTANCE);
            }

            public boolean hasConstantName() {
                return ((Span) this.instance).hasConstantName();
            }

            public String getConstantName() {
                return ((Span) this.instance).getConstantName();
            }

            public ByteString getConstantNameBytes() {
                return ((Span) this.instance).getConstantNameBytes();
            }

            public Builder setConstantName(String value) {
                copyOnWrite();
                ((Span) this.instance).setConstantName(value);
                return this;
            }

            public Builder clearConstantName() {
                copyOnWrite();
                ((Span) this.instance).clearConstantName();
                return this;
            }

            public Builder setConstantNameBytes(ByteString value) {
                copyOnWrite();
                ((Span) this.instance).setConstantNameBytes(value);
                return this;
            }

            public boolean hasHashedName() {
                return ((Span) this.instance).hasHashedName();
            }

            public long getHashedName() {
                return ((Span) this.instance).getHashedName();
            }

            public Builder setHashedName(long value) {
                copyOnWrite();
                ((Span) this.instance).setHashedName(value);
                return this;
            }

            public Builder clearHashedName() {
                copyOnWrite();
                ((Span) this.instance).clearHashedName();
                return this;
            }

            public boolean hasName() {
                return ((Span) this.instance).hasName();
            }

            public String getName() {
                return ((Span) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((Span) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((Span) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((Span) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((Span) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasId() {
                return ((Span) this.instance).hasId();
            }

            public long getId() {
                return ((Span) this.instance).getId();
            }

            public Builder setId(long value) {
                copyOnWrite();
                ((Span) this.instance).setId(value);
                return this;
            }

            public Builder clearId() {
                copyOnWrite();
                ((Span) this.instance).clearId();
                return this;
            }

            public boolean hasParentId() {
                return ((Span) this.instance).hasParentId();
            }

            public long getParentId() {
                return ((Span) this.instance).getParentId();
            }

            public Builder setParentId(long value) {
                copyOnWrite();
                ((Span) this.instance).setParentId(value);
                return this;
            }

            public Builder clearParentId() {
                copyOnWrite();
                ((Span) this.instance).clearParentId();
                return this;
            }

            public boolean hasStartTimeMs() {
                return ((Span) this.instance).hasStartTimeMs();
            }

            public long getStartTimeMs() {
                return ((Span) this.instance).getStartTimeMs();
            }

            public Builder setStartTimeMs(long value) {
                copyOnWrite();
                ((Span) this.instance).setStartTimeMs(value);
                return this;
            }

            public Builder clearStartTimeMs() {
                copyOnWrite();
                ((Span) this.instance).clearStartTimeMs();
                return this;
            }

            public boolean hasDurationMs() {
                return ((Span) this.instance).hasDurationMs();
            }

            public long getDurationMs() {
                return ((Span) this.instance).getDurationMs();
            }

            public Builder setDurationMs(long value) {
                copyOnWrite();
                ((Span) this.instance).setDurationMs(value);
                return this;
            }

            public Builder clearDurationMs() {
                copyOnWrite();
                ((Span) this.instance).clearDurationMs();
                return this;
            }

            public boolean hasCpuTimeMs() {
                return ((Span) this.instance).hasCpuTimeMs();
            }

            public int getCpuTimeMs() {
                return ((Span) this.instance).getCpuTimeMs();
            }

            public Builder setCpuTimeMs(int value) {
                copyOnWrite();
                ((Span) this.instance).setCpuTimeMs(value);
                return this;
            }

            public Builder clearCpuTimeMs() {
                copyOnWrite();
                ((Span) this.instance).clearCpuTimeMs();
                return this;
            }

            public boolean hasThreadId() {
                return ((Span) this.instance).hasThreadId();
            }

            public long getThreadId() {
                return ((Span) this.instance).getThreadId();
            }

            public Builder setThreadId(long value) {
                copyOnWrite();
                ((Span) this.instance).setThreadId(value);
                return this;
            }

            public Builder clearThreadId() {
                copyOnWrite();
                ((Span) this.instance).clearThreadId();
                return this;
            }

            public boolean hasSpanType() {
                return ((Span) this.instance).hasSpanType();
            }

            public SpanType getSpanType() {
                return ((Span) this.instance).getSpanType();
            }

            public Builder setSpanType(SpanType value) {
                copyOnWrite();
                ((Span) this.instance).setSpanType(value);
                return this;
            }

            public Builder clearSpanType() {
                copyOnWrite();
                ((Span) this.instance).clearSpanType();
                return this;
            }

            public boolean hasMetricExtension() {
                return ((Span) this.instance).hasMetricExtension();
            }

            public ExtensionMetric.MetricExtension getMetricExtension() {
                return ((Span) this.instance).getMetricExtension();
            }

            public Builder setMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((Span) this.instance).setMetricExtension(value);
                return this;
            }

            public Builder setMetricExtension(ExtensionMetric.MetricExtension.Builder builderForValue) {
                copyOnWrite();
                ((Span) this.instance).setMetricExtension(builderForValue);
                return this;
            }

            public Builder mergeMetricExtension(ExtensionMetric.MetricExtension value) {
                copyOnWrite();
                ((Span) this.instance).mergeMetricExtension(value);
                return this;
            }

            public Builder clearMetricExtension() {
                copyOnWrite();
                ((Span) this.instance).clearMetricExtension();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Span();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0000\u0000\u0001\b\u0000\u0002\u0005\u0003\u0003\u0005\u0004\u0004\u0002\u0005\u0005\u0002\u0006\u0006\u0002\b\u0007\f\t\b\u0005\u0001\t\b\u0002\n\u0004\u0007\u000b\t\n", new Object[]{"bitField0_", "constantName_", "id_", "parentId_", "startTimeMs_", "durationMs_", "threadId_", "spanType_", SpanType.internalGetVerifier(), "hashedName_", "name_", "cpuTimeMs_", "metricExtension_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Span> parser = PARSER;
                    if (parser == null) {
                        synchronized (Span.class) {
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
            GeneratedMessageLite.registerDefaultInstance(Span.class, DEFAULT_INSTANCE);
        }

        public static Span getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Span> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CollectionError extends GeneratedMessageLite<CollectionError, Builder> implements CollectionErrorOrBuilder {
        /* access modifiers changed from: private */
        public static final CollectionError DEFAULT_INSTANCE = new CollectionError();
        public static final int NUM_DROPPED_SPANS_FIELD_NUMBER = 5;
        private static volatile Parser<CollectionError> PARSER = null;
        public static final int TIMED_OUT_AFTER_MS_FIELD_NUMBER = 6;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int numDroppedSpans_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long timedOutAfterMs_;

        private CollectionError() {
        }

        public boolean hasNumDroppedSpans() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getNumDroppedSpans() {
            return this.numDroppedSpans_;
        }

        /* access modifiers changed from: private */
        public void setNumDroppedSpans(int value) {
            this.bitField0_ |= 1;
            this.numDroppedSpans_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumDroppedSpans() {
            this.bitField0_ &= -2;
            this.numDroppedSpans_ = 0;
        }

        public boolean hasTimedOutAfterMs() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getTimedOutAfterMs() {
            return this.timedOutAfterMs_;
        }

        /* access modifiers changed from: private */
        public void setTimedOutAfterMs(long value) {
            this.bitField0_ |= 2;
            this.timedOutAfterMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTimedOutAfterMs() {
            this.bitField0_ &= -3;
            this.timedOutAfterMs_ = 0;
        }

        public static CollectionError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CollectionError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CollectionError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CollectionError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CollectionError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CollectionError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CollectionError parseFrom(InputStream input) throws IOException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CollectionError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CollectionError parseDelimitedFrom(InputStream input) throws IOException {
            return (CollectionError) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CollectionError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CollectionError) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CollectionError parseFrom(CodedInputStream input) throws IOException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CollectionError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CollectionError) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CollectionError prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<CollectionError, Builder> implements CollectionErrorOrBuilder {
            private Builder() {
                super(CollectionError.DEFAULT_INSTANCE);
            }

            public boolean hasNumDroppedSpans() {
                return ((CollectionError) this.instance).hasNumDroppedSpans();
            }

            public int getNumDroppedSpans() {
                return ((CollectionError) this.instance).getNumDroppedSpans();
            }

            public Builder setNumDroppedSpans(int value) {
                copyOnWrite();
                ((CollectionError) this.instance).setNumDroppedSpans(value);
                return this;
            }

            public Builder clearNumDroppedSpans() {
                copyOnWrite();
                ((CollectionError) this.instance).clearNumDroppedSpans();
                return this;
            }

            public boolean hasTimedOutAfterMs() {
                return ((CollectionError) this.instance).hasTimedOutAfterMs();
            }

            public long getTimedOutAfterMs() {
                return ((CollectionError) this.instance).getTimedOutAfterMs();
            }

            public Builder setTimedOutAfterMs(long value) {
                copyOnWrite();
                ((CollectionError) this.instance).setTimedOutAfterMs(value);
                return this;
            }

            public Builder clearTimedOutAfterMs() {
                copyOnWrite();
                ((CollectionError) this.instance).clearTimedOutAfterMs();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CollectionError();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0005\u0006\u0002\u0000\u0000\u0000\u0005\u0004\u0000\u0006\u0002\u0001", new Object[]{"bitField0_", "numDroppedSpans_", "timedOutAfterMs_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CollectionError> parser = PARSER;
                    if (parser == null) {
                        synchronized (CollectionError.class) {
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
            GeneratedMessageLite.registerDefaultInstance(CollectionError.class, DEFAULT_INSTANCE);
        }

        public static CollectionError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CollectionError> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
