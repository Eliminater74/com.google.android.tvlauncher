package dagger.internal;

import javax.inject.Provider;

import dagger.Lazy;

public final class ProviderOfLazy<T> implements Provider<Lazy<T>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Provider<T> provider;

    private ProviderOfLazy(Provider<T> provider2) {
        this.provider = provider2;
    }

    public static <T> Provider<Lazy<T>> create(Provider<T> provider2) {
        return new ProviderOfLazy((Provider) Preconditions.checkNotNull(provider2));
    }

    public Lazy<T> get() {
        return DoubleCheck.lazy(this.provider);
    }
}
