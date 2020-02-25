package com.google.android.exoplayer2;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaClock;

import java.io.IOException;

public abstract class NoSampleRenderer implements Renderer, RendererCapabilities {
    private RendererConfiguration configuration;
    private int index;
    private int state;
    private SampleStream stream;
    private boolean streamIsFinal;

    public void setOperatingRate(float f) throws ExoPlaybackException {
        Renderer$$CC.setOperatingRate$$dflt$$(this, f);
    }

    public final int getTrackType() {
        return 6;
    }

    public final RendererCapabilities getCapabilities() {
        return this;
    }

    public MediaClock getMediaClock() {
        return null;
    }

    public final int getState() {
        return this.state;
    }

    public final void enable(RendererConfiguration configuration2, Format[] formats, SampleStream stream2, long positionUs, boolean joining, long offsetUs) throws ExoPlaybackException {
        Assertions.checkState(this.state == 0);
        this.configuration = configuration2;
        this.state = 1;
        onEnabled(joining);
        replaceStream(formats, stream2, offsetUs);
        onPositionReset(positionUs, joining);
    }

    public final void start() throws ExoPlaybackException {
        boolean z = true;
        if (this.state != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.state = 2;
        onStarted();
    }

    public final void replaceStream(Format[] formats, SampleStream stream2, long offsetUs) throws ExoPlaybackException {
        Assertions.checkState(!this.streamIsFinal);
        this.stream = stream2;
        onRendererOffsetChanged(offsetUs);
    }

    public final SampleStream getStream() {
        return this.stream;
    }

    public final boolean hasReadStreamToEnd() {
        return true;
    }

    public long getReadingPositionUs() {
        return Long.MIN_VALUE;
    }

    public final void setCurrentStreamFinal() {
        this.streamIsFinal = true;
    }

    public final boolean isCurrentStreamFinal() {
        return this.streamIsFinal;
    }

    public final void maybeThrowStreamError() throws IOException {
    }

    public final void resetPosition(long positionUs) throws ExoPlaybackException {
        this.streamIsFinal = false;
        onPositionReset(positionUs, false);
    }

    public final void stop() throws ExoPlaybackException {
        Assertions.checkState(this.state == 2);
        this.state = 1;
        onStopped();
    }

    public final void disable() {
        boolean z = true;
        if (this.state != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.state = 0;
        this.stream = null;
        this.streamIsFinal = false;
        onDisabled();
    }

    public final void reset() {
        Assertions.checkState(this.state == 0);
        onReset();
    }

    public boolean isReady() {
        return true;
    }

    public boolean isEnded() {
        return true;
    }

    public int supportsFormat(Format format) throws ExoPlaybackException {
        return 0;
    }

    public int supportsMixedMimeTypeAdaptation() throws ExoPlaybackException {
        return 0;
    }

    public void handleMessage(int what, @Nullable Object object) throws ExoPlaybackException {
    }

    /* access modifiers changed from: protected */
    public void onEnabled(boolean joining) throws ExoPlaybackException {
    }

    /* access modifiers changed from: protected */
    public void onRendererOffsetChanged(long offsetUs) throws ExoPlaybackException {
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
    }

    /* access modifiers changed from: protected */
    public void onStarted() throws ExoPlaybackException {
    }

    /* access modifiers changed from: protected */
    public void onStopped() throws ExoPlaybackException {
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
    }

    /* access modifiers changed from: protected */
    public void onReset() {
    }

    /* access modifiers changed from: protected */
    public final RendererConfiguration getConfiguration() {
        return this.configuration;
    }

    /* access modifiers changed from: protected */
    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index2) {
        this.index = index2;
    }
}
