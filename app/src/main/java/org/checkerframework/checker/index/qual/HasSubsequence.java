package org.checkerframework.checker.index.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.JavaExpression;

@Target({ElementType.FIELD})
public @interface HasSubsequence {
    @JavaExpression
    String from();

    @JavaExpression
    /* renamed from: to */
    String mo33738to();

    @JavaExpression
    String value();
}
