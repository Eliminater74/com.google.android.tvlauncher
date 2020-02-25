package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.FilteringHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;

import java.io.IOException;
import java.util.List;

public final class HlsMediaSource extends BaseMediaSource implements HlsPlaylistTracker.PrimaryPlaylistListener {
    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.hls");
    }

    private final boolean allowChunklessPreparation;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private final HlsExtractorFactory extractorFactory;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final Uri manifestUri;
    private final HlsPlaylistTracker playlistTracker;
    @Nullable
    private final Object tag;
    private final boolean useSessionKeys;
    @Nullable
    private TransferListener mediaTransferListener;

    private HlsMediaSource(Uri manifestUri2, HlsDataSourceFactory dataSourceFactory2, HlsExtractorFactory extractorFactory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, HlsPlaylistTracker playlistTracker2, boolean allowChunklessPreparation2, boolean useSessionKeys2, @Nullable Object tag2) {
        this.manifestUri = manifestUri2;
        this.dataSourceFactory = dataSourceFactory2;
        this.extractorFactory = extractorFactory2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.playlistTracker = playlistTracker2;
        this.allowChunklessPreparation = allowChunklessPreparation2;
        this.useSessionKeys = useSessionKeys2;
        this.tag = tag2;
    }

    @Nullable
    public Object getTag() {
        return this.tag;
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener2) {
        this.mediaTransferListener = mediaTransferListener2;
        this.playlistTracker.start(this.manifestUri, createEventDispatcher(null), this);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.playlistTracker.maybeThrowPrimaryPlaylistRefreshError();
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        return new HlsMediaPeriod(this.extractorFactory, this.playlistTracker, this.dataSourceFactory, this.mediaTransferListener, this.loadErrorHandlingPolicy, createEventDispatcher(id), allocator, this.compositeSequenceableLoaderFactory, this.allowChunklessPreparation, this.useSessionKeys);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((HlsMediaPeriod) mediaPeriod).release();
    }

    public void releaseSourceInternal() {
        this.playlistTracker.stop();
    }

    public void onPrimaryPlaylistRefreshed(HlsMediaPlaylist playlist) {
        long windowStartTimeMs;
        long presentationStartTimeMs;
        SinglePeriodTimeline timeline;
        long windowDefaultStartPositionUs;
        HlsMediaPlaylist hlsMediaPlaylist = playlist;
        if (hlsMediaPlaylist.hasProgramDateTime) {
            windowStartTimeMs = C0841C.usToMs(hlsMediaPlaylist.startTimeUs);
        } else {
            windowStartTimeMs = -9223372036854775807L;
        }
        if (hlsMediaPlaylist.playlistType == 2 || hlsMediaPlaylist.playlistType == 1) {
            presentationStartTimeMs = windowStartTimeMs;
        } else {
            presentationStartTimeMs = -9223372036854775807L;
        }
        long windowDefaultStartPositionUs2 = hlsMediaPlaylist.startOffsetUs;
        if (this.playlistTracker.isLive()) {
            long offsetFromInitialStartTimeUs = hlsMediaPlaylist.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
            long periodDurationUs = hlsMediaPlaylist.hasEndTag ? offsetFromInitialStartTimeUs + hlsMediaPlaylist.durationUs : -9223372036854775807L;
            List<HlsMediaPlaylist.Segment> segments = hlsMediaPlaylist.segments;
            if (windowDefaultStartPositionUs2 != C0841C.TIME_UNSET) {
                windowDefaultStartPositionUs = windowDefaultStartPositionUs2;
            } else if (segments.isEmpty()) {
                windowDefaultStartPositionUs = 0;
            } else {
                windowDefaultStartPositionUs = segments.get(Math.max(0, segments.size() - 3)).relativeStartTimeUs;
            }
            timeline = new SinglePeriodTimeline(presentationStartTimeMs, windowStartTimeMs, periodDurationUs, hlsMediaPlaylist.durationUs, offsetFromInitialStartTimeUs, windowDefaultStartPositionUs, true, !hlsMediaPlaylist.hasEndTag, this.tag);
        } else {
            if (windowDefaultStartPositionUs2 == C0841C.TIME_UNSET) {
                windowDefaultStartPositionUs2 = 0;
            }
            timeline = new SinglePeriodTimeline(presentationStartTimeMs, windowStartTimeMs, hlsMediaPlaylist.durationUs, hlsMediaPlaylist.durationUs, 0, windowDefaultStartPositionUs2, true, false, this.tag);
        }
        refreshSourceInfo(timeline, new HlsManifest(this.playlistTracker.getMasterPlaylist(), hlsMediaPlaylist));
    }

    public static final class Factory implements AdsMediaSource.MediaSourceFactory {
        private final HlsDataSourceFactory hlsDataSourceFactory;
        private boolean allowChunklessPreparation;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private HlsExtractorFactory extractorFactory;
        private boolean isCreateCalled;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private HlsPlaylistParserFactory playlistParserFactory;
        private HlsPlaylistTracker.Factory playlistTrackerFactory;
        @Nullable
        private List<StreamKey> streamKeys;
        @Nullable
        private Object tag;
        private boolean useSessionKeys;

        public Factory(DataSource.Factory dataSourceFactory) {
            this(new DefaultHlsDataSourceFactory(dataSourceFactory));
        }

        public Factory(HlsDataSourceFactory hlsDataSourceFactory2) {
            this.hlsDataSourceFactory = (HlsDataSourceFactory) Assertions.checkNotNull(hlsDataSourceFactory2);
            this.playlistParserFactory = new DefaultHlsPlaylistParserFactory();
            this.playlistTrackerFactory = DefaultHlsPlaylistTracker.FACTORY;
            this.extractorFactory = HlsExtractorFactory.DEFAULT;
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
        }

        public Factory setTag(Object tag2) {
            Assertions.checkState(!this.isCreateCalled);
            this.tag = tag2;
            return this;
        }

        public Factory setExtractorFactory(HlsExtractorFactory extractorFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.extractorFactory = (HlsExtractorFactory) Assertions.checkNotNull(extractorFactory2);
            return this;
        }

        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy2) {
            Assertions.checkState(!this.isCreateCalled);
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
            return this;
        }

        @Deprecated
        public Factory setMinLoadableRetryCount(int minLoadableRetryCount) {
            Assertions.checkState(!this.isCreateCalled);
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy(minLoadableRetryCount);
            return this;
        }

        public Factory setPlaylistParserFactory(HlsPlaylistParserFactory playlistParserFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.playlistParserFactory = (HlsPlaylistParserFactory) Assertions.checkNotNull(playlistParserFactory2);
            return this;
        }

        public Factory setStreamKeys(List<StreamKey> streamKeys2) {
            Assertions.checkState(!this.isCreateCalled);
            this.streamKeys = streamKeys2;
            return this;
        }

        public Factory setPlaylistTrackerFactory(HlsPlaylistTracker.Factory playlistTrackerFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.playlistTrackerFactory = (HlsPlaylistTracker.Factory) Assertions.checkNotNull(playlistTrackerFactory2);
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.compositeSequenceableLoaderFactory = (CompositeSequenceableLoaderFactory) Assertions.checkNotNull(compositeSequenceableLoaderFactory2);
            return this;
        }

        public Factory setAllowChunklessPreparation(boolean allowChunklessPreparation2) {
            Assertions.checkState(!this.isCreateCalled);
            this.allowChunklessPreparation = allowChunklessPreparation2;
            return this;
        }

        public Factory setUseSessionKeys(boolean useSessionKeys2) {
            this.useSessionKeys = useSessionKeys2;
            return this;
        }

        public HlsMediaSource createMediaSource(Uri playlistUri) {
            this.isCreateCalled = true;
            List<StreamKey> list = this.streamKeys;
            if (list != null) {
                this.playlistParserFactory = new FilteringHlsPlaylistParserFactory(this.playlistParserFactory, list);
            }
            HlsDataSourceFactory hlsDataSourceFactory2 = this.hlsDataSourceFactory;
            HlsExtractorFactory hlsExtractorFactory = this.extractorFactory;
            CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2 = this.compositeSequenceableLoaderFactory;
            LoadErrorHandlingPolicy loadErrorHandlingPolicy2 = this.loadErrorHandlingPolicy;
            return new HlsMediaSource(playlistUri, hlsDataSourceFactory2, hlsExtractorFactory, compositeSequenceableLoaderFactory2, loadErrorHandlingPolicy2, this.playlistTrackerFactory.createTracker(hlsDataSourceFactory2, loadErrorHandlingPolicy2, this.playlistParserFactory), this.allowChunklessPreparation, this.useSessionKeys, this.tag);
        }

        @Deprecated
        public HlsMediaSource createMediaSource(Uri playlistUri, @Nullable Handler eventHandler, @Nullable MediaSourceEventListener eventListener) {
            HlsMediaSource mediaSource = createMediaSource(playlistUri);
            if (!(eventHandler == null || eventListener == null)) {
                mediaSource.addEventListener(eventHandler, eventListener);
            }
            return mediaSource;
        }

        public int[] getSupportedTypes() {
            return new int[]{2};
        }
    }
}
