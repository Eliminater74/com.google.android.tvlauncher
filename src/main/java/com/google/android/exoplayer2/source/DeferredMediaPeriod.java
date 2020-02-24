package com.google.android.exoplayer2.source;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import java.io.IOException;
import java.util.List;

public final class DeferredMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
    private final Allocator allocator;
    private MediaPeriod.Callback callback;

    /* renamed from: id */
    public final MediaSource.MediaPeriodId f91id;
    @Nullable
    private PrepareErrorListener listener;
    private MediaPeriod mediaPeriod;
    public final MediaSource mediaSource;
    private boolean notifiedPrepareError;
    private long preparePositionOverrideUs = C0841C.TIME_UNSET;
    private long preparePositionUs;

    public interface PrepareErrorListener {
        void onPrepareError(MediaSource.MediaPeriodId mediaPeriodId, IOException iOException);
    }

    public List getStreamKeys(List list) {
        return MediaPeriod$$CC.getStreamKeys$$dflt$$(this, list);
    }

    public DeferredMediaPeriod(MediaSource mediaSource2, MediaSource.MediaPeriodId id, Allocator allocator2, long preparePositionUs2) {
        this.f91id = id;
        this.allocator = allocator2;
        this.mediaSource = mediaSource2;
        this.preparePositionUs = preparePositionUs2;
    }

    public void setPrepareErrorListener(PrepareErrorListener listener2) {
        this.listener = listener2;
    }

    public long getPreparePositionUs() {
        return this.preparePositionUs;
    }

    public void overridePreparePositionUs(long preparePositionUs2) {
        this.preparePositionOverrideUs = preparePositionUs2;
    }

    public void createPeriod(MediaSource.MediaPeriodId id) {
        long preparePositionUs2 = getPreparePositionWithOverride(this.preparePositionUs);
        this.mediaPeriod = this.mediaSource.createPeriod(id, this.allocator, preparePositionUs2);
        if (this.callback != null) {
            this.mediaPeriod.prepare(this, preparePositionUs2);
        }
    }

    public void releasePeriod() {
        MediaPeriod mediaPeriod2 = this.mediaPeriod;
        if (mediaPeriod2 != null) {
            this.mediaSource.releasePeriod(mediaPeriod2);
        }
    }

    public void prepare(MediaPeriod.Callback callback2, long preparePositionUs2) {
        this.callback = callback2;
        MediaPeriod mediaPeriod2 = this.mediaPeriod;
        if (mediaPeriod2 != null) {
            mediaPeriod2.prepare(this, getPreparePositionWithOverride(this.preparePositionUs));
        }
    }

    public void maybeThrowPrepareError() throws IOException {
        try {
            if (this.mediaPeriod != null) {
                this.mediaPeriod.maybeThrowPrepareError();
            } else {
                this.mediaSource.maybeThrowSourceInfoRefreshError();
            }
        } catch (IOException e) {
            PrepareErrorListener prepareErrorListener = this.listener;
            if (prepareErrorListener == null) {
                throw e;
            } else if (!this.notifiedPrepareError) {
                this.notifiedPrepareError = true;
                prepareErrorListener.onPrepareError(this.f91id, e);
            }
        }
    }

    public TrackGroupArray getTrackGroups() {
        return this.mediaPeriod.getTrackGroups();
    }

    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        long positionUs2;
        if (this.preparePositionOverrideUs == C0841C.TIME_UNSET || positionUs != this.preparePositionUs) {
            positionUs2 = positionUs;
        } else {
            positionUs2 = this.preparePositionOverrideUs;
            this.preparePositionOverrideUs = C0841C.TIME_UNSET;
        }
        return this.mediaPeriod.selectTracks(selections, mayRetainStreamFlags, streams, streamResetFlags, positionUs2);
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        this.mediaPeriod.discardBuffer(positionUs, toKeyframe);
    }

    public long readDiscontinuity() {
        return this.mediaPeriod.readDiscontinuity();
    }

    public long getBufferedPositionUs() {
        return this.mediaPeriod.getBufferedPositionUs();
    }

    public long seekToUs(long positionUs) {
        return this.mediaPeriod.seekToUs(positionUs);
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        return this.mediaPeriod.getAdjustedSeekPositionUs(positionUs, seekParameters);
    }

    public long getNextLoadPositionUs() {
        return this.mediaPeriod.getNextLoadPositionUs();
    }

    public void reevaluateBuffer(long positionUs) {
        this.mediaPeriod.reevaluateBuffer(positionUs);
    }

    public boolean continueLoading(long positionUs) {
        MediaPeriod mediaPeriod2 = this.mediaPeriod;
        return mediaPeriod2 != null && mediaPeriod2.continueLoading(positionUs);
    }

    public void onContinueLoadingRequested(MediaPeriod source) {
        this.callback.onContinueLoadingRequested(this);
    }

    public void onPrepared(MediaPeriod mediaPeriod2) {
        this.callback.onPrepared(this);
    }

    private long getPreparePositionWithOverride(long preparePositionUs2) {
        long j = this.preparePositionOverrideUs;
        if (j != C0841C.TIME_UNSET) {
            return j;
        }
        return preparePositionUs2;
    }
}
