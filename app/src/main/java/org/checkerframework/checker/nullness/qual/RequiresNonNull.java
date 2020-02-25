package org.checkerframework.checker.nullness.qual;

import org.checkerframework.framework.qual.PreconditionAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@PreconditionAnnotation(qualifier = NonNull.class)
public @interface RequiresNonNull {
    String[] value();
}
