package com.google.android.exoplayer2.p008ui;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player$EventListener$$CC;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;

import java.util.Locale;

/* renamed from: com.google.android.exoplayer2.ui.DebugTextViewHelper */
public class DebugTextViewHelper implements Player.EventListener, Runnable {
    private static final int REFRESH_INTERVAL_MS = 1000;
    private final SimpleExoPlayer player;
    private final TextView textView;
    private boolean started;

    public DebugTextViewHelper(SimpleExoPlayer player2, TextView textView2) {
        Assertions.checkArgument(player2.getApplicationLooper() == Looper.getMainLooper());
        this.player = player2;
        this.textView = textView2;
    }

    private static String getDecoderCountersBufferCountString(DecoderCounters counters) {
        if (counters == null) {
            return "";
        }
        counters.ensureUpdated();
        int i = counters.skippedInputBufferCount;
        int i2 = counters.skippedOutputBufferCount;
        int i3 = counters.renderedOutputBufferCount;
        int i4 = counters.droppedBufferCount;
        int i5 = counters.maxConsecutiveDroppedBufferCount;
        int i6 = counters.droppedToKeyframeCount;
        StringBuilder sb = new StringBuilder(93);
        sb.append(" sib:");
        sb.append(i);
        sb.append(" sb:");
        sb.append(i2);
        sb.append(" rb:");
        sb.append(i3);
        sb.append(" db:");
        sb.append(i4);
        sb.append(" mcdb:");
        sb.append(i5);
        sb.append(" dk:");
        sb.append(i6);
        return sb.toString();
    }

    private static String getPixelAspectRatioString(float pixelAspectRatio) {
        if (pixelAspectRatio == -1.0f || pixelAspectRatio == 1.0f) {
            return "";
        }
        String valueOf = String.valueOf(String.format(Locale.US, "%.02f", Float.valueOf(pixelAspectRatio)));
        return valueOf.length() != 0 ? " par:".concat(valueOf) : new String(" par:");
    }

    public void onLoadingChanged(boolean z) {
        Player$EventListener$$CC.onLoadingChanged$$dflt$$(this, z);
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Player$EventListener$$CC.onPlaybackParametersChanged$$dflt$$(this, playbackParameters);
    }

    public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        Player$EventListener$$CC.onPlayerError$$dflt$$(this, exoPlaybackException);
    }

    public void onRepeatModeChanged(int i) {
        Player$EventListener$$CC.onRepeatModeChanged$$dflt$$(this, i);
    }

    public void onSeekProcessed() {
        Player$EventListener$$CC.onSeekProcessed$$dflt$$(this);
    }

    public void onShuffleModeEnabledChanged(boolean z) {
        Player$EventListener$$CC.onShuffleModeEnabledChanged$$dflt$$(this, z);
    }

    public void onTimelineChanged(Timeline timeline, Object obj, int i) {
        Player$EventListener$$CC.onTimelineChanged$$dflt$$(this, timeline, obj, i);
    }

    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        Player$EventListener$$CC.onTracksChanged$$dflt$$(this, trackGroupArray, trackSelectionArray);
    }

    public final void start() {
        if (!this.started) {
            this.started = true;
            this.player.addListener(this);
            updateAndPost();
        }
    }

    public final void stop() {
        if (this.started) {
            this.started = false;
            this.player.removeListener(this);
            this.textView.removeCallbacks(this);
        }
    }

    public final void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        updateAndPost();
    }

    public final void onPositionDiscontinuity(int reason) {
        updateAndPost();
    }

    public final void run() {
        updateAndPost();
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"SetTextI18n"})
    public final void updateAndPost() {
        this.textView.setText(getDebugString());
        this.textView.removeCallbacks(this);
        this.textView.postDelayed(this, 1000);
    }

    /* access modifiers changed from: protected */
    public String getDebugString() {
        String playerStateString = getPlayerStateString();
        String videoString = getVideoString();
        String audioString = getAudioString();
        StringBuilder sb = new StringBuilder(String.valueOf(playerStateString).length() + String.valueOf(videoString).length() + String.valueOf(audioString).length());
        sb.append(playerStateString);
        sb.append(videoString);
        sb.append(audioString);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String getPlayerStateString() {
        String playbackStateString;
        int playbackState = this.player.getPlaybackState();
        if (playbackState == 1) {
            playbackStateString = "idle";
        } else if (playbackState == 2) {
            playbackStateString = "buffering";
        } else if (playbackState == 3) {
            playbackStateString = "ready";
        } else if (playbackState != 4) {
            playbackStateString = "unknown";
        } else {
            playbackStateString = "ended";
        }
        return String.format("playWhenReady:%s playbackState:%s window:%s", Boolean.valueOf(this.player.getPlayWhenReady()), playbackStateString, Integer.valueOf(this.player.getCurrentWindowIndex()));
    }

    /* access modifiers changed from: protected */
    public String getVideoString() {
        Format format = this.player.getVideoFormat();
        DecoderCounters decoderCounters = this.player.getVideoDecoderCounters();
        if (format == null || decoderCounters == null) {
            return "";
        }
        String str = format.sampleMimeType;
        String str2 = format.f72id;
        int i = format.width;
        int i2 = format.height;
        String pixelAspectRatioString = getPixelAspectRatioString(format.pixelWidthHeightRatio);
        String decoderCountersBufferCountString = getDecoderCountersBufferCountString(decoderCounters);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 32 + String.valueOf(str2).length() + String.valueOf(pixelAspectRatioString).length() + String.valueOf(decoderCountersBufferCountString).length());
        sb.append("\n");
        sb.append(str);
        sb.append("(id:");
        sb.append(str2);
        sb.append(" r:");
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        sb.append(pixelAspectRatioString);
        sb.append(decoderCountersBufferCountString);
        sb.append(")");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String getAudioString() {
        Format format = this.player.getAudioFormat();
        DecoderCounters decoderCounters = this.player.getAudioDecoderCounters();
        if (format == null || decoderCounters == null) {
            return "";
        }
        String str = format.sampleMimeType;
        String str2 = format.f72id;
        int i = format.sampleRate;
        int i2 = format.channelCount;
        String decoderCountersBufferCountString = getDecoderCountersBufferCountString(decoderCounters);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 36 + String.valueOf(str2).length() + String.valueOf(decoderCountersBufferCountString).length());
        sb.append("\n");
        sb.append(str);
        sb.append("(id:");
        sb.append(str2);
        sb.append(" hz:");
        sb.append(i);
        sb.append(" ch:");
        sb.append(i2);
        sb.append(decoderCountersBufferCountString);
        sb.append(")");
        return sb.toString();
    }
}
