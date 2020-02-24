package dagger.internal;

import dagger.Lazy;
import javax.inject.Provider;

public final class DoubleCheck<T> implements Provider<T>, Lazy<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider<T> provider;

    private DoubleCheck(Provider<T> provider2) {
        this.provider = provider2;
    }

    public T get() {
        Object result = this.instance;
        if (result == UNINITIALIZED) {
            synchronized (this) {
                result = this.instance;
                if (result == UNINITIALIZED) {
                    result = this.provider.get();
                    this.instance = reentrantCheck(this.instance, result);
                    this.provider = null;
                }
            }
        }
        return result;
    }

    public static Object reentrantCheck(Object currentInstance, Object newInstance) {
        if (!(currentInstance != UNINITIALIZED && !(currentInstance instanceof MemoizedSentinel)) || currentInstance == newInstance) {
            return newInstance;
        }
        String valueOf = String.valueOf(currentInstance);
        String valueOf2 = String.valueOf(newInstance);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 118 + String.valueOf(valueOf2).length());
        sb.append("Scoped provider was invoked recursively returning different results: ");
        sb.append(valueOf);
        sb.append(" & ");
        sb.append(valueOf2);
        sb.append(". This is likely due to a circular dependency.");
        throw new IllegalStateException(sb.toString());
    }

    public static <P extends Provider<T>, T> Provider<T> provider(P delegate) {
        Preconditions.checkNotNull(delegate);
        if (delegate instanceof DoubleCheck) {
            return delegate;
        }
        return new DoubleCheck(delegate);
    }

    public static <P extends Provider<T>, T> Lazy<T> lazy(P provider2) {
        if (provider2 instanceof Lazy) {
            return (Lazy) provider2;
        }
        return new DoubleCheck((Provider) Preconditions.checkNotNull(provider2));
    }
}
