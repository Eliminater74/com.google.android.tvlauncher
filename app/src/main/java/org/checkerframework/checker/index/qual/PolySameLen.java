package org.checkerframework.checker.index.qual;

import org.checkerframework.framework.qual.PolymorphicQualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@PolymorphicQualifier(SameLenUnknown.class)
public @interface PolySameLen {
}
