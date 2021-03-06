package com.google.protobuf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface ProtoOneof {
    int[] fieldNumbers();

    int index();

    Class<?>[] storedTypes();

    FieldType[] types();
}
