package com.google.protos.logs_proto;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.DescriptorProtos;
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
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class LogsAnnotations {
    public static final int FIELD_ENCRYPTION_KEY_NAME_FIELD_NUMBER = 26652850;
    public static final int FILE_NOT_USED_FOR_LOGGING_EXCEPT_ENUMS_FIELD_NUMBER = 21596320;
    public static final int FILE_VETTED_FOR_LOGS_ANNOTATIONS_FIELD_NUMBER = 28993747;
    public static final int ID_TYPE_FIELD_NUMBER = 21713708;
    public static final int IS_ENCRYPTED_FIELD_NUMBER = 26652850;
    public static final int IS_PRIVATE_LOG_FIELD_NUMBER = 23459630;
    public static final int MAX_RECURSION_DEPTH_FIELD_NUMBER = 53697879;
    public static final int MSG_DETAILS_FIELD_NUMBER = 21467048;
    public static final int MSG_ID_TYPE_FIELD_NUMBER = 21713708;
    public static final int MSG_NOT_LOGGED_IN_SAWMILL_FIELD_NUMBER = 21596320;
    public static final int MSG_TEMP_LOGS_ONLY_FIELD_NUMBER = 21623477;
    public static final int NOT_LOGGED_IN_SAWMILL_FIELD_NUMBER = 21596320;
    public static final int SAWMILL_FILTER_OVERRIDE_APPROVED_BY_LOGS_ACCESS_FIELD_NUMBER = 56871503;
    public static final int TEMP_LOGS_ONLY_FIELD_NUMBER = 21623477;
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, String> fieldEncryptionKeyName = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), "", null, null, 26652850, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, Boolean> fileNotUsedForLoggingExceptEnums = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), null, null, null, 21596320, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, Boolean> fileVettedForLogsAnnotations = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), null, null, null, FILE_VETTED_FOR_LOGS_ANNOTATIONS_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, IdentifierType> idType = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), IdentifierType.LOGSID_NONE, null, IdentifierType.internalGetValueMap(), 21713708, WireFormat.FieldType.ENUM, IdentifierType.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Boolean> isEncrypted = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), null, null, null, 26652850, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Boolean> isPrivateLog = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), null, null, null, IS_PRIVATE_LOG_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Integer> maxRecursionDepth = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), 0, null, null, MAX_RECURSION_DEPTH_FIELD_NUMBER, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, MessageDetails> msgDetails = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), MessageDetails.getDefaultInstance(), MessageDetails.getDefaultInstance(), null, MSG_DETAILS_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, MessageDetails.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, IdentifierType> msgIdType = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), IdentifierType.LOGSID_NONE, null, IdentifierType.internalGetValueMap(), 21713708, WireFormat.FieldType.ENUM, IdentifierType.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, Boolean> msgNotLoggedInSawmill = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), null, null, null, 21596320, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, Boolean> msgTempLogsOnly = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), null, null, null, 21623477, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Boolean> notLoggedInSawmill = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), null, null, null, 21596320, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, String> sawmillFilterOverrideApprovedByLogsAccess = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), "", null, null, SAWMILL_FILTER_OVERRIDE_APPROVED_BY_LOGS_ACCESS_FIELD_NUMBER, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Boolean> tempLogsOnly = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), null, null, null, 21623477, WireFormat.FieldType.BOOL, Boolean.class);

    public interface MessageDetailsOrBuilder extends MessageLiteOrBuilder {
        MessageDetails.Type getMayAppearIn(int i);

        int getMayAppearInCount();

        List<MessageDetails.Type> getMayAppearInList();
    }

    private LogsAnnotations() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) idType);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) tempLogsOnly);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) isPrivateLog);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) notLoggedInSawmill);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) isEncrypted);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) maxRecursionDepth);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) sawmillFilterOverrideApprovedByLogsAccess);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) msgIdType);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) msgTempLogsOnly);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) msgNotLoggedInSawmill);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) msgDetails);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) fieldEncryptionKeyName);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) fileNotUsedForLoggingExceptEnums);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) fileVettedForLogsAnnotations);
    }

    public enum IdentifierType implements Internal.EnumLite {
        LOGSID_NONE(0),
        LOGSID_IP_ADDRESS(1),
        LOGSID_IP_ADDRESS_INTERNAL(2),
        LOGSID_USER_AGENT(3),
        LOGSID_SENSITIVE_TIMESTAMP(4),
        LOGSID_SENSITIVE_LOCATION(5),
        LOGSID_APPROXIMATE_LOCATION(15),
        LOGSID_COARSE_LOCATION(6),
        LOGSID_OTHER_LOCATION(9),
        LOGSID_OTHER_VERSION_ID(7),
        LOGSID_REFERER(8),
        LOGSID_THIRD_PARTY_PARAMETERS(16),
        LOGSID_OTHER_PSEUDONYMOUS_ID(10),
        LOGSID_PREF(11),
        LOGSID_ZWIEBACK(12),
        LOGSID_BISCOTTI(13),
        LOGSID_CUSTOM_SESSION_ID(14),
        LOGSID_OTHER_PERSONAL_ID(20),
        LOGSID_GAIA_ID(21),
        LOGSID_EMAIL(22),
        LOGSID_USERNAME(23),
        LOGSID_PHONE_NUMBER(24),
        LOGSID_GAIA_ID_PUBLIC(207),
        LOGSID_OTHER_AUTHENTICATED_ID(30),
        LOGSID_OTHER_UNAUTHENTICATED_ID(31),
        LOGSID_PARTNER_OR_CUSTOMER_ID(32),
        LOGSID_PUBLISHER_ID(35),
        LOGSID_DASHER_ID(33),
        LOGSID_FOCUS_GROUP_ID(34),
        LOGSID_OTHER_MOBILE_DEVICE_ID(50),
        LOGSID_GSERVICES_ANDROID_ID(51),
        LOGSID_HARDWARE_ID(52),
        LOGSID_MSISDN_ID(53),
        LOGSID_BUILD_SERIAL_ID(54),
        LOGSID_UDID_ID(55),
        LOGSID_ANDROID_LOGGING_ID(56),
        LOGSID_SECURE_SETTINGS_ANDROID_ID(57),
        LOGSID_OTHER_IDENTIFYING_USER_INFO(100),
        LOGSID_USER_INPUT(200),
        LOGSID_DEMOGRAPHIC_INFO(201),
        LOGSID_GENERIC_KEY(202),
        LOGSID_GENERIC_VALUE(203),
        LOGSID_COOKIE(204),
        LOGSID_URL(205),
        LOGSID_HTTPHEADER(206);
        
        public static final int LOGSID_ANDROID_LOGGING_ID_VALUE = 56;
        public static final int LOGSID_APPROXIMATE_LOCATION_VALUE = 15;
        public static final int LOGSID_BISCOTTI_VALUE = 13;
        public static final int LOGSID_BUILD_SERIAL_ID_VALUE = 54;
        public static final int LOGSID_COARSE_LOCATION_VALUE = 6;
        public static final int LOGSID_COOKIE_VALUE = 204;
        public static final int LOGSID_CUSTOM_SESSION_ID_VALUE = 14;
        public static final int LOGSID_DASHER_ID_VALUE = 33;
        public static final int LOGSID_DEMOGRAPHIC_INFO_VALUE = 201;
        public static final int LOGSID_EMAIL_VALUE = 22;
        public static final int LOGSID_FOCUS_GROUP_ID_VALUE = 34;
        public static final int LOGSID_GAIA_ID_PUBLIC_VALUE = 207;
        public static final int LOGSID_GAIA_ID_VALUE = 21;
        public static final int LOGSID_GENERIC_KEY_VALUE = 202;
        public static final int LOGSID_GENERIC_VALUE_VALUE = 203;
        public static final int LOGSID_GSERVICES_ANDROID_ID_VALUE = 51;
        public static final int LOGSID_HARDWARE_ID_VALUE = 52;
        public static final int LOGSID_HTTPHEADER_VALUE = 206;
        public static final int LOGSID_IP_ADDRESS_INTERNAL_VALUE = 2;
        public static final int LOGSID_IP_ADDRESS_VALUE = 1;
        public static final int LOGSID_MSISDN_ID_VALUE = 53;
        public static final int LOGSID_NONE_VALUE = 0;
        public static final int LOGSID_OTHER_AUTHENTICATED_ID_VALUE = 30;
        public static final int LOGSID_OTHER_IDENTIFYING_USER_INFO_VALUE = 100;
        public static final int LOGSID_OTHER_LOCATION_VALUE = 9;
        public static final int LOGSID_OTHER_MOBILE_DEVICE_ID_VALUE = 50;
        public static final int LOGSID_OTHER_PERSONAL_ID_VALUE = 20;
        public static final int LOGSID_OTHER_PSEUDONYMOUS_ID_VALUE = 10;
        public static final int LOGSID_OTHER_UNAUTHENTICATED_ID_VALUE = 31;
        public static final int LOGSID_OTHER_VERSION_ID_VALUE = 7;
        public static final int LOGSID_PARTNER_OR_CUSTOMER_ID_VALUE = 32;
        public static final int LOGSID_PHONE_NUMBER_VALUE = 24;
        public static final int LOGSID_PREF_VALUE = 11;
        public static final int LOGSID_PUBLISHER_ID_VALUE = 35;
        public static final int LOGSID_REFERER_VALUE = 8;
        public static final int LOGSID_SECURE_SETTINGS_ANDROID_ID_VALUE = 57;
        public static final int LOGSID_SENSITIVE_LOCATION_VALUE = 5;
        public static final int LOGSID_SENSITIVE_TIMESTAMP_VALUE = 4;
        public static final int LOGSID_THIRD_PARTY_PARAMETERS_VALUE = 16;
        public static final int LOGSID_UDID_ID_VALUE = 55;
        public static final int LOGSID_URL_VALUE = 205;
        public static final int LOGSID_USERNAME_VALUE = 23;
        public static final int LOGSID_USER_AGENT_VALUE = 3;
        public static final int LOGSID_USER_INPUT_VALUE = 200;
        public static final int LOGSID_ZWIEBACK_VALUE = 12;
        private static final Internal.EnumLiteMap<IdentifierType> internalValueMap = new Internal.EnumLiteMap<IdentifierType>() {
            public IdentifierType findValueByNumber(int number) {
                return IdentifierType.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static IdentifierType forNumber(int value2) {
            if (value2 == 100) {
                return LOGSID_OTHER_IDENTIFYING_USER_INFO;
            }
            switch (value2) {
                case 0:
                    return LOGSID_NONE;
                case 1:
                    return LOGSID_IP_ADDRESS;
                case 2:
                    return LOGSID_IP_ADDRESS_INTERNAL;
                case 3:
                    return LOGSID_USER_AGENT;
                case 4:
                    return LOGSID_SENSITIVE_TIMESTAMP;
                case 5:
                    return LOGSID_SENSITIVE_LOCATION;
                case 6:
                    return LOGSID_COARSE_LOCATION;
                case 7:
                    return LOGSID_OTHER_VERSION_ID;
                case 8:
                    return LOGSID_REFERER;
                case 9:
                    return LOGSID_OTHER_LOCATION;
                case 10:
                    return LOGSID_OTHER_PSEUDONYMOUS_ID;
                case 11:
                    return LOGSID_PREF;
                case 12:
                    return LOGSID_ZWIEBACK;
                case 13:
                    return LOGSID_BISCOTTI;
                case 14:
                    return LOGSID_CUSTOM_SESSION_ID;
                case 15:
                    return LOGSID_APPROXIMATE_LOCATION;
                case 16:
                    return LOGSID_THIRD_PARTY_PARAMETERS;
                default:
                    switch (value2) {
                        case 20:
                            return LOGSID_OTHER_PERSONAL_ID;
                        case 21:
                            return LOGSID_GAIA_ID;
                        case 22:
                            return LOGSID_EMAIL;
                        case 23:
                            return LOGSID_USERNAME;
                        case 24:
                            return LOGSID_PHONE_NUMBER;
                        default:
                            switch (value2) {
                                case 30:
                                    return LOGSID_OTHER_AUTHENTICATED_ID;
                                case 31:
                                    return LOGSID_OTHER_UNAUTHENTICATED_ID;
                                case 32:
                                    return LOGSID_PARTNER_OR_CUSTOMER_ID;
                                case 33:
                                    return LOGSID_DASHER_ID;
                                case 34:
                                    return LOGSID_FOCUS_GROUP_ID;
                                case 35:
                                    return LOGSID_PUBLISHER_ID;
                                default:
                                    switch (value2) {
                                        case 50:
                                            return LOGSID_OTHER_MOBILE_DEVICE_ID;
                                        case 51:
                                            return LOGSID_GSERVICES_ANDROID_ID;
                                        case 52:
                                            return LOGSID_HARDWARE_ID;
                                        case 53:
                                            return LOGSID_MSISDN_ID;
                                        case 54:
                                            return LOGSID_BUILD_SERIAL_ID;
                                        case 55:
                                            return LOGSID_UDID_ID;
                                        case 56:
                                            return LOGSID_ANDROID_LOGGING_ID;
                                        case 57:
                                            return LOGSID_SECURE_SETTINGS_ANDROID_ID;
                                        default:
                                            switch (value2) {
                                                case 200:
                                                    return LOGSID_USER_INPUT;
                                                case 201:
                                                    return LOGSID_DEMOGRAPHIC_INFO;
                                                case 202:
                                                    return LOGSID_GENERIC_KEY;
                                                case 203:
                                                    return LOGSID_GENERIC_VALUE;
                                                case 204:
                                                    return LOGSID_COOKIE;
                                                case 205:
                                                    return LOGSID_URL;
                                                case 206:
                                                    return LOGSID_HTTPHEADER;
                                                case 207:
                                                    return LOGSID_GAIA_ID_PUBLIC;
                                                default:
                                                    return null;
                                            }
                                    }
                            }
                    }
            }
        }

        public static Internal.EnumLiteMap<IdentifierType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return IdentifierTypeVerifier.INSTANCE;
        }

        private static final class IdentifierTypeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new IdentifierTypeVerifier();

            private IdentifierTypeVerifier() {
            }

            public boolean isInRange(int number) {
                return IdentifierType.forNumber(number) != null;
            }
        }

        private IdentifierType(int value2) {
            this.value = value2;
        }
    }

    @ProtoMessage(checkInitialized = {1}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MessageDetails extends GeneratedMessageLite<MessageDetails, Builder> implements MessageDetailsOrBuilder {
        /* access modifiers changed from: private */
        public static final MessageDetails DEFAULT_INSTANCE = new MessageDetails();
        public static final int MAY_APPEAR_IN_FIELD_NUMBER = 1;
        private static volatile Parser<MessageDetails> PARSER;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Type> mayAppearIn_ = emptyProtobufList();
        private byte memoizedIsInitialized = 2;

        public interface TypeOrBuilder extends MessageLiteOrBuilder {
            String getLogType();

            ByteString getLogTypeBytes();

            String getSourceType();

            ByteString getSourceTypeBytes();

            boolean hasLogType();

            boolean hasSourceType();
        }

        private MessageDetails() {
        }

        @ProtoMessage(checkInitialized = {1, 2}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class Type extends GeneratedMessageLite<Type, Builder> implements TypeOrBuilder {
            /* access modifiers changed from: private */
            public static final Type DEFAULT_INSTANCE = new Type();
            public static final int LOG_TYPE_FIELD_NUMBER = 2;
            private static volatile Parser<Type> PARSER = null;
            public static final int SOURCE_TYPE_FIELD_NUMBER = 1;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = true, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private String logType_ = "";
            private byte memoizedIsInitialized = 2;
            @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = true, type = FieldType.STRING)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private String sourceType_ = "";

            private Type() {
            }

            public boolean hasSourceType() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getSourceType() {
                return this.sourceType_;
            }

            public ByteString getSourceTypeBytes() {
                return ByteString.copyFromUtf8(this.sourceType_);
            }

            /* access modifiers changed from: private */
            public void setSourceType(String value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.sourceType_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearSourceType() {
                this.bitField0_ &= -2;
                this.sourceType_ = getDefaultInstance().getSourceType();
            }

            /* access modifiers changed from: private */
            public void setSourceTypeBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.sourceType_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public boolean hasLogType() {
                return (this.bitField0_ & 2) != 0;
            }

            public String getLogType() {
                return this.logType_;
            }

            public ByteString getLogTypeBytes() {
                return ByteString.copyFromUtf8(this.logType_);
            }

            /* access modifiers changed from: private */
            public void setLogType(String value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.logType_ = value;
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearLogType() {
                this.bitField0_ &= -3;
                this.logType_ = getDefaultInstance().getLogType();
            }

            /* access modifiers changed from: private */
            public void setLogTypeBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.logType_ = value.toStringUtf8();
                    return;
                }
                throw new NullPointerException();
            }

            public static Type parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Type parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Type parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Type parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Type parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Type parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Type parseFrom(InputStream input) throws IOException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Type parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Type parseDelimitedFrom(InputStream input) throws IOException {
                return (Type) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static Type parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Type) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Type parseFrom(CodedInputStream input) throws IOException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Type parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(Type prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<Type, Builder> implements TypeOrBuilder {
                private Builder() {
                    super(Type.DEFAULT_INSTANCE);
                }

                public boolean hasSourceType() {
                    return ((Type) this.instance).hasSourceType();
                }

                public String getSourceType() {
                    return ((Type) this.instance).getSourceType();
                }

                public ByteString getSourceTypeBytes() {
                    return ((Type) this.instance).getSourceTypeBytes();
                }

                public Builder setSourceType(String value) {
                    copyOnWrite();
                    ((Type) this.instance).setSourceType(value);
                    return this;
                }

                public Builder clearSourceType() {
                    copyOnWrite();
                    ((Type) this.instance).clearSourceType();
                    return this;
                }

                public Builder setSourceTypeBytes(ByteString value) {
                    copyOnWrite();
                    ((Type) this.instance).setSourceTypeBytes(value);
                    return this;
                }

                public boolean hasLogType() {
                    return ((Type) this.instance).hasLogType();
                }

                public String getLogType() {
                    return ((Type) this.instance).getLogType();
                }

                public ByteString getLogTypeBytes() {
                    return ((Type) this.instance).getLogTypeBytes();
                }

                public Builder setLogType(String value) {
                    copyOnWrite();
                    ((Type) this.instance).setLogType(value);
                    return this;
                }

                public Builder clearLogType() {
                    copyOnWrite();
                    ((Type) this.instance).clearLogType();
                    return this;
                }

                public Builder setLogTypeBytes(ByteString value) {
                    copyOnWrite();
                    ((Type) this.instance).setLogTypeBytes(value);
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                int i = 1;
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new Type();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001Ԉ\u0000\u0002Ԉ\u0001", new Object[]{"bitField0_", "sourceType_", "logType_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<Type> parser = PARSER;
                        if (parser == null) {
                            synchronized (Type.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return Byte.valueOf(this.memoizedIsInitialized);
                    case SET_MEMOIZED_IS_INITIALIZED:
                        if (arg0 == null) {
                            i = 0;
                        }
                        this.memoizedIsInitialized = (byte) i;
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                GeneratedMessageLite.registerDefaultInstance(Type.class, DEFAULT_INSTANCE);
            }

            public static Type getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Type> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<Type> getMayAppearInList() {
            return this.mayAppearIn_;
        }

        public List<? extends TypeOrBuilder> getMayAppearInOrBuilderList() {
            return this.mayAppearIn_;
        }

        public int getMayAppearInCount() {
            return this.mayAppearIn_.size();
        }

        public Type getMayAppearIn(int index) {
            return this.mayAppearIn_.get(index);
        }

        public TypeOrBuilder getMayAppearInOrBuilder(int index) {
            return this.mayAppearIn_.get(index);
        }

        private void ensureMayAppearInIsMutable() {
            if (!this.mayAppearIn_.isModifiable()) {
                this.mayAppearIn_ = GeneratedMessageLite.mutableCopy(this.mayAppearIn_);
            }
        }

        /* access modifiers changed from: private */
        public void setMayAppearIn(int index, Type value) {
            if (value != null) {
                ensureMayAppearInIsMutable();
                this.mayAppearIn_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMayAppearIn(int index, Type.Builder builderForValue) {
            ensureMayAppearInIsMutable();
            this.mayAppearIn_.set(index, (Type) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addMayAppearIn(Type value) {
            if (value != null) {
                ensureMayAppearInIsMutable();
                this.mayAppearIn_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addMayAppearIn(int index, Type value) {
            if (value != null) {
                ensureMayAppearInIsMutable();
                this.mayAppearIn_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addMayAppearIn(Type.Builder builderForValue) {
            ensureMayAppearInIsMutable();
            this.mayAppearIn_.add((Type) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addMayAppearIn(int index, Type.Builder builderForValue) {
            ensureMayAppearInIsMutable();
            this.mayAppearIn_.add(index, (Type) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protos.logs_proto.LogsAnnotations$MessageDetails$Type>, com.google.protobuf.Internal$ProtobufList<com.google.protos.logs_proto.LogsAnnotations$MessageDetails$Type>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllMayAppearIn(Iterable<? extends Type> values) {
            ensureMayAppearInIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.mayAppearIn_);
        }

        /* access modifiers changed from: private */
        public void clearMayAppearIn() {
            this.mayAppearIn_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeMayAppearIn(int index) {
            ensureMayAppearInIsMutable();
            this.mayAppearIn_.remove(index);
        }

        public static MessageDetails parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MessageDetails parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MessageDetails parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MessageDetails parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MessageDetails parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MessageDetails parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MessageDetails parseFrom(InputStream input) throws IOException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MessageDetails parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MessageDetails parseDelimitedFrom(InputStream input) throws IOException {
            return (MessageDetails) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MessageDetails parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageDetails) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MessageDetails parseFrom(CodedInputStream input) throws IOException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MessageDetails parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MessageDetails prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<MessageDetails, Builder> implements MessageDetailsOrBuilder {
            private Builder() {
                super(MessageDetails.DEFAULT_INSTANCE);
            }

            public List<Type> getMayAppearInList() {
                return Collections.unmodifiableList(((MessageDetails) this.instance).getMayAppearInList());
            }

            public int getMayAppearInCount() {
                return ((MessageDetails) this.instance).getMayAppearInCount();
            }

            public Type getMayAppearIn(int index) {
                return ((MessageDetails) this.instance).getMayAppearIn(index);
            }

            public Builder setMayAppearIn(int index, Type value) {
                copyOnWrite();
                ((MessageDetails) this.instance).setMayAppearIn(index, value);
                return this;
            }

            public Builder setMayAppearIn(int index, Type.Builder builderForValue) {
                copyOnWrite();
                ((MessageDetails) this.instance).setMayAppearIn(index, builderForValue);
                return this;
            }

            public Builder addMayAppearIn(Type value) {
                copyOnWrite();
                ((MessageDetails) this.instance).addMayAppearIn(value);
                return this;
            }

            public Builder addMayAppearIn(int index, Type value) {
                copyOnWrite();
                ((MessageDetails) this.instance).addMayAppearIn(index, value);
                return this;
            }

            public Builder addMayAppearIn(Type.Builder builderForValue) {
                copyOnWrite();
                ((MessageDetails) this.instance).addMayAppearIn(builderForValue);
                return this;
            }

            public Builder addMayAppearIn(int index, Type.Builder builderForValue) {
                copyOnWrite();
                ((MessageDetails) this.instance).addMayAppearIn(index, builderForValue);
                return this;
            }

            public Builder addAllMayAppearIn(Iterable<? extends Type> values) {
                copyOnWrite();
                ((MessageDetails) this.instance).addAllMayAppearIn(values);
                return this;
            }

            public Builder clearMayAppearIn() {
                copyOnWrite();
                ((MessageDetails) this.instance).clearMayAppearIn();
                return this;
            }

            public Builder removeMayAppearIn(int index) {
                copyOnWrite();
                ((MessageDetails) this.instance).removeMayAppearIn(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            int i = 1;
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MessageDetails();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001Л", new Object[]{"mayAppearIn_", Type.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MessageDetails> parser = PARSER;
                    if (parser == null) {
                        synchronized (MessageDetails.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    if (arg0 == null) {
                        i = 0;
                    }
                    this.memoizedIsInitialized = (byte) i;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(MessageDetails.class, DEFAULT_INSTANCE);
        }

        public static MessageDetails getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MessageDetails> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
