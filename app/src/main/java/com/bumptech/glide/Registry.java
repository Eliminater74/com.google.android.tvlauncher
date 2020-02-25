package com.bumptech.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.util.Pools;

import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.data.DataRewinderRegistry;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.LoadPath;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.ModelLoaderRegistry;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.load.resource.transcode.TranscoderRegistry;
import com.bumptech.glide.provider.EncoderRegistry;
import com.bumptech.glide.provider.ImageHeaderParserRegistry;
import com.bumptech.glide.provider.LoadPathCache;
import com.bumptech.glide.provider.ModelToResourceClassCache;
import com.bumptech.glide.provider.ResourceDecoderRegistry;
import com.bumptech.glide.provider.ResourceEncoderRegistry;
import com.bumptech.glide.util.pool.FactoryPools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Registry {
    public static final String BUCKET_BITMAP = "Bitmap";
    public static final String BUCKET_BITMAP_DRAWABLE = "BitmapDrawable";
    public static final String BUCKET_GIF = "Gif";
    private static final String BUCKET_APPEND_ALL = "legacy_append";
    private static final String BUCKET_PREPEND_ALL = "legacy_prepend_all";
    private final DataRewinderRegistry dataRewinderRegistry = new DataRewinderRegistry();
    private final ResourceDecoderRegistry decoderRegistry = new ResourceDecoderRegistry();
    private final EncoderRegistry encoderRegistry = new EncoderRegistry();
    private final ImageHeaderParserRegistry imageHeaderParserRegistry = new ImageHeaderParserRegistry();
    private final LoadPathCache loadPathCache = new LoadPathCache();
    private final ModelToResourceClassCache modelToResourceClassCache = new ModelToResourceClassCache();
    private final ResourceEncoderRegistry resourceEncoderRegistry = new ResourceEncoderRegistry();
    private final Pools.Pool<List<Throwable>> throwableListPool = FactoryPools.threadSafeList();
    private final ModelLoaderRegistry modelLoaderRegistry = new ModelLoaderRegistry(this.throwableListPool);
    private final TranscoderRegistry transcoderRegistry = new TranscoderRegistry();

    public Registry() {
        setResourceDecoderBucketPriorityList(Arrays.asList(BUCKET_GIF, BUCKET_BITMAP, BUCKET_BITMAP_DRAWABLE));
    }

    @Deprecated
    @NonNull
    public <Data> Registry register(@NonNull Class<Data> dataClass, @NonNull Encoder<Data> encoder) {
        return append(dataClass, encoder);
    }

    @NonNull
    public <Data> Registry append(@NonNull Class cls, @NonNull Encoder encoder) {
        this.encoderRegistry.append(cls, encoder);
        return this;
    }

    @NonNull
    public <Data> Registry prepend(@NonNull Class<Data> dataClass, @NonNull Encoder<Data> encoder) {
        this.encoderRegistry.prepend(dataClass, encoder);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry append(@NonNull Class cls, @NonNull Class cls2, @NonNull ResourceDecoder resourceDecoder) {
        append(BUCKET_APPEND_ALL, cls, cls2, resourceDecoder);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry append(@NonNull String bucket, @NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull ResourceDecoder<Data, TResource> decoder) {
        this.decoderRegistry.append(bucket, decoder, dataClass, resourceClass);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry prepend(@NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull ResourceDecoder<Data, TResource> decoder) {
        prepend(BUCKET_PREPEND_ALL, dataClass, resourceClass, decoder);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry prepend(@NonNull String bucket, @NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull ResourceDecoder<Data, TResource> decoder) {
        this.decoderRegistry.prepend(bucket, decoder, dataClass, resourceClass);
        return this;
    }

    @NonNull
    public final Registry setResourceDecoderBucketPriorityList(@NonNull List<String> buckets) {
        List<String> modifiedBuckets = new ArrayList<>(buckets.size());
        modifiedBuckets.addAll(buckets);
        modifiedBuckets.add(0, BUCKET_PREPEND_ALL);
        modifiedBuckets.add(BUCKET_APPEND_ALL);
        this.decoderRegistry.setBucketPriorityList(modifiedBuckets);
        return this;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.ResourceEncoder):com.bumptech.glide.Registry
     arg types: [java.lang.Class<TResource>, com.bumptech.glide.load.ResourceEncoder<TResource>]
     candidates:
      com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.Encoder):com.bumptech.glide.Registry
      com.bumptech.glide.Registry.append(java.lang.Class, com.bumptech.glide.load.ResourceEncoder):com.bumptech.glide.Registry */
    @Deprecated
    @NonNull
    public <TResource> Registry register(@NonNull Class<TResource> resourceClass, @NonNull ResourceEncoder<TResource> encoder) {
        return append((Class) resourceClass, (ResourceEncoder) encoder);
    }

    @NonNull
    public <TResource> Registry append(@NonNull Class cls, @NonNull ResourceEncoder resourceEncoder) {
        this.resourceEncoderRegistry.append(cls, resourceEncoder);
        return this;
    }

    @NonNull
    public <TResource> Registry prepend(@NonNull Class<TResource> resourceClass, @NonNull ResourceEncoder<TResource> encoder) {
        this.resourceEncoderRegistry.prepend(resourceClass, encoder);
        return this;
    }

    @NonNull
    public Registry register(@NonNull DataRewinder.Factory<?> factory) {
        this.dataRewinderRegistry.register(factory);
        return this;
    }

    @NonNull
    public <TResource, Transcode> Registry register(@NonNull Class<TResource> resourceClass, @NonNull Class<Transcode> transcodeClass, @NonNull ResourceTranscoder<TResource, Transcode> transcoder) {
        this.transcoderRegistry.register(resourceClass, transcodeClass, transcoder);
        return this;
    }

    @NonNull
    public Registry register(@NonNull ImageHeaderParser parser) {
        this.imageHeaderParserRegistry.add(parser);
        return this;
    }

    @NonNull
    public <Model, Data> Registry append(@NonNull Class cls, @NonNull Class cls2, @NonNull ModelLoaderFactory modelLoaderFactory) {
        this.modelLoaderRegistry.append(cls, cls2, modelLoaderFactory);
        return this;
    }

    @NonNull
    public <Model, Data> Registry prepend(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull ModelLoaderFactory<Model, Data> factory) {
        this.modelLoaderRegistry.prepend(modelClass, dataClass, factory);
        return this;
    }

    @NonNull
    public <Model, Data> Registry replace(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> factory) {
        this.modelLoaderRegistry.replace(modelClass, dataClass, factory);
        return this;
    }

    @Nullable
    public <Data, TResource, Transcode> LoadPath<Data, TResource, Transcode> getLoadPath(@NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull Class<Transcode> transcodeClass) {
        LoadPath<Data, TResource, Transcode> result = this.loadPathCache.get(dataClass, resourceClass, transcodeClass);
        if (this.loadPathCache.isEmptyLoadPath(result)) {
            return null;
        }
        if (result == null) {
            List<DecodePath<Data, TResource, Transcode>> decodePaths = getDecodePaths(dataClass, resourceClass, transcodeClass);
            if (decodePaths.isEmpty()) {
                result = null;
            } else {
                result = new LoadPath<>(dataClass, resourceClass, transcodeClass, decodePaths, this.throwableListPool);
            }
            this.loadPathCache.put(dataClass, resourceClass, transcodeClass, result);
        }
        return result;
    }

    @NonNull
    private <Data, TResource, Transcode> List<DecodePath<Data, TResource, Transcode>> getDecodePaths(@NonNull Class<Data> dataClass, @NonNull Class<TResource> resourceClass, @NonNull Class<Transcode> transcodeClass) {
        Class<Data> cls = dataClass;
        List<DecodePath<Data, TResource, Transcode>> decodePaths = new ArrayList<>();
        for (Class<TResource> registeredResourceClass : this.decoderRegistry.getResourceClasses(cls, resourceClass)) {
            for (Class<Transcode> registeredTranscodeClass : this.transcoderRegistry.getTranscodeClasses(registeredResourceClass, transcodeClass)) {
                decodePaths.add(new DecodePath<>(dataClass, registeredResourceClass, registeredTranscodeClass, this.decoderRegistry.getDecoders(cls, registeredResourceClass), this.transcoderRegistry.get(registeredResourceClass, registeredTranscodeClass), this.throwableListPool));
            }
        }
        return decodePaths;
    }

    @NonNull
    public <Model, TResource, Transcode> List<Class<?>> getRegisteredResourceClasses(@NonNull Class<Model> modelClass, @NonNull Class<TResource> resourceClass, @NonNull Class<Transcode> transcodeClass) {
        List<Class<?>> result = this.modelToResourceClassCache.get(modelClass, resourceClass, transcodeClass);
        if (result == null) {
            result = new ArrayList<>();
            for (Class<?> dataClass : this.modelLoaderRegistry.getDataClasses(modelClass)) {
                for (Class<?> registeredResourceClass : this.decoderRegistry.getResourceClasses(dataClass, resourceClass)) {
                    if (!this.transcoderRegistry.getTranscodeClasses(registeredResourceClass, transcodeClass).isEmpty() && !result.contains(registeredResourceClass)) {
                        result.add(registeredResourceClass);
                    }
                }
            }
            this.modelToResourceClassCache.put(modelClass, resourceClass, transcodeClass, Collections.unmodifiableList(result));
        }
        return result;
    }

    public boolean isResourceEncoderAvailable(@NonNull Resource<?> resource) {
        return this.resourceEncoderRegistry.get(resource.getResourceClass()) != null;
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [com.bumptech.glide.load.engine.Resource, com.bumptech.glide.load.engine.Resource<X>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <X> com.bumptech.glide.load.ResourceEncoder<X> getResultEncoder(@android.support.annotation.NonNull com.bumptech.glide.load.engine.Resource<X> r4) throws com.bumptech.glide.Registry.NoResultEncoderAvailableException {
        /*
            r3 = this;
            com.bumptech.glide.provider.ResourceEncoderRegistry r0 = r3.resourceEncoderRegistry
            java.lang.Class r1 = r4.getResourceClass()
            com.bumptech.glide.load.ResourceEncoder r0 = r0.get(r1)
            if (r0 == 0) goto L_0x000d
            return r0
        L_0x000d:
            com.bumptech.glide.Registry$NoResultEncoderAvailableException r1 = new com.bumptech.glide.Registry$NoResultEncoderAvailableException
            java.lang.Class r2 = r4.getResourceClass()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.Registry.getResultEncoder(com.bumptech.glide.load.engine.Resource):com.bumptech.glide.load.ResourceEncoder");
    }

    @NonNull
    public <X> Encoder<X> getSourceEncoder(@NonNull X data) throws NoSourceEncoderAvailableException {
        Encoder<X> encoder = this.encoderRegistry.getEncoder(data.getClass());
        if (encoder != null) {
            return encoder;
        }
        throw new NoSourceEncoderAvailableException(data.getClass());
    }

    @NonNull
    public <X> DataRewinder<X> getRewinder(@NonNull X data) {
        return this.dataRewinderRegistry.build(data);
    }

    @NonNull
    public <Model> List<ModelLoader<Model, ?>> getModelLoaders(@NonNull Model model) {
        List<ModelLoader<Model, ?>> result = this.modelLoaderRegistry.getModelLoaders(model);
        if (!result.isEmpty()) {
            return result;
        }
        throw new NoModelLoaderAvailableException(model);
    }

    @NonNull
    public List<ImageHeaderParser> getImageHeaderParsers() {
        List<ImageHeaderParser> result = this.imageHeaderParserRegistry.getParsers();
        if (!result.isEmpty()) {
            return result;
        }
        throw new NoImageHeaderParserException();
    }

    public static class NoModelLoaderAvailableException extends MissingComponentException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public NoModelLoaderAvailableException(@android.support.annotation.NonNull java.lang.Object r4) {
            /*
                r3 = this;
                java.lang.String r0 = java.lang.String.valueOf(r4)
                java.lang.String r1 = java.lang.String.valueOf(r0)
                int r1 = r1.length()
                int r1 = r1 + 43
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r1)
                java.lang.String r1 = "Failed to find any ModelLoaders for model: "
                r2.append(r1)
                r2.append(r0)
                java.lang.String r0 = r2.toString()
                r3.<init>(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.Registry.NoModelLoaderAvailableException.<init>(java.lang.Object):void");
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public NoModelLoaderAvailableException(@android.support.annotation.NonNull java.lang.Class<?> r5, @android.support.annotation.NonNull java.lang.Class<?> r6) {
            /*
                r4 = this;
                java.lang.String r0 = java.lang.String.valueOf(r5)
                java.lang.String r1 = java.lang.String.valueOf(r6)
                java.lang.String r2 = java.lang.String.valueOf(r0)
                int r2 = r2.length()
                int r2 = r2 + 54
                java.lang.String r3 = java.lang.String.valueOf(r1)
                int r3 = r3.length()
                int r2 = r2 + r3
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>(r2)
                java.lang.String r2 = "Failed to find any ModelLoaders for model: "
                r3.append(r2)
                r3.append(r0)
                java.lang.String r0 = " and data: "
                r3.append(r0)
                r3.append(r1)
                java.lang.String r0 = r3.toString()
                r4.<init>(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.Registry.NoModelLoaderAvailableException.<init>(java.lang.Class, java.lang.Class):void");
        }
    }

    public static class NoResultEncoderAvailableException extends MissingComponentException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public NoResultEncoderAvailableException(@android.support.annotation.NonNull java.lang.Class<?> r4) {
            /*
                r3 = this;
                java.lang.String r0 = java.lang.String.valueOf(r4)
                java.lang.String r1 = java.lang.String.valueOf(r0)
                int r1 = r1.length()
                int r1 = r1 + 227
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r1)
                java.lang.String r1 = "Failed to find result encoder for resource class: "
                r2.append(r1)
                r2.append(r0)
                java.lang.String r0 = ", you may need to consider registering a new Encoder for the requested type or DiskCacheStrategy.DATA/DiskCacheStrategy.NONE if caching your transformed resource is unnecessary."
                r2.append(r0)
                java.lang.String r0 = r2.toString()
                r3.<init>(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.Registry.NoResultEncoderAvailableException.<init>(java.lang.Class):void");
        }
    }

    public static class NoSourceEncoderAvailableException extends MissingComponentException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public NoSourceEncoderAvailableException(@android.support.annotation.NonNull java.lang.Class<?> r4) {
            /*
                r3 = this;
                java.lang.String r0 = java.lang.String.valueOf(r4)
                java.lang.String r1 = java.lang.String.valueOf(r0)
                int r1 = r1.length()
                int r1 = r1 + 46
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r1)
                java.lang.String r1 = "Failed to find source encoder for data class: "
                r2.append(r1)
                r2.append(r0)
                java.lang.String r0 = r2.toString()
                r3.<init>(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.Registry.NoSourceEncoderAvailableException.<init>(java.lang.Class):void");
        }
    }

    public static class MissingComponentException extends RuntimeException {
        public MissingComponentException(@NonNull String message) {
            super(message);
        }
    }

    public static final class NoImageHeaderParserException extends MissingComponentException {
        public NoImageHeaderParserException() {
            super("Failed to find image header parser.");
        }
    }
}
