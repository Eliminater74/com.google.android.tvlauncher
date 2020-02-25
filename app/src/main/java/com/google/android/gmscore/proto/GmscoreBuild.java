package com.google.android.gmscore.proto;

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

public final class GmscoreBuild {

    private GmscoreBuild() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface GmsCoreBuildOrBuilder extends MessageLiteOrBuilder {
        GmsCoreBuildVariant getVariant();

        int getVersionCode();

        String getVersionName();

        ByteString getVersionNameBytes();

        boolean hasVariant();

        boolean hasVersionCode();

        boolean hasVersionName();
    }

    public interface GmsCoreBuildVariantOrBuilder extends MessageLiteOrBuilder {
        GmsCoreBuildVariant.GmsCoreArchitecture getArchitecture();

        GmsCoreBuildVariant.GmsCoreBuildType getBuildType();

        GmsCoreBuildVariant.GmsCoreScreenDensity getScreenDensity();

        boolean hasArchitecture();

        boolean hasBuildType();

        boolean hasScreenDensity();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class GmsCoreBuild extends GeneratedMessageLite<GmsCoreBuild, Builder> implements GmsCoreBuildOrBuilder {
        /* access modifiers changed from: private */
        public static final GmsCoreBuild DEFAULT_INSTANCE = new GmsCoreBuild();
        public static final int VARIANT_FIELD_NUMBER = 3;
        public static final int VERSION_CODE_FIELD_NUMBER = 1;
        public static final int VERSION_NAME_FIELD_NUMBER = 2;
        private static volatile Parser<GmsCoreBuild> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(GmsCoreBuild.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private GmsCoreBuildVariant variant_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int versionCode_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String versionName_ = "";

        private GmsCoreBuild() {
        }

        public static GmsCoreBuild parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmsCoreBuild parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmsCoreBuild parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmsCoreBuild parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmsCoreBuild parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmsCoreBuild parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmsCoreBuild parseFrom(InputStream input) throws IOException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GmsCoreBuild parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GmsCoreBuild parseDelimitedFrom(InputStream input) throws IOException {
            return (GmsCoreBuild) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GmsCoreBuild parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmsCoreBuild) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GmsCoreBuild parseFrom(CodedInputStream input) throws IOException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GmsCoreBuild parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmsCoreBuild) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GmsCoreBuild prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static GmsCoreBuild getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GmsCoreBuild> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasVersionCode() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getVersionCode() {
            return this.versionCode_;
        }

        /* access modifiers changed from: private */
        public void setVersionCode(int value) {
            this.bitField0_ |= 1;
            this.versionCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearVersionCode() {
            this.bitField0_ &= -2;
            this.versionCode_ = 0;
        }

        public boolean hasVersionName() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getVersionName() {
            return this.versionName_;
        }

        /* access modifiers changed from: private */
        public void setVersionName(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.versionName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getVersionNameBytes() {
            return ByteString.copyFromUtf8(this.versionName_);
        }

        /* access modifiers changed from: private */
        public void setVersionNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.versionName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearVersionName() {
            this.bitField0_ &= -3;
            this.versionName_ = getDefaultInstance().getVersionName();
        }

        public boolean hasVariant() {
            return (this.bitField0_ & 4) != 0;
        }

        public GmsCoreBuildVariant getVariant() {
            GmsCoreBuildVariant gmsCoreBuildVariant = this.variant_;
            return gmsCoreBuildVariant == null ? GmsCoreBuildVariant.getDefaultInstance() : gmsCoreBuildVariant;
        }

        /* access modifiers changed from: private */
        public void setVariant(GmsCoreBuildVariant value) {
            if (value != null) {
                this.variant_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setVariant(GmsCoreBuildVariant.Builder builderForValue) {
            this.variant_ = (GmsCoreBuildVariant) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeVariant(GmsCoreBuildVariant value) {
            if (value != null) {
                GmsCoreBuildVariant gmsCoreBuildVariant = this.variant_;
                if (gmsCoreBuildVariant == null || gmsCoreBuildVariant == GmsCoreBuildVariant.getDefaultInstance()) {
                    this.variant_ = value;
                } else {
                    this.variant_ = (GmsCoreBuildVariant) ((GmsCoreBuildVariant.Builder) GmsCoreBuildVariant.newBuilder(this.variant_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearVariant() {
            this.variant_ = null;
            this.bitField0_ &= -5;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GmsCoreBuild();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\b\u0001\u0003\t\u0002", new Object[]{"bitField0_", "versionCode_", "versionName_", "variant_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GmsCoreBuild> parser = PARSER;
                    if (parser == null) {
                        synchronized (GmsCoreBuild.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<GmsCoreBuild, Builder> implements GmsCoreBuildOrBuilder {
            private Builder() {
                super(GmsCoreBuild.DEFAULT_INSTANCE);
            }

            public boolean hasVersionCode() {
                return ((GmsCoreBuild) this.instance).hasVersionCode();
            }

            public int getVersionCode() {
                return ((GmsCoreBuild) this.instance).getVersionCode();
            }

            public Builder setVersionCode(int value) {
                copyOnWrite();
                ((GmsCoreBuild) this.instance).setVersionCode(value);
                return this;
            }

            public Builder clearVersionCode() {
                copyOnWrite();
                ((GmsCoreBuild) this.instance).clearVersionCode();
                return this;
            }

            public boolean hasVersionName() {
                return ((GmsCoreBuild) this.instance).hasVersionName();
            }

            public String getVersionName() {
                return ((GmsCoreBuild) this.instance).getVersionName();
            }

            public Builder setVersionName(String value) {
                copyOnWrite();
                ((GmsCoreBuild) this.instance).setVersionName(value);
                return this;
            }

            public ByteString getVersionNameBytes() {
                return ((GmsCoreBuild) this.instance).getVersionNameBytes();
            }

            public Builder setVersionNameBytes(ByteString value) {
                copyOnWrite();
                ((GmsCoreBuild) this.instance).setVersionNameBytes(value);
                return this;
            }

            public Builder clearVersionName() {
                copyOnWrite();
                ((GmsCoreBuild) this.instance).clearVersionName();
                return this;
            }

            public boolean hasVariant() {
                return ((GmsCoreBuild) this.instance).hasVariant();
            }

            public GmsCoreBuildVariant getVariant() {
                return ((GmsCoreBuild) this.instance).getVariant();
            }

            public Builder setVariant(GmsCoreBuildVariant value) {
                copyOnWrite();
                ((GmsCoreBuild) this.instance).setVariant(value);
                return this;
            }

            public Builder setVariant(GmsCoreBuildVariant.Builder builderForValue) {
                copyOnWrite();
                ((GmsCoreBuild) this.instance).setVariant(builderForValue);
                return this;
            }

            public Builder mergeVariant(GmsCoreBuildVariant value) {
                copyOnWrite();
                ((GmsCoreBuild) this.instance).mergeVariant(value);
                return this;
            }

            public Builder clearVariant() {
                copyOnWrite();
                ((GmsCoreBuild) this.instance).clearVariant();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class GmsCoreBuildVariant extends GeneratedMessageLite<GmsCoreBuildVariant, Builder> implements GmsCoreBuildVariantOrBuilder {
        public static final int ARCHITECTURE_FIELD_NUMBER = 2;
        public static final int BUILD_TYPE_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final GmsCoreBuildVariant DEFAULT_INSTANCE = new GmsCoreBuildVariant();
        public static final int SCREEN_DENSITY_FIELD_NUMBER = 3;
        private static volatile Parser<GmsCoreBuildVariant> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(GmsCoreBuildVariant.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int architecture_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int buildType_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int screenDensity_;

        private GmsCoreBuildVariant() {
        }

        public static GmsCoreBuildVariant parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmsCoreBuildVariant parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmsCoreBuildVariant parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmsCoreBuildVariant parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmsCoreBuildVariant parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GmsCoreBuildVariant parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GmsCoreBuildVariant parseFrom(InputStream input) throws IOException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GmsCoreBuildVariant parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GmsCoreBuildVariant parseDelimitedFrom(InputStream input) throws IOException {
            return (GmsCoreBuildVariant) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GmsCoreBuildVariant parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmsCoreBuildVariant) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GmsCoreBuildVariant parseFrom(CodedInputStream input) throws IOException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GmsCoreBuildVariant parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GmsCoreBuildVariant) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GmsCoreBuildVariant prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static GmsCoreBuildVariant getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GmsCoreBuildVariant> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasBuildType() {
            return (this.bitField0_ & 1) != 0;
        }

        public GmsCoreBuildType getBuildType() {
            GmsCoreBuildType result = GmsCoreBuildType.forNumber(this.buildType_);
            return result == null ? GmsCoreBuildType.BUILD_TYPE_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setBuildType(GmsCoreBuildType value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.buildType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearBuildType() {
            this.bitField0_ &= -2;
            this.buildType_ = 0;
        }

        public boolean hasArchitecture() {
            return (this.bitField0_ & 2) != 0;
        }

        public GmsCoreArchitecture getArchitecture() {
            GmsCoreArchitecture result = GmsCoreArchitecture.forNumber(this.architecture_);
            return result == null ? GmsCoreArchitecture.ARCH_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setArchitecture(GmsCoreArchitecture value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.architecture_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearArchitecture() {
            this.bitField0_ &= -3;
            this.architecture_ = 0;
        }

        public boolean hasScreenDensity() {
            return (this.bitField0_ & 4) != 0;
        }

        public GmsCoreScreenDensity getScreenDensity() {
            GmsCoreScreenDensity result = GmsCoreScreenDensity.forNumber(this.screenDensity_);
            return result == null ? GmsCoreScreenDensity.DENSITY_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setScreenDensity(GmsCoreScreenDensity value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.screenDensity_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearScreenDensity() {
            this.bitField0_ &= -5;
            this.screenDensity_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GmsCoreBuildVariant();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\f\u0000\u0002\f\u0001\u0003\f\u0002", new Object[]{"bitField0_", "buildType_", GmsCoreBuildType.internalGetVerifier(), "architecture_", GmsCoreArchitecture.internalGetVerifier(), "screenDensity_", GmsCoreScreenDensity.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GmsCoreBuildVariant> parser = PARSER;
                    if (parser == null) {
                        synchronized (GmsCoreBuildVariant.class) {
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

        public enum GmsCoreBuildType implements Internal.EnumLite {
            BUILD_TYPE_UNKNOWN(0),
            BUILD_TYPE_PROD(1),
            BUILD_TYPE_INTERNAL(2),
            BUILD_TYPE_PRODLMP(3),
            BUILD_TYPE_THINGS(4),
            BUILD_TYPE_PRODMNC(5),
            BUILD_TYPE_WEARABLE(6),
            BUILD_TYPE_AUTO(7),
            BUILD_TYPE_ATV(9),
            BUILD_TYPE_PRODPIX(10),
            BUILD_TYPE_PRODPI(11),
            BUILD_TYPE_PRODGO(12),
            BUILD_TYPE_PRODQT(13);

            public static final int BUILD_TYPE_ATV_VALUE = 9;
            public static final int BUILD_TYPE_AUTO_VALUE = 7;
            public static final int BUILD_TYPE_INTERNAL_VALUE = 2;
            public static final int BUILD_TYPE_PRODGO_VALUE = 12;
            public static final int BUILD_TYPE_PRODLMP_VALUE = 3;
            public static final int BUILD_TYPE_PRODMNC_VALUE = 5;
            public static final int BUILD_TYPE_PRODPIX_VALUE = 10;
            public static final int BUILD_TYPE_PRODPI_VALUE = 11;
            public static final int BUILD_TYPE_PRODQT_VALUE = 13;
            public static final int BUILD_TYPE_PROD_VALUE = 1;
            public static final int BUILD_TYPE_THINGS_VALUE = 4;
            public static final int BUILD_TYPE_UNKNOWN_VALUE = 0;
            public static final int BUILD_TYPE_WEARABLE_VALUE = 6;
            private static final Internal.EnumLiteMap<GmsCoreBuildType> internalValueMap = new Internal.EnumLiteMap<GmsCoreBuildType>() {
                public GmsCoreBuildType findValueByNumber(int number) {
                    return GmsCoreBuildType.forNumber(number);
                }
            };
            private final int value;

            private GmsCoreBuildType(int value2) {
                this.value = value2;
            }

            public static GmsCoreBuildType forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return BUILD_TYPE_UNKNOWN;
                    case 1:
                        return BUILD_TYPE_PROD;
                    case 2:
                        return BUILD_TYPE_INTERNAL;
                    case 3:
                        return BUILD_TYPE_PRODLMP;
                    case 4:
                        return BUILD_TYPE_THINGS;
                    case 5:
                        return BUILD_TYPE_PRODMNC;
                    case 6:
                        return BUILD_TYPE_WEARABLE;
                    case 7:
                        return BUILD_TYPE_AUTO;
                    case 8:
                    default:
                        return null;
                    case 9:
                        return BUILD_TYPE_ATV;
                    case 10:
                        return BUILD_TYPE_PRODPIX;
                    case 11:
                        return BUILD_TYPE_PRODPI;
                    case 12:
                        return BUILD_TYPE_PRODGO;
                    case 13:
                        return BUILD_TYPE_PRODQT;
                }
            }

            public static Internal.EnumLiteMap<GmsCoreBuildType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return GmsCoreBuildTypeVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class GmsCoreBuildTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new GmsCoreBuildTypeVerifier();

                private GmsCoreBuildTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return GmsCoreBuildType.forNumber(number) != null;
                }
            }
        }

        public enum GmsCoreArchitecture implements Internal.EnumLite {
            ARCH_UNKNOWN(0),
            ARCH_NON_NATIVE(1),
            ARCH_ARMV5(2),
            ARCH_ARMV7(4),
            ARCH_ARM64(5),
            ARCH_MIPS(6),
            ARCH_MIPS_64(7),
            ARCH_X86(8),
            ARCH_X86_64(9);

            public static final int ARCH_ARM64_VALUE = 5;
            public static final int ARCH_ARMV5_VALUE = 2;
            public static final int ARCH_ARMV7_VALUE = 4;
            public static final int ARCH_MIPS_64_VALUE = 7;
            public static final int ARCH_MIPS_VALUE = 6;
            public static final int ARCH_NON_NATIVE_VALUE = 1;
            public static final int ARCH_UNKNOWN_VALUE = 0;
            public static final int ARCH_X86_64_VALUE = 9;
            public static final int ARCH_X86_VALUE = 8;
            private static final Internal.EnumLiteMap<GmsCoreArchitecture> internalValueMap = new Internal.EnumLiteMap<GmsCoreArchitecture>() {
                public GmsCoreArchitecture findValueByNumber(int number) {
                    return GmsCoreArchitecture.forNumber(number);
                }
            };
            private final int value;

            private GmsCoreArchitecture(int value2) {
                this.value = value2;
            }

            public static GmsCoreArchitecture forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return ARCH_UNKNOWN;
                    case 1:
                        return ARCH_NON_NATIVE;
                    case 2:
                        return ARCH_ARMV5;
                    case 3:
                    default:
                        return null;
                    case 4:
                        return ARCH_ARMV7;
                    case 5:
                        return ARCH_ARM64;
                    case 6:
                        return ARCH_MIPS;
                    case 7:
                        return ARCH_MIPS_64;
                    case 8:
                        return ARCH_X86;
                    case 9:
                        return ARCH_X86_64;
                }
            }

            public static Internal.EnumLiteMap<GmsCoreArchitecture> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return GmsCoreArchitectureVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class GmsCoreArchitectureVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new GmsCoreArchitectureVerifier();

                private GmsCoreArchitectureVerifier() {
                }

                public boolean isInRange(int number) {
                    return GmsCoreArchitecture.forNumber(number) != null;
                }
            }
        }

        public enum GmsCoreScreenDensity implements Internal.EnumLite {
            DENSITY_UNKNOWN(0),
            DENSITY_ALLDPI(1),
            DENSITY_LDPI(2),
            DENSITY_MDPI(3),
            DENSITY_TVDPI(4),
            DENSITY_HDPI(5),
            DENSITY_XHDPI(7),
            DENSITY_DPI400(8),
            DENSITY_XXHDPI(9),
            DENSITY_XXXHDPI(10);

            public static final int DENSITY_ALLDPI_VALUE = 1;
            public static final int DENSITY_DPI400_VALUE = 8;
            public static final int DENSITY_HDPI_VALUE = 5;
            public static final int DENSITY_LDPI_VALUE = 2;
            public static final int DENSITY_MDPI_VALUE = 3;
            public static final int DENSITY_TVDPI_VALUE = 4;
            public static final int DENSITY_UNKNOWN_VALUE = 0;
            public static final int DENSITY_XHDPI_VALUE = 7;
            public static final int DENSITY_XXHDPI_VALUE = 9;
            public static final int DENSITY_XXXHDPI_VALUE = 10;
            private static final Internal.EnumLiteMap<GmsCoreScreenDensity> internalValueMap = new Internal.EnumLiteMap<GmsCoreScreenDensity>() {
                public GmsCoreScreenDensity findValueByNumber(int number) {
                    return GmsCoreScreenDensity.forNumber(number);
                }
            };
            private final int value;

            private GmsCoreScreenDensity(int value2) {
                this.value = value2;
            }

            public static GmsCoreScreenDensity forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return DENSITY_UNKNOWN;
                    case 1:
                        return DENSITY_ALLDPI;
                    case 2:
                        return DENSITY_LDPI;
                    case 3:
                        return DENSITY_MDPI;
                    case 4:
                        return DENSITY_TVDPI;
                    case 5:
                        return DENSITY_HDPI;
                    case 6:
                    default:
                        return null;
                    case 7:
                        return DENSITY_XHDPI;
                    case 8:
                        return DENSITY_DPI400;
                    case 9:
                        return DENSITY_XXHDPI;
                    case 10:
                        return DENSITY_XXXHDPI;
                }
            }

            public static Internal.EnumLiteMap<GmsCoreScreenDensity> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return GmsCoreScreenDensityVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class GmsCoreScreenDensityVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new GmsCoreScreenDensityVerifier();

                private GmsCoreScreenDensityVerifier() {
                }

                public boolean isInRange(int number) {
                    return GmsCoreScreenDensity.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<GmsCoreBuildVariant, Builder> implements GmsCoreBuildVariantOrBuilder {
            private Builder() {
                super(GmsCoreBuildVariant.DEFAULT_INSTANCE);
            }

            public boolean hasBuildType() {
                return ((GmsCoreBuildVariant) this.instance).hasBuildType();
            }

            public GmsCoreBuildType getBuildType() {
                return ((GmsCoreBuildVariant) this.instance).getBuildType();
            }

            public Builder setBuildType(GmsCoreBuildType value) {
                copyOnWrite();
                ((GmsCoreBuildVariant) this.instance).setBuildType(value);
                return this;
            }

            public Builder clearBuildType() {
                copyOnWrite();
                ((GmsCoreBuildVariant) this.instance).clearBuildType();
                return this;
            }

            public boolean hasArchitecture() {
                return ((GmsCoreBuildVariant) this.instance).hasArchitecture();
            }

            public GmsCoreArchitecture getArchitecture() {
                return ((GmsCoreBuildVariant) this.instance).getArchitecture();
            }

            public Builder setArchitecture(GmsCoreArchitecture value) {
                copyOnWrite();
                ((GmsCoreBuildVariant) this.instance).setArchitecture(value);
                return this;
            }

            public Builder clearArchitecture() {
                copyOnWrite();
                ((GmsCoreBuildVariant) this.instance).clearArchitecture();
                return this;
            }

            public boolean hasScreenDensity() {
                return ((GmsCoreBuildVariant) this.instance).hasScreenDensity();
            }

            public GmsCoreScreenDensity getScreenDensity() {
                return ((GmsCoreBuildVariant) this.instance).getScreenDensity();
            }

            public Builder setScreenDensity(GmsCoreScreenDensity value) {
                copyOnWrite();
                ((GmsCoreBuildVariant) this.instance).setScreenDensity(value);
                return this;
            }

            public Builder clearScreenDensity() {
                copyOnWrite();
                ((GmsCoreBuildVariant) this.instance).clearScreenDensity();
                return this;
            }
        }
    }
}
