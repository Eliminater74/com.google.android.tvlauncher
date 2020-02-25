package org.checkerframework.checker.lock.qual;

import org.checkerframework.framework.qual.DefaultInUncheckedCodeFor;
import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TypeUseLocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SubtypeOf({})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@DefaultInUncheckedCodeFor({TypeUseLocation.RECEIVER})
@Retention(RetentionPolicy.RUNTIME)
public @interface GuardedByUnknown {
}
