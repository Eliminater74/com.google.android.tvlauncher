package org.checkerframework.checker.lock.qual;

import org.checkerframework.framework.qual.InvisibleQualifier;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InvisibleQualifier
@SubtypeOf({LockPossiblyHeld.class})
@Documented
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockHeld {
}
