package com.bumptech.glide.load.engine;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.p001v4.util.Pools;
import android.util.Log;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.DataFetcherGenerator;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.GlideTrace;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DecodeJob<R> implements DataFetcherGenerator.FetcherReadyCallback, Runnable, Comparable<DecodeJob<?>>, FactoryPools.Poolable {
    private static final String TAG = "DecodeJob";
    private Callback<R> callback;
    private Key currentAttemptingKey;
    private Object currentData;
    private DataSource currentDataSource;
    private DataFetcher<?> currentFetcher;
    private volatile DataFetcherGenerator currentGenerator;
    private Key currentSourceKey;
    private Thread currentThread;
    private final DecodeHelper<R> decodeHelper = new DecodeHelper<>();
    private final DeferredEncodeManager<?> deferredEncodeManager = new DeferredEncodeManager<>();
    private final DiskCacheProvider diskCacheProvider;
    private DiskCacheStrategy diskCacheStrategy;
    private GlideContext glideContext;
    private int height;
    private volatile boolean isCallbackNotified;
    private volatile boolean isCancelled;
    private EngineKey loadKey;
    private Object model;
    private boolean onlyRetrieveFromCache;
    private Options options;
    private int order;
    private final Pools.Pool<DecodeJob<?>> pool;
    private Priority priority;
    private final ReleaseManager releaseManager = new ReleaseManager();
    private RunReason runReason;
    private Key signature;
    private Stage stage;
    private long startFetchTime;
    private final StateVerifier stateVerifier = StateVerifier.newInstance();
    private final List<Throwable> throwables = new ArrayList();
    private int width;

    interface Callback<R> {
        void onLoadFailed(GlideException glideException);

        void onResourceReady(Resource<R> resource, DataSource dataSource);

        void reschedule(DecodeJob<?> decodeJob);
    }

    interface DiskCacheProvider {
        DiskCache getDiskCache();
    }

    private enum RunReason {
        INITIALIZE,
        SWITCH_TO_SOURCE_SERVICE,
        DECODE_DATA
    }

    private enum Stage {
        INITIALIZE,
        RESOURCE_CACHE,
        DATA_CACHE,
        SOURCE,
        ENCODE,
        FINISHED
    }

    public /* bridge */ /* synthetic */ int compareTo(@NonNull Object obj) {
        return compareTo((DecodeJob<?>) ((DecodeJob) obj));
    }

    DecodeJob(DiskCacheProvider diskCacheProvider2, Pools.Pool<DecodeJob<?>> pool2) {
        this.diskCacheProvider = diskCacheProvider2;
        this.pool = pool2;
    }

    /* access modifiers changed from: package-private */
    public DecodeJob<R> init(GlideContext glideContext2, Object model2, EngineKey loadKey2, Key signature2, int width2, int height2, Class<?> resourceClass, Class<R> transcodeClass, Priority priority2, DiskCacheStrategy diskCacheStrategy2, Map<Class<?>, Transformation<?>> transformations, boolean isTransformationRequired, boolean isScaleOnlyOrNoTransform, boolean onlyRetrieveFromCache2, Options options2, Callback<R> callback2, int order2) {
        int i = width2;
        int i2 = height2;
        DiskCacheStrategy diskCacheStrategy3 = diskCacheStrategy2;
        this.decodeHelper.init(glideContext2, model2, signature2, i, i2, diskCacheStrategy3, resourceClass, transcodeClass, priority2, options2, transformations, isTransformationRequired, isScaleOnlyOrNoTransform, this.diskCacheProvider);
        this.glideContext = glideContext2;
        this.signature = signature2;
        this.priority = priority2;
        this.loadKey = loadKey2;
        this.width = i;
        this.height = i2;
        this.diskCacheStrategy = diskCacheStrategy3;
        this.onlyRetrieveFromCache = onlyRetrieveFromCache2;
        this.options = options2;
        this.callback = callback2;
        this.order = order2;
        this.runReason = RunReason.INITIALIZE;
        this.model = model2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean willDecodeFromCache() {
        Stage firstStage = getNextStage(Stage.INITIALIZE);
        return firstStage == Stage.RESOURCE_CACHE || firstStage == Stage.DATA_CACHE;
    }

    /* access modifiers changed from: package-private */
    public void release(boolean isRemovedFromQueue) {
        if (this.releaseManager.release(isRemovedFromQueue)) {
            releaseInternal();
        }
    }

    private void onEncodeComplete() {
        if (this.releaseManager.onEncodeComplete()) {
            releaseInternal();
        }
    }

    private void onLoadFailed() {
        if (this.releaseManager.onFailed()) {
            releaseInternal();
        }
    }

    private void releaseInternal() {
        this.releaseManager.reset();
        this.deferredEncodeManager.clear();
        this.decodeHelper.clear();
        this.isCallbackNotified = false;
        this.glideContext = null;
        this.signature = null;
        this.options = null;
        this.priority = null;
        this.loadKey = null;
        this.callback = null;
        this.stage = null;
        this.currentGenerator = null;
        this.currentThread = null;
        this.currentSourceKey = null;
        this.currentData = null;
        this.currentDataSource = null;
        this.currentFetcher = null;
        this.startFetchTime = 0;
        this.isCancelled = false;
        this.model = null;
        this.throwables.clear();
        this.pool.release(this);
    }

    public int compareTo(@NonNull DecodeJob<?> other) {
        int result = getPriority() - other.getPriority();
        if (result == 0) {
            return this.order - other.order;
        }
        return result;
    }

    private int getPriority() {
        return this.priority.ordinal();
    }

    public void cancel() {
        this.isCancelled = true;
        DataFetcherGenerator local = this.currentGenerator;
        if (local != null) {
            local.cancel();
        }
    }

    public void run() {
        GlideTrace.beginSectionFormat("DecodeJob#run(model=%s)", this.model);
        DataFetcher<?> localFetcher = this.currentFetcher;
        try {
            if (this.isCancelled) {
                notifyFailed();
                if (localFetcher != null) {
                    localFetcher.cleanup();
                }
                GlideTrace.endSection();
                return;
            }
            runWrapped();
            if (localFetcher != null) {
                localFetcher.cleanup();
            }
            GlideTrace.endSection();
        } catch (CallbackException e) {
            throw e;
        } catch (Throwable e2) {
            if (localFetcher != null) {
                localFetcher.cleanup();
            }
            GlideTrace.endSection();
            throw e2;
        }
    }

    private void runWrapped() {
        int i = C07891.$SwitchMap$com$bumptech$glide$load$engine$DecodeJob$RunReason[this.runReason.ordinal()];
        if (i == 1) {
            this.stage = getNextStage(Stage.INITIALIZE);
            this.currentGenerator = getNextGenerator();
            runGenerators();
        } else if (i == 2) {
            runGenerators();
        } else if (i == 3) {
            decodeFromRetrievedData();
        } else {
            String valueOf = String.valueOf(this.runReason);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 25);
            sb.append("Unrecognized run reason: ");
            sb.append(valueOf);
            throw new IllegalStateException(sb.toString());
        }
    }

    private DataFetcherGenerator getNextGenerator() {
        int i = C07891.$SwitchMap$com$bumptech$glide$load$engine$DecodeJob$Stage[this.stage.ordinal()];
        if (i == 1) {
            return new ResourceCacheGenerator(this.decodeHelper, this);
        }
        if (i == 2) {
            return new DataCacheGenerator(this.decodeHelper, this);
        }
        if (i == 3) {
            return new SourceGenerator(this.decodeHelper, this);
        }
        if (i == 4) {
            return null;
        }
        String valueOf = String.valueOf(this.stage);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
        sb.append("Unrecognized stage: ");
        sb.append(valueOf);
        throw new IllegalStateException(sb.toString());
    }

    private void runGenerators() {
        this.currentThread = Thread.currentThread();
        this.startFetchTime = LogTime.getLogTime();
        boolean isStarted = false;
        while (!this.isCancelled && this.currentGenerator != null) {
            boolean startNext = this.currentGenerator.startNext();
            isStarted = startNext;
            if (startNext) {
                break;
            }
            this.stage = getNextStage(this.stage);
            this.currentGenerator = getNextGenerator();
            if (this.stage == Stage.SOURCE) {
                reschedule();
                return;
            }
        }
        if ((this.stage == Stage.FINISHED || this.isCancelled) && !isStarted) {
            notifyFailed();
        }
    }

    private void notifyFailed() {
        setNotifiedOrThrow();
        this.callback.onLoadFailed(new GlideException("Failed to load resource", new ArrayList(this.throwables)));
        onLoadFailed();
    }

    private void notifyComplete(Resource<R> resource, DataSource dataSource) {
        setNotifiedOrThrow();
        this.callback.onResourceReady(resource, dataSource);
    }

    private void setNotifiedOrThrow() {
        Throwable lastThrown;
        this.stateVerifier.throwIfRecycled();
        if (this.isCallbackNotified) {
            if (this.throwables.isEmpty()) {
                lastThrown = null;
            } else {
                List<Throwable> list = this.throwables;
                lastThrown = list.get(list.size() - 1);
            }
            throw new IllegalStateException("Already notified", lastThrown);
        }
        this.isCallbackNotified = true;
    }

    private Stage getNextStage(Stage current) {
        int i = C07891.$SwitchMap$com$bumptech$glide$load$engine$DecodeJob$Stage[current.ordinal()];
        if (i == 1) {
            return this.diskCacheStrategy.decodeCachedData() ? Stage.DATA_CACHE : getNextStage(Stage.DATA_CACHE);
        }
        if (i == 2) {
            return this.onlyRetrieveFromCache ? Stage.FINISHED : Stage.SOURCE;
        }
        if (i == 3 || i == 4) {
            return Stage.FINISHED;
        }
        if (i == 5) {
            return this.diskCacheStrategy.decodeCachedResource() ? Stage.RESOURCE_CACHE : getNextStage(Stage.RESOURCE_CACHE);
        }
        String valueOf = String.valueOf(current);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
        sb.append("Unrecognized stage: ");
        sb.append(valueOf);
        throw new IllegalArgumentException(sb.toString());
    }

    public void reschedule() {
        this.runReason = RunReason.SWITCH_TO_SOURCE_SERVICE;
        this.callback.reschedule(this);
    }

    public void onDataFetcherReady(Key sourceKey, Object data, DataFetcher<?> fetcher, DataSource dataSource, Key attemptedKey) {
        this.currentSourceKey = sourceKey;
        this.currentData = data;
        this.currentFetcher = fetcher;
        this.currentDataSource = dataSource;
        this.currentAttemptingKey = attemptedKey;
        if (Thread.currentThread() != this.currentThread) {
            this.runReason = RunReason.DECODE_DATA;
            this.callback.reschedule(this);
            return;
        }
        GlideTrace.beginSection("DecodeJob.decodeFromRetrievedData");
        try {
            decodeFromRetrievedData();
        } finally {
            GlideTrace.endSection();
        }
    }

    public void onDataFetcherFailed(Key attemptedKey, Exception e, DataFetcher<?> fetcher, DataSource dataSource) {
        fetcher.cleanup();
        GlideException exception = new GlideException("Fetching data failed", e);
        exception.setLoggingDetails(attemptedKey, dataSource, fetcher.getDataClass());
        this.throwables.add(exception);
        if (Thread.currentThread() != this.currentThread) {
            this.runReason = RunReason.SWITCH_TO_SOURCE_SERVICE;
            this.callback.reschedule(this);
            return;
        }
        runGenerators();
    }

    private void decodeFromRetrievedData() {
        if (Log.isLoggable(TAG, 2)) {
            long j = this.startFetchTime;
            String valueOf = String.valueOf(this.currentData);
            String valueOf2 = String.valueOf(this.currentSourceKey);
            String valueOf3 = String.valueOf(this.currentFetcher);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 30 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
            sb.append("data: ");
            sb.append(valueOf);
            sb.append(", cache key: ");
            sb.append(valueOf2);
            sb.append(", fetcher: ");
            sb.append(valueOf3);
            logWithTimeAndKey("Retrieved data", j, sb.toString());
        }
        Resource<R> resource = null;
        try {
            resource = decodeFromData(this.currentFetcher, this.currentData, this.currentDataSource);
        } catch (GlideException e) {
            e.setLoggingDetails(this.currentAttemptingKey, this.currentDataSource);
            this.throwables.add(e);
        }
        if (resource != null) {
            notifyEncodeAndRelease(resource, this.currentDataSource);
        } else {
            runGenerators();
        }
    }

    private void notifyEncodeAndRelease(Resource<R> resource, DataSource dataSource) {
        if (resource instanceof Initializable) {
            ((Initializable) resource).initialize();
        }
        Resource<R> result = resource;
        LockedResource<R> lockedResource = null;
        if (this.deferredEncodeManager.hasResourceToEncode()) {
            lockedResource = LockedResource.obtain(resource);
            result = lockedResource;
        }
        notifyComplete(result, dataSource);
        this.stage = Stage.ENCODE;
        try {
            if (this.deferredEncodeManager.hasResourceToEncode()) {
                this.deferredEncodeManager.encode(this.diskCacheProvider, this.options);
            }
            onEncodeComplete();
        } finally {
            if (lockedResource != null) {
                lockedResource.unlock();
            }
        }
    }

    private <Data> Resource<R> decodeFromData(DataFetcher<?> fetcher, Data data, DataSource dataSource) throws GlideException {
        if (data == null) {
            fetcher.cleanup();
            return null;
        }
        try {
            long startTime = LogTime.getLogTime();
            Resource<R> result = decodeFromFetcher(data, dataSource);
            if (Log.isLoggable(TAG, 2)) {
                String valueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 15);
                sb.append("Decoded result ");
                sb.append(valueOf);
                logWithTimeAndKey(sb.toString(), startTime);
            }
            return result;
        } finally {
            fetcher.cleanup();
        }
    }

    private <Data> Resource<R> decodeFromFetcher(Data data, DataSource dataSource) throws GlideException {
        return runLoadPath(data, dataSource, this.decodeHelper.getLoadPath(data.getClass()));
    }

    @NonNull
    private Options getOptionsWithHardwareConfig(DataSource dataSource) {
        Options options2 = this.options;
        if (Build.VERSION.SDK_INT < 26) {
            return options2;
        }
        boolean isHardwareConfigSafe = dataSource == DataSource.RESOURCE_DISK_CACHE || this.decodeHelper.isScaleOnlyOrNoTransform();
        Boolean isHardwareConfigAllowed = (Boolean) options2.get(Downsampler.ALLOW_HARDWARE_CONFIG);
        if (isHardwareConfigAllowed != null && (!isHardwareConfigAllowed.booleanValue() || isHardwareConfigSafe)) {
            return options2;
        }
        Options options3 = new Options();
        options3.putAll(this.options);
        options3.set(Downsampler.ALLOW_HARDWARE_CONFIG, Boolean.valueOf(isHardwareConfigSafe));
        return options3;
    }

    private <Data, ResourceType> Resource<R> runLoadPath(Data data, DataSource dataSource, LoadPath<Data, ResourceType, R> path) throws GlideException {
        Options options2 = getOptionsWithHardwareConfig(dataSource);
        DataRewinder<Data> rewinder = this.glideContext.getRegistry().getRewinder(data);
        try {
            return path.load(rewinder, options2, this.width, this.height, new DecodeCallback(dataSource));
        } finally {
            rewinder.cleanup();
        }
    }

    private void logWithTimeAndKey(String message, long startTime) {
        logWithTimeAndKey(message, startTime, null);
    }

    private void logWithTimeAndKey(String message, long startTime, String extraArgs) {
        String str;
        double elapsedMillis = LogTime.getElapsedMillis(startTime);
        String valueOf = String.valueOf(this.loadKey);
        if (extraArgs != null) {
            String valueOf2 = String.valueOf(extraArgs);
            str = valueOf2.length() != 0 ? ", ".concat(valueOf2) : new String(", ");
        } else {
            str = "";
        }
        String name = Thread.currentThread().getName();
        StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 50 + String.valueOf(valueOf).length() + String.valueOf(str).length() + String.valueOf(name).length());
        sb.append(message);
        sb.append(" in ");
        sb.append(elapsedMillis);
        sb.append(", load key: ");
        sb.append(valueOf);
        sb.append(str);
        sb.append(", thread: ");
        sb.append(name);
        Log.v(TAG, sb.toString());
    }

    @NonNull
    public StateVerifier getVerifier() {
        return this.stateVerifier;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <Z> Resource<Z> onResourceDecoded(DataSource dataSource, @NonNull Resource<Z> decoded) {
        Transformation<Z> appliedTransformation;
        Resource<Z> transformed;
        EncodeStrategy encodeStrategy;
        ResourceEncoder<Z> encoder;
        Key key;
        ResourceEncoder<Z> encoder2;
        DataSource dataSource2 = dataSource;
        Resource<Z> resource = decoded;
        Class<?> cls = decoded.get().getClass();
        Resource<Z> transformed2 = decoded;
        if (dataSource2 != DataSource.RESOURCE_DISK_CACHE) {
            Transformation<Z> appliedTransformation2 = this.decodeHelper.getTransformation(cls);
            appliedTransformation = appliedTransformation2;
            transformed = appliedTransformation2.transform(this.glideContext, resource, this.width, this.height);
        } else {
            appliedTransformation = null;
            transformed = transformed2;
        }
        if (!resource.equals(transformed)) {
            decoded.recycle();
        }
        if (this.decodeHelper.isResourceEncoderAvailable(transformed)) {
            ResourceEncoder<Z> encoder3 = this.decodeHelper.getResultEncoder(transformed);
            encoder = encoder3;
            encodeStrategy = encoder3.getEncodeStrategy(this.options);
        } else {
            encoder = null;
            encodeStrategy = EncodeStrategy.NONE;
        }
        Resource<Z> result = transformed;
        if (!this.diskCacheStrategy.isResourceCacheable(!this.decodeHelper.isSourceKey(this.currentSourceKey), dataSource2, encodeStrategy)) {
            return result;
        }
        if (encoder != null) {
            int i = C07891.$SwitchMap$com$bumptech$glide$load$EncodeStrategy[encodeStrategy.ordinal()];
            if (i == 1) {
                encoder2 = encoder;
                key = new DataCacheKey(this.currentSourceKey, this.signature);
            } else if (i == 2) {
                encoder2 = encoder;
                key = new ResourceCacheKey(this.decodeHelper.getArrayPool(), this.currentSourceKey, this.signature, this.width, this.height, appliedTransformation, cls, this.options);
            } else {
                String valueOf = String.valueOf(encodeStrategy);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append("Unknown strategy: ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
            Resource<Z> lockedResult = LockedResource.obtain(transformed);
            this.deferredEncodeManager.init(key, encoder2, lockedResult);
            return lockedResult;
        }
        throw new Registry.NoResultEncoderAvailableException(transformed.get().getClass());
    }

    /* renamed from: com.bumptech.glide.load.engine.DecodeJob$1 */
    static /* synthetic */ class C07891 {
        static final /* synthetic */ int[] $SwitchMap$com$bumptech$glide$load$EncodeStrategy = new int[EncodeStrategy.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$RunReason = new int[RunReason.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$Stage = new int[Stage.values().length];

        static {
            try {
                $SwitchMap$com$bumptech$glide$load$EncodeStrategy[EncodeStrategy.SOURCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$bumptech$glide$load$EncodeStrategy[EncodeStrategy.TRANSFORMED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$Stage[Stage.RESOURCE_CACHE.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$Stage[Stage.DATA_CACHE.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$Stage[Stage.SOURCE.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$Stage[Stage.FINISHED.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$Stage[Stage.INITIALIZE.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$RunReason[RunReason.INITIALIZE.ordinal()] = 1;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$RunReason[RunReason.SWITCH_TO_SOURCE_SERVICE.ordinal()] = 2;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$bumptech$glide$load$engine$DecodeJob$RunReason[RunReason.DECODE_DATA.ordinal()] = 3;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    private final class DecodeCallback<Z> implements DecodePath.DecodeCallback<Z> {
        private final DataSource dataSource;

        DecodeCallback(DataSource dataSource2) {
            this.dataSource = dataSource2;
        }

        @NonNull
        public Resource<Z> onResourceDecoded(@NonNull Resource<Z> decoded) {
            return DecodeJob.this.onResourceDecoded(this.dataSource, decoded);
        }
    }

    private static class ReleaseManager {
        private boolean isEncodeComplete;
        private boolean isFailed;
        private boolean isReleased;

        ReleaseManager() {
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean release(boolean isRemovedFromQueue) {
            this.isReleased = true;
            return isComplete(isRemovedFromQueue);
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean onEncodeComplete() {
            this.isEncodeComplete = true;
            return isComplete(false);
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean onFailed() {
            this.isFailed = true;
            return isComplete(false);
        }

        /* access modifiers changed from: package-private */
        public synchronized void reset() {
            this.isEncodeComplete = false;
            this.isReleased = false;
            this.isFailed = false;
        }

        private boolean isComplete(boolean isRemovedFromQueue) {
            return (this.isFailed || isRemovedFromQueue || this.isEncodeComplete) && this.isReleased;
        }
    }

    private static class DeferredEncodeManager<Z> {
        private ResourceEncoder<Z> encoder;
        private Key key;
        private LockedResource<Z> toEncode;

        DeferredEncodeManager() {
        }

        /* JADX WARN: Type inference failed for: r2v0, types: [com.bumptech.glide.load.ResourceEncoder<Z>, com.bumptech.glide.load.ResourceEncoder<X>] */
        /* JADX WARN: Type inference failed for: r3v0, types: [com.bumptech.glide.load.engine.LockedResource<X>, com.bumptech.glide.load.engine.LockedResource<Z>] */
        /* access modifiers changed from: package-private */
        /* JADX WARNING: Unknown variable types count: 2 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public <X> void init(com.bumptech.glide.load.Key r1, com.bumptech.glide.load.ResourceEncoder<X> r2, com.bumptech.glide.load.engine.LockedResource<X> r3) {
            /*
                r0 = this;
                r0.key = r1
                r0.encoder = r2
                r0.toEncode = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.DecodeJob.DeferredEncodeManager.init(com.bumptech.glide.load.Key, com.bumptech.glide.load.ResourceEncoder, com.bumptech.glide.load.engine.LockedResource):void");
        }

        /* access modifiers changed from: package-private */
        public void encode(DiskCacheProvider diskCacheProvider, Options options) {
            GlideTrace.beginSection("DecodeJob.encode");
            try {
                diskCacheProvider.getDiskCache().put(this.key, new DataCacheWriter(this.encoder, this.toEncode, options));
            } finally {
                this.toEncode.unlock();
                GlideTrace.endSection();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean hasResourceToEncode() {
            return this.toEncode != null;
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            this.key = null;
            this.encoder = null;
            this.toEncode = null;
        }
    }
}
