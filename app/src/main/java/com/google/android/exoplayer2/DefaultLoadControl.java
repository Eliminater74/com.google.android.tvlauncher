package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public class DefaultLoadControl implements LoadControl {
    public static final int DEFAULT_BACK_BUFFER_DURATION_MS = 0;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS = 5000;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_MS = 2500;
    public static final int DEFAULT_MAX_BUFFER_MS = 50000;
    public static final int DEFAULT_MIN_BUFFER_MS = 15000;
    public static final boolean DEFAULT_PRIORITIZE_TIME_OVER_SIZE_THRESHOLDS = true;
    public static final boolean DEFAULT_RETAIN_BACK_BUFFER_FROM_KEYFRAME = false;
    public static final int DEFAULT_TARGET_BUFFER_BYTES = -1;
    private final DefaultAllocator allocator;
    private final long backBufferDurationUs;
    private final long bufferForPlaybackAfterRebufferUs;
    private final long bufferForPlaybackUs;
    private boolean isBuffering;
    private final long maxBufferUs;
    private final long minBufferUs;
    private final boolean prioritizeTimeOverSizeThresholds;
    private final boolean retainBackBufferFromKeyframe;
    private final int targetBufferBytesOverwrite;
    private int targetBufferSize;

    public static final class Builder {
        private DefaultAllocator allocator;
        private int backBufferDurationMs = 0;
        private int bufferForPlaybackAfterRebufferMs = 5000;
        private int bufferForPlaybackMs = 2500;
        private boolean createDefaultLoadControlCalled;
        private int maxBufferMs = 50000;
        private int minBufferMs = 15000;
        private boolean prioritizeTimeOverSizeThresholds = true;
        private boolean retainBackBufferFromKeyframe = false;
        private int targetBufferBytes = -1;

        public Builder setAllocator(DefaultAllocator allocator2) {
            Assertions.checkState(!this.createDefaultLoadControlCalled);
            this.allocator = allocator2;
            return this;
        }

        public Builder setBufferDurationsMs(int minBufferMs2, int maxBufferMs2, int bufferForPlaybackMs2, int bufferForPlaybackAfterRebufferMs2) {
            Assertions.checkState(!this.createDefaultLoadControlCalled);
            this.minBufferMs = minBufferMs2;
            this.maxBufferMs = maxBufferMs2;
            this.bufferForPlaybackMs = bufferForPlaybackMs2;
            this.bufferForPlaybackAfterRebufferMs = bufferForPlaybackAfterRebufferMs2;
            return this;
        }

        public Builder setTargetBufferBytes(int targetBufferBytes2) {
            Assertions.checkState(!this.createDefaultLoadControlCalled);
            this.targetBufferBytes = targetBufferBytes2;
            return this;
        }

        public Builder setPrioritizeTimeOverSizeThresholds(boolean prioritizeTimeOverSizeThresholds2) {
            Assertions.checkState(!this.createDefaultLoadControlCalled);
            this.prioritizeTimeOverSizeThresholds = prioritizeTimeOverSizeThresholds2;
            return this;
        }

        public Builder setBackBuffer(int backBufferDurationMs2, boolean retainBackBufferFromKeyframe2) {
            Assertions.checkState(!this.createDefaultLoadControlCalled);
            this.backBufferDurationMs = backBufferDurationMs2;
            this.retainBackBufferFromKeyframe = retainBackBufferFromKeyframe2;
            return this;
        }

        public DefaultLoadControl createDefaultLoadControl() {
            Assertions.checkState(!this.createDefaultLoadControlCalled);
            this.createDefaultLoadControlCalled = true;
            if (this.allocator == null) {
                this.allocator = new DefaultAllocator(true, 65536);
            }
            return new DefaultLoadControl(this.allocator, this.minBufferMs, this.maxBufferMs, this.bufferForPlaybackMs, this.bufferForPlaybackAfterRebufferMs, this.targetBufferBytes, this.prioritizeTimeOverSizeThresholds, this.backBufferDurationMs, this.retainBackBufferFromKeyframe);
        }
    }

    public DefaultLoadControl() {
        this(new DefaultAllocator(true, 65536));
    }

    @Deprecated
    public DefaultLoadControl(DefaultAllocator allocator2) {
        this(allocator2, 15000, 50000, 2500, 5000, -1, true);
    }

    @Deprecated
    public DefaultLoadControl(DefaultAllocator allocator2, int minBufferMs, int maxBufferMs, int bufferForPlaybackMs, int bufferForPlaybackAfterRebufferMs, int targetBufferBytes, boolean prioritizeTimeOverSizeThresholds2) {
        this(allocator2, minBufferMs, maxBufferMs, bufferForPlaybackMs, bufferForPlaybackAfterRebufferMs, targetBufferBytes, prioritizeTimeOverSizeThresholds2, 0, false);
    }

    protected DefaultLoadControl(DefaultAllocator allocator2, int minBufferMs, int maxBufferMs, int bufferForPlaybackMs, int bufferForPlaybackAfterRebufferMs, int targetBufferBytes, boolean prioritizeTimeOverSizeThresholds2, int backBufferDurationMs, boolean retainBackBufferFromKeyframe2) {
        assertGreaterOrEqual(bufferForPlaybackMs, 0, "bufferForPlaybackMs", "0");
        assertGreaterOrEqual(bufferForPlaybackAfterRebufferMs, 0, "bufferForPlaybackAfterRebufferMs", "0");
        assertGreaterOrEqual(minBufferMs, bufferForPlaybackMs, "minBufferMs", "bufferForPlaybackMs");
        assertGreaterOrEqual(minBufferMs, bufferForPlaybackAfterRebufferMs, "minBufferMs", "bufferForPlaybackAfterRebufferMs");
        assertGreaterOrEqual(maxBufferMs, minBufferMs, "maxBufferMs", "minBufferMs");
        assertGreaterOrEqual(backBufferDurationMs, 0, "backBufferDurationMs", "0");
        this.allocator = allocator2;
        this.minBufferUs = C0841C.msToUs((long) minBufferMs);
        this.maxBufferUs = C0841C.msToUs((long) maxBufferMs);
        this.bufferForPlaybackUs = C0841C.msToUs((long) bufferForPlaybackMs);
        this.bufferForPlaybackAfterRebufferUs = C0841C.msToUs((long) bufferForPlaybackAfterRebufferMs);
        this.targetBufferBytesOverwrite = targetBufferBytes;
        this.prioritizeTimeOverSizeThresholds = prioritizeTimeOverSizeThresholds2;
        this.backBufferDurationUs = C0841C.msToUs((long) backBufferDurationMs);
        this.retainBackBufferFromKeyframe = retainBackBufferFromKeyframe2;
    }

    public void onPrepared() {
        reset(false);
    }

    public void onTracksSelected(Renderer[] renderers, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        int i = this.targetBufferBytesOverwrite;
        if (i == -1) {
            i = calculateTargetBufferSize(renderers, trackSelections);
        }
        this.targetBufferSize = i;
        this.allocator.setTargetBufferSize(this.targetBufferSize);
    }

    public void onStopped() {
        reset(true);
    }

    public void onReleased() {
        reset(true);
    }

    public Allocator getAllocator() {
        return this.allocator;
    }

    public long getBackBufferDurationUs() {
        return this.backBufferDurationUs;
    }

    public boolean retainBackBufferFromKeyframe() {
        return this.retainBackBufferFromKeyframe;
    }

    public boolean shouldContinueLoading(long bufferedDurationUs, float playbackSpeed) {
        boolean z = true;
        boolean targetBufferSizeReached = this.allocator.getTotalBytesAllocated() >= this.targetBufferSize;
        long minBufferUs2 = this.minBufferUs;
        if (playbackSpeed > 1.0f) {
            minBufferUs2 = Math.min(Util.getMediaDurationForPlayoutDuration(minBufferUs2, playbackSpeed), this.maxBufferUs);
        }
        if (bufferedDurationUs < minBufferUs2) {
            if (!this.prioritizeTimeOverSizeThresholds && targetBufferSizeReached) {
                z = false;
            }
            this.isBuffering = z;
        } else if (bufferedDurationUs >= this.maxBufferUs || targetBufferSizeReached) {
            this.isBuffering = false;
        }
        return this.isBuffering;
    }

    public boolean shouldStartPlayback(long bufferedDurationUs, float playbackSpeed, boolean rebuffering) {
        long bufferedDurationUs2 = Util.getPlayoutDurationForMediaDuration(bufferedDurationUs, playbackSpeed);
        long minBufferDurationUs = rebuffering ? this.bufferForPlaybackAfterRebufferUs : this.bufferForPlaybackUs;
        return minBufferDurationUs <= 0 || bufferedDurationUs2 >= minBufferDurationUs || (!this.prioritizeTimeOverSizeThresholds && this.allocator.getTotalBytesAllocated() >= this.targetBufferSize);
    }

    /* access modifiers changed from: protected */
    public int calculateTargetBufferSize(Renderer[] renderers, TrackSelectionArray trackSelectionArray) {
        int targetBufferSize2 = 0;
        for (int i = 0; i < renderers.length; i++) {
            if (trackSelectionArray.get(i) != null) {
                targetBufferSize2 += Util.getDefaultBufferSize(renderers[i].getTrackType());
            }
        }
        return targetBufferSize2;
    }

    private void reset(boolean resetAllocator) {
        this.targetBufferSize = 0;
        this.isBuffering = false;
        if (resetAllocator) {
            this.allocator.reset();
        }
    }

    private static void assertGreaterOrEqual(int value1, int value2, String name1, String name2) {
        boolean z = value1 >= value2;
        StringBuilder sb = new StringBuilder(String.valueOf(name1).length() + 21 + String.valueOf(name2).length());
        sb.append(name1);
        sb.append(" cannot be less than ");
        sb.append(name2);
        Assertions.checkArgument(z, sb.toString());
    }
}
