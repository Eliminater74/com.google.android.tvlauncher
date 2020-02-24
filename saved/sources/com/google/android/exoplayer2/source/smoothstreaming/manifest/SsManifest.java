package com.google.android.exoplayer2.source.smoothstreaming.manifest;

import android.net.Uri;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SsManifest implements FilterableManifest<SsManifest> {
    public static final int UNSET_LOOKAHEAD = -1;
    public final long durationUs;
    public final long dvrWindowLengthUs;
    public final boolean isLive;
    public final int lookAheadCount;
    public final int majorVersion;
    public final int minorVersion;
    public final ProtectionElement protectionElement;
    public final StreamElement[] streamElements;

    public static class ProtectionElement {
        public final byte[] data;
        public final TrackEncryptionBox[] trackEncryptionBoxes;
        public final UUID uuid;

        public ProtectionElement(UUID uuid2, byte[] data2, TrackEncryptionBox[] trackEncryptionBoxes2) {
            this.uuid = uuid2;
            this.data = data2;
            this.trackEncryptionBoxes = trackEncryptionBoxes2;
        }
    }

    public static class StreamElement {
        private static final String URL_PLACEHOLDER_BITRATE_1 = "{bitrate}";
        private static final String URL_PLACEHOLDER_BITRATE_2 = "{Bitrate}";
        private static final String URL_PLACEHOLDER_START_TIME_1 = "{start time}";
        private static final String URL_PLACEHOLDER_START_TIME_2 = "{start_time}";
        private final String baseUri;
        public final int chunkCount;
        private final List<Long> chunkStartTimes;
        private final long[] chunkStartTimesUs;
        private final String chunkTemplate;
        public final int displayHeight;
        public final int displayWidth;
        public final Format[] formats;
        public final String language;
        private final long lastChunkDurationUs;
        public final int maxHeight;
        public final int maxWidth;
        public final String name;
        public final String subType;
        public final long timescale;
        public final int type;

        public StreamElement(String baseUri2, String chunkTemplate2, int type2, String subType2, long timescale2, String name2, int maxWidth2, int maxHeight2, int displayWidth2, int displayHeight2, String language2, Format[] formats2, List<Long> chunkStartTimes2, long lastChunkDuration) {
            this(baseUri2, chunkTemplate2, type2, subType2, timescale2, name2, maxWidth2, maxHeight2, displayWidth2, displayHeight2, language2, formats2, chunkStartTimes2, Util.scaleLargeTimestamps(chunkStartTimes2, 1000000, timescale2), Util.scaleLargeTimestamp(lastChunkDuration, 1000000, timescale2));
        }

        private StreamElement(String baseUri2, String chunkTemplate2, int type2, String subType2, long timescale2, String name2, int maxWidth2, int maxHeight2, int displayWidth2, int displayHeight2, String language2, Format[] formats2, List<Long> chunkStartTimes2, long[] chunkStartTimesUs2, long lastChunkDurationUs2) {
            this.baseUri = baseUri2;
            this.chunkTemplate = chunkTemplate2;
            this.type = type2;
            this.subType = subType2;
            this.timescale = timescale2;
            this.name = name2;
            this.maxWidth = maxWidth2;
            this.maxHeight = maxHeight2;
            this.displayWidth = displayWidth2;
            this.displayHeight = displayHeight2;
            this.language = language2;
            this.formats = formats2;
            this.chunkStartTimes = chunkStartTimes2;
            this.chunkStartTimesUs = chunkStartTimesUs2;
            this.lastChunkDurationUs = lastChunkDurationUs2;
            this.chunkCount = chunkStartTimes2.size();
        }

        public StreamElement copy(Format[] formats2) {
            String str = this.baseUri;
            return new StreamElement(str, this.chunkTemplate, this.type, this.subType, this.timescale, this.name, this.maxWidth, this.maxHeight, this.displayWidth, this.displayHeight, this.language, formats2, this.chunkStartTimes, this.chunkStartTimesUs, this.lastChunkDurationUs);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int
         arg types: [long[], long, int, int]
         candidates:
          com.google.android.exoplayer2.util.Util.binarySearchFloor(java.util.List, java.lang.Comparable, boolean, boolean):int
          com.google.android.exoplayer2.util.Util.binarySearchFloor(int[], int, boolean, boolean):int
          com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int */
        public int getChunkIndex(long timeUs) {
            return Util.binarySearchFloor(this.chunkStartTimesUs, timeUs, true, true);
        }

        public long getStartTimeUs(int chunkIndex) {
            return this.chunkStartTimesUs[chunkIndex];
        }

        public long getChunkDurationUs(int chunkIndex) {
            if (chunkIndex == this.chunkCount - 1) {
                return this.lastChunkDurationUs;
            }
            long[] jArr = this.chunkStartTimesUs;
            return jArr[chunkIndex + 1] - jArr[chunkIndex];
        }

        public Uri buildRequestUri(int track, int chunkIndex) {
            boolean z = true;
            Assertions.checkState(this.formats != null);
            Assertions.checkState(this.chunkStartTimes != null);
            if (chunkIndex >= this.chunkStartTimes.size()) {
                z = false;
            }
            Assertions.checkState(z);
            String bitrateString = Integer.toString(this.formats[track].bitrate);
            String startTimeString = this.chunkStartTimes.get(chunkIndex).toString();
            return UriUtil.resolveToUri(this.baseUri, this.chunkTemplate.replace(URL_PLACEHOLDER_BITRATE_1, bitrateString).replace(URL_PLACEHOLDER_BITRATE_2, bitrateString).replace(URL_PLACEHOLDER_START_TIME_1, startTimeString).replace(URL_PLACEHOLDER_START_TIME_2, startTimeString));
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SsManifest(int r18, int r19, long r20, long r22, long r24, int r26, boolean r27, com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest.ProtectionElement r28, com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest.StreamElement[] r29) {
        /*
            r17 = this;
            r0 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r2 = 0
            int r4 = (r22 > r2 ? 1 : (r22 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x000e
            r9 = r0
            goto L_0x001a
        L_0x000e:
            r6 = 1000000(0xf4240, double:4.940656E-318)
            r4 = r22
            r8 = r20
            long r4 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r4, r6, r8)
            r9 = r4
        L_0x001a:
            int r4 = (r24 > r2 ? 1 : (r24 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x0020
            r11 = r0
            goto L_0x002c
        L_0x0020:
            r13 = 1000000(0xf4240, double:4.940656E-318)
            r11 = r24
            r15 = r20
            long r0 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r11, r13, r15)
            r11 = r0
        L_0x002c:
            r6 = r17
            r7 = r18
            r8 = r19
            r13 = r26
            r14 = r27
            r15 = r28
            r16 = r29
            r6.<init>(r7, r8, r9, r11, r13, r14, r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest.<init>(int, int, long, long, long, int, boolean, com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest$ProtectionElement, com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest$StreamElement[]):void");
    }

    private SsManifest(int majorVersion2, int minorVersion2, long durationUs2, long dvrWindowLengthUs2, int lookAheadCount2, boolean isLive2, ProtectionElement protectionElement2, StreamElement[] streamElements2) {
        this.majorVersion = majorVersion2;
        this.minorVersion = minorVersion2;
        this.durationUs = durationUs2;
        this.dvrWindowLengthUs = dvrWindowLengthUs2;
        this.lookAheadCount = lookAheadCount2;
        this.isLive = isLive2;
        this.protectionElement = protectionElement2;
        this.streamElements = streamElements2;
    }

    public final SsManifest copy(List<StreamKey> streamKeys) {
        ArrayList<StreamKey> sortedKeys = new ArrayList<>(streamKeys);
        Collections.sort(sortedKeys);
        StreamElement currentStreamElement = null;
        List<StreamElement> copiedStreamElements = new ArrayList<>();
        List<Format> copiedFormats = new ArrayList<>();
        for (int i = 0; i < sortedKeys.size(); i++) {
            StreamKey key = (StreamKey) sortedKeys.get(i);
            StreamElement streamElement = this.streamElements[key.groupIndex];
            if (!(streamElement == currentStreamElement || currentStreamElement == null)) {
                copiedStreamElements.add(currentStreamElement.copy((Format[]) copiedFormats.toArray(new Format[0])));
                copiedFormats.clear();
            }
            currentStreamElement = streamElement;
            copiedFormats.add(streamElement.formats[key.trackIndex]);
        }
        if (currentStreamElement != null) {
            copiedStreamElements.add(currentStreamElement.copy((Format[]) copiedFormats.toArray(new Format[0])));
        }
        return new SsManifest(this.majorVersion, this.minorVersion, this.durationUs, this.dvrWindowLengthUs, this.lookAheadCount, this.isLive, this.protectionElement, (StreamElement[]) copiedStreamElements.toArray(new StreamElement[0]));
    }
}
