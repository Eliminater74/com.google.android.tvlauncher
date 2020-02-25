package com.google.analytics.config.protoverifier.proto;

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

@ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
public final class MutationRules extends GeneratedMessageLite<MutationRules, Builder> implements MutationRulesOrBuilder {
    /* access modifiers changed from: private */
    public static final MutationRules DEFAULT_INSTANCE = new MutationRules();
    public static final int MUTABILITY_FIELD_NUMBER = 1;
    private static volatile Parser<MutationRules> PARSER;

    static {
        GeneratedMessageLite.registerDefaultInstance(MutationRules.class, DEFAULT_INSTANCE);
    }

    @ProtoPresenceBits(mo28548id = 0)
    private int bitField0_;
    @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
    @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
    private int mutability_ = 1;

    private MutationRules() {
    }

    public static MutationRules parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MutationRules parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MutationRules parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MutationRules parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MutationRules parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MutationRules parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MutationRules parseFrom(InputStream input) throws IOException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static MutationRules parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static MutationRules parseDelimitedFrom(InputStream input) throws IOException {
        return (MutationRules) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static MutationRules parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MutationRules) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static MutationRules parseFrom(CodedInputStream input) throws IOException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static MutationRules parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MutationRules) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(MutationRules prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static MutationRules getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MutationRules> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    public boolean hasMutability() {
        return (this.bitField0_ & 1) != 0;
    }

    public Mutability getMutability() {
        Mutability result = Mutability.forNumber(this.mutability_);
        return result == null ? Mutability.MUTABLE : result;
    }

    /* access modifiers changed from: private */
    public void setMutability(Mutability value) {
        if (value != null) {
            this.bitField0_ |= 1;
            this.mutability_ = value.getNumber();
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void clearMutability() {
        this.bitField0_ &= -2;
        this.mutability_ = 1;
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new MutationRules();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\f\u0000", new Object[]{"bitField0_", "mutability_", Mutability.internalGetVerifier()});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<MutationRules> parser = PARSER;
                if (parser == null) {
                    synchronized (MutationRules.class) {
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

    public enum Mutability implements Internal.EnumLite {
        UNKNOWN(0),
        MUTABLE(1),
        IMMUTABLE(2);

        public static final int IMMUTABLE_VALUE = 2;
        public static final int MUTABLE_VALUE = 1;
        public static final int UNKNOWN_VALUE = 0;
        private static final Internal.EnumLiteMap<Mutability> internalValueMap = new Internal.EnumLiteMap<Mutability>() {
            public Mutability findValueByNumber(int number) {
                return Mutability.forNumber(number);
            }
        };
        private final int value;

        private Mutability(int value2) {
            this.value = value2;
        }

        public static Mutability forNumber(int value2) {
            if (value2 == 0) {
                return UNKNOWN;
            }
            if (value2 == 1) {
                return MUTABLE;
            }
            if (value2 != 2) {
                return null;
            }
            return IMMUTABLE;
        }

        public static Internal.EnumLiteMap<Mutability> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return MutabilityVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class MutabilityVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new MutabilityVerifier();

            private MutabilityVerifier() {
            }

            public boolean isInRange(int number) {
                return Mutability.forNumber(number) != null;
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<MutationRules, Builder> implements MutationRulesOrBuilder {
        private Builder() {
            super(MutationRules.DEFAULT_INSTANCE);
        }

        public boolean hasMutability() {
            return ((MutationRules) this.instance).hasMutability();
        }

        public Mutability getMutability() {
            return ((MutationRules) this.instance).getMutability();
        }

        public Builder setMutability(Mutability value) {
            copyOnWrite();
            ((MutationRules) this.instance).setMutability(value);
            return this;
        }

        public Builder clearMutability() {
            copyOnWrite();
            ((MutationRules) this.instance).clearMutability();
            return this;
        }
    }
}
