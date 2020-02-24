package org.checkerframework.checker.formatter.qual;

import java.lang.annotation.Target;
import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.InvisibleQualifier;
import org.checkerframework.framework.qual.SubtypeOf;

@InvisibleQualifier
@SubtypeOf({})
@Target({})
@DefaultQualifierInHierarchy
public @interface UnknownFormat {
}
