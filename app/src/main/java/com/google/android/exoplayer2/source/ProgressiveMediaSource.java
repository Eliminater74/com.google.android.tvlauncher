package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
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

public final class ProgressiveMediaSource extends BaseMediaSource implements ProgressiveMediaPeriod.Listener {
    public static final int DEFAULT_LOADING_CHECK_INTERVAL_BYTES = 1048576;
    private final int continueLoadingCheckIntervalBytes;
    @Nullable
    private final String customCacheKey;
    private final DataSource.Factory dataSourceFactory;
    private final ExtractorsFactory extractorsFactory;
    private final LoadErrorHandlingPolicy loadableLoadErrorHandlingPolicy;
    @Nullable
    private final Object tag;
    private final Uri uri;
    private long timelineDurationUs = C0841C.TIME_UNSET;
    private boolean timelineIsSeekable;
    @Nullable
    private TransferListener transferListener;

    ProgressiveMediaSource(Uri uri2, DataSource.Factory dataSourceFactory2, ExtractorsFactory extractorsFactory2, LoadErrorHandlingPolicy loadableLoadErrorHandlingPolicy2, @Nullable String customCacheKey2, int continueLoadingCheckIntervalBytes2, @Nullable Object tag2) {
        this.uri = uri2;
        this.dataSourceFactory = dataSourceFactory2;
        this.extractorsFactory = extractorsFactory2;
        this.loadableLoadErrorHandlingPolicy = loadableLoadErrorHandlingPolicy2;
        this.customCacheKey = customCacheKey2;
        this.continueLoadingCheckIntervalBytes = continueLoadingCheckIntervalBytes2;
        this.tag = tag2;
    }

    @Nullable
    public Object getTag() {
        return this.tag;
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener) {
        this.transferListener = mediaTransferListener;
        notifySourceInfoRefreshed(this.timelineDurationUs, this.timelineIsSeekable);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        DataSource dataSource = this.dataSourceFactory.createDataSource();
        TransferListener transferListener2 = this.transferListener;
        if (transferListener2 != null) {
            dataSource.addTransferListener(transferListener2);
        }
        return new ProgressiveMediaPeriod(this.uri, dataSource, this.extractorsFactory.createExtractors(), this.loadableLoadErrorHandlingPolicy, createEventDispatcher(id), this, allocator, this.customCacheKey, this.continueLoadingCheckIntervalBytes);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((ProgressiveMediaPeriod) mediaPeriod).release();
    }

    public void releaseSourceInternal() {
    }

    public void onSourceInfoRefreshed(long durationUs, boolean isSeekable) {
        long durationUs2 = durationUs == C0841C.TIME_UNSET ? this.timelineDurationUs : durationUs;
        if (this.timelineDurationUs != durationUs2 || this.timelineIsSeekable != isSeekable) {
            notifySourceInfoRefreshed(durationUs2, isSeekable);
        }
    }

    private void notifySourceInfoRefreshed(long durationUs, boolean isSeekable) {
        this.timelineDurationUs = durationUs;
        this.timelineIsSeekable = isSeekable;
        refreshSourceInfo(new SinglePeriodTimeline(this.timelineDurationUs, this.timelineIsSeekable, false, this.tag), null);
    }

    public static final class Factory implements AdsMediaSource.MediaSourceFactory {
        private final DataSource.Factory dataSourceFactory;
        private int continueLoadingCheckIntervalBytes;
        @Nullable
        private String customCacheKey;
        private ExtractorsFactory extractorsFactory;
        private boolean isCreateCalled;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        @Nullable
        private Object tag;

        public Factory(DataSource.Factory dataSourceFactory2) {
            this(dataSourceFactory2, new DefaultExtractorsFactory());
        }

        public Factory(DataSource.Factory dataSourceFactory2, ExtractorsFactory extractorsFactory2) {
            this.dataSourceFactory = dataSourceFactory2;
            this.extractorsFactory = extractorsFactory2;
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.continueLoadingCheckIntervalBytes = 1048576;
        }

        @Deprecated
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

        public ProgressiveMediaSource createMediaSource(Uri uri) {
            this.isCreateCalled = true;
            return new ProgressiveMediaSource(uri, this.dataSourceFactory, this.extractorsFactory, this.loadErrorHandlingPolicy, this.customCacheKey, this.continueLoadingCheckIntervalBytes, this.tag);
        }

        public int[] getSupportedTypes() {
            return new int[]{3};
        }
    }
}
