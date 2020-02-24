package org.checkerframework.common.reflection.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({UnknownMethod.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodVal {
    String[] className();

    String[] methodName();

    int[] params();
}
