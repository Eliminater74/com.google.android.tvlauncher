package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BaseMediaSource implements MediaSource {
    private final MediaSourceEventListener.EventDispatcher eventDispatcher = new MediaSourceEventListener.EventDispatcher();
    @Nullable
    private Looper looper;
    @Nullable
    private Object manifest;
    private final ArrayList<MediaSource.SourceInfoRefreshListener> sourceInfoListeners = new ArrayList<>(1);
    @Nullable
    private Timeline timeline;

    public Object getTag() {
        return MediaSource$$CC.getTag$$dflt$$(this);
    }

    public void prepareSource(ExoPlayer exoPlayer, boolean z, MediaSource.SourceInfoRefreshListener sourceInfoRefreshListener, TransferListener transferListener) {
        MediaSource$$CC.prepareSource$$dflt$$(this, exoPlayer, z, sourceInfoRefreshListener, transferListener);
    }

    /* access modifiers changed from: protected */
    public abstract void prepareSourceInternal(@Nullable TransferListener transferListener);

    /* access modifiers changed from: protected */
    public abstract void releaseSourceInternal();

    /* access modifiers changed from: protected */
    public final void refreshSourceInfo(Timeline timeline2, @Nullable Object manifest2) {
        this.timeline = timeline2;
        this.manifest = manifest2;
        Iterator<MediaSource.SourceInfoRefreshListener> it = this.sourceInfoListeners.iterator();
        while (it.hasNext()) {
            it.next().onSourceInfoRefreshed(this, timeline2, manifest2);
        }
    }

    /* access modifiers changed from: protected */
    public final MediaSourceEventListener.EventDispatcher createEventDispatcher(@Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        return this.eventDispatcher.withParameters(0, mediaPeriodId, 0);
    }

    /* access modifiers changed from: protected */
    public final MediaSourceEventListener.EventDispatcher createEventDispatcher(MediaSource.MediaPeriodId mediaPeriodId, long mediaTimeOffsetMs) {
        Assertions.checkArgument(mediaPeriodId != null);
        return this.eventDispatcher.withParameters(0, mediaPeriodId, mediaTimeOffsetMs);
    }

    /* access modifiers changed from: protected */
    public final MediaSourceEventListener.EventDispatcher createEventDispatcher(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, long mediaTimeOffsetMs) {
        return this.eventDispatcher.withParameters(windowIndex, mediaPeriodId, mediaTimeOffsetMs);
    }

    public final void addEventListener(Handler handler, MediaSourceEventListener eventListener) {
        this.eventDispatcher.addEventListener(handler, eventListener);
    }

    public final void removeEventListener(MediaSourceEventListener eventListener) {
        this.eventDispatcher.removeEventListener(eventListener);
    }

    public final void prepareSource(MediaSource.SourceInfoRefreshListener listener, @Nullable TransferListener mediaTransferListener) {
        Looper looper2 = Looper.myLooper();
        Looper looper3 = this.looper;
        Assertions.checkArgument(looper3 == null || looper3 == looper2);
        this.sourceInfoListeners.add(listener);
        if (this.looper == null) {
            this.looper = looper2;
            prepareSourceInternal(mediaTransferListener);
            return;
        }
        Timeline timeline2 = this.timeline;
        if (timeline2 != null) {
            listener.onSourceInfoRefreshed(this, timeline2, this.manifest);
        }
    }

    public final void releaseSource(MediaSource.SourceInfoRefreshListener listener) {
        this.sourceInfoListeners.remove(listener);
        if (this.sourceInfoListeners.isEmpty()) {
            this.looper = null;
            this.timeline = null;
            this.manifest = null;
            releaseSourceInternal();
        }
    }
}
