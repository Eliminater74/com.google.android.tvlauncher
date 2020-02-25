package com.google.apps.jspb;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.WireFormat;

public final class Jspb {
    public static final int ALLOW_BROKEN_JAVA_UNSIGNED_DESERIALIZATION_BEHAVIOR_FIELD_NUMBER = 17706;
    public static final int BUILDER_FIELD_NUMBER = 22366751;
    public static final int GENERATE_FROM_OBJECT_FIELD_NUMBER = 81874859;
    public static final int GENERATE_XID_FIELD_NUMBER = 78050980;
    public static final int IGNORE_FIELD_NUMBER = 17702;
    public static final int JSTYPE_FIELD_NUMBER = 17703;
    public static final int JS_NAMESPACE_FIELD_NUMBER = 17702;
    public static final int MAP_KEY_FIELD_NUMBER = 17704;
    public static final int MESSAGE_ID_FIELD_NUMBER = 17701;
    public static final int NAMESPACE_ONLY_FIELD_NUMBER = 17702;
    public static final int RESPONSE_PROTO_FIELD_NUMBER = 17701;
    public static final int USE_BROKEN_JAVA_UNSIGNED_SERIALIZATION_BEHAVIOR_FIELD_NUMBER = 17705;
    public static final int USE_BROKEN_PROTO2_SEMANTICS_FIELD_NUMBER = 129271686;
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Boolean> allowBrokenJavaUnsignedDeserializationBehavior = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), null, null, null, ALLOW_BROKEN_JAVA_UNSIGNED_DESERIALIZATION_BEHAVIOR_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, Boolean> builder = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), null, null, null, BUILDER_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, Boolean> generateFromObject = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), null, null, null, GENERATE_FROM_OBJECT_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, Boolean> generateXid = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), null, null, null, GENERATE_XID_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Boolean> ignore = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), null, null, null, 17702, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, String> jsNamespace = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), "", null, null, 17702, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, JsType> jstype = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), JsType.INT52, null, JsType.internalGetValueMap(), JSTYPE_FIELD_NUMBER, WireFormat.FieldType.ENUM, JsType.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, String> mapKey = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), "", null, null, MAP_KEY_FIELD_NUMBER, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, String> messageId = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), "", null, null, 17701, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.MessageOptions, Boolean> namespaceOnly = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.MessageOptions.getDefaultInstance(), null, null, null, 17702, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, Boolean> responseProto = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), null, null, null, 17701, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Boolean> useBrokenJavaUnsignedSerializationBehavior = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), null, null, null, USE_BROKEN_JAVA_UNSIGNED_SERIALIZATION_BEHAVIOR_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FileOptions, Boolean> useBrokenProto2Semantics = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FileOptions.getDefaultInstance(), true, null, null, USE_BROKEN_PROTO2_SEMANTICS_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);

    private Jspb() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) ignore);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) jstype);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) mapKey);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) useBrokenJavaUnsignedSerializationBehavior);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) allowBrokenJavaUnsignedDeserializationBehavior);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) messageId);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) generateXid);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) generateFromObject);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) namespaceOnly);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) builder);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) responseProto);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) jsNamespace);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) useBrokenProto2Semantics);
    }

    public enum JsType implements Internal.EnumLite {
        INT52(0),
        NUMBER(1),
        STRING(2),
        INTEGER(100);

        public static final int INT52_VALUE = 0;
        public static final int INTEGER_VALUE = 100;
        public static final int NUMBER_VALUE = 1;
        public static final int STRING_VALUE = 2;
        private static final Internal.EnumLiteMap<JsType> internalValueMap = new Internal.EnumLiteMap<JsType>() {
            public JsType findValueByNumber(int number) {
                return JsType.forNumber(number);
            }
        };
        private final int value;

        private JsType(int value2) {
            this.value = value2;
        }

        public static JsType forNumber(int value2) {
            if (value2 == 0) {
                return INT52;
            }
            if (value2 == 1) {
                return NUMBER;
            }
            if (value2 == 2) {
                return STRING;
            }
            if (value2 != 100) {
                return null;
            }
            return INTEGER;
        }

        public static Internal.EnumLiteMap<JsType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return JsTypeVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class JsTypeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new JsTypeVerifier();

            private JsTypeVerifier() {
            }

            public boolean isInRange(int number) {
                return JsType.forNumber(number) != null;
            }
        }
    }
}
