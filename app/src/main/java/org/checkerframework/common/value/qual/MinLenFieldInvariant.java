package org.checkerframework.common.value.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

@Inherited
@Target({ElementType.TYPE})
public @interface MinLenFieldInvariant {
    String[] field();

    int[] minLen();
}
