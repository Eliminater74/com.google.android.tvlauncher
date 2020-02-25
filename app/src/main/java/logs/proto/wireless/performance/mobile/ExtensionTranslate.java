package logs.proto.wireless.performance.mobile;

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
import com.google.protos.translating.offline.service.CommonEnums;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ExtensionTranslate {

    private ExtensionTranslate() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface TranslateExtensionOrBuilder extends MessageLiteOrBuilder {
        TranslationStats getTranslationStats();

        boolean hasTranslationStats();
    }

    public interface TranslationStatsOrBuilder extends MessageLiteOrBuilder {
        TranslationStats.TranslationEngine getEngine();

        String getPackageVersionCode();

        ByteString getPackageVersionCodeBytes();

        int getQueryLength();

        String getSourceLang();

        ByteString getSourceLangBytes();

        String getTargetLang();

        ByteString getTargetLangBytes();

        CommonEnums.Source getTranslationSource();

        boolean hasEngine();

        boolean hasPackageVersionCode();

        boolean hasQueryLength();

        boolean hasSourceLang();

        boolean hasTargetLang();

        boolean hasTranslationSource();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class TranslateExtension extends GeneratedMessageLite<TranslateExtension, Builder> implements TranslateExtensionOrBuilder {
        /* access modifiers changed from: private */
        public static final TranslateExtension DEFAULT_INSTANCE = new TranslateExtension();
        public static final int TRANSLATION_STATS_FIELD_NUMBER = 1;
        private static volatile Parser<TranslateExtension> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(TranslateExtension.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private TranslationStats translationStats_;

        private TranslateExtension() {
        }

        public static TranslateExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TranslateExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TranslateExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TranslateExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TranslateExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TranslateExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TranslateExtension parseFrom(InputStream input) throws IOException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TranslateExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TranslateExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (TranslateExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TranslateExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TranslateExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TranslateExtension parseFrom(CodedInputStream input) throws IOException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TranslateExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TranslateExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TranslateExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static TranslateExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TranslateExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasTranslationStats() {
            return (this.bitField0_ & 1) != 0;
        }

        public TranslationStats getTranslationStats() {
            TranslationStats translationStats = this.translationStats_;
            return translationStats == null ? TranslationStats.getDefaultInstance() : translationStats;
        }

        /* access modifiers changed from: private */
        public void setTranslationStats(TranslationStats value) {
            if (value != null) {
                this.translationStats_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setTranslationStats(TranslationStats.Builder builderForValue) {
            this.translationStats_ = (TranslationStats) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeTranslationStats(TranslationStats value) {
            if (value != null) {
                TranslationStats translationStats = this.translationStats_;
                if (translationStats == null || translationStats == TranslationStats.getDefaultInstance()) {
                    this.translationStats_ = value;
                } else {
                    this.translationStats_ = (TranslationStats) ((TranslationStats.Builder) TranslationStats.newBuilder(this.translationStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTranslationStats() {
            this.translationStats_ = null;
            this.bitField0_ &= -2;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new TranslateExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t\u0000", new Object[]{"bitField0_", "translationStats_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<TranslateExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (TranslateExtension.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<TranslateExtension, Builder> implements TranslateExtensionOrBuilder {
            private Builder() {
                super(TranslateExtension.DEFAULT_INSTANCE);
            }

            public boolean hasTranslationStats() {
                return ((TranslateExtension) this.instance).hasTranslationStats();
            }

            public TranslationStats getTranslationStats() {
                return ((TranslateExtension) this.instance).getTranslationStats();
            }

            public Builder setTranslationStats(TranslationStats value) {
                copyOnWrite();
                ((TranslateExtension) this.instance).setTranslationStats(value);
                return this;
            }

            public Builder setTranslationStats(TranslationStats.Builder builderForValue) {
                copyOnWrite();
                ((TranslateExtension) this.instance).setTranslationStats(builderForValue);
                return this;
            }

            public Builder mergeTranslationStats(TranslationStats value) {
                copyOnWrite();
                ((TranslateExtension) this.instance).mergeTranslationStats(value);
                return this;
            }

            public Builder clearTranslationStats() {
                copyOnWrite();
                ((TranslateExtension) this.instance).clearTranslationStats();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class TranslationStats extends GeneratedMessageLite<TranslationStats, Builder> implements TranslationStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final TranslationStats DEFAULT_INSTANCE = new TranslationStats();
        public static final int ENGINE_FIELD_NUMBER = 4;
        public static final int PACKAGE_VERSION_CODE_FIELD_NUMBER = 6;
        public static final int QUERY_LENGTH_FIELD_NUMBER = 3;
        public static final int SOURCE_LANG_FIELD_NUMBER = 1;
        public static final int TARGET_LANG_FIELD_NUMBER = 2;
        public static final int TRANSLATION_SOURCE_FIELD_NUMBER = 5;
        private static volatile Parser<TranslationStats> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(TranslationStats.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int engine_;
        @ProtoField(fieldNumber = 6, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private String packageVersionCode_ = "";
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int queryLength_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String sourceLang_ = "";
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String targetLang_ = "";
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int translationSource_;

        private TranslationStats() {
        }

        public static TranslationStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TranslationStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TranslationStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TranslationStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TranslationStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TranslationStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TranslationStats parseFrom(InputStream input) throws IOException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TranslationStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TranslationStats parseDelimitedFrom(InputStream input) throws IOException {
            return (TranslationStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TranslationStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TranslationStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TranslationStats parseFrom(CodedInputStream input) throws IOException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TranslationStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TranslationStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TranslationStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static TranslationStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TranslationStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasSourceLang() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getSourceLang() {
            return this.sourceLang_;
        }

        /* access modifiers changed from: private */
        public void setSourceLang(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.sourceLang_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getSourceLangBytes() {
            return ByteString.copyFromUtf8(this.sourceLang_);
        }

        /* access modifiers changed from: private */
        public void setSourceLangBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.sourceLang_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSourceLang() {
            this.bitField0_ &= -2;
            this.sourceLang_ = getDefaultInstance().getSourceLang();
        }

        public boolean hasTargetLang() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getTargetLang() {
            return this.targetLang_;
        }

        /* access modifiers changed from: private */
        public void setTargetLang(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.targetLang_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getTargetLangBytes() {
            return ByteString.copyFromUtf8(this.targetLang_);
        }

        /* access modifiers changed from: private */
        public void setTargetLangBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.targetLang_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTargetLang() {
            this.bitField0_ &= -3;
            this.targetLang_ = getDefaultInstance().getTargetLang();
        }

        public boolean hasQueryLength() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getQueryLength() {
            return this.queryLength_;
        }

        /* access modifiers changed from: private */
        public void setQueryLength(int value) {
            this.bitField0_ |= 4;
            this.queryLength_ = value;
        }

        /* access modifiers changed from: private */
        public void clearQueryLength() {
            this.bitField0_ &= -5;
            this.queryLength_ = 0;
        }

        public boolean hasEngine() {
            return (this.bitField0_ & 8) != 0;
        }

        public TranslationEngine getEngine() {
            TranslationEngine result = TranslationEngine.forNumber(this.engine_);
            return result == null ? TranslationEngine.TRANSLATION_ENGINE_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setEngine(TranslationEngine value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.engine_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearEngine() {
            this.bitField0_ &= -9;
            this.engine_ = 0;
        }

        public boolean hasTranslationSource() {
            return (this.bitField0_ & 16) != 0;
        }

        public CommonEnums.Source getTranslationSource() {
            CommonEnums.Source result = CommonEnums.Source.forNumber(this.translationSource_);
            return result == null ? CommonEnums.Source.SOURCE_DEFAULT : result;
        }

        /* access modifiers changed from: private */
        public void setTranslationSource(CommonEnums.Source value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.translationSource_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTranslationSource() {
            this.bitField0_ &= -17;
            this.translationSource_ = 0;
        }

        public boolean hasPackageVersionCode() {
            return (this.bitField0_ & 32) != 0;
        }

        public String getPackageVersionCode() {
            return this.packageVersionCode_;
        }

        /* access modifiers changed from: private */
        public void setPackageVersionCode(String value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.packageVersionCode_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getPackageVersionCodeBytes() {
            return ByteString.copyFromUtf8(this.packageVersionCode_);
        }

        /* access modifiers changed from: private */
        public void setPackageVersionCodeBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.packageVersionCode_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageVersionCode() {
            this.bitField0_ &= -33;
            this.packageVersionCode_ = getDefaultInstance().getPackageVersionCode();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new TranslationStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\u0004\u0002\u0004\f\u0003\u0005\f\u0004\u0006\b\u0005", new Object[]{"bitField0_", "sourceLang_", "targetLang_", "queryLength_", "engine_", TranslationEngine.internalGetVerifier(), "translationSource_", CommonEnums.Source.internalGetVerifier(), "packageVersionCode_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<TranslationStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (TranslationStats.class) {
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

        public enum TranslationEngine implements Internal.EnumLite {
            TRANSLATION_ENGINE_UNKNOWN(0),
            TRANSLATION_ENGINE_MOBILE(1),
            TRANSLATION_ENGINE_ONLINE(2);

            public static final int TRANSLATION_ENGINE_MOBILE_VALUE = 1;
            public static final int TRANSLATION_ENGINE_ONLINE_VALUE = 2;
            public static final int TRANSLATION_ENGINE_UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<TranslationEngine> internalValueMap = new Internal.EnumLiteMap<TranslationEngine>() {
                public TranslationEngine findValueByNumber(int number) {
                    return TranslationEngine.forNumber(number);
                }
            };
            private final int value;

            private TranslationEngine(int value2) {
                this.value = value2;
            }

            public static TranslationEngine forNumber(int value2) {
                if (value2 == 0) {
                    return TRANSLATION_ENGINE_UNKNOWN;
                }
                if (value2 == 1) {
                    return TRANSLATION_ENGINE_MOBILE;
                }
                if (value2 != 2) {
                    return null;
                }
                return TRANSLATION_ENGINE_ONLINE;
            }

            public static Internal.EnumLiteMap<TranslationEngine> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TranslationEngineVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class TranslationEngineVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new TranslationEngineVerifier();

                private TranslationEngineVerifier() {
                }

                public boolean isInRange(int number) {
                    return TranslationEngine.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<TranslationStats, Builder> implements TranslationStatsOrBuilder {
            private Builder() {
                super(TranslationStats.DEFAULT_INSTANCE);
            }

            public boolean hasSourceLang() {
                return ((TranslationStats) this.instance).hasSourceLang();
            }

            public String getSourceLang() {
                return ((TranslationStats) this.instance).getSourceLang();
            }

            public Builder setSourceLang(String value) {
                copyOnWrite();
                ((TranslationStats) this.instance).setSourceLang(value);
                return this;
            }

            public ByteString getSourceLangBytes() {
                return ((TranslationStats) this.instance).getSourceLangBytes();
            }

            public Builder setSourceLangBytes(ByteString value) {
                copyOnWrite();
                ((TranslationStats) this.instance).setSourceLangBytes(value);
                return this;
            }

            public Builder clearSourceLang() {
                copyOnWrite();
                ((TranslationStats) this.instance).clearSourceLang();
                return this;
            }

            public boolean hasTargetLang() {
                return ((TranslationStats) this.instance).hasTargetLang();
            }

            public String getTargetLang() {
                return ((TranslationStats) this.instance).getTargetLang();
            }

            public Builder setTargetLang(String value) {
                copyOnWrite();
                ((TranslationStats) this.instance).setTargetLang(value);
                return this;
            }

            public ByteString getTargetLangBytes() {
                return ((TranslationStats) this.instance).getTargetLangBytes();
            }

            public Builder setTargetLangBytes(ByteString value) {
                copyOnWrite();
                ((TranslationStats) this.instance).setTargetLangBytes(value);
                return this;
            }

            public Builder clearTargetLang() {
                copyOnWrite();
                ((TranslationStats) this.instance).clearTargetLang();
                return this;
            }

            public boolean hasQueryLength() {
                return ((TranslationStats) this.instance).hasQueryLength();
            }

            public int getQueryLength() {
                return ((TranslationStats) this.instance).getQueryLength();
            }

            public Builder setQueryLength(int value) {
                copyOnWrite();
                ((TranslationStats) this.instance).setQueryLength(value);
                return this;
            }

            public Builder clearQueryLength() {
                copyOnWrite();
                ((TranslationStats) this.instance).clearQueryLength();
                return this;
            }

            public boolean hasEngine() {
                return ((TranslationStats) this.instance).hasEngine();
            }

            public TranslationEngine getEngine() {
                return ((TranslationStats) this.instance).getEngine();
            }

            public Builder setEngine(TranslationEngine value) {
                copyOnWrite();
                ((TranslationStats) this.instance).setEngine(value);
                return this;
            }

            public Builder clearEngine() {
                copyOnWrite();
                ((TranslationStats) this.instance).clearEngine();
                return this;
            }

            public boolean hasTranslationSource() {
                return ((TranslationStats) this.instance).hasTranslationSource();
            }

            public CommonEnums.Source getTranslationSource() {
                return ((TranslationStats) this.instance).getTranslationSource();
            }

            public Builder setTranslationSource(CommonEnums.Source value) {
                copyOnWrite();
                ((TranslationStats) this.instance).setTranslationSource(value);
                return this;
            }

            public Builder clearTranslationSource() {
                copyOnWrite();
                ((TranslationStats) this.instance).clearTranslationSource();
                return this;
            }

            public boolean hasPackageVersionCode() {
                return ((TranslationStats) this.instance).hasPackageVersionCode();
            }

            public String getPackageVersionCode() {
                return ((TranslationStats) this.instance).getPackageVersionCode();
            }

            public Builder setPackageVersionCode(String value) {
                copyOnWrite();
                ((TranslationStats) this.instance).setPackageVersionCode(value);
                return this;
            }

            public ByteString getPackageVersionCodeBytes() {
                return ((TranslationStats) this.instance).getPackageVersionCodeBytes();
            }

            public Builder setPackageVersionCodeBytes(ByteString value) {
                copyOnWrite();
                ((TranslationStats) this.instance).setPackageVersionCodeBytes(value);
                return this;
            }

            public Builder clearPackageVersionCode() {
                copyOnWrite();
                ((TranslationStats) this.instance).clearPackageVersionCode();
                return this;
            }
        }
    }
}
