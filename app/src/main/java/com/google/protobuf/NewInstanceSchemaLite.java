package com.google.protobuf;

final class NewInstanceSchemaLite implements NewInstanceSchema {
    NewInstanceSchemaLite() {
    }

    public Object newInstance(Object defaultInstance) {
        return ((GeneratedMessageLite) defaultInstance).dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE);
    }
}
