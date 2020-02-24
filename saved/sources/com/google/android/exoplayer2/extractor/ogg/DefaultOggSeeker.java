package com.google.android.exoplayer2.extractor.ogg;

import android.support.annotation.VisibleForTesting;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Assertions;
import java.io.EOFException;
import java.io.IOException;

final class DefaultOggSeeker implements OggSeeker {
    private static final int DEFAULT_OFFSET = 30000;
    @VisibleForTesting
    public static final int MATCH_BYTE_RANGE = 100000;
    @VisibleForTesting
    public static final int MATCH_RANGE = 72000;
    private static final int STATE_IDLE = 3;
    private static final int STATE_READ_LAST_PAGE = 1;
    private static final int STATE_SEEK = 2;
    private static final int STATE_SEEK_TO_END = 0;
    private long end;
    private long endGranule;
    private final long endPosition;
    private final OggPageHeader pageHeader = new OggPageHeader();
    private long positionBeforeSeekToEnd;
    private long start;
    private long startGranule;
    /* access modifiers changed from: private */
    public final long startPosition;
    private int state;
    /* access modifiers changed from: private */
    public final StreamReader streamReader;
    private long targetGranule;
    /* access modifiers changed from: private */
    public long totalGranules;

    public DefaultOggSeeker(long startPosition2, long endPosition2, StreamReader streamReader2, long firstPayloadPageSize, long firstPayloadPageGranulePosition, boolean firstPayloadPageIsLastPage) {
        Assertions.checkArgument(startPosition2 >= 0 && endPosition2 > startPosition2);
        this.streamReader = streamReader2;
        this.startPosition = startPosition2;
        this.endPosition = endPosition2;
        if (firstPayloadPageSize == endPosition2 - startPosition2 || firstPayloadPageIsLastPage) {
            this.totalGranules = firstPayloadPageGranulePosition;
            this.state = 3;
            return;
        }
        this.state = 0;
    }

    /* JADX INFO: Multiple debug info for r0v7 long: [D('currentGranule' long), D('position' long)] */
    public long read(ExtractorInput input) throws IOException, InterruptedException {
        long position;
        int i = this.state;
        if (i == 0) {
            this.positionBeforeSeekToEnd = input.getPosition();
            this.state = 1;
            long lastPageSearchPosition = this.endPosition - 65307;
            if (lastPageSearchPosition > this.positionBeforeSeekToEnd) {
                return lastPageSearchPosition;
            }
        } else if (i != 1) {
            if (i == 2) {
                long currentGranule = this.targetGranule;
                if (currentGranule == 0) {
                    position = 0;
                } else {
                    long position2 = getNextSeekPosition(currentGranule, input);
                    if (position2 >= 0) {
                        return position2;
                    }
                    position = skipToPageOfGranule(input, this.targetGranule, -(position2 + 2));
                }
                this.state = 3;
                return -(2 + position);
            } else if (i == 3) {
                return -1;
            } else {
                throw new IllegalStateException();
            }
        }
        this.totalGranules = readGranuleOfLastPage(input);
        this.state = 3;
        return this.positionBeforeSeekToEnd;
    }

    public long startSeek(long timeUs) {
        int i = this.state;
        Assertions.checkArgument(i == 3 || i == 2);
        long j = 0;
        if (timeUs != 0) {
            j = this.streamReader.convertTimeToGranule(timeUs);
        }
        this.targetGranule = j;
        this.state = 2;
        resetSeeking();
        return this.targetGranule;
    }

    public OggSeekMap createSeekMap() {
        if (this.totalGranules != 0) {
            return new OggSeekMap();
        }
        return null;
    }

    @VisibleForTesting
    public void resetSeeking() {
        this.start = this.startPosition;
        this.end = this.endPosition;
        this.startGranule = 0;
        this.endGranule = this.totalGranules;
    }

