package org.checkerframework.checker.regex.qual;

import java.lang.annotation.Target;
import org.checkerframework.framework.qual.InvisibleQualifier;
import org.checkerframework.framework.qual.SubtypeOf;

@InvisibleQualifier
@SubtypeOf({UnknownRegex.class})
@Target({})
public @interface PartialRegex {
    String value() default "";
}
