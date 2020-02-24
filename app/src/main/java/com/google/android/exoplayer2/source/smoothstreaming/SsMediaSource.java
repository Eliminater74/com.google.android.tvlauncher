package com.google.android.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsUtil;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SsMediaSource extends BaseMediaSource implements Loader.Callback<ParsingLoadable<SsManifest>> {
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    private static final int MINIMUM_MANIFEST_REFRESH_PERIOD_MS = 5000;
    private static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private final SsChunkSource.Factory chunkSourceFactory;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final long livePresentationDelayMs;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private SsManifest manifest;
    private DataSource manifestDataSource;
    private final DataSource.Factory manifestDataSourceFactory;
    private final MediaSourceEventListener.EventDispatcher manifestEventDispatcher;
    private long manifestLoadStartTimestamp;
    private Loader manifestLoader;
    private LoaderErrorThrower manifestLoaderErrorThrower;
    private final ParsingLoadable.Parser<? extends SsManifest> manifestParser;
    private Handler manifestRefreshHandler;
    private final Uri manifestUri;
    private final ArrayList<SsMediaPeriod> mediaPeriods;
    @Nullable
    private TransferListener mediaTransferListener;
    private final boolean sideloadedManifest;
    @Nullable
    private final Object tag;

    public /* bridge */ /* synthetic */ void onLoadCanceled(Loader.Loadable loadable, long j, long j2, boolean z) {
        onLoadCanceled((ParsingLoadable<SsManifest>) ((ParsingLoadable) loadable), j, j2, z);
    }

    public /* bridge */ /* synthetic */ void onLoadCompleted(Loader.Loadable loadable, long j, long j2) {
        onLoadCompleted((ParsingLoadable<SsManifest>) ((ParsingLoadable) loadable), j, j2);
    }

    public /* bridge */ /* synthetic */ Loader.LoadErrorAction onLoadError(Loader.Loadable loadable, long j, long j2, IOException iOException, int i) {
        return onLoadError((ParsingLoadable<SsManifest>) ((ParsingLoadable) loadable), j, j2, iOException, i);
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.smoothstreaming");
    }

    public static final class Factory implements AdsMediaSource.MediaSourceFactory {
        private final SsChunkSource.Factory chunkSourceFactory;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private boolean isCreateCalled;
        private long livePresentationDelayMs;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        @Nullable
        private final DataSource.Factory manifestDataSourceFactory;
        @Nullable
        private ParsingLoadable.Parser<? extends SsManifest> manifestParser;
        @Nullable
        private List<StreamKey> streamKeys;
        @Nullable
        private Object tag;

        public Factory(DataSource.Factory dataSourceFactory) {
            this(new DefaultSsChunkSource.Factory(dataSourceFactory), dataSourceFactory);
        }

        public Factory(SsChunkSource.Factory chunkSourceFactory2, @Nullable DataSource.Factory manifestDataSourceFactory2) {
            this.chunkSourceFactory = (SsChunkSource.Factory) Assertions.checkNotNull(chunkSourceFactory2);
            this.manifestDataSourceFactory = manifestDataSourceFactory2;
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.livePresentationDelayMs = 30000;
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
        }

        public Factory setTag(Object tag2) {
            Assertions.checkState(!this.isCreateCalled);
            this.tag = tag2;
            return this;
        }

        @Deprecated
        public Factory setMinLoadableRetryCount(int minLoadableRetryCount) {
            return setLoadErrorHandlingPolicy(new DefaultLoadErrorHandlingPolicy(minLoadableRetryCount));
        }

        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy2) {
            Assertions.checkState(!this.isCreateCalled);
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
            return this;
        }

        public Factory setLivePresentationDelayMs(long livePresentationDelayMs2) {
            Assertions.checkState(!this.isCreateCalled);
            this.livePresentationDelayMs = livePresentationDelayMs2;
            return this;
        }

        public Factory setManifestParser(ParsingLoadable.Parser<? extends SsManifest> manifestParser2) {
            Assertions.checkState(!this.isCreateCalled);
            this.manifestParser = (ParsingLoadable.Parser) Assertions.checkNotNull(manifestParser2);
            return this;
        }

        public Factory setStreamKeys(List<StreamKey> streamKeys2) {
            Assertions.checkState(!this.isCreateCalled);
            this.streamKeys = streamKeys2;
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.compositeSequenceableLoaderFactory = (CompositeSequenceableLoaderFactory) Assertions.checkNotNull(compositeSequenceableLoaderFactory2);
            return this;
        }

        public SsMediaSource createMediaSource(SsManifest manifest) {
            Assertions.checkArgument(!manifest.isLive);
            this.isCreateCalled = true;
            List<StreamKey> list = this.streamKeys;
            if (list != null && !list.isEmpty()) {
                manifest = manifest.copy(this.streamKeys);
            }
            return new SsMediaSource(manifest, null, null, null, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.tag);
        }

        @Deprecated
        public SsMediaSource createMediaSource(SsManifest manifest, @Nullable Handler eventHandler, @Nullable MediaSourceEventListener eventListener) {
            SsMediaSource mediaSource = createMediaSource(manifest);
            if (!(eventHandler == null || eventListener == null)) {
                mediaSource.addEventListener(eventHandler, eventListener);
            }
            return mediaSource;
        }

        public SsMediaSource createMediaSource(Uri manifestUri) {
            this.isCreateCalled = true;
            if (this.manifestParser == null) {
                this.manifestParser = new SsManifestParser();
            }
            List<StreamKey> list = this.streamKeys;
            if (list != null) {
                this.manifestParser = new FilteringManifestParser(this.manifestParser, list);
            }
            return new SsMediaSource(null, (Uri) Assertions.checkNotNull(manifestUri), this.manifestDataSourceFactory, this.manifestParser, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.tag);
        }

        @Deprecated
        public SsMediaSource createMediaSource(Uri manifestUri, @Nullable Handler eventHandler, @Nullable MediaSourceEventListener eventListener) {
            SsMediaSource mediaSource = createMediaSource(manifestUri);
            if (!(eventHandler == null || eventListener == null)) {
                mediaSource.addEventListener(eventHandler, eventListener);
            }
            return mediaSource;
        }

        public int[] getSupportedTypes() {
            return new int[]{1};
        }
    }

    @Deprecated
    public SsMediaSource(SsManifest manifest2, SsChunkSource.Factory chunkSourceFactory2, Handler eventHandler, MediaSourceEventListener eventListener) {
        this(manifest2, chunkSourceFactory2, 3, eventHandler, eventListener);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public SsMediaSource(SsManifest manifest2, SsChunkSource.Factory chunkSourceFactory2, int minLoadableRetryCount, Handler eventHandler, MediaSourceEventListener eventListener) {
        this(manifest2, null, null, null, chunkSourceFactory2, new DefaultCompositeSequenceableLoaderFactory(), new DefaultLoadErrorHandlingPolicy(minLoadableRetryCount), 30000, null);
        Handler handler = eventHandler;
        MediaSourceEventListener mediaSourceEventListener = eventListener;
        if (handler != null && mediaSourceEventListener != null) {
            addEventListener(handler, mediaSourceEventListener);
        }
    }

    @Deprecated
    public SsMediaSource(Uri manifestUri2, DataSource.Factory manifestDataSourceFactory2, SsChunkSource.Factory chunkSourceFactory2, Handler eventHandler, MediaSourceEventListener eventListener) {
        this(manifestUri2, manifestDataSourceFactory2, chunkSourceFactory2, 3, 30000, eventHandler, eventListener);
    }

    @Deprecated
    public SsMediaSource(Uri manifestUri2, DataSource.Factory manifestDataSourceFactory2, SsChunkSource.Factory chunkSourceFactory2, int minLoadableRetryCount, long livePresentationDelayMs2, Handler eventHandler, MediaSourceEventListener eventListener) {
        this(manifestUri2, manifestDataSourceFactory2, new SsManifestParser(), chunkSourceFactory2, minLoadableRetryCount, livePresentationDelayMs2, eventHandler, eventListener);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public SsMediaSource(Uri manifestUri2, DataSource.Factory manifestDataSourceFactory2, ParsingLoadable.Parser<? extends SsManifest> manifestParser2, SsChunkSource.Factory chunkSourceFactory2, int minLoadableRetryCount, long livePresentationDelayMs2, Handler eventHandler, MediaSourceEventListener eventListener) {
        this(null, manifestUri2, manifestDataSourceFactory2, manifestParser2, chunkSourceFactory2, new DefaultCompositeSequenceableLoaderFactory(), new DefaultLoadErrorHandlingPolicy(minLoadableRetryCount), livePresentationDelayMs2, null);
        Handler handler = eventHandler;
        MediaSourceEventListener mediaSourceEventListener = eventListener;
        if (handler != null && mediaSourceEventListener != null) {
            addEventListener(handler, mediaSourceEventListener);
        }
    }

    private SsMediaSource(SsManifest manifest2, Uri manifestUri2, DataSource.Factory manifestDataSourceFactory2, ParsingLoadable.Parser<? extends SsManifest> manifestParser2, SsChunkSource.Factory chunkSourceFactory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, long livePresentationDelayMs2, @Nullable Object tag2) {
        boolean z = false;
        Assertions.checkState(manifest2 == null || !manifest2.isLive);
        this.manifest = manifest2;
        this.manifestUri = manifestUri2 == null ? null : SsUtil.fixManifestUri(manifestUri2);
        this.manifestDataSourceFactory = manifestDataSourceFactory2;
        this.manifestParser = manifestParser2;
        this.chunkSourceFactory = chunkSourceFactory2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.livePresentationDelayMs = livePresentationDelayMs2;
        this.manifestEventDispatcher = createEventDispatcher(null);
        this.tag = tag2;
        this.sideloadedManifest = manifest2 != null ? true : z;
        this.mediaPeriods = new ArrayList<>();
    }

    @Nullable
    public Object getTag() {
        return this.tag;
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener2) {
        this.mediaTransferListener = mediaTransferListener2;
        if (this.sideloadedManifest) {
            this.manifestLoaderErrorThrower = new LoaderErrorThrower.Dummy();
            processManifest();
            return;
        }
        this.manifestDataSource = this.manifestDataSourceFactory.createDataSource();
        this.manifestLoader = new Loader("Loader:Manifest");
        this.manifestLoaderErrorThrower = this.manifestLoader;
        this.manifestRefreshHandler = new Handler();
        bridge$lambda$0$SsMediaSource();
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        SsMediaPeriod period = new SsMediaPeriod(this.manifest, this.chunkSourceFactory, this.mediaTransferListener, this.compositeSequenceableLoaderFactory, this.loadErrorHandlingPolicy, createEventDispatcher(id), this.manifestLoaderErrorThrower, allocator);
        this.mediaPeriods.add(period);
        return period;
    }

    public void releasePeriod(MediaPeriod period) {
        ((SsMediaPeriod) period).release();
        this.mediaPeriods.remove(period);
    }

    public void releaseSourceInternal() {
        this.manifest = this.sideloadedManifest ? this.manifest : null;
        this.manifestDataSource = null;
        this.manifestLoadStartTimestamp = 0;
        Loader loader = this.manifestLoader;
        if (loader != null) {
            loader.release();
            this.manifestLoader = null;
        }
        Handler handler = this.manifestRefreshHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.manifestRefreshHandler = null;
        }
    }

    public void onLoadCompleted(ParsingLoadable<SsManifest> loadable, long elapsedRealtimeMs, long loadDurationMs) {
        ParsingLoadable<SsManifest> parsingLoadable = loadable;
        this.manifestEventDispatcher.loadCompleted(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), parsingLoadable.type, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        this.manifest = loadable.getResult();
        this.manifestLoadStartTimestamp = elapsedRealtimeMs - loadDurationMs;
        processManifest();
        scheduleManifestRefresh();
    }

    public void onLoadCanceled(ParsingLoadable<SsManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        ParsingLoadable<SsManifest> parsingLoadable = loadable;
        this.manifestEventDispatcher.loadCanceled(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), parsingLoadable.type, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
    }

    public Loader.LoadErrorAction onLoadError(ParsingLoadable<SsManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
        Loader.LoadErrorAction loadErrorAction;
        ParsingLoadable<SsManifest> parsingLoadable = loadable;
        long retryDelayMs = this.loadErrorHandlingPolicy.getRetryDelayMsFor(4, loadDurationMs, error, errorCount);
        if (retryDelayMs == C0841C.TIME_UNSET) {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
        } else {
            loadErrorAction = Loader.createRetryAction(false, retryDelayMs);
        }
        this.manifestEventDispatcher.loadError(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), parsingLoadable.type, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded(), error, !loadErrorAction.isRetry());
        return loadErrorAction;
    }

    private void processManifest() {
        SinglePeriodTimeline singlePeriodTimeline;
        long durationUs;
        for (int i = 0; i < this.mediaPeriods.size(); i++) {
            this.mediaPeriods.get(i).updateManifest(this.manifest);
        }
        long endTimeUs = Long.MIN_VALUE;
        long startTimeUs = Long.MAX_VALUE;
        for (SsManifest.StreamElement element : this.manifest.streamElements) {
            if (element.chunkCount > 0) {
                startTimeUs = Math.min(startTimeUs, element.getStartTimeUs(0));
                endTimeUs = Math.max(endTimeUs, element.getStartTimeUs(element.chunkCount - 1) + element.getChunkDurationUs(element.chunkCount - 1));
            }
        }
        if (startTimeUs == Long.MAX_VALUE) {
            singlePeriodTimeline = new SinglePeriodTimeline(this.manifest.isLive ? -9223372036854775807L : 0, 0, 0, 0, true, this.manifest.isLive, this.tag);
        } else if (this.manifest.isLive) {
            if (this.manifest.dvrWindowLengthUs != C0841C.TIME_UNSET && this.manifest.dvrWindowLengthUs > 0) {
                startTimeUs = Math.max(startTimeUs, endTimeUs - this.manifest.dvrWindowLengthUs);
            }
            long durationUs2 = endTimeUs - startTimeUs;
            long defaultStartPositionUs = durationUs2 - C0841C.msToUs(this.livePresentationDelayMs);
            if (defaultStartPositionUs < MIN_LIVE_DEFAULT_START_POSITION_US) {
                defaultStartPositionUs = Math.min((long) MIN_LIVE_DEFAULT_START_POSITION_US, durationUs2 / 2);
            }
            singlePeriodTimeline = new SinglePeriodTimeline(C0841C.TIME_UNSET, durationUs2, startTimeUs, defaultStartPositionUs, true, true, this.tag);
        } else {
            if (this.manifest.durationUs != C0841C.TIME_UNSET) {
                durationUs = this.manifest.durationUs;
            } else {
                durationUs = endTimeUs - startTimeUs;
            }
            singlePeriodTimeline = new SinglePeriodTimeline(startTimeUs + durationUs, durationUs, startTimeUs, 0, true, false, this.tag);
        }
        refreshSourceInfo(singlePeriodTimeline, this.manifest);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    private void scheduleManifestRefresh() {
        if (this.manifest.isLive) {
            this.manifestRefreshHandler.postDelayed(new SsMediaSource$$Lambda$0(this), Math.max(0L, (this.manifestLoadStartTimestamp + DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) - SystemClock.elapsedRealtime()));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: startLoadingManifest */
    public void bridge$lambda$0$SsMediaSource() {
        ParsingLoadable<SsManifest> loadable = new ParsingLoadable<>(this.manifestDataSource, this.manifestUri, 4, this.manifestParser);
        this.manifestEventDispatcher.loadStarted(loadable.dataSpec, loadable.type, this.manifestLoader.startLoading(loadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(loadable.type)));
    }
}