    @VisibleForTesting
    public long getNextSeekPosition(long targetGranule2, ExtractorInput input) throws IOException, InterruptedException {
        ExtractorInput extractorInput = input;
        long j = 2;
        if (this.start == this.end) {
            return -(this.startGranule + 2);
        }
        long initialPosition = input.getPosition();
        if (!skipToNextPage(extractorInput, this.end)) {
            long j2 = this.start;
            if (j2 != initialPosition) {
                return j2;
            }
            throw new IOException("No ogg page can be found.");
        }
        this.pageHeader.populate(extractorInput, false);
        input.resetPeekPosition();
        long granuleDistance = targetGranule2 - this.pageHeader.granulePosition;
        int pageSize = this.pageHeader.headerSize + this.pageHeader.bodySize;
        if (granuleDistance < 0 || granuleDistance > 72000) {
            if (granuleDistance < 0) {
                this.end = initialPosition;
                this.endGranule = this.pageHeader.granulePosition;
            } else {
                this.start = input.getPosition() + ((long) pageSize);
                this.startGranule = this.pageHeader.granulePosition;
                if ((this.end - this.start) + ((long) pageSize) < 100000) {
                    extractorInput.skipFully(pageSize);
                    return -(this.startGranule + 2);
                }
            }
            long j3 = this.end;
            long j4 = this.start;
            if (j3 - j4 < 100000) {
                this.end = j4;
                return j4;
            }
            long j5 = (long) pageSize;
            if (granuleDistance > 0) {
                j = 1;
            }
            long offset = j5 * j;
            long j6 = this.end;
            long j7 = this.start;
            return Math.min(Math.max((input.getPosition() - offset) + (((j6 - j7) * granuleDistance) / (this.endGranule - this.startGranule)), j7), this.end - 1);
        }
        extractorInput.skipFully(pageSize);
        return -(this.pageHeader.granulePosition + 2);
    }

    /* access modifiers changed from: private */
    public long getEstimatedPosition(long position, long granuleDistance, long offset) {
        long j = this.endPosition;
        long j2 = this.startPosition;
        long position2 = position + ((((j - j2) * granuleDistance) / this.totalGranules) - offset);
        if (position2 < j2) {
            position2 = this.startPosition;
        }
        long j3 = this.endPosition;
        if (position2 >= j3) {
            return j3 - 1;
        }
        return position2;
    }

    private class OggSeekMap implements SeekMap {
        private OggSeekMap() {
        }

        public boolean isSeekable() {
            return true;
        }

        public SeekMap.SeekPoints getSeekPoints(long timeUs) {
            if (timeUs == 0) {
                return new SeekMap.SeekPoints(new SeekPoint(0, DefaultOggSeeker.this.startPosition));
            }
            long granule = DefaultOggSeeker.this.streamReader.convertTimeToGranule(timeUs);
            DefaultOggSeeker defaultOggSeeker = DefaultOggSeeker.this;
            return new SeekMap.SeekPoints(new SeekPoint(timeUs, defaultOggSeeker.getEstimatedPosition(defaultOggSeeker.startPosition, granule, 30000)));
        }

        public long getDurationUs() {
            return DefaultOggSeeker.this.streamReader.convertGranuleToTime(DefaultOggSeeker.this.totalGranules);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void skipToNextPage(ExtractorInput input) throws IOException, InterruptedException {
        if (!skipToNextPage(input, this.endPosition)) {
            throw new EOFException();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean skipToNextPage(ExtractorInput input, long limit) throws IOException, InterruptedException {
        long limit2 = Math.min(3 + limit, this.endPosition);
        byte[] buffer = new byte[2048];
        int peekLength = buffer.length;
        while (true) {
            if (input.getPosition() + ((long) peekLength) > limit2 && (peekLength = (int) (limit2 - input.getPosition())) < 4) {
                return false;
            }
            input.peekFully(buffer, 0, peekLength, false);
            for (int i = 0; i < peekLength - 3; i++) {
                if (buffer[i] == 79 && buffer[i + 1] == 103 && buffer[i + 2] == 103 && buffer[i + 3] == 83) {
                    input.skipFully(i);
                    return true;
                }
            }
            input.skipFully(peekLength - 3);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public long readGranuleOfLastPage(ExtractorInput input) throws IOException, InterruptedException {
        skipToNextPage(input);
        this.pageHeader.reset();
        while ((this.pageHeader.type & 4) != 4 && input.getPosition() < this.endPosition) {
            this.pageHeader.populate(input, false);
            input.skipFully(this.pageHeader.headerSize + this.pageHeader.bodySize);
        }
        return this.pageHeader.granulePosition;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public long skipToPageOfGranule(ExtractorInput input, long targetGranule2, long currentGranule) throws IOException, InterruptedException {
        this.pageHeader.populate(input, false);
        while (this.pageHeader.granulePosition < targetGranule2) {
            input.skipFully(this.pageHeader.headerSize + this.pageHeader.bodySize);
            currentGranule = this.pageHeader.granulePosition;
            this.pageHeader.populate(input, false);
        }
        input.resetPeekPosition();
        return currentGranule;
    }
}
