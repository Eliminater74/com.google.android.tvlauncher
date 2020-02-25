package org.checkerframework.checker.index.qual;

import org.checkerframework.framework.qual.JavaExpression;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SubtypeOf({SameLenUnknown.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface SameLen {
    @JavaExpression
    String[] value();
}
