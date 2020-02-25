package org.checkerframework.checker.signature.qual;

import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SubtypeOf({SignatureUnknown.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface MethodDescriptor {
}
