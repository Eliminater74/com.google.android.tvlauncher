package com.google.android.exoplayer2.source;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class MergingMediaSource extends CompositeMediaSource<Integer> {
    private static final int PERIOD_COUNT_UNSET = -1;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final MediaSource[] mediaSources;
    private IllegalMergeException mergeError;
    private final ArrayList<MediaSource> pendingTimelineSources;
    private int periodCount;
    private Object primaryManifest;
    private final Timeline[] timelines;

    public static final class IllegalMergeException extends IOException {
        public static final int REASON_PERIOD_COUNT_MISMATCH = 0;
        public final int reason;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Reason {
        }

        public IllegalMergeException(int reason2) {
            this.reason = reason2;
        }
    }

    public MergingMediaSource(MediaSource... mediaSources2) {
        this(new DefaultCompositeSequenceableLoaderFactory(), mediaSources2);
    }

    public MergingMediaSource(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, MediaSource... mediaSources2) {
        this.mediaSources = mediaSources2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.pendingTimelineSources = new ArrayList<>(Arrays.asList(mediaSources2));
        this.periodCount = -1;
        this.timelines = new Timeline[mediaSources2.length];
    }

    @Nullable
    public Object getTag() {
        MediaSource[] mediaSourceArr = this.mediaSources;
        if (mediaSourceArr.length > 0) {
            return mediaSourceArr[0].getTag();
        }
        return null;
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener) {
        super.prepareSourceInternal(mediaTransferListener);
        for (int i = 0; i < this.mediaSources.length; i++) {
            prepareChildSource(Integer.valueOf(i), this.mediaSources[i]);
        }
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        IllegalMergeException illegalMergeException = this.mergeError;
        if (illegalMergeException == null) {
            super.maybeThrowSourceInfoRefreshError();
            return;
        }
        throw illegalMergeException;
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        MediaPeriod[] periods = new MediaPeriod[this.mediaSources.length];
        int periodIndex = this.timelines[0].getIndexOfPeriod(id.periodUid);
        for (int i = 0; i < periods.length; i++) {
            periods[i] = this.mediaSources[i].createPeriod(id.copyWithPeriodUid(this.timelines[i].getUidOfPeriod(periodIndex)), allocator, startPositionUs);
        }
        return new MergingMediaPeriod(this.compositeSequenceableLoaderFactory, periods);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        MergingMediaPeriod mergingPeriod = (MergingMediaPeriod) mediaPeriod;
        int i = 0;
        while (true) {
            MediaSource[] mediaSourceArr = this.mediaSources;
            if (i < mediaSourceArr.length) {
                mediaSourceArr[i].releasePeriod(mergingPeriod.periods[i]);
                i++;
            } else {
                return;
            }
        }
    }

    public void releaseSourceInternal() {
        super.releaseSourceInternal();
        Arrays.fill(this.timelines, (Object) null);
        this.primaryManifest = null;
        this.periodCount = -1;
        this.mergeError = null;
        this.pendingTimelineSources.clear();
        Collections.addAll(this.pendingTimelineSources, this.mediaSources);
    }

    /* access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(Integer id, MediaSource mediaSource, Timeline timeline, @Nullable Object manifest) {
        if (this.mergeError == null) {
            this.mergeError = checkTimelineMerges(timeline);
        }
        if (this.mergeError == null) {
            this.pendingTimelineSources.remove(mediaSource);
            this.timelines[id.intValue()] = timeline;
            if (mediaSource == this.mediaSources[0]) {
                this.primaryManifest = manifest;
            }
            if (this.pendingTimelineSources.isEmpty()) {
                refreshSourceInfo(this.timelines[0], this.primaryManifest);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(Integer id, MediaSource.MediaPeriodId mediaPeriodId) {
        if (id.intValue() == 0) {
            return mediaPeriodId;
        }
        return null;
    }

    private IllegalMergeException checkTimelineMerges(Timeline timeline) {
        if (this.periodCount == -1) {
            this.periodCount = timeline.getPeriodCount();
            return null;
        } else if (timeline.getPeriodCount() != this.periodCount) {
            return new IllegalMergeException(0);
        } else {
            return null;
        }
    }
}
