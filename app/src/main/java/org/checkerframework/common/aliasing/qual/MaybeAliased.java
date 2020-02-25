package org.checkerframework.common.aliasing.qual;

import org.checkerframework.framework.qual.DefaultFor;
import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.LiteralKind;
import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TypeUseLocation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@DefaultFor({TypeUseLocation.UPPER_BOUND, TypeUseLocation.LOWER_BOUND})
@ImplicitFor(literals = {LiteralKind.NULL}, typeNames = {Void.class})
@SubtypeOf({})
@Documented
@Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@DefaultQualifierInHierarchy
@Retention(RetentionPolicy.RUNTIME)
public @interface MaybeAliased {
}
