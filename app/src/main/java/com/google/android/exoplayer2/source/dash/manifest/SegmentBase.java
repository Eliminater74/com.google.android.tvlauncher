package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public abstract class SegmentBase {
    final RangedUri initialization;
    final long presentationTimeOffset;
    final long timescale;

    public SegmentBase(RangedUri initialization2, long timescale2, long presentationTimeOffset2) {
        this.initialization = initialization2;
        this.timescale = timescale2;
        this.presentationTimeOffset = presentationTimeOffset2;
    }

    public RangedUri getInitialization(Representation representation) {
        return this.initialization;
    }

    public long getPresentationTimeOffsetUs() {
        return Util.scaleLargeTimestamp(this.presentationTimeOffset, 1000000, this.timescale);
    }

    public static class SingleSegmentBase extends SegmentBase {
        final long indexLength;
        final long indexStart;

        public SingleSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, long indexStart2, long indexLength2) {
            super(initialization, timescale, presentationTimeOffset);
            this.indexStart = indexStart2;
            this.indexLength = indexLength2;
        }

        public SingleSegmentBase() {
            this(null, 1, 0, 0, 0);
        }

        public RangedUri getIndex() {
            long j = this.indexLength;
            if (j <= 0) {
                return null;
            }
            return new RangedUri(null, this.indexStart, j);
        }
    }

    public static abstract class MultiSegmentBase extends SegmentBase {
        final long duration;
        final List<SegmentTimelineElement> segmentTimeline;
        final long startNumber;

        public MultiSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber2, long duration2, List<SegmentTimelineElement> segmentTimeline2) {
            super(initialization, timescale, presentationTimeOffset);
            this.startNumber = startNumber2;
            this.duration = duration2;
            this.segmentTimeline = segmentTimeline2;
        }

        public abstract int getSegmentCount(long j);

        public abstract RangedUri getSegmentUrl(Representation representation, long j);

        public long getSegmentNum(long timeUs, long periodDurationUs) {
            long firstSegmentNum = getFirstSegmentNum();
            long segmentCount = (long) getSegmentCount(periodDurationUs);
            if (segmentCount == 0) {
                return firstSegmentNum;
            }
            if (this.segmentTimeline == null) {
                long segmentNum = this.startNumber + (timeUs / ((this.duration * 1000000) / this.timescale));
                if (segmentNum < firstSegmentNum) {
                    return firstSegmentNum;
                }
                if (segmentCount == -1) {
                    return segmentNum;
                }
                return Math.min(segmentNum, (firstSegmentNum + segmentCount) - 1);
            }
            long lowIndex = firstSegmentNum;
            long highIndex = (firstSegmentNum + segmentCount) - 1;
            while (lowIndex <= highIndex) {
                long midIndex = ((highIndex - lowIndex) / 2) + lowIndex;
                long midTimeUs = getSegmentTimeUs(midIndex);
                if (midTimeUs < timeUs) {
                    lowIndex = midIndex + 1;
                } else if (midTimeUs <= timeUs) {
                    return midIndex;
                } else {
                    highIndex = midIndex - 1;
                }
            }
            return lowIndex == firstSegmentNum ? lowIndex : highIndex;
        }

        public final long getSegmentDurationUs(long sequenceNumber, long periodDurationUs) {
            List<SegmentTimelineElement> list = this.segmentTimeline;
            if (list != null) {
                return (1000000 * list.get((int) (sequenceNumber - this.startNumber)).duration) / this.timescale;
            }
            int segmentCount = getSegmentCount(periodDurationUs);
            if (segmentCount == -1 || sequenceNumber != (getFirstSegmentNum() + ((long) segmentCount)) - 1) {
                return (this.duration * 1000000) / this.timescale;
            }
            return periodDurationUs - getSegmentTimeUs(sequenceNumber);
        }

        public final long getSegmentTimeUs(long sequenceNumber) {
            long unscaledSegmentTime;
            List<SegmentTimelineElement> list = this.segmentTimeline;
            if (list != null) {
                unscaledSegmentTime = list.get((int) (sequenceNumber - this.startNumber)).startTime - this.presentationTimeOffset;
            } else {
                unscaledSegmentTime = (sequenceNumber - this.startNumber) * this.duration;
            }
            return Util.scaleLargeTimestamp(unscaledSegmentTime, 1000000, this.timescale);
        }

        public long getFirstSegmentNum() {
            return this.startNumber;
        }

        public boolean isExplicit() {
            return this.segmentTimeline != null;
        }
    }

    public static class SegmentList extends MultiSegmentBase {
        final List<RangedUri> mediaSegments;

        public SegmentList(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber, long duration, List<SegmentTimelineElement> segmentTimeline, List<RangedUri> mediaSegments2) {
            super(initialization, timescale, presentationTimeOffset, startNumber, duration, segmentTimeline);
            this.mediaSegments = mediaSegments2;
        }

        public RangedUri getSegmentUrl(Representation representation, long sequenceNumber) {
            return this.mediaSegments.get((int) (sequenceNumber - this.startNumber));
        }

        public int getSegmentCount(long periodDurationUs) {
            return this.mediaSegments.size();
        }

        public boolean isExplicit() {
            return true;
        }
    }

    public static class SegmentTemplate extends MultiSegmentBase {
        final UrlTemplate initializationTemplate;
        final UrlTemplate mediaTemplate;

        public SegmentTemplate(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber, long duration, List<SegmentTimelineElement> segmentTimeline, UrlTemplate initializationTemplate2, UrlTemplate mediaTemplate2) {
            super(initialization, timescale, presentationTimeOffset, startNumber, duration, segmentTimeline);
            this.initializationTemplate = initializationTemplate2;
            this.mediaTemplate = mediaTemplate2;
        }

        public RangedUri getInitialization(Representation representation) {
            UrlTemplate urlTemplate = this.initializationTemplate;
            if (urlTemplate != null) {
                return new RangedUri(urlTemplate.buildUri(representation.format.f72id, 0, representation.format.bitrate, 0), 0, -1);
            }
            return super.getInitialization(representation);
        }

        public RangedUri getSegmentUrl(Representation representation, long sequenceNumber) {
            long time;
            Representation representation2 = representation;
            if (this.segmentTimeline != null) {
                time = ((SegmentTimelineElement) this.segmentTimeline.get((int) (sequenceNumber - this.startNumber))).startTime;
            } else {
                time = (sequenceNumber - this.startNumber) * this.duration;
            }
            return new RangedUri(this.mediaTemplate.buildUri(representation2.format.f72id, sequenceNumber, representation2.format.bitrate, time), 0, -1);
        }

        public int getSegmentCount(long periodDurationUs) {
            if (this.segmentTimeline != null) {
                return this.segmentTimeline.size();
            }
            if (periodDurationUs != C0841C.TIME_UNSET) {
                return (int) Util.ceilDivide(periodDurationUs, (this.duration * 1000000) / this.timescale);
            }
            return -1;
        }
    }

    public static class SegmentTimelineElement {
        final long duration;
        final long startTime;

        public SegmentTimelineElement(long startTime2, long duration2) {
            this.startTime = startTime2;
            this.duration = duration2;
        }
    }
}
