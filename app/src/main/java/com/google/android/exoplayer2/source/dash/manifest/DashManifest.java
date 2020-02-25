package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.offline.StreamKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DashManifest implements FilterableManifest<DashManifest> {
    public final long availabilityStartTimeMs;
    public final long durationMs;
    public final boolean dynamic;
    public final Uri location;
    public final long minBufferTimeMs;
    public final long minUpdatePeriodMs;
    @Nullable
    public final ProgramInformation programInformation;
    public final long publishTimeMs;
    public final long suggestedPresentationDelayMs;
    public final long timeShiftBufferDepthMs;
    public final UtcTimingElement utcTiming;
    private final List<Period> periods;

    @Deprecated
    public DashManifest(long availabilityStartTimeMs2, long durationMs2, long minBufferTimeMs2, boolean dynamic2, long minUpdatePeriodMs2, long timeShiftBufferDepthMs2, long suggestedPresentationDelayMs2, long publishTimeMs2, UtcTimingElement utcTiming2, Uri location2, List<Period> periods2) {
        this(availabilityStartTimeMs2, durationMs2, minBufferTimeMs2, dynamic2, minUpdatePeriodMs2, timeShiftBufferDepthMs2, suggestedPresentationDelayMs2, publishTimeMs2, null, utcTiming2, location2, periods2);
    }

    public DashManifest(long availabilityStartTimeMs2, long durationMs2, long minBufferTimeMs2, boolean dynamic2, long minUpdatePeriodMs2, long timeShiftBufferDepthMs2, long suggestedPresentationDelayMs2, long publishTimeMs2, @Nullable ProgramInformation programInformation2, UtcTimingElement utcTiming2, Uri location2, List<Period> periods2) {
        this.availabilityStartTimeMs = availabilityStartTimeMs2;
        this.durationMs = durationMs2;
        this.minBufferTimeMs = minBufferTimeMs2;
        this.dynamic = dynamic2;
        this.minUpdatePeriodMs = minUpdatePeriodMs2;
        this.timeShiftBufferDepthMs = timeShiftBufferDepthMs2;
        this.suggestedPresentationDelayMs = suggestedPresentationDelayMs2;
        this.publishTimeMs = publishTimeMs2;
        this.programInformation = programInformation2;
        this.utcTiming = utcTiming2;
        this.location = location2;
        this.periods = periods2 == null ? Collections.emptyList() : periods2;
    }

    private static ArrayList<AdaptationSet> copyAdaptationSets(List<AdaptationSet> adaptationSets, LinkedList<StreamKey> keys) {
        StreamKey key = keys.poll();
        int periodIndex = key.periodIndex;
        ArrayList<AdaptationSet> copyAdaptationSets = new ArrayList<>();
        do {
            int adaptationSetIndex = key.groupIndex;
            AdaptationSet adaptationSet = adaptationSets.get(adaptationSetIndex);
            List<Representation> representations = adaptationSet.representations;
            ArrayList<Representation> copyRepresentations = new ArrayList<>();
            do {
                copyRepresentations.add(representations.get(key.trackIndex));
                key = keys.poll();
                if (key.periodIndex != periodIndex) {
                    break;
                }
            } while (key.groupIndex == adaptationSetIndex);
            copyAdaptationSets.add(new AdaptationSet(adaptationSet.f95id, adaptationSet.type, copyRepresentations, adaptationSet.accessibilityDescriptors, adaptationSet.supplementalProperties));
        } while (key.periodIndex == periodIndex);
        keys.addFirst(key);
        return copyAdaptationSets;
    }

    public final int getPeriodCount() {
        return this.periods.size();
    }

    public final Period getPeriod(int index) {
        return this.periods.get(index);
    }

    public final long getPeriodDurationMs(int index) {
        if (index != this.periods.size() - 1) {
            return this.periods.get(index + 1).startMs - this.periods.get(index).startMs;
        }
        long j = this.durationMs;
        return j == C0841C.TIME_UNSET ? C0841C.TIME_UNSET : j - this.periods.get(index).startMs;
    }

    public final long getPeriodDurationUs(int index) {
        return C0841C.msToUs(getPeriodDurationMs(index));
    }

    public final DashManifest copy(List<StreamKey> streamKeys) {
        long newDuration;
        LinkedList<StreamKey> keys = new LinkedList<>(streamKeys);
        Collections.sort(keys);
        keys.add(new StreamKey(-1, -1, -1));
        ArrayList<Period> copyPeriods = new ArrayList<>();
        int periodIndex = 0;
        long shiftMs = 0;
        while (true) {
            int periodCount = getPeriodCount();
            newDuration = C0841C.TIME_UNSET;
            if (periodIndex >= periodCount) {
                break;
            }
            if (((StreamKey) keys.peek()).periodIndex != periodIndex) {
                long periodDurationMs = getPeriodDurationMs(periodIndex);
                if (periodDurationMs != C0841C.TIME_UNSET) {
                    shiftMs += periodDurationMs;
                }
            } else {
                Period period = getPeriod(periodIndex);
                copyPeriods.add(new Period(period.f97id, period.startMs - shiftMs, copyAdaptationSets(period.adaptationSets, keys), period.eventStreams));
            }
            periodIndex++;
        }
        long j = this.durationMs;
        if (j != C0841C.TIME_UNSET) {
            newDuration = j - shiftMs;
        }
        return new DashManifest(this.availabilityStartTimeMs, newDuration, this.minBufferTimeMs, this.dynamic, this.minUpdatePeriodMs, this.timeShiftBufferDepthMs, this.suggestedPresentationDelayMs, this.publishTimeMs, this.programInformation, this.utcTiming, this.location, copyPeriods);
    }
}
