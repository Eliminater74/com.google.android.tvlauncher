package org.checkerframework.checker.regex.qual;

import org.checkerframework.framework.qual.InvisibleQualifier;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.Target;

@InvisibleQualifier
@SubtypeOf({UnknownRegex.class})
@Target({})
public @interface PartialRegex {
    String value() default "";
}
