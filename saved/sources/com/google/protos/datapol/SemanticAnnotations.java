package com.google.protos.datapol;

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
import com.google.protos.datapol.RetentionAnnotations;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public final class SemanticAnnotations {
    public static final int DATA_FORMAT_FIELD_NUMBER = 40221563;
    public static final int FIELD_DETAILS_FIELD_NUMBER = 40093572;
    public static final int FILE_VETTED_FOR_DATAPOL_ANNOTATIONS_FIELD_NUMBER = 43601160;
    public static final int FILE_VETTING_STATUS_FIELD_NUMBER = 71304954;
    public static final int LOCATION_QUALIFIER_FIELD_NUMBER = 69646961;
    public static final int MSG_DETAILS_FIELD_NUMBER = 41744383;
    public static final int MSG_LOCATION_QUALIFIER_FIELD_NUMBER = 69646961;
    public static final int MSG_QUALIFIER_FIELD_NUMBER = 41551199;
    public static final int MSG_RETENTION_FIELD_NUMBER = 41909987;
    public static final int MSG_SEMANTIC_TYPE_FIELD_NUMBER = 41149386;
    public static final int QUALIFIER_FIELD_NUMBER = 40270992;
    public static final int RETENTION_FIELD_NUMBER = 40223876;
    public static final int SEMANTIC_TYPE_FIELD_NUMBER = 40075780;
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, DataFormat> dataFormat = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), DataFormat.DF_NONE, null, DataFormat.internalGetValueMap(), DATA_FORMAT_FIELD_NUMBER, WireFormat.FieldType.ENUM, DataFormat.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, FieldDetails> fieldDetails = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), FieldDetails.getDefaultInstance(), FieldDetails.getDefaultInstance(), null, FIELD_DETAILS_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, FieldDetails.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, Boolean> fileVettedForDatapolAnnotations = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), false, null, null, FILE_VETTED_FOR_DATAPOL_ANNOTATIONS_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, String> fileVettingStatus = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), "", null, null, FILE_VETTING_STATUS_FIELD_NUMBER, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, LocationQualifier> locationQualifier = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), LocationQualifier.getDefaultInstance(), LocationQualifier.getDefaultInstance(), null, 69646961, WireFormat.FieldType.MESSAGE, LocationQualifier.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, MessageDetails> msgDetails = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), MessageDetails.getDefaultInstance(), MessageDetails.getDefaultInstance(), null, MSG_DETAILS_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, MessageDetails.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, LocationQualifier> msgLocationQualifier = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), LocationQualifier.getDefaultInstance(), LocationQualifier.getDefaultInstance(), null, 69646961, WireFormat.FieldType.MESSAGE, LocationQualifier.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, Qualifier> msgQualifier = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), Qualifier.getDefaultInstance(), Qualifier.getDefaultInstance(), null, MSG_QUALIFIER_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, Qualifier.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, List<RetentionAnnotations.RetentionSpec>> msgRetention = GeneratedMessageLite.newRepeatedGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), RetentionAnnotations.RetentionSpec.getDefaultInstance(), null, MSG_RETENTION_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, false, RetentionAnnotations.RetentionSpec.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, SemanticType> msgSemanticType = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), SemanticType.ST_NOT_SPECIFIED, null, SemanticType.internalGetValueMap(), MSG_SEMANTIC_TYPE_FIELD_NUMBER, WireFormat.FieldType.ENUM, SemanticType.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Qualifier> qualifier = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), Qualifier.getDefaultInstance(), Qualifier.getDefaultInstance(), null, QUALIFIER_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, Qualifier.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, List<RetentionAnnotations.RetentionSpec>> retention = GeneratedMessageLite.newRepeatedGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), RetentionAnnotations.RetentionSpec.getDefaultInstance(), null, RETENTION_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, false, RetentionAnnotations.RetentionSpec.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, SemanticType> semanticType = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), SemanticType.ST_NOT_SPECIFIED, null, SemanticType.internalGetValueMap(), SEMANTIC_TYPE_FIELD_NUMBER, WireFormat.FieldType.ENUM, SemanticType.class);

    public interface FieldDetailsOrBuilder extends MessageLiteOrBuilder {
        SemanticType getSemanticType(int i);

        int getSemanticTypeCount();

        List<SemanticType> getSemanticTypeList();
    }

    public interface LocationQualifierOrBuilder extends MessageLiteOrBuilder {
        boolean getNonUserLocation();

        boolean getPreciseLocation();

        boolean hasNonUserLocation();

        boolean hasPreciseLocation();
    }

    public interface MessageDetailsOrBuilder extends MessageLiteOrBuilder {
        SemanticType getSemanticType(int i);

        int getSemanticTypeCount();

        List<SemanticType> getSemanticTypeList();
    }

    public interface QualifierOrBuilder extends MessageLiteOrBuilder {
        boolean getAutoDeleteWithin180Days();

        boolean getAutoDeleteWithinWipeout();

        boolean getHasExplicitConsent();

        boolean getIsAccessTarget();

        boolean getIsEncrypted();

        boolean getIsGoogle();

        boolean getIsPartner();

        boolean getIsPublic();

        boolean getIsPublisher();

        boolean getLimitedAccess();

        boolean getNonUserLocation();

        boolean getOtherUser();

        int getRelatedField();

        boolean hasAutoDeleteWithin180Days();

        boolean hasAutoDeleteWithinWipeout();

        boolean hasHasExplicitConsent();

        boolean hasIsAccessTarget();

        boolean hasIsEncrypted();

        boolean hasIsGoogle();

        boolean hasIsPartner();

        boolean hasIsPublic();

        boolean hasIsPublisher();

        boolean hasLimitedAccess();

        boolean hasNonUserLocation();

        boolean hasOtherUser();

        boolean hasRelatedField();
    }

    private SemanticAnnotations() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) semanticType);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) qualifier);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) locationQualifier);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) fieldDetails);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) dataFormat);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) retention);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) msgSemanticType);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) msgQualifier);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) msgLocationQualifier);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) msgDetails);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) msgRetention);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) fileVettedForDatapolAnnotations);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) fileVettingStatus);
    }

    public enum SemanticType implements Internal.EnumLite {
        ST_NOT_SPECIFIED(0),
        ST_NOT_REQUIRED(999),
        ST_PSEUDONYMOUS_ID(1000),
        ST_ZWIEBACK_ID(1001),
        ST_PREF_ID(1002),
        ST_BISCOTTI_ID(1003),
        ST_ANALYTICS_ID(1004),
        ST_MANDELBREAD_ID(1005),
        ST_ANDROID_LOGGING_ID2(1006),
        ST_ACSA_ID(1007),
        ST_HERREVAD_ID(1008),
        ST_IDENTIFYING_ID(ST_IDENTIFYING_ID_VALUE),
        ST_EMAIL_ID(ST_EMAIL_ID_VALUE),
        ST_NAME(ST_NAME_VALUE),
        ST_PHONE_NUMBER(ST_PHONE_NUMBER_VALUE),
        ST_GAIA_ID(ST_GAIA_ID_VALUE),
        ST_USERNAME(ST_USERNAME_VALUE),
        ST_GSERVICES_ANDROID_ID(ST_GSERVICES_ANDROID_ID_VALUE),
        ST_ARES_ID(ST_ARES_ID_VALUE),
        ST_MEDICAL_RECORD_NUMBER(ST_MEDICAL_RECORD_NUMBER_VALUE),
        ST_PAYMENTS_RISK_DATA(ST_PAYMENTS_RISK_DATA_VALUE),
        ST_SPII_ID(ST_SPII_ID_VALUE),
        ST_GOVERNMENT_ID_NUMBER(ST_GOVERNMENT_ID_NUMBER_VALUE),
        ST_HEALTHCARE_INFO(ST_HEALTHCARE_INFO_VALUE),
        ST_SENSITIVE_BACKGROUND_INFO(ST_SENSITIVE_BACKGROUND_INFO_VALUE),
        ST_CARDHOLDER_DATA(ST_CARDHOLDER_DATA_VALUE),
        ST_CHD_PAN(ST_CHD_PAN_VALUE),
        ST_CHD_INFO(ST_CHD_INFO_VALUE),
        ST_PAYMENTS_INFO(ST_PAYMENTS_INFO_VALUE),
        ST_CRITICAL_PAYMENT_INFO(ST_CRITICAL_PAYMENT_INFO_VALUE),
        ST_PAYMENT_ID(ST_PAYMENT_ID_VALUE),
        ST_LIMITED_USE_PAYMENTS_INFO(ST_LIMITED_USE_PAYMENTS_INFO_VALUE),
        ST_PAYMENTS_INFERRED_REAL_IDENTITY(ST_PAYMENTS_INFERRED_REAL_IDENTITY_VALUE),
        ST_PAYMENTS_TRANSACTION_INFO(ST_PAYMENTS_TRANSACTION_INFO_VALUE),
        ST_NETWORK_ENDPOINT(ST_NETWORK_ENDPOINT_VALUE),
        ST_IP_ADDRESS(ST_IP_ADDRESS_VALUE),
        ST_HARDWARE_ID(ST_HARDWARE_ID_VALUE),
        ST_ANDROID_LOGGING_ID(ST_ANDROID_LOGGING_ID_VALUE),
        ST_SOFTWARE_ID(1500),
        ST_USER_AGENT(ST_USER_AGENT_VALUE),
        ST_ANONYMOUS_DATA(ST_ANONYMOUS_DATA_VALUE),
        ST_DEMOGRAPHIC_INFO(ST_DEMOGRAPHIC_INFO_VALUE),
        ST_LOCATION(ST_LOCATION_VALUE),
        ST_PRECISE_LOCATION(ST_PRECISE_LOCATION_VALUE),
        ST_COARSE_LOCATION(ST_COARSE_LOCATION_VALUE),
        ST_GOOGLE_RELATIONSHIP_ID(ST_GOOGLE_RELATIONSHIP_ID_VALUE),
        ST_CUSTOMER_ID(ST_CUSTOMER_ID_VALUE),
        ST_PARTNER_ID(ST_PARTNER_ID_VALUE),
        ST_PUBLISHER_ID(ST_PUBLISHER_ID_VALUE),
        ST_USER_CONTENT(ST_USER_CONTENT_VALUE),
        ST_USER_QUERY(ST_USER_QUERY_VALUE),
        ST_THIRD_PARTY_DATA(2000),
        ST_TIMESTAMP(ST_TIMESTAMP_VALUE),
        ST_SENSITIVE_TIMESTAMP(ST_SENSITIVE_TIMESTAMP_VALUE),
        ST_SESSION_ID(ST_SESSION_ID_VALUE),
        ST_PERSONAL_DATA(ST_PERSONAL_DATA_VALUE),
        ST_AVOCADO_ID(2500),
        ST_SECURITY_MATERIAL(ST_SECURITY_MATERIAL_VALUE),
        ST_SECURITY_KEY(ST_SECURITY_KEY_VALUE),
        ST_ACCOUNT_CREDENTIAL(ST_ACCOUNT_CREDENTIAL_VALUE),
        ST_PAYMENTS_PCI_SAD(ST_PAYMENTS_PCI_SAD_VALUE),
        ST_CONTENT_DEPENDENT(ST_CONTENT_DEPENDENT_VALUE),
        ST_DEBUG_INFO(ST_DEBUG_INFO_VALUE),
        ST_KEY_VALUE_PAIR(ST_KEY_VALUE_PAIR_VALUE),
        ST_KEY(ST_KEY_VALUE),
        ST_VALUE(ST_VALUE_VALUE),
        ST_REFERER_URL(ST_REFERER_URL_VALUE);
        
        public static final int ST_ACCOUNT_CREDENTIAL_VALUE = 2602;
        public static final int ST_ACSA_ID_VALUE = 1007;
        public static final int ST_ANALYTICS_ID_VALUE = 1004;
        public static final int ST_ANDROID_LOGGING_ID2_VALUE = 1006;
        public static final int ST_ANDROID_LOGGING_ID_VALUE = 1401;
        public static final int ST_ANONYMOUS_DATA_VALUE = 1600;
        public static final int ST_ARES_ID_VALUE = 1108;
        public static final int ST_AVOCADO_ID_VALUE = 2500;
        public static final int ST_BISCOTTI_ID_VALUE = 1003;
        public static final int ST_CARDHOLDER_DATA_VALUE = 1202;
        public static final int ST_CHD_INFO_VALUE = 1212;
        public static final int ST_CHD_PAN_VALUE = 1211;
        public static final int ST_COARSE_LOCATION_VALUE = 1702;
        public static final int ST_CONTENT_DEPENDENT_VALUE = 9900;
        public static final int ST_CRITICAL_PAYMENT_INFO_VALUE = 1221;
        public static final int ST_CUSTOMER_ID_VALUE = 1801;
        public static final int ST_DEBUG_INFO_VALUE = 9901;
        public static final int ST_DEMOGRAPHIC_INFO_VALUE = 1601;
        public static final int ST_EMAIL_ID_VALUE = 1102;
        public static final int ST_GAIA_ID_VALUE = 1105;
        public static final int ST_GOOGLE_RELATIONSHIP_ID_VALUE = 1800;
        public static final int ST_GOVERNMENT_ID_NUMBER_VALUE = 1201;
        public static final int ST_GSERVICES_ANDROID_ID_VALUE = 1107;
        public static final int ST_HARDWARE_ID_VALUE = 1400;
        public static final int ST_HEALTHCARE_INFO_VALUE = 1203;
        public static final int ST_HERREVAD_ID_VALUE = 1008;
        public static final int ST_IDENTIFYING_ID_VALUE = 1100;
        public static final int ST_IP_ADDRESS_VALUE = 1301;
        public static final int ST_KEY_VALUE = 9903;
        public static final int ST_KEY_VALUE_PAIR_VALUE = 9902;
        public static final int ST_LIMITED_USE_PAYMENTS_INFO_VALUE = 1223;
        public static final int ST_LOCATION_VALUE = 1700;
        public static final int ST_MANDELBREAD_ID_VALUE = 1005;
        public static final int ST_MEDICAL_RECORD_NUMBER_VALUE = 1109;
        public static final int ST_NAME_VALUE = 1103;
        public static final int ST_NETWORK_ENDPOINT_VALUE = 1300;
        public static final int ST_NOT_REQUIRED_VALUE = 999;
        public static final int ST_NOT_SPECIFIED_VALUE = 0;
        public static final int ST_PARTNER_ID_VALUE = 1802;
        public static final int ST_PAYMENTS_INFERRED_REAL_IDENTITY_VALUE = 1224;
        public static final int ST_PAYMENTS_INFO_VALUE = 1220;
        public static final int ST_PAYMENTS_PCI_SAD_VALUE = 2603;
        public static final int ST_PAYMENTS_RISK_DATA_VALUE = 1110;
        public static final int ST_PAYMENTS_TRANSACTION_INFO_VALUE = 1240;
        public static final int ST_PAYMENT_ID_VALUE = 1222;
        public static final int ST_PERSONAL_DATA_VALUE = 2400;
        public static final int ST_PHONE_NUMBER_VALUE = 1104;
        public static final int ST_PRECISE_LOCATION_VALUE = 1701;
        public static final int ST_PREF_ID_VALUE = 1002;
        public static final int ST_PSEUDONYMOUS_ID_VALUE = 1000;
        public static final int ST_PUBLISHER_ID_VALUE = 1803;
        public static final int ST_REFERER_URL_VALUE = 9905;
        public static final int ST_SECURITY_KEY_VALUE = 2601;
        public static final int ST_SECURITY_MATERIAL_VALUE = 2600;
        public static final int ST_SENSITIVE_BACKGROUND_INFO_VALUE = 1204;
        public static final int ST_SENSITIVE_TIMESTAMP_VALUE = 2101;
        public static final int ST_SESSION_ID_VALUE = 2300;
        public static final int ST_SOFTWARE_ID_VALUE = 1500;
        public static final int ST_SPII_ID_VALUE = 1200;
        public static final int ST_THIRD_PARTY_DATA_VALUE = 2000;
        public static final int ST_TIMESTAMP_VALUE = 2100;
        public static final int ST_USERNAME_VALUE = 1106;
        public static final int ST_USER_AGENT_VALUE = 1501;
        public static final int ST_USER_CONTENT_VALUE = 1900;
        public static final int ST_USER_QUERY_VALUE = 1901;
        public static final int ST_VALUE_VALUE = 9904;
        public static final int ST_ZWIEBACK_ID_VALUE = 1001;
        private static final Internal.EnumLiteMap<SemanticType> internalValueMap = new Internal.EnumLiteMap<SemanticType>() {
            public SemanticType findValueByNumber(int number) {
                return SemanticType.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static SemanticType forNumber(int value2) {
            if (value2 != 0) {
                if (value2 == 1211) {
                    return ST_CHD_PAN;
                }
                if (value2 == 1212) {
                    return ST_CHD_INFO;
                }
                if (value2 == 1300) {
                    return ST_NETWORK_ENDPOINT;
                }
                if (value2 == 1301) {
                    return ST_IP_ADDRESS;
                }
                if (value2 == 1400) {
                    return ST_HARDWARE_ID;
                }
                if (value2 == 1401) {
                    return ST_ANDROID_LOGGING_ID;
                }
                if (value2 == 1500) {
                    return ST_SOFTWARE_ID;
                }
                if (value2 == 1501) {
                    return ST_USER_AGENT;
                }
                if (value2 == 1600) {
                    return ST_ANONYMOUS_DATA;
                }
                if (value2 == 1601) {
                    return ST_DEMOGRAPHIC_INFO;
                }
                switch (value2) {
                    case 0:
                        break;
                    case 999:
                        return ST_NOT_REQUIRED;
                    case 1000:
                        return ST_PSEUDONYMOUS_ID;
                    case 1001:
                        return ST_ZWIEBACK_ID;
                    case 1002:
                        return ST_PREF_ID;
                    case 1003:
                        return ST_BISCOTTI_ID;
                    case 1004:
                        return ST_ANALYTICS_ID;
                    case 1005:
                        return ST_MANDELBREAD_ID;
                    case 1006:
                        return ST_ANDROID_LOGGING_ID2;
                    case 1007:
                        return ST_ACSA_ID;
                    case 1008:
                        return ST_HERREVAD_ID;
                    case ST_IDENTIFYING_ID_VALUE:
                        return ST_IDENTIFYING_ID;
                    case ST_PAYMENTS_TRANSACTION_INFO_VALUE:
                        return ST_PAYMENTS_TRANSACTION_INFO;
                    case ST_GOOGLE_RELATIONSHIP_ID_VALUE:
                        return ST_GOOGLE_RELATIONSHIP_ID;
                    case ST_CUSTOMER_ID_VALUE:
                        return ST_CUSTOMER_ID;
                    case ST_PARTNER_ID_VALUE:
                        return ST_PARTNER_ID;
                    case ST_PUBLISHER_ID_VALUE:
                        return ST_PUBLISHER_ID;
                    case ST_USER_CONTENT_VALUE:
                        return ST_USER_CONTENT;
                    case ST_USER_QUERY_VALUE:
                        return ST_USER_QUERY;
                    case 2000:
                        return ST_THIRD_PARTY_DATA;
                    case ST_TIMESTAMP_VALUE:
                        return ST_TIMESTAMP;
                    case ST_SENSITIVE_TIMESTAMP_VALUE:
                        return ST_SENSITIVE_TIMESTAMP;
                    case ST_SESSION_ID_VALUE:
                        return ST_SESSION_ID;
                    case ST_PERSONAL_DATA_VALUE:
                        return ST_PERSONAL_DATA;
                    case 2500:
                        return ST_AVOCADO_ID;
                    case ST_SECURITY_MATERIAL_VALUE:
                        return ST_SECURITY_MATERIAL;
                    case ST_SECURITY_KEY_VALUE:
                        return ST_SECURITY_KEY;
                    case ST_ACCOUNT_CREDENTIAL_VALUE:
                        return ST_ACCOUNT_CREDENTIAL;
                    case ST_PAYMENTS_PCI_SAD_VALUE:
                        return ST_PAYMENTS_PCI_SAD;
                    case ST_CONTENT_DEPENDENT_VALUE:
                        return ST_CONTENT_DEPENDENT;
                    case ST_DEBUG_INFO_VALUE:
                        return ST_DEBUG_INFO;
                    case ST_KEY_VALUE_PAIR_VALUE:
                        return ST_KEY_VALUE_PAIR;
                    case ST_KEY_VALUE:
                        return ST_KEY;
                    case ST_VALUE_VALUE:
                        return ST_VALUE;
                    case ST_REFERER_URL_VALUE:
                        return ST_REFERER_URL;
                    default:
                        switch (value2) {
                            case ST_EMAIL_ID_VALUE:
                                return ST_EMAIL_ID;
                            case ST_NAME_VALUE:
                                return ST_NAME;
                            case ST_PHONE_NUMBER_VALUE:
                                return ST_PHONE_NUMBER;
                            case ST_GAIA_ID_VALUE:
                                return ST_GAIA_ID;
                            case ST_USERNAME_VALUE:
                                return ST_USERNAME;
                            case ST_GSERVICES_ANDROID_ID_VALUE:
                                return ST_GSERVICES_ANDROID_ID;
                            case ST_ARES_ID_VALUE:
                                return ST_ARES_ID;
                            case ST_MEDICAL_RECORD_NUMBER_VALUE:
                                return ST_MEDICAL_RECORD_NUMBER;
                            case ST_PAYMENTS_RISK_DATA_VALUE:
                                return ST_PAYMENTS_RISK_DATA;
                            default:
                                switch (value2) {
                                    case ST_SPII_ID_VALUE:
                                        return ST_SPII_ID;
                                    case ST_GOVERNMENT_ID_NUMBER_VALUE:
                                        return ST_GOVERNMENT_ID_NUMBER;
                                    case ST_CARDHOLDER_DATA_VALUE:
                                        return ST_CARDHOLDER_DATA;
                                    case ST_HEALTHCARE_INFO_VALUE:
                                        return ST_HEALTHCARE_INFO;
                                    case ST_SENSITIVE_BACKGROUND_INFO_VALUE:
                                        return ST_SENSITIVE_BACKGROUND_INFO;
                                    default:
                                        switch (value2) {
                                            case ST_PAYMENTS_INFO_VALUE:
                                                return ST_PAYMENTS_INFO;
                                            case ST_CRITICAL_PAYMENT_INFO_VALUE:
                                                return ST_CRITICAL_PAYMENT_INFO;
                                            case ST_PAYMENT_ID_VALUE:
                                                return ST_PAYMENT_ID;
                                            case ST_LIMITED_USE_PAYMENTS_INFO_VALUE:
                                                return ST_LIMITED_USE_PAYMENTS_INFO;
                                            case ST_PAYMENTS_INFERRED_REAL_IDENTITY_VALUE:
                                                return ST_PAYMENTS_INFERRED_REAL_IDENTITY;
                                            default:
                                                switch (value2) {
                                                    case ST_LOCATION_VALUE:
                                                        return ST_LOCATION;
                                                    case ST_PRECISE_LOCATION_VALUE:
                                                        return ST_PRECISE_LOCATION;
                                                    case ST_COARSE_LOCATION_VALUE:
                                                        return ST_COARSE_LOCATION;
                                                    default:
                                                        return null;
                                                }
                                        }
                                }
                        }
                }
            }
            return ST_NOT_SPECIFIED;
        }

        public static Internal.EnumLiteMap<SemanticType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return SemanticTypeVerifier.INSTANCE;
        }

        private static final class SemanticTypeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new SemanticTypeVerifier();

            private SemanticTypeVerifier() {
            }

            public boolean isInRange(int number) {
                return SemanticType.forNumber(number) != null;
            }
        }

        private SemanticType(int value2) {
            this.value = value2;
        }
    }

    public enum DataFormat implements Internal.EnumLite {
        DF_NONE(0),
        DF_HTTPHEADER(1),
        DF_COOKIE(2),
        DF_URL(3),
        DF_CGI_ARGS(4),
        DF_HOST_ORDER(5),
        DF_BYTE_SWAPPED(6),
        DF_LOGGING_ELEMENT_TYPE_ID(7);
        
        public static final int DF_BYTE_SWAPPED_VALUE = 6;
        public static final int DF_CGI_ARGS_VALUE = 4;
        public static final int DF_COOKIE_VALUE = 2;
        public static final int DF_HOST_ORDER_VALUE = 5;
        public static final int DF_HTTPHEADER_VALUE = 1;
        public static final int DF_LOGGING_ELEMENT_TYPE_ID_VALUE = 7;
        public static final int DF_NONE_VALUE = 0;
        public static final int DF_URL_VALUE = 3;
        private static final Internal.EnumLiteMap<DataFormat> internalValueMap = new Internal.EnumLiteMap<DataFormat>() {
            public DataFormat findValueByNumber(int number) {
                return DataFormat.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static DataFormat forNumber(int value2) {
            switch (value2) {
                case 0:
                    return DF_NONE;
                case 1:
                    return DF_HTTPHEADER;
                case 2:
                    return DF_COOKIE;
                case 3:
                    return DF_URL;
                case 4:
                    return DF_CGI_ARGS;
                case 5:
                    return DF_HOST_ORDER;
                case 6:
                    return DF_BYTE_SWAPPED;
                case 7:
                    return DF_LOGGING_ELEMENT_TYPE_ID;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<DataFormat> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return DataFormatVerifier.INSTANCE;
        }

        private static final class DataFormatVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new DataFormatVerifier();

            private DataFormatVerifier() {
            }

            public boolean isInRange(int number) {
                return DataFormat.forNumber(number) != null;
            }
        }

        private DataFormat(int value2) {
            this.value = value2;
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Qualifier extends GeneratedMessageLite<Qualifier, Builder> implements QualifierOrBuilder {
        public static final int AUTO_DELETE_WITHIN_180_DAYS_FIELD_NUMBER = 13;
        public static final int AUTO_DELETE_WITHIN_WIPEOUT_FIELD_NUMBER = 11;
        /* access modifiers changed from: private */
        public static final Qualifier DEFAULT_INSTANCE = new Qualifier();
        public static final int HAS_EXPLICIT_CONSENT_FIELD_NUMBER = 6;
        public static final int IS_ACCESS_TARGET_FIELD_NUMBER = 12;
        public static final int IS_ENCRYPTED_FIELD_NUMBER = 7;
        public static final int IS_GOOGLE_FIELD_NUMBER = 2;
        public static final int IS_PARTNER_FIELD_NUMBER = 4;
        public static final int IS_PUBLIC_FIELD_NUMBER = 1;
        public static final int IS_PUBLISHER_FIELD_NUMBER = 5;
        public static final int LIMITED_ACCESS_FIELD_NUMBER = 10;
        public static final int NON_USER_LOCATION_FIELD_NUMBER = 9;
        public static final int OTHER_USER_FIELD_NUMBER = 3;
        private static volatile Parser<Qualifier> PARSER = null;
        public static final int RELATED_FIELD_FIELD_NUMBER = 8;
        @ProtoField(fieldNumber = 13, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private boolean autoDeleteWithin180Days_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private boolean autoDeleteWithinWipeout_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private boolean hasExplicitConsent_;
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4096, presenceBitsId = 0)
        private boolean isAccessTarget_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private boolean isEncrypted_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean isGoogle_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private boolean isPartner_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean isPublic_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private boolean isPublisher_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private boolean limitedAccess_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private boolean nonUserLocation_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean otherUser_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int relatedField_;

        private Qualifier() {
        }

        public boolean hasIsPublic() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getIsPublic() {
            return this.isPublic_;
        }

        /* access modifiers changed from: private */
        public void setIsPublic(boolean value) {
            this.bitField0_ |= 1;
            this.isPublic_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsPublic() {
            this.bitField0_ &= -2;
            this.isPublic_ = false;
        }

        public boolean hasIsGoogle() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getIsGoogle() {
            return this.isGoogle_;
        }

        /* access modifiers changed from: private */
        public void setIsGoogle(boolean value) {
            this.bitField0_ |= 2;
            this.isGoogle_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsGoogle() {
            this.bitField0_ &= -3;
            this.isGoogle_ = false;
        }

        public boolean hasOtherUser() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getOtherUser() {
            return this.otherUser_;
        }

        /* access modifiers changed from: private */
        public void setOtherUser(boolean value) {
            this.bitField0_ |= 4;
            this.otherUser_ = value;
        }

        /* access modifiers changed from: private */
        public void clearOtherUser() {
            this.bitField0_ &= -5;
            this.otherUser_ = false;
        }

        public boolean hasIsPartner() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getIsPartner() {
            return this.isPartner_;
        }

        /* access modifiers changed from: private */
        public void setIsPartner(boolean value) {
            this.bitField0_ |= 8;
            this.isPartner_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsPartner() {
            this.bitField0_ &= -9;
            this.isPartner_ = false;
        }

        public boolean hasIsPublisher() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getIsPublisher() {
            return this.isPublisher_;
        }

        /* access modifiers changed from: private */
        public void setIsPublisher(boolean value) {
            this.bitField0_ |= 16;
            this.isPublisher_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsPublisher() {
            this.bitField0_ &= -17;
            this.isPublisher_ = false;
        }

        public boolean hasHasExplicitConsent() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getHasExplicitConsent() {
            return this.hasExplicitConsent_;
        }

        /* access modifiers changed from: private */
        public void setHasExplicitConsent(boolean value) {
            this.bitField0_ |= 32;
            this.hasExplicitConsent_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasExplicitConsent() {
            this.bitField0_ &= -33;
            this.hasExplicitConsent_ = false;
        }

        public boolean hasIsEncrypted() {
            return (this.bitField0_ & 64) != 0;
        }

        public boolean getIsEncrypted() {
            return this.isEncrypted_;
        }

        /* access modifiers changed from: private */
        public void setIsEncrypted(boolean value) {
            this.bitField0_ |= 64;
            this.isEncrypted_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsEncrypted() {
            this.bitField0_ &= -65;
            this.isEncrypted_ = false;
        }

        public boolean hasRelatedField() {
            return (this.bitField0_ & 128) != 0;
        }

        public int getRelatedField() {
            return this.relatedField_;
        }

        /* access modifiers changed from: private */
        public void setRelatedField(int value) {
            this.bitField0_ |= 128;
            this.relatedField_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRelatedField() {
            this.bitField0_ &= -129;
            this.relatedField_ = 0;
        }

        public boolean hasNonUserLocation() {
            return (this.bitField0_ & 256) != 0;
        }

        public boolean getNonUserLocation() {
            return this.nonUserLocation_;
        }

        /* access modifiers changed from: private */
        public void setNonUserLocation(boolean value) {
            this.bitField0_ |= 256;
            this.nonUserLocation_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNonUserLocation() {
            this.bitField0_ &= -257;
            this.nonUserLocation_ = false;
        }

        public boolean hasLimitedAccess() {
            return (this.bitField0_ & 512) != 0;
        }

        public boolean getLimitedAccess() {
            return this.limitedAccess_;
        }

        /* access modifiers changed from: private */
        public void setLimitedAccess(boolean value) {
            this.bitField0_ |= 512;
            this.limitedAccess_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLimitedAccess() {
            this.bitField0_ &= -513;
            this.limitedAccess_ = false;
        }

        public boolean hasAutoDeleteWithinWipeout() {
            return (this.bitField0_ & 1024) != 0;
        }

        public boolean getAutoDeleteWithinWipeout() {
            return this.autoDeleteWithinWipeout_;
        }

        /* access modifiers changed from: private */
        public void setAutoDeleteWithinWipeout(boolean value) {
            this.bitField0_ |= 1024;
            this.autoDeleteWithinWipeout_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAutoDeleteWithinWipeout() {
            this.bitField0_ &= -1025;
            this.autoDeleteWithinWipeout_ = false;
        }

        public boolean hasAutoDeleteWithin180Days() {
            return (this.bitField0_ & 2048) != 0;
        }

        public boolean getAutoDeleteWithin180Days() {
            return this.autoDeleteWithin180Days_;
        }

        /* access modifiers changed from: private */
        public void setAutoDeleteWithin180Days(boolean value) {
            this.bitField0_ |= 2048;
            this.autoDeleteWithin180Days_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAutoDeleteWithin180Days() {
            this.bitField0_ &= -2049;
            this.autoDeleteWithin180Days_ = false;
        }

        public boolean hasIsAccessTarget() {
            return (this.bitField0_ & 4096) != 0;
        }

        public boolean getIsAccessTarget() {
            return this.isAccessTarget_;
        }

        /* access modifiers changed from: private */
        public void setIsAccessTarget(boolean value) {
            this.bitField0_ |= 4096;
            this.isAccessTarget_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsAccessTarget() {
            this.bitField0_ &= -4097;
            this.isAccessTarget_ = false;
        }

        public static Qualifier parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Qualifier parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Qualifier parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Qualifier parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Qualifier parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Qualifier parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Qualifier parseFrom(InputStream input) throws IOException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Qualifier parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Qualifier parseDelimitedFrom(InputStream input) throws IOException {
            return (Qualifier) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Qualifier parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Qualifier) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Qualifier parseFrom(CodedInputStream input) throws IOException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Qualifier parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Qualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Qualifier prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Qualifier, Builder> implements QualifierOrBuilder {
            private Builder() {
                super(Qualifier.DEFAULT_INSTANCE);
            }

            public boolean hasIsPublic() {
                return ((Qualifier) this.instance).hasIsPublic();
            }

            public boolean getIsPublic() {
                return ((Qualifier) this.instance).getIsPublic();
            }

            public Builder setIsPublic(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setIsPublic(value);
                return this;
            }

            public Builder clearIsPublic() {
                copyOnWrite();
                ((Qualifier) this.instance).clearIsPublic();
                return this;
            }

            public boolean hasIsGoogle() {
                return ((Qualifier) this.instance).hasIsGoogle();
            }

            public boolean getIsGoogle() {
                return ((Qualifier) this.instance).getIsGoogle();
            }

            public Builder setIsGoogle(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setIsGoogle(value);
                return this;
            }

            public Builder clearIsGoogle() {
                copyOnWrite();
                ((Qualifier) this.instance).clearIsGoogle();
                return this;
            }

            public boolean hasOtherUser() {
                return ((Qualifier) this.instance).hasOtherUser();
            }

            public boolean getOtherUser() {
                return ((Qualifier) this.instance).getOtherUser();
            }

            public Builder setOtherUser(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setOtherUser(value);
                return this;
            }

            public Builder clearOtherUser() {
                copyOnWrite();
                ((Qualifier) this.instance).clearOtherUser();
                return this;
            }

            public boolean hasIsPartner() {
                return ((Qualifier) this.instance).hasIsPartner();
            }

            public boolean getIsPartner() {
                return ((Qualifier) this.instance).getIsPartner();
            }

            public Builder setIsPartner(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setIsPartner(value);
                return this;
            }

            public Builder clearIsPartner() {
                copyOnWrite();
                ((Qualifier) this.instance).clearIsPartner();
                return this;
            }

            public boolean hasIsPublisher() {
                return ((Qualifier) this.instance).hasIsPublisher();
            }

            public boolean getIsPublisher() {
                return ((Qualifier) this.instance).getIsPublisher();
            }

            public Builder setIsPublisher(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setIsPublisher(value);
                return this;
            }

            public Builder clearIsPublisher() {
                copyOnWrite();
                ((Qualifier) this.instance).clearIsPublisher();
                return this;
            }

            public boolean hasHasExplicitConsent() {
                return ((Qualifier) this.instance).hasHasExplicitConsent();
            }

            public boolean getHasExplicitConsent() {
                return ((Qualifier) this.instance).getHasExplicitConsent();
            }

            public Builder setHasExplicitConsent(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setHasExplicitConsent(value);
                return this;
            }

            public Builder clearHasExplicitConsent() {
                copyOnWrite();
                ((Qualifier) this.instance).clearHasExplicitConsent();
                return this;
            }

            public boolean hasIsEncrypted() {
                return ((Qualifier) this.instance).hasIsEncrypted();
            }

            public boolean getIsEncrypted() {
                return ((Qualifier) this.instance).getIsEncrypted();
            }

            public Builder setIsEncrypted(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setIsEncrypted(value);
                return this;
            }

            public Builder clearIsEncrypted() {
                copyOnWrite();
                ((Qualifier) this.instance).clearIsEncrypted();
                return this;
            }

            public boolean hasRelatedField() {
                return ((Qualifier) this.instance).hasRelatedField();
            }

            public int getRelatedField() {
                return ((Qualifier) this.instance).getRelatedField();
            }

            public Builder setRelatedField(int value) {
                copyOnWrite();
                ((Qualifier) this.instance).setRelatedField(value);
                return this;
            }

            public Builder clearRelatedField() {
                copyOnWrite();
                ((Qualifier) this.instance).clearRelatedField();
                return this;
            }

            public boolean hasNonUserLocation() {
                return ((Qualifier) this.instance).hasNonUserLocation();
            }

            public boolean getNonUserLocation() {
                return ((Qualifier) this.instance).getNonUserLocation();
            }

            public Builder setNonUserLocation(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setNonUserLocation(value);
                return this;
            }

            public Builder clearNonUserLocation() {
                copyOnWrite();
                ((Qualifier) this.instance).clearNonUserLocation();
                return this;
            }

            public boolean hasLimitedAccess() {
                return ((Qualifier) this.instance).hasLimitedAccess();
            }

            public boolean getLimitedAccess() {
                return ((Qualifier) this.instance).getLimitedAccess();
            }

            public Builder setLimitedAccess(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setLimitedAccess(value);
                return this;
            }

            public Builder clearLimitedAccess() {
                copyOnWrite();
                ((Qualifier) this.instance).clearLimitedAccess();
                return this;
            }

            public boolean hasAutoDeleteWithinWipeout() {
                return ((Qualifier) this.instance).hasAutoDeleteWithinWipeout();
            }

            public boolean getAutoDeleteWithinWipeout() {
                return ((Qualifier) this.instance).getAutoDeleteWithinWipeout();
            }

            public Builder setAutoDeleteWithinWipeout(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setAutoDeleteWithinWipeout(value);
                return this;
            }

            public Builder clearAutoDeleteWithinWipeout() {
                copyOnWrite();
                ((Qualifier) this.instance).clearAutoDeleteWithinWipeout();
                return this;
            }

            public boolean hasAutoDeleteWithin180Days() {
                return ((Qualifier) this.instance).hasAutoDeleteWithin180Days();
            }

            public boolean getAutoDeleteWithin180Days() {
                return ((Qualifier) this.instance).getAutoDeleteWithin180Days();
            }

            public Builder setAutoDeleteWithin180Days(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setAutoDeleteWithin180Days(value);
                return this;
            }

            public Builder clearAutoDeleteWithin180Days() {
                copyOnWrite();
                ((Qualifier) this.instance).clearAutoDeleteWithin180Days();
                return this;
            }

            public boolean hasIsAccessTarget() {
                return ((Qualifier) this.instance).hasIsAccessTarget();
            }

            public boolean getIsAccessTarget() {
                return ((Qualifier) this.instance).getIsAccessTarget();
            }

            public Builder setIsAccessTarget(boolean value) {
                copyOnWrite();
                ((Qualifier) this.instance).setIsAccessTarget(value);
                return this;
            }

            public Builder clearIsAccessTarget() {
                copyOnWrite();
                ((Qualifier) this.instance).clearIsAccessTarget();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Qualifier();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\r\u0000\u0001\u0001\r\r\u0000\u0000\u0000\u0001\u0007\u0000\u0002\u0007\u0001\u0003\u0007\u0002\u0004\u0007\u0003\u0005\u0007\u0004\u0006\u0007\u0005\u0007\u0007\u0006\b\u0004\u0007\t\u0007\b\n\u0007\t\u000b\u0007\n\f\u0007\f\r\u0007\u000b", new Object[]{"bitField0_", "isPublic_", "isGoogle_", "otherUser_", "isPartner_", "isPublisher_", "hasExplicitConsent_", "isEncrypted_", "relatedField_", "nonUserLocation_", "limitedAccess_", "autoDeleteWithinWipeout_", "isAccessTarget_", "autoDeleteWithin180Days_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Qualifier> parser = PARSER;
                    if (parser == null) {
                        synchronized (Qualifier.class) {
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
            GeneratedMessageLite.registerDefaultInstance(Qualifier.class, DEFAULT_INSTANCE);
        }

        public static Qualifier getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Qualifier> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class LocationQualifier extends GeneratedMessageLite<LocationQualifier, Builder> implements LocationQualifierOrBuilder {
        /* access modifiers changed from: private */
        public static final LocationQualifier DEFAULT_INSTANCE = new LocationQualifier();
        public static final int NON_USER_LOCATION_FIELD_NUMBER = 1;
        private static volatile Parser<LocationQualifier> PARSER = null;
        public static final int PRECISE_LOCATION_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean nonUserLocation_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean preciseLocation_;

        private LocationQualifier() {
        }

        public boolean hasNonUserLocation() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getNonUserLocation() {
            return this.nonUserLocation_;
        }

        /* access modifiers changed from: private */
        public void setNonUserLocation(boolean value) {
            this.bitField0_ |= 1;
            this.nonUserLocation_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNonUserLocation() {
            this.bitField0_ &= -2;
            this.nonUserLocation_ = false;
        }

        public boolean hasPreciseLocation() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getPreciseLocation() {
            return this.preciseLocation_;
        }

        /* access modifiers changed from: private */
        public void setPreciseLocation(boolean value) {
            this.bitField0_ |= 2;
            this.preciseLocation_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPreciseLocation() {
            this.bitField0_ &= -3;
            this.preciseLocation_ = false;
        }

        public static LocationQualifier parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static LocationQualifier parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static LocationQualifier parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static LocationQualifier parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static LocationQualifier parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static LocationQualifier parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static LocationQualifier parseFrom(InputStream input) throws IOException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static LocationQualifier parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static LocationQualifier parseDelimitedFrom(InputStream input) throws IOException {
            return (LocationQualifier) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static LocationQualifier parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (LocationQualifier) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static LocationQualifier parseFrom(CodedInputStream input) throws IOException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static LocationQualifier parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (LocationQualifier) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(LocationQualifier prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<LocationQualifier, Builder> implements LocationQualifierOrBuilder {
            private Builder() {
                super(LocationQualifier.DEFAULT_INSTANCE);
            }

            public boolean hasNonUserLocation() {
                return ((LocationQualifier) this.instance).hasNonUserLocation();
            }

            public boolean getNonUserLocation() {
                return ((LocationQualifier) this.instance).getNonUserLocation();
            }

            public Builder setNonUserLocation(boolean value) {
                copyOnWrite();
                ((LocationQualifier) this.instance).setNonUserLocation(value);
                return this;
            }

            public Builder clearNonUserLocation() {
                copyOnWrite();
                ((LocationQualifier) this.instance).clearNonUserLocation();
                return this;
            }

            public boolean hasPreciseLocation() {
                return ((LocationQualifier) this.instance).hasPreciseLocation();
            }

            public boolean getPreciseLocation() {
                return ((LocationQualifier) this.instance).getPreciseLocation();
            }

            public Builder setPreciseLocation(boolean value) {
                copyOnWrite();
                ((LocationQualifier) this.instance).setPreciseLocation(value);
                return this;
            }

            public Builder clearPreciseLocation() {
                copyOnWrite();
                ((LocationQualifier) this.instance).clearPreciseLocation();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new LocationQualifier();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0007\u0000\u0002\u0007\u0001", new Object[]{"bitField0_", "nonUserLocation_", "preciseLocation_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<LocationQualifier> parser = PARSER;
                    if (parser == null) {
                        synchronized (LocationQualifier.class) {
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
            GeneratedMessageLite.registerDefaultInstance(LocationQualifier.class, DEFAULT_INSTANCE);
        }

        public static LocationQualifier getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LocationQualifier> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class FieldDetails extends GeneratedMessageLite<FieldDetails, Builder> implements FieldDetailsOrBuilder {
        /* access modifiers changed from: private */
        public static final FieldDetails DEFAULT_INSTANCE = new FieldDetails();
        private static volatile Parser<FieldDetails> PARSER = null;
        public static final int SEMANTIC_TYPE_FIELD_NUMBER = 1;
        private static final Internal.ListAdapter.Converter<Integer, SemanticType> semanticType_converter_ = new Internal.ListAdapter.Converter<Integer, SemanticType>() {
            public SemanticType convert(Integer from) {
                SemanticType result = SemanticType.forNumber(from.intValue());
                return result == null ? SemanticType.ST_NOT_SPECIFIED : result;
            }
        };
        @ProtoField(fieldNumber = 1, type = FieldType.ENUM_LIST)
        private Internal.IntList semanticType_ = emptyIntList();

        private FieldDetails() {
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(FieldDetails.class, DEFAULT_INSTANCE);
        }

        public List<SemanticType> getSemanticTypeList() {
            return new Internal.ListAdapter(this.semanticType_, semanticType_converter_);
        }

        public int getSemanticTypeCount() {
            return this.semanticType_.size();
        }

        public SemanticType getSemanticType(int index) {
            return semanticType_converter_.convert(Integer.valueOf(this.semanticType_.getInt(index)));
        }

        private void ensureSemanticTypeIsMutable() {
            if (!this.semanticType_.isModifiable()) {
                this.semanticType_ = GeneratedMessageLite.mutableCopy(this.semanticType_);
            }
        }

        /* access modifiers changed from: private */
        public void setSemanticType(int index, SemanticType value) {
            if (value != null) {
                ensureSemanticTypeIsMutable();
                this.semanticType_.setInt(index, value.getNumber());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSemanticType(SemanticType value) {
            if (value != null) {
                ensureSemanticTypeIsMutable();
                this.semanticType_.addInt(value.getNumber());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAllSemanticType(Iterable<? extends SemanticType> values) {
            ensureSemanticTypeIsMutable();
            for (SemanticType value : values) {
                this.semanticType_.addInt(value.getNumber());
            }
        }

        /* access modifiers changed from: private */
        public void clearSemanticType() {
            this.semanticType_ = emptyIntList();
        }

        public static FieldDetails parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldDetails parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldDetails parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldDetails parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldDetails parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldDetails parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldDetails parseFrom(InputStream input) throws IOException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldDetails parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldDetails parseDelimitedFrom(InputStream input) throws IOException {
            return (FieldDetails) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldDetails parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDetails) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldDetails parseFrom(CodedInputStream input) throws IOException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldDetails parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDetails) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FieldDetails prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FieldDetails, Builder> implements FieldDetailsOrBuilder {
            private Builder() {
                super(FieldDetails.DEFAULT_INSTANCE);
            }

            public List<SemanticType> getSemanticTypeList() {
                return ((FieldDetails) this.instance).getSemanticTypeList();
            }

            public int getSemanticTypeCount() {
                return ((FieldDetails) this.instance).getSemanticTypeCount();
            }

            public SemanticType getSemanticType(int index) {
                return ((FieldDetails) this.instance).getSemanticType(index);
            }

            public Builder setSemanticType(int index, SemanticType value) {
                copyOnWrite();
                ((FieldDetails) this.instance).setSemanticType(index, value);
                return this;
            }

            public Builder addSemanticType(SemanticType value) {
                copyOnWrite();
                ((FieldDetails) this.instance).addSemanticType(value);
                return this;
            }

            public Builder addAllSemanticType(Iterable<? extends SemanticType> values) {
                copyOnWrite();
                ((FieldDetails) this.instance).addAllSemanticType(values);
                return this;
            }

            public Builder clearSemanticType() {
                copyOnWrite();
                ((FieldDetails) this.instance).clearSemanticType();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FieldDetails();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001e", new Object[]{"semanticType_", SemanticType.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FieldDetails> parser = PARSER;
                    if (parser == null) {
                        synchronized (FieldDetails.class) {
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

        public static FieldDetails getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FieldDetails> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MessageDetails extends GeneratedMessageLite<MessageDetails, Builder> implements MessageDetailsOrBuilder {
        /* access modifiers changed from: private */
        public static final MessageDetails DEFAULT_INSTANCE = new MessageDetails();
        private static volatile Parser<MessageDetails> PARSER = null;
        public static final int SEMANTIC_TYPE_FIELD_NUMBER = 1;
        private static final Internal.ListAdapter.Converter<Integer, SemanticType> semanticType_converter_ = new Internal.ListAdapter.Converter<Integer, SemanticType>() {
            public SemanticType convert(Integer from) {
                SemanticType result = SemanticType.forNumber(from.intValue());
                return result == null ? SemanticType.ST_NOT_SPECIFIED : result;
            }
        };
        @ProtoField(fieldNumber = 1, type = FieldType.ENUM_LIST)
        private Internal.IntList semanticType_ = emptyIntList();

        private MessageDetails() {
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(MessageDetails.class, DEFAULT_INSTANCE);
        }

        public List<SemanticType> getSemanticTypeList() {
            return new Internal.ListAdapter(this.semanticType_, semanticType_converter_);
        }

        public int getSemanticTypeCount() {
            return this.semanticType_.size();
        }

        public SemanticType getSemanticType(int index) {
            return semanticType_converter_.convert(Integer.valueOf(this.semanticType_.getInt(index)));
        }

        private void ensureSemanticTypeIsMutable() {
            if (!this.semanticType_.isModifiable()) {
                this.semanticType_ = GeneratedMessageLite.mutableCopy(this.semanticType_);
            }
        }

        /* access modifiers changed from: private */
        public void setSemanticType(int index, SemanticType value) {
            if (value != null) {
                ensureSemanticTypeIsMutable();
                this.semanticType_.setInt(index, value.getNumber());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSemanticType(SemanticType value) {
            if (value != null) {
                ensureSemanticTypeIsMutable();
                this.semanticType_.addInt(value.getNumber());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAllSemanticType(Iterable<? extends SemanticType> values) {
            ensureSemanticTypeIsMutable();
            for (SemanticType value : values) {
                this.semanticType_.addInt(value.getNumber());
            }
        }

        /* access modifiers changed from: private */
        public void clearSemanticType() {
            this.semanticType_ = emptyIntList();
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

            public List<SemanticType> getSemanticTypeList() {
                return ((MessageDetails) this.instance).getSemanticTypeList();
            }

            public int getSemanticTypeCount() {
                return ((MessageDetails) this.instance).getSemanticTypeCount();
            }

            public SemanticType getSemanticType(int index) {
                return ((MessageDetails) this.instance).getSemanticType(index);
            }

            public Builder setSemanticType(int index, SemanticType value) {
                copyOnWrite();
                ((MessageDetails) this.instance).setSemanticType(index, value);
                return this;
            }

            public Builder addSemanticType(SemanticType value) {
                copyOnWrite();
                ((MessageDetails) this.instance).addSemanticType(value);
                return this;
            }

            public Builder addAllSemanticType(Iterable<? extends SemanticType> values) {
                copyOnWrite();
                ((MessageDetails) this.instance).addAllSemanticType(values);
                return this;
            }

            public Builder clearSemanticType() {
                copyOnWrite();
                ((MessageDetails) this.instance).clearSemanticType();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MessageDetails();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001e", new Object[]{"semanticType_", SemanticType.internalGetVerifier()});
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
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static MessageDetails getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MessageDetails> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
