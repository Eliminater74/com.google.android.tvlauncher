package com.google.android.exoplayer2.source;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;

import java.util.HashMap;
import java.util.Map;

public final class LoopingMediaSource extends CompositeMediaSource<Void> {
    private final Map<MediaSource.MediaPeriodId, MediaSource.MediaPeriodId> childMediaPeriodIdToMediaPeriodId;
    private final MediaSource childSource;
    private final int loopCount;
    private final Map<MediaPeriod, MediaSource.MediaPeriodId> mediaPeriodToChildMediaPeriodId;

    public LoopingMediaSource(MediaSource childSource2) {
        this(childSource2, Integer.MAX_VALUE);
    }

    public LoopingMediaSource(MediaSource childSource2, int loopCount2) {
        Assertions.checkArgument(loopCount2 > 0);
        this.childSource = childSource2;
        this.loopCount = loopCount2;
        this.childMediaPeriodIdToMediaPeriodId = new HashMap();
        this.mediaPeriodToChildMediaPeriodId = new HashMap();
    }

    @Nullable
    public Object getTag() {
        return this.childSource.getTag();
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener) {
        super.prepareSourceInternal(mediaTransferListener);
        prepareChildSource(null, this.childSource);
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        if (this.loopCount == Integer.MAX_VALUE) {
            return this.childSource.createPeriod(id, allocator, startPositionUs);
        }
        MediaSource.MediaPeriodId childMediaPeriodId = id.copyWithPeriodUid(LoopingTimeline.getChildPeriodUidFromConcatenatedUid(id.periodUid));
        this.childMediaPeriodIdToMediaPeriodId.put(childMediaPeriodId, id);
        MediaPeriod mediaPeriod = this.childSource.createPeriod(childMediaPeriodId, allocator, startPositionUs);
        this.mediaPeriodToChildMediaPeriodId.put(mediaPeriod, childMediaPeriodId);
        return mediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.childSource.releasePeriod(mediaPeriod);
        MediaSource.MediaPeriodId childMediaPeriodId = this.mediaPeriodToChildMediaPeriodId.remove(mediaPeriod);
        if (childMediaPeriodId != null) {
            this.childMediaPeriodIdToMediaPeriodId.remove(childMediaPeriodId);
        }
    }

    /* access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(Void id, MediaSource mediaSource, Timeline timeline, @Nullable Object manifest) {
        Timeline loopingTimeline;
        int i = this.loopCount;
        if (i != Integer.MAX_VALUE) {
            loopingTimeline = new LoopingTimeline(timeline, i);
        } else {
            loopingTimeline = new InfinitelyLoopingTimeline(timeline);
        }
        refreshSourceInfo(loopingTimeline, manifest);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(Void id, MediaSource.MediaPeriodId mediaPeriodId) {
        if (this.loopCount != Integer.MAX_VALUE) {
            return this.childMediaPeriodIdToMediaPeriodId.get(mediaPeriodId);
        }
        return mediaPeriodId;
    }

    private static final class LoopingTimeline extends AbstractConcatenatedTimeline {
        private final int childPeriodCount;
        private final Timeline childTimeline;
        private final int childWindowCount;
        private final int loopCount;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LoopingTimeline(Timeline childTimeline2, int loopCount2) {
            super(false, new ShuffleOrder.UnshuffledShuffleOrder(loopCount2));
            boolean z = false;
            this.childTimeline = childTimeline2;
            this.childPeriodCount = childTimeline2.getPeriodCount();
            this.childWindowCount = childTimeline2.getWindowCount();
            this.loopCount = loopCount2;
            int i = this.childPeriodCount;
            if (i > 0) {
                Assertions.checkState(loopCount2 <= Integer.MAX_VALUE / i ? true : z, "LoopingMediaSource contains too many periods");
            }
        }

        public int getWindowCount() {
            return this.childWindowCount * this.loopCount;
        }

        public int getPeriodCount() {
            return this.childPeriodCount * this.loopCount;
        }

        /* access modifiers changed from: protected */
        public int getChildIndexByPeriodIndex(int periodIndex) {
            return periodIndex / this.childPeriodCount;
        }

        /* access modifiers changed from: protected */
        public int getChildIndexByWindowIndex(int windowIndex) {
            return windowIndex / this.childWindowCount;
        }

        /* access modifiers changed from: protected */
        public int getChildIndexByChildUid(Object childUid) {
            if (!(childUid instanceof Integer)) {
                return -1;
            }
            return ((Integer) childUid).intValue();
        }

        /* access modifiers changed from: protected */
        public Timeline getTimelineByChildIndex(int childIndex) {
            return this.childTimeline;
        }

        /* access modifiers changed from: protected */
        public int getFirstPeriodIndexByChildIndex(int childIndex) {
            return this.childPeriodCount * childIndex;
        }

        /* access modifiers changed from: protected */
        public int getFirstWindowIndexByChildIndex(int childIndex) {
            return this.childWindowCount * childIndex;
        }

        /* access modifiers changed from: protected */
        public Object getChildUidByChildIndex(int childIndex) {
            return Integer.valueOf(childIndex);
        }
    }

    private static final class InfinitelyLoopingTimeline extends ForwardingTimeline {
        public InfinitelyLoopingTimeline(Timeline timeline) {
            super(timeline);
        }

        public int getNextWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
            int childNextWindowIndex = this.timeline.getNextWindowIndex(windowIndex, repeatMode, shuffleModeEnabled);
            if (childNextWindowIndex == -1) {
                return getFirstWindowIndex(shuffleModeEnabled);
            }
            return childNextWindowIndex;
        }

        public int getPreviousWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
            int childPreviousWindowIndex = this.timeline.getPreviousWindowIndex(windowIndex, repeatMode, shuffleModeEnabled);
            if (childPreviousWindowIndex == -1) {
                return getLastWindowIndex(shuffleModeEnabled);
            }
            return childPreviousWindowIndex;
        }
    }
}
