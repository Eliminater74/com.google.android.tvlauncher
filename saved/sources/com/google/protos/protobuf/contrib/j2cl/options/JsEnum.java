package com.google.protos.protobuf.contrib.j2cl.options;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.WireFormat;

public final class JsEnum {
    public static final int GENERATE_JS_ENUM_FIELD_NUMBER = 13371;
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.EnumOptions, Boolean> generateJsEnum = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.EnumOptions.getDefaultInstance(), false, null, null, GENERATE_JS_ENUM_FIELD_NUMBER, WireFormat.FieldType.BOOL, Boolean.class);

    private JsEnum() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) generateJsEnum);
    }
}
