package com.google.analytics.config.protoverifier.proto;

import com.google.protobuf.MessageLiteOrBuilder;

public interface MutationRulesOrBuilder extends MessageLiteOrBuilder {
    MutationRules.Mutability getMutability();

    boolean hasMutability();
}
