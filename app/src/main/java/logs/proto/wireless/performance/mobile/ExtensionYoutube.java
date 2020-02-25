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

public final class ExtensionYoutube {

    private ExtensionYoutube() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface InnerTubeRequestInfoOrBuilder extends MessageLiteOrBuilder {
        long getParsingTimeMs();

        int getResponseProtoSizeBytes();

        String getServiceAnnotation(int i);

        ByteString getServiceAnnotationBytes(int i);

        int getServiceAnnotationCount();

        List<String> getServiceAnnotationList();

        boolean hasParsingTimeMs();

        boolean hasResponseProtoSizeBytes();
    }

    public interface YouTubeExtensionOrBuilder extends MessageLiteOrBuilder {
        InnerTubeRequestInfo getInnertubeRequestInfo();

        boolean hasInnertubeRequestInfo();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class YouTubeExtension extends GeneratedMessageLite<YouTubeExtension, Builder> implements YouTubeExtensionOrBuilder {
        /* access modifiers changed from: private */
        public static final YouTubeExtension DEFAULT_INSTANCE = new YouTubeExtension();
        public static final int INNERTUBE_REQUEST_INFO_FIELD_NUMBER = 1;
        private static volatile Parser<YouTubeExtension> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(YouTubeExtension.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private InnerTubeRequestInfo innertubeRequestInfo_;

        private YouTubeExtension() {
        }

        public static YouTubeExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static YouTubeExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static YouTubeExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static YouTubeExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static YouTubeExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static YouTubeExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static YouTubeExtension parseFrom(InputStream input) throws IOException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static YouTubeExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static YouTubeExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (YouTubeExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static YouTubeExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (YouTubeExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static YouTubeExtension parseFrom(CodedInputStream input) throws IOException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static YouTubeExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (YouTubeExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(YouTubeExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static YouTubeExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<YouTubeExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasInnertubeRequestInfo() {
            return (this.bitField0_ & 1) != 0;
        }

        public InnerTubeRequestInfo getInnertubeRequestInfo() {
            InnerTubeRequestInfo innerTubeRequestInfo = this.innertubeRequestInfo_;
            return innerTubeRequestInfo == null ? InnerTubeRequestInfo.getDefaultInstance() : innerTubeRequestInfo;
        }

        /* access modifiers changed from: private */
        public void setInnertubeRequestInfo(InnerTubeRequestInfo value) {
            if (value != null) {
                this.innertubeRequestInfo_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setInnertubeRequestInfo(InnerTubeRequestInfo.Builder builderForValue) {
            this.innertubeRequestInfo_ = (InnerTubeRequestInfo) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeInnertubeRequestInfo(InnerTubeRequestInfo value) {
            if (value != null) {
                InnerTubeRequestInfo innerTubeRequestInfo = this.innertubeRequestInfo_;
                if (innerTubeRequestInfo == null || innerTubeRequestInfo == InnerTubeRequestInfo.getDefaultInstance()) {
                    this.innertubeRequestInfo_ = value;
                } else {
                    this.innertubeRequestInfo_ = (InnerTubeRequestInfo) ((InnerTubeRequestInfo.Builder) InnerTubeRequestInfo.newBuilder(this.innertubeRequestInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearInnertubeRequestInfo() {
            this.innertubeRequestInfo_ = null;
            this.bitField0_ &= -2;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new YouTubeExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t\u0000", new Object[]{"bitField0_", "innertubeRequestInfo_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<YouTubeExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (YouTubeExtension.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<YouTubeExtension, Builder> implements YouTubeExtensionOrBuilder {
            private Builder() {
                super(YouTubeExtension.DEFAULT_INSTANCE);
            }

            public boolean hasInnertubeRequestInfo() {
                return ((YouTubeExtension) this.instance).hasInnertubeRequestInfo();
            }

            public InnerTubeRequestInfo getInnertubeRequestInfo() {
                return ((YouTubeExtension) this.instance).getInnertubeRequestInfo();
            }

            public Builder setInnertubeRequestInfo(InnerTubeRequestInfo value) {
                copyOnWrite();
                ((YouTubeExtension) this.instance).setInnertubeRequestInfo(value);
                return this;
            }

            public Builder setInnertubeRequestInfo(InnerTubeRequestInfo.Builder builderForValue) {
                copyOnWrite();
                ((YouTubeExtension) this.instance).setInnertubeRequestInfo(builderForValue);
                return this;
            }

            public Builder mergeInnertubeRequestInfo(InnerTubeRequestInfo value) {
                copyOnWrite();
                ((YouTubeExtension) this.instance).mergeInnertubeRequestInfo(value);
                return this;
            }

            public Builder clearInnertubeRequestInfo() {
                copyOnWrite();
                ((YouTubeExtension) this.instance).clearInnertubeRequestInfo();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class InnerTubeRequestInfo extends GeneratedMessageLite<InnerTubeRequestInfo, Builder> implements InnerTubeRequestInfoOrBuilder {
        /* access modifiers changed from: private */
        public static final InnerTubeRequestInfo DEFAULT_INSTANCE = new InnerTubeRequestInfo();
        public static final int PARSING_TIME_MS_FIELD_NUMBER = 1;
        public static final int RESPONSE_PROTO_SIZE_BYTES_FIELD_NUMBER = 2;
        public static final int SERVICE_ANNOTATION_FIELD_NUMBER = 3;
        private static volatile Parser<InnerTubeRequestInfo> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(InnerTubeRequestInfo.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long parsingTimeMs_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.UINT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int responseProtoSizeBytes_;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
        private Internal.ProtobufList<String> serviceAnnotation_ = GeneratedMessageLite.emptyProtobufList();

        private InnerTubeRequestInfo() {
        }

        public static InnerTubeRequestInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static InnerTubeRequestInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static InnerTubeRequestInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static InnerTubeRequestInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static InnerTubeRequestInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static InnerTubeRequestInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static InnerTubeRequestInfo parseFrom(InputStream input) throws IOException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static InnerTubeRequestInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static InnerTubeRequestInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (InnerTubeRequestInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static InnerTubeRequestInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (InnerTubeRequestInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static InnerTubeRequestInfo parseFrom(CodedInputStream input) throws IOException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static InnerTubeRequestInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (InnerTubeRequestInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(InnerTubeRequestInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static InnerTubeRequestInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<InnerTubeRequestInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasParsingTimeMs() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getParsingTimeMs() {
            return this.parsingTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setParsingTimeMs(long value) {
            this.bitField0_ |= 1;
            this.parsingTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearParsingTimeMs() {
            this.bitField0_ &= -2;
            this.parsingTimeMs_ = 0;
        }

        public boolean hasResponseProtoSizeBytes() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getResponseProtoSizeBytes() {
            return this.responseProtoSizeBytes_;
        }

        /* access modifiers changed from: private */
        public void setResponseProtoSizeBytes(int value) {
            this.bitField0_ |= 2;
            this.responseProtoSizeBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearResponseProtoSizeBytes() {
            this.bitField0_ &= -3;
            this.responseProtoSizeBytes_ = 0;
        }

        public List<String> getServiceAnnotationList() {
            return this.serviceAnnotation_;
        }

        public int getServiceAnnotationCount() {
            return this.serviceAnnotation_.size();
        }

        public String getServiceAnnotation(int index) {
            return this.serviceAnnotation_.get(index);
        }

        public ByteString getServiceAnnotationBytes(int index) {
            return ByteString.copyFromUtf8(this.serviceAnnotation_.get(index));
        }

        private void ensureServiceAnnotationIsMutable() {
            if (!this.serviceAnnotation_.isModifiable()) {
                this.serviceAnnotation_ = GeneratedMessageLite.mutableCopy(this.serviceAnnotation_);
            }
        }

        /* access modifiers changed from: private */
        public void setServiceAnnotation(int index, String value) {
            if (value != null) {
                ensureServiceAnnotationIsMutable();
                this.serviceAnnotation_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addServiceAnnotation(String value) {
            if (value != null) {
                ensureServiceAnnotationIsMutable();
                this.serviceAnnotation_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<java.lang.String>, com.google.protobuf.Internal$ProtobufList<java.lang.String>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllServiceAnnotation(Iterable<String> values) {
            ensureServiceAnnotationIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.serviceAnnotation_);
        }

        /* access modifiers changed from: private */
        public void clearServiceAnnotation() {
            this.serviceAnnotation_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addServiceAnnotationBytes(ByteString value) {
            if (value != null) {
                ensureServiceAnnotationIsMutable();
                this.serviceAnnotation_.add(value.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new InnerTubeRequestInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001\u0002\u0000\u0002\u000b\u0001\u0003\u001a", new Object[]{"bitField0_", "parsingTimeMs_", "responseProtoSizeBytes_", "serviceAnnotation_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<InnerTubeRequestInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (InnerTubeRequestInfo.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<InnerTubeRequestInfo, Builder> implements InnerTubeRequestInfoOrBuilder {
            private Builder() {
                super(InnerTubeRequestInfo.DEFAULT_INSTANCE);
            }

            public boolean hasParsingTimeMs() {
                return ((InnerTubeRequestInfo) this.instance).hasParsingTimeMs();
            }

            public long getParsingTimeMs() {
                return ((InnerTubeRequestInfo) this.instance).getParsingTimeMs();
            }

            public Builder setParsingTimeMs(long value) {
                copyOnWrite();
                ((InnerTubeRequestInfo) this.instance).setParsingTimeMs(value);
                return this;
            }

            public Builder clearParsingTimeMs() {
                copyOnWrite();
                ((InnerTubeRequestInfo) this.instance).clearParsingTimeMs();
                return this;
            }

            public boolean hasResponseProtoSizeBytes() {
                return ((InnerTubeRequestInfo) this.instance).hasResponseProtoSizeBytes();
            }

            public int getResponseProtoSizeBytes() {
                return ((InnerTubeRequestInfo) this.instance).getResponseProtoSizeBytes();
            }

            public Builder setResponseProtoSizeBytes(int value) {
                copyOnWrite();
                ((InnerTubeRequestInfo) this.instance).setResponseProtoSizeBytes(value);
                return this;
            }

            public Builder clearResponseProtoSizeBytes() {
                copyOnWrite();
                ((InnerTubeRequestInfo) this.instance).clearResponseProtoSizeBytes();
                return this;
            }

            public List<String> getServiceAnnotationList() {
                return Collections.unmodifiableList(((InnerTubeRequestInfo) this.instance).getServiceAnnotationList());
            }

            public int getServiceAnnotationCount() {
                return ((InnerTubeRequestInfo) this.instance).getServiceAnnotationCount();
            }

            public String getServiceAnnotation(int index) {
                return ((InnerTubeRequestInfo) this.instance).getServiceAnnotation(index);
            }

            public ByteString getServiceAnnotationBytes(int index) {
                return ((InnerTubeRequestInfo) this.instance).getServiceAnnotationBytes(index);
            }

            public Builder setServiceAnnotation(int index, String value) {
                copyOnWrite();
                ((InnerTubeRequestInfo) this.instance).setServiceAnnotation(index, value);
                return this;
            }

            public Builder addServiceAnnotation(String value) {
                copyOnWrite();
                ((InnerTubeRequestInfo) this.instance).addServiceAnnotation(value);
                return this;
            }

            public Builder addAllServiceAnnotation(Iterable<String> values) {
                copyOnWrite();
                ((InnerTubeRequestInfo) this.instance).addAllServiceAnnotation(values);
                return this;
            }

            public Builder clearServiceAnnotation() {
                copyOnWrite();
                ((InnerTubeRequestInfo) this.instance).clearServiceAnnotation();
                return this;
            }

            public Builder addServiceAnnotationBytes(ByteString value) {
                copyOnWrite();
                ((InnerTubeRequestInfo) this.instance).addServiceAnnotationBytes(value);
                return this;
            }
        }
    }
}
