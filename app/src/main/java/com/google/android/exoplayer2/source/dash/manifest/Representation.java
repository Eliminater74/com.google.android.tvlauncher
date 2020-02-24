package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.dash.DashSegmentIndex;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase;
import java.util.Collections;
import java.util.List;

public abstract class Representation {
    public static final long REVISION_ID_DEFAULT = -1;
    public final String baseUrl;
    public final Format format;
    public final List<Descriptor> inbandEventStreams;
    private final RangedUri initializationUri;
    public final long presentationTimeOffsetUs;
    public final long revisionId;

    public abstract String getCacheKey();

    public abstract DashSegmentIndex getIndex();

    public abstract RangedUri getIndexUri();

    public static Representation newInstance(long revisionId2, Format format2, String baseUrl2, SegmentBase segmentBase) {
        return newInstance(revisionId2, format2, baseUrl2, segmentBase, null);
    }

    public static Representation newInstance(long revisionId2, Format format2, String baseUrl2, SegmentBase segmentBase, List<Descriptor> inbandEventStreams2) {
        return newInstance(revisionId2, format2, baseUrl2, segmentBase, inbandEventStreams2, null);
    }

    public static Representation newInstance(long revisionId2, Format format2, String baseUrl2, SegmentBase segmentBase, List<Descriptor> inbandEventStreams2, String cacheKey) {
        SegmentBase segmentBase2 = segmentBase;
        if (segmentBase2 instanceof SegmentBase.SingleSegmentBase) {
            return new SingleSegmentRepresentation(revisionId2, format2, baseUrl2, (SegmentBase.SingleSegmentBase) segmentBase2, inbandEventStreams2, cacheKey, -1);
        } else if (segmentBase2 instanceof SegmentBase.MultiSegmentBase) {
            return new MultiSegmentRepresentation(revisionId2, format2, baseUrl2, (SegmentBase.MultiSegmentBase) segmentBase2, inbandEventStreams2);
        } else {
            throw new IllegalArgumentException("segmentBase must be of type SingleSegmentBase or MultiSegmentBase");
        }
    }

    private Representation(long revisionId2, Format format2, String baseUrl2, SegmentBase segmentBase, List<Descriptor> inbandEventStreams2) {
        List<Descriptor> list;
        this.revisionId = revisionId2;
        this.format = format2;
        this.baseUrl = baseUrl2;
        if (inbandEventStreams2 == null) {
            list = Collections.emptyList();
        } else {
            list = Collections.unmodifiableList(inbandEventStreams2);
        }
        this.inbandEventStreams = list;
        this.initializationUri = segmentBase.getInitialization(this);
        this.presentationTimeOffsetUs = segmentBase.getPresentationTimeOffsetUs();
    }

    public RangedUri getInitializationUri() {
        return this.initializationUri;
    }

    public static class SingleSegmentRepresentation extends Representation {
        private final String cacheKey;
        public final long contentLength;
        private final RangedUri indexUri;
        private final SingleSegmentIndex segmentIndex;
        public final Uri uri;

        public static SingleSegmentRepresentation newInstance(long revisionId, Format format, String uri2, long initializationStart, long initializationEnd, long indexStart, long indexEnd, List<Descriptor> inbandEventStreams, String cacheKey2, long contentLength2) {
            return new SingleSegmentRepresentation(revisionId, format, uri2, new SegmentBase.SingleSegmentBase(new RangedUri(null, initializationStart, (initializationEnd - initializationStart) + 1), 1, 0, indexStart, (indexEnd - indexStart) + 1), inbandEventStreams, cacheKey2, contentLength2);
        }

        public SingleSegmentRepresentation(long revisionId, Format format, String baseUrl, SegmentBase.SingleSegmentBase segmentBase, List<Descriptor> inbandEventStreams, String cacheKey2, long contentLength2) {
            super(revisionId, format, baseUrl, segmentBase, inbandEventStreams);
            SingleSegmentIndex singleSegmentIndex;
            this.uri = Uri.parse(baseUrl);
            this.indexUri = segmentBase.getIndex();
            this.cacheKey = cacheKey2;
            this.contentLength = contentLength2;
            if (this.indexUri != null) {
                singleSegmentIndex = null;
            } else {
                singleSegmentIndex = new SingleSegmentIndex(new RangedUri(null, 0, contentLength2));
            }
            this.segmentIndex = singleSegmentIndex;
        }

        public RangedUri getIndexUri() {
            return this.indexUri;
        }

        public DashSegmentIndex getIndex() {
            return this.segmentIndex;
        }

        public String getCacheKey() {
            return this.cacheKey;
        }
    }

    public static class MultiSegmentRepresentation extends Representation implements DashSegmentIndex {
        private final SegmentBase.MultiSegmentBase segmentBase;

        public MultiSegmentRepresentation(long revisionId, Format format, String baseUrl, SegmentBase.MultiSegmentBase segmentBase2, List<Descriptor> inbandEventStreams) {
            super(revisionId, format, baseUrl, segmentBase2, inbandEventStreams);
            this.segmentBase = segmentBase2;
        }

        public RangedUri getIndexUri() {
            return null;
        }

        public DashSegmentIndex getIndex() {
            return this;
        }

        public String getCacheKey() {
            return null;
        }

        public RangedUri getSegmentUrl(long segmentIndex) {
            return this.segmentBase.getSegmentUrl(this, segmentIndex);
        }

        public long getSegmentNum(long timeUs, long periodDurationUs) {
            return this.segmentBase.getSegmentNum(timeUs, periodDurationUs);
        }

        public long getTimeUs(long segmentIndex) {
            return this.segmentBase.getSegmentTimeUs(segmentIndex);
        }

        public long getDurationUs(long segmentIndex, long periodDurationUs) {
            return this.segmentBase.getSegmentDurationUs(segmentIndex, periodDurationUs);
        }

        public long getFirstSegmentNum() {
            return this.segmentBase.getFirstSegmentNum();
        }

        public int getSegmentCount(long periodDurationUs) {
            return this.segmentBase.getSegmentCount(periodDurationUs);
        }

        public boolean isExplicit() {
            return this.segmentBase.isExplicit();
        }
    }
}
