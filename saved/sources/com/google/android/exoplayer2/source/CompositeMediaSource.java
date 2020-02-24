package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.HashMap;

public abstract class CompositeMediaSource<T> extends BaseMediaSource {
    private final HashMap<T, MediaSourceAndListener> childSources = new HashMap<>();
    @Nullable
    private Handler eventHandler;
    @Nullable
    private TransferListener mediaTransferListener;

    /* access modifiers changed from: protected */
    /* renamed from: onChildSourceInfoRefreshed */
    public abstract void lambda$prepareChildSource$0$CompositeMediaSource(T t, MediaSource mediaSource, Timeline timeline, @Nullable Object obj);

    protected CompositeMediaSource() {
    }

    @CallSuper
    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener2) {
        this.mediaTransferListener = mediaTransferListener2;
        this.eventHandler = new Handler();
    }

    @CallSuper
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        for (MediaSourceAndListener childSource : this.childSources.values()) {
            childSource.mediaSource.maybeThrowSourceInfoRefreshError();
        }
    }

    @CallSuper
    public void releaseSourceInternal() {
        for (MediaSourceAndListener childSource : this.childSources.values()) {
            childSource.mediaSource.releaseSource(childSource.listener);
            childSource.mediaSource.removeEventListener(childSource.eventListener);
        }
        this.childSources.clear();
    }

    /* access modifiers changed from: protected */
    public final void prepareChildSource(T id, MediaSource mediaSource) {
        Assertions.checkArgument(!this.childSources.containsKey(id));
        MediaSource.SourceInfoRefreshListener sourceListener = new CompositeMediaSource$$Lambda$0(this, id);
        MediaSourceEventListener eventListener = new ForwardingEventListener(id);
        this.childSources.put(id, new MediaSourceAndListener(mediaSource, sourceListener, eventListener));
        mediaSource.addEventListener((Handler) Assertions.checkNotNull(this.eventHandler), eventListener);
        mediaSource.prepareSource(sourceListener, this.mediaTransferListener);
    }

    /* access modifiers changed from: protected */
    public final void releaseChildSource(T id) {
        MediaSourceAndListener removedChild = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.remove(id));
        removedChild.mediaSource.releaseSource(removedChild.listener);
        removedChild.mediaSource.removeEventListener(removedChild.eventListener);
    }

    /* access modifiers changed from: protected */
    public int getWindowIndexForChildWindowIndex(T t, int windowIndex) {
        return windowIndex;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(T t, MediaSource.MediaPeriodId mediaPeriodId) {
        return mediaPeriodId;
    }

    /* access modifiers changed from: protected */
    public long getMediaTimeForChildMediaTime(@Nullable T t, long mediaTimeMs) {
        return mediaTimeMs;
    }

    private static final class MediaSourceAndListener {
        public final MediaSourceEventListener eventListener;
        public final MediaSource.SourceInfoRefreshListener listener;
        public final MediaSource mediaSource;

        public MediaSourceAndListener(MediaSource mediaSource2, MediaSource.SourceInfoRefreshListener listener2, MediaSourceEventListener eventListener2) {
            this.mediaSource = mediaSource2;
            this.listener = listener2;
            this.eventListener = eventListener2;
        }
    }

    private final class ForwardingEventListener implements MediaSourceEventListener {
        private MediaSourceEventListener.EventDispatcher eventDispatcher;

        /* renamed from: id */
        private final T f90id;

        public ForwardingEventListener(T id) {
            this.eventDispatcher = CompositeMediaSource.this.createEventDispatcher(null);
            this.f90id = id;
        }

        public void onMediaPeriodCreated(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.eventDispatcher.mediaPeriodCreated();
            }
        }

        public void onMediaPeriodReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.eventDispatcher.mediaPeriodReleased();
            }
        }

        public void onLoadStarted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventData, MediaSourceEventListener.MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.eventDispatcher.loadStarted(loadEventData, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        public void onLoadCompleted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventData, MediaSourceEventListener.MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.eventDispatcher.loadCompleted(loadEventData, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        public void onLoadCanceled(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventData, MediaSourceEventListener.MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.eventDispatcher.loadCanceled(loadEventData, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        public void onLoadError(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventData, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.eventDispatcher.loadError(loadEventData, maybeUpdateMediaLoadData(mediaLoadData), error, wasCanceled);
            }
        }

        public void onReadingStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.eventDispatcher.readingStarted();
            }
        }

        public void onUpstreamDiscarded(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.eventDispatcher.upstreamDiscarded(maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        public void onDownstreamFormatChanged(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(windowIndex, mediaPeriodId)) {
                this.eventDispatcher.downstreamFormatChanged(maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        private boolean maybeUpdateEventDispatcher(int childWindowIndex, @Nullable MediaSource.MediaPeriodId childMediaPeriodId) {
            MediaSource.MediaPeriodId mediaPeriodId = null;
            if (childMediaPeriodId != null && (mediaPeriodId = CompositeMediaSource.this.getMediaPeriodIdForChildMediaPeriodId(this.f90id, childMediaPeriodId)) == null) {
                return false;
            }
            int windowIndex = CompositeMediaSource.this.getWindowIndexForChildWindowIndex(this.f90id, childWindowIndex);
            if (this.eventDispatcher.windowIndex == windowIndex && Util.areEqual(this.eventDispatcher.mediaPeriodId, mediaPeriodId)) {
                return true;
            }
            this.eventDispatcher = CompositeMediaSource.this.createEventDispatcher(windowIndex, mediaPeriodId, 0);
            return true;
        }

        private MediaSourceEventListener.MediaLoadData maybeUpdateMediaLoadData(MediaSourceEventListener.MediaLoadData mediaLoadData) {
            MediaSourceEventListener.MediaLoadData mediaLoadData2 = mediaLoadData;
            long mediaStartTimeMs = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.f90id, mediaLoadData2.mediaStartTimeMs);
            long mediaEndTimeMs = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.f90id, mediaLoadData2.mediaEndTimeMs);
            if (mediaStartTimeMs == mediaLoadData2.mediaStartTimeMs && mediaEndTimeMs == mediaLoadData2.mediaEndTimeMs) {
                return mediaLoadData2;
            }
            return new MediaSourceEventListener.MediaLoadData(mediaLoadData2.dataType, mediaLoadData2.trackType, mediaLoadData2.trackFormat, mediaLoadData2.trackSelectionReason, mediaLoadData2.trackSelectionData, mediaStartTimeMs, mediaEndTimeMs);
        }
    }
}
