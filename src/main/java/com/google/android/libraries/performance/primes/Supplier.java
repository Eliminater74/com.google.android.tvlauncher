package com.google.android.libraries.performance.primes;

public interface Supplier<T> {
    T get();

    public static final class Lazy<X> implements Supplier<X> {
        private volatile Supplier<X> factory;
        private volatile X instance;

        public Lazy(Supplier<X> factory2) {
            this.factory = factory2;
        }

        public X get() {
            if (this.instance == null) {
                synchronized (this) {
                    if (this.instance == null) {
                        this.instance = this.factory.get();
                        this.factory = null;
                    }
                }
            }
            return this.instance;
        }
    }
}
