package com.google.protos.china.policy;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.WireFormat;

public final class ChinaAnnotations {
    public static final int RESTRICTED_FIELD_NUMBER = 61533623;
    public static final int SERIALIZED_DATA_FIELD_NUMBER = 61530914;
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, PrcRestrictionTag> restricted = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), PrcRestrictionTag.PR_ALL_RESTRICTED, null, PrcRestrictionTag.internalGetValueMap(), RESTRICTED_FIELD_NUMBER, WireFormat.FieldType.ENUM, PrcRestrictionTag.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, SerializedDataTag> serializedData = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), SerializedDataTag.NONE, null, SerializedDataTag.internalGetValueMap(), SERIALIZED_DATA_FIELD_NUMBER, WireFormat.FieldType.ENUM, SerializedDataTag.class);

    private ChinaAnnotations() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) restricted);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) serializedData);
    }

    public enum PrcRestrictionTag implements Internal.EnumLite {
        PR_ALL_RESTRICTED(0),
        PR_CONTAINS_NON_RESTRICTED(1),
        PR_CONTAINS_SERIALIZED_NON_RESTRICTED(2);
        
        public static final int PR_ALL_RESTRICTED_VALUE = 0;
        public static final int PR_CONTAINS_NON_RESTRICTED_VALUE = 1;
        public static final int PR_CONTAINS_SERIALIZED_NON_RESTRICTED_VALUE = 2;
        private static final Internal.EnumLiteMap<PrcRestrictionTag> internalValueMap = new Internal.EnumLiteMap<PrcRestrictionTag>() {
            public PrcRestrictionTag findValueByNumber(int number) {
                return PrcRestrictionTag.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static PrcRestrictionTag forNumber(int value2) {
            if (value2 == 0) {
                return PR_ALL_RESTRICTED;
            }
            if (value2 == 1) {
                return PR_CONTAINS_NON_RESTRICTED;
            }
            if (value2 != 2) {
                return null;
            }
            return PR_CONTAINS_SERIALIZED_NON_RESTRICTED;
        }

        public static Internal.EnumLiteMap<PrcRestrictionTag> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return PrcRestrictionTagVerifier.INSTANCE;
        }

        private static final class PrcRestrictionTagVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new PrcRestrictionTagVerifier();

            private PrcRestrictionTagVerifier() {
            }

            public boolean isInRange(int number) {
                return PrcRestrictionTag.forNumber(number) != null;
            }
        }

        private PrcRestrictionTag(int value2) {
            this.value = value2;
        }
    }

    public enum SerializedDataTag implements Internal.EnumLite {
        NONE(0),
        SD_CLEARCUT_SOURCE_EXTENSION(1),
        SD_CLEARCUT_SOURCE_EXTENSION_JS(2),
        SD_CLEARCUT_LOGEVENTS(3);
        
        public static final int NONE_VALUE = 0;
        public static final int SD_CLEARCUT_LOGEVENTS_VALUE = 3;
        public static final int SD_CLEARCUT_SOURCE_EXTENSION_JS_VALUE = 2;
        public static final int SD_CLEARCUT_SOURCE_EXTENSION_VALUE = 1;
        private static final Internal.EnumLiteMap<SerializedDataTag> internalValueMap = new Internal.EnumLiteMap<SerializedDataTag>() {
            public SerializedDataTag findValueByNumber(int number) {
                return SerializedDataTag.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static SerializedDataTag forNumber(int value2) {
            if (value2 == 0) {
                return NONE;
            }
            if (value2 == 1) {
                return SD_CLEARCUT_SOURCE_EXTENSION;
            }
            if (value2 == 2) {
                return SD_CLEARCUT_SOURCE_EXTENSION_JS;
            }
            if (value2 != 3) {
                return null;
            }
            return SD_CLEARCUT_LOGEVENTS;
        }

        public static Internal.EnumLiteMap<SerializedDataTag> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return SerializedDataTagVerifier.INSTANCE;
        }

        private static final class SerializedDataTagVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new SerializedDataTagVerifier();

            private SerializedDataTagVerifier() {
            }

            public boolean isInRange(int number) {
                return SerializedDataTag.forNumber(number) != null;
            }
        }

        private SerializedDataTag(int value2) {
            this.value = value2;
        }
    }
}
