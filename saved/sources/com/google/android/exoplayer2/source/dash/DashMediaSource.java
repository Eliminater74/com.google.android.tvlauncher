package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DashMediaSource extends BaseMediaSource {
    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_FIXED_MS = 30000;
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_PREFER_MANIFEST_MS = -1;
    private static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private static final int NOTIFY_MANIFEST_INTERVAL_MS = 5000;
    private static final String TAG = "DashMediaSource";
    private final DashChunkSource.Factory chunkSourceFactory;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private DataSource dataSource;
    private long elapsedRealtimeOffsetMs;
    private long expiredManifestPublishTimeUs;
    private int firstPeriodId;
    private Handler handler;
    private Uri initialManifestUri;
    private final long livePresentationDelayMs;
    private final boolean livePresentationDelayOverridesManifest;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    /* access modifiers changed from: private */
    public Loader loader;
    private DashManifest manifest;
    private final ManifestCallback manifestCallback;
    private final DataSource.Factory manifestDataSourceFactory;
    private final MediaSourceEventListener.EventDispatcher manifestEventDispatcher;
    /* access modifiers changed from: private */
    public IOException manifestFatalError;
    private long manifestLoadEndTimestampMs;
    private final LoaderErrorThrower manifestLoadErrorThrower;
    private boolean manifestLoadPending;
    private long manifestLoadStartTimestampMs;
    private final ParsingLoadable.Parser<? extends DashManifest> manifestParser;
    private Uri manifestUri;
    private final Object manifestUriLock;
    @Nullable
    private TransferListener mediaTransferListener;
    private final SparseArray<DashMediaPeriod> periodsById;
    private final PlayerEmsgHandler.PlayerEmsgCallback playerEmsgCallback;
    private final Runnable refreshManifestRunnable;
    private final boolean sideloadedManifest;
    private final Runnable simulateManifestRefreshRunnable;
    private int staleManifestReloadAttempt;
    @Nullable
    private final Object tag;

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.dash");
    }

    public static final class Factory implements AdsMediaSource.MediaSourceFactory {
        private final DashChunkSource.Factory chunkSourceFactory;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private boolean isCreateCalled;
        private long livePresentationDelayMs;
        private boolean livePresentationDelayOverridesManifest;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        @Nullable
        private final DataSource.Factory manifestDataSourceFactory;
        @Nullable
        private ParsingLoadable.Parser<? extends DashManifest> manifestParser;
        @Nullable
        private List<StreamKey> streamKeys;
        @Nullable
        private Object tag;

        public Factory(DataSource.Factory dataSourceFactory) {
            this(new DefaultDashChunkSource.Factory(dataSourceFactory), dataSourceFactory);
        }

        public Factory(DashChunkSource.Factory chunkSourceFactory2, @Nullable DataSource.Factory manifestDataSourceFactory2) {
            this.chunkSourceFactory = (DashChunkSource.Factory) Assertions.checkNotNull(chunkSourceFactory2);
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

        @Deprecated
        public Factory setLivePresentationDelayMs(long livePresentationDelayMs2) {
            if (livePresentationDelayMs2 == -1) {
                return setLivePresentationDelayMs(30000, false);
            }
            return setLivePresentationDelayMs(livePresentationDelayMs2, true);
        }

        public Factory setLivePresentationDelayMs(long livePresentationDelayMs2, boolean overridesManifest) {
            Assertions.checkState(!this.isCreateCalled);
            this.livePresentationDelayMs = livePresentationDelayMs2;
            this.livePresentationDelayOverridesManifest = overridesManifest;
            return this;
        }

        public Factory setManifestParser(ParsingLoadable.Parser<? extends DashManifest> manifestParser2) {
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

        public DashMediaSource createMediaSource(DashManifest manifest) {
            Assertions.checkArgument(!manifest.dynamic);
            this.isCreateCalled = true;
            List<StreamKey> list = this.streamKeys;
            if (list != null && !list.isEmpty()) {
                manifest = manifest.copy(this.streamKeys);
            }
            return new DashMediaSource(manifest, null, null, null, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.livePresentationDelayOverridesManifest, this.tag);
        }

        @Deprecated
        public DashMediaSource createMediaSource(DashManifest manifest, @Nullable Handler eventHandler, @Nullable MediaSourceEventListener eventListener) {
            DashMediaSource mediaSource = createMediaSource(manifest);
            if (!(eventHandler == null || eventListener == null)) {
                mediaSource.addEventListener(eventHandler, eventListener);
            }
            return mediaSource;
        }

        public DashMediaSource createMediaSource(Uri manifestUri) {
            this.isCreateCalled = true;
            if (this.manifestParser == null) {
                this.manifestParser = new DashManifestParser();
            }
            List<StreamKey> list = this.streamKeys;
            if (list != null) {
                this.manifestParser = new FilteringManifestParser(this.manifestParser, list);
            }
            return new DashMediaSource(null, (Uri) Assertions.checkNotNull(manifestUri), this.manifestDataSourceFactory, this.manifestParser, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.livePresentationDelayOverridesManifest, this.tag);
        }

        @Deprecated
        public DashMediaSource createMediaSource(Uri manifestUri, @Nullable Handler eventHandler, @Nullable MediaSourceEventListener eventListener) {
            DashMediaSource mediaSource = createMediaSource(manifestUri);
            if (!(eventHandler == null || eventListener == null)) {
                mediaSource.addEventListener(eventHandler, eventListener);
            }
            return mediaSource;
        }

        public int[] getSupportedTypes() {
            return new int[]{0};
        }
    }

    @Deprecated
    public DashMediaSource(DashManifest manifest2, DashChunkSource.Factory chunkSourceFactory2, Handler eventHandler, MediaSourceEventListener eventListener) {
        this(manifest2, chunkSourceFactory2, 3, eventHandler, eventListener);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public DashMediaSource(DashManifest manifest2, DashChunkSource.Factory chunkSourceFactory2, int minLoadableRetryCount, Handler eventHandler, MediaSourceEventListener eventListener) {
        this(manifest2, null, null, null, chunkSourceFactory2, new DefaultCompositeSequenceableLoaderFactory(), new DefaultLoadErrorHandlingPolicy(minLoadableRetryCount), 30000, false, null);
        Handler handler2 = eventHandler;
        MediaSourceEventListener mediaSourceEventListener = eventListener;
        if (handler2 != null && mediaSourceEventListener != null) {
            addEventListener(handler2, mediaSourceEventListener);
        }
    }

    @Deprecated
    public DashMediaSource(Uri manifestUri2, DataSource.Factory manifestDataSourceFactory2, DashChunkSource.Factory chunkSourceFactory2, Handler eventHandler, MediaSourceEventListener eventListener) {
        this(manifestUri2, manifestDataSourceFactory2, chunkSourceFactory2, 3, -1, eventHandler, eventListener);
    }

    @Deprecated
    public DashMediaSource(Uri manifestUri2, DataSource.Factory manifestDataSourceFactory2, DashChunkSource.Factory chunkSourceFactory2, int minLoadableRetryCount, long livePresentationDelayMs2, Handler eventHandler, MediaSourceEventListener eventListener) {
        this(manifestUri2, manifestDataSourceFactory2, new DashManifestParser(), chunkSourceFactory2, minLoadableRetryCount, livePresentationDelayMs2, eventHandler, eventListener);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DashMediaSource(android.net.Uri r16, com.google.android.exoplayer2.upstream.DataSource.Factory r17, com.google.android.exoplayer2.upstream.ParsingLoadable.Parser<? extends com.google.android.exoplayer2.source.dash.manifest.DashManifest> r18, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory r19, int r20, long r21, android.os.Handler r23, com.google.android.exoplayer2.source.MediaSourceEventListener r24) {
        /*
            r15 = this;
            r0 = r23
            r1 = r24
            com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory r8 = new com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory
            r8.<init>()
            com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy r9 = new com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy
            r14 = r20
            r9.<init>(r14)
            r2 = -1
            int r4 = (r21 > r2 ? 1 : (r21 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x001a
            r4 = 30000(0x7530, double:1.4822E-319)
            r10 = r4
            goto L_0x001c
        L_0x001a:
            r10 = r21
        L_0x001c:
            int r4 = (r21 > r2 ? 1 : (r21 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0023
            r2 = 1
            r12 = 1
            goto L_0x0025
        L_0x0023:
            r2 = 0
            r12 = 0
        L_0x0025:
            r13 = 0
            r3 = 0
            r2 = r15
            r4 = r16
            r5 = r17
            r6 = r18
            r7 = r19
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r12, r13)
            if (r0 == 0) goto L_0x003c
            if (r1 == 0) goto L_0x003c
            r2 = r15
            r15.addEventListener(r0, r1)
            goto L_0x003d
        L_0x003c:
            r2 = r15
        L_0x003d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.DashMediaSource.<init>(android.net.Uri, com.google.android.exoplayer2.upstream.DataSource$Factory, com.google.android.exoplayer2.upstream.ParsingLoadable$Parser, com.google.android.exoplayer2.source.dash.DashChunkSource$Factory, int, long, android.os.Handler, com.google.android.exoplayer2.source.MediaSourceEventListener):void");
    }

    private DashMediaSource(DashManifest manifest2, Uri manifestUri2, DataSource.Factory manifestDataSourceFactory2, ParsingLoadable.Parser<? extends DashManifest> manifestParser2, DashChunkSource.Factory chunkSourceFactory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, long livePresentationDelayMs2, boolean livePresentationDelayOverridesManifest2, @Nullable Object tag2) {
        this.initialManifestUri = manifestUri2;
        this.manifest = manifest2;
        this.manifestUri = manifestUri2;
        this.manifestDataSourceFactory = manifestDataSourceFactory2;
        this.manifestParser = manifestParser2;
        this.chunkSourceFactory = chunkSourceFactory2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.livePresentationDelayMs = livePresentationDelayMs2;
        this.livePresentationDelayOverridesManifest = livePresentationDelayOverridesManifest2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.tag = tag2;
        this.sideloadedManifest = manifest2 != null;
        this.manifestEventDispatcher = createEventDispatcher(null);
        this.manifestUriLock = new Object();
        this.periodsById = new SparseArray<>();
        this.playerEmsgCallback = new DefaultPlayerEmsgCallback();
        this.expiredManifestPublishTimeUs = C0841C.TIME_UNSET;
        if (this.sideloadedManifest) {
            Assertions.checkState(true ^ manifest2.dynamic);
            this.manifestCallback = null;
            this.refreshManifestRunnable = null;
            this.simulateManifestRefreshRunnable = null;
            this.manifestLoadErrorThrower = new LoaderErrorThrower.Dummy();
            return;
        }
        this.manifestCallback = new ManifestCallback();
        this.manifestLoadErrorThrower = new ManifestLoadErrorThrower();
        this.refreshManifestRunnable = new DashMediaSource$$Lambda$0(this);
        this.simulateManifestRefreshRunnable = new DashMediaSource$$Lambda$1(this);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$0$DashMediaSource() {
        processManifest(false);
    }

    public void replaceManifestUri(Uri manifestUri2) {
        synchronized (this.manifestUriLock) {
            this.manifestUri = manifestUri2;
            this.initialManifestUri = manifestUri2;
        }
    }

    @Nullable
    public Object getTag() {
        return this.tag;
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener2) {
        this.mediaTransferListener = mediaTransferListener2;
        if (this.sideloadedManifest) {
            processManifest(false);
            return;
        }
        this.dataSource = this.manifestDataSourceFactory.createDataSource();
        this.loader = new Loader("Loader:DashMediaSource");
        this.handler = new Handler();
        bridge$lambda$0$DashMediaSource();
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.manifestLoadErrorThrower.maybeThrowError();
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId periodId, Allocator allocator, long startPositionUs) {
        MediaSource.MediaPeriodId mediaPeriodId = periodId;
        int periodIndex = ((Integer) mediaPeriodId.periodUid).intValue() - this.firstPeriodId;
        MediaSourceEventListener.EventDispatcher periodEventDispatcher = createEventDispatcher(mediaPeriodId, this.manifest.getPeriod(periodIndex).startMs);
        DashManifest dashManifest = this.manifest;
        DashChunkSource.Factory factory = this.chunkSourceFactory;
        TransferListener transferListener = this.mediaTransferListener;
        LoadErrorHandlingPolicy loadErrorHandlingPolicy2 = this.loadErrorHandlingPolicy;
        long j = this.elapsedRealtimeOffsetMs;
        LoaderErrorThrower loaderErrorThrower = this.manifestLoadErrorThrower;
        CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2 = this.compositeSequenceableLoaderFactory;
        CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory3 = compositeSequenceableLoaderFactory2;
        DashMediaPeriod mediaPeriod = new DashMediaPeriod(this.firstPeriodId + periodIndex, dashManifest, periodIndex, factory, transferListener, loadErrorHandlingPolicy2, periodEventDispatcher, j, loaderErrorThrower, allocator, compositeSequenceableLoaderFactory3, this.playerEmsgCallback);
        this.periodsById.put(mediaPeriod.f94id, mediaPeriod);
        return mediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        DashMediaPeriod dashMediaPeriod = (DashMediaPeriod) mediaPeriod;
        dashMediaPeriod.release();
        this.periodsById.remove(dashMediaPeriod.f94id);
    }

    public void releaseSourceInternal() {
        this.manifestLoadPending = false;
        this.dataSource = null;
        Loader loader2 = this.loader;
        if (loader2 != null) {
            loader2.release();
            this.loader = null;
        }
        this.manifestLoadStartTimestampMs = 0;
        this.manifestLoadEndTimestampMs = 0;
        this.manifest = this.sideloadedManifest ? this.manifest : null;
        this.manifestUri = this.initialManifestUri;
        this.manifestFatalError = null;
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
            this.handler = null;
        }
        this.elapsedRealtimeOffsetMs = 0;
        this.staleManifestReloadAttempt = 0;
        this.expiredManifestPublishTimeUs = C0841C.TIME_UNSET;
        this.firstPeriodId = 0;
        this.periodsById.clear();
    }

    /* access modifiers changed from: package-private */
    public void onDashManifestRefreshRequested() {
        this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
        bridge$lambda$0$DashMediaSource();
    }

    /* access modifiers changed from: package-private */
    public void onDashManifestPublishTimeExpired(long expiredManifestPublishTimeUs2) {
        long j = this.expiredManifestPublishTimeUs;
        if (j == C0841C.TIME_UNSET || j < expiredManifestPublishTimeUs2) {
            this.expiredManifestPublishTimeUs = expiredManifestPublishTimeUs2;
        }
    }

    /* access modifiers changed from: package-private */
    public void onManifestLoadCompleted(ParsingLoadable<DashManifest> loadable, long elapsedRealtimeMs, long loadDurationMs) {
        ParsingLoadable<DashManifest> parsingLoadable = loadable;
        long j = elapsedRealtimeMs;
        this.manifestEventDispatcher.loadCompleted(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), parsingLoadable.type, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        DashManifest newManifest = loadable.getResult();
        DashManifest dashManifest = this.manifest;
        boolean isSameUriInstance = false;
        int oldPeriodCount = dashManifest == null ? 0 : dashManifest.getPeriodCount();
        long newFirstPeriodStartTimeMs = newManifest.getPeriod(0).startMs;
        int removedPeriodCount = 0;
        while (removedPeriodCount < oldPeriodCount && this.manifest.getPeriod(removedPeriodCount).startMs < newFirstPeriodStartTimeMs) {
            removedPeriodCount++;
        }
        if (newManifest.dynamic) {
            boolean isManifestStale = false;
            if (oldPeriodCount - removedPeriodCount > newManifest.getPeriodCount()) {
                Log.m30w(TAG, "Loaded out of sync manifest");
                isManifestStale = true;
            } else if (this.expiredManifestPublishTimeUs != C0841C.TIME_UNSET && newManifest.publishTimeMs * 1000 <= this.expiredManifestPublishTimeUs) {
                long j2 = newManifest.publishTimeMs;
                long j3 = this.expiredManifestPublishTimeUs;
                StringBuilder sb = new StringBuilder(73);
                sb.append("Loaded stale dynamic manifest: ");
                sb.append(j2);
                sb.append(", ");
                sb.append(j3);
                Log.m30w(TAG, sb.toString());
                isManifestStale = true;
            }
            if (isManifestStale) {
                int i = this.staleManifestReloadAttempt;
                this.staleManifestReloadAttempt = i + 1;
                if (i < this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(parsingLoadable.type)) {
                    scheduleManifestRefresh(getManifestLoadRetryDelayMillis());
                    return;
                } else {
                    this.manifestFatalError = new DashManifestStaleException();
                    return;
                }
            } else {
                isSameUriInstance = false;
                this.staleManifestReloadAttempt = 0;
            }
        }
        this.manifest = newManifest;
        this.manifestLoadPending &= this.manifest.dynamic;
        this.manifestLoadStartTimestampMs = j - loadDurationMs;
        this.manifestLoadEndTimestampMs = j;
        if (this.manifest.location != null) {
            synchronized (this.manifestUriLock) {
                if (parsingLoadable.dataSpec.uri == this.manifestUri) {
                    isSameUriInstance = true;
                }
                if (isSameUriInstance) {
                    this.manifestUri = this.manifest.location;
                }
            }
        }
        if (oldPeriodCount != 0) {
            this.firstPeriodId += removedPeriodCount;
            processManifest(true);
        } else if (!this.manifest.dynamic || this.manifest.utcTiming == null) {
            processManifest(true);
        } else {
            resolveUtcTimingElement(this.manifest.utcTiming);
        }
    }

    /* access modifiers changed from: package-private */
    public Loader.LoadErrorAction onManifestLoadError(ParsingLoadable<DashManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
        Loader.LoadErrorAction loadErrorAction;
        ParsingLoadable<DashManifest> parsingLoadable = loadable;
        long retryDelayMs = this.loadErrorHandlingPolicy.getRetryDelayMsFor(4, loadDurationMs, error, errorCount);
        if (retryDelayMs == C0841C.TIME_UNSET) {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
        } else {
            loadErrorAction = Loader.createRetryAction(false, retryDelayMs);
        }
        this.manifestEventDispatcher.loadError(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), parsingLoadable.type, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded(), error, !loadErrorAction.isRetry());
        return loadErrorAction;
    }

    /* access modifiers changed from: package-private */
    public void onUtcTimestampLoadCompleted(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs) {
        ParsingLoadable<Long> parsingLoadable = loadable;
        this.manifestEventDispatcher.loadCompleted(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), parsingLoadable.type, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        onUtcTimestampResolved(loadable.getResult().longValue() - elapsedRealtimeMs);
    }

    /* access modifiers changed from: package-private */
    public Loader.LoadErrorAction onUtcTimestampLoadError(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error) {
        ParsingLoadable<Long> parsingLoadable = loadable;
        this.manifestEventDispatcher.loadError(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), parsingLoadable.type, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded(), error, true);
        onUtcTimestampResolutionError(error);
        return Loader.DONT_RETRY;
    }

    /* access modifiers changed from: package-private */
    public void onLoadCanceled(ParsingLoadable<?> loadable, long elapsedRealtimeMs, long loadDurationMs) {
        ParsingLoadable<?> parsingLoadable = loadable;
        this.manifestEventDispatcher.loadCanceled(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), parsingLoadable.type, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
    }

    private void resolveUtcTimingElement(UtcTimingElement timingElement) {
        String scheme = timingElement.schemeIdUri;
        if (Util.areEqual(scheme, "urn:mpeg:dash:utc:direct:2014") || Util.areEqual(scheme, "urn:mpeg:dash:utc:direct:2012")) {
            resolveUtcTimingElementDirect(timingElement);
        } else if (Util.areEqual(scheme, "urn:mpeg:dash:utc:http-iso:2014") || Util.areEqual(scheme, "urn:mpeg:dash:utc:http-iso:2012")) {
            resolveUtcTimingElementHttp(timingElement, new Iso8601Parser());
        } else if (Util.areEqual(scheme, "urn:mpeg:dash:utc:http-xsdate:2014") || Util.areEqual(scheme, "urn:mpeg:dash:utc:http-xsdate:2012")) {
            resolveUtcTimingElementHttp(timingElement, new XsDateTimeParser());
        } else {
            onUtcTimestampResolutionError(new IOException("Unsupported UTC timing scheme"));
        }
    }

    private void resolveUtcTimingElementDirect(UtcTimingElement timingElement) {
        try {
            onUtcTimestampResolved(Util.parseXsDateTime(timingElement.value) - this.manifestLoadEndTimestampMs);
        } catch (ParserException e) {
            onUtcTimestampResolutionError(e);
        }
    }

    private void resolveUtcTimingElementHttp(UtcTimingElement timingElement, ParsingLoadable.Parser<Long> parser) {
        startLoading(new ParsingLoadable(this.dataSource, Uri.parse(timingElement.value), 5, parser), new UtcTimestampCallback(), 1);
    }

    private void onUtcTimestampResolved(long elapsedRealtimeOffsetMs2) {
        this.elapsedRealtimeOffsetMs = elapsedRealtimeOffsetMs2;
        processManifest(true);
    }

    private void onUtcTimestampResolutionError(IOException error) {
        Log.m27e(TAG, "Failed to resolve UtcTiming element.", error);
        processManifest(true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    private void processManifest(boolean scheduleRefresh) {
        boolean windowChangingImplicitly;
        for (int i = 0; i < this.periodsById.size(); i++) {
            int id = this.periodsById.keyAt(i);
            if (id >= this.firstPeriodId) {
                this.periodsById.valueAt(i).updateManifest(this.manifest, id - this.firstPeriodId);
            }
        }
        boolean windowChangingImplicitly2 = false;
        int lastPeriodIndex = this.manifest.getPeriodCount() - 1;
        PeriodSeekInfo firstPeriodSeekInfo = PeriodSeekInfo.createPeriodSeekInfo(this.manifest.getPeriod(0), this.manifest.getPeriodDurationUs(0));
        PeriodSeekInfo lastPeriodSeekInfo = PeriodSeekInfo.createPeriodSeekInfo(this.manifest.getPeriod(lastPeriodIndex), this.manifest.getPeriodDurationUs(lastPeriodIndex));
        long currentStartTimeUs = firstPeriodSeekInfo.availableStartTimeUs;
        long currentEndTimeUs = lastPeriodSeekInfo.availableEndTimeUs;
        if (!this.manifest.dynamic || lastPeriodSeekInfo.isIndexExplicit) {
            windowChangingImplicitly = false;
        } else {
            currentEndTimeUs = Math.min((getNowUnixTimeUs() - C0841C.msToUs(this.manifest.availabilityStartTimeMs)) - C0841C.msToUs(this.manifest.getPeriod(lastPeriodIndex).startMs), currentEndTimeUs);
            if (this.manifest.timeShiftBufferDepthMs != C0841C.TIME_UNSET) {
                int periodIndex = lastPeriodIndex;
                long offsetInPeriodUs = currentEndTimeUs - C0841C.msToUs(this.manifest.timeShiftBufferDepthMs);
                while (offsetInPeriodUs < 0 && periodIndex > 0) {
                    periodIndex--;
                    offsetInPeriodUs += this.manifest.getPeriodDurationUs(periodIndex);
                    windowChangingImplicitly2 = windowChangingImplicitly2;
                }
                if (periodIndex == 0) {
                    currentStartTimeUs = Math.max(currentStartTimeUs, offsetInPeriodUs);
                } else {
                    currentStartTimeUs = this.manifest.getPeriodDurationUs(0);
                }
            }
            windowChangingImplicitly = true;
        }
        long windowDurationUs = currentEndTimeUs - currentStartTimeUs;
        for (int i2 = 0; i2 < this.manifest.getPeriodCount() - 1; i2++) {
            windowDurationUs += this.manifest.getPeriodDurationUs(i2);
        }
        long windowDefaultStartPositionUs = 0;
        if (this.manifest.dynamic) {
            long presentationDelayForManifestMs = this.livePresentationDelayMs;
            if (!this.livePresentationDelayOverridesManifest && this.manifest.suggestedPresentationDelayMs != C0841C.TIME_UNSET) {
                presentationDelayForManifestMs = this.manifest.suggestedPresentationDelayMs;
            }
            windowDefaultStartPositionUs = windowDurationUs - C0841C.msToUs(presentationDelayForManifestMs);
            if (windowDefaultStartPositionUs < MIN_LIVE_DEFAULT_START_POSITION_US) {
                windowDefaultStartPositionUs = Math.min((long) MIN_LIVE_DEFAULT_START_POSITION_US, windowDurationUs / 2);
            }
        }
        refreshSourceInfo(new DashTimeline(this.manifest.availabilityStartTimeMs, this.manifest.availabilityStartTimeMs + this.manifest.getPeriod(0).startMs + C0841C.usToMs(currentStartTimeUs), this.firstPeriodId, currentStartTimeUs, windowDurationUs, windowDefaultStartPositionUs, this.manifest, this.tag), this.manifest);
        if (!this.sideloadedManifest) {
            this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
            if (windowChangingImplicitly) {
                this.handler.postDelayed(this.simulateManifestRefreshRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            }
            if (this.manifestLoadPending) {
                bridge$lambda$0$DashMediaSource();
            } else if (scheduleRefresh && this.manifest.dynamic && this.manifest.minUpdatePeriodMs != C0841C.TIME_UNSET) {
                long minUpdatePeriodMs = this.manifest.minUpdatePeriodMs;
                if (minUpdatePeriodMs == 0) {
                    minUpdatePeriodMs = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
                }
                scheduleManifestRefresh(Math.max(0L, (this.manifestLoadStartTimestampMs + minUpdatePeriodMs) - SystemClock.elapsedRealtime()));
            }
        }
    }

    private void scheduleManifestRefresh(long delayUntilNextLoadMs) {
        this.handler.postDelayed(this.refreshManifestRunnable, delayUntilNextLoadMs);
    }

    /* access modifiers changed from: private */
    /* renamed from: startLoadingManifest */
    public void bridge$lambda$0$DashMediaSource() {
        Uri manifestUri2;
        this.handler.removeCallbacks(this.refreshManifestRunnable);
        if (this.loader.isLoading()) {
            this.manifestLoadPending = true;
            return;
        }
        synchronized (this.manifestUriLock) {
            manifestUri2 = this.manifestUri;
        }
        this.manifestLoadPending = false;
        startLoading(new ParsingLoadable(this.dataSource, manifestUri2, 4, this.manifestParser), this.manifestCallback, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(4));
    }

    private long getManifestLoadRetryDelayMillis() {
        return (long) Math.min((this.staleManifestReloadAttempt - 1) * 1000, 5000);
    }

    private <T> void startLoading(ParsingLoadable<T> loadable, Loader.Callback<ParsingLoadable<T>> callback, int minRetryCount) {
        this.manifestEventDispatcher.loadStarted(loadable.dataSpec, loadable.type, this.loader.startLoading(loadable, callback, minRetryCount));
    }

    private long getNowUnixTimeUs() {
        if (this.elapsedRealtimeOffsetMs != 0) {
            return C0841C.msToUs(SystemClock.elapsedRealtime() + this.elapsedRealtimeOffsetMs);
        }
        return C0841C.msToUs(System.currentTimeMillis());
    }

    private static final class PeriodSeekInfo {
        public final long availableEndTimeUs;
        public final long availableStartTimeUs;
        public final boolean isIndexExplicit;

        /* JADX INFO: Multiple debug info for r9v4 long: [D('availableEndTimeUs' long), D('adaptationSetCount' int)] */
        /* JADX INFO: Multiple debug info for r0v8 long: [D('adaptationSetAvailableEndTimeUs' long), D('lastSegmentNum' long)] */
        public static PeriodSeekInfo createPeriodSeekInfo(Period period, long durationUs) {
            boolean haveAudioVideoAdaptationSets;
            int adaptationSetCount;
            Period period2 = period;
            long j = durationUs;
            int adaptationSetCount2 = period2.adaptationSets.size();
            boolean haveAudioVideoAdaptationSets2 = false;
            int i = 0;
            while (true) {
                if (i >= adaptationSetCount2) {
                    break;
                }
                int type = period2.adaptationSets.get(i).type;
                if (type == 1 || type == 2) {
                    haveAudioVideoAdaptationSets2 = true;
                } else {
                    i++;
                }
            }
            haveAudioVideoAdaptationSets2 = true;
            int i2 = 0;
            long availableStartTimeUs2 = 0;
            boolean isIndexExplicit2 = false;
            boolean seenEmptyIndex = false;
            long availableStartTimeUs3 = Long.MAX_VALUE;
            while (i2 < adaptationSetCount2) {
                AdaptationSet adaptationSet = period2.adaptationSets.get(i2);
                if (!haveAudioVideoAdaptationSets2 || adaptationSet.type != 3) {
                    DashSegmentIndex index = adaptationSet.representations.get(0).getIndex();
                    if (index == null) {
                        return new PeriodSeekInfo(true, 0, durationUs);
                    }
                    DashSegmentIndex index2 = index;
                    adaptationSetCount = adaptationSetCount2;
                    haveAudioVideoAdaptationSets = haveAudioVideoAdaptationSets2;
                    long availableEndTimeUs2 = availableStartTimeUs3;
                    boolean isIndexExplicit3 = index2.isExplicit() | isIndexExplicit2;
                    DashSegmentIndex index3 = index2;
                    int segmentCount = index3.getSegmentCount(j);
                    if (segmentCount == 0) {
                        isIndexExplicit2 = isIndexExplicit3;
                        seenEmptyIndex = true;
                        availableStartTimeUs2 = 0;
                        availableStartTimeUs3 = 0;
                    } else if (!seenEmptyIndex) {
                        long firstSegmentNum = index3.getFirstSegmentNum();
                        boolean isIndexExplicit4 = isIndexExplicit3;
                        long availableStartTimeUs4 = Math.max(availableStartTimeUs2, index3.getTimeUs(firstSegmentNum));
                        if (segmentCount != -1) {
                            long lastSegmentNum = (((long) segmentCount) + firstSegmentNum) - 1;
                            availableStartTimeUs2 = availableStartTimeUs4;
                            isIndexExplicit2 = isIndexExplicit4;
                            availableStartTimeUs3 = Math.min(availableEndTimeUs2, index3.getTimeUs(lastSegmentNum) + index3.getDurationUs(lastSegmentNum, j));
                        } else {
                            availableStartTimeUs2 = availableStartTimeUs4;
                            isIndexExplicit2 = isIndexExplicit4;
                            availableStartTimeUs3 = availableEndTimeUs2;
                        }
                    } else {
                        isIndexExplicit2 = isIndexExplicit3;
                        availableStartTimeUs3 = availableEndTimeUs2;
                    }
                } else {
                    adaptationSetCount = adaptationSetCount2;
                    haveAudioVideoAdaptationSets = haveAudioVideoAdaptationSets2;
                }
                i2++;
                period2 = period;
                adaptationSetCount2 = adaptationSetCount;
                haveAudioVideoAdaptationSets2 = haveAudioVideoAdaptationSets;
            }
            return new PeriodSeekInfo(isIndexExplicit2, availableStartTimeUs2, availableStartTimeUs3);
        }

        private PeriodSeekInfo(boolean isIndexExplicit2, long availableStartTimeUs2, long availableEndTimeUs2) {
            this.isIndexExplicit = isIndexExplicit2;
            this.availableStartTimeUs = availableStartTimeUs2;
            this.availableEndTimeUs = availableEndTimeUs2;
        }
    }

    private static final class DashTimeline extends Timeline {
        private final int firstPeriodId;
        private final DashManifest manifest;
        private final long offsetInFirstPeriodUs;
        private final long presentationStartTimeMs;
        private final long windowDefaultStartPositionUs;
        private final long windowDurationUs;
        private final long windowStartTimeMs;
        @Nullable
        private final Object windowTag;

        public DashTimeline(long presentationStartTimeMs2, long windowStartTimeMs2, int firstPeriodId2, long offsetInFirstPeriodUs2, long windowDurationUs2, long windowDefaultStartPositionUs2, DashManifest manifest2, @Nullable Object windowTag2) {
            this.presentationStartTimeMs = presentationStartTimeMs2;
            this.windowStartTimeMs = windowStartTimeMs2;
            this.firstPeriodId = firstPeriodId2;
            this.offsetInFirstPeriodUs = offsetInFirstPeriodUs2;
            this.windowDurationUs = windowDurationUs2;
            this.windowDefaultStartPositionUs = windowDefaultStartPositionUs2;
            this.manifest = manifest2;
            this.windowTag = windowTag2;
        }

        public int getPeriodCount() {
            return this.manifest.getPeriodCount();
        }

        public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIdentifiers) {
            Assertions.checkIndex(periodIndex, 0, getPeriodCount());
            Integer uid = null;
            Object id = setIdentifiers ? this.manifest.getPeriod(periodIndex).f97id : null;
            if (setIdentifiers) {
                uid = Integer.valueOf(this.firstPeriodId + periodIndex);
            }
            return period.set(id, uid, 0, this.manifest.getPeriodDurationUs(periodIndex), C0841C.msToUs(this.manifest.getPeriod(periodIndex).startMs - this.manifest.getPeriod(0).startMs) - this.offsetInFirstPeriodUs);
        }

        public int getWindowCount() {
            return 1;
        }

        public Timeline.Window getWindow(int windowIndex, Timeline.Window window, boolean setTag, long defaultPositionProjectionUs) {
            Assertions.checkIndex(windowIndex, 0, 1);
            return window.set(setTag ? this.windowTag : null, this.presentationStartTimeMs, this.windowStartTimeMs, true, this.manifest.dynamic && this.manifest.minUpdatePeriodMs != C0841C.TIME_UNSET && this.manifest.durationMs == C0841C.TIME_UNSET, getAdjustedWindowDefaultStartPositionUs(defaultPositionProjectionUs), this.windowDurationUs, 0, getPeriodCount() - 1, this.offsetInFirstPeriodUs);
        }

        public int getIndexOfPeriod(Object uid) {
            int periodIndex;
            if ((uid instanceof Integer) && (periodIndex = ((Integer) uid).intValue() - this.firstPeriodId) >= 0 && periodIndex < getPeriodCount()) {
                return periodIndex;
            }
            return -1;
        }

        private long getAdjustedWindowDefaultStartPositionUs(long defaultPositionProjectionUs) {
            DashSegmentIndex snapIndex;
            long windowDefaultStartPositionUs2 = this.windowDefaultStartPositionUs;
            if (!this.manifest.dynamic) {
                return windowDefaultStartPositionUs2;
            }
            if (defaultPositionProjectionUs > 0) {
                windowDefaultStartPositionUs2 += defaultPositionProjectionUs;
                if (windowDefaultStartPositionUs2 > this.windowDurationUs) {
                    return C0841C.TIME_UNSET;
                }
            }
            int periodIndex = 0;
            long defaultStartPositionInPeriodUs = this.offsetInFirstPeriodUs + windowDefaultStartPositionUs2;
            long periodDurationUs = this.manifest.getPeriodDurationUs(0);
            while (periodIndex < this.manifest.getPeriodCount() - 1 && defaultStartPositionInPeriodUs >= periodDurationUs) {
                defaultStartPositionInPeriodUs -= periodDurationUs;
                periodIndex++;
                periodDurationUs = this.manifest.getPeriodDurationUs(periodIndex);
            }
            Period period = this.manifest.getPeriod(periodIndex);
            int videoAdaptationSetIndex = period.getAdaptationSetIndex(2);
            if (videoAdaptationSetIndex == -1 || (snapIndex = period.adaptationSets.get(videoAdaptationSetIndex).representations.get(0).getIndex()) == null || snapIndex.getSegmentCount(periodDurationUs) == 0) {
                return windowDefaultStartPositionUs2;
            }
            return (snapIndex.getTimeUs(snapIndex.getSegmentNum(defaultStartPositionInPeriodUs, periodDurationUs)) + windowDefaultStartPositionUs2) - defaultStartPositionInPeriodUs;
        }

        public Object getUidOfPeriod(int periodIndex) {
            Assertions.checkIndex(periodIndex, 0, getPeriodCount());
            return Integer.valueOf(this.firstPeriodId + periodIndex);
        }
    }

    private final class DefaultPlayerEmsgCallback implements PlayerEmsgHandler.PlayerEmsgCallback {
        private DefaultPlayerEmsgCallback() {
        }

        public void onDashManifestRefreshRequested() {
            DashMediaSource.this.onDashManifestRefreshRequested();
        }

        public void onDashManifestPublishTimeExpired(long expiredManifestPublishTimeUs) {
            DashMediaSource.this.onDashManifestPublishTimeExpired(expiredManifestPublishTimeUs);
        }
    }

    private final class ManifestCallback implements Loader.Callback<ParsingLoadable<DashManifest>> {
        private ManifestCallback() {
        }

        public /* bridge */ /* synthetic */ void onLoadCanceled(Loader.Loadable loadable, long j, long j2, boolean z) {
            onLoadCanceled((ParsingLoadable<DashManifest>) ((ParsingLoadable) loadable), j, j2, z);
        }

        public /* bridge */ /* synthetic */ void onLoadCompleted(Loader.Loadable loadable, long j, long j2) {
            onLoadCompleted((ParsingLoadable<DashManifest>) ((ParsingLoadable) loadable), j, j2);
        }

        public /* bridge */ /* synthetic */ Loader.LoadErrorAction onLoadError(Loader.Loadable loadable, long j, long j2, IOException iOException, int i) {
            return onLoadError((ParsingLoadable<DashManifest>) ((ParsingLoadable) loadable), j, j2, iOException, i);
        }

        public void onLoadCompleted(ParsingLoadable<DashManifest> loadable, long elapsedRealtimeMs, long loadDurationMs) {
            DashMediaSource.this.onManifestLoadCompleted(loadable, elapsedRealtimeMs, loadDurationMs);
        }

        public void onLoadCanceled(ParsingLoadable<DashManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
            DashMediaSource.this.onLoadCanceled(loadable, elapsedRealtimeMs, loadDurationMs);
        }

        public Loader.LoadErrorAction onLoadError(ParsingLoadable<DashManifest> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
            return DashMediaSource.this.onManifestLoadError(loadable, elapsedRealtimeMs, loadDurationMs, error, errorCount);
        }
    }

    private final class UtcTimestampCallback implements Loader.Callback<ParsingLoadable<Long>> {
        private UtcTimestampCallback() {
        }

        public /* bridge */ /* synthetic */ void onLoadCanceled(Loader.Loadable loadable, long j, long j2, boolean z) {
            onLoadCanceled((ParsingLoadable<Long>) ((ParsingLoadable) loadable), j, j2, z);
        }

        public /* bridge */ /* synthetic */ void onLoadCompleted(Loader.Loadable loadable, long j, long j2) {
            onLoadCompleted((ParsingLoadable<Long>) ((ParsingLoadable) loadable), j, j2);
        }

        public /* bridge */ /* synthetic */ Loader.LoadErrorAction onLoadError(Loader.Loadable loadable, long j, long j2, IOException iOException, int i) {
            return onLoadError((ParsingLoadable<Long>) ((ParsingLoadable) loadable), j, j2, iOException, i);
        }

        public void onLoadCompleted(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs) {
            DashMediaSource.this.onUtcTimestampLoadCompleted(loadable, elapsedRealtimeMs, loadDurationMs);
        }

        public void onLoadCanceled(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
            DashMediaSource.this.onLoadCanceled(loadable, elapsedRealtimeMs, loadDurationMs);
        }

        public Loader.LoadErrorAction onLoadError(ParsingLoadable<Long> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
            return DashMediaSource.this.onUtcTimestampLoadError(loadable, elapsedRealtimeMs, loadDurationMs, error);
        }
    }

    private static final class XsDateTimeParser implements ParsingLoadable.Parser<Long> {
        private XsDateTimeParser() {
        }

        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            return Long.valueOf(Util.parseXsDateTime(new BufferedReader(new InputStreamReader(inputStream)).readLine()));
        }
    }

    static final class Iso8601Parser implements ParsingLoadable.Parser<Long> {
        private static final Pattern TIMESTAMP_WITH_TIMEZONE_PATTERN = Pattern.compile("(.+?)(Z|((\\+|-|−)(\\d\\d)(:?(\\d\\d))?))");

        Iso8601Parser() {
        }

        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            String firstLine = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8"))).readLine();
            try {
                Matcher matcher = TIMESTAMP_WITH_TIMEZONE_PATTERN.matcher(firstLine);
                if (!matcher.matches()) {
                    String valueOf = String.valueOf(firstLine);
                    throw new ParserException(valueOf.length() != 0 ? "Couldn't parse timestamp: ".concat(valueOf) : new String("Couldn't parse timestamp: "));
                }
                String timestampWithoutTimezone = matcher.group(1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                long timestampMs = format.parse(timestampWithoutTimezone).getTime();
                if (!"Z".equals(matcher.group(2))) {
                    long sign = "+".equals(matcher.group(4)) ? 1 : -1;
                    long hours = Long.parseLong(matcher.group(5));
                    String minutesString = matcher.group(7);
                    timestampMs -= ((((hours * 60) + (TextUtils.isEmpty(minutesString) ? 0 : Long.parseLong(minutesString))) * 60) * 1000) * sign;
                }
                return Long.valueOf(timestampMs);
            } catch (ParseException e) {
                throw new ParserException(e);
            }
        }
    }

    final class ManifestLoadErrorThrower implements LoaderErrorThrower {
        ManifestLoadErrorThrower() {
        }

        public void maybeThrowError() throws IOException {
            DashMediaSource.this.loader.maybeThrowError();
            maybeThrowManifestError();
        }

        public void maybeThrowError(int minRetryCount) throws IOException {
            DashMediaSource.this.loader.maybeThrowError(minRetryCount);
            maybeThrowManifestError();
        }

        private void maybeThrowManifestError() throws IOException {
            if (DashMediaSource.this.manifestFatalError != null) {
                throw DashMediaSource.this.manifestFatalError;
            }
        }
    }
}
