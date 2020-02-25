package com.google.android.exoplayer2.extractor.p007ts;

import android.util.SparseArray;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.extractor.ts.TsPayloadReader */
public interface TsPayloadReader {
    public static final int FLAG_DATA_ALIGNMENT_INDICATOR = 4;
    public static final int FLAG_PAYLOAD_UNIT_START_INDICATOR = 1;
    public static final int FLAG_RANDOM_ACCESS_INDICATOR = 2;

    void consume(ParsableByteArray parsableByteArray, int i) throws ParserException;

    void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator);

    void seek();

    /* renamed from: com.google.android.exoplayer2.extractor.ts.TsPayloadReader$Factory */
    public interface Factory {
        SparseArray<TsPayloadReader> createInitialPayloadReaders();

        TsPayloadReader createPayloadReader(int i, EsInfo esInfo);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.extractor.ts.TsPayloadReader$Flags */
    public @interface Flags {
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.TsPayloadReader$EsInfo */
    public static final class EsInfo {
        public final byte[] descriptorBytes;
        public final List<DvbSubtitleInfo> dvbSubtitleInfos;
        public final String language;
        public final int streamType;

        public EsInfo(int streamType2, String language2, List<DvbSubtitleInfo> dvbSubtitleInfos2, byte[] descriptorBytes2) {
            List<DvbSubtitleInfo> list;
            this.streamType = streamType2;
            this.language = language2;
            if (dvbSubtitleInfos2 == null) {
                list = Collections.emptyList();
            } else {
                list = Collections.unmodifiableList(dvbSubtitleInfos2);
            }
            this.dvbSubtitleInfos = list;
            this.descriptorBytes = descriptorBytes2;
        }
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.TsPayloadReader$DvbSubtitleInfo */
    public static final class DvbSubtitleInfo {
        public final byte[] initializationData;
        public final String language;
        public final int type;

        public DvbSubtitleInfo(String language2, int type2, byte[] initializationData2) {
            this.language = language2;
            this.type = type2;
            this.initializationData = initializationData2;
        }
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.TsPayloadReader$TrackIdGenerator */
    public static final class TrackIdGenerator {
        private static final int ID_UNSET = Integer.MIN_VALUE;
        private final int firstTrackId;
        private final String formatIdPrefix;
        private final int trackIdIncrement;
        private String formatId;
        private int trackId;

        public TrackIdGenerator(int firstTrackId2, int trackIdIncrement2) {
            this(Integer.MIN_VALUE, firstTrackId2, trackIdIncrement2);
        }

        public TrackIdGenerator(int programNumber, int firstTrackId2, int trackIdIncrement2) {
            String str;
            if (programNumber != Integer.MIN_VALUE) {
                StringBuilder sb = new StringBuilder(12);
                sb.append(programNumber);
                sb.append("/");
                str = sb.toString();
            } else {
                str = "";
            }
            this.formatIdPrefix = str;
            this.firstTrackId = firstTrackId2;
            this.trackIdIncrement = trackIdIncrement2;
            this.trackId = Integer.MIN_VALUE;
        }

        public void generateNewId() {
            int i = this.trackId;
            this.trackId = i == Integer.MIN_VALUE ? this.firstTrackId : i + this.trackIdIncrement;
            String str = this.formatIdPrefix;
            int i2 = this.trackId;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 11);
            sb.append(str);
            sb.append(i2);
            this.formatId = sb.toString();
        }

        public int getTrackId() {
            maybeThrowUninitializedError();
            return this.trackId;
        }

        public String getFormatId() {
            maybeThrowUninitializedError();
            return this.formatId;
        }

        private void maybeThrowUninitializedError() {
            if (this.trackId == Integer.MIN_VALUE) {
                throw new IllegalStateException("generateNewId() must be called before retrieving ids.");
            }
        }
    }
}
