package com.google.android.gms.framework.logging.proto;

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

public final class GcoreDimensions {

    public interface DeviceInfoOrBuilder extends MessageLiteOrBuilder {
        int getDeviceState();

        boolean hasDeviceState();
    }

    public interface EntryPointInfoOrBuilder extends MessageLiteOrBuilder {
        int getIntentHash();

        String getName();

        ByteString getNameBytes();

        EntryPointInfo.EntryPointType getType();

        boolean hasIntentHash();

        boolean hasName();

        boolean hasType();
    }

    public interface GCoreClientInfoOrBuilder extends MessageLiteOrBuilder {
        String getCallingPackage();

        ByteString getCallingPackageBytes();

        GCoreClientInfo.ClientType getClientType();

        int getSdkVersion();

        int getVersionCode();

        String getVersionName();

        ByteString getVersionNameBytes();

        boolean hasCallingPackage();

        boolean hasClientType();

        boolean hasSdkVersion();

        boolean hasVersionCode();

        boolean hasVersionName();
    }

    public interface GCoreDimensionsOrBuilder extends MessageLiteOrBuilder {
        GCoreClientInfo getClientInfo();

        DeviceInfo getDeviceInfo();

        EntryPointInfo getEntryPointInfo();

        GCoreModuleInfo getModuleInfo();

        boolean hasClientInfo();

        boolean hasDeviceInfo();

        boolean hasEntryPointInfo();

        boolean hasModuleInfo();
    }

    public interface GCoreModuleInfoOrBuilder extends MessageLiteOrBuilder {
        String getModuleId();

        ByteString getModuleIdBytes();

        int getModuleVersion();

        boolean hasModuleId();

        boolean hasModuleVersion();
    }

