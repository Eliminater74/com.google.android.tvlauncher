package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

public final class SingleSampleMediaSource extends BaseMediaSource {
    private final DataSource.Factory dataSourceFactory;
    private final DataSpec dataSpec;
    private final long durationUs;
    private final Format format;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    @Nullable
    private final Object tag;
    private final Timeline timeline;
    @Nullable
    private TransferListener transferListener;
    private final boolean treatLoadErrorsAsEndOfStream;

    @Deprecated
    public interface EventListener {
        void onLoadError(int i, IOException iOException);
    }

    public static final class Factory {
        private final DataSource.Factory dataSourceFactory;
        private boolean isCreateCalled;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
        @Nullable
        private Object tag;
        private boolean treatLoadErrorsAsEndOfStream;

        public Factory(DataSource.Factory dataSourceFactory2) {
            this.dataSourceFactory = (DataSource.Factory) Assertions.checkNotNull(dataSourceFactory2);
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

        public Factory setTreatLoadErrorsAsEndOfStream(boolean treatLoadErrorsAsEndOfStream2) {
            Assertions.checkState(!this.isCreateCalled);
            this.treatLoadErrorsAsEndOfStream = treatLoadErrorsAsEndOfStream2;
            return this;
        }

        public SingleSampleMediaSource createMediaSource(Uri uri, Format format, long durationUs) {
            this.isCreateCalled = true;
            return new SingleSampleMediaSource(uri, this.dataSourceFactory, format, durationUs, this.loadErrorHandlingPolicy, this.treatLoadErrorsAsEndOfStream, this.tag);
        }

        @Deprecated
        public SingleSampleMediaSource createMediaSource(Uri uri, Format format, long durationUs, @Nullable Handler eventHandler, @Nullable MediaSourceEventListener eventListener) {
            SingleSampleMediaSource mediaSource = createMediaSource(uri, format, durationUs);
            if (!(eventHandler == null || eventListener == null)) {
                mediaSource.addEventListener(eventHandler, eventListener);
            }
            return mediaSource;
        }
    }

    @Deprecated
    public SingleSampleMediaSource(Uri uri, DataSource.Factory dataSourceFactory2, Format format2, long durationUs2) {
        this(uri, dataSourceFactory2, format2, durationUs2, 3);
    }

    @Deprecated
    public SingleSampleMediaSource(Uri uri, DataSource.Factory dataSourceFactory2, Format format2, long durationUs2, int minLoadableRetryCount) {
        this(uri, dataSourceFactory2, format2, durationUs2, new DefaultLoadErrorHandlingPolicy(minLoadableRetryCount), false, null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public SingleSampleMediaSource(Uri uri, DataSource.Factory dataSourceFactory2, Format format2, long durationUs2, int minLoadableRetryCount, Handler eventHandler, EventListener eventListener, int eventSourceId, boolean treatLoadErrorsAsEndOfStream2) {
        this(uri, dataSourceFactory2, format2, durationUs2, new DefaultLoadErrorHandlingPolicy(minLoadableRetryCount), treatLoadErrorsAsEndOfStream2, null);
        Handler handler = eventHandler;
        EventListener eventListener2 = eventListener;
        if (handler != null && eventListener2 != null) {
            addEventListener(handler, new EventListenerWrapper(eventListener2, eventSourceId));
        }
    }

    private SingleSampleMediaSource(Uri uri, DataSource.Factory dataSourceFactory2, Format format2, long durationUs2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, boolean treatLoadErrorsAsEndOfStream2, @Nullable Object tag2) {
        this.dataSourceFactory = dataSourceFactory2;
        this.format = format2;
        this.durationUs = durationUs2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.treatLoadErrorsAsEndOfStream = treatLoadErrorsAsEndOfStream2;
        this.tag = tag2;
        this.dataSpec = new DataSpec(uri, 1);
        this.timeline = new SinglePeriodTimeline(durationUs2, true, false, tag2);
    }

    @Nullable
    public Object getTag() {
        return this.tag;
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener) {
        this.transferListener = mediaTransferListener;
        refreshSourceInfo(this.timeline, null);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        return new SingleSampleMediaPeriod(this.dataSpec, this.dataSourceFactory, this.transferListener, this.format, this.durationUs, this.loadErrorHandlingPolicy, createEventDispatcher(id), this.treatLoadErrorsAsEndOfStream);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((SingleSampleMediaPeriod) mediaPeriod).release();
    }

    public void releaseSourceInternal() {
    }

    @Deprecated
    private static final class EventListenerWrapper extends DefaultMediaSourceEventListener {
        private final EventListener eventListener;
        private final int eventSourceId;

        public EventListenerWrapper(EventListener eventListener2, int eventSourceId2) {
            this.eventListener = (EventListener) Assertions.checkNotNull(eventListener2);
            this.eventSourceId = eventSourceId2;
        }

        public void onLoadError(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
            this.eventListener.onLoadError(this.eventSourceId, error);
        }
    }
}
