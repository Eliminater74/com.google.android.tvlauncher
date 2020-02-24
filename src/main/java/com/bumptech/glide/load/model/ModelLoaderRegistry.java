package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.util.Pools;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelLoaderRegistry {
    private final ModelLoaderCache cache;
    private final MultiModelLoaderFactory multiModelLoaderFactory;

    public ModelLoaderRegistry(@NonNull Pools.Pool<List<Throwable>> throwableListPool) {
        this(new MultiModelLoaderFactory(throwableListPool));
    }

    private ModelLoaderRegistry(@NonNull MultiModelLoaderFactory multiModelLoaderFactory2) {
        this.cache = new ModelLoaderCache();
        this.multiModelLoaderFactory = multiModelLoaderFactory2;
    }

    public synchronized <Model, Data> void append(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> factory) {
        this.multiModelLoaderFactory.append(modelClass, dataClass, factory);
        this.cache.clear();
    }

    public synchronized <Model, Data> void prepend(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> factory) {
        this.multiModelLoaderFactory.prepend(modelClass, dataClass, factory);
        this.cache.clear();
    }

    public synchronized <Model, Data> void remove(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass) {
        tearDown(this.multiModelLoaderFactory.remove(modelClass, dataClass));
        this.cache.clear();
    }

    public synchronized <Model, Data> void replace(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> factory) {
        tearDown(this.multiModelLoaderFactory.replace(modelClass, dataClass, factory));
        this.cache.clear();
    }

    private <Model, Data> void tearDown(@NonNull List<ModelLoaderFactory<? extends Model, ? extends Data>> factories) {
        for (ModelLoaderFactory<? extends Model, ? extends Data> factory : factories) {
            factory.teardown();
        }
    }

    @NonNull
    public <A> List<ModelLoader<A, ?>> getModelLoaders(@NonNull A model) {
        List<ModelLoader<A, ?>> modelLoaders = getModelLoadersForClass(getClass(model));
        int size = modelLoaders.size();
        boolean isEmpty = true;
        List<ModelLoader<A, ?>> filteredLoaders = Collections.emptyList();
        for (int i = 0; i < size; i++) {
            ModelLoader<A, ?> loader = modelLoaders.get(i);
            if (loader.handles(model)) {
                if (isEmpty) {
                    filteredLoaders = new ArrayList<>(size - i);
                    isEmpty = false;
                }
                filteredLoaders.add(loader);
            }
        }
        return filteredLoaders;
    }

    public synchronized <Model, Data> ModelLoader<Model, Data> build(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass) {
        return this.multiModelLoaderFactory.build(modelClass, dataClass);
    }

    @NonNull
    public synchronized List<Class<?>> getDataClasses(@NonNull Class<?> modelClass) {
        return this.multiModelLoaderFactory.getDataClasses(modelClass);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Class<A>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized <A> java.util.List<com.bumptech.glide.load.model.ModelLoader<A, ?>> getModelLoadersForClass(@android.support.annotation.NonNull java.lang.Class<A> r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            com.bumptech.glide.load.model.ModelLoaderRegistry$ModelLoaderCache r0 = r2.cache     // Catch:{ all -> 0x001b }
            java.util.List r0 = r0.get(r3)     // Catch:{ all -> 0x001b }
            if (r0 != 0) goto L_0x0019
            com.bumptech.glide.load.model.MultiModelLoaderFactory r1 = r2.multiModelLoaderFactory     // Catch:{ all -> 0x001b }
            java.util.List r1 = r1.build(r3)     // Catch:{ all -> 0x001b }
            java.util.List r1 = java.util.Collections.unmodifiableList(r1)     // Catch:{ all -> 0x001b }
            r0 = r1
            com.bumptech.glide.load.model.ModelLoaderRegistry$ModelLoaderCache r1 = r2.cache     // Catch:{ all -> 0x001b }
            r1.put(r3, r0)     // Catch:{ all -> 0x001b }
        L_0x0019:
            monitor-exit(r2)
            return r0
        L_0x001b:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.model.ModelLoaderRegistry.getModelLoadersForClass(java.lang.Class):java.util.List");
    }

    @NonNull
    private static <A> Class<A> getClass(@NonNull A model) {
        return model.getClass();
    }

    private static class ModelLoaderCache {
        private final Map<Class<?>, Entry<?>> cachedModelLoaders = new HashMap();

        ModelLoaderCache() {
        }

        public void clear() {
            this.cachedModelLoaders.clear();
        }

        public <Model> void put(Class<Model> modelClass, List<ModelLoader<Model, ?>> loaders) {
            if (this.cachedModelLoaders.put(modelClass, new Entry(loaders)) != null) {
                String valueOf = String.valueOf(modelClass);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34);
                sb.append("Already cached loaders for model: ");
                sb.append(valueOf);
                throw new IllegalStateException(sb.toString());
            }
        }

        @Nullable
        public <Model> List<ModelLoader<Model, ?>> get(Class<Model> modelClass) {
            Entry<Model> entry = this.cachedModelLoaders.get(modelClass);
            if (entry == null) {
                return null;
            }
            return entry.loaders;
        }

        private static class Entry<Model> {
            final List<ModelLoader<Model, ?>> loaders;

            public Entry(List<ModelLoader<Model, ?>> loaders2) {
                this.loaders = loaders2;
            }
        }
    }
}
