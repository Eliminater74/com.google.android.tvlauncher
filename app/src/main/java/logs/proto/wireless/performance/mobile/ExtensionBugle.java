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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ExtensionBugle {

    private ExtensionBugle() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface BugleExtensionOrBuilder extends MessageLiteOrBuilder {
        boolean getIsRcsEnabled();

        BugleMobileCode getMobileCode();

        boolean hasIsRcsEnabled();

        boolean hasMobileCode();
    }

    public interface BugleMobileCodeOrBuilder extends MessageLiteOrBuilder {
        @Deprecated
        int getMcc();

        String getMccString();

        ByteString getMccStringBytes();

        @Deprecated
        int getMnc();

        String getMncString();

        ByteString getMncStringBytes();

        BugleMobileCode.Mvno getMvno();

        String getNetworkOperatorName();

        ByteString getNetworkOperatorNameBytes();

        String getSimOperatorName();

        ByteString getSimOperatorNameBytes();

        @Deprecated
        boolean hasMcc();

        boolean hasMccString();

        @Deprecated
        boolean hasMnc();

        boolean hasMncString();

        boolean hasMvno();

        boolean hasNetworkOperatorName();

        boolean hasSimOperatorName();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class BugleExtension extends GeneratedMessageLite<BugleExtension, Builder> implements BugleExtensionOrBuilder {
        /* access modifiers changed from: private */
        public static final BugleExtension DEFAULT_INSTANCE = new BugleExtension();
        public static final int IS_RCS_ENABLED_FIELD_NUMBER = 1;
        public static final int MOBILE_CODE_FIELD_NUMBER = 2;
        private static volatile Parser<BugleExtension> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(BugleExtension.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean isRcsEnabled_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private BugleMobileCode mobileCode_;

        private BugleExtension() {
        }

        public static BugleExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BugleExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BugleExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BugleExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BugleExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BugleExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BugleExtension parseFrom(InputStream input) throws IOException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BugleExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BugleExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (BugleExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static BugleExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BugleExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BugleExtension parseFrom(CodedInputStream input) throws IOException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BugleExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BugleExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(BugleExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static BugleExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BugleExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasIsRcsEnabled() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getIsRcsEnabled() {
            return this.isRcsEnabled_;
        }

        /* access modifiers changed from: private */
        public void setIsRcsEnabled(boolean value) {
            this.bitField0_ |= 1;
            this.isRcsEnabled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsRcsEnabled() {
            this.bitField0_ &= -2;
            this.isRcsEnabled_ = false;
        }

        public boolean hasMobileCode() {
            return (this.bitField0_ & 2) != 0;
        }

        public BugleMobileCode getMobileCode() {
            BugleMobileCode bugleMobileCode = this.mobileCode_;
            return bugleMobileCode == null ? BugleMobileCode.getDefaultInstance() : bugleMobileCode;
        }

        /* access modifiers changed from: private */
        public void setMobileCode(BugleMobileCode value) {
            if (value != null) {
                this.mobileCode_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMobileCode(BugleMobileCode.Builder builderForValue) {
            this.mobileCode_ = (BugleMobileCode) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeMobileCode(BugleMobileCode value) {
            if (value != null) {
                BugleMobileCode bugleMobileCode = this.mobileCode_;
                if (bugleMobileCode == null || bugleMobileCode == BugleMobileCode.getDefaultInstance()) {
                    this.mobileCode_ = value;
                } else {
                    this.mobileCode_ = (BugleMobileCode) ((BugleMobileCode.Builder) BugleMobileCode.newBuilder(this.mobileCode_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMobileCode() {
            this.mobileCode_ = null;
            this.bitField0_ &= -3;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new BugleExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0007\u0000\u0002\t\u0001", new Object[]{"bitField0_", "isRcsEnabled_", "mobileCode_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<BugleExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (BugleExtension.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<BugleExtension, Builder> implements BugleExtensionOrBuilder {
            private Builder() {
                super(BugleExtension.DEFAULT_INSTANCE);
            }

            public boolean hasIsRcsEnabled() {
                return ((BugleExtension) this.instance).hasIsRcsEnabled();
            }

            public boolean getIsRcsEnabled() {
                return ((BugleExtension) this.instance).getIsRcsEnabled();
            }

            public Builder setIsRcsEnabled(boolean value) {
                copyOnWrite();
                ((BugleExtension) this.instance).setIsRcsEnabled(value);
                return this;
            }

            public Builder clearIsRcsEnabled() {
                copyOnWrite();
                ((BugleExtension) this.instance).clearIsRcsEnabled();
                return this;
            }

            public boolean hasMobileCode() {
                return ((BugleExtension) this.instance).hasMobileCode();
            }

            public BugleMobileCode getMobileCode() {
                return ((BugleExtension) this.instance).getMobileCode();
            }

            public Builder setMobileCode(BugleMobileCode value) {
                copyOnWrite();
                ((BugleExtension) this.instance).setMobileCode(value);
                return this;
            }

            public Builder setMobileCode(BugleMobileCode.Builder builderForValue) {
                copyOnWrite();
                ((BugleExtension) this.instance).setMobileCode(builderForValue);
                return this;
            }

            public Builder mergeMobileCode(BugleMobileCode value) {
                copyOnWrite();
                ((BugleExtension) this.instance).mergeMobileCode(value);
                return this;
            }

            public Builder clearMobileCode() {
                copyOnWrite();
                ((BugleExtension) this.instance).clearMobileCode();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class BugleMobileCode extends GeneratedMessageLite<BugleMobileCode, Builder> implements BugleMobileCodeOrBuilder {
        /* access modifiers changed from: private */
        public static final BugleMobileCode DEFAULT_INSTANCE = new BugleMobileCode();
        public static final int MCC_FIELD_NUMBER = 1;
        public static final int MCC_STRING_FIELD_NUMBER = 6;
        public static final int MNC_FIELD_NUMBER = 2;
        public static final int MNC_STRING_FIELD_NUMBER = 7;
        public static final int MVNO_FIELD_NUMBER = 5;
        public static final int NETWORK_OPERATOR_NAME_FIELD_NUMBER = 4;
        public static final int SIM_OPERATOR_NAME_FIELD_NUMBER = 3;
        private static volatile Parser<BugleMobileCode> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(BugleMobileCode.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 6, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private String mccString_ = "";
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int mcc_;
        @ProtoField(fieldNumber = 7, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private String mncString_ = "";
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int mnc_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int mvno_;
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private String networkOperatorName_ = "";
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String simOperatorName_ = "";

        private BugleMobileCode() {
        }

        public static BugleMobileCode parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BugleMobileCode parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BugleMobileCode parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BugleMobileCode parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BugleMobileCode parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BugleMobileCode parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BugleMobileCode parseFrom(InputStream input) throws IOException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BugleMobileCode parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BugleMobileCode parseDelimitedFrom(InputStream input) throws IOException {
            return (BugleMobileCode) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static BugleMobileCode parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BugleMobileCode) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BugleMobileCode parseFrom(CodedInputStream input) throws IOException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BugleMobileCode parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BugleMobileCode) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(BugleMobileCode prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static BugleMobileCode getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BugleMobileCode> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        @Deprecated
        public boolean hasMcc() {
            return (this.bitField0_ & 1) != 0;
        }

        @Deprecated
        public int getMcc() {
            return this.mcc_;
        }

        /* access modifiers changed from: private */
        public void setMcc(int value) {
            this.bitField0_ |= 1;
            this.mcc_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMcc() {
            this.bitField0_ &= -2;
            this.mcc_ = 0;
        }

        @Deprecated
        public boolean hasMnc() {
            return (this.bitField0_ & 2) != 0;
        }

        @Deprecated
        public int getMnc() {
            return this.mnc_;
        }

        /* access modifiers changed from: private */
        public void setMnc(int value) {
            this.bitField0_ |= 2;
            this.mnc_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMnc() {
            this.bitField0_ &= -3;
            this.mnc_ = 0;
        }

        public boolean hasSimOperatorName() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getSimOperatorName() {
            return this.simOperatorName_;
        }

        /* access modifiers changed from: private */
        public void setSimOperatorName(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.simOperatorName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getSimOperatorNameBytes() {
            return ByteString.copyFromUtf8(this.simOperatorName_);
        }

        /* access modifiers changed from: private */
        public void setSimOperatorNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.simOperatorName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSimOperatorName() {
            this.bitField0_ &= -5;
            this.simOperatorName_ = getDefaultInstance().getSimOperatorName();
        }

        public boolean hasNetworkOperatorName() {
            return (this.bitField0_ & 8) != 0;
        }

        public String getNetworkOperatorName() {
            return this.networkOperatorName_;
        }

        /* access modifiers changed from: private */
        public void setNetworkOperatorName(String value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.networkOperatorName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getNetworkOperatorNameBytes() {
            return ByteString.copyFromUtf8(this.networkOperatorName_);
        }

        /* access modifiers changed from: private */
        public void setNetworkOperatorNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.networkOperatorName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearNetworkOperatorName() {
            this.bitField0_ &= -9;
            this.networkOperatorName_ = getDefaultInstance().getNetworkOperatorName();
        }

        public boolean hasMvno() {
            return (this.bitField0_ & 16) != 0;
        }

        public Mvno getMvno() {
            Mvno result = Mvno.forNumber(this.mvno_);
            return result == null ? Mvno.UNKNOWN_MVNO : result;
        }

        /* access modifiers changed from: private */
        public void setMvno(Mvno value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.mvno_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMvno() {
            this.bitField0_ &= -17;
            this.mvno_ = 0;
        }

        public boolean hasMccString() {
            return (this.bitField0_ & 32) != 0;
        }

        public String getMccString() {
            return this.mccString_;
        }

        /* access modifiers changed from: private */
        public void setMccString(String value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.mccString_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getMccStringBytes() {
            return ByteString.copyFromUtf8(this.mccString_);
        }

        /* access modifiers changed from: private */
        public void setMccStringBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.mccString_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMccString() {
            this.bitField0_ &= -33;
            this.mccString_ = getDefaultInstance().getMccString();
        }

        public boolean hasMncString() {
            return (this.bitField0_ & 64) != 0;
        }

        public String getMncString() {
            return this.mncString_;
        }

        /* access modifiers changed from: private */
        public void setMncString(String value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.mncString_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getMncStringBytes() {
            return ByteString.copyFromUtf8(this.mncString_);
        }

        /* access modifiers changed from: private */
        public void setMncStringBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.mncString_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMncString() {
            this.bitField0_ &= -65;
            this.mncString_ = getDefaultInstance().getMncString();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new BugleMobileCode();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\b\u0002\u0004\b\u0003\u0005\f\u0004\u0006\b\u0005\u0007\b\u0006", new Object[]{"bitField0_", "mcc_", "mnc_", "simOperatorName_", "networkOperatorName_", "mvno_", Mvno.internalGetVerifier(), "mccString_", "mncString_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<BugleMobileCode> parser = PARSER;
                    if (parser == null) {
                        synchronized (BugleMobileCode.class) {
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

        public enum Mvno implements Internal.EnumLite {
            UNKNOWN_MVNO(0),
            PROJECT_FI(1);

            public static final int PROJECT_FI_VALUE = 1;
            public static final int UNKNOWN_MVNO_VALUE = 0;
            private static final Internal.EnumLiteMap<Mvno> internalValueMap = new Internal.EnumLiteMap<Mvno>() {
                public Mvno findValueByNumber(int number) {
                    return Mvno.forNumber(number);
                }
            };
            private final int value;

            private Mvno(int value2) {
                this.value = value2;
            }

            public static Mvno forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN_MVNO;
                }
                if (value2 != 1) {
                    return null;
                }
                return PROJECT_FI;
            }

            public static Internal.EnumLiteMap<Mvno> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return MvnoVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class MvnoVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new MvnoVerifier();

                private MvnoVerifier() {
                }

                public boolean isInRange(int number) {
                    return Mvno.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<BugleMobileCode, Builder> implements BugleMobileCodeOrBuilder {
            private Builder() {
                super(BugleMobileCode.DEFAULT_INSTANCE);
            }

            @Deprecated
            public boolean hasMcc() {
                return ((BugleMobileCode) this.instance).hasMcc();
            }

            @Deprecated
            public int getMcc() {
                return ((BugleMobileCode) this.instance).getMcc();
            }

            @Deprecated
            public Builder setMcc(int value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setMcc(value);
                return this;
            }

            @Deprecated
            public Builder clearMcc() {
                copyOnWrite();
                ((BugleMobileCode) this.instance).clearMcc();
                return this;
            }

            @Deprecated
            public boolean hasMnc() {
                return ((BugleMobileCode) this.instance).hasMnc();
            }

            @Deprecated
            public int getMnc() {
                return ((BugleMobileCode) this.instance).getMnc();
            }

            @Deprecated
            public Builder setMnc(int value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setMnc(value);
                return this;
            }

            @Deprecated
            public Builder clearMnc() {
                copyOnWrite();
                ((BugleMobileCode) this.instance).clearMnc();
                return this;
            }

            public boolean hasSimOperatorName() {
                return ((BugleMobileCode) this.instance).hasSimOperatorName();
            }

            public String getSimOperatorName() {
                return ((BugleMobileCode) this.instance).getSimOperatorName();
            }

            public Builder setSimOperatorName(String value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setSimOperatorName(value);
                return this;
            }

            public ByteString getSimOperatorNameBytes() {
                return ((BugleMobileCode) this.instance).getSimOperatorNameBytes();
            }

            public Builder setSimOperatorNameBytes(ByteString value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setSimOperatorNameBytes(value);
                return this;
            }

            public Builder clearSimOperatorName() {
                copyOnWrite();
                ((BugleMobileCode) this.instance).clearSimOperatorName();
                return this;
            }

            public boolean hasNetworkOperatorName() {
                return ((BugleMobileCode) this.instance).hasNetworkOperatorName();
            }

            public String getNetworkOperatorName() {
                return ((BugleMobileCode) this.instance).getNetworkOperatorName();
            }

            public Builder setNetworkOperatorName(String value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setNetworkOperatorName(value);
                return this;
            }

            public ByteString getNetworkOperatorNameBytes() {
                return ((BugleMobileCode) this.instance).getNetworkOperatorNameBytes();
            }

            public Builder setNetworkOperatorNameBytes(ByteString value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setNetworkOperatorNameBytes(value);
                return this;
            }

            public Builder clearNetworkOperatorName() {
                copyOnWrite();
                ((BugleMobileCode) this.instance).clearNetworkOperatorName();
                return this;
            }

            public boolean hasMvno() {
                return ((BugleMobileCode) this.instance).hasMvno();
            }

            public Mvno getMvno() {
                return ((BugleMobileCode) this.instance).getMvno();
            }

            public Builder setMvno(Mvno value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setMvno(value);
                return this;
            }

            public Builder clearMvno() {
                copyOnWrite();
                ((BugleMobileCode) this.instance).clearMvno();
                return this;
            }

            public boolean hasMccString() {
                return ((BugleMobileCode) this.instance).hasMccString();
            }

            public String getMccString() {
                return ((BugleMobileCode) this.instance).getMccString();
            }

            public Builder setMccString(String value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setMccString(value);
                return this;
            }

            public ByteString getMccStringBytes() {
                return ((BugleMobileCode) this.instance).getMccStringBytes();
            }

            public Builder setMccStringBytes(ByteString value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setMccStringBytes(value);
                return this;
            }

            public Builder clearMccString() {
                copyOnWrite();
                ((BugleMobileCode) this.instance).clearMccString();
                return this;
            }

            public boolean hasMncString() {
                return ((BugleMobileCode) this.instance).hasMncString();
            }

            public String getMncString() {
                return ((BugleMobileCode) this.instance).getMncString();
            }

            public Builder setMncString(String value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setMncString(value);
                return this;
            }

            public ByteString getMncStringBytes() {
                return ((BugleMobileCode) this.instance).getMncStringBytes();
            }

            public Builder setMncStringBytes(ByteString value) {
                copyOnWrite();
                ((BugleMobileCode) this.instance).setMncStringBytes(value);
                return this;
            }

            public Builder clearMncString() {
                copyOnWrite();
                ((BugleMobileCode) this.instance).clearMncString();
                return this;
            }
        }
    }
}
