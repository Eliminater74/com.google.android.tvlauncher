package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;

import java.io.File;
import java.util.List;

class ResourceCacheGenerator implements DataFetcherGenerator, DataFetcher.DataCallback<Object> {
    /* renamed from: cb */
    private final DataFetcherGenerator.FetcherReadyCallback f55cb;
    private final DecodeHelper<?> helper;
    private File cacheFile;
    private ResourceCacheKey currentKey;
    private volatile ModelLoader.LoadData<?> loadData;
    private int modelLoaderIndex;
    private List<ModelLoader<File, ?>> modelLoaders;
    private int resourceClassIndex = -1;
    private int sourceIdIndex;
    private Key sourceKey;

    ResourceCacheGenerator(DecodeHelper<?> helper2, DataFetcherGenerator.FetcherReadyCallback cb) {
        this.helper = helper2;
        this.f55cb = cb;
    }

    public boolean startNext() {
        List<Key> sourceIds = this.helper.getCacheKeys();
        boolean z = false;
        if (sourceIds.isEmpty()) {
            return false;
        }
        List<Class<?>> resourceClasses = this.helper.getRegisteredResourceClasses();
        if (!resourceClasses.isEmpty()) {
            while (true) {
                if (this.modelLoaders == null || !hasNextModelLoader()) {
                    this.resourceClassIndex++;
                    if (this.resourceClassIndex >= resourceClasses.size()) {
                        this.sourceIdIndex++;
                        if (this.sourceIdIndex >= sourceIds.size()) {
                            return z;
                        }
                        this.resourceClassIndex = z ? 1 : 0;
                    }
                    Key sourceId = sourceIds.get(this.sourceIdIndex);
                    Class<?> resourceClass = resourceClasses.get(this.resourceClassIndex);
                    ResourceCacheKey resourceCacheKey = r5;
                    ResourceCacheKey resourceCacheKey2 = new ResourceCacheKey(this.helper.getArrayPool(), sourceId, this.helper.getSignature(), this.helper.getWidth(), this.helper.getHeight(), this.helper.getTransformation(resourceClass), resourceClass, this.helper.getOptions());
                    this.currentKey = resourceCacheKey;
                    this.cacheFile = this.helper.getDiskCache().get(this.currentKey);
                    File file = this.cacheFile;
                    if (file != null) {
                        this.sourceKey = sourceId;
                        this.modelLoaders = this.helper.getModelLoaders(file);
                        z = false;
                        this.modelLoaderIndex = 0;
                    } else {
                        z = false;
                    }
                } else {
                    this.loadData = null;
                    boolean started = false;
                    while (!started && hasNextModelLoader()) {
                        List<ModelLoader<File, ?>> list = this.modelLoaders;
                        int i = this.modelLoaderIndex;
                        this.modelLoaderIndex = i + 1;
                        this.loadData = list.get(i).buildLoadData(this.cacheFile, this.helper.getWidth(), this.helper.getHeight(), this.helper.getOptions());
                        if (this.loadData != null && this.helper.hasLoadPath(this.loadData.fetcher.getDataClass())) {
                            started = true;
                            this.loadData.fetcher.loadData(this.helper.getPriority(), this);
                        }
                    }
                    return started;
                }
            }
        } else if (File.class.equals(this.helper.getTranscodeClass())) {
            return false;
        } else {
            String valueOf = String.valueOf(this.helper.getModelClass());
            String valueOf2 = String.valueOf(this.helper.getTranscodeClass());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 38 + String.valueOf(valueOf2).length());
            sb.append("Failed to find any load path from ");
            sb.append(valueOf);
            sb.append(" to ");
            sb.append(valueOf2);
            throw new IllegalStateException(sb.toString());
        }
    }

    private boolean hasNextModelLoader() {
        return this.modelLoaderIndex < this.modelLoaders.size();
    }

    public void cancel() {
        ModelLoader.LoadData<?> local = this.loadData;
        if (local != null) {
            local.fetcher.cancel();
        }
    }

    public void onDataReady(Object data) {
        this.f55cb.onDataFetcherReady(this.sourceKey, data, this.loadData.fetcher, DataSource.RESOURCE_DISK_CACHE, this.currentKey);
    }

    public void onLoadFailed(@NonNull Exception e) {
        this.f55cb.onDataFetcherFailed(this.currentKey, e, this.loadData.fetcher, DataSource.RESOURCE_DISK_CACHE);
    }
}
