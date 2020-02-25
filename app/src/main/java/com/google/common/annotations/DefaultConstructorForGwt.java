package com.google.common.annotations;

import com.google.errorprone.annotations.IncompatibleModifiers;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.Modifier;

@GwtCompatible
@GoogleInternal
@Documented
@Target({ElementType.CONSTRUCTOR})
@IncompatibleModifiers({Modifier.PUBLIC})
@Retention(RetentionPolicy.CLASS)
public @interface DefaultConstructorForGwt {
}
