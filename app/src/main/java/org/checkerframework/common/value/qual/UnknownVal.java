package org.checkerframework.common.value.qual;

import org.checkerframework.framework.qual.DefaultInUncheckedCodeFor;
import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TypeUseLocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SubtypeOf({})
@Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@DefaultInUncheckedCodeFor({TypeUseLocation.PARAMETER})
@DefaultQualifierInHierarchy
@Retention(RetentionPolicy.RUNTIME)
public @interface UnknownVal {
}
