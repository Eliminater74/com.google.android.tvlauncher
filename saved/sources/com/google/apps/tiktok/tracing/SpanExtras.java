package com.google.apps.tiktok.tracing;

import android.support.p001v4.util.SimpleArrayMap;
import com.google.apps.tiktok.tracing.SpanExtra;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotMock;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
@DoNotMock
public abstract class SpanExtras {
    @Nullable
    private final SpanExtras delegate;
    /* access modifiers changed from: private */
    public boolean isFrozen;
    /* access modifiers changed from: private */
    public final SimpleArrayMap<SpanExtraKey<?>, Object> map;

    @DoNotMock
    public interface Builder {
        SpanExtras build();

        @CanIgnoreReturnValue
        <T> Builder put(SpanExtraKey<T> spanExtraKey, T t);
    }

    public static <T> SpanExtra<T> getSpanExtra(SpanExtraKey<T> key, SpanExtras extras, TracingRestricted restricted) {
        Preconditions.checkNotNull(restricted);
        T result = extras.get(key);
        if (result == null) {
            return SpanExtra.create(SpanExtra.State.UNSET);
        }
        return SpanExtra.create((Object) result);
    }

    static final class SpanExtrasImpl extends SpanExtras implements Builder {
        static final SpanExtras EMPTY_EXTRAS = new SpanExtrasImpl(null, new SimpleArrayMap(0)).freeze();

        private SpanExtrasImpl(SpanExtras delegate, SimpleArrayMap<SpanExtraKey<?>, Object> map) {
            super(map);
        }

        public SpanExtras build() {
            return freeze();
        }

        @CanIgnoreReturnValue
        public <T> Builder put(SpanExtraKey<T> key, T value) {
            Preconditions.checkState(!this.isFrozen, "Can't mutate after handing to trace");
            Preconditions.checkNotNull(value);
            Preconditions.checkState(!containsKey(key), "Key already present");
            this.map.put(key, value);
            return this;
        }
    }

    public static Builder newBuilder() {
        return SpanExtrasImpl.EMPTY_EXTRAS.toBuilder();
    }

    public final Builder toBuilder() {
        return new SpanExtrasImpl(new SimpleArrayMap());
    }

    public static SpanExtras empty() {
        return SpanExtrasImpl.EMPTY_EXTRAS;
    }

    private SpanExtras(@Nullable SpanExtras delegate2, SimpleArrayMap<SpanExtraKey<?>, Object> map2) {
        this.isFrozen = false;
        if (delegate2 != null) {
            Preconditions.checkArgument(delegate2.isFrozen);
        }
        this.delegate = delegate2;
        this.map = map2;
    }

    static SpanExtras copyCombine(SpanExtras first, SpanExtras second) {
        if (first.isEmpty()) {
            return second;
        }
        if (second.isEmpty()) {
            return first;
        }
        return copyCombine(ImmutableSet.m151of(first, second));
    }

    static SpanExtras copyCombine(Set<SpanExtras> extras) {
        SpanExtras spanExtras;
        SpanExtras spanExtras2;
        if (extras.isEmpty()) {
            return SpanExtrasImpl.EMPTY_EXTRAS;
        }
        if (extras.size() == 1) {
            return extras.iterator().next();
        }
        int size = 0;
        for (SpanExtras extra : extras) {
            do {
                size += extra.map.size();
                spanExtras2 = extra.delegate;
                extra = spanExtras2;
            } while (spanExtras2 != null);
        }
        if (size == 0) {
            return SpanExtrasImpl.EMPTY_EXTRAS;
        }
        SimpleArrayMap<SpanExtraKey<?>, Object> map2 = new SimpleArrayMap<>(size);
        for (SpanExtras extra2 : extras) {
            do {
                for (int i = 0; i < extra2.map.size(); i++) {
                    Preconditions.checkArgument(map2.put(extra2.map.keyAt(i), extra2.map.valueAt(i)) == null, "Duplicate bindings: %s", extra2.map.keyAt(i));
                }
                spanExtras = extra2.delegate;
                extra2 = spanExtras;
            } while (spanExtras != null);
        }
        return new SpanExtrasImpl(map2).freeze();
    }

    /* access modifiers changed from: package-private */
    public final SpanExtras freeze() {
        if (!this.isFrozen) {
            this.isFrozen = true;
            if (this.delegate == null || !this.map.isEmpty()) {
                return this;
            }
            return this.delegate;
        }
        throw new IllegalStateException("Already frozen");
    }

    /* access modifiers changed from: package-private */
    public final boolean isEmpty() {
        return this == SpanExtrasImpl.EMPTY_EXTRAS;
    }

    /* access modifiers changed from: package-private */
    public final boolean containsKey(SpanExtraKey<?> key) {
        SpanExtras spanExtras;
        return this.map.containsKey(key) || ((spanExtras = this.delegate) != null && spanExtras.containsKey(key));
    }

    /* access modifiers changed from: package-private */
    public final boolean isFrozen() {
        return this.isFrozen;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final <T> T get(SpanExtraKey<T> key) {
        SpanExtras spanExtras;
        Preconditions.checkState(this.isFrozen);
        T object = this.map.get(key);
        if (object != null || (spanExtras = this.delegate) == null) {
            return object;
        }
        return spanExtras.get(key);
    }
}
