package com.google.errorprone.annotations.concurrent;

import com.google.errorprone.annotations.GoogleInternal;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@GoogleInternal
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteOnly {
}
