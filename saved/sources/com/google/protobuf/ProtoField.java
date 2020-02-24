package com.google.protobuf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface ProtoField {
    int fieldNumber();

    boolean isEnforceUtf8() default true;

    boolean isLazy() default false;

    boolean isRequired() default false;

    FieldType type();
}
