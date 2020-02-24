package com.google.android.exoplayer2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.MetadataRetriever;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.surfacecapturer.SingleFrameMediaCodecVideoRenderer;
import com.google.android.exoplayer2.video.surfacecapturer.VideoRendererOutputCapturer;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.ArrayDeque;
import java.util.Queue;

final class MetadataRetrieverImpl implements MetadataRetriever, Handler.Callback {
    private static final MetadataRetriever.Options DEFAULT_OPTIONS = new MetadataRetriever.Options.Builder().build();
    private static final int MSG_PROCESS_NEXT_QUERY = 0;
    private static final int MSG_RECAPTURE_FRAME = 1;
    private static final int RECAPTURE_FRAME_DELAY_MILLIS = 15000;
    private static final int VIDEO_RENDERER_INDEX = 0;
    /* access modifiers changed from: private */
    public int currentOutputHeight;
    /* access modifiers changed from: private */
    public int currentOutputWidth;
    /* access modifiers changed from: private */
    public final ExoPlayerImpl exoPlayer;
    private final Handler handler;
    private boolean isRecapturing;
    /* access modifiers changed from: private */
    @Nullable
    public Bitmap lastCapturedBitmap;
    /* access modifiers changed from: private */
    @Nullable
    public Exception lastSurfaceCaptureException;
    @Nullable
    private MediaSource mediaSource;
    /* access modifiers changed from: private */
    @Nullable
    public MetadataRetriever.MediaSourceCallback mediaSourceCallback;
    /* access modifiers changed from: private */
    public boolean outputCapturePending;
    /* access modifiers changed from: private */
    public boolean outputSurfaceSetPending;
    private final Timeline.Period period;
    /* access modifiers changed from: private */
    public long playerSeekAckPositionMs;
    private final Queue<Query> queryQueue;
    private int queryWindowIndex;
    private final RendererCapabilities[] rendererCapabilities;
    private SeekParameters seekParameters;
    private final DefaultTrackSelector standaloneTrackSelector;
    /* access modifiers changed from: private */
    public Timeline timeline;
    /* access modifiers changed from: private */
    @Nullable
    public Object trackGroupsPeriodUid;
    private final DefaultTrackSelector trackSelector;
    private final VideoRendererOutputCapturer videoRendererOutputCapturer;
    private final Timeline.Window window;

    public MetadataRetrieverImpl(Context context, Clock clock) {
        this(context, clock, (Looper) Assertions.checkNotNull(Looper.myLooper()));
    }

    public MetadataRetrieverImpl(Context context, Clock clock, Looper eventLooper) {
        Context context2 = context;
        this.trackSelector = new DefaultTrackSelector();
        DefaultLoadControl loadControl = new DefaultLoadControl.Builder().createDefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(context2).build();
        SingleFrameMediaCodecVideoRenderer videoRenderer = new SingleFrameMediaCodecVideoRenderer(context2, MediaCodecSelector.DEFAULT);
        this.exoPlayer = new ExoPlayerImpl(new Renderer[]{videoRenderer}, this.trackSelector, loadControl, bandwidthMeter, clock, eventLooper);
        this.exoPlayer.setPlayWhenReady(false);
        this.exoPlayer.addListener(new ComponentListener());
        this.rendererCapabilities = new RendererCapabilities[]{videoRenderer.getCapabilities()};
        this.standaloneTrackSelector = new DefaultTrackSelector();
        this.standaloneTrackSelector.init(MetadataRetrieverImpl$$Lambda$0.$instance, bandwidthMeter);
        this.handler = Util.createHandler(eventLooper, this);
        this.videoRendererOutputCapturer = new VideoRendererOutputCapturer(new VideoRendererOutputCapturerCallback(), this.handler, videoRenderer, this.exoPlayer);
        this.seekParameters = SeekParameters.DEFAULT;
        this.timeline = Timeline.EMPTY;
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        this.queryQueue = new ArrayDeque();
    }

    static final /* synthetic */ void lambda$new$0$MetadataRetrieverImpl() {
    }

    public void prepare(MediaSource mediaSource2, MetadataRetriever.MediaSourceCallback mediaSourceCallback2) {
        Assertions.checkState(this.mediaSource == null);
        this.mediaSource = mediaSource2;
        this.mediaSourceCallback = mediaSourceCallback2;
        disableVideoRenderer();
        this.exoPlayer.prepare(mediaSource2);
        this.trackGroupsPeriodUid = null;
    }

