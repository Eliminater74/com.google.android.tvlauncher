package com.bumptech.glide.load.engine;

import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class DecodeHelper<Transcode> {
    private final List<Key> cacheKeys = new ArrayList();
    private DecodeJob.DiskCacheProvider diskCacheProvider;
    private DiskCacheStrategy diskCacheStrategy;
    private GlideContext glideContext;
    private int height;
    private boolean isCacheKeysSet;
    private boolean isLoadDataSet;
    private boolean isScaleOnlyOrNoTransform;
    private boolean isTransformationRequired;
    private final List<ModelLoader.LoadData<?>> loadData = new ArrayList();
    private Object model;
    private Options options;
    private Priority priority;
    private Class<?> resourceClass;
    private Key signature;
    private Class<Transcode> transcodeClass;
    private Map<Class<?>, Transformation<?>> transformations;
    private int width;

    DecodeHelper() {
    }

    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.Class<Transcode>, java.lang.Class<R>] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <R> void init(com.bumptech.glide.GlideContext r1, java.lang.Object r2, com.bumptech.glide.load.Key r3, int r4, int r5, com.bumptech.glide.load.engine.DiskCacheStrategy r6, java.lang.Class<?> r7, java.lang.Class<R> r8, com.bumptech.glide.Priority r9, com.bumptech.glide.load.Options r10, java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.Transformation<?>> r11, boolean r12, boolean r13, com.bumptech.glide.load.engine.DecodeJob.DiskCacheProvider r14) {
        /*
            r0 = this;
            r0.glideContext = r1
            r0.model = r2
            r0.signature = r3
            r0.width = r4
            r0.height = r5
            r0.diskCacheStrategy = r6
            r0.resourceClass = r7
            r0.diskCacheProvider = r14
            r0.transcodeClass = r8
            r0.priority = r9
            r0.options = r10
            r0.transformations = r11
            r0.isTransformationRequired = r12
            r0.isScaleOnlyOrNoTransform = r13
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.DecodeHelper.init(com.bumptech.glide.GlideContext, java.lang.Object, com.bumptech.glide.load.Key, int, int, com.bumptech.glide.load.engine.DiskCacheStrategy, java.lang.Class, java.lang.Class, com.bumptech.glide.Priority, com.bumptech.glide.load.Options, java.util.Map, boolean, boolean, com.bumptech.glide.load.engine.DecodeJob$DiskCacheProvider):void");
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.glideContext = null;
        this.model = null;
        this.signature = null;
        this.resourceClass = null;
        this.transcodeClass = null;
        this.options = null;
        this.priority = null;
        this.transformations = null;
        this.diskCacheStrategy = null;
        this.loadData.clear();
        this.isLoadDataSet = false;
        this.cacheKeys.clear();
        this.isCacheKeysSet = false;
    }

    /* access modifiers changed from: package-private */
    public DiskCache getDiskCache() {
        return this.diskCacheProvider.getDiskCache();
    }

    /* access modifiers changed from: package-private */
    public DiskCacheStrategy getDiskCacheStrategy() {
        return this.diskCacheStrategy;
    }

    /* access modifiers changed from: package-private */
    public Priority getPriority() {
        return this.priority;
    }

    /* access modifiers changed from: package-private */
    public Options getOptions() {
        return this.options;
    }

    /* access modifiers changed from: package-private */
    public Key getSignature() {
        return this.signature;
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return this.width;
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        return this.height;
    }

    /* access modifiers changed from: package-private */
    public ArrayPool getArrayPool() {
        return this.glideContext.getArrayPool();
    }

    /* access modifiers changed from: package-private */
    public Class<?> getTranscodeClass() {
        return this.transcodeClass;
    }

    /* access modifiers changed from: package-private */
    public Class<?> getModelClass() {
        return this.model.getClass();
    }

    /* access modifiers changed from: package-private */
    public List<Class<?>> getRegisteredResourceClasses() {
        return this.glideContext.getRegistry().getRegisteredResourceClasses(this.model.getClass(), this.resourceClass, this.transcodeClass);
    }

    /* access modifiers changed from: package-private */
    public boolean hasLoadPath(Class<?> dataClass) {
        return getLoadPath(dataClass) != null;
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.Class<Data>, java.lang.Class] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <Data> com.bumptech.glide.load.engine.LoadPath<Data, ?, Transcode> getLoadPath(java.lang.Class<Data> r4) {
        /*
            r3 = this;
            com.bumptech.glide.GlideContext r0 = r3.glideContext
            com.bumptech.glide.Registry r0 = r0.getRegistry()
            java.lang.Class<?> r1 = r3.resourceClass
            java.lang.Class<Transcode> r2 = r3.transcodeClass
            com.bumptech.glide.load.engine.LoadPath r0 = r0.getLoadPath(r4, r1, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.DecodeHelper.getLoadPath(java.lang.Class):com.bumptech.glide.load.engine.LoadPath");
    }

    /* access modifiers changed from: package-private */
    public boolean isScaleOnlyOrNoTransform() {
        return this.isScaleOnlyOrNoTransform;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: com.bumptech.glide.load.Transformation<Z>} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <Z> com.bumptech.glide.load.Transformation<Z> getTransformation(java.lang.Class<Z> r6) {
        /*
            r5 = this;
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.Transformation<?>> r0 = r5.transformations
            java.lang.Object r0 = r0.get(r6)
            com.bumptech.glide.load.Transformation r0 = (com.bumptech.glide.load.Transformation) r0
            if (r0 != 0) goto L_0x0035
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.Transformation<?>> r1 = r5.transformations
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0014:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0035
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            java.lang.Class r3 = (java.lang.Class) r3
            boolean r3 = r3.isAssignableFrom(r6)
            if (r3 == 0) goto L_0x0034
            java.lang.Object r1 = r2.getValue()
            r0 = r1
            com.bumptech.glide.load.Transformation r0 = (com.bumptech.glide.load.Transformation) r0
            goto L_0x0035
        L_0x0034:
            goto L_0x0014
        L_0x0035:
            if (r0 != 0) goto L_0x0073
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.Transformation<?>> r1 = r5.transformations
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x006e
            boolean r1 = r5.isTransformationRequired
            if (r1 != 0) goto L_0x0044
            goto L_0x006e
        L_0x0044:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = java.lang.String.valueOf(r6)
            java.lang.String r3 = java.lang.String.valueOf(r2)
            int r3 = r3.length()
            int r3 = r3 + 115
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            java.lang.String r3 = "Missing transformation for "
            r4.append(r3)
            r4.append(r2)
            java.lang.String r2 = ". If you wish to ignore unknown resource types, use the optional transformation methods."
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            r1.<init>(r2)
            throw r1
        L_0x006e:
            com.bumptech.glide.load.resource.UnitTransformation r1 = com.bumptech.glide.load.resource.UnitTransformation.get()
            return r1
        L_0x0073:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.DecodeHelper.getTransformation(java.lang.Class):com.bumptech.glide.load.Transformation");
    }

    /* access modifiers changed from: package-private */
    public boolean isResourceEncoderAvailable(Resource<?> resource) {
        return this.glideContext.getRegistry().isResourceEncoderAvailable(resource);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.bumptech.glide.load.engine.Resource<Z>, com.bumptech.glide.load.engine.Resource] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <Z> com.bumptech.glide.load.ResourceEncoder<Z> getResultEncoder(com.bumptech.glide.load.engine.Resource<Z> r2) {
        /*
            r1 = this;
            com.bumptech.glide.GlideContext r0 = r1.glideContext
            com.bumptech.glide.Registry r0 = r0.getRegistry()
            com.bumptech.glide.load.ResourceEncoder r0 = r0.getResultEncoder(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.DecodeHelper.getResultEncoder(com.bumptech.glide.load.engine.Resource):com.bumptech.glide.load.ResourceEncoder");
    }

    /* access modifiers changed from: package-private */
    public List<ModelLoader<File, ?>> getModelLoaders(File file) throws Registry.NoModelLoaderAvailableException {
        return this.glideContext.getRegistry().getModelLoaders(file);
    }

    /* access modifiers changed from: package-private */
    public boolean isSourceKey(Key key) {
        List<ModelLoader.LoadData<?>> loadData2 = getLoadData();
        int size = loadData2.size();
        for (int i = 0; i < size; i++) {
            if (loadData2.get(i).sourceKey.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public List<ModelLoader.LoadData<?>> getLoadData() {
        if (!this.isLoadDataSet) {
            this.isLoadDataSet = true;
            this.loadData.clear();
            List<ModelLoader<Object, ?>> modelLoaders = this.glideContext.getRegistry().getModelLoaders(this.model);
            int size = modelLoaders.size();
            for (int i = 0; i < size; i++) {
                ModelLoader.LoadData<?> current = modelLoaders.get(i).buildLoadData(this.model, this.width, this.height, this.options);
                if (current != null) {
                    this.loadData.add(current);
                }
            }
        }
        return this.loadData;
    }

    /* access modifiers changed from: package-private */
    public List<Key> getCacheKeys() {
        if (!this.isCacheKeysSet) {
            this.isCacheKeysSet = true;
            this.cacheKeys.clear();
            List<ModelLoader.LoadData<?>> loadData2 = getLoadData();
            int size = loadData2.size();
            for (int i = 0; i < size; i++) {
                ModelLoader.LoadData<?> data = loadData2.get(i);
                if (!this.cacheKeys.contains(data.sourceKey)) {
                    this.cacheKeys.add(data.sourceKey);
                }
                for (int j = 0; j < data.alternateKeys.size(); j++) {
                    if (!this.cacheKeys.contains(data.alternateKeys.get(j))) {
                        this.cacheKeys.add(data.alternateKeys.get(j));
                    }
                }
            }
        }
        return this.cacheKeys;
    }

    /* access modifiers changed from: package-private */
    public <X> Encoder<X> getSourceEncoder(X data) throws Registry.NoSourceEncoderAvailableException {
        return this.glideContext.getRegistry().getSourceEncoder(data);
    }
}
