package com.google.analytics.config.protoverifier.proto;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.WireFormat;

public final class RulesProto {
    public static final int MUTATION_RULES_FIELD_NUMBER = 28140454;
    public static final int RULES_FIELD_NUMBER = 28140453;
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, MutationRules> mutationRules = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), MutationRules.getDefaultInstance(), MutationRules.getDefaultInstance(), null, MUTATION_RULES_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, MutationRules.class);
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FieldOptions, Rules> rules = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FieldOptions.getDefaultInstance(), Rules.getDefaultInstance(), Rules.getDefaultInstance(), null, RULES_FIELD_NUMBER, WireFormat.FieldType.MESSAGE, Rules.class);

    private RulesProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) rules);
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) mutationRules);
    }
}
