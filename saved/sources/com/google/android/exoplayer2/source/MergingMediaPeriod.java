package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

final class MergingMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
    private MediaPeriod.Callback callback;
    private final ArrayList<MediaPeriod> childrenPendingPreparation = new ArrayList<>();
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private MediaPeriod[] enabledPeriods;
    public final MediaPeriod[] periods;
    private final IdentityHashMap<SampleStream, Integer> streamPeriodIndices;
    private TrackGroupArray trackGroups;

    public List getStreamKeys(List list) {
        return MediaPeriod$$CC.getStreamKeys$$dflt$$(this, list);
    }

    public MergingMediaPeriod(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, MediaPeriod... periods2) {
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.periods = periods2;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory2.createCompositeSequenceableLoader(new SequenceableLoader[0]);
        this.streamPeriodIndices = new IdentityHashMap<>();
    }

    public void prepare(MediaPeriod.Callback callback2, long positionUs) {
        this.callback = callback2;
        Collections.addAll(this.childrenPendingPreparation, this.periods);
        for (MediaPeriod period : this.periods) {
            period.prepare(this, positionUs);
        }
    }

    public void maybeThrowPrepareError() throws IOException {
        for (MediaPeriod period : this.periods) {
            period.maybeThrowPrepareError();
        }
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    /* JADX INFO: Multiple debug info for r14v2 int: [D('childSelections' com.google.android.exoplayer2.trackselection.TrackSelection[]), D('i' int)] */
    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        int i;
        TrackSelection[] trackSelectionArr = selections;
        SampleStream[] sampleStreamArr = streams;
        int[] streamChildIndices = new int[trackSelectionArr.length];
        int[] selectionChildIndices = new int[trackSelectionArr.length];
        for (int i2 = 0; i2 < trackSelectionArr.length; i2++) {
            if (sampleStreamArr[i2] == null) {
                i = -1;
            } else {
                i = this.streamPeriodIndices.get(sampleStreamArr[i2]).intValue();
            }
            streamChildIndices[i2] = i;
            selectionChildIndices[i2] = -1;
            if (trackSelectionArr[i2] != null) {
                TrackGroup trackGroup = trackSelectionArr[i2].getTrackGroup();
                int j = 0;
                while (true) {
                    MediaPeriod[] mediaPeriodArr = this.periods;
                    if (j >= mediaPeriodArr.length) {
                        break;
                    } else if (mediaPeriodArr[j].getTrackGroups().indexOf(trackGroup) != -1) {
                        selectionChildIndices[i2] = j;
                        break;
                    } else {
                        j++;
                    }
                }
            }
        }
        this.streamPeriodIndices.clear();
        SampleStream[] newStreams = new SampleStream[trackSelectionArr.length];
        SampleStream[] childStreams = new SampleStream[trackSelectionArr.length];
        TrackSelection[] childSelections = new TrackSelection[trackSelectionArr.length];
        ArrayList arrayList = new ArrayList(this.periods.length);
        long positionUs2 = positionUs;
        int i3 = 0;
        while (i3 < this.periods.length) {
            for (int j2 = 0; j2 < trackSelectionArr.length; j2++) {
                TrackSelection trackSelection = null;
                childStreams[j2] = streamChildIndices[j2] == i3 ? sampleStreamArr[j2] : null;
                if (selectionChildIndices[j2] == i3) {
                    trackSelection = trackSelectionArr[j2];
                }
                childSelections[j2] = trackSelection;
            }
            TrackSelection[] childSelections2 = childSelections;
            int i4 = i3;
            long selectPositionUs = this.periods[i3].selectTracks(childSelections, mayRetainStreamFlags, childStreams, streamResetFlags, positionUs2);
            if (i4 == 0) {
                positionUs2 = selectPositionUs;
            } else if (selectPositionUs != positionUs2) {
                throw new IllegalStateException("Children enabled at different positions.");
            }
            boolean periodEnabled = false;
            for (int j3 = 0; j3 < trackSelectionArr.length; j3++) {
                boolean z = true;
                if (selectionChildIndices[j3] == i4) {
                    if (childStreams[j3] == null) {
                        z = false;
                    }
                    Assertions.checkState(z);
                    newStreams[j3] = childStreams[j3];
                    periodEnabled = true;
                    this.streamPeriodIndices.put(childStreams[j3], Integer.valueOf(i4));
                } else if (streamChildIndices[j3] == i4) {
                    if (childStreams[j3] != null) {
                        z = false;
                    }
                    Assertions.checkState(z);
                }
            }
            if (periodEnabled) {
                arrayList.add(this.periods[i4]);
            }
            i3 = i4 + 1;
            sampleStreamArr = streams;
            childSelections = childSelections2;
        }
        System.arraycopy(newStreams, 0, streams, 0, newStreams.length);
        this.enabledPeriods = new MediaPeriod[arrayList.size()];
        arrayList.toArray(this.enabledPeriods);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.enabledPeriods);
        return positionUs2;
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        for (MediaPeriod period : this.enabledPeriods) {
            period.discardBuffer(positionUs, toKeyframe);
        }
    }

    public void reevaluateBuffer(long positionUs) {
        this.compositeSequenceableLoader.reevaluateBuffer(positionUs);
    }

    public boolean continueLoading(long positionUs) {
        if (this.childrenPendingPreparation.isEmpty()) {
            return this.compositeSequenceableLoader.continueLoading(positionUs);
        }
        int childrenPendingPreparationSize = this.childrenPendingPreparation.size();
        for (int i = 0; i < childrenPendingPreparationSize; i++) {
            this.childrenPendingPreparation.get(i).continueLoading(positionUs);
        }
        return false;
    }

    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    public long readDiscontinuity() {
        long positionUs = this.periods[0].readDiscontinuity();
        int i = 1;
        while (true) {
            MediaPeriod[] mediaPeriodArr = this.periods;
            if (i >= mediaPeriodArr.length) {
                if (positionUs != C0841C.TIME_UNSET) {
                    MediaPeriod[] mediaPeriodArr2 = this.enabledPeriods;
                    int length = mediaPeriodArr2.length;
                    int i2 = 0;
                    while (i2 < length) {
                        MediaPeriod enabledPeriod = mediaPeriodArr2[i2];
                        if (enabledPeriod == this.periods[0] || enabledPeriod.seekToUs(positionUs) == positionUs) {
                            i2++;
                        } else {
                            throw new IllegalStateException("Unexpected child seekToUs result.");
                        }
                    }
                }
                return positionUs;
            } else if (mediaPeriodArr[i].readDiscontinuity() == C0841C.TIME_UNSET) {
                i++;
            } else {
                throw new IllegalStateException("Child reported discontinuity.");
            }
        }
    }

    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    public long seekToUs(long positionUs) {
        long positionUs2 = this.enabledPeriods[0].seekToUs(positionUs);
        int i = 1;
        while (true) {
            MediaPeriod[] mediaPeriodArr = this.enabledPeriods;
            if (i >= mediaPeriodArr.length) {
                return positionUs2;
            }
            if (mediaPeriodArr[i].seekToUs(positionUs2) == positionUs2) {
                i++;
            } else {
                throw new IllegalStateException("Unexpected child seekToUs result.");
            }
        }
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        return this.enabledPeriods[0].getAdjustedSeekPositionUs(positionUs, seekParameters);
    }

    public void onPrepared(MediaPeriod preparedPeriod) {
        this.childrenPendingPreparation.remove(preparedPeriod);
        if (this.childrenPendingPreparation.isEmpty()) {
            int totalTrackGroupCount = 0;
            for (MediaPeriod period : this.periods) {
                totalTrackGroupCount += period.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArray = new TrackGroup[totalTrackGroupCount];
            int trackGroupIndex = 0;
            for (MediaPeriod period2 : this.periods) {
                TrackGroupArray periodTrackGroups = period2.getTrackGroups();
                int periodTrackGroupCount = periodTrackGroups.length;
                int j = 0;
                while (j < periodTrackGroupCount) {
                    trackGroupArray[trackGroupIndex] = periodTrackGroups.get(j);
                    j++;
                    trackGroupIndex++;
                }
            }
            this.trackGroups = new TrackGroupArray(trackGroupArray);
            this.callback.onPrepared(this);
        }
    }

    public void onContinueLoadingRequested(MediaPeriod ignored) {
        this.callback.onContinueLoadingRequested(this);
    }
}
