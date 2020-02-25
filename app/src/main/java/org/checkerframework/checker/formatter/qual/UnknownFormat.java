package org.checkerframework.checker.formatter.qual;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.InvisibleQualifier;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.Target;

@InvisibleQualifier
@SubtypeOf({})
@Target({})
@DefaultQualifierInHierarchy
public @interface UnknownFormat {
}
