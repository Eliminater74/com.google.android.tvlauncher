package com.google.common.annotations;

@GwtCompatible
public @interface VisibleForTesting {

    @GoogleInternal
    Visibility productionVisibility() default Visibility.PRIVATE;

    @GoogleInternal
    public enum Visibility {
        NONE,
        PRIVATE,
        PACKAGE_PRIVATE,
        PROTECTED
    }
}
