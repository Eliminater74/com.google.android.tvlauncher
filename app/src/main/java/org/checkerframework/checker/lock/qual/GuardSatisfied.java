package org.checkerframework.checker.lock.qual;

import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TargetLocations;
import org.checkerframework.framework.qual.TypeUseLocation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SubtypeOf({GuardedByUnknown.class})
@Documented
@Target({ElementType.TYPE_USE})
@TargetLocations({TypeUseLocation.RECEIVER, TypeUseLocation.PARAMETER, TypeUseLocation.RETURN})
@Retention(RetentionPolicy.RUNTIME)
public @interface GuardSatisfied {
    int value() default -1;
}
