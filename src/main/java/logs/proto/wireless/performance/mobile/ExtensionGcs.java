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

public final class ExtensionGcs {

    public interface GcsExtensionOrBuilder extends MessageLiteOrBuilder {
        int getBondingSpecId();

        boolean getIsVpnModeBridge();

        boolean hasBondingSpecId();

        boolean hasIsVpnModeBridge();
    }

    private ExtensionGcs() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class GcsExtension extends GeneratedMessageLite<GcsExtension, Builder> implements GcsExtensionOrBuilder {
        public static final int BONDING_SPEC_ID_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final GcsExtension DEFAULT_INSTANCE = new GcsExtension();
        public static final int IS_VPN_MODE_BRIDGE_FIELD_NUMBER = 1;
        private static volatile Parser<GcsExtension> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.UINT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int bondingSpecId_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean isVpnModeBridge_;

        private GcsExtension() {
        }

        public boolean hasIsVpnModeBridge() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getIsVpnModeBridge() {
            return this.isVpnModeBridge_;
        }

        /* access modifiers changed from: private */
        public void setIsVpnModeBridge(boolean value) {
            this.bitField0_ |= 1;
            this.isVpnModeBridge_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsVpnModeBridge() {
            this.bitField0_ &= -2;
            this.isVpnModeBridge_ = false;
        }

        public boolean hasBondingSpecId() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getBondingSpecId() {
            return this.bondingSpecId_;
        }

        /* access modifiers changed from: private */
        public void setBondingSpecId(int value) {
            this.bitField0_ |= 2;
            this.bondingSpecId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBondingSpecId() {
            this.bitField0_ &= -3;
            this.bondingSpecId_ = 0;
        }

        public static GcsExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GcsExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GcsExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GcsExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GcsExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GcsExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GcsExtension parseFrom(InputStream input) throws IOException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GcsExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GcsExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (GcsExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GcsExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GcsExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GcsExtension parseFrom(CodedInputStream input) throws IOException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GcsExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GcsExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GcsExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<GcsExtension, Builder> implements GcsExtensionOrBuilder {
            private Builder() {
                super(GcsExtension.DEFAULT_INSTANCE);
            }

            public boolean hasIsVpnModeBridge() {
                return ((GcsExtension) this.instance).hasIsVpnModeBridge();
            }

            public boolean getIsVpnModeBridge() {
                return ((GcsExtension) this.instance).getIsVpnModeBridge();
            }

            public Builder setIsVpnModeBridge(boolean value) {
                copyOnWrite();
                ((GcsExtension) this.instance).setIsVpnModeBridge(value);
                return this;
            }

            public Builder clearIsVpnModeBridge() {
                copyOnWrite();
                ((GcsExtension) this.instance).clearIsVpnModeBridge();
                return this;
            }

            public boolean hasBondingSpecId() {
                return ((GcsExtension) this.instance).hasBondingSpecId();
            }

            public int getBondingSpecId() {
                return ((GcsExtension) this.instance).getBondingSpecId();
            }

            public Builder setBondingSpecId(int value) {
                copyOnWrite();
                ((GcsExtension) this.instance).setBondingSpecId(value);
                return this;
            }

            public Builder clearBondingSpecId() {
                copyOnWrite();
                ((GcsExtension) this.instance).clearBondingSpecId();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GcsExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0007\u0000\u0002\u000b\u0001", new Object[]{"bitField0_", "isVpnModeBridge_", "bondingSpecId_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GcsExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (GcsExtension.class) {
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
            GeneratedMessageLite.registerDefaultInstance(GcsExtension.class, DEFAULT_INSTANCE);
        }

        public static GcsExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GcsExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
