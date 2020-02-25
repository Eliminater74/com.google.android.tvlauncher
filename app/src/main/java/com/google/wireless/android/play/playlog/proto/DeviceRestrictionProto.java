package com.google.wireless.android.play.playlog.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtoMessage;
import com.google.protobuf.ProtoSyntax;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class DeviceRestrictionProto {

    private DeviceRestrictionProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface DeviceRestrictionOrBuilder extends MessageLiteOrBuilder {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class DeviceRestriction extends GeneratedMessageLite<DeviceRestriction, Builder> implements DeviceRestrictionOrBuilder {
        /* access modifiers changed from: private */
        public static final DeviceRestriction DEFAULT_INSTANCE = new DeviceRestriction();
        private static volatile Parser<DeviceRestriction> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(DeviceRestriction.class, DEFAULT_INSTANCE);
        }

        private DeviceRestriction() {
        }

        public static DeviceRestriction parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceRestriction parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceRestriction parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceRestriction parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceRestriction parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceRestriction parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceRestriction parseFrom(InputStream input) throws IOException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceRestriction parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceRestriction parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceRestriction) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceRestriction parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceRestriction) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceRestriction parseFrom(CodedInputStream input) throws IOException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceRestriction parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceRestriction) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DeviceRestriction prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static DeviceRestriction getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceRestriction> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DeviceRestriction();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0000", null);
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DeviceRestriction> parser = PARSER;
                    if (parser == null) {
                        synchronized (DeviceRestriction.class) {
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

        /* renamed from: com.google.wireless.android.play.playlog.proto.DeviceRestrictionProto$DeviceRestriction$Id */
        public enum C1930Id implements Internal.EnumLite {
            NO_RESTRICTION(0),
            SIDEWINDER_DEVICE(1),
            LATCHSKY_DEVICE(2);

            public static final int LATCHSKY_DEVICE_VALUE = 2;
            public static final int NO_RESTRICTION_VALUE = 0;
            public static final int SIDEWINDER_DEVICE_VALUE = 1;
            private static final Internal.EnumLiteMap<C1930Id> internalValueMap = new Internal.EnumLiteMap<C1930Id>() {
                public C1930Id findValueByNumber(int number) {
                    return C1930Id.forNumber(number);
                }
            };
            private final int value;

            private C1930Id(int value2) {
                this.value = value2;
            }

            public static C1930Id forNumber(int value2) {
                if (value2 == 0) {
                    return NO_RESTRICTION;
                }
                if (value2 == 1) {
                    return SIDEWINDER_DEVICE;
                }
                if (value2 != 2) {
                    return null;
                }
                return LATCHSKY_DEVICE;
            }

            public static Internal.EnumLiteMap<C1930Id> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return IdVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            /* renamed from: com.google.wireless.android.play.playlog.proto.DeviceRestrictionProto$DeviceRestriction$Id$IdVerifier */
            private static final class IdVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new IdVerifier();

                private IdVerifier() {
                }

                public boolean isInRange(int number) {
                    return C1930Id.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DeviceRestriction, Builder> implements DeviceRestrictionOrBuilder {
            private Builder() {
                super(DeviceRestriction.DEFAULT_INSTANCE);
            }
        }
    }
}
