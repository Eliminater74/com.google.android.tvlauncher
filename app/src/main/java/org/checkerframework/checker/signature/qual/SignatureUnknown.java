package org.checkerframework.checker.signature.qual;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.Target;

@SubtypeOf({})
@Target({})
@DefaultQualifierInHierarchy
public @interface SignatureUnknown {
}
