package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public interface MediaSourceEventListener {
    void onDownstreamFormatChanged(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData);

    void onLoadCanceled(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onLoadCompleted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onLoadError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z);

    void onLoadStarted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onMediaPeriodCreated(int i, MediaSource.MediaPeriodId mediaPeriodId);

    void onMediaPeriodReleased(int i, MediaSource.MediaPeriodId mediaPeriodId);

    void onReadingStarted(int i, MediaSource.MediaPeriodId mediaPeriodId);

    void onUpstreamDiscarded(int i, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData);

    public static final class LoadEventInfo {
        public final long bytesLoaded;
        public final DataSpec dataSpec;
        public final long elapsedRealtimeMs;
        public final long loadDurationMs;
        public final Map<String, List<String>> responseHeaders;
        public final Uri uri;

        public LoadEventInfo(DataSpec dataSpec2, Uri uri2, Map<String, List<String>> responseHeaders2, long elapsedRealtimeMs2, long loadDurationMs2, long bytesLoaded2) {
            this.dataSpec = dataSpec2;
            this.uri = uri2;
            this.responseHeaders = responseHeaders2;
            this.elapsedRealtimeMs = elapsedRealtimeMs2;
            this.loadDurationMs = loadDurationMs2;
            this.bytesLoaded = bytesLoaded2;
        }
    }

    public static final class MediaLoadData {
        public final int dataType;
        public final long mediaEndTimeMs;
        public final long mediaStartTimeMs;
        @Nullable
        public final Format trackFormat;
        @Nullable
        public final Object trackSelectionData;
        public final int trackSelectionReason;
        public final int trackType;

        public MediaLoadData(int dataType2, int trackType2, @Nullable Format trackFormat2, int trackSelectionReason2, @Nullable Object trackSelectionData2, long mediaStartTimeMs2, long mediaEndTimeMs2) {
            this.dataType = dataType2;
            this.trackType = trackType2;
            this.trackFormat = trackFormat2;
            this.trackSelectionReason = trackSelectionReason2;
            this.trackSelectionData = trackSelectionData2;
            this.mediaStartTimeMs = mediaStartTimeMs2;
            this.mediaEndTimeMs = mediaEndTimeMs2;
        }
    }

    public static final class EventDispatcher {
        @Nullable
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final int windowIndex;
        private final CopyOnWriteArrayList<ListenerAndHandler> listenerAndHandlers;
        private final long mediaTimeOffsetMs;

        public EventDispatcher() {
            this(new CopyOnWriteArrayList(), 0, null, 0);
        }

        private EventDispatcher(CopyOnWriteArrayList<ListenerAndHandler> listenerAndHandlers2, int windowIndex2, @Nullable MediaSource.MediaPeriodId mediaPeriodId2, long mediaTimeOffsetMs2) {
            this.listenerAndHandlers = listenerAndHandlers2;
            this.windowIndex = windowIndex2;
            this.mediaPeriodId = mediaPeriodId2;
            this.mediaTimeOffsetMs = mediaTimeOffsetMs2;
        }

        @CheckResult
        public EventDispatcher withParameters(int windowIndex2, @Nullable MediaSource.MediaPeriodId mediaPeriodId2, long mediaTimeOffsetMs2) {
            return new EventDispatcher(this.listenerAndHandlers, windowIndex2, mediaPeriodId2, mediaTimeOffsetMs2);
        }

        public void addEventListener(Handler handler, MediaSourceEventListener eventListener) {
            Assertions.checkArgument((handler == null || eventListener == null) ? false : true);
            this.listenerAndHandlers.add(new ListenerAndHandler(handler, eventListener));
        }

        public void removeEventListener(MediaSourceEventListener eventListener) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                if (listenerAndHandler.listener == eventListener) {
                    this.listenerAndHandlers.remove(listenerAndHandler);
                }
            }
        }

        public void mediaPeriodCreated() {
            MediaSource.MediaPeriodId mediaPeriodId2 = (MediaSource.MediaPeriodId) Assertions.checkNotNull(this.mediaPeriodId);
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                postOrRun(listenerAndHandler.handler, new MediaSourceEventListener$EventDispatcher$$Lambda$0(this, listenerAndHandler.listener, mediaPeriodId2));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$mediaPeriodCreated$0$MediaSourceEventListener$EventDispatcher */
        public final /* synthetic */ void mo14397xa9fff584(MediaSourceEventListener listener, MediaSource.MediaPeriodId mediaPeriodId2) {
            listener.onMediaPeriodCreated(this.windowIndex, mediaPeriodId2);
        }

        public void mediaPeriodReleased() {
            MediaSource.MediaPeriodId mediaPeriodId2 = (MediaSource.MediaPeriodId) Assertions.checkNotNull(this.mediaPeriodId);
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                postOrRun(listenerAndHandler.handler, new MediaSourceEventListener$EventDispatcher$$Lambda$1(this, listenerAndHandler.listener, mediaPeriodId2));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$mediaPeriodReleased$1$MediaSourceEventListener$EventDispatcher */
        public final /* synthetic */ void mo14398xb596e71e(MediaSourceEventListener listener, MediaSource.MediaPeriodId mediaPeriodId2) {
            listener.onMediaPeriodReleased(this.windowIndex, mediaPeriodId2);
        }

        public void loadStarted(DataSpec dataSpec, int dataType, long elapsedRealtimeMs) {
            loadStarted(dataSpec, dataType, -1, null, 0, null, C0841C.TIME_UNSET, C0841C.TIME_UNSET, elapsedRealtimeMs);
        }

        public void loadStarted(DataSpec dataSpec, int dataType, int trackType, @Nullable Format trackFormat, int trackSelectionReason, @Nullable Object trackSelectionData, long mediaStartTimeUs, long mediaEndTimeUs, long elapsedRealtimeMs) {
            loadStarted(new LoadEventInfo(dataSpec, dataSpec.uri, Collections.emptyMap(), elapsedRealtimeMs, 0, 0), new MediaLoadData(dataType, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)));
        }

        public void loadStarted(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                postOrRun(listenerAndHandler.handler, new MediaSourceEventListener$EventDispatcher$$Lambda$2(this, listenerAndHandler.listener, loadEventInfo, mediaLoadData));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$loadStarted$2$MediaSourceEventListener$EventDispatcher(MediaSourceEventListener listener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            listener.onLoadStarted(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public void loadCompleted(DataSpec dataSpec, Uri uri, Map<String, List<String>> responseHeaders, int dataType, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
            loadCompleted(dataSpec, uri, responseHeaders, dataType, -1, null, 0, null, C0841C.TIME_UNSET, C0841C.TIME_UNSET, elapsedRealtimeMs, loadDurationMs, bytesLoaded);
        }

        public void loadCompleted(DataSpec dataSpec, Uri uri, Map<String, List<String>> responseHeaders, int dataType, int trackType, @Nullable Format trackFormat, int trackSelectionReason, @Nullable Object trackSelectionData, long mediaStartTimeUs, long mediaEndTimeUs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
            loadCompleted(new LoadEventInfo(dataSpec, uri, responseHeaders, elapsedRealtimeMs, loadDurationMs, bytesLoaded), new MediaLoadData(dataType, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)));
        }

        public void loadCompleted(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                postOrRun(listenerAndHandler.handler, new MediaSourceEventListener$EventDispatcher$$Lambda$3(this, listenerAndHandler.listener, loadEventInfo, mediaLoadData));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$loadCompleted$3$MediaSourceEventListener$EventDispatcher(MediaSourceEventListener listener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            listener.onLoadCompleted(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public void loadCanceled(DataSpec dataSpec, Uri uri, Map<String, List<String>> responseHeaders, int dataType, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
            loadCanceled(dataSpec, uri, responseHeaders, dataType, -1, null, 0, null, C0841C.TIME_UNSET, C0841C.TIME_UNSET, elapsedRealtimeMs, loadDurationMs, bytesLoaded);
        }

        public void loadCanceled(DataSpec dataSpec, Uri uri, Map<String, List<String>> responseHeaders, int dataType, int trackType, @Nullable Format trackFormat, int trackSelectionReason, @Nullable Object trackSelectionData, long mediaStartTimeUs, long mediaEndTimeUs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
            loadCanceled(new LoadEventInfo(dataSpec, uri, responseHeaders, elapsedRealtimeMs, loadDurationMs, bytesLoaded), new MediaLoadData(dataType, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)));
        }

        public void loadCanceled(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                postOrRun(listenerAndHandler.handler, new MediaSourceEventListener$EventDispatcher$$Lambda$4(this, listenerAndHandler.listener, loadEventInfo, mediaLoadData));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$loadCanceled$4$MediaSourceEventListener$EventDispatcher(MediaSourceEventListener listener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            listener.onLoadCanceled(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public void loadError(DataSpec dataSpec, Uri uri, Map<String, List<String>> responseHeaders, int dataType, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, IOException error, boolean wasCanceled) {
            loadError(dataSpec, uri, responseHeaders, dataType, -1, null, 0, null, C0841C.TIME_UNSET, C0841C.TIME_UNSET, elapsedRealtimeMs, loadDurationMs, bytesLoaded, error, wasCanceled);
        }

        public void loadError(DataSpec dataSpec, Uri uri, Map<String, List<String>> responseHeaders, int dataType, int trackType, @Nullable Format trackFormat, int trackSelectionReason, @Nullable Object trackSelectionData, long mediaStartTimeUs, long mediaEndTimeUs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, IOException error, boolean wasCanceled) {
            loadError(new LoadEventInfo(dataSpec, uri, responseHeaders, elapsedRealtimeMs, loadDurationMs, bytesLoaded), new MediaLoadData(dataType, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)), error, wasCanceled);
        }

        public void loadError(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                postOrRun(listenerAndHandler.handler, new MediaSourceEventListener$EventDispatcher$$Lambda$5(this, listenerAndHandler.listener, loadEventInfo, mediaLoadData, error, wasCanceled));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$loadError$5$MediaSourceEventListener$EventDispatcher(MediaSourceEventListener listener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
            listener.onLoadError(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData, error, wasCanceled);
        }

        public void readingStarted() {
            MediaSource.MediaPeriodId mediaPeriodId2 = (MediaSource.MediaPeriodId) Assertions.checkNotNull(this.mediaPeriodId);
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                postOrRun(listenerAndHandler.handler, new MediaSourceEventListener$EventDispatcher$$Lambda$6(this, listenerAndHandler.listener, mediaPeriodId2));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$readingStarted$6$MediaSourceEventListener$EventDispatcher(MediaSourceEventListener listener, MediaSource.MediaPeriodId mediaPeriodId2) {
            listener.onReadingStarted(this.windowIndex, mediaPeriodId2);
        }

        public void upstreamDiscarded(int trackType, long mediaStartTimeUs, long mediaEndTimeUs) {
            upstreamDiscarded(new MediaLoadData(1, trackType, null, 3, null, adjustMediaTime(mediaStartTimeUs), adjustMediaTime(mediaEndTimeUs)));
        }

        public void upstreamDiscarded(MediaLoadData mediaLoadData) {
            MediaSource.MediaPeriodId mediaPeriodId2 = (MediaSource.MediaPeriodId) Assertions.checkNotNull(this.mediaPeriodId);
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                postOrRun(listenerAndHandler.handler, new MediaSourceEventListener$EventDispatcher$$Lambda$7(this, listenerAndHandler.listener, mediaPeriodId2, mediaLoadData));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$upstreamDiscarded$7$MediaSourceEventListener$EventDispatcher */
        public final /* synthetic */ void mo14400xcce9218(MediaSourceEventListener listener, MediaSource.MediaPeriodId mediaPeriodId2, MediaLoadData mediaLoadData) {
            listener.onUpstreamDiscarded(this.windowIndex, mediaPeriodId2, mediaLoadData);
        }

        public void downstreamFormatChanged(int trackType, @Nullable Format trackFormat, int trackSelectionReason, @Nullable Object trackSelectionData, long mediaTimeUs) {
            downstreamFormatChanged(new MediaLoadData(1, trackType, trackFormat, trackSelectionReason, trackSelectionData, adjustMediaTime(mediaTimeUs), C0841C.TIME_UNSET));
        }

        public void downstreamFormatChanged(MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler listenerAndHandler = it.next();
                postOrRun(listenerAndHandler.handler, new MediaSourceEventListener$EventDispatcher$$Lambda$8(this, listenerAndHandler.listener, mediaLoadData));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$downstreamFormatChanged$8$MediaSourceEventListener$EventDispatcher */
        public final /* synthetic */ void mo14392x9a021abe(MediaSourceEventListener listener, MediaLoadData mediaLoadData) {
            listener.onDownstreamFormatChanged(this.windowIndex, this.mediaPeriodId, mediaLoadData);
        }

        private long adjustMediaTime(long mediaTimeUs) {
            long mediaTimeMs = C0841C.usToMs(mediaTimeUs);
            return mediaTimeMs == C0841C.TIME_UNSET ? C0841C.TIME_UNSET : this.mediaTimeOffsetMs + mediaTimeMs;
        }

        private void postOrRun(Handler handler, Runnable runnable) {
            if (handler.getLooper() == Looper.myLooper()) {
                runnable.run();
            } else {
                handler.post(runnable);
            }
        }

        private static final class ListenerAndHandler {
            public final Handler handler;
            public final MediaSourceEventListener listener;

            public ListenerAndHandler(Handler handler2, MediaSourceEventListener listener2) {
                this.handler = handler2;
                this.listener = listener2;
            }
        }
    }
}
