package org.checkerframework.common.aliasing.qual;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.InvisibleQualifier;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InvisibleQualifier
@SubtypeOf({LeakedToResult.class})
@Documented
@Target({})
@DefaultQualifierInHierarchy
@Retention(RetentionPolicy.RUNTIME)
public @interface MaybeLeaked {
}
