package com.google.apps.tiktok.tracing;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
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

@ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
public final class Span extends GeneratedMessageLite<Span, Builder> implements SpanOrBuilder {
    public static final int CPU_TIME_MS_FIELD_NUMBER = 7;
    /* access modifiers changed from: private */
    public static final Span DEFAULT_INSTANCE = new Span();
    public static final int DURATION_MS_FIELD_NUMBER = 5;
    public static final int ID_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int PARENT_ID_FIELD_NUMBER = 3;
    private static volatile Parser<Span> PARSER = null;
    public static final int RELATIVE_START_TIME_MS_FIELD_NUMBER = 4;
    public static final int UI_THREAD_FIELD_NUMBER = 6;
    @ProtoPresenceBits(mo28548id = 0)
    private int bitField0_;
    @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.INT32)
    @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
    private int cpuTimeMs_;
    @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT64)
    @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
    private long durationMs_;
    @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
    @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
    private int id_;
    @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
    @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
    private String name_ = "";
    @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
    @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
    private int parentId_;
    @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
    @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
    private long relativeStartTimeMs_;
    @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.BOOL)
    @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
    private boolean uiThread_;

    private Span() {
    }

    public boolean hasName() {
        return (this.bitField0_ & 1) != 0;
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
            this.bitField0_ |= 1;
            this.name_ = value;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void clearName() {
        this.bitField0_ &= -2;
        this.name_ = getDefaultInstance().getName();
    }

    /* access modifiers changed from: private */
    public void setNameBytes(ByteString value) {
        if (value != null) {
            this.bitField0_ |= 1;
            this.name_ = value.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public boolean hasId() {
        return (this.bitField0_ & 2) != 0;
    }

    public int getId() {
        return this.id_;
    }

    /* access modifiers changed from: private */
    public void setId(int value) {
        this.bitField0_ |= 2;
        this.id_ = value;
    }

    /* access modifiers changed from: private */
    public void clearId() {
        this.bitField0_ &= -3;
        this.id_ = 0;
    }

    public boolean hasParentId() {
        return (this.bitField0_ & 4) != 0;
    }

    public int getParentId() {
        return this.parentId_;
    }

    /* access modifiers changed from: private */
    public void setParentId(int value) {
        this.bitField0_ |= 4;
        this.parentId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearParentId() {
        this.bitField0_ &= -5;
        this.parentId_ = 0;
    }

    public boolean hasRelativeStartTimeMs() {
        return (this.bitField0_ & 8) != 0;
    }

    public long getRelativeStartTimeMs() {
        return this.relativeStartTimeMs_;
    }

    /* access modifiers changed from: private */
    public void setRelativeStartTimeMs(long value) {
        this.bitField0_ |= 8;
        this.relativeStartTimeMs_ = value;
    }

    /* access modifiers changed from: private */
    public void clearRelativeStartTimeMs() {
        this.bitField0_ &= -9;
        this.relativeStartTimeMs_ = 0;
    }

    public boolean hasDurationMs() {
        return (this.bitField0_ & 16) != 0;
    }

    public long getDurationMs() {
        return this.durationMs_;
    }

    /* access modifiers changed from: private */
    public void setDurationMs(long value) {
        this.bitField0_ |= 16;
        this.durationMs_ = value;
    }

    /* access modifiers changed from: private */
    public void clearDurationMs() {
        this.bitField0_ &= -17;
        this.durationMs_ = 0;
    }

    public boolean hasCpuTimeMs() {
        return (this.bitField0_ & 32) != 0;
    }

    public int getCpuTimeMs() {
        return this.cpuTimeMs_;
    }

    /* access modifiers changed from: private */
    public void setCpuTimeMs(int value) {
        this.bitField0_ |= 32;
        this.cpuTimeMs_ = value;
    }

    /* access modifiers changed from: private */
    public void clearCpuTimeMs() {
        this.bitField0_ &= -33;
        this.cpuTimeMs_ = 0;
    }

    public boolean hasUiThread() {
        return (this.bitField0_ & 64) != 0;
    }

    public boolean getUiThread() {
        return this.uiThread_;
    }

    /* access modifiers changed from: private */
    public void setUiThread(boolean value) {
        this.bitField0_ |= 64;
        this.uiThread_ = value;
    }

    /* access modifiers changed from: private */
    public void clearUiThread() {
        this.bitField0_ &= -65;
        this.uiThread_ = false;
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

        public int getId() {
            return ((Span) this.instance).getId();
        }

        public Builder setId(int value) {
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

        public int getParentId() {
            return ((Span) this.instance).getParentId();
        }

        public Builder setParentId(int value) {
            copyOnWrite();
            ((Span) this.instance).setParentId(value);
            return this;
        }

        public Builder clearParentId() {
            copyOnWrite();
            ((Span) this.instance).clearParentId();
            return this;
        }

        public boolean hasRelativeStartTimeMs() {
            return ((Span) this.instance).hasRelativeStartTimeMs();
        }

        public long getRelativeStartTimeMs() {
            return ((Span) this.instance).getRelativeStartTimeMs();
        }

        public Builder setRelativeStartTimeMs(long value) {
            copyOnWrite();
            ((Span) this.instance).setRelativeStartTimeMs(value);
            return this;
        }

        public Builder clearRelativeStartTimeMs() {
            copyOnWrite();
            ((Span) this.instance).clearRelativeStartTimeMs();
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

        public boolean hasUiThread() {
            return ((Span) this.instance).hasUiThread();
        }

        public boolean getUiThread() {
            return ((Span) this.instance).getUiThread();
        }

        public Builder setUiThread(boolean value) {
            copyOnWrite();
            ((Span) this.instance).setUiThread(value);
            return this;
        }

        public Builder clearUiThread() {
            copyOnWrite();
            ((Span) this.instance).clearUiThread();
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
                return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001\b\u0000\u0002\u0004\u0001\u0003\u0004\u0002\u0004\u0002\u0003\u0005\u0002\u0004\u0006\u0007\u0006\u0007\u0004\u0005", new Object[]{"bitField0_", "name_", "id_", "parentId_", "relativeStartTimeMs_", "durationMs_", "uiThread_", "cpuTimeMs_"});
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