    public void stop() {
        resetRetriever();
        this.exoPlayer.stop(true);
    }

    public void release() {
        resetRetriever();
        this.exoPlayer.release();
        this.videoRendererOutputCapturer.release();
    }

    public Object getMetadata(MetadataRetriever.MetadataCallback callback) {
        return getMetadata(0, callback);
    }

    public Object getMetadata(long positionMs, MetadataRetriever.MetadataCallback callback) {
        Assertions.checkState(this.mediaSource != null);
        MetadataQuery metadataQuery = new MetadataQuery(this.queryWindowIndex, positionMs, callback, this.timeline, this.seekParameters);
        this.queryQueue.add(metadataQuery);
        scheduleProcessNextQuery();
        return metadataQuery;
    }

    public Object getFrameAtTime(long positionMs, MetadataRetriever.FrameCallback callback) {
        return getFrameAtTime(positionMs, DEFAULT_OPTIONS, callback);
    }

    public Object getFrameAtTime(long positionMs, MetadataRetriever.Options options, MetadataRetriever.FrameCallback callback) {
        Assertions.checkState(this.mediaSource != null);
        FrameQuery frameQuery = new FrameQuery(this.queryWindowIndex, positionMs, options, callback, this.timeline, this.seekParameters);
        this.queryQueue.add(frameQuery);
        scheduleProcessNextQuery();
        return frameQuery;
    }

    public void setWindowIndex(int windowIndex) {
        this.queryWindowIndex = windowIndex;
    }

    public int getWindowIndex() {
        return this.queryWindowIndex;
    }

    public long getWindowDurationMs() {
        int i = this.queryWindowIndex;
        if (i < 0 || i >= this.timeline.getWindowCount()) {
            return C0841C.TIME_UNSET;
        }
        return this.timeline.getWindow(this.queryWindowIndex, this.window).getDurationMs();
    }

    public Timeline getCurrentTimeline() {
        return this.timeline;
    }

    public void setSeekParameters(SeekParameters seekParameters2) {
        this.seekParameters = seekParameters2;
    }

    public SeekParameters getSeekParameters() {
        return this.seekParameters;
    }

    public boolean handleMessage(Message msg) {
        int i = msg.what;
        if (i == 0) {
            processNextQuery();
            return true;
        } else if (i != 1) {
            return false;
        } else {
            processRecaptureFrame();
            return true;
        }
    }

    private void scheduleProcessNextQuery() {
        if (!this.queryQueue.isEmpty()) {
            this.handler.sendEmptyMessage(0);
        }
    }

    /* access modifiers changed from: private */
    public void processNextQuery() {
        this.handler.removeMessages(0);
        Query query = this.queryQueue.peek();
        if (query != null) {
            try {
                if (!maybeHandleQuery(query)) {
                    return;
                }
            } catch (Exception exception) {
                query.notifyQueryFailed(exception);
            }
            this.queryQueue.remove();
            scheduleProcessNextQuery();
        }
    }

    private boolean maybeHandleQuery(Query query) throws Exception {
        Object currentTrackGroupsPeriodUid = this.trackGroupsPeriodUid;
        if (currentTrackGroupsPeriodUid == null) {
            return false;
        }
        maybeResolveQueryPeriodUid(query);
        Object queryPeriodUid = query.periodUid;
        if (queryPeriodUid == null) {
            return false;
        }
        if (!this.exoPlayer.getSeekParameters().equals(query.seekParameters)) {
            this.exoPlayer.setSeekParameters(query.seekParameters);
        }
        int periodIndexInLatestTimeline = this.timeline.getIndexOfPeriod(queryPeriodUid);
        if (periodIndexInLatestTimeline != -1) {
            Pair<Integer, Long> windowIndexAndPositionUsInLatestTimeline = resolveQueryWindowIndexPositionUsInLatestTimeline(query, periodIndexInLatestTimeline);
            int windowIndexInLatestTimeline = ((Integer) windowIndexAndPositionUsInLatestTimeline.first).intValue();
            long windowPositionInLatestTimelineMs = C0841C.usToMs(((Long) windowIndexAndPositionUsInLatestTimeline.second).longValue());
            if (!currentTrackGroupsPeriodUid.equals(queryPeriodUid)) {
                if (isVideoRendererEnabled()) {
                    disableVideoRenderer();
                }
                seekToWindowPosition(windowIndexInLatestTimeline, windowPositionInLatestTimelineMs);
                return false;
            } else if (!(query instanceof MetadataQuery)) {
                return maybeHandleFrameQuery((FrameQuery) query, windowIndexInLatestTimeline, windowPositionInLatestTimelineMs);
            } else {
                handleMetadataAnswer((MetadataQuery) query, windowIndexInLatestTimeline, periodIndexInLatestTimeline);
                return true;
            }
        } else {
            throw new IllegalSeekPositionException(query.timeline, query.windowIndex, query.windowPositionMs);
        }
    }

