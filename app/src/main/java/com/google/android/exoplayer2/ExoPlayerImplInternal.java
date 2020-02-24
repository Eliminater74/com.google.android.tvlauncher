package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.DefaultMediaClock;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

final class ExoPlayerImplInternal implements Handler.Callback, MediaPeriod.Callback, TrackSelector.InvalidationListener, MediaSource.SourceInfoRefreshListener, DefaultMediaClock.PlaybackParameterListener, PlayerMessage.Sender {
    private static final int IDLE_INTERVAL_MS = 1000;
    private static final int MSG_DO_SOME_WORK = 2;
    public static final int MSG_ERROR = 2;
    private static final int MSG_PERIOD_PREPARED = 9;
    public static final int MSG_PLAYBACK_INFO_CHANGED = 0;
    public static final int MSG_PLAYBACK_PARAMETERS_CHANGED = 1;
    private static final int MSG_PLAYBACK_PARAMETERS_CHANGED_INTERNAL = 17;
    private static final int MSG_PREPARE = 0;
    private static final int MSG_REFRESH_SOURCE_INFO = 8;
    private static final int MSG_RELEASE = 7;
    private static final int MSG_SEEK_TO = 3;
    private static final int MSG_SEND_MESSAGE = 15;
    private static final int MSG_SEND_MESSAGE_TO_TARGET_THREAD = 16;
    private static final int MSG_SET_FOREGROUND_MODE = 14;
    private static final int MSG_SET_PLAYBACK_PARAMETERS = 4;
    private static final int MSG_SET_PLAY_WHEN_READY = 1;
    private static final int MSG_SET_REPEAT_MODE = 12;
    private static final int MSG_SET_SEEK_PARAMETERS = 5;
    private static final int MSG_SET_SHUFFLE_ENABLED = 13;
    private static final int MSG_SOURCE_CONTINUE_LOADING_REQUESTED = 10;
    private static final int MSG_STOP = 6;
    private static final int MSG_TRACK_SELECTION_INVALIDATED = 11;
    private static final int PREPARING_SOURCE_INTERVAL_MS = 10;
    private static final int RENDERING_INTERVAL_MS = 10;
    private static final String TAG = "ExoPlayerImplInternal";
    private final long backBufferDurationUs;
    private final BandwidthMeter bandwidthMeter;
    private final Clock clock;
    private final TrackSelectorResult emptyTrackSelectorResult;
    private Renderer[] enabledRenderers;
    private final Handler eventHandler;
    private boolean foregroundMode;
    private final HandlerWrapper handler;
    private final HandlerThread internalPlaybackThread;
    private final LoadControl loadControl;
    private final DefaultMediaClock mediaClock;
    private MediaSource mediaSource;
    private int nextPendingMessageIndex;
    private SeekPosition pendingInitialSeekPosition;
    private final ArrayList<PendingMessageInfo> pendingMessages;
    private int pendingPrepareCount;
    private final Timeline.Period period;
    private boolean playWhenReady;
    private PlaybackInfo playbackInfo;
    private final PlaybackInfoUpdate playbackInfoUpdate;
    private final MediaPeriodQueue queue = new MediaPeriodQueue();
    private boolean rebuffering;
    private boolean released;
    private final RendererCapabilities[] rendererCapabilities;
    private long rendererPositionUs;
    private final Renderer[] renderers;
    private int repeatMode;
    private final boolean retainBackBufferFromKeyframe;
    private SeekParameters seekParameters;
    private boolean shuffleModeEnabled;
    private final TrackSelector trackSelector;
    private final Timeline.Window window;

    public ExoPlayerImplInternal(Renderer[] renderers2, TrackSelector trackSelector2, TrackSelectorResult emptyTrackSelectorResult2, LoadControl loadControl2, BandwidthMeter bandwidthMeter2, boolean playWhenReady2, int repeatMode2, boolean shuffleModeEnabled2, Handler eventHandler2, Clock clock2) {
        this.renderers = renderers2;
        this.trackSelector = trackSelector2;
        this.emptyTrackSelectorResult = emptyTrackSelectorResult2;
        this.loadControl = loadControl2;
        this.bandwidthMeter = bandwidthMeter2;
        this.playWhenReady = playWhenReady2;
        this.repeatMode = repeatMode2;
        this.shuffleModeEnabled = shuffleModeEnabled2;
        this.eventHandler = eventHandler2;
        this.clock = clock2;
        this.backBufferDurationUs = loadControl2.getBackBufferDurationUs();
        this.retainBackBufferFromKeyframe = loadControl2.retainBackBufferFromKeyframe();
        this.seekParameters = SeekParameters.DEFAULT;
        this.playbackInfo = PlaybackInfo.createDummy(C0841C.TIME_UNSET, emptyTrackSelectorResult2);
        this.playbackInfoUpdate = new PlaybackInfoUpdate();
        this.rendererCapabilities = new RendererCapabilities[renderers2.length];
        for (int i = 0; i < renderers2.length; i++) {
            renderers2[i].setIndex(i);
            this.rendererCapabilities[i] = renderers2[i].getCapabilities();
        }
        this.mediaClock = new DefaultMediaClock(this, clock2);
        this.pendingMessages = new ArrayList<>();
        this.enabledRenderers = new Renderer[0];
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        trackSelector2.init(this, bandwidthMeter2);
        this.internalPlaybackThread = new HandlerThread("ExoPlayerImplInternal:Handler", -16);
        this.internalPlaybackThread.start();
        this.handler = clock2.createHandler(this.internalPlaybackThread.getLooper(), this);
    }

    public void prepare(MediaSource mediaSource2, boolean resetPosition, boolean resetState) {
        this.handler.obtainMessage(0, resetPosition, resetState, mediaSource2).sendToTarget();
    }

    public void setPlayWhenReady(boolean playWhenReady2) {
        this.handler.obtainMessage(1, playWhenReady2, 0).sendToTarget();
    }

    public void setRepeatMode(int repeatMode2) {
        this.handler.obtainMessage(12, repeatMode2, 0).sendToTarget();
    }

    public void setShuffleModeEnabled(boolean shuffleModeEnabled2) {
        this.handler.obtainMessage(13, shuffleModeEnabled2, 0).sendToTarget();
    }

    public void seekTo(Timeline timeline, int windowIndex, long positionUs) {
        this.handler.obtainMessage(3, new SeekPosition(timeline, windowIndex, positionUs)).sendToTarget();
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.handler.obtainMessage(4, playbackParameters).sendToTarget();
    }

    public void setSeekParameters(SeekParameters seekParameters2) {
        this.handler.obtainMessage(5, seekParameters2).sendToTarget();
    }

    public void stop(boolean reset) {
        this.handler.obtainMessage(6, reset, 0).sendToTarget();
    }

    public synchronized void sendMessage(PlayerMessage message) {
        if (this.released) {
            Log.m30w(TAG, "Ignoring messages sent after release.");
            message.markAsProcessed(false);
            return;
        }
        this.handler.obtainMessage(15, message).sendToTarget();
    }

