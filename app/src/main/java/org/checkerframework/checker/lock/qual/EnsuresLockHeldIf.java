package org.checkerframework.checker.lock.qual;

import org.checkerframework.framework.qual.ConditionalPostconditionAnnotation;
import org.checkerframework.framework.qual.InheritedAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ConditionalPostconditionAnnotation(qualifier = LockHeld.class)
@Documented
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@InheritedAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsuresLockHeldIf {
    String[] expression();

    boolean result();
}
