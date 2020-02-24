package dagger.internal;

import javax.inject.Provider;

public final class SingleCheck<T> implements Provider<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider<T> provider;

    private SingleCheck(Provider<T> provider2) {
        this.provider = provider2;
    }

    public T get() {
        Object local = this.instance;
        if (local != UNINITIALIZED) {
            return local;
        }
        Provider<T> providerReference = this.provider;
        if (providerReference == null) {
            return this.instance;
        }
        Object local2 = providerReference.get();
        this.instance = local2;
        this.provider = null;
        return local2;
    }

    public static <P extends Provider<T>, T> Provider<T> provider(P provider2) {
        if ((provider2 instanceof SingleCheck) || (provider2 instanceof DoubleCheck)) {
            return provider2;
        }
        return new SingleCheck((Provider) Preconditions.checkNotNull(provider2));
    }
}
