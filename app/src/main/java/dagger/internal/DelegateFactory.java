package dagger.internal;

import javax.inject.Provider;

public final class DelegateFactory<T> implements Factory<T> {
    private Provider<T> delegate;

    public T get() {
        Provider<T> provider = this.delegate;
        if (provider != null) {
            return provider.get();
        }
        throw new IllegalStateException();
    }

    @Deprecated
    public void setDelegatedProvider(Provider<T> delegate2) {
        setDelegate(this, delegate2);
    }

    public static <T> void setDelegate(Provider<T> delegateFactory, Provider<T> delegate2) {
        Preconditions.checkNotNull(delegate2);
        DelegateFactory<T> asDelegateFactory = (DelegateFactory) delegateFactory;
        if (asDelegateFactory.delegate == null) {
            asDelegateFactory.delegate = delegate2;
            return;
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: package-private */
    public Provider<T> getDelegate() {
        return (Provider) Preconditions.checkNotNull(this.delegate);
    }
}
