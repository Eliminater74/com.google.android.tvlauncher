package com.google.android.exoplayer2.source.hls.playlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.offline.StreamKey;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

public final class HlsMediaPlaylist extends HlsPlaylist {
    public static final int PLAYLIST_TYPE_EVENT = 2;
    public static final int PLAYLIST_TYPE_UNKNOWN = 0;
    public static final int PLAYLIST_TYPE_VOD = 1;
    public final int discontinuitySequence;
    public final long durationUs;
    public final boolean hasDiscontinuitySequence;
    public final boolean hasEndTag;
    public final boolean hasProgramDateTime;
    public final long mediaSequence;
    public final int playlistType;
    @Nullable
    public final DrmInitData protectionSchemes;
    public final List<Segment> segments;
    public final long startOffsetUs;
    public final long startTimeUs;
    public final long targetDurationUs;
    public final int version;

    public HlsMediaPlaylist(int playlistType2, String baseUri, List<String> tags, long startOffsetUs2, long startTimeUs2, boolean hasDiscontinuitySequence2, int discontinuitySequence2, long mediaSequence2, int version2, long targetDurationUs2, boolean hasIndependentSegments, boolean hasEndTag2, boolean hasProgramDateTime2, @Nullable DrmInitData protectionSchemes2, List<Segment> segments2) {
        super(baseUri, tags, hasIndependentSegments);
        long j;
        long j2;
        this.playlistType = playlistType2;
        this.startTimeUs = startTimeUs2;
        this.hasDiscontinuitySequence = hasDiscontinuitySequence2;
        this.discontinuitySequence = discontinuitySequence2;
        this.mediaSequence = mediaSequence2;
        this.version = version2;
        this.targetDurationUs = targetDurationUs2;
        this.hasEndTag = hasEndTag2;
        this.hasProgramDateTime = hasProgramDateTime2;
        this.protectionSchemes = protectionSchemes2;
        this.segments = Collections.unmodifiableList(segments2);
        if (!segments2.isEmpty()) {
            Segment last = segments2.get(segments2.size() - 1);
            this.durationUs = last.relativeStartTimeUs + last.durationUs;
            j = 0;
        } else {
            j = 0;
            this.durationUs = 0;
        }
        if (startOffsetUs2 == C0841C.TIME_UNSET) {
            j2 = -9223372036854775807L;
        } else {
            j2 = startOffsetUs2 >= j ? startOffsetUs2 : this.durationUs + startOffsetUs2;
        }
        this.startOffsetUs = j2;
    }

    public HlsMediaPlaylist copy(List<StreamKey> list) {
        return this;
    }

    public boolean isNewerThan(HlsMediaPlaylist other) {
        if (other != null) {
            long j = this.mediaSequence;
            long j2 = other.mediaSequence;
            if (j <= j2) {
                if (j < j2) {
                    return false;
                }
                int segmentCount = this.segments.size();
                int otherSegmentCount = other.segments.size();
                if (segmentCount > otherSegmentCount) {
                    return true;
                }
                if (segmentCount != otherSegmentCount || !this.hasEndTag || other.hasEndTag) {
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    public long getEndTimeUs() {
        return this.startTimeUs + this.durationUs;
    }

    public HlsMediaPlaylist copyWith(long startTimeUs2, int discontinuitySequence2) {
        return new HlsMediaPlaylist(this.playlistType, this.baseUri, this.tags, this.startOffsetUs, startTimeUs2, true, discontinuitySequence2, this.mediaSequence, this.version, this.targetDurationUs, this.hasIndependentSegments, this.hasEndTag, this.hasProgramDateTime, this.protectionSchemes, this.segments);
    }

    public HlsMediaPlaylist copyWithEndTag() {
        if (this.hasEndTag) {
            return this;
        }
        HlsMediaPlaylist hlsMediaPlaylist = r2;
        HlsMediaPlaylist hlsMediaPlaylist2 = new HlsMediaPlaylist(this.playlistType, this.baseUri, this.tags, this.startOffsetUs, this.startTimeUs, this.hasDiscontinuitySequence, this.discontinuitySequence, this.mediaSequence, this.version, this.targetDurationUs, this.hasIndependentSegments, true, this.hasProgramDateTime, this.protectionSchemes, this.segments);
        return hlsMediaPlaylist;
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaylistType {
    }

    public static final class Segment implements Comparable<Long> {
        public final long byterangeLength;
        public final long byterangeOffset;
        @Nullable
        public final DrmInitData drmInitData;
        public final long durationUs;
        @Nullable
        public final String encryptionIV;
        @Nullable
        public final String fullSegmentEncryptionKeyUri;
        public final boolean hasGapTag;
        @Nullable
        public final Segment initializationSegment;
        public final int relativeDiscontinuitySequence;
        public final long relativeStartTimeUs;
        public final String title;
        public final String url;

        public Segment(String uri, long byterangeOffset2, long byterangeLength2, String fullSegmentEncryptionKeyUri2, String encryptionIV2) {
            this(uri, null, "", 0, -1, C0841C.TIME_UNSET, null, fullSegmentEncryptionKeyUri2, encryptionIV2, byterangeOffset2, byterangeLength2, false);
        }

        public Segment(String url2, @Nullable Segment initializationSegment2, String title2, long durationUs2, int relativeDiscontinuitySequence2, long relativeStartTimeUs2, @Nullable DrmInitData drmInitData2, @Nullable String fullSegmentEncryptionKeyUri2, @Nullable String encryptionIV2, long byterangeOffset2, long byterangeLength2, boolean hasGapTag2) {
            this.url = url2;
            this.initializationSegment = initializationSegment2;
            this.title = title2;
            this.durationUs = durationUs2;
            this.relativeDiscontinuitySequence = relativeDiscontinuitySequence2;
            this.relativeStartTimeUs = relativeStartTimeUs2;
            this.drmInitData = drmInitData2;
            this.fullSegmentEncryptionKeyUri = fullSegmentEncryptionKeyUri2;
            this.encryptionIV = encryptionIV2;
            this.byterangeOffset = byterangeOffset2;
            this.byterangeLength = byterangeLength2;
            this.hasGapTag = hasGapTag2;
        }

        public int compareTo(@NonNull Long relativeStartTimeUs2) {
            if (this.relativeStartTimeUs > relativeStartTimeUs2.longValue()) {
                return 1;
            }
            return this.relativeStartTimeUs < relativeStartTimeUs2.longValue() ? -1 : 0;
        }
    }
}
