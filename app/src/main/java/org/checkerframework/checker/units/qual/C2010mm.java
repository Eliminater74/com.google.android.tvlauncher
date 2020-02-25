package org.checkerframework.checker.units.qual;

import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SubtypeOf({Length.class})
@Documented
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@UnitsMultiple(prefix = Prefix.milli, quantity = C2008m.class)
@Retention(RetentionPolicy.RUNTIME)
/* renamed from: org.checkerframework.checker.units.qual.mm */
public @interface C2010mm {
}
