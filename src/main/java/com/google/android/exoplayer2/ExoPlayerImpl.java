package com.google.android.exoplayer2;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.BasePlayer;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class ExoPlayerImpl extends BasePlayer implements ExoPlayer {
    private static final String TAG = "ExoPlayerImpl";
    final TrackSelectorResult emptyTrackSelectorResult;
    private final Handler eventHandler;
    private boolean foregroundMode;
    private boolean hasPendingPrepare;
    private boolean hasPendingSeek;
    private boolean internalPlayWhenReady;
    private final ExoPlayerImplInternal internalPlayer;
    private final Handler internalPlayerHandler;
    private final CopyOnWriteArrayList<BasePlayer.ListenerHolder> listeners;
    private int maskingPeriodIndex;
    private int maskingWindowIndex;
    private long maskingWindowPositionMs;
    private MediaSource mediaSource;
    private final ArrayDeque<Runnable> pendingListenerNotifications;
    private int pendingOperationAcks;
    private final Timeline.Period period;
    private boolean playWhenReady;
    @Nullable
    private ExoPlaybackException playbackError;
    private PlaybackInfo playbackInfo;
    private PlaybackParameters playbackParameters;
    private final Renderer[] renderers;
    private int repeatMode;
    private SeekParameters seekParameters;
    private boolean shuffleModeEnabled;
    private final TrackSelector trackSelector;

    @SuppressLint({"HandlerLeak"})
    public ExoPlayerImpl(Renderer[] renderers2, TrackSelector trackSelector2, LoadControl loadControl, BandwidthMeter bandwidthMeter, Clock clock, Looper looper) {
        Renderer[] rendererArr = renderers2;
        String hexString = Integer.toHexString(System.identityHashCode(this));
        String str = Util.DEVICE_DEBUG_INFO;
        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 30 + String.valueOf(str).length());
        sb.append("Init ");
        sb.append(hexString);
        sb.append(" [");
        sb.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        sb.append("] [");
        sb.append(str);
        sb.append("]");
        Log.m28i(TAG, sb.toString());
        Assertions.checkState(rendererArr.length > 0);
        this.renderers = (Renderer[]) Assertions.checkNotNull(renderers2);
        this.trackSelector = (TrackSelector) Assertions.checkNotNull(trackSelector2);
        this.playWhenReady = false;
        this.repeatMode = 0;
        this.shuffleModeEnabled = false;
        this.listeners = new CopyOnWriteArrayList<>();
        this.emptyTrackSelectorResult = new TrackSelectorResult(new RendererConfiguration[rendererArr.length], new TrackSelection[rendererArr.length], null);
        this.period = new Timeline.Period();
        this.playbackParameters = PlaybackParameters.DEFAULT;
        this.seekParameters = SeekParameters.DEFAULT;
        this.eventHandler = new Handler(looper) {
            public void handleMessage(Message msg) {
                ExoPlayerImpl.this.handleEvent(msg);
            }
        };
        this.playbackInfo = PlaybackInfo.createDummy(0, this.emptyTrackSelectorResult);
        this.pendingListenerNotifications = new ArrayDeque<>();
        this.internalPlayer = new ExoPlayerImplInternal(renderers2, trackSelector2, this.emptyTrackSelectorResult, loadControl, bandwidthMeter, this.playWhenReady, this.repeatMode, this.shuffleModeEnabled, this.eventHandler, clock);
        this.internalPlayerHandler = new Handler(this.internalPlayer.getPlaybackLooper());
    }

    @Nullable
    public Player.AudioComponent getAudioComponent() {
        return null;
    }

    @Nullable
    public Player.VideoComponent getVideoComponent() {
        return null;
    }

    @Nullable
    public Player.TextComponent getTextComponent() {
        return null;
    }

    @Nullable
    public Player.MetadataComponent getMetadataComponent() {
        return null;
    }

    public Looper getPlaybackLooper() {
        return this.internalPlayer.getPlaybackLooper();
    }

    public Looper getApplicationLooper() {
        return this.eventHandler.getLooper();
    }

    public void addListener(Player.EventListener listener) {
        this.listeners.addIfAbsent(new BasePlayer.ListenerHolder(listener));
    }

    public void removeListener(Player.EventListener listener) {
        Iterator<BasePlayer.ListenerHolder> it = this.listeners.iterator();
        while (it.hasNext()) {
            BasePlayer.ListenerHolder listenerHolder = it.next();
            if (listenerHolder.listener.equals(listener)) {
                listenerHolder.release();
                this.listeners.remove(listenerHolder);
            }
        }
    }

    public int getPlaybackState() {
        return this.playbackInfo.playbackState;
    }

    @Nullable
    public ExoPlaybackException getPlaybackError() {
        return this.playbackError;
    }

    public void retry() {
        if (this.mediaSource == null) {
            return;
        }
        if (this.playbackError != null || this.playbackInfo.playbackState == 1) {
            prepare(this.mediaSource, false, false);
        }
    }

    public void prepare(MediaSource mediaSource2) {
        prepare(mediaSource2, true, true);
    }

    public void prepare(MediaSource mediaSource2, boolean resetPosition, boolean resetState) {
        this.playbackError = null;
        this.mediaSource = mediaSource2;
        PlaybackInfo playbackInfo2 = getResetPlaybackInfo(resetPosition, resetState, 2);
        this.hasPendingPrepare = true;
        this.pendingOperationAcks++;
        this.internalPlayer.prepare(mediaSource2, resetPosition, resetState);
        updatePlaybackInfo(playbackInfo2, false, 4, 1, false);
    }

    public void setPlayWhenReady(boolean playWhenReady2) {
        setPlayWhenReady(playWhenReady2, false);
    }

    public void setPlayWhenReady(boolean playWhenReady2, boolean suppressPlayback) {
        boolean internalPlayWhenReady2 = playWhenReady2 && !suppressPlayback;
        if (this.internalPlayWhenReady != internalPlayWhenReady2) {
            this.internalPlayWhenReady = internalPlayWhenReady2;
            this.internalPlayer.setPlayWhenReady(internalPlayWhenReady2);
        }
        if (this.playWhenReady != playWhenReady2) {
            this.playWhenReady = playWhenReady2;
            notifyListeners(new ExoPlayerImpl$$Lambda$0(playWhenReady2, this.playbackInfo.playbackState));
        }
    }

    public boolean getPlayWhenReady() {
        return this.playWhenReady;
    }

    public void setRepeatMode(int repeatMode2) {
        if (this.repeatMode != repeatMode2) {
            this.repeatMode = repeatMode2;
            this.internalPlayer.setRepeatMode(repeatMode2);
            notifyListeners(new ExoPlayerImpl$$Lambda$1(repeatMode2));
        }
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    public void setShuffleModeEnabled(boolean shuffleModeEnabled2) {
        if (this.shuffleModeEnabled != shuffleModeEnabled2) {
            this.shuffleModeEnabled = shuffleModeEnabled2;
            this.internalPlayer.setShuffleModeEnabled(shuffleModeEnabled2);
            notifyListeners(new ExoPlayerImpl$$Lambda$2(shuffleModeEnabled2));
        }
    }

    public boolean getShuffleModeEnabled() {
        return this.shuffleModeEnabled;
    }

    public boolean isLoading() {
        return this.playbackInfo.isLoading;
    }

    public void seekTo(int windowIndex, long positionMs) {
        Timeline timeline = this.playbackInfo.timeline;
        if (windowIndex < 0 || (!timeline.isEmpty() && windowIndex >= timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(timeline, windowIndex, positionMs);
        }
        this.hasPendingSeek = true;
        this.pendingOperationAcks++;
        if (isPlayingAd()) {
            Log.m30w(TAG, "seekTo ignored because an ad is playing");
            this.eventHandler.obtainMessage(0, 1, -1, this.playbackInfo).sendToTarget();
            return;
        }
        this.maskingWindowIndex = windowIndex;
        if (timeline.isEmpty()) {
            this.maskingWindowPositionMs = positionMs == C0841C.TIME_UNSET ? 0 : positionMs;
            this.maskingPeriodIndex = 0;
        } else {
            long windowPositionUs = positionMs == C0841C.TIME_UNSET ? timeline.getWindow(windowIndex, this.window).getDefaultPositionUs() : C0841C.msToUs(positionMs);
            Pair<Object, Long> periodUidAndPosition = timeline.getPeriodPosition(this.window, this.period, windowIndex, windowPositionUs);
            this.maskingWindowPositionMs = C0841C.usToMs(windowPositionUs);
            this.maskingPeriodIndex = timeline.getIndexOfPeriod(periodUidAndPosition.first);
        }
        this.internalPlayer.seekTo(timeline, windowIndex, C0841C.msToUs(positionMs));
        notifyListeners(ExoPlayerImpl$$Lambda$3.$instance);
    }

    public void setPlaybackParameters(@Nullable PlaybackParameters playbackParameters2) {
        if (playbackParameters2 == null) {
            playbackParameters2 = PlaybackParameters.DEFAULT;
        }
        this.internalPlayer.setPlaybackParameters(playbackParameters2);
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.playbackParameters;
    }

    public void setSeekParameters(@Nullable SeekParameters seekParameters2) {
        if (seekParameters2 == null) {
            seekParameters2 = SeekParameters.DEFAULT;
        }
        if (!this.seekParameters.equals(seekParameters2)) {
            this.seekParameters = seekParameters2;
            this.internalPlayer.setSeekParameters(seekParameters2);
        }
    }

    public SeekParameters getSeekParameters() {
        return this.seekParameters;
    }

    public void setForegroundMode(boolean foregroundMode2) {
        if (this.foregroundMode != foregroundMode2) {
            this.foregroundMode = foregroundMode2;
            this.internalPlayer.setForegroundMode(foregroundMode2);
        }
    }

    public void stop(boolean reset) {
        if (reset) {
            this.playbackError = null;
            this.mediaSource = null;
        }
        PlaybackInfo playbackInfo2 = getResetPlaybackInfo(reset, reset, 1);
        this.pendingOperationAcks++;
        this.internalPlayer.stop(reset);
        updatePlaybackInfo(playbackInfo2, false, 4, 1, false);
    }

    public void release() {
        String hexString = Integer.toHexString(System.identityHashCode(this));
        String str = Util.DEVICE_DEBUG_INFO;
        String registeredModules = ExoPlayerLibraryInfo.registeredModules();
        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 36 + String.valueOf(str).length() + String.valueOf(registeredModules).length());
        sb.append("Release ");
        sb.append(hexString);
        sb.append(" [");
        sb.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        sb.append("] [");
        sb.append(str);
        sb.append("] [");
        sb.append(registeredModules);
        sb.append("]");
        Log.m28i(TAG, sb.toString());
        this.mediaSource = null;
        this.internalPlayer.release();
        this.eventHandler.removeCallbacksAndMessages(null);
        this.playbackInfo = getResetPlaybackInfo(false, false, 1);
    }

    @Deprecated
    public void sendMessages(ExoPlayer.ExoPlayerMessage... messages) {
        for (ExoPlayer.ExoPlayerMessage message : messages) {
            createMessage(message.target).setType(message.messageType).setPayload(message.message).send();
        }
    }

    public PlayerMessage createMessage(PlayerMessage.Target target) {
        return new PlayerMessage(this.internalPlayer, target, this.playbackInfo.timeline, getCurrentWindowIndex(), this.internalPlayerHandler);
    }

    @Deprecated
    public void blockingSendMessages(ExoPlayer.ExoPlayerMessage... messages) {
        List<PlayerMessage> playerMessages = new ArrayList<>();
        for (ExoPlayer.ExoPlayerMessage message : messages) {
            playerMessages.add(createMessage(message.target).setType(message.messageType).setPayload(message.message).send());
        }
        boolean wasInterrupted = false;
        for (PlayerMessage message2 : playerMessages) {
            boolean blockMessage = true;
            while (blockMessage) {
                try {
                    message2.blockUntilDelivered();
                    blockMessage = false;
                } catch (InterruptedException e) {
                    wasInterrupted = true;
                }
            }
        }
        if (wasInterrupted) {
            Thread.currentThread().interrupt();
        }
    }

    public int getCurrentPeriodIndex() {
        if (shouldMaskPosition()) {
            return this.maskingPeriodIndex;
        }
        return this.playbackInfo.timeline.getIndexOfPeriod(this.playbackInfo.periodId.periodUid);
    }

    public int getCurrentWindowIndex() {
        if (shouldMaskPosition()) {
            return this.maskingWindowIndex;
        }
        return this.playbackInfo.timeline.getPeriodByUid(this.playbackInfo.periodId.periodUid, this.period).windowIndex;
    }

    public long getDuration() {
        if (!isPlayingAd()) {
            return getContentDuration();
        }
        MediaSource.MediaPeriodId periodId = this.playbackInfo.periodId;
        this.playbackInfo.timeline.getPeriodByUid(periodId.periodUid, this.period);
        return C0841C.usToMs(this.period.getAdDurationUs(periodId.adGroupIndex, periodId.adIndexInAdGroup));
    }

    public long getCurrentPosition() {
        if (shouldMaskPosition()) {
            return this.maskingWindowPositionMs;
        }
        if (this.playbackInfo.periodId.isAd()) {
            return C0841C.usToMs(this.playbackInfo.positionUs);
        }
        return periodPositionUsToWindowPositionMs(this.playbackInfo.periodId, this.playbackInfo.positionUs);
    }

    public long getBufferedPosition() {
        if (!isPlayingAd()) {
            return getContentBufferedPosition();
        }
        if (this.playbackInfo.loadingMediaPeriodId.equals(this.playbackInfo.periodId)) {
            return C0841C.usToMs(this.playbackInfo.bufferedPositionUs);
        }
        return getDuration();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    public long getTotalBufferedDuration() {
        return Math.max(0L, C0841C.usToMs(this.playbackInfo.totalBufferedDurationUs));
    }

    public boolean isPlayingAd() {
        return !shouldMaskPosition() && this.playbackInfo.periodId.isAd();
    }

    public int getCurrentAdGroupIndex() {
        if (isPlayingAd()) {
            return this.playbackInfo.periodId.adGroupIndex;
        }
        return -1;
    }

    public int getCurrentAdIndexInAdGroup() {
        if (isPlayingAd()) {
            return this.playbackInfo.periodId.adIndexInAdGroup;
        }
        return -1;
    }

    public long getContentPosition() {
        if (!isPlayingAd()) {
            return getCurrentPosition();
        }
        this.playbackInfo.timeline.getPeriodByUid(this.playbackInfo.periodId.periodUid, this.period);
        return this.period.getPositionInWindowMs() + C0841C.usToMs(this.playbackInfo.contentPositionUs);
    }

    public long getContentBufferedPosition() {
        if (shouldMaskPosition()) {
            return this.maskingWindowPositionMs;
        }
        if (this.playbackInfo.loadingMediaPeriodId.windowSequenceNumber != this.playbackInfo.periodId.windowSequenceNumber) {
            return this.playbackInfo.timeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
        }
        long contentBufferedPositionUs = this.playbackInfo.bufferedPositionUs;
        if (this.playbackInfo.loadingMediaPeriodId.isAd()) {
            Timeline.Period loadingPeriod = this.playbackInfo.timeline.getPeriodByUid(this.playbackInfo.loadingMediaPeriodId.periodUid, this.period);
            contentBufferedPositionUs = loadingPeriod.getAdGroupTimeUs(this.playbackInfo.loadingMediaPeriodId.adGroupIndex);
            if (contentBufferedPositionUs == Long.MIN_VALUE) {
                contentBufferedPositionUs = loadingPeriod.durationUs;
            }
        }
        return periodPositionUsToWindowPositionMs(this.playbackInfo.loadingMediaPeriodId, contentBufferedPositionUs);
    }

    public int getRendererCount() {
        return this.renderers.length;
    }

    public int getRendererType(int index) {
        return this.renderers[index].getTrackType();
    }

    public TrackGroupArray getCurrentTrackGroups() {
        return this.playbackInfo.trackGroups;
    }

    public TrackSelectionArray getCurrentTrackSelections() {
        return this.playbackInfo.trackSelectorResult.selections;
    }

    public Timeline getCurrentTimeline() {
        return this.playbackInfo.timeline;
    }

    public Object getCurrentManifest() {
        return this.playbackInfo.manifest;
    }

    /* access modifiers changed from: package-private */
    public void handleEvent(Message msg) {
        int i = msg.what;
        boolean z = true;
        if (i == 0) {
            PlaybackInfo playbackInfo2 = (PlaybackInfo) msg.obj;
            int i2 = msg.arg1;
            if (msg.arg2 == -1) {
                z = false;
            }
            handlePlaybackInfo(playbackInfo2, i2, z, msg.arg2);
        } else if (i == 1) {
            PlaybackParameters playbackParameters2 = (PlaybackParameters) msg.obj;
            if (!this.playbackParameters.equals(playbackParameters2)) {
                this.playbackParameters = playbackParameters2;
                notifyListeners(new ExoPlayerImpl$$Lambda$4(playbackParameters2));
            }
        } else if (i == 2) {
            ExoPlaybackException playbackError2 = (ExoPlaybackException) msg.obj;
            this.playbackError = playbackError2;
            notifyListeners(new ExoPlayerImpl$$Lambda$5(playbackError2));
        } else {
            throw new IllegalStateException();
        }
    }

    private void handlePlaybackInfo(PlaybackInfo playbackInfo2, int operationAcks, boolean positionDiscontinuity, int positionDiscontinuityReason) {
        int timelineChangeReason;
        this.pendingOperationAcks -= operationAcks;
        if (this.pendingOperationAcks == 0) {
            if (playbackInfo2.startPositionUs == C0841C.TIME_UNSET) {
                playbackInfo2 = playbackInfo2.resetToNewPosition(playbackInfo2.periodId, 0, playbackInfo2.contentPositionUs);
            }
            if (!this.playbackInfo.timeline.isEmpty() && playbackInfo2.timeline.isEmpty()) {
                this.maskingPeriodIndex = 0;
                this.maskingWindowIndex = 0;
                this.maskingWindowPositionMs = 0;
            }
            if (this.hasPendingPrepare) {
                timelineChangeReason = 0;
            } else {
                timelineChangeReason = 2;
            }
            boolean seekProcessed = this.hasPendingSeek;
            this.hasPendingPrepare = false;
            this.hasPendingSeek = false;
            updatePlaybackInfo(playbackInfo2, positionDiscontinuity, positionDiscontinuityReason, timelineChangeReason, seekProcessed);
        }
    }

    private PlaybackInfo getResetPlaybackInfo(boolean resetPosition, boolean resetState, int playbackState) {
        MediaSource.MediaPeriodId mediaPeriodId;
        long j = 0;
        boolean resetPosition2 = false;
        if (resetPosition) {
            this.maskingWindowIndex = 0;
            this.maskingPeriodIndex = 0;
            this.maskingWindowPositionMs = 0;
        } else {
            this.maskingWindowIndex = getCurrentWindowIndex();
            this.maskingPeriodIndex = getCurrentPeriodIndex();
            this.maskingWindowPositionMs = getCurrentPosition();
        }
        if (resetPosition || resetState) {
            resetPosition2 = true;
        }
        if (resetPosition2) {
            mediaPeriodId = this.playbackInfo.getDummyFirstMediaPeriodId(this.shuffleModeEnabled, this.window);
        } else {
            mediaPeriodId = this.playbackInfo.periodId;
        }
        if (!resetPosition2) {
            j = this.playbackInfo.positionUs;
        }
        long startPositionUs = j;
        return new PlaybackInfo(resetState ? Timeline.EMPTY : this.playbackInfo.timeline, resetState ? null : this.playbackInfo.manifest, mediaPeriodId, startPositionUs, resetPosition2 ? C0841C.TIME_UNSET : this.playbackInfo.contentPositionUs, playbackState, false, resetState ? TrackGroupArray.EMPTY : this.playbackInfo.trackGroups, resetState ? this.emptyTrackSelectorResult : this.playbackInfo.trackSelectorResult, mediaPeriodId, startPositionUs, 0, startPositionUs);
    }

    private void updatePlaybackInfo(PlaybackInfo playbackInfo2, boolean positionDiscontinuity, int positionDiscontinuityReason, int timelineChangeReason, boolean seekProcessed) {
        PlaybackInfo previousPlaybackInfo = this.playbackInfo;
        this.playbackInfo = playbackInfo2;
        notifyListeners(new PlaybackInfoUpdate(playbackInfo2, previousPlaybackInfo, this.listeners, this.trackSelector, positionDiscontinuity, positionDiscontinuityReason, timelineChangeReason, seekProcessed, this.playWhenReady));
    }

    private void notifyListeners(BasePlayer.ListenerInvocation listenerInvocation) {
        notifyListeners(new ExoPlayerImpl$$Lambda$6(new CopyOnWriteArrayList<>(this.listeners), listenerInvocation));
    }

    private void notifyListeners(Runnable listenerNotificationRunnable) {
        boolean isRunningRecursiveListenerNotification = !this.pendingListenerNotifications.isEmpty();
        this.pendingListenerNotifications.addLast(listenerNotificationRunnable);
        if (!isRunningRecursiveListenerNotification) {
            while (!this.pendingListenerNotifications.isEmpty()) {
                this.pendingListenerNotifications.peekFirst().run();
                this.pendingListenerNotifications.removeFirst();
            }
        }
    }

    private long periodPositionUsToWindowPositionMs(MediaSource.MediaPeriodId periodId, long positionUs) {
        long positionMs = C0841C.usToMs(positionUs);
        this.playbackInfo.timeline.getPeriodByUid(periodId.periodUid, this.period);
        return positionMs + this.period.getPositionInWindowMs();
    }

    private boolean shouldMaskPosition() {
        return this.playbackInfo.timeline.isEmpty() || this.pendingOperationAcks > 0;
    }

    private static final class PlaybackInfoUpdate implements Runnable {
        private final boolean isLoadingChanged;
        private final CopyOnWriteArrayList<BasePlayer.ListenerHolder> listenerSnapshot;
        private final boolean playWhenReady;
        private final PlaybackInfo playbackInfo;
        private final boolean playbackStateChanged;
        private final boolean positionDiscontinuity;
        private final int positionDiscontinuityReason;
        private final boolean seekProcessed;
        private final int timelineChangeReason;
        private final boolean timelineOrManifestChanged;
        private final TrackSelector trackSelector;
        private final boolean trackSelectorResultChanged;

        public PlaybackInfoUpdate(PlaybackInfo playbackInfo2, PlaybackInfo previousPlaybackInfo, CopyOnWriteArrayList<BasePlayer.ListenerHolder> listeners, TrackSelector trackSelector2, boolean positionDiscontinuity2, int positionDiscontinuityReason2, int timelineChangeReason2, boolean seekProcessed2, boolean playWhenReady2) {
            this.playbackInfo = playbackInfo2;
            this.listenerSnapshot = new CopyOnWriteArrayList<>(listeners);
            this.trackSelector = trackSelector2;
            this.positionDiscontinuity = positionDiscontinuity2;
            this.positionDiscontinuityReason = positionDiscontinuityReason2;
            this.timelineChangeReason = timelineChangeReason2;
            this.seekProcessed = seekProcessed2;
            this.playWhenReady = playWhenReady2;
            boolean z = true;
            this.playbackStateChanged = previousPlaybackInfo.playbackState != playbackInfo2.playbackState;
            this.timelineOrManifestChanged = (previousPlaybackInfo.timeline == playbackInfo2.timeline && previousPlaybackInfo.manifest == playbackInfo2.manifest) ? false : true;
            this.isLoadingChanged = previousPlaybackInfo.isLoading != playbackInfo2.isLoading;
            this.trackSelectorResultChanged = previousPlaybackInfo.trackSelectorResult == playbackInfo2.trackSelectorResult ? false : z;
        }

        public void run() {
            if (this.timelineOrManifestChanged || this.timelineChangeReason == 0) {
                ExoPlayerImpl.invokeAll(this.listenerSnapshot, new ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$0(this));
            }
            if (this.positionDiscontinuity) {
                ExoPlayerImpl.invokeAll(this.listenerSnapshot, new ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$1(this));
            }
            if (this.trackSelectorResultChanged) {
                this.trackSelector.onSelectionActivated(this.playbackInfo.trackSelectorResult.info);
                ExoPlayerImpl.invokeAll(this.listenerSnapshot, new ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$2(this));
            }
            if (this.isLoadingChanged) {
                ExoPlayerImpl.invokeAll(this.listenerSnapshot, new ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$3(this));
            }
            if (this.playbackStateChanged) {
                ExoPlayerImpl.invokeAll(this.listenerSnapshot, new ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$4(this));
            }
            if (this.seekProcessed) {
                ExoPlayerImpl.invokeAll(this.listenerSnapshot, ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$5.$instance);
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$run$0$ExoPlayerImpl$PlaybackInfoUpdate(Player.EventListener listener) {
            listener.onTimelineChanged(this.playbackInfo.timeline, this.playbackInfo.manifest, this.timelineChangeReason);
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$run$1$ExoPlayerImpl$PlaybackInfoUpdate(Player.EventListener listener) {
            listener.onPositionDiscontinuity(this.positionDiscontinuityReason);
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$run$2$ExoPlayerImpl$PlaybackInfoUpdate(Player.EventListener listener) {
            listener.onTracksChanged(this.playbackInfo.trackGroups, this.playbackInfo.trackSelectorResult.selections);
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$run$3$ExoPlayerImpl$PlaybackInfoUpdate(Player.EventListener listener) {
            listener.onLoadingChanged(this.playbackInfo.isLoading);
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$run$4$ExoPlayerImpl$PlaybackInfoUpdate(Player.EventListener listener) {
            listener.onPlayerStateChanged(this.playWhenReady, this.playbackInfo.playbackState);
        }
    }

    /* access modifiers changed from: private */
    public static void invokeAll(CopyOnWriteArrayList<BasePlayer.ListenerHolder> listeners2, BasePlayer.ListenerInvocation listenerInvocation) {
        Iterator<BasePlayer.ListenerHolder> it = listeners2.iterator();
        while (it.hasNext()) {
            it.next().invoke(listenerInvocation);
        }
    }
}
