package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.Map;

@GwtCompatible
public final class Functions {
    private Functions() {
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE;
    }

    public static <E> Function<E, E> identity() {
        return IdentityFunction.INSTANCE;
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new FunctionForMapNoDefault(map);
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @NullableDecl V defaultValue) {
        return new ForMapWithDefault(map, defaultValue);
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, ? extends B> f) {
        return new FunctionComposition(g, f);
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new PredicateFunction(predicate);
    }

    public static <E> Function<Object, E> constant(@NullableDecl E value) {
        return new ConstantFunction(value);
    }

    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new SupplierFunction(supplier);
    }

    private enum ToStringFunction implements Function<Object, String> {
        INSTANCE;

        public String apply(Object o) {
            Preconditions.checkNotNull(o);
            return o.toString();
        }

        public String toString() {
            return "Functions.toStringFunction()";
        }
    }

    private enum IdentityFunction implements Function<Object, Object> {
        INSTANCE;

        @NullableDecl
        public Object apply(@NullableDecl Object o) {
            return o;
        }

        public String toString() {
            return "Functions.identity()";
        }
    }

    private static class FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final Map<K, V> map;

        FunctionForMapNoDefault(Map<K, V> map2) {
            this.map = (Map) Preconditions.checkNotNull(map2);
        }

        public V apply(@NullableDecl K key) {
            V result = this.map.get(key);
            Preconditions.checkArgument(result != null || this.map.containsKey(key), "Key '%s' not present in map", key);
            return result;
        }

        public boolean equals(@NullableDecl Object o) {
            if (o instanceof FunctionForMapNoDefault) {
                return this.map.equals(((FunctionForMapNoDefault) o).map);
            }
            return false;
        }

        public int hashCode() {
            return this.map.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.map);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
            sb.append("Functions.forMap(");
            sb.append(valueOf);
            sb.append(")");
            return sb.toString();
        }
    }

    private static class ForMapWithDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        @NullableDecl
        final V defaultValue;
        final Map<K, ? extends V> map;

        ForMapWithDefault(Map<K, ? extends V> map2, @NullableDecl V defaultValue2) {
            this.map = (Map) Preconditions.checkNotNull(map2);
            this.defaultValue = defaultValue2;
        }

        public V apply(@NullableDecl K key) {
            V result = this.map.get(key);
            return (result != null || this.map.containsKey(key)) ? result : this.defaultValue;
        }

        public boolean equals(@NullableDecl Object o) {
            if (!(o instanceof ForMapWithDefault)) {
                return false;
            }
            ForMapWithDefault<?, ?> that = (ForMapWithDefault) o;
            if (!this.map.equals(that.map) || !Objects.equal(this.defaultValue, that.defaultValue)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.map, this.defaultValue);
        }

        public String toString() {
            String valueOf = String.valueOf(this.map);
            String valueOf2 = String.valueOf(this.defaultValue);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 33 + String.valueOf(valueOf2).length());
            sb.append("Functions.forMap(");
            sb.append(valueOf);
            sb.append(", defaultValue=");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }

    private static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable {
        private static final long serialVersionUID = 0;

        /* renamed from: f */
        private final Function<A, ? extends B> f161f;

        /* renamed from: g */
        private final Function<B, C> f162g;

        public FunctionComposition(Function<B, C> g, Function<A, ? extends B> f) {
            this.f162g = (Function) Preconditions.checkNotNull(g);
            this.f161f = (Function) Preconditions.checkNotNull(f);
        }

        public C apply(@NullableDecl A a) {
            return this.f162g.apply(this.f161f.apply(a));
        }

        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof FunctionComposition)) {
                return false;
            }
            FunctionComposition<?, ?, ?> that = (FunctionComposition) obj;
            if (!this.f161f.equals(that.f161f) || !this.f162g.equals(that.f162g)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.f161f.hashCode() ^ this.f162g.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.f162g);
            String valueOf2 = String.valueOf(this.f161f);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 2 + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append("(");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }

    private static class PredicateFunction<T> implements Function<T, Boolean>, Serializable {
        private static final long serialVersionUID = 0;
        private final Predicate<T> predicate;

        private PredicateFunction(Predicate<T> predicate2) {
            this.predicate = (Predicate) Preconditions.checkNotNull(predicate2);
        }

        public Boolean apply(@NullableDecl T t) {
            return Boolean.valueOf(this.predicate.apply(t));
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof PredicateFunction) {
                return this.predicate.equals(((PredicateFunction) obj).predicate);
            }
            return false;
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.predicate);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
            sb.append("Functions.forPredicate(");
            sb.append(valueOf);
            sb.append(")");
            return sb.toString();
        }
    }

    private static class ConstantFunction<E> implements Function<Object, E>, Serializable {
        private static final long serialVersionUID = 0;
        @NullableDecl
        private final E value;

        public ConstantFunction(@NullableDecl E value2) {
            this.value = value2;
        }

        public E apply(@NullableDecl Object from) {
            return this.value;
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof ConstantFunction) {
                return Objects.equal(this.value, ((ConstantFunction) obj).value);
            }
            return false;
        }

        public int hashCode() {
            E e = this.value;
            if (e == null) {
                return 0;
            }
            return e.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.value);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
            sb.append("Functions.constant(");
            sb.append(valueOf);
            sb.append(")");
            return sb.toString();
        }
    }

    private static class SupplierFunction<T> implements Function<Object, T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Supplier<T> supplier;

        private SupplierFunction(Supplier<T> supplier2) {
            this.supplier = (Supplier) Preconditions.checkNotNull(supplier2);
        }

        public T apply(@NullableDecl Object input) {
            return this.supplier.get();
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof SupplierFunction) {
                return this.supplier.equals(((SupplierFunction) obj).supplier);
            }
            return false;
        }

        public int hashCode() {
            return this.supplier.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.supplier);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
            sb.append("Functions.forSupplier(");
            sb.append(valueOf);
            sb.append(")");
            return sb.toString();
        }
    }
}
