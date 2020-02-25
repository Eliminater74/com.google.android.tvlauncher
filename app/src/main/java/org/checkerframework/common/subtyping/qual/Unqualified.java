package org.checkerframework.common.subtyping.qual;

import org.checkerframework.framework.qual.InvisibleQualifier;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.Target;

@InvisibleQualifier
@SubtypeOf({})
@Target({})
public @interface Unqualified {
}
