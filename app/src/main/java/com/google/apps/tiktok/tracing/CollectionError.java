package com.google.apps.tiktok.tracing;

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

@ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
public final class CollectionError extends GeneratedMessageLite<CollectionError, Builder> implements CollectionErrorOrBuilder {
    /* access modifiers changed from: private */
    public static final CollectionError DEFAULT_INSTANCE = new CollectionError();
    public static final int TIMED_OUT_FIELD_NUMBER = 2;
    public static final int TOO_MANY_SPANS_FIELD_NUMBER = 1;
    private static volatile Parser<CollectionError> PARSER = null;

    static {
        GeneratedMessageLite.registerDefaultInstance(CollectionError.class, DEFAULT_INSTANCE);
    }

    @ProtoPresenceBits(mo28548id = 0)
    private int bitField0_;
    @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
    @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
    private TimedOut timedOut_;
    @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
    @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
    private TooManySpans tooManySpans_;

    private CollectionError() {
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

    public static CollectionError getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<CollectionError> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    public boolean hasTooManySpans() {
        return (this.bitField0_ & 1) != 0;
    }

    public TooManySpans getTooManySpans() {
        TooManySpans tooManySpans = this.tooManySpans_;
        return tooManySpans == null ? TooManySpans.getDefaultInstance() : tooManySpans;
    }

    /* access modifiers changed from: private */
    public void setTooManySpans(TooManySpans value) {
        if (value != null) {
            this.tooManySpans_ = value;
            this.bitField0_ |= 1;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void setTooManySpans(TooManySpans.Builder builderForValue) {
        this.tooManySpans_ = (TooManySpans) builderForValue.build();
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeTooManySpans(TooManySpans value) {
        if (value != null) {
            TooManySpans tooManySpans = this.tooManySpans_;
            if (tooManySpans == null || tooManySpans == TooManySpans.getDefaultInstance()) {
                this.tooManySpans_ = value;
            } else {
                this.tooManySpans_ = (TooManySpans) ((TooManySpans.Builder) TooManySpans.newBuilder(this.tooManySpans_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
            }
            this.bitField0_ |= 1;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void clearTooManySpans() {
        this.tooManySpans_ = null;
        this.bitField0_ &= -2;
    }

    public boolean hasTimedOut() {
        return (this.bitField0_ & 2) != 0;
    }

    public TimedOut getTimedOut() {
        TimedOut timedOut = this.timedOut_;
        return timedOut == null ? TimedOut.getDefaultInstance() : timedOut;
    }

    /* access modifiers changed from: private */
    public void setTimedOut(TimedOut value) {
        if (value != null) {
            this.timedOut_ = value;
            this.bitField0_ |= 2;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void setTimedOut(TimedOut.Builder builderForValue) {
        this.timedOut_ = (TimedOut) builderForValue.build();
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void mergeTimedOut(TimedOut value) {
        if (value != null) {
            TimedOut timedOut = this.timedOut_;
            if (timedOut == null || timedOut == TimedOut.getDefaultInstance()) {
                this.timedOut_ = value;
            } else {
                this.timedOut_ = (TimedOut) ((TimedOut.Builder) TimedOut.newBuilder(this.timedOut_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
            }
            this.bitField0_ |= 2;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void clearTimedOut() {
        this.timedOut_ = null;
        this.bitField0_ &= -3;
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new CollectionError();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001", new Object[]{"bitField0_", "tooManySpans_", "timedOut_"});
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

    public interface TimedOutOrBuilder extends MessageLiteOrBuilder {
        int getNumUnfinishedSpans();

        long getTimedOutAfterMs();

        boolean hasNumUnfinishedSpans();

        boolean hasTimedOutAfterMs();
    }

    public interface TooManySpansOrBuilder extends MessageLiteOrBuilder {
        int getNumDroppedSpans();

        boolean hasNumDroppedSpans();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class TooManySpans extends GeneratedMessageLite<TooManySpans, Builder> implements TooManySpansOrBuilder {
        /* access modifiers changed from: private */
        public static final TooManySpans DEFAULT_INSTANCE = new TooManySpans();
        public static final int NUM_DROPPED_SPANS_FIELD_NUMBER = 1;
        private static volatile Parser<TooManySpans> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(TooManySpans.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int numDroppedSpans_;

        private TooManySpans() {
        }

        public static TooManySpans parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TooManySpans parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TooManySpans parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TooManySpans parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TooManySpans parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TooManySpans parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TooManySpans parseFrom(InputStream input) throws IOException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TooManySpans parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TooManySpans parseDelimitedFrom(InputStream input) throws IOException {
            return (TooManySpans) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TooManySpans parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TooManySpans) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TooManySpans parseFrom(CodedInputStream input) throws IOException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TooManySpans parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TooManySpans) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TooManySpans prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static TooManySpans getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TooManySpans> parser() {
            return DEFAULT_INSTANCE.getParserForType();
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

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new TooManySpans();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004\u0000", new Object[]{"bitField0_", "numDroppedSpans_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<TooManySpans> parser = PARSER;
                    if (parser == null) {
                        synchronized (TooManySpans.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<TooManySpans, Builder> implements TooManySpansOrBuilder {
            private Builder() {
                super(TooManySpans.DEFAULT_INSTANCE);
            }

            public boolean hasNumDroppedSpans() {
                return ((TooManySpans) this.instance).hasNumDroppedSpans();
            }

            public int getNumDroppedSpans() {
                return ((TooManySpans) this.instance).getNumDroppedSpans();
            }

            public Builder setNumDroppedSpans(int value) {
                copyOnWrite();
                ((TooManySpans) this.instance).setNumDroppedSpans(value);
                return this;
            }

            public Builder clearNumDroppedSpans() {
                copyOnWrite();
                ((TooManySpans) this.instance).clearNumDroppedSpans();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class TimedOut extends GeneratedMessageLite<TimedOut, Builder> implements TimedOutOrBuilder {
        /* access modifiers changed from: private */
        public static final TimedOut DEFAULT_INSTANCE = new TimedOut();
        public static final int NUM_UNFINISHED_SPANS_FIELD_NUMBER = 2;
        public static final int TIMED_OUT_AFTER_MS_FIELD_NUMBER = 1;
        private static volatile Parser<TimedOut> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(TimedOut.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int numUnfinishedSpans_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long timedOutAfterMs_;

        private TimedOut() {
        }

        public static TimedOut parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TimedOut parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TimedOut parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TimedOut parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TimedOut parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TimedOut parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TimedOut parseFrom(InputStream input) throws IOException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TimedOut parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TimedOut parseDelimitedFrom(InputStream input) throws IOException {
            return (TimedOut) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TimedOut parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TimedOut) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TimedOut parseFrom(CodedInputStream input) throws IOException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TimedOut parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TimedOut) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TimedOut prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static TimedOut getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TimedOut> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasTimedOutAfterMs() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getTimedOutAfterMs() {
            return this.timedOutAfterMs_;
        }

        /* access modifiers changed from: private */
        public void setTimedOutAfterMs(long value) {
            this.bitField0_ |= 1;
            this.timedOutAfterMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTimedOutAfterMs() {
            this.bitField0_ &= -2;
            this.timedOutAfterMs_ = 0;
        }

        public boolean hasNumUnfinishedSpans() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumUnfinishedSpans() {
            return this.numUnfinishedSpans_;
        }

        /* access modifiers changed from: private */
        public void setNumUnfinishedSpans(int value) {
            this.bitField0_ |= 2;
            this.numUnfinishedSpans_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumUnfinishedSpans() {
            this.bitField0_ &= -3;
            this.numUnfinishedSpans_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new TimedOut();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0000\u0002\u0004\u0001", new Object[]{"bitField0_", "timedOutAfterMs_", "numUnfinishedSpans_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<TimedOut> parser = PARSER;
                    if (parser == null) {
                        synchronized (TimedOut.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<TimedOut, Builder> implements TimedOutOrBuilder {
            private Builder() {
                super(TimedOut.DEFAULT_INSTANCE);
            }

            public boolean hasTimedOutAfterMs() {
                return ((TimedOut) this.instance).hasTimedOutAfterMs();
            }

            public long getTimedOutAfterMs() {
                return ((TimedOut) this.instance).getTimedOutAfterMs();
            }

            public Builder setTimedOutAfterMs(long value) {
                copyOnWrite();
                ((TimedOut) this.instance).setTimedOutAfterMs(value);
                return this;
            }

            public Builder clearTimedOutAfterMs() {
                copyOnWrite();
                ((TimedOut) this.instance).clearTimedOutAfterMs();
                return this;
            }

            public boolean hasNumUnfinishedSpans() {
                return ((TimedOut) this.instance).hasNumUnfinishedSpans();
            }

            public int getNumUnfinishedSpans() {
                return ((TimedOut) this.instance).getNumUnfinishedSpans();
            }

            public Builder setNumUnfinishedSpans(int value) {
                copyOnWrite();
                ((TimedOut) this.instance).setNumUnfinishedSpans(value);
                return this;
            }

            public Builder clearNumUnfinishedSpans() {
                copyOnWrite();
                ((TimedOut) this.instance).clearNumUnfinishedSpans();
                return this;
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<CollectionError, Builder> implements CollectionErrorOrBuilder {
        private Builder() {
            super(CollectionError.DEFAULT_INSTANCE);
        }

        public boolean hasTooManySpans() {
            return ((CollectionError) this.instance).hasTooManySpans();
        }

        public TooManySpans getTooManySpans() {
            return ((CollectionError) this.instance).getTooManySpans();
        }

        public Builder setTooManySpans(TooManySpans value) {
            copyOnWrite();
            ((CollectionError) this.instance).setTooManySpans(value);
            return this;
        }

        public Builder setTooManySpans(TooManySpans.Builder builderForValue) {
            copyOnWrite();
            ((CollectionError) this.instance).setTooManySpans(builderForValue);
            return this;
        }

        public Builder mergeTooManySpans(TooManySpans value) {
            copyOnWrite();
            ((CollectionError) this.instance).mergeTooManySpans(value);
            return this;
        }

        public Builder clearTooManySpans() {
            copyOnWrite();
            ((CollectionError) this.instance).clearTooManySpans();
            return this;
        }

        public boolean hasTimedOut() {
            return ((CollectionError) this.instance).hasTimedOut();
        }

        public TimedOut getTimedOut() {
            return ((CollectionError) this.instance).getTimedOut();
        }

        public Builder setTimedOut(TimedOut value) {
            copyOnWrite();
            ((CollectionError) this.instance).setTimedOut(value);
            return this;
        }

        public Builder setTimedOut(TimedOut.Builder builderForValue) {
            copyOnWrite();
            ((CollectionError) this.instance).setTimedOut(builderForValue);
            return this;
        }

        public Builder mergeTimedOut(TimedOut value) {
            copyOnWrite();
            ((CollectionError) this.instance).mergeTimedOut(value);
            return this;
        }

        public Builder clearTimedOut() {
            copyOnWrite();
            ((CollectionError) this.instance).clearTimedOut();
            return this;
        }
    }
}
