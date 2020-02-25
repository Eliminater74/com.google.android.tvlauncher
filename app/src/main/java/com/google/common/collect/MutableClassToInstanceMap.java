package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Primitives;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@GwtIncompatible
public final class MutableClassToInstanceMap<B> extends ForwardingMap<Class<? extends B>, B> implements ClassToInstanceMap<B>, Serializable {
    private final Map<Class<? extends B>, B> delegate;

    private MutableClassToInstanceMap(Map<Class<? extends B>, B> delegate2) {
        this.delegate = (Map) Preconditions.checkNotNull(delegate2);
    }

    public static <B> MutableClassToInstanceMap<B> create() {
        return new MutableClassToInstanceMap<>(new HashMap());
    }

    public static <B> MutableClassToInstanceMap<B> create(Map<Class<? extends B>, B> backingMap) {
        return new MutableClassToInstanceMap<>(backingMap);
    }

    static <B> Map.Entry<Class<? extends B>, B> checkedEntry(final Map.Entry<Class<? extends B>, B> entry) {
        return new ForwardingMapEntry<Class<? extends B>, B>() {
            /* access modifiers changed from: protected */
            public Map.Entry<Class<? extends B>, B> delegate() {
                return entry;
            }

            public B setValue(B value) {
                return super.setValue(MutableClassToInstanceMap.cast((Class) getKey(), value));
            }
        };
    }

    /* access modifiers changed from: private */
    @CanIgnoreReturnValue
    public static <B, T extends B> T cast(Class<T> type, B value) {
        return Primitives.wrap(type).cast(value);
    }

    /* access modifiers changed from: protected */
    public Map<Class<? extends B>, B> delegate() {
        return this.delegate;
    }

    public Set<Map.Entry<Class<? extends B>, B>> entrySet() {
        return new ForwardingSet<Map.Entry<Class<? extends B>, B>>() {
            /* access modifiers changed from: protected */
            public Set<Map.Entry<Class<? extends B>, B>> delegate() {
                return MutableClassToInstanceMap.this.delegate().entrySet();
            }

            public Iterator<Map.Entry<Class<? extends B>, B>> iterator() {
                return new TransformedIterator<Map.Entry<Class<? extends B>, B>, Map.Entry<Class<? extends B>, B>>(this, delegate().iterator()) {
                    /* access modifiers changed from: package-private */
                    public Map.Entry<Class<? extends B>, B> transform(Map.Entry<Class<? extends B>, B> from) {
                        return MutableClassToInstanceMap.checkedEntry(from);
                    }
                };
            }

            public Object[] toArray() {
                return standardToArray();
            }

            public <T> T[] toArray(T[] array) {
                return standardToArray(array);
            }
        };
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ForwardingMap.put(java.lang.Object, java.lang.Object):V
     arg types: [java.lang.Class<? extends B>, ? extends B]
     candidates:
      com.google.common.collect.MutableClassToInstanceMap.put(java.lang.Class, java.lang.Object):B
      com.google.common.collect.ForwardingMap.put(java.lang.Object, java.lang.Object):V */
    @CanIgnoreReturnValue
    public B put(Class<? extends B> key, B value) {
        return super.put((Object) key, (Object) cast(key, value));
    }

    public void putAll(Map<? extends Class<? extends B>, ? extends B> map) {
        Map<Class<? extends B>, B> copy = new LinkedHashMap<>(map);
        for (Map.Entry<? extends Class<? extends B>, B> entry : copy.entrySet()) {
            cast((Class) entry.getKey(), entry.getValue());
        }
        super.putAll(copy);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.MutableClassToInstanceMap.put(java.lang.Class, java.lang.Object):B
     arg types: [java.lang.Class<T>, T]
     candidates:
      com.google.common.collect.MutableClassToInstanceMap.put(java.lang.Object, java.lang.Object):java.lang.Object
      com.google.common.collect.ForwardingMap.put(java.lang.Object, java.lang.Object):V
      ClspMth{java.util.Map.put(java.lang.Object, java.lang.Object):V}
      ClspMth{java.util.Map.put(java.lang.Object, java.lang.Object):V}
      com.google.common.collect.MutableClassToInstanceMap.put(java.lang.Class, java.lang.Object):B */
    @CanIgnoreReturnValue
    public <T extends B> T putInstance(Class<T> type, T value) {
        return cast(type, put((Class) type, (Object) value));
    }

    public <T extends B> T getInstance(Class<T> type) {
        return cast(type, get(type));
    }

    private Object writeReplace() {
        return new SerializedForm(delegate());
    }

    private static final class SerializedForm<B> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Map<Class<? extends B>, B> backingMap;

        SerializedForm(Map<Class<? extends B>, B> backingMap2) {
            this.backingMap = backingMap2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return MutableClassToInstanceMap.create(this.backingMap);
        }
    }
}
