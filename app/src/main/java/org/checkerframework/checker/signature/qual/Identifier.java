package org.checkerframework.checker.signature.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({DotSeparatedIdentifiers.class, BinaryNameInUnnamedPackage.class, IdentifierOrArray.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@ImplicitFor(stringPatterns = {"^([A-Za-z_][A-Za-z_0-9]*)$"})
public @interface Identifier {
}
