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
import logs.proto.wireless.performance.mobile.ExtensionBugle;

public final class ExtensionCsapk {

    public interface CsapkExtensionOrBuilder extends MessageLiteOrBuilder {
        boolean getIsRcsEnabled();

        String getMcc();

        ByteString getMccBytes();

        String getMnc();

        ByteString getMncBytes();

        ExtensionBugle.BugleMobileCode.Mvno getMvno();

        String getNetworkOperatorName();

        ByteString getNetworkOperatorNameBytes();

        String getSimOperatorName();

        ByteString getSimOperatorNameBytes();

        boolean hasIsRcsEnabled();

        boolean hasMcc();

        boolean hasMnc();

        boolean hasMvno();

        boolean hasNetworkOperatorName();

        boolean hasSimOperatorName();
    }

    private ExtensionCsapk() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CsapkExtension extends GeneratedMessageLite<CsapkExtension, Builder> implements CsapkExtensionOrBuilder {
        /* access modifiers changed from: private */
        public static final CsapkExtension DEFAULT_INSTANCE = new CsapkExtension();
        public static final int IS_RCS_ENABLED_FIELD_NUMBER = 1;
        public static final int MCC_FIELD_NUMBER = 2;
        public static final int MNC_FIELD_NUMBER = 3;
        public static final int MVNO_FIELD_NUMBER = 6;
        public static final int NETWORK_OPERATOR_NAME_FIELD_NUMBER = 5;
        private static volatile Parser<CsapkExtension> PARSER = null;
        public static final int SIM_OPERATOR_NAME_FIELD_NUMBER = 4;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean isRcsEnabled_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String mcc_ = "";
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String mnc_ = "";
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int mvno_;
        @ProtoField(fieldNumber = 5, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private String networkOperatorName_ = "";
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private String simOperatorName_ = "";

        private CsapkExtension() {
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

        public boolean hasMcc() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getMcc() {
            return this.mcc_;
        }

        public ByteString getMccBytes() {
            return ByteString.copyFromUtf8(this.mcc_);
        }

        /* access modifiers changed from: private */
        public void setMcc(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.mcc_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMcc() {
            this.bitField0_ &= -3;
            this.mcc_ = getDefaultInstance().getMcc();
        }

        /* access modifiers changed from: private */
        public void setMccBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.mcc_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasMnc() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getMnc() {
            return this.mnc_;
        }

        public ByteString getMncBytes() {
            return ByteString.copyFromUtf8(this.mnc_);
        }

        /* access modifiers changed from: private */
        public void setMnc(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.mnc_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMnc() {
            this.bitField0_ &= -5;
            this.mnc_ = getDefaultInstance().getMnc();
        }

        /* access modifiers changed from: private */
        public void setMncBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.mnc_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasSimOperatorName() {
            return (this.bitField0_ & 8) != 0;
        }

        public String getSimOperatorName() {
            return this.simOperatorName_;
        }

        public ByteString getSimOperatorNameBytes() {
            return ByteString.copyFromUtf8(this.simOperatorName_);
        }

        /* access modifiers changed from: private */
        public void setSimOperatorName(String value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.simOperatorName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSimOperatorName() {
            this.bitField0_ &= -9;
            this.simOperatorName_ = getDefaultInstance().getSimOperatorName();
        }

        /* access modifiers changed from: private */
        public void setSimOperatorNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.simOperatorName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasNetworkOperatorName() {
            return (this.bitField0_ & 16) != 0;
        }

        public String getNetworkOperatorName() {
            return this.networkOperatorName_;
        }

        public ByteString getNetworkOperatorNameBytes() {
            return ByteString.copyFromUtf8(this.networkOperatorName_);
        }

        /* access modifiers changed from: private */
        public void setNetworkOperatorName(String value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.networkOperatorName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearNetworkOperatorName() {
            this.bitField0_ &= -17;
            this.networkOperatorName_ = getDefaultInstance().getNetworkOperatorName();
        }

        /* access modifiers changed from: private */
        public void setNetworkOperatorNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.networkOperatorName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasMvno() {
            return (this.bitField0_ & 32) != 0;
        }

        public ExtensionBugle.BugleMobileCode.Mvno getMvno() {
            ExtensionBugle.BugleMobileCode.Mvno result = ExtensionBugle.BugleMobileCode.Mvno.forNumber(this.mvno_);
            return result == null ? ExtensionBugle.BugleMobileCode.Mvno.UNKNOWN_MVNO : result;
        }

        /* access modifiers changed from: private */
        public void setMvno(ExtensionBugle.BugleMobileCode.Mvno value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.mvno_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMvno() {
            this.bitField0_ &= -33;
            this.mvno_ = 0;
        }

        public static CsapkExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CsapkExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CsapkExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CsapkExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CsapkExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CsapkExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CsapkExtension parseFrom(InputStream input) throws IOException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CsapkExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CsapkExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (CsapkExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CsapkExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CsapkExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CsapkExtension parseFrom(CodedInputStream input) throws IOException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CsapkExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CsapkExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CsapkExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<CsapkExtension, Builder> implements CsapkExtensionOrBuilder {
            private Builder() {
                super(CsapkExtension.DEFAULT_INSTANCE);
            }

            public boolean hasIsRcsEnabled() {
                return ((CsapkExtension) this.instance).hasIsRcsEnabled();
            }

            public boolean getIsRcsEnabled() {
                return ((CsapkExtension) this.instance).getIsRcsEnabled();
            }

            public Builder setIsRcsEnabled(boolean value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setIsRcsEnabled(value);
                return this;
            }

            public Builder clearIsRcsEnabled() {
                copyOnWrite();
                ((CsapkExtension) this.instance).clearIsRcsEnabled();
                return this;
            }

            public boolean hasMcc() {
                return ((CsapkExtension) this.instance).hasMcc();
            }

            public String getMcc() {
                return ((CsapkExtension) this.instance).getMcc();
            }

            public ByteString getMccBytes() {
                return ((CsapkExtension) this.instance).getMccBytes();
            }

            public Builder setMcc(String value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setMcc(value);
                return this;
            }

            public Builder clearMcc() {
                copyOnWrite();
                ((CsapkExtension) this.instance).clearMcc();
                return this;
            }

            public Builder setMccBytes(ByteString value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setMccBytes(value);
                return this;
            }

            public boolean hasMnc() {
                return ((CsapkExtension) this.instance).hasMnc();
            }

            public String getMnc() {
                return ((CsapkExtension) this.instance).getMnc();
            }

            public ByteString getMncBytes() {
                return ((CsapkExtension) this.instance).getMncBytes();
            }

            public Builder setMnc(String value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setMnc(value);
                return this;
            }

            public Builder clearMnc() {
                copyOnWrite();
                ((CsapkExtension) this.instance).clearMnc();
                return this;
            }

            public Builder setMncBytes(ByteString value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setMncBytes(value);
                return this;
            }

            public boolean hasSimOperatorName() {
                return ((CsapkExtension) this.instance).hasSimOperatorName();
            }

            public String getSimOperatorName() {
                return ((CsapkExtension) this.instance).getSimOperatorName();
            }

            public ByteString getSimOperatorNameBytes() {
                return ((CsapkExtension) this.instance).getSimOperatorNameBytes();
            }

            public Builder setSimOperatorName(String value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setSimOperatorName(value);
                return this;
            }

            public Builder clearSimOperatorName() {
                copyOnWrite();
                ((CsapkExtension) this.instance).clearSimOperatorName();
                return this;
            }

            public Builder setSimOperatorNameBytes(ByteString value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setSimOperatorNameBytes(value);
                return this;
            }

            public boolean hasNetworkOperatorName() {
                return ((CsapkExtension) this.instance).hasNetworkOperatorName();
            }

            public String getNetworkOperatorName() {
                return ((CsapkExtension) this.instance).getNetworkOperatorName();
            }

            public ByteString getNetworkOperatorNameBytes() {
                return ((CsapkExtension) this.instance).getNetworkOperatorNameBytes();
            }

            public Builder setNetworkOperatorName(String value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setNetworkOperatorName(value);
                return this;
            }

            public Builder clearNetworkOperatorName() {
                copyOnWrite();
                ((CsapkExtension) this.instance).clearNetworkOperatorName();
                return this;
            }

            public Builder setNetworkOperatorNameBytes(ByteString value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setNetworkOperatorNameBytes(value);
                return this;
            }

            public boolean hasMvno() {
                return ((CsapkExtension) this.instance).hasMvno();
            }

            public ExtensionBugle.BugleMobileCode.Mvno getMvno() {
                return ((CsapkExtension) this.instance).getMvno();
            }

            public Builder setMvno(ExtensionBugle.BugleMobileCode.Mvno value) {
                copyOnWrite();
                ((CsapkExtension) this.instance).setMvno(value);
                return this;
            }

            public Builder clearMvno() {
                copyOnWrite();
                ((CsapkExtension) this.instance).clearMvno();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CsapkExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\u0007\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\b\u0004\u0006\f\u0005", new Object[]{"bitField0_", "isRcsEnabled_", "mcc_", "mnc_", "simOperatorName_", "networkOperatorName_", "mvno_", ExtensionBugle.BugleMobileCode.Mvno.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CsapkExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (CsapkExtension.class) {
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
            GeneratedMessageLite.registerDefaultInstance(CsapkExtension.class, DEFAULT_INSTANCE);
        }

        public static CsapkExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CsapkExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
