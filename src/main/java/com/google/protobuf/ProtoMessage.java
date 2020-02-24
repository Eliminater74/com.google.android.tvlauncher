package com.google.protobuf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface ProtoMessage {
    int[] checkInitialized() default {};

    boolean messageSetWireFormat() default false;

    ProtoSyntax protoSyntax();
}