    public synchronized void setForegroundMode(boolean foregroundMode2) {
        if (foregroundMode2) {
            this.handler.obtainMessage(14, 1, 0).sendToTarget();
        } else {
            AtomicBoolean processedFlag = new AtomicBoolean();
            this.handler.obtainMessage(14, 0, 0, processedFlag).sendToTarget();
            boolean wasInterrupted = false;
            while (!processedFlag.get() && !this.released) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    wasInterrupted = true;
                }
            }
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0023, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void release() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.released     // Catch:{ all -> 0x0024 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            com.google.android.exoplayer2.util.HandlerWrapper r0 = r2.handler     // Catch:{ all -> 0x0024 }
            r1 = 7
            r0.sendEmptyMessage(r1)     // Catch:{ all -> 0x0024 }
            r0 = 0
        L_0x000e:
            boolean r1 = r2.released     // Catch:{ all -> 0x0024 }
            if (r1 != 0) goto L_0x0019
            r2.wait()     // Catch:{ InterruptedException -> 0x0016 }
            goto L_0x000e
        L_0x0016:
            r1 = move-exception
            r0 = 1
            goto L_0x000e
        L_0x0019:
            if (r0 == 0) goto L_0x0022
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0024 }
            r1.interrupt()     // Catch:{ all -> 0x0024 }
        L_0x0022:
            monitor-exit(r2)
            return
        L_0x0024:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.release():void");
    }

    public Looper getPlaybackLooper() {
        return this.internalPlaybackThread.getLooper();
    }

    public void onSourceInfoRefreshed(MediaSource source, Timeline timeline, Object manifest) {
        this.handler.obtainMessage(8, new MediaSourceRefreshInfo(source, timeline, manifest)).sendToTarget();
    }

    public void onPrepared(MediaPeriod source) {
        this.handler.obtainMessage(9, source).sendToTarget();
    }

    public void onContinueLoadingRequested(MediaPeriod source) {
        this.handler.obtainMessage(10, source).sendToTarget();
    }

    public void onTrackSelectionsInvalidated() {
        this.handler.sendEmptyMessage(11);
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        this.handler.obtainMessage(17, playbackParameters).sendToTarget();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: java.lang.RuntimeException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: java.lang.OutOfMemoryError} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handleMessage(android.os.Message r8) {
        /*
            r7 = this;
            java.lang.String r0 = "ExoPlayerImplInternal"
            r1 = 2
            r2 = 1
            r3 = 0
            int r4 = r8.what     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            switch(r4) {
                case 0: goto L_0x009c;
                case 1: goto L_0x0091;
                case 2: goto L_0x008d;
                case 3: goto L_0x0085;
                case 4: goto L_0x007d;
                case 5: goto L_0x0075;
                case 6: goto L_0x006a;
                case 7: goto L_0x0066;
                case 8: goto L_0x005e;
                case 9: goto L_0x0056;
                case 10: goto L_0x004e;
                case 11: goto L_0x0049;
                case 12: goto L_0x0042;
                case 13: goto L_0x0036;
                case 14: goto L_0x0026;
                case 15: goto L_0x001d;
                case 16: goto L_0x0014;
                case 17: goto L_0x000b;
                default: goto L_0x000a;
            }     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
        L_0x000a:
            return r3
        L_0x000b:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.PlaybackParameters r4 = (com.google.android.exoplayer2.PlaybackParameters) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.handlePlaybackParameters(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0014:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.PlayerMessage r4 = (com.google.android.exoplayer2.PlayerMessage) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.sendMessageToTargetThread(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x001d:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.PlayerMessage r4 = (com.google.android.exoplayer2.PlayerMessage) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.sendMessageInternal(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0026:
            int r4 = r8.arg1     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            if (r4 == 0) goto L_0x002c
            r4 = 1
            goto L_0x002d
        L_0x002c:
            r4 = 0
        L_0x002d:
            java.lang.Object r5 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            java.util.concurrent.atomic.AtomicBoolean r5 = (java.util.concurrent.atomic.AtomicBoolean) r5     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.setForegroundModeInternal(r4, r5)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0036:
            int r4 = r8.arg1     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            if (r4 == 0) goto L_0x003c
            r4 = 1
            goto L_0x003d
        L_0x003c:
            r4 = 0
        L_0x003d:
            r7.setShuffleModeEnabledInternal(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0042:
            int r4 = r8.arg1     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.setRepeatModeInternal(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0049:
            r7.reselectTracksInternal()     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x004e:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.source.MediaPeriod r4 = (com.google.android.exoplayer2.source.MediaPeriod) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.handleContinueLoadingRequested(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0056:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.source.MediaPeriod r4 = (com.google.android.exoplayer2.source.MediaPeriod) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.handlePeriodPrepared(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x005e:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.ExoPlayerImplInternal$MediaSourceRefreshInfo r4 = (com.google.android.exoplayer2.ExoPlayerImplInternal.MediaSourceRefreshInfo) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.handleSourceInfoRefreshed(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0066:
            r7.releaseInternal()     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            return r2
        L_0x006a:
            int r4 = r8.arg1     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            if (r4 == 0) goto L_0x0070
            r4 = 1
            goto L_0x0071
        L_0x0070:
            r4 = 0
        L_0x0071:
            r7.stopInternal(r3, r4, r2)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0075:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.SeekParameters r4 = (com.google.android.exoplayer2.SeekParameters) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.setSeekParametersInternal(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x007d:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.PlaybackParameters r4 = (com.google.android.exoplayer2.PlaybackParameters) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.setPlaybackParametersInternal(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0085:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition r4 = (com.google.android.exoplayer2.ExoPlayerImplInternal.SeekPosition) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            r7.seekToInternal(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x008d:
            r7.doSomeWork()     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x0091:
            int r4 = r8.arg1     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            if (r4 == 0) goto L_0x0097
            r4 = 1
            goto L_0x0098
        L_0x0097:
            r4 = 0
        L_0x0098:
            r7.setPlayWhenReadyInternal(r4)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x00b2
        L_0x009c:
            java.lang.Object r4 = r8.obj     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            com.google.android.exoplayer2.source.MediaSource r4 = (com.google.android.exoplayer2.source.MediaSource) r4     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            int r5 = r8.arg1     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            if (r5 == 0) goto L_0x00a6
            r5 = 1
            goto L_0x00a7
        L_0x00a6:
            r5 = 0
        L_0x00a7:
            int r6 = r8.arg2     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            if (r6 == 0) goto L_0x00ad
            r6 = 1
            goto L_0x00ae
        L_0x00ad:
            r6 = 0
        L_0x00ae:
            r7.prepareInternal(r4, r5, r6)     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
        L_0x00b2:
            r7.maybeNotifyPlaybackInfoChanged()     // Catch:{ ExoPlaybackException -> 0x00fc, IOException -> 0x00e2, RuntimeException -> 0x00b8, OutOfMemoryError -> 0x00b6 }
            goto L_0x0111
        L_0x00b6:
            r4 = move-exception
            goto L_0x00b9
        L_0x00b8:
            r4 = move-exception
        L_0x00b9:
            java.lang.String r5 = "Internal runtime error."
            com.google.android.exoplayer2.util.Log.m27e(r0, r5, r4)
            boolean r0 = r4 instanceof java.lang.OutOfMemoryError
            if (r0 == 0) goto L_0x00ca
            r0 = r4
            java.lang.OutOfMemoryError r0 = (java.lang.OutOfMemoryError) r0
            com.google.android.exoplayer2.ExoPlaybackException r0 = com.google.android.exoplayer2.ExoPlaybackException.createForOutOfMemoryError(r0)
            goto L_0x00d1
        L_0x00ca:
            r0 = r4
            java.lang.RuntimeException r0 = (java.lang.RuntimeException) r0
            com.google.android.exoplayer2.ExoPlaybackException r0 = com.google.android.exoplayer2.ExoPlaybackException.createForUnexpected(r0)
        L_0x00d1:
            android.os.Handler r5 = r7.eventHandler
            android.os.Message r1 = r5.obtainMessage(r1, r0)
            r1.sendToTarget()
            r7.stopInternal(r2, r3, r3)
            r7.maybeNotifyPlaybackInfoChanged()
            goto L_0x0112
        L_0x00e2:
            r4 = move-exception
            java.lang.String r5 = "Source error."
            com.google.android.exoplayer2.util.Log.m27e(r0, r5, r4)
            android.os.Handler r0 = r7.eventHandler
            com.google.android.exoplayer2.ExoPlaybackException r5 = com.google.android.exoplayer2.ExoPlaybackException.createForSource(r4)
            android.os.Message r0 = r0.obtainMessage(r1, r5)
            r0.sendToTarget()
            r7.stopInternal(r3, r3, r3)
            r7.maybeNotifyPlaybackInfoChanged()
            goto L_0x0111
        L_0x00fc:
            r4 = move-exception
            java.lang.String r5 = "Playback error."
            com.google.android.exoplayer2.util.Log.m27e(r0, r5, r4)
            android.os.Handler r0 = r7.eventHandler
            android.os.Message r0 = r0.obtainMessage(r1, r4)
            r0.sendToTarget()
            r7.stopInternal(r2, r3, r3)
            r7.maybeNotifyPlaybackInfoChanged()
        L_0x0111:
        L_0x0112:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.handleMessage(android.os.Message):boolean");
    }

    private void setState(int state) {
        if (this.playbackInfo.playbackState != state) {
            this.playbackInfo = this.playbackInfo.copyWithPlaybackState(state);
        }
    }

    private void setIsLoading(boolean isLoading) {
        if (this.playbackInfo.isLoading != isLoading) {
            this.playbackInfo = this.playbackInfo.copyWithIsLoading(isLoading);
        }
    }

    private void maybeNotifyPlaybackInfoChanged() {
        int i;
        if (this.playbackInfoUpdate.hasPendingUpdate(this.playbackInfo)) {
            Handler handler2 = this.eventHandler;
            int access$100 = this.playbackInfoUpdate.operationAcks;
            if (this.playbackInfoUpdate.positionDiscontinuity) {
                i = this.playbackInfoUpdate.discontinuityReason;
            } else {
                i = -1;
            }
            handler2.obtainMessage(0, access$100, i, this.playbackInfo).sendToTarget();
            this.playbackInfoUpdate.reset(this.playbackInfo);
        }
    }

    private void prepareInternal(MediaSource mediaSource2, boolean resetPosition, boolean resetState) {
        this.pendingPrepareCount++;
        resetInternal(false, true, resetPosition, resetState);
        this.loadControl.onPrepared();
        this.mediaSource = mediaSource2;
        setState(2);
        mediaSource2.prepareSource(this, this.bandwidthMeter.getTransferListener());
        this.handler.sendEmptyMessage(2);
    }

    private void setPlayWhenReadyInternal(boolean playWhenReady2) throws ExoPlaybackException {
        this.rebuffering = false;
        this.playWhenReady = playWhenReady2;
        if (!playWhenReady2) {
            stopRenderers();
            updatePlaybackPositions();
        } else if (this.playbackInfo.playbackState == 3) {
            startRenderers();
            this.handler.sendEmptyMessage(2);
        } else if (this.playbackInfo.playbackState == 2) {
            this.handler.sendEmptyMessage(2);
        }
    }

    private void setRepeatModeInternal(int repeatMode2) throws ExoPlaybackException {
        this.repeatMode = repeatMode2;
        if (!this.queue.updateRepeatMode(repeatMode2)) {
            seekToCurrentPosition(true);
        }
        handleLoadingMediaPeriodChanged(false);
    }

    private void setShuffleModeEnabledInternal(boolean shuffleModeEnabled2) throws ExoPlaybackException {
        this.shuffleModeEnabled = shuffleModeEnabled2;
        if (!this.queue.updateShuffleModeEnabled(shuffleModeEnabled2)) {
            seekToCurrentPosition(true);
        }
        handleLoadingMediaPeriodChanged(false);
    }

    private void seekToCurrentPosition(boolean sendDiscontinuity) throws ExoPlaybackException {
        MediaSource.MediaPeriodId periodId = this.queue.getPlayingPeriod().info.f73id;
        long newPositionUs = seekToPeriodPosition(periodId, this.playbackInfo.positionUs, true);
        if (newPositionUs != this.playbackInfo.positionUs) {
            PlaybackInfo playbackInfo2 = this.playbackInfo;
            this.playbackInfo = playbackInfo2.copyWithNewPosition(periodId, newPositionUs, playbackInfo2.contentPositionUs, getTotalBufferedDurationUs());
            if (sendDiscontinuity) {
                this.playbackInfoUpdate.setPositionDiscontinuity(4);
            }
        }
    }

    private void startRenderers() throws ExoPlaybackException {
        this.rebuffering = false;
        this.mediaClock.start();
        for (Renderer renderer : this.enabledRenderers) {
            renderer.start();
        }
    }

    private void stopRenderers() throws ExoPlaybackException {
        this.mediaClock.stop();
        for (Renderer renderer : this.enabledRenderers) {
            ensureStopped(renderer);
        }
    }

    private void updatePlaybackPositions() throws ExoPlaybackException {
        if (this.queue.hasPlayingPeriod()) {
            MediaPeriodHolder playingPeriodHolder = this.queue.getPlayingPeriod();
            long periodPositionUs = playingPeriodHolder.mediaPeriod.readDiscontinuity();
            if (periodPositionUs != C0841C.TIME_UNSET) {
                resetRendererPosition(periodPositionUs);
                if (periodPositionUs != this.playbackInfo.positionUs) {
                    PlaybackInfo playbackInfo2 = this.playbackInfo;
                    this.playbackInfo = playbackInfo2.copyWithNewPosition(playbackInfo2.periodId, periodPositionUs, this.playbackInfo.contentPositionUs, getTotalBufferedDurationUs());
                    this.playbackInfoUpdate.setPositionDiscontinuity(4);
                }
            } else {
                this.rendererPositionUs = this.mediaClock.syncAndGetPositionUs();
                long periodPositionUs2 = playingPeriodHolder.toPeriodTime(this.rendererPositionUs);
                maybeTriggerPendingMessages(this.playbackInfo.positionUs, periodPositionUs2);
                this.playbackInfo.positionUs = periodPositionUs2;
            }
            MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
            this.playbackInfo.bufferedPositionUs = loadingPeriod.getBufferedPositionUs();
            this.playbackInfo.totalBufferedDurationUs = getTotalBufferedDurationUs();
        }
    }

    private void doSomeWork() throws ExoPlaybackException, IOException {
        long operationStartTimeMs = this.clock.uptimeMillis();
        updatePeriods();
        if (!this.queue.hasPlayingPeriod()) {
            maybeThrowPeriodPrepareError();
            scheduleNextWork(operationStartTimeMs, 10);
            return;
        }
        MediaPeriodHolder playingPeriodHolder = this.queue.getPlayingPeriod();
        TraceUtil.beginSection("doSomeWork");
        updatePlaybackPositions();
        long rendererPositionElapsedRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        playingPeriodHolder.mediaPeriod.discardBuffer(this.playbackInfo.positionUs - this.backBufferDurationUs, this.retainBackBufferFromKeyframe);
        boolean renderersReadyOrEnded = true;
        boolean renderersEnded = true;
        for (Renderer renderer : this.enabledRenderers) {
            renderer.render(this.rendererPositionUs, rendererPositionElapsedRealtimeUs);
            boolean z = true;
            renderersEnded = renderersEnded && renderer.isEnded();
            boolean rendererReadyOrEnded = renderer.isReady() || renderer.isEnded() || rendererWaitingForNextStream(renderer);
            if (!rendererReadyOrEnded) {
                renderer.maybeThrowStreamError();
            }
            if (!renderersReadyOrEnded || !rendererReadyOrEnded) {
                z = false;
            }
            renderersReadyOrEnded = z;
        }
        if (!renderersReadyOrEnded) {
            maybeThrowPeriodPrepareError();
        }
        long playingPeriodDurationUs = playingPeriodHolder.info.durationUs;
        if (renderersEnded && ((playingPeriodDurationUs == C0841C.TIME_UNSET || playingPeriodDurationUs <= this.playbackInfo.positionUs) && playingPeriodHolder.info.isFinal)) {
            setState(4);
            stopRenderers();
        } else if (this.playbackInfo.playbackState == 2 && shouldTransitionToReadyState(renderersReadyOrEnded)) {
            setState(3);
            if (this.playWhenReady) {
                startRenderers();
            }
        } else if (this.playbackInfo.playbackState == 3 && (this.enabledRenderers.length != 0 ? !renderersReadyOrEnded : !isTimelineReady())) {
            this.rebuffering = this.playWhenReady;
            setState(2);
            stopRenderers();
        }
        if (this.playbackInfo.playbackState == 2) {
            for (Renderer renderer2 : this.enabledRenderers) {
                renderer2.maybeThrowStreamError();
            }
        }
        if ((this.playWhenReady && this.playbackInfo.playbackState == 3) || this.playbackInfo.playbackState == 2) {
            scheduleNextWork(operationStartTimeMs, 10);
        } else if (this.enabledRenderers.length == 0 || this.playbackInfo.playbackState == 4) {
            this.handler.removeMessages(2);
        } else {
            scheduleNextWork(operationStartTimeMs, 1000);
        }
        TraceUtil.endSection();
    }

    private void scheduleNextWork(long thisOperationStartTimeMs, long intervalMs) {
        this.handler.removeMessages(2);
        this.handler.sendEmptyMessageAtTime(2, thisOperationStartTimeMs + intervalMs);
    }

    /* JADX INFO: Multiple debug info for r7v3 java.lang.Object: [D('periodId' com.google.android.exoplayer2.source.MediaSource$MediaPeriodId), D('periodUid' java.lang.Object)] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x011c  */
    /* JADX WARNING: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal.SeekPosition r26) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r25 = this;
            r1 = r25
            r2 = r26
            com.google.android.exoplayer2.ExoPlayerImplInternal$PlaybackInfoUpdate r0 = r1.playbackInfoUpdate
            r3 = 1
            r0.incrementPendingOperationAcks(r3)
            android.util.Pair r4 = r1.resolveSeekPosition(r2, r3)
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r0 = 0
            if (r4 != 0) goto L_0x0033
            com.google.android.exoplayer2.PlaybackInfo r7 = r1.playbackInfo
            boolean r8 = r1.shuffleModeEnabled
            com.google.android.exoplayer2.Timeline$Window r9 = r1.window
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r7 = r7.getDummyFirstMediaPeriodId(r8, r9)
            r8 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r10 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r12 = 1
            r23 = r8
            r8 = r7
            r7 = r12
            r12 = r23
            goto L_0x0060
        L_0x0033:
            java.lang.Object r7 = r4.first
            java.lang.Object r8 = r4.second
            java.lang.Long r8 = (java.lang.Long) r8
            long r10 = r8.longValue()
            com.google.android.exoplayer2.MediaPeriodQueue r8 = r1.queue
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r8 = r8.resolveMediaPeriodIdForAds(r7, r10)
            boolean r9 = r8.isAd()
            if (r9 == 0) goto L_0x004e
            r12 = 0
            r9 = 1
            r7 = r9
            goto L_0x0060
        L_0x004e:
            java.lang.Object r9 = r4.second
            java.lang.Long r9 = (java.lang.Long) r9
            long r12 = r9.longValue()
            long r14 = r2.windowPositionUs
            int r9 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r9 != 0) goto L_0x005e
            r9 = 1
            goto L_0x005f
        L_0x005e:
            r9 = 0
        L_0x005f:
            r7 = r9
        L_0x0060:
            r9 = 2
            com.google.android.exoplayer2.source.MediaSource r14 = r1.mediaSource     // Catch:{ all -> 0x0106 }
            if (r14 == 0) goto L_0x00e7
            int r14 = r1.pendingPrepareCount     // Catch:{ all -> 0x0106 }
            if (r14 <= 0) goto L_0x006d
            r22 = r4
            goto L_0x00e9
        L_0x006d:
            int r14 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r14 != 0) goto L_0x0081
            r5 = 4
            r1.setState(r5)     // Catch:{ all -> 0x007c }
            r1.resetInternal(r0, r0, r3, r0)     // Catch:{ all -> 0x007c }
            r22 = r4
            goto L_0x00eb
        L_0x007c:
            r0 = move-exception
            r22 = r4
            goto L_0x0109
        L_0x0081:
            r5 = r12
            com.google.android.exoplayer2.PlaybackInfo r14 = r1.playbackInfo     // Catch:{ all -> 0x0106 }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r14 = r14.periodId     // Catch:{ all -> 0x0106 }
            boolean r14 = r8.equals(r14)     // Catch:{ all -> 0x0106 }
            if (r14 == 0) goto L_0x00d7
            com.google.android.exoplayer2.MediaPeriodQueue r14 = r1.queue     // Catch:{ all -> 0x0106 }
            com.google.android.exoplayer2.MediaPeriodHolder r14 = r14.getPlayingPeriod()     // Catch:{ all -> 0x0106 }
            r15 = r14
            if (r15 == 0) goto L_0x00a5
            r16 = 0
            int r14 = (r5 > r16 ? 1 : (r5 == r16 ? 0 : -1))
            if (r14 == 0) goto L_0x00a5
            com.google.android.exoplayer2.source.MediaPeriod r14 = r15.mediaPeriod     // Catch:{ all -> 0x007c }
            com.google.android.exoplayer2.SeekParameters r0 = r1.seekParameters     // Catch:{ all -> 0x007c }
            long r17 = r14.getAdjustedSeekPositionUs(r5, r0)     // Catch:{ all -> 0x007c }
            r5 = r17
        L_0x00a5:
            long r17 = com.google.android.exoplayer2.C0841C.usToMs(r5)     // Catch:{ all -> 0x0106 }
            com.google.android.exoplayer2.PlaybackInfo r0 = r1.playbackInfo     // Catch:{ all -> 0x0106 }
            r22 = r4
            long r3 = r0.positionUs     // Catch:{ all -> 0x0104 }
            long r3 = com.google.android.exoplayer2.C0841C.usToMs(r3)     // Catch:{ all -> 0x0104 }
            int r0 = (r17 > r3 ? 1 : (r17 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x00d5
            com.google.android.exoplayer2.PlaybackInfo r0 = r1.playbackInfo     // Catch:{ all -> 0x0104 }
            long r3 = r0.positionUs     // Catch:{ all -> 0x0104 }
            r16 = r3
            com.google.android.exoplayer2.PlaybackInfo r14 = r1.playbackInfo
            long r20 = r25.getTotalBufferedDurationUs()
            r0 = r15
            r15 = r8
            r18 = r10
            com.google.android.exoplayer2.PlaybackInfo r3 = r14.copyWithNewPosition(r15, r16, r18, r20)
            r1.playbackInfo = r3
            if (r7 == 0) goto L_0x00d4
            com.google.android.exoplayer2.ExoPlayerImplInternal$PlaybackInfoUpdate r3 = r1.playbackInfoUpdate
            r3.setPositionDiscontinuity(r9)
        L_0x00d4:
            return
        L_0x00d5:
            r0 = r15
            goto L_0x00d9
        L_0x00d7:
            r22 = r4
        L_0x00d9:
            long r3 = r1.seekToPeriodPosition(r8, r5)     // Catch:{ all -> 0x0104 }
            int r0 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
            if (r0 == 0) goto L_0x00e3
            r14 = 1
            goto L_0x00e4
        L_0x00e3:
            r14 = 0
        L_0x00e4:
            r7 = r7 | r14
            r12 = r3
            goto L_0x00eb
        L_0x00e7:
            r22 = r4
        L_0x00e9:
            r1.pendingInitialSeekPosition = r2     // Catch:{ all -> 0x0104 }
        L_0x00eb:
            com.google.android.exoplayer2.PlaybackInfo r14 = r1.playbackInfo
            long r20 = r25.getTotalBufferedDurationUs()
            r15 = r8
            r16 = r12
            r18 = r10
            com.google.android.exoplayer2.PlaybackInfo r0 = r14.copyWithNewPosition(r15, r16, r18, r20)
            r1.playbackInfo = r0
            if (r7 == 0) goto L_0x0103
            com.google.android.exoplayer2.ExoPlayerImplInternal$PlaybackInfoUpdate r0 = r1.playbackInfoUpdate
            r0.setPositionDiscontinuity(r9)
        L_0x0103:
            return
        L_0x0104:
            r0 = move-exception
            goto L_0x0109
        L_0x0106:
            r0 = move-exception
            r22 = r4
        L_0x0109:
            com.google.android.exoplayer2.PlaybackInfo r14 = r1.playbackInfo
            long r20 = r25.getTotalBufferedDurationUs()
            r15 = r8
            r16 = r12
            r18 = r10
            com.google.android.exoplayer2.PlaybackInfo r3 = r14.copyWithNewPosition(r15, r16, r18, r20)
            r1.playbackInfo = r3
            if (r7 == 0) goto L_0x0121
            com.google.android.exoplayer2.ExoPlayerImplInternal$PlaybackInfoUpdate r3 = r1.playbackInfoUpdate
            r3.setPositionDiscontinuity(r9)
        L_0x0121:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition):void");
    }

    private long seekToPeriodPosition(MediaSource.MediaPeriodId periodId, long periodPositionUs) throws ExoPlaybackException {
        return seekToPeriodPosition(periodId, periodPositionUs, this.queue.getPlayingPeriod() != this.queue.getReadingPeriod());
    }

    private long seekToPeriodPosition(MediaSource.MediaPeriodId periodId, long periodPositionUs, boolean forceDisableRenderers) throws ExoPlaybackException {
        stopRenderers();
        this.rebuffering = false;
        setState(2);
        MediaPeriodHolder oldPlayingPeriodHolder = this.queue.getPlayingPeriod();
        MediaPeriodHolder newPlayingPeriodHolder = oldPlayingPeriodHolder;
        while (true) {
            if (newPlayingPeriodHolder != null) {
                if (periodId.equals(newPlayingPeriodHolder.info.f73id) && newPlayingPeriodHolder.prepared) {
                    this.queue.removeAfter(newPlayingPeriodHolder);
                    break;
                }
                newPlayingPeriodHolder = this.queue.advancePlayingPeriod();
            } else {
                break;
            }
        }
        if (oldPlayingPeriodHolder != newPlayingPeriodHolder || forceDisableRenderers) {
            for (Renderer renderer : this.enabledRenderers) {
                disableRenderer(renderer);
            }
            this.enabledRenderers = new Renderer[0];
            oldPlayingPeriodHolder = null;
        }
        if (newPlayingPeriodHolder != null) {
            updatePlayingPeriodRenderers(oldPlayingPeriodHolder);
            if (newPlayingPeriodHolder.hasEnabledTracks) {
                periodPositionUs = newPlayingPeriodHolder.mediaPeriod.seekToUs(periodPositionUs);
                newPlayingPeriodHolder.mediaPeriod.discardBuffer(periodPositionUs - this.backBufferDurationUs, this.retainBackBufferFromKeyframe);
            }
            resetRendererPosition(periodPositionUs);
            maybeContinueLoading();
        } else {
            this.queue.clear(true);
            this.playbackInfo = this.playbackInfo.copyWithTrackInfo(TrackGroupArray.EMPTY, this.emptyTrackSelectorResult);
            resetRendererPosition(periodPositionUs);
        }
        handleLoadingMediaPeriodChanged(false);
        this.handler.sendEmptyMessage(2);
        return periodPositionUs;
    }

    private void resetRendererPosition(long periodPositionUs) throws ExoPlaybackException {
        long j;
        if (!this.queue.hasPlayingPeriod()) {
            j = periodPositionUs;
        } else {
            j = this.queue.getPlayingPeriod().toRendererTime(periodPositionUs);
        }
        this.rendererPositionUs = j;
        this.mediaClock.resetPosition(this.rendererPositionUs);
        for (Renderer renderer : this.enabledRenderers) {
            renderer.resetPosition(this.rendererPositionUs);
        }
        notifyTrackSelectionDiscontinuity();
    }

    private void setPlaybackParametersInternal(PlaybackParameters playbackParameters) {
        this.mediaClock.setPlaybackParameters(playbackParameters);
    }

    private void setSeekParametersInternal(SeekParameters seekParameters2) {
        this.seekParameters = seekParameters2;
    }

    private void setForegroundModeInternal(boolean foregroundMode2, @Nullable AtomicBoolean processedFlag) {
        if (this.foregroundMode != foregroundMode2) {
            this.foregroundMode = foregroundMode2;
            if (!foregroundMode2) {
                for (Renderer renderer : this.renderers) {
                    if (renderer.getState() == 0) {
                        renderer.reset();
                    }
                }
            }
        }
        if (processedFlag != null) {
            synchronized (this) {
                processedFlag.set(true);
                notifyAll();
            }
        }
    }

    private void stopInternal(boolean forceResetRenderers, boolean resetPositionAndState, boolean acknowledgeStop) {
        resetInternal(forceResetRenderers || !this.foregroundMode, true, resetPositionAndState, resetPositionAndState);
        this.playbackInfoUpdate.incrementPendingOperationAcks(this.pendingPrepareCount + (acknowledgeStop));
        this.pendingPrepareCount = 0;
        this.loadControl.onStopped();
        setState(1);
    }

    private void releaseInternal() {
        resetInternal(true, true, true, true);
        this.loadControl.onReleased();
        setState(1);
        this.internalPlaybackThread.quit();
        synchronized (this) {
            this.released = true;
            notifyAll();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00e4  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x010f  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0112  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void resetInternal(boolean r24, boolean r25, boolean r26, boolean r27) {
        /*
            r23 = this;
            r1 = r23
            com.google.android.exoplayer2.util.HandlerWrapper r0 = r1.handler
            r2 = 2
            r0.removeMessages(r2)
            r2 = 0
            r1.rebuffering = r2
            com.google.android.exoplayer2.DefaultMediaClock r0 = r1.mediaClock
            r0.stop()
            r3 = 0
            r1.rendererPositionUs = r3
            com.google.android.exoplayer2.Renderer[] r3 = r1.enabledRenderers
            int r4 = r3.length
            r5 = 0
        L_0x0018:
            java.lang.String r6 = "ExoPlayerImplInternal"
            if (r5 >= r4) goto L_0x002d
            r7 = r3[r5]
            r1.disableRenderer(r7)     // Catch:{ ExoPlaybackException -> 0x0024, RuntimeException -> 0x0022 }
            goto L_0x002a
        L_0x0022:
            r0 = move-exception
            goto L_0x0025
        L_0x0024:
            r0 = move-exception
        L_0x0025:
            java.lang.String r8 = "Disable failed."
            com.google.android.exoplayer2.util.Log.m27e(r6, r8, r0)
        L_0x002a:
            int r5 = r5 + 1
            goto L_0x0018
        L_0x002d:
            if (r24 == 0) goto L_0x0046
            com.google.android.exoplayer2.Renderer[] r3 = r1.renderers
            int r4 = r3.length
            r5 = 0
        L_0x0033:
            if (r5 >= r4) goto L_0x0046
            r7 = r3[r5]
            r7.reset()     // Catch:{ RuntimeException -> 0x003b }
            goto L_0x0043
        L_0x003b:
            r0 = move-exception
            r8 = r0
            r0 = r8
            java.lang.String r8 = "Reset failed."
            com.google.android.exoplayer2.util.Log.m27e(r6, r8, r0)
        L_0x0043:
            int r5 = r5 + 1
            goto L_0x0033
        L_0x0046:
            com.google.android.exoplayer2.Renderer[] r0 = new com.google.android.exoplayer2.Renderer[r2]
            r1.enabledRenderers = r0
            r0 = 0
            if (r26 == 0) goto L_0x0050
            r1.pendingInitialSeekPosition = r0
            goto L_0x0089
        L_0x0050:
            if (r27 == 0) goto L_0x0089
            r3 = 1
            com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition r4 = r1.pendingInitialSeekPosition
            if (r4 != 0) goto L_0x008b
            com.google.android.exoplayer2.PlaybackInfo r4 = r1.playbackInfo
            com.google.android.exoplayer2.Timeline r4 = r4.timeline
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L_0x008b
            com.google.android.exoplayer2.PlaybackInfo r4 = r1.playbackInfo
            com.google.android.exoplayer2.Timeline r4 = r4.timeline
            com.google.android.exoplayer2.PlaybackInfo r5 = r1.playbackInfo
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r5 = r5.periodId
            java.lang.Object r5 = r5.periodUid
            com.google.android.exoplayer2.Timeline$Period r6 = r1.period
            r4.getPeriodByUid(r5, r6)
            com.google.android.exoplayer2.PlaybackInfo r4 = r1.playbackInfo
            long r4 = r4.positionUs
            com.google.android.exoplayer2.Timeline$Period r6 = r1.period
            long r6 = r6.getPositionInWindowUs()
            long r4 = r4 + r6
            com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition r6 = new com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition
            com.google.android.exoplayer2.Timeline r7 = com.google.android.exoplayer2.Timeline.EMPTY
            com.google.android.exoplayer2.Timeline$Period r8 = r1.period
            int r8 = r8.windowIndex
            r6.<init>(r7, r8, r4)
            r1.pendingInitialSeekPosition = r6
            goto L_0x008b
        L_0x0089:
            r3 = r26
        L_0x008b:
            com.google.android.exoplayer2.MediaPeriodQueue r4 = r1.queue
            if (r3 != 0) goto L_0x0091
            r5 = 1
            goto L_0x0092
        L_0x0091:
            r5 = 0
        L_0x0092:
            r4.clear(r5)
            r1.setIsLoading(r2)
            if (r27 == 0) goto L_0x00c0
            com.google.android.exoplayer2.MediaPeriodQueue r4 = r1.queue
            com.google.android.exoplayer2.Timeline r5 = com.google.android.exoplayer2.Timeline.EMPTY
            r4.setTimeline(r5)
            java.util.ArrayList<com.google.android.exoplayer2.ExoPlayerImplInternal$PendingMessageInfo> r4 = r1.pendingMessages
            java.util.Iterator r4 = r4.iterator()
        L_0x00a7:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00b9
            java.lang.Object r5 = r4.next()
            com.google.android.exoplayer2.ExoPlayerImplInternal$PendingMessageInfo r5 = (com.google.android.exoplayer2.ExoPlayerImplInternal.PendingMessageInfo) r5
            com.google.android.exoplayer2.PlayerMessage r6 = r5.message
            r6.markAsProcessed(r2)
            goto L_0x00a7
        L_0x00b9:
            java.util.ArrayList<com.google.android.exoplayer2.ExoPlayerImplInternal$PendingMessageInfo> r4 = r1.pendingMessages
            r4.clear()
            r1.nextPendingMessageIndex = r2
        L_0x00c0:
            if (r3 == 0) goto L_0x00ce
            com.google.android.exoplayer2.PlaybackInfo r2 = r1.playbackInfo
            boolean r4 = r1.shuffleModeEnabled
            com.google.android.exoplayer2.Timeline$Window r5 = r1.window
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r2.getDummyFirstMediaPeriodId(r4, r5)
            r7 = r2
            goto L_0x00d3
        L_0x00ce:
            com.google.android.exoplayer2.PlaybackInfo r2 = r1.playbackInfo
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r2.periodId
            r7 = r2
        L_0x00d3:
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r3 == 0) goto L_0x00dd
            r8 = r4
            goto L_0x00e1
        L_0x00dd:
            com.google.android.exoplayer2.PlaybackInfo r2 = r1.playbackInfo
            long r8 = r2.positionUs
        L_0x00e1:
            if (r3 == 0) goto L_0x00e4
            goto L_0x00e8
        L_0x00e4:
            com.google.android.exoplayer2.PlaybackInfo r2 = r1.playbackInfo
            long r4 = r2.contentPositionUs
        L_0x00e8:
            r10 = r4
            com.google.android.exoplayer2.PlaybackInfo r2 = new com.google.android.exoplayer2.PlaybackInfo
            if (r27 == 0) goto L_0x00f0
            com.google.android.exoplayer2.Timeline r4 = com.google.android.exoplayer2.Timeline.EMPTY
            goto L_0x00f4
        L_0x00f0:
            com.google.android.exoplayer2.PlaybackInfo r4 = r1.playbackInfo
            com.google.android.exoplayer2.Timeline r4 = r4.timeline
        L_0x00f4:
            r5 = r4
            if (r27 == 0) goto L_0x00f9
            r6 = r0
            goto L_0x00fe
        L_0x00f9:
            com.google.android.exoplayer2.PlaybackInfo r4 = r1.playbackInfo
            java.lang.Object r4 = r4.manifest
            r6 = r4
        L_0x00fe:
            com.google.android.exoplayer2.PlaybackInfo r4 = r1.playbackInfo
            int r12 = r4.playbackState
            r13 = 0
            if (r27 == 0) goto L_0x0108
            com.google.android.exoplayer2.source.TrackGroupArray r4 = com.google.android.exoplayer2.source.TrackGroupArray.EMPTY
            goto L_0x010c
        L_0x0108:
            com.google.android.exoplayer2.PlaybackInfo r4 = r1.playbackInfo
            com.google.android.exoplayer2.source.TrackGroupArray r4 = r4.trackGroups
        L_0x010c:
            r14 = r4
            if (r27 == 0) goto L_0x0112
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r4 = r1.emptyTrackSelectorResult
            goto L_0x0116
        L_0x0112:
            com.google.android.exoplayer2.PlaybackInfo r4 = r1.playbackInfo
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r4 = r4.trackSelectorResult
        L_0x0116:
            r15 = r4
            r19 = 0
            r4 = r2
            r16 = r7
            r17 = r8
            r21 = r8
            r4.<init>(r5, r6, r7, r8, r10, r12, r13, r14, r15, r16, r17, r19, r21)
            r1.playbackInfo = r2
            if (r25 == 0) goto L_0x0130
            com.google.android.exoplayer2.source.MediaSource r2 = r1.mediaSource
            if (r2 == 0) goto L_0x0130
            r2.releaseSource(r1)
            r1.mediaSource = r0
        L_0x0130:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.resetInternal(boolean, boolean, boolean, boolean):void");
    }

    private void sendMessageInternal(PlayerMessage message) throws ExoPlaybackException {
        if (message.getPositionMs() == C0841C.TIME_UNSET) {
            sendMessageToTarget(message);
        } else if (this.mediaSource == null || this.pendingPrepareCount > 0) {
            this.pendingMessages.add(new PendingMessageInfo(message));
        } else {
            PendingMessageInfo pendingMessageInfo = new PendingMessageInfo(message);
            if (resolvePendingMessagePosition(pendingMessageInfo)) {
                this.pendingMessages.add(pendingMessageInfo);
                Collections.sort(this.pendingMessages);
                return;
            }
            message.markAsProcessed(false);
        }
    }

    private void sendMessageToTarget(PlayerMessage message) throws ExoPlaybackException {
        if (message.getHandler().getLooper() == this.handler.getLooper()) {
            deliverMessage(message);
            if (this.playbackInfo.playbackState == 3 || this.playbackInfo.playbackState == 2) {
                this.handler.sendEmptyMessage(2);
                return;
            }
            return;
        }
        this.handler.obtainMessage(16, message).sendToTarget();
    }

    private void sendMessageToTargetThread(PlayerMessage message) {
        message.getHandler().post(new ExoPlayerImplInternal$$Lambda$0(this, message));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$sendMessageToTargetThread$0$ExoPlayerImplInternal(PlayerMessage message) {
        try {
            deliverMessage(message);
        } catch (ExoPlaybackException e) {
            Log.m27e(TAG, "Unexpected error delivering message on external thread.", e);
            throw new RuntimeException(e);
        }
    }

    private void deliverMessage(PlayerMessage message) throws ExoPlaybackException {
        if (!message.isCanceled()) {
            try {
                message.getTarget().handleMessage(message.getType(), message.getPayload());
            } finally {
                message.markAsProcessed(true);
            }
        }
    }

    private void resolvePendingMessagePositions() {
        for (int i = this.pendingMessages.size() - 1; i >= 0; i--) {
            if (!resolvePendingMessagePosition(this.pendingMessages.get(i))) {
                this.pendingMessages.get(i).message.markAsProcessed(false);
                this.pendingMessages.remove(i);
            }
        }
        Collections.sort(this.pendingMessages);
    }

    private boolean resolvePendingMessagePosition(PendingMessageInfo pendingMessageInfo) {
        if (pendingMessageInfo.resolvedPeriodUid == null) {
            Pair<Object, Long> periodPosition = resolveSeekPosition(new SeekPosition(pendingMessageInfo.message.getTimeline(), pendingMessageInfo.message.getWindowIndex(), C0841C.msToUs(pendingMessageInfo.message.getPositionMs())), false);
            if (periodPosition == null) {
                return false;
            }
            pendingMessageInfo.setResolvedPosition(this.playbackInfo.timeline.getIndexOfPeriod(periodPosition.first), ((Long) periodPosition.second).longValue(), periodPosition.first);
            return true;
        }
        int index = this.playbackInfo.timeline.getIndexOfPeriod(pendingMessageInfo.resolvedPeriodUid);
        if (index == -1) {
            return false;
        }
        pendingMessageInfo.resolvedPeriodIndex = index;
        return true;
    }

    private void maybeTriggerPendingMessages(long oldPeriodPositionUs, long newPeriodPositionUs) throws ExoPlaybackException {
        PendingMessageInfo nextInfo;
        PendingMessageInfo pendingMessageInfo;
        PendingMessageInfo pendingMessageInfo2;
        if (!this.pendingMessages.isEmpty() && !this.playbackInfo.periodId.isAd()) {
            if (this.playbackInfo.startPositionUs == oldPeriodPositionUs) {
                oldPeriodPositionUs--;
            }
            int currentPeriodIndex = this.playbackInfo.timeline.getIndexOfPeriod(this.playbackInfo.periodId.periodUid);
            int i = this.nextPendingMessageIndex;
            PendingMessageInfo previousInfo = i > 0 ? this.pendingMessages.get(i - 1) : null;
            while (previousInfo != null && (previousInfo.resolvedPeriodIndex > currentPeriodIndex || (previousInfo.resolvedPeriodIndex == currentPeriodIndex && previousInfo.resolvedPeriodTimeUs > oldPeriodPositionUs))) {
                this.nextPendingMessageIndex--;
                int i2 = this.nextPendingMessageIndex;
                previousInfo = i2 > 0 ? this.pendingMessages.get(i2 - 1) : null;
            }
            if (this.nextPendingMessageIndex < this.pendingMessages.size()) {
                nextInfo = this.pendingMessages.get(this.nextPendingMessageIndex);
            } else {
                nextInfo = null;
            }
            while (nextInfo != null && nextInfo.resolvedPeriodUid != null && (nextInfo.resolvedPeriodIndex < currentPeriodIndex || (nextInfo.resolvedPeriodIndex == currentPeriodIndex && nextInfo.resolvedPeriodTimeUs <= oldPeriodPositionUs))) {
                this.nextPendingMessageIndex++;
                if (this.nextPendingMessageIndex < this.pendingMessages.size()) {
                    pendingMessageInfo2 = this.pendingMessages.get(this.nextPendingMessageIndex);
                } else {
                    pendingMessageInfo2 = null;
                }
                nextInfo = pendingMessageInfo2;
            }
            while (nextInfo != null && nextInfo.resolvedPeriodUid != null && nextInfo.resolvedPeriodIndex == currentPeriodIndex && nextInfo.resolvedPeriodTimeUs > oldPeriodPositionUs && nextInfo.resolvedPeriodTimeUs <= newPeriodPositionUs) {
                sendMessageToTarget(nextInfo.message);
                if (nextInfo.message.getDeleteAfterDelivery() || nextInfo.message.isCanceled()) {
                    this.pendingMessages.remove(this.nextPendingMessageIndex);
                } else {
                    this.nextPendingMessageIndex++;
                }
                if (this.nextPendingMessageIndex < this.pendingMessages.size()) {
                    pendingMessageInfo = this.pendingMessages.get(this.nextPendingMessageIndex);
                } else {
                    pendingMessageInfo = null;
                }
                nextInfo = pendingMessageInfo;
            }
        }
    }

    private void ensureStopped(Renderer renderer) throws ExoPlaybackException {
        if (renderer.getState() == 2) {
            renderer.stop();
        }
    }

    private void disableRenderer(Renderer renderer) throws ExoPlaybackException {
        this.mediaClock.onRendererDisabled(renderer);
        ensureStopped(renderer);
        renderer.disable();
    }

    private void reselectTracksInternal() throws ExoPlaybackException {
        if (this.queue.hasPlayingPeriod()) {
            float playbackSpeed = this.mediaClock.getPlaybackParameters().speed;
            MediaPeriodHolder periodHolder = this.queue.getPlayingPeriod();
            MediaPeriodHolder readingPeriodHolder = this.queue.getReadingPeriod();
            boolean selectionsChangedForReadPeriod = true;
            while (periodHolder != null && periodHolder.prepared) {
                TrackSelectorResult newTrackSelectorResult = periodHolder.selectTracks(playbackSpeed, this.playbackInfo.timeline);
                if (newTrackSelectorResult != null) {
                    if (selectionsChangedForReadPeriod) {
                        MediaPeriodHolder playingPeriodHolder = this.queue.getPlayingPeriod();
                        boolean recreateStreams = this.queue.removeAfter(playingPeriodHolder);
                        boolean[] streamResetFlags = new boolean[this.renderers.length];
                        boolean[] streamResetFlags2 = streamResetFlags;
                        long periodPositionUs = playingPeriodHolder.applyTrackSelection(newTrackSelectorResult, this.playbackInfo.positionUs, recreateStreams, streamResetFlags);
                        if (!(this.playbackInfo.playbackState == 4 || periodPositionUs == this.playbackInfo.positionUs)) {
                            PlaybackInfo playbackInfo2 = this.playbackInfo;
                            this.playbackInfo = playbackInfo2.copyWithNewPosition(playbackInfo2.periodId, periodPositionUs, this.playbackInfo.contentPositionUs, getTotalBufferedDurationUs());
                            this.playbackInfoUpdate.setPositionDiscontinuity(4);
                            resetRendererPosition(periodPositionUs);
                        }
                        int enabledRendererCount = 0;
                        boolean[] rendererWasEnabledFlags = new boolean[this.renderers.length];
                        int i = 0;
                        while (true) {
                            Renderer[] rendererArr = this.renderers;
                            if (i >= rendererArr.length) {
                                break;
                            }
                            Renderer renderer = rendererArr[i];
                            rendererWasEnabledFlags[i] = renderer.getState() != 0;
                            SampleStream sampleStream = playingPeriodHolder.sampleStreams[i];
                            if (sampleStream != null) {
                                enabledRendererCount++;
                            }
                            if (rendererWasEnabledFlags[i]) {
                                if (sampleStream != renderer.getStream()) {
                                    disableRenderer(renderer);
                                } else if (streamResetFlags2[i]) {
                                    renderer.resetPosition(this.rendererPositionUs);
                                }
                            }
                            i++;
                        }
                        this.playbackInfo = this.playbackInfo.copyWithTrackInfo(playingPeriodHolder.getTrackGroups(), playingPeriodHolder.getTrackSelectorResult());
                        enableRenderers(rendererWasEnabledFlags, enabledRendererCount);
                    } else {
                        this.queue.removeAfter(periodHolder);
                        if (periodHolder.prepared) {
                            periodHolder.applyTrackSelection(newTrackSelectorResult, Math.max(periodHolder.info.startPositionUs, periodHolder.toPeriodTime(this.rendererPositionUs)), false);
                        }
                    }
                    handleLoadingMediaPeriodChanged(true);
                    if (this.playbackInfo.playbackState != 4) {
                        maybeContinueLoading();
                        updatePlaybackPositions();
                        this.handler.sendEmptyMessage(2);
                        return;
                    }
                    return;
                }
                if (periodHolder == readingPeriodHolder) {
                    selectionsChangedForReadPeriod = false;
                }
                periodHolder = periodHolder.getNext();
            }
        }
    }

    private void updateTrackSelectionPlaybackSpeed(float playbackSpeed) {
        MediaPeriodHolder periodHolder = this.queue.getFrontPeriod();
        while (periodHolder != null && periodHolder.prepared) {
            for (TrackSelection trackSelection : periodHolder.getTrackSelectorResult().selections.getAll()) {
                if (trackSelection != null) {
                    trackSelection.onPlaybackSpeed(playbackSpeed);
                }
            }
            periodHolder = periodHolder.getNext();
        }
    }

    private void notifyTrackSelectionDiscontinuity() {
        for (MediaPeriodHolder periodHolder = this.queue.getFrontPeriod(); periodHolder != null; periodHolder = periodHolder.getNext()) {
            TrackSelectorResult trackSelectorResult = periodHolder.getTrackSelectorResult();
            if (trackSelectorResult != null) {
                for (TrackSelection trackSelection : trackSelectorResult.selections.getAll()) {
                    if (trackSelection != null) {
                        trackSelection.onDiscontinuity();
                    }
                }
            }
        }
    }

    private boolean shouldTransitionToReadyState(boolean renderersReadyOrEnded) {
        if (this.enabledRenderers.length == 0) {
            return isTimelineReady();
        }
        if (!renderersReadyOrEnded) {
            return false;
        }
        if (!this.playbackInfo.isLoading) {
            return true;
        }
        MediaPeriodHolder loadingHolder = this.queue.getLoadingPeriod();
        if ((loadingHolder.isFullyBuffered() && loadingHolder.info.isFinal) || this.loadControl.shouldStartPlayback(getTotalBufferedDurationUs(), this.mediaClock.getPlaybackParameters().speed, this.rebuffering)) {
            return true;
        }
        return false;
    }

    private boolean isTimelineReady() {
        MediaPeriodHolder playingPeriodHolder = this.queue.getPlayingPeriod();
        MediaPeriodHolder nextPeriodHolder = playingPeriodHolder.getNext();
        long playingPeriodDurationUs = playingPeriodHolder.info.durationUs;
        return playingPeriodDurationUs == C0841C.TIME_UNSET || this.playbackInfo.positionUs < playingPeriodDurationUs || (nextPeriodHolder != null && (nextPeriodHolder.prepared || nextPeriodHolder.info.f73id.isAd()));
    }

    private void maybeThrowSourceInfoRefreshError() throws IOException {
        if (this.queue.getLoadingPeriod() != null) {
            Renderer[] rendererArr = this.enabledRenderers;
            int length = rendererArr.length;
            int i = 0;
            while (i < length) {
                if (rendererArr[i].hasReadStreamToEnd()) {
                    i++;
                } else {
                    return;
                }
            }
        }
        this.mediaSource.maybeThrowSourceInfoRefreshError();
    }

    private void maybeThrowPeriodPrepareError() throws IOException {
        MediaPeriodHolder loadingPeriodHolder = this.queue.getLoadingPeriod();
        MediaPeriodHolder readingPeriodHolder = this.queue.getReadingPeriod();
        if (loadingPeriodHolder != null && !loadingPeriodHolder.prepared) {
            if (readingPeriodHolder == null || readingPeriodHolder.getNext() == loadingPeriodHolder) {
                Renderer[] rendererArr = this.enabledRenderers;
                int length = rendererArr.length;
                int i = 0;
                while (i < length) {
                    if (rendererArr[i].hasReadStreamToEnd()) {
                        i++;
                    } else {
                        return;
                    }
                }
                loadingPeriodHolder.mediaPeriod.maybeThrowPrepareError();
            }
        }
    }

    private void handleSourceInfoRefreshed(MediaSourceRefreshInfo sourceRefreshInfo) throws ExoPlaybackException {
        MediaSourceRefreshInfo mediaSourceRefreshInfo = sourceRefreshInfo;
        if (mediaSourceRefreshInfo.source == this.mediaSource) {
            this.playbackInfoUpdate.incrementPendingOperationAcks(this.pendingPrepareCount);
            this.pendingPrepareCount = 0;
            Timeline oldTimeline = this.playbackInfo.timeline;
            Timeline timeline = mediaSourceRefreshInfo.timeline;
            Object manifest = mediaSourceRefreshInfo.manifest;
            this.queue.setTimeline(timeline);
            this.playbackInfo = this.playbackInfo.copyWithTimeline(timeline, manifest);
            resolvePendingMessagePositions();
            MediaSource.MediaPeriodId newPeriodId = this.playbackInfo.periodId;
            long oldContentPositionUs = this.playbackInfo.periodId.isAd() ? this.playbackInfo.contentPositionUs : this.playbackInfo.positionUs;
            long newContentPositionUs = oldContentPositionUs;
            SeekPosition seekPosition = this.pendingInitialSeekPosition;
            if (seekPosition != null) {
                Pair<Object, Long> periodPosition = resolveSeekPosition(seekPosition, true);
                this.pendingInitialSeekPosition = null;
                if (periodPosition == null) {
                    handleSourceInfoRefreshEndedPlayback();
                    return;
                } else {
                    newContentPositionUs = ((Long) periodPosition.second).longValue();
                    newPeriodId = this.queue.resolveMediaPeriodIdForAds(periodPosition.first, newContentPositionUs);
                }
            } else if (oldContentPositionUs == C0841C.TIME_UNSET && !timeline.isEmpty()) {
                Pair<Object, Long> defaultPosition = getPeriodPosition(timeline, timeline.getFirstWindowIndex(this.shuffleModeEnabled), C0841C.TIME_UNSET);
                newContentPositionUs = ((Long) defaultPosition.second).longValue();
                newPeriodId = this.queue.resolveMediaPeriodIdForAds(defaultPosition.first, newContentPositionUs);
            } else if (timeline.getIndexOfPeriod(newPeriodId.periodUid) == -1) {
                Object newPeriodUid = resolveSubsequentPeriod(newPeriodId.periodUid, oldTimeline, timeline);
                if (newPeriodUid == null) {
                    handleSourceInfoRefreshEndedPlayback();
                    return;
                }
                Pair<Object, Long> defaultPosition2 = getPeriodPosition(timeline, timeline.getPeriodByUid(newPeriodUid, this.period).windowIndex, C0841C.TIME_UNSET);
                newContentPositionUs = ((Long) defaultPosition2.second).longValue();
                newPeriodId = this.queue.resolveMediaPeriodIdForAds(defaultPosition2.first, newContentPositionUs);
            } else if (newPeriodId.isAd()) {
                newPeriodId = this.queue.resolveMediaPeriodIdForAds(newPeriodId.periodUid, newContentPositionUs);
            }
            if (!this.playbackInfo.periodId.equals(newPeriodId) || oldContentPositionUs != newContentPositionUs) {
                MediaPeriodHolder periodHolder = this.queue.getFrontPeriod();
                if (periodHolder != null) {
                    while (periodHolder.getNext() != null) {
                        periodHolder = periodHolder.getNext();
                        if (periodHolder.info.f73id.equals(newPeriodId)) {
                            periodHolder.info = this.queue.getUpdatedMediaPeriodInfo(periodHolder.info);
                        }
                    }
                }
                this.playbackInfo = this.playbackInfo.copyWithNewPosition(newPeriodId, seekToPeriodPosition(newPeriodId, newPeriodId.isAd() ? 0 : newContentPositionUs), newContentPositionUs, getTotalBufferedDurationUs());
                return;
            }
            if (!this.queue.updateQueuedPeriods(this.rendererPositionUs, getMaxRendererReadPositionUs())) {
                seekToCurrentPosition(false);
            }
            handleLoadingMediaPeriodChanged(false);
        }
    }

    private long getMaxRendererReadPositionUs() {
        MediaPeriodHolder readingHolder = this.queue.getReadingPeriod();
        if (readingHolder == null) {
            return 0;
        }
        long maxReadPositionUs = readingHolder.getRendererOffset();
        int i = 0;
        while (true) {
            Renderer[] rendererArr = this.renderers;
            if (i >= rendererArr.length) {
                return maxReadPositionUs;
            }
            if (rendererArr[i].getState() != 0 && this.renderers[i].getStream() == readingHolder.sampleStreams[i]) {
                long readingPositionUs = this.renderers[i].getReadingPositionUs();
                if (readingPositionUs == Long.MIN_VALUE) {
                    return Long.MIN_VALUE;
                }
                maxReadPositionUs = Math.max(readingPositionUs, maxReadPositionUs);
            }
            i++;
        }
    }

    private void handleSourceInfoRefreshEndedPlayback() {
        setState(4);
        resetInternal(false, false, true, false);
    }

    @Nullable
    private Object resolveSubsequentPeriod(Object oldPeriodUid, Timeline oldTimeline, Timeline newTimeline) {
        int oldPeriodIndex = oldTimeline.getIndexOfPeriod(oldPeriodUid);
        int newPeriodIndex = -1;
        int maxIterations = oldTimeline.getPeriodCount();
        for (int i = 0; i < maxIterations && newPeriodIndex == -1 && (oldPeriodIndex = oldTimeline.getNextPeriodIndex(oldPeriodIndex, this.period, this.window, this.repeatMode, this.shuffleModeEnabled)) != -1; i++) {
            newPeriodIndex = newTimeline.getIndexOfPeriod(oldTimeline.getUidOfPeriod(oldPeriodIndex));
        }
        if (newPeriodIndex == -1) {
            return null;
        }
        return newTimeline.getUidOfPeriod(newPeriodIndex);
    }

    private Pair<Object, Long> resolveSeekPosition(SeekPosition seekPosition, boolean trySubsequentPeriods) {
        int periodIndex;
        Timeline timeline = this.playbackInfo.timeline;
        Timeline seekTimeline = seekPosition.timeline;
        if (timeline.isEmpty()) {
            return null;
        }
        if (seekTimeline.isEmpty()) {
            seekTimeline = timeline;
        }
        try {
            Pair<Object, Long> periodPosition = seekTimeline.getPeriodPosition(this.window, this.period, seekPosition.windowIndex, seekPosition.windowPositionUs);
            if (timeline == seekTimeline || (periodIndex = timeline.getIndexOfPeriod(periodPosition.first)) != -1) {
                return periodPosition;
            }
            if (!trySubsequentPeriods || resolveSubsequentPeriod(periodPosition.first, seekTimeline, timeline) == null) {
                return null;
            }
            return getPeriodPosition(timeline, timeline.getPeriod(periodIndex, this.period).windowIndex, C0841C.TIME_UNSET);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private Pair<Object, Long> getPeriodPosition(Timeline timeline, int windowIndex, long windowPositionUs) {
        return timeline.getPeriodPosition(this.window, this.period, windowIndex, windowPositionUs);
    }

    private void updatePeriods() throws ExoPlaybackException, IOException {
        MediaPeriodHolder playingPeriodHolder;
        MediaPeriodHolder loadingPeriodHolder;
        int discontinuityReason;
        ExoPlayerImplInternal exoPlayerImplInternal = this;
        MediaSource mediaSource2 = exoPlayerImplInternal.mediaSource;
        if (mediaSource2 != null) {
            if (exoPlayerImplInternal.pendingPrepareCount > 0) {
                mediaSource2.maybeThrowSourceInfoRefreshError();
                return;
            }
            maybeUpdateLoadingPeriod();
            MediaPeriodHolder loadingPeriodHolder2 = exoPlayerImplInternal.queue.getLoadingPeriod();
            if (loadingPeriodHolder2 == null || loadingPeriodHolder2.isFullyBuffered()) {
                exoPlayerImplInternal.setIsLoading(false);
            } else if (!exoPlayerImplInternal.playbackInfo.isLoading) {
                maybeContinueLoading();
            }
            if (exoPlayerImplInternal.queue.hasPlayingPeriod()) {
                MediaPeriodHolder playingPeriodHolder2 = exoPlayerImplInternal.queue.getPlayingPeriod();
                MediaPeriodHolder readingPeriodHolder = exoPlayerImplInternal.queue.getReadingPeriod();
                boolean advancedPlayingPeriod = false;
                while (exoPlayerImplInternal.playWhenReady && playingPeriodHolder2 != readingPeriodHolder && exoPlayerImplInternal.rendererPositionUs >= playingPeriodHolder2.getNext().getStartPositionRendererTime()) {
                    if (advancedPlayingPeriod) {
                        maybeNotifyPlaybackInfoChanged();
                    }
                    if (playingPeriodHolder2.info.isLastInTimelinePeriod) {
                        discontinuityReason = 0;
                    } else {
                        discontinuityReason = 3;
                    }
                    MediaPeriodHolder oldPlayingPeriodHolder = playingPeriodHolder2;
                    playingPeriodHolder2 = exoPlayerImplInternal.queue.advancePlayingPeriod();
                    exoPlayerImplInternal.updatePlayingPeriodRenderers(oldPlayingPeriodHolder);
                    exoPlayerImplInternal.playbackInfo = exoPlayerImplInternal.playbackInfo.copyWithNewPosition(playingPeriodHolder2.info.f73id, playingPeriodHolder2.info.startPositionUs, playingPeriodHolder2.info.contentPositionUs, getTotalBufferedDurationUs());
                    exoPlayerImplInternal.playbackInfoUpdate.setPositionDiscontinuity(discontinuityReason);
                    updatePlaybackPositions();
                    advancedPlayingPeriod = true;
                }
                if (readingPeriodHolder.info.isFinal) {
                    int i = 0;
                    while (true) {
                        Renderer[] rendererArr = exoPlayerImplInternal.renderers;
                        if (i < rendererArr.length) {
                            Renderer renderer = rendererArr[i];
                            SampleStream sampleStream = readingPeriodHolder.sampleStreams[i];
                            if (sampleStream != null && renderer.getStream() == sampleStream && renderer.hasReadStreamToEnd()) {
                                renderer.setCurrentStreamFinal();
                            }
                            i++;
                        } else {
                            return;
                        }
                    }
                } else if (readingPeriodHolder.getNext() != null) {
                    int i2 = 0;
                    while (true) {
                        Renderer[] rendererArr2 = exoPlayerImplInternal.renderers;
                        if (i2 < rendererArr2.length) {
                            Renderer renderer2 = rendererArr2[i2];
                            SampleStream sampleStream2 = readingPeriodHolder.sampleStreams[i2];
                            if (renderer2.getStream() != sampleStream2) {
                                return;
                            }
                            if (sampleStream2 == null || renderer2.hasReadStreamToEnd()) {
                                i2++;
                            } else {
                                return;
                            }
                        } else if (!readingPeriodHolder.getNext().prepared) {
                            maybeThrowPeriodPrepareError();
                            return;
                        } else {
                            TrackSelectorResult oldTrackSelectorResult = readingPeriodHolder.getTrackSelectorResult();
                            MediaPeriodHolder readingPeriodHolder2 = exoPlayerImplInternal.queue.advanceReadingPeriod();
                            TrackSelectorResult newTrackSelectorResult = readingPeriodHolder2.getTrackSelectorResult();
                            boolean initialDiscontinuity = readingPeriodHolder2.mediaPeriod.readDiscontinuity() != C0841C.TIME_UNSET;
                            int i3 = 0;
                            while (true) {
                                Renderer[] rendererArr3 = exoPlayerImplInternal.renderers;
                                if (i3 < rendererArr3.length) {
                                    Renderer renderer3 = rendererArr3[i3];
                                    if (!oldTrackSelectorResult.isRendererEnabled(i3)) {
                                        loadingPeriodHolder = loadingPeriodHolder2;
                                        playingPeriodHolder = playingPeriodHolder2;
                                    } else if (initialDiscontinuity) {
                                        renderer3.setCurrentStreamFinal();
                                        loadingPeriodHolder = loadingPeriodHolder2;
                                        playingPeriodHolder = playingPeriodHolder2;
                                    } else if (!renderer3.isCurrentStreamFinal()) {
                                        TrackSelection newSelection = newTrackSelectorResult.selections.get(i3);
                                        boolean newRendererEnabled = newTrackSelectorResult.isRendererEnabled(i3);
                                        boolean isNoSampleRenderer = exoPlayerImplInternal.rendererCapabilities[i3].getTrackType() == 6;
                                        RendererConfiguration oldConfig = oldTrackSelectorResult.rendererConfigurations[i3];
                                        RendererConfiguration newConfig = newTrackSelectorResult.rendererConfigurations[i3];
                                        if (!newRendererEnabled || !newConfig.equals(oldConfig) || isNoSampleRenderer) {
                                            loadingPeriodHolder = loadingPeriodHolder2;
                                            playingPeriodHolder = playingPeriodHolder2;
                                            renderer3.setCurrentStreamFinal();
                                        } else {
                                            loadingPeriodHolder = loadingPeriodHolder2;
                                            playingPeriodHolder = playingPeriodHolder2;
                                            renderer3.replaceStream(getFormats(newSelection), readingPeriodHolder2.sampleStreams[i3], readingPeriodHolder2.getRendererOffset());
                                        }
                                    } else {
                                        loadingPeriodHolder = loadingPeriodHolder2;
                                        playingPeriodHolder = playingPeriodHolder2;
                                    }
                                    i3++;
                                    exoPlayerImplInternal = this;
                                    loadingPeriodHolder2 = loadingPeriodHolder;
                                    playingPeriodHolder2 = playingPeriodHolder;
                                } else {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void maybeUpdateLoadingPeriod() throws IOException {
        this.queue.reevaluateBuffer(this.rendererPositionUs);
        if (this.queue.shouldLoadNextMediaPeriod()) {
            MediaPeriodInfo info = this.queue.getNextMediaPeriodInfo(this.rendererPositionUs, this.playbackInfo);
            if (info == null) {
                maybeThrowSourceInfoRefreshError();
                return;
            }
            this.queue.enqueueNextMediaPeriod(this.rendererCapabilities, this.trackSelector, this.loadControl.getAllocator(), this.mediaSource, info).prepare(this, info.startPositionUs);
            setIsLoading(true);
            handleLoadingMediaPeriodChanged(false);
        }
    }

    private void handlePeriodPrepared(MediaPeriod mediaPeriod) throws ExoPlaybackException {
        if (this.queue.isLoading(mediaPeriod)) {
            MediaPeriodHolder loadingPeriodHolder = this.queue.getLoadingPeriod();
            loadingPeriodHolder.handlePrepared(this.mediaClock.getPlaybackParameters().speed, this.playbackInfo.timeline);
            updateLoadControlTrackSelection(loadingPeriodHolder.getTrackGroups(), loadingPeriodHolder.getTrackSelectorResult());
            if (!this.queue.hasPlayingPeriod()) {
                resetRendererPosition(this.queue.advancePlayingPeriod().info.startPositionUs);
                updatePlayingPeriodRenderers(null);
            }
            maybeContinueLoading();
        }
    }

    private void handleContinueLoadingRequested(MediaPeriod mediaPeriod) {
        if (this.queue.isLoading(mediaPeriod)) {
            this.queue.reevaluateBuffer(this.rendererPositionUs);
            maybeContinueLoading();
        }
    }

    private void handlePlaybackParameters(PlaybackParameters playbackParameters) throws ExoPlaybackException {
        this.eventHandler.obtainMessage(1, playbackParameters).sendToTarget();
        updateTrackSelectionPlaybackSpeed(playbackParameters.speed);
        for (Renderer renderer : this.renderers) {
            if (renderer != null) {
                renderer.setOperatingRate(playbackParameters.speed);
            }
        }
    }

    private void maybeContinueLoading() {
        MediaPeriodHolder loadingPeriodHolder = this.queue.getLoadingPeriod();
        long nextLoadPositionUs = loadingPeriodHolder.getNextLoadPositionUs();
        if (nextLoadPositionUs == Long.MIN_VALUE) {
            setIsLoading(false);
            return;
        }
        boolean continueLoading = this.loadControl.shouldContinueLoading(getTotalBufferedDurationUs(nextLoadPositionUs), this.mediaClock.getPlaybackParameters().speed);
        setIsLoading(continueLoading);
        if (continueLoading) {
            loadingPeriodHolder.continueLoading(this.rendererPositionUs);
        }
    }

    private void updatePlayingPeriodRenderers(@Nullable MediaPeriodHolder oldPlayingPeriodHolder) throws ExoPlaybackException {
        MediaPeriodHolder newPlayingPeriodHolder = this.queue.getPlayingPeriod();
        if (newPlayingPeriodHolder != null && oldPlayingPeriodHolder != newPlayingPeriodHolder) {
            int enabledRendererCount = 0;
            boolean[] rendererWasEnabledFlags = new boolean[this.renderers.length];
            int i = 0;
            while (true) {
                Renderer[] rendererArr = this.renderers;
                if (i < rendererArr.length) {
                    Renderer renderer = rendererArr[i];
                    rendererWasEnabledFlags[i] = renderer.getState() != 0;
                    if (newPlayingPeriodHolder.getTrackSelectorResult().isRendererEnabled(i)) {
                        enabledRendererCount++;
                    }
                    if (rendererWasEnabledFlags[i] && (!newPlayingPeriodHolder.getTrackSelectorResult().isRendererEnabled(i) || (renderer.isCurrentStreamFinal() && renderer.getStream() == oldPlayingPeriodHolder.sampleStreams[i]))) {
                        disableRenderer(renderer);
                    }
                    i++;
                } else {
                    this.playbackInfo = this.playbackInfo.copyWithTrackInfo(newPlayingPeriodHolder.getTrackGroups(), newPlayingPeriodHolder.getTrackSelectorResult());
                    enableRenderers(rendererWasEnabledFlags, enabledRendererCount);
                    return;
                }
            }
        }
    }

    private void enableRenderers(boolean[] rendererWasEnabledFlags, int totalEnabledRendererCount) throws ExoPlaybackException {
        this.enabledRenderers = new Renderer[totalEnabledRendererCount];
        int enabledRendererCount = 0;
        TrackSelectorResult trackSelectorResult = this.queue.getPlayingPeriod().getTrackSelectorResult();
        for (int i = 0; i < this.renderers.length; i++) {
            if (!trackSelectorResult.isRendererEnabled(i)) {
                this.renderers[i].reset();
            }
        }
        for (int i2 = 0; i2 < this.renderers.length; i2++) {
            if (trackSelectorResult.isRendererEnabled(i2)) {
                enableRenderer(i2, rendererWasEnabledFlags[i2], enabledRendererCount);
                enabledRendererCount++;
            }
        }
    }

    private void enableRenderer(int rendererIndex, boolean wasRendererEnabled, int enabledRendererIndex) throws ExoPlaybackException {
        int i = rendererIndex;
        MediaPeriodHolder playingPeriodHolder = this.queue.getPlayingPeriod();
        Renderer renderer = this.renderers[i];
        this.enabledRenderers[enabledRendererIndex] = renderer;
        if (renderer.getState() == 0) {
            TrackSelectorResult trackSelectorResult = playingPeriodHolder.getTrackSelectorResult();
            RendererConfiguration rendererConfiguration = trackSelectorResult.rendererConfigurations[i];
            Format[] formats = getFormats(trackSelectorResult.selections.get(i));
            boolean playing = this.playWhenReady && this.playbackInfo.playbackState == 3;
            renderer.enable(rendererConfiguration, formats, playingPeriodHolder.sampleStreams[i], this.rendererPositionUs, !wasRendererEnabled && playing, playingPeriodHolder.getRendererOffset());
            this.mediaClock.onRendererEnabled(renderer);
            if (playing) {
                renderer.start();
            }
        }
    }

    private boolean rendererWaitingForNextStream(Renderer renderer) {
        MediaPeriodHolder nextPeriodHolder = this.queue.getReadingPeriod().getNext();
        return nextPeriodHolder != null && nextPeriodHolder.prepared && renderer.hasReadStreamToEnd();
    }

    private void handleLoadingMediaPeriodChanged(boolean loadingTrackSelectionChanged) {
        long j;
        MediaPeriodHolder loadingMediaPeriodHolder = this.queue.getLoadingPeriod();
        MediaSource.MediaPeriodId loadingMediaPeriodId = loadingMediaPeriodHolder == null ? this.playbackInfo.periodId : loadingMediaPeriodHolder.info.f73id;
        boolean loadingMediaPeriodChanged = !this.playbackInfo.loadingMediaPeriodId.equals(loadingMediaPeriodId);
        if (loadingMediaPeriodChanged) {
            this.playbackInfo = this.playbackInfo.copyWithLoadingMediaPeriodId(loadingMediaPeriodId);
        }
        PlaybackInfo playbackInfo2 = this.playbackInfo;
        if (loadingMediaPeriodHolder == null) {
            j = playbackInfo2.positionUs;
        } else {
            j = loadingMediaPeriodHolder.getBufferedPositionUs();
        }
        playbackInfo2.bufferedPositionUs = j;
        this.playbackInfo.totalBufferedDurationUs = getTotalBufferedDurationUs();
        if ((loadingMediaPeriodChanged || loadingTrackSelectionChanged) && loadingMediaPeriodHolder != null && loadingMediaPeriodHolder.prepared) {
            updateLoadControlTrackSelection(loadingMediaPeriodHolder.getTrackGroups(), loadingMediaPeriodHolder.getTrackSelectorResult());
        }
    }

    private long getTotalBufferedDurationUs() {
        return getTotalBufferedDurationUs(this.playbackInfo.bufferedPositionUs);
    }

    private long getTotalBufferedDurationUs(long bufferedPositionInLoadingPeriodUs) {
        MediaPeriodHolder loadingPeriodHolder = this.queue.getLoadingPeriod();
        if (loadingPeriodHolder == null) {
            return 0;
        }
        return bufferedPositionInLoadingPeriodUs - loadingPeriodHolder.toPeriodTime(this.rendererPositionUs);
    }

    private void updateLoadControlTrackSelection(TrackGroupArray trackGroups, TrackSelectorResult trackSelectorResult) {
        this.loadControl.onTracksSelected(this.renderers, trackGroups, trackSelectorResult.selections);
    }

    private static Format[] getFormats(TrackSelection newSelection) {
        int length = newSelection != null ? newSelection.length() : 0;
        Format[] formats = new Format[length];
        for (int i = 0; i < length; i++) {
            formats[i] = newSelection.getFormat(i);
        }
        return formats;
    }

    private static final class SeekPosition {
        public final Timeline timeline;
        public final int windowIndex;
        public final long windowPositionUs;

        public SeekPosition(Timeline timeline2, int windowIndex2, long windowPositionUs2) {
            this.timeline = timeline2;
            this.windowIndex = windowIndex2;
            this.windowPositionUs = windowPositionUs2;
        }
    }

    private static final class PendingMessageInfo implements Comparable<PendingMessageInfo> {
        public final PlayerMessage message;
        public int resolvedPeriodIndex;
        public long resolvedPeriodTimeUs;
        @Nullable
        public Object resolvedPeriodUid;

        public PendingMessageInfo(PlayerMessage message2) {
            this.message = message2;
        }

        public void setResolvedPosition(int periodIndex, long periodTimeUs, Object periodUid) {
            this.resolvedPeriodIndex = periodIndex;
            this.resolvedPeriodTimeUs = periodTimeUs;
            this.resolvedPeriodUid = periodUid;
        }

        public int compareTo(@NonNull PendingMessageInfo other) {
            if ((this.resolvedPeriodUid == null) != (other.resolvedPeriodUid == null)) {
                if (this.resolvedPeriodUid != null) {
                    return -1;
                }
                return 1;
            } else if (this.resolvedPeriodUid == null) {
                return 0;
            } else {
                int comparePeriodIndex = this.resolvedPeriodIndex - other.resolvedPeriodIndex;
                if (comparePeriodIndex != 0) {
                    return comparePeriodIndex;
                }
                return Util.compareLong(this.resolvedPeriodTimeUs, other.resolvedPeriodTimeUs);
            }
        }
    }

    private static final class MediaSourceRefreshInfo {
        public final Object manifest;
        public final MediaSource source;
        public final Timeline timeline;

        public MediaSourceRefreshInfo(MediaSource source2, Timeline timeline2, Object manifest2) {
            this.source = source2;
            this.timeline = timeline2;
            this.manifest = manifest2;
        }
    }

    private static final class PlaybackInfoUpdate {
        /* access modifiers changed from: private */
        public int discontinuityReason;
        private PlaybackInfo lastPlaybackInfo;
        /* access modifiers changed from: private */
        public int operationAcks;
        /* access modifiers changed from: private */
        public boolean positionDiscontinuity;

        private PlaybackInfoUpdate() {
        }

        public boolean hasPendingUpdate(PlaybackInfo playbackInfo) {
            return playbackInfo != this.lastPlaybackInfo || this.operationAcks > 0 || this.positionDiscontinuity;
        }

        public void reset(PlaybackInfo playbackInfo) {
            this.lastPlaybackInfo = playbackInfo;
            this.operationAcks = 0;
            this.positionDiscontinuity = false;
        }

        public void incrementPendingOperationAcks(int operationAcks2) {
            this.operationAcks += operationAcks2;
        }

        public void setPositionDiscontinuity(int discontinuityReason2) {
            boolean z = true;
            if (!this.positionDiscontinuity || this.discontinuityReason == 4) {
                this.positionDiscontinuity = true;
                this.discontinuityReason = discontinuityReason2;
                return;
            }
            if (discontinuityReason2 != 4) {
                z = false;
            }
            Assertions.checkArgument(z);
        }
    }
}