    private void maybeResolveQueryPeriodUid(Query query) {
        if (query.periodUid == null) {
            Timeline queryTimeline = query.timeline;
            if (queryTimeline.isEmpty()) {
                queryTimeline = this.timeline;
            }
            Pair<Object, Long> periodUidAndPositionUs = queryTimeline.getPeriodPosition(this.window, this.period, query.windowIndex, C0841C.msToUs(query.windowPositionMs));
            if (periodUidAndPositionUs != null) {
                query.periodPositionUs = ((Long) periodUidAndPositionUs.second).longValue();
                query.periodUid = periodUidAndPositionUs.first;
            }
        }
    }

    private Pair<Integer, Long> resolveQueryWindowIndexPositionUsInLatestTimeline(Query metadataQuery, int periodIndexInLatestTimeline) {
        Timeline.Period periodInLatestTimeline = this.timeline.getPeriod(periodIndexInLatestTimeline, this.period);
        return new Pair<>(Integer.valueOf(periodInLatestTimeline.windowIndex), Long.valueOf(periodInLatestTimeline.getPositionInWindowUs() + metadataQuery.periodPositionUs));
    }

    private void seekToWindowPosition(int windowIndex, long positionMs) {
        this.trackGroupsPeriodUid = null;
        this.exoPlayer.seekTo(windowIndex, positionMs);
    }

    private void handleMetadataAnswer(MetadataQuery metadataQuery, int windowIndexInLatestTimeline, int periodIndexInLatestTimeline) {
        metadataQuery.metadataCallback.onMetadataAvailable(metadataQuery, this.exoPlayer.getCurrentTrackGroups(), this.timeline, windowIndexInLatestTimeline, periodIndexInLatestTimeline);
    }

    private boolean maybeHandleFrameQuery(FrameQuery frameQuery, int windowIndexInLatestTimeline, long windowPositionInLatestTimelineMs) throws Exception {
        ensureFrameQueryWidthHeightResolved(frameQuery);
        if (!isOutputSurfaceSuitableForFrameQuery(frameQuery)) {
            setOutputSurface(frameQuery.options.width, frameQuery.options.height);
            return false;
        } else if (this.lastCapturedBitmap == null && this.lastSurfaceCaptureException == null) {
            renderFrameForCapturing(windowIndexInLatestTimeline, windowPositionInLatestTimelineMs);
            return false;
        } else {
            maybeThrowSurfaceCapturingException();
            handleFrameQueryAnswer(frameQuery, (Bitmap) Assertions.checkNotNull(this.lastCapturedBitmap), windowIndexInLatestTimeline, windowPositionInLatestTimelineMs);
            return true;
        }
    }

    private void ensureFrameQueryWidthHeightResolved(FrameQuery frameQuery) throws Exception {
        if (!frameQuery.outputSizeResolved()) {
            try {
                Format selectedFormat = getVideoFormatForFrameQuery();
                if (selectedFormat != null) {
                    frameQuery.resolveOutputSizeFromFormat(selectedFormat.width, selectedFormat.height);
                    return;
                }
                throw new Exception("Cannot resolve frame size");
            } catch (ExoPlaybackException e) {
                throw new Exception("Cannot resolve frame size", e);
            }
        }
    }

    @Nullable
    private Format getVideoFormatForFrameQuery() throws Exception {
        TrackSelection selection;
        TrackSelectionArray selectionArray = this.standaloneTrackSelector.selectTracks(this.rendererCapabilities, this.exoPlayer.getCurrentTrackGroups(), new MediaSource.MediaPeriodId(this.timeline.getUidOfPeriod(this.exoPlayer.getCurrentPeriodIndex())), this.timeline).selections;
        if (!(selectionArray == null || selectionArray.length != 1 || (selection = selectionArray.get(0)) == null)) {
            for (int i = 0; i < selection.length(); i++) {
                Format format = selection.getFormat(i);
                if (format.width != -1 && format.height != -1) {
                    return format;
                }
            }
        }
        return null;
    }

