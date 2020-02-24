package com.google.android.tvrecommendations.shared.util;

public final class BuildType {
    public static final boolean DEBUG = false;
    public static final boolean DOGFOOD = false;

    public static <T> T newInstance(Class<T> cls, String className, Object... constructorArgs) {
        String valueOf = String.valueOf(className);
        throw new UnsupportedOperationException(valueOf.length() != 0 ? "Cannot allocate ".concat(valueOf) : new String("Cannot allocate "));
    }

    private BuildType() {
    }
}
