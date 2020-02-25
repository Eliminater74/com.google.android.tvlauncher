package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.concurrent.LazyInit;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.Iterator;

@GwtCompatible
public abstract class Converter<A, B> implements Function<A, B> {
    private final boolean handleNullAutomatically;
    @MonotonicNonNullDecl
    @LazyInit
    private transient Converter<B, A> reverse;

    protected Converter() {
        this(true);
    }

    Converter(boolean handleNullAutomatically2) {
        this.handleNullAutomatically = handleNullAutomatically2;
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> forwardFunction, Function<? super B, ? extends A> backwardFunction) {
        return new FunctionBasedConverter(forwardFunction, backwardFunction);
    }

    public static <T> Converter<T, T> identity() {
        return IdentityConverter.INSTANCE;
    }

    /* access modifiers changed from: protected */
    @ForOverride
    public abstract A doBackward(Object obj);

    /* access modifiers changed from: protected */
    @ForOverride
    public abstract B doForward(Object obj);

    @NullableDecl
    @CanIgnoreReturnValue
    public final B convert(@NullableDecl A a) {
        return correctedDoForward(a);
    }

    /* access modifiers changed from: package-private */
    @NullableDecl
    public B correctedDoForward(@NullableDecl A a) {
        if (!this.handleNullAutomatically) {
            return doForward(a);
        }
        if (a == null) {
            return null;
        }
        return Preconditions.checkNotNull(doForward(a));
    }

    /* access modifiers changed from: package-private */
    @NullableDecl
    public A correctedDoBackward(@NullableDecl B b) {
        if (!this.handleNullAutomatically) {
            return doBackward(b);
        }
        if (b == null) {
            return null;
        }
        return Preconditions.checkNotNull(doBackward(b));
    }

    @CanIgnoreReturnValue
    public Iterable<B> convertAll(final Iterable<? extends A> fromIterable) {
        Preconditions.checkNotNull(fromIterable, "fromIterable");
        return new Iterable<B>() {
            public Iterator<B> iterator() {
                return new Iterator<B>() {
                    private final Iterator<? extends A> fromIterator = fromIterable.iterator();

                    public boolean hasNext() {
                        return this.fromIterator.hasNext();
                    }

                    public B next() {
                        return Converter.this.convert(this.fromIterator.next());
                    }

                    public void remove() {
                        this.fromIterator.remove();
                    }
                };
            }
        };
    }

