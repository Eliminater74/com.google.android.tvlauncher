package org.checkerframework.checker.signature.qual;

import java.lang.annotation.Target;
import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({})
@Target({})
@DefaultQualifierInHierarchy
public @interface SignatureUnknown {
}
