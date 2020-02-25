package org.checkerframework.checker.signature.qual;

import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SubtypeOf({ClassGetName.class, FieldDescriptor.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@ImplicitFor(stringPatterns = {"^([BCDFIJSZ]|\\[+[BCDFIJSZ]|\\[L[A-Za-z_][A-Za-z_0-9]*;)$"})
public @interface FieldDescriptorForPrimitiveOrArrayInUnnamedPackage {
}
