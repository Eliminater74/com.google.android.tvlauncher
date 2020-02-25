package org.checkerframework.checker.fenum.qual;

import org.checkerframework.framework.qual.DefaultFor;
import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TypeUseLocation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@DefaultFor({TypeUseLocation.EXCEPTION_PARAMETER})
@SubtypeOf({FenumTop.class})
@Documented
@Target({})
@DefaultQualifierInHierarchy
@Retention(RetentionPolicy.RUNTIME)
public @interface FenumUnqualified {
}
