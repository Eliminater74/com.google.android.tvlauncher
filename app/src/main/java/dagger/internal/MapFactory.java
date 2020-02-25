package dagger.internal;

import java.util.Collections;
import java.util.Map;

import javax.inject.Provider;

public final class MapFactory<K, V> extends AbstractMapFactory<K, V, V> {
    private static final Provider<Map<Object, Object>> EMPTY = InstanceFactory.create(Collections.emptyMap());

    private MapFactory(Map<K, Provider<V>> map) {
        super(map);
    }

    public static <K, V> Builder<K, V> builder(int size) {
        return new Builder<>(size);
    }

    public static <K, V> Provider<Map<K, V>> emptyMapProvider() {
        return EMPTY;
    }

    public Map<K, V> get() {
        Map<K, V> result = DaggerCollections.newLinkedHashMapWithExpectedSize(contributingMap().size());
        for (Map.Entry<K, Provider<V>> entry : contributingMap().entrySet()) {
            result.put(entry.getKey(), entry.getValue().get());
        }
        return Collections.unmodifiableMap(result);
    }

    public static final class Builder<K, V> extends AbstractMapFactory.Builder<K, V, V> {
        private Builder(int size) {
            super(size);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: dagger.internal.AbstractMapFactory.Builder.put(java.lang.Object, javax.inject.Provider):dagger.internal.AbstractMapFactory$Builder<K, V, V2>
         arg types: [K, javax.inject.Provider<V>]
         candidates:
          dagger.internal.MapFactory.Builder.put(java.lang.Object, javax.inject.Provider):dagger.internal.MapFactory$Builder<K, V>
          dagger.internal.AbstractMapFactory.Builder.put(java.lang.Object, javax.inject.Provider):dagger.internal.AbstractMapFactory$Builder<K, V, V2> */
        public Builder<K, V> put(K key, Provider<V> providerOfValue) {
            super.put((Object) key, (Provider) providerOfValue);
            return this;
        }

        public Builder<K, V> putAll(Provider<Map<K, V>> mapFactory) {
            super.putAll((Provider) mapFactory);
            return this;
        }

        public MapFactory<K, V> build() {
            return new MapFactory<>(this.map);
        }
    }
}
