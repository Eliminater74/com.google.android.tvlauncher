package com.google.android.exoplayer2.source.hls.playlist;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class DefaultHlsPlaylistTracker implements HlsPlaylistTracker, Loader.Callback<ParsingLoadable<HlsPlaylist>> {
    public static final double DEFAULT_PLAYLIST_STUCK_TARGET_DURATION_COEFFICIENT = 3.5d;
    public static final HlsPlaylistTracker.Factory FACTORY = DefaultHlsPlaylistTracker$$Lambda$0.$instance;
    /* access modifiers changed from: private */
    public final HlsDataSourceFactory dataSourceFactory;
    /* access modifiers changed from: private */
    public final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    /* access modifiers changed from: private */
    public final double playlistStuckTargetDurationCoefficient;
    private final List<HlsPlaylistTracker.PlaylistEventListener> listeners;
    private final HashMap<Uri, MediaPlaylistBundle> playlistBundles;
    private final HlsPlaylistParserFactory playlistParserFactory;
    /* access modifiers changed from: private */
    @Nullable
    public MediaSourceEventListener.EventDispatcher eventDispatcher;
    /* access modifiers changed from: private */
    @Nullable
    public ParsingLoadable.Parser<HlsPlaylist> mediaPlaylistParser;
    /* access modifiers changed from: private */
    @Nullable
    public Handler playlistRefreshHandler;
    /* access modifiers changed from: private */
    @Nullable
    public Uri primaryMediaPlaylistUrl;
    @Nullable
    private Loader initialPlaylistLoader;
    private long initialStartTimeUs;
    private boolean isLive;
    @Nullable
    private HlsMasterPlaylist masterPlaylist;
    @Nullable
    private HlsMediaPlaylist primaryMediaPlaylistSnapshot;
    @Nullable
    private HlsPlaylistTracker.PrimaryPlaylistListener primaryPlaylistListener;

    public DefaultHlsPlaylistTracker(HlsDataSourceFactory dataSourceFactory2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, HlsPlaylistParserFactory playlistParserFactory2) {
        this(dataSourceFactory2, loadErrorHandlingPolicy2, playlistParserFactory2, 3.5d);
    }

    public DefaultHlsPlaylistTracker(HlsDataSourceFactory dataSourceFactory2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, HlsPlaylistParserFactory playlistParserFactory2, double playlistStuckTargetDurationCoefficient2) {
        this.dataSourceFactory = dataSourceFactory2;
        this.playlistParserFactory = playlistParserFactory2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.playlistStuckTargetDurationCoefficient = playlistStuckTargetDurationCoefficient2;
        this.listeners = new ArrayList();
        this.playlistBundles = new HashMap<>();
        this.initialStartTimeUs = C0841C.TIME_UNSET;
    }

    private static HlsMediaPlaylist.Segment getFirstOldOverlappingSegment(HlsMediaPlaylist oldPlaylist, HlsMediaPlaylist loadedPlaylist) {
        int mediaSequenceOffset = (int) (loadedPlaylist.mediaSequence - oldPlaylist.mediaSequence);
        List<HlsMediaPlaylist.Segment> oldSegments = oldPlaylist.segments;
        if (mediaSequenceOffset < oldSegments.size()) {
            return oldSegments.get(mediaSequenceOffset);
        }
        return null;
    }

    public /* bridge */ /* synthetic */ void onLoadCanceled(Loader.Loadable loadable, long j, long j2, boolean z) {
        onLoadCanceled((ParsingLoadable<HlsPlaylist>) ((ParsingLoadable) loadable), j, j2, z);
    }

    public /* bridge */ /* synthetic */ void onLoadCompleted(Loader.Loadable loadable, long j, long j2) {
        onLoadCompleted((ParsingLoadable<HlsPlaylist>) ((ParsingLoadable) loadable), j, j2);
    }

    public /* bridge */ /* synthetic */ Loader.LoadErrorAction onLoadError(Loader.Loadable loadable, long j, long j2, IOException iOException, int i) {
        return onLoadError((ParsingLoadable<HlsPlaylist>) ((ParsingLoadable) loadable), j, j2, iOException, i);
    }

    public void start(Uri initialPlaylistUri, MediaSourceEventListener.EventDispatcher eventDispatcher2, HlsPlaylistTracker.PrimaryPlaylistListener primaryPlaylistListener2) {
        this.playlistRefreshHandler = new Handler();
        this.eventDispatcher = eventDispatcher2;
        this.primaryPlaylistListener = primaryPlaylistListener2;
        ParsingLoadable<HlsPlaylist> masterPlaylistLoadable = new ParsingLoadable<>(this.dataSourceFactory.createDataSource(4), initialPlaylistUri, 4, this.playlistParserFactory.createPlaylistParser());
        Assertions.checkState(this.initialPlaylistLoader == null);
        this.initialPlaylistLoader = new Loader("DefaultHlsPlaylistTracker:MasterPlaylist");
        eventDispatcher2.loadStarted(masterPlaylistLoadable.dataSpec, masterPlaylistLoadable.type, this.initialPlaylistLoader.startLoading(masterPlaylistLoadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(masterPlaylistLoadable.type)));
    }

    public void stop() {
        this.primaryMediaPlaylistUrl = null;
        this.primaryMediaPlaylistSnapshot = null;
        this.masterPlaylist = null;
        this.initialStartTimeUs = C0841C.TIME_UNSET;
        this.initialPlaylistLoader.release();
        this.initialPlaylistLoader = null;
        for (MediaPlaylistBundle bundle : this.playlistBundles.values()) {
            bundle.release();
        }
        this.playlistRefreshHandler.removeCallbacksAndMessages(null);
        this.playlistRefreshHandler = null;
        this.playlistBundles.clear();
    }

    public void addListener(HlsPlaylistTracker.PlaylistEventListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(HlsPlaylistTracker.PlaylistEventListener listener) {
        this.listeners.remove(listener);
    }

    @Nullable
    public HlsMasterPlaylist getMasterPlaylist() {
        return this.masterPlaylist;
    }

    public HlsMediaPlaylist getPlaylistSnapshot(Uri url, boolean isForPlayback) {
        HlsMediaPlaylist snapshot = this.playlistBundles.get(url).getPlaylistSnapshot();
        if (snapshot != null && isForPlayback) {
            maybeSetPrimaryUrl(url);
        }
        return snapshot;
    }

    public long getInitialStartTimeUs() {
        return this.initialStartTimeUs;
    }

    public boolean isSnapshotValid(Uri url) {
        return this.playlistBundles.get(url).isSnapshotValid();
    }

    public void maybeThrowPrimaryPlaylistRefreshError() throws IOException {
        Loader loader = this.initialPlaylistLoader;
        if (loader != null) {
            loader.maybeThrowError();
        }
        Uri uri = this.primaryMediaPlaylistUrl;
        if (uri != null) {
            maybeThrowPlaylistRefreshError(uri);
        }
    }

    public void maybeThrowPlaylistRefreshError(Uri url) throws IOException {
        this.playlistBundles.get(url).maybeThrowPlaylistRefreshError();
    }

    public void refreshPlaylist(Uri url) {
        this.playlistBundles.get(url).loadPlaylist();
    }

    public boolean isLive() {
        return this.isLive;
    }

    public void onLoadCompleted(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs) {
        HlsMasterPlaylist masterPlaylist2;
        HlsPlaylist result = loadable.getResult();
        boolean isMediaPlaylist = result instanceof HlsMediaPlaylist;
        if (isMediaPlaylist) {
            masterPlaylist2 = HlsMasterPlaylist.createSingleVariantMasterPlaylist(result.baseUri);
        } else {
            masterPlaylist2 = (HlsMasterPlaylist) result;
        }
        this.masterPlaylist = masterPlaylist2;
        this.mediaPlaylistParser = this.playlistParserFactory.createPlaylistParser(masterPlaylist2);
        this.primaryMediaPlaylistUrl = masterPlaylist2.variants.get(0).url;
        createBundles(masterPlaylist2.mediaPlaylistUrls);
        MediaPlaylistBundle primaryBundle = this.playlistBundles.get(this.primaryMediaPlaylistUrl);
        if (isMediaPlaylist) {
            primaryBundle.processLoadedPlaylist((HlsMediaPlaylist) result, loadDurationMs);
        } else {
            primaryBundle.loadPlaylist();
        }
        this.eventDispatcher.loadCompleted(loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), 4, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
    }

    public void onLoadCanceled(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        this.eventDispatcher.loadCanceled(loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), 4, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
    }

    public Loader.LoadErrorAction onLoadError(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
        ParsingLoadable<HlsPlaylist> parsingLoadable = loadable;
        long retryDelayMs = this.loadErrorHandlingPolicy.getRetryDelayMsFor(parsingLoadable.type, loadDurationMs, error, errorCount);
        boolean isFatal = retryDelayMs == C0841C.TIME_UNSET;
        this.eventDispatcher.loadError(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), 4, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded(), error, isFatal);
        if (isFatal) {
            return Loader.DONT_RETRY_FATAL;
        }
        return Loader.createRetryAction(false, retryDelayMs);
    }

    /* access modifiers changed from: private */
    public boolean maybeSelectNewPrimaryUrl() {
        List<HlsMasterPlaylist.Variant> variants = this.masterPlaylist.variants;
        int variantsSize = variants.size();
        long currentTimeMs = SystemClock.elapsedRealtime();
        for (int i = 0; i < variantsSize; i++) {
            MediaPlaylistBundle bundle = this.playlistBundles.get(variants.get(i).url);
            if (currentTimeMs > bundle.blacklistUntilMs) {
                this.primaryMediaPlaylistUrl = bundle.playlistUrl;
                bundle.loadPlaylist();
                return true;
            }
        }
        return false;
    }

    private void maybeSetPrimaryUrl(Uri url) {
        if (!url.equals(this.primaryMediaPlaylistUrl) && isVariantUrl(url)) {
            HlsMediaPlaylist hlsMediaPlaylist = this.primaryMediaPlaylistSnapshot;
            if (hlsMediaPlaylist == null || !hlsMediaPlaylist.hasEndTag) {
                this.primaryMediaPlaylistUrl = url;
                this.playlistBundles.get(this.primaryMediaPlaylistUrl).loadPlaylist();
            }
        }
    }

    private boolean isVariantUrl(Uri playlistUrl) {
        List<HlsMasterPlaylist.Variant> variants = this.masterPlaylist.variants;
        for (int i = 0; i < variants.size(); i++) {
            if (playlistUrl.equals(variants.get(i).url)) {
                return true;
            }
        }
        return false;
    }

    private void createBundles(List<Uri> urls) {
        int listSize = urls.size();
        for (int i = 0; i < listSize; i++) {
            Uri url = urls.get(i);
            this.playlistBundles.put(url, new MediaPlaylistBundle(url));
        }
    }

    /* access modifiers changed from: private */
    public void onPlaylistUpdated(Uri url, HlsMediaPlaylist newSnapshot) {
        if (url.equals(this.primaryMediaPlaylistUrl)) {
            if (this.primaryMediaPlaylistSnapshot == null) {
                this.isLive = !newSnapshot.hasEndTag;
                this.initialStartTimeUs = newSnapshot.startTimeUs;
            }
            this.primaryMediaPlaylistSnapshot = newSnapshot;
            this.primaryPlaylistListener.onPrimaryPlaylistRefreshed(newSnapshot);
        }
        int listenersSize = this.listeners.size();
        for (int i = 0; i < listenersSize; i++) {
            this.listeners.get(i).onPlaylistChanged();
        }
    }

    /* access modifiers changed from: private */
    public boolean notifyPlaylistError(Uri playlistUrl, long blacklistDurationMs) {
        int listenersSize = this.listeners.size();
        boolean anyBlacklistingFailed = false;
        for (int i = 0; i < listenersSize; i++) {
            anyBlacklistingFailed |= !this.listeners.get(i).onPlaylistError(playlistUrl, blacklistDurationMs);
        }
        return anyBlacklistingFailed;
    }

    /* access modifiers changed from: private */
    public HlsMediaPlaylist getLatestPlaylistSnapshot(HlsMediaPlaylist oldPlaylist, HlsMediaPlaylist loadedPlaylist) {
        if (loadedPlaylist.isNewerThan(oldPlaylist)) {
            return loadedPlaylist.copyWith(getLoadedPlaylistStartTimeUs(oldPlaylist, loadedPlaylist), getLoadedPlaylistDiscontinuitySequence(oldPlaylist, loadedPlaylist));
        }
        if (loadedPlaylist.hasEndTag) {
            return oldPlaylist.copyWithEndTag();
        }
        return oldPlaylist;
    }

    private long getLoadedPlaylistStartTimeUs(HlsMediaPlaylist oldPlaylist, HlsMediaPlaylist loadedPlaylist) {
        if (loadedPlaylist.hasProgramDateTime) {
            return loadedPlaylist.startTimeUs;
        }
        HlsMediaPlaylist hlsMediaPlaylist = this.primaryMediaPlaylistSnapshot;
        long primarySnapshotStartTimeUs = hlsMediaPlaylist != null ? hlsMediaPlaylist.startTimeUs : 0;
        if (oldPlaylist == null) {
            return primarySnapshotStartTimeUs;
        }
        int oldPlaylistSize = oldPlaylist.segments.size();
        HlsMediaPlaylist.Segment firstOldOverlappingSegment = getFirstOldOverlappingSegment(oldPlaylist, loadedPlaylist);
        if (firstOldOverlappingSegment != null) {
            return oldPlaylist.startTimeUs + firstOldOverlappingSegment.relativeStartTimeUs;
        }
        if (((long) oldPlaylistSize) == loadedPlaylist.mediaSequence - oldPlaylist.mediaSequence) {
            return oldPlaylist.getEndTimeUs();
        }
        return primarySnapshotStartTimeUs;
    }

    private int getLoadedPlaylistDiscontinuitySequence(HlsMediaPlaylist oldPlaylist, HlsMediaPlaylist loadedPlaylist) {
        int primaryUrlDiscontinuitySequence;
        HlsMediaPlaylist.Segment firstOldOverlappingSegment;
        if (loadedPlaylist.hasDiscontinuitySequence) {
            return loadedPlaylist.discontinuitySequence;
        }
        HlsMediaPlaylist hlsMediaPlaylist = this.primaryMediaPlaylistSnapshot;
        if (hlsMediaPlaylist != null) {
            primaryUrlDiscontinuitySequence = hlsMediaPlaylist.discontinuitySequence;
        } else {
            primaryUrlDiscontinuitySequence = 0;
        }
        if (oldPlaylist == null || (firstOldOverlappingSegment = getFirstOldOverlappingSegment(oldPlaylist, loadedPlaylist)) == null) {
            return primaryUrlDiscontinuitySequence;
        }
        return (oldPlaylist.discontinuitySequence + firstOldOverlappingSegment.relativeDiscontinuitySequence) - loadedPlaylist.segments.get(0).relativeDiscontinuitySequence;
    }

    private final class MediaPlaylistBundle implements Loader.Callback<ParsingLoadable<HlsPlaylist>>, Runnable {
        /* access modifiers changed from: private */
        public final Uri playlistUrl;
        private final ParsingLoadable<HlsPlaylist> mediaPlaylistLoadable;
        private final Loader mediaPlaylistLoader = new Loader("DefaultHlsPlaylistTracker:MediaPlaylist");
        /* access modifiers changed from: private */
        public long blacklistUntilMs;
        private long earliestNextLoadTimeMs;
        private long lastSnapshotChangeMs;
        private long lastSnapshotLoadMs;
        private boolean loadPending;
        private IOException playlistError;
        private HlsMediaPlaylist playlistSnapshot;

        public MediaPlaylistBundle(Uri playlistUrl2) {
            this.playlistUrl = playlistUrl2;
            this.mediaPlaylistLoadable = new ParsingLoadable<>(DefaultHlsPlaylistTracker.this.dataSourceFactory.createDataSource(4), playlistUrl2, 4, DefaultHlsPlaylistTracker.this.mediaPlaylistParser);
        }

        public /* bridge */ /* synthetic */ void onLoadCanceled(Loader.Loadable loadable, long j, long j2, boolean z) {
            onLoadCanceled((ParsingLoadable<HlsPlaylist>) ((ParsingLoadable) loadable), j, j2, z);
        }

        public /* bridge */ /* synthetic */ void onLoadCompleted(Loader.Loadable loadable, long j, long j2) {
            onLoadCompleted((ParsingLoadable<HlsPlaylist>) ((ParsingLoadable) loadable), j, j2);
        }

        public /* bridge */ /* synthetic */ Loader.LoadErrorAction onLoadError(Loader.Loadable loadable, long j, long j2, IOException iOException, int i) {
            return onLoadError((ParsingLoadable<HlsPlaylist>) ((ParsingLoadable) loadable), j, j2, iOException, i);
        }

        public HlsMediaPlaylist getPlaylistSnapshot() {
            return this.playlistSnapshot;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.lang.Math.max(long, long):long}
         arg types: [int, long]
         candidates:
          ClspMth{java.lang.Math.max(double, double):double}
          ClspMth{java.lang.Math.max(int, int):int}
          ClspMth{java.lang.Math.max(float, float):float}
          ClspMth{java.lang.Math.max(long, long):long} */
        public boolean isSnapshotValid() {
            if (this.playlistSnapshot == null) {
                return false;
            }
            long currentTimeMs = SystemClock.elapsedRealtime();
            long snapshotValidityDurationMs = Math.max(30000L, C0841C.usToMs(this.playlistSnapshot.durationUs));
            if (this.playlistSnapshot.hasEndTag || this.playlistSnapshot.playlistType == 2 || this.playlistSnapshot.playlistType == 1 || this.lastSnapshotLoadMs + snapshotValidityDurationMs > currentTimeMs) {
                return true;
            }
            return false;
        }

        public void release() {
            this.mediaPlaylistLoader.release();
        }

        public void loadPlaylist() {
            this.blacklistUntilMs = 0;
            if (!this.loadPending && !this.mediaPlaylistLoader.isLoading()) {
                long currentTimeMs = SystemClock.elapsedRealtime();
                if (currentTimeMs < this.earliestNextLoadTimeMs) {
                    this.loadPending = true;
                    DefaultHlsPlaylistTracker.this.playlistRefreshHandler.postDelayed(this, this.earliestNextLoadTimeMs - currentTimeMs);
                    return;
                }
                loadPlaylistImmediately();
            }
        }

        public void maybeThrowPlaylistRefreshError() throws IOException {
            this.mediaPlaylistLoader.maybeThrowError();
            IOException iOException = this.playlistError;
            if (iOException != null) {
                throw iOException;
            }
        }

        public void onLoadCompleted(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs) {
            HlsPlaylist result = loadable.getResult();
            if (result instanceof HlsMediaPlaylist) {
                processLoadedPlaylist((HlsMediaPlaylist) result, loadDurationMs);
                DefaultHlsPlaylistTracker.this.eventDispatcher.loadCompleted(loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), 4, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
                return;
            }
            this.playlistError = new ParserException("Loaded playlist has unexpected type.");
        }

        public void onLoadCanceled(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
            DefaultHlsPlaylistTracker.this.eventDispatcher.loadCanceled(loadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), 4, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        }

        public Loader.LoadErrorAction onLoadError(ParsingLoadable<HlsPlaylist> loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
            Loader.LoadErrorAction loadErrorAction;
            ParsingLoadable<HlsPlaylist> parsingLoadable = loadable;
            long blacklistDurationMs = DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.getBlacklistDurationMsFor(parsingLoadable.type, loadDurationMs, error, errorCount);
            boolean shouldBlacklist = blacklistDurationMs != C0841C.TIME_UNSET;
            boolean blacklistingFailed = DefaultHlsPlaylistTracker.this.notifyPlaylistError(this.playlistUrl, blacklistDurationMs) || !shouldBlacklist;
            if (shouldBlacklist) {
                blacklistingFailed |= blacklistPlaylist(blacklistDurationMs);
            }
            if (blacklistingFailed) {
                long retryDelay = DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.getRetryDelayMsFor(parsingLoadable.type, loadDurationMs, error, errorCount);
                if (retryDelay != C0841C.TIME_UNSET) {
                    loadErrorAction = Loader.createRetryAction(false, retryDelay);
                } else {
                    loadErrorAction = Loader.DONT_RETRY_FATAL;
                }
            } else {
                loadErrorAction = Loader.DONT_RETRY;
            }
            DefaultHlsPlaylistTracker.this.eventDispatcher.loadError(parsingLoadable.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), 4, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded(), error, !loadErrorAction.isRetry());
            return loadErrorAction;
        }

        public void run() {
            this.loadPending = false;
            loadPlaylistImmediately();
        }

        private void loadPlaylistImmediately() {
            DefaultHlsPlaylistTracker.this.eventDispatcher.loadStarted(this.mediaPlaylistLoadable.dataSpec, this.mediaPlaylistLoadable.type, this.mediaPlaylistLoader.startLoading(this.mediaPlaylistLoadable, this, DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(this.mediaPlaylistLoadable.type)));
        }

        /* access modifiers changed from: private */
        public void processLoadedPlaylist(HlsMediaPlaylist loadedPlaylist, long loadDurationMs) {
            long j;
            HlsMediaPlaylist hlsMediaPlaylist = loadedPlaylist;
            HlsMediaPlaylist oldPlaylist = this.playlistSnapshot;
            long currentTimeMs = SystemClock.elapsedRealtime();
            this.lastSnapshotLoadMs = currentTimeMs;
            this.playlistSnapshot = DefaultHlsPlaylistTracker.this.getLatestPlaylistSnapshot(oldPlaylist, hlsMediaPlaylist);
            HlsMediaPlaylist hlsMediaPlaylist2 = this.playlistSnapshot;
            if (hlsMediaPlaylist2 != oldPlaylist) {
                this.playlistError = null;
                this.lastSnapshotChangeMs = currentTimeMs;
                DefaultHlsPlaylistTracker.this.onPlaylistUpdated(this.playlistUrl, hlsMediaPlaylist2);
            } else if (!hlsMediaPlaylist2.hasEndTag) {
                if (hlsMediaPlaylist.mediaSequence + ((long) hlsMediaPlaylist.segments.size()) < this.playlistSnapshot.mediaSequence) {
                    this.playlistError = new HlsPlaylistTracker.PlaylistResetException(this.playlistUrl);
                    boolean unused = DefaultHlsPlaylistTracker.this.notifyPlaylistError(this.playlistUrl, C0841C.TIME_UNSET);
                } else {
                    double usToMs = (double) C0841C.usToMs(this.playlistSnapshot.targetDurationUs);
                    double access$1100 = DefaultHlsPlaylistTracker.this.playlistStuckTargetDurationCoefficient;
                    Double.isNaN(usToMs);
                    if (((double) (currentTimeMs - this.lastSnapshotChangeMs)) > usToMs * access$1100) {
                        this.playlistError = new HlsPlaylistTracker.PlaylistStuckException(this.playlistUrl);
                        long blacklistDurationMs = DefaultHlsPlaylistTracker.this.loadErrorHandlingPolicy.getBlacklistDurationMsFor(4, loadDurationMs, this.playlistError, 1);
                        boolean unused2 = DefaultHlsPlaylistTracker.this.notifyPlaylistError(this.playlistUrl, blacklistDurationMs);
                        if (blacklistDurationMs != C0841C.TIME_UNSET) {
                            blacklistPlaylist(blacklistDurationMs);
                        }
                    }
                }
            }
            HlsMediaPlaylist hlsMediaPlaylist3 = this.playlistSnapshot;
            if (hlsMediaPlaylist3 != oldPlaylist) {
                j = hlsMediaPlaylist3.targetDurationUs;
            } else {
                j = hlsMediaPlaylist3.targetDurationUs / 2;
            }
            this.earliestNextLoadTimeMs = C0841C.usToMs(j) + currentTimeMs;
            if (this.playlistUrl.equals(DefaultHlsPlaylistTracker.this.primaryMediaPlaylistUrl) && !this.playlistSnapshot.hasEndTag) {
                loadPlaylist();
            }
        }

        private boolean blacklistPlaylist(long blacklistDurationMs) {
            this.blacklistUntilMs = SystemClock.elapsedRealtime() + blacklistDurationMs;
            return this.playlistUrl.equals(DefaultHlsPlaylistTracker.this.primaryMediaPlaylistUrl) && !DefaultHlsPlaylistTracker.this.maybeSelectNewPrimaryUrl();
        }
    }
}
