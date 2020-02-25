package com.google.apps.tiktok.tracing;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
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

@ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
public final class TraceRecord extends GeneratedMessageLite<TraceRecord, Builder> implements TraceRecordOrBuilder {
    /* access modifiers changed from: private */
    public static final TraceRecord DEFAULT_INSTANCE = new TraceRecord();
    public static final int ERROR_FIELD_NUMBER = 10;
    public static final int SPANS_FIELD_NUMBER = 3;
    public static final int START_ELAPSED_TIME_MS_FIELD_NUMBER = 5;
    public static final int START_TIME_MS_FIELD_NUMBER = 4;
    public static final int UUID_LEAST_SIGNIFICANT_BITS_FIELD_NUMBER = 2;
    public static final int UUID_MOST_SIGNIFICANT_BITS_FIELD_NUMBER = 1;
    private static volatile Parser<TraceRecord> PARSER = null;

    static {
        GeneratedMessageLite.registerDefaultInstance(TraceRecord.class, DEFAULT_INSTANCE);
    }

    @ProtoPresenceBits(mo28548id = 0)
    private int bitField0_;
    @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.MESSAGE)
    @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
    private CollectionError error_;
    @ProtoField(fieldNumber = 3, type = FieldType.MESSAGE_LIST)
    private Internal.ProtobufList<Span> spans_ = emptyProtobufList();
    @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT64)
    @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
    private long startElapsedTimeMs_;
    @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
    @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
    private long startTimeMs_;
    @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.FIXED64)
    @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
    private long uuidLeastSignificantBits_;
    @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.FIXED64)
    @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
    private long uuidMostSignificantBits_;

    private TraceRecord() {
    }

    public static TraceRecord parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static TraceRecord parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static TraceRecord parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static TraceRecord parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static TraceRecord parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static TraceRecord parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static TraceRecord parseFrom(InputStream input) throws IOException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static TraceRecord parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static TraceRecord parseDelimitedFrom(InputStream input) throws IOException {
        return (TraceRecord) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static TraceRecord parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (TraceRecord) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static TraceRecord parseFrom(CodedInputStream input) throws IOException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static TraceRecord parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (TraceRecord) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(TraceRecord prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static TraceRecord getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<TraceRecord> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    public boolean hasUuidMostSignificantBits() {
        return (this.bitField0_ & 1) != 0;
    }

    public long getUuidMostSignificantBits() {
        return this.uuidMostSignificantBits_;
    }

    /* access modifiers changed from: private */
    public void setUuidMostSignificantBits(long value) {
        this.bitField0_ |= 1;
        this.uuidMostSignificantBits_ = value;
    }

    /* access modifiers changed from: private */
    public void clearUuidMostSignificantBits() {
        this.bitField0_ &= -2;
        this.uuidMostSignificantBits_ = 0;
    }

    public boolean hasUuidLeastSignificantBits() {
        return (this.bitField0_ & 2) != 0;
    }

    public long getUuidLeastSignificantBits() {
        return this.uuidLeastSignificantBits_;
    }

    /* access modifiers changed from: private */
    public void setUuidLeastSignificantBits(long value) {
        this.bitField0_ |= 2;
        this.uuidLeastSignificantBits_ = value;
    }

    /* access modifiers changed from: private */
    public void clearUuidLeastSignificantBits() {
        this.bitField0_ &= -3;
        this.uuidLeastSignificantBits_ = 0;
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
     arg types: [java.lang.Iterable<? extends com.google.apps.tiktok.tracing.Span>, com.google.protobuf.Internal$ProtobufList<com.google.apps.tiktok.tracing.Span>]
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

    public boolean hasStartTimeMs() {
        return (this.bitField0_ & 4) != 0;
    }

    public long getStartTimeMs() {
        return this.startTimeMs_;
    }

    /* access modifiers changed from: private */
    public void setStartTimeMs(long value) {
        this.bitField0_ |= 4;
        this.startTimeMs_ = value;
    }

    /* access modifiers changed from: private */
    public void clearStartTimeMs() {
        this.bitField0_ &= -5;
        this.startTimeMs_ = 0;
    }

    public boolean hasStartElapsedTimeMs() {
        return (this.bitField0_ & 8) != 0;
    }

    public long getStartElapsedTimeMs() {
        return this.startElapsedTimeMs_;
    }

    /* access modifiers changed from: private */
    public void setStartElapsedTimeMs(long value) {
        this.bitField0_ |= 8;
        this.startElapsedTimeMs_ = value;
    }

    /* access modifiers changed from: private */
    public void clearStartElapsedTimeMs() {
        this.bitField0_ &= -9;
        this.startElapsedTimeMs_ = 0;
    }

    public boolean hasError() {
        return (this.bitField0_ & 16) != 0;
    }

    public CollectionError getError() {
        CollectionError collectionError = this.error_;
        return collectionError == null ? CollectionError.getDefaultInstance() : collectionError;
    }

    /* access modifiers changed from: private */
    public void setError(CollectionError value) {
        if (value != null) {
            this.error_ = value;
            this.bitField0_ |= 16;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void setError(CollectionError.Builder builderForValue) {
        this.error_ = (CollectionError) builderForValue.build();
        this.bitField0_ |= 16;
    }

    /* access modifiers changed from: private */
    public void mergeError(CollectionError value) {
        if (value != null) {
            CollectionError collectionError = this.error_;
            if (collectionError == null || collectionError == CollectionError.getDefaultInstance()) {
                this.error_ = value;
            } else {
                this.error_ = (CollectionError) ((CollectionError.Builder) CollectionError.newBuilder(this.error_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
            }
            this.bitField0_ |= 16;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void clearError() {
        this.error_ = null;
        this.bitField0_ &= -17;
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new TraceRecord();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\n\u0006\u0000\u0001\u0000\u0001\u0005\u0000\u0002\u0005\u0001\u0003\u001b\u0004\u0002\u0002\u0005\u0002\u0003\n\t\u0004", new Object[]{"bitField0_", "uuidMostSignificantBits_", "uuidLeastSignificantBits_", "spans_", Span.class, "startTimeMs_", "startElapsedTimeMs_", "error_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<TraceRecord> parser = PARSER;
                if (parser == null) {
                    synchronized (TraceRecord.class) {
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

    public static final class Builder extends GeneratedMessageLite.Builder<TraceRecord, Builder> implements TraceRecordOrBuilder {
        private Builder() {
            super(TraceRecord.DEFAULT_INSTANCE);
        }

        public boolean hasUuidMostSignificantBits() {
            return ((TraceRecord) this.instance).hasUuidMostSignificantBits();
        }

        public long getUuidMostSignificantBits() {
            return ((TraceRecord) this.instance).getUuidMostSignificantBits();
        }

        public Builder setUuidMostSignificantBits(long value) {
            copyOnWrite();
            ((TraceRecord) this.instance).setUuidMostSignificantBits(value);
            return this;
        }

        public Builder clearUuidMostSignificantBits() {
            copyOnWrite();
            ((TraceRecord) this.instance).clearUuidMostSignificantBits();
            return this;
        }

        public boolean hasUuidLeastSignificantBits() {
            return ((TraceRecord) this.instance).hasUuidLeastSignificantBits();
        }

        public long getUuidLeastSignificantBits() {
            return ((TraceRecord) this.instance).getUuidLeastSignificantBits();
        }

        public Builder setUuidLeastSignificantBits(long value) {
            copyOnWrite();
            ((TraceRecord) this.instance).setUuidLeastSignificantBits(value);
            return this;
        }

        public Builder clearUuidLeastSignificantBits() {
            copyOnWrite();
            ((TraceRecord) this.instance).clearUuidLeastSignificantBits();
            return this;
        }

        public List<Span> getSpansList() {
            return Collections.unmodifiableList(((TraceRecord) this.instance).getSpansList());
        }

        public int getSpansCount() {
            return ((TraceRecord) this.instance).getSpansCount();
        }

        public Span getSpans(int index) {
            return ((TraceRecord) this.instance).getSpans(index);
        }

        public Builder setSpans(int index, Span value) {
            copyOnWrite();
            ((TraceRecord) this.instance).setSpans(index, value);
            return this;
        }

        public Builder setSpans(int index, Span.Builder builderForValue) {
            copyOnWrite();
            ((TraceRecord) this.instance).setSpans(index, builderForValue);
            return this;
        }

        public Builder addSpans(Span value) {
            copyOnWrite();
            ((TraceRecord) this.instance).addSpans(value);
            return this;
        }

        public Builder addSpans(int index, Span value) {
            copyOnWrite();
            ((TraceRecord) this.instance).addSpans(index, value);
            return this;
        }

        public Builder addSpans(Span.Builder builderForValue) {
            copyOnWrite();
            ((TraceRecord) this.instance).addSpans(builderForValue);
            return this;
        }

        public Builder addSpans(int index, Span.Builder builderForValue) {
            copyOnWrite();
            ((TraceRecord) this.instance).addSpans(index, builderForValue);
            return this;
        }

        public Builder addAllSpans(Iterable<? extends Span> values) {
            copyOnWrite();
            ((TraceRecord) this.instance).addAllSpans(values);
            return this;
        }

        public Builder clearSpans() {
            copyOnWrite();
            ((TraceRecord) this.instance).clearSpans();
            return this;
        }

        public Builder removeSpans(int index) {
            copyOnWrite();
            ((TraceRecord) this.instance).removeSpans(index);
            return this;
        }

        public boolean hasStartTimeMs() {
            return ((TraceRecord) this.instance).hasStartTimeMs();
        }

        public long getStartTimeMs() {
            return ((TraceRecord) this.instance).getStartTimeMs();
        }

        public Builder setStartTimeMs(long value) {
            copyOnWrite();
            ((TraceRecord) this.instance).setStartTimeMs(value);
            return this;
        }

        public Builder clearStartTimeMs() {
            copyOnWrite();
            ((TraceRecord) this.instance).clearStartTimeMs();
            return this;
        }

        public boolean hasStartElapsedTimeMs() {
            return ((TraceRecord) this.instance).hasStartElapsedTimeMs();
        }

        public long getStartElapsedTimeMs() {
            return ((TraceRecord) this.instance).getStartElapsedTimeMs();
        }

        public Builder setStartElapsedTimeMs(long value) {
            copyOnWrite();
            ((TraceRecord) this.instance).setStartElapsedTimeMs(value);
            return this;
        }

        public Builder clearStartElapsedTimeMs() {
            copyOnWrite();
            ((TraceRecord) this.instance).clearStartElapsedTimeMs();
            return this;
        }

        public boolean hasError() {
            return ((TraceRecord) this.instance).hasError();
        }

        public CollectionError getError() {
            return ((TraceRecord) this.instance).getError();
        }

        public Builder setError(CollectionError value) {
            copyOnWrite();
            ((TraceRecord) this.instance).setError(value);
            return this;
        }

        public Builder setError(CollectionError.Builder builderForValue) {
            copyOnWrite();
            ((TraceRecord) this.instance).setError(builderForValue);
            return this;
        }

        public Builder mergeError(CollectionError value) {
            copyOnWrite();
            ((TraceRecord) this.instance).mergeError(value);
            return this;
        }

        public Builder clearError() {
            copyOnWrite();
            ((TraceRecord) this.instance).clearError();
            return this;
        }
    }
}
