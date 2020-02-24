package com.google.errorprone.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@GoogleInternal
@Retention(RetentionPolicy.RUNTIME)
public @interface RestrictedInheritance {
    String allowedOnPath() default "";

    String checkerName() default "RestrictedApi";

    String explanation();

    String link();

    Class<? extends Annotation> suggestedWhitelistAnnotation() default DontSuggestFixes.class;

    boolean warningOnlyForRefactoring() default false;

    Class<? extends Annotation>[] whitelistAnnotations() default {};
}
