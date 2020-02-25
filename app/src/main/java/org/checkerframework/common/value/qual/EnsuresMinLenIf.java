package org.checkerframework.common.value.qual;

import org.checkerframework.framework.qual.ConditionalPostconditionAnnotation;
import org.checkerframework.framework.qual.InheritedAnnotation;
import org.checkerframework.framework.qual.QualifierArgument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ConditionalPostconditionAnnotation(qualifier = MinLen.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@InheritedAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsuresMinLenIf {
    String[] expression();

    boolean result();

    @QualifierArgument("value")
    int targetValue() default 0;
}
