package org.checkerframework.checker.signature.qual;

import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SubtypeOf({FieldDescriptorForPrimitiveOrArrayInUnnamedPackage.class, Identifier.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@ImplicitFor(stringPatterns = {"^[BCDFIJSZ]$"})
public @interface FieldDescriptorForPrimitive {
}
