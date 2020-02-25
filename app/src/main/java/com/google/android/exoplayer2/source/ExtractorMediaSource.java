package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;

import java.io.IOException;

@Deprecated
public final class ExtractorMediaSource extends BaseMediaSource implements MediaSource.SourceInfoRefreshListener {
    @Deprecated
    public static final int DEFAULT_LOADING_CHECK_INTERVAL_BYTES = 1048576;
    private final ProgressiveMediaSource progressiveMediaSource;

    @Deprecated
    public ExtractorMediaSource(Uri uri, DataSource.Factory dataSourceFactory, ExtractorsFactory extractorsFactory, Handler eventHandler, EventListener eventListener) {
        this(uri, dataSourceFactory, extractorsFactory, eventHandler, eventListener, null);
    }

    @Deprecated
    public ExtractorMediaSource(Uri uri, DataSource.Factory dataSourceFactory, ExtractorsFactory extractorsFactory, Handler eventHandler, EventListener eventListener, String customCacheKey) {
        this(uri, dataSourceFactory, extractorsFactory, eventHandler, eventListener, customCacheKey, 1048576);
    }

    @Deprecated
    public ExtractorMediaSource(Uri uri, DataSource.Factory dataSourceFactory, ExtractorsFactory extractorsFactory, Handler eventHandler, EventListener eventListener, String customCacheKey, int continueLoadingCheckIntervalBytes) {
        this(uri, dataSourceFactory, extractorsFactory, new DefaultLoadErrorHandlingPolicy(), customCacheKey, continueLoadingCheckIntervalBytes, (Object) null);
        if (eventListener != null && eventHandler != null) {
            addEventListener(eventHandler, new EventListenerWrapper(eventListener));
        }
    }

    private ExtractorMediaSource(Uri uri, DataSource.Factory dataSourceFactory, ExtractorsFactory extractorsFactory, LoadErrorHandlingPolicy loadableLoadErrorHandlingPolicy, @Nullable String customCacheKey, int continueLoadingCheckIntervalBytes, @Nullable Object tag) {
        this.progressiveMediaSource = new ProgressiveMediaSource(uri, dataSourceFactory, extractorsFactory, loadableLoadErrorHandlingPolicy, customCacheKey, continueLoadingCheckIntervalBytes, tag);
    }

    @Nullable
    public Object getTag() {
        return this.progressiveMediaSource.getTag();
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener) {
        this.progressiveMediaSource.prepareSource(this, mediaTransferListener);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.progressiveMediaSource.maybeThrowSourceInfoRefreshError();
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        return this.progressiveMediaSource.createPeriod(id, allocator, startPositionUs);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.progressiveMediaSource.releasePeriod(mediaPeriod);
    }

    public void releaseSourceInternal() {
        this.progressiveMediaSource.releaseSource(this);
    }

    public void onSourceInfoRefreshed(MediaSource source, Timeline timeline, @Nullable Object manifest) {
        refreshSourceInfo(timeline, manifest);
    }

    @Deprecated
    public interface EventListener {
        void onLoadError(IOException iOException);
    }

    @Deprecated
    public static final class Factory implements AdsMediaSource.MediaSourceFactory {
        private final DataSource.Factory dataSourceFactory;
        private int continueLoadingCheckIntervalBytes = 1048576;
        @Nullable
        private String customCacheKey;
        @Nullable
        private ExtractorsFactory extractorsFactory;
        private boolean isCreateCalled;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
        @Nullable
        private Object tag;

        public Factory(DataSource.Factory dataSourceFactory2) {
            this.dataSourceFactory = dataSourceFactory2;
        }

        public Factory setExtractorsFactory(ExtractorsFactory extractorsFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.extractorsFactory = extractorsFactory2;
            return this;
        }

        public Factory setCustomCacheKey(String customCacheKey2) {
            Assertions.checkState(!this.isCreateCalled);
            this.customCacheKey = customCacheKey2;
            return this;
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

        public Factory setContinueLoadingCheckIntervalBytes(int continueLoadingCheckIntervalBytes2) {
            Assertions.checkState(!this.isCreateCalled);
            this.continueLoadingCheckIntervalBytes = continueLoadingCheckIntervalBytes2;
            return this;
        }

        public ExtractorMediaSource createMediaSource(Uri uri) {
            this.isCreateCalled = true;
            if (this.extractorsFactory == null) {
                this.extractorsFactory = new DefaultExtractorsFactory();
            }
            return new ExtractorMediaSource(uri, this.dataSourceFactory, this.extractorsFactory, this.loadErrorHandlingPolicy, this.customCacheKey, this.continueLoadingCheckIntervalBytes, this.tag);
        }

        @Deprecated
        public ExtractorMediaSource createMediaSource(Uri uri, @Nullable Handler eventHandler, @Nullable MediaSourceEventListener eventListener) {
            ExtractorMediaSource mediaSource = createMediaSource(uri);
            if (!(eventHandler == null || eventListener == null)) {
                mediaSource.addEventListener(eventHandler, eventListener);
            }
            return mediaSource;
        }

        public int[] getSupportedTypes() {
            return new int[]{3};
        }
    }

    @Deprecated
    private static final class EventListenerWrapper extends DefaultMediaSourceEventListener {
        private final EventListener eventListener;

        public EventListenerWrapper(EventListener eventListener2) {
            this.eventListener = (EventListener) Assertions.checkNotNull(eventListener2);
        }

        public void onLoadError(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
            this.eventListener.onLoadError(error);
        }
    }
}
