package org.checkerframework.common.value.qual;

import org.checkerframework.framework.qual.PolymorphicQualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@PolymorphicQualifier(UnknownVal.class)
public @interface PolyValue {
}