    private boolean isOutputSurfaceSuitableForFrameQuery(FrameQuery frameQuery) {
        return this.currentOutputWidth == frameQuery.options.width && this.currentOutputHeight == frameQuery.options.height;
    }

    private void setOutputSurface(int width, int height) {
        if (!this.outputSurfaceSetPending) {
            this.outputSurfaceSetPending = true;
            this.videoRendererOutputCapturer.setOutputSize(width, height);
        }
    }

    private void renderFrameForCapturing(int windowIndexInLatestTimeline, long windowPositionInLatestTimelineMs) {
        if (!this.outputCapturePending) {
            if (this.exoPlayer.getCurrentPosition() != windowPositionInLatestTimelineMs) {
                this.exoPlayer.seekTo(windowIndexInLatestTimeline, windowPositionInLatestTimelineMs);
                this.outputCapturePending = isVideoRendererEnabled();
            } else if (isVideoRendererEnabled()) {
                disableVideoRenderer();
                return;
            } else {
                enableVideoRenderer();
                this.outputCapturePending = true;
            }
            if (this.outputCapturePending) {
                scheduleRecaptureFrame();
            }
        }
    }

    private void maybeThrowSurfaceCapturingException() throws Exception {
        if (this.lastSurfaceCaptureException != null) {
            this.handler.removeMessages(1);
            this.isRecapturing = false;
            Exception exceptionToThrow = new Exception("Failed to capture frame", this.lastSurfaceCaptureException);
            this.lastSurfaceCaptureException = null;
            throw exceptionToThrow;
        }
    }

    private void handleFrameQueryAnswer(FrameQuery frameQuery, Bitmap bitmap, int windowIndexInLatestTimeline, long windowPositionInLatestTimelineMs) {
        this.handler.removeMessages(1);
        this.isRecapturing = false;
        frameQuery.frameCallback.onBitmapAvailable(frameQuery, bitmap, this.timeline, windowIndexInLatestTimeline, windowPositionInLatestTimelineMs);
        this.lastCapturedBitmap = null;
    }

    private boolean isVideoRendererEnabled() {
        return !this.trackSelector.getParameters().getRendererDisabled(0);
    }

    private void enableVideoRenderer() {
        DefaultTrackSelector.ParametersBuilder parametersBuilder = this.trackSelector.buildUponParameters();
        parametersBuilder.setRendererDisabled(0, false);
        this.trackSelector.setParameters(parametersBuilder);
    }

    private void disableVideoRenderer() {
        DefaultTrackSelector.ParametersBuilder parametersBuilder = this.trackSelector.buildUponParameters();
        parametersBuilder.setRendererDisabled(0, true);
        this.trackSelector.setParameters(parametersBuilder);
    }

    private void scheduleRecaptureFrame() {
        this.handler.removeMessages(1);
        this.handler.sendEmptyMessageDelayed(1, 15000);
    }

    /* access modifiers changed from: private */
    public void processRecaptureFrame() {
        this.handler.removeMessages(1);
        this.outputCapturePending = false;
        if (!this.isRecapturing) {
            this.isRecapturing = true;
            disableVideoRenderer();
            processNextQuery();
            return;
        }
        this.lastSurfaceCaptureException = new Exception("Player does not render this frame due to unknown error");
        processNextQuery();
    }

    private void resetRetriever() {
        this.trackGroupsPeriodUid = null;
        this.mediaSource = null;
        this.mediaSourceCallback = null;
        this.timeline = Timeline.EMPTY;
        this.queryWindowIndex = 0;
        this.outputCapturePending = false;
        this.isRecapturing = false;
        this.lastCapturedBitmap = null;
        this.lastSurfaceCaptureException = null;
        this.playerSeekAckPositionMs = C0841C.TIME_UNSET;
        this.queryQueue.clear();
        this.handler.removeCallbacksAndMessages(null);
    }

    private final class ComponentListener implements Player.EventListener {
        public void onLoadingChanged(boolean z) {
            Player$EventListener$$CC.onLoadingChanged$$dflt$$(this, z);
        }

        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Player$EventListener$$CC.onPlaybackParametersChanged$$dflt$$(this, playbackParameters);
        }

