package com.google.common.annotations;

@GwtCompatible
public @interface VisibleForTesting {

    @GoogleInternal
    public enum Visibility {
        NONE,
        PRIVATE,
        PACKAGE_PRIVATE,
        PROTECTED
    }

    @GoogleInternal
    Visibility productionVisibility() default Visibility.PRIVATE;
}
