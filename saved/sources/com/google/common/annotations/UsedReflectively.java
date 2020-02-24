package com.google.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@GwtCompatible
@GoogleInternal
@Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface UsedReflectively {
}
