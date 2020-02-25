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

import logs.proto.wireless.performance.mobile.android.gmm.AgmmSystemHealthMetrics;

public final class ExtensionGmm {

    private ExtensionGmm() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface GmmExtensionOrBuilder extends MessageLiteOrBuilder {
        int getExternalInvocationType();

        int getRequestId();

        AgmmSystemHealthMetrics.SystemHealthMetricExtension getSystemHealthMetricExtension();

        boolean hasExternalInvocationType();

        boolean hasRequestId();

        boolean hasSystemHealthMetricExtension();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class GmmExtension extends GeneratedMessageLite<GmmExtension, Builder> implements GmmExtensionOrBuilder {
        /* access modifiers changed from: private */
        public static final GmmExtension DEFAULT_INSTANCE = new GmmExtension();
        public static final int EXTERNAL_INVOCATION_TYPE_FIELD_NUMBER = 2;
        public static final int REQUEST_ID_FIELD_NUMBER = 1;
        public static final int SYSTEM_HEALTH_METRIC_EXTENSION_FIELD_NUMBER = 3;
        private static volatile Parser<GmmExtension> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(GmmExtension.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int externalInvocationType_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int requestId_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private AgmmSystemHealthMetrics.SystemHealthMetricExtension systemHealthMetricExtension_;

        private GmmExtension() {
        }

        public static GmmExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmmExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmmExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmmExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmmExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmmExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmmExtension parseFrom(InputStream input) throws IOException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GmmExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GmmExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (GmmExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GmmExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmmExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GmmExtension parseFrom(CodedInputStream input) throws IOException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GmmExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmmExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GmmExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static GmmExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GmmExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasRequestId() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getRequestId() {
            return this.requestId_;
        }

        /* access modifiers changed from: private */
        public void setRequestId(int value) {
            this.bitField0_ |= 1;
            this.requestId_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRequestId() {
            this.bitField0_ &= -2;
            this.requestId_ = 0;
        }

        public boolean hasExternalInvocationType() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getExternalInvocationType() {
            return this.externalInvocationType_;
        }

        /* access modifiers changed from: private */
        public void setExternalInvocationType(int value) {
            this.bitField0_ |= 2;
            this.externalInvocationType_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExternalInvocationType() {
            this.bitField0_ &= -3;
            this.externalInvocationType_ = 0;
        }

        public boolean hasSystemHealthMetricExtension() {
            return (this.bitField0_ & 4) != 0;
        }

        public AgmmSystemHealthMetrics.SystemHealthMetricExtension getSystemHealthMetricExtension() {
            AgmmSystemHealthMetrics.SystemHealthMetricExtension systemHealthMetricExtension = this.systemHealthMetricExtension_;
            return systemHealthMetricExtension == null ? AgmmSystemHealthMetrics.SystemHealthMetricExtension.getDefaultInstance() : systemHealthMetricExtension;
        }

        /* access modifiers changed from: private */
        public void setSystemHealthMetricExtension(AgmmSystemHealthMetrics.SystemHealthMetricExtension value) {
            if (value != null) {
                this.systemHealthMetricExtension_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSystemHealthMetricExtension(AgmmSystemHealthMetrics.SystemHealthMetricExtension.Builder builderForValue) {
            this.systemHealthMetricExtension_ = (AgmmSystemHealthMetrics.SystemHealthMetricExtension) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeSystemHealthMetricExtension(AgmmSystemHealthMetrics.SystemHealthMetricExtension value) {
            if (value != null) {
                AgmmSystemHealthMetrics.SystemHealthMetricExtension systemHealthMetricExtension = this.systemHealthMetricExtension_;
                if (systemHealthMetricExtension == null || systemHealthMetricExtension == AgmmSystemHealthMetrics.SystemHealthMetricExtension.getDefaultInstance()) {
                    this.systemHealthMetricExtension_ = value;
                } else {
                    this.systemHealthMetricExtension_ = (AgmmSystemHealthMetrics.SystemHealthMetricExtension) ((AgmmSystemHealthMetrics.SystemHealthMetricExtension.Builder) AgmmSystemHealthMetrics.SystemHealthMetricExtension.newBuilder(this.systemHealthMetricExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSystemHealthMetricExtension() {
            this.systemHealthMetricExtension_ = null;
            this.bitField0_ &= -5;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GmmExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\t\u0002", new Object[]{"bitField0_", "requestId_", "externalInvocationType_", "systemHealthMetricExtension_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GmmExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (GmmExtension.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<GmmExtension, Builder> implements GmmExtensionOrBuilder {
            private Builder() {
                super(GmmExtension.DEFAULT_INSTANCE);
            }

            public boolean hasRequestId() {
                return ((GmmExtension) this.instance).hasRequestId();
            }

            public int getRequestId() {
                return ((GmmExtension) this.instance).getRequestId();
            }

            public Builder setRequestId(int value) {
                copyOnWrite();
                ((GmmExtension) this.instance).setRequestId(value);
                return this;
            }

            public Builder clearRequestId() {
                copyOnWrite();
                ((GmmExtension) this.instance).clearRequestId();
                return this;
            }

            public boolean hasExternalInvocationType() {
                return ((GmmExtension) this.instance).hasExternalInvocationType();
            }

            public int getExternalInvocationType() {
                return ((GmmExtension) this.instance).getExternalInvocationType();
            }

            public Builder setExternalInvocationType(int value) {
                copyOnWrite();
                ((GmmExtension) this.instance).setExternalInvocationType(value);
                return this;
            }

            public Builder clearExternalInvocationType() {
                copyOnWrite();
                ((GmmExtension) this.instance).clearExternalInvocationType();
                return this;
            }

            public boolean hasSystemHealthMetricExtension() {
                return ((GmmExtension) this.instance).hasSystemHealthMetricExtension();
            }

            public AgmmSystemHealthMetrics.SystemHealthMetricExtension getSystemHealthMetricExtension() {
                return ((GmmExtension) this.instance).getSystemHealthMetricExtension();
            }

            public Builder setSystemHealthMetricExtension(AgmmSystemHealthMetrics.SystemHealthMetricExtension value) {
                copyOnWrite();
                ((GmmExtension) this.instance).setSystemHealthMetricExtension(value);
                return this;
            }

            public Builder setSystemHealthMetricExtension(AgmmSystemHealthMetrics.SystemHealthMetricExtension.Builder builderForValue) {
                copyOnWrite();
                ((GmmExtension) this.instance).setSystemHealthMetricExtension(builderForValue);
                return this;
            }

            public Builder mergeSystemHealthMetricExtension(AgmmSystemHealthMetrics.SystemHealthMetricExtension value) {
                copyOnWrite();
                ((GmmExtension) this.instance).mergeSystemHealthMetricExtension(value);
                return this;
            }

            public Builder clearSystemHealthMetricExtension() {
                copyOnWrite();
                ((GmmExtension) this.instance).clearSystemHealthMetricExtension();
                return this;
            }
        }
    }
}