        public void onPlayerStateChanged(boolean z, int i) {
            Player$EventListener$$CC.onPlayerStateChanged$$dflt$$(this, z, i);
        }

        public void onPositionDiscontinuity(int i) {
            Player$EventListener$$CC.onPositionDiscontinuity$$dflt$$(this, i);
        }

        public void onRepeatModeChanged(int i) {
            Player$EventListener$$CC.onRepeatModeChanged$$dflt$$(this, i);
        }

        public void onShuffleModeEnabledChanged(boolean z) {
            Player$EventListener$$CC.onShuffleModeEnabledChanged$$dflt$$(this, z);
        }

        private ComponentListener() {
        }

        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
            if (!timeline.equals(MetadataRetrieverImpl.this.timeline)) {
                long unused = MetadataRetrieverImpl.this.playerSeekAckPositionMs = C0841C.TIME_UNSET;
                Timeline unused2 = MetadataRetrieverImpl.this.timeline = timeline;
                if (MetadataRetrieverImpl.this.mediaSourceCallback != null) {
                    MetadataRetrieverImpl.this.mediaSourceCallback.onTimelineUpdated(timeline, manifest, reason);
                }
            }
        }

        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            if (MetadataRetrieverImpl.this.exoPlayer.emptyTrackSelectorResult.selections != trackSelections) {
                int currentPeriodIndex = MetadataRetrieverImpl.this.exoPlayer.getCurrentPeriodIndex();
                if (currentPeriodIndex < MetadataRetrieverImpl.this.timeline.getPeriodCount()) {
                    MetadataRetrieverImpl metadataRetrieverImpl = MetadataRetrieverImpl.this;
                    Object unused = metadataRetrieverImpl.trackGroupsPeriodUid = metadataRetrieverImpl.timeline.getUidOfPeriod(currentPeriodIndex);
                }
                MetadataRetrieverImpl.this.processNextQuery();
            }
        }

        public void onSeekProcessed() {
            long lastPlayerSeekAckPositionMs = MetadataRetrieverImpl.this.playerSeekAckPositionMs;
            MetadataRetrieverImpl metadataRetrieverImpl = MetadataRetrieverImpl.this;
            long unused = metadataRetrieverImpl.playerSeekAckPositionMs = metadataRetrieverImpl.exoPlayer.getCurrentPosition();
            if (!MetadataRetrieverImpl.this.outputCapturePending || MetadataRetrieverImpl.this.playerSeekAckPositionMs != lastPlayerSeekAckPositionMs) {
                MetadataRetrieverImpl.this.processNextQuery();
            } else {
                MetadataRetrieverImpl.this.processRecaptureFrame();
            }
        }

        public void onPlayerError(ExoPlaybackException exception) {
            MetadataRetriever.MediaSourceCallback mediaSourceCallback = MetadataRetrieverImpl.this.mediaSourceCallback;
            MetadataRetrieverImpl.this.stop();
            if (mediaSourceCallback != null) {
                mediaSourceCallback.onTimelineUnavailable(exception);
            }
        }
    }

    private final class VideoRendererOutputCapturerCallback implements VideoRendererOutputCapturer.Callback {
        private VideoRendererOutputCapturerCallback() {
        }

        public void onOutputSizeSet(int width, int height) {
            int unused = MetadataRetrieverImpl.this.currentOutputWidth = width;
            int unused2 = MetadataRetrieverImpl.this.currentOutputHeight = height;
            if (MetadataRetrieverImpl.this.outputSurfaceSetPending) {
                boolean unused3 = MetadataRetrieverImpl.this.outputSurfaceSetPending = false;
                MetadataRetrieverImpl.this.processNextQuery();
            }
        }

        public void onSurfaceCaptured(Bitmap bitmap) {
            if (MetadataRetrieverImpl.this.outputCapturePending) {
                Bitmap unused = MetadataRetrieverImpl.this.lastCapturedBitmap = bitmap;
                boolean unused2 = MetadataRetrieverImpl.this.outputCapturePending = false;
                MetadataRetrieverImpl.this.processNextQuery();
            }
        }

        public void onSurfaceCaptureError(Exception exception) {
            if (MetadataRetrieverImpl.this.outputCapturePending) {
                Exception unused = MetadataRetrieverImpl.this.lastSurfaceCaptureException = exception;
                boolean unused2 = MetadataRetrieverImpl.this.outputCapturePending = false;
                MetadataRetrieverImpl.this.processNextQuery();
            }
        }
    }

    private static abstract class Query {
        public long periodPositionUs = C0841C.TIME_UNSET;
        @Nullable
        public Object periodUid;
        public final SeekParameters seekParameters;
        public final Timeline timeline;
        public final int windowIndex;
        public final long windowPositionMs;

        /* access modifiers changed from: protected */
        public abstract void notifyQueryFailed(Exception exc);

        protected Query(int windowIndex2, long windowPositionMs2, Timeline timeline2, SeekParameters seekParameters2) {
            this.windowIndex = windowIndex2;
            this.windowPositionMs = windowPositionMs2;
            this.timeline = timeline2;
            this.seekParameters = seekParameters2;
        }

        public String toString() {
            String valueOf = String.valueOf(this.timeline);
            int i = this.windowIndex;
            long j = this.windowPositionMs;
            String valueOf2 = String.valueOf(this.periodUid);
            long j2 = this.periodPositionUs;
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + ClientAnalytics.LogRequest.LogSource.DROP_BOX_VALUE + String.valueOf(valueOf2).length());
            sb.append("Query{timeline=");
            sb.append(valueOf);
            sb.append(", windowIndex=");
            sb.append(i);
            sb.append(", windowPositionMs=");
            sb.append(j);
            sb.append(", periodUid=");
            sb.append(valueOf2);
            sb.append(", periodPositionUs=");
            sb.append(j2);
            sb.append('}');
            return sb.toString();
        }
    }

    private static final class MetadataQuery extends Query {
        public final MetadataRetriever.MetadataCallback metadataCallback;

        private MetadataQuery(int windowIndex, long positionMs, MetadataRetriever.MetadataCallback metadataCallback2, Timeline timeline, SeekParameters seekParameters) {
            super(windowIndex, positionMs, timeline, seekParameters);
            this.metadataCallback = metadataCallback2;
            this.periodPositionUs = C0841C.TIME_UNSET;
        }

        /* access modifiers changed from: protected */
        public void notifyQueryFailed(Exception exception) {
            this.metadataCallback.onMetadataUnavailable(this, exception);
        }
    }

    private static final class FrameQuery extends Query {
        public final MetadataRetriever.FrameCallback frameCallback;
        public MetadataRetriever.Options options;
        private boolean outputSizeResolved;

        private FrameQuery(int windowIndex, long positionMs, MetadataRetriever.Options options2, MetadataRetriever.FrameCallback metadataCallback, Timeline timeline, SeekParameters seekParameters) {
            super(windowIndex, positionMs, timeline, seekParameters);
            this.options = options2;
            this.frameCallback = metadataCallback;
        }

        /* access modifiers changed from: protected */
        public void notifyQueryFailed(Exception exception) {
            this.frameCallback.onBitmapUnavailable(this, exception);
        }

        public void resolveOutputSizeFromFormat(int formatWidth, int formatHeight) {
            int outputWidth = this.options.width;
            int outputHeight = this.options.height;
            if (this.options.width == -1 && this.options.height == -1) {
                outputWidth = formatWidth;
                outputHeight = formatHeight;
            } else if (this.options.height == -1) {
                outputHeight = (this.options.width * formatHeight) / formatWidth;
            } else if (this.options.width == -1) {
                outputWidth = (this.options.height * formatWidth) / formatHeight;
            } else {
                double d = (double) this.options.width;
                double d2 = (double) this.options.height;
                Double.isNaN(d);
                Double.isNaN(d2);
                double optionsAspectRatio = d / d2;
                double d3 = (double) formatWidth;
                double d4 = (double) formatHeight;
                Double.isNaN(d3);
                Double.isNaN(d4);
                double formatAspectRatio = d3 / d4;
                if (optionsAspectRatio < formatAspectRatio) {
                    double d5 = (double) this.options.width;
                    Double.isNaN(d5);
                    outputHeight = (int) (d5 / formatAspectRatio);
                } else {
                    double d6 = (double) this.options.height;
                    Double.isNaN(d6);
                    outputWidth = (int) (d6 * formatAspectRatio);
                }
            }
            this.options = new MetadataRetriever.Options.Builder().setOutputWidthHeight(outputWidth, outputHeight).build();
            this.outputSizeResolved = true;
        }

        public boolean outputSizeResolved() {
            return this.outputSizeResolved;
        }
    }
}
