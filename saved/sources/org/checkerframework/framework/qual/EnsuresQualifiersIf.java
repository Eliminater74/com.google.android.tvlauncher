package org.checkerframework.framework.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@InheritedAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsuresQualifiersIf {
    EnsuresQualifierIf[] value();
}
