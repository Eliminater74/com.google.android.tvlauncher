package org.checkerframework.common.value.qual;

import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SubtypeOf({UnknownVal.class})
@Target({})
@Retention(RetentionPolicy.SOURCE)
public @interface IntRangeFromNonNegative {
}
