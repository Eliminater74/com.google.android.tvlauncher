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

public final class ExtensionPhotosScanner {

    private ExtensionPhotosScanner() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface PhotosScannerExtensionOrBuilder extends MessageLiteOrBuilder {
        int getNumPhotosOnExport();

        boolean hasNumPhotosOnExport();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PhotosScannerExtension extends GeneratedMessageLite<PhotosScannerExtension, Builder> implements PhotosScannerExtensionOrBuilder {
        /* access modifiers changed from: private */
        public static final PhotosScannerExtension DEFAULT_INSTANCE = new PhotosScannerExtension();
        public static final int NUM_PHOTOS_ON_EXPORT_FIELD_NUMBER = 1;
        private static volatile Parser<PhotosScannerExtension> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(PhotosScannerExtension.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int numPhotosOnExport_;

        private PhotosScannerExtension() {
        }

        public static PhotosScannerExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PhotosScannerExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PhotosScannerExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PhotosScannerExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PhotosScannerExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PhotosScannerExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PhotosScannerExtension parseFrom(InputStream input) throws IOException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PhotosScannerExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PhotosScannerExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (PhotosScannerExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PhotosScannerExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PhotosScannerExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PhotosScannerExtension parseFrom(CodedInputStream input) throws IOException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PhotosScannerExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PhotosScannerExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PhotosScannerExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static PhotosScannerExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PhotosScannerExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasNumPhotosOnExport() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getNumPhotosOnExport() {
            return this.numPhotosOnExport_;
        }

        /* access modifiers changed from: private */
        public void setNumPhotosOnExport(int value) {
            this.bitField0_ |= 1;
            this.numPhotosOnExport_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumPhotosOnExport() {
            this.bitField0_ &= -2;
            this.numPhotosOnExport_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PhotosScannerExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004\u0000", new Object[]{"bitField0_", "numPhotosOnExport_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PhotosScannerExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (PhotosScannerExtension.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<PhotosScannerExtension, Builder> implements PhotosScannerExtensionOrBuilder {
            private Builder() {
                super(PhotosScannerExtension.DEFAULT_INSTANCE);
            }

            public boolean hasNumPhotosOnExport() {
                return ((PhotosScannerExtension) this.instance).hasNumPhotosOnExport();
            }

            public int getNumPhotosOnExport() {
                return ((PhotosScannerExtension) this.instance).getNumPhotosOnExport();
            }

            public Builder setNumPhotosOnExport(int value) {
                copyOnWrite();
                ((PhotosScannerExtension) this.instance).setNumPhotosOnExport(value);
                return this;
            }

            public Builder clearNumPhotosOnExport() {
                copyOnWrite();
                ((PhotosScannerExtension) this.instance).clearNumPhotosOnExport();
                return this;
            }
        }
    }
}