    private GcoreDimensions() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class GCoreDimensions extends GeneratedMessageLite<GCoreDimensions, Builder> implements GCoreDimensionsOrBuilder {
        public static final int CLIENT_INFO_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final GCoreDimensions DEFAULT_INSTANCE = new GCoreDimensions();
        public static final int DEVICE_INFO_FIELD_NUMBER = 4;
        public static final int ENTRY_POINT_INFO_FIELD_NUMBER = 2;
        public static final int MODULE_INFO_FIELD_NUMBER = 3;
        private static volatile Parser<GCoreDimensions> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private GCoreClientInfo clientInfo_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private DeviceInfo deviceInfo_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private EntryPointInfo entryPointInfo_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private GCoreModuleInfo moduleInfo_;

        private GCoreDimensions() {
        }

        public boolean hasClientInfo() {
            return (this.bitField0_ & 1) != 0;
        }

        public GCoreClientInfo getClientInfo() {
            GCoreClientInfo gCoreClientInfo = this.clientInfo_;
            return gCoreClientInfo == null ? GCoreClientInfo.getDefaultInstance() : gCoreClientInfo;
        }

        /* access modifiers changed from: private */
        public void setClientInfo(GCoreClientInfo value) {
            if (value != null) {
                this.clientInfo_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setClientInfo(GCoreClientInfo.Builder builderForValue) {
            this.clientInfo_ = (GCoreClientInfo) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeClientInfo(GCoreClientInfo value) {
            if (value != null) {
                GCoreClientInfo gCoreClientInfo = this.clientInfo_;
                if (gCoreClientInfo == null || gCoreClientInfo == GCoreClientInfo.getDefaultInstance()) {
                    this.clientInfo_ = value;
                } else {
                    this.clientInfo_ = (GCoreClientInfo) ((GCoreClientInfo.Builder) GCoreClientInfo.newBuilder(this.clientInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearClientInfo() {
            this.clientInfo_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasEntryPointInfo() {
            return (this.bitField0_ & 2) != 0;
        }

        public EntryPointInfo getEntryPointInfo() {
            EntryPointInfo entryPointInfo = this.entryPointInfo_;
            return entryPointInfo == null ? EntryPointInfo.getDefaultInstance() : entryPointInfo;
        }

        /* access modifiers changed from: private */
        public void setEntryPointInfo(EntryPointInfo value) {
            if (value != null) {
                this.entryPointInfo_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setEntryPointInfo(EntryPointInfo.Builder builderForValue) {
            this.entryPointInfo_ = (EntryPointInfo) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeEntryPointInfo(EntryPointInfo value) {
            if (value != null) {
                EntryPointInfo entryPointInfo = this.entryPointInfo_;
                if (entryPointInfo == null || entryPointInfo == EntryPointInfo.getDefaultInstance()) {
                    this.entryPointInfo_ = value;
                } else {
                    this.entryPointInfo_ = (EntryPointInfo) ((EntryPointInfo.Builder) EntryPointInfo.newBuilder(this.entryPointInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearEntryPointInfo() {
            this.entryPointInfo_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasModuleInfo() {
            return (this.bitField0_ & 4) != 0;
        }

        public GCoreModuleInfo getModuleInfo() {
            GCoreModuleInfo gCoreModuleInfo = this.moduleInfo_;
            return gCoreModuleInfo == null ? GCoreModuleInfo.getDefaultInstance() : gCoreModuleInfo;
        }

        /* access modifiers changed from: private */
        public void setModuleInfo(GCoreModuleInfo value) {
            if (value != null) {
                this.moduleInfo_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setModuleInfo(GCoreModuleInfo.Builder builderForValue) {
            this.moduleInfo_ = (GCoreModuleInfo) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeModuleInfo(GCoreModuleInfo value) {
            if (value != null) {
                GCoreModuleInfo gCoreModuleInfo = this.moduleInfo_;
                if (gCoreModuleInfo == null || gCoreModuleInfo == GCoreModuleInfo.getDefaultInstance()) {
                    this.moduleInfo_ = value;
                } else {
                    this.moduleInfo_ = (GCoreModuleInfo) ((GCoreModuleInfo.Builder) GCoreModuleInfo.newBuilder(this.moduleInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearModuleInfo() {
            this.moduleInfo_ = null;
            this.bitField0_ &= -5;
        }

        public boolean hasDeviceInfo() {
            return (this.bitField0_ & 8) != 0;
        }

        public DeviceInfo getDeviceInfo() {
            DeviceInfo deviceInfo = this.deviceInfo_;
            return deviceInfo == null ? DeviceInfo.getDefaultInstance() : deviceInfo;
        }

        /* access modifiers changed from: private */
        public void setDeviceInfo(DeviceInfo value) {
            if (value != null) {
                this.deviceInfo_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setDeviceInfo(DeviceInfo.Builder builderForValue) {
            this.deviceInfo_ = (DeviceInfo) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeDeviceInfo(DeviceInfo value) {
            if (value != null) {
                DeviceInfo deviceInfo = this.deviceInfo_;
                if (deviceInfo == null || deviceInfo == DeviceInfo.getDefaultInstance()) {
                    this.deviceInfo_ = value;
                } else {
                    this.deviceInfo_ = (DeviceInfo) ((DeviceInfo.Builder) DeviceInfo.newBuilder(this.deviceInfo_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDeviceInfo() {
            this.deviceInfo_ = null;
            this.bitField0_ &= -9;
        }

        public static GCoreDimensions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GCoreDimensions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GCoreDimensions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GCoreDimensions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GCoreDimensions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GCoreDimensions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GCoreDimensions parseFrom(InputStream input) throws IOException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GCoreDimensions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GCoreDimensions parseDelimitedFrom(InputStream input) throws IOException {
            return (GCoreDimensions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GCoreDimensions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GCoreDimensions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GCoreDimensions parseFrom(CodedInputStream input) throws IOException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GCoreDimensions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GCoreDimensions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GCoreDimensions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<GCoreDimensions, Builder> implements GCoreDimensionsOrBuilder {
            private Builder() {
                super(GCoreDimensions.DEFAULT_INSTANCE);
            }

            public boolean hasClientInfo() {
                return ((GCoreDimensions) this.instance).hasClientInfo();
            }

            public GCoreClientInfo getClientInfo() {
                return ((GCoreDimensions) this.instance).getClientInfo();
            }

            public Builder setClientInfo(GCoreClientInfo value) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).setClientInfo(value);
                return this;
            }

            public Builder setClientInfo(GCoreClientInfo.Builder builderForValue) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).setClientInfo(builderForValue);
                return this;
            }

            public Builder mergeClientInfo(GCoreClientInfo value) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).mergeClientInfo(value);
                return this;
            }

            public Builder clearClientInfo() {
                copyOnWrite();
                ((GCoreDimensions) this.instance).clearClientInfo();
                return this;
            }

            public boolean hasEntryPointInfo() {
                return ((GCoreDimensions) this.instance).hasEntryPointInfo();
            }

            public EntryPointInfo getEntryPointInfo() {
                return ((GCoreDimensions) this.instance).getEntryPointInfo();
            }

            public Builder setEntryPointInfo(EntryPointInfo value) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).setEntryPointInfo(value);
                return this;
            }

            public Builder setEntryPointInfo(EntryPointInfo.Builder builderForValue) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).setEntryPointInfo(builderForValue);
                return this;
            }

            public Builder mergeEntryPointInfo(EntryPointInfo value) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).mergeEntryPointInfo(value);
                return this;
            }

            public Builder clearEntryPointInfo() {
                copyOnWrite();
                ((GCoreDimensions) this.instance).clearEntryPointInfo();
                return this;
            }

            public boolean hasModuleInfo() {
                return ((GCoreDimensions) this.instance).hasModuleInfo();
            }

            public GCoreModuleInfo getModuleInfo() {
                return ((GCoreDimensions) this.instance).getModuleInfo();
            }

            public Builder setModuleInfo(GCoreModuleInfo value) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).setModuleInfo(value);
                return this;
            }

            public Builder setModuleInfo(GCoreModuleInfo.Builder builderForValue) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).setModuleInfo(builderForValue);
                return this;
            }

            public Builder mergeModuleInfo(GCoreModuleInfo value) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).mergeModuleInfo(value);
                return this;
            }

            public Builder clearModuleInfo() {
                copyOnWrite();
                ((GCoreDimensions) this.instance).clearModuleInfo();
                return this;
            }

            public boolean hasDeviceInfo() {
                return ((GCoreDimensions) this.instance).hasDeviceInfo();
            }

            public DeviceInfo getDeviceInfo() {
                return ((GCoreDimensions) this.instance).getDeviceInfo();
            }

            public Builder setDeviceInfo(DeviceInfo value) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).setDeviceInfo(value);
                return this;
            }

            public Builder setDeviceInfo(DeviceInfo.Builder builderForValue) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).setDeviceInfo(builderForValue);
                return this;
            }

            public Builder mergeDeviceInfo(DeviceInfo value) {
                copyOnWrite();
                ((GCoreDimensions) this.instance).mergeDeviceInfo(value);
                return this;
            }

            public Builder clearDeviceInfo() {
                copyOnWrite();
                ((GCoreDimensions) this.instance).clearDeviceInfo();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GCoreDimensions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\t\u0003", new Object[]{"bitField0_", "clientInfo_", "entryPointInfo_", "moduleInfo_", "deviceInfo_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GCoreDimensions> parser = PARSER;
                    if (parser == null) {
                        synchronized (GCoreDimensions.class) {
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
            GeneratedMessageLite.registerDefaultInstance(GCoreDimensions.class, DEFAULT_INSTANCE);
        }

        public static GCoreDimensions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GCoreDimensions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class GCoreClientInfo extends GeneratedMessageLite<GCoreClientInfo, Builder> implements GCoreClientInfoOrBuilder {
        public static final int CALLING_PACKAGE_FIELD_NUMBER = 1;
        public static final int CLIENT_TYPE_FIELD_NUMBER = 5;
        /* access modifiers changed from: private */
        public static final GCoreClientInfo DEFAULT_INSTANCE = new GCoreClientInfo();
        private static volatile Parser<GCoreClientInfo> PARSER = null;
        public static final int SDK_VERSION_FIELD_NUMBER = 2;
        public static final int VERSION_CODE_FIELD_NUMBER = 3;
        public static final int VERSION_NAME_FIELD_NUMBER = 4;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String callingPackage_ = "";
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int clientType_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int sdkVersion_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int versionCode_;
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private String versionName_ = "";

        private GCoreClientInfo() {
        }

        public enum ClientType implements Internal.EnumLite {
            UNKNOWN(0),
            ZERO_PARTY(1),
            FIRST_PARTY(2),
            THIRD_PARTY(3);
            
            public static final int FIRST_PARTY_VALUE = 2;
            public static final int THIRD_PARTY_VALUE = 3;
            public static final int UNKNOWN_VALUE = 0;
            public static final int ZERO_PARTY_VALUE = 1;
            private static final Internal.EnumLiteMap<ClientType> internalValueMap = new Internal.EnumLiteMap<ClientType>() {
                public ClientType findValueByNumber(int number) {
                    return ClientType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static ClientType forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return ZERO_PARTY;
                }
                if (value2 == 2) {
                    return FIRST_PARTY;
                }
                if (value2 != 3) {
                    return null;
                }
                return THIRD_PARTY;
            }

            public static Internal.EnumLiteMap<ClientType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return ClientTypeVerifier.INSTANCE;
            }

            private static final class ClientTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new ClientTypeVerifier();

                private ClientTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return ClientType.forNumber(number) != null;
                }
            }

            private ClientType(int value2) {
                this.value = value2;
            }
        }

        public boolean hasCallingPackage() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getCallingPackage() {
            return this.callingPackage_;
        }

        public ByteString getCallingPackageBytes() {
            return ByteString.copyFromUtf8(this.callingPackage_);
        }

        /* access modifiers changed from: private */
        public void setCallingPackage(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.callingPackage_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCallingPackage() {
            this.bitField0_ &= -2;
            this.callingPackage_ = getDefaultInstance().getCallingPackage();
        }

        /* access modifiers changed from: private */
        public void setCallingPackageBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.callingPackage_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasSdkVersion() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getSdkVersion() {
            return this.sdkVersion_;
        }

        /* access modifiers changed from: private */
        public void setSdkVersion(int value) {
            this.bitField0_ |= 2;
            this.sdkVersion_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSdkVersion() {
            this.bitField0_ &= -3;
            this.sdkVersion_ = 0;
        }

        public boolean hasVersionCode() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getVersionCode() {
            return this.versionCode_;
        }

        /* access modifiers changed from: private */
        public void setVersionCode(int value) {
            this.bitField0_ |= 4;
            this.versionCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearVersionCode() {
            this.bitField0_ &= -5;
            this.versionCode_ = 0;
        }

        public boolean hasVersionName() {
            return (this.bitField0_ & 8) != 0;
        }

        public String getVersionName() {
            return this.versionName_;
        }

        public ByteString getVersionNameBytes() {
            return ByteString.copyFromUtf8(this.versionName_);
        }

        /* access modifiers changed from: private */
        public void setVersionName(String value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.versionName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearVersionName() {
            this.bitField0_ &= -9;
            this.versionName_ = getDefaultInstance().getVersionName();
        }

        /* access modifiers changed from: private */
        public void setVersionNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.versionName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasClientType() {
            return (this.bitField0_ & 16) != 0;
        }

        public ClientType getClientType() {
            ClientType result = ClientType.forNumber(this.clientType_);
            return result == null ? ClientType.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setClientType(ClientType value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.clientType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearClientType() {
            this.bitField0_ &= -17;
            this.clientType_ = 0;
        }

        public static GCoreClientInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GCoreClientInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GCoreClientInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GCoreClientInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GCoreClientInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GCoreClientInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GCoreClientInfo parseFrom(InputStream input) throws IOException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GCoreClientInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GCoreClientInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (GCoreClientInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GCoreClientInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GCoreClientInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GCoreClientInfo parseFrom(CodedInputStream input) throws IOException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GCoreClientInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GCoreClientInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GCoreClientInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<GCoreClientInfo, Builder> implements GCoreClientInfoOrBuilder {
            private Builder() {
                super(GCoreClientInfo.DEFAULT_INSTANCE);
            }

            public boolean hasCallingPackage() {
                return ((GCoreClientInfo) this.instance).hasCallingPackage();
            }

            public String getCallingPackage() {
                return ((GCoreClientInfo) this.instance).getCallingPackage();
            }

            public ByteString getCallingPackageBytes() {
                return ((GCoreClientInfo) this.instance).getCallingPackageBytes();
            }

            public Builder setCallingPackage(String value) {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).setCallingPackage(value);
                return this;
            }

            public Builder clearCallingPackage() {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).clearCallingPackage();
                return this;
            }

            public Builder setCallingPackageBytes(ByteString value) {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).setCallingPackageBytes(value);
                return this;
            }

            public boolean hasSdkVersion() {
                return ((GCoreClientInfo) this.instance).hasSdkVersion();
            }

            public int getSdkVersion() {
                return ((GCoreClientInfo) this.instance).getSdkVersion();
            }

            public Builder setSdkVersion(int value) {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).setSdkVersion(value);
                return this;
            }

            public Builder clearSdkVersion() {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).clearSdkVersion();
                return this;
            }

            public boolean hasVersionCode() {
                return ((GCoreClientInfo) this.instance).hasVersionCode();
            }

            public int getVersionCode() {
                return ((GCoreClientInfo) this.instance).getVersionCode();
            }

            public Builder setVersionCode(int value) {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).setVersionCode(value);
                return this;
            }

            public Builder clearVersionCode() {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).clearVersionCode();
                return this;
            }

            public boolean hasVersionName() {
                return ((GCoreClientInfo) this.instance).hasVersionName();
            }

            public String getVersionName() {
                return ((GCoreClientInfo) this.instance).getVersionName();
            }

            public ByteString getVersionNameBytes() {
                return ((GCoreClientInfo) this.instance).getVersionNameBytes();
            }

            public Builder setVersionName(String value) {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).setVersionName(value);
                return this;
            }

            public Builder clearVersionName() {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).clearVersionName();
                return this;
            }

            public Builder setVersionNameBytes(ByteString value) {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).setVersionNameBytes(value);
                return this;
            }

            public boolean hasClientType() {
                return ((GCoreClientInfo) this.instance).hasClientType();
            }

            public ClientType getClientType() {
                return ((GCoreClientInfo) this.instance).getClientType();
            }

            public Builder setClientType(ClientType value) {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).setClientType(value);
                return this;
            }

            public Builder clearClientType() {
                copyOnWrite();
                ((GCoreClientInfo) this.instance).clearClientType();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GCoreClientInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\u0004\u0001\u0003\u0004\u0002\u0004\b\u0003\u0005\f\u0004", new Object[]{"bitField0_", "callingPackage_", "sdkVersion_", "versionCode_", "versionName_", "clientType_", ClientType.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GCoreClientInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (GCoreClientInfo.class) {
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
            GeneratedMessageLite.registerDefaultInstance(GCoreClientInfo.class, DEFAULT_INSTANCE);
        }

        public static GCoreClientInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GCoreClientInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class EntryPointInfo extends GeneratedMessageLite<EntryPointInfo, Builder> implements EntryPointInfoOrBuilder {
        /* access modifiers changed from: private */
        public static final EntryPointInfo DEFAULT_INSTANCE = new EntryPointInfo();
        public static final int INTENT_HASH_FIELD_NUMBER = 3;
        public static final int NAME_FIELD_NUMBER = 2;
        private static volatile Parser<EntryPointInfo> PARSER = null;
        public static final int TYPE_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int intentHash_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int type_;

        private EntryPointInfo() {
        }

        public enum EntryPointType implements Internal.EnumLite {
            UNKNOWN(0),
            PROVIDENCE(1),
            INTENT_OPERATION(2),
            BROADCAST_RECEIVER(3),
            CONTENT_PROVIDER(4),
            ACTIVITY(5),
            SERVICE(6),
            BINDER(7),
            SYNC_ADAPTER(8),
            GCM_TASK(9),
            INTENT_SERVICE(10),
            SERVICE_CONNECTION(11),
            GCM_LISTENER(12),
            CALLBACKS(13),
            ALARM_LISTENER(14),
            CUSTOM_EVENT_LOOP(15),
            SENSOR_EVENT_LISTENER(16),
            BLE_SCAN_CALLBACK(17),
            BINDER_BY_INTERCEPTOR(18),
            CONTENT_OBSERVER(19),
            BACKUP_AGENT(20);
            
            public static final int ACTIVITY_VALUE = 5;
            public static final int ALARM_LISTENER_VALUE = 14;
            public static final int BACKUP_AGENT_VALUE = 20;
            public static final int BINDER_BY_INTERCEPTOR_VALUE = 18;
            public static final int BINDER_VALUE = 7;
            public static final int BLE_SCAN_CALLBACK_VALUE = 17;
            public static final int BROADCAST_RECEIVER_VALUE = 3;
            public static final int CALLBACKS_VALUE = 13;
            public static final int CONTENT_OBSERVER_VALUE = 19;
            public static final int CONTENT_PROVIDER_VALUE = 4;
            public static final int CUSTOM_EVENT_LOOP_VALUE = 15;
            public static final int GCM_LISTENER_VALUE = 12;
            public static final int GCM_TASK_VALUE = 9;
            public static final int INTENT_OPERATION_VALUE = 2;
            public static final int INTENT_SERVICE_VALUE = 10;
            public static final int PROVIDENCE_VALUE = 1;
            public static final int SENSOR_EVENT_LISTENER_VALUE = 16;
            public static final int SERVICE_CONNECTION_VALUE = 11;
            public static final int SERVICE_VALUE = 6;
            public static final int SYNC_ADAPTER_VALUE = 8;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<EntryPointType> internalValueMap = new Internal.EnumLiteMap<EntryPointType>() {
                public EntryPointType findValueByNumber(int number) {
                    return EntryPointType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static EntryPointType forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return UNKNOWN;
                    case 1:
                        return PROVIDENCE;
                    case 2:
                        return INTENT_OPERATION;
                    case 3:
                        return BROADCAST_RECEIVER;
                    case 4:
                        return CONTENT_PROVIDER;
                    case 5:
                        return ACTIVITY;
                    case 6:
                        return SERVICE;
                    case 7:
                        return BINDER;
                    case 8:
                        return SYNC_ADAPTER;
                    case 9:
                        return GCM_TASK;
                    case 10:
                        return INTENT_SERVICE;
                    case 11:
                        return SERVICE_CONNECTION;
                    case 12:
                        return GCM_LISTENER;
                    case 13:
                        return CALLBACKS;
                    case 14:
                        return ALARM_LISTENER;
                    case 15:
                        return CUSTOM_EVENT_LOOP;
                    case 16:
                        return SENSOR_EVENT_LISTENER;
                    case 17:
                        return BLE_SCAN_CALLBACK;
                    case 18:
                        return BINDER_BY_INTERCEPTOR;
                    case 19:
                        return CONTENT_OBSERVER;
                    case 20:
                        return BACKUP_AGENT;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<EntryPointType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return EntryPointTypeVerifier.INSTANCE;
            }

            private static final class EntryPointTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new EntryPointTypeVerifier();

                private EntryPointTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return EntryPointType.forNumber(number) != null;
                }
            }

            private EntryPointType(int value2) {
                this.value = value2;
            }
        }

        public boolean hasType() {
            return (this.bitField0_ & 1) != 0;
        }

        public EntryPointType getType() {
            EntryPointType result = EntryPointType.forNumber(this.type_);
            return result == null ? EntryPointType.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setType(EntryPointType value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.type_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearType() {
            this.bitField0_ &= -2;
            this.type_ = 0;
        }

        public boolean hasName() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -3;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasIntentHash() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getIntentHash() {
            return this.intentHash_;
        }

        /* access modifiers changed from: private */
        public void setIntentHash(int value) {
            this.bitField0_ |= 4;
            this.intentHash_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIntentHash() {
            this.bitField0_ &= -5;
            this.intentHash_ = 0;
        }

        public static EntryPointInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EntryPointInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EntryPointInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EntryPointInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EntryPointInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EntryPointInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EntryPointInfo parseFrom(InputStream input) throws IOException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EntryPointInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EntryPointInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (EntryPointInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static EntryPointInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EntryPointInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EntryPointInfo parseFrom(CodedInputStream input) throws IOException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EntryPointInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EntryPointInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(EntryPointInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<EntryPointInfo, Builder> implements EntryPointInfoOrBuilder {
            private Builder() {
                super(EntryPointInfo.DEFAULT_INSTANCE);
            }

            public boolean hasType() {
                return ((EntryPointInfo) this.instance).hasType();
            }

            public EntryPointType getType() {
                return ((EntryPointInfo) this.instance).getType();
            }

            public Builder setType(EntryPointType value) {
                copyOnWrite();
                ((EntryPointInfo) this.instance).setType(value);
                return this;
            }

            public Builder clearType() {
                copyOnWrite();
                ((EntryPointInfo) this.instance).clearType();
                return this;
            }

            public boolean hasName() {
                return ((EntryPointInfo) this.instance).hasName();
            }

            public String getName() {
                return ((EntryPointInfo) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((EntryPointInfo) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((EntryPointInfo) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((EntryPointInfo) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((EntryPointInfo) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasIntentHash() {
                return ((EntryPointInfo) this.instance).hasIntentHash();
            }

            public int getIntentHash() {
                return ((EntryPointInfo) this.instance).getIntentHash();
            }

            public Builder setIntentHash(int value) {
                copyOnWrite();
                ((EntryPointInfo) this.instance).setIntentHash(value);
                return this;
            }

            public Builder clearIntentHash() {
                copyOnWrite();
                ((EntryPointInfo) this.instance).clearIntentHash();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new EntryPointInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\f\u0000\u0002\b\u0001\u0003\u0004\u0002", new Object[]{"bitField0_", "type_", EntryPointType.internalGetVerifier(), "name_", "intentHash_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<EntryPointInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (EntryPointInfo.class) {
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
            GeneratedMessageLite.registerDefaultInstance(EntryPointInfo.class, DEFAULT_INSTANCE);
        }

        public static EntryPointInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EntryPointInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class GCoreModuleInfo extends GeneratedMessageLite<GCoreModuleInfo, Builder> implements GCoreModuleInfoOrBuilder {
        /* access modifiers changed from: private */
        public static final GCoreModuleInfo DEFAULT_INSTANCE = new GCoreModuleInfo();
        public static final int MODULE_ID_FIELD_NUMBER = 1;
        public static final int MODULE_VERSION_FIELD_NUMBER = 2;
        private static volatile Parser<GCoreModuleInfo> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String moduleId_ = "";
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int moduleVersion_;

        private GCoreModuleInfo() {
        }

        public boolean hasModuleId() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getModuleId() {
            return this.moduleId_;
        }

        public ByteString getModuleIdBytes() {
            return ByteString.copyFromUtf8(this.moduleId_);
        }

        /* access modifiers changed from: private */
        public void setModuleId(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.moduleId_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearModuleId() {
            this.bitField0_ &= -2;
            this.moduleId_ = getDefaultInstance().getModuleId();
        }

        /* access modifiers changed from: private */
        public void setModuleIdBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.moduleId_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasModuleVersion() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getModuleVersion() {
            return this.moduleVersion_;
        }

        /* access modifiers changed from: private */
        public void setModuleVersion(int value) {
            this.bitField0_ |= 2;
            this.moduleVersion_ = value;
        }

        /* access modifiers changed from: private */
        public void clearModuleVersion() {
            this.bitField0_ &= -3;
            this.moduleVersion_ = 0;
        }

        public static GCoreModuleInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GCoreModuleInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GCoreModuleInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GCoreModuleInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GCoreModuleInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GCoreModuleInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GCoreModuleInfo parseFrom(InputStream input) throws IOException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GCoreModuleInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GCoreModuleInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (GCoreModuleInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GCoreModuleInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GCoreModuleInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GCoreModuleInfo parseFrom(CodedInputStream input) throws IOException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GCoreModuleInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GCoreModuleInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GCoreModuleInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<GCoreModuleInfo, Builder> implements GCoreModuleInfoOrBuilder {
            private Builder() {
                super(GCoreModuleInfo.DEFAULT_INSTANCE);
            }

            public boolean hasModuleId() {
                return ((GCoreModuleInfo) this.instance).hasModuleId();
            }

            public String getModuleId() {
                return ((GCoreModuleInfo) this.instance).getModuleId();
            }

            public ByteString getModuleIdBytes() {
                return ((GCoreModuleInfo) this.instance).getModuleIdBytes();
            }

            public Builder setModuleId(String value) {
                copyOnWrite();
                ((GCoreModuleInfo) this.instance).setModuleId(value);
                return this;
            }

            public Builder clearModuleId() {
                copyOnWrite();
                ((GCoreModuleInfo) this.instance).clearModuleId();
                return this;
            }

            public Builder setModuleIdBytes(ByteString value) {
                copyOnWrite();
                ((GCoreModuleInfo) this.instance).setModuleIdBytes(value);
                return this;
            }

            public boolean hasModuleVersion() {
                return ((GCoreModuleInfo) this.instance).hasModuleVersion();
            }

            public int getModuleVersion() {
                return ((GCoreModuleInfo) this.instance).getModuleVersion();
            }

            public Builder setModuleVersion(int value) {
                copyOnWrite();
                ((GCoreModuleInfo) this.instance).setModuleVersion(value);
                return this;
            }

            public Builder clearModuleVersion() {
                copyOnWrite();
                ((GCoreModuleInfo) this.instance).clearModuleVersion();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GCoreModuleInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\b\u0000\u0002\u0004\u0001", new Object[]{"bitField0_", "moduleId_", "moduleVersion_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GCoreModuleInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (GCoreModuleInfo.class) {
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
            GeneratedMessageLite.registerDefaultInstance(GCoreModuleInfo.class, DEFAULT_INSTANCE);
        }

        public static GCoreModuleInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GCoreModuleInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class DeviceInfo extends GeneratedMessageLite<DeviceInfo, Builder> implements DeviceInfoOrBuilder {
        /* access modifiers changed from: private */
        public static final DeviceInfo DEFAULT_INSTANCE = new DeviceInfo();
        public static final int DEVICE_STATE_FIELD_NUMBER = 1;
        private static volatile Parser<DeviceInfo> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int deviceState_;

        private DeviceInfo() {
        }

        public enum DeviceState implements Internal.EnumLite {
            STATE_NONE(0),
            STATE_POWER_CONNECTED(1),
            STATE_INTERACTIVE(2),
            STATE_UNINITIALIZED(7),
            STATE_BATTERY_LOW(8),
            STATE_DEVICE_IDLE_MODE(16),
            STATE_POWER_SAVE_MODE(32);
            
            public static final int STATE_BATTERY_LOW_VALUE = 8;
            public static final int STATE_DEVICE_IDLE_MODE_VALUE = 16;
            public static final int STATE_INTERACTIVE_VALUE = 2;
            public static final int STATE_NONE_VALUE = 0;
            public static final int STATE_POWER_CONNECTED_VALUE = 1;
            public static final int STATE_POWER_SAVE_MODE_VALUE = 32;
            public static final int STATE_UNINITIALIZED_VALUE = 7;
            private static final Internal.EnumLiteMap<DeviceState> internalValueMap = new Internal.EnumLiteMap<DeviceState>() {
                public DeviceState findValueByNumber(int number) {
                    return DeviceState.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static DeviceState forNumber(int value2) {
                if (value2 == 0) {
                    return STATE_NONE;
                }
                if (value2 == 1) {
                    return STATE_POWER_CONNECTED;
                }
                if (value2 == 2) {
                    return STATE_INTERACTIVE;
                }
                if (value2 == 7) {
                    return STATE_UNINITIALIZED;
                }
                if (value2 == 8) {
                    return STATE_BATTERY_LOW;
                }
                if (value2 == 16) {
                    return STATE_DEVICE_IDLE_MODE;
                }
                if (value2 != 32) {
                    return null;
                }
                return STATE_POWER_SAVE_MODE;
            }

            public static Internal.EnumLiteMap<DeviceState> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return DeviceStateVerifier.INSTANCE;
            }

            private static final class DeviceStateVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new DeviceStateVerifier();

                private DeviceStateVerifier() {
                }

                public boolean isInRange(int number) {
                    return DeviceState.forNumber(number) != null;
                }
            }

            private DeviceState(int value2) {
                this.value = value2;
            }
        }

        public boolean hasDeviceState() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getDeviceState() {
            return this.deviceState_;
        }

        /* access modifiers changed from: private */
        public void setDeviceState(int value) {
            this.bitField0_ |= 1;
            this.deviceState_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeviceState() {
            this.bitField0_ &= -2;
            this.deviceState_ = 0;
        }

        public static DeviceInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceInfo parseFrom(InputStream input) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceInfo parseFrom(CodedInputStream input) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DeviceInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DeviceInfo, Builder> implements DeviceInfoOrBuilder {
            private Builder() {
                super(DeviceInfo.DEFAULT_INSTANCE);
            }

            public boolean hasDeviceState() {
                return ((DeviceInfo) this.instance).hasDeviceState();
            }

            public int getDeviceState() {
                return ((DeviceInfo) this.instance).getDeviceState();
            }

            public Builder setDeviceState(int value) {
                copyOnWrite();
                ((DeviceInfo) this.instance).setDeviceState(value);
                return this;
            }

            public Builder clearDeviceState() {
                copyOnWrite();
                ((DeviceInfo) this.instance).clearDeviceState();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DeviceInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004\u0000", new Object[]{"bitField0_", "deviceState_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DeviceInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (DeviceInfo.class) {
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
            GeneratedMessageLite.registerDefaultInstance(DeviceInfo.class, DEFAULT_INSTANCE);
        }

        public static DeviceInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