    @CanIgnoreReturnValue
    public Converter<B, A> reverse() {
        Converter<B, A> result = this.reverse;
        if (result != null) {
            return result;
        }
        ReverseConverter reverseConverter = new ReverseConverter(this);
        this.reverse = reverseConverter;
        return reverseConverter;
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.common.base.Converter<B, C>, com.google.common.base.Converter] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <C> com.google.common.base.Converter<A, C> andThen(com.google.common.base.Converter<B, C> r2) {
        /*
            r1 = this;
            com.google.common.base.Converter r0 = r1.doAndThen(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Converter.andThen(com.google.common.base.Converter):com.google.common.base.Converter");
    }

    /* access modifiers changed from: package-private */
    public <C> Converter<A, C> doAndThen(Converter<B, C> secondConverter) {
        return new ConverterComposition(this, (Converter) Preconditions.checkNotNull(secondConverter));
    }

    @NullableDecl
    @CanIgnoreReturnValue
    @Deprecated
    public final B apply(@NullableDecl A a) {
        return convert(a);
    }

    public boolean equals(@NullableDecl Object object) {
        return super.equals(object);
    }

    private static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable {
        private static final long serialVersionUID = 0;
        final Converter<A, B> original;

        ReverseConverter(Converter<A, B> original2) {
            this.original = original2;
        }

        /* access modifiers changed from: protected */
        public A doForward(B b) {
            throw new AssertionError();
        }

        /* access modifiers changed from: protected */
        public B doBackward(A a) {
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        @NullableDecl
        public A correctedDoForward(@NullableDecl B b) {
            return this.original.correctedDoBackward(b);
        }

        /* access modifiers changed from: package-private */
        @NullableDecl
        public B correctedDoBackward(@NullableDecl A a) {
            return this.original.correctedDoForward(a);
        }

        public Converter<A, B> reverse() {
            return this.original;
        }

        public boolean equals(@NullableDecl Object object) {
            if (object instanceof ReverseConverter) {
                return this.original.equals(((ReverseConverter) object).original);
            }
            return false;
        }

        public int hashCode() {
            return this.original.hashCode() ^ -1;
        }

        public String toString() {
            String valueOf = String.valueOf(this.original);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 10);
            sb.append(valueOf);
            sb.append(".reverse()");
            return sb.toString();
        }
    }

    private static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable {
        private static final long serialVersionUID = 0;
        final Converter<A, B> first;
        final Converter<B, C> second;

        ConverterComposition(Converter<A, B> first2, Converter<B, C> second2) {
            this.first = first2;
            this.second = second2;
        }

        /* access modifiers changed from: protected */
        public C doForward(A a) {
            throw new AssertionError();
        }

        /* access modifiers changed from: protected */
        public A doBackward(C c) {
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        @NullableDecl
        public C correctedDoForward(@NullableDecl A a) {
            return this.second.correctedDoForward(this.first.correctedDoForward(a));
        }

        /* access modifiers changed from: package-private */
        @NullableDecl
        public A correctedDoBackward(@NullableDecl C c) {
            return this.first.correctedDoBackward(this.second.correctedDoBackward(c));
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof ConverterComposition)) {
                return false;
            }
            ConverterComposition<?, ?, ?> that = (ConverterComposition) object;
            if (!this.first.equals(that.first) || !this.second.equals(that.second)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.first.hashCode() * 31) + this.second.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.first);
            String valueOf2 = String.valueOf(this.second);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 10 + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append(".andThen(");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }

    private static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable {
        private final Function<? super B, ? extends A> backwardFunction;
        private final Function<? super A, ? extends B> forwardFunction;

        private FunctionBasedConverter(Function<? super A, ? extends B> forwardFunction2, Function<? super B, ? extends A> backwardFunction2) {
            this.forwardFunction = (Function) Preconditions.checkNotNull(forwardFunction2);
            this.backwardFunction = (Function) Preconditions.checkNotNull(backwardFunction2);
        }

        /* access modifiers changed from: protected */
        public B doForward(A a) {
            return this.forwardFunction.apply(a);
        }

        /* access modifiers changed from: protected */
        public A doBackward(B b) {
            return this.backwardFunction.apply(b);
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof FunctionBasedConverter)) {
                return false;
            }
            FunctionBasedConverter<?, ?> that = (FunctionBasedConverter) object;
            if (!this.forwardFunction.equals(that.forwardFunction) || !this.backwardFunction.equals(that.backwardFunction)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.forwardFunction.hashCode() * 31) + this.backwardFunction.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.forwardFunction);
            String valueOf2 = String.valueOf(this.backwardFunction);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18 + String.valueOf(valueOf2).length());
            sb.append("Converter.from(");
            sb.append(valueOf);
            sb.append(", ");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }

    private static final class IdentityConverter<T> extends Converter<T, T> implements Serializable {
        static final IdentityConverter INSTANCE = new IdentityConverter();
        private static final long serialVersionUID = 0;

        private IdentityConverter() {
        }

        /* access modifiers changed from: protected */
        public T doForward(T t) {
            return t;
        }

        /* access modifiers changed from: protected */
        public T doBackward(T t) {
            return t;
        }

        public IdentityConverter<T> reverse() {
            return this;
        }

        /* access modifiers changed from: package-private */
        public <S> Converter<T, S> doAndThen(Converter<T, S> otherConverter) {
            return (Converter) Preconditions.checkNotNull(otherConverter, "otherConverter");
        }

        public String toString() {
            return "Converter.identity()";
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }
}
