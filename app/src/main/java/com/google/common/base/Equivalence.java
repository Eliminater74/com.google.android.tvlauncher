package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.ForOverride;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;

@GwtCompatible
public abstract class Equivalence<T> {
    protected Equivalence() {
    }

    public static Equivalence<Object> equals() {
        return Equals.INSTANCE;
    }

    public static Equivalence<Object> identity() {
        return Identity.INSTANCE;
    }

    /* access modifiers changed from: protected */
    @ForOverride
    public abstract boolean doEquivalent(T t, T t2);

    /* access modifiers changed from: protected */
    @ForOverride
    public abstract int doHash(T t);

    public final boolean equivalent(@NullableDecl T a, @NullableDecl T b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return doEquivalent(a, b);
    }

    public final int hash(@NullableDecl T t) {
        if (t == null) {
            return 0;
        }
        return doHash(t);
    }

    public final <F> Equivalence<F> onResultOf(Function<F, ? extends T> function) {
        return new FunctionalEquivalence(function, this);
    }

    public final <S extends T> Wrapper<S> wrap(@NullableDecl S reference) {
        return new Wrapper<>(reference);
    }

    @GwtCompatible(serializable = true)
    public final <S extends T> Equivalence<Iterable<S>> pairwise() {
        return new PairwiseEquivalence(this);
    }

    public final Predicate<T> equivalentTo(@NullableDecl T target) {
        return new EquivalentToPredicate(this, target);
    }

    public static final class Wrapper<T> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Equivalence<? super T> equivalence;
        @NullableDecl
        private final T reference;

        private Wrapper(Equivalence<? super T> equivalence2, @NullableDecl T reference2) {
            this.equivalence = (Equivalence) Preconditions.checkNotNull(equivalence2);
            this.reference = reference2;
        }

        @NullableDecl
        public T get() {
            return this.reference;
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Wrapper)) {
                return false;
            }
            Wrapper<?> that = (Wrapper) obj;
            if (this.equivalence.equals(that.equivalence)) {
                return this.equivalence.equivalent(this.reference, that.reference);
            }
            return false;
        }

        public int hashCode() {
            return this.equivalence.hash(this.reference);
        }

        public String toString() {
            String valueOf = String.valueOf(this.equivalence);
            String valueOf2 = String.valueOf(this.reference);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 7 + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append(".wrap(");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }

    private static final class EquivalentToPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Equivalence<T> equivalence;
        @NullableDecl
        private final T target;

        EquivalentToPredicate(Equivalence<T> equivalence2, @NullableDecl T target2) {
            this.equivalence = (Equivalence) Preconditions.checkNotNull(equivalence2);
            this.target = target2;
        }

        public boolean apply(@NullableDecl T input) {
            return this.equivalence.equivalent(input, this.target);
        }

        public boolean equals(@NullableDecl Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EquivalentToPredicate)) {
                return false;
            }
            EquivalentToPredicate<?> that = (EquivalentToPredicate) obj;
            if (!this.equivalence.equals(that.equivalence) || !Objects.equal(this.target, that.target)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.equivalence, this.target);
        }

        public String toString() {
            String valueOf = String.valueOf(this.equivalence);
            String valueOf2 = String.valueOf(this.target);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 15 + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append(".equivalentTo(");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class Equals extends Equivalence<Object> implements Serializable {
        static final Equals INSTANCE = new Equals();
        private static final long serialVersionUID = 1;

        Equals() {
        }

        /* access modifiers changed from: protected */
        public boolean doEquivalent(Object a, Object b) {
            return a.equals(b);
        }

        /* access modifiers changed from: protected */
        public int doHash(Object o) {
            return o.hashCode();
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    static final class Identity extends Equivalence<Object> implements Serializable {
        static final Identity INSTANCE = new Identity();
        private static final long serialVersionUID = 1;

        Identity() {
        }

        /* access modifiers changed from: protected */
        public boolean doEquivalent(Object a, Object b) {
            return false;
        }

        /* access modifiers changed from: protected */
        public int doHash(Object o) {
            return System.identityHashCode(o);
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }
}
